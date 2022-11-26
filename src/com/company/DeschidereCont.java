package com.company;

import javax.print.attribute.standard.JobName;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class DeschidereCont extends JFrame {
    private JPanel contentPane;
    int id; String iban; int tip;
    private Connection connection;
    private JTextField suma = new JTextField(7);
    private JTextField tipCont = new JTextField(7);
    private JButton genereaza = new JButton("Generate account");

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
    public DeschidereCont(){}

    public DeschidereCont(Connection connection,int id, int tip, String iban){
        this.id = id;
        this.tip = tip;
        this.iban = iban;
        setBounds(450, 190, 500, 350);
        setResizable(false);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);
        contentPane.add(suma);
        contentPane.add(tipCont);
        contentPane.add(genereaza);
        JTextField mesaj = new JTextField("Introduceti suma");
        mesaj.setBounds(100,20,250,20);
        mesaj.setEditable(false);
        JTextField mesaj1 = new JTextField("Introduceti tipul: 0 - economii; 1 - curent");
        mesaj1.setBounds(100,80,250,20);
        mesaj1.setEditable(false);
        contentPane.add(mesaj);
        contentPane.add(mesaj1);
        suma.setBounds(100,50,150,20);
        tipCont.setBounds(100,100,150,20);
        genereaza.setBounds(100,200,200,50);
        //String userInput = "";
        genereaza.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int finalUserInput = 0;
                int finalUserInput2 = 0;
                try {
                    String userInput = suma.getText();
                    finalUserInput = Integer.parseInt(userInput);

                    String userInput2 = tipCont.getText();
                    finalUserInput2 = Integer.parseInt(userInput2);
                } catch (NumberFormatException nfex) {
                    nfex.printStackTrace();
                }
                try {
                    System.out.println(finalUserInput);
                    PreparedStatement st = (PreparedStatement) connection
                            .prepareStatement("Call deschidere_cont(?,?,?,?);");

                    st.setString(1, iban);
                    st.setInt(2, id);
                    st.setInt(3,finalUserInput2);
                    st.setInt(4, finalUserInput);
                    ResultSet rs = st.executeQuery();

                    DetaliiCont cont = new DetaliiCont(id,connection);
                    cont.setVisible(true);
                    dispose();
                    setVisible(false);
                    cont.contNou.setVisible(true);

                    cont.setTitle("Generate account");

                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        });
    }
    String getUserInput() {
        return suma.getText();
    }
}
