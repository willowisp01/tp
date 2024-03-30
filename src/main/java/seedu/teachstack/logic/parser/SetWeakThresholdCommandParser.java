package seedu.teachstack.logic.parser;

import static seedu.teachstack.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.teachstack.logic.parser.CliSyntax.*;

import seedu.teachstack.logic.commands.SetWeakThresholdCommand;
import seedu.teachstack.logic.parser.exceptions.ParseException;
import seedu.teachstack.model.person.Grade;

/**
 * Parses input arguments and creates a SetWeakThresholdCommand Object
 */
public class SetWeakThresholdCommandParser implements Parser<SetWeakThresholdCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the SetWeakThresholdCommand
     * @return a SetWeakThreshold object to execute
     * @throws ParseException if the user input does not conform the expected format
     */
    public SetWeakThresholdCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_GRADE);

        if (!argMultimap.getValue(PREFIX_GRADE).isPresent()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    SetWeakThresholdCommand.MESSAGE_SET_THRESHOLD_FAIL));
        }

        try {
            Grade g = ParserUtil.parseGrade(argMultimap.getValue(PREFIX_GRADE).get());
            return new SetWeakThresholdCommand(g);

        } catch (Exception e) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    SetWeakThresholdCommand.MESSAGE_SET_THRESHOLD_FAIL));
        }

    }

}
