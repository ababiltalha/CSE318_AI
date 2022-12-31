public class VAH1 extends VariableOrderHeuristic {
    // Variable chosen by the order of the smallest domains
    @Override
    public Variable getNextVariable(LatinSquare latinSquare) {
        Variable nextVariable = null;
        for (Variable v :
                latinSquare.emptyVariables) {
            if (nextVariable == null || v.getDomainSize() < nextVariable.getDomainSize()) {
                nextVariable = v;
            }
        }
//        latinSquare.emptyVariables.remove(nextVariable);
//        System.out.println(latinSquare.emptyVariables.size());
        return nextVariable;
    }
}
