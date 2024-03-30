package seedu.teachstack.logic.parser;

import static seedu.teachstack.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.teachstack.logic.commands.CommandTestUtil.GROUP_DESC_1;
import static seedu.teachstack.logic.commands.CommandTestUtil.GROUP_DESC_2B;
import static seedu.teachstack.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.teachstack.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.teachstack.model.util.SampleDataUtil.getGroupSet;

import org.junit.jupiter.api.Test;

import seedu.teachstack.logic.commands.FindCommand;
import seedu.teachstack.model.person.PersonInGroupPredicate;

public class FindCommandParserTest {

    private FindCommandParser parser = new FindCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_noPrefix_throwsParseException() {
        assertParseFailure(parser, "Group 1", String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_withPreamble_throwsParseException() {
        assertParseFailure(parser, "should not be here" + GROUP_DESC_1,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindCommand() {
        // no leading and trailing whitespaces
        FindCommand expectedFindCommand =
                new FindCommand(new PersonInGroupPredicate(getGroupSet("Group 2B", "Group 1")));
        assertParseSuccess(parser, GROUP_DESC_1 + GROUP_DESC_2B, expectedFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n" + GROUP_DESC_1 + "\n \t" + GROUP_DESC_2B + " \t", expectedFindCommand);
    }

}
