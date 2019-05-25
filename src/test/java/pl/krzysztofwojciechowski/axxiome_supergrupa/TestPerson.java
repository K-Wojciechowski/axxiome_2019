package pl.krzysztofwojciechowski.axxiome_supergrupa;

import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

public class TestPerson {

    @Test
    public void testAddFriendBidirectional() {
        Person p1 = new Person("A");
        Person p2 = new Person("B");
        p1.addFriend(p2);
        assertTrue(p1.knows(p2));
        assertTrue(p2.knows(p1));
    }

    @Test
    public void testKnows() {
        Person p1 = new Person("A");
        Person p2 = new Person("B");
        Person p3 = new Person("C");

        assertFalse(p1.knows(p2));
        assertFalse(p2.knows(p1));
        assertFalse(p2.knows(p3));
        assertFalse(p3.knows(p2));
        assertFalse(p1.knows(p3));
        assertFalse(p3.knows(p1));

        p1.addFriend(p2);
        assertTrue(p1.knows(p2));
        assertTrue(p2.knows(p1));
        assertFalse(p2.knows(p3));
        assertFalse(p3.knows(p2));
        assertFalse(p1.knows(p3));
        assertFalse(p3.knows(p1));

        p2.addFriend(p3);
        assertTrue(p1.knows(p2));
        assertTrue(p2.knows(p1));
        assertTrue(p2.knows(p3));
        assertTrue(p3.knows(p2));
        assertFalse(p1.knows(p3));
        assertFalse(p3.knows(p1));
    }

    @Test
    public void testNameBehavior() {
        Person p1 = new Person("Alice");
        Person p2 = new Person("Alice");
        assertEquals(p1, p2);
        assertEquals(p1.getName(), p1.toString());
        Set<Person> people = new HashSet<Person>();
        people.add(p1);
        people.add(p2);
        assertEquals(1, people.size());
    }

    @Test
    public void testGetFriendsImmutable() {
        Person p1 = new Person("A");
        Person p2 = new Person("B");
        p1.getFriends().add(p2);
        assertFalse(p1.knows(p2));
    }
}