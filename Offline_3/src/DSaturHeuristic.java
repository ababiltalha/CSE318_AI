public class DSaturHeuristic extends ConstructiveHeuristic {
    @Override
    public Node getNextNode(Graph graph) {
        Node nextNode = null;
        int maxSaturDegree = 0;
        int maxDegree = 0;
        for (Node node : graph.vertices) {
            if (node.saturationDegree.size() > maxSaturDegree
                    && graph.assignedColors[node.index] == -1) {
                maxSaturDegree = node.saturationDegree.size();
                maxDegree = graph.adjacencyList[node.index].size();
                nextNode = node;
            } else if (node.saturationDegree.size() == maxSaturDegree
                    && graph.assignedColors[node.index] == -1) {
                if (graph.adjacencyList[node.index].size() >= maxDegree) {
                    maxDegree = graph.adjacencyList[node.index].size();
                    nextNode = node;
                }
            }
        }
//        System.out.println(nextNode.course.courseID + " " + nextNode.saturationDegree.size());
        return nextNode;
    }

    @Override
    public String toString() {
        return "DSatur";
    }
}
