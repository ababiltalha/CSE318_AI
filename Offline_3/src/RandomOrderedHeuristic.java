import java.util.Random;

public class RandomOrderedHeuristic extends ConstructiveHeuristic {
    // returns a random node
    @Override
    public Node getNextNode(Graph graph) {
        Node nextNode = null;
        while (true) {
            Node node = graph.vertices.get((int) ((Math.random() * (graph.n))));
            if (graph.assignedColors[node.index] == -1) {
                nextNode = node;
                break;
            }
        }
        return nextNode;
    }

    @Override
    public String toString() {
        return "Random Ordered";
    }
}
