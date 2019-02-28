package manager;

import Exceptions.InputsEmptyException;
import dao.PostDAO;
import dao.impl.PostDaoimpl;
import entites.Post;
import entites.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

public class PostLibrary {

    private final Logger LOG = LoggerFactory.getLogger(this.getClass());

    private static class PostLibraryHolder {
        private final static PostLibrary instance = new PostLibrary();
    }

    public static PostLibrary getInstance() { return PostLibrary.PostLibraryHolder.instance; }

    private PostDAO postDAO = new PostDaoimpl();

    public ArrayList<Post> getListPostOfUser(User usr) {
        return postDAO.getListPostOfUser(usr);}

    public ArrayList<Post> getListPostOfStalks(User usr) {
        return postDAO.getListPostOfStalks(usr);}

    public int addPostOfUser(User user, LocalDate dateOfPublication, Time timeOfPublication, String texte) throws InputsEmptyException, SQLException{
        int result = 0;
        if ("".equals(texte)){
            throw new InputsEmptyException("Le message du post est vide");
        } else {
            Post newPost= new Post(user, dateOfPublication, timeOfPublication, texte);
            postDAO.addPostOfUser(newPost, user);
            LOG.info(" {} a ajouté un post.", user.getIdentifiant());
        }
        return result;
    };


    public Post getPost(int postId) { return postDAO.getPost(postId);}

    public void delPost(int postId) { postDAO.delPost(postId);}

    public void addLikeToPost(int post_id, int user_id) { postDAO.addLikeToPost(post_id,user_id); }

    public void delLikeToPost(int post_id, int user_id) { postDAO.delLikeToPost(post_id,user_id); }

    public HashMap<Integer,Boolean> setUserLikeOnPost(ArrayList<Post> postList, int user_id) {
        HashMap<Integer,Boolean> isExistaLikeOfUserOnPost= new HashMap<>();
        for (Post post: postList) {
            if(post.getLikeOfUser().contains(user_id)) {
                isExistaLikeOfUserOnPost.put(post.getId(),true);
            } else {
                isExistaLikeOfUserOnPost.put(post.getId(),false);
            }
        }
        return isExistaLikeOfUserOnPost;
    }
}
