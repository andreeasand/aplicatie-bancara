package com.company;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class TransferFavorit extends JFrame {
    private JPanel contentPane;
    private Connection connection;
    private JButton btnNewButton;
    private JTextField suma = new JTextField(7);
    private JTextField receptor = new JTextField(7);
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
    public TransferFavorit(Connection connection,int id){
        setBounds(450, 190, 500, 350);
        setResizable(false);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);
        contentPane.add(receptor);
        contentPane.add(suma);
        contentPane.add(genereaza);
        JTextField mesaj = new JTextField("Introduceti suma");
        mesaj.setBounds(100, 20, 250, 20);
        mesaj.setEditable(false);
        contentPane.add(mesaj);
        suma.setBounds(100, 40, 150, 20);

        JTextField mesaj1 = new JTextField("Introduceti numele receptorului");
        mesaj1.setBounds(100, 70, 250, 20);
        mesaj1.setEditable(false);
        contentPane.add(mesaj1);
        receptor.setBounds(100, 90, 150, 20);

        genereaza.setBounds(100, 200, 200, 50);

        genereaza.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String finalUserInput = "";
                int finalUserInput1 = 0;
                try {
                    String userInput = receptor.getText();
                    finalUserInput = userInput;

                    String userInput1 = suma.getText();
                    finalUserInput1 = Integer.parseInt(userInput1);

                } catch (NumberFormatException nfex) {
                    nfex.printStackTrace();
                }
                try {
                    PreparedStatement st = (PreparedStatement) connection
                            .prepareStatement("Call transfer_favorit(?,?,?);");

                    st.setInt(1,id);
                    st.setString(2,finalUserInput);
                    st.setInt(3, finalUserInput1);
                    ResultSet rs = st.executeQuery();
                    String iban_receptor = "";
                    int gasit = 0;
                    try (Statement stmt = connection.createStatement()) {
                        ResultSet rs2 = stmt.executeQuery("Select * from utilizator ");
                        while (rs2.next()) {
                            if (rs2.getString("nume").equals(finalUserInput)) {
                                iban_receptor = rs2.getString("iban");
                            }
                        }
                    } catch (SQLException sqlException) {
                        sqlException.printStackTrace();
                    }
                    try (Statement stmt = connection.createStatement()) {
                        ResultSet rs2 = stmt.executeQuery("Select * from conturi_favorite ");
                        while (rs2.next()) {
                            if (rs2.getInt("id_persoana") == id && rs2.getString("iban_favorit").equals(iban_receptor)) {
                                gasit = 1;
                            }
                        }
                    } catch (SQLException sqlException) {
                        sqlException.printStackTrace();
                    }
                    if(gasit == 0)
                        JOptionPane.showMessageDialog(btnNewButton, "EROARE - Numele nu se afla intre favorite");
                    else JOptionPane.showMessageDialog(btnNewButton, "Cererea a fost inregistrata. Se asteapta aprobare de la angajat");
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        });
    }
}
