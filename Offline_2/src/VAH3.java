public class VAH3 extends VariableOrderHeuristic {
    // Variable chosen by the order of the smallest domains, tie broken by max-forward-degree
    @Override
    public Variable getNextVariable(LatinSquare latinSquare) {
        Variable nextVariable = null;
        for (Variable v :
                latinSquare.unassignedVariables) {
            if (nextVariable == null || v.getDomainSize() < nextVariable.getDomainSize()) {
                nextVariable = v;
            } else if (v.getDomainSize() == nextVariable.getDomainSize()) {
                int[] rowUnassignedVariables = new int[latinSquare.N];
                int[] colUnassignedVariables = new int[latinSquare.N];
                for (Variable v2 :
                        latinSquare.unassignedVariables) {
                    rowUnassignedVariables[v2.row]++;
                    colUnassignedVariables[v2.col]++;
                }
                int variableDegree = rowUnassignedVariables[v.row] + colUnassignedVariables[v.col] - 2;
                int nextVariableDegree = rowUnassignedVariables[nextVariable.row] + colUnassignedVariables[nextVariable.col] - 2;
                if (variableDegree > nextVariableDegree) {
                    nextVariable = v;
                }
            }
        }
//        latinSquare.emptyVariables.remove(nextVariable);
//        System.out.println(latinSquare.emptyVariables.size());
        return nextVariable;
    }
}
