package doemon.response;

import doemon.exception.DoemonException;
import doemon.task.Task;
import doemon.task.TaskList;

import java.util.ArrayList;

public class Response {
    /**
     * Text art of Doemon.
     */
    private static final String logo =
            "                       _______________\n" +
                    "                      /  --. --.      \\ \n" +
                    "                     /  | '| ' |   \\   \\ \n" +
                    "                    / /  `-O--'     \\   \\ \n" +
                    "                   |.  --  |  --     |   |\n" +
                    "                   |  --   |  --     |   |\n" +
                    "                    \\  (___|_______) /  /\n" +
                    "                     \\              /  /\n" +
                    "                       |== (t) ===|____";
    /**
     * Introduction string that is printed when Doemon is started.
     */
    private static final String introStr = "Hello I'm\n" + logo + "\t\t\tDoemon!";
    /**
     * String that is printed when Doemon is exited.
     */
    private static final String exitStr = "I'm going to sleep now...See you again soon!";

    /**
     * Returns welcome message.
     *
     * @return Welcome string.
     */
    public static String welcomeString() {
        return introStr;
    }

    /**
     * Returns the specified list of tasks.
     *
     * @param tasks List of tasks to be displayed.
     * @return Task list string.
     */
    public String taskListString(TaskList tasks) {
        if (tasks.getSize() == 0) {
            return  "You have no tasks!";
        }
        StringBuilder listStringBuilder = new StringBuilder("Here is what's on my bread:\n\t");
        for (int i = 1; i <= tasks.getSize(); i++) {
            listStringBuilder.append(i)
                    .append(".")
                    .append(tasks.getTask(i - 1))
                    .append("\n\t");
        }
        return listStringBuilder.toString().trim();
    }

    /**
     * Returns add task message.
     *
     * @param task     Task to be added.
     * @param numTasks New total number of tasks.
     * @return Add task response string.
     */
    public String addTaskString(Task task, int numTasks) {
        return String.format(
                "Alright! I have recorded this task on my bread:\n\t" +
                        "  %s\n\tYou now have %d task(s) recorded on my bread.",
                task.toString(),
                numTasks);
    }

    /**
     * Returns mark task message.
     *
     * @param task Task to be marked.
     * @return Mark task response string.
     */
    public String markTaskString(Task task) {
        return String.format("Yay! This task is now marked as done:\n\t  %s", task);
    }

    /**
     * Returns unmark task message.
     *
     * @param task Task to be unmarked.
     * @return Unmark task response string.
     */
    public String unmarkTaskString(Task task) {
        return String.format("I guess you weren't done with that one:\n\t  %s", task);
    }

    /**
     * Returns delete task message.
     *
     * @param task     Task to be deleted.
     * @param numTasks New total number of tasks.
     * @return Delete task response string.
     */
    public String deleteTaskString(Task task, int numTasks) {
        return String.format("I used a knife to slice off this task from my bread:\n\t  %s"
                + "\n\tThere are %d items left on my bread.", task, numTasks);
    }

    /**
     * Returns all tasks found using the find keyword.
     *
     * @param tasks List of tasks found.
     * @return Found task response string.
     */
    public String foundTasksString(ArrayList<Task> tasks) {
        int taskNum = 1;
        if (tasks.size() == 0) {
            return "I couldn't find any matches on my bread...";
        }
        StringBuilder sb = new StringBuilder("Here are the matches I found on my bread: ");
        for (Task task : tasks) {
            sb.append("\n\t").append(taskNum++).append(".").append(task.toString());
        }
        return sb.toString();
    }

    /**
     * Returns error message.
     *
     * @param e Error to be displayed.
     * @return Error response string.
     */
    public String errorString(DoemonException e) {
        return e.toString();
    }

    /**
     * Returns exit message.
     *
     * @return Exit string.
     */
    public String exitString() {
        return this.exitStr;
    }
}