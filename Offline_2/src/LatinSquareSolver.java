import java.util.ArrayList;

public class LatinSquareSolver {
    public LatinSquare latinSquare;
    public VariableOrderHeuristic variableOrderHeuristic;
    public ValueOrderHeuristic valueOrderHeuristic;
    public long backtrackCount;
    public long nodeCount;

    LatinSquareSolver(LatinSquare latinSquare, VariableOrderHeuristic variableOrderHeuristic,
                      ValueOrderHeuristic valueOrderHeuristic) {
        this.latinSquare = latinSquare;
        this.variableOrderHeuristic = variableOrderHeuristic;
        this.valueOrderHeuristic = valueOrderHeuristic;
        this.backtrackCount = 0;
        this.nodeCount = 0;
    }

    public boolean backtrack() {
        if (this.latinSquare.emptyVariables.size() == 0) return true;

        Variable nextVariable = this.variableOrderHeuristic.getNextVariable(this.latinSquare);
        if (nextVariable == null) return false;

        while(valueOrderHeuristic.hasNextValue(nextVariable)) {
            int nextValue = valueOrderHeuristic.getNextValue(nextVariable);
            if (this.latinSquare.holds(nextVariable.row, nextVariable.col, nextValue)) {
                System.out.print("Setting value " + nextValue + " to variable " + nextVariable);
                ArrayList<Variable> updatedVariables = this.latinSquare.setValue(nextVariable, nextValue);
                this.nodeCount++;
                if (backtrack()) return true; // if the next backtrack returns true, we have found a solution
                this.latinSquare.unsetValue(nextVariable, updatedVariables);
                this.backtrackCount++;
            }
        }
        return false;
    }


}
