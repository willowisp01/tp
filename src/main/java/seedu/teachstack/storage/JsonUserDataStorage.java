package seedu.teachstack.storage;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.teachstack.commons.core.LogsCenter;
import seedu.teachstack.commons.exceptions.DataLoadingException;
import seedu.teachstack.commons.util.FileUtil;
import seedu.teachstack.commons.util.JsonUtil;

/**
 * A class to access user data stored as a json file on the hard disk.
 */
public class JsonUserDataStorage implements UserDataStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonUserDataStorage.class);

    private Path filePath;

    public JsonUserDataStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getUserDataFilePath() {
        return filePath;
    }

    @Override
    public Optional<JsonSerializableUserData> readUserData() throws DataLoadingException {
        return readUserData(filePath);
    }

    /**
     * Similar to {@link #readUserData()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataLoadingException if loading the data from storage failed.
     */
    public Optional<JsonSerializableUserData> readUserData(Path filePath) throws DataLoadingException {
        requireNonNull(filePath);

        Optional<JsonSerializableUserData> jsonUserData = JsonUtil.readJsonFile(
                filePath, JsonSerializableUserData.class);
        if (!jsonUserData.isPresent()) {
            return Optional.empty();
        }

        setFields(jsonUserData.get());
        return Optional.of(jsonUserData.get());
    }

    @Override
    public void saveUserData() throws IOException {
        saveUserData(filePath);
    }

    /**
     * Similar to {@link #saveUserData()}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    public void saveUserData(Path filePath) throws IOException {
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableUserData(), filePath);
    }

    public void setFields(JsonSerializableUserData data) {
        for (JsonAdaptedField field : data.toFields()) {
            field.performAction();
        }
    }
}
