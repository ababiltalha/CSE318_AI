public class LargestDegreeHeuristic extends ConstructiveHeuristic {
    // returns the node with the highest degree
    @Override
    public Node getNextNode(Graph graph) {
        Node nextNode = null;
        int maxDegree = 0;
        for (Node node : graph.vertices) {
            if (graph.adjacencyList[node.index].size() >= maxDegree
                    && graph.assignedColors[node.index] == -1) {
                maxDegree = graph.adjacencyList[node.index].size();
                nextNode = node;
            }
        }
        return nextNode;
    }

    @Override
    public String toString() {
        return "Largest Degree";
    }
}
