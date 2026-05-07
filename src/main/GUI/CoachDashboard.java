package main.GUI;

import managers.SystemManager;
import models.Coach;
import models.Member;

import javax.swing.*;
import java.awt.*;

public class CoachDashboard extends JFrame {

    private SystemManager system;
    private Coach coach;
    private JTextArea outputArea;

    public CoachDashboard(SystemManager system, Coach coach) {
        this.system = system;
        this.coach = coach;

        setTitle("Coach Dashboard - " + coach.getName());
        setSize(650, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        JLabel title = new JLabel("Coach Dashboard", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 24));

        JPanel buttonPanel = new JPanel(new GridLayout(7, 1, 10, 10));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JButton viewMembersBtn = new JButton("View My Members");
        JButton setPlanBtn = new JButton("Set Plan for Member");
        JButton addScheduleBtn = new JButton("Add Schedule Entry");
        JButton viewScheduleBtn = new JButton("View Schedule");
        JButton sendMessageBtn = new JButton("Send Message");
        JButton updateInfoBtn = new JButton("Update My Info");
        JButton logoutBtn = new JButton("Logout");

        buttonPanel.add(viewMembersBtn);
        buttonPanel.add(setPlanBtn);
        buttonPanel.add(addScheduleBtn);
        buttonPanel.add(viewScheduleBtn);
        buttonPanel.add(sendMessageBtn);
        buttonPanel.add(updateInfoBtn);
        buttonPanel.add(logoutBtn);

        outputArea = new JTextArea();
        outputArea.setEditable(false);

        add(title, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.WEST);
        add(new JScrollPane(outputArea), BorderLayout.CENTER);

        viewMembersBtn.addActionListener(e -> viewMembers());
        setPlanBtn.addActionListener(e -> setPlanForMember());
        addScheduleBtn.addActionListener(e -> addScheduleEntry());
        viewScheduleBtn.addActionListener(e -> viewSchedule());
        sendMessageBtn.addActionListener(e -> sendMessage());
        updateInfoBtn.addActionListener(e -> updateInfo());
        logoutBtn.addActionListener(e -> logout());

        setVisible(true);
    }

    private void viewMembers() {
        StringBuilder sb = new StringBuilder("===== My Members =====\n");

        if (coach.getMemberNames().isEmpty()) {
            sb.append("No members assigned yet.");
        } else {
            for (String memberName : coach.getMemberNames()) {
                sb.append("- ").append(memberName).append("\n");
            }
        }

        outputArea.setText(sb.toString());
    }

    private void setPlanForMember() {
        String memberName = JOptionPane.showInputDialog(this, "Member name:");
        if (memberName == null || memberName.trim().isEmpty()) return;

        Member member = system.searchMemberByName(memberName.trim());

        if (member == null) {
            outputArea.setText("Member not found.");
            return;
        }

        if (!coach.getMemberNames().contains(member.getName())) {
            outputArea.setText("This member is not assigned to you.");
            return;
        }

        String plan = JOptionPane.showInputDialog(this, "Enter plan:");
        if (plan == null || plan.trim().isEmpty()) return;

        coach.setPlan(member, plan.trim());
        system.saveAll();

        outputArea.setText("Plan set successfully for " + member.getName());
    }

    private void addScheduleEntry() {
        String entry = JOptionPane.showInputDialog(this, "Enter schedule entry:");

        if (entry == null || entry.trim().isEmpty()) return;

        coach.addScheduleEntry(entry.trim());
        system.saveAll();

        outputArea.setText("Schedule entry added:\n" + entry);
    }

    private void viewSchedule() {
        StringBuilder sb = new StringBuilder("===== My Schedule =====\n");

        if (coach.getSchedule().isEmpty()) {
            sb.append("No schedule entries yet.");
        } else {
            for (String entry : coach.getSchedule()) {
                sb.append("- ").append(entry).append("\n");
            }
        }

        outputArea.setText(sb.toString());
    }

    private void sendMessage() {
        String message = JOptionPane.showInputDialog(this, "Enter message:");

        if (message == null || message.trim().isEmpty()) return;

        StringBuilder sb = new StringBuilder();
        sb.append("--- Message from Coach ").append(coach.getName()).append(" ---\n");

        if (coach.getMemberNames().isEmpty()) {
            sb.append("No members to message.");
        } else {
            for (String memberName : coach.getMemberNames()) {
                sb.append("-> ").append(memberName).append(": ").append(message).append("\n");
            }
        }

        outputArea.setText(sb.toString());
    }

    private void updateInfo() {
        String newName = JOptionPane.showInputDialog(this, "New name:", coach.getName());
        String newUsername = JOptionPane.showInputDialog(this, "New username:", coach.getUsername());

        if (newName != null && !newName.trim().isEmpty()) {
            coach.setName(newName.trim());
        }

        if (newUsername != null && !newUsername.trim().isEmpty()) {
            coach.setUsername(newUsername.trim());
        }

        system.saveAll();

        outputArea.setText("Info updated successfully.");
        setTitle("Coach Dashboard - " + coach.getName());
    }

    private void logout() {
        dispose();
        new LoginFrame();
    }
}