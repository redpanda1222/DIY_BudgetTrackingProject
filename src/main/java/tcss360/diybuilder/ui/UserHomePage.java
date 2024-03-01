/*
 * Team Periwinkle
 */
package tcss360.diybuilder.ui;

import tcss360.diybuilder.models.Project;
import tcss360.diybuilder.models.User;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Project UI.
 *
 * @author Mey Vo
 * @author Soe Lin
 */
public class UserHomePage extends JFrame {
    /** Panel for project list. */
    private JPanel projectListPanel;
    /** User object. */
    protected User myUser;
   
    

    /**
     * Constructor.
     *
     * @param theUser user object
     */
    public UserHomePage(User theUser) {
        super("DIY Control");
       // userProjects = myUser.getUserProjects();
        myUser = theUser;
        createMenuBar();
    }

    /**
     * Set up the GUI and display.
     */
    public void display() {
        //set layout
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(500, 500);
        this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        // Welcome Label
        JLabel welcomeUser = new JLabel("Welcome Back " + myUser.getUserName(), null, SwingConstants.CENTER);
        welcomeUser.setFont(new Font("Arial", Font.PLAIN, 30));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2; // Make this component span across two columns
        // Change this to create desired space below the label
        gbc.insets = new Insets(10, 10, 50, 10);
        this.add(welcomeUser, gbc);

        // Reset insets 
        

        // Panel of  "+" and "Create new project" labels
        JPanel projectCreatePanel = new JPanel();
        projectCreatePanel.setLayout(new BoxLayout(projectCreatePanel, BoxLayout.X_AXIS));
        gbc.insets = new Insets(10, 10, 10, 10);
        // creat "+" create new project
        JLabel createProjectLabel = new JLabel("+");
        createProjectLabel.setToolTipText("Create new project");
        createProjectLabel.setFont(new Font("Arial", Font.BOLD, 20));
        createProjectLabel.setForeground(Color.RED);
        projectCreatePanel.add(createProjectLabel);

        // Add space between "+" and "Create new project" labels
        // Change the number 10 to increase or decrease the space
        projectCreatePanel.add(Box.createHorizontalStrut(10));

        // Create "Create new project" label
        JLabel projectLabel = new JLabel("Create new project");
        projectLabel.setHorizontalAlignment(SwingConstants.LEFT);
        projectCreatePanel.add(projectLabel);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        this.add(projectCreatePanel, gbc);

        createProjectLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        createProjectLabel.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                JPanel projectDetailsPanel = new JPanel(new GridLayout(4, 2));

                projectDetailsPanel.add(new JLabel("Title:"));
                JTextField projectNameField = new JTextField();
                projectDetailsPanel.add(projectNameField);
                projectDetailsPanel.add(new JLabel("Budget:"));
                JTextField budgetField = new JTextField();
                projectDetailsPanel.add(budgetField);
                projectDetailsPanel.add(new JLabel("Description:"));
                JTextField descriptionField = new JTextField();
                projectDetailsPanel.add(descriptionField);

                // Show the panel in a JOptionPane
                int result = JOptionPane.showConfirmDialog(UserHomePage.this, projectDetailsPanel,
                        "Create a new project", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
                if (result == JOptionPane.OK_OPTION) {
                    String projectName = projectNameField.getText();
                    String budgetText = budgetField.getText();
                    double budget = Double.parseDouble(budgetText);
                    String description = descriptionField.getText();
                    if (!projectName.trim().isEmpty()) {
                        myUser.addProject(projectName, budget, description);
                        JOptionPane.showMessageDialog(UserHomePage.this, "New project created: "
                                + projectName, "Create Project", JOptionPane.INFORMATION_MESSAGE);
                                // update list project
                                updateProjectList();
                    } else {
                        JOptionPane.showMessageDialog(UserHomePage.this, "Invalid project name",
                                "Create Project", JOptionPane.WARNING_MESSAGE);
                    }
                }
            }
        });

        // Set up layout
        projectListPanel = new JPanel(new GridBagLayout());
        gbc.gridx = 0;
        gbc.gridy = 2;
        //gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        updateProjectList();
        this.add(projectListPanel, gbc);
        this.setLocationRelativeTo(null);
        setVisible(true);
    }


     /**
     * Update the project list based on the added or deleted task.
     */
    private void updateProjectList() {
        // Create Dataset
        projectListPanel.removeAll();
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(2, 2, 2, 2);


        for (int i = 0; i < myUser.getUserProjects().size(); i++) {
            final String projectName = myUser.getUserProjects().get(i).getName();

            // Mouseclick delete project
            ProjectButton projectButton = new ProjectButton(projectName, i);

//            Image cursorDeleteImage = Toolkit.getDefaultToolkit().getImage("src/main/resources/delete-24.png");
//            Image resizedDeleteImage = cursorDeleteImage.getScaledInstance(20, 20, Image.SCALE_SMOOTH);
//
//            // Create a custom cursor using the image
//            Cursor customDeleteCursor = Toolkit.getDefaultToolkit().createCustomCursor(
//                    resizedDeleteImage, new java.awt.Point(0, 0), "CustomCursor");
            projectButton.getDeleteLabelLabel().setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

            projectButton.getDeleteLabelLabel().addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    int confirm = JOptionPane.showConfirmDialog(projectListPanel,
                            "Are you sure you want to delete this project?", "Confirm Delete",
                            JOptionPane.YES_NO_OPTION);
                    if (confirm == JOptionPane.YES_OPTION) {
                        boolean deleted = deleteProject(projectName);
                        if (deleted) {
                            JOptionPane.showMessageDialog(projectListPanel, "Project deleted: " + projectName,
                                    "Delete Project", JOptionPane.INFORMATION_MESSAGE);
                            updateProjectList();
                        } else {
                            JOptionPane.showMessageDialog(projectListPanel, "Failed to delete the project",
                                    "Delete Project", JOptionPane.WARNING_MESSAGE);
                        }
                    }
                }
            });

            projectButton.addActionListener();
