package seedu.teachstack.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static seedu.teachstack.testutil.TypicalArchivedPersons.getTypicalArchivedBook;
import static seedu.teachstack.testutil.TypicalPersons.getTypicalAddressBook;

import java.nio.file.Path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.teachstack.commons.core.GuiSettings;
import seedu.teachstack.model.AddressBook;
import seedu.teachstack.model.ArchivedBook;
import seedu.teachstack.model.ReadOnlyAddressBook;
import seedu.teachstack.model.ReadOnlyArchivedBook;
import seedu.teachstack.model.UserPrefs;

public class StorageManagerTest {

    @TempDir
    public Path testFolder;

    private StorageManager storageManager;

    @BeforeEach
    public void setUp() {
        JsonAddressBookStorage addressBookStorage = new JsonAddressBookStorage(getTempFilePath("ab"));
        JsonArchivedBookStorage archivedBookStorage = new JsonArchivedBookStorage(getTempFilePath("aa"));
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(getTempFilePath("prefs"));
        JsonUserDataStorage userDataStorage = new JsonUserDataStorage(getTempFilePath("data"));
        storageManager = new StorageManager(addressBookStorage, archivedBookStorage, userDataStorage, userPrefsStorage);
    }

    private Path getTempFilePath(String fileName) {
        return testFolder.resolve(fileName);
    }

    @Test
    public void prefsReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link JsonUserPrefsStorage} class.
         * More extensive testing of UserPref saving/reading is done in {@link JsonUserPrefsStorageTest} class.
         */
        UserPrefs original = new UserPrefs();
        original.setGuiSettings(new GuiSettings(300, 600, 4, 6));
        storageManager.saveUserPrefs(original);
        UserPrefs retrieved = storageManager.readUserPrefs().get();
        assertEquals(original, retrieved);
    }

    @Test
    public void addressBookReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link JsonAddressBookStorage} class.
         * More extensive testing of UserPref saving/reading is done in {@link JsonAddressBookStorageTest} class.
         */
        AddressBook original = getTypicalAddressBook();
        storageManager.saveAddressBook(original);
        ReadOnlyAddressBook retrieved = storageManager.readAddressBook().get();
        assertEquals(original, new AddressBook(retrieved));
    }

    @Test
    public void getAddressBookFilePath() {
        assertNotNull(storageManager.getAddressBookFilePath());
    }

    @Test
    public void archivedBookReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link JsonArchivedBookStorage} class.
         * More extensive testing of UserPref saving/reading is done in {@link JsonArchivedBookStorageTest} class.
         */
        ArchivedBook original = getTypicalArchivedBook();
        storageManager.saveArchivedBook(original);
        ReadOnlyArchivedBook retrieved = storageManager.readArchivedBook().get();
        assertEquals(original, new ArchivedBook(retrieved));
    }

    @Test
    public void getArchivedBookFilePath() {
        assertNotNull(storageManager.getArchivedBookFilePath());
    }

    @Test
    public void userDataStorageReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link JsonUserDataStorage} class.
         * More extensive testing of UserPref saving/reading is done in {@link JsonUserDataStorageTest} class.
         */
        UserDataStorage original = new JsonUserDataStorage(storageManager.getUserDataFilePath());
        storageManager.saveUserData();
        JsonSerializableUserData retrieved = storageManager.readUserData().get();
        assertEquals(original.readUserData().get(), retrieved);
    }

    @Test
    public void userDataStorage() {
        assertNotNull(storageManager.getUserDataFilePath());
    }
}
