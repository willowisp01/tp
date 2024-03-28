package seedu.teachstack.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static seedu.teachstack.testutil.Assert.assertThrows;
import static seedu.teachstack.testutil.TypicalArchivedPersons.getTypicalArchivedBook;
import static seedu.teachstack.testutil.TypicalPersons.ALICE;
import static seedu.teachstack.testutil.TypicalPersons.HOON;
import static seedu.teachstack.testutil.TypicalPersons.IDA;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.teachstack.commons.exceptions.DataLoadingException;
import seedu.teachstack.model.ArchivedBook;
import seedu.teachstack.model.ReadOnlyArchivedBook;

public class JsonArchivedBookStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonAddressBookStorageTest");

    @TempDir
    public Path testFolder;

    @Test
    public void readArchivedBook_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> readArchivedBook(null));
    }

    private java.util.Optional<ReadOnlyArchivedBook> readArchivedBook(String filePath) throws Exception {
        return new JsonArchivedBookStorage(Paths.get(filePath)).readArchivedBook(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readArchivedBook("NonExistentFile.json").isPresent());
    }

    @Test
    public void read_notJsonFormat_exceptionThrown() {
        assertThrows(DataLoadingException.class, () -> readArchivedBook("notJsonFormatAddressBook.json"));
    }

    @Test
    public void readArchivedBook_invalidPersonArchivedBook_throwDataLoadingException() {
        assertThrows(DataLoadingException.class, () -> readArchivedBook("invalidPersonAddressBook.json"));
    }

    @Test
    public void readArchivedBook_invalidAndValidPersonArchivedBook_throwDataLoadingException() {
        assertThrows(DataLoadingException.class, () -> readArchivedBook("invalidAndValidPersonAddressBook.json"));
    }

    @Test
    public void readAndSaveArchivedBook_allInOrder_success() throws Exception {
        Path filePath = testFolder.resolve("TempAddressBook.json");
        ArchivedBook original = getTypicalArchivedBook();
        JsonArchivedBookStorage jsonArchivedBookStorage = new JsonArchivedBookStorage(filePath);

        // Save in new file and read back
        jsonArchivedBookStorage.saveArchivedBook(original, filePath);
        ReadOnlyArchivedBook readBack = jsonArchivedBookStorage.readArchivedBook(filePath).get();
        assertEquals(original, new ArchivedBook(readBack));

        // Modify data, overwrite exiting file, and read back
        original.addPerson(HOON);
        original.removePerson(ALICE);
        jsonArchivedBookStorage.saveArchivedBook(original, filePath);
        readBack = jsonArchivedBookStorage.readArchivedBook(filePath).get();
        assertEquals(original, new ArchivedBook(readBack));

        // Save and read without specifying file path
        original.addPerson(IDA);
        jsonArchivedBookStorage.saveArchivedBook(original); // file path not specified
        readBack = jsonArchivedBookStorage.readArchivedBook().get(); // file path not specified
        assertEquals(original, new ArchivedBook(readBack));

    }

    @Test
    public void saveArchivedBook_nullArchivedBook_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveArchivedBook(null, "SomeFile.json"));
    }

    /**
     * Saves {@code archivedBook} at the specified {@code filePath}.
     */
    private void saveArchivedBook(ReadOnlyArchivedBook archivedBook, String filePath) {
        try {
            new JsonArchivedBookStorage(Paths.get(filePath))
                    .saveArchivedBook(archivedBook, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveArchivedBook_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveArchivedBook(new ArchivedBook(), null));
    }
}
