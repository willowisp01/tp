package seedu.teachstack.model.person;

import java.util.List;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.teachstack.commons.util.StringUtil;

/**
 * Predicate to filter archived persons.
 */
public class ArchivedPersonPredicate implements Predicate<Person> {

    private final ObservableList<Person> archivedPerson;

    public ArchivedPersonPredicate(ObservableList<Person> archivedPerson) {
        this.archivedPerson = archivedPerson;

    }

    @Override
    public boolean test(Person person) {
        return person.isArchivedPerson();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        return other instanceof ArchivedPersonPredicate;
    }
}
