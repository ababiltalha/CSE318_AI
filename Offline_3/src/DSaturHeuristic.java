public class DSaturHeuristic extends ConstructiveHeuristic {
    // returns the node with the highest saturation degree (highest number of unique colors in its neighbors)
    // tiebreak with the highest degree
    @Override
    public Node getNextNode(Graph graph) {
        Node nextNode = null;
        int maxSaturDegree = 0;
        int maxDegree = 0;
        for (Node node : graph.vertices) {
            if (node.neighborColors.size() > maxSaturDegree
                    && graph.assignedColors[node.index] == -1) {
                maxSaturDegree = node.neighborColors.size();
                maxDegree = graph.adjacencyList[node.index].size();
                nextNode = node;
            } else if (node.neighborColors.size() == maxSaturDegree
                    && graph.assignedColors[node.index] == -1) {
                if (graph.adjacencyList[node.index].size() >= maxDegree) {
                    maxDegree = graph.adjacencyList[node.index].size();
                    nextNode = node;
                }
            }
        }
        return nextNode;
    }

    @Override
    public String toString() {
        return "DSatur";
    }
}
