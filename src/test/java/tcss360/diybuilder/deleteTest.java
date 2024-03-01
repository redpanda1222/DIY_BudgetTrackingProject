package tcss360.diybuilder;

import org.junit.Test;
import tcss360.diybuilder.SystemControl.Controller;
import tcss360.diybuilder.SystemControl.ProjectController;
import tcss360.diybuilder.SystemControl.UserController;

import static org.junit.Assert.*;

/**
 * Unit test for deleting project components
 *  Warning: user must be created beforehand along with project, task and item
 *  Warning: when running delete, item, then task, then project or will run into errors
 */

public class deleteTest {
    /**
     * delete data from json file
     *  @author Mahiliet Awasso
     */
    @Test
    public void deleteItem() {
        Controller c = new Controller();
        UserController.loadUserData();
        String username = "mey1";
        UserController.loadUserAccount(username);

        // Items and projects to be deleted for testing
        String projectName = "livingroom ";
        String taskName = "painting";
        String itemName = "ink";

        ProjectController.loadProject(projectName);
        ProjectController.loadTask(taskName);

        // Delete the item
        try {
            ProjectController.deleteItem(itemName);
        } catch (Exception e) {
            fail("Item deletion failed: " + e.getMessage());
        }
    }

    @Test
    public void deleteTask() {
        Controller c = new Controller();
        UserController.loadUserData();
        String username = "mey1";
        UserController.loadUserAccount(username);

        // Items and projects to be deleted for testing
        String projectName = "livingroom ";
        String taskName = "painting";

        ProjectController.loadProject(projectName);

        // Delete the task
        try {
            ProjectController.deleteTask(taskName);
        } catch (Exception e) {
            fail("Task deletion failed: " + e.getMessage());
        }
    }

    @Test
    public void deleteProject() {
        Controller c = new Controller();
        UserController.loadUserData();
        String username = "mey1";
        UserController.loadUserAccount(username);

        // Items and projects to be deleted for testing
        String projectName = "livingroom ";

        // Delete the project
        try {
            ProjectController.deleteProject(projectName);
        } catch (Exception e) {
            fail("Project deletion failed: " + e.getMessage());
        }
    }
}
