package seedu.teachstack.logic.commands;

import static seedu.teachstack.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.teachstack.logic.commands.exceptions.CommandException;
import seedu.teachstack.model.Model;
import seedu.teachstack.model.group.Group;
import seedu.teachstack.model.person.Person;
import seedu.teachstack.model.person.StudentId;

/**
 * Randomly put all existing weaker students into groups with the given name.
 */
public class RandomCommand extends Command {

    public static final String COMMAND_WORD = "random";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Randomly put weaker students into the specified number of groups"
            + " with the given group name. "
            + "Existing groups will be preserved.\n"
            + "Parameters: NUMBER_OF_GROUPS (must be a positive integer) "
            + "gp/ [GROUP]\n"
            + "Example: " + COMMAND_WORD + " 3 "
            + "gp/ Consultation Group.";

    public static final String MESSAGE_RANDOM_GROUP_NON_POSITIVE_NUM = "Number of groups must be a positive integer.";

    public static final String MESSAGE_RANDOM_MORE_GROUPS_THAN_STUDENT = "%d existing weaker student(s). "
            + "Number of groups should not be more than %d.";
    public static final String MESSAGE_RANDOM_GROUP_SUCCESS = "Randomly formed %d group(s) with name: %s";

    private final String name;
    private final int numOfGroup;

    /**
     * @param name of the groups to be formed
     * @param numOfGroup to be formed
     */
    public RandomCommand(String name, int numOfGroup) {
        requireAllNonNull(name, numOfGroup);
        this.numOfGroup = numOfGroup;
        this.name = name;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        if (numOfGroup < 1) {
            throw new CommandException(MESSAGE_RANDOM_GROUP_NON_POSITIVE_NUM);
        }
        List<Person> weak = new ArrayList<>();
        weak.addAll(model.getWeak());
        int numWeakStudents = weak.size();
        if (numWeakStudents <= numOfGroup) {
            throw new CommandException(String.format(MESSAGE_RANDOM_MORE_GROUPS_THAN_STUDENT,
                    numWeakStudents, numWeakStudents));
        }

        Collections.shuffle(weak);
        int groupSize = numWeakStudents / numOfGroup;
        int irregularSize = numWeakStudents % numOfGroup;

        int count = 0;
        int currGroup = 1;


        Set<Group> group = null;
        Set<StudentId> member = null;
        for (Person person : weak) {
            if (count == 0) {
                group = new HashSet<>();
                member = new HashSet<>();
                group.add(new Group(name + " " + currGroup));
            }
            member.add(person.getStudentId());
            count++;

            if ((irregularSize == 0 && count == groupSize) || count > groupSize) {
                count = 0;
                currGroup++;
                GroupCommand command = new GroupCommand(group, member);
                command.execute(model);
            } else if (count == groupSize && irregularSize > 0) {
                irregularSize--;
            }
        }

        return new CommandResult(String.format(MESSAGE_RANDOM_GROUP_SUCCESS, numOfGroup, name));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof RandomCommand)) {
            return false;
        }

        RandomCommand e = (RandomCommand) other;
        return name.equals(e.name)
                && numOfGroup == e.numOfGroup;
    }
}
