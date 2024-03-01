package tcss360.diybuilder.SystemControl;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import tcss360.diybuilder.models.*;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Handles data retrieval, writing, and deletion from web of JSONOBJECTs, Specifically handles
 * everything invloving project, task, and items
 * @author Alex Garcia
 * @author Soe Lin
 */
public class ProjectController extends UserController {

    /**Used to keep preloaded data making access to nested data easier*/
    private static JSONObject currentProject;

    /**Used to keep preloaded data for current task*/
    private static JSONObject currentTask;

    /**Used to keep preloaded data for current item*/
    private static JSONObject currentItem;

    /**Used to keep indexes of the preloaded data*/
    private static int projectIndex;
    private static int taskIndex;
    private static int itemIndex;


    public ProjectController() {}



    /**
     * helper method to fill ProjectList
     * reads in projects data for a given user
     * @param username username for the user whose data is to be retrieved
     * @return returns an array of projects Objects for the user
     */
    static public ArrayList<Project> readProjects(String username) {
        ArrayList<Project> projectsList = new ArrayList<>();


        //currently sloppy but will retrieve the "projects array for users"
        JSONArray projectData = readProjectdata(username);

        for (Object project : projectData) {
            JSONObject projTemp = (JSONObject) project; //not sure why I cant just do this directly but ok

            String name = projTemp.get("title").toString();
            double budget = Double.parseDouble(projTemp.get("budget").toString());
            String plan = projTemp.get("title").toString();
            String description = projTemp.get("description").toString();
            String note = projTemp.get("note").toString();

            ArrayList<Task> tasks = readtasks(username, name);
            Project temp = new Project(name, budget, description, tasks);
            projectsList.add(temp);
        }
        return projectsList;
    }

    /**
     *reads in the tasks for a give project
     * @param userName username whom the project belongs to
     * @param projectName project in which the task is located in
     * @return returns an array of Task Objects
     */
    static public ArrayList<Task> readtasks(String userName, String projectName) {
        ArrayList<Task> result = new ArrayList<>();

        JSONArray projectData = readProjectdata(userName);
        JSONArray projectTasks = new JSONArray();
        //find the project in jsonObject and the jsonArray for tasks
        for (Object project : projectData) {
            JSONObject temp = (JSONObject) project;
            String title = (String) temp.get("title");

            if(title.equals(projectName)){
                projectTasks = (JSONArray) temp.get("tasks");
            }
        }

        for(Object task : projectTasks){
            JSONObject taskJson = (JSONObject) task;
            String name = (String)taskJson.get("name");
            JSONArray items = (JSONArray) taskJson.get("items");
            ArrayList<Item> list = JSONArrayToItemArray(items);
            Task temp  = new Task(name,list );
            result.add(temp);
        }
        return result;
    }

    /**
     * helper method to convert a JSONarray to an item Array
     * @param things fun name for the JSON array to be converted
     * @return returns an array List of item objects
     */
    protected static ArrayList<Item> JSONArrayToItemArray(JSONArray things) {

        ArrayList<Item> result = new ArrayList<>(things.size());
        for(Object item: things){
            JSONObject itemTemp = (JSONObject) item; //not sure why I cant just do this directly but ok

            String name = itemTemp.get("name").toString();
            double price = Double.parseDouble(itemTemp.get("price").toString());
            int unit = Integer.parseInt(itemTemp.get("unit").toString());

            Item temp = new Item(name, price, unit);
            result.add(temp);
        }
        return result;
    }


    /**
     * returns the JSONARRAY filled with a users projects
     * @param userName username of the user
     * @return Json Array for all user projects
     */
    protected static JSONArray readProjectdata(String userName){

        //might be redudant but makes sure we have everything loaded in
        if(userData.isEmpty()){
            loadUserData();
        }
        if(currentUser == null){
            System.out.println("ahsbdashdbasd");
            loadUserAccount(userName);
        }


        JSONArray projectData = (JSONArray) currentUser.get("projects");

        return projectData;
    }

