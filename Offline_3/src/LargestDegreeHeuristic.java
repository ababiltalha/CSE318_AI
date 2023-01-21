public class LargestDegreeHeuristic extends ConstructiveHeuristic {
    @Override
    public Node getNextNode(Graph graph) {
        Node nextNode = null;
        int maxDegree = 0;
//        System.out.println("before for loop");
        for (Node node : graph.vertices) {
            if (graph.adjacencyList[node.index].size() >= maxDegree
                    && graph.assignedColors[node.index] == -1) {
                maxDegree = graph.adjacencyList[node.index].size();
                nextNode = node;
//                System.out.println(maxDegree + " " + nextNode.course.courseID);
            }
        }
        return nextNode;
    }

    @Override
    public String toString() {
        return "Largest Degree";
    }
}
