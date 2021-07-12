package test;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import algorithm.Vertex;
import algorithm.Line;

public class testLine {
    private static Vertex a;
    private static Vertex b;
    private static Vertex c;
    private static Vertex d;
    private static Line ab;
    private static Line ac;
    private static Line bc;
    private static Line bd;

    @BeforeAll
    public static void init(){
        a = new Vertex(0, 0, 0);
        b = new Vertex(1, 1, 0);
        c = new Vertex(2, 0, 1);
        d = new Vertex(3, 1, 1);
        ab = new Line(23, a, b);
        ac = new Line(231, a, b);
        bc = new Line(34, b, c);
        bd = new Line(90, b, d);
    }

    @Test
    public void testEqualsCoord() {
        boolean firsec = ab.equals(ac);
        boolean secthird = ab.equals(bc);

        Assert.assertTrue(firsec);
        Assert.assertFalse(secthird);
    }

    @Test
    public void testCompareCoord() {

        Assert.assertEquals(ab.compareTo(bc), -1);
        Assert.assertEquals(bd.compareTo(bc), 1);
    }
}
