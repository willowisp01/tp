package seedu.teachstack.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.teachstack.commons.exceptions.DataLoadingException;
import seedu.teachstack.model.ReadOnlyAddressBook;
import seedu.teachstack.model.ReadOnlyArchivedBook;
import seedu.teachstack.model.ReadOnlyUserPrefs;
import seedu.teachstack.model.UserPrefs;

/**
 * API of the Storage component
 */
public interface Storage extends AddressBookStorage, ArchivedBookStorage, UserPrefsStorage, UserDataStorage {

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataLoadingException;

    @Override
    void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException;

    @Override
    Path getAddressBookFilePath();

    @Override
    Optional<ReadOnlyAddressBook> readAddressBook() throws DataLoadingException;

    @Override
    void saveAddressBook(ReadOnlyAddressBook addressBook) throws IOException;

    @Override
    Path getArchivedBookFilePath();

    @Override
    Optional<ReadOnlyArchivedBook> readArchivedBook() throws DataLoadingException;

    @Override
    void saveArchivedBook(ReadOnlyArchivedBook archivedBook) throws IOException;

    @Override
    Path getUserDataFilePath();

    @Override
    Optional<JsonSerializableUserData> readUserData() throws DataLoadingException;

    @Override
    void saveUserData() throws IOException;
}
