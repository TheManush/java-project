
package csedu.flight.mangement.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class ResetPassword extends JFrame implements ActionListener {
    JPasswordField tfNewPassword, tfConfirmPassword;
    JButton resetPassword, back;
    JLabel lblNewPassword, lblConfirmPassword, lblTitle, lblBackground;
    String username;

    public ResetPassword(String username) {
        this.username = username;
        setResizable(false);
        setLayout(null);

        ImageIcon backgroundImage = new ImageIcon("path_to_background_image.jpg");
        lblBackground = new JLabel(backgroundImage);
        lblBackground.setBounds(0, 0, 500, 300);
        add(lblBackground);

        ImageIcon logoIcon = new ImageIcon("path_to_logo_image.png");
        Image logoImage = logoIcon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        logoIcon = new ImageIcon(logoImage);

        JLabel logoLabel = new JLabel(logoIcon);
        logoLabel.setBounds(0, 0, 0, 0);    //resize needed
        lblBackground.add(logoLabel);

        lblTitle = new JLabel("Reset Password");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblTitle.setForeground(new Color(0, 102, 102));
        lblTitle.setBounds(150, 30, 250, 30);
        lblBackground.add(lblTitle);

        lblNewPassword = new JLabel("New Password:");
        lblNewPassword.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblNewPassword.setBounds(50, 90, 100, 20);
        lblBackground.add(lblNewPassword);

        tfNewPassword = new JPasswordField();
        tfNewPassword.setBounds(175, 90, 245, 25);
        lblBackground.add(tfNewPassword);

        lblConfirmPassword = new JLabel("Confirm Password:");
        lblConfirmPassword.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblConfirmPassword.setBounds(50, 130, 120, 20);
        lblBackground.add(lblConfirmPassword);

        tfConfirmPassword = new JPasswordField();
        tfConfirmPassword.setBounds(175, 130, 245, 25);
        lblBackground.add(tfConfirmPassword);

        resetPassword = new JButton("Reset");
        resetPassword.setBounds(175, 170, 110, 30);
        resetPassword.addActionListener(this);
        resetPassword.setBackground(new Color(0, 102, 102));
        resetPassword.setForeground(Color.WHITE);
        resetPassword.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblBackground.add(resetPassword);

        back = new JButton("Back");
        back.setBounds(320, 170, 100, 30);
        back.addActionListener(this);
        back.setBackground(new Color(0, 102, 102));
        back.setForeground(Color.WHITE);
        back.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblBackground.add(back);

        setSize(500, 350);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == resetPassword) {
            String newPassword = new String(tfNewPassword.getPassword());
            String confirmPassword = new String(tfConfirmPassword.getPassword());

            if (newPassword.isEmpty() || confirmPassword.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Both password fields must be filled.");
                return;
            }

            if (!newPassword.equals(confirmPassword)) {
                JOptionPane.showMessageDialog(null, "Passwords do not match. Please try again.");
                return;
            }

            try {
                Conn c = new Conn();
                String updateQuery = "UPDATE login SET password = ? WHERE username = ?";
                PreparedStatement ps = c.c.prepareStatement(updateQuery);
                ps.setString(1, newPassword);
                ps.setString(2, username);
                int rowsUpdated = ps.executeUpdate();

                if (rowsUpdated > 0) {
                    JOptionPane.showMessageDialog(null, "Password updated successfully!");
                    new Login();
                    setVisible(false);
                } else {
                    JOptionPane.showMessageDialog(null, "Username not found.");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (ae.getSource() == back) {
            new ForgotPassword();
            setVisible(false);
        }
    }

    public static void main(String[] args) {
        new ResetPassword("testuser");
    }
}