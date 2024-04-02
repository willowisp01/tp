package seedu.teachstack.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.teachstack.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.teachstack.logic.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.teachstack.testutil.Assert.assertThrows;
import static seedu.teachstack.testutil.TypicalArchivedStudentIds.ID_FIRST_PERSON;

import org.junit.jupiter.api.Test;

import seedu.teachstack.logic.commands.ClearArchiveCommand;
import seedu.teachstack.logic.commands.DeleteArchiveCommand;
import seedu.teachstack.logic.commands.EditArchiveCommand;
import seedu.teachstack.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.teachstack.logic.commands.HelpCommand;
import seedu.teachstack.logic.commands.UnarchiveCommand;
import seedu.teachstack.logic.parser.exceptions.ParseException;
import seedu.teachstack.model.person.Person;
import seedu.teachstack.testutil.EditPersonDescriptorBuilder;
import seedu.teachstack.testutil.PersonBuilder;
import seedu.teachstack.testutil.PersonUtil;

public class ArchivedBookParserTest {

    private final ArchivedBookParser parser = new ArchivedBookParser();

    @Test
    public void parseCommand_clear_archived() throws Exception {
        assertTrue(parser.parseCommand(ClearArchiveCommand.COMMAND_WORD) instanceof ClearArchiveCommand);
        assertTrue(parser.parseCommand(ClearArchiveCommand.COMMAND_WORD + " 3") instanceof ClearArchiveCommand);
    }

    @Test
    public void parseCommand_delete_archived() throws Exception {
        DeleteArchiveCommand command = (DeleteArchiveCommand) parser.parseCommand(
                DeleteArchiveCommand.COMMAND_WORD + " " + ID_FIRST_PERSON);
        assertEquals(new DeleteArchiveCommand(ID_FIRST_PERSON), command);
    }

    @Test
    public void parseCommand_edit_archived() throws Exception {
        Person person = new PersonBuilder().build();
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(person).build();
        EditArchiveCommand command = (EditArchiveCommand) parser.parseCommand(EditArchiveCommand.COMMAND_WORD
                + " " + ID_FIRST_PERSON + " " + PersonUtil.getEditPersonDescriptorDetails(descriptor));
        assertEquals(new EditArchiveCommand(ID_FIRST_PERSON, descriptor), command);
    }

    @Test
    public void parseCommand_unarchived() throws Exception {
        UnarchiveCommand command = (UnarchiveCommand) parser.parseCommand(
                UnarchiveCommand.COMMAND_WORD + " " + ID_FIRST_PERSON);
        assertEquals(new UnarchiveCommand(ID_FIRST_PERSON), command);
    }

    @Test
    public void parseCommand_unrecognisedInput_throwsParseException() {
        assertThrows(ParseException.class, String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE), ()
                -> parser.parseCommand(""));
    }

    @Test
    public void parseCommand_unknownCommand_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_UNKNOWN_COMMAND, () -> parser.parseCommand("unknownCommand"));
    }
}
