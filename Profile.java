package csedu.flight.mangement.system;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class Profile extends JFrame {

    public Profile() {
        setTitle("User Profile");
        setSize(500, 400);
        setLocationRelativeTo(null);
        setLayout(null);
        setResizable(false);
        getContentPane().setBackground(new Color(240, 248, 255));

        JLabel titleLabel = new JLabel("User Profile");
        titleLabel.setFont(new Font("Brush Script MT", Font.BOLD, 36));
        titleLabel.setForeground(new Color(0, 102, 102));
        titleLabel.setBounds(150, 20, 200, 50);
        add(titleLabel);

        try {
            Conn conn = new Conn();
            String query = "SELECT * FROM login WHERE username = '" + UserSession.loggedInUser + "'";
            ResultSet rs = conn.s.executeQuery(query);

            if (rs.next()) {
                JLabel fullNameLabel = new JLabel("Full Name: " + rs.getString("fullName"));
                fullNameLabel.setFont(new Font("Segoe UI", Font.PLAIN, 18));
                fullNameLabel.setBounds(50, 100, 400, 30);
                add(fullNameLabel);

                JLabel emailLabel = new JLabel("Email: " + rs.getString("email"));
                emailLabel.setFont(new Font("Segoe UI", Font.PLAIN, 18));
                emailLabel.setBounds(50, 150, 400, 30);
                add(emailLabel);

                JLabel usernameLabel = new JLabel("Username: " + rs.getString("username"));
                usernameLabel.setFont(new Font("Segoe UI", Font.PLAIN, 18));
                usernameLabel.setBounds(50, 200, 400, 30);
                add(usernameLabel);
            } else {
                JOptionPane.showMessageDialog(this, "No profile data found!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        JButton backButton = new JButton("Back");
        backButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        backButton.setBackground(new Color(0, 102, 102));
        backButton.setForeground(Color.WHITE);
        backButton.setBounds(200, 300, 100, 30);
        backButton.addActionListener(e -> dispose());
        add(backButton);

        setVisible(true);
    }
}
