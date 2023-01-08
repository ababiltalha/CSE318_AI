public class VAH1 extends VariableOrderHeuristic {
    // Variable chosen by the order of the smallest domains
    @Override
    public Variable getNextVariable(LatinSquare latinSquare) {
        Variable nextVariable = null;
        for (Variable v :
                latinSquare.unassignedVariables) {
            if (nextVariable == null || v.getDomainSize() < nextVariable.getDomainSize()) {
                nextVariable = v;
            }
        }
        return nextVariable;
    }

    @Override
    public String toString() {
        return "VAH1";
    }

}
