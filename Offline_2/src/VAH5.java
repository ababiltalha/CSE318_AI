import java.util.Random;

public class VAH5 extends VariableOrderHeuristic {
    // Variable chosen by the order of the smallest domains
    static int seed = 77;
    @Override
    public Variable getNextVariable(LatinSquare latinSquare) {
        Random random = new Random(seed++);
        Variable nextVariable = latinSquare.unassignedVariables.get(random.nextInt(latinSquare.unassignedVariables.size()));
        return nextVariable;
    }

    @Override
    public String toString() {
        return "VAH5";
    }
}
