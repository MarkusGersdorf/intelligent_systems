package de.uol.is.tat;

public class TentsTreesObject {

    int row;
    int col;
    int remainingOptions;
    boolean tentBuild;

    public TentsTreesObject(int col, int row, int remainingOptions, boolean tentBuild) {
        this.row = row;
        this.col = col;
        this.remainingOptions = remainingOptions;
        this.tentBuild = tentBuild;
    }

    public void decreaseRemainingOptions() {
        remainingOptions--;
    }
}
