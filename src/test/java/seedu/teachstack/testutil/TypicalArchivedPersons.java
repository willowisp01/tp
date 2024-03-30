package seedu.teachstack.testutil;

import static seedu.teachstack.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.teachstack.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.teachstack.logic.commands.CommandTestUtil.VALID_GRADE_AMY;
import static seedu.teachstack.logic.commands.CommandTestUtil.VALID_GRADE_BOB;
import static seedu.teachstack.logic.commands.CommandTestUtil.VALID_GROUP_GROUP1;
import static seedu.teachstack.logic.commands.CommandTestUtil.VALID_GROUP_GROUP2B;
import static seedu.teachstack.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.teachstack.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.teachstack.logic.commands.CommandTestUtil.VALID_STUDENTID_AMY;
import static seedu.teachstack.logic.commands.CommandTestUtil.VALID_STUDENTID_BOB;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.teachstack.model.ArchivedBook;
import seedu.teachstack.model.person.Person;

/**
 * A utility class containing a list of {@code Person} objects to be used in tests.
 */
public class TypicalArchivedPersons {

    public static final Person JOHN = new PersonBuilder().withName("John Doe")
            .withStudentId("A1234567X")
            .withEmail("e1234567@u.nus.edu")
            .withGrade("A+")
            .withGroups("Group 1").build();

    public static final Person JANE = new PersonBuilder().withName("Jane Smith")
            .withStudentId("A2345678X")
            .withEmail("e2345678@u.nus.edu")
            .withGrade("A")
            .withGroups("Group 2B", "Group 1").build();

    public static final Person EMILY = new PersonBuilder().withName("Emily Johnson")
            .withStudentId("A3456789X")
            .withGrade("A-")
            .withEmail("e3456789@u.nus.edu")
            .build();

    public static final Person MICHAEL = new PersonBuilder().withName("Michael Brown")
            .withStudentId("A4567890X")
            .withGrade("B+")
            .withEmail("e4567890@u.nus.edu")
            .withGroups("Group 1").build();

    public static final Person SARAH = new PersonBuilder().withName("Sarah Wilson")
            .withStudentId("A5678901X")
            .withGrade("B")
            .withEmail("e5678901@u.nus.edu")
            .build();

    public static final Person DAVID = new PersonBuilder().withName("David Taylor")
            .withStudentId("A6789012X")
            .withGrade("B-")
            .withEmail("e6789012@u.nus.edu")
            .build();

    // Manually added
    public static final Person EMMA = new PersonBuilder().withName("Emma Clark")
            .withStudentId("A7890123X")
            .withGrade("C")
            .withEmail("e7890123@u.nus.edu")
            .build();

    public static final Person OLIVER = new PersonBuilder().withName("Oliver White")
            .withStudentId("A8901234X")
            .withEmail("e8901234@u.nus.edu")
            .build();

    // Manually added - Person's details found in {@code CommandTestUtil}
    public static final Person AMY = new PersonBuilder().withName(VALID_NAME_AMY).withStudentId(VALID_STUDENTID_AMY)
            .withEmail(VALID_EMAIL_AMY).withGrade(VALID_GRADE_AMY)
            .withGroups(VALID_GROUP_GROUP2B).build();
    public static final Person BOB = new PersonBuilder().withName(VALID_NAME_BOB).withStudentId(VALID_STUDENTID_BOB)
            .withEmail(VALID_EMAIL_BOB).withGrade(VALID_GRADE_BOB)
            .withGroups(VALID_GROUP_GROUP1, VALID_GROUP_GROUP2B).build();


    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalArchivedPersons() {} // prevents instantiation

    /**
     * Returns an {@code ArchivedBook} with all the typical archived persons.
     */
    public static ArchivedBook getTypicalArchivedBook() {
        ArchivedBook ab = new ArchivedBook();
        for (Person person : getTypicalArchivedPersons()) {
            ab.addPerson(person);
        }
        return ab;
    }

    public static List<Person> getTypicalArchivedPersons() {
        return new ArrayList<>(Arrays.asList(JOHN, JANE, EMILY, MICHAEL, SARAH, DAVID));
    }
}
