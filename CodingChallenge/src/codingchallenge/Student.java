/*
 * File name: Student.java
 * Programmer: Nikunj Ratnaparkhi
 * Date:  2/15/2016
 */
package codingchallenge;

/**
 *
 * @author Nikunj
 */
public class Student {
    
    private String user_id;
    private String user_name;
    private String course_id;
    private String state;

    /**
     * 
     * @param user_id
     * @param user_name
     * @param course_id
     * @param state 
     */
    public Student(String user_id, String user_name, String course_id, String state) {
        this.user_id = user_id;
        this.user_name = user_name;
        this.course_id = course_id;
        this.state = state;
    }

    /**
     * 
     * @return user_id
     */
    public String getUser_id() {
        return user_id;
    }
    
    /**
     * 
     * @param user_id 
     */
    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    /**
     * 
     * @return user_name
     */
    public String getUser_name() {
        return user_name;
    }

    /**
     * 
     * @param user_name 
     */
    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    /**
     * 
     * @return course_id
     */
    public String getCourse_id() {
        return course_id;
    }

    /**
     * 
     * @param course_id 
     */
    public void setCourse_id(String course_id) {
        this.course_id = course_id;
    }

    /**
     * 
     * @return state
     */
    public String getState() {
        return state;
    }

    /**
     * 
     * @param state 
     */
    public void setState(String state) {
        this.state = state;
    }
    
    
    
    
    
}
