package seedu.teachstack.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.teachstack.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.teachstack.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.teachstack.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.teachstack.testutil.TypicalArchivedPersons.getTypicalArchivedBook;
import static seedu.teachstack.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.teachstack.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.teachstack.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.teachstack.testutil.TypicalStudentIds.ID_FIRST_PERSON;
import static seedu.teachstack.testutil.TypicalStudentIds.ID_SECOND_PERSON;

import org.junit.jupiter.api.Test;

import seedu.teachstack.logic.Messages;
import seedu.teachstack.model.Model;
import seedu.teachstack.model.ModelManager;
import seedu.teachstack.model.UserPrefs;
import seedu.teachstack.model.person.Person;
import seedu.teachstack.model.person.StudentId;

public class ArchiveCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), getTypicalArchivedBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Person personToArchive = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        StudentId id = personToArchive.getStudentId();
        ArchiveCommand archiveCommand = new ArchiveCommand(id);

        String expectedMessage = String.format(ArchiveCommand.MESSAGE_ARCHIVE_PERSON_SUCCESS,
                Messages.format(personToArchive));

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), model.getArchivedBook(), new UserPrefs());
        expectedModel.archivePerson(personToArchive);

        assertCommandSuccess(archiveCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        StudentId outOfBoundId = new StudentId("A9999999Z");
        ArchiveCommand archiveCommand = new ArchiveCommand(outOfBoundId);

        assertCommandFailure(archiveCommand, model, Messages.MESSAGE_INVALID_DISPLAYED_STUDENT_ID);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Person personToArchive = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        StudentId id = personToArchive.getStudentId();

        ArchiveCommand archiveCommand = new ArchiveCommand(id);

        String expectedMessage = String.format(ArchiveCommand.MESSAGE_ARCHIVE_PERSON_SUCCESS,
                Messages.format(personToArchive));

        Model expectedModel = new ModelManager(model.getAddressBook(), model.getArchivedBook(), new UserPrefs());
        expectedModel.archivePerson(personToArchive);
        showNoPerson(expectedModel);

        assertCommandSuccess(archiveCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_success() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Person personToArchive = model.getAddressBook().getPersonList().get(INDEX_SECOND_PERSON.getZeroBased());
        StudentId outOfBoundId = personToArchive.getStudentId();

        // ensures that outOfBoundId is still in bounds of address book list
        assertTrue(model.getAddressBook().getPersonList().stream()
                .filter(person -> person.getStudentId().equals(outOfBoundId)).toArray().length == 1);

        ArchiveCommand archiveCommand = new ArchiveCommand(outOfBoundId);

        String expectedMessage = String.format(ArchiveCommand.MESSAGE_ARCHIVE_PERSON_SUCCESS,
                Messages.format(personToArchive));

        Model expectedModel = new ModelManager(model.getAddressBook(), model.getArchivedBook(), new UserPrefs());
        expectedModel.archivePerson(personToArchive);
        showPersonAtIndex(expectedModel, INDEX_FIRST_PERSON);

        assertCommandSuccess(archiveCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void equals() {
        ArchiveCommand archiveFirstCommand = new ArchiveCommand(ID_FIRST_PERSON);
        ArchiveCommand archiveSecondCommand = new ArchiveCommand(ID_SECOND_PERSON);

        // same object -> returns true
        assertTrue(archiveFirstCommand.equals(archiveFirstCommand));

        // same values -> returns true
        ArchiveCommand archiveFirstCommandCopy = new ArchiveCommand(ID_FIRST_PERSON);
        assertTrue(archiveFirstCommand.equals(archiveFirstCommandCopy));

        // different types -> returns false
        assertFalse(archiveFirstCommand.equals(1));

        // null -> returns false
        assertFalse(archiveFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(archiveFirstCommand.equals(archiveSecondCommand));
    }

    @Test
    public void toStringMethod() {
        StudentId targetId = new StudentId("A0123456A");
        ArchiveCommand archiveCommand = new ArchiveCommand(targetId);
        String expected = ArchiveCommand.class.getCanonicalName() + "{targetId=" + targetId + "}";
        assertEquals(expected, archiveCommand.toString());
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoPerson(Model model) {
        model.updateFilteredPersonList(p -> false);

        assertTrue(model.getFilteredPersonList().isEmpty());
    }
}
