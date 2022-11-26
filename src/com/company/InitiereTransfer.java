package com.company;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class InitiereTransfer extends JFrame {
    private JPanel contentPane;
    private Connection connection;
    private JButton btnNewButton;
    private JTextField suma = new JTextField(7);
    private JTextField iban_receptor = new JTextField(7);
    private JButton genereaza = new JButton("Initieaza transfer");

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
    public InitiereTransfer(Connection connection, int id, String iban){
        setBounds(450, 190, 500, 350);
        setResizable(false);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);
        contentPane.add(iban_receptor);
        contentPane.add(suma);
        contentPane.add(genereaza);
        JTextField mesaj = new JTextField("Introduceti suma");
        mesaj.setBounds(100, 20, 250, 20);
        mesaj.setEditable(false);
        contentPane.add(mesaj);
        suma.setBounds(100, 40, 150, 20);

        JTextField mesaj1 = new JTextField("Introduceti IBAN-ul receptorului");
        mesaj1.setBounds(100, 70, 250, 20);
        mesaj1.setEditable(false);
        contentPane.add(mesaj1);
        iban_receptor.setBounds(100, 90, 150, 20);

        genereaza.setBounds(100, 200, 200, 50);

        genereaza.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String finalUserInput = "";
                int finalUserInput1 = 0;
                try {
                    String userInput = iban_receptor.getText();
                    finalUserInput = userInput;

                    String userInput1 = suma.getText();
                    finalUserInput1 = Integer.parseInt(userInput1);

                } catch (NumberFormatException nfex) {
                    nfex.printStackTrace();
                }
                try {
                    System.out.println(iban);
                    PreparedStatement st = (PreparedStatement) connection
                            .prepareStatement("Call initiere_transfer(?,?,?,?);");

                    st.setInt(1,id);
                    st.setString(2,iban);
                    st.setString(3, finalUserInput);
                    st.setInt(4, finalUserInput1);
                    ResultSet rs = st.executeQuery();
                    System.out.println(finalUserInput1);
                    //ROBTRL22073215
                    JOptionPane.showMessageDialog(btnNewButton, "Cererea a fost inregistrata. Se asteapta aprobare de la angajat");
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        });
    }
}
