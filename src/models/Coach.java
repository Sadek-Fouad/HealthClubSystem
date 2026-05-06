package models;

import java.util.ArrayList;
import java.util.List;

public class Coach extends User {

    private List<String> memberNames;
    private List<String> schedule;

    public Coach(int id, String name, String username, String password) {
        super(id, name, username, password);
        this.memberNames = new ArrayList<>();
        this.schedule = new ArrayList<>();
    }

    @Override
    public String getRole() { return "Coach"; }

    @Override
    public void showDashboard() {
        System.out.println("=============================");
        System.out.println("      COACH DASHBOARD        ");
        System.out.println("=============================");
        System.out.println("Welcome, " + getName());
        System.out.println("Your members (" + memberNames.size() + "):");
        if (memberNames.isEmpty()) {
            System.out.println("  No members assigned yet.");
        } else {
            for (String m : memberNames)
                System.out.println("  - " + m);
        }
        System.out.println("Schedule entries: " + schedule.size());
        System.out.println("=============================");
    }

    public void addMemberName(String name) {
        if (!memberNames.contains(name))
            memberNames.add(name);
    }

    public void removeMemberName(String name) {
        memberNames.remove(name);
    }

    public void addScheduleEntry(String entry) {
        schedule.add(entry);
    }

    public void setPlan(Member member, String plan) {
        member.setPlan(plan);
        System.out.println("Plan set for " + member.getName());
    }

    public void sendMessage(String message) {
        System.out.println("--- Message from Coach " + getName() + " ---");
        if (memberNames.isEmpty()) {
            System.out.println("  No members to message.");
        } else {
            for (String m : memberNames)
                System.out.println("  -> " + m + ": " + message);
        }
        System.out.println("-------------------------------------------");
    }

    // Getters
    public List<String> getMemberNames() { return memberNames; }
    public List<String> getSchedule()    { return schedule; }

    @Override
    public String toFileString() {
        String members  = String.join(";", memberNames);
        String sched    = String.join(";", schedule);
        return getId() + "," + getName() + "," + getUsername() + "," +
                getPassword() + "," + getRole() + "," + members + "," + sched;
    }
}