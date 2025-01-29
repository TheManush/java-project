package airlinemanagementsystem;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.awt.event.*;
import net.proteanit.sql.DbUtils;

public class JourneyDetails extends JFrame implements ActionListener {
    JTable table;
    JTextField id;
    JButton show;

    public JourneyDetails() {
        getContentPane().setBackground(new Color(30, 30, 30)); // Dark background
        setLayout(null);

        // Title Label
        JLabel title = new JLabel("Journey Details");
        title.setFont(new Font("Tahoma", Font.BOLD, 24));
        title.setForeground(Color.YELLOW);
        title.setBounds(300, 10, 300, 30);
        add(title);

        // National ID Label
        JLabel lblpnr = new JLabel("National ID:");
        lblpnr.setFont(new Font("Tahoma", Font.PLAIN, 18));
        lblpnr.setForeground(Color.WHITE);
        lblpnr.setBounds(50, 60, 150, 25);
        add(lblpnr);

        // National ID Text Field
        id = new JTextField();
        id.setFont(new Font("Tahoma", Font.PLAIN, 16));
        id.setBounds(200, 60, 250, 30);
        add(id);

        // Show Details Button
        show = new JButton("Show Details");
        show.setFont(new Font("Tahoma", Font.PLAIN, 16));
        show.setBackground(new Color(70, 130, 180)); // Steel Blue
        show.setForeground(Color.WHITE);
        show.setBounds(470, 60, 150, 30);
        show.addActionListener(this);
        add(show);

        // Table Setup
        table = new JTable();
        table.setFont(new Font("Tahoma", Font.PLAIN, 14));
        table.setRowHeight(25); // Increase row height for better readability
        table.setBackground(new Color(240, 240, 240)); // Light gray background
        table.setForeground(Color.BLACK);

        JScrollPane jsp = new JScrollPane(table);
        jsp.setBounds(50, 120, 700, 300);
        jsp.setBackground(Color.BLACK);
        add(jsp);

        setResizable(false);
        setSize(800, 500);
        setLocationRelativeTo(null); // Center the window
        
        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        String nationalId = id.getText().trim(); // Ensure no leading/trailing spaces
        if (nationalId.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please enter a National ID.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Get the logged-in username from the session
        String loggedInUser = UserSession.loggedInUser;

        try {
            Conn conn = new Conn();
            // Fetch data only for the current logged-in user
            String query = "SELECT * FROM reservation WHERE id = '" + nationalId + "' AND added_by = '" + loggedInUser + "'";
            ResultSet rs = conn.s.executeQuery(query);

            if (!rs.isBeforeFirst()) { // Check if the ResultSet is empty
                JOptionPane.showMessageDialog(null, "No Information Found or You are not authorized to view this data.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Populate the table with the result set
            table.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error fetching data. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        new JourneyDetails();
    }
}
