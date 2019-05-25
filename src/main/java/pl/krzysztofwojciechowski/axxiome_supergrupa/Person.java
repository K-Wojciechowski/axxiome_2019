/* Axxiome 2019 - Krzysztof Wojciechowski */
package pl.krzysztofwojciechowski.axxiome_supergrupa;

import java.util.HashSet;
import java.util.Set;

public class Person {
    private String name;
    private Set<Person> friends = new HashSet<>();

    public Person(String name) {
        this.name = name;
    }

    /** Make two people friends. (This method adds both sides of the relationship.) */
    public void addFriend(Person person) {
        friends.add(person);
        person.friends.add(this);
    }

    /** Check if a person knows someone else. */
    public boolean knows(Person person) {
        return friends.contains(person);
    }

    /** Get an unmodifiable copy of the person's friends set. */
    public Set<Person> getFriends() {
        return new HashSet<>(friends);
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Person) {
            return name.equals(((Person)obj).name);
        }
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
