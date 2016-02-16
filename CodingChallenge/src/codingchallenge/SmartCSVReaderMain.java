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
    }
    
    
       /**
        * This method reads the Student CSV and Course CSV, one at a time.
        * @param myFile
        * @param isStudentCSV
        * @param recordHash
        * @return 
        */
       public void readCSV(String myFile, boolean isStudentCSV, HashMap<String, ?> recordHash){
        //CSV file header
        //findCSVHeaderInformation handles the situation whenever CSV file with random column order is given as an input
        final String [] FILE_HEADER = findCSVHeaderInformation(myFile);
        final String USER_ID = "user_id";
        final String USER_NAME = "user_name";
        final String COURSE_ID = "course_id";
        final String STATE = "state";
        final String COURSE_NAME = "course_name";
        
        boolean isCSVValidFormat = false;
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
                    }
                }
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(SmartCSVReaderMain.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(SmartCSVReaderMain.class.getName()).log(Level.SEVERE, null, ex);
        }        
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

    private boolean csvFormatChecker(CSVRecord csvRecord, boolean studentCSV) {
       // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        return true;
    }

    private void storeInStudentHashMap(Student student, HashMap<String, Student> studentTempHash) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void storeInCourseHashMap(Course course, HashMap<String, Course> courseTempHash) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
