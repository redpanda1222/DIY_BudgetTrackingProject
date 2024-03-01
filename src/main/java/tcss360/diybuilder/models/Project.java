/*
 * Team Periwinkle
 */
package tcss360.diybuilder.models;

import tcss360.diybuilder.SystemControl.ProjectController;

import java.util.ArrayList;

/**
 * Project Object class.
 *
 * @author Mey Vo
 * @author Soe Lin
 */
public class Project {
    /** The name of the Project. */
    private String name;
    /** The estimated budget of the Project. */
    private double budget;
    /** The description of the Project. */
    private String description;
    /** Array list of Task Object. */
    private ArrayList<Task> taskList = new ArrayList<>();

    private String note = "";


    /**
     * Constructor.
     *
     * @param name project name
     * @param budget project budget
     * @param description project description
     */
    public Project(String name, double budget, String description, ArrayList<Task> theTaskList) {
        this.name = name;
        this.budget = budget;
        this.description = description;
        taskList = theTaskList;
    }

    /**
     * Another constructor.
     *
     * @param name project name
     * @param budget project budget
     * @param description project description
     */
    public Project(String name, double budget, String description) {
        this.name = name;
        this.budget = budget;
        this.description = description;
    }


    public Project() {

    }

    /**
     * Return the budget of the Project.
     *
     * @return budget
     */
    public double getBudget() {
        return budget;
    }

    /**
     * Return the description of the Project.
     *
     * @return description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Set the name of the Project.
     *
     * @param name project name
     */
    public void setTitle(String name) {
        this.name = name;
    }

    /**
     * Return the name of the Project.
     *
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Return the ArrayList of task of the Project.
     *
     * @return Task ArrayList
     */
    public ArrayList<Task> getTaskList() {
        return taskList;
    }

    /**
     * used to get a specific project Object
     *
     * @param taskName task name
     * @return task object
     */
    public Task getTask(String taskName){
        for (Task task: taskList) {
            if(task.getName().equals(taskName)){
                return task;
            }
        }

        //change this later
        return new Task("");
    }

    /**
     * Adding task to the ArrayList and adding to JSON file.
     *
     * @param theTask task object
     */
    public void addTask(Task theTask) {
        taskList.add(theTask);

        // code to add for the JSON file
        ProjectController.createTask(theTask.getName());
    }

    /**
     * Deleting task from the ArrayList and deleting from the JSON file.
     *
     * @param index index
     */
    public void deleteTask(int index) {
        // code to add for the JSON file
        ProjectController.deleteTask(taskList.get(index).getName());

        taskList.remove(index);
    }

    public String getNote(){
        if(note.isEmpty()){
            note = ProjectController.findNote();
        }
        return note;}

    public void setNote(String newNote){
        this.note = newNote;
        ProjectController.saveNote(newNote);
    }
}
