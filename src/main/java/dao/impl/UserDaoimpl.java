package dao.impl;

import dao.UserDAO;
import entites.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;

public class UserDaoimpl implements UserDAO {

    private final Logger LOG = LoggerFactory.getLogger(this.getClass());

    @Override
    public ArrayList<User> listUser() {
        ArrayList<User> users = new ArrayList<>();
        try (Connection connection = DataSourceProvider.getDataSource().getConnection()) {
            try (Statement statement = connection.createStatement()) {
                String sql = "SELECT * FROM users";
                try (ResultSet resultSet = statement.executeQuery(sql)) {
                    while (resultSet.next()) {

                        User user = new User(
                                resultSet.getInt("id"),
                                resultSet.getString("name"),
                                resultSet.getString("lastName"),
                                resultSet.getString("user_identifiant"),
                                resultSet.getString("mail"),
                                resultSet.getString("pass"),
                                resultSet.getDate("dob").toLocalDate(),
                                resultSet.getString("description"),
                                resultSet.getString("defaultAvatar"),
                                resultSet.getBoolean("isAdmin"),
                                resultSet.getString("theme"));
                        users.add(user);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    @Override
    public User getUser(String identifiant) throws SQLException {
        User user = null;
        try (Connection connection = DataSourceProvider.getDataSource().getConnection()) {
            String sql = "SELECT * FROM users WHERE user_identifiant = ? ;";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, identifiant);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        return new User(
                                resultSet.getInt("id"),
                                resultSet.getString("name"),
                                resultSet.getString("lastName"),
                                resultSet.getString("user_identifiant"),
                                resultSet.getString("mail"),
                                resultSet.getString("pass"),
                                resultSet.getDate("dob").toLocalDate(),
                                resultSet.getString("description"),
                                resultSet.getString("defaultAvatar"),
                                resultSet.getBoolean("isAdmin"),
                                resultSet.getString("theme"));
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return user;
    }

    @Override
    public User getUser(int id) throws SQLException {
        User user = null;
        try (Connection connection = DataSourceProvider.getDataSource().getConnection()) {
            String sql = "SELECT * FROM users WHERE id = ? ;";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, id);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        return new User(
                                resultSet.getInt("id"),
                                resultSet.getString("name"),
                                resultSet.getString("lastName"),
                                resultSet.getString("user_identifiant"),
                                resultSet.getString("mail"),
                                resultSet.getString("pass"),
                                resultSet.getDate("dob").toLocalDate(),
                                resultSet.getString("description"),
                                resultSet.getString("defaultAvatar"),
                                resultSet.getBoolean("isAdmin"),
                                resultSet.getString("theme"));
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return user;
    }

    @Override
    public int addUser(User usr) {
        try (Connection connection = DataSourceProvider.getDataSource().getConnection()) {
            int generatedId = 0;
            String sql = "INSERT INTO users(user_identifiant, name, lastName, mail, dob, pass) VALUES(?, ?, ?, ?, ?, ?);";
            try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);) {
                statement.setString(1, usr.getIdentifiant());
                statement.setString(2, usr.getName());
                statement.setString(3, usr.getLastName());
                statement.setString(4, usr.getMail());
                statement.setDate(5, Date.valueOf(usr.getDob()));
                statement.setString(6, usr.getPass());
                statement.executeUpdate();

                ResultSet ids = statement.getGeneratedKeys();

                if (ids.next()) {
                    generatedId = ids.getInt(1);
                }
                usr.setId(generatedId);
            }

            sql = "INSERT INTO stalks(user_id, user_stalked) VALUES(?, ?);";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, usr.getId());
                statement.setInt(2, usr.getId());
                statement.executeUpdate();
            }

            return generatedId;
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public void delUser(int id) throws SQLException {
        try (Connection connection = DataSourceProvider.getDataSource().getConnection()) {
            String sql = "DELETE FROM stalks WHERE user_id = ? OR user_stalked = ?;";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, id);
                statement.setInt(2, id);
                statement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            sql = "DELETE FROM posts WHERE user_id = ? ;";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, id);
                statement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
             sql = "DELETE FROM users WHERE id = ? ;";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, id);
                statement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void updateUser(String identifiant, User usr) {
        try (Connection connection = DataSourceProvider.getDataSource().getConnection()) {
            String sql = "UPDATE users SET name = ?, lastName = ?, mail = ?, dob = ?, pass = ?, description = ?, theme = ?  WHERE user_identifiant = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {

                statement.setString(1,usr.getName());
                statement.setString(2,usr.getLastName());
                statement.setString(3,usr.getMail());
                statement.setDate(4,Date.valueOf(usr.getDob()));
                statement.setString(5,usr.getPass());
                statement.setString(6,usr.getDescription());
                statement.setString(7,usr.getTheme());
                statement.setString(8,identifiant);
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void updateUserStatus(int id, String status) {
        try (Connection connection = DataSourceProvider.getDataSource().getConnection()) {
            String sql = "UPDATE users SET isAdmin = ?  WHERE id = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                boolean admin = false;
                if ("Administrateur".equals(status)) {
                    admin = true;
                }
                statement.setBoolean(1,admin);
                statement.setInt(2,id);
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public ArrayList<User> searchUser(String searchChaine) {
        ArrayList<User> users = new ArrayList<>();
        try (Connection connection = DataSourceProvider.getDataSource().getConnection()) {
            String sql = "SELECT * FROM users WHERE user_identifiant LIKE ? OR name LIKE ? OR lastName LIKE ? ORDER BY lastName, name, user_identifiant ;";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1,"%"+searchChaine+"%");
                statement.setString(2,"%"+searchChaine+"%");
                statement.setString(3,"%"+searchChaine+"%");
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        User user = new User(
                                resultSet.getInt("id"),
                                resultSet.getString("name"),
                                resultSet.getString("lastName"),
                                resultSet.getString("user_identifiant"),
                                resultSet.getString("mail"),
                                resultSet.getString("pass"),
                                resultSet.getDate("dob").toLocalDate(),
                                resultSet.getString("description"),
                                resultSet.getString("defaultAvatar"),
                                resultSet.getBoolean("isAdmin"),
                                resultSet.getString("theme"));
                        users.add(user);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    @Override
    public ArrayList<User> getStalksOfUser(User usr) {
        ArrayList<User> stalks = new ArrayList<>();
        try (Connection connection = DataSourceProvider.getDataSource().getConnection()) {
            String sql = "SELECT * FROM stalks WHERE user_id = ?;";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1,usr.getId());
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        stalks.add(getUser(resultSet.getInt("user_stalked")));
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return stalks;
    }

    @Override
    public void addStalkToUser(int currentUse_id, int targetUser_id) {
        try (Connection connection = DataSourceProvider.getDataSource().getConnection()) {
            String sql = "INSERT INTO stalks (user_id,user_stalked) VALUES (?,?);";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1,currentUse_id);
                statement.setInt(2,targetUser_id);
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delStalkOfUser(int currentUser_id, int targetUser_id) {
        try (Connection connection = DataSourceProvider.getDataSource().getConnection()) {
            String sql = "DELETE FROM stalks WHERE user_id = ? and user_stalked = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1,currentUser_id);
                statement.setInt(2,targetUser_id);
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ArrayList<User> propositionOfUser(User usr,int nbElmts) {
        ArrayList<User> proposition = new ArrayList<>();
        ArrayList<User> listResult = new ArrayList<>();
        ArrayList<User> alreadyStalked = getStalksOfUser(usr);

        for (User propUser: listUser()) {
            boolean isStalked = false;
            for (User user: alreadyStalked) {
                if (propUser.getIdentifiant().equals(user.getIdentifiant())) {
                    isStalked = true;
                }
            }
            if (!isStalked) {
                proposition.add(propUser);
            }
        }
        java.util.Random rd = new java.util.Random(System.currentTimeMillis());
        while (listResult.size() < nbElmts && proposition.size() != listResult.size()) {
            User randomUser = proposition.get(rd.nextInt(proposition.size()));
            if (!listResult.contains(randomUser)) {
                listResult.add(randomUser);
            }
        }
        return listResult;
    }
}






