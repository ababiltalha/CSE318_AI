public class AscendingOrderedValue extends ValueOrderHeuristic {

    public boolean[] initialDomain;
    public AscendingOrderedValue(Variable variable) {
        this.initialDomain = variable.domain.clone();
    }
    @Override
    public int getNextValue() {
        int nextValue = 0;
        for (int i = 0; i < initialDomain.length; i++) {
            if (this.initialDomain[i]) {
                nextValue = i + 1;
                break;
            }
        }
        if(nextValue != 0) this.initialDomain[nextValue-1] = false;
        return nextValue;
    }

    @Override
    public boolean hasNextValue() {
        for (boolean b :
                this.initialDomain) {
            if (b) return true;
        }
        return false;
    }

}
