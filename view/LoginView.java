package view;

import javax.swing.*;
public class LoginView extends JFrame {
    public JTextField userField = new JTextField(1);
    public JPasswordField passField = new JPasswordField(10);
    public JButton loginBtn = new JButton("Login");
    public JLabel msgLabel = new JLabel();

    public LoginView() {
        setTitle("Login");
        setSize(600, 400);
        setLayout(new java.awt.GridLayout(4,4));
        add(new JLabel("Username:")); add(userField);
        add(new JLabel("Password:")); add(passField);
        add(loginBtn); add(msgLabel);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }
}