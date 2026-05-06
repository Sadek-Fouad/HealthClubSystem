package managers;

import models.Admin;
import models.Coach;
import models.Member;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FileManager {

    private static final String MEMBERS_FILE = "data/members.txt";
    private static final String COACHES_FILE = "data/coaches.txt";
    private static final String ADMINS_FILE = "data/admins.txt";

    public static void saveMembers(List<Member> members) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(MEMBERS_FILE))) {
            for (Member m : members) {
                pw.println(m.toFileString());
            }
            System.out.println("Members saved.");
        } catch (IOException e) {
            System.out.println("Error saving members: " + e.getMessage());
        }
    }

    public static List<Member> loadMembers() {
        List<Member> members = new ArrayList<>();
        File file = new File(MEMBERS_FILE);

        if (!file.exists()) {
            return members;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;

            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    continue;
                }

                String[] p = line.split(",", 8);
                int id = Integer.parseInt(p[0]);
                String name = p[1];
                String username = p[2];
                String password = p[3];
                long subscriptionTime = Long.parseLong(p[5]);
                String coachName = p[6];
                String schedule = p[7];

                Member member = new Member(id, name, username, password, new Date(subscriptionTime));
                member.setCoachName(coachName);
                member.setPlan(schedule);

                members.add(member);
            }

            System.out.println("Members loaded: " + members.size());
        } catch (IOException e) {
            System.out.println("Error loading members: " + e.getMessage());
        }

        return members;
    }

    public static void saveCoaches(List<Coach> coaches) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(COACHES_FILE))) {
            for (Coach c : coaches) {
                pw.println(c.toFileString());
            }
            System.out.println("Coaches saved.");
        } catch (IOException e) {
            System.out.println("Error saving coaches: " + e.getMessage());
        }
    }

    public static List<Coach> loadCoaches() {
        List<Coach> coaches = new ArrayList<>();
        File file = new File(COACHES_FILE);

        if (!file.exists()) {
            return coaches;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;

            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    continue;
                }

                String[] p = line.split(",", 7);
                int id = Integer.parseInt(p[0]);
                String name = p[1];
                String username = p[2];
                String password = p[3];
                String membersPart = p[5];
                String schedulePart = p[6];

                Coach coach = new Coach(id, name, username, password);

                if (!membersPart.equals("null") && !membersPart.isEmpty()) {
                    String[] memberNames = membersPart.split(";");
                    for (String memberName : memberNames) {
                        coach.addMemberName(memberName);
                    }
                }

                if (!schedulePart.equals("null") && !schedulePart.isEmpty()) {
                    String[] scheduleEntries = schedulePart.split(";");
                    for (String entry : scheduleEntries) {
                        coach.addScheduleEntry(entry);
                    }
                }

                coaches.add(coach);
            }

            System.out.println("Coaches loaded: " + coaches.size());
        } catch (IOException e) {
            System.out.println("Error loading coaches: " + e.getMessage());
        }

        return coaches;
    }

    public static void saveAdmins(List<Admin> admins) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(ADMINS_FILE))) {
            for (Admin a : admins) {
                pw.println(a.toFileString());
            }
            System.out.println("Admins saved.");
        } catch (IOException e) {
            System.out.println("Error saving admins: " + e.getMessage());
        }
    }

    public static List<Admin> loadAdmins() {
        List<Admin> admins = new ArrayList<>();
        File file = new File(ADMINS_FILE);

        if (!file.exists()) {
            return admins;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;

            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    continue;
                }

                String[] p = line.split(",", 5);
                int id = Integer.parseInt(p[0]);
                String name = p[1];
                String username = p[2];
                String password = p[3];

                Admin admin = new Admin(id, name, username, password);
                admins.add(admin);
            }

            System.out.println("Admins loaded: " + admins.size());
        } catch (IOException e) {
            System.out.println("Error loading admins: " + e.getMessage());
        }

        return admins;
    }
}