    /**
     * To be used when creating a new project(will handle saving the project information to th json file)
     * @param project project object to be created
     * @param userName the owner of this project
     */
    public static void createProject(String userName, Project project){
        //might be redundant but makes sure we have everything loaded in
        if(userData.isEmpty()){
            loadUserData();
        }
        if(currentUser.isEmpty()){
            loadUserAccount(userName);
        }

        JSONArray userProjects = (JSONArray) currentUser.get("projects");

        //initialize empty JsonObject and JSONArray to fill then add
        JSONObject newProject = new JSONObject(); //commonly used
        JSONArray tempTasks = new JSONArray();

        //add project information to a Json Object
        newProject.put("title", project.getName());
        newProject.put("budget", project.getBudget());
        newProject.put("description", project.getDescription());
        newProject.put("tasks", tempTasks);
        newProject.put("note", "");

        //add everything back to the user data and update json file
        userProjects.add(newProject);
        currentUser.replace("projects", userProjects);
        userData.replace(userName, currentUser);

        //make sure both volatile and permanant data are updated accordingly
        try {
            updateData(userData);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * To be used when creating a new Task(will handle saving the project information to th json file)
     * Only call this function of Project has already been loaded!!
     * @param taskName name for the new task to be added
     */
    public static void createTask(String taskName){
        JSONArray userProjects = (JSONArray) currentUser.get("projects");
        //just in case
        if(currentProject.isEmpty()){
            System.out.println("ERROR: PROJECT IS EMPTY");
            return;
        }

        //adding some information in case of a null exception might be beneficial
        JSONArray projectTasks = (JSONArray) currentProject.get("tasks");

        //initialize empty JsonObject and JSONArray to fill then add
        JSONObject newTask = new JSONObject();
        JSONArray tempItemArray = new JSONArray();

        //add task information to a Json Object
        newTask.put("name", taskName);
        newTask.put("items", tempItemArray);

        //add everything back to project data and then user data
        projectTasks.add(newTask);
        currentProject.replace("tasks", projectTasks);
        userProjects.set(projectIndex, currentProject);
        currentUser.replace("projects",userProjects);
        userData.replace((String)currentUser.get("username"), currentUser);

        try {
            updateData(userData);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * To be used when creating a new Item
     * Only call this function if Task has already been loaded!!
     * @param itemName name for the new item to be added
     * @param itemPrice price for the new Item
     * @param itemUnits number of units for the new item
     */
    public static void createItem(String itemName, Double itemPrice, int itemUnits){
        JSONArray userProjects = (JSONArray) currentUser.get("projects");
        JSONArray projectTasks = (JSONArray) currentProject.get("tasks");
        JSONArray taskItems = (JSONArray) currentTask.get("items");

        //initialize empty JsonObject to fill then add
        JSONObject newItem = new JSONObject();

        //add task information to a Json Object
        newItem.put("name", itemName);
        newItem.put("price", itemPrice);
        newItem.put("unit", itemUnits);


        //add everything back to project data and then user data
        taskItems.add(newItem);

        currentTask.replace("taskItems", taskItems);

        //update the currentTask in the Project task Array
        projectTasks.set(taskIndex, currentTask);
        currentProject.replace("tasks",projectTasks);
        userProjects.set(projectIndex, currentProject);
        currentUser.replace("projects", userProjects);

        userData.replace((String)currentUser.get("username"), currentUser);

        try {
            updateData(userData);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    /**
     * loads in the current project into the static datafield
     * @param title title of the project user has selected
     */
    public static void loadProject(String title){
        JSONArray userProjects = (JSONArray) currentUser.get("projects");

        for (int i = 0; i < userProjects.size(); i++) {
            JSONObject tempProj = (JSONObject)userProjects.get(i);
            String projTitle = (String)tempProj.get("title");

            if(projTitle.equals(title)){
                currentProject = tempProj;
                projectIndex = i;
            }
        }


        //for deubugging purposes
        if(currentProject.isEmpty()){
            System.out.println("Item was not loaded!!");
        }
    }

    /**
     * loads in the current task into static datafield
     * @param taskName
     */
    public static void loadTask(String taskName){
        JSONArray projectTasks = (JSONArray) currentProject.get("tasks");

        for (int i = 0; i < projectTasks.size() ; i++) {
            JSONObject tempTask = (JSONObject)projectTasks.get(i);
            String tempName = (String)tempTask.get("name");

            if(tempName.equals(taskName)){
                currentTask = tempTask;
                taskIndex = i;
            }
        }
        

        //for deubugging purposes
        if(currentTask.isEmpty()){
            System.out.println("Task was not loaded!!");
        }
    }

    /**
     * loads in the current Item into static datafield
     * assumes task is already loaded in
     * @param itemName name for the item to be loaded in
     */
    public static void loadItem(String itemName){
        JSONArray taskItems = (JSONArray) currentTask.get("items");

        for (int i = 0; i < taskItems.size(); i++) {
            JSONObject tempItem = (JSONObject)taskItems.get(i);
            String tempName = (String)tempItem.get("name");

            if(tempName.equals(itemName)){
                currentItem = tempItem;
                itemIndex = i;
            }
        }

        //for deubugging purposes
        if(currentItem.isEmpty()){
            System.out.println("Item was not loaded!!");
        }
    }



    /**
     * @author Soe
     * @param theTask
     * @return
     */
    public static double calcuateTaskCost(Task theTask) {
        double result = 0;
        ArrayList<Item> itemsList = theTask.getItemsList();
        for (int j = 0; j < itemsList.size(); j++) {
            result += itemsList.get(j).getTotalCost();
        }
        return result;
    }


    /**
     * used to delete a task from volotile and permananent memory
     * WARNING: use only after currentUser has been loaded in
     * @param title title of the project to be deleted
     */
    public static void deleteProject(String title){
        if(currentUser.isEmpty()){
            System.out.println("ERROR: User has not been loaded in properly cannot delete project");
        }

        JSONArray userProjects = (JSONArray) currentUser.get("projects");//array of all user objects

        //find the projects and remove it
        for (int i = 0; i < userProjects.size() ; i++) {
            JSONObject temp = (JSONObject) userProjects.get(i);
            String tempTitle = (String) temp.get("title");

            if(tempTitle.equals(title)){
                userProjects.remove(i);
            }
        }

        //update all volatile data
        currentUser.replace("projects", userProjects);

        userData.replace(currentUser.get("name"), currentUser);//think about removing userData Tag

        //update permanent data
        try {
            updateData(userData);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * used to delete a task from volatile and permanent memory
     * WARNING: use after currentProject has ben loaded in
     * @param taskName name of task to be deleted
     */
    public static void deleteTask(String taskName){
        if(currentProject.isEmpty()){
            System.out.println("ERROR: Project has not been loaded in cannot delete task");
            return;
        }
        JSONArray userProjects = (JSONArray) currentUser.get("projects");//array of all user objects
        JSONArray projectTasks = (JSONArray) currentProject.get("tasks");//array of all project tasks

        //find the task and remove it
        for (int i = 0; i < projectTasks.size() ; i++) {
            JSONObject temp = (JSONObject) projectTasks.get(i);
            String tempName = (String) temp.get("name");

            if(tempName.equals(taskName)){
                projectTasks.remove(i);
            }
        }

        //update all volatile data
        //add everything back to project data and then user data
        currentProject.replace("tasks", projectTasks);
        userProjects.set(projectIndex, currentProject);
        currentUser.replace("projects",userProjects);
        userData.replace((String)currentUser.get("username"), currentUser);

        try {
            updateData(userData);
        } catch (IOException e) {
            e.printStackTrace();
        }
        currentProject.replace("projects", userProjects);

        userData.replace(currentUser.get("name"), currentUser);//think about removing userData Tag

        //update permanent data
        try {
            updateData(userData);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    /**
     * used to delete a task from volatile and permanant memory
     * WARNING: Use after currentTask has been loaded in
     * @param itemName name of item to be deleted
     */
    public static void deleteItem(String itemName){
        if(currentTask.isEmpty()){
            System.out.println("ERROR: Task has not been loaded in cannot delete item");
            return;
        }

        //to be used later to update volatile data
        JSONArray userProjects = (JSONArray) currentUser.get("projects");//array of current user projects
        JSONArray projectTasks = (JSONArray) currentProject.get("tasks");//array of current project tasks
        JSONArray taskItems = (JSONArray) currentTask.get("items");//array of task items

        //find the item and remove it
        for (int i = 0; i < taskItems.size() ; i++) {
            JSONObject temp = (JSONObject) taskItems.get(i);
            String tempName = (String) temp.get("name");

            if(tempName.equals(itemName)){
                taskItems.remove(i);
            }
        }

        //add everything back to project data and then user data
        currentTask.replace("items", taskItems);

        //update the currentTask in the Project task Array
        projectTasks.set(taskIndex, currentTask);
        currentProject.replace("tasks",projectTasks);
        userProjects.set(projectIndex, currentProject);
        currentUser.replace("projects", userProjects);

        userData.replace((String)currentUser.get("username"), currentUser);

        try {
            updateData(userData);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    /**
     * edits an item in permanant and volatile data
     * @param itemName new name for the item
     * @param price new price for the item
     * @param unit new
     */
    public static void editItem(String itemName, Double price, int unit){
        //just a precaution, shouldnt ever actually run
        if(currentTask.isEmpty()){
            System.out.println("ERROR: Task has not been loaded in cannot delete item");
            return;
        }

        //to be used later to update volatile data
        JSONArray userProjects = (JSONArray) currentUser.get("projects");//array of current user projects
        JSONArray projectTasks = (JSONArray) currentProject.get("tasks");//array of current project tasks
        JSONArray taskItems = (JSONArray) currentTask.get("items");//array of task items

        //make edits to the volatile (JSONObject)
        currentItem.replace("name", itemName);
        currentItem.replace("price", price);
        currentItem.replace("unit", unit );


        //add everything back to project data and then user data
        taskItems.set(itemIndex, currentItem);
        currentTask.replace("items", taskItems);

        //update the currentTask in the Project task Array
        projectTasks.set(taskIndex, currentTask);
        currentProject.replace("tasks",projectTasks);
        userProjects.set(projectIndex, currentProject);
        currentUser.replace("projects", userProjects);

        userData.replace((String)currentUser.get("username"), currentUser);

        try {
            updateData(userData);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * calculates the total for the project
     * @param theBudget budget object
     * @return
     * @Soe Lin
     */
    public static double calculateOverallTotal(Budget theBudget) {
        double result = 0;
        ArrayList<Task> taskList = theBudget.getTasksList();
        for (int i = 0; i < taskList.size(); i++) {
            result += ProjectController.calcuateTaskCost(taskList.get(i));
        }

        return result;
    }

    /**
     * save note for the currently loaded user and project
     * @param note
     */
    public static void saveNote(String note){
        if(currentUser.isEmpty()){
            System.out.println("ERROR: User has not been loaded in properly cannot delete project");
        }

        JSONArray userProjects = (JSONArray) currentUser.get("projects");//array of all user objects


        //edit note
        currentProject.replace("note", note);
        userProjects.set(projectIndex, currentProject);
        currentUser.replace("projects", userProjects);

        userData.replace(currentUser.get("name"), currentUser);//think about removing userData Tag

        //update permanent data
        try {
            updateData(userData);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * get the note for the currently loaded project
     * @return note in the form of a string
     */
    public static String findNote(){
        return currentProject.get("note").toString();
    }
}
