package test;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import algorithm.Coordinates;

public class testCoordinates{

    private static Coordinates first;
    private static Coordinates second;
    private static Coordinates third;
    private static Coordinates forth;

    @BeforeAll
    public static void init(){
        first = new Coordinates(1, 2);
        second = new Coordinates(1, 2);
        third = new Coordinates(3, 2);
        forth = new Coordinates (1, 1);
    }

    @Test
    public void testEqualsCoord() {
        boolean firsec = first.equals(second);
        boolean secthird = second.equals(third);

        Assert.assertTrue(firsec);
        Assert.assertFalse(secthird);
    }

    @Test
    public void testDistanceCoord() {
        double firsec = first.distance(second);
        double secthird = second.distance(third);
        double firfor = first.distance(forth);
        double thifor = third.distance(forth);

        Assert.assertEquals(firsec, 0, 0.000001);
        Assert.assertEquals(secthird, 2, 0.000001);
        Assert.assertEquals(firfor,  1, 0.000001);
        Assert.assertEquals(thifor,  Math.sqrt(5), 0.000001);
    }

    @Test


    public void testMoveCoord() {
        forth.moveCord(2, 1);

        Assert.assertTrue(forth.equals(third));
    }
}
