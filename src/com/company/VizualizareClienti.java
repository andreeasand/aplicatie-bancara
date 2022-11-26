package com.company;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class VizualizareClienti extends JFrame {
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
    public VizualizareClienti(int id, Connection connection) {
        //setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(450, 190, 500, 400);
        setResizable(false);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);
        int x=100;
        try (Statement stmt = connection.createStatement()) {
            ResultSet rs2 = stmt.executeQuery("Select * from cont ");
            while (rs2.next()) {
                    int id_utilizator = rs2.getInt("id_utilizator");
                    JTextField iban = new JTextField("IBAN:  " + rs2.getString("iban"));
                    try (Statement stmt2 = connection.createStatement()) {
                        ResultSet rs3 = stmt2.executeQuery("Select * from utilizator");
                        while (rs3.next()) {
                            if(rs3.getInt("id") == id_utilizator) {
                                System.out.println("*");
                                JTextField nume = new JTextField("Nume:  " + rs3.getString("nume"));
                                JTextField prenume = new JTextField("Prenume:  " + rs3.getString("prenume"));
                                nume.setBounds(200, x, 100, 20);
                                prenume.setBounds(300, x, 100, 20);
                                iban.setBounds(50, x, 150, 20);
                                contentPane.add(nume);
                                contentPane.add(prenume);
                                contentPane.add(iban);
                                iban.setEditable(false);
                                nume.setEditable(false);
                                prenume.setEditable(false);
                                x = x+20;

                            }
                        }
                    } catch (SQLException sqlException) {
                        sqlException.printStackTrace();
                    }
            }
        } catch (SQLException e2) {
            e2.printStackTrace();
        }
    }
}
