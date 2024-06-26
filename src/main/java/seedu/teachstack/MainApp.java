package seedu.teachstack;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import javafx.application.Application;
import javafx.stage.Stage;
import seedu.teachstack.commons.core.Config;
import seedu.teachstack.commons.core.LogsCenter;
import seedu.teachstack.commons.core.Version;
import seedu.teachstack.commons.exceptions.DataLoadingException;
import seedu.teachstack.commons.util.ConfigUtil;
import seedu.teachstack.commons.util.StringUtil;
import seedu.teachstack.logic.Logic;
import seedu.teachstack.logic.LogicManager;
import seedu.teachstack.model.AddressBook;
import seedu.teachstack.model.ArchivedBook;
import seedu.teachstack.model.Model;
import seedu.teachstack.model.ModelManager;
import seedu.teachstack.model.ReadOnlyAddressBook;
import seedu.teachstack.model.ReadOnlyArchivedBook;
import seedu.teachstack.model.ReadOnlyUserPrefs;
import seedu.teachstack.model.UserPrefs;
import seedu.teachstack.model.util.SampleDataUtil;
import seedu.teachstack.storage.AddressBookStorage;
import seedu.teachstack.storage.ArchivedBookStorage;
import seedu.teachstack.storage.JsonAddressBookStorage;
import seedu.teachstack.storage.JsonArchivedBookStorage;
import seedu.teachstack.storage.JsonSerializableUserData;
import seedu.teachstack.storage.JsonUserDataStorage;
import seedu.teachstack.storage.JsonUserPrefsStorage;
import seedu.teachstack.storage.Storage;
import seedu.teachstack.storage.StorageManager;
import seedu.teachstack.storage.UserDataStorage;
import seedu.teachstack.storage.UserPrefsStorage;
import seedu.teachstack.ui.Ui;
import seedu.teachstack.ui.UiManager;

/**
 * Runs the application.
 */
public class MainApp extends Application {

    public static final Version VERSION = new Version(1, 3, 0, true);

    private static final Logger logger = LogsCenter.getLogger(MainApp.class);

    protected Ui ui;
    protected Logic logic;
    protected Storage storage;
    protected Model model;
    protected Config config;

    @Override
    public void init() throws Exception {
        logger.info("=============================[ Initializing TeachStack ]===========================");
        super.init();

        AppParameters appParameters = AppParameters.parse(getParameters());
        config = initConfig(appParameters.getConfigPath());
        initLogging(config);

        UserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(config.getUserPrefsFilePath());
        UserPrefs userPrefs = initPrefs(userPrefsStorage);
        AddressBookStorage addressBookStorage = new JsonAddressBookStorage(userPrefs.getAddressBookFilePath());
        ArchivedBookStorage archivedBookStorage = new JsonArchivedBookStorage(userPrefs.getArchivedBookFilePath());
        UserDataStorage userDataStorage = new JsonUserDataStorage(userPrefs.getUserDataFilePath());
        storage = new StorageManager(addressBookStorage, archivedBookStorage, userDataStorage, userPrefsStorage);

        initUserData(storage);

        model = initModelManager(storage, userPrefs);

        logic = new LogicManager(model, storage);

        ui = new UiManager(logic);
    }

    private void initUserData(Storage storage) {
        logger.info("Using user data file : " + storage.getUserDataFilePath());

        Optional<JsonSerializableUserData> userDataOptional;

        try {
            userDataOptional = storage.readUserData();
            if (!userDataOptional.isPresent()) {
                logger.info("Creating a new data file " + storage.getAddressBookFilePath()
                        + " with default values.");
            }
        } catch (DataLoadingException e) {
            logger.warning("Data file at " + storage.getUserDataFilePath() + " could not be loaded.");
        }
    }

