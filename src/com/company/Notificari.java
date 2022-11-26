package com.company;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Notificari extends JFrame {
    private JPanel contentPane;
    private Connection connection;

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

    public Notificari(int id, Connection connection) {
        //setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(350, 190, 800, 400);
        setResizable(false);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);
        int x = 100;
        try (Statement stmt = connection.createStatement()) {
            ResultSet rs2 = stmt.executeQuery("Select * from cereri_angajat ");
            int x2=20;
            while (rs2.next()) {
                System.out.println(x2);
                JButton yes = new JButton("yes");
                JButton no = new JButton("no");
                JTextField denumire = new JTextField( rs2.getString("denumire_cerere"));
                String denumire_tranzactie = rs2.getString("denumire_cerere");
                int id_pers = rs2.getInt("id_persoana");
                JTextField id_persoana = new JTextField(String.valueOf(id_pers));
                int id_transfer = rs2.getInt("id_transfer");
                JTextField iban = new JTextField(rs2.getString("iban"));
                denumire.setBounds(50,x2,200,20);
                id_persoana.setBounds(30,x2,20,20);
                iban.setBounds(250,x2,200,20);
                yes.setBounds(450,x2,100,20);
                no.setBounds(550,x2,100,20);
                contentPane.add(denumire);
                contentPane.add(iban);
                contentPane.add(id_persoana);
                contentPane.add(yes);
                contentPane.add(no);
                denumire.setVisible(true);
                id_persoana.setVisible(true);
                iban.setVisible(true);
                yes.setVisible(true);
                no.setVisible(true);
                denumire.setEditable(false);
                id_persoana.setEditable(false);
                iban.setEditable(false);
                x2 = x2 + 20;
                yes.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (denumire_tranzactie.equals("INITIERE_TRANSFER"))
                            try {
                                PreparedStatement st = (PreparedStatement) connection
                                        .prepareStatement("Call aprobare_transfer(?,1);");

                                st.setInt(1, id_transfer);
                                ResultSet rs = st.executeQuery();
                                yes.setVisible(false);
                                no.setVisible(false);
                            } catch (SQLException ex) {
                                ex.printStackTrace();
                            }
                    }
                });
                no.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (denumire_tranzactie.equals("INITIERE_TRANSFER"))
                            try {
                                PreparedStatement st = (PreparedStatement) connection
                                        .prepareStatement("Call aprobare_transfer(?,0);");

                                st.setInt(1, id_transfer);
                                ResultSet rs = st.executeQuery();
                                yes.setVisible(false);
                                no.setVisible(false);
                            } catch (SQLException ex) {
                                ex.printStackTrace();
                            }
                    }
                });
            }
        } catch (SQLException e2) {
            e2.printStackTrace();
        }

    }
}
