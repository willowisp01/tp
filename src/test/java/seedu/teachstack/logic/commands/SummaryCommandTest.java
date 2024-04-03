package seedu.teachstack.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.teachstack.model.person.Person;
import seedu.teachstack.testutil.PersonBuilder;
import seedu.teachstack.testutil.TypicalPersons;

public class SummaryCommandTest {

    private ObservableList<Person> students;

    @BeforeEach
    public void setUp() {
        students = FXCollections.observableArrayList(
                new PersonBuilder(TypicalPersons.ALICE).build(),
                new PersonBuilder(TypicalPersons.BENSON).build(),
                new PersonBuilder(TypicalPersons.BOB).build(),
                new PersonBuilder(TypicalPersons.DANIEL).build(),
                new PersonBuilder(TypicalPersons.CARL).build()
        );
    }

    @Test
    public void calculateStandardDeviation_validData_returnsCorrectValue() {
        double meanGrade = 7.0; // Assuming mean grade for the test data is 7
        SummaryCommand summaryCommand = new SummaryCommand();
        double standardDeviation = summaryCommand.calculateStandardDeviation(students, meanGrade);
        assertEquals(2.449, standardDeviation, 0.001); // Asserting with a delta for precision
    }

    @Test
    public void countStudentsWithGrade_validData_returnsCorrectCount() {
        SummaryCommand summaryCommand = new SummaryCommand();
        int countA = summaryCommand.countStudentsWithGrade(students, "A");
        int countB = summaryCommand.countStudentsWithGrade(students, "B");
        int countC = summaryCommand.countStudentsWithGrade(students, "C");

        assertEquals(1, countA);
        assertEquals(1, countB);
        assertEquals(0, countC);
    }

}