    /**
     * Returns a {@code ModelManager} with the data from {@code storage}'s address book and {@code userPrefs}. <br>
     * The data from the sample address book will be used instead if {@code storage}'s address book is not found,
     * or an empty address book will be used instead if errors occur when reading {@code storage}'s address book.
     */
    private Model initModelManager(Storage storage, ReadOnlyUserPrefs userPrefs) {
        logger.info("Using data file : " + storage.getAddressBookFilePath());
        logger.info("Using archive file : " + storage.getArchivedBookFilePath());

        Optional<ReadOnlyAddressBook> addressBookOptional;
        ReadOnlyAddressBook initialData;
        Optional<ReadOnlyArchivedBook> archivedBookOptional;
        ReadOnlyArchivedBook initialData2;
        try {
            addressBookOptional = storage.readAddressBook();
            archivedBookOptional = storage.readArchivedBook();
            if (!addressBookOptional.isPresent()) {
                logger.info("Creating a new data file " + storage.getAddressBookFilePath()
                        + " populated with sample TeachStack data.");
            }
            initialData = addressBookOptional.orElseGet(SampleDataUtil::getSampleAddressBook);
            initialData2 = archivedBookOptional.orElseGet(ArchivedBook::new);
        } catch (DataLoadingException e) {
            logger.warning("Data file at " + storage.getAddressBookFilePath() + " could not be loaded."
                    + " Will be starting with an empty TeachStack.");
            initialData = new AddressBook();
            initialData2 = new ArchivedBook();
        }

        return new ModelManager(initialData, initialData2, userPrefs);
    }

    private void initLogging(Config config) {
        LogsCenter.init(config);
    }

    /**
     * Returns a {@code Config} using the file at {@code configFilePath}. <br>
     * The default file path {@code Config#DEFAULT_CONFIG_FILE} will be used instead
     * if {@code configFilePath} is null.
     */
    protected Config initConfig(Path configFilePath) {
        Config initializedConfig;
        Path configFilePathUsed;

        configFilePathUsed = Config.DEFAULT_CONFIG_FILE;

        if (configFilePath != null) {
            logger.info("Custom Config file specified " + configFilePath);
            configFilePathUsed = configFilePath;
        }

        logger.info("Using config file : " + configFilePathUsed);

        try {
            Optional<Config> configOptional = ConfigUtil.readConfig(configFilePathUsed);
            if (!configOptional.isPresent()) {
                logger.info("Creating new config file " + configFilePathUsed);
            }
            initializedConfig = configOptional.orElse(new Config());
        } catch (DataLoadingException e) {
            logger.warning("Config file at " + configFilePathUsed + " could not be loaded."
                    + " Using default config properties.");
            initializedConfig = new Config();
        }

        //Update config file in case it was missing to begin with or there are new/unused fields
        try {
            ConfigUtil.saveConfig(initializedConfig, configFilePathUsed);
        } catch (IOException e) {
            logger.warning("Failed to save config file : " + StringUtil.getDetails(e));
        }
        return initializedConfig;
    }

    /**
     * Returns a {@code UserPrefs} using the file at {@code storage}'s user prefs file path,
     * or a new {@code UserPrefs} with default configuration if errors occur when
     * reading from the file.
     */
    protected UserPrefs initPrefs(UserPrefsStorage storage) {
        Path prefsFilePath = storage.getUserPrefsFilePath();
        logger.info("Using preference file : " + prefsFilePath);

        UserPrefs initializedPrefs;
        try {
            Optional<UserPrefs> prefsOptional = storage.readUserPrefs();
            if (!prefsOptional.isPresent()) {
                logger.info("Creating new preference file " + prefsFilePath);
            }
            initializedPrefs = prefsOptional.orElse(new UserPrefs());
        } catch (DataLoadingException e) {
            logger.warning("Preference file at " + prefsFilePath + " could not be loaded."
                    + " Using default preferences.");
            initializedPrefs = new UserPrefs();
        }

        //Update prefs file in case it was missing to begin with or there are new/unused fields
        try {
            storage.saveUserPrefs(initializedPrefs);
        } catch (IOException e) {
            logger.warning("Failed to save config file : " + StringUtil.getDetails(e));
        }

        return initializedPrefs;
    }

    @Override
    public void start(Stage primaryStage) {
        logger.info("Starting TeachStack " + MainApp.VERSION);
        ui.start(primaryStage);
    }

    @Override
    public void stop() {
        logger.info("============================ [ Stopping TeachStack ] =============================");
        try {
            storage.saveUserPrefs(model.getUserPrefs());
        } catch (IOException e) {
            logger.severe("Failed to save preferences " + StringUtil.getDetails(e));
        }
    }
}
