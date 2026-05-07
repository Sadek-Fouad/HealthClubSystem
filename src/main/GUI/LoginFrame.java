package main.GUI;

import managers.SystemManager;
import models.Admin;
import models.Coach;
import models.Member;
import models.User;

import javax.swing.*;
import java.awt.*;

public class LoginFrame extends JFrame {

    private SystemManager system;
    private JTextField usernameField;
    private JPasswordField passwordField;

    public LoginFrame() {
        system = new SystemManager();

        setTitle("Health Club Management System - Login");
        setSize(400, 250);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(4, 1, 10, 10));

        JLabel title = new JLabel("Health Club Login", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 22));

        usernameField = new JTextField();
        passwordField = new JPasswordField();

        JButton loginButton = new JButton("Login");

        add(title);
        add(createInputPanel("Username:", usernameField));
        add(createInputPanel("Password:", passwordField));
        add(loginButton);

        loginButton.addActionListener(e -> login());

        setVisible(true);
    }

    private JPanel createInputPanel(String labelText, JComponent field) {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(5, 30, 5, 30));
        panel.add(new JLabel(labelText), BorderLayout.WEST);
        panel.add(field, BorderLayout.CENTER);
        return panel;
    }

    private void login() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword()).trim();

        User user = system.login(username, password);

        if (user == null) {
            JOptionPane.showMessageDialog(this, "Invalid username or password");
            return;
        }

        JOptionPane.showMessageDialog(this, "Login successful as " + user.getRole());

        dispose();

        if (user instanceof Admin) {
            new AdminDashboard(system, (Admin) user);
        } else if (user instanceof Coach) {
            new CoachDashboard(system, (Coach) user);
        } else if (user instanceof Member) {
            new MemberDashboard(system, (Member) user);
        }
    }
}