package com.example;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;
import com.toedter.calendar.JCalendar;

public class App extends JFrame implements ActionListener {
    private String selectedDate;
    private JTextArea summary, ootd, drugs, weather, wake, eats, sweets, album, achievements;

    public App() {
        final File journalsFolder = new File("C:\\Users\\Sid-Gaming\\Desktop\\docsclone\\docsclone");
        if (!journalsFolder.exists()) {
            journalsFolder.mkdirs();
        }
        // Create GUI with calendar and buttons
        
        JPanel calendarPanel = new JPanel(new BorderLayout());
        final JCalendar calendar = new JCalendar();
        calendar.setBackground(Color.BLACK);
        calendarPanel.add(calendar, BorderLayout.CENTER);
        JButton newJournalButton = new JButton("Create new Journal");
        final JButton viewEditButton = new JButton("View/Edit an Entry");
        JButton newButton = new JButton("New");
        JPanel buttonPanel = new JPanel(new GridLayout(1, 2));
        buttonPanel.add(viewEditButton);
        buttonPanel.add(newButton);
        calendarPanel.add(buttonPanel, BorderLayout.SOUTH);
        add(calendarPanel, BorderLayout.CENTER);
        // Add action listeners to buttons and calendar
        // When the user clicks the New button, prompt the user to select a date for the
        // journal entry
        // Create a button that adds a bullet point to the currently focused text field

        newButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Create a date picker dialog
                JCalendar calendar = new JCalendar();
                int result = JOptionPane.showConfirmDialog(null, calendar, "Select a date",
                        JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
                if (result == JOptionPane.OK_OPTION) {
                    Date selectedDate = calendar.getDate();
                    SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd, yyyy");
                    final String formattedDate = dateFormat.format(selectedDate);
                    final JTextArea summaryField = new JTextArea(5, 20);
                    final JTextArea ootdField = new JTextArea(5, 20);
                    final JTextArea drugsField = new JTextArea(5, 20);
                    final JTextArea weatherField = new JTextArea(5, 20);
                    final JTextArea wakeField = new JTextArea(5, 20);
                    final JTextArea eatsField = new JTextArea(5, 20);
                    final JTextArea sweetsField = new JTextArea(5, 20);
                    final JTextArea albumField = new JTextArea(5, 20);
                    final JTextArea achievementsField = new JTextArea(5, 20);
                    final JButton saveButton = new JButton("Save");
                    JButton bulletButton = new JButton("•");
                    bulletButton.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            // Get the current cursor position in the text area
                            int position = summaryField.getCaretPosition();

                            // Insert a bullet point at the current cursor position
                            summaryField.insert("• ", position);
                        }
                    });
                    // Add the tab key listener to the summary field
                    summaryField.addKeyListener(new KeyAdapter() {
                        public void keyPressed(KeyEvent e) {
                            if (e.getKeyCode() == KeyEvent.VK_TAB) {
                                // Get the current cursor position in the text area
                                int position = summaryField.getCaretPosition();

                                // Insert 15 spaces at the current cursor position
                                StringBuilder spaces = new StringBuilder();
                                for (int i = 0; i < 15; i++) {
                                    spaces.append(" ");
                                }
                                summaryField.insert(spaces.toString(), position);
                                

                                // Consume the tab key event to prevent default behavior
                                e.consume();
                            
                            }
                        }
                    });

                    // Add the bullet point button to the form panel

