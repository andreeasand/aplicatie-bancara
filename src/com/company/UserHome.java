package com.company;


import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class UserHome extends JFrame {

    private static final long serialVersionUID = 1;
    private JPanel contentPane;
    private Connection connection;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    UserHome frame = new UserHome();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public UserHome() {
    }

    /**
     * Create the frame.
     */
    public UserHome(String userSes,Connection connection) {

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(250, 100, 1014, 700);
        setResizable(false);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);
        JButton btnNewButton = new JButton("Logout");
        btnNewButton.setForeground(new Color(0, 0, 0));
        btnNewButton.setBackground(UIManager.getColor("Button.disabledForeground"));
        btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 39));
        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int a = JOptionPane.showConfirmDialog(btnNewButton, "Are you sure?");
                // JOptionPane.setRootFrame(null);
                if (a == JOptionPane.YES_OPTION) {
                    dispose();
                    UserLogin obj = new UserLogin();
                    obj.setTitle("Student-Login");
                    obj.setVisible(true);
                }
                /*dispose();
                UserLogin obj = new UserLogin();

                obj.setTitle("Student-Login");
                obj.setVisible(true);*/

            }
        });
        btnNewButton.setBounds(247, 118, 491, 114);
        contentPane.add(btnNewButton);
        JButton button = new JButton("Change-password\r\n");
        button.setBackground(UIManager.getColor("Button.disabledForeground"));
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ChangePassword bo = new ChangePassword(userSes);
                bo.setTitle("Change Password");
                bo.setVisible(true);

            }
        });
        button.setFont(new Font("Tahoma", Font.PLAIN, 35));
        button.setBounds(247, 320, 491, 114);
        contentPane.add(button);

        int tip = 0;
        String prenume = null;
        try(Statement stmt = connection.createStatement()) {
            ResultSet rs2 = stmt.executeQuery("Select * from Utilizator ");
            while(rs2.next()) {
                if(rs2.getString("nume").equals(userSes))
                    tip = rs2.getInt("tip");
            }
        } catch (SQLException e2) {
            e2.printStackTrace();
        }

        if(tip == 0) {
            JComboBox meniuAdmin = new JComboBox(new String[]{"Detalii cont curent","Modificare utilizator","Vizualizare utilizator","Notificari"});
            ;
            meniuAdmin.setBackground(UIManager.getColor("Button.disabledForeground"));
            meniuAdmin.setFont(new Font("Tahoma", Font.PLAIN, 35));
            meniuAdmin.setBounds(247, 522, 491, 114);
            contentPane.add(meniuAdmin);
        }
        if(tip == 1) {
            JComboBox meniuAngajat = new JComboBox(new String[]{"Detalii contract de munca","Vizualizare clienti","Notificari"});
            meniuAngajat.setBackground(UIManager.getColor("Button.disabledForeground"));
            meniuAngajat.setFont(new Font("Tahoma", Font.PLAIN, 35));
            meniuAngajat.setBounds(247, 522, 491, 114);
            contentPane.add(meniuAngajat);
            JButton next = new JButton("Next");
            next.setBounds(820,522,100,100);
            contentPane.add(next);
            next.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    int id = 0;
                    String iban = "";
                    try (Statement stmt = connection.createStatement()) {
                        ResultSet rs2 = stmt.executeQuery("Select * from Utilizator ");
                        while (rs2.next()) {
                            if (rs2.getString("nume").equals(userSes)) {
                                id = rs2.getInt("id");
                            }
                        }
                    } catch (SQLException e2) {
                        e2.printStackTrace();
                    }
                    if(meniuAngajat.getItemAt(meniuAngajat.getSelectedIndex()).equals("Detalii contract de munca")){
                        DetaliiContractAngajat detalii = new DetaliiContractAngajat(id,connection);
                        detalii.setVisible(true);
                        detalii.setTitle("Detalii Contract Angajat");
                    }
                    if(meniuAngajat.getItemAt(meniuAngajat.getSelectedIndex()).equals("Vizualizare clienti")){
                        VizualizareClienti view = new VizualizareClienti(id,connection);
                        view.setVisible(true);
                        view.setTitle("Vizualizare Clienti");
                    }
                    if(meniuAngajat.getItemAt(meniuAngajat.getSelectedIndex()).equals("Notificari")){
                        Notificari notif = new Notificari(id,connection);
                        notif.setVisible(true);
                        notif.setTitle("Notificari");
                    }
                }
            });
        }
        if(tip == 2) {
            JComboBox meniuClient = new JComboBox(new String[]{"Detalii cont curent","Deschidere cont","Deschidere depozit","Lichidare cont","Lichidare depozit",
            "Cerere card","Plata factura","Initiere transfer","Transfer favorit"});
            meniuClient.setBackground(UIManager.getColor("Button.disabledForeground"));
            meniuClient.setFont(new Font("Tahoma", Font.PLAIN, 35));
            meniuClient.setBounds(247, 522, 491, 114);
            contentPane.add(meniuClient);
            JButton next = new JButton("Next");
            next.setBounds(820,522,100,100);
            contentPane.add(next);
            next.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    int id = 0;
                    int tip = 0;
                    String iban = "";
                    try(Statement stmt = connection.createStatement()) {
                        ResultSet rs2 = stmt.executeQuery("Select * from Utilizator ");
                        while(rs2.next()) {
                            if(rs2.getString("nume").equals(userSes)) {
                                tip = rs2.getInt("tip");
                                id = rs2.getInt("id");
                            }
                        }
                    } catch (SQLException e2) {
                        e2.printStackTrace();
                    }
                    if(meniuClient.getItemAt(meniuClient.getSelectedIndex()).equals("Detalii cont curent")) {
                        DetaliiCont detalii = new DetaliiCont(id,connection);
                        detalii.setVisible(true);
                        detalii.setTitle("Detalii");
                    }
                    if(meniuClient.getItemAt(meniuClient.getSelectedIndex()).equals("Deschidere cont")){
                        int random = (int) ((Math.random() * (30000000 - 20000000)) + 20000000);
                        iban = "ROBTRL" + random;
                        DeschidereCont cont = new DeschidereCont(connection,id,tip,iban);
                        cont.setVisible(true);
                        cont.setTitle("Generate account");
                    }
                    if(meniuClient.getItemAt(meniuClient.getSelectedIndex()).equals("Deschidere depozit")){
                        DeschidereDepozit depozit = new DeschidereDepozit(connection,id,tip);
                        depozit.setVisible(true);
                        depozit.setTitle("Generate deposit");
                    }
                    if(meniuClient.getItemAt(meniuClient.getSelectedIndex()).equals("Lichidare cont")) {
                        try(Statement stmt = connection.createStatement()) {
                            ResultSet rs2 = stmt.executeQuery("Select * from Utilizator ");
                            while(rs2.next()) {
                                if(rs2.getInt("id") == id)
                                {
                                    iban = rs2.getString("iban");
                                }
                            }
                        } catch (SQLException e2) {
                            e2.printStackTrace();
                        }
                        System.out.println(iban);
                        try {
                            PreparedStatement st = (PreparedStatement) connection
                                    .prepareStatement("Call lichidare_cont(?);");

                            st.setString(1, iban);
                            ResultSet rs = st.executeQuery();
                        } catch (SQLException throwables) {
                            throwables.printStackTrace();
                        }
                        JOptionPane.showMessageDialog(btnNewButton, "Contul dumneavoastra cu IBAN-ul " + iban + " alaturi de toate activitatile sale(" +
                                "tranzactii, depozite, transferuri) a fost lichidat");
                    }
                    if(meniuClient.getItemAt(meniuClient.getSelectedIndex()).equals("Lichidare depozit")){
                        int suma = 0;
                        try(Statement stmt = connection.createStatement()) {
                            ResultSet rs2 = stmt.executeQuery("Select * from depozit ");
                            while(rs2.next()) {
                                if(rs2.getInt("id") == id)
                                {
                                    suma = rs2.getInt("suma_depozit");
                                }
                            }
                        } catch (SQLException e2) {
                            e2.printStackTrace();
                        }
                        System.out.println(suma);
                        if(suma > 500000)
                            JOptionPane.showMessageDialog(btnNewButton, "Suma e mai mare de 500000. Se asteapta acceptul unui admin");
                            else
                            JOptionPane.showMessageDialog(btnNewButton, "Depozitul dumneavoastra din contul cu IBAN-ul " + iban + " a fost lichidat");
                    }
                    //ROBTRL25818576
                    if(meniuClient.getItemAt(meniuClient.getSelectedIndex()).equals("Cerere card")){
                        CerereCard card = new CerereCard(id, connection);
                        card.setVisible(true);
                        card.setTitle("Cerere card");
                    }
                    if(meniuClient.getItemAt(meniuClient.getSelectedIndex()).equals("Plata factura")){
                        PlataFactura plata = new PlataFactura(id, connection);
                        plata.setVisible(true);
                        plata.setTitle("Plata factura");
                    }
                    if(meniuClient.getItemAt(meniuClient.getSelectedIndex()).equals("Initiere transfer")){
                        try(Statement stmt = connection.createStatement()) {
                            ResultSet rs2 = stmt.executeQuery("Select * from utilizator ");
                            while(rs2.next()) {
                                if(rs2.getInt("id") == id)
                                {
                                    iban = rs2.getString("iban");
                                }
                            }
                        } catch (SQLException e2) {
                            e2.printStackTrace();
                        }
                        InitiereTransfer transfer = new InitiereTransfer(connection,id,iban);
                        transfer.setVisible(true);
                        transfer.setTitle("Initiere transfer");
                    }
                    if(meniuClient.getItemAt(meniuClient.getSelectedIndex()).equals("Transfer favorit")){
                        TransferFavorit favorit = new TransferFavorit(connection, id);
                        favorit.setVisible(true);
                        favorit.setTitle("Transfer favorit");
                    }
                }
                });

        }
    }
}