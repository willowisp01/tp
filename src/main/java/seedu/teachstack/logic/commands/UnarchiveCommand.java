package seedu.teachstack.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Optional;

import seedu.teachstack.commons.util.ToStringBuilder;
import seedu.teachstack.logic.Messages;
import seedu.teachstack.logic.commands.exceptions.CommandException;
import seedu.teachstack.model.Model;
import seedu.teachstack.model.person.Person;
import seedu.teachstack.model.person.StudentId;

/**
 * Unarchives a person identified using it's student id from the archived book.
 */
public class UnarchiveCommand extends Command {

    public static final String COMMAND_WORD = "unarchived";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Unarchives the person identified by their student id.\n"
            + "Parameters: STUDENT_ID (must be in format Axxxxxxx[A-Z] where x can be any digit, "
            + "[A-Z] can be any capital letter).\n"
            + "Example: " + COMMAND_WORD + " A0123456A";

    public static final String MESSAGE_UNARCHIVE_PERSON_SUCCESS = "Unarchived Person: %1$s";

    private final StudentId targetId;

    public UnarchiveCommand(StudentId targetId) {
        this.targetId = targetId;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> list = model.getArchivedBook().getArchivedList();

        Optional<Person> personOptional = list.stream()
                .filter(p -> p.getStudentId().equals(targetId))
                .findFirst();

        if (!personOptional.isPresent()) {
            throw new CommandException(Messages.MESSAGE_INVALID_DISPLAYED_STUDENT_ID);
        }

        Person personToUnarchive = personOptional.get();
        model.unarchivePerson(personToUnarchive);
        return new CommandResult(String.format(MESSAGE_UNARCHIVE_PERSON_SUCCESS, Messages.format(personToUnarchive)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof UnarchiveCommand)) {
            return false;
        }

        UnarchiveCommand otherUnarchiveCommand = (UnarchiveCommand) other;
        return targetId.equals(otherUnarchiveCommand.targetId);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetId", targetId)
                .toString();
    }
}
