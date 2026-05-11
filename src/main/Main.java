package main;

import managers.SystemManager;
import managers.ReportGenerator;
import models.Admin;
import models.Coach;
import models.Member;
import models.User;

import java.util.Date;
import java.util.Scanner;

public class Main {

    static Scanner scanner = new Scanner(System.in);
    static SystemManager system = new SystemManager();

    public static void main(String[] args) {

        system.checkExpiringSubscriptions();

        System.out.println("\n================================");
        System.out.println("   HEALTH CLUB MANAGEMENT SYSTEM");
        System.out.println("================================\n");

        User loggedIn = null;

        // ── Login loop ─────────────────────────────────────────
        while (loggedIn == null) {
            System.out.print("Username: ");
            String username = scanner.nextLine().trim();
            System.out.print("Password: ");
            String password = scanner.nextLine().trim();

            loggedIn = system.login(username, password);

            if (loggedIn == null)
                System.out.println("Invalid credentials. Try again.\n");
        }

        System.out.println("\nLogin successful!");
        loggedIn.showDashboard();

        // ── Route to correct menu based on role ────────────────
        switch (loggedIn.getRole()) {
            case "Admin":  adminMenu((Admin) loggedIn);  break;
            case "Coach":  coachMenu((Coach) loggedIn);  break;
            case "Member": memberMenu((Member) loggedIn); break;
        }

        System.out.println("\nGoodbye!");
        scanner.close();
    }

    // ══════════════════════════════════════════════════════════
    //  ADMIN MENU
    // ══════════════════════════════════════════════════════════

