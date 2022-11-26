package com.company;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class PlataFactura extends JFrame {
    private JPanel contentPane;
    private Connection connection;
    private JButton btnNewButton;
    private JTextField codFactura = new JTextField(7);
    private JTextField iban = new JTextField(7);
    private JButton genereaza = new JButton("Plateste factura");

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    DetaliiCont frame = new DetaliiCont();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public PlataFactura(int id, Connection connection) {
        setBounds(450, 190, 500, 350);
        setResizable(false);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);
        contentPane.add(iban);
        contentPane.add(codFactura);
        contentPane.add(genereaza);
        JTextField mesaj = new JTextField("Introduceti codul facturii");
        mesaj.setBounds(100, 20, 250, 20);
        mesaj.setEditable(false);
        contentPane.add(mesaj);
        codFactura.setBounds(100, 40, 150, 20);

        JTextField mesaj1 = new JTextField("Introduceti IBAN-ul");
        mesaj1.setBounds(100, 70, 250, 20);
        mesaj1.setEditable(false);
        contentPane.add(mesaj1);
        iban.setBounds(100, 90, 150, 20);

        genereaza.setBounds(100, 200, 200, 50);

        genereaza.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String finalUserInput = "";
                int finalUserInput1 = 0;
                try {
                    String userInput = iban.getText();
                    finalUserInput = userInput;

                    String userInput1 = codFactura.getText();
                    finalUserInput1 = Integer.parseInt(userInput1);

                } catch (NumberFormatException nfex) {
                    nfex.printStackTrace();
                }
                try {
                    System.out.println(finalUserInput);
                    PreparedStatement st = (PreparedStatement) connection
                            .prepareStatement("Call plata_facturi(?,?);");


                    st.setInt(1, finalUserInput1);
                    st.setString(2, finalUserInput);
                    ResultSet rs = st.executeQuery();
                    System.out.println(finalUserInput1);
                    try (Statement stmt = connection.createStatement()) {
                        ResultSet rs2 = stmt.executeQuery("Select * from factura ");
                        while (rs2.next()) {
                            if (rs2.getInt("cod") == finalUserInput1) {
                                if (rs2.getBoolean("platita") == true)
                                    JOptionPane.showMessageDialog(btnNewButton, "Cererea a fost inregistrata. Factura a fost platita");
                                else
                                    JOptionPane.showMessageDialog(btnNewButton, "Cererea a fost inregistrata. Sold insuficient");
                            }
                        }
                    } catch (SQLException sqlException) {
                        sqlException.printStackTrace();
                    }
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        });
    }
}
