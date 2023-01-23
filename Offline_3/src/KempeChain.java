import java.util.ArrayList;

public class KempeChain {
    public static void interchange(Graph graph) {
        Node node1 = graph.vertices.get((int) ((Math.random() * graph.n)));
        Node node2;
        if(!graph.adjacencyList[node1.index].isEmpty())
            node2 = graph.adjacencyList[node1.index].get(
                    (int) ((Math.random() * graph.adjacencyList[node1.index].size())));
        else return;
        int timeslot1 = graph.getTimeSlot(node1.course.courseID);
        int timeslot2 = graph.getTimeSlot(node2.course.courseID);
        ArrayList<Node> visited = new ArrayList<Node>();
        ArrayList<Node> kempeChain = new ArrayList<Node>();
        getKempeChain(node2, timeslot1, timeslot2, visited, kempeChain, graph);
        for (Node node : kempeChain) {
            if (graph.getTimeSlot(node.course.courseID) == timeslot1) {
                graph.assignedColors[node.index] = timeslot2;
            } else if (graph.getTimeSlot(node.course.courseID) == timeslot2) {
                graph.assignedColors[node.index] = timeslot1;
            }
        }

    }

    public static void getKempeChain(Node node, int timeslot1, int timeslot2,
                                     ArrayList<Node> visited, ArrayList<Node> kempeChain, Graph graph) {
        visited.add(node);
        kempeChain.add(node);
        for (Node neighbor : graph.adjacencyList[node.index]) {
            if (graph.getTimeSlot(neighbor.course.courseID) == timeslot1
                    || graph.getTimeSlot(neighbor.course.courseID) == timeslot2) {
                if (!visited.contains(neighbor)) {
                    getKempeChain(neighbor, timeslot1, timeslot2, visited, kempeChain, graph);
                }
            }
        }
    }
}