//            Image cursorImage = Toolkit.getDefaultToolkit().getImage("src/main/resources/cursor-60.png");
//            Image resizedCursorImage = cursorImage.getScaledInstance(20, 20, Image.SCALE_SMOOTH);
//
//            // Create a custom cursor using the image
//            Cursor customCursor = Toolkit.getDefaultToolkit().createCustomCursor(
//                    resizedCursorImage, new java.awt.Point(0, 0), "CustomCursor");
//
//            // Add the ProjectButton to the task list panel
//            projectButton.setCursor(customCursor);
            projectListPanel.add(projectButton, gbc);
            gbc.gridy++;
        }

          // Refresh the project list panel
        projectListPanel.revalidate();
        projectListPanel.repaint();
    }

    /**
     * Delete project from the project list.
     *
     * @param projectName name of the project
     * @return boolean
     */
    private boolean deleteProject(String projectName) {
        for (Project project : myUser.getUserProjects()) {
            if (project.getName().equals(projectName)) {
                myUser.deleteProject(projectName);
                return true;
            }
        }
        return false;
    }

    
     /**
     * Creating menu bar for JFrame.
     */
    private void createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        JMenu settingsSection = new JMenu("Settings");

        menuBar.add(Box.createRigidArea(new Dimension(400, 0)));
        menuBar.add(Box.createHorizontalGlue());
        menuBar.add(settingsSection); // Add settingsSection to the menu bar


        // Creat "Sign Out" menu item
        JMenuItem signOutMenuItem = new JMenuItem("Sign Out");
        signOutMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
                DIYControl jFrame = new DIYControl();
                jFrame.display();
            }
        });
    

        //Creat "About" menu item
        JMenuItem aboutMenuItem = new JMenuItem("About");
        aboutMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
                About aboutPage = new About(myUser);
                aboutPage.display();
            }
        });

        settingsSection.add(aboutMenuItem);
        settingsSection.addSeparator();
        settingsSection.add(signOutMenuItem);
        setJMenuBar(menuBar);
    }

    /**
     * Custom project button class.
     *
     * @author Soe Lin
     */
    private class ProjectButton extends JButton {
         /** Integer to use and actionPerformed. */
        private final int index;
         /** Delete label. */
        private final JLabel deleteLabel;

        /**
         * Constructor.
         *
         * @param name name of the project
         * @param theIndex index position for the project
         */
        public ProjectButton(String name, int theIndex) {
            super(name);
            index = theIndex;
            deleteLabel = new JLabel("-");
            setup();
        }

         /**
         * Set up for custom project button.
         */
        public void setup() {
            this.setHorizontalAlignment(SwingConstants.LEFT);
            this.setPreferredSize(new Dimension(220, 25));
            this.setFocusable(false);
            deleteLabel.setFont(new Font("", Font.BOLD, 20));
            deleteLabel.setForeground(Color.RED);
            deleteLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            this.setLayout(new BorderLayout());
            this.add(deleteLabel, BorderLayout.EAST);
        }

         /**
         * Return delete label.
         *
         * @return deleteLabel
         */
        public JLabel getDeleteLabelLabel() {
            return deleteLabel;
        }

        /**
         * Add action listener to the button.
         */
        public void addActionListener() {
            this.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    dispose();
                    ProjectPage p = new ProjectPage(myUser.getUserProjects().get(index), myUser);
                    p.display();
                }
            });
        }
    }

}
