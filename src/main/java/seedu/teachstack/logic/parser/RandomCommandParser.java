package seedu.teachstack.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.teachstack.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.teachstack.logic.parser.CliSyntax.PREFIX_GROUP;

import java.util.stream.Stream;

import seedu.teachstack.logic.commands.RandomCommand;
import seedu.teachstack.logic.parser.exceptions.ParseException;
import seedu.teachstack.model.group.Group;


/**
 * Parses input arguments and creates a new RandomCommand object
 */
public class RandomCommandParser implements Parser<RandomCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the {@code RandomCommand}
     * and returns a {@code RandomCommand} object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public RandomCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_GROUP);

        if (!arePrefixesPresent(argMultimap, PREFIX_GROUP)
                || argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, RandomCommand.MESSAGE_USAGE));
        }
        int numOfGroup;
        try {
            numOfGroup = Integer.parseInt(argMultimap.getPreamble());
        } catch (NumberFormatException nfe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, RandomCommand.MESSAGE_USAGE), nfe);
        }

        String name = argMultimap.getValue(PREFIX_GROUP).orElse("");

        if (name.equals("") || !name.matches(Group.VALIDATION_REGEX)) {
            throw new ParseException(Group.MESSAGE_CONSTRAINTS);
        }

        String trimmedName = name.trim();

        return new RandomCommand(trimmedName, numOfGroup);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
