package pl.krzysztofwojciechowski.axxiome_supergrupa;

import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

public class TestGroup {
    @Test
    public void testCreation() {
        Person p1 = new Person("A");
        Person p2 = new Person("B");
        p1.addFriend(p2);
        Group g = new Group(p1, p2);
        assertEquals(2, g.size());
    }

    @Test
    public void testTryGrow() {
        Person p1 = new Person("A");
        Person p2 = new Person("B");
        Person p3 = new Person("C");
        Person p4 = new Person("D");
        p1.addFriend(p2);
        p1.addFriend(p3);
        p1.addFriend(p4);
        p2.addFriend(p3);

        Group g1 = new Group(p1, p2);
        assertEquals(2, g1.size());

        Group g2 = g1.tryGrow(p3);
        assertEquals(2, g1.size());
        assertEquals(3, g2.size());

        assertNull(g1.tryGrow(p4));
        assertNull(g1.tryGrow(p1));
    }

    @Test
    public void compareTo() {
        Person p1 = new Person("Alice");
        Person p2 = new Person("Rob");
        Person p3 = new Person("Ron");
        Group g1 = new Group(p1, p2);
        Group g2 = new Group(p1, p3);
        assertTrue(g1.compareTo(g2) < 0);

    }

    @Test
    public void getFriendsOutsideGroup() {
        Person p1 = new Person("A");
        Person p2 = new Person("B");
        Person p3 = new Person("C");
        Person p4 = new Person("D");
        p1.addFriend(p2);
        p1.addFriend(p3);
        p3.addFriend(p4);

        Set<Person> people = new HashSet<>();
        people.add(p1);
        people.add(p2);
        people.add(p3);
        people.add(p4);

        Group g = new Group(p1, p2);
        Set<Person> friends = g.getFriendsOutsideGroup(people);
        assertEquals(1, friends.size());
        assertEquals(p3, friends.iterator().next());
    }

    @Test
    public void testSetBehavior() {
        Person p1 = new Person("A");
        Person p2 = new Person("B");
        Group g1 = new Group(p1, p2);
        Group g2 = new Group(p1, p2);
        assertEquals(g1, g2);
        assertEquals(g1.hashCode(), g2.hashCode());
    }

    @Test
    public void testToString() {
        Person p1 = new Person("Alice");
        Person p2 = new Person("Bob");
        assertEquals("Alice Bob", new Group(p1, p2).toString());
    }
}