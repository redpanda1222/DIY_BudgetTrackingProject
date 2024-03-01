/*
 * Team Periwinkle
 */
package tcss360.diybuilder.models;

import java.util.ArrayList;

/**
 * Budget Object class.
 *
 * @author Soe Lin
 */
public class Budget {

    /** ArrayList of Task Object. */
    private ArrayList<Task> tasksList;
    /** Estimated Budtet that user entered. */
    private double estimatedBudget;

    /**
     * Constructor.
     *
     * @param theTasksList
     * @param theEstimatedBudget
     */
    public Budget(ArrayList<Task> theTasksList, double theEstimatedBudget) {
        tasksList = new ArrayList<>();

        if(theTasksList.size()>0){
            tasksList.addAll(theTasksList);
        }
        estimatedBudget = theEstimatedBudget;
    }

    /**
     * Return ArrayList of Task Object.
     *
     * @return ArrayList of Task Object
     */
    public ArrayList<Task> getTasksList() {
        return new ArrayList<>(tasksList);
    }

    /**
     * Return estimated budget.
     *
     * @return estimatedBudget
     */
    public double getEstimatedBudget() {
        return estimatedBudget;
    }
}

