package seedu.teachstack.storage;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.teachstack.model.Model;
import seedu.teachstack.model.person.Grade;

public class JsonSerializableUserDataTest {

    @Test
    public void equals() {
        JsonSerializableUserData first = new JsonSerializableUserData(); // default values

        JsonSerializableUserData.setGradeThreshold(new Grade("A+"));
        JsonSerializableUserData second = new JsonSerializableUserData();

        JsonSerializableUserData.setLastRequestedFind(p -> false);
        JsonSerializableUserData third = new JsonSerializableUserData();

        // same object -> returns true
        assertTrue(first.equals(first));

        // null -> returns false
        assertFalse(first.equals(null));

        // different threshold -> returns false
        assertFalse(first.equals(second));

        // different predicate -> returns false
        assertFalse(first.equals(third));

        // same threshold and predicate -> returns true
        JsonSerializableUserData.setLastRequestedFind(Model.PREDICATE_SHOW_ALL_PERSONS);
        JsonSerializableUserData fourth = new JsonSerializableUserData();
        assertTrue(second.equals(fourth));
    }
}
