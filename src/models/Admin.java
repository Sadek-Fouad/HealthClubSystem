package models;

import java.util.List;

public class Admin extends User {

    public Admin(int id, String name, String username, String password) {
        super(id, name, username, password);
    }

    @Override
    public String getRole() { return "Admin"; }

    @Override
    public void showDashboard() {
        System.out.println("=============================");
        System.out.println("      ADMIN DASHBOARD        ");
        System.out.println("=============================");
        System.out.println("Welcome, " + getName());
        System.out.println("You can  Manage Members, Manage Coaches, Manage Billing, Generate Reports and Assign Coach to Member");
        System.out.println("Choose From The Menu The Task That You Wanna Perform:" );
        System.out.println("=============================");
    }

    // Member operations

    public void addMember(List<Member> members, Member m) {
        members.add(m);
        System.out.println("Member added: " + m.getName());
    }

    public void deleteMember(List<Member> members, int id) {
        boolean removed = members.removeIf(m -> m.getId() == id);
        if (removed)
            System.out.println("Member ID " + id + " deleted.");
        else
            System.out.println("Member ID " + id + " not found.");
    }

    public void updateMember(List<Member> members, int id, String newName, String newUsername) {
        for (Member m : members) {
            if (m.getId() == id) {
                m.setName(newName);
                m.setUsername(newUsername);
                System.out.println("Member updated: " + m);
                return;
            }
        }
        System.out.println("Member ID " + id + " not found.");
    }

    public Member searchMember(List<Member> members, String name) {
        for (Member m : members) {
            if (m.getName().equalsIgnoreCase(name)) {
                return m;
            }
        }
        System.out.println("No member found with name: " + name);
        return null;
    }

    public void listMembers(List<Member> members) {
        if (members.isEmpty()) {
            System.out.println("No members registered.");
            return;
        }

        System.out.println("===== Member List =====");
        for (Member m : members) {
            System.out.println("  " + m + " | Coach: " + m.getCoachName()
                    + " | Expires: " + m.getSubscriptionEndDate());
        }
    }

    // Coach operations

    public void addCoach(List<Coach> coaches, Coach c) {
        coaches.add(c);
        System.out.println("Coach added: " + c.getName());
    }

    public void deleteCoach(List<Coach> coaches, int id) {
        boolean removed = coaches.removeIf(c -> c.getId() == id);
        if (removed)
            System.out.println("Coach ID " + id + " deleted.");
        else
            System.out.println("Coach ID " + id + " not found.");
    }

    public void updateCoach(List<Coach> coaches, int id, String newName, String newUsername) {
        for (Coach c : coaches) {
            if (c.getId() == id) {
                c.setName(newName);
                c.setUsername(newUsername);
                System.out.println("Coach updated: " + c);
                return;
            }
        }
        System.out.println("Coach ID " + id + " not found.");
    }

    public Coach searchCoach(List<Coach> coaches, String name) {
        for (Coach c : coaches) {
            if (c.getName().equalsIgnoreCase(name)) {
                return c;
            }
        }
        System.out.println("No coach found with name: " + name);
        return null;
    }

    public void listCoaches(List<Coach> coaches) {
        if (coaches.isEmpty()) {
            System.out.println("No coaches registered.");
            return;
        }

        System.out.println("===== Coach List =====");
        for (Coach c : coaches) {
            System.out.println("  " + c + " | Members: " + c.getMemberNames().size());
        }
    }

    // Billing operations

    public void manageBilling(Member member, double amount, boolean paid) {
        System.out.println("===== Billing Info =====");
        System.out.println("Member: " + member.getName());
        System.out.println("Amount: " + amount);
        if (paid)
            System.out.println("Status: Paid");
        else
            System.out.println("Status: Not Paid");
    }

    // Reports

    public void generateMemberReport(List<Member> members) {
        if (members.isEmpty()) {
            System.out.println("No members available for report.");
            return;
        }

        System.out.println("===== Members Report =====");
        for (Member m : members) {
            System.out.println("Name: " + m.getName());
            System.out.println("ID: " + m.getId());
            System.out.println("Coach: " + m.getCoachName());
            System.out.println("Subscription End Date: " + m.getSubscriptionEndDate());
            System.out.println("Days Left: " + m.daysUntilExpiry());
            System.out.println("-------------------------");
        }
    }

    // Assign coach to member

    public void assignCoach(Member member, Coach coach) {
        member.setCoachName(coach.getName());
        coach.addMemberName(member.getName());
        System.out.println(coach.getName() + " assigned to " + member.getName());
    }

    @Override
    public String toFileString() {
        return getId() + "," + getName() + "," + getUsername() + "," +
                getPassword() + "," + getRole();
    }
}