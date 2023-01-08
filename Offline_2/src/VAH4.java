public class VAH4 extends VariableOrderHeuristic {
    // Variable chosen by the minimum of the ratio of domain size and max-forward-degree
    @Override
    public Variable getNextVariable(LatinSquare latinSquare) {
        Variable nextVariable = latinSquare.unassignedVariables.get(0);
        int[] rowUnassignedVariables = new int[latinSquare.N];
        int[] colUnassignedVariables = new int[latinSquare.N];
        for (Variable v :
                latinSquare.unassignedVariables) {
            rowUnassignedVariables[v.row]++;
            colUnassignedVariables[v.col]++;
        }
        int nextVariableDegree = 1;
        for (Variable v :
                latinSquare.unassignedVariables) {
            int variableDegree = rowUnassignedVariables[v.row] + colUnassignedVariables[v.col] - 2;
            double variableRatio = (double) v.getDomainSize() / variableDegree;
            double nextVariableRatio = (double) nextVariable.getDomainSize() / nextVariableDegree;
            if (nextVariable == null || variableRatio < nextVariableRatio) {
                nextVariable = v;
                nextVariableDegree = variableDegree;
            }
        }
        return nextVariable;
    }

    @Override
    public String toString() {
        return "VAH4";
    }
}
