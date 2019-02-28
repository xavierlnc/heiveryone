package dao;

import entites.Post;
import entites.User;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface UserDAO {

    public List<User> listUser();

    public User getUser(String identifiant) throws SQLException;

    public User getUser(int id) throws SQLException;

    public int addUser(User usr);

    public void delUser(int id) throws SQLException;

    public void updateUser(String identifiant, User usr);

    public void updateUserStatus(int id, String status);

    public ArrayList<User> searchUser(String searchChaine);

    public ArrayList<User> getStalksOfUser (User usr);

    public void addStalkToUser (int currentUser_id, int targetUser_id);

    public void delStalkOfUser (int currentUser_id, int targetUser_id);

    public ArrayList<User> propositionOfUser(User usr,int nbElmts);
}
