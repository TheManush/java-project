package airlinemanagementsystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class AdminDashboard extends JFrame implements ActionListener {
    JButton addFlight, viewFlights, viewPassengers, logout;
    JLabel headerLabel;
    JPanel panel;

    public AdminDashboard() {
        setTitle("Admin Dashboard");
        setSize(900, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JLabel background = new JLabel(new ImageIcon("C:\\Users\\Amd\\Desktop\\OOP Project\\AirlineManagementSystem4\\src\\airlinemanagementsystem\\icons\\dashboard_bg1.jpg"));
        background.setLayout(new BorderLayout());
        setContentPane(background);

        headerLabel = new JLabel("Welcome, Admin!", SwingConstants.CENTER);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 24));
        headerLabel.setForeground(Color.BLACK);
        background.add(headerLabel, BorderLayout.NORTH);

        panel = new JPanel(new GridLayout(2, 2, 20, 20));
        panel.setOpaque(false);

        addFlight = createButton("Add Flight", "C:\\Users\\Amd\\Desktop\\OOP Project\\AirlineManagementSystem4\\src\\airlinemanagementsystem\\icons\\add_icon.jpg", "Add a new flight to the system");
        viewFlights = createButton("View Flights", "C:\\Users\\Amd\\Desktop\\OOP Project\\AirlineManagementSystem4\\src\\airlinemanagementsystem\\icons\\flight_icon.jpg", "Check available flights");
        viewPassengers = createButton("View Passengers", "C:\\Users\\Amd\\Desktop\\OOP Project\\AirlineManagementSystem4\\src\\airlinemanagementsystem\\icons\\passenger_icon.jpg", "View registered passengers");
        logout = createButton("Logout", "C:\\Users\\Amd\\Desktop\\OOP Project\\AirlineManagementSystem4\\src\\airlinemanagementsystem\\icons\\logout_icon.jpeg", "Log out of the system");

        panel.add(addFlight);
        panel.add(viewFlights);
        panel.add(viewPassengers);
        panel.add(logout);

        background.add(panel, BorderLayout.CENTER);

        setVisible(true);
    }

    private JButton createButton(String text, String iconPath, String tooltip) {
        ImageIcon originalIcon = new ImageIcon(iconPath);
        Image scaledImage = originalIcon.getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH);
        ImageIcon resizedIcon = new ImageIcon(scaledImage);

        JButton button = new JButton(text, resizedIcon);
        button.setFont(new Font("Arial", Font.BOLD, 18));
        button.setForeground(Color.WHITE);
        button.setBackground(new Color(30, 144, 255));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setToolTipText(tooltip);
        button.setHorizontalTextPosition(SwingConstants.CENTER);
        button.setVerticalTextPosition(SwingConstants.BOTTOM);
        button.addActionListener(this);

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(0, 102, 204));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(30, 144, 255));
            }
        });

        return button;
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == addFlight) {
            new AddFlightWindow();
        } else if (ae.getSource() == viewFlights) {
            new FlightInfo();
        } else if (ae.getSource() == viewPassengers) {
            new ViewPassengers();
        } else if (ae.getSource() == logout) {
            setVisible(false);
            new Login();
        }
    }

    public static void main(String[] args) {
        new AdminDashboard();
    }
}
