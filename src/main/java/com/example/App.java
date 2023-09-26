package com.example;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.util.Date;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;
import com.toedter.calendar.JCalendar;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;
import java.text.SimpleDateFormat;

public class App extends JFrame {
    // Buttons
    private JButton createNewFileButton, openOldFileButton, viewFileButton;
    // Text area for displaying the summary
    private JTextArea summaryArea;

    public App() {
        setTitle("Journaling App");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 500);
        // Center the window on the screen
        setLocationRelativeTo(null);
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        // Create the calendar component
        final JCalendar calendar = new JCalendar();
        add(calendar);

        // Create text fields for summary, OOTD, and other information
        final JTextField summaryField = new JTextField(20);
        final JTextField ootdField = new JTextField(20);
        final JTextField drugsField = new JTextField(20);
        final JTextField weatherField = new JTextField(20);
        final JTextField wakeField = new JTextField(20);
        final JTextField eatsField = new JTextField(20);
        final JTextField sweetsField = new JTextField(20);
        final JTextField albumotdField = new JTextField(20);
        final JTextField achievementsField = new JTextField(20);

        // Create three buttons
        createNewFileButton = new JButton("Create New File");
        openOldFileButton = new JButton("Open Old File");
        viewFileButton = new JButton("View/Edit File");

        createNewFileButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // get the selected date from the calendar
                Date selectedDate = calendar.getDate();
                SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd, yyyy");
                final String fileName = dateFormat.format(selectedDate) + ".pdf";

