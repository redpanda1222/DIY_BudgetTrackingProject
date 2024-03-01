/*
 * Team Periwinkle
 */
package tcss360.diybuilder.models;

import java.util.ArrayList;
import tcss360.diybuilder.SystemControl.ProjectController;

/**
 * Task Object class.
 * Represents a task in the DIY Builder application.
 * Tasks are used to organize and manage items for a specific project.
 *
 * This class provides methods to add, remove, and edit items within a task.
 * Each task has a name and an associated list of items.
 *
 * Tasks can be created, modified, and retrieved using the DIY Builder application.
 * The tasks and their associated items are stored in JSON format.
 *
 * This class interacts with the {@link Item} class and the {@link ProjectController}
 * for item management and persistence.
 * @author Soe Lin
 * @author Mey Vo
 * @author Charmel Mbala
 */
public class Task {

    /** THe name of the Task. */
    private String name;
    /** Array List of item. */
    private ArrayList<Item> itemsList;

    /**
     * Constructor.
     *
     * @param theName
     * @param theItemsList
     */
    public Task(String theName, ArrayList<Item> theItemsList) {
        name = theName;
        itemsList = theItemsList;
    }

    /**
     * Another constructor.
     *
     * @param name name of the user
     */
    public Task(String name){
        this.name = name;
    }

    /**
     * Get name of the task.
     *
     * @return task name
     */
    public String getName() {
        return name;
    }

    /**
     * Set name of the task.
     *
     * @param theName task name
     */
    public void setName(String theName) {
        name = theName;
    }

    /**
     * Get the item list.
     *
     * @return items list
     */
    public ArrayList<Item> getItemsList() {
        return itemsList;
    }

    /**
     * Adding item to the list and JSON file.
     *
     * @param itemName item name
     * @param price price per unit of item
     * @param unit total unit of item
     */
    public void addItem(String itemName, double price, int unit) {
        Item newItem= new Item(itemName, price, unit);

        //add to object datafield
        itemsList.add(newItem);

        //add to permanant data
        ProjectController.createItem(itemName, price,unit);
    }

    /**
     * Removing item from the list and JSON file.
     *
     * @param itemName item name
     */
    public void removeItem(String itemName) {
        // Delete from project Controller
        for (int i = 0; i < itemsList.size(); i++) {
            if(itemsList.get(i).getName().equals(itemName)){
                ProjectController.deleteItem(itemName);
                itemsList.remove(i);
            }
        }
    }

    /**
     * Edits an item in the itemsList based on the item name.
     * Updates the price and unit of the specified item.
     * @param itemName the item to be edited
     * @param newPrice the new price to be set
     * @param newUnit  the new unit amount to be set
     */
    public void editItem(String itemName, double newPrice, int newUnit) {
        for (Item item : itemsList) {
            if (item.getName().equals(itemName)) {
                item.setPrice(newPrice);
                item.setUnit(newUnit);
                break;
            }
        }

        //item should be loaded in when editing an item
        ProjectController.editItem(itemName, newPrice, newUnit);

    }

    /**
     * Get an item.
     *
     * @param itemName item name
     * @return item object
     */
    public Item getItem(String itemName){
        for (Item item: itemsList) {
            if(item.getName().equals(itemName)){
                return item;
            }
        }

        //for error checkin this might not be ideal
        return new Item("", 0, 0);
    }

}

