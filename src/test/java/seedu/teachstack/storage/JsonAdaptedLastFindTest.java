package seedu.teachstack.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.teachstack.testutil.TypicalPersons.BENSON;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.teachstack.model.person.PersonInGroupPredicate;

public class JsonAdaptedLastFindTest {
    private static final PersonInGroupPredicate VALID_PREDICATE = new PersonInGroupPredicate(BENSON.getGroups());
    private static final List<JsonAdaptedGroup> VALID_GROUPS = BENSON.getGroups().stream()
            .map(JsonAdaptedGroup::new)
            .collect(Collectors.toList());

    @Test
    public void constructor_incorrectPredicate_noGroupsAdded() {
        JsonAdaptedLastFind first = new JsonAdaptedLastFind(p -> false);
        JsonAdaptedLastFind second = new JsonAdaptedLastFind(new ArrayList<>());
        assertEquals(first, second);
    }

    @Test
    public void constructor_correctPredicate_success() {
        JsonAdaptedLastFind first = new JsonAdaptedLastFind(VALID_PREDICATE);
        JsonAdaptedLastFind second = new JsonAdaptedLastFind(VALID_GROUPS);
        assertEquals(first, second);
    }
}
