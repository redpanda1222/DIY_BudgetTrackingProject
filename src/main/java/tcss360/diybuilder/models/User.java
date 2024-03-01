/*
 * Team Periwinkle
 */
package tcss360.diybuilder.models;
/**
 * User Object Class.
 *
 * @author Alex Garcia
 * @author Mey Vo
 */
import tcss360.diybuilder.SystemControl.ProjectController;
import tcss360.diybuilder.SystemControl.UserController;

import java.io.*;
import java.util.ArrayList;

public class User implements Serializable {

    /** Name of the user. */
    private String userName;
    /** Email of the user. */
    private String email;
    /** Password of the user. */
    private String password;
    /** List of project. */
    private ArrayList<Project> userProjects;

    /** Serial version UID. */
    private static final long serialVersionUID = 1L;

    /**
     * Constructor.
     *
     * @param name user name
     * @param email user email
     * @param password user password
     */
    public User(String name, String email, String password){
        UserController.loadUserAccount(name);
        this.userName = name;
        this.email = email;
        this.password = password;
        this.userProjects = ProjectController.readProjects(this.userName);
    }

    /**
     * Constructor.
     *
     * @param username user name
     */
    public User(String username){
        UserController.loadUserAccount(username);
        User temp = UserController.getUserObject(username);
        this.userName = temp.userName;
        this.email = temp.email;
        this.password = temp.password;
        this.userProjects = temp.userProjects;
    }


    /** getters*/
    public String getEmail(){
        return email;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }


    //setters
    public void setUserName(String name){
        this.userName = name;
    }

    public void setEmail(String email){this.email = email;}

    public void setPassword(String password) {
        this.password = password;
    }

    public ArrayList<Project> getUserProjects() {
        return new ArrayList<Project>(userProjects);
    }

    /**
     *
     * @param title
     * @param budget
     * @param description
     */
    public void addProject(String title,double budget, String description ){

        //add project to datafield for the user object instance
        Project newProj = new Project(title, budget, description);
        this.userProjects.add(newProj);

        //add project to permanent data
        ProjectController.createProject(userName, newProj);
    }

    //bunch of helper methods

    //methods to add
    //addProject()


    /**
     * used to get a specific project Object
     * @param title
     * @return
     */
    public Project getProject(String title){
        for (Project project: userProjects) {
            if(project.getName().equals(title)){
                return project;
            }
        }
        return new Project();
    }

     


    /**
     *
     * @return list with project titles for a user
     */
    public ArrayList<String> getProjectTitles() {
        ArrayList<String> result = new ArrayList<>(userProjects.size());
        for (Project project : userProjects) {
            result.add(project.getName());
        }

        return result;

    }

    /**
     * check to see if there is a user with the same username already
     * @return
     */
    public boolean alreadyExists(){
        return UserController.userExists(this.userName);
    }

    /**
     * Serialize a User Object
     * @throws IOException
     */
    public void serialize() throws IOException {
        String filepath = "src/main/resources/protocols/user.txt";
        FileOutputStream fileOutputStream = new FileOutputStream(filepath);
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
        objectOutputStream.writeObject(this);
    }


    /**
     * Deserialize a User Object and return
     * @throws IOException
     */
    public void deserialize() throws IOException, ClassNotFoundException {
        String filepath = "src/main/resources/protocols/user.txt";
        FileInputStream fileInputStream = new FileInputStream(filepath);
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
        User deserializedUser = (User) objectInputStream.readObject();
        this.email = deserializedUser.email;
        this.userName = deserializedUser.userName;
    }

    /**
     * 
     * @param title
     * @param budget
     * @param description
     * @author Mey Vo
    */
    public void addProject(String title, Double budget, String description){

        // add project to detafield for the user object instance
        Project p = new Project(title, budget, description);
        this.userProjects.add(p);

        //add project to permanent data
        ProjectController.createProject(userName, p);
    }

     /**
     * deletes a project from valotile and permanent data
     * @param projectName project to be deleted
      * @author Mey Vo
    */
    public void deleteProject(String projectName) {

        for (int i = 0; i < userProjects.size() ; i++) {
            if(userProjects.get(i).getName().equals(projectName)){
                ProjectController.deleteProject(userProjects.get(i).getName());
                userProjects.remove(i);
            }
        }
    }

}
