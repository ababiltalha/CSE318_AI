public class VAH2 extends VariableOrderHeuristic {
    // Variable chosen by the order of the maximum degree of unassigned variables, max-forward-degree
    @Override
    public Variable getNextVariable(LatinSquare latinSquare) {
        Variable nextVariable = null;
        int maxDegree = 0;
        int[] rowUnassignedVariables = new int[latinSquare.N];
        int[] colUnassignedVariables = new int[latinSquare.N];
        for (Variable v :
                latinSquare.unassignedVariables) {
            rowUnassignedVariables[v.row]++;
            colUnassignedVariables[v.col]++;
        }
        for (Variable v :
                latinSquare.unassignedVariables) {
            int variableDegree = rowUnassignedVariables[v.row] + colUnassignedVariables[v.col] - 2;
            if (nextVariable == null || variableDegree > maxDegree) {
                nextVariable = v;
                maxDegree = variableDegree;
            }
        }
        return nextVariable;
    }
}
