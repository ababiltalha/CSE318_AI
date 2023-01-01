import java.util.ArrayList;

public class Variable {
    public boolean[] domain; // domain[i] = true if i+1 is in the domain
    public int row;
    public int col;
    public int N;
    public int value;

    Variable(int N, int row, int col, int value) {
        this.N = N;
        this.row = row;
        this.col = col;
        if(value != 0) {
            this.domain = null;
            this.value = value;
        }
        else {
            this.domain = new boolean[N];
            for(int i = 0; i < N; i++) {
                this.domain[i] = true;
            }
            this.value = 0;
        }
    }

    public void setDomain(boolean[] domain) {
        this.domain = domain;
    }

    public boolean setValue(int value) {
        if (this.domain[value-1]) {
            this.value = value;
            this.domain[value-1] = false;
            return true;
        }
        else return false;
    }

    public int unsetValue() {
        int value = this.value;
        this.value = 0;
        this.domain[value-1] = true;
        return value;
    }

    public int getDomainSize() {
        int count = 0;
        for (boolean b :
                this.domain) {
            if (b) count++;
        }
        return count;
    }


    @Override
    public String toString() {
        String s = "";
        s += "( " + this.row + ", " + this.col + " ) : " + this.value + "\n";
        return s;
    }

    public void printDomain() {
        System.out.print("[ ");
        for (int i = 0; i < this.N; i++) {
            if (this.domain != null && this.domain[i]) {
                System.out.print(i+1 + " ");
            }
        }
        System.out.println("]");
    }

}
