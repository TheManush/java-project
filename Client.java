package airlinemanagementsystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.Socket;

public class Client extends JFrame {
    private JComboBox<String> questionDropdown;
    private JPanel chatPanel;
    private JButton sendButton, clearButton, backButton;
    private PrintWriter out;

    public Client() {
        setTitle("Airline Chatbot");
        setSize(700, 600); 
        setResizable(false); 
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(new Color(176, 190, 197)); // light gray

        // Chat panel 
        chatPanel = new JPanel();
        chatPanel.setLayout(new BoxLayout(chatPanel, BoxLayout.Y_AXIS));
        chatPanel.setBackground(new Color(66, 66, 66)); // Dark gray 
        JScrollPane scrollPane = new JScrollPane(chatPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

     
        String[] questions = {
            "hi",
            "who am i speaking to?",
            "can i give a review?",
            "tell me about the developers",
            "what can i do here?",
            "can i cancel my ticket?",
            "how can i check your flights?",
            "bye"
        };

        // Dropdown for selecting questions
        questionDropdown = new JComboBox<>(questions);
        questionDropdown.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        questionDropdown.setPreferredSize(new Dimension(300, 30));

        // Send button with styling
        sendButton = new JButton("Send");
        sendButton.setFont(new Font("Segoe UIy", Font.BOLD, 14));
        sendButton.setBackground(new Color(0, 255, 0)); // Green 
        sendButton.setForeground(Color.WHITE);
        sendButton.setFocusPainted(false);
        sendButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Clear button with styling
        clearButton = new JButton("Clear");
        clearButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        clearButton.setBackground(new Color(255, 0, 0)); // Red 
        clearButton.setForeground(Color.WHITE);
        clearButton.setFocusPainted(false);
        clearButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Back button with styling
        backButton = new JButton("Back");
        backButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        backButton.setBackground(new Color(0, 0, 255)); // Blue 
        backButton.setForeground(Color.WHITE);
        backButton.setFocusPainted(false);
        backButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

       
        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedQuestion = (String) questionDropdown.getSelectedItem();
                addMessage(selectedQuestion, true); // Align to the right (client message)
                out.println(selectedQuestion.toLowerCase()); // Send question in lowercase
                questionDropdown.setSelectedIndex(0); // Reset dropdown
            }
        });

        
        clearButton.addActionListener(new ActionListener() {
            
            public void actionPerformed(ActionEvent e) {
                chatPanel.removeAll(); 
                chatPanel.revalidate();
                chatPanel.repaint();
            }
        });

        // Back button action
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); 
                new Front(); 
            }
        });

        // Layout setup
        setLayout(new BorderLayout(10, 10));
        add(scrollPane, BorderLayout.CENTER);

        // Right panel for buttons
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new GridLayout(3, 1, 10, 20)); // 3 rows, 1 column, spacing between buttons
        rightPanel.setBackground(new Color(66, 66, 66)); // Dark gray 
        rightPanel.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10)); // Padding

        
        rightPanel.add(sendButton);
        rightPanel.add(clearButton);
        rightPanel.add(backButton);

        
        add(rightPanel, BorderLayout.EAST);

        
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        bottomPanel.setBackground(new Color(66, 66, 66)); // Dark gray
        bottomPanel.add(questionDropdown);
        
        add(bottomPanel, BorderLayout.SOUTH);

        try {
            Socket socket = new Socket("localhost", 7500);  //server
            out = new PrintWriter(socket.getOutputStream(), true);
            new Thread(new ServerListener(socket)).start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addMessage(String message, boolean isClient) {
        JPanel messagePanel = new JPanel();
        messagePanel.setLayout(new FlowLayout(isClient ? FlowLayout.RIGHT : FlowLayout.LEFT));
        messagePanel.setBackground(new Color(66, 66, 66)); // Dark gray 

        // Create bubble
        JTextArea textArea = new JTextArea(message);
        textArea.setEditable(false);
        textArea.setLineWrap(true); 
        textArea.setWrapStyleWord(true); 
        textArea.setBackground(isClient ? new Color(76, 175, 80) : new Color(33, 150, 243)); // Green for client, blue for server
        textArea.setForeground(Color.WHITE);
        textArea.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        textArea.setColumns(30); 
        textArea.setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 12)); 

        // Add rounded corners to the bubble
        JPanel bubblePanel = new JPanel(new BorderLayout());
        bubblePanel.setBackground(new Color(66, 66, 66)); // Dark gray background
        bubblePanel.add(textArea, BorderLayout.CENTER);
        bubblePanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5)); // Padding around the bubble

        messagePanel.add(bubblePanel);
        chatPanel.add(messagePanel);
        chatPanel.revalidate();
        chatPanel.repaint();

        // Scroll to the bottom of the chat panel
        JScrollBar vertical = ((JScrollPane) chatPanel.getParent().getParent()).getVerticalScrollBar();
        vertical.setValue(vertical.getMaximum());
    }

    private class ServerListener implements Runnable {
        private BufferedReader in;

        public ServerListener(Socket socket) throws IOException {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        }

        public void run() {
            try {
                String response;
                while ((response = in.readLine()) != null) {
                    addMessage(response, false); // Align to the left (server message)
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void startClient() {
        SwingUtilities.invokeLater(() -> {
            Client client = new Client();
            client.setVisible(true);
        });
    }

    public static void main(String[] args) {
        startClient();
    }
}
