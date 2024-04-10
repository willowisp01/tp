package seedu.teachstack.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.teachstack.model.ArchivedBook;
import seedu.teachstack.model.Model;

/**
 * Clears the archived book.
 */
public class ClearArchiveCommand extends Command {

    public static final String COMMAND_WORD = "clear_archive";
    public static final String MESSAGE_SUCCESS = "Archived book has been cleared!";
    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.setArchivedBook(new ArchivedBook());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}

