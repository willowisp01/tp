package seedu.teachstack.logic.commands;

import javafx.collections.ObservableList;
import seedu.teachstack.logic.commands.exceptions.CommandException;
import seedu.teachstack.model.Model;
import seedu.teachstack.model.person.Person;
import java.util.DoubleSummaryStatistics;


public class SummaryCommand extends Command{
    public static final String COMMAND_WORD = "summary";
    public static final String MESSAGE_SUCCESS = "Summary: Total Students: %d %.2f %.2f";

    public SummaryCommand() {

    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        ObservableList<Person> students = model.getFilteredPersonList();
        int totalStudents = students.size();

        DoubleSummaryStatistics stats = students.stream()
                .mapToDouble(student -> student.getGrade().gradeToInt())
                .summaryStatistics();

        double meanGrade = stats.getAverage();
        double standardDeviation = calculateStandardDeviation(students, meanGrade);

        String summaryMessage = String.format(MESSAGE_SUCCESS, totalStudents, meanGrade, standardDeviation);

        // Return CommandResult with summary message
        return new CommandResult(summaryMessage, false, false, true);
        //return new CommandResult(String.format(MESSAGE_SUCCESS, totalStudents, meanGrade, standardDeviation));
    }

    private double calculateStandardDeviation(ObservableList<Person> students, double meanGrade) {
        double variance = students.stream()
                .mapToDouble(student -> {
                    double diff = student.getGrade().gradeToInt() - meanGrade;
                    return diff * diff;
                })
                .average()
                .orElse(0);

        return Math.sqrt(variance);
    }
}
