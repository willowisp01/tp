package seedu.teachstack.storage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.teachstack.model.ModelManager;
import seedu.teachstack.model.person.Grade;
import seedu.teachstack.model.person.Person;

/**
 * A user data file that is serializable to JSON format.
 */
@JsonRootName(value = "userdata")
public class JsonSerializableUserData {
    private static Grade gradeThreshold = Grade.retrieveThreshold();
    private static Predicate<Person> lastRequestedFind = ModelManager.getStartingFilter();
    @JsonProperty("fields")
    private final List<JsonAdaptedField> fields = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableUserData} with the current fields.
     */
    @JsonCreator
    public JsonSerializableUserData() {
        fields.addAll(Arrays.stream(this.getClass().getDeclaredFields())
                .filter(f -> !f.getType().equals(List.class))
                .map(f -> {
                    try {
                        return JsonAdaptedField.createField(f.getName(), f.get(this));
                    } catch (IllegalAccessException iae) {
                        // Should not happen as we are accessing the same class
                        return null;
                    }
                })
                .collect(Collectors.toList()));
    }

    public List<JsonAdaptedField> toFields() {
        return fields;
    }

    public static void setGradeThreshold(Grade grade) {
        gradeThreshold = grade;
    }

    public static void setLastRequestedFind(Predicate<Person> predicate) {
        lastRequestedFind = predicate;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof JsonSerializableUserData)) {
            return false;
        }

        JsonSerializableUserData otherData = (JsonSerializableUserData) other;
        return this.fields.equals(otherData.fields);
    }
}
