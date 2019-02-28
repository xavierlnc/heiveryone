package manager;

import Exceptions.IdentifiantAlreadyExistsException;
import Exceptions.IdentifiantOrPassIncorrectException;
import Exceptions.InputsEmptyException;
import Exceptions.UserDeletesHimselfException;
import dao.UserDAO;
import dao.impl.UserDaoimpl;
import entites.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.MotDePasseUtils;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class UserLibrary {

    private final Logger LOG = LoggerFactory.getLogger(this.getClass());

    private static class UserLibraryHolder {
        private final static UserLibrary instance = new UserLibrary();
    }

    public static UserLibrary getInstance() { return UserLibraryHolder.instance; }

    private UserDAO userDAO = new UserDaoimpl();

    public List<User> listUser() {return userDAO.listUser();}

    public User getUser(String identifiant) throws SQLException {return  userDAO.getUser(identifiant);}

    public User getUser(int id) throws SQLException {return  userDAO.getUser(id);}

    public int addUser(String name, String lastName, String dob, String mail, String identifiant, String pass) throws SQLException, IdentifiantAlreadyExistsException, InputsEmptyException {
        int result = 0;
        if ("".equals(name) || "".equals(lastName) || "".equals(dob) || "".equals(mail) || "".equals(identifiant) || "".equals(pass)) {
            throw new InputsEmptyException("Les champs sont vides");
        } else {
            DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            User newUser = new User(name,lastName,identifiant,mail,MotDePasseUtils.genererMotDePasse(pass),LocalDate.parse(dob,dateFormat));
            if(getUser(newUser.getIdentifiant()) == null) {
                result = userDAO.addUser(newUser);
                LOG.info("Un utilisateur a été ajouté : {}",newUser.getId());
            } else {
                throw new IdentifiantAlreadyExistsException("L'identifiant existe deja");
            }
        }
        return result;
    };

    public void delUser(int current_UserId, int target_userId) throws SQLException, UserDeletesHimselfException {
        if (current_UserId != target_userId) {
            userDAO.delUser(target_userId);
            LOG.info("Un utilisateur a été supprimé : {}",target_userId);
        } else {
            LOG.warn("{} tente de se supprimer",target_userId);
            throw new UserDeletesHimselfException();
        }
    }


    public void verifyUserInfos(String identifiant, String password) throws IdentifiantOrPassIncorrectException, SQLException {
        User user = getUser(identifiant);
        if (user == null) {
            throw new IdentifiantOrPassIncorrectException("Utilisateur introuvable");
        } else if (!MotDePasseUtils.validerMotDePasse(password,user.getPass())) {
            throw new IdentifiantOrPassIncorrectException("Mot de passe incorrect");
        }
    }

    public void updateUser(String name, String lastName, String dobAsString, String description, String theme,
                           String mail, String identifiant, String oldPass, String newPass, String confNewPass) throws SQLException {

        User user = null;
        try {
            user = UserLibrary.getInstance().getUser(identifiant);
            //General information
            if (!"".equals(name)) {
                user.setName(name);
            } else {
                LOG.warn("{} essaye d'enregistrer un prenom vide", identifiant);
            }

            if (!"".equals(lastName)) {
                user.setLastName(lastName);
            } else {
                LOG.warn("{} essaye d'enregistrer un nom vide", identifiant);
            }

            DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate dob = LocalDate.parse(dobAsString, dateFormat);
            if (dob != null) {
                user.setDob(dob);
            } else {
                LOG.warn("{} essaye d'enregistrer une date de naissance vide", identifiant);
            }

            user.setDescription(description);
            user.setTheme(theme);

            //Connection information
            if (!"".equals(mail)) {
                user.setMail(mail);
            } else {
                LOG.warn("{} essaye d'enregistrer un mail vide", identifiant);
            }

            if (MotDePasseUtils.validerMotDePasse(oldPass,user.getPass())) {
                if ( (newPass != null && !"".equals(newPass)) && newPass.equals(confNewPass) ) {
                    user.setPass(MotDePasseUtils.genererMotDePasse(newPass));
                }
            }

            userDAO.updateUser(identifiant, user);

        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException();
        }

    }

    public void updateUserStatus(int id, String status) { userDAO.updateUserStatus(id, status);}

    public ArrayList<User> searchUser(String searchChaine) { return userDAO.searchUser(searchChaine); }

    public ArrayList<User> getStalksOfUser (User usr) { return userDAO.getStalksOfUser(usr); }

    public void addStalkToUser (int currentUser_id, int targetUser_id) {userDAO.addStalkToUser(currentUser_id, targetUser_id);}

    public void delStalkOfUser (int currentUser_id, int targetUser_id) {userDAO.delStalkOfUser(currentUser_id, targetUser_id);}

    public ArrayList<User> propositionOfUser(User usr,int nbElmts) { return userDAO.propositionOfUser(usr,nbElmts); }

    public HashMap<String, Boolean> accountPageManager(User current_user,User target_user) {
        HashMap<String, Boolean> result = new HashMap<>();

        //Description
        boolean description = false;
        if (!("".equals(target_user.getDescription()) || target_user.getDescription() == null)) {
            description = true;
        }
        result.put("description",description);

        //If it's user's page | if stalk the user or not
        boolean same = false;
        boolean stalk = false;
        ArrayList<User> currentStalks = UserLibrary.getInstance().getStalksOfUser(current_user);
        for (User usr: currentStalks) {
            if (usr.getIdentifiant().equals(target_user.getIdentifiant())) {
                if (target_user.getIdentifiant().equals(current_user.getIdentifiant())) {
                    same = true;
                    stalk = true;
                } else {
                    stalk = true;
                }
            }
        }
        result.put("same",same);
        result.put("stalk",stalk);
        return result;
    }

    public HashMap<String, Integer> stalksInfo(User current_user) {
        HashMap<String, Integer> result = new HashMap<>();
        result.put("numberOfPosts",PostLibrary.getInstance().getListPostOfUser(current_user).size());
        result.put("numberOfStalks",getStalksOfUser(current_user).size());
        return result;
    }
}
