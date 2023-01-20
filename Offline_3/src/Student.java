import java.util.ArrayList;

public class Student {
    public int studentID;
    public int numberOfCourses;
    public ArrayList<String> courses;

    public Student(int studentID, ArrayList<String> courses) {
        this.studentID = studentID;
        this.courses = courses;
        this.numberOfCourses = this.courses.size();
    }

    public void addCourse(String courseID) {
        this.courses.add(courseID);
        this.numberOfCourses++;
    }

    @Override
    public String toString() {
        return "Student " + studentID + " has " + numberOfCourses + " courses " + courses;
    }
}
