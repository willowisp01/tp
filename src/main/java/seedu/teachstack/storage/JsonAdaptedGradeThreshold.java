package seedu.teachstack.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.teachstack.model.person.Grade;

/**
 * Jackson-friendly version of the grade threshold field.
 */
public class JsonAdaptedGradeThreshold extends JsonAdaptedField {
    private final String gradeThreshold;

    @JsonCreator
    public JsonAdaptedGradeThreshold(@JsonProperty("gradeThreshold") String grade) {
        gradeThreshold = grade;
    }

    @Override
    public void performAction() {
        Grade.modifyThreshold(new Grade(gradeThreshold));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof JsonAdaptedGradeThreshold)) {
            return false;
        }

        JsonAdaptedGradeThreshold otherThreshold = (JsonAdaptedGradeThreshold) other;
        return this.gradeThreshold.equals(otherThreshold.gradeThreshold);
    }
}
