package seedu.teachstack.storage;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.teachstack.commons.core.LogsCenter;
import seedu.teachstack.commons.exceptions.DataLoadingException;
import seedu.teachstack.commons.exceptions.IllegalValueException;
import seedu.teachstack.commons.util.FileUtil;
import seedu.teachstack.commons.util.JsonUtil;
import seedu.teachstack.model.ReadOnlyArchivedBook;

/**
 * A class to access ArchivedBook data stored as a json file on the hard disk.
 */
public class JsonArchivedBookStorage implements ArchivedBookStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonArchivedBookStorage.class);

    private Path filePath;

    public JsonArchivedBookStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getArchivedBookFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyArchivedBook> readArchivedBook() throws DataLoadingException {
        return readArchivedBook(filePath);
    }

    /**
     * Similar to {@link #readArchivedBook()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataLoadingException if loading the data from storage failed.
     */
    public Optional<ReadOnlyArchivedBook> readArchivedBook(Path filePath) throws DataLoadingException {
        requireNonNull(filePath);

        Optional<JsonSerializableArchivedBook> jsonArchivedBook = JsonUtil.readJsonFile(
                filePath, JsonSerializableArchivedBook.class);
        if (!jsonArchivedBook.isPresent()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonArchivedBook.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataLoadingException(ive);
        }
    }

    @Override
    public void saveArchivedBook(ReadOnlyArchivedBook archivedBook) throws IOException {
        saveArchivedBook(archivedBook, filePath);
    }

    /**
     * Similar to {@link #saveArchivedBook(ReadOnlyArchivedBook)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    public void saveArchivedBook(ReadOnlyArchivedBook archivedBook, Path filePath) throws IOException {
        requireNonNull(archivedBook);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableArchivedBook(archivedBook), filePath);
    }

}
