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
        for(int i=0;i<CSVFile.length;i++){
            isStudentCSV = myMainClassObj.determineCSVTypeByHeader(CSVFile[i]);
            if(isStudentCSV)
                studentCSVFile = CSVFile[i];
            else
                courseCSVFile = CSVFile[i];
        }
    }
    
    
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
    
}
