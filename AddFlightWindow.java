package airlinemanagementsystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class AddFlightWindow extends JFrame implements ActionListener {
    JTextField tfFlightCode, tfFlightName, tfSource, tfDestination;
    JButton submit, back;

    public AddFlightWindow() {
        setTitle("Add Flight");
        setSize(500, 400);
        setLocationRelativeTo(null);
        setLayout(null);

        // Labels
        JLabel lblFlightCode = new JLabel("Flight Code:");
        lblFlightCode.setBounds(50, 50, 100, 20);
        add(lblFlightCode);

        JLabel lblFlightName = new JLabel("Flight Name:");
        lblFlightName.setBounds(50, 100, 100, 20);
        add(lblFlightName);

        JLabel lblSource = new JLabel("Source:");
        lblSource.setBounds(50, 150, 100, 20);
        add(lblSource);

        JLabel lblDestination = new JLabel("Destination:");
        lblDestination.setBounds(50, 200, 100, 20);
        add(lblDestination);

        // Text Fields
        tfFlightCode = new JTextField();
        tfFlightCode.setBounds(150, 50, 200, 25);
        add(tfFlightCode);

        tfFlightName = new JTextField();
        tfFlightName.setBounds(150, 100, 200, 25);
        add(tfFlightName);

        tfSource = new JTextField();
        tfSource.setBounds(150, 150, 200, 25);
        add(tfSource);

        tfDestination = new JTextField();
        tfDestination.setBounds(150, 200, 200, 25);
        add(tfDestination);

        // Buttons
        submit = new JButton("Submit");
        submit.setBounds(100, 270, 100, 30);
        submit.addActionListener(this);
        add(submit);

        back = new JButton("Back");
        back.setBounds(250, 270, 100, 30);
        back.addActionListener(this);
        add(back);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == submit) {
            String flightCode = tfFlightCode.getText();
            String flightName = tfFlightName.getText();
            String source = tfSource.getText();
            String destination = tfDestination.getText();

            if (flightCode.isEmpty() || flightName.isEmpty() || source.isEmpty() || destination.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please fill all fields!");
                return;
            }

            try {
                Conn c = new Conn();
                String query = "INSERT INTO flight (f_code, f_name, source, destination) VALUES ('" +
                        flightCode + "', '" + flightName + "', '" + source + "', '" + destination + "')";
                c.s.executeUpdate(query);
                JOptionPane.showMessageDialog(null, "Flight Added Successfully!");
                clearFields();
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
            }
        } else if (ae.getSource() == back) {
            setVisible(false); // Close the Add Flight window
        }
    }

    private void clearFields() {
        tfFlightCode.setText("");
        tfFlightName.setText("");
        tfSource.setText("");
        tfDestination.setText("");
    }
}