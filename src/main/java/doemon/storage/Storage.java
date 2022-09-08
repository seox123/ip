package doemon.storage;

import doemon.exception.TaskDataException;
import doemon.task.Deadline;
import doemon.task.Event;
import doemon.task.Task;
import doemon.task.Todo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Storage {

    private String filePath;
    private ArrayList<String> taskSaveStrings;

    public Storage(String filePath) {
        this.filePath = filePath;
        this.taskSaveStrings = new ArrayList<>();
    }

    public void addTaskData(Task task) throws TaskDataException {
        this.taskSaveStrings.add(task.saveString());
        saveTasks();
    }

    public void markTaskData(int taskIndex) throws TaskDataException {
        this.taskSaveStrings.set(
                taskIndex,
                this.taskSaveStrings.get(taskIndex).replaceFirst("0", "1"));
        saveTasks();
    }

    public void unmarkTaskData(int taskIndex) throws TaskDataException {
        this.taskSaveStrings.set(
                taskIndex,
                this.taskSaveStrings.get(taskIndex).replaceFirst("1", "0"));
        saveTasks();
    }

    public void deleteTaskData(int taskIndex) throws TaskDataException {
        this.taskSaveStrings.remove(taskIndex);
        saveTasks();
    }

    public ArrayList<Task> loadTasks() throws TaskDataException {
        ArrayList<Task> loadedTasks = new ArrayList<>();

        // Check and create data directory if necessary
        File directory = new File("./data");
        if (!directory.exists()) {
            directory.mkdir();
        }

        BufferedReader br = null;
        try {
            File taskDataFile = new File(this.filePath);
            if (taskDataFile.exists()) {
                br = new BufferedReader(new FileReader(taskDataFile));
                String taskData = br.readLine();
                while (taskData != null) {
                    String[] taskDataArr = taskData.split(" \\| ");
                    String taskType = taskDataArr[0];
                    boolean isMarked = taskDataArr[1].equals("1");
                    String taskDescription = taskDataArr[2];
                    if (taskType.equals("T")) {
                        Task task = new Todo(taskDescription);
                        if (isMarked) {
                            task.mark();
                        }
                        loadedTasks.add(task);
                    } else if (taskType.equals("D")) {
                        Task task = new Deadline(taskDescription, taskDataArr[3]);
                        if (isMarked) {
                            task.mark();
                        }
                        loadedTasks.add(task);
                    } else {
                        Task task = new Event(taskDescription, taskDataArr[3]);
                        if (isMarked) {
                            task.mark();
                        }
                        loadedTasks.add(task);
                    }
                    this.taskSaveStrings.add(taskData);
                    taskData = br.readLine();
                }
            } else {
                taskDataFile.createNewFile();
            }
        } catch (IOException e) {
            throw new TaskDataException();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    throw new TaskDataException();
                }
            }
        }
        return loadedTasks;
    }

    private void saveTasks() throws TaskDataException {
        BufferedWriter bw = null;
        try {
            bw = new BufferedWriter(new FileWriter(new File(this.filePath)));
            StringBuilder toWrite = new StringBuilder();
            for (int i = 0; i < this.taskSaveStrings.size(); i++) {
                toWrite.append(this.taskSaveStrings.get(i));
                if (i < this.taskSaveStrings.size() - 1) {
                    toWrite.append('\n');
                }
            }
            bw.write(toWrite.toString());
        } catch (IOException e) {
            throw new TaskDataException();
        } finally {
            try {
                bw.close();
            } catch (IOException e) {
                throw new TaskDataException();
            }
        }
    }
}