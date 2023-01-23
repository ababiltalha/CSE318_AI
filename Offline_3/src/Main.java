import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Main {
    static final int MAXITERATIONS = 1000;

    public static void main(String[] args) {
        Scanner scn = new Scanner(System.in);
        // taking choices for analysis
        System.out.println("Enter choice of dataset:" +
                "\n1. car-f-92" +
                "\n2. car-s-91" +
                "\n3. kfu-s-93" +
                "\n4. tre-s-92" +
                "\n5. yor-f-83");
        int dataset = scn.nextInt()-1;
        System.out.println("Enter choice of constructive heuristic:" +
                "\n1. Largest Degree Heuristic" +
                "\n2. DSatur Heuristic" +
                "\n3. Largest Enrollment Heuristic" +
                "\n4. Random Ordered Heuristic");
        int heu = scn.nextInt()-1;
        System.out.println("Enter choice of penalty strategy:" +
                "\n1. Exponential Penalty" +
                "\n2. Linear Penalty");
        int pen = scn.nextInt()-1;


        String[] filename = {"data/car-f-92",
                "data/car-s-91",
                "data/kfu-s-93",
                "data/tre-s-92",
                "data/yor-f-83"};

        ConstructiveHeuristic[] constructiveHeuristic = {new LargestDegreeHeuristic(),
                new DSaturHeuristic(),
                new LargestEnrollmentHeuristic(),
                new RandomOrderedHeuristic()};

        PenaltyStrategy[] penaltyStrategy = {new ExponentialStrategy(),
                new LinearStrategy()};

        assignTimeSlots(filename[dataset], constructiveHeuristic[heu], penaltyStrategy[pen]);
    }

    static void assignTimeSlots(String filename, ConstructiveHeuristic constructiveHeuristic, PenaltyStrategy penaltyStrategy) {
        System.out.println(filename);
        writeLog(filename);
        File courseFile = new File(filename + ".crs");
        File studentFile = new File(filename + ".stu");

        ArrayList<Course> courses = new ArrayList<>();
        ArrayList<Student> students = new ArrayList<>();

        try {
            // populate courses and students lists
            processInput(courseFile, studentFile, courses, students);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        // form a graph with the data populated
        Graph graph = createGraph(courses, students);

        int numberOfTimeslots;
        double initialPenalty;

        numberOfTimeslots = graph.colorGraph(constructiveHeuristic);
        // check if coloring breaks hard constraint
        if(graph.checkColoring()) System.out.println("Coloring is valid");
        else System.out.println("Coloring is invalid");

        System.out.println(constructiveHeuristic + " heuristic applied, timeslots required : " + numberOfTimeslots);
        writeLog(constructiveHeuristic + " heuristic applied, timeslots required : " + numberOfTimeslots);

        // calculate initial penalty
        initialPenalty = penaltyStrategy.getAveragePenalty(graph, students);
        System.out.println(penaltyStrategy + ", initial penalty : " + initialPenalty);
        writeLog(penaltyStrategy + ", initial penalty : " + String.format("%,.3f", initialPenalty));

        double currentPenalty = initialPenalty;
        double penaltyAfterKempeChain;
        int i = 0;
        // applying Kempe-chain Interchange
        while(i < MAXITERATIONS){
            // take a node at random and take a random neighbor of that node
            Node node1 = graph.vertices.get((int) ((Math.random() * graph.n)));
            Node node2;
            if(!graph.adjacencyList[node1.index].isEmpty())
                node2 = graph.adjacencyList[node1.index].get(
                        (int) ((Math.random() * graph.adjacencyList[node1.index].size())));
            else continue;
            KempeChain.interchange(graph, node1, node2);
//            System.out.println(graph.checkColoring());
            penaltyAfterKempeChain = penaltyStrategy.getAveragePenalty(graph, students);
            // if decreasing penalty, continue, else interchange back
            if (penaltyAfterKempeChain < currentPenalty) currentPenalty = penaltyAfterKempeChain;
            else KempeChain.interchange(graph, node1, node2);
            i++;
        }
        System.out.println("After " + i + " iterations of Kempe chain interchange, penalty : " + currentPenalty);
        writeLog("After " + i + " iterations of Kempe chain interchange, penalty : " + String.format("%,.3f", currentPenalty));

        // check if interchanging breaks hard constraint
        if(graph.checkColoring()) System.out.println("Coloring is still valid");
        else System.out.println("Coloring is invalid");

        double penaltyAfterPairSwap;
        i = 0;
        // applying Pair-swap Operator
        while(i < MAXITERATIONS){
            // take two random nodes
            Node node1 = graph.vertices.get((int) ((Math.random() * graph.n)));
            Node node2 = graph.vertices.get((int) ((Math.random() * graph.n)));
            // if same node, continue
            if (node2.course.courseID.equals(node1.course.courseID)) continue;
            int timeslot1 = graph.getTimeSlot(node1.course.courseID);
            int timeslot2 = graph.getTimeSlot(node2.course.courseID);
            // if assigned same timeslot, continue
            if (timeslot1 == timeslot2) continue;
            // if neighbor of one assigned same timeslot, continue
            boolean flag = false;
            for (Node neighbor : graph.adjacencyList[node1.index]) {
                if (graph.getTimeSlot(neighbor.course.courseID) == timeslot1
                        || graph.getTimeSlot(neighbor.course.courseID) == timeslot2) flag=true;
            }
            if (flag) continue;
            flag = false;
            for (Node neighbor : graph.adjacencyList[node2.index]) {
                if (graph.getTimeSlot(neighbor.course.courseID) == timeslot1
                        || graph.getTimeSlot(neighbor.course.courseID) == timeslot2) flag=true;
            }
            if (flag) continue;

            // swap timeslots
            graph.assignedColors[node1.index] = timeslot2;
            graph.assignedColors[node2.index] = timeslot1;
            penaltyAfterPairSwap = penaltyStrategy.getAveragePenalty(graph, students);
            // if decreasing penalty, continue, else swap back
            if (penaltyAfterPairSwap < currentPenalty) currentPenalty = penaltyAfterPairSwap;
            else {
                graph.assignedColors[node1.index] = timeslot1;
                graph.assignedColors[node2.index] = timeslot2;
            }
            i++;
        }
        System.out.println("After " + i + " iterations of pair swap, penalty : " + currentPenalty);
        writeLog("After " + i + " iterations of pair swap, penalty : " + String.format("%,.3f", currentPenalty));

        if(graph.checkColoring()) System.out.println("Coloring is still valid");
        else System.out.println("Coloring is invalid");

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


