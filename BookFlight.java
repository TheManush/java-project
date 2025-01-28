package airlinemanagementsystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import com.toedter.calendar.JDateChooser;
import java.util.*;

public class BookFlight extends JFrame implements ActionListener {

    JTextField tfid;
    JLabel tfname, tfnationality, tfaddress, labelgender, labelfname, labelfcode;
    JButton bookflight, fetchButton, flight;
    Choice source, destination;
    JDateChooser dcdate;
    JLabel loggedInUserLabel; // To display the logged-in user

    public BookFlight() {
        getContentPane().setBackground(Color.WHITE);
        setLayout(null);

        // Add a label to show the logged-in username at the top-right
        loggedInUserLabel = new JLabel("Logged in as: " + UserSession.loggedInUser);
        loggedInUserLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
        loggedInUserLabel.setForeground(Color.BLUE);
        loggedInUserLabel.setBounds(850, 20, 200, 25); // Position it at the top-right corner
        add(loggedInUserLabel);

        JLabel heading = new JLabel("Book Flight");
        heading.setBounds(420, 20, 500, 35);
        heading.setFont(new Font("Tahoma", Font.PLAIN, 32));
        heading.setForeground(Color.BLUE);
        add(heading);

        JLabel lblid = new JLabel("National ID");
        lblid.setBounds(60, 80, 150, 25);
        lblid.setFont(new Font("Tahoma", Font.PLAIN, 16));
        add(lblid);

        tfid = new JTextField();
        tfid.setBounds(220, 80, 150, 25);
        add(tfid);

        fetchButton = new JButton("Search");
        fetchButton.setBackground(Color.BLACK);
        fetchButton.setForeground(Color.WHITE);
        fetchButton.setBounds(380, 80, 120, 25);
        fetchButton.addActionListener(this);
        add(fetchButton);

        JLabel lblname = new JLabel("Name");
        lblname.setBounds(60, 130, 150, 25);
        lblname.setFont(new Font("Tahoma", Font.PLAIN, 16));
        add(lblname);

        tfname = new JLabel();
        tfname.setBounds(220, 130, 150, 25);
        add(tfname);

        JLabel lblnationality = new JLabel("Nationality");
        lblnationality.setBounds(60, 180, 150, 25);
        lblnationality.setFont(new Font("Tahoma", Font.PLAIN, 16));
        add(lblnationality);

        tfnationality = new JLabel();
        tfnationality.setBounds(220, 180, 150, 25);
        add(tfnationality);

        JLabel lbladdress = new JLabel("Address");
        lbladdress.setBounds(60, 230, 150, 25);
        lbladdress.setFont(new Font("Tahoma", Font.PLAIN, 16));
        add(lbladdress);

        tfaddress = new JLabel();
        tfaddress.setBounds(220, 230, 150, 25);
        add(tfaddress);

        JLabel lblgender = new JLabel("Gender");
        lblgender.setBounds(60, 280, 150, 25);
        lblgender.setFont(new Font("Tahoma", Font.PLAIN, 16));
        add(lblgender);

        labelgender = new JLabel();
        labelgender.setBounds(220, 280, 150, 25);
        add(labelgender);

        JLabel lblsource = new JLabel("Source");
        lblsource.setBounds(60, 330, 150, 25);
        lblsource.setFont(new Font("Tahoma", Font.PLAIN, 16));
        add(lblsource);

        source = new Choice();
        source.setBounds(220, 330, 150, 25);
        add(source);

        JLabel lbldest = new JLabel("Destination");
        lbldest.setBounds(60, 380, 150, 25);
        lbldest.setFont(new Font("Tahoma", Font.PLAIN, 16));
        add(lbldest);

        destination = new Choice();
        destination.setBounds(220, 380, 150, 25);
        add(destination);

        try {
            Conn c = new Conn();
            String query = "SELECT * FROM flight";
            ResultSet rs = c.s.executeQuery(query);

            while(rs.next()) {
                source.add(rs.getString("source"));
                destination.add(rs.getString("destination"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        flight = new JButton("Search Flights");
        flight.setBackground(Color.BLACK);
        flight.setForeground(Color.WHITE);
        flight.setBounds(380, 380, 120, 25);
        flight.addActionListener(this);
        add(flight);

        JLabel lblfname = new JLabel("Flight Name");
        lblfname.setBounds(60, 430, 150, 25);
        lblfname.setFont(new Font("Tahoma", Font.PLAIN, 16));
        add(lblfname);

        labelfname = new JLabel();
        labelfname.setBounds(220, 430, 150, 25);
        add(labelfname);

        JLabel lblfcode = new JLabel("Flight Code");
        lblfcode.setBounds(60, 480, 150, 25);
        lblfcode.setFont(new Font("Tahoma", Font.PLAIN, 16));
        add(lblfcode);

        labelfcode = new JLabel();
        labelfcode.setBounds(220, 480, 150, 25);
        add(labelfcode);

        JLabel lbldate = new JLabel("Date of Travel");
        lbldate.setBounds(60, 530, 150, 25);
        lbldate.setFont(new Font("Tahoma", Font.PLAIN, 16));
        add(lbldate);

        dcdate = new JDateChooser();
        dcdate.setBounds(220, 530, 150, 25);
        add(dcdate);

        bookflight = new JButton("Book Flight");
        bookflight.setBackground(Color.BLACK);
        bookflight.setForeground(Color.WHITE);
        bookflight.setBounds(220, 580, 150, 25);
        bookflight.addActionListener(this);
        add(bookflight);

        setSize(1100, 700);
        setLocation(200, 50);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
    if (ae.getSource() == fetchButton) {
        String id = tfid.getText();
        String loggedInUser = UserSession.loggedInUser;

        try {
            Conn conn = new Conn();
            String query = "SELECT * FROM passenger1 WHERE id = '" + id + "' AND added_by = '" + loggedInUser + "'";

            ResultSet rs = conn.s.executeQuery(query);

            if (rs.next()) {
                tfname.setText(rs.getString("name"));
                tfnationality.setText(rs.getString("nationality"));
                tfaddress.setText(rs.getString("address"));
                labelgender.setText(rs.getString("gender"));
            } else {
                JOptionPane.showMessageDialog(null, "No matching passenger found for your account.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    } else if (ae.getSource() == flight) {
        String src = source.getSelectedItem();
        String dest = destination.getSelectedItem();
        try {
            Conn conn = new Conn();
            String query = "SELECT * FROM flight WHERE source = '" + src + "' AND destination = '" + dest + "'";

            ResultSet rs = conn.s.executeQuery(query);

            if (rs.next()) {
                labelfname.setText(rs.getString("f_name"));
                labelfcode.setText(rs.getString("f_code"));
            } else {
                JOptionPane.showMessageDialog(null, "No Flights Found");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    } else if (ae.getSource() == bookflight) {
        // Validate required fields
        String id = tfid.getText();
        String name = tfname.getText();
        String nationality = tfnationality.getText();
        String flightname = labelfname.getText();
        String flightcode = labelfcode.getText();
        String src = source.getSelectedItem();
        String des = destination.getSelectedItem();
        String ddate = ((JTextField) dcdate.getDateEditor().getUiComponent()).getText();
        String loggedInUser = UserSession.loggedInUser;

        if (id.isEmpty() || name.isEmpty() || nationality.isEmpty() || flightname.isEmpty() || flightcode.isEmpty() || src.isEmpty() || des.isEmpty() || ddate.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please fill in all fields.");
            return;
        }

        try {
        Random random = new Random();
            Conn conn = new Conn();

            // Updated query to insert reservation
            String query = "INSERT INTO reservation (PNR, TIC, id, name, nationality, flightname, flightcode, src, des, ddate, added_by) VALUES ('" +
                           "PNR-" + random.nextInt(1000000) + "', '" +
                           "TIC-" + random.nextInt(10000) + "', '" +
                           id + "', '" +
                           name + "', '" +
                           nationality + "', '" +
                           flightname + "', '" +
                           flightcode + "', '" +
                           src + "', '" +
                           des + "', '" +
                           ddate + "', '" +
                           loggedInUser + "')";
            // Debugging: Print query to ensure it's correct
            System.out.println("Executing query: " + query);

            conn.s.executeUpdate(query);

            JOptionPane.showMessageDialog(null, "Ticket Booked Successfully");

            setVisible(false);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error booking the flight.");
        }
    }
}


    public static void main(String[] args) {
        new BookFlight();
    }
}
