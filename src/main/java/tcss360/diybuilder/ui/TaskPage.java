package tcss360.diybuilder.ui;
import tcss360.diybuilder.SystemControl.ProjectController;
import tcss360.diybuilder.models.Item;
import tcss360.diybuilder.models.Project;
import tcss360.diybuilder.models.Task;
import tcss360.diybuilder.models.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

/**
 * Login and Sign Up UI.
 *
 * @author Soe Lin
 * @author Charmel Mbala
 */

public class TaskPage extends JFrame {

    //private ArrayList<Item> items;
    private JPanel itemListPanel;
     /** User object. */
    private User myUser;
     /** Project object. */
    private Project project;
    private int myIndex;
    private Task myTask;

    /**
     * The TaskPage class represents the user interface for a specific task in the DIY Builder application.
     * It allows users to view, add, and manage items related to the task.
     * This class extends the JFrame class to create a window-based UI.
     * @param theP
     * @param theUser
     * @param theIndex
     */
    public TaskPage(Project theP, User theUser, int theIndex) {
        super("DIY Control");
        project = theP;
        myIndex = theIndex;
        myTask = project.getTaskList().get(theIndex);
        ProjectController.loadTask(myTask.getName());
        myUser = theUser;
       // items = project.getTaskList().get(theIndex).getItemsList();
        itemListPanel = new JPanel(new GridBagLayout());
    }

