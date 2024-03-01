package tcss360.diybuilder.ui;

import tcss360.diybuilder.models.Project;
import tcss360.diybuilder.models.User;

import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * GUI for notepage
 * @author Mahiliet Awasso
 * Editor/contributor: Soe
 */
public class NotesPage extends JFrame {

    //datafield
    private JTextArea textArea;
    private JScrollPane scrollPane;
    private JMenuBar menuBar;
    private JMenu menuFile;
    private JMenuItem iSave, iOpen;

    //current user and project
    private static User currentUser;
    private static Project currentProject;
    private String previousNote;


    /**
     * constructor for notes page
     * @param user currently signed in user
     * @param project currently signed in project
     */
    public NotesPage(User user, Project project) {
        super("Note");
        this.currentUser = user;
        this.currentProject = project;

        //create the window
        createWindow();
        createTextArea();
        createMenuBar();
        //createFileMenu();

    }

    public void display(){
        this.setVisible(true);
    }
    /**
     * sets up the ovarall frame
     * @author Mahiliet Awasso
     */
    public void createWindow() {

        this.setSize(500, 500);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setLayout(new BorderLayout());
    }


    /**
     * create text area
     * @author Mahiliet Awasso
     */
    public void createTextArea() {
        textArea = new JTextArea();
        scrollPane = new JScrollPane(textArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        this.add(scrollPane);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        textArea.setText(currentProject.getNote());
        previousNote = textArea.getText();
        //this.add(textArea);
    }

    /**
     * create the menu bar on the top left
     * @author Mahiliet Awasso
     */
    public void createMenuBar() {
        menuBar = new JMenuBar();
        this.setJMenuBar(menuBar);

        menuFile = new JMenu("File");
        JButton saveButton = new JButton("Save");
        saveButton.setFocusable(false);
        menuBar.add(saveButton);
        //menuBar.add(menuFile);

        // Add a home button to go back to the user's home page
        menuBar.add(Box.createHorizontalGlue());
        JButton homeButton = new JButton("Home");
        JButton backButton = new JButton("Back");
        homeButton.setFocusable(false);
        backButton.setFocusable(false);
        this.getContentPane().add(homeButton, BorderLayout.NORTH);
        menuBar.add(backButton);
        menuBar.add(homeButton);
        homeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!previousNote.equals(textArea.getText())) {
                    int result = JOptionPane.showConfirmDialog(getParent(), "Do you want to save current Note?",
                            "Save Note", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
                    if (result == JOptionPane.OK_OPTION) {
                        String noteContent = textArea.getText();
                        currentProject.setNote(noteContent);
                        JOptionPane.showMessageDialog(getParent(), "Notes saved successfully!");
                    }
                }
                dispose();
                UserHomePage userHomePage = new UserHomePage(currentUser);
                userHomePage.display();
            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!previousNote.equals(textArea.getText())) {
                    int result = JOptionPane.showConfirmDialog(getParent(), "Do you want to save current Note?",
                            "Save Note", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
                    if (result == JOptionPane.OK_OPTION) {
                        String noteContent = textArea.getText();
                        currentProject.setNote(noteContent);
                        JOptionPane.showMessageDialog(getParent(), "Notes saved successfully!");
                    }
                }
                dispose();
                ProjectPage p = new ProjectPage(currentProject, currentUser);
                p.display();
            }
        });

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String noteContent = textArea.getText();
                previousNote = textArea.getText();
                currentProject.setNote(noteContent);
                JOptionPane.showMessageDialog(getParent(), "Notes saved successfully!");
            }
        });
    }

//    /**
//     * creating a menu item(speciffically the save delete open items)
//     * @author Mahiliet Awasso
//     */
//    public void createFileMenu() {
//        iSave = new JMenuItem("Save");
//        menuFile.add(iSave);
//        iSave.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//
//                String noteContent = textArea.getText();
//                currentProject.setNote(noteContent);
//            }
//        });
//    }
}