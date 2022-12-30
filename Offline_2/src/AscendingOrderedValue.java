public class AscendingOrderedValue extends ValueOrderHeuristic {
    @Override
    public int getNextValue(Variable variable) {
        int nextValue = 0;
        for (int i = 0; i < variable.N; i++) {
            if (variable.domain[i]) {
                nextValue = i + 1;
                break;
            }
        }
        return nextValue;
    }

    @Override
    public boolean hasNextValue(Variable variable) {
        for (boolean b :
                variable.domain) {
            if (b) return true;
        }
        return false;
    }

}
