/*
 * File name: SmartCSVReaderMain.java
 * Programmer: Nikunj Ratnaparkhi
 * Date:  2/15/2016
 */
package codingchallenge;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

/**
 *
 * @author Nikunj
 */
public class SmartCSVReaderMain {

    public static void main(String[] args) {
        
        SmartCSVReaderMain myMainClassObj = new SmartCSVReaderMain();
        
        
        String[] CSVFile = new String[2];
        CSVFile[0] = "CSV1.csv";
        CSVFile[1] = "CSV2.csv";
        String studentCSVFile="";
        String courseCSVFile="";
        boolean isStudentCSV = false;
        /**
         * Out of 2 CSV inputs, which CSV is Student CSV and which one is Course.
         * */
        for(int i=0;i<CSVFile.length;i++){
            isStudentCSV = myMainClassObj.determineCSVTypeByHeader(CSVFile[i]);
            if(isStudentCSV)
                studentCSVFile = CSVFile[i];
            else
                courseCSVFile = CSVFile[i];
        }
        //HashMaps to store Student and Course CSV
        HashMap<String, Student> studentHash = new HashMap<String, Student>();
        HashMap<String, Course>  courseHash = new HashMap<String, Course>() ;
        
        //Reading the Data inside CSV and Storing it into the HashMap.
        //New record gets added and existing record is updated
        myMainClassObj.readCSV(studentCSVFile, true, studentHash);
        myMainClassObj.readCSV(courseCSVFile, false, courseHash);
        
        myMainClassObj.determineActiveStudentsInActiveCourse(studentHash,courseHash);
    }
    
    
       /**
        * This method reads the Student CSV and Course CSV, one at a time.
        * @param myFile
        * @param isStudentCSV
        * @param recordHash
        * @return 
        */
       public int readCSV(String myFile, boolean isStudentCSV, HashMap<String, ?> recordHash){
        //CSV file header
        //findCSVHeaderInformation handles the situation whenever CSV file with random column order is given as an input
        final String [] FILE_HEADER = findCSVHeaderInformation(myFile);
        final String USER_ID = "user_id";
        final String USER_NAME = "user_name";
        final String COURSE_ID = "course_id";
        final String STATE = "state";
        final String COURSE_NAME = "course_name";
        
        boolean isCSVValidFormat = false;
        int numOfRecords = 0;
        FileReader fileReader = null;
        CSVParser csvFileParser = null;
        
        CSVFormat csvFileFormat = CSVFormat.DEFAULT.withHeader(FILE_HEADER);
        
        try {
            fileReader = new FileReader(myFile);
            //If Student CSV then check format and built the Student HashMap
            if(isStudentCSV){
                csvFileParser = new CSVParser(fileReader, csvFileFormat);
                List studentCsvRecords = csvFileParser.getRecords(); 
                isCSVValidFormat = csvFormatChecker((CSVRecord) studentCsvRecords.get(0), isStudentCSV);
                
                if(isCSVValidFormat){
                    HashMap<String, Student> studentTempHash = (HashMap<String, Student>) recordHash;
                    for (int i = 1; i < studentCsvRecords.size(); i++) {
                        CSVRecord record = (CSVRecord) studentCsvRecords.get(i);
                        storeInStudentHashMap(new Student(record.get(USER_ID).toString(),
                                                        record.get(USER_NAME).toString(),
                                                        record.get(COURSE_ID).toString(),
                                                        record.get(STATE).toString()), 
                                                        studentTempHash);
                        numOfRecords++;
                    }
                }
            }else{   //If Course CSV then check format and built the Course HashMap
                csvFileParser = new CSVParser(fileReader,csvFileFormat);
                List courseCsvRecords = csvFileParser.getRecords(); 
                isCSVValidFormat = csvFormatChecker((CSVRecord) courseCsvRecords.get(0), isStudentCSV);
                
                if(isCSVValidFormat){
                    HashMap<String, Course> courseTempHash = (HashMap<String, Course>) recordHash;
                    for (int i = 1; i < courseCsvRecords.size(); i++) {
                        CSVRecord record = (CSVRecord) courseCsvRecords.get(i);
                        storeInCourseHashMap(new Course(record.get(COURSE_ID).toString(),
                                                        record.get(COURSE_NAME).toString(),
                                                        record.get(STATE).toString()), 
                                                        courseTempHash);
                        numOfRecords++;
                    }
                }
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(SmartCSVReaderMain.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(SmartCSVReaderMain.class.getName()).log(Level.SEVERE, null, ex);
        }  
        return numOfRecords;
    }
       
    
    /**
     * This method checks if the Student CSV or Course CSV is in correct format
     * @param header
     * @param isStudentCSV
     * @return 
     */
    public boolean csvFormatChecker(CSVRecord header, boolean isStudentCSV) {
        if(isStudentCSV){
            if(header.get("user_id").toString().trim().equals("user_id") &&
                    header.get("user_name").toString().trim().equals("user_name") &&
                    header.get("course_id").toString().trim().equals("course_id") &&
                    header.get("state").toString().trim().equals("state")){
                if(header.size() == 4)
                    return true;
            }
        }else{
            if(header.get("course_id").toString().trim().equals("course_id") &&
                    header.get("course_name").toString().trim().equals("course_name") &&
                    header.get("state").toString().trim().equals("state")){
                if(header.size() == 3)
                    return true;
            }
        }
        System.out.println("Incorrect format of CSV file");
        return false;
    }

    /**
     * This method Store Student Object in HashMap. user_id is Key and Student Object is Value
     * @param student
     * @param recordHash 
     * @return 
     */
    public int storeInStudentHashMap(Student student, HashMap<String, Student> recordHash) {
        if(!recordHash.containsKey(student.getUser_id())){
            recordHash.put(student.getUser_id(), student);
        }
        else
            recordHash.replace(student.getUser_id(), student);
        return recordHash.size();
    }

    /**
     * This method Store Course Object in HashMap. course_id is Key and Course Object is Value
     * @param course
     * @param recordHash
     * @return 
     */
    public int storeInCourseHashMap(Course course, HashMap<String, Course> recordHash) {
        if(!recordHash.containsKey(course.getCourse_id())){
            recordHash.put(course.getCourse_id(), course);
        }
        else
            recordHash.replace(course.getCourse_id(), course); 
        return recordHash.size();
    }
    
        /**
     * This method returns the header of CSV so that it can be used further to parse CSV
     * This method also handles the situation if any CSV with jumbled column order is given as input
     * @param CSVFile
     * @return 
     */
    public String[] findCSVHeaderInformation(String CSVFile){
        BufferedReader br = null;
        StringBuilder header = new StringBuilder();
        File file = new File(CSVFile);
        try {
            br = new BufferedReader (new FileReader(file));
            try {
                 header.append(br.readLine().toString());
            } catch (IOException ex) {
                Logger.getLogger(SmartCSVReaderMain.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(SmartCSVReaderMain.class.getName()).log(Level.SEVERE, null, ex);
        }
        String tempHeader[] = header.toString().split(",");
        int i = 0;
        for(String s : tempHeader){
            s = s.trim();
            tempHeader[i] = s;
            i++;
        }
        return tempHeader;
    }
    
    /**
     * This method determines which CSV file is Student CSV and which one is Course CSV
     * @param CSVFile
     * @return 
     */ 
    public boolean determineCSVTypeByHeader(String CSVFile){
        BufferedReader br = null;
        StringBuilder header = new StringBuilder();
        File file = new File(CSVFile);
        try {
            br = new BufferedReader (new FileReader(file));
            try {
                 header.append(br.readLine().toString());
            } catch (IOException ex) {
                Logger.getLogger(SmartCSVReaderMain.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(SmartCSVReaderMain.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if(header.toString().contains("user_id"))
            return true;
        return false;
    }
    
    
    /**
     * This method list Active Students who are registered in Active Course
     * @param student
     * @param course 
     */
    public void determineActiveStudentsInActiveCourse(HashMap<String, Student> student, HashMap<String, Course> course){
        int finalRecordCount = 0;
         for(String courseKey: course.keySet()){
             if(course.get(courseKey).getState().trim().toString().equals("active")){
                 for(String studentKey: student.keySet()){
                     if(student.get(studentKey).getState().trim().toString().equals("active") && student.get(studentKey).getCourse_id().trim().toString().equals(course.get(courseKey).getCourse_id().toString())){
                        System.out.println("Active Course: "+course.get(courseKey).getCourse_name()+" || Name of Active Student: "+student.get(studentKey).getUser_name());
                        finalRecordCount++;      
                     }
                 }
             }
         }
    }
    
    
    
}
