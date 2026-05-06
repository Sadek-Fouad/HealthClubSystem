package managers;

import models.Admin;
import models.Coach;
import models.Member;
import models.User;

import java.util.List;

public class SystemManager {

    private List<Member> members;
    private List<Coach>  coaches;
    private List<Admin>  admins;

    // ══════════════════════════════════════════════════════════
    //  CONSTRUCTOR — loads all data from files on startup
    // ══════════════════════════════════════════════════════════

    public SystemManager() {
        members = FileManager.loadMembers();
        coaches = FileManager.loadCoaches();
        admins  = FileManager.loadAdmins();

        // If no admin exists yet, create a default one
        if (admins.isEmpty()) {
            admins.add(new Admin(1, "Super Admin", "admin", "admin123"));
            System.out.println("Default admin created — username: admin, password: admin123");
            saveAll();
        }
    }

    // ══════════════════════════════════════════════════════════
    //  LOGIN
    // ══════════════════════════════════════════════════════════

    public User login(String username, String password) {
        // Check admins
        for (Admin a : admins)
            if (a.getUsername().equals(username) && a.checkPassword(password))
                return a;

        // Check coaches
        for (Coach c : coaches)
            if (c.getUsername().equals(username) && c.checkPassword(password))
                return c;

        // Check members
        for (Member m : members)
            if (m.getUsername().equals(username) && m.checkPassword(password))
                return m;

        return null; // login failed
    }

    // ══════════════════════════════════════════════════════════
    //  CHANGING ADMIN
    // ══════════════════════════════════════════════════════════

    public void addAdmin(Admin a) {
        admins.add(a);
        System.out.println("Admin added: " + a.getName());
        saveAll();
    }

    public void deleteAdmin(int id) {
        if (admins.size() == 1) {
            System.out.println("Cannot delete the last admin.");
            return;
        }

        boolean removed = admins.removeIf(a -> a.getId() == id);

        if (removed) {
            System.out.println("Admin ID " + id + " deleted.");
            saveAll();
        } else {
            System.out.println("Admin ID " + id + " not found.");
        }
    }

    public Admin getAdminById(int id) {
        for (Admin a : admins) {
            if (a.getId() == id)
                return a;
        }
        return null;
    }

    public int nextAdminId() { return admins.stream().mapToInt(a -> a.getId()).max().orElse(0) + 1; }

    // ══════════════════════════════════════════════════════════
    //  MEMBER OPERATIONS
    // ══════════════════════════════════════════════════════════

    public void addMember(Member m) {
        members.add(m);
        System.out.println("Member added: " + m.getName());
        saveAll();
    }

    public void deleteMember(int id) {
        boolean removed = members.removeIf(m -> m.getId() == id);
        if (removed) {
            System.out.println("Member ID " + id + " deleted.");
            saveAll();
        } else {
            System.out.println("Member ID " + id + " not found.");
        }
    }

    public void updateMember(int id, String newName, String newUsername) {
        for (Member m : members) {
            if (m.getId() == id) {
                m.setName(newName);
                m.setUsername(newUsername);
                System.out.println("Member updated: " + m);
                saveAll();
                return;
            }
        }
        System.out.println("Member ID " + id + " not found.");
    }

    public Member searchMemberByName(String name) {
        for (Member m : members)
            if (m.getName().equalsIgnoreCase(name))
                return m;
        return null;
    }

    public Member getMemberById(int id) {
        for (Member m : members)
            if (m.getId() == id)
                return m;
        return null;
    }

    // ══════════════════════════════════════════════════════════
    //  COACH OPERATIONS
    // ══════════════════════════════════════════════════════════

    public void addCoach(Coach c) {
        coaches.add(c);
        System.out.println("Coach added: " + c.getName());
        saveAll();
    }

    public void deleteCoach(int id) {
        boolean removed = coaches.removeIf(c -> c.getId() == id);
        if (removed) {
            System.out.println("Coach ID " + id + " deleted.");
            saveAll();
        } else {
            System.out.println("Coach ID " + id + " not found.");
        }
    }

    public Coach getCoachById(int id) {
        for (Coach c : coaches)
            if (c.getId() == id)
                return c;
        return null;
    }

    public Coach getCoachByName(String name) {
        for (Coach c : coaches)
            if (c.getName().equalsIgnoreCase(name))
                return c;
        return null;
    }

    // ══════════════════════════════════════════════════════════
    //  ASSIGN COACH TO MEMBER
    // ══════════════════════════════════════════════════════════

    public void assignCoach(int memberId, int coachId) {
        Member member = getMemberById(memberId);
        Coach  coach  = getCoachById(coachId);

        if (member == null) { System.out.println("Member not found."); return; }
        if (coach  == null) { System.out.println("Coach not found.");  return; }

        // Remove from old coach's list if assigned
        if (!member.getCoachName().equals("Not assigned")) {
            Coach oldCoach = getCoachByName(member.getCoachName());
            if (oldCoach != null)
                oldCoach.removeMemberName(member.getName());
        }

        member.setCoachName(coach.getName());
        coach.addMemberName(member.getName());
        System.out.println(coach.getName() + " assigned to " + member.getName());
        saveAll();
    }

    // ══════════════════════════════════════════════════════════
    //  NOTIFICATIONS — check expiring subscriptions
    // ══════════════════════════════════════════════════════════

    public void checkExpiringSubscriptions() {
        System.out.println("===== Subscription Alerts =====");
        boolean any = false;
        for (Member m : members) {
            long days = m.daysUntilExpiry();
            if (days <= 7 && days >= 0) {
                System.out.println("  ALERT: " + m.getName()
                        + " — expires in " + days + " day(s)!");
                any = true;
            }
        }
        if (!any)
            System.out.println("  No subscriptions expiring soon.");
        System.out.println("================================");
    }

    // ══════════════════════════════════════════════════════════
    //  ID GENERATOR — auto-increment per type
    // ══════════════════════════════════════════════════════════

    public int nextMemberId() {
        return members.stream().mapToInt(m -> m.getId()).max().orElse(0) + 1;
    }

    public int nextCoachId() {
        return coaches.stream().mapToInt(c -> c.getId()).max().orElse(0) + 1;
    }

    // ══════════════════════════════════════════════════════════
    //  GETTERS
    // ══════════════════════════════════════════════════════════

    public List<Member> getMembers() { return members; }
    public List<Coach>  getCoaches() { return coaches; }
    public List<Admin>  getAdmins()  { return admins; }

    // ══════════════════════════════════════════════════════════
    //  SAVE ALL
    // ══════════════════════════════════════════════════════════

    public void saveAll() {
        FileManager.saveMembers(members);
        FileManager.saveCoaches(coaches);
        FileManager.saveAdmins(admins);
    }
}