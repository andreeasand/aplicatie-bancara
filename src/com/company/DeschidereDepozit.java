package com.company;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class DeschidereDepozit extends JFrame {
    private JPanel contentPane;
    int id; int tip;
    private Connection connection;
    private JButton btnNewButton;
    private JTextField suma = new JTextField(7);
    private JTextField perioada = new JTextField(7);
    private JTextField iban = new JTextField(7);
    private JButton genereaza = new JButton("Generate deposit");

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
    public DeschidereDepozit(){}

    public DeschidereDepozit(Connection connection, int id, int tip){
        this.id =id;
        this.tip = tip;
        setBounds(450, 190, 500, 350);
        setResizable(false);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);
        contentPane.add(suma);
        contentPane.add(perioada);
        contentPane.add(iban);
        contentPane.add(genereaza);
        JTextField mesaj1 = new JTextField("Introduceti suma");
        mesaj1.setBounds(100,20,250,20);
        mesaj1.setEditable(false);
        contentPane.add(mesaj1);
        JTextField mesaj2 = new JTextField("Introduceti perioada");
        mesaj2.setBounds(100,70,250,20);
        mesaj2.setEditable(false);
        contentPane.add(mesaj2);
        JTextField mesaj3 = new JTextField("Introduceti IBAN-ul");
        mesaj3.setBounds(100,120,250,20);
        mesaj3.setEditable(false);
        contentPane.add(mesaj3);
        suma.setBounds(100,40,150,20);
        perioada.setBounds(100,90,150,20);
        iban.setBounds(100,140,150,20);

        genereaza.setBounds(100,200,200,50);

        genereaza.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int finalUserInput = 0;
                int finalUserInput2 = 0;
                String finalUserInput3 = "";
                try {
                    String userInput = suma.getText();
                    finalUserInput = Integer.parseInt(userInput);

                    String userInput2 = perioada.getText();
                    finalUserInput2 = Integer.parseInt(userInput2);

                    String userInput3 = iban.getText();
                    finalUserInput3 = userInput3;


                } catch (NumberFormatException nfex) {
                    nfex.printStackTrace();
                }
                try {
                    System.out.println(finalUserInput);
                    PreparedStatement st = (PreparedStatement) connection
                            .prepareStatement("Call deschidere_depozit(?,?,?,0);");

                    st.setString(1, finalUserInput3);
                    st.setInt(2, finalUserInput2);
                    st.setInt(3, finalUserInput);
                    ResultSet rs = st.executeQuery();
                    if(finalUserInput > 500000) {
                        JOptionPane.showMessageDialog(btnNewButton, "Suma e mai mare de 500000. Se asteapta acceptul unui angajat");
                        //se va insera automat si o tupla in cereri_angajat si aia o sa i apara lui la notificari
                    }
                    DetaliiCont depozit = new DetaliiCont(id,connection);
                    depozit.setVisible(true);
                    dispose();
                    setVisible(false);
                    depozit.depozitNou.setVisible(true);

                    depozit.setTitle("Generate deposit");

                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        });
    }
}
