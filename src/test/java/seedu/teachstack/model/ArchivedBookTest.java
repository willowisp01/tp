package seedu.teachstack.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.teachstack.logic.commands.CommandTestUtil.VALID_GROUP_GROUP1;
import static seedu.teachstack.testutil.Assert.assertThrows;
import static seedu.teachstack.testutil.TypicalArchivedPersons.ALICE;
import static seedu.teachstack.testutil.TypicalArchivedPersons.getTypicalArchivedBook;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.teachstack.model.person.Person;
import seedu.teachstack.model.person.exceptions.DuplicatePersonException;
import seedu.teachstack.testutil.PersonBuilder;

public class ArchivedBookTest {

    private final ArchivedBook archivedBook = new ArchivedBook();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), archivedBook.getArchivedList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> archivedBook.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlyArchivedBook_replacesData() {
        ArchivedBook newData = getTypicalArchivedBook();
        archivedBook.resetData(newData);
        assertEquals(newData, archivedBook);
    }

    @Test
    public void resetData_withDuplicatePersons_throwsDuplicatePersonException() {
        // Two persons with the same identity fields
        Person editedAlice = new PersonBuilder(ALICE).withGroups(VALID_GROUP_GROUP1).build();
        List<Person> newPersons = Arrays.asList(ALICE, editedAlice);
        ArchivedBookStub newData = new ArchivedBookStub(newPersons);

        assertThrows(DuplicatePersonException.class, () -> archivedBook.resetData(newData));
    }

    @Test
    public void hasPerson_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> archivedBook.hasPerson(null));
    }

    @Test
    public void hasPerson_personNotInArchivedBook_returnsFalse() {
        assertFalse(archivedBook.hasPerson(ALICE));
    }

    @Test
    public void hasPerson_personInArchivedBook_returnsTrue() {
        archivedBook.addPerson(ALICE);
        assertTrue(archivedBook.hasPerson(ALICE));
    }

    @Test
    public void hasPerson_personWithSameIdentityFieldsInArchivedBook_returnsTrue() {
        archivedBook.addPerson(ALICE);
        Person editedAlice = new PersonBuilder(ALICE).withGroups(VALID_GROUP_GROUP1).build();
        assertTrue(archivedBook.hasPerson(editedAlice));
    }

    @Test
    public void getPersonList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> archivedBook.getArchivedList().remove(0));
    }

    @Test
    public void toStringMethod() {
        String expected = ArchivedBook.class.getCanonicalName() + "{persons=" + archivedBook.getArchivedList() + "}";
        assertEquals(expected, archivedBook.toString());
    }

    /**
     * A stub ReadOnlyArchivedBook whose archived list can violate interface constraints.
     */
    private static class ArchivedBookStub implements ReadOnlyArchivedBook {
        private final ObservableList<Person> persons = FXCollections.observableArrayList();

        ArchivedBookStub(Collection<Person> persons) {
            this.persons.setAll(persons);
        }

        @Override
        public ObservableList<Person> getArchivedList() {
            return persons;
        }
    }

}
