public class LargestEnrollmentHeuristic extends ConstructiveHeuristic {
    @Override
    public Node getNextNode(Graph graph) {
        Node nextNode = null;
        int maxEnrollment = 0;
        for (Node node : graph.vertices) {
            if (node.course.numberOfStudents > maxEnrollment
                    && graph.assignedColors[node.index] == -1) {
                maxEnrollment = node.course.numberOfStudents;
                nextNode = node;
            }
        }
//        System.out.println(maxEnrollment);
        return nextNode;
    }

    @Override
    public String toString() {
        return "Largest Enrollment";
    }
}
