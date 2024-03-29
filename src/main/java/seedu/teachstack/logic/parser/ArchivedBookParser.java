package seedu.teachstack.logic.parser;

import static seedu.teachstack.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.teachstack.logic.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.teachstack.commons.core.LogsCenter;
import seedu.teachstack.logic.commands.ClearArchiveCommand;
import seedu.teachstack.logic.commands.Command;
import seedu.teachstack.logic.commands.DeleteArchiveCommand;
import seedu.teachstack.logic.commands.EditArchiveCommand;
import seedu.teachstack.logic.commands.HelpCommand;
import seedu.teachstack.logic.commands.UnarchiveCommand;
import seedu.teachstack.logic.parser.exceptions.ParseException;

/**
 * Parses user input.
 */
public class ArchivedBookParser {

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>[^\\s]+)(\\s+(?<subCommand>archive))?(?<arguments>.*)");
    private static final Logger logger = LogsCenter.getLogger(ArchivedBookParser.class);

    /**
     * Parses user input into command for execution.
     *
     * @param userInput full user input string
     * @return the command based on the user input
     * @throws ParseException if the user input does not conform the expected format
     */
    public Command parseCommand(String userInput) throws ParseException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");

        // Note to developers: Change the log level in config.json to enable lower level (i.e., FINE, FINER and lower)
        // log messages such as the one below.
        // Lower level log messages are used sparingly to minimize noise in the code.
        logger.fine("Command word: " + commandWord + "; Arguments: " + arguments);

        switch (commandWord) {

        case UnarchiveCommand.COMMAND_WORD:
            return new UnarchiveCommandParser().parse(arguments);

        case EditArchiveCommand.COMMAND_WORD:
            return new EditArchiveCommandParser().parse(arguments);

        case DeleteArchiveCommand.COMMAND_WORD:
            return new DeleteArchiveCommandParser().parse(arguments);

        case ClearArchiveCommand.COMMAND_WORD:
            return new ClearArchiveCommand();

        default:
            logger.finer("This user input caused a ParseException: " + userInput);
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

}

