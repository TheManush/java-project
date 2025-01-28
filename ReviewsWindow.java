package airlinemanagementsystem;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class ReviewsWindow extends JFrame {

    private JTextArea reviewsDisplayArea;
    private JTextField searchField;
    private JButton searchButton;

    public ReviewsWindow() {
        setTitle("View Reviews");
        setLayout(new BorderLayout());
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);

        // Search Panel
        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new FlowLayout());

        searchField = new JTextField(10);
        searchField.setFont(new Font("Tahoma", Font.PLAIN, 14));
        searchPanel.add(new JLabel("Search by Rating (1-5):"));
        searchPanel.add(searchField);

        searchButton = new JButton("Search");
        searchButton.setFont(new Font("Tahoma", Font.BOLD, 14));
        searchButton.addActionListener(e -> loadReviews());
        searchPanel.add(searchButton);

        add(searchPanel, BorderLayout.NORTH);

        // Reviews Display Area
        reviewsDisplayArea = new JTextArea();
        reviewsDisplayArea.setEditable(false);
        reviewsDisplayArea.setFont(new Font("Tahoma", Font.PLAIN, 14));
        reviewsDisplayArea.setLineWrap(true);
        reviewsDisplayArea.setWrapStyleWord(true);

        JScrollPane scrollPane = new JScrollPane(reviewsDisplayArea);
        add(scrollPane, BorderLayout.CENTER);

        loadReviews();
        setVisible(true);
    }

    private void loadReviews() {
        StringBuilder reviews = new StringBuilder();
        try {
            Conn conn = new Conn();
            String query = "SELECT username, review, rating, timestamp FROM reviews";

            // Add rating filter if search field is not empty
            String ratingFilter = searchField.getText().trim();
            if (!ratingFilter.isEmpty()) {
                query += " WHERE rating = " + ratingFilter;
            }

            query += " ORDER BY timestamp DESC";

            ResultSet rs = conn.s.executeQuery(query);

            while (rs.next()) {
                String username = rs.getString("username");
                String review = rs.getString("review");
                int rating = rs.getInt("rating");
                String timestamp = rs.getString("timestamp");

                reviews.append("User: ").append(username).append("\n");
                reviews.append("Rating: ").append(rating).append("/5\n");
                reviews.append("Review: ").append(review).append("\n");
                reviews.append("Posted on: ").append(timestamp).append("\n\n");
            }

            reviewsDisplayArea.setText(reviews.toString());
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error loading reviews.");
        }
    }
}