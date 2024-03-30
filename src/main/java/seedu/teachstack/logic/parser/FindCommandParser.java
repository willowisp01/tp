package seedu.teachstack.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.teachstack.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.teachstack.logic.parser.CliSyntax.PREFIX_GROUP;

import java.util.Set;

import seedu.teachstack.logic.commands.FindCommand;
import seedu.teachstack.logic.parser.exceptions.ParseException;
import seedu.teachstack.model.group.Group;
import seedu.teachstack.model.person.PersonInGroupPredicate;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindCommandParser implements Parser<FindCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns a FindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_GROUP);
        if (!argMultimap.getValue(PREFIX_GROUP).isPresent() || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        Set<Group> groups = ParserUtil.parseGroups(argMultimap.getAllValues(PREFIX_GROUP));
        return new FindCommand(new PersonInGroupPredicate(groups));
    }
}
