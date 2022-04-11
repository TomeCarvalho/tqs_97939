/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package euromillions;


import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author ico0
 */
public class DipTest {
    private static final int RANGE_STARS = 12;

    private Dip instance;


    public DipTest() {
    }

    @BeforeEach
    public void setUp() {
        instance = new Dip(new int[]{10, 20, 30, 40, 50}, new int[]{1, 2});
    }

    @AfterEach
    public void tearDown() {
        instance = null;
    }


    @Test
    public void testConstructorFromBadArrays() {
        assertThrows(IllegalArgumentException.class, () -> new Dip(new int[]{}, new int[]{}));
    }

    @Test
    public void testFormat() {
        // note: correct the implementation of the format(), not the test...
        String result = instance.format();
        assertEquals("N[ 10 20 30 40 50] S[  1  2]", result, "format as string: formatted string not as expected. ");
    }

    @Test
    public void testStarRange() {
        assertThrows(IllegalArgumentException.class,
                () -> new Dip(new int[]{1, 2, 3, 4, 5}, new int[]{1, RANGE_STARS + 1}));
        assertThrows(IllegalArgumentException.class,
                () -> new Dip(new int[]{1, 2, 3, 4, 5}, new int[]{1, 0}));
    }
}
