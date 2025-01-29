package airlinemanagementsystem;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import net.proteanit.sql.DbUtils;

public class FlightInfo extends JFrame {
    
    public FlightInfo() {
        
        getContentPane().setBackground(Color.WHITE);
        setLayout(null);
        
        
        JLabel lblTitle = new JLabel("Available Flights");
        lblTitle.setFont(new Font("Tahoma", Font.BOLD, 24));
        lblTitle.setForeground(Color.BLUE);
        lblTitle.setBounds(300, 20, 300, 30);
        add(lblTitle);
        
       
        JTable table = new JTable() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table.setFont(new Font("Tahoma", Font.PLAIN, 14));
        table.setRowHeight(25);
        table.setSelectionBackground(Color.LIGHT_GRAY);

        
        try {
            Conn conn = new Conn();
            ResultSet rs = conn.s.executeQuery("SELECT * FROM flight");
            table.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        
        JScrollPane jsp = new JScrollPane(table);
        jsp.setBounds(50, 80, 700, 300);
        add(jsp);
        
        
        JLabel lblSearch = new JLabel("Search by Destination:");
        lblSearch.setFont(new Font("Tahoma", Font.PLAIN, 16));
        lblSearch.setBounds(50, 400, 200, 25);
        add(lblSearch);
        
        JTextField tfSearch = new JTextField();
        tfSearch.setBounds(220, 400, 200, 25);
        add(tfSearch);
        
        JButton btnSearch = new JButton("Search");
        btnSearch.setBounds(440, 400, 100, 25);
        btnSearch.addActionListener(e -> {
            String destination = tfSearch.getText();
            if (!destination.isEmpty()) {
                try {
                    Conn conn = new Conn();
                    String query = "SELECT * FROM flight WHERE destination LIKE ?";
                    PreparedStatement ps = conn.c.prepareStatement(query);
                    ps.setString(1, "%" + destination + "%");
                    ResultSet rs = ps.executeQuery();
                    table.setModel(DbUtils.resultSetToTableModel(rs));
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            } else {
                JOptionPane.showMessageDialog(null, "Please enter a destination to search!");
            }
        });
        add(btnSearch);
        
        
        JButton btnBack = new JButton("Back");
        btnBack.setBounds(600, 400, 100, 25);
        btnBack.addActionListener(e -> setVisible(false));
        add(btnBack);
        
       
        setSize(800, 500);
        setLocation(400, 200);
        setVisible(true);
    }

    public static void main(String[] args) {
        new FlightInfo();
    }
}
