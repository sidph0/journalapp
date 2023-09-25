package com.example;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;


public class App extends JFrame {
    // Buttons
    private JButton createNewFileButton, openOldFileButton;

    public App() {
        setTitle("Journaling App");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 500);
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        createNewFileButton = new JButton("Create New File");
        openOldFileButton = new JButton("Open Old File");

        add(createNewFileButton);
        add(openOldFileButton);

        setVisible(true);
    }

    public static void main(String[] args) {
        new App();
    }

}