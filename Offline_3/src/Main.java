import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        String filename = "data/in";
        File courseFile = new File(filename + ".crs");
        File studentFile = new File(filename + ".stu");

        ArrayList<Course> courses = new ArrayList<>();
        ArrayList<Student> students = new ArrayList<>();

        try {
            Scanner scn = new Scanner(courseFile);
            while (scn.hasNextLine()){
                String line = scn.nextLine();
                String[] splits = line.split(" ");
                String courseID = splits[0];
                int numberOfStudents = Integer.parseInt(splits[1]);
                Course course = new Course(courseID, numberOfStudents);
                courses.add(course);
            }

            scn = new Scanner(studentFile);
            int studentID = 1;
            while (scn.hasNextLine()){
                String line = scn.nextLine();
                String[] splits = line.split(" ");
                Student student = new Student(studentID++, new ArrayList<>());
                for (int i = 0; i < splits.length; i++) {
                    student.addCourse(splits[i]);
                }
                students.add(student);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

//        for (Course course : courses) System.out.println(course);
//        for (Student student : students) System.out.println(student);

        ArrayList<Node> nodes = new ArrayList<>();
        for (Course course : courses) {
            nodes.add(new Node(course));
        }

        Graph graph = new Graph(nodes);
        for (Student student : students) {
            for (int i = 0; i < student.numberOfCourses; i++) {
                for (int j = i + 1; j < student.numberOfCourses; j++) {
                    String courseID1 = student.courses.get(i);
                    String courseID2 = student.courses.get(j);
                    Node node1 = null, node2 = null;
                    for (Node node : nodes) {
                        if (node.course.courseID.equals(courseID1)) node1 = node;
                        if (node.course.courseID.equals(courseID2)) node2 = node;
                    }
                    graph.connect(node1, node2);
                }
            }
        }
//        graph.printAdjacencyList();
        int numberOfTimeslots = graph.colorGraph();
        System.out.println("Number of timeslots: " + numberOfTimeslots);











    }
}
