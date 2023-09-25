package com.example;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.util.Date;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;

public class App extends JFrame {
    // Buttons
    private JButton createNewFileButton, openOldFileButton;

    public App() {
        setTitle("Journaling App");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 500);
        // Center the window on the screen
        setLocationRelativeTo(null);
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        // Create two buttons
        createNewFileButton = new JButton("Create New File");
        openOldFileButton = new JButton("Open Old File");

        add(createNewFileButton);
        add(openOldFileButton);

        setVisible(true);
    }

    public static void main(String[] args) {
        new App();
    }

    class CreateNewFileButton extends JFrame implements ActionListener {
        private JLabel titleLabel, dateLabel, summaryLabel, ootdLabel, drugsLabel, weatherLabel, achievementsLabel;
        private JTextField titleField, summaryField, ootdField, drugsField, weatherField, achievementsField;
        private JButton saveButton, closeButton;

        public CreateNewFileButton() {
            setTitle("Create New File");
        }
        public void actionPerformed(ActionEvent e) {
            
        }
    }
}