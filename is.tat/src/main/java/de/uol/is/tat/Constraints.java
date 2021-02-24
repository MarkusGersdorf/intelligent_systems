package de.uol.is.tat;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Constraints implements IConstraints {
    protected ArrayList<IField[][]> fields;
    public Constraints() {
    }
    @Override
    public boolean one_tent_per_tree(IField[][] f) {
        return f[0][1].get_field_type() == IField.field_type.TENT ||
                f[1][0].get_field_type() == IField.field_type.TENT ||
                f[1][2].get_field_type() == IField.field_type.TENT ||
                f[2][1].get_field_type() == IField.field_type.TENT;
    }

    @Override
    public boolean one_tree_per_tent(IField[][] f) {
        return f[0][1].get_field_type() == IField.field_type.TREE ||
                f[1][0].get_field_type() == IField.field_type.TREE ||
                f[1][2].get_field_type() == IField.field_type.TREE ||
                f[2][1].get_field_type() == IField.field_type.TREE;
    }

    @Override
    public boolean horizontal_vertical_tent_at_tree(IField[][] f) {
        return false;
    }

    @Override
    public boolean adjacent_tent_tree(IField[][] f) {
        return false;
    }

    @Override
    public boolean only_n_tents_per_row(IField[][] f, int field_i, int field_j) {
        int row_limit = f[field_i][0].get_border_limit();
        int column_limit = f[0][field_j].get_border_limit();

        int number_of_tents_row = 0;
        int number_of_tents_column = 0;

        for(int i = 0; i < f[0].length; i++) {
            if(f[field_i][i].get_field_type() == IField.field_type.TENT) {
                number_of_tents_row++;
            }
        }

        for(int i = 0; i < f.length; i++) {
            if(f[i][field_j].get_field_type() == IField.field_type.TENT) {
                number_of_tents_column++;
            }
        }

        return row_limit == number_of_tents_row && column_limit == number_of_tents_column;
    }

    @Override
    public boolean no_tent_around_tent(IField[][] f) {

        for(int i = 0; i < f.length; i++) {
            for(int j = 0; j < f.length; j++) {
                if(i == 1 && j == 1) {
                    continue;
                }
                if(f[i][j].get_field_type() == IField.field_type.TENT) {
                    return false;
                }
            }
        }
        return true;
    }
}
