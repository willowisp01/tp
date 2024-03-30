package seedu.teachstack.logic;

import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.nio.file.Path;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import seedu.teachstack.commons.core.GuiSettings;
import seedu.teachstack.commons.core.LogsCenter;
import seedu.teachstack.logic.commands.Command;
import seedu.teachstack.logic.commands.CommandResult;
import seedu.teachstack.logic.commands.exceptions.CommandException;
import seedu.teachstack.logic.parser.AddressBookParser;
import seedu.teachstack.logic.parser.ArchivedBookParser;
import seedu.teachstack.logic.parser.exceptions.ParseException;
import seedu.teachstack.model.Model;
import seedu.teachstack.model.ReadOnlyAddressBook;
import seedu.teachstack.model.ReadOnlyArchivedBook;
import seedu.teachstack.model.person.Person;
import seedu.teachstack.storage.Storage;

/**
 * The main LogicManager of the app.
 */
public class LogicManager implements Logic {
    public static final String FILE_OPS_ERROR_FORMAT = "Could not save data due to the following error: %s";

    public static final String FILE_OPS_PERMISSION_ERROR_FORMAT =
            "Could not save data to file %s due to insufficient permissions to write to the file or the folder.";

    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;
    private final Storage storage;
    private final AddressBookParser addressBookParser;
    private final ArchivedBookParser archivedBookParser;

    /**
     * Constructs a {@code LogicManager} with the given {@code Model} and {@code Storage}.
     */
    public LogicManager(Model model, Storage storage) {
        this.model = model;
        this.storage = storage;
        addressBookParser = new AddressBookParser();
        archivedBookParser = new ArchivedBookParser();
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");

//        String[] words = commandText.trim().split("\\s+");
        boolean isArchivedBookCommand = commandText.contains("archived");
        CommandResult commandResult;
        Command command;

        if (isArchivedBookCommand) {
            command = archivedBookParser.parseCommand(commandText);
        } else {
            command = addressBookParser.parseCommand(commandText);
        }

        commandResult = command.execute(model);

        try {
            storage.saveAddressBook(model.getAddressBook());
            storage.saveArchivedBook(model.getArchivedBook());
        } catch (AccessDeniedException e) {
            throw new CommandException(String.format(FILE_OPS_PERMISSION_ERROR_FORMAT, e.getMessage()), e);
        } catch (IOException ioe) {
            throw new CommandException(String.format(FILE_OPS_ERROR_FORMAT, ioe.getMessage()), ioe);
        }

        return commandResult;
    }

    @Override
    public ReadOnlyAddressBook getAddressBook() {
        return model.getAddressBook();
    }

    @Override
    public ObservableList<Person> getFilteredPersonList() {
        return model.getFilteredPersonList();
    }

    @Override
    public ReadOnlyArchivedBook getArchivedBook() {
        return model.getArchivedBook();
    }

    @Override
    public ObservableList<Person> getFilteredArchivedList() {
        return model.getFilteredArchivedList();
    }

    @Override
    public Path getAddressBookFilePath() {
        return model.getAddressBookFilePath();
    }

    @Override
    public Path getArchivedBookFilePath() {
        return model.getArchivedBookFilePath();
    }

    @Override
    public GuiSettings getGuiSettings() {
        return model.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        model.setGuiSettings(guiSettings);
    }
}