                    JPanel form = new JPanel(new GridLayout(11, 1));
                    form.add(new JLabel("Date:\n"));
                    form.add(new JLabel(formattedDate));
                    form.add(new JLabel("Summary:"));
                    form.add(new JScrollPane(summaryField));
                    form.add(new JLabel("OOTD:"));
                    form.add(new JScrollPane(ootdField));
                    form.add(new JLabel("Drugs used:"));
                    form.add(new JScrollPane(drugsField));
                    form.add(new JLabel("Weather:"));
                    form.add(new JScrollPane(weatherField));
                    form.add(new JLabel("Wake up time:"));
                    form.add(new JScrollPane(wakeField));
                    form.add(new JLabel("Eats:"));
                    form.add(new JScrollPane(eatsField));
                    form.add(new JLabel("Sweets:"));
                    form.add(new JScrollPane(sweetsField));
                    form.add(new JLabel("Album of the day:"));
                    form.add(new JScrollPane(albumField));
                    form.add(new JLabel("Achievements:"));
                    form.add(new JScrollPane(achievementsField));
                    form.add(saveButton);
                    form.add(bulletButton);
                    saveButton.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            if (e.getSource() == saveButton) {
                                // Save the journal entry to a PDF file
                                try {
                                    Font font = new Font(Font.SERIF, Font.PLAIN, 13);
                                    // Create a new PDF document
                                    Document document = new Document();
                                    String fileName = "Journal Entry " + formattedDate + ".pdf";
                                    PdfWriter.getInstance(document, new FileOutputStream(fileName));
                                    document.open();

                                    // Add the user input to the PDF document
                                    document.add(new Paragraph("Date: " + formattedDate));
                                    document.add(new Paragraph("Summary: " + summaryField.getText()));
                                    document.add(new Paragraph("OOTD: " + ootdField.getText()));
                                    document.add(new Paragraph("Drugs used: " + drugsField.getText()));
                                    document.add(new Paragraph("Weather: " + weatherField.getText()));
                                    document.add(new Paragraph("Wake up time: " + wakeField.getText()));
                                    document.add(new Paragraph("Eats: " + eatsField.getText()));
                                    document.add(new Paragraph("Sweets: " + sweetsField.getText()));
                                    document.add(new Paragraph("Album of the day: " + albumField.getText()));
                                    document.add(new Paragraph("Achievements: " + achievementsField.getText()));

                                    // Close the PDF document
                                    document.close();

                                    JOptionPane.showMessageDialog(null, "Journal entry saved to " + fileName);
                                } catch (FileNotFoundException | DocumentException ex) {
                                    ex.printStackTrace();
                                }
                            }

                        }

                    });
                    form.add(saveButton);

                    JFrame journalPage = new JFrame(formattedDate);
                    journalPage.add(form);
                    journalPage.pack();
                    journalPage.setLocationRelativeTo(null);
                    journalPage.setVisible(true);
                }
            }
        });

        // Initialize form fields
        summary = new JTextArea();
        ootd = new JTextArea();
        drugs = new JTextArea();
        weather = new JTextArea();
        wake = new JTextArea();
        eats = new JTextArea();
        sweets = new JTextArea();
        album = new JTextArea();
        achievements = new JTextArea();

        // When the user clicks on a date in the calendar, display the summary of the
        // journal entry named after that date
        calendar.getDayChooser().addPropertyChangeListener("day", new PropertyChangeListener() {
            public void propertyChange(PropertyChangeEvent e) {
                int day = (Integer) e.getNewValue();
                final JCalendar calendar = new JCalendar();
                String fileName = String.format("%tF.txt", calendar.getCalendar());
                File file = new File(fileName);
                if (file.exists()) {
                    try {
                        BufferedReader reader = new BufferedReader(new FileReader(file));
                        String line;
                        while ((line = reader.readLine()) != null) {
                            if (line.startsWith("Summary:")) {
                                summary.setText(line.substring(8));
                            } else if (line.startsWith("OOTD:")) {
                                ootd.setText(line.substring(5));
                            } else if (line.startsWith("Drugs used:")) {
                                drugs.setText(line.substring(12));
                            } else if (line.startsWith("Weather:")) {
                                weather.setText(line.substring(9));
                            } else if (line.startsWith("Wake:")) {
                                wake.setText(line.substring(6));
                            } else if (line.startsWith("Eats:")) {
                                eats.setText(line.substring(6));
                            } else if (line.startsWith("Sweets:")) {
                                sweets.setText(line.substring(8));
                            } else if (line.startsWith("Album of the day:")) {
                                album.setText(line.substring(17));
                            } else if (line.startsWith("Achievements:")) {
                                achievements.setText(line.substring(14));
                            }
                        }
                        reader.close();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                } else {
                    summary.setText("");
                    ootd.setText("");
                    drugs.setText("");
                    weather.setText("");
                    wake.setText("");
                    eats.setText("");
                    sweets.setText("");
                    album.setText("");
                    achievements.setText("");
                }
            }
        });

        // Declare the calendar variable as final outside of the actionPerformed method

        // Add action listener to view/edit button
        viewEditButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Get the selected date from the calendar
                Date selectedDate = calendar.getDate();
                SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd, yyyy");
                final String formattedDate = dateFormat.format(selectedDate);

                // Check if a journal entry exists for the selected date
                File journalEntryFile = new File("Journal Entry " + formattedDate + ".pdf");
                if (!journalEntryFile.exists()) {
                    JOptionPane.showMessageDialog(null, "No entry found for " + formattedDate);
                    return;
                }

                // Prompt the user to view the summary
                int result = JOptionPane.showConfirmDialog(null,
                        "Entry found for " + formattedDate + ". Would you like to view the summary?", "View Summary",
                        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (result == JOptionPane.NO_OPTION) {
                    return;
                }

                // Read the summary from the journal entry file
                try (InputStream inputStream = new FileInputStream(journalEntryFile)) {
                    PdfReader reader = new PdfReader(inputStream);
                    String summary = PdfTextExtractor.getTextFromPage(reader, 1);

                    // Create a new JFrame instance and add the summary text area to it
                    JFrame summaryFrame = new JFrame("Summary for " + formattedDate);
                    JTextArea summaryField = new JTextArea(summary, 20, 40);
                    summaryField.setEditable(false);
                    JScrollPane scrollPane = new JScrollPane(summaryField);
                    summaryFrame.add(scrollPane);

                    // Set the size of the JFrame to a larger size than the default size
                    summaryFrame.setSize(800, 600);
                    summaryFrame.setVisible(true);
                } catch (IOException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Error reading journal entry file: " + ex.getMessage(), "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    public static void main(String[] args) {
        App app = new App();
        app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        app.pack();
        app.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'actionPerformed'");
    }
}