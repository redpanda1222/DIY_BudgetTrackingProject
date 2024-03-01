/*
 * Team Periwinkle
 */
package tcss360.diybuilder.ui;
import tcss360.diybuilder.models.User;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * About UI + Object class.
 *
 * @author Soe Lin
 */
public class About extends JFrame {
    /** Version number. */
    private static final String VERSIONNUMBER = " 0.1";
    /** Panel to hold information. */
    private final JPanel myPanel;
    /** User object. */
    private final User user;

    /**
     * Constructor.
     *
     * @param theUser user object.
     */
    public About(User theUser) {
        super("DIYControl");
        myPanel = new JPanel();
        user = theUser;
    }

    /**
     * Set up the GUI and display.
     */
    public void display() {

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(400, 500);
        this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel titleLabel = new JLabel("About");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        this.add(titleLabel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        this.add(myPanel, gbc);

        gbc.insets = new Insets(10, 10, 5, 5);
        JButton backButton = new JButton("Back");
        backButton.setFocusable(false);
        JButton exportButton = new JButton("Export");
        exportButton.setFocusable(false);
        JButton importButton = new JButton("Import");
        importButton.setFocusable(false);

        gbc.gridx = 0;
        gbc.gridy = 2;
        this.add(importButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        this.add(exportButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        this.add(backButton, gbc);

        JLabel line0 = new JLabel("Version Number:" + VERSIONNUMBER);
        JLabel line1 = new JLabel("This app is registered to: " + user.getUserName());
        JLabel line2 = new JLabel("Email address of the user: " + user.getEmail());
        JLabel line3 = new JLabel("This app is provided by Team Periwinkle.");
        JLabel line4 = new JLabel("Members of Team Periwinkle:");
        JLabel line5 = new JLabel("Soe Lin, nickname: redpanda1222");
        JLabel line6 = new JLabel("Alex Garcia, nickname: froabble");
        JLabel line7 = new JLabel("Mahiliet Awasso, nickname: mahi");
        JLabel line8 = new JLabel("Mey Vo, nickname: meyww");
        JLabel line9 = new JLabel("Charmel Mbala, nickname: luckycharms0");

        myPanel.add(line0);
        myPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        myPanel.add(line1);
        myPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        myPanel.add(line2);
        myPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        myPanel.add(line3);
        myPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        myPanel.add(line4);
        myPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        myPanel.add(line5);
        myPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        myPanel.add(line6);
        myPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        myPanel.add(line7);
        myPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        myPanel.add(line8);
        myPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        myPanel.add(line9);
        myPanel.setLayout(new BoxLayout(myPanel, BoxLayout.Y_AXIS));

        // Buttons' action
        importButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                try {
                    user.deserialize();
                } catch (IOException | ClassNotFoundException ex) {
                    throw new RuntimeException(ex);
                }

            }
        });

        exportButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                try {
                    user.serialize();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }

            }
        });

        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //need user Home Page to go back
                dispose();
                UserHomePage userHomePage = new UserHomePage(user);
                userHomePage.display();
            }
        });

        this.setLocationRelativeTo(null);
        this.setVisible(true);

    }
}