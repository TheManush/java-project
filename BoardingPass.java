 
package csedu.flight.mangement.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.FileOutputStream;
import java.sql.*;
import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfWriter;
import java.util.*;

public class BoardingPass extends JFrame implements ActionListener {

    JTextField tfpnr;
    JLabel tfname, tfnationality, lblsrc, lbldest, labelfname, labelfcode, labeldate;
    JButton fetchButton, printButton;

    public BoardingPass() {
        setLayout(new BorderLayout());
        setResizable(false);

        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(24, 37, 49));
        headerPanel.setLayout(new BorderLayout());

        
        JLabel heading = new JLabel("AIR CSEDU", JLabel.CENTER);
        heading.setFont(new Font("Tahoma", Font.PLAIN, 32));
        heading.setForeground(new Color(46, 204, 113));
        headerPanel.add(heading, BorderLayout.CENTER);

        add(headerPanel, BorderLayout.NORTH);

        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(null);
        contentPanel.setBackground(new Color(240, 248, 255));
        add(contentPanel, BorderLayout.CENTER);

        JLabel subheading = new JLabel("Boarding Pass", JLabel.CENTER);
        subheading.setFont(new Font("Tahoma", Font.BOLD, 24));
        subheading.setForeground(new Color(52, 152, 219));
        subheading.setBounds(380, 20, 230, 30);
        contentPanel.add(subheading);

        JLabel lblaadhar = new JLabel("PNR DETAILS");
        lblaadhar.setBounds(60, 110, 150, 25);
        lblaadhar.setFont(new Font("Tahoma", Font.PLAIN, 16));
        lblaadhar.setForeground(Color.BLACK);
        contentPanel.add(lblaadhar);

        tfpnr = new JTextField();
        tfpnr.setBounds(220, 110, 150, 25);
        tfpnr.setBackground(new Color(236, 240, 241));
        contentPanel.add(tfpnr);

        fetchButton = new JButton("Enter");
        fetchButton.setBackground(new Color(41, 128, 185));
        fetchButton.setForeground(Color.WHITE);
        fetchButton.setBounds(380, 110, 120, 25);
        fetchButton.addActionListener(this);
        contentPanel.add(fetchButton);

        JLabel lblname = new JLabel("NAME:");
        lblname.setBounds(60, 150, 150, 25);
        lblname.setFont(new Font("Tahoma", Font.PLAIN, 16));
        lblname.setForeground(Color.BLACK);
        contentPanel.add(lblname);

        tfname = new JLabel();
        tfname.setBounds(220, 150, 150, 25);
        contentPanel.add(tfname);

        JLabel lblnationality = new JLabel("NATIONALITY:");
        lblnationality.setBounds(60, 190, 150, 25);
        lblnationality.setFont(new Font("Tahoma", Font.PLAIN, 16));
        lblnationality.setForeground(Color.BLACK);
        contentPanel.add(lblnationality);

        tfnationality = new JLabel();
        tfnationality.setBounds(220, 190, 150, 25);
        contentPanel.add(tfnationality);

        JLabel lbladdress = new JLabel("SRC:");
        lbladdress.setBounds(60, 230, 150, 25);
        lbladdress.setFont(new Font("Tahoma", Font.PLAIN, 16));
        lbladdress.setForeground(Color.BLACK);
        contentPanel.add(lbladdress);

        lblsrc = new JLabel();
        lblsrc.setBounds(220, 230, 150, 25);
        contentPanel.add(lblsrc);

        JLabel lblgender = new JLabel("DEST:");
        lblgender.setBounds(380, 230, 150, 25);
        lblgender.setFont(new Font("Tahoma", Font.PLAIN, 16));
        lblgender.setForeground(Color.BLACK);
        contentPanel.add(lblgender);

        lbldest = new JLabel();
        lbldest.setBounds(540, 230, 150, 25);
        contentPanel.add(lbldest);