    static void adminMenu(Admin admin) {
        boolean running = true;
        while (running) {
            System.out.println("\n===== Admin Menu =====");
            System.out.println("1.  Add member");
            System.out.println("2.  Delete member");
            System.out.println("3.  Update member");
            System.out.println("4.  List all members");
            System.out.println("5.  Search member by name");
            System.out.println("6.  Add coach");
            System.out.println("7.  Delete coach");
            System.out.println("8.  List all coaches");
            System.out.println("9.  Assign coach to member");
            System.out.println("10. Add admin");
            System.out.println("11. Delete admin");
            System.out.println("12. List all admins");
            System.out.println("13. Manage billing");
            System.out.println("14. Generate member report");
            System.out.println("15. Check expiring subscriptions");
            System.out.println("0.  Logout");
            System.out.print("Choice: ");

            String choice = scanner.nextLine().trim();

            switch (choice) {

                case "1": // Add member
                    System.out.print("Name: ");
                    String mName = scanner.nextLine().trim();
                    System.out.print("Username: ");
                    String mUser = scanner.nextLine().trim();
                    System.out.print("Password: ");
                    String mPass = scanner.nextLine().trim();
                    System.out.print("Subscription days from today: ");
                    int days;

                    while (true) {
                        System.out.print("Subscription days from today: ");
                        String input = scanner.nextLine().trim();

                        try {
                            days = Integer.parseInt(input);

                            if (days <= 0) {
                                System.out.println("Subscription days must be greater than 0.");
                                continue;
                            }

                            break;

                        } catch (NumberFormatException e) {
                            System.out.println("Please enter a valid number.");
                        }
                    }                    Date endDate = new Date(System.currentTimeMillis()
                            + (long) days * 24 * 60 * 60 * 1000);
                    Member newMember = new Member(system.nextMemberId(),
                            mName, mUser, mPass, endDate);
                    system.addMember(newMember);
                    break;

                case "2": // Delete member
                    System.out.print("Member ID to delete: ");
                    int delId = Integer.parseInt(scanner.nextLine().trim());
                    system.deleteMember(delId);
                    break;

                case "3": // Update member
                    System.out.print("Member ID to update: ");
                    int upId = Integer.parseInt(scanner.nextLine().trim());
                    System.out.print("New name: ");
                    String newName = scanner.nextLine().trim();
                    System.out.print("New username: ");
                    String newUser = scanner.nextLine().trim();
                    system.updateMember(upId, newName, newUser);
                    break;

                case "4": // List members
                    if (system.getMembers().isEmpty()) {
                        System.out.println("No members registered.");
                    } else {
                        System.out.println("===== All Members =====");
                        for (Member m : system.getMembers())
                            System.out.println("  " + m
                                    + " | Coach: "   + m.getCoachName()
                                    + " | Expires: " + m.getSubscriptionEndDate()
                                    + " | Days left: " + m.daysUntilExpiry());
                    }
                    break;

                case "5": // Search member
                    System.out.print("Member name to search: ");
                    String sName = scanner.nextLine().trim();
                    Member found = system.searchMemberByName(sName);
                    if (found != null)
                        System.out.println("Found: " + found
                                + " | Coach: " + found.getCoachName()
                                + " | Plan: "  + found.getPlan());
                    else
                        System.out.println("Member not found.");
                    break;

                case "6": // Add coach
                    System.out.print("Name: ");
                    String cName = scanner.nextLine().trim();
                    System.out.print("Username: ");
                    String cUser = scanner.nextLine().trim();
                    System.out.print("Password: ");
                    String cPass = scanner.nextLine().trim();
                    system.addCoach(new Coach(system.nextCoachId(),
                            cName, cUser, cPass));
                    break;

                case "7": // Delete coach
                    System.out.print("Coach ID to delete: ");
                    int cDelId = Integer.parseInt(scanner.nextLine().trim());
                    system.deleteCoach(cDelId);
                    break;

                case "8": // List coaches
                    if (system.getCoaches().isEmpty()) {
                        System.out.println("No coaches registered.");
                    } else {
                        System.out.println("===== All Coaches =====");
                        for (Coach c : system.getCoaches())
                            System.out.println("  " + c
                                    + " | Members: " + c.getMemberNames().size());
                    }
                    break;

                case "9": // Assign coach
                    System.out.print("Member ID: ");
                    int aMemId = Integer.parseInt(scanner.nextLine().trim());
                    System.out.print("Coach ID: ");
                    int aCoaId = Integer.parseInt(scanner.nextLine().trim());
                    system.assignCoach(aMemId, aCoaId);
                    break;

                case "10": // Add admin
                    System.out.print("Admin name: ");
                    String aName = scanner.nextLine().trim();
                    System.out.print("Admin username: ");
                    String aUser = scanner.nextLine().trim();
                    System.out.print("Admin password: ");
                    String aPass = scanner.nextLine().trim();

                    Admin newAdmin = new Admin(system.nextAdminId(), aName, aUser, aPass);
                    system.addAdmin(newAdmin);
                    break;

                case "11": // Delete admin
                    System.out.print("Admin ID to delete: ");
                    int adminId = Integer.parseInt(scanner.nextLine().trim());

                    if (admin.getId() == adminId) {
                        System.out.println("You cannot delete the currently logged-in admin.");
                    } else {
                        system.deleteAdmin(adminId);
                    }
                    break;

                case "12": // List admins
                    if (system.getAdmins().isEmpty()) {
                        System.out.println("No admins registered.");
                    } else {
                        System.out.println("===== All Admins =====");
                        for (Admin a : system.getAdmins()) {
                            System.out.println("  " + a);
                        }
                    }
                    break;

                case "13": // Billing
                    billingMenu();
                    break;

                case "14": // Report
                    ReportGenerator.memberReport(system.getMembers());
                    break;

                case "15": // Expiring
                    system.checkExpiringSubscriptions();
                    break;

                case "0":
                    running = false;
                    break;

                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    // ══════════════════════════════════════════════════════════
    //  COACH MENU
    // ══════════════════════════════════════════════════════════

    static void coachMenu(Coach coach) {
        boolean running = true;
        while (running) {
            System.out.println("\n===== Coach Menu =====");
            System.out.println("1. View my members");
            System.out.println("2. Set plan for a member");
            System.out.println("3. Add schedule entry");
            System.out.println("4. View my schedule");
            System.out.println("5. Send message to all members");
            System.out.println("6. Update my info");
            System.out.println("0. Logout");
            System.out.print("Choice: ");

            String choice = scanner.nextLine().trim();

            switch (choice) {

                case "1": // View members
                    coach.showDashboard();
                    break;

                case "2": // Set plan
                    System.out.print("Member name: ");
                    String pName = scanner.nextLine().trim();
                    Member pm = system.searchMemberByName(pName);
                    if (pm != null) {
                        System.out.print("Plan: ");
                        String plan = scanner.nextLine().trim();
                        coach.setPlan(pm, plan);
                        system.saveAll();
                    } else {
                        System.out.println("Member not found.");
                    }
                    break;

                case "3": // Add schedule
                    System.out.print("Schedule entry: ");
                    String entry = scanner.nextLine().trim();
                    coach.addScheduleEntry(entry);
                    system.saveAll();
                    System.out.println("Schedule entry added.");
                    break;

                case "4": // View schedule
                    System.out.println("===== Your Schedule =====");
                    if (coach.getSchedule().isEmpty())
                        System.out.println("  No entries yet.");
                    else
                        for (String s : coach.getSchedule())
                            System.out.println("  - " + s);
                    break;

                case "5": // Send message
                    System.out.print("Message: ");
                    String msg = scanner.nextLine().trim();
                    coach.sendMessage(msg);
                    break;

                case "6": // Update info
                    System.out.print("New name (Enter to skip): ");
                    String newN = scanner.nextLine().trim();
                    System.out.print("New username (Enter to skip): ");
                    String newU = scanner.nextLine().trim();
                    if (!newN.isEmpty()) coach.setName(newN);
                    if (!newU.isEmpty()) coach.setUsername(newU);
                    system.saveAll();
                    System.out.println("Info updated.");
                    break;

                case "0":
                    running = false;
                    break;

                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    // ══════════════════════════════════════════════════════════
    //  MEMBER MENU
    // ══════════════════════════════════════════════════════════

    static void memberMenu(Member member) {
        boolean running = true;
        while (running) {
            System.out.println("\n===== Member Menu =====");
            System.out.println("1. View my dashboard");
            System.out.println("2. View my coach and schedule");
            System.out.println("3. Update my info");
            System.out.println("0. Logout");
            System.out.print("Choice: ");

            String choice = scanner.nextLine().trim();

            switch (choice) {

                case "1":
                    member.showDashboard();
                    break;

                case "2": // View coach & schedule
                    System.out.println("===== Your Coach & Schedule =====");
                    System.out.println("Coach : " + member.getCoachName());
                    System.out.println("Plan  : " + member.getPlan());
                    Coach c = system.getCoachByName(member.getCoachName());
                    if (c != null) {
                        System.out.println("Coach's schedule:");
                        if (c.getSchedule().isEmpty())
                            System.out.println("  No schedule set yet.");
                        else
                            for (String s : c.getSchedule())
                                System.out.println("  - " + s);
                    }
                    break;

                case "3": // Update info
                    System.out.print("New name (Enter to skip): ");
                    String newN = scanner.nextLine().trim();
                    System.out.print("New username (Enter to skip): ");
                    String newU = scanner.nextLine().trim();
                    if (!newN.isEmpty()) member.setName(newN);
                    if (!newU.isEmpty()) member.setUsername(newU);
                    system.saveAll();
                    System.out.println("Info updated.");
                    break;

                case "0":
                    running = false;
                    break;

                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    // ══════════════════════════════════════════════════════════
    //  BILLING MENU
    // ══════════════════════════════════════════════════════════

    static void billingMenu() {
        System.out.println("\n===== Billing Menu =====");
        System.out.println("1. Add bill for a member");
        System.out.println("2. View all bills");
        System.out.println("0. Back");
        System.out.print("Choice: ");
        String choice = scanner.nextLine().trim();

        switch (choice) {
            case "1":
                System.out.print("Member ID: ");
                int id = Integer.parseInt(scanner.nextLine().trim());
                System.out.print("Amount: ");
                double amount = Double.parseDouble(scanner.nextLine().trim());
                ReportGenerator.addBill(id, amount);
                break;
            case "2":
                ReportGenerator.listBills();
                break;
        }
    }
}