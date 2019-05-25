/* Axxiome 2019 - Krzysztof Wojciechowski */
package pl.krzysztofwojciechowski.axxiome_supergrupa;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Group implements Comparable<Group> {
    private Set<Person> members;

    private Group(Set<Person> members) {
        this.members = members;
    }

    public Group(Person member1, Person member2) {
        this.members = new HashSet<>();
        members.add(member1);
        members.add(member2);
        member1.addFriend(member2);
    }

    public int size() {
        return members.size();
    }

    /** Try to grow the group by adding a new person.
     * @param p Person to add to the group.
     * @return A new larger group or null if the person cannot be added.
     */
    public Group tryGrow(Person p) {
        if (members.contains(p)) {
            // Person is already a member, cannot grow group.
            return null;
        } else if (!members.stream().allMatch(m -> m.knows(p))) {
            // Not everybody knows this person, cannot grow group.
            return null;
        }
        Set<Person> newMembers = new HashSet<>(members);
        newMembers.add(p);
        return new Group(newMembers);
    }

    @Override
    public String toString() {
        return String.join(" ", getSortedNames());
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Group) {
            return members.equals(((Group)obj).members);
        }
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        return members.hashCode();
    }

    /**
     * Get a list of sorted names of group members.
     */
    private List<String> getSortedNames() {
        return members.stream().map(Person::getName).sorted().collect(Collectors.toList());
    }

    /**
     * Compare member names of two groups.
     * @return Negative integer if this &lt; other, 0 if this == other, positive integer if this &gt; other.
     */
    @Override
    public int compareTo(Group other) {
        if (this.members.size() != other.members.size()) {
            throw new IllegalArgumentException("Can only compare groups of equal sizes");
        }
        List<String> sortedThisMemberNames = this.getSortedNames();
        List<String> sortedOtherMemberNames = other.getSortedNames();
        for (int i = 0; i < members.size(); i++) {
            int cmp = sortedThisMemberNames.get(i).compareTo(sortedOtherMemberNames.get(i));
            if (cmp != 0) return cmp;
        }
        return 0;
    }

    /**
     * Get a set of friends of the group members that are not in the group.
     * @param otherPeople Potential friends (all people available)
     * @return Set of friends outside group
     */
    public Set<Person> getFriendsOutsideGroup(Set<Person> otherPeople) {
        // Take the friend sets from all group members and merge them into one.
        Set<Person> friends = new HashSet<>();
        members.stream().map(Person::getFriends).forEach(friends::addAll);
        // Then, remove the group members from the set.
        friends.removeAll(members);  // modifies allFriends in-place
        return friends;
    }
}
