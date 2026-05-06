package models;

import java.util.Date;

public class Member extends User {

    private Date subscriptionEndDate;
    private String coachName;
    private String schedule;

    public Member(int id, String name, String username, String password, Date subscriptionEndDate) {
        super(id, name, username, password);
        this.subscriptionEndDate = subscriptionEndDate;
        this.coachName = "Not assigned";
        this.schedule = "Not set";
    }

    @Override
    public String getRole() { return "Member"; }

    @Override
    public void showDashboard() {
        System.out.println("=============================");
        System.out.println("      MEMBER DASHBOARD       ");
        System.out.println("=============================");
        System.out.println("Welcome, " + getName());
        System.out.println("Coach      : " + coachName);
        System.out.println("Schedule       : " + schedule);
        System.out.println("Expires    : " + subscriptionEndDate);
        System.out.println("Days left  : " + daysUntilExpiry());
        if (daysUntilExpiry() <= 7)
            System.out.println("*** WARNING: Subscription expiring soon! ***");
        else if (daysUntilExpiry() <= 0)
            System.out.println("*** ALERT: Subscription has expired! ***");
        System.out.println("=============================");
    }

    public long daysUntilExpiry() {
        long diff = subscriptionEndDate.getTime() - new Date().getTime();
        return diff / (1000 * 60 * 60 * 24);
    }

    // Getters
    public Date getSubscriptionEndDate() { return subscriptionEndDate; }
    public String getCoachName()         { return coachName; }
    public String getPlan()              { return schedule; }

    // Setters
    public void setSubscriptionEndDate(Date end) { this.subscriptionEndDate = end; }
    public void setCoachName(String coachName) { this.coachName = coachName; }
    public void setPlan(String plan)           { this.schedule = plan; }

    @Override
    public String toFileString() {
        return getId() + "," + getName() + "," + getUsername() + "," +
                getPassword() + "," + getRole() + "," +
                subscriptionEndDate.getTime() + "," + coachName + "," + schedule;
    }
}