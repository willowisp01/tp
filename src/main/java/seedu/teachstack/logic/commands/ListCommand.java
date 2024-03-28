package seedu.teachstack.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.teachstack.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import seedu.teachstack.model.Model;

/**
 * Lists all persons in the address book to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_SUCCESS = "Listed all persons";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Lists all persons in the address book.\n"
            + COMMAND_WORD + " archive"
            + ": Lists all persons in the archive list.\n";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
