package airlinemanagementsystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class Review extends JFrame implements ActionListener {

    JTextArea reviewArea;
    JButton postButton, viewReviewsButton, backButton;
    JLabel usernameLabel, charLimitLabel, ratingLabel;
    JComboBox<Integer> ratingComboBox;

    public Review() {
        setTitle("User Reviews");
        setLayout(null);
        getContentPane().setBackground(new Color(245, 245, 245)); // Soft background color
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Disable close operation
        setResizable(false); // Disable resizing

        // Back Button
        backButton = new JButton("Back");
        backButton.setBounds(10, 10, 80, 30);
        backButton.setBackground(new Color(0, 123, 255)); // Bright button color
        backButton.setForeground(Color.WHITE);
        backButton.setFont(new Font("Tahoma", Font.BOLD, 12));
        backButton.addActionListener(this);
        add(backButton);

        // Display logged-in username
        usernameLabel = new JLabel("Logged in as: " + UserSession.loggedInUser);
        usernameLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
        usernameLabel.setBounds(100, 20, 400, 30);
        usernameLabel.setHorizontalAlignment(SwingConstants.LEFT);
        usernameLabel.setForeground(new Color(50, 50, 50));
        add(usernameLabel);

        JLabel titleLabel = new JLabel("Post Your Review:");
        titleLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
        titleLabel.setBounds(50, 60, 200, 30);
        titleLabel.setForeground(new Color(50, 50, 50));
        add(titleLabel);

        // Large text area for review
        reviewArea = new JTextArea();
        reviewArea.setBounds(50, 100, 500, 150);
        reviewArea.setLineWrap(true);
        reviewArea.setWrapStyleWord(true);
        reviewArea.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
        reviewArea.setFont(new Font("Tahoma", Font.PLAIN, 14));

        // Limit the text to 500 characters
        reviewArea.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                if (reviewArea.getText().length() >= 500) {
                    e.consume();
                }
            }
        });
        add(reviewArea);

        // Character limit label
        charLimitLabel = new JLabel("Character limit: 500");
        charLimitLabel.setFont(new Font("Tahoma", Font.ITALIC, 12));
        charLimitLabel.setBounds(50, 260, 200, 20);
        charLimitLabel.setForeground(new Color(120, 120, 120));
        add(charLimitLabel);

        // Rating Label and ComboBox
        ratingLabel = new JLabel("Rating (1-5):");
        ratingLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
        ratingLabel.setBounds(50, 290, 100, 20);
        add(ratingLabel);

        Integer[] ratings = {1, 2, 3, 4, 5};
        ratingComboBox = new JComboBox<>(ratings);
        ratingComboBox.setBounds(160, 290, 50, 20);
        add(ratingComboBox);

        postButton = new JButton("Post Review");
        postButton.setBounds(50, 330, 150, 30);
        postButton.setBackground(new Color(0, 123, 255)); // Bright button color
        postButton.setForeground(Color.WHITE);
        postButton.setFont(new Font("Tahoma", Font.BOLD, 14));
        postButton.addActionListener(this);
        add(postButton);

        // Button to open reviews in another window
        viewReviewsButton = new JButton("View Reviews");
        viewReviewsButton.setBounds(250, 330, 150, 30);
        viewReviewsButton.setBackground(new Color(0, 123, 255));
        viewReviewsButton.setForeground(Color.WHITE);
        viewReviewsButton.setFont(new Font("Tahoma", Font.BOLD, 14));
        viewReviewsButton.addActionListener(this);
        add(viewReviewsButton);

        setSize(600, 450);
        setLocation(300, 200);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == postButton) {
            String reviewText = reviewArea.getText();
            int rating = (int) ratingComboBox.getSelectedItem();

            if (reviewText.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Review cannot be empty!");
                return;
            }

            try {
                Conn conn = new Conn();
                String query = "INSERT INTO reviews (username, review, rating) VALUES ('" + UserSession.loggedInUser + "', '" + reviewText + "', " + rating + ")";
                conn.s.executeUpdate(query);

                JOptionPane.showMessageDialog(null, "Review posted successfully!");
                reviewArea.setText("");
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (ae.getSource() == viewReviewsButton) {
            new ReviewsWindow();
        } else if (ae.getSource() == backButton) {
            dispose(); // Close the current window
        }
    }

    public static void main(String[] args) {
        new Review();
    }
}