/*
 * Team Periwinkle
 */
package tcss360.diybuilder.ui;

//import tcss360.diybuilder.SystemControl.UserController;
import tcss360.diybuilder.SystemControl.UserController;
import tcss360.diybuilder.models.User;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Login and Sign Up UI.
 *
 * @author Soe Lin
 * @editor Charmel Mbala
 */
public class DIYControl extends JFrame {

    /** Label of title. */
    private JLabel titleLabel;
    /** Username label. */
    private JLabel usernameLabel;
    /** Username field to enter username. */
    private JTextField usernameField;
    /** Email label. */
    private JLabel emailLabel;
    /** Email field to enter email address. */
    private JTextField emailField;
    /** Password label. */
    private JLabel passwordLabel;
    /** Password field to enter password. */
    private JPasswordField passwordField;
    /** Panel to hold all the buttons. */
    private JPanel buttonsPanel;
    /** Login button. */
    private JButton loginButton;
    /** Sing Up button. */
    private JButton signUpButton;
    /** Back button. */
    private JButton backButton;
    /** Create button. */
    private JButton createButton;
    /** Exit Button. */
    private JButton exitButton;
    /** String variable to hold username. */
    private String username;
    /** String variable to hold email. */
    private String email;
    /** String variable to hold password. */
    private String password;
    /** UserController to perform read and write data. */
    //private final UserController userC;

    /**
     * Constructor.
     */
    public DIYControl() {
        super("DIYControl");
        //userC = new UserController();
    }

    /**
     * Set up the GUI and display.
     */
    public void display()  {
        setSize(500, 500);
        // Set layout
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        setup();

        // Title label
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        add(titleLabel, gbc);

        // Username label and field
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        add(usernameLabel, gbc);
        gbc.gridx = 1;
        gbc.gridy = 1;
        add(usernameField, gbc);

        // Password label and field
        gbc.gridx = 0;
        gbc.gridy = 2;
        add(passwordLabel, gbc);
        gbc.gridx = 1;
        gbc.gridy = 2;
        add(passwordField, gbc);

        // Buttons Panel
        buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.Y_AXIS));
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 4;
        add(buttonsPanel, gbc);

        // Adding buttons to the button panel
        buttonsPanel.add(loginButton);
        buttonsPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        buttonsPanel.add(signUpButton);
        buttonsPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        buttonsPanel.add(exitButton);


        // Buttons' action section
        exitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                username = usernameField.getText();
                password = String.valueOf(passwordField.getPassword());

                if (username.compareTo("") == 0 || password.compareTo("") == 0) {
                    JOptionPane.showMessageDialog(getParent(), "Please enter your Username and Password.");
                } else{

                    //currently uses controller, if time permits change this is use models only
                    if (UserController.checkCredentials(username, password)) {
                        dispose();
                        User u = new User(username);
                        UserHomePage userHomePage = new UserHomePage(u);
                        userHomePage.display();
                    } else {
                        JOptionPane.showMessageDialog(getParent(), "Wrong Username or Password.");
                    }

                    // create user Home Page
                }

            }
        });

        signUpButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                usernameField.setText("");
                passwordField.setText("");
                titleLabel.setText("Create an account");

                remove(passwordLabel);
                remove(passwordField);
                remove(buttonsPanel);

                GridBagConstraints gbc = new GridBagConstraints();
                gbc.insets = new Insets(10, 10, 10, 10);

                // Email label and field
                emailLabel = new JLabel("Email Address:");
                gbc.gridx = 0;
                gbc.gridy = 2;
                add(emailLabel, gbc);
                emailField = new JTextField(20);
                gbc.gridx = 1;
                gbc.gridy = 2;
                add(emailField, gbc);

                gbc.gridx = 0;
                gbc.gridy = 3;
                add(passwordLabel, gbc);
                gbc.gridx = 1;
                gbc.gridy = 3;
                add(passwordField, gbc);

                gbc.gridx = 0;
                gbc.gridy = 5;
                gbc.gridwidth = 4;
                add(buttonsPanel, gbc);

                buttonsPanel.removeAll();
                buttonsPanel.add(createButton);
                buttonsPanel.add(Box.createRigidArea(new Dimension(0, 10)));
                buttonsPanel.add(backButton);
                buttonsPanel.add(Box.createRigidArea(new Dimension(0, 10)));
                buttonsPanel.add(exitButton);
            }
        });

        createButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                username = usernameField.getText();
                email = emailField.getText();
                password = String.valueOf(passwordField.getPassword());
                // Validation for proper username only letters and numbers
                String usernamePattern = "^[a-zA-Z0-9]+$";
                // Validation for proper email address
                String emailPattern = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";


                if (username.compareTo("") == 0 || email.compareTo("") == 0 || password.compareTo("") == 0) {
                    JOptionPane.showMessageDialog(getParent(),
                            "Please enter your Username, Email Address, and Password.", "DIYControl",
                            JOptionPane.WARNING_MESSAGE);
                } else {

                    //could use just the model but will work for now
                    if (UserController.userExists(username)) {
                        JOptionPane.showMessageDialog(getParent(), "Username already exists. Please try again.");
                    } else {
                        if(username.matches(usernamePattern) && email.matches(emailPattern)){

                        try {
                            //uses user controller for now but
                            UserController.createUser(username, email, password);
                            JOptionPane.showMessageDialog(getParent(), "Account Created Successfully!");
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }}
                        else { JOptionPane.showMessageDialog(getParent(), "Invalid Username/Email. Please try again.");}
                    }
                }

            }
        });

        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                usernameField.setText("");
                emailField.setText("");
                passwordField.setText("");

                titleLabel.setText("Welcome to DIY Control");

                remove(emailField);
                remove(emailLabel);
                remove(passwordLabel);
                remove(passwordField);
                remove(buttonsPanel);

                GridBagConstraints gbc = new GridBagConstraints();
                gbc.insets = new Insets(10, 10, 10, 10);

                gbc.gridx = 0;
                gbc.gridy = 2;
                add(passwordLabel, gbc);
                gbc.gridx = 1;
                gbc.gridy = 2;
                add(passwordField, gbc);

                gbc.gridx = 0;
                gbc.gridy = 4;
                gbc.gridwidth = 4;
                add(buttonsPanel, gbc);

                buttonsPanel.removeAll();
                buttonsPanel.add(loginButton);
                buttonsPanel.add(Box.createRigidArea(new Dimension(0, 10)));
                buttonsPanel.add(signUpButton);
                buttonsPanel.add(Box.createRigidArea(new Dimension(0, 10)));
                buttonsPanel.add(exitButton);
                revalidate();
                repaint();
            }
        });

        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

    }

    /**
     * Set up the necessary fields.
     */
    private void setup() {
        titleLabel = new JLabel("Welcome to DIYControl");
        titleLabel.setFont(new Font("", Font.BOLD, 24));

        usernameLabel = new JLabel("Username:");
        usernameField = new JTextField(20);
        emailLabel = new JLabel("Email Address:");
        emailField = new JTextField(20);
        passwordLabel = new JLabel("Password:");
        passwordField = new JPasswordField(20);

        buttonsPanel = new JPanel();
        loginButton = new JButton("Login");
        signUpButton = new JButton("Sign Up");
        backButton = new JButton("Back");
        exitButton = new JButton("Exit");
        createButton = new JButton("Create");

        // Setting buttons' size to the same size
        Dimension buttonSize = new Dimension(100, 50);
        signUpButton.setMaximumSize(buttonSize);
        loginButton.setMaximumSize(buttonSize);
        backButton.setMaximumSize(buttonSize);
        exitButton.setMaximumSize(buttonSize);
        createButton.setMaximumSize(buttonSize);
    }
}