package de.uol.is.tat;

/**
 * Interface for a Constraints class
 */
public interface IConstraints {

    boolean oneTentPerTree(IField[][] f);

    boolean oneTreePerTent(IField[][] f);

    boolean onlyNTentsPerRow(IField[][] f, int i, int j);

    boolean noTentAroundTent(IField[][] f);
}
