package main.GUI;

import managers.ReportGenerator;
import managers.SystemManager;
import models.Admin;
import models.Coach;
import models.Member;

import javax.swing.*;
import java.awt.*;
import java.util.Date;

public class AdminDashboard extends JFrame {

    private SystemManager system;
    private Admin admin;
    private JTextArea outputArea;

    public AdminDashboard(SystemManager system, Admin admin) {
        this.system = system;
        this.admin = admin;

        setTitle("Admin Dashboard - " + admin.getName());
        setSize(900, 550);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        JLabel title = new JLabel("Admin Dashboard", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 24));

        JPanel buttonPanel = new JPanel(new GridLayout(8, 2, 10, 10));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JButton addMemberBtn = new JButton("Add Member");
        JButton deleteMemberBtn = new JButton("Delete Member");
        JButton updateMemberBtn = new JButton("Update Member");
        JButton listMembersBtn = new JButton("List Members");
        JButton searchMemberBtn = new JButton("Search Member");
        JButton addCoachBtn = new JButton("Add Coach");
        JButton deleteCoachBtn = new JButton("Delete Coach");
        JButton listCoachesBtn = new JButton("List Coaches");
        JButton assignCoachBtn = new JButton("Assign Coach");
        JButton addAdminBtn = new JButton("Add Admin");
        JButton deleteAdminBtn = new JButton("Delete Admin");
        JButton listAdminsBtn = new JButton("List Admins");
        JButton billingBtn = new JButton("Manage Billing");
        JButton reportBtn = new JButton("Member Report");
        JButton alertsBtn = new JButton("Check Expiring");
        JButton logoutBtn = new JButton("Logout");

        buttonPanel.add(addMemberBtn);
        buttonPanel.add(deleteMemberBtn);
        buttonPanel.add(updateMemberBtn);
        buttonPanel.add(listMembersBtn);
        buttonPanel.add(searchMemberBtn);
        buttonPanel.add(addCoachBtn);
        buttonPanel.add(deleteCoachBtn);
        buttonPanel.add(listCoachesBtn);
        buttonPanel.add(assignCoachBtn);
        buttonPanel.add(addAdminBtn);
        buttonPanel.add(deleteAdminBtn);
        buttonPanel.add(listAdminsBtn);
        buttonPanel.add(billingBtn);
        buttonPanel.add(reportBtn);
        buttonPanel.add(alertsBtn);
        buttonPanel.add(logoutBtn);

        outputArea = new JTextArea();
        outputArea.setEditable(false);

        add(title, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.WEST);
        add(new JScrollPane(outputArea), BorderLayout.CENTER);

        addMemberBtn.addActionListener(e -> addMember());
        deleteMemberBtn.addActionListener(e -> deleteMember());
        updateMemberBtn.addActionListener(e -> updateMember());
        listMembersBtn.addActionListener(e -> listMembers());
        searchMemberBtn.addActionListener(e -> searchMember());
        addCoachBtn.addActionListener(e -> addCoach());
        deleteCoachBtn.addActionListener(e -> deleteCoach());
        listCoachesBtn.addActionListener(e -> listCoaches());
        assignCoachBtn.addActionListener(e -> assignCoach());
        addAdminBtn.addActionListener(e -> addAdmin());
        deleteAdminBtn.addActionListener(e -> deleteAdmin());
        listAdminsBtn.addActionListener(e -> listAdmins());
        billingBtn.addActionListener(e -> billingMenu());
        reportBtn.addActionListener(e -> showMemberReport());
        alertsBtn.addActionListener(e -> checkExpiring());
        logoutBtn.addActionListener(e -> logout());

