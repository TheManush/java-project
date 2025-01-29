
package csedu.flight.mangement.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.Random;

public class ForgotPassword extends JFrame implements ActionListener {
    JButton submit, back, sendOtp;
    JTextField tfusername, tffullname, tfemail, tfotp;
    JLabel lblusername, lblFullname, lblEmail, lblOtp, lblTitle, lblBackground;
    String generatedOtp = "";

    public ForgotPassword() {
        setLayout(null);

        ImageIcon backgroundImage = new ImageIcon("path_to_background_image.jpg");
        lblBackground = new JLabel(backgroundImage);
        lblBackground.setBounds(0, 0, 500, 400);
        add(lblBackground);

        ImageIcon logoIcon = new ImageIcon("path_to_logo_image.png");
        Image logoImage = logoIcon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        logoIcon = new ImageIcon(logoImage);

        JLabel logoLabel = new JLabel(logoIcon);
        logoLabel.setBounds(0, 0, 0, 0);   //resize needed
        lblBackground.add(logoLabel);

        lblTitle = new JLabel("Password Recovery");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblTitle.setForeground(new Color(0, 102, 102));
        lblTitle.setBounds(150, 30, 250, 30);
        lblBackground.add(lblTitle);

        lblusername = new JLabel("Username:");
        lblusername.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblusername.setBounds(50, 90, 100, 20);
        lblBackground.add(lblusername);

        tfusername = new JTextField();
        tfusername.setBounds(150, 90, 250, 25);
        lblBackground.add(tfusername);

        lblFullname = new JLabel("Full Name:");
        lblFullname.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblFullname.setBounds(50, 130, 100, 20);
        lblBackground.add(lblFullname);

        tffullname = new JTextField();
        tffullname.setBounds(150, 130, 250, 25);
        lblBackground.add(tffullname);

        lblEmail = new JLabel("Email:");
        lblEmail.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblEmail.setBounds(50, 170, 100, 20);
        lblBackground.add(lblEmail);

        tfemail = new JTextField();
        tfemail.setBounds(150, 170, 250, 25);
        lblBackground.add(tfemail);

        lblOtp = new JLabel("OTP:");
        lblOtp.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblOtp.setBounds(50, 250, 100, 20);
        lblBackground.add(lblOtp);

        tfotp = new JTextField();
        tfotp.setBounds(150, 250, 250, 25);
        lblBackground.add(tfotp);

        sendOtp = new JButton("Send OTP");
        sendOtp.setBounds(150, 210, 120, 30);
        sendOtp.addActionListener(this);
        sendOtp.setBackground(new Color(0, 102, 102));
        sendOtp.setForeground(Color.WHITE);
        sendOtp.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblBackground.add(sendOtp);

        submit = new JButton("Submit");
        submit.setBounds(150, 290, 120, 30);
        submit.addActionListener(this);
        submit.setBackground(new Color(0, 102, 102));
        submit.setForeground(Color.WHITE);
        submit.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblBackground.add(submit);

        back = new JButton("Back");
        back.setBounds(300, 290, 100, 30);
        back.addActionListener(this);
        back.setBackground(new Color(0, 102, 102));
        back.setForeground(Color.WHITE);
        back.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblBackground.add(back);

        setSize(500, 450);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == sendOtp) {
            String username = tfusername.getText();
            String fullName = tffullname.getText();
            String email = tfemail.getText();

            try {
                Conn c = new Conn();
                String query = "SELECT * FROM login WHERE username = ? AND fullName = ? AND email = ?";
                PreparedStatement ps = c.c.prepareStatement(query);
                ps.setString(1, username);
                ps.setString(2, fullName);
                ps.setString(3, email);
                ResultSet rs = ps.executeQuery();

                if (rs.next()) {
                    Random rand = new Random();
                    generatedOtp = String.format("%06d", rand.nextInt(1000000));
                    JOptionPane.showMessageDialog(null, "OTP sent to your email: " + email);
                    tfotp.setText(generatedOtp);
                } else {
                    JOptionPane.showMessageDialog(null, "Details do not match. Please try again.");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (ae.getSource() == submit) {
            String otp = tfotp.getText();

            if (otp.equals(generatedOtp)) {
                new ResetPassword(tfusername.getText());
                setVisible(false);
            } else {
                JOptionPane.showMessageDialog(null, "Invalid OTP. Please try again.");
            }
        }

        if (ae.getSource() == back) {
            new Login();
            setVisible(false);
        }
    }

    public static void main(String[] args) {
        new ForgotPassword();
    }
}