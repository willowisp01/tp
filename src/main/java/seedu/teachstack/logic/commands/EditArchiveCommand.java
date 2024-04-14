package seedu.teachstack.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.teachstack.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.teachstack.logic.parser.CliSyntax.PREFIX_GRADE;
import static seedu.teachstack.logic.parser.CliSyntax.PREFIX_GROUP;
import static seedu.teachstack.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.teachstack.logic.parser.CliSyntax.PREFIX_STUDENTID;
import static seedu.teachstack.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.Set;

import seedu.teachstack.commons.util.ToStringBuilder;
import seedu.teachstack.logic.Messages;
import seedu.teachstack.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.teachstack.logic.commands.exceptions.CommandException;
import seedu.teachstack.model.Model;
import seedu.teachstack.model.group.Group;
import seedu.teachstack.model.person.Email;
import seedu.teachstack.model.person.Grade;
import seedu.teachstack.model.person.Name;
import seedu.teachstack.model.person.Person;
import seedu.teachstack.model.person.StudentId;

/**
 * Edits the details of an existing person in the archived book.
 */
public class EditArchiveCommand extends Command {

    public static final String COMMAND_WORD = "edit_archived";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the person identified "
            + "by the student id. "
            + "Parameters: STUDENT_ID (must be in format Axxxxxxx[A-Z] where x can be any digit, "
            + "[A-Z] can be any capital letter) "
            + "[" + PREFIX_STUDENTID + "STUDENTID] "
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_EMAIL + "EMAIL] "
            + "[" + PREFIX_GRADE + "GRADE] "
            + "[" + PREFIX_GROUP + "GROUP]...\n"
            + "Example: " + COMMAND_WORD + " A1234567A "
            + PREFIX_EMAIL + "e1234567@u.nus.edu";

    public static final String MESSAGE_EDIT_ARCHIVED_PERSON_SUCCESS = "Edited Archived Person: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_PERSON =
            "This person already exists in the persons list/archived list. "
            + "Check to make sure StudentID and email are unique.";

    private final StudentId id;
    private final EditPersonDescriptor editPersonDescriptor;

    /**
     * @param id of the person in the archived person list to edit
     * @param editPersonDescriptor details to edit the person with
     */
    public EditArchiveCommand(StudentId id, EditPersonDescriptor editPersonDescriptor) {
        requireNonNull(id);
        requireNonNull(editPersonDescriptor);

        this.id = id;
        this.editPersonDescriptor = new EditPersonDescriptor(editPersonDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        Person personToEdit = model.getArchivedPerson(id);
        if (personToEdit == null) {
            throw new CommandException(Messages.MESSAGE_INVALID_DISPLAYED_STUDENT_ID);
        }

        Person editedPerson = createEditedPerson(personToEdit, editPersonDescriptor);

        if ((!personToEdit.isSameEmail(editedPerson) && model.hasArchivedEmail(editedPerson))
                || !personToEdit.isSameId(editedPerson) && model.hasArchivedId(editedPerson)) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        }

        if ((!personToEdit.isSameEmail(editedPerson) && model.hasEmail(editedPerson))
                || !personToEdit.isSameId(editedPerson) && model.hasId(editedPerson)) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        }

        model.setArchivedPerson(personToEdit, editedPerson);
        model.updateFilteredArchivedList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(String.format(MESSAGE_EDIT_ARCHIVED_PERSON_SUCCESS, Messages.format(editedPerson)));
    }

    /**
     * Creates and returns a {@code Person} with the details of {@code personToEdit}
     * edited with {@code editPersonDescriptor}.
     */
    private static Person createEditedPerson(Person personToEdit, EditPersonDescriptor editPersonDescriptor) {
        assert personToEdit != null;

        Name updatedName = editPersonDescriptor.getName().orElse(personToEdit.getName());

        StudentId updatedStudentId = editPersonDescriptor.getStudentId().orElse(personToEdit.getStudentId());
        Email updatedEmail = editPersonDescriptor.getEmail().orElse(personToEdit.getEmail());
        Grade updatedGrade = editPersonDescriptor.getGrade().orElse(personToEdit.getGrade());
        Set<Group> updatedGroups = editPersonDescriptor.getGroups().orElse(personToEdit.getGroups());

        return new Person(updatedName, updatedStudentId, updatedEmail, updatedGrade, updatedGroups);

    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditArchiveCommand)) {
            return false;
        }

        EditArchiveCommand otherEditArchiveCommand = (EditArchiveCommand) other;
        return id.equals(otherEditArchiveCommand.id)
                && editPersonDescriptor.equals(otherEditArchiveCommand.editPersonDescriptor);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("student id", id)
                .add("editPersonDescriptor", editPersonDescriptor)
                .toString();
    }
}
