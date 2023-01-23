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
        if(graph.checkColoring()) System.out.println("Coloring is valid");
        else System.out.println("Coloring is invalid");
        System.out.println(constructiveHeuristic + " heuristic applied, timeslots required : " + numberOfTimeslots);
        writeLog(constructiveHeuristic + " heuristic applied, timeslots required : " + numberOfTimeslots);

        initialPenalty = penaltyStrategy.getAveragePenalty(graph, students);
        System.out.println(penaltyStrategy + ", initial penalty : " + initialPenalty);
        writeLog(penaltyStrategy + ", initial penalty : " + initialPenalty);

        double currentPenalty = initialPenalty;
        double penaltyAfterKempeChain;
        int maxIterations = 1000;
        int i = 0;
        while(i < maxIterations){
            Node node1 = graph.vertices.get((int) ((Math.random() * graph.n)));
            Node node2;
            if(!graph.adjacencyList[node1.index].isEmpty())
                node2 = graph.adjacencyList[node1.index].get(
                        (int) ((Math.random() * graph.adjacencyList[node1.index].size())));
            else continue;
            KempeChain.interchange(graph, node1, node2);
//            System.out.println(graph.checkColoring());
            penaltyAfterKempeChain = penaltyStrategy.getAveragePenalty(graph, students);
            if (penaltyAfterKempeChain < currentPenalty) currentPenalty = penaltyAfterKempeChain;
            else {
                KempeChain.interchange(graph, node1, node2);
            }
            i++;
        }
        System.out.println("After " + i + " iterations of Kempe chain interchange, penalty : " + currentPenalty);
        writeLog("After " + i + " iterations of Kempe chain interchange, penalty : " + currentPenalty);
        if(graph.checkColoring()) System.out.println("Coloring is valid");
        else System.out.println("Coloring is invalid");

        penaltyAfterKempeChain = currentPenalty;
        double penaltyAfterPairSwap;
        i = 0;
//        Collections.shuffle(graph.vertices);
        while(i < maxIterations){
//            for (int index1= 0; index1 < graph.vertices.size() - 1; index1++) {
//                for (int index2 = index1 + 1; index2 < graph.vertices.size(); index2++) {
//                    Node node1 = graph.vertices.get(index1);
            Node node1 = graph.vertices.get((int) ((Math.random() * graph.n)));
//                    Node node2 = graph.vertices.get(index2);
            Node node2 = graph.vertices.get((int) ((Math.random() * graph.n)));
            if (node2.course.courseID.equals(node1.course.courseID)) continue;
            int timeslot1 = graph.getTimeSlot(node1.course.courseID);
            int timeslot2 = graph.getTimeSlot(node2.course.courseID);
            if (timeslot1 == timeslot2) continue;
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

//                    if(kempeChain1.size() == 1 && kempeChain2.size() == 1) {
//                        for (int j = 0; j < kempeChain1.size() ; j++) System.out.print("1 " + kempeChain1.get(j).course.courseID + " " + graph.getTimeSlot(kempeChain1.get(j).course.courseID) + " vs ");
//                        for (int j = 0; j < kempeChain2.size() ; j++) System.out.print("2 " + kempeChain2.get(j).course.courseID + " " + graph.getTimeSlot(kempeChain2.get(j).course.courseID));
//                        System.out.println();
            i++;
            graph.assignedColors[node1.index] = timeslot2;
            graph.assignedColors[node2.index] = timeslot1;
            penaltyAfterPairSwap = penaltyStrategy.getAveragePenalty(graph, students);
//            System.out.println(graph.checkColoring());
            if (penaltyAfterPairSwap < currentPenalty) currentPenalty = penaltyAfterPairSwap;
            else {
                graph.assignedColors[node1.index] = timeslot1;
                graph.assignedColors[node2.index] = timeslot2;
            }
//                    }
//                }
//            }
        }
        System.out.println("After " + i + " iterations of pair swap interchange, penalty : " + currentPenalty);
        writeLog("After " + i + " iterations of pair swap interchange, penalty : " + currentPenalty);


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


