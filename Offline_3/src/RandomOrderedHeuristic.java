import java.util.Random;

public class RandomOrderedHeuristic extends ConstructiveHeuristic {
    private static int SEED=12345;
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