        setVisible(true);
    }

    private void addMember() {
        String name = JOptionPane.showInputDialog(this, "Member name:");
        String username = JOptionPane.showInputDialog(this, "Username:");
        String password = JOptionPane.showInputDialog(this, "Password:");
        String daysText = JOptionPane.showInputDialog(this, "Subscription days:");

        if (name == null || username == null || password == null || daysText == null) return;

        int days;

        try {
            days = Integer.parseInt(daysText.trim());

            if (days <= 0) {
                JOptionPane.showMessageDialog(this,
                        "Subscription days must be greater than 0.");
                return;
            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this,
                    "Please enter a valid number for subscription days.");
            return;
        }        Date endDate = new Date(System.currentTimeMillis() + (long) days * 24 * 60 * 60 * 1000);

        Member member = new Member(system.nextMemberId(), name, username, password, endDate);
        system.addMember(member);

        outputArea.setText("Member added successfully: " + name);
    }

    private void deleteMember() {
        String idText = JOptionPane.showInputDialog(this, "Member ID to delete:");
        if (idText == null) return;

        int id = Integer.parseInt(idText);
        system.deleteMember(id);
        outputArea.setText("Delete member operation completed.");
    }

    private void updateMember() {
        String idText = JOptionPane.showInputDialog(this, "Member ID to update:");
        if (idText == null) return;

        int id = Integer.parseInt(idText);
        String newName = JOptionPane.showInputDialog(this, "New name:");
        String newUsername = JOptionPane.showInputDialog(this, "New username:");

        if (newName == null || newUsername == null) return;

        system.updateMember(id, newName, newUsername);
        outputArea.setText("Update member operation completed.");
    }

    private void listMembers() {
        StringBuilder sb = new StringBuilder("===== Members =====\n");

        if (system.getMembers().isEmpty()) {
            sb.append("No members registered.");
        } else {
            for (Member m : system.getMembers()) {
                sb.append(m)
                        .append(" | Coach: ").append(m.getCoachName())
                        .append(" | Expires: ").append(m.getSubscriptionEndDate())
                        .append(" | Days left: ").append(m.daysUntilExpiry())
                        .append("\n");
            }
        }

        outputArea.setText(sb.toString());
    }

    private void searchMember() {
        String name = JOptionPane.showInputDialog(this, "Member name to search:");
        if (name == null) return;

        Member found = system.searchMemberByName(name);

        if (found == null) {
            outputArea.setText("Member not found.");
        } else {
            outputArea.setText("Found: " + found
                    + "\nCoach: " + found.getCoachName()
                    + "\nPlan: " + found.getPlan()
                    + "\nExpires: " + found.getSubscriptionEndDate()
                    + "\nDays left: " + found.daysUntilExpiry());
        }
    }

    private void addCoach() {
        String name = JOptionPane.showInputDialog(this, "Coach name:");
        String username = JOptionPane.showInputDialog(this, "Username:");
        String password = JOptionPane.showInputDialog(this, "Password:");

        if (name == null || username == null || password == null) return;

        Coach coach = new Coach(system.nextCoachId(), name, username, password);
        system.addCoach(coach);

        outputArea.setText("Coach added successfully: " + name);
    }

    private void deleteCoach() {
        String idText = JOptionPane.showInputDialog(this, "Coach ID to delete:");
        if (idText == null) return;

        int id = Integer.parseInt(idText);
        system.deleteCoach(id);
        outputArea.setText("Delete coach operation completed.");
    }

    private void listCoaches() {
        StringBuilder sb = new StringBuilder("===== Coaches =====\n");

        if (system.getCoaches().isEmpty()) {
            sb.append("No coaches registered.");
        } else {
            for (Coach c : system.getCoaches()) {
                sb.append(c)
                        .append(" | Members: ")
                        .append(c.getMemberNames().size())
                        .append("\n");
            }
        }

        outputArea.setText(sb.toString());
    }

    private void assignCoach() {
        String memberIdText = JOptionPane.showInputDialog(this, "Member ID:");
        String coachIdText = JOptionPane.showInputDialog(this, "Coach ID:");

        if (memberIdText == null || coachIdText == null) return;

        int memberId = Integer.parseInt(memberIdText);
        int coachId = Integer.parseInt(coachIdText);

        system.assignCoach(memberId, coachId);
        outputArea.setText("Coach assignment completed.");
    }

    private void addAdmin() {
        String name = JOptionPane.showInputDialog(this, "Admin name:");
        String username = JOptionPane.showInputDialog(this, "Username:");
        String password = JOptionPane.showInputDialog(this, "Password:");

        if (name == null || username == null || password == null) return;

        Admin newAdmin = new Admin(system.nextAdminId(), name, username, password);
        system.addAdmin(newAdmin);

        outputArea.setText("Admin added successfully: " + name);
    }

    private void deleteAdmin() {
        String idText = JOptionPane.showInputDialog(this, "Admin ID to delete:");
        if (idText == null) return;

        int adminId = Integer.parseInt(idText);

        if (admin.getId() == adminId) {
            outputArea.setText("You cannot delete the currently logged-in admin.");
        } else {
            system.deleteAdmin(adminId);
            outputArea.setText("Delete admin operation completed.");
        }
    }

    private void listAdmins() {
        StringBuilder sb = new StringBuilder("===== Admins =====\n");

        for (Admin a : system.getAdmins()) {
            sb.append(a).append("\n");
        }

        outputArea.setText(sb.toString());
    }

    private void billingMenu() {
        String[] options = {"Add Bill", "View Bills"};
        int choice = JOptionPane.showOptionDialog(
                this,
                "Choose billing operation:",
                "Billing Menu",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                options,
                options[0]
        );

        if (choice == 0) {
            String idText = JOptionPane.showInputDialog(this, "Member ID:");
            String amountText = JOptionPane.showInputDialog(this, "Amount:");

            if (idText == null || amountText == null) return;

            int id = Integer.parseInt(idText);
            double amount = Double.parseDouble(amountText);

            ReportGenerator.addBill(id, amount);
            outputArea.setText("Bill added for member ID: " + id + "\nAmount: " + amount);

        } else if (choice == 1) {
            outputArea.setText("Bills are printed in the console by ReportGenerator.listBills().");
            ReportGenerator.listBills();
        }
    }

    private void showMemberReport() {
        StringBuilder sb = new StringBuilder("========== Member Report ==========\n");

        if (system.getMembers().isEmpty()) {
            sb.append("No members available for report.");
        } else {
            for (Member m : system.getMembers()) {
                sb.append("ID: ").append(m.getId())
                        .append(" | Name: ").append(m.getName())
                        .append(" | Coach: ").append(m.getCoachName())
                        .append(" | Expires: ").append(m.getSubscriptionEndDate())
                        .append(" | Days Left: ").append(m.daysUntilExpiry())
                        .append("\n");
            }
        }

        outputArea.setText(sb.toString());
    }

    private void checkExpiring() {
        StringBuilder sb = new StringBuilder("===== Subscription Alerts =====\n");
        boolean any = false;

        for (Member m : system.getMembers()) {
            long days = m.daysUntilExpiry();

            if (days <= 7 && days >= 0) {
                sb.append(m.getName())
                        .append(" expires in ")
                        .append(days)
                        .append(" day(s)\n");
                any = true;
            } else if (days < 0) {
                sb.append(m.getName())
                        .append(" subscription has expired.\n");
                any = true;
            }
        }

        if (!any) {
            sb.append("No expiring subscriptions.");
        }

        outputArea.setText(sb.toString());
    }

    private void logout() {
        dispose();
        new LoginFrame();
    }
}