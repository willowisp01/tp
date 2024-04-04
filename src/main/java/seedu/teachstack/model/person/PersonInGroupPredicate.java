package seedu.teachstack.model.person;

import java.util.Set;
import java.util.function.Predicate;

import seedu.teachstack.commons.util.ToStringBuilder;
import seedu.teachstack.model.group.Group;

/**
 * Tests that a {@code Person}'s {@code Group}s matches any of the groups given.
 */
public class PersonInGroupPredicate implements Predicate<Person> {
    private final Set<Group> groups;

    public PersonInGroupPredicate(Set<Group> groups) {
        this.groups = groups;
    }

    public Set<Group> getGroups() {
        return groups;
    }

    @Override
    public boolean test(Person person) {
        return groups.stream()
                .allMatch(group -> person.getGroups().contains(group));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof PersonInGroupPredicate)) {
            return false;
        }

        PersonInGroupPredicate otherPersonInGroupPredicate = (PersonInGroupPredicate) other;
        return groups.equals(otherPersonInGroupPredicate.groups);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("groups", groups).toString();
    }
}
