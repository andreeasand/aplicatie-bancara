package com.company;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DetaliiContractAngajat extends JFrame {
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
    public DetaliiContractAngajat(){

    }
    public DetaliiContractAngajat(int id, Connection connection)
    {
        //setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(450, 190, 500, 400);
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
                    JTextField nume = new JTextField("Nume:  " + rs2.getString("nume"));
                    JTextField prenume = new JTextField("Prenume:  " + rs2.getString("prenume"));
                    JTextField adresa = new JTextField("Adresa: " + rs2.getString("adresa"));
                    JTextField telefon = new JTextField("Telefon: " + rs2.getString("telefon"));
                    JTextField contract = new JTextField("Contract: " + rs2.getString("nr_contract"));
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
        try(Statement stmt = connection.createStatement()) {
            ResultSet rs2 = stmt.executeQuery("Select * from Angajat ");
            while (rs2.next()) {
                if (rs2.getInt("id_angajat") == id) {
                    JTextField norma = new JTextField("Norma: " + rs2.getInt("norma"));
                    JTextField salariu = new JTextField("Salariu: " + rs2.getInt("salariu"));
                    JTextField sucursala = new JTextField("Sucursala: " + rs2.getString("sucursala"));
                    JTextField departament = new JTextField("Departament: " + rs2.getString("departament"));
                    norma.setBounds(100,220,300,20);
                    salariu.setBounds(100,240,300,20);
                    sucursala.setBounds(100,260,300,20);
                    departament.setBounds(100,280,300,20);
                    contentPane.add(norma);
                    contentPane.add(salariu);
                    contentPane.add(sucursala);
                    contentPane.add(departament);
                    norma.setEditable(false);
                    salariu.setEditable(false);
                    sucursala.setEditable(false);
                    departament.setEditable(false);

                }
            }
        }
        catch (SQLException e2) {
            e2.printStackTrace();
        }


    }
}
