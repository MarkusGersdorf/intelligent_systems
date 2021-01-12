package de.uol.is.tat;

public interface IConstraints {

    boolean one_tent_per_tree(IField[][] f);

    boolean one_tree_per_tent(IField[][] f);

    boolean horizontal_vertical_tent_at_tree(IField[][] f);

    boolean adjacent_tent_tree(IField[][] f);

    boolean only_n_tents_per_row(IField[][] f,int i, int j);

    boolean no_tent_around_tent(IField[][] f);


}
