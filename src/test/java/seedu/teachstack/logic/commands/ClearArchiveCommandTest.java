package seedu.teachstack.logic.commands;

import static seedu.teachstack.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.teachstack.testutil.TypicalArchivedPersons.getTypicalArchivedBook;
import static seedu.teachstack.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.teachstack.model.ArchivedBook;
import seedu.teachstack.model.Model;
import seedu.teachstack.model.ModelManager;
import seedu.teachstack.model.UserPrefs;

public class ClearArchiveCommandTest {

    @Test
    public void execute_emptyArchivedBook_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();

        assertCommandSuccess(new ClearArchiveCommand(), model, ClearArchiveCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_nonEmptyArchivedBook_success() {
        Model model = new ModelManager(getTypicalAddressBook(), getTypicalArchivedBook(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalAddressBook(), getTypicalArchivedBook(), new UserPrefs());
        expectedModel.setArchivedBook(new ArchivedBook());

        assertCommandSuccess(new ClearArchiveCommand(), model, ClearArchiveCommand.MESSAGE_SUCCESS, expectedModel);
    }

}
