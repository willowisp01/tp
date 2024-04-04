package seedu.teachstack.model;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableList;
import seedu.teachstack.commons.util.ToStringBuilder;
import seedu.teachstack.model.person.Person;
import seedu.teachstack.model.person.UniquePersonList;

/**
 * Wraps all data at the archived-book level
 * Duplicates are not allowed (by .isSamePerson comparison)
 */
public class ArchivedBook implements ReadOnlyArchivedBook {
    private final UniquePersonList archivedPersons;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        archivedPersons = new UniquePersonList();
    }

    public ArchivedBook() {}

    /**
     * Creates an ArchivedBook using the Persons in the {@code toBeCopied}
     */
    public ArchivedBook(ReadOnlyArchivedBook toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the archived list with {@code persons}.
     * {@code persons} must not contain duplicate persons.
     */
    public void setPersons(List<Person> persons) {
        this.archivedPersons.setPersons(persons);
    }


    /**
     * Resets the existing data of this {@code ArchivedBook} with {@code newData}.
     */
    public void resetData(ReadOnlyArchivedBook newData) {
        requireNonNull(newData);
        setPersons(newData.getArchivedList());
    }

    //// person-level operations

    /**
     * Returns true if a person with the same identity as {@code person} exists in the archived book.
     */
    public boolean hasPerson(Person person) {
        requireNonNull(person);
        return archivedPersons.contains(person);
    }

    /**
     * Adds a person to the archived book.
     * The person must not already exist in the archived book.
     */
    public void addPerson(Person p) {
        archivedPersons.addToArchive(p);
        archivedPersons.sort();
    }

    /**
     * Replaces the given person {@code target} in the list with {@code editedPerson}.
     * {@code target} must exist in the archived book.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the archived book.
     */
    public void setPerson(Person target, Person editedPerson) {
        requireNonNull(editedPerson);
        archivedPersons.setPerson(target, editedPerson);
    }

    /**
     * Removes {@code key} from this {@code ArchivedBook}.
     * {@code key} must exist in the archived book.
     */
    public void removePerson(Person key) {
        archivedPersons.remove(key);
    }

    /**
     * Returns true if a person with the same {@code StudentId} as {@code person} exists in the archived book.
     */
    public boolean hasArchivedId(Person person) {
        requireNonNull(person);
        return archivedPersons.containsById(person);
    }

    /**
     * Returns true if a person with the same {@code Email} as {@code person} exists in the archived book.
     */
    public boolean hasArchivedEmail(Person person) {
        requireNonNull(person);
        return archivedPersons.containsByEmail(person);
    }


    //// util methods

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("persons", archivedPersons)
                .toString();
    }

    @Override
    public ObservableList<Person> getArchivedList() {
        return archivedPersons.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ArchivedBook)) {
            return false;
        }

        ArchivedBook otherArchivedBook = (ArchivedBook) other;
        return archivedPersons.equals(otherArchivedBook.archivedPersons);
    }

    @Override
    public int hashCode() {
        return archivedPersons.hashCode();
    }

    /**
     * Sort the Persons in UniquePersonList.
     */
    public void sort() {
        archivedPersons.sort();
    }
}
