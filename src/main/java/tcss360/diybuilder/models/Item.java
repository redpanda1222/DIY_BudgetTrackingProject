/*
 * Team Periwinkle
 */
package tcss360.diybuilder.models;

/**
 * Item Object class.
 *
 * @author Soe Lin
 */
public class Item {

    /** Name of the item. */
    private String name;

    /** Price of the item. */
    private double price;
    /** Total unit of the item. */
    private int unit;

    /**
     * Constructor.
     *
     * @param theName name of the item
     * @param thePrice price of the item
     * @param theUnit total unit of the item
     */
    public Item(String theName, double thePrice, int theUnit) {
        name = theName;
        price = thePrice;
        unit = theUnit;
    }

    /**
     * Return the name of the item.
     *
     * @return name of the item
     */
    public String getName() {
        return name;
    }

    /**
     * Return the price of the item.
     *
     * @return price of the item
     */
    public double getPrice() {
        return price;
    }

    /**
     * Return the total unit of the item.
     *
     * @return total unit of the item
     */
    public int getUnit() {
        return unit;
    }

    /**
     * Set the name of the item.
     *
     * @param theName name of the item
     */
    public void setName(String theName) {
        name = theName;
    }

    /**
     * Set the price of the item.
     *
     * @param thePrice price of the item
     */
    public void setPrice(double thePrice) {
        price = thePrice;
    }

    /**
     * Set the total unit of the item.
     *
     * @param theUnit total unit of the item
     */
    public void setUnit(int theUnit) {
        unit = theUnit;
    }

    /**
     * Return the total cost based on the total unit.
     *
     * @return total cost
     */
    public double getTotalCost() {
        return price * unit;
    }

    @Override
    public String toString() {
        return String.format("%s, price per unit: $%.2f, total unit: %d, total price: $%.2f", name, price, unit, price * unit);
    }

}


