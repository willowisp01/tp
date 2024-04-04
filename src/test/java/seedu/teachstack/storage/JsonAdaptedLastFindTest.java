package seedu.teachstack.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.teachstack.testutil.TypicalArchivedPersons.getTypicalArchivedBook;
import static seedu.teachstack.testutil.TypicalPersons.BENSON;
import static seedu.teachstack.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.teachstack.model.Model;
import seedu.teachstack.model.ModelManager;
import seedu.teachstack.model.UserPrefs;
import seedu.teachstack.model.person.Person;
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

    @Test
    public void equals() {
        JsonAdaptedLastFind first = new JsonAdaptedLastFind(VALID_PREDICATE);
        JsonAdaptedLastFind second = new JsonAdaptedLastFind(VALID_GROUPS);
        JsonAdaptedLastFind third = new JsonAdaptedLastFind(p -> false);

        // same groups -> returns true
        assertTrue(first.equals(second));

        // same object -> returns true
        assertTrue(first.equals(first));

        // null -> returns false
        assertFalse(first.equals(null));

        // different groups -> returns false
        assertFalse(first.equals(third));
    }

    @Test
    public void performAction_noGroups_allListed() {
        Model model = new ModelManager(getTypicalAddressBook(), getTypicalArchivedBook(), new UserPrefs());
        List<Person> all = model.getFilteredPersonList();
        JsonAdaptedLastFind noFilter = new JsonAdaptedLastFind(p -> false);
        noFilter.performAction();
        Model expectedModel = new ModelManager(getTypicalAddressBook(), getTypicalArchivedBook(), new UserPrefs());
        assertEquals(all, expectedModel.getFilteredPersonList());
    }

    @Test
    public void performAction_invalidGroup_allListed() {
        Model model = new ModelManager(getTypicalAddressBook(), getTypicalArchivedBook(), new UserPrefs());
        model.updateFilteredPersonList(Model.PREDICATE_SHOW_ALL_PERSONS);
        List<Person> all = model.getFilteredPersonList();
        JsonAdaptedLastFind invalidFilter = new JsonAdaptedLastFind(Arrays.asList(new JsonAdaptedGroup("@")));
        invalidFilter.performAction();
        Model expectedModel = new ModelManager(getTypicalAddressBook(), getTypicalArchivedBook(), new UserPrefs());
        assertEquals(all, expectedModel.getFilteredPersonList());
    }

    @Test
    public void performAction_hasGroups_filterSuccess() {
        JsonAdaptedLastFind filter = new JsonAdaptedLastFind(VALID_PREDICATE);
        filter.performAction();
        Model model = new ModelManager(getTypicalAddressBook(), getTypicalArchivedBook(), new UserPrefs());
        assertEquals(Arrays.asList(BENSON), model.getFilteredPersonList());
    }
}
