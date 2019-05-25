/* Axxiome 2019 - Krzysztof Wojciechowski */
package pl.krzysztofwojciechowski.axxiome_supergrupa;

import java.util.Map;
import java.util.Set;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;

public class GroupCreator {
    private Map<String, Person> peopleMap = new HashMap<>();
    private Set<Person> peopleSet = new HashSet<>();
    private Map<Integer, Set<Group>> groups = new HashMap<>();

    {
        groups.put(2, new HashSet<>());
    }

    /** Get or create a person with the given name.
     * @param name Name of the person.
     * @return The person with this name.
     */
    public Person getOrCreate(String name) {
        if (peopleMap.containsKey(name)) {
            return peopleMap.get(name);
        }
        Person newPerson = new Person(name);
        peopleMap.put(name, newPerson);
        peopleSet.add(newPerson);
        return newPerson;
    }

    /** Get the maximum group size currently in the system. */
    public int getMaxGroupSize() {
        return groups.keySet().stream().mapToInt(i -> i).max().orElse(0);
    }

    /** Make an initial group of size 2 that consists of the two arguments to this method. */
    public void makeInitialGroup(Person person1, Person person2) {
        assert person1 != person2: "Persons must be different";
        person1.addFriend(person2); // also adds the opposite relationship
        groups.get(2).add(new Group(person1, person2));
    }

    public Group createSuperGroup() {
        Set<Group> newLevel;
        do {
            newLevel = new HashSet<>();
            int maxGroupSize = getMaxGroupSize();
            int newLevelNum = maxGroupSize + 1;

            for (Group g : groups.get(maxGroupSize)) {
                for (Person p : peopleSet) {
                    Group grownGroup = g.tryGrow(p);
                    if (grownGroup != null) newLevel.add(grownGroup);
                }
            }
            if (!newLevel.isEmpty()) {
                groups.put(newLevelNum, newLevel);
            }
        } while (!newLevel.isEmpty());

        return getSuperGroup();
    }

    public Group getSuperGroup() {
        Set<Group> candidates = groups.get(getMaxGroupSize());
        assert !candidates.isEmpty();
        if (candidates.size() == 1) return candidates.iterator().next();

        // Tie-breaker 1: most friends outside the group
        HashMap<Integer, Set<Group>> outsideFriendsCounts = new HashMap<>();
        int bestSeen = 0;
        for (Group g : candidates) {
            Set<Person> friendsOutsideGroup = g.getFriendsOutsideGroup(peopleSet);
            int count = friendsOutsideGroup.size();
            if (count > bestSeen) bestSeen = count;
            if (!outsideFriendsCounts.containsKey(count)) {
                outsideFriendsCounts.put(count, new HashSet<>());
            }
            outsideFriendsCounts.get(count).add(g);
        }

        Set<Group> bestSet = outsideFriendsCounts.get(bestSeen);
        assert !bestSet.isEmpty();
        if (bestSet.size() == 1) return bestSet.iterator().next();


        // Tie-breaker 2: lexicographic string sorting
        // Sorting implemented in Group.compareTo
        return bestSet.stream().sorted().findFirst().get();
    }


    public static void main(String[] args) {
        GroupCreator creator = new GroupCreator();
        Scanner s = new Scanner(System.in);
        String[] line = s.nextLine().split(" ");
        int relationshipCount = Integer.parseInt(line[1]);
        for (int i = 0; i < relationshipCount; i++) {
            line = s.nextLine().split(" ");
            Person person1 = creator.getOrCreate(line[0]);
            Person person2 = creator.getOrCreate(line[1]);
            creator.makeInitialGroup(person1, person2);
        }
        Group superGroup = creator.createSuperGroup();
        System.out.println(superGroup.size());
        System.out.println(superGroup);
    }
}
