package seedu.teachstack.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.teachstack.logic.parser.CliSyntax.PREFIX_GROUP;

import seedu.teachstack.commons.util.ToStringBuilder;
import seedu.teachstack.logic.Messages;
import seedu.teachstack.model.Model;
import seedu.teachstack.model.person.PersonInGroupPredicate;

/**
 * Finds and lists all persons in address book who is a member of any of the specified groups.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all students who are members of the specified"
            + "group(s) (case-sensitive) and displays them as a list with index numbers. If multiple groups are"
            + "specified, only students who are part of all groups will be displayed.\n"
            + "Parameters: " + PREFIX_GROUP + "GROUP...\n"
            + "Example: " + COMMAND_WORD + " " + PREFIX_GROUP + "Group 1 " + PREFIX_GROUP + "Group 2";

    private final PersonInGroupPredicate predicate;

    public FindCommand(PersonInGroupPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredPersonList(predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, model.getFilteredPersonList().size()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof FindCommand)) {
            return false;
        }

        FindCommand otherFindCommand = (FindCommand) other;
        return predicate.equals(otherFindCommand.predicate);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("predicate", predicate)
                .toString();
    }
}
