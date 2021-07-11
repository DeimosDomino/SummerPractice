package test;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import algorithm.Vertex;

public class testVertex {
    private static Vertex first;
    private static Vertex second;
    private static Vertex third;
    private static Vertex forth;

    @BeforeAll
    public static void init(){
        first = new Vertex(0, 0, 0);
        second = new Vertex(0, 0, 0);
        third = new Vertex(2, 0, 1);
        forth = new Vertex(3, 1, 1);
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
        Assert.assertEquals(secthird, 1, 0.000001);
        Assert.assertEquals(firfor,  Math.sqrt(2), 0.000001);
        Assert.assertEquals(thifor,  1, 0.000001);
    }

    @Test
    public void testCompareCoord() {

        Assert.assertEquals(first.compareTo(third), -1);
        Assert.assertEquals(forth.compareTo(third), 1);
    }
}
