package csedu.flight.mangement.system;


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Front extends JFrame implements ActionListener {

    JButton homeButton, ReviewButton, faqsButton, logoutButton, profileButton;

    public Front() {
        setTitle("Front Screen");
        setSize(1200, 750);
        setLocationRelativeTo(null); 
        setLayout(null);
        setResizable(false);

       
        JPanel topPanel = new JPanel();
        topPanel.setBackground(new Color(240, 248, 255));
        topPanel.setBounds(0, 0, 1200, 60); 
        topPanel.setLayout(null);
        add(topPanel);

        logoutButton = new JButton("Logout");
        logoutButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        logoutButton.setBackground(new Color(255, 69, 0)); // Red color 
        logoutButton.setForeground(Color.WHITE);
        logoutButton.setBounds(1050, 10, 120, 40); 
        logoutButton.addActionListener(this);
        logoutButton.setFocusPainted(false);
        topPanel.add(logoutButton);

   
        profileButton = new JButton("Profile");
        profileButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        profileButton.setBackground(new Color(0, 153, 76)); // Green color 
        profileButton.setForeground(Color.WHITE);
        profileButton.setBounds(910, 10, 120, 40); 
        profileButton.addActionListener(this);
        profileButton.setFocusPainted(false);
        topPanel.add(profileButton);

        
        ImageIcon backgroundImage = new ImageIcon("C:\\Users\\Ahnaf\\Documents\\NetBeansProjects\\AirlineManagementSystem\\src\\airlinemanagementsystem\\icons\\sma.jpg"); // Update with your JPG file path
        Image scaledImage = backgroundImage.getImage().getScaledInstance(1200, 690, Image.SCALE_DEFAULT);
        JLabel background = new JLabel(new ImageIcon(scaledImage));
        background.setBounds(0, 60, 1200, 690); 
        background.setLayout(null);
        add(background);

        
        JLabel welcomeMessage = new JLabel("Welcome to CSEDU AIRLINES");
        welcomeMessage.setFont(new Font("Brush Script MT", Font.BOLD, 45)); 
        welcomeMessage.setForeground(new Color(255, 255, 255));
        welcomeMessage.setBounds(50, 120, 600, 50); 
        background.add(welcomeMessage);

        
        homeButton = new JButton("Home");
        homeButton.setBounds(50, 320, 150, 40); 
        homeButton.setBackground(new Color(0, 102, 102));
        homeButton.setForeground(Color.WHITE);
        homeButton.setFont(new Font("Segoe UI", Font.BOLD, 16)); 
        homeButton.addActionListener(this);
        homeButton.setFocusPainted(false);
        background.add(homeButton);

      
        ReviewButton = new JButton("Review");
        ReviewButton.setBounds(50, 380, 150, 40); 
        ReviewButton.setBackground(new Color(0, 102, 102));
        ReviewButton.setForeground(Color.WHITE);
        ReviewButton.setFont(new Font("Segoe UI", Font.BOLD, 16)); 
        ReviewButton.addActionListener(this);
        ReviewButton.setFocusPainted(false);
        background.add(ReviewButton);

        
        faqsButton = new JButton("FAQs");
        faqsButton.setBounds(50, 440, 150, 40); 
        faqsButton.setBackground(new Color(0, 102, 102));
        faqsButton.setForeground(Color.WHITE);
        faqsButton.setFont(new Font("Segoe UI", Font.BOLD, 16)); 
        faqsButton.addActionListener(this);
        faqsButton.setFocusPainted(false);
        background.add(faqsButton);

        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == homeButton) {
            this.dispose();
            new Home();

        } else if (ae.getSource() == ReviewButton) {
            new Review(); 
        } else if (ae.getSource() == faqsButton) {
            Client.startClient(); 
        } else if (ae.getSource() == logoutButton) {
            
            int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to logout?", "Logout", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                UserSession.loggedInUser = null; 
                this.dispose(); 
                new Login();
            }
        } else if (ae.getSource() == profileButton) {
            new Profile(); 
        }
    }

    public static void main(String[] args) {
        
        UserSession.loggedInUser = "Test"; 
        new Front();
    }
}


