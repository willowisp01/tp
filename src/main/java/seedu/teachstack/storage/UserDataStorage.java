package seedu.teachstack.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.teachstack.commons.exceptions.DataLoadingException;

/**
 * Represents a storage for user-related fields.
 */
public interface UserDataStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getUserDataFilePath();

    /**
     * Reads user data.
     *
     * @throws DataLoadingException if loading the data from storage failed.
     */
    Optional<JsonSerializableUserData> readUserData() throws DataLoadingException;

    /**
     * @see #getUserDataFilePath()
     */
    Optional<JsonSerializableUserData> readUserData(Path filePath) throws DataLoadingException;

    /**
     * Saves the user data to the storage.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveUserData() throws IOException;

    /**
     * @see #saveUserData()
     */
    void saveUserData(Path filePath) throws IOException;
}
