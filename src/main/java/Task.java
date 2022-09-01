import java.util.ArrayList;

public class Task {
    /**
     * The description of the task.
     */
    private String description;
    /**
     * A boolean indicating if the task is marked as done.
     */
    private boolean isDone;
    /**
     * Stores the task data to be saved.
     */
    public static ArrayList<String> SAVE_DATA = new ArrayList<>();

    /**
     * Constructor for a Task object.
     * @param description the string description of the task
     */
    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    /**
     * Marks the task as done.
     */
    public void mark() {
        isDone = true;
    }

    /**
     * Marks the task as undone.
     */
    public void unmark() {
        isDone = false;
    }

    /**
     * Returns a string used to save the task.
     * @return a string used to save the task
     */
    public String saveString() {
        String isMarked = isDone ? "1" : "0";
        return String.format("%s | %s", isMarked, this.description);
    }

    /**
     * Returns a string representation of this task.
     * @return a string representing this task
     */
    @Override
    public String toString() {
        return String.format("[%s] %s", this.isDone ? "X" : " ", this.description);
    }
}