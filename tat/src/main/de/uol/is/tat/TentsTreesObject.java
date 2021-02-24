package de.uol.is.tat;

import lombok.Data;

import java.util.ArrayList;

/**
 * This class is used to use an object that stores various information to find a solution faster and easier. It stores
 * the coordinates of the associated tree, the number of options that have been selected, the associated tent and an
 * ArrayList with all the current valid options that are still available.
 */
@Data
public class TentsTreesObject {

    ArrayList<ArrayList<Integer>> remainingOptions;
    ArrayList<Integer> tent = null;
    int row;
    int col;
    int choosedOption = 0;

    public TentsTreesObject(int col, int row) {
        this.row = row;
        this.col = col;
    }

    /**
     * Gets a new list with the current valid available options passed to override them.
     *
     * @param arrayList A list of all currently available options to set a tent.
     */
    public void updateRemainingOptions(ArrayList<ArrayList<Integer>> arrayList) { this.remainingOptions = arrayList; }

    /**
     * This method is used to delete the tent reference.
     */
    public void deleteTent() {
        this.tent = null;
    }

    /**
     * This method checks if there is currently a tent available.
     *
     * @return Returns a boolean value whether a tent exists.
     */
    public boolean isTentBuild() {
        return tent != null;
    }
}
