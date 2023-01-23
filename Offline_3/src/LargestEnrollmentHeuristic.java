public class LargestEnrollmentHeuristic extends ConstructiveHeuristic {
    // returns the node with the highest enrollment of students in that course
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
        return nextNode;
    }

    @Override
    public String toString() {
        return "Largest Enrollment";
    }
}
