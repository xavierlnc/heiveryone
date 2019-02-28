package dao;

import entites.Post;
import entites.User;

import java.util.ArrayList;

public interface PostDAO {

    public ArrayList<Post> getListPostOfUser(User usr);

    public ArrayList<Post> getListPostOfStalks(User usr);

    public void addPostOfUser(Post post, User usr);

    public Post getPost(int postId);

    public void delPost(int postId);

    public void addLikeToPost(int post_id, int user_id);

    public void delLikeToPost(int post_id, int user_id);
}
