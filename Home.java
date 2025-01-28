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

        // Sidebar panel
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new GridLayout(6, 1, 0, 0)); // No gaps between buttons
        sidebar.setBackground(new Color(25, 120, 220)); // Slightly darker blue for contrast

        // Add buttons to the sidebar
        addSidebarButton(sidebar, "Flight Details", "View details about flights", this);
        addSidebarButton(sidebar, "Add Customer Details", "Add new customer information", this);
        addSidebarButton(sidebar, "Book Flight", "Book a flight for customers", this);
        addSidebarButton(sidebar, "Journey Details", "View journey details", this);
        addSidebarButton(sidebar, "Cancel Ticket", "Cancel a flight ticket", this);
        addSidebarButton(sidebar, "Boarding Pass", "Generate a boarding pass", this);

        // Add sidebar to the frame
        add(sidebar, BorderLayout.WEST);

        // Top bar with user info
        JPanel topBar = new JPanel();
        topBar.setLayout(new BorderLayout());
        topBar.setBackground(new Color(230, 230, 250)); // Light lavender
        topBar.setPreferredSize(new Dimension(1200, 80)); // Increased height of the top bar

        // Logged-in user label
        loggedInUserLabel = new JLabel("Logged in as: " + UserSession.loggedInUser, JLabel.RIGHT);
        loggedInUserLabel.setFont(new Font("Tahoma", Font.PLAIN, 16)); // Slightly larger font
        loggedInUserLabel.setForeground(new Color(70, 130, 180)); // Steel blue
        loggedInUserLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 20)); // Padding
        topBar.add(loggedInUserLabel, BorderLayout.CENTER);

        // Add top bar to the frame
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

        // Add main panel to the frame
        add(mainPanel, BorderLayout.CENTER);

        // Bottom panel for the Back button
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new FlowLayout(FlowLayout.RIGHT)); // Align to the bottom-right
        bottomPanel.setBackground(new Color(230, 230, 250)); // Light lavender
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Padding

        // Back button
        JButton backButton = new JButton("Back");
        backButton.setFont(new Font("Tahoma", Font.PLAIN, 16)); // Larger font
        backButton.setBackground(new Color(30, 144, 255)); // Same blue as sidebar buttons
        backButton.setForeground(Color.WHITE);
        backButton.setFocusPainted(false);
        backButton.setBorder(new LineBorder(new Color(25, 120, 220), 1)); // Border for contrast
        backButton.setPreferredSize(new Dimension(120, 40)); // Larger button size
        backButton.addActionListener(e -> {
            dispose(); // Close the current window
            new Front(); // Redirect to the front screen (or whatever screen you want)
        });
        bottomPanel.add(backButton);

        // Add bottom panel to the frame
        add(bottomPanel, BorderLayout.SOUTH);

        // Set frame to be visible
        setLocationRelativeTo(null); // Center the window
        setVisible(true);
    }

    // Helper method to add buttons to the sidebar
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

    // Action listener for sidebar buttons
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

    // Main method for testing
    public static void main(String[] args) {
        // Set a default logged-in user for testing
        UserSession.loggedInUser = "testuser"; 
        new Home();
    }
}
