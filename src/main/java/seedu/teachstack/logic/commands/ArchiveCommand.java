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
 * Archives a person identified using it's student id from the address book.
 */
public class ArchiveCommand extends Command {

    public static final String COMMAND_WORD = "archive";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Archives the person identified by their student id.\n"
            + "Parameters: STUDENT_ID (must be in format Axxxxxxx[A-Z] where x can be any digit, "
            + "[A-Z] can be any capital letter).\n"
            + "Example: " + COMMAND_WORD + " A0123456A";

    public static final String MESSAGE_ARCHIVE_PERSON_SUCCESS = "Archived Person: %1$s";

    private final StudentId targetId;

    public ArchiveCommand(StudentId targetId) {
        this.targetId = targetId;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> list = model.getAddressBook().getPersonList();

        Optional<Person> personOptional = list.stream()
                .filter(p -> p.getStudentId().equals(targetId))
                .findFirst();

        if (!personOptional.isPresent()) {
            throw new CommandException(Messages.MESSAGE_INVALID_DISPLAYED_STUDENT_ID);
        }

        Person personToArchive = personOptional.get();
        personToArchive.setArchivedPerson();
        model.archivePerson(personToArchive);
        return new CommandResult(String.format(MESSAGE_ARCHIVE_PERSON_SUCCESS, Messages.format(personToArchive)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ArchiveCommand)) {
            return false;
        }

        ArchiveCommand otherArchiveCommand = (ArchiveCommand) other;
        return targetId.equals(otherArchiveCommand.targetId);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetId", targetId)
                .toString();
    }
}
