import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scn = new Scanner(System.in);
        // taking choices for analysis
        String filename = "data/yor-f-83";

        ConstructiveHeuristic largestDegree = new LargestDegreeHeuristic();
        ConstructiveHeuristic largestEnrollment = new LargestEnrollmentHeuristic();
        ConstructiveHeuristic dSatur = new DSaturHeuristic();
        ConstructiveHeuristic random = new RandomOrderedHeuristic();

        PenaltyStrategy exponential = new ExponentialStrategy();
        PenaltyStrategy linear = new LinearStrategy();

        assignTimeSlots(filename, largestDegree, exponential);
//        assignTimeSlots(filename, dSatur, exponential);
//        assignTimeSlots(filename, largestEnrollment, exponential);
//        assignTimeSlots(filename, random, exponential);



    }

    static void assignTimeSlots(String filename, ConstructiveHeuristic constructiveHeuristic, PenaltyStrategy penaltyStrategy) {
        System.out.println(filename);
        writeLog(filename);
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
        double initialPenalty;

        numberOfTimeslots = graph.colorGraph(constructiveHeuristic);
        System.out.println(constructiveHeuristic + " heuristic applied, timeslots required : " + numberOfTimeslots);
        writeLog(constructiveHeuristic + " heuristic applied, timeslots required : " + numberOfTimeslots);

        initialPenalty = penaltyStrategy.getAveragePenalty(graph, students);
        System.out.println(penaltyStrategy + ", initial penalty : " + initialPenalty);
        writeLog(penaltyStrategy + ", initial penalty : " + initialPenalty);

        // print vertices of the graph
//        for (int i = 0; i < graph.vertices.size(); i++) {
//            System.out.println(graph.vertices.get(i).course);
//        }




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

    // keep track of output
    public static void writeLog(String str){
        try {
            FileWriter fw = new FileWriter("log.txt", true);
            fw.write(str+"\n");
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


