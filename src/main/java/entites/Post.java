package entites;

import java.sql.Time;
import java.time.LocalDate;
import java.util.ArrayList;

public class Post {

    private int id;
    private User author;
    private LocalDate dateOfPublication;
    private Time timeOfPublication;
    private String texte;
    private ArrayList<Integer> likeOfUser;

    public Post(User user, LocalDate dateOfPublication,Time timeOfPublication, String texte) {
        this.author = user;
        this.dateOfPublication = dateOfPublication;
        this.timeOfPublication = timeOfPublication;
        this.texte = texte;
    }

    public Post(Integer id, User user, LocalDate dateOfPublication, Time timeOfPublication, String texte, ArrayList<Integer> likeOfUser) {
        this.id = id;
        this.author = user;
        this.dateOfPublication = dateOfPublication;
        this.timeOfPublication = timeOfPublication;
        this.texte = texte;
        this.likeOfUser = likeOfUser;
    }

    public User getAuthor() { return this.author; }

    public LocalDate getDateOfPublication() { return this.dateOfPublication;}

    public String getTexte() { return this.texte; }

    public void setId(int id) {
        this.id = id;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public void setDateOfPublication(LocalDate dateOfPublication) {
        this.dateOfPublication = dateOfPublication;
    }

    public void setTimeOfPublication(Time timeOfPublication) {
        this.timeOfPublication = timeOfPublication;
    }

    public void setTexte(String texte) {
        this.texte = texte;
    }

    public int getId() {
        return id;
    }

    public Time getTimeOfPublication() {
        return timeOfPublication;
    }

    public void setLikeOfUser(ArrayList<Integer> likeOfUser) { this.likeOfUser = likeOfUser; }

    public ArrayList<Integer> getLikeOfUser() {return this.likeOfUser; }

    public int getNumberOfLike() {return this.likeOfUser.size(); }
}
