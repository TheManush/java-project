package csedu.flight.mangement.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

// Base class for shared UI features
abstract class BaseWindow extends JFrame {

    public BaseWindow(String title, int width, int height) {
        setTitle(title);
        setSize(width, height);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
    }

    protected JButton createButton(String text, int width, int height, Font font, Color bgColor, Color fgColor) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(width, height));
        button.setFont(font);
        button.setBackground(bgColor);
        button.setForeground(fgColor);
        return button;
    }

    protected JLabel createLabel(String text, Font font, Color color) {
        JLabel label = new JLabel(text);
        label.setFont(font);
        label.setForeground(color);
        return label;
    }

    protected JTextArea createTextArea(Font font, boolean editable, boolean lineWrap) {
        JTextArea textArea = new JTextArea();
        textArea.setFont(font);
        textArea.setEditable(editable);
        textArea.setLineWrap(lineWrap);
        textArea.setWrapStyleWord(lineWrap);
        return textArea;
    }
}

// Review posting class
// Review posting class
public class Review extends BaseWindow implements ActionListener {

    private JTextArea reviewArea;
    private JButton postButton, viewReviewsButton, backButton;
    private JLabel usernameLabel, charLimitLabel, ratingLabel;
    private JComboBox<Integer> ratingComboBox;

    public Review() {
        super("User Reviews", 600, 450);
        setLayout(null);

        getContentPane().setBackground(new Color(245, 245, 245));

        // Back Button
        backButton = createButton("Back", 80, 30, new Font("Tahoma", Font.BOLD, 12), new Color(0, 123, 255), Color.WHITE);
        backButton.setBounds(10, 10, 80, 30);
        backButton.addActionListener(this);
        add(backButton);

        // Display logged-in username
        usernameLabel = createLabel("Logged in as: " + UserSession.loggedInUser, new Font("Tahoma", Font.BOLD, 14), new Color(50, 50, 50));
        usernameLabel.setBounds(100, 20, 400, 30);
        add(usernameLabel);

        JLabel titleLabel = createLabel("Post Your Review:", new Font("Tahoma", Font.BOLD, 16), new Color(50, 50, 50));
        titleLabel.setBounds(50, 60, 200, 30);
        add(titleLabel);

        // Review Area
        reviewArea = createTextArea(new Font("Tahoma", Font.PLAIN, 14), true, true);
        reviewArea.setBounds(50, 100, 500, 150);
        reviewArea.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));

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
        charLimitLabel = createLabel("Character limit: 500", new Font("Tahoma", Font.ITALIC, 12), new Color(120, 120, 120));
        charLimitLabel.setBounds(50, 260, 200, 20);
        add(charLimitLabel);

        // Rating Label and ComboBox
        ratingLabel = createLabel("Rating (1-5):", new Font("Tahoma", Font.BOLD, 14), Color.BLACK);
        ratingLabel.setBounds(50, 290, 100, 20);
        add(ratingLabel);

        Integer[] ratings = {1, 2, 3, 4, 5};
        ratingComboBox = new JComboBox<>(ratings);
        ratingComboBox.setBounds(160, 290, 50, 20);
        add(ratingComboBox);

        // Post Review Button
        postButton = createButton("Post Review", 150, 30, new Font("Tahoma", Font.BOLD, 14), new Color(0, 123, 255), Color.WHITE);
        postButton.setBounds(50, 330, 150, 30);
        postButton.addActionListener(this);
        add(postButton);

        // View Reviews Button
        viewReviewsButton = createButton("View Reviews", 150, 30, new Font("Tahoma", Font.BOLD, 14), new Color(0, 123, 255), Color.WHITE);
        viewReviewsButton.setBounds(250, 330, 150, 30);
        viewReviewsButton.addActionListener(this);
        add(viewReviewsButton);

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
            new Reviews1Window(); // Open the ReviewsWindow
        } else if (ae.getSource() == backButton) {
            dispose(); // Close the current window
        }
    }

    public static void main(String[] args) {
        new Review();
    }
}



class Reviews1Window extends BaseWindow {

    private JTextArea reviewsDisplayArea;
    private JTextField searchField;
    private JButton searchButton;

    public Reviews1Window() {
        super("View Reviews", 600, 400);
        setLayout(new BorderLayout());

        // Search Panel
        JPanel searchPanel = new JPanel(new FlowLayout());
        searchField = new JTextField(10);
        searchField.setFont(new Font("Tahoma", Font.PLAIN, 14));
        searchPanel.add(new JLabel("Search by Rating (1-5):"));
        searchPanel.add(searchField);

        searchButton = createButton("Search", 100, 30, new Font("Tahoma", Font.BOLD, 14), new Color(0, 123, 255), Color.WHITE);
        searchButton.addActionListener(e -> loadReviews());
        searchPanel.add(searchButton);

        add(searchPanel, BorderLayout.NORTH);

        // Reviews Display Area
        reviewsDisplayArea = createTextArea(new Font("Tahoma", Font.PLAIN, 14), false, true);
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
