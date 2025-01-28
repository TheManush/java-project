package airlinemanagementsystem;

import java.io.*;
import java.net.*;
import java.util.HashMap;

public class AirlineServer {
    private static HashMap<String, String> faq;

    public static void main(String[] args) {
        startServer();
    }

    public static void startServer() {
        faq = new HashMap<>();
        // Add airline-specific question-response pairs
        faq.put("hi", "Hello");
        faq.put("who am i speaking to?", "You are speaking to AIRU, CSEDU Airlines' virtual assistant.");
        faq.put("can i give a review?", "Yes, you can give us a review on the Review option. We accept all sorts of comments as it will inspire us to improve our service.");
        faq.put("tell me about the developers", "This is a Java OOP project made by students of CSEDU29; Ahnaf(38), Saad(32), Sakafy(34), Arafat(60).");
        faq.put("what can i do here?", "You can buy tickets for any of our available flights, receive the boarding pass, or cancel your ticket.");
        faq.put("can i cancel my ticket?", "Yes, you are able to cancel any ticket you have bought with the help of the PNR number.");
        faq.put("how can i check your flights?", "You can click the FlightInfo button to learn about upcoming flights.");
        faq.put("bye", "Goodbye. Have a lovely day!");

        try (ServerSocket serverSocket = new ServerSocket(7500)) {
            System.out.println("Airline Server is running...");
            while (true) {
                Socket socket = serverSocket.accept();
                new ClientHandler(socket).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static class ClientHandler extends Thread {
        private Socket socket;

        public ClientHandler(Socket socket) {
            this.socket = socket;
        }

        public void run() {
            try (
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true)
            ) {
                String question;
                while ((question = in.readLine()) != null) {
                    question = question.toLowerCase();
                    String reply = faq.getOrDefault(question, "Sorry, I don't understand that.");
                    out.println(reply);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}