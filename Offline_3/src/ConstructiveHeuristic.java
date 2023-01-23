public abstract class ConstructiveHeuristic {
    // gives next node to color (next course to assign timeslot)
    public abstract Node getNextNode(Graph graph);
    public abstract String toString();
}
