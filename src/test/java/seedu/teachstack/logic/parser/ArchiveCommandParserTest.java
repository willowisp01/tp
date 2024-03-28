package seedu.teachstack.logic.parser;

import static seedu.teachstack.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.teachstack.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.teachstack.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.teachstack.testutil.TypicalStudentIds.ID_FIRST_PERSON;

import org.junit.jupiter.api.Test;

import seedu.teachstack.logic.commands.ArchiveCommand;

/**
 * As we are only doing white-box testing, our test cases do not cover path variations
 * outside of the DeleteCommand code. For example, inputs "A0123456A" and "A0123456A abc" take the
 * same path through the ArchiveCommand, and therefore we test only one of them.
 * The path variation for those two cases occur inside the ParserUtil, and
 * therefore should be covered by the ParserUtilTest.
 */
public class ArchiveCommandParserTest {

    private ArchiveCommandParser parser = new ArchiveCommandParser();

    @Test
    public void parse_validArgs_returnsArchiveCommand() {
        assertParseSuccess(parser, "A0128956X", new ArchiveCommand(ID_FIRST_PERSON));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT, ArchiveCommand.MESSAGE_USAGE));
    }
}
