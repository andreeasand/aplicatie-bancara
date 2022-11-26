package com.company;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class CerereCard extends JFrame {
    private JPanel contentPane;
    private Connection connection;
    private JButton btnNewButton;
    private JTextField iban = new JTextField(7);
    private JButton genereaza = new JButton("Cerere card");

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

    public CerereCard(int id, Connection connection){
        setBounds(450, 190, 500, 350);
        setResizable(false);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);
        contentPane.add(iban);
        contentPane.add(genereaza);
        JTextField mesaj1 = new JTextField("Introduceti IBAN-ul");
        mesaj1.setBounds(100,20,250,20);
        mesaj1.setEditable(false);
        contentPane.add(mesaj1);
        iban.setBounds(100,140,150,20);

        genereaza.setBounds(100,200,200,50);

        genereaza.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String finalUserInput = "";
                try {
                    String userInput = iban.getText();
                    finalUserInput = userInput;
                } catch (NumberFormatException nfex) {
                    nfex.printStackTrace();
                }
                System.out.println(id);
                try {
                    System.out.println(finalUserInput);
                    PreparedStatement st = (PreparedStatement) connection
                            .prepareStatement("Call cerere_card(?,?,0,0);");


                    st.setString(1, finalUserInput);
                    st.setInt(2,id);
                    ResultSet rs = st.executeQuery();
                    JOptionPane.showMessageDialog(btnNewButton, "Cererea a fost inregistrata. Se asteapta aprobare de la administrator si angajat");
                    //dispose();
                   // setVisible(false);


                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        });
    }
}

