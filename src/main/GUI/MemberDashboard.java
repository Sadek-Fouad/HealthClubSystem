package main.GUI;

import managers.SystemManager;
import models.Coach;
import models.Member;

import javax.swing.*;
import java.awt.*;

public class MemberDashboard extends JFrame {

    private SystemManager system;
    private Member member;
    private JTextArea outputArea;

    public MemberDashboard(SystemManager system, Member member) {
        this.system = system;
        this.member = member;

        setTitle("Member Dashboard - " + member.getName());
        setSize(650, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        JLabel title = new JLabel("Member Dashboard", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 24));

        JPanel buttonPanel = new JPanel(new GridLayout(4, 1, 10, 10));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JButton viewDashboardBtn = new JButton("View My Dashboard");
        JButton viewCoachScheduleBtn = new JButton("View Coach & Schedule");
        JButton updateInfoBtn = new JButton("Update My Info");
        JButton logoutBtn = new JButton("Logout");

        buttonPanel.add(viewDashboardBtn);
        buttonPanel.add(viewCoachScheduleBtn);
        buttonPanel.add(updateInfoBtn);
        buttonPanel.add(logoutBtn);

        outputArea = new JTextArea();
        outputArea.setEditable(false);

        add(title, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.WEST);
        add(new JScrollPane(outputArea), BorderLayout.CENTER);

        viewDashboardBtn.addActionListener(e -> viewDashboard());
        viewCoachScheduleBtn.addActionListener(e -> viewCoachAndSchedule());
        updateInfoBtn.addActionListener(e -> updateInfo());
        logoutBtn.addActionListener(e -> logout());

        setVisible(true);
    }

    private void viewDashboard() {
        StringBuilder sb = new StringBuilder();

        sb.append("===== Member Dashboard =====\n");
        sb.append("Name: ").append(member.getName()).append("\n");
        sb.append("Username: ").append(member.getUsername()).append("\n");
        sb.append("Coach: ").append(member.getCoachName()).append("\n");
        sb.append("Plan: ").append(member.getPlan()).append("\n");
        sb.append("Subscription End Date: ").append(member.getSubscriptionEndDate()).append("\n");
        sb.append("Days Left: ").append(member.daysUntilExpiry()).append("\n");

        if (member.daysUntilExpiry() <= 0) {
            sb.append("\nALERT: Subscription has expired!\n");
        } else if (member.daysUntilExpiry() <= 7) {
            sb.append("\nWARNING: Subscription expiring soon!\n");
        }

        outputArea.setText(sb.toString());
    }

    private void viewCoachAndSchedule() {
        StringBuilder sb = new StringBuilder();

        sb.append("===== Coach & Schedule =====\n");
        sb.append("Coach: ").append(member.getCoachName()).append("\n");
        sb.append("Plan: ").append(member.getPlan()).append("\n\n");

        Coach coach = system.getCoachByName(member.getCoachName());

        if (coach == null) {
            sb.append("No coach assigned yet.");
        } else {
            sb.append("Coach Schedule:\n");

            if (coach.getSchedule().isEmpty()) {
                sb.append("No schedule set yet.");
            } else {
                for (String entry : coach.getSchedule()) {
                    sb.append("- ").append(entry).append("\n");
                }
            }
        }

        outputArea.setText(sb.toString());
    }

    private void updateInfo() {
        String newName = JOptionPane.showInputDialog(this, "New name:", member.getName());
        String newUsername = JOptionPane.showInputDialog(this, "New username:", member.getUsername());

        if (newName != null && !newName.trim().isEmpty()) {
            member.setName(newName.trim());
        }

        if (newUsername != null && !newUsername.trim().isEmpty()) {
            member.setUsername(newUsername.trim());
        }

        system.saveAll();

        outputArea.setText("Info updated successfully.");
        setTitle("Member Dashboard - " + member.getName());
    }

    private void logout() {
        dispose();
        new LoginFrame();
    }
}