package seedu.teachstack.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.teachstack.commons.exceptions.IllegalValueException;
import seedu.teachstack.model.Model;
import seedu.teachstack.model.ModelManager;
import seedu.teachstack.model.group.Group;
import seedu.teachstack.model.person.Person;
import seedu.teachstack.model.person.PersonInGroupPredicate;

/**
 * Jackson-friendly version of the last find request executed.
 */
public class JsonAdaptedLastFind extends JsonAdaptedField {
    private final List<JsonAdaptedGroup> groups = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedLastFind} object with the given groups.
     *
     * @param groups The groups.
     */
    @JsonCreator
    public JsonAdaptedLastFind(@JsonProperty("groups") List<JsonAdaptedGroup> groups) {
        if (!groups.isEmpty()) {
            this.groups.addAll(groups);
        }
    }

    /**
     * Constructs a {@code JsonAdaptedLastFind} object with the given predicate.
     *
     * @param predicate The predicate.
     */
    public JsonAdaptedLastFind(Predicate<Person> predicate) {
        if (predicate instanceof PersonInGroupPredicate) {
            PersonInGroupPredicate lastFind = (PersonInGroupPredicate) predicate;
            this.groups.addAll(lastFind.getGroups().stream()
                    .map(JsonAdaptedGroup::new)
                    .collect(Collectors.toList()));
        }
    }

    @Override
    public void performAction() {
        if (!groups.isEmpty()) {
            try {
                Set<Group> set = new HashSet<>();
                for (JsonAdaptedGroup group : groups) {
                    Group toModelType = group.toModelType();
                    set.add(toModelType);
                }
                ModelManager.setStartingFilter(new PersonInGroupPredicate(set));
            } catch (IllegalValueException ive) {
                ModelManager.setStartingFilter(Model.PREDICATE_SHOW_ALL_PERSONS);
            }
        }
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof JsonAdaptedLastFind)) {
            return false;
        }

        JsonAdaptedLastFind otherFind = (JsonAdaptedLastFind) other;
        return this.groups.equals(otherFind.groups);
    }
}
