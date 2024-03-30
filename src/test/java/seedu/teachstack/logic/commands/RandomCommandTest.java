package seedu.teachstack.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static seedu.teachstack.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.teachstack.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.teachstack.testutil.Assert.assertThrows;
import static seedu.teachstack.testutil.TypicalPersons.ALICE;
import static seedu.teachstack.testutil.TypicalPersons.FIONA;
import static seedu.teachstack.testutil.TypicalPersons.GEORGE;
import static seedu.teachstack.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.teachstack.logic.commands.exceptions.CommandException;
import seedu.teachstack.model.AddressBook;
import seedu.teachstack.model.Model;
import seedu.teachstack.model.ModelManager;
import seedu.teachstack.model.UserPrefs;
import seedu.teachstack.model.group.Group;
import seedu.teachstack.model.person.Person;
import seedu.teachstack.testutil.PersonBuilder;

public class RandomCommandTest {
    private static final String GROUP_NAME_A = "TestGroupA";
    private static final String GROUP_NAME_B = "TestGroupB";

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }


    @Test
    public void constructor_nullName_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new RandomCommand(null, 1));
    }

    @Test
    public void execute_validNameAndNumber_success() {
        RandomCommand randomCommand = new RandomCommand(GROUP_NAME_A, 1);
        Person editedPerson2 = new PersonBuilder(ALICE).withGrade("C").withGroups().build();
        model.setPerson(model.getPerson(ALICE.getStudentId()), editedPerson2);

        Person editedPerson = new PersonBuilder(GEORGE).withGroups(GROUP_NAME_A + " 1").build();
        Person editedPerson2Copy = new PersonBuilder(editedPerson2).withGroups(GROUP_NAME_A + " 1").build();
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(model.getPerson(ALICE.getStudentId()), editedPerson2Copy);
        expectedModel.setPerson(model.getPerson(GEORGE.getStudentId()), editedPerson);
        assertCommandSuccess(randomCommand, model,
                String.format(RandomCommand.MESSAGE_RANDOM_GROUP_SUCCESS, 1, GROUP_NAME_A), expectedModel);
    }

    @Test
    public void execute_validNameAndNumberUnequalGroup_success() {
        RandomCommand randomCommand = new RandomCommand(GROUP_NAME_A, 2);
        Person editedPerson2 = new PersonBuilder(ALICE).withGrade("C").withGroups().build();
        model.setPerson(model.getPerson(ALICE.getStudentId()), editedPerson2);
        Person editedPerson3 = new PersonBuilder(FIONA).withGrade("D").withGroups().build();
        model.setPerson(model.getPerson(FIONA.getStudentId()), editedPerson3);

        try {
            randomCommand.execute(model);
            if (model.getPerson(ALICE.getStudentId()).getGroups().contains(new Group(GROUP_NAME_A + " 1"))) {
                if (model.getPerson(GEORGE.getStudentId()).getGroups().contains(new Group(GROUP_NAME_A + " 1"))) {
                    assertTrue(model.getPerson(FIONA.getStudentId())
                            .getGroups().contains(new Group(GROUP_NAME_A + " 2")));

                } else if (model.getPerson(GEORGE.getStudentId())
                        .getGroups().contains(new Group(GROUP_NAME_A + " 2"))) {
                    assertTrue(model.getPerson(FIONA.getStudentId())
                            .getGroups().contains(new Group(GROUP_NAME_A + " 1")));
                }
            } else if (model.getPerson(ALICE.getStudentId()).getGroups().contains(new Group(GROUP_NAME_A + " 2"))) {
                assertTrue((model.getPerson(FIONA.getStudentId())
                        .getGroups().contains(new Group(GROUP_NAME_A + " 1")))
                        && (model.getPerson(GEORGE.getStudentId())
                        .getGroups().contains(new Group(GROUP_NAME_A + " 1"))));
            }
        } catch (CommandException e) {
            fail();
        }


    }

    @Test
    public void execute_negativeNumber_throwsCommandException() {
        RandomCommand randomCommand = new RandomCommand(GROUP_NAME_A, -3);
        assertCommandFailure(randomCommand, model,
                RandomCommand.MESSAGE_RANDOM_GROUP_NON_POSITIVE_NUM);
    }

    @Test
    public void execute_moreGroupsThanWeakStudent_throwsCommandException() {
        RandomCommand randomCommand = new RandomCommand(GROUP_NAME_A, 3);
        Person editedPerson2 = new PersonBuilder(ALICE).withGrade("C").build();
        model.setPerson(model.getPerson(ALICE.getStudentId()), editedPerson2);
        assertCommandFailure(randomCommand, model,
                String.format(RandomCommand.MESSAGE_RANDOM_MORE_GROUPS_THAN_STUDENT, 2, 2));
    }

    @Test
    public void execute_sameGroupsAsWeakStudent_throwsCommandException() {
        RandomCommand randomCommand = new RandomCommand(GROUP_NAME_A, 2);
        Person editedPerson2 = new PersonBuilder(ALICE).withGrade("C").build();
        model.setPerson(model.getPerson(ALICE.getStudentId()), editedPerson2);
        assertCommandFailure(randomCommand, model,
                String.format(RandomCommand.MESSAGE_RANDOM_MORE_GROUPS_THAN_STUDENT, 2, 2));
    }

    @Test
    public void execute_zeroGroup_throwsCommandException() {
        RandomCommand randomCommand = new RandomCommand(GROUP_NAME_A, 0);
        assertCommandFailure(randomCommand, model,
                RandomCommand.MESSAGE_RANDOM_GROUP_NON_POSITIVE_NUM);
    }

    @Test
    public void execute_zeroWeakStudent_throwsCommandException() {
        RandomCommand randomCommand = new RandomCommand(GROUP_NAME_A, 1);
        Person editedPerson = new PersonBuilder(GEORGE).withGrade("A").build();
        model.setPerson(model.getPerson(GEORGE.getStudentId()), editedPerson);
        assertCommandFailure(randomCommand, model,
                RandomCommand.MESSAGE_RANDOM_NO_STUDENTS);
    }

    @Test
    public void equals() {
        RandomCommand randomFirstCommand = new RandomCommand(GROUP_NAME_A, 1);
        RandomCommand randomSecondCommand = new RandomCommand(GROUP_NAME_A, 2);
        RandomCommand randomThirdCommand = new RandomCommand(GROUP_NAME_B, 1);

        // same object -> returns true
        assertTrue(randomFirstCommand.equals(randomFirstCommand));

        // same values -> returns true
        RandomCommand randomFirstCommandCopy = new RandomCommand(GROUP_NAME_A, 1);
        assertTrue(randomFirstCommand.equals(randomFirstCommandCopy));

        // different types -> returns false
        assertFalse(randomFirstCommand.equals(1));

        // null -> returns false
        assertFalse(randomFirstCommand.equals(null));

        // different values -> returns false
        assertFalse(randomFirstCommand.equals(randomSecondCommand));
        assertFalse(randomFirstCommand.equals(randomThirdCommand));
        assertFalse(randomSecondCommand.equals(randomThirdCommand));
    }

    @Test
    public void toStringMethod() {
        RandomCommand randomCommand = new RandomCommand(GROUP_NAME_A, 2);
        String expected = RandomCommand.class.getCanonicalName() + "{name=" + GROUP_NAME_A + ", numOfGroup=" + 2 + "}";
        assertEquals(expected, randomCommand.toString());
    }

}
