package de.uol.is.tat;

public interface IConstraints {

    boolean oneTentPerTree(IField[][] f);

    boolean oneTreePerTent(IField[][] f);

    boolean horizontalVerticalTentAtTree(IField[][] f);

    boolean adjacentTentTree(IField[][] f);

    boolean onlyNTentsPerRow(IField[][] f, int i, int j);

    boolean noTentAroundTent(IField[][] f);


}
