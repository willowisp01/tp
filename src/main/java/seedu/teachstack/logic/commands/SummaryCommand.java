package seedu.teachstack.logic.commands;

import javafx.collections.ObservableList;
import seedu.teachstack.logic.commands.exceptions.CommandException;
import seedu.teachstack.model.Model;
import seedu.teachstack.model.person.Person;

public class SummaryCommand extends Command{
    public static final String COMMAND_WORD = "summary";
    public static final String MESSAGE_SUCCESS = "Summary: Total Students: %d";

    public SummaryCommand() {

    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        ObservableList<Person> students = model.getFilteredPersonList();
        int totalStudents = students.size();
        return new CommandResult(String.format(MESSAGE_SUCCESS, totalStudents));
    }
}
