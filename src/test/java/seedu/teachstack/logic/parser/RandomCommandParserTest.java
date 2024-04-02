package seedu.teachstack.logic.parser;

import static seedu.teachstack.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.teachstack.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.teachstack.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.teachstack.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.teachstack.logic.commands.RandomCommand;
import seedu.teachstack.model.group.Group;

public class RandomCommandParserTest {
    private RandomCommandParser parser = new RandomCommandParser();

    @Test
    public void parse_allValid_returnsRandomCommand() {
        assertParseSuccess(parser, "2 gp/Group", new RandomCommand("Group", 2));

        // with trailing space
        assertParseSuccess(parser, "2   gp/ Group ", new RandomCommand("Group", 2));
    }

    @Test
    public void parse_missingArg_throwsParseException() {
        assertParseFailure(parser, "2",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, RandomCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "gp/ TestGroup",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, RandomCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_nullArg_throwsNullPointerException() {
        // null
        assertThrows(NullPointerException.class, () -> new RandomCommandParser().parse(null));
    }

    @Test
    public void parse_invalidNumber_throwsParseException() {
        // String type
        assertParseFailure(parser, "String gp/Group",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, RandomCommand.MESSAGE_USAGE));

        // float type
        assertParseFailure(parser, "1.10 gp/Group",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, RandomCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidGroupName_throwsParseException() {
        // empty String
        assertParseFailure(parser, "2 gp/", Group.MESSAGE_CONSTRAINTS);

        // non-alphanumeric String
        assertParseFailure(parser, "2 gp/Gr@up", Group.MESSAGE_CONSTRAINTS);
    }
}
