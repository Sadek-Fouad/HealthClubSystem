package managers;

import models.Member;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ReportGenerator {

    // In-memory bill list (saved to file in Phase 2 extension)
    private static List<String> bills = new ArrayList<>();

    public static void memberReport(List<Member> members) {
        System.out.println("\n========== Member Report ==========");
        System.out.printf("%-5s %-20s %-15s %-20s %-10s%n",
                "ID", "Name", "Coach", "Expires", "Days Left");
        System.out.println("----------------------------------------------------");
        for (Member m : members) {
            System.out.printf("%-5d %-20s %-15s %-20s %-10d%n",
                    m.getId(),
                    m.getName(),
                    m.getCoachName(),
                    m.getSubscriptionEndDate(),
                    m.daysUntilExpiry());
        }
        System.out.println("====================================");
    }

    public static void addBill(int memberId, double amount) {
        String bill = "Member ID: " + memberId
                + " | Amount: $" + amount
                + " | Date: " + new Date()
                + " | Status: UNPAID";
        bills.add(bill);
        System.out.println("Bill added: " + bill);
    }

    public static void listBills() {
        System.out.println("\n========== Bills ==========");
        if (bills.isEmpty())
            System.out.println("No bills recorded.");
        else
            for (String b : bills)
                System.out.println("  " + b);
        System.out.println("============================");
    }
}