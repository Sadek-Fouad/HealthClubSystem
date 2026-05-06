package models;

public abstract class User {
    private int id;
    private String name;
    private String username;
    private String password;

    public User(int id, String name, String username, String password){
        this.id = id;
        this.name = name;
        this.username  = username ;
        this.password = password;
    }
    // Getters
    public int getId() {return id;}
    public String getName() {return name;}
    public String getUsername() {return username;}

    // Setters
    public void setName(String name) {this.name = name;}
    public void setUsername(String username) { this.username = username; }
    public void setPassword(String password) { this.password = password; }

    public boolean login(String username, String password) {
        if(this.username.equals(username) && checkPassword(password)) {
            System.out.println("Login successful");
            return true;
        } else {
            System.out.println("Invalid username or password");
            return false;
        }
    }

    public void logout() {
        System.out.println("User logged out");
    }

    public boolean checkPassword(String input){
        return this.password.equals(input);
    }

    protected String getPassword() {
        return password;
    }

    public abstract String getRole();
    public abstract void showDashboard();

    public String toFileString() {
        return id + "," + name + "," + username + "," + password + "," + getRole();
    }

    @Override
    public String toString() {
        return "[" + getRole() + "] " + name + " (ID: " + id + ")";
    }
}