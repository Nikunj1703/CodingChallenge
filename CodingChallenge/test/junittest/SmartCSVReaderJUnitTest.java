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
    //If Course CSV is given as an input, it should return false
    @Test
    public void determineCSVByHeaderForCourseCSV() {
        String myFile = "CSV2.csv";
        SmartCSVReaderMain testProject = new SmartCSVReaderMain();
        boolean expectedResult = false;
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
    
    
    //Unit Test: To test if the Student CSV is in any random column order
    @Test
    public void csvFormatCheckerForIncorrectStudentCSVTest() throws FileNotFoundException, IOException {
        String myFile = "CSV3.csv";
        boolean isStudentCSV = true;
        CSVFormat studentCsvFileFormat = CSVFormat.DEFAULT.withHeader("state", "user_id", "user_name","course_id");
        FileReader fileReader = new FileReader(myFile);
        CSVParser csvFileParser = new CSVParser(fileReader, studentCsvFileFormat);
        List studentCsvRecords = csvFileParser.getRecords(); 
        
        SmartCSVReaderMain testProject = new SmartCSVReaderMain();

        boolean expectedResult = true;
        boolean actualResult = testProject.csvFormatChecker((CSVRecord) studentCsvRecords.get(0), isStudentCSV);
        assertEquals("Unit Test: To test if the Student CSV is in any random column order", expectedResult, actualResult);
    }
    
    //Unit Test of readCSV and storeInStudentHashMap method
    @Test
     public void storeInStudentHashMapTest() {
         Student student = new Student("1", "ABC", "2", "active");
         HashMap<String, Student> temp = new HashMap<String, Student>();
         SmartCSVReaderMain testProject = new SmartCSVReaderMain();
         testProject.storeInStudentHashMap(student, temp);
         int expectedResult = 1;
         assertEquals("Unit Test: To test if storeInStudentHashMap method is adding new records", expectedResult, temp.size());
         
         student = new Student("1", "ABC", "2", "deleted");
         testProject.storeInStudentHashMap(student, temp);
         assertEquals("Unit Test: To test if storeInStudentHashMap method is replacing existing records", expectedResult, temp.size());
         
         student = new Student("2", "XYZ", "2", "active");
         expectedResult = 2;
         testProject.storeInStudentHashMap(student, temp);
         assertEquals("Unit Test: To test if storeInStudentHashMap method is adding 1 more record", expectedResult, temp.size());
     }

    //Unit Test of readCSV and storeInCourseHashMap method
    @Test
    public void storeInCourseHashMapTest() {
         Course course = new Course("1", "Science", "active");
         HashMap<String, Course> temp = new HashMap<String, Course>();
         SmartCSVReaderMain testProject = new SmartCSVReaderMain();
         testProject.storeInCourseHashMap(course, temp);
         int expectedResult = 1;
         assertEquals("Unit Test: To test if storeInCourseHashMap method is adding new records", expectedResult, temp.size());
         
         course = new Course("1", "Science", "deleted");
         testProject.storeInCourseHashMap(course, temp);
         assertEquals("Unit Test: To test if storeInCourseHashMap method is replacing existing records", expectedResult, temp.size());
         
         course = new Course("2", "Biology", "active");
         expectedResult = 2;
         testProject.storeInCourseHashMap(course, temp);
         assertEquals("Unit Test: To test if storeInCourseHashMap method is adding 1 more record", expectedResult, temp.size());
    }
    
    //Unit Test: To test the value of header information in any CSV which has Jumbled Columns
    @Test
    public void findCSVHeaderInformationTest(){
        String myFile = "JumbledColumnStudentCSV.csv";
        SmartCSVReaderMain testProject = new SmartCSVReaderMain();
        
        String[] expectedResult = "state,user_id,course_id,user_name".split(",");
        String[] actualResult = testProject.findCSVHeaderInformation(myFile);
        
        assertEquals("Unit Test: To test the header of any CSV file which has Jumbled Columns", expectedResult, actualResult);
        
    }
    
    //Unit Test: To check if the format of Data in Student CSV is in correct format.
    @Test
    public void checkFormatOfDataInStudentCSVTest() throws FileNotFoundException, IOException{
        String myFile = "CSV1.csv";
        boolean isStudentCSV = true;
       
        CSVFormat studentCsvFileFormat = CSVFormat.DEFAULT.withHeader("user_id", "user_name", "course_id", "state");
        FileReader fileReader = new FileReader(myFile);
        CSVParser csvFileParser = new CSVParser(fileReader, studentCsvFileFormat);
        List studentCsvRecords = csvFileParser.getRecords(); 
        SmartCSVReaderMain testProject = new SmartCSVReaderMain();

        boolean expectedResult = true;
        boolean actualResult = testProject.checkFormatOfDataInCSV((CSVRecord) studentCsvRecords.get(6), isStudentCSV);
        assertEquals("Unit Test: To check if the format of Data in Student CSV is in correct format", expectedResult, actualResult);
    }
    
    //Unit Test: To check if the format of Data in Student CSV is in correct format.
    @Test
    public void checkFormatOfCourseDataInStudentCSVTest() throws FileNotFoundException, IOException{
        String myFile = "CSV1.csv";
        boolean isStudentCSV = true;
       
        CSVFormat studentCsvFileFormat = CSVFormat.DEFAULT.withHeader("user_id", "user_name", "course_id", "state");
        FileReader fileReader = new FileReader(myFile);
        CSVParser csvFileParser = new CSVParser(fileReader, studentCsvFileFormat);
        List studentCsvRecords = csvFileParser.getRecords(); 
        SmartCSVReaderMain testProject = new SmartCSVReaderMain();

        boolean expectedResult = false;
        boolean actualResult = testProject.checkFormatOfDataInCSV((CSVRecord) studentCsvRecords.get(9), isStudentCSV);
        assertEquals("Unit Test: To check if the format of Data in Student CSV is in correct format", expectedResult, actualResult);
    }
    
    
    //Unit Test: To check if the format of Data in Student CSV is in incorrect format.
    @Test
    public void checkFormatOfDataInStudentCSVNegativeTest() throws FileNotFoundException, IOException{
        String myFile = "CSV1.csv";
        boolean isStudentCSV = true;
       
        CSVFormat studentCsvFileFormat = CSVFormat.DEFAULT.withHeader("user_id", "user_name", "course_id", "state");
        FileReader fileReader = new FileReader(myFile);
        CSVParser csvFileParser = new CSVParser(fileReader, studentCsvFileFormat);
        List studentCsvRecords = csvFileParser.getRecords(); 
        SmartCSVReaderMain testProject = new SmartCSVReaderMain();

        boolean expectedResult = false;
        boolean actualResult = testProject.checkFormatOfDataInCSV((CSVRecord) studentCsvRecords.get(7), isStudentCSV);
        assertEquals("Unit Test: To check if the format of Data in Student CSV is in incorrect format.", expectedResult, actualResult);
    }
    
    //Unit Test: To check if the format of Data in Course CSV is in incorrect format.
    @Test
    public void checkFormatOfDataInCourseCSVNegativeTest() throws FileNotFoundException, IOException{
        String myFile = "CSV2.csv";
        boolean isStudentCSV = false;
       
        CSVFormat studentCsvFileFormat = CSVFormat.DEFAULT.withHeader("course_id", "course_name", "state");
        FileReader fileReader = new FileReader(myFile);
        CSVParser csvFileParser = new CSVParser(fileReader, studentCsvFileFormat);
        List studentCsvRecords = csvFileParser.getRecords(); 
        SmartCSVReaderMain testProject = new SmartCSVReaderMain();

        boolean expectedResult = false;
        boolean actualResult = testProject.checkFormatOfDataInCSV((CSVRecord) studentCsvRecords.get(4), isStudentCSV);
        assertEquals("Unit Test: To check if the format of Data in Course CSV is in incorrect format", expectedResult, actualResult);
    }


    /**
     * Acceptance Test This test case sets the acceptance criteria by testing
     * readCSV, storeInCourseHashMap, convertHashMapToArrayList, and
     * determineActiveStudentsInActiveCourse method This test case also tests
     * that Student with Invalid course ID should not show-up in the final list
     */
    @Test
    public void determineActiveStudentsInActiveCourseTest() {
        HashMap<String, Student> studentHash = new HashMap<String, Student>();
        studentHash.put("1", new Student("1", "Ross Galler", "1", "active"));
        studentHash.put("3", new Student("3", "Rachel Green", "1", "active"));
        studentHash.put("4", new Student("4", "Chandler Bing", "2", "active"));
        studentHash.put("2", new Student("2", "Joye Tribiyani", "3", "deleted"));
        studentHash.put("5", new Student("5", "Monica Galler", "5", "active"));
        
        HashMap<String, Course> courseHash = new HashMap<String, Course>();
        courseHash.put("1", new Course("1", "Physics", "active"));
        courseHash.put("2", new Course("2", "Biology", "deleted"));
        courseHash.put("3", new Course("3", "English", "active"));

        SmartCSVReaderMain testProject = new SmartCSVReaderMain();
        int expectedResult = 2;
        int actualResult = testProject.determineActiveStudentsInActiveCourse(studentHash, courseHash);

        assertEquals("Acceptance Test to determine Active Students In Active Course", expectedResult, actualResult);
    }
}
