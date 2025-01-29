package airlinemanagementsystem;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.awt.event.*;
import java.util.regex.Pattern;

public class Profile extends JFrame implements ActionListener {

    JTextField tfFullName, tfEmail, tfDob;
    JLabel lblUsername;
    JButton backButton, editButton, saveButton, cancelButton;

    public Profile() {
        setTitle("User Profile");
        setSize(500, 500);
        setLocationRelativeTo(null);
        setLayout(null);
        setResizable(false);
        getContentPane().setBackground(new Color(240, 248, 255));

        JLabel titleLabel = new JLabel("User Profile");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 26));
        titleLabel.setForeground(new Color(0, 102, 102));
        titleLabel.setBounds(170, 20, 200, 40);
        add(titleLabel);

        try {
            Conn conn = new Conn();
            String query = "SELECT * FROM login WHERE username = ?";
            PreparedStatement ps = conn.c.prepareStatement(query);
            ps.setString(1, UserSession.loggedInUser);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                JLabel lblFullName = new JLabel("Full Name:");
                lblFullName.setFont(new Font("Segoe UI", Font.BOLD, 14));
                lblFullName.setBounds(50, 100, 100, 30);
                add(lblFullName);

                tfFullName = new JTextField(rs.getString("fullName"));
                tfFullName.setBounds(160, 100, 250, 30);
                tfFullName.setEditable(false);
                add(tfFullName);

                JLabel lblEmail = new JLabel("Email:");
                lblEmail.setFont(new Font("Segoe UI", Font.BOLD, 14));
                lblEmail.setBounds(50, 150, 100, 30);
                add(lblEmail);

                tfEmail = new JTextField(rs.getString("email"));
                tfEmail.setBounds(160, 150, 250, 30);
                tfEmail.setEditable(false);
                add(tfEmail);

                JLabel lblUsernameText = new JLabel("Username:");
                lblUsernameText.setFont(new Font("Segoe UI", Font.BOLD, 14));
                lblUsernameText.setBounds(50, 200, 100, 30);
                add(lblUsernameText);

                lblUsername = new JLabel(rs.getString("username"));
                lblUsername.setFont(new Font("Segoe UI", Font.PLAIN, 14));
                lblUsername.setBounds(160, 200, 250, 30);
                add(lblUsername);

                JLabel lblDob = new JLabel("Date of Birth:");
                lblDob.setFont(new Font("Segoe UI", Font.BOLD, 14));
                lblDob.setBounds(50, 250, 100, 30);
                add(lblDob);

                tfDob = new JTextField(rs.getString("dob"));
                tfDob.setBounds(160, 250, 250, 30);
                tfDob.setEditable(false);
                add(tfDob);
            } else {
                JOptionPane.showMessageDialog(this, "No profile data found!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        
        backButton = new JButton("Back");
        backButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        backButton.setBackground(new Color(0, 102, 102));
        backButton.setForeground(Color.WHITE);
        backButton.setBounds(50, 350, 100, 35);
        backButton.addActionListener(e -> dispose());
        add(backButton);

        
        editButton = new JButton("Edit");
        editButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        editButton.setBackground(new Color(0, 102, 102));
        editButton.setForeground(Color.WHITE);
        editButton.setBounds(310, 350, 100, 35);
        editButton.addActionListener(this);
        add(editButton);
        
        //hidden
        cancelButton = new JButton("Cancel");
        cancelButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        cancelButton.setBackground(new Color(0, 102, 102));
        cancelButton.setForeground(Color.WHITE);
        cancelButton.setBounds(180, 350, 100, 35);
        cancelButton.addActionListener(this);
        cancelButton.setVisible(false);
        add(cancelButton);
        
        //hidden
        saveButton = new JButton("Save");
        saveButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        saveButton.setBackground(new Color(0, 102, 102));
        saveButton.setForeground(Color.WHITE);
        saveButton.setBounds(310, 350, 100, 35);
        saveButton.setVisible(false);
        saveButton.addActionListener(this);
        add(saveButton);

        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == editButton) {
            // Enable text fields for editing
            tfFullName.setEditable(true);
            tfEmail.setEditable(true);
            tfDob.setEditable(true);

            
            editButton.setVisible(false);
            saveButton.setVisible(true);
            cancelButton.setVisible(true);
        } else if (ae.getSource() == saveButton) {
            
            String fullName = tfFullName.getText();
            String email = tfEmail.getText();
            String dob = tfDob.getText();

            if (fullName.isEmpty() || email.isEmpty() || dob.isEmpty()) {
                JOptionPane.showMessageDialog(this, "All fields must be filled!");
                return;
            }

          /*  if (!isValidEmail(email)) {
                JOptionPane.showMessageDialog(this, "Invalid email format!");
                return;
            }*/

            if (!isValidDOB(dob)) {
                JOptionPane.showMessageDialog(this, "Invalid Date of Birth format! Use YYYY-MM-DD.");
                return;
            }

            
            try {
                Conn conn = new Conn();
                String updateQuery = "UPDATE login SET fullName = ?, email = ?, dob = ? WHERE username = ?";
                PreparedStatement ps = conn.c.prepareStatement(updateQuery);
                ps.setString(1, fullName);
                ps.setString(2, email);
                ps.setString(3, dob);
                ps.setString(4, UserSession.loggedInUser);

                int rowsUpdated = ps.executeUpdate();
                if (rowsUpdated > 0) {
                    JOptionPane.showMessageDialog(this, "Profile updated successfully!");

                    // Disable buttons
                    tfFullName.setEditable(false);
                    tfEmail.setEditable(false);
                    tfDob.setEditable(false);

                    // Show buttons
                    editButton.setVisible(true);
                    saveButton.setVisible(false);
                    cancelButton.setVisible(false);
                } else {
                    JOptionPane.showMessageDialog(this, "Update failed! Please try again.");
                }
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Database error: " + e.getMessage());
            }
        }
        
        if (ae.getSource() == cancelButton) {
            try {
                Conn conn = new Conn();
                String query = "SELECT * FROM login WHERE username = ?";
                PreparedStatement ps = conn.c.prepareStatement(query);
                ps.setString(1, UserSession.loggedInUser);
                ResultSet rs = ps.executeQuery();

                if (rs.next()) {
                    tfFullName.setText(rs.getString("fullName"));
                    tfEmail.setText(rs.getString("email"));
                    tfDob.setText(rs.getString("dob"));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            
            tfFullName.setEditable(false);
            tfEmail.setEditable(false);
            tfDob.setEditable(false);

            editButton.setVisible(true);
            saveButton.setVisible(false);
            cancelButton.setVisible(false);
        }

    }

    // Email validation
   /* private boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        return Pattern.matches(emailRegex, email);
    }*/

    // Date of Birth validation (YYYY-MM-DD format)
    private boolean isValidDOB(String dob) {
        String dobRegex = "^\\d{4}-\\d{2}-\\d{2}$";
        return Pattern.matches(dobRegex, dob);
    }

    public static void main(String[] args) {
       
        UserSession.loggedInUser = "123";
        new Profile();
    }
}
