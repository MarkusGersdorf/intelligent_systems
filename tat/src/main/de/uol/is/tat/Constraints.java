package de.uol.is.tat;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Constraints implements IConstraints {
    protected ArrayList<IField[][]> fields;
    public Constraints(ArrayList<IField[][]> f) {
        this.fields = f;
    }
    @Override
    public boolean one_tent_per_tree(IField f) {
        return false;
    }

    @Override
    public boolean one_tree_per_tent(IField f) {
        return false;
    }

    @Override
    public boolean horizontal_vertical_tent_at_tree(IField f) {
        return false;
    }

    @Override
    public boolean adjacent_tent_tree(IField f) {
        return false;
    }

    @Override
    public boolean only_n_tents_per_row(IField f, int n) {
        return false;
    }

    @Override
    public boolean no_tent_around_tent(IField f) {
        return false;
    }
}
