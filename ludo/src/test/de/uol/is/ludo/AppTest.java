package de.uol.is.ludo;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * Unit test for simple App.
 */
public class AppTest {
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue() {
        assertTrue(true);
    }

    @Test
    public void sortCollectionRandom() {

        Pawn pawn1 = new Pawn(1, 1, new Field(1));
        Pawn pawn2 = new Pawn(1, 1, new Field(2));
        Pawn pawn3 = new Pawn(1, 1, new Field(3));

        ArrayList<Pawn> pawns = new ArrayList<>();
        pawns.add(pawn1);
        pawns.add(pawn2);
        pawns.add(pawn3);

        Collections.shuffle(pawns, new Random());

        assertTrue(pawns.get(0) == pawn1 || pawns.get(1) == pawn2 || pawns.get(2) == pawn3);
    }

    @Test
    public void sortCollectionByField() {
        Pawn pawn1 = new Pawn(1, 1, new Field(1));
        Pawn pawn2 = new Pawn(1, 1, new Field(2));
        Pawn pawn3 = new Pawn(1, 1, new Field(3));

        ArrayList<Pawn> pawns = new ArrayList<>();
        pawns.add(pawn2);
        pawns.add(pawn3);
        pawns.add(pawn1);

        pawns.sort(Comparator.comparing(Pawn::getFieldId));

        assertTrue(pawns.get(0) == pawn1 && pawns.get(1) == pawn2 && pawns.get(2) == pawn3);
    }

    @Test
    public void randomFunction() {
        for (int i = 0; i < 1000; i++) {
            int random = (int) (Math.random() * (2 + 1) + 0);
            if (random < 0 || random > 2) {
                fail();
            }
        }
        assertTrue(true);
    }

}