    /**
     * Display the UI to the JFrame initialize panels and Buttons.
     */
    public void display() {

        createMenuBar();

        // Task name label
        JLabel taskName = new JLabel("Task name: " + project.getTaskList().get(myIndex).getName());
        taskName.setFont(new Font("Arial", Font.BOLD, 16));

        // Panel for items section
        JPanel buttonsPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JPanel itemCreatePanel = new JPanel();
        JLabel createItemLabel = new JLabel("+");
        createItemLabel.setToolTipText("Add new Item");
        createItemLabel.setFont(new Font("Arial", Font.BOLD, 20));
        createItemLabel.setForeground(Color.RED);
        itemCreatePanel.add(createItemLabel);

        createItemLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        createItemLabel.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                JPanel itemDetailsPanel = new JPanel(new GridLayout(3, 2));

                itemDetailsPanel.add(new JLabel("Item Name:"));
                JTextField itemNameField = new JTextField();
                itemDetailsPanel.add(itemNameField);

                itemDetailsPanel.add(new JLabel("Price Per unit:"));
                JTextField pricePerUnitField = new JTextField();
                itemDetailsPanel.add(pricePerUnitField);

                itemDetailsPanel.add(new JLabel("Total unit:"));
                JTextField totalUnitField = new JTextField();
                itemDetailsPanel.add(totalUnitField);

                // Show the panel in a JOptionPane
                int result = JOptionPane.showConfirmDialog(getParent(), itemDetailsPanel, "Create a new item",
                        JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
                if (result == JOptionPane.OK_OPTION) {
                    String name = itemNameField.getText();
                    String price = pricePerUnitField.getText();
                    double pricePerUnit = Double.parseDouble(price);
                    String unit = totalUnitField.getText();
                    int totalUnit = Integer.parseInt(unit);

                    // Check to see if the price exceeds the budget
                    if(pricePerUnit > project.getBudget()){
                        JOptionPane.showMessageDialog(getParent(), "Number exceeds set Budget.");
                        return;
                    }
                    //Check if item is duplicate from existing item list
                    if (duplicateCheck(name)){
                        return; // Duplicate found
                    }
                    // Create new item
                    myTask.addItem(name, pricePerUnit, totalUnit);
                    //items.add(new Item(name, pricePerUnit, totalUnit));

                    JOptionPane.showMessageDialog(getParent(), "New item added: " + name,
                            "Add item", JOptionPane.INFORMATION_MESSAGE);

                    // Update the list of items
                    updateItemList();
                }
            }
        });

        // Add space between "+" and "Create new project" labels
        itemCreatePanel.add(Box.createHorizontalStrut(10)); // Change the number 10 to increase or decrease the space

        // Create "Create new project" label
        JLabel itemLabel = new JLabel("Add new Item");
        itemLabel.setHorizontalAlignment(SwingConstants.LEFT);
        itemCreatePanel.add(itemLabel);

        gbc.gridx = 0;
        gbc.gridy = 0;
        buttonsPanel.add(itemCreatePanel, gbc);

        updateItemList();
        gbc.gridx = 0;
        gbc.gridy = 1;
        buttonsPanel.add(itemListPanel, gbc);

        this.setLayout(new BorderLayout());
        this.add(taskName, BorderLayout.NORTH);
        this.add(buttonsPanel, BorderLayout.CENTER);

        this.setSize(500, 500);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

     /**
     * Update the Item list based on the added or deleted task.
     */
    private void updateItemList() {
        itemListPanel.removeAll();

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(2, 2, 2, 2);

        for (int i = 0; i < myTask.getItemsList().size(); i++) {
            String itemName = myTask.getItemsList().get(i).getName();

            // Mouseclick delete project
            ItemButton itemButton = new ItemButton(itemName, i);

            Image cursorDeleteImage = Toolkit.getDefaultToolkit().getImage("src/main/resources/delete-24.png");
            Image resizedDeleteImage = cursorDeleteImage.getScaledInstance(20, 20, Image.SCALE_SMOOTH);

            // Create a custom cursor using the image
            Cursor customDeleteCursor = Toolkit.getDefaultToolkit().createCustomCursor(
                    resizedDeleteImage, new Point(0, 0), "CustomCursor");
            itemButton.getDeleteLabel().setCursor(customDeleteCursor);
            itemButton.getDeleteLabel().addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    int confirm = JOptionPane.showConfirmDialog(getParent(),
                            "Are you sure you want to delete this item?",
                            "Confirm Delete", JOptionPane.YES_NO_OPTION);
                    if (confirm == JOptionPane.YES_OPTION) {
                        boolean deleted = deleteItem(itemName);
                        if (deleted) {
                            JOptionPane.showMessageDialog(getParent(), "Item deleted: " + itemName,
                                    "Delete Item", JOptionPane.INFORMATION_MESSAGE);
                            updateItemList();
                        } else {
                            JOptionPane.showMessageDialog(getParent(), "Failed to delete the item",
                                    "Delete Item", JOptionPane.WARNING_MESSAGE);
                        }
                    }
                }
            });

            Image cursorImage = Toolkit.getDefaultToolkit().getImage("src/main/resources/cursor-60.png");
            Image resizedCursorImage = cursorImage.getScaledInstance(20, 20, Image.SCALE_SMOOTH);

            // Create a custom cursor using the image
            Cursor customCursor = Toolkit.getDefaultToolkit().createCustomCursor(
                    resizedCursorImage, new Point(0, 0), "CustomCursor");

            itemButton.setCursor(customCursor);

            // Add the ItemButton to the item list panel
            itemListPanel.add(itemButton, gbc);
            itemButton.addActionListener();
            gbc.gridy++;
        }

        // Refresh the item list panel
        itemListPanel.revalidate();
        itemListPanel.repaint();
    }

    private boolean deleteItem(String itemName) {
        for (Item item : myTask.getItemsList()) {
            if (item.getName().equals(itemName)) {
                myTask.removeItem(itemName);
                //items.remove(item);
                return true;
            }
        }
        return false;
    }


    /**
     * Determine if adding a new item is a duplicate of an already existing item.
     * @param name
     * @return
     */
    private boolean duplicateCheck(String name) {
        for (Item item : myTask.getItemsList()) {
            if (item.getName().equals(name)) {
                JOptionPane.showMessageDialog(getParent(), "Duplicate item. Please try again.");
                return true; // Match found
            }
        }
        return false; //Match not found
    }


     /**
     * Creating menu bar for JFrame.
     */
    private void createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        JMenu settingsSection = new JMenu("Settings");

        // Create back button
        JButton backIconButton = new JButton("Back");
        backIconButton.setFocusable(false);
        backIconButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                ProjectPage p = new ProjectPage(project, myUser);
                p.display();
            }
        });

        JButton homeButton = new JButton("Home");
        homeButton.setFocusable(false);

        homeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                UserHomePage u = new UserHomePage(myUser);
                u.display();
            }
        });

        // Create back icon panel
        JPanel backIconPanel = new JPanel();
        backIconPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        backIconPanel.add(backIconButton);
        backIconPanel.add(homeButton);

        menuBar.add(backIconPanel);
        menuBar.add(Box.createHorizontalGlue());
        menuBar.add(settingsSection); // Add settingsSection to the menu bar

        // Create "Budget" menu item
        JMenuItem budgetMenuItem = new JMenuItem("Budget");
        budgetMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
                BudgetPage b = new BudgetPage(project, myUser, 2, myIndex);
                b.display();
            }
        });
        settingsSection.add(budgetMenuItem); // Add the Budget menu item to settingsSection

        setJMenuBar(menuBar);
    }

    /**
     * Custom TaskButton inner class.
     */
    private class ItemButton extends JButton {
        /** Integer to use and actionPerformed. */
        private final int index;
        /** Delete label. */
        private JLabel deleteLabel;

        /**
         * Constructor.
         *
         * @param name
         * @param theIndex
         */
        public ItemButton(String name, int theIndex) {
            super(name);
            index = theIndex;
            setup();
        }

        /**
         * Return delete label.
         *
         * @return deleteLabel
         */
        public JLabel getDeleteLabel() {
            return deleteLabel;
        }

        /**
         * Set up for custom task button.
         */
        private void setup() {
            this.setHorizontalAlignment(SwingConstants.LEFT);
            this.setPreferredSize(new Dimension(220, 25));
            this.setFocusable(false);

            // Create the delete label and add it to the button
            deleteLabel = new JLabel("-");
            deleteLabel.setFont(new Font("", Font.BOLD, 20));
            deleteLabel.setForeground(Color.RED);
            deleteLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            this.setLayout(new BorderLayout());
            this.add(deleteLabel, BorderLayout.EAST);
        }

        /**
         * Add action listener to the button.
         */
        public void addActionListener() {
            this.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JPanel itemDetailsPanel = new JPanel(new GridLayout(3, 2));

                    itemDetailsPanel.add(new JLabel("Item Name:"));
                    JTextField itemNameField = new JTextField();
                    itemNameField.setText(myTask.getItemsList().get(index).getName());
                    itemDetailsPanel.add(itemNameField);

                    itemDetailsPanel.add(new JLabel("Price Per unit:"));
                    JTextField pricePerUnitField = new JTextField();
                    pricePerUnitField.setText("" + myTask.getItemsList().get(index).getPrice());
                    itemDetailsPanel.add(pricePerUnitField);

                    itemDetailsPanel.add(new JLabel("Total unit:"));
                    JTextField totalUnitField = new JTextField();
                    totalUnitField.setText("" + myTask.getItemsList().get(index).getUnit());
                    itemDetailsPanel.add(totalUnitField);

                    // Show the panel in a JOptionPane
                    int result = JOptionPane.showConfirmDialog(getParent(), itemDetailsPanel, "Item details",
                            JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
                    if (result == JOptionPane.OK_OPTION) {
                        String name = itemNameField.getText();
                        String price = pricePerUnitField.getText();
                        double pricePerUnit = Double.parseDouble(price);

                        //Check to see if pricePerUnit of existing item doesn't exceed budget
                        if(pricePerUnit >project.getBudget()){
                            JOptionPane.showMessageDialog(getParent(), "number exceeds set Budget.");
                            return;
                        }
                        String unit = totalUnitField.getText();
                        int totalUnit = Integer.parseInt(unit);

                        myTask.getItemsList().get(index).setName(name);
                        myTask.getItemsList().get(index).setPrice(pricePerUnit);
                        myTask.getItemsList().get(index).setUnit(totalUnit);

                        JOptionPane.showMessageDialog(getParent(), "Item updated",
                                "Item details", JOptionPane.INFORMATION_MESSAGE);
                    }
                }
            });
        }
    }

}
