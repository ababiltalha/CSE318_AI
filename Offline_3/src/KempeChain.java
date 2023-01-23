import java.util.ArrayList;

public class KempeChain {
    public static int timeslot1;
    public static int timeslot2;

    public static void interchange(Graph graph, Node node1, Node node2) {

        timeslot1 = graph.getTimeSlot(node1.course.courseID);
        timeslot2 = graph.getTimeSlot(node2.course.courseID);
        if (timeslot1 == timeslot2) return;
        ArrayList<Node> visited = new ArrayList<Node>();
        ArrayList<Node> kempeChain = new ArrayList<Node>();
        visited.add(node1);
        kempeChain.add(node1);
        getKempeChain(node1, timeslot2, visited, kempeChain, graph);
        for (Node node : kempeChain) {
            if (graph.getTimeSlot(node.course.courseID) == timeslot1) {
                graph.assignedColors[node.index] = timeslot2;
            } else if (graph.getTimeSlot(node.course.courseID) == timeslot2) {
                graph.assignedColors[node.index] = timeslot1;
            }
        }

    }

    public static void getKempeChain(Node node, int timeslot,
                                     ArrayList<Node> visited, ArrayList<Node> kempeChain, Graph graph) {
//        visited.add(node);
//        kempeChain.add(node);
        for (Node neighbor : graph.adjacencyList[node.index]) {
            if (graph.getTimeSlot(neighbor.course.courseID) == timeslot) {
                if (!visited.contains(neighbor)) {
                    visited.add(neighbor);
                    kempeChain.add(neighbor);
                    getKempeChain(neighbor, timeslot1+timeslot2-timeslot, visited, kempeChain, graph);
                }
            }
        }
    }
}
