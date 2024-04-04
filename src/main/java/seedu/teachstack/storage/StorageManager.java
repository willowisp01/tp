package seedu.teachstack.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.teachstack.commons.core.LogsCenter;
import seedu.teachstack.commons.exceptions.DataLoadingException;
import seedu.teachstack.model.ReadOnlyAddressBook;
import seedu.teachstack.model.ReadOnlyArchivedBook;
import seedu.teachstack.model.ReadOnlyUserPrefs;
import seedu.teachstack.model.UserPrefs;

/**
 * Manages storage of AddressBook data in local storage.
 */
public class StorageManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private AddressBookStorage addressBookStorage;
    private ArchivedBookStorage archivedBookStorage;
    private UserDataStorage userDataStorage;
    private UserPrefsStorage userPrefsStorage;

    /**
     * Creates a {@code StorageManager} with the given {@code AddressBookStorage} and {@code UserPrefStorage}.
     */
    public StorageManager(AddressBookStorage addressBookStorage, ArchivedBookStorage archivedBookStorage,
                          UserDataStorage userDataStorage, UserPrefsStorage userPrefsStorage) {
        this.addressBookStorage = addressBookStorage;
        this.archivedBookStorage = archivedBookStorage;
        this.userDataStorage = userDataStorage;
        this.userPrefsStorage = userPrefsStorage;
    }

    // ================ UserPrefs methods ==============================

    @Override
    public Path getUserPrefsFilePath() {
        return userPrefsStorage.getUserPrefsFilePath();
    }

    @Override
    public Optional<UserPrefs> readUserPrefs() throws DataLoadingException {
        return userPrefsStorage.readUserPrefs();
    }

    @Override
    public void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException {
        userPrefsStorage.saveUserPrefs(userPrefs);
    }

    // ================ AddressBook methods ==============================

    @Override
    public Path getAddressBookFilePath() {
        return addressBookStorage.getAddressBookFilePath();
    }

    @Override
    public Optional<ReadOnlyAddressBook> readAddressBook() throws DataLoadingException {
        return readAddressBook(addressBookStorage.getAddressBookFilePath());
    }

    @Override
    public Optional<ReadOnlyAddressBook> readAddressBook(Path filePath) throws DataLoadingException {
        logger.fine("Attempting to read data from file: " + filePath);
        return addressBookStorage.readAddressBook(filePath);
    }

    @Override
    public void saveAddressBook(ReadOnlyAddressBook addressBook) throws IOException {
        saveAddressBook(addressBook, addressBookStorage.getAddressBookFilePath());
    }

    @Override
    public void saveAddressBook(ReadOnlyAddressBook addressBook, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        addressBookStorage.saveAddressBook(addressBook, filePath);
    }

    // ================ ArchivedBook methods ==============================

    @Override
    public Path getArchivedBookFilePath() {
        return archivedBookStorage.getArchivedBookFilePath();
    }

    @Override
    public Optional<ReadOnlyArchivedBook> readArchivedBook() throws DataLoadingException {
        return readArchivedBook(archivedBookStorage.getArchivedBookFilePath());
    }

    @Override
    public Optional<ReadOnlyArchivedBook> readArchivedBook(Path filePath) throws DataLoadingException {
        logger.fine("Attempting to read data from file: " + filePath);
        return archivedBookStorage.readArchivedBook(filePath);
    }

    @Override
    public void saveArchivedBook(ReadOnlyArchivedBook archivedBook) throws IOException {
        saveArchivedBook(archivedBook, archivedBookStorage.getArchivedBookFilePath());
    }

    @Override
    public void saveArchivedBook(ReadOnlyArchivedBook archivedBook, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        archivedBookStorage.saveArchivedBook(archivedBook, filePath);
    }

    // ================ UserData methods ==============================

    @Override
    public Path getUserDataFilePath() {
        return userDataStorage.getUserDataFilePath();
    }

    @Override
    public Optional<JsonSerializableUserData> readUserData() throws DataLoadingException {
        return readUserData(userDataStorage.getUserDataFilePath());
    }

    @Override
    public Optional<JsonSerializableUserData> readUserData(Path filePath) throws DataLoadingException {
        logger.fine("Attempting to read data from file: " + filePath);
        return userDataStorage.readUserData(filePath);
    }

    @Override
    public void saveUserData() throws IOException {
        saveUserData(userDataStorage.getUserDataFilePath());
    }

    @Override
    public void saveUserData(Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        userDataStorage.saveUserData(filePath);
    }
}
