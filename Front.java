package csedu.flight.mangement.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Front extends JFrame implements ActionListener {

    JButton homeButton, ReviewButton, faqsButton, logoutButton, profileButton;

    public Front() {
        setTitle("Front Screen");
        setSize(1200, 750); // Updated window size
        setLocationRelativeTo(null); // Center the window on the screen
        setLayout(null);
        setResizable(false);

        // Top Panel for Welcome Message
        JPanel topPanel = new JPanel();
        topPanel.setBackground(new Color(240, 248, 255));
        topPanel.setBounds(0, 0, 1200, 60); // Adjusted width
        topPanel.setLayout(null);
        add(topPanel);

        // Username Label
        JLabel welcomeLabel = new JLabel("User: " + UserSession.loggedInUser); // Display username
        welcomeLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        welcomeLabel.setForeground(new Color(0, 102, 102));
        welcomeLabel.setBounds(850, 15, 200, 30); // Adjusted position to top-right corner
        topPanel.add(welcomeLabel);

        // Logout Button
        logoutButton = new JButton("Logout");
        logoutButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        logoutButton.setBackground(new Color(255, 69, 0)); // Red color for logout
        logoutButton.setForeground(Color.WHITE);
        logoutButton.setBounds(1050, 10, 120, 40); // Increased size to 120x40
        logoutButton.addActionListener(this);
        topPanel.add(logoutButton);

        // Profile Button
        profileButton = new JButton("Profile");
        profileButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        profileButton.setBackground(new Color(0, 153, 76)); // Green color for Profile button
        profileButton.setForeground(Color.WHITE);
        profileButton.setBounds(910, 10, 120, 40); // Below the logout button
        profileButton.addActionListener(this);
        topPanel.add(profileButton);

        // Background Image
        ImageIcon backgroundImage = new ImageIcon("C:\\Users\\saadb\\OneDrive\\Documents\\NetBeansProjects\\CSEDU Flight Mangement System\\src\\csedu\\flight\\mangement\\system\\icons\\sma.jpg"); // Update with your JPG file path
        Image scaledImage = backgroundImage.getImage().getScaledInstance(1200, 690, Image.SCALE_DEFAULT); // Adjusted height
        JLabel background = new JLabel(new ImageIcon(scaledImage));
        background.setBounds(0, 60, 1200, 690); // Adjusted dimensions
        background.setLayout(null);
        add(background);

        // Welcome Message
        JLabel welcomeMessage = new JLabel("Welcome to CSEDU AIRLINES");
        welcomeMessage.setFont(new Font("Brush Script MT", Font.BOLD, 45)); // Artistic cursive font
        welcomeMessage.setForeground(new Color(255, 255, 255)); // White text
        welcomeMessage.setBounds(50, 120, 600, 50); // Centered and above the buttons
        background.add(welcomeMessage);

        // Home Button
        homeButton = new JButton("Home");
        homeButton.setBounds(50, 320, 150, 40); // Smaller size and moved to the left
        homeButton.setBackground(new Color(0, 102, 102));
        homeButton.setForeground(Color.WHITE);
        homeButton.setFont(new Font("Segoe UI", Font.BOLD, 16)); // Slightly smaller font
        homeButton.addActionListener(this);
        background.add(homeButton);

        // About Us Button
        ReviewButton = new JButton("Review");
        ReviewButton.setBounds(50, 380, 150, 40); // Smaller size and moved to the left
        ReviewButton.setBackground(new Color(0, 102, 102));
        ReviewButton.setForeground(Color.WHITE);
        ReviewButton.setFont(new Font("Segoe UI", Font.BOLD, 16)); // Slightly smaller font
        ReviewButton.addActionListener(this);
        background.add(ReviewButton);

        // FAQs Button
        faqsButton = new JButton("FAQs");
        faqsButton.setBounds(50, 440, 150, 40); // Smaller size and moved to the left
        faqsButton.setBackground(new Color(0, 102, 102));
        faqsButton.setForeground(Color.WHITE);
        faqsButton.setFont(new Font("Segoe UI", Font.BOLD, 16)); // Slightly smaller font
        faqsButton.addActionListener(this);
        background.add(faqsButton);

        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == homeButton) {
            this.dispose();
            new Home();

        } else if (ae.getSource() == ReviewButton) {
            new Review(); // Redirect to Review page
        } else if (ae.getSource() == faqsButton) {
            Client.startClient(); // Redirect to FAQs
        } else if (ae.getSource() == logoutButton) {
            // Logout logic
            int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to logout?", "Logout", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                UserSession.loggedInUser = null; // Clear the logged-in user session
                this.dispose(); // Close the current window
                new Login(); // Redirect to the login screen
            }
        } else if (ae.getSource() == profileButton) {
            new Profile(); // Open the Profile window
        }
    }

    public static void main(String[] args) {
        // For testing purposes, set the logged-in user
        UserSession.loggedInUser = "Test"; // Set a default username for testing
        new Front();
    }
}
