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
    private PrintWriter out;

    public Client() {
        setTitle("Airline Chatbot");
        setSize(700, 600);
        setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(new Color(176, 190, 197)); // gray

        chatPanel = new JPanel();
        chatPanel.setLayout(new BoxLayout(chatPanel, BoxLayout.Y_AXIS));
        chatPanel.setBackground(Color.WHITE); // Chat background white
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

        questionDropdown = new JComboBox<>(questions);
        questionDropdown.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        questionDropdown.setPreferredSize(new Dimension(300, 30));

        JButton sendButton = createButton("Send", new Color(76, 175, 80), e -> sendMessage());
        JButton backButton = createButton("Back", new Color(33, 150, 243), e -> {
            dispose();
            new Front();
        });

        setLayout(new BorderLayout(10, 10));
        add(scrollPane, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        bottomPanel.setBackground(new Color(66, 66, 66));
        bottomPanel.add(questionDropdown);
        bottomPanel.add(sendButton);
        bottomPanel.add(backButton);
        add(bottomPanel, BorderLayout.SOUTH);

        connectToServer();
    }

    private JButton createButton(String text, Color color, ActionListener listener) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setPreferredSize(new Dimension(100, 30));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.addActionListener(listener);
        return button;
    }

    private void sendMessage() {
        String selectedQuestion = (String) questionDropdown.getSelectedItem();
        addMessage(selectedQuestion, true);
        out.println(selectedQuestion.toLowerCase());
        questionDropdown.setSelectedIndex(0);
    }

    private void addMessage(String message, boolean isClient) {
        JPanel messagePanel = new JPanel(new FlowLayout(isClient ? FlowLayout.RIGHT : FlowLayout.LEFT));
        messagePanel.setBackground(Color.WHITE); 

        
        JTextArea textArea = new JTextArea(message);
        textArea.setEditable(false);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setForeground(Color.WHITE);
        textArea.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        textArea.setColumns(30);
        textArea.setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 12));
        textArea.setBackground(isClient ? new Color(65,105,225) : new Color(0, 0, 255));

        
        JPanel bubblePanel = new JPanel(new BorderLayout());
        bubblePanel.setBackground(Color.WHITE); 
        bubblePanel.add(textArea, BorderLayout.CENTER);
        bubblePanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        messagePanel.add(bubblePanel);
        chatPanel.add(messagePanel);
        chatPanel.revalidate();
        chatPanel.repaint();

        JScrollBar vertical = ((JScrollPane) chatPanel.getParent().getParent()).getVerticalScrollBar();
        vertical.setValue(vertical.getMaximum());
    }

    private void connectToServer() {
        try {
            Socket socket = new Socket("localhost", 7500);
            out = new PrintWriter(socket.getOutputStream(), true);
            new Thread(new ServerListener(socket)).start();
        } catch (Exception e) {
            e.printStackTrace();
        }
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
                    addMessage(response, false); 
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

