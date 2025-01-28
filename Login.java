package airlinemanagementsystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class Login extends JFrame implements ActionListener {
    JButton submit, reset, register;
    JTextField tfusername;
    JPasswordField tfpassword;

    public Login() {
        getContentPane().setBackground(Color.WHITE);
        setLayout(null);
        
        JPanel leftPanel = new JPanel();
        leftPanel.setBackground(new Color(0, 102, 102));
        leftPanel.setBounds(0, 0, 400, 500);
        leftPanel.setLayout(null); // Use null layout to position components manually
        add(leftPanel);

        // Load the logo image
        ImageIcon logoIcon = new ImageIcon("C:\\Users\\Ahnaf\\Downloads\\pngegg.png"); // Replace with the actual path to your logo image
        Image logoImage = logoIcon.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH); // Resize the image if necessary
        logoIcon = new ImageIcon(logoImage); // Convert back to ImageIcon

        // Create a JLabel to display the logo
        JLabel logoLabel = new JLabel(logoIcon);
        logoLabel.setBounds(100, 50, 200, 200); // Position and size of the logo
        leftPanel.add(logoLabel);

        // Create a JLabel to display the text "csedu airlines"
        JLabel airlineLabel = new JLabel("CSEDU AIRLINES");
        airlineLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        airlineLabel.setForeground(Color.WHITE);
        airlineLabel.setBounds(120, 260, 200, 30); // Position and size of the text
        leftPanel.add(airlineLabel);

        JPanel rightPanel = new JPanel();
        rightPanel.setBackground(Color.WHITE);
        rightPanel.setBounds(400, 0, 400, 500);
        rightPanel.setLayout(null);
        add(rightPanel);

        JLabel lblTitle = new JLabel("LOGIN");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 36));
        lblTitle.setBounds(150, 30, 230, 40);
        lblTitle.setForeground(new Color(0, 102, 102));
        rightPanel.add(lblTitle);

        JLabel lblusername = new JLabel("Username");
        lblusername.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblusername.setBounds(50, 100, 100, 20);
        rightPanel.add(lblusername);

        tfusername = new JTextField();
        tfusername.setBounds(50, 130, 300, 25);
        rightPanel.add(tfusername);

        JLabel lblpassword = new JLabel("Password");
        lblpassword.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblpassword.setBounds(50, 180, 100, 20);
        rightPanel.add(lblpassword);

        tfpassword = new JPasswordField();
        tfpassword.setBounds(50, 210, 300, 25);
        rightPanel.add(tfpassword);

        reset = new JButton("Reset");
        reset.setBounds(70, 270, 100, 30);
        reset.addActionListener(this);
        reset.setBackground(new Color(0, 102, 102)); 
        reset.setForeground(Color.WHITE); 
        reset.setFont(new Font("Segoe UI", Font.BOLD, 14)); 
        rightPanel.add(reset);

        submit = new JButton("Submit");
        submit.setBounds(200, 270, 100, 30);
        submit.addActionListener(this);
        submit.setBackground(new Color(0, 102, 102)); 
        submit.setForeground(Color.WHITE); 
        submit.setFont(new Font("Segoe UI", Font.BOLD, 14)); 
        rightPanel.add(submit);

        register = new JButton("Register");
        register.setBounds(220, 400, 100, 30);
        register.addActionListener(this);
        register.setBackground(new Color(0, 102, 102)); 
        register.setForeground(Color.WHITE); 
        register.setFont(new Font("Segoe UI", Font.BOLD, 14)); 
        rightPanel.add(register);

        JLabel lblRegisterPrompt = new JLabel("Don't have an account?");
        lblRegisterPrompt.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblRegisterPrompt.setBounds(50, 400, 150, 20);
        rightPanel.add(lblRegisterPrompt);

        setSize(800, 500);
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
                    // Store the logged-in user in UserSession
                    UserSession.loggedInUser = username;

                    JOptionPane.showMessageDialog(null, "Login Successful");

                    // Check if the username is "admin"
                    if (username.equals("admin")) {
                        // Open the admin class
                        new AdminDashboard(); // Replace AdminDashboard with your admin class
                    } else {
                        // Open the regular user class
                        new Front(); // Replace Review with your regular user class
                    }
                    setVisible(false); // Hide the login window
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
        }
    }

    public static void main(String[] args) {
        new Login();
    }
}
