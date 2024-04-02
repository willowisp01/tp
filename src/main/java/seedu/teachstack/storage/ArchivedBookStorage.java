package seedu.teachstack.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.teachstack.commons.exceptions.DataLoadingException;
import seedu.teachstack.model.ReadOnlyArchivedBook;

/**
 * Represents a storage for {@link seedu.teachstack.model.ArchivedBook}.
 */
public interface ArchivedBookStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getArchivedBookFilePath();

    /**
     * Returns Archived data as a {@link ReadOnlyArchivedBook}.
     * Returns {@code Optional.empty()} if storage file is not found.
     *
     * @throws DataLoadingException if loading the data from storage failed.
     */
    Optional<ReadOnlyArchivedBook> readArchivedBook() throws DataLoadingException;

    /**
     * @see #getArchivedBookFilePath()
     */
    Optional<ReadOnlyArchivedBook> readArchivedBook(Path filePath) throws DataLoadingException;

    /**
     * Saves the given {@link ReadOnlyArchivedBook} to the storage.
     * @param archivedBook cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveArchivedBook(ReadOnlyArchivedBook archivedBook) throws IOException;

    /**
     * @see #saveArchivedBook(ReadOnlyArchivedBook)
     */
    void saveArchivedBook(ReadOnlyArchivedBook archivedBook, Path filePath) throws IOException;

}
