import java.util.Comparator;

public class ManhattanCostComparator implements Comparator <SearchNode> {
    @Override
    public int compare(SearchNode o1, SearchNode o2) {
        return Integer.compare(o1.cost + o1.manhattanHeuristic(), o2.cost + o2.manhattanHeuristic());
    }
}
