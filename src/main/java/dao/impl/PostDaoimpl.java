package dao.impl;

import dao.PostDAO;
import entites.Post;
import entites.User;
import manager.UserLibrary;

import java.sql.*;
import java.util.ArrayList;

public class PostDaoimpl implements PostDAO {

    @Override
    public ArrayList<Post> getListPostOfUser(User usr) {
        ArrayList<Post> posts = new ArrayList<>();
        try (Connection connection = DataSourceProvider.getDataSource().getConnection()) {
            String sql = "SELECT * FROM posts WHERE user_id=? ORDER BY date_of_post DESC, time_of_post DESC;";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, usr.getId());
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        Post post = new Post(
                                resultSet.getInt("id"),
                                usr,
                                resultSet.getDate("date_of_post").toLocalDate(),
                                resultSet.getTime("time_of_post"),
                                resultSet.getString("texte"),
                                getUserLikeOfPost(resultSet.getInt("id")));
                        posts.add(post);

                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return posts;
    }

    @Override
    public ArrayList<Post> getListPostOfStalks(User usr) {
        ArrayList<Post> posts = new ArrayList<>();
        try (Connection connection = DataSourceProvider.getDataSource().getConnection()) {
            String sql = "SELECT * FROM posts WHERE user_id IN (SELECT user_stalked FROM stalks WHERE user_id = ? ) ORDER BY date_of_post DESC, time_of_post DESC;";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1,usr.getId());
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        Post post = new Post(
                                resultSet.getInt("id"),
                                UserLibrary.getInstance().getUser(resultSet.getInt("user_id")),
                                resultSet.getDate("date_of_post").toLocalDate(),
                                resultSet.getTime("time_of_post"),
                                resultSet.getString("texte"),
                                getUserLikeOfPost(resultSet.getInt("id")));
                        posts.add(post);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return posts;
    }

    @Override
    public void addPostOfUser(Post post, User usr) {
        try (Connection connection = DataSourceProvider.getDataSource().getConnection()) {
            String sql = "INSERT INTO posts(user_id, date_of_post, time_of_post, texte) VALUES(?, ?, ?, ?);";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, usr.getId());
                statement.setDate(2, Date.valueOf(post.getDateOfPublication()));
                statement.setTime(3, post.getTimeOfPublication());
                statement.setString(4, post.getTexte());
                statement.executeUpdate();
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Post getPost(int postId) {
        Post post = null;
        try (Connection connection = DataSourceProvider.getDataSource().getConnection()) {
            String sql = "SELECT * FROM posts WHERE id = ? ;";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                    statement.setInt(1,postId);
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        post = new Post(
                                resultSet.getInt("id"),
                                UserLibrary.getInstance().getUser(resultSet.getInt("user_id")),
                                resultSet.getDate("date_of_post").toLocalDate(),
                                resultSet.getTime("time_of_post"),
                                resultSet.getString("texte"),
                                getUserLikeOfPost(resultSet.getInt("id")));
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return  post;
    }

    @Override
    public void delPost(int postId) {
        try (Connection connection = DataSourceProvider.getDataSource().getConnection()) {
            String sql = "DELETE FROM posts WHERE id = ?;";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, postId);
                statement.executeUpdate();
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        try (Connection connection = DataSourceProvider.getDataSource().getConnection()) {
            String sql = "DELETE FROM postLike WHERE post_id = ?;";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, postId);
                statement.executeUpdate();
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private ArrayList<Integer> getUserLikeOfPost(int post_id) {
        ArrayList<Integer> result = new ArrayList<>();
        try (Connection connection = DataSourceProvider.getDataSource().getConnection()) {
            String sql = "SELECT * FROM postLike WHERE post_id = ? ;";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1,post_id);
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        result.add(resultSet.getInt("userLike_id"));
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public void delLikeToPost(int post_id, int user_id) {
        try (Connection connection = DataSourceProvider.getDataSource().getConnection()) {
            String sql = "DELETE FROM postLike WHERE post_id = ? AND userLike_id = ?;";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1,post_id);
                statement.setInt(2,user_id);
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addLikeToPost(int post_id, int user_id) {
        try (Connection connection = DataSourceProvider.getDataSource().getConnection()) {
            String sql = "INSERT INTO postLike(post_id, userLike_id) VALUES (?,?);";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1,post_id);
                statement.setInt(2,user_id);
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

}
