import java.util.ArrayList;
public abstract class PenaltyStrategy {
    public abstract double getAveragePenalty(Graph graph, ArrayList<Student> students);
    public abstract String toString();
}
