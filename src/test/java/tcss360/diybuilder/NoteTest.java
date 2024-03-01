/*
 * Team Periwinkle
 */
package tcss360.diybuilder;
/**
 * About NoteTest.
 *
 * @author Mey Vo
 */

import org.junit.*;
import tcss360.diybuilder.SystemControl.*;
import java.io.IOException;

import static org.junit.Assert.*;
/**
 * Unit test for Note.
 */
public class NoteTest{
    private ProjectController projectController;
    private Controller controller;

    /**
     * set up
     *
     */
    @Before
    public void setUp() {
        projectController = new ProjectController();
        controller = new Controller();

    }

    /**
     * read data from json file
     *
     */
    @Test
    public void saveNote() throws IOException {
        String username = "soe1";
        String projectName = "wall";
        UserController.loadUserAccount(username);
        projectController.loadProject(projectName);

        // Add note
        String note = "hi";
        projectController.saveNote( note);

        // Assert
        assertEquals(note, ProjectController.findNote());
    }
}







