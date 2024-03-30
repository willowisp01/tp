package seedu.teachstack.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.teachstack.model.util.SampleDataUtil.getGroupSet;

import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.teachstack.model.group.Group;
import seedu.teachstack.testutil.PersonBuilder;

public class PersonInGroupPredicateTest {

    @Test
    public void equals() {
        Set<Group> firstGroupSet = getGroupSet("first");
        Set<Group> secondGroupSet = getGroupSet("first", "second");

        PersonInGroupPredicate firstPredicate = new PersonInGroupPredicate(firstGroupSet);
        PersonInGroupPredicate secondPredicate = new PersonInGroupPredicate(secondGroupSet);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        PersonInGroupPredicate firstPredicateCopy = new PersonInGroupPredicate(firstGroupSet);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_personInGroup_returnsTrue() {
        // One group
        PersonInGroupPredicate predicate = new PersonInGroupPredicate(getGroupSet("Group 1"));
        assertTrue(predicate.test(new PersonBuilder().withGroups("Group 1").build()));

        // Multiple groups
        predicate = new PersonInGroupPredicate(getGroupSet("Group 1", "Group 2"));
        assertTrue(predicate.test(new PersonBuilder().withGroups("Group 1", "Group 2").build()));

        // Only one matching group
        predicate = new PersonInGroupPredicate(getGroupSet("Group 1"));
        assertTrue(predicate.test(new PersonBuilder().withGroups("Group 1", "Group 2").build()));
    }

    @Test
    public void test_personNotInGroup_returnsFalse() {
        // No group
        PersonInGroupPredicate predicate = new PersonInGroupPredicate(getGroupSet("Group 1"));
        assertFalse(predicate.test(new PersonBuilder().build()));

        // Non-matching group
        predicate = new PersonInGroupPredicate(getGroupSet("Group 1"));
        assertFalse(predicate.test(new PersonBuilder().withGroups("Group 2").build()));
    }

    @Test
    public void toStringMethod() {
        Set<Group> groups = getGroupSet("Group 1", "Group 2");
        PersonInGroupPredicate predicate = new PersonInGroupPredicate(groups);

        String expected = PersonInGroupPredicate.class.getCanonicalName() + "{groups=" + groups + "}";
        assertEquals(expected, predicate.toString());
    }
}
