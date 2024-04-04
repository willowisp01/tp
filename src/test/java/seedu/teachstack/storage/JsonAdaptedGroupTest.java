package seedu.teachstack.storage;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class JsonAdaptedGroupTest {
    @Test
    public void equals() {
        JsonAdaptedGroup first = new JsonAdaptedGroup("first");
        JsonAdaptedGroup second = new JsonAdaptedGroup("second");

        // same name -> returns true
        assertTrue(first.equals(new JsonAdaptedGroup("first")));

        // same object -> returns true
        assertTrue(first.equals(first));

        // null -> returns false
        assertFalse(first.equals(null));

        // different names -> returns false
        assertFalse(first.equals(second));
    }
}
