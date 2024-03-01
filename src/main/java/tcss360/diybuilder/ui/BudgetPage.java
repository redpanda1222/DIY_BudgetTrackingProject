/*
 * Team Periwinkle
 */
package tcss360.diybuilder.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import tcss360.diybuilder.SystemControl.ProjectController;
import tcss360.diybuilder.SystemControl.UserController;
import tcss360.diybuilder.models.*;

/**
 * Budget UI.
 *
 * @author Soe Lin
 */
public class BudgetPage extends JFrame {

    /** Items column title to use in table. */
    private static final String[] ITEMS_COLUMNS = { "Items", "Cost per Unit", "Total Unit", "Total Cost" };
    /** Task column title to use in table. */
    private static final String[] TASKS_COLUMNS = { "Tasks", "Amount" };
    /** Category column title to use in table. */
    private static final String[] CATEGORY_COLUMNS = { "Category", "Amount" };
    /** Panel to hold items' table. */
    private final JPanel panel1;
    /** Panel to hold task and category table. */
    private final JPanel panel2;
    /** Budget object. */
    private final Budget myBudget;
    /** Project object. */
    private final Project myproject;
    /** User object. */
    private final User myUser;
    private int selection;
    private int indexForTask;

    /**
     * Constructor.
     *
     * @param theP project object
     * @param theUser user object
     */
    public BudgetPage(Project theP, User theUser, int flag, int index) //also need parameter for project to go back to project page
    {
        super(theP.getName() + "'s Budget");
        myproject = theP;
        myUser = theUser;
        selection = flag;
        indexForTask = index;
        myBudget = new Budget(theP.getTaskList(), theP.getBudget());
        int rows;
        if (myBudget.getTasksList().size() == 0) {
            rows = 1;
        } else {
            rows = myBudget.getTasksList().size();
        }
        panel1 = new JPanel(new GridLayout(rows, 1));
        panel2 = new JPanel(new GridLayout(2, 1));
    }

    /**
     * Set up the GUI and display.
     */
    public void display()
    {
        createMenuBar();

        // Category amount
        double overallTotal = ProjectController.calculateOverallTotal(myBudget);
        double estimatedBudget = myBudget.getEstimatedBudget();
        double remainingBudget = estimatedBudget - overallTotal;

        // Category Table
        Object[][] categoryData = {
                {"Overall Total", String.format("$%.2f", overallTotal)},
                {"Estimated Budget", String.format("$%.2f", estimatedBudget)},
                {"Remaining Budget", String.format("$%.2f", remainingBudget)}
        };
        JTable categoryTable = new JTable(categoryData, CATEGORY_COLUMNS) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make all cells read-only
            }
        };
        categoryTable.setFocusable(false);
        categoryTable.setCellSelectionEnabled(false);
        JScrollPane categoryScrollPane = new JScrollPane(categoryTable);

        // Panel1 setup
        panel1.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        panel1.setPreferredSize(new Dimension(800, 400));

        // Task data and Items Table setup
        List<Object[]> tasksData = new ArrayList<>();
        ArrayList<Task> tasksList = myBudget.getTasksList();

        if (tasksList.size() != 0 ) {

            for (Task task : tasksList) {
                // Tasks Data
                String[] taskString = {task.getName(), String.format("$%.2f", ProjectController.calcuateTaskCost(task))};
                Object[] taskRow = {taskString[0], taskString[1]};
                tasksData.add(taskRow);

                // Items Data
                List<Object[]> itemsData = new ArrayList<>();
                ArrayList<Item> itemsList = task.getItemsList();
                for (Item item : itemsList) {
                    String[] itemString = {item.getName(), String.format("$%.2f", item.getPrice()),
                            String.valueOf(item.getUnit()), String.format("$%.2f",item.getTotalCost())};
                    Object[] itemRow = {itemString[0], itemString[1], itemString[2], itemString[3]};
                    itemsData.add(itemRow);
                }

                // Items Table
                JTable itemsTable = new JTable(itemsData.toArray(new Object[0][0]), ITEMS_COLUMNS) {
                    @Override
                    public boolean isCellEditable(int row, int column) {
                        return false; // Make all cells read-only
                    }
                };
                itemsTable.setCellSelectionEnabled(false);
                itemsTable.setFocusable(false);
                JScrollPane itemsScrollPane = new JScrollPane(itemsTable);

                // Creating Items Table Label
                JLabel taskLabel = new JLabel(task.getName());
                taskLabel.setFont(taskLabel.getFont().deriveFont(Font.BOLD, 14));
                taskLabel.setHorizontalAlignment(JLabel.CENTER);

                // Adding to Panel1
                panel1.add(taskLabel);
                panel1.add(itemsScrollPane);
            }
        } else {
            JLabel taskLabel = new JLabel("Empty Tasks");
            taskLabel.setFont(taskLabel.getFont().deriveFont(Font.BOLD, 14));
            taskLabel.setHorizontalAlignment(JLabel.CENTER);

            panel1.add(taskLabel);
        }

        // Task Table
        JTable tasksTable = new JTable(tasksData.toArray(new Object[0][0]), TASKS_COLUMNS) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make all cells read-only
            }
        };
        tasksTable.setFocusable(false);
        tasksTable.setCellSelectionEnabled(false);
        JScrollPane tasksScrollPane = new JScrollPane(tasksTable);

        // Panel2 setup
        panel2.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        panel2.setPreferredSize(new Dimension(800, 100));
        panel2.add(tasksScrollPane);
        panel2.add(categoryScrollPane);

        // Add the panel to the frame
        this.setLayout(new BorderLayout());
        this.add(panel1, BorderLayout.NORTH);
        this.add(panel2, BorderLayout.CENTER);

        // Set frame properties
        this.setSize(new Dimension(900, 700));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setVisible(true);

        // Warning Message for Remaining Budget
        if (remainingBudget < 0)
        {
            JOptionPane.showMessageDialog(getParent(),
                    "You have negative remaining Budget!\n" +
                            "Consider to edit the estimated budget or Reduce the items.",
                    "DIYControl", JOptionPane.WARNING_MESSAGE);
        }
    }

    /**
     * Create menu bar.
     */
    private void createMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        // Create back button
        JButton backIconButton = new JButton("Back");
        backIconButton.setFocusable(false);

        JButton homeButton = new JButton("Home");
        homeButton.setFocusable(false);


        if (selection == 1) {
            backIconButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    dispose();
                    ProjectPage p = new ProjectPage(myproject, myUser);
                    p.display();
                }
            });
        } else if (selection == 2) {
            backIconButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    dispose();
                    TaskPage t = new TaskPage(myproject, myUser, indexForTask);
                    t.display();
                }
            });
        }

        homeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                UserHomePage u = new UserHomePage(myUser);
                u.display();
            }
        });

        menuBar.add(backIconButton);
        menuBar.add(homeButton);

        setJMenuBar(menuBar);
    }
}

