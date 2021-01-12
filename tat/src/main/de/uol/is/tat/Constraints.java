package de.uol.is.tat;

import java.util.ArrayList;

public class Constraints implements IConstraints {
    protected ArrayList<IField[][]> fields;
    public Constraints(ArrayList<IField[][]> f) {
        this.fields = f;
    }

    /**
     *
     * @param f small field (9x9)
     * @return true if one tent stands by a tree.
     */
    @Override
    public boolean one_tent_per_tree(IField f) {
        return false;
    }

    /**
     *
     * @param f small field (9x9)
     * @return true if one tree is attached to a tent.
     */
    @Override
    public boolean one_tree_per_tent(IField f) {
        return false;
    }

    /**
     *
     * @param f gives the hole row
     * @param n number of tents
     * @return true if the number of tents in the row matches with n.
     */
    @Override
    public boolean only_n_tents_per_row(IField f, int n) {
        return false;
    }

    /**
     *
     * @param f small field (9x9)
     * @return true if there is no other tent in the 9x9 Matrix.
     */
    @Override
    public boolean no_tent_around_tent(IField f) {
        return false;
    }

    /**
     *
     * @deprecated
     */
    @Override
    public boolean horizontal_vertical_tent_at_tree(IField f) {
        return false;
    }

    /**
     *
     * @deprecated
     */
    @Override
    public boolean adjacent_tent_tree(IField f) {
        return false;
    }
}
