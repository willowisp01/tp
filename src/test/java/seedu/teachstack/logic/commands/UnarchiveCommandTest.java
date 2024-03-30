package seedu.teachstack.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.teachstack.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.teachstack.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.teachstack.logic.commands.CommandTestUtil.showArchivedPersonAtIndex;
import static seedu.teachstack.testutil.TypicalArchivedPersons.getTypicalArchivedBook;
import static seedu.teachstack.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.teachstack.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.teachstack.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.teachstack.testutil.TypicalArchivedStudentIds.ID_FIRST_PERSON;
import static seedu.teachstack.testutil.TypicalArchivedStudentIds.ID_SECOND_PERSON;

import org.junit.jupiter.api.Test;

import seedu.teachstack.logic.Messages;
import seedu.teachstack.model.Model;
import seedu.teachstack.model.ModelManager;
import seedu.teachstack.model.UserPrefs;
import seedu.teachstack.model.person.Person;
import seedu.teachstack.model.person.StudentId;

public class UnarchiveCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), getTypicalArchivedBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Person personToUnarchive = model.getFilteredArchivedList().get(INDEX_FIRST_PERSON.getZeroBased());
        StudentId id = personToUnarchive.getStudentId();
        UnarchiveCommand unarchiveCommand = new UnarchiveCommand(id);

        String expectedMessage = String.format(UnarchiveCommand.MESSAGE_UNARCHIVE_PERSON_SUCCESS,
                Messages.format(personToUnarchive));

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), model.getArchivedBook(), new UserPrefs());
        expectedModel.unarchivePerson(personToUnarchive);

        assertCommandSuccess(unarchiveCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        StudentId outOfBoundId = new StudentId("A9999999Z");
        UnarchiveCommand unarchiveCommand = new UnarchiveCommand(outOfBoundId);

        assertCommandFailure(unarchiveCommand, model, Messages.MESSAGE_INVALID_DISPLAYED_STUDENT_ID);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showArchivedPersonAtIndex(model, INDEX_FIRST_PERSON);

        Person personToUnarchive = model.getFilteredArchivedList().get(INDEX_FIRST_PERSON.getZeroBased());
        StudentId id = personToUnarchive.getStudentId();
        UnarchiveCommand unarchiveCommand = new UnarchiveCommand(id);

        String expectedMessage = String.format(UnarchiveCommand.MESSAGE_UNARCHIVE_PERSON_SUCCESS,
                Messages.format(personToUnarchive));

        Model expectedModel = new ModelManager(model.getAddressBook(), model.getArchivedBook(), new UserPrefs());
        expectedModel.unarchivePerson(personToUnarchive);
        showNoPerson(expectedModel);

        assertCommandSuccess(unarchiveCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_success() {
        showArchivedPersonAtIndex(model, INDEX_FIRST_PERSON);

        Person personToUnarchive = model.getArchivedBook().getArchivedList().get(INDEX_SECOND_PERSON.getZeroBased());
        StudentId outOfBoundId = personToUnarchive.getStudentId();

        // ensures that outOfBoundId is still in bounds of archived book list
        assertTrue(model.getArchivedBook().getArchivedList().stream()
                .filter(person -> person.getStudentId().equals(outOfBoundId)).toArray().length == 1);

        UnarchiveCommand unarchiveCommand = new UnarchiveCommand(outOfBoundId);

        String expectedMessage = String.format(UnarchiveCommand.MESSAGE_UNARCHIVE_PERSON_SUCCESS,
                Messages.format(personToUnarchive));

        Model expectedModel = new ModelManager(model.getAddressBook(), model.getArchivedBook(), new UserPrefs());
        expectedModel.unarchivePerson(personToUnarchive);
        showArchivedPersonAtIndex(expectedModel, INDEX_FIRST_PERSON);

        assertCommandSuccess(unarchiveCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void equals() {
        UnarchiveCommand unarchiveFirstCommand = new UnarchiveCommand(ID_FIRST_PERSON);
        UnarchiveCommand unarchiveSecondCommand = new UnarchiveCommand(ID_SECOND_PERSON);

        // same object -> returns true
        assertTrue(unarchiveFirstCommand.equals(unarchiveFirstCommand));

        // same values -> returns true
        UnarchiveCommand unarchiveFirstCommandCopy = new UnarchiveCommand(ID_FIRST_PERSON);
        assertTrue(unarchiveFirstCommand.equals(unarchiveFirstCommandCopy));

        // different types -> returns false
        assertFalse(unarchiveFirstCommand.equals(1));

        // null -> returns false
        assertFalse(unarchiveFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(unarchiveFirstCommand.equals(unarchiveSecondCommand));
    }

    @Test
    public void toStringMethod() {
        StudentId targetId = new StudentId("A0123456A");
        UnarchiveCommand unarchiveCommand = new UnarchiveCommand(targetId);
        String expected = UnarchiveCommand.class.getCanonicalName() + "{targetId=" + targetId + "}";
        assertEquals(expected, unarchiveCommand.toString());
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoPerson(Model model) {
        model.updateFilteredArchivedList(p -> false);

        assertTrue(model.getFilteredArchivedList().isEmpty());
    }
}
