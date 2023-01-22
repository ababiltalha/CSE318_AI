import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scn = new Scanner(System.in);
        // taking choices for analysis
        String filename = "data/tre-s-92";
        System.out.println(filename);
        File courseFile = new File(filename + ".crs");
        File studentFile = new File(filename + ".stu");

        ArrayList<Course> courses = new ArrayList<>();
        ArrayList<Student> students = new ArrayList<>();

        try {
            processInput(courseFile, studentFile, courses, students);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

//        for (Course course : courses) System.out.println(course);
//        for (Student student : students) System.out.println(student);

        Graph graph = createGraph(courses, students);

        int numberOfTimeslots;

        ConstructiveHeuristic largestDegreeHeuristic = new LargestDegreeHeuristic();
        ConstructiveHeuristic largestEnrollmentHeuristic = new LargestEnrollmentHeuristic();
        ConstructiveHeuristic DSaturHeuristic = new DSaturHeuristic();
        ConstructiveHeuristic randomOrderedHeuristic = new RandomOrderedHeuristic();

        numberOfTimeslots = graph.colorGraph(largestDegreeHeuristic);
        System.out.println(largestDegreeHeuristic + " heuristic applied, timeslots required : " + numberOfTimeslots);

        numberOfTimeslots = graph.colorGraph(largestEnrollmentHeuristic);
        System.out.println(largestEnrollmentHeuristic + " heuristic applied, timeslots required : " + numberOfTimeslots);

        numberOfTimeslots = graph.colorGraph(DSaturHeuristic);
        System.out.println(DSaturHeuristic + " heuristic applied, timeslots required : " + numberOfTimeslots);

        numberOfTimeslots = graph.colorGraph(randomOrderedHeuristic);
        System.out.println(randomOrderedHeuristic + " heuristic applied, timeslots required : " + numberOfTimeslots);













    }

    static void processInput(File courseFile, File studentFile, ArrayList<Course> courses,
                             ArrayList<Student> students) throws FileNotFoundException {
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
    }

    static Graph createGraph(ArrayList<Course> courses, ArrayList<Student> students) {
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
                    if(graph.adjacencyList[node1.index].contains(node2)
                            && graph.adjacencyList[node2.index].contains(node1)) continue;
                    graph.connect(node1, node2);
                }
            }
        }
//        graph.printAdjacencyList();
        return graph;
    }


}
