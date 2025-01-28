package airlinemanagementsystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class Register extends JFrame implements ActionListener {
    JTextField tfFullName, tfUsername;
    JPasswordField tfPassword, tfConfirmPassword;
    JButton register, back;

    public Register() {
        getContentPane().setBackground(Color.WHITE);
        setLayout(null);

        // Left Panel
        JPanel leftPanel = new JPanel();
        leftPanel.setBackground(new Color(0, 102, 102));
        leftPanel.setBounds(0, 0, 400, 500);
        add(leftPanel);
        
       /* submit = new JButton("Submit");
        submit.setBounds(200, 270, 100, 30);
        submit.addActionListener(this);
        submit.setBackground(new Color(0, 102, 102)); // Set background color to green
        submit.setForeground(Color.WHITE); // Set font color to white
        submit.setFont(new Font("Segoe UI", Font.BOLD, 14)); // Set font to Tahoma, bold, size 14
        rightPanel.add(submit);*/
        // Right Panel
        JPanel rightPanel = new JPanel();
        rightPanel.setBackground(Color.WHITE);
        rightPanel.setBounds(400, 0, 400, 500);
        rightPanel.setLayout(null);
        add(rightPanel);

        JLabel lblTitle = new JLabel("REGISTER");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 36));
        lblTitle.setBounds(130, 30, 200, 40);
        lblTitle.setForeground(new Color(0, 102, 102));
        rightPanel.add(lblTitle);

        JLabel lblFullName = new JLabel("Full Name");
        lblFullName.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblFullName.setBounds(50, 100, 100, 20);
        rightPanel.add(lblFullName);

        tfFullName = new JTextField();
        tfFullName.setBounds(150, 100, 200, 25);
        rightPanel.add(tfFullName);

        JLabel lblUsername = new JLabel("Username");
        lblUsername.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblUsername.setBounds(50, 150, 100, 20);
        rightPanel.add(lblUsername);

        tfUsername = new JTextField();
        tfUsername.setBounds(150, 150, 200, 25);
        rightPanel.add(tfUsername);

        JLabel lblPassword = new JLabel("Password");
        lblPassword.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblPassword.setBounds(50, 200, 100, 20);
        rightPanel.add(lblPassword);

        tfPassword = new JPasswordField();
        tfPassword.setBounds(150, 200, 200, 25);
        rightPanel.add(tfPassword);

        JLabel lblConfirmPassword = new JLabel("Confirm Password");
        lblConfirmPassword.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblConfirmPassword.setBounds(30, 250, 120, 20);
        rightPanel.add(lblConfirmPassword);

        tfConfirmPassword = new JPasswordField();
        tfConfirmPassword.setBounds(150, 250, 200, 25);
        rightPanel.add(tfConfirmPassword);

        register = new JButton("Register");
        register.setBounds(70, 320, 120, 30);
        register.addActionListener(this);
        register.setFont(new Font("Segoe UI", Font.BOLD, 14));
        register.setForeground(Color.WHITE);
        register.setBackground(new Color(0, 102, 102));
        rightPanel.add(register);

        back = new JButton("Back");
        back.setBounds(220, 320, 120, 30);
        back.addActionListener(this);
        back.setFont(new Font("Segoe UI", Font.BOLD, 14));
        back.setForeground(Color.WHITE);
        back.setBackground(new Color(0, 102, 102));
        rightPanel.add(back);

        setSize(800, 500);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == register) {
            String fullName = tfFullName.getText();
            String username = tfUsername.getText();
            String password = new String(tfPassword.getPassword());
            String confirmPassword = new String(tfConfirmPassword.getPassword());

            if (fullName.isEmpty() || username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                JOptionPane.showMessageDialog(null, "All fields are required.");
                return;
            }

            if (!password.equals(confirmPassword)) {
                JOptionPane.showMessageDialog(null, "Passwords do not match. Please try again.");
                tfPassword.setText("");
                tfConfirmPassword.setText("");
                return;
            }

       try {
                Conn c = new Conn();

                // Step 1: Check if the username already exists in the database
                String checkQuery = "SELECT * FROM login WHERE username = ?";
                PreparedStatement psCheck = c.c.prepareStatement(checkQuery); // Directly use the "c" field
                psCheck.setString(1, username);
                ResultSet rs = psCheck.executeQuery();

                if (rs.next()) {
                    // Username already exists
                    JOptionPane.showMessageDialog(null, "Username already exists. Please choose a different username.");
                } else {
                    // Step 2: Insert the new user if username doesn't exist
                    String insertQuery = "INSERT INTO login (username, password) VALUES (?, ?)";
                    PreparedStatement psInsert = c.c.prepareStatement(insertQuery);
                    psInsert.setString(1, username);
                    psInsert.setString(2, password);
                    psInsert.executeUpdate();

                    JOptionPane.showMessageDialog(null, "Registration successful!");
                    setVisible(false);
                    new Login(); // Redirect to Login screen
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (ae.getSource() == back) {
            setVisible(false);
            new Login(); 
        }
    }

    public static void main(String[] args) {
        new Register();
    }
}
