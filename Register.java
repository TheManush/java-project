package csedu.flight.mangement.system;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.regex.Pattern;

public class Register extends JFrame implements ActionListener {
    JTextField tfFullName, tfUsername, tfEmail, tfDob;
    JPasswordField tfPassword, tfConfirmPassword;
    JButton register, back;

    public Register() {
        getContentPane().setBackground(Color.WHITE);
        setLayout(null);

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

        JLabel airlineLabel = new JLabel("CSEDU AIRLINES");
        airlineLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        airlineLabel.setForeground(Color.WHITE);
        airlineLabel.setBounds(100, 360, 200, 30);
        leftPanel.add(airlineLabel);

        JPanel rightPanel = new JPanel();
        rightPanel.setBackground(Color.WHITE);
        rightPanel.setBounds(400, 0, 400, 600);
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

        JLabel lblEmail = new JLabel("Email");
        lblEmail.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblEmail.setBounds(60, 200, 100, 20);
        rightPanel.add(lblEmail);

        tfEmail = new JTextField();
        tfEmail.setBounds(150, 200, 200, 25);
        rightPanel.add(tfEmail);

        JLabel lblDob = new JLabel("Date of Birth");
        lblDob.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblDob.setBounds(50, 250, 100, 20);
        rightPanel.add(lblDob);

        tfDob = new JTextField();
        tfDob.setBounds(150, 250, 200, 25);
        rightPanel.add(tfDob);

        JLabel lblDobFormat = new JLabel("(YYYY-MM-DD)");
        lblDobFormat.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblDobFormat.setForeground(Color.GRAY);
        lblDobFormat.setBounds(150, 275, 200, 20);
        rightPanel.add(lblDobFormat);

        JLabel lblPassword = new JLabel("Password");
        lblPassword.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblPassword.setBounds(50, 300, 100, 20);
        rightPanel.add(lblPassword);

        tfPassword = new JPasswordField();
        tfPassword.setBounds(150, 300, 200, 25);
        rightPanel.add(tfPassword);

        JLabel lblConfirmPassword = new JLabel("Confirm Password");
        lblConfirmPassword.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblConfirmPassword.setBounds(30, 350, 120, 20);
        rightPanel.add(lblConfirmPassword);

        tfConfirmPassword = new JPasswordField();
        tfConfirmPassword.setBounds(150, 350, 200, 25);
        rightPanel.add(tfConfirmPassword);

        register = new JButton("Register");
        register.setBounds(70, 420, 120, 30);
        register.addActionListener(this);
        register.setFont(new Font("Segoe UI", Font.BOLD, 14));
        register.setForeground(Color.WHITE);
        register.setBackground(new Color(0, 102, 102));
        rightPanel.add(register);

        back = new JButton("Back");
        back.setBounds(220, 420, 120, 30);
        back.addActionListener(this);
        back.setFont(new Font("Segoe UI", Font.BOLD, 14));
        back.setForeground(Color.WHITE);
        back.setBackground(new Color(0, 102, 102));
        rightPanel.add(back);

        setSize(800, 600);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private boolean isValidDOB(String dob) {
        String regex = "\\d{4}-\\d{2}-\\d{2}";
        if (!Pattern.matches(regex, dob)) {
            return false;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        sdf.setLenient(false);
        try {
            sdf.parse(dob);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == register) {
            String fullName = tfFullName.getText();
            String username = tfUsername.getText();
            String email = tfEmail.getText();
            String dob = tfDob.getText();
            String password = new String(tfPassword.getPassword());
            String confirmPassword = new String(tfConfirmPassword.getPassword());

            if (fullName.isEmpty() || username.isEmpty() || email.isEmpty() || dob.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                JOptionPane.showMessageDialog(null, "All fields are required.");
                return;
            }

            if (!password.equals(confirmPassword)) {
                JOptionPane.showMessageDialog(null, "Passwords do not match. Please try again.");
                tfPassword.setText("");
                tfConfirmPassword.setText("");
                return;
            }

            if (!isValidDOB(dob)) {
                JOptionPane.showMessageDialog(null, "Invalid Date of Birth format. Please enter in YYYY-MM-DD format.");
                return;
            }

            try {
                Conn c = new Conn();

                String checkQuery = "SELECT * FROM login WHERE username = ? OR email = ?";
                PreparedStatement psCheck = c.c.prepareStatement(checkQuery);
                psCheck.setString(1, username);
                psCheck.setString(2, email);
                ResultSet rs = psCheck.executeQuery();

                if (rs.next()) {
                    JOptionPane.showMessageDialog(null, "Username or Email already exists. Please choose a different one.");
                } else {
                    String insertQuery = "INSERT INTO login (username, fullName, dob, password, email) VALUES (?, ?, ?, ?, ?)";
                    PreparedStatement psInsert = c.c.prepareStatement(insertQuery);
                    psInsert.setString(1, username);
                    psInsert.setString(2, fullName);
                    psInsert.setString(3, dob);
                    psInsert.setString(4, password);
                    psInsert.setString(5, email);
                    psInsert.executeUpdate();

                    JOptionPane.showMessageDialog(null, "Registration successful!");
                    setVisible(false);
                    new Login();
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
