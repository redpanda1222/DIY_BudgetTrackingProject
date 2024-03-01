package tcss360.diybuilder;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static tcss360.diybuilder.SystemControl.UserController.*;

import org.junit.Test;
import tcss360.diybuilder.SystemControl.Controller;
import tcss360.diybuilder.SystemControl.*;
import tcss360.diybuilder.models.*;

import java.util.ArrayList;
/**
 * Unit test for creating a project
 * @author Charmel Mbala
 */


public class CreateTest {
    private Controller c;
    //private ProjectController projectController;
    private String username;
    private String password;
    private String projectName;
    private double budget;
    private String description;
    private Project createTheProject;

    /**
     * Test the creation of a Project
     */
    @Test
    public void createProject(){
        c = new Controller();
        username = "a6";
        password = "a6";
        loadUserData();

        UserController.loadUserAccount(username);

        projectName = "Bathroom remodel";
        budget = 12000.0;
        description = "new bathroom remodel.";

        createTheProject = new Project(projectName, budget,description);

        // Create the project
        ProjectController.createProject(username, createTheProject);

        // Load the Project
        ProjectController.loadProject(projectName);
        ArrayList<Project> projects = ProjectController.readProjects(username);

        // Assert that the project name has been created and matches
        boolean projectFound = false;
        for (Project project : projects) {
            if (project.getName().equals(projectName)) {
                projectFound = true;
                break;
            }
        }
        assertTrue(projectFound);
    }


    /**
     * Test the creation of a task
     */
    @Test
    public void createTask(){
        c = new Controller();
        username = "a6";
        password = "a6";
        loadUserData();

        UserController.loadUserAccount(username);

        //Initialize Project details
        projectName = "Bathroom remodel";
        budget = 12000.0;
        description = "new bathroom remodel.";
        String taskName1 = "Bathroom tiles";
        String taskName2 = "Shower Head fix";
        createTheProject = new Project(projectName, budget,description);

        // Create the project
        ProjectController.createProject(username, createTheProject);

        // Load the Project
        ProjectController.loadProject(projectName);

        //Create and load tasks
        ProjectController.createTask( taskName1);
        ProjectController.loadTask( taskName1);

        ProjectController.createTask(taskName2);
        ProjectController.loadTask(taskName2);

        ArrayList<Project> projects = ProjectController.readProjects(username);

        // Assert that the project name has been created and matches
        boolean projectFound = false;
        for (Project project : projects) {
            if (project.getName().equals(projectName)) {
                projectFound = true;

                // Check the tasks of the project
                ArrayList<Task> tasks = project.getTaskList();
                boolean taskFound = false;
                for (Task task : tasks) {
                    if (task.getName().equals( taskName1) ||task.getName().equals( taskName2) ) {
                        taskFound = true;
                        break;
                    }
                }
                assertTrue(taskFound);

                break;
            }
        }
        assertTrue(projectFound);








    }
}
