package de.uol.is.tat;

import java.util.ArrayList;

public class TentsTreesObject {

    int row;
    int col;
    boolean tentBuild;
    ArrayList<ArrayList<Integer>> remainingOptions;

    public TentsTreesObject(int col, int row, ArrayList<ArrayList<Integer>> remainingOptions, boolean tentBuild) {
        this.row = row;
        this.col = col;
        this.remainingOptions = remainingOptions;
        this.tentBuild = tentBuild;
    }

    public void updateRemainingOptions(ArrayList<ArrayList<Integer>> arrayList) {
        this.remainingOptions = arrayList;
    }
}