                try {
                    // Create a new PDF document and save it in the journals folder
                    Document document = new Document();
                    PdfWriter.getInstance(document, new FileOutputStream(fileName));
                    document.open();
                    document.add(new Paragraph(dateFormat.format(selectedDate)));
                    document.add(new Paragraph("Summary: " + summaryField.getText()));
                    document.add(new Paragraph("OOTD: " + ootdField.getText()));
                    document.add(new Paragraph("Drugs used: " + drugsField.getText()));
                    document.add(new Paragraph("Weather: " + weatherField.getText()));
                    document.add(new Paragraph("Wake: " + wakeField.getText()));
                    document.add(new Paragraph("Eats: " + eatsField.getText()));
                    document.add(new Paragraph("Sweets: " + sweetsField.getText()));
                    document.add(new Paragraph("Album of the day: " + albumotdField.getText()));
                    document.add(new Paragraph("Achievements: " + achievementsField.getText()));
                    document.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        openOldFileButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // handle open old file button click
            }
        });

        viewFileButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // get the selected date from the calendar
                Date selectedDate = calendar.getDate();
                
                SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd, yyyy");
                final String fileName = dateFormat.format(selectedDate) + ".pdf";
                File file = new File(fileName);
                if (file.exists()) {
                    try {
                        // Extract the text from the PDF file
                        PdfReader reader = new PdfReader(fileName);
                        final String text = PdfTextExtractor.getTextFromPage(reader, 1);
                        reader.close();
                        // Create a new window with the contents of the PDF file
                        final JFrame editFrame = new JFrame(dateFormat.format(selectedDate));
                        editFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                        editFrame.setSize(500, 500);
                        editFrame.setLocationRelativeTo(null);
                        editFrame.setLayout(new BoxLayout(editFrame.getContentPane(), BoxLayout.Y_AXIS));
                        final JTextArea editArea = new JTextArea(text);
                        JScrollPane scrollPane = new JScrollPane(editArea);
                        editFrame.add(scrollPane);
                        JButton saveButton = new JButton("Save");
                        saveButton.addActionListener(new ActionListener() {
                            public void actionPerformed(ActionEvent e) {
                                PdfStamper stamper = null;
                                try {
                                    stamper = new PdfStamper(new PdfReader(fileName), new FileOutputStream(fileName));
                                    stamper.getAcroFields().setField("text", editArea.getText());
                                } catch (IOException | DocumentException ex) {
                                    ex.printStackTrace();
                                } finally {
                                    if (stamper != null) {
                                        try {
                                            stamper.close();
                                        } catch (DocumentException | IOException ex) {
                                            ex.printStackTrace();
                                        }
                                    }
                                }
                            }
                        });
                        editFrame.add(saveButton);
                        editFrame.setVisible(true);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "No entry found on" + dateFormat.format(selectedDate),
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Add the text fields and buttons to the panel
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(10, 2));
        inputPanel.add(new JLabel("Summary:"));
        inputPanel.add(summaryField);
        inputPanel.add(new JLabel("OOTD:"));
        inputPanel.add(ootdField);
        inputPanel.add(new JLabel("Drugs used:"));
        inputPanel.add(drugsField);
        inputPanel.add(new JLabel("Weather:"));
        inputPanel.add(weatherField);
        inputPanel.add(new JLabel("Wake:"));
        inputPanel.add(wakeField);
        inputPanel.add(new JLabel("Eats:"));
        inputPanel.add(eatsField);
        inputPanel.add(new JLabel("Sweets:"));
        inputPanel.add(sweetsField);
        inputPanel.add(new JLabel("Album of the day:"));
        inputPanel.add(albumotdField);
        inputPanel.add(new JLabel("Achievements:"));
        inputPanel.add(achievementsField);
        add(inputPanel);

        // Create the summary area
        summaryArea = new JTextArea(5, 20);
        summaryArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(summaryArea);
        add(scrollPane);

        // Add the buttons to the panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(createNewFileButton);
        buttonPanel.add(openOldFileButton);
        buttonPanel.add(viewFileButton);
        add(buttonPanel);

        // Add a listener to the calendar component to update the summary area
        calendar.addPropertyChangeListener("calendar", new PropertyChangeListener() {
            public void propertyChange(PropertyChangeEvent e) {
                Date selectedDate = calendar.getDate();
                SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd, yyyy");
                final String fileName = dateFormat.format(selectedDate) + ".pdf";
                try {
                    // Extract the text from the PDF file
                    PdfReader reader = new PdfReader(fileName);
                    String text = PdfTextExtractor.getTextFromPage(reader, 1);
                    reader.close();
                    // Search for the summary in the text
                    int summaryIndex = text.indexOf("Summary:");
                    if (summaryIndex != -1) {
                        int endIndex = text.indexOf("\n", summaryIndex);
                        String summary = text.substring(summaryIndex + 9, endIndex);
                        summaryArea.setText(summary);
                    } else {
                        summaryArea.setText("No summary written on date.");
                    }
                } catch (Exception ex) {
                    summaryArea.setText("No summary written on date.");
                }
            }
        });

        // Show the window
        setVisible(true);
    }

    public static void main(String[] args) {
        App app = new App();
    }

    class CreateNewFileButton extends JFrame implements ActionListener {
        // Declare var gui components
        private JLabel wakeLabel, eatsLabel, sweetsLabel, albumotdLabel, dateLabel, summaryLabel, ootdLabel, drugsLabel,
                weatherLabel, achievementsLabel;
        private JTextField wakeField, eatsField, sweetsField, albumotdField, summaryField, ootdField, drugsField,
                weatherField, achievementsField;
        private JButton saveButton, closeButton;

        public CreateNewFileButton() {
            setTitle("Create New File");
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            setSize(500, 500);
            setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
            // Center the window on the screen
            setLocationRelativeTo(null);

            // initialize gui components
            dateLabel = new JLabel("Date: " + new Date().toString());
            summaryLabel = new JLabel("Summary:");
            ootdLabel = new JLabel("OOTD:");
            drugsLabel = new JLabel("Drugs used:");
            weatherLabel = new JLabel("Weather:");
            achievementsLabel = new JLabel("Achievements:");
            eatsLabel = new JLabel("Eats:");
            sweetsLabel = new JLabel("Sweets:");
            albumotdLabel = new JLabel("Album of the day:");
            wakeLabel = new JLabel("Wake:");

            summaryField = new JTextField(30);
            ootdField = new JTextField(30);
            drugsField = new JTextField(30);
            weatherField = new JTextField(30);
            achievementsField = new JTextField(30);
            eatsField = new JTextField(30);
            sweetsField = new JTextField(30);
            albumotdField = new JTextField(30);
            wakeField = new JTextField(30);

            saveButton = new JButton("Save and Close");
            closeButton = new JButton("Close without Saving");

            // action listeners for buttons
            saveButton.addActionListener(this);
            closeButton.addActionListener(this);

            // add gui components to frame
            add(dateLabel);
            add(new JLabel("")); // empty label for space
            add(summaryLabel);
            add(summaryField);
            add(ootdLabel);
            add(ootdField);
            add(drugsLabel);
            add(drugsField);
            add(weatherLabel);
            add(weatherField);
            add(wakeLabel);
            add(wakeField);
            add(eatsLabel);
            add(eatsField);
            add(sweetsLabel);
            add(sweetsField);
            add(albumotdLabel);
            add(albumotdField);
            add(achievementsLabel);
            add(achievementsField);
            add(saveButton);
            add(closeButton);

            setVisible(true);
        }

        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == saveButton) {
                try {
                    // Set the directory to the project directory
                    String directory = System.getProperty("user.dir") + "\\journals\\";
                    File journalsFolder = new File(directory + "journals");
                    if (!journalsFolder.exists()) {
                        journalsFolder.mkdir();
                    }
                    // Create a new PDF document and save it in the journals folder
                    Document document = new Document();
                    PdfWriter.getInstance(document, new FileOutputStream("journal" + ".pdf"));
                    document.open();
                    document.add(new Paragraph(dateLabel.getText()));
                    document.add(new Paragraph("Summary: " + summaryField.getText()));
                    document.add(new Paragraph("OOTD: " + ootdField.getText()));
                    document.add(new Paragraph("Drugs used: " + drugsField.getText()));
                    document.add(new Paragraph("Weather: " + weatherField.getText()));
                    document.add(new Paragraph("Wake: " + wakeField.getText()));
                    document.add(new Paragraph("Eats: " + eatsField.getText()));
                    document.add(new Paragraph("Sweets: " + sweetsField.getText()));
                    document.add(new Paragraph("Album of the day: " + albumotdField.getText()));
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