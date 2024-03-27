package seedu.teachstack.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.teachstack.commons.exceptions.IllegalValueException;
import seedu.teachstack.model.ArchivedBook;
import seedu.teachstack.model.ReadOnlyArchivedBook;
import seedu.teachstack.model.person.Person;

/**
 * An Immutable ArchivedBook that is serializable to JSON format.
 */
@JsonRootName(value = "archivedbook")
class JsonSerializableArchivedBook {

    public static final String MESSAGE_DUPLICATE_PERSON = "Archived list contains duplicate person(s).";

    private final List<JsonAdaptedPerson> persons = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableArchivedBook} with the given persons.
     */
    @JsonCreator
    public JsonSerializableArchivedBook(@JsonProperty("persons") List<JsonAdaptedPerson> persons) {
        this.persons.addAll(persons);
    }

    /**
     * Converts a given {@code ReadOnlyArchivedBook} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableArchivedBook}.
     */
    public JsonSerializableArchivedBook(ReadOnlyArchivedBook source) {
        persons.addAll(source.getArchivedList().stream().map(JsonAdaptedPerson::new).collect(Collectors.toList()));
    }

    /**
     * Converts this archived book into the model's {@code ArchivedBook} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public ArchivedBook toModelType() throws IllegalValueException {
        ArchivedBook archivedBook = new ArchivedBook();
        for (JsonAdaptedPerson jsonAdaptedPerson : persons) {
            Person person = jsonAdaptedPerson.toModelType();
            if (archivedBook.hasPerson(person)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_PERSON);
            }
            archivedBook.addPerson(person);
        }
        return archivedBook;
    }

}
