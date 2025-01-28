package airlinemanagementsystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class AdminDashboard extends JFrame implements ActionListener {
    JButton addFlight, viewFlights, viewPassengers, logout;

    public AdminDashboard() {
        setTitle("Admin Dashboard");
        setSize(800, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(2, 2, 10, 10)); // 2 rows, 2 columns

        // Buttons
        addFlight = new JButton("Add Flight");
        viewFlights = new JButton("View Flights");
        viewPassengers = new JButton("View Passengers");
        logout = new JButton("Logout");

        // Add action listeners
        addFlight.addActionListener(this);
        viewFlights.addActionListener(this);
        viewPassengers.addActionListener(this);
        logout.addActionListener(this);

        // Add buttons to the frame
        add(addFlight);
        add(viewFlights);
        add(viewPassengers);
        add(logout);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == addFlight) {
            new AddFlightWindow(); // Open the Add Flight window
        } else if (ae.getSource() == viewFlights) {
            new FlightInfo();
        } else if (ae.getSource() == viewPassengers) {
            new ViewPassengers();
        } else if (ae.getSource() == logout) {
            setVisible(false); // Close the admin dashboard
            new Login(); // Return to the login screen
        }
    }
        public static void main(String[] args) {
        // For testing purposes, set the logged-in user
        
        new AdminDashboard();
    }
}
