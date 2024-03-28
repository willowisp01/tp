package seedu.teachstack.testutil;

import javafx.scene.shape.Arc;
import seedu.teachstack.model.ArchivedBook;
import seedu.teachstack.model.person.Person;

/**
 * A utility class to help with building ArchivedBook objects.
 * Example usage: <br>
 *     {@code ArchivedBook ab = new ArchivedBookBuilder().withPerson("John", "Doe").build();}
 */
public class ArchivedBookBuilder {

    private ArchivedBook archivedBook;

    public ArchivedBookBuilder() {
        archivedBook = new ArchivedBook();
    }

    public ArchivedBookBuilder(ArchivedBook archivedBook) {
        this.archivedBook = archivedBook;
    }

    /**
     * Adds a new {@code Person} to the {@code ArchivedBook} that we are building.
     */
    public ArchivedBookBuilder withPerson(Person person) {
        archivedBook.addPerson(person);
        return this;
    }

    public ArchivedBook build() {
        return archivedBook;
    }
}