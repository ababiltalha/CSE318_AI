public class Course {
    public String courseID;
    public int numberOfStudents;

    public Course(String courseID, int numberOfStudents) {
        this.courseID = courseID;
        this.numberOfStudents = numberOfStudents;
    }

    @Override
    public String toString() {
        return "Course " + courseID + " has " + numberOfStudents + " students";
    }
}
