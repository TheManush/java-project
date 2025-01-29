package csedu.flight.mangement.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class Login extends JFrame implements ActionListener {
    JButton submit, reset, register, forgotPassword;
    JTextField tfusername;
    JPasswordField tfpassword;

    public Login() {
        getContentPane().setBackground(Color.WHITE);
        setLayout(null);
        setResizable(false);

        JPanel leftPanel = new JPanel();
        leftPanel.setBackground(new Color(0, 102, 102));
        leftPanel.setBounds(0, 0, 400, 600); 
        leftPanel.setLayout(null);
        add(leftPanel);

        ImageIcon logoIcon = new ImageIcon("C:\\Users\\Ahnaf\\Documents\\NetBeansProjects\\AirlineManagementSystem\\src\\airlinemanagementsystem\\icons\\pngegg.png");
        Image logoImage = logoIcon.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
        logoIcon = new ImageIcon(logoImage);

        JLabel logoLabel = new JLabel(logoIcon);
        logoLabel.setBounds(100, 150, 200, 200);
        leftPanel.add(logoLabel);

        JLabel airlineLabel = new JLabel("SIGN UP NOW");
        airlineLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        airlineLabel.setForeground(Color.WHITE);
        airlineLabel.setBounds(100, 360, 200, 30);
        leftPanel.add(airlineLabel);

        JPanel rightPanel = new JPanel();
        rightPanel.setBackground(Color.WHITE);
        rightPanel.setBounds(400, 0, 400, 600); 
        rightPanel.setLayout(null);
        add(rightPanel);

        JLabel lblTitle = new JLabel("LOGIN");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 36));
        lblTitle.setBounds(150, 30, 230, 40);
        lblTitle.setForeground(new Color(0, 102, 102));
        rightPanel.add(lblTitle);

        JLabel lblusername = new JLabel("Username");
        lblusername.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblusername.setBounds(50, 120, 100, 20); 
        rightPanel.add(lblusername);

        tfusername = new JTextField();
        tfusername.setBounds(50, 150, 300, 25); 
        rightPanel.add(tfusername);

        JLabel lblpassword = new JLabel("Password");
        lblpassword.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblpassword.setBounds(50, 200, 100, 20); 
        rightPanel.add(lblpassword);

        tfpassword = new JPasswordField();
        tfpassword.setBounds(50, 230, 300, 25); 
        rightPanel.add(tfpassword);

        reset = new JButton("Reset");
        reset.setBounds(70, 290, 100, 30); 
        reset.addActionListener(this);
        reset.setBackground(new Color(0, 102, 102));
        reset.setForeground(Color.WHITE);
        reset.setFont(new Font("Segoe UI", Font.BOLD, 14));
        rightPanel.add(reset);

        submit = new JButton("Submit");
        submit.setBounds(200, 290, 100, 30); 
        submit.addActionListener(this);
        submit.setBackground(new Color(0, 102, 102));
        submit.setForeground(Color.WHITE);
        submit.setFont(new Font("Segoe UI", Font.BOLD, 14));
        rightPanel.add(submit);

        register = new JButton("Register");
        register.setBounds(220, 500, 100, 30); 
        register.addActionListener(this);
        register.setBackground(new Color(0, 102, 102));
        register.setForeground(Color.WHITE);
        register.setFont(new Font("Segoe UI", Font.BOLD, 14));
        rightPanel.add(register);

        JLabel lblRegisterPrompt = new JLabel("Don't have an account?");
        lblRegisterPrompt.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblRegisterPrompt.setBounds(50, 500, 150, 20); 
        rightPanel.add(lblRegisterPrompt);

        // Forgot Password Button
        forgotPassword = new JButton("Forgot Password?");
        forgotPassword.setBounds(100, 350, 200, 28); 
        forgotPassword.addActionListener(this);
        forgotPassword.setBackground(new Color(0, 102, 102));
        forgotPassword.setForeground(Color.WHITE);
        forgotPassword.setFont(new Font("Segoe UI", Font.BOLD, 14));
        rightPanel.add(forgotPassword);

        setSize(800, 600); 
        setLocationRelativeTo(null); 
        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == submit) {
            String username = tfusername.getText();
            String password = tfpassword.getText();
            try {
                Conn c = new Conn();

                String query = "select * from login where username = '" + username + "' and password = '" + password + "'";

                ResultSet rs = c.s.executeQuery(query);
                if (rs.next()) {
                    UserSession.loggedInUser = username;
                    JOptionPane.showMessageDialog(null, "Login Successful");

                    if (username.equals("admin")) {
                        new AdminDashboard(); 
                    } else {
                        new Front(); 
                    }
                    setVisible(false);
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid Username or Password");
                    tfusername.setText("");
                    tfpassword.setText("");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (ae.getSource() == reset) {
            tfusername.setText("");
            tfpassword.setText("");
        } else if (ae.getSource() == register) {
            setVisible(false);
            new Register();
        } else if (ae.getSource() == forgotPassword) {
            new ForgetPassword(); 
        }
    }

    public static void main(String[] args) {
        new Login();
    }
}
