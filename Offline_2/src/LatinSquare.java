import java.util.ArrayList;

public class LatinSquare {
    public int N;
    public int[][] inputSquare;
    public Variable[][] latinSquare;
    public ArrayList<Variable> emptyVariables;

    LatinSquare(int N, int[][] inputSquare) {
        this.N = N;
        this.inputSquare = inputSquare;
        this.latinSquare = new Variable[N][N];
        this.emptyVariables = new ArrayList<>();

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                this.latinSquare[i][j] = new Variable(N, i, j, inputSquare[i][j]);
                if (inputSquare[i][j] == 0) {
                    this.emptyVariables.add(this.latinSquare[i][j]);
                }
            }
        }
        updateDomains();
    }
    ArrayList<Variable> getEmptyVariables() {
        return this.emptyVariables;
    }

    public void updateDomains(){
        for (Variable v :
                this.emptyVariables) {
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

    public boolean holds(int row, int col, int value) {
        for (int i = 0; i < N; i++) {
            if (this.latinSquare[row][i].value == value) return false;
            if (this.latinSquare[i][col].value == value) return false;
        }
        return true;
    }

    public ArrayList<Variable> setValue(Variable variable, int value) {
        variable.setValue(value);
        this.emptyVariables.remove(variable);
        ArrayList<Variable> updatedVariables = new ArrayList<>();
        boolean domainEmptied = false; // denotes if the domain of any variable was emptied
        for (int i = 0; i < N; i++) {
            if (this.latinSquare[variable.row][i].value == 0 && i!=variable.col) {
                this.latinSquare[variable.row][i].domain[value-1] = false;
                updatedVariables.add(this.latinSquare[variable.row][i]);
//                if (this.latinSquare[variable.row][i].getDomainSize() == 0) domainEmptied = true;
            }
            if (this.latinSquare[i][variable.col].value == 0 && i!=variable.row) {
                this.latinSquare[i][variable.col].domain[value-1] = false;
                updatedVariables.add(this.latinSquare[i][variable.col]);
//                if (this.latinSquare[i][variable.col].getDomainSize() == 0) domainEmptied = true;
            }
        }
        return updatedVariables;
    }

    public void unsetValue(Variable variable, ArrayList<Variable> updatedVariables) {
        if (variable.value == 0) return;
        int value = variable.unsetValue();
        this.emptyVariables.add(variable);
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
