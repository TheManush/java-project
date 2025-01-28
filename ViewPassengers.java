package airlinemanagementsystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class ViewPassengers extends JFrame implements ActionListener {
    JTextField tfFlightCode;
    JButton search, back;
    JTextArea passengerList;
    JScrollPane scrollPane;

    public ViewPassengers() {
        setTitle("View Passengers");
        setSize(800, 400);
        setLocationRelativeTo(null);
        setLayout(null);

        // Label and Text Field for Flight Code
        JLabel lblFlightCode = new JLabel("Enter Flight Code:");
        lblFlightCode.setBounds(50, 30, 150, 20);
        add(lblFlightCode);

        tfFlightCode = new JTextField();
        tfFlightCode.setBounds(200, 30, 150, 25);
        add(tfFlightCode);

        // Search Button
        search = new JButton("Search");
        search.setBounds(400, 30, 100, 25);
        search.addActionListener(this);
        add(search);

        // Back Button
        back = new JButton("Back");
        back.setBounds(520, 30, 100, 25);
        back.addActionListener(this);
        add(back);

        // TextArea to display passenger details
        passengerList = new JTextArea();
        passengerList.setEditable(false); // Make it read-only
        passengerList.setFont(new Font("Monospaced", Font.PLAIN, 12)); // Use monospaced font for alignment

        // ScrollPane for the TextArea
        scrollPane = new JScrollPane(passengerList);
        scrollPane.setBounds(50, 80, 500, 250);
        add(scrollPane);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == search) {
            String flightCode = tfFlightCode.getText().trim();

            if (flightCode.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please enter a Flight Code!");
                return;
            }

            try {
                Conn c = new Conn();
                String query = "SELECT PNR, TIC, name, ddate FROM reservation WHERE flightcode = '" + flightCode + "'";
                ResultSet rs = c.s.executeQuery(query);

                // Clear the previous list
                passengerList.setText("");

                // Add a header
                passengerList.append(String.format("%-10s %-10s %-20s %-15s%n", "PNR", "TIC", "Name", "Date"));
                passengerList.append("------------------------------------------------------------\n");

                boolean found = false;
                while (rs.next()) {
                    found = true;
                    String pnr = rs.getString("PNR");
                    String tic = rs.getString("TIC");
                    String name = rs.getString("name");
                    String date = rs.getString("ddate");

                    // Append passenger details to the TextArea
                    passengerList.append(String.format("%-10s %-10s %-20s %-15s%n", pnr, tic, name, date));
                }

                if (!found) {
                    passengerList.setText("No passengers found for Flight Code: " + flightCode);
                }
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
            }
        } else if (ae.getSource() == back) {
            setVisible(false); // Close the View Passengers window
        }
    }
}