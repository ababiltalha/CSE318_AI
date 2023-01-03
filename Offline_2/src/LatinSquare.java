import java.util.ArrayList;

public class LatinSquare {
    public int N;
    private final int[][] inputSquare;
    public Variable[][] latinSquare;
    public ArrayList<Variable> unassignedVariables;

    LatinSquare(int N, int[][] inputSquare) {
        this.N = N;
        this.inputSquare = inputSquare;
        this.latinSquare = new Variable[N][N];
        this.unassignedVariables = new ArrayList<>();

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                this.latinSquare[i][j] = new Variable(N, i, j, inputSquare[i][j]);
                if (inputSquare[i][j] == 0) {
                    this.unassignedVariables.add(this.latinSquare[i][j]);
                } else this.latinSquare[i][j].domain = null;
            }
        }
        initiateDomains();
    }

    // initiate domains of all variables
    private void initiateDomains(){
        for (Variable v :
                this.unassignedVariables) {
            for (int i = 0; i < N; i++) {
                if (this.inputSquare[v.row][i] != 0) {
                    v.domain[this.inputSquare[v.row][i]-1] = false;
                }
                if (this.inputSquare[i][v.col] != 0) {
                    v.domain[this.inputSquare[i][v.col]-1] = false;
                }
            }
        }
    }

    // function for checking if the current assignment is consistent
    public boolean holds(int row, int col, int value) {
        for (int i = 0; i < N; i++) {
            if (this.latinSquare[row][i].value == value) return false;
            if (this.latinSquare[i][col].value == value) return false;
        }
        return true;
    }

    // function sets the value in the asked variable, and constricts the domains of the other variables
    // in the same row and column, return a list of variables of which the domains were constricted
    public ArrayList<Variable> setValue(Variable variable, int value) {
        if (value == 0) {
            System.out.println("0 in value");
            return null;
        }
        if(!variable.setValue(value)) {
            System.out.println("Value not in domain");
            return null;
        }
        this.unassignedVariables.remove(variable);
        ArrayList<Variable> updatedVariables = new ArrayList<>();
        for (int i = 0; i < N; i++) {
            if (this.latinSquare[variable.row][i].value == 0 && i!=variable.col &&
                    this.latinSquare[variable.row][i].domain[value-1]) {
                this.latinSquare[variable.row][i].domain[value-1] = false;
                updatedVariables.add(this.latinSquare[variable.row][i]);
            }
            if (this.latinSquare[i][variable.col].value == 0 && i!=variable.row &&
                    this.latinSquare[i][variable.col].domain[value-1]) {
                this.latinSquare[i][variable.col].domain[value-1] = false;
                updatedVariables.add(this.latinSquare[i][variable.col]);
            }
        }
        return updatedVariables;
    }

    // function unsets the value in the asked variable,
    // and un-constricts the domains of the other variables
    public void unsetValue(Variable variable, ArrayList<Variable> updatedVariables) {
        int value = variable.unsetValue();
        this.unassignedVariables.add(variable);
        for (Variable v :
                updatedVariables) {
            v.domain[value-1] = true;
        }
    }

    @Override
    public String toString() {
        String s = "N = " + this.N + "\n";
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                s += this.latinSquare[i][j].value + "\t";
            }
            s += "\n";
        }
        return s;
    }
}
