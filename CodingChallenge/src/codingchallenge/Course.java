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
public class Course {
    private String course_id;
    private String course_name;
    private String state;

    /**
     * 
     * @param course_id
     * @param course_name
     * @param state 
     */
    public Course(String course_id, String course_name, String state) {
        this.course_id = course_id;
        this.course_name = course_name;
        this.state = state;
    }

    /**
     * 
     * @return 
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
     * @return 
     */
    public String getCourse_name() {
        return course_name;
    }

    /**
     * 
     * @param course_name 
     */
    public void setCourse_name(String course_name) {
        this.course_name = course_name;
    }

    /**
     * 
     * @return 
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
