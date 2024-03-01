/*
 * Team Periwinkle
 */
package tcss360.diybuilder;

/**
 *
 * @author Mey Vo
 */
import org.junit.Before;
import org.junit.Test;
import tcss360.diybuilder.SystemControl.UserController;
import tcss360.diybuilder.models.User;
import java.io.IOException;
import static org.junit.Assert.*;

public class UserControllerTest {

    private UserController userController;

    @Before
    public void setup() {
        userController = new UserController();
        // Create a test user
        String username = "alexg123";
        String password = "123";
        String email = "alexg123@gmail.com";
    }

    @Test
    public void testCreateUser() throws IOException {
        String username = "alexg123";
        String email = "alexg123@example.com";
        String password = "123";

        // Create the user
        userController.createUser(username, email, password);

        // Retrieve the created user object
        User user = userController.getUserObject(username);

        // Assert that the user object is not null
        assertNotNull(user);

        // Assert that the retrieved user object has the correct properties
        assertEquals(username, user.getUserName());
        assertEquals(email, user.getEmail());
        assertEquals(password, user.getPassword());
    }

    @Test
    public void testCheckCredentials_InvalidPassword() throws IOException {
        String username = "alexg123";
        String password = "12";

        // Test invalid password
        assertFalse(userController.checkCredentials(username, password));
    }

    @Test
    public void testCheckCredentials_ValidCredentials() throws IOException {
        String username = "alexg123";
        String password = "123";

        // Test valid credentials
        assertTrue(userController.checkCredentials(username, password));
    }

    @Test
    public void testCheckCredentials_InvalidUsername() throws IOException {
        String username = "alex123";
        String password = "123";

        // Test invalid username
        assertFalse(userController.checkCredentials(username, password));
    }

    @Test
    public void testUserExists() throws IOException {
        String existingUsername = "alexg123";
        String nonExistingUsername = "";

        // Test existing username
        assertTrue(userController.userExists(existingUsername));

        // Test non-existing username
        assertFalse(userController.userExists(nonExistingUsername));
    }
}