        JLabel lblfname = new JLabel("Flight Name:");
        lblfname.setBounds(60, 270, 150, 25);
        lblfname.setFont(new Font("Tahoma", Font.PLAIN, 16));
        lblfname.setForeground(Color.BLACK);
        contentPanel.add(lblfname);

        labelfname = new JLabel();
        labelfname.setBounds(220, 270, 150, 25);
        contentPanel.add(labelfname);

        JLabel lblfcode = new JLabel("Flight Code:");
        lblfcode.setBounds(380, 270, 150, 25);
        lblfcode.setFont(new Font("Tahoma", Font.PLAIN, 16));
        lblfcode.setForeground(Color.BLACK);
        contentPanel.add(lblfcode);

        labelfcode = new JLabel();
        labelfcode.setBounds(540, 270, 150, 25);
        contentPanel.add(labelfcode);

        JLabel lbldate = new JLabel("Date:");
        lbldate.setBounds(60, 310, 150, 25);
        lbldate.setFont(new Font("Tahoma", Font.PLAIN, 16));
        lbldate.setForeground(Color.BLACK);
        contentPanel.add(lbldate);

        labeldate = new JLabel();
        labeldate.setBounds(220, 310, 150, 25);
        contentPanel.add(labeldate);

        
        printButton = new JButton("Print");
        printButton.setBackground(new Color(41, 128, 185));
        printButton.setForeground(Color.WHITE);
        printButton.setBounds(680, 350, 120, 25);
        printButton.addActionListener(this);
        contentPanel.add(printButton);
 
        setSize(1000, 500);
        setLocation(300, 150);
        setVisible(true);
    }
    
      public void actionPerformed(ActionEvent ae) {
        String loggedInUser = UserSession.loggedInUser;
        if (ae.getSource() == fetchButton) {
            String pnr = tfpnr.getText();

            try {
                Conn conn = new Conn();

                String query = "SELECT * FROM reservation WHERE PNR = '" + pnr + "' AND added_by = '" + loggedInUser + "'";
           
                ResultSet rs = conn.s.executeQuery(query);
                if (!rs.isBeforeFirst()) { // Check if the ResultSet is empty
                    JOptionPane.showMessageDialog(null, "No Information Found or You are not authorized to view this data.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (rs.next()) {
                    tfname.setText(rs.getString("name"));
                    tfnationality.setText(rs.getString("nationality"));
                    lblsrc.setText(rs.getString("src"));
                    lbldest.setText(rs.getString("des"));
                    labelfname.setText(rs.getString("flightname"));
                    labelfcode.setText(rs.getString("flightcode"));
                    labeldate.setText(rs.getString("ddate"));
                } else {
                    JOptionPane.showMessageDialog(null, "Please enter correct PNR");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (ae.getSource() == printButton) {
            printWindowAsPDF();
        }
    }

private void printWindowAsPDF() {
    try {
        // Check if PNR is empty
        String pnr = tfpnr.getText().trim();
        if (pnr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "PNR cannot be empty. Please enter a valid PNR.");
            return;
        }

        // Capture the window as an image
        BufferedImage image = new Robot().createScreenCapture(this.getBounds());

        // Generate a unique filename using the PNR number
        String fileName = "BoardingPass_" + pnr + ".pdf"; // Example: BoardingPass_123456.pdf

        // Print the file path for debugging
        System.out.println("Saving PDF to: " + new java.io.File(fileName).getAbsolutePath());

        // Create a PDF document
        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream(fileName));
        document.open();

        // Convert the image to an iText Image
        Image pdfImage = Image.getInstance(image, null);

        // Scale the image to fit the PDF page
        pdfImage.scaleToFit(document.getPageSize().getWidth(), document.getPageSize().getHeight());

        // Add the image to the PDF
        document.add(pdfImage);

        document.close();

        JOptionPane.showMessageDialog(null, "Boarding pass saved as " + fileName);
    } catch (Exception e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(null, "Error generating PDF: " + e.getMessage());
    }
}

    public static void main(String[] args) {
        new BoardingPass();
    }
}
