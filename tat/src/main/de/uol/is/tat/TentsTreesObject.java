package de.uol.is.tat;

import java.util.ArrayList;

public class TentsTreesObject {

    int row;
    int col;
    ArrayList<Integer> tent = null;
    ArrayList<ArrayList<Integer>> remainingOptions;

    public TentsTreesObject(int col, int row, ArrayList<ArrayList<Integer>> remainingOptions) {
        this.row = row;
        this.col = col;
        this.remainingOptions = remainingOptions;
    }

    public TentsTreesObject(int col, int row) {
        this.row = row;
        this.col = col;
    }

    public void updateRemainingOptions(ArrayList<ArrayList<Integer>> arrayList) {
        this.remainingOptions = arrayList;
    }

    public boolean isTentBuild() {
        return tent != null;
    }

    public void setTent(ArrayList<Integer> tent) {
        this.tent = tent;
    }

    public void deleteTent() {
        this.tent = null;
    }
}
