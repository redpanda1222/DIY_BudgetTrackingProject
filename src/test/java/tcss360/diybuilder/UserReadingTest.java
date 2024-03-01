package tcss360.diybuilder;

import org.junit.*;
import tcss360.diybuilder.SystemControl.Controller;
import tcss360.diybuilder.SystemControl.UserController;
import tcss360.diybuilder.models.*;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Unit Tests for reading in data from the Json File
 * @author Alex Garcia
 */
public class UserReadingTest
{

    /**
     * test to see if the user object gets read in correctly(projects and all)
     * will cover the most complex portions of controllers and models
     * @author Alex Garcia
     */
    @Test
    public void loadUserObject(){
        UserController controller = new UserController();

        String expectedUsername = "charmel12";
        String expectedProjectName = "bathroom remodel";
        double expectedProjectBudget = 200.0;
        String expectedTaskName = "sink";
        String expectedItemName = "faucet";
        double expectedItemPrice = 40.0;
        final double DELTA = 0;

        User charmel = new User(expectedUsername);

        Project charmelsFirstProject = charmel.getProject(expectedProjectName);
        Task projectTask = charmelsFirstProject.getTask(expectedTaskName);
        Item taskItem = projectTask.getItem(expectedItemName);

        String actualUsername = charmel.getUserName();
        String actualProjectName = charmelsFirstProject.getName();
        double actualProjectBudget = charmelsFirstProject.getBudget();
        String actualTaskName = projectTask.getName();
        String actualItemName = taskItem.getName();
        double actualItemPrice = taskItem.getPrice();

        assertEquals(expectedUsername, actualUsername);
        assertEquals(expectedProjectName, actualProjectName);
        assertEquals(expectedProjectBudget, actualProjectBudget, DELTA);
        assertEquals(expectedTaskName, actualTaskName);
        assertEquals(expectedItemName, actualItemName);
        assertEquals(expectedItemPrice, actualItemPrice, DELTA);
    }

}
