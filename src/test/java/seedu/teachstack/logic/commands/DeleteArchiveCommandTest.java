package seedu.teachstack.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.teachstack.logic.commands.CommandTestUtil.assertArchivedBookCommandFailure;
import static seedu.teachstack.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.teachstack.logic.commands.CommandTestUtil.showArchivedPersonAtIndex;
import static seedu.teachstack.testutil.TypicalArchivedPersons.getTypicalArchivedBook;
import static seedu.teachstack.testutil.TypicalArchivedStudentIds.ID_FIRST_PERSON;
import static seedu.teachstack.testutil.TypicalArchivedStudentIds.ID_SECOND_PERSON;
import static seedu.teachstack.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.teachstack.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.teachstack.logic.Messages;
import seedu.teachstack.model.Model;
import seedu.teachstack.model.ModelManager;
import seedu.teachstack.model.UserPrefs;
import seedu.teachstack.model.person.Person;
import seedu.teachstack.model.person.StudentId;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code DeleteArchiveCommand}.
 */
public class DeleteArchiveCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), getTypicalArchivedBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Person personToDelete = model.getArchivedPerson(ID_FIRST_PERSON);
        DeleteArchiveCommand deleteArchiveCommand = new DeleteArchiveCommand(ID_FIRST_PERSON);

        String expectedMessage = String.format(DeleteArchiveCommand.MESSAGE_DELETE_ARCHIVE_PERSON_SUCCESS,
                Messages.format(personToDelete));

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), model.getArchivedBook(), new UserPrefs());
        expectedModel.deleteArchivedPerson(personToDelete);

        assertCommandSuccess(deleteArchiveCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        StudentId outOfBoundId = new StudentId("A9999999Z");
        DeleteArchiveCommand deleteArchiveCommand = new DeleteArchiveCommand(outOfBoundId);

        assertArchivedBookCommandFailure(deleteArchiveCommand, model, Messages.MESSAGE_INVALID_DISPLAYED_STUDENT_ID);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showArchivedPersonAtIndex(model, INDEX_FIRST_PERSON);

        Person personToDelete = model.getArchivedPerson(ID_FIRST_PERSON);
        StudentId id = personToDelete.getStudentId();

        DeleteArchiveCommand deleteArchiveCommand = new DeleteArchiveCommand(id);

        String expectedMessage = String.format(DeleteArchiveCommand.MESSAGE_DELETE_ARCHIVE_PERSON_SUCCESS,
                Messages.format(personToDelete));

        Model expectedModel = new ModelManager(model.getAddressBook(), model.getArchivedBook(), new UserPrefs());
        expectedModel.deleteArchivedPerson(personToDelete);
        showNoPerson(expectedModel);

        assertCommandSuccess(deleteArchiveCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_success() {
        showArchivedPersonAtIndex(model, INDEX_FIRST_PERSON);

        Person personToDelete = model.getArchivedPerson(ID_SECOND_PERSON);
        StudentId outOfBoundId = personToDelete.getStudentId();

        // ensures that outOfBoundId is still in bounds of archived book list
        assertTrue(model.getArchivedBook().getArchivedList().stream()
                .filter(person -> person.getStudentId().equals(outOfBoundId)).toArray().length == 1);

        DeleteArchiveCommand deleteArchiveCommand = new DeleteArchiveCommand(outOfBoundId);

        String expectedMessage = String.format(DeleteArchiveCommand.MESSAGE_DELETE_ARCHIVE_PERSON_SUCCESS,
                Messages.format(personToDelete));

        Model expectedModel = new ModelManager(model.getAddressBook(), model.getArchivedBook(), new UserPrefs());
        expectedModel.deleteArchivedPerson(personToDelete);
        showArchivedPersonAtIndex(expectedModel, INDEX_FIRST_PERSON);

        assertCommandSuccess(deleteArchiveCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void equals() {
        DeleteArchiveCommand deleteArchiveFirstCommand = new DeleteArchiveCommand(ID_FIRST_PERSON);
        DeleteArchiveCommand deleteArchiveSecondCommand = new DeleteArchiveCommand(ID_SECOND_PERSON);

        // same object -> returns true
        assertTrue(deleteArchiveFirstCommand.equals(deleteArchiveFirstCommand));

        // same values -> returns true
        DeleteArchiveCommand deleteArchiveFirstCommandCopy = new DeleteArchiveCommand(ID_FIRST_PERSON);
        assertTrue(deleteArchiveFirstCommand.equals(deleteArchiveFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteArchiveFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteArchiveFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(deleteArchiveFirstCommand.equals(deleteArchiveSecondCommand));
    }

    @Test
    public void toStringMethod() {
        StudentId targetId = new StudentId("A0123456A");
        DeleteArchiveCommand deleteArchiveCommand = new DeleteArchiveCommand(targetId);
        String expected = DeleteArchiveCommand.class.getCanonicalName() + "{targetId=" + targetId + "}";
        assertEquals(expected, deleteArchiveCommand.toString());
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoPerson(Model model) {
        model.updateFilteredArchivedList(p -> false);

        assertTrue(model.getFilteredArchivedList().isEmpty());
    }
}
