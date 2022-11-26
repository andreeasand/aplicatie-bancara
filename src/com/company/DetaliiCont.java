package com.company;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DetaliiCont extends JFrame {
    private JPanel contentPane;
    JTextField contNou = new JTextField("S-a creat noul cont cu datele:");
    JTextField depozitNou = new JTextField("S-a creat noul depozit cu datele:");
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
    public DetaliiCont(){

    }
    public DetaliiCont(int id,Connection connection)
    {
        //setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(450, 190, 500, 350);
        setResizable(false);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);
        try(Statement stmt = connection.createStatement()) {
            ResultSet rs2 = stmt.executeQuery("Select * from Utilizator ");
            while(rs2.next()) {
                if(rs2.getInt("id") == id)
                {

                    JTextField iban = new JTextField("IBAN:  " + rs2.getString("iban"));
                    JTextField nume = new JTextField("Nume  " + rs2.getString("nume"));
                    JTextField prenume = new JTextField("Prenume  " + rs2.getString("prenume"));
                    JTextField adresa = new JTextField("Adresa " + rs2.getString("adresa"));
                    JTextField telefon = new JTextField("Telefon " + rs2.getString("telefon"));
                    JTextField contract = new JTextField("Contract " + rs2.getString("nr_contract"));
                    iban.setBounds(100,100,300,20);
                    nume.setBounds(100,120,300,20);
                    prenume.setBounds(100,140,300,20);
                    adresa.setBounds(100,160,300,20);
                    telefon.setBounds(100,180,300,20);
                    contract.setBounds(100,200,300,20);
                    contentPane.add(nume);
                    contentPane.add(prenume);
                    contentPane.add(iban);
                    contentPane.add(adresa);
                    contentPane.add(telefon);
                    contentPane.add(contract);
                    contentPane.add(contNou);
                    contentPane.add(depozitNou);
                    contNou.setBounds(100,70,300,20);
                    depozitNou.setBounds(100,70,300,20);
                    contNou.setVisible(false);
                    depozitNou.setVisible(false);
                    contNou.setEditable(false);
                    depozitNou.setEditable(false);
                    iban.setEditable(false);
                    nume.setEditable(false);
                    prenume.setEditable(false);
                    adresa.setEditable(false);
                    telefon.setEditable(false);
                    contract.setEditable(false);

                }
            }
        } catch (SQLException e2) {
            e2.printStackTrace();
        }

    }
}
