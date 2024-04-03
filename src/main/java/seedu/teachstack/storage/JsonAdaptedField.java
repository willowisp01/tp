package seedu.teachstack.storage;

import java.util.function.Predicate;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import seedu.teachstack.model.person.Person;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME)
@JsonSubTypes({
        @JsonSubTypes.Type(value = JsonAdaptedGradeThreshold.class, name = "gradeThreshold"),
        @JsonSubTypes.Type(value = JsonAdaptedLastFind.class, name = "lastRequestedFind")
})
public abstract class JsonAdaptedField {

    @SuppressWarnings("unchecked")
    public static JsonAdaptedField createField(String type, Object value) {
        switch (type) {
        case "gradeThreshold":
            return new JsonAdaptedGradeThreshold(value.toString()); // value is of type Grade
        case "lastRequestedFind":
            return new JsonAdaptedLastFind((Predicate<Person>) value);
        default:
            // Should never happen
            return null;
        }
    }

    public abstract void performAction();
}
