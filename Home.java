package airlinemanagementsystem;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.*;

public class Home extends JFrame implements ActionListener {

    JLabel loggedInUserLabel;

    public Home() {
        
        setLayout(new BorderLayout());
        setTitle("Air CSEDU Management System");
        setSize(1200, 850); // Reduced width to 1200
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new GridLayout(6, 1, 0, 0)); // No gaps between buttons
        sidebar.setBackground(new Color(25, 120, 220)); // Slightly darker blue for contrast

       
        addSidebarButton(sidebar, "Flight Details", "View details about flights", this);
        addSidebarButton(sidebar, "Add Customer Details", "Add new customer information", this);
        addSidebarButton(sidebar, "Book Flight", "Book a flight for customers", this);
        addSidebarButton(sidebar, "Journey Details", "View journey details", this);
        addSidebarButton(sidebar, "Cancel Ticket", "Cancel a flight ticket", this);
        addSidebarButton(sidebar, "Boarding Pass", "Generate a boarding pass", this);

        
        add(sidebar, BorderLayout.WEST);

        // Top bar with user info and back button
        JPanel topBar = new JPanel();
        topBar.setLayout(new BorderLayout());
        topBar.setBackground(new Color(230, 230, 250)); // Light lavender

        // Logged-in user label
        loggedInUserLabel = new JLabel("Logged in as: " + UserSession.loggedInUser, JLabel.RIGHT);
        loggedInUserLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
        loggedInUserLabel.setForeground(new Color(70, 130, 180)); // Steel blue
        loggedInUserLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 20));
        topBar.add(loggedInUserLabel, BorderLayout.CENTER);

        // Back button
        JButton backButton = new JButton("Back");
        backButton.setFont(new Font("Tahoma", Font.PLAIN, 14));
        backButton.setBackground(new Color(30, 144, 255)); // Same blue as sidebar buttons
        backButton.setForeground(Color.WHITE);
        backButton.setBorder(new LineBorder(new Color(25, 120, 220), 1)); // Border for contrast
        backButton.addActionListener(e -> {
            setVisible(false); // Hide the current window
            new Front(); // Redirect to the Front screen
        });
        topBar.add(backButton, BorderLayout.EAST);

        add(topBar, BorderLayout.NORTH);

        // Main content panel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);

        // Load and display an image
        ImageIcon imageIcon = new ImageIcon("C:\\Users\\Ahnaf\\Downloads\\asi.jpg"); // Replace with your image path
        JLabel imageLabel = new JLabel(imageIcon);
        imageLabel.setHorizontalAlignment(JLabel.CENTER); // Center the image
        mainPanel.add(imageLabel, BorderLayout.CENTER);

        add(mainPanel, BorderLayout.CENTER);

        // Set frame to be visible
        setLocationRelativeTo(null); // Center the window
        setVisible(true);
    }

    
    private void addSidebarButton(JPanel sidebar, String text, String tooltip, ActionListener listener) {
        JButton button = new JButton(text);
        button.setFont(new Font("Tahoma", Font.PLAIN, 16));
        button.setBackground(new Color(30, 144, 255)); 
        button.setForeground(Color.WHITE); 
        button.setToolTipText(tooltip);
        button.setFocusPainted(false);
        button.setBorder(new LineBorder(new Color(25, 120, 220), 1)); 
        button.addActionListener(listener);
        sidebar.add(button);
    }

    public void actionPerformed(ActionEvent ae) {
        String command = ae.getActionCommand();

        if (command.equals("Flight Details")) {
            new FlightInfo();
        } else if (command.equals("Add Customer Details")) {
            new AddCustomer();
        } else if (command.equals("Book Flight")) {
            new BookFlight();
        } else if (command.equals("Journey Details")) {
            new JourneyDetails();
        } else if (command.equals("Cancel Ticket")) {
            new Cancel();
        } else if (command.equals("Boarding Pass")) {
            new BoardingPass();
        }
    }

    public static void main(String[] args) {
        
        UserSession.loggedInUser = "testuser"; 
        new Home();
    }
}