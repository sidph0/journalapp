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

        createNewFileButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new CreateNewFileButton();
            }
        });

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
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            setSize(500, 500);
            setLayout(new GridLayout(8, 2));
            // Center the window on the screen
            setLocationRelativeTo(null);

            titleLabel = new JLabel("Title:");
            dateLabel = new JLabel("Date: " + new Date().toString());
            summaryLabel = new JLabel("Summary:");
            ootdLabel = new JLabel("OOTD:");
            drugsLabel = new JLabel("Drugs used:");
            weatherLabel = new JLabel("Weather:");
            achievementsLabel = new JLabel("Achievements:");

            titleField = new JTextField();
            summaryField = new JTextField();
            ootdField = new JTextField();
            drugsField = new JTextField();
            weatherField = new JTextField();
            achievementsField = new JTextField();

            saveButton = new JButton("Save and Close");
            closeButton = new JButton("Close without Saving");

            saveButton.addActionListener(this);
            closeButton.addActionListener(this);

            add(titleLabel);
            add(titleField);
            add(dateLabel);
            add(new JLabel(""));
            add(summaryLabel);
            add(summaryField);
            add(ootdLabel);
            add(ootdField);
            add(drugsLabel);
            add(drugsField);
            add(weatherLabel);
            add(weatherField);
            add(achievementsLabel);
            add(achievementsField);
            add(saveButton);
            add(closeButton);

            setVisible(true);
        }

        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == saveButton) {
                try {

                    String directory = System.getProperty("user.dir") + "\\journals\\";
                    File journalsFolder = new File(directory + "journals");
                    if (!journalsFolder.exists()) {
                        journalsFolder.mkdir();
                    }
                    Document document = new Document();
                    PdfWriter.getInstance(document, new FileOutputStream(titleField.getText() + ".pdf"));
                    document.open();
                    document.add(new Paragraph(dateLabel.getText()));
                    document.add(new Paragraph("Summary: " + summaryField.getText()));
                    document.add(new Paragraph("OOTD: " + ootdField.getText()));
                    document.add(new Paragraph("Drugs used: " + drugsField.getText()));
                    document.add(new Paragraph("Weather: " + weatherField.getText()));
                    document.add(new Paragraph("Achievements: " + achievementsField.getText()));
                    document.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                dispose();
            } else if (e.getSource() == closeButton) {
                dispose();
            }
        }
    }
}