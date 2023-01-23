import java.util.ArrayList;

public class KempeChain {
    // the timeslots to be swapped
    public static int timeslot1;
    public static int timeslot2;

    // swaps the timeslots of the two nodes and all nodes in the kempe chain
    public static void interchange(Graph graph, Node node1, Node node2) {
        timeslot1 = graph.getTimeSlot(node1.course.courseID);
        timeslot2 = graph.getTimeSlot(node2.course.courseID);
        if (timeslot1 == timeslot2) return;
        ArrayList<Node> visited = new ArrayList<Node>(); // visited list for DFS
        ArrayList<Node> kempeChain = new ArrayList<Node>();
        visited.add(node1);
        kempeChain.add(node1);
        getKempeChain(node1, timeslot2, visited, kempeChain, graph);
        // swap the timeslots
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
        // DFS to get the kempe chain
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
