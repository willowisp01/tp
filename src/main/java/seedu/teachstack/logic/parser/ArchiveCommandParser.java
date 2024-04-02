package seedu.teachstack.logic.parser;

import static seedu.teachstack.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.teachstack.logic.commands.ArchiveCommand;
import seedu.teachstack.logic.parser.exceptions.ParseException;
import seedu.teachstack.model.person.StudentId;

/**
 * Parses input arguments and creates a new ArchiveCommand object
 */
public class ArchiveCommandParser implements Parser<ArchiveCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ArchiveCommand
     * and returns a ArchiveCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ArchiveCommand parse(String args) throws ParseException {
        try {
            StudentId id = ParserUtil.parseStudentId(args);
            return new ArchiveCommand(id);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ArchiveCommand.MESSAGE_USAGE), pe);
        }
    }

}

