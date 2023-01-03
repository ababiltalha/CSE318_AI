import java.util.ArrayList;

public class LatinSquareSolver {
    public LatinSquare latinSquare;
    public VariableOrderHeuristic variableOrderHeuristic;
    public long backtrackCount;
    public long nodeCount;

    LatinSquareSolver(LatinSquare latinSquare, VariableOrderHeuristic variableOrderHeuristic) {
        this.latinSquare = latinSquare;
        this.variableOrderHeuristic = variableOrderHeuristic;
        this.backtrackCount = 0;
        this.nodeCount = 0;
    }

    // backtracking without forward checking
    public boolean backtrack() {
        if (this.latinSquare.unassignedVariables.size() == 0) return true;

        Variable nextVariable = this.variableOrderHeuristic.getNextVariable(this.latinSquare);
        if (nextVariable == null) return false;

        ValueOrderHeuristic valueOrderHeuristic = new AscendingOrderedValue(nextVariable);
        while(valueOrderHeuristic.hasNextValue()) {
            int nextValue = valueOrderHeuristic.getNextValue();
            this.nodeCount++;
            if (this.latinSquare.holds(nextVariable.row, nextVariable.col, nextValue)) {
//                System.out.print("Setting value " + nextValue + " to variable " + nextVariable);
                ArrayList<Variable> updatedVariables = this.latinSquare.setValue(nextVariable, nextValue);
                if (backtrack()) return true; // if the next backtrack returns true, we have found a solution
                this.latinSquare.unsetValue(nextVariable, updatedVariables);
            }
        }
        this.backtrackCount++;
        return false;
    }

    public long getBacktrackCount() {
        return backtrackCount;
    }

    public long getNodeCount() {
        return nodeCount;
    }

    // backtracking with forward checking
    public boolean backtrackWithForwardChecking() {
        if (this.latinSquare.unassignedVariables.size() == 0) return true;

        Variable nextVariable = this.variableOrderHeuristic.getNextVariable(this.latinSquare);
        if (nextVariable == null) return false;

        ValueOrderHeuristic valueOrderHeuristic = new AscendingOrderedValue(nextVariable);
        while (valueOrderHeuristic.hasNextValue()) {
            int nextValue = valueOrderHeuristic.getNextValue();
            this.nodeCount++;
            if (this.latinSquare.holds(nextVariable.row, nextVariable.col, nextValue)) {
//                System.out.print("Setting value " + nextValue + " to variable " + nextVariable);
                ArrayList<Variable> updatedVariables = this.latinSquare.setValue(nextVariable, nextValue);
//                System.out.println(updatedVariables);
                for (Variable v:
                     updatedVariables){
                    if (v.getDomainSize() == 0) {
                        this.latinSquare.unsetValue(nextVariable, updatedVariables);
                        this.backtrackCount++;
                        return false;
                    }
                }
                if (backtrackWithForwardChecking()) return true; // if the next backtrack returns true, we have found a solution
                this.latinSquare.unsetValue(nextVariable, updatedVariables);
            }
        }
        this.backtrackCount++;
        return false;
    }
}
