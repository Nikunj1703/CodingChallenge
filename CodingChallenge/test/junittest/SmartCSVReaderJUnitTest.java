package junittest;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import codingchallenge.Course;
import codingchallenge.SmartCSVReaderMain;
import codingchallenge.Student;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 *
 * @author Nikunj
 */
public class SmartCSVReaderJUnitTest {
    
     /**
     * Unit Test Case of readCSV of Students
     * storeInStudentHashMap and csvFormatChecker methods are Mocked
     * This Test method checks if correct number of records are read from Student CSV
     * */
    @Test
    public void readStudentCSVTest() 
    {
        String myFile = "CSV1.csv";
        boolean isStudentCSV = true;
        SmartCSVReaderMain temp = mock(SmartCSVReaderMain.class);
        SmartCSVReaderMain projectMain = new SmartCSVReaderMain();
        when(temp.storeInStudentHashMap(new Student("","","",""), new HashMap<String, Student>())).thenReturn(0);
        when(temp.csvFormatChecker(null, true)).thenReturn(true);
        
        int expectedResult = 7;
        int actualResult = projectMain.readCSV(myFile, isStudentCSV, new HashMap<String, Student>());
        
        assertEquals("Unit Test: Number of Records read in Student CSV File", expectedResult, actualResult);
    }

    /**
     * Unit Test Case of readCSV of Courses
     * storeInCourseHashMap and csvFormatChecker methods are Mocked
     * This Test method checks if correct number of records are read from Course CSV
     * */
    @Test
    public void readCourseCSVTest() {
        String myFile = "CSV2.csv";
        boolean isStudentCSV = false;
        HashMap<String, Course> recordHash = new HashMap<String, Course>();
        SmartCSVReaderMain temp = mock(SmartCSVReaderMain.class);
        SmartCSVReaderMain projectMain = new SmartCSVReaderMain();
        when(temp.storeInCourseHashMap(new Course("","",""), new HashMap<String, Course>())).thenReturn(0);
        when(temp.csvFormatChecker(null, false)).thenReturn(true);
        
        int expectedResult = 4;
        int actualResult = projectMain.readCSV(myFile, isStudentCSV, new HashMap<String, Course>());
        
        assertEquals("Unit Test: Number of Records read in Course CSV File", expectedResult, actualResult);
    }

    //Unit Test Case of determining CSV by header for Students
    //If Student CSV is given as an input, it should return true
    @Test
    public void determineCSVByHeaderForStudentCSV() {
        String myFile = "CSV1.csv";
        SmartCSVReaderMain testProject = new SmartCSVReaderMain();
        boolean expectedResult = true;
        boolean actualResult = testProject.determineCSVTypeByHeader(myFile);

        assertEquals("Unit Test: Determine Student CSV File by Header Informaiton", expectedResult, actualResult);
    }

    //Unit Test Case of determining CSV by header for Courses
    //If Course CSV is given as an input, it should return true
    @Test
    public void determineCSVByHeaderForCourseCSV() {
        String myFile = "CSV2.csv";
        SmartCSVReaderMain testProject = new SmartCSVReaderMain();
        boolean expectedResult = true;
        boolean actualResult = testProject.determineCSVTypeByHeader(myFile);

        assertEquals("Unit Test: Determine Course CSV File by Header Informaiton", expectedResult, actualResult);
    }

    //Unit Test: To test if the Student CSV is in correct format
    @Test
    public void csvFormatCheckerForStudentCSVTest() throws FileNotFoundException, IOException {
        String myFile = "CSV1.csv";
        boolean isStudentCSV = true;
       
        CSVFormat studentCsvFileFormat = CSVFormat.DEFAULT.withHeader("user_id", "user_name", "course_id", "state");
        FileReader fileReader = new FileReader(myFile);
        CSVParser csvFileParser = new CSVParser(fileReader, studentCsvFileFormat);
        List studentCsvRecords = csvFileParser.getRecords(); 
        SmartCSVReaderMain testProject = new SmartCSVReaderMain();

        boolean expectedResult = true;
        boolean actualResult = testProject.csvFormatChecker((CSVRecord) studentCsvRecords.get(0), isStudentCSV);
        assertEquals("Unit Test: To test if the Student CSV is in correct format", expectedResult, actualResult);
    }

    //Unit Test: To test if the Course CSV is in correct format
    @Test
    public void csvFormatCheckerForCourseCSVTest() throws FileNotFoundException, IOException {
        String myFile = "CSV2.csv";
        boolean isStudentCSV = false;
        CSVFormat courseCsvFileFormat = CSVFormat.DEFAULT.withHeader("course_id", "course_name", "state");
        FileReader fileReader = new FileReader(myFile);
        CSVParser csvFileParser = new CSVParser(fileReader, courseCsvFileFormat);
        List courseCsvRecords = csvFileParser.getRecords(); 
        
        SmartCSVReaderMain testProject = new SmartCSVReaderMain();

        boolean expectedResult = true;
        boolean actualResult = testProject.csvFormatChecker((CSVRecord) courseCsvRecords.get(0), isStudentCSV);
        assertEquals("Unit Test: To test if the Course CSV is in correct format", expectedResult, actualResult);
    }
}
