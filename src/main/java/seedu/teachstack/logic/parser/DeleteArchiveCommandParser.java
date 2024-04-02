package seedu.teachstack.logic.parser;

import static seedu.teachstack.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.teachstack.logic.commands.DeleteArchiveCommand;
import seedu.teachstack.logic.parser.exceptions.ParseException;
import seedu.teachstack.model.person.StudentId;

/**
 * Parses input arguments and creates a new DeleteArchiveCommand object
 */
public class DeleteArchiveCommandParser implements Parser<DeleteArchiveCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteArchiveCommand
     * and returns a DeleteArchiveCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteArchiveCommand parse(String args) throws ParseException {
        try {
            StudentId id = ParserUtil.parseStudentId(args);
            return new DeleteArchiveCommand(id);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteArchiveCommand.MESSAGE_USAGE), pe);
        }
    }

}
