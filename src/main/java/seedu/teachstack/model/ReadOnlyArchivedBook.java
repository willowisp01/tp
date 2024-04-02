package seedu.teachstack.model;

import javafx.collections.ObservableList;
import seedu.teachstack.model.person.Person;

/**
 * Unmodifiable view of an archived book
 */
public interface ReadOnlyArchivedBook {

    /**
     * Returns an unmodifiable view of the archived list.
     * This list will not contain any duplicate persons.
     */
    ObservableList<Person> getArchivedList();
}
