package seedu.teachstack.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.teachstack.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.teachstack.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.teachstack.logic.commands.CommandTestUtil.VALID_GRADE_BOB;
import static seedu.teachstack.logic.commands.CommandTestUtil.VALID_GROUP_GROUP1;
import static seedu.teachstack.logic.commands.CommandTestUtil.VALID_GROUP_GROUP2B;
import static seedu.teachstack.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.teachstack.logic.commands.CommandTestUtil.VALID_STUDENTID_AMY;
import static seedu.teachstack.logic.commands.CommandTestUtil.VALID_STUDENTID_BOB;
import static seedu.teachstack.testutil.Assert.assertThrows;
import static seedu.teachstack.testutil.TypicalPersons.ALICE;
import static seedu.teachstack.testutil.TypicalPersons.BOB;

import org.junit.jupiter.api.Test;

import seedu.teachstack.testutil.PersonBuilder;

public class PersonTest {

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        Person person = new PersonBuilder().build();
        assertThrows(UnsupportedOperationException.class, () -> person.getGroups().remove(0));
    }

    @Test
    public void isSamePerson() {
        // same object -> returns true
        assertTrue(ALICE.isSamePerson(ALICE));

        // null -> returns false
        assertFalse(ALICE.isSamePerson(null));

        // same student id and email, all other attributes different -> returns true
        Person editedAlice = new PersonBuilder(ALICE).withName(VALID_NAME_BOB)
                .withGrade(VALID_GRADE_BOB).withGroups(VALID_GROUP_GROUP1).build();

        assertTrue(ALICE.isSamePerson(editedAlice));

        // different student id, all other attributes same -> returns false
        editedAlice = new PersonBuilder(ALICE).withStudentId(VALID_STUDENTID_AMY).build();
        assertFalse(ALICE.isSamePerson(editedAlice));
    }

    @Test
    public void isSameEmail() {
        // same object -> returns true
        assertTrue(ALICE.isSameEmail(ALICE));

        // null -> returns false
        assertFalse(ALICE.isSameEmail(null));

        // same email, all other attributes different -> returns true
        Person editedAlice = new PersonBuilder(ALICE).withName(VALID_NAME_BOB).withStudentId(VALID_STUDENTID_BOB)
                .withGrade(VALID_GRADE_BOB).withGroups(VALID_GROUP_GROUP1).build();

        assertTrue(ALICE.isSameEmail(editedAlice));

        // different email, all other attributes same -> returns false
        editedAlice = new PersonBuilder(ALICE).withEmail(VALID_EMAIL_AMY).build();
        assertFalse(ALICE.isSameEmail(editedAlice));
    }

    @Test
    public void isSameId() {
        // same object -> returns true
        assertTrue(ALICE.isSameId(ALICE));

        // null -> returns false
        assertFalse(ALICE.isSameId(null));

        // same student id, all other attributes different -> returns true
        Person editedAlice = new PersonBuilder(ALICE).withName(VALID_NAME_BOB).withEmail(VALID_EMAIL_BOB)
                .withGrade(VALID_GRADE_BOB).withGroups(VALID_GROUP_GROUP1).build();

        assertTrue(ALICE.isSameId(editedAlice));

        // different student id, all other attributes same -> returns false
        editedAlice = new PersonBuilder(ALICE).withStudentId(VALID_STUDENTID_AMY).build();
        assertFalse(ALICE.isSameId(editedAlice));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Person aliceCopy = new PersonBuilder(ALICE).build();
        assertTrue(ALICE.equals(aliceCopy));

        // same object -> returns true
        assertTrue(ALICE.equals(ALICE));

        // null -> returns false
        assertFalse(ALICE.equals(null));

        // different type -> returns false
        assertFalse(ALICE.equals(5));

        // different person -> returns false
        assertFalse(ALICE.equals(BOB));

        // different name -> returns false
        Person editedAlice = new PersonBuilder(ALICE).withName(VALID_NAME_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different studentId -> returns false
        editedAlice = new PersonBuilder(ALICE).withStudentId(VALID_STUDENTID_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different email -> returns false
        editedAlice = new PersonBuilder(ALICE).withEmail(VALID_EMAIL_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different grade -> returns false
        editedAlice = new PersonBuilder(ALICE).withGrade(VALID_GRADE_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different groups -> returns false
        editedAlice = new PersonBuilder(ALICE).withGroups(VALID_GROUP_GROUP2B).build();

        assertFalse(ALICE.equals(editedAlice));
    }

    @Test
    public void toStringMethod() {

        String expected = Person.class.getCanonicalName() + "{name=" + ALICE.getName() + ", studentId="
                + ALICE.getStudentId() + ", email=" + ALICE.getEmail()
                + ", grade=" + ALICE.getGrade() + ", groups=" + ALICE.getGroups() + "}";

        assertEquals(expected, ALICE.toString());
    }
}
