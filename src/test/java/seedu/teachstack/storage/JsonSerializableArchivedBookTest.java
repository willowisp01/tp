package seedu.teachstack.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.teachstack.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import seedu.teachstack.commons.exceptions.IllegalValueException;
import seedu.teachstack.commons.util.JsonUtil;
import seedu.teachstack.model.ArchivedBook;
import seedu.teachstack.testutil.TypicalArchivedPersons;

public class JsonSerializableArchivedBookTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonSerializableAddressBookTest");
    private static final Path TYPICAL_PERSONS_FILE = TEST_DATA_FOLDER.resolve("typicalPersonsArchivedBook.json");
    private static final Path INVALID_PERSON_FILE = TEST_DATA_FOLDER.resolve("invalidPersonAddressBook.json");
    private static final Path DUPLICATE_PERSON_FILE = TEST_DATA_FOLDER.resolve("duplicatePersonAddressBook.json");

    @Test
    public void toModelType_typicalArchivedPersonsFile_success() throws Exception {
        JsonSerializableArchivedBook dataFromFile = JsonUtil.readJsonFile(TYPICAL_PERSONS_FILE,
                JsonSerializableArchivedBook.class).get();
        ArchivedBook archivedBookFromFile = dataFromFile.toModelType();
        ArchivedBook typicalPersonsAddressBook = TypicalArchivedPersons.getTypicalArchivedBook();
        assertEquals(archivedBookFromFile, typicalPersonsAddressBook);
    }

    @Test
    public void toModelType_invalidArchivedPersonFile_throwsIllegalValueException() throws Exception {
        JsonSerializableArchivedBook dataFromFile = JsonUtil.readJsonFile(INVALID_PERSON_FILE,
                JsonSerializableArchivedBook.class).get();
        assertThrows(IllegalValueException.class, dataFromFile::toModelType);
    }

    @Test
    public void toModelType_duplicateArchivedPersons_throwsIllegalValueException() throws Exception {
        JsonSerializableArchivedBook dataFromFile = JsonUtil.readJsonFile(DUPLICATE_PERSON_FILE,
                JsonSerializableArchivedBook.class).get();
        assertThrows(IllegalValueException.class, JsonSerializableArchivedBook.MESSAGE_DUPLICATE_PERSON,
                dataFromFile::toModelType);
    }

}
