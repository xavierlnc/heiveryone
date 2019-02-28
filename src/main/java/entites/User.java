package entites;


import java.time.LocalDate;
import java.util.ArrayList;

public class User {

    private int id;
    private String name;
    private String lastName;
    private String identifiant;
    private String mail;
    private LocalDate dob;
    private String pass;
    private String description;
    private ArrayList<User> stalks;
    private String avatar;
    private boolean isAdmin;
    private String theme;

    public User(String name, String lastName, String identifiant, String mail, String pass, LocalDate dob) {
        this.name = name;
        this.lastName = lastName;
        this.identifiant = identifiant;
        this.mail = mail;
        this.dob = dob;
        this.pass = pass;
    }

    public User(Integer id, String name, String lastName, String identifiant, String mail, String pass, LocalDate dob, String description, String avatar, boolean isAdmin, String theme) {
        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.identifiant = identifiant;
        this.mail = mail;
        this.dob = dob;
        this.pass = pass;
        this.description = description;
        this.avatar = avatar;
        this.isAdmin = isAdmin;
        this.theme = theme;
    }

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public String getName() { return this.name; }

    public void setName(String name) {this.name = name;}

    public String getLastName() { return this.lastName; }

    public  void setLastName(String lastName) {this.lastName = lastName;}

    public LocalDate getDob() { return this.dob; }

    public void setDob(LocalDate dob) {this.dob = dob;}

    public String getIdentifiant() { return this.identifiant; }

    public String getMail() {return this.mail;}

    public void setMail(String mail) {this.mail = mail;}

    public String getPass() { return this.pass; }

    public void setPass(String pass){this.pass = pass; }

    public String getDescription() { return description; }

    public void setDescription(String description) { this.description = description; }

    public ArrayList<User> getStalks() { return this.stalks; }

    public void setStalks(ArrayList<User> stalks) { this.stalks = stalks; }

    public String getAvatar() { return this.avatar; }

    public void setAvatar(String avatar) { this.avatar = avatar; }

    public String getAvatarPath() {return "/avatar/" +getAvatar(); }

    public boolean isAdmin() {return this.isAdmin; }

    public void setAdmin(boolean isAdmin) { this.isAdmin = isAdmin; }

    public String getTheme() { return this.theme; }

    public void setTheme(String theme) { this.theme = theme; }
}
