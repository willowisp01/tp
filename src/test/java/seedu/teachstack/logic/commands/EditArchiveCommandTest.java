package seedu.teachstack.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.teachstack.logic.commands.CommandTestUtil.DESC_AMY;
import static seedu.teachstack.logic.commands.CommandTestUtil.DESC_BOB;
import static seedu.teachstack.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.teachstack.logic.commands.CommandTestUtil.VALID_GRADE_BOB;
import static seedu.teachstack.logic.commands.CommandTestUtil.VALID_GROUP_GROUP1;
import static seedu.teachstack.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.teachstack.logic.commands.CommandTestUtil.VALID_STUDENTID_BOB;
import static seedu.teachstack.logic.commands.CommandTestUtil.assertArchivedBookCommandFailure;
import static seedu.teachstack.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.teachstack.logic.commands.CommandTestUtil.showArchivedPersonAtIndex;
import static seedu.teachstack.testutil.TypicalArchivedPersons.getTypicalArchivedBook;
import static seedu.teachstack.testutil.TypicalArchivedStudentIds.ID_FIRST_PERSON;
import static seedu.teachstack.testutil.TypicalArchivedStudentIds.ID_SECOND_PERSON;
import static seedu.teachstack.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.teachstack.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.teachstack.commons.core.index.Index;
import seedu.teachstack.logic.Messages;
import seedu.teachstack.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.teachstack.model.AddressBook;
import seedu.teachstack.model.ArchivedBook;
import seedu.teachstack.model.Model;
import seedu.teachstack.model.ModelManager;
import seedu.teachstack.model.UserPrefs;
import seedu.teachstack.model.person.Person;
import seedu.teachstack.model.person.StudentId;
import seedu.teachstack.testutil.EditPersonDescriptorBuilder;
import seedu.teachstack.testutil.PersonBuilder;


/**
 * Contains integration tests (interaction with the Model) and unit tests for EditArchiveCommand.
 */
public class EditArchiveCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), getTypicalArchivedBook(), new UserPrefs());

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Person editedPerson = new PersonBuilder().withStudentId("A0128956X").build();
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(editedPerson).build();
        EditArchiveCommand editArchiveCommand = new EditArchiveCommand(ID_FIRST_PERSON, descriptor);

        String expectedMessage = String.format(EditArchiveCommand.MESSAGE_EDIT_ARCHIVED_PERSON_SUCCESS,
                Messages.format(editedPerson));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()),
                new ArchivedBook(model.getArchivedBook()), new UserPrefs());
        expectedModel.setArchivedPerson(model.getArchivedPerson(ID_FIRST_PERSON), editedPerson);

        assertCommandSuccess(editArchiveCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Index indexLastPerson = Index.fromOneBased(model.getFilteredArchivedList().size());
        Person lastPerson = model.getFilteredArchivedList().get(indexLastPerson.getZeroBased());
        StudentId id = lastPerson.getStudentId();

        PersonBuilder personInList = new PersonBuilder(lastPerson);

        Person editedPerson = personInList.withName(VALID_NAME_BOB).withStudentId(VALID_STUDENTID_BOB)
                .withGrade(VALID_GRADE_BOB).withEmail(VALID_EMAIL_BOB).withGroups(VALID_GROUP_GROUP1).build();

        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB)
                .withStudentId(VALID_STUDENTID_BOB).withGrade(VALID_GRADE_BOB)
                .withEmail(VALID_EMAIL_BOB).withGroups(VALID_GROUP_GROUP1).build();

        EditArchiveCommand editArchiveCommand = new EditArchiveCommand(id, descriptor);

        String expectedMessage = String.format(EditArchiveCommand.MESSAGE_EDIT_ARCHIVED_PERSON_SUCCESS,
                Messages.format(editedPerson));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()),
                new ArchivedBook(model.getArchivedBook()), new UserPrefs());
        expectedModel.setArchivedPerson(lastPerson, editedPerson);

        assertCommandSuccess(editArchiveCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        EditArchiveCommand editArchiveCommand = new EditArchiveCommand(ID_FIRST_PERSON,
                new EditPersonDescriptor());
        Person editedPerson = model.getArchivedPerson(ID_FIRST_PERSON);

        String expectedMessage = String.format(EditArchiveCommand.MESSAGE_EDIT_ARCHIVED_PERSON_SUCCESS,
                Messages.format(editedPerson));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()),
                new ArchivedBook(model.getArchivedBook()), new UserPrefs());

        assertCommandSuccess(editArchiveCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        showArchivedPersonAtIndex(model, INDEX_FIRST_PERSON);

        Person personInFilteredList = model.getArchivedPerson(ID_FIRST_PERSON);
        Person editedPerson = new PersonBuilder(personInFilteredList).withName(VALID_NAME_BOB).build();
        EditArchiveCommand editArchiveCommand = new EditArchiveCommand(ID_FIRST_PERSON,
                new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB).build());

        String expectedMessage = String.format(EditArchiveCommand.MESSAGE_EDIT_ARCHIVED_PERSON_SUCCESS,
                Messages.format(editedPerson));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()),
                new ArchivedBook(model.getArchivedBook()), new UserPrefs());
        expectedModel.setArchivedPerson(model.getArchivedPerson(ID_FIRST_PERSON), editedPerson);

        assertCommandSuccess(editArchiveCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicatePersonUnfilteredList_failure() {
        Person firstPerson = model.getFilteredArchivedList().get(INDEX_FIRST_PERSON.getZeroBased());
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(firstPerson).build();
        EditArchiveCommand editArchiveCommand = new EditArchiveCommand(ID_SECOND_PERSON, descriptor);

        assertArchivedBookCommandFailure(editArchiveCommand, model, EditArchiveCommand.MESSAGE_DUPLICATE_PERSON);
    }

    @Test
    public void execute_duplicatePersonFilteredList_failure() {
        showArchivedPersonAtIndex(model, INDEX_FIRST_PERSON);

        // edit person in filtered list into a duplicate in archived book
        Person personInList = model.getArchivedPerson(ID_SECOND_PERSON);
        EditArchiveCommand editArchiveCommand = new EditArchiveCommand(ID_FIRST_PERSON,
                new EditPersonDescriptorBuilder(personInList).build());

        assertArchivedBookCommandFailure(editArchiveCommand, model, EditArchiveCommand.MESSAGE_DUPLICATE_PERSON);
    }

    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() {
        StudentId outOfBoundId = new StudentId("A9999999Z");
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB).build();
        EditArchiveCommand editArchiveCommand = new EditArchiveCommand(outOfBoundId, descriptor);

        assertArchivedBookCommandFailure(editArchiveCommand, model, Messages.MESSAGE_INVALID_DISPLAYED_STUDENT_ID);
    }

    /**
     * Edit filtered list where student id is not in filtered list,
     * but in archived book
     */
    @Test
    public void execute_invalidPersonIndexFilteredList_success() {
        showArchivedPersonAtIndex(model, INDEX_FIRST_PERSON);
        StudentId outOfBoundId = ID_SECOND_PERSON;
        Person personNotInFilteredList = model.getArchivedPerson(ID_SECOND_PERSON);
        Person editedPerson = new PersonBuilder(personNotInFilteredList).withName(VALID_NAME_BOB).build();

        // ensures that outOfBoundIndex is still in bounds of archive book list
        assertTrue(model.getArchivedBook().getArchivedList().stream()
                .filter(person -> person.getStudentId().equals(outOfBoundId)).toArray().length == 1);

        EditArchiveCommand editArchiveCommand = new EditArchiveCommand(outOfBoundId,
                new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB).build());

        String expectedMessage = String.format(EditArchiveCommand.MESSAGE_EDIT_ARCHIVED_PERSON_SUCCESS,
                Messages.format(editedPerson));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()),
                new ArchivedBook(model.getArchivedBook()), new UserPrefs());
        expectedModel.setArchivedPerson(model.getArchivedBook().getArchivedList().get(1), editedPerson);

        assertCommandSuccess(editArchiveCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void equals() {
        final EditCommand standardCommand = new EditCommand(ID_FIRST_PERSON, DESC_AMY);

        // same values -> returns true
        EditPersonDescriptor copyDescriptor = new EditPersonDescriptor(DESC_AMY);
        EditCommand commandWithSameValues = new EditCommand(ID_FIRST_PERSON, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditCommand(ID_SECOND_PERSON, DESC_AMY)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditCommand(ID_FIRST_PERSON, DESC_BOB)));
    }

    @Test
    public void toStringMethod() {
        StudentId id = new StudentId("A0123456A");
        EditPersonDescriptor editPersonDescriptor = new EditPersonDescriptor();
        EditCommand editCommand = new EditCommand(id, editPersonDescriptor);
        String expected = EditCommand.class.getCanonicalName() + "{student id=" + id + ", editPersonDescriptor="
                + editPersonDescriptor + "}";
        assertEquals(expected, editCommand.toString());
    }

}
