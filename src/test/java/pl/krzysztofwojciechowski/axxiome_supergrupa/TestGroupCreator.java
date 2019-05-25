package pl.krzysztofwojciechowski.axxiome_supergrupa;

import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TestGroupCreator {
    GroupCreator creator;
    Person p1, p2, p3, p4, p5, p6, p7, p8;
    @Before
    public void setUp() {
        creator = new GroupCreator();
        p1 = creator.getOrCreate("A");
        p2 = creator.getOrCreate("B");
        p3 = creator.getOrCreate("C");
        p4 = creator.getOrCreate("D");
        p5 = creator.getOrCreate("E");
        p6 = creator.getOrCreate("F");
        p7 = creator.getOrCreate("G");
        p8 = creator.getOrCreate("H");

    }

    @Test
    public void testOne() {
        creator.makeInitialGroup(p1, p2);
        creator.makeInitialGroup(p2, p3);
        creator.makeInitialGroup(p3, p1);
        assertEquals(2, creator.getMaxGroupSize());
        Group newGroup = creator.createSuperGroup();
        assertEquals(3, creator.getMaxGroupSize());
        assertEquals("A B C", newGroup.toString());
    }

    @Test
    public void testTwo() {
        creator.makeInitialGroup(p1, p2);
        creator.makeInitialGroup(p2, p3);
        creator.makeInitialGroup(p4, p5);
        creator.makeInitialGroup(p5, p1);
        creator.makeInitialGroup(p1, p3);
        creator.makeInitialGroup(p4, p1);
        creator.makeInitialGroup(p3, p6);
        creator.makeInitialGroup(p7, p4);
        assertEquals(2, creator.getMaxGroupSize());
        Group newGroup = creator.createSuperGroup();
        assertEquals(3, creator.getMaxGroupSize());
        assertEquals("A B C", newGroup.toString());
    }

    @Test
    public void testThree() {
        creator.makeInitialGroup(p1, p2);
        creator.makeInitialGroup(p2, p3);
        creator.makeInitialGroup(p4, p5);
        creator.makeInitialGroup(p5, p1);
        creator.makeInitialGroup(p1, p3);
        creator.makeInitialGroup(p4, p1);
        creator.makeInitialGroup(p3, p6);
        creator.makeInitialGroup(p7, p4);
        creator.makeInitialGroup(p4, p8);
        assertEquals(2, creator.getMaxGroupSize());
        Group newGroup = creator.createSuperGroup();
        assertEquals(3, creator.getMaxGroupSize());
        assertEquals("A D E", newGroup.toString());
    }

    @Test
    public void testGetOrCreate() {
        Person p1b = creator.getOrCreate("A");
        assertEquals(p1, p1b);
        assertSame(p1, p1b);
    }
}