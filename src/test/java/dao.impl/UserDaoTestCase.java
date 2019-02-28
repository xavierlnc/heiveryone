package dao.impl;

import entites.User;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

public class UserDaoTestCase {

    private UserDaoimpl userDaoImpl = new UserDaoimpl();

    @Before
    public void initDb() throws Exception {
        try (Connection connection = DataSourceProvider.getDataSource().getConnection();
             Statement stmt = connection.createStatement()) {
            stmt.executeUpdate("DELETE FROM users;");
            stmt.executeUpdate("DELETE FROM stalks");
            stmt.executeUpdate("ALTER TABLE `users` AUTO_INCREMENT=0");


            stmt.executeUpdate("INSERT INTO `users`(`user_identifiant`,`name`,`lastName`,`mail`,`dob`,`pass` ) " +
                    "VALUES ('AdminHei','Admin', 'Hei', 'admin.hei@hei.yncrea.fr', '2018-11-05', '$argon2i$v=19$m=65536,t=5,p=1$1pExvKiY/4q9J1Z/DhUrPcDspbsBWhTiyopvgTaF1bovBYuiqcOUPbrk0g3G/TumpMg/Aff6KduTbP4BhREaXUEq5iU/ZQO3F+WTM4KIP1WxcUzGkQiv/qG0IJQoACtzfwATCkHhoNkflX+r7FqFhye5unFJlbyvFgG3EubEjMI$NOLfzfUeeRtC+LaGC/LvZN4U+n4xXzsdiSO8olXkk6VQzgkJ4HbaTrdCNwbZLSIjkrbBFL4HL3HnzmSqZ0X7/DXJP1XL3aehNQ34X0/UVG0HG8sOmrjGOaCqx+pkIodryWO05qPCMDQIvGATvmyCuEJHU5iPA3JOfGZssoqQKD4');");
            stmt.executeUpdate("INSERT INTO `users`(`user_identifiant`,`name`,`lastName`,`mail`,`dob`,`pass` ) " +
                    "VALUES ('Xavierlnc','Xavier', 'Lencou', 'xavier.lencou@hei.yncrea.fr', '1997-10-23', '$argon2i$v=19$m=65536,t=5,p=1$1pExvKiY/4q9J1Z/DhUrPcDspbsBWhTiyopvgTaF1bovBYuiqcOUPbrk0g3G/TumpMg/Aff6KduTbP4BhREaXUEq5iU/ZQO3F+WTM4KIP1WxcUzGkQiv/qG0IJQoACtzfwATCkHhoNkflX+r7FqFhye5unFJlbyvFgG3EubEjMI$NOLfzfUeeRtC+LaGC/LvZN4U+n4xXzsdiSO8olXkk6VQzgkJ4HbaTrdCNwbZLSIjkrbBFL4HL3HnzmSqZ0X7/DXJP1XL3aehNQ34X0/UVG0HG8sOmrjGOaCqx+pkIodryWO05qPCMDQIvGATvmyCuEJHU5iPA3JOfGZssoqQKD4');");
            stmt.executeUpdate("INSERT INTO `users`(`user_identifiant`,`name`,`lastName`,`mail`,`dob`,`pass` ) " +
                    "VALUES ('GregoireLdcq','Gregoire', 'Leducq', 'gregoire.leducq@hei.yncrea.fr', '1997-12-09', '$argon2i$v=19$m=65536,t=5,p=1$1pExvKiY/4q9J1Z/DhUrPcDspbsBWhTiyopvgTaF1bovBYuiqcOUPbrk0g3G/TumpMg/Aff6KduTbP4BhREaXUEq5iU/ZQO3F+WTM4KIP1WxcUzGkQiv/qG0IJQoACtzfwATCkHhoNkflX+r7FqFhye5unFJlbyvFgG3EubEjMI$NOLfzfUeeRtC+LaGC/LvZN4U+n4xXzsdiSO8olXkk6VQzgkJ4HbaTrdCNwbZLSIjkrbBFL4HL3HnzmSqZ0X7/DXJP1XL3aehNQ34X0/UVG0HG8sOmrjGOaCqx+pkIodryWO05qPCMDQIvGATvmyCuEJHU5iPA3JOfGZssoqQKD4');");
            stmt.executeUpdate("INSERT INTO `users`(`user_identifiant`,`name`,`lastName`,`mail`,`dob`,`pass` ) " +
                    "VALUES ('ClaudeBlmb','Claude', 'Balamba', 'claude.balamba@hei.yncrea.fr', '1996-03-25', '$argon2i$v=19$m=65536,t=5,p=1$1pExvKiY/4q9J1Z/DhUrPcDspbsBWhTiyopvgTaF1bovBYuiqcOUPbrk0g3G/TumpMg/Aff6KduTbP4BhREaXUEq5iU/ZQO3F+WTM4KIP1WxcUzGkQiv/qG0IJQoACtzfwATCkHhoNkflX+r7FqFhye5unFJlbyvFgG3EubEjMI$NOLfzfUeeRtC+LaGC/LvZN4U+n4xXzsdiSO8olXkk6VQzgkJ4HbaTrdCNwbZLSIjkrbBFL4HL3HnzmSqZ0X7/DXJP1XL3aehNQ34X0/UVG0HG8sOmrjGOaCqx+pkIodryWO05qPCMDQIvGATvmyCuEJHU5iPA3JOfGZssoqQKD4');");
            stmt.executeUpdate("INSERT INTO `users`(`user_identifiant`,`name`,`lastName`,`mail`,`dob`,`pass` ) " +
                    "VALUES ('BenoitMrcr','Benoît', 'Mercier', 'Benoît.Mercier@hei.yncrea.fr', '1997-01-01', '$argon2i$v=19$m=65536,t=5,p=1$1pExvKiY/4q9J1Z/DhUrPcDspbsBWhTiyopvgTaF1bovBYuiqcOUPbrk0g3G/TumpMg/Aff6KduTbP4BhREaXUEq5iU/ZQO3F+WTM4KIP1WxcUzGkQiv/qG0IJQoACtzfwATCkHhoNkflX+r7FqFhye5unFJlbyvFgG3EubEjMI$NOLfzfUeeRtC+LaGC/LvZN4U+n4xXzsdiSO8olXkk6VQzgkJ4HbaTrdCNwbZLSIjkrbBFL4HL3HnzmSqZ0X7/DXJP1XL3aehNQ34X0/UVG0HG8sOmrjGOaCqx+pkIodryWO05qPCMDQIvGATvmyCuEJHU5iPA3JOfGZssoqQKD4');");


         /* stmt.executeUpdate( "INSERT INTO `stalks`(`user_id`,`user_stalked`) VALUES (1,1));")
          stmt.executeUpdate( "INSERT INTO `stalks`(`user_id`,`user_stalked`) VALUES (1,1);)  " +
                    "VALUES (2,2));");
          stmt.executeUpdate( "INSERT INTO `stalks`(`user_id`,`user_stalked`) VALUES (1,1);)  " +
                    "VALUES (3,3));");
          stmt.executeUpdate( "INSERT INTO `stalks`(`user_id`,`user_stalked`) VALUES (1,1);)  " +
                    "VALUES (4,4));");
          stmt.executeUpdate( "INSERT INTO `stalks`(`user_id`,`user_stalked`) VALUES (1,1);)  " +
                    "VALUES (5,5));");
          stmt.executeUpdate( "INSERT INTO `stalks`(`user_id`,`user_stalked`) VALUES (1,1);)  " +
                    "VALUES (6,6));");
          stmt.executeUpdate( "INSERT INTO `stalks`(`user_id`,`user_stalked`) VALUES (1,1);)  " +
                    "VALUES (7,7));");
          stmt.executeUpdate( "INSERT INTO `stalks`(`user_id`,`user_stalked`) VALUES (1,1);)  " +
                    "VALUES (8,8));");
          stmt.executeUpdate( "INSERT INTO `stalks`(`user_id`,`user_stalked`) VALUES (1,1);)  " +
                    "VALUES (9,9));");
          stmt.executeUpdate( "INSERT INTO `stalks`(`user_id`,`user_stalked`) VALUES (1,1);)  " +
                    "VALUES (10,10));");
          stmt.executeUpdate( "INSERT INTO `stalks`(`user_id`,`user_stalked`) VALUES (1,1);)  " +
                    "VALUES (1,2));");
          stmt.executeUpdate( "INSERT INTO `stalks`(`user_id`,`user_stalked`) VALUES (1,1);)  " +
                    "VALUES (1,3));");
          stmt.executeUpdate( "INSERT INTO `stalks`(`user_id`,`user_stalked`) VALUES (1,1);)  " +
                    "VALUES (1,4));"); */



        }
    }

    @Test
    public void shouldListUsers() {
        // WHEN
        List<User> users = userDaoImpl.listUser();

        // THEN
        assertThat(users).hasSize(5);
        assertThat(users).extracting("identifiant", "name", "lastName", "mail", "dob", "pass").containsOnly(
                tuple("AdminHei", "Admin", "Hei", "admin.hei@hei.yncrea.fr", LocalDate.of(2018, 11, 5), "$argon2i$v=19$m=65536,t=5,p=1$1pExvKiY/4q9J1Z/DhUrPcDspbsBWhTiyopvgTaF1bovBYuiqcOUPbrk0g3G/TumpMg/Aff6KduTbP4BhREaXUEq5iU/ZQO3F+WTM4KIP1WxcUzGkQiv/qG0IJQoACtzfwATCkHhoNkflX+r7FqFhye5unFJlbyvFgG3EubEjMI$NOLfzfUeeRtC+LaGC/LvZN4U+n4xXzsdiSO8olXkk6VQzgkJ4HbaTrdCNwbZLSIjkrbBFL4HL3HnzmSqZ0X7/DXJP1XL3aehNQ34X0/UVG0HG8sOmrjGOaCqx+pkIodryWO05qPCMDQIvGATvmyCuEJHU5iPA3JOfGZssoqQKD4"),
                tuple("Xavierlnc", "Xavier", "Lencou", "xavier.lencou@hei.yncrea.fr", LocalDate.of(1997, 10, 23), "$argon2i$v=19$m=65536,t=5,p=1$1pExvKiY/4q9J1Z/DhUrPcDspbsBWhTiyopvgTaF1bovBYuiqcOUPbrk0g3G/TumpMg/Aff6KduTbP4BhREaXUEq5iU/ZQO3F+WTM4KIP1WxcUzGkQiv/qG0IJQoACtzfwATCkHhoNkflX+r7FqFhye5unFJlbyvFgG3EubEjMI$NOLfzfUeeRtC+LaGC/LvZN4U+n4xXzsdiSO8olXkk6VQzgkJ4HbaTrdCNwbZLSIjkrbBFL4HL3HnzmSqZ0X7/DXJP1XL3aehNQ34X0/UVG0HG8sOmrjGOaCqx+pkIodryWO05qPCMDQIvGATvmyCuEJHU5iPA3JOfGZssoqQKD4"),
                tuple("GregoireLdcq", "Gregoire", "Leducq", "gregoire.leducq@hei.yncrea.fr", LocalDate.of(1997, 12, 9), "$argon2i$v=19$m=65536,t=5,p=1$1pExvKiY/4q9J1Z/DhUrPcDspbsBWhTiyopvgTaF1bovBYuiqcOUPbrk0g3G/TumpMg/Aff6KduTbP4BhREaXUEq5iU/ZQO3F+WTM4KIP1WxcUzGkQiv/qG0IJQoACtzfwATCkHhoNkflX+r7FqFhye5unFJlbyvFgG3EubEjMI$NOLfzfUeeRtC+LaGC/LvZN4U+n4xXzsdiSO8olXkk6VQzgkJ4HbaTrdCNwbZLSIjkrbBFL4HL3HnzmSqZ0X7/DXJP1XL3aehNQ34X0/UVG0HG8sOmrjGOaCqx+pkIodryWO05qPCMDQIvGATvmyCuEJHU5iPA3JOfGZssoqQKD4"),
                tuple("ClaudeBlmb", "Claude", "Balamba", "claude.balamba@hei.yncrea.fr", LocalDate.of(1996, 3, 25), "$argon2i$v=19$m=65536,t=5,p=1$1pExvKiY/4q9J1Z/DhUrPcDspbsBWhTiyopvgTaF1bovBYuiqcOUPbrk0g3G/TumpMg/Aff6KduTbP4BhREaXUEq5iU/ZQO3F+WTM4KIP1WxcUzGkQiv/qG0IJQoACtzfwATCkHhoNkflX+r7FqFhye5unFJlbyvFgG3EubEjMI$NOLfzfUeeRtC+LaGC/LvZN4U+n4xXzsdiSO8olXkk6VQzgkJ4HbaTrdCNwbZLSIjkrbBFL4HL3HnzmSqZ0X7/DXJP1XL3aehNQ34X0/UVG0HG8sOmrjGOaCqx+pkIodryWO05qPCMDQIvGATvmyCuEJHU5iPA3JOfGZssoqQKD4"),
                tuple("BenoitMrcr", "Benoît", "Mercier", "Benoît.Mercier@hei.yncrea.fr", LocalDate.of(1997, 1, 1), "$argon2i$v=19$m=65536,t=5,p=1$1pExvKiY/4q9J1Z/DhUrPcDspbsBWhTiyopvgTaF1bovBYuiqcOUPbrk0g3G/TumpMg/Aff6KduTbP4BhREaXUEq5iU/ZQO3F+WTM4KIP1WxcUzGkQiv/qG0IJQoACtzfwATCkHhoNkflX+r7FqFhye5unFJlbyvFgG3EubEjMI$NOLfzfUeeRtC+LaGC/LvZN4U+n4xXzsdiSO8olXkk6VQzgkJ4HbaTrdCNwbZLSIjkrbBFL4HL3HnzmSqZ0X7/DXJP1XL3aehNQ34X0/UVG0HG8sOmrjGOaCqx+pkIodryWO05qPCMDQIvGATvmyCuEJHU5iPA3JOfGZssoqQKD4")
        );
    }


    @Test
    public void shouldGetUser() throws SQLException {
        //WHEN
        User user = userDaoImpl.getUser("AdminHei");

        //THEN
        assertThat(user).isNotNull();
        assertThat(user.getName()).isEqualTo("Admin");
    }


   @Test
    public void shouldGetUserById() throws SQLException {
        //WHEN
        User user = userDaoImpl.getUser(1);

        //THEN
        assertThat(user).isNotNull();
        assertThat(user.getIdentifiant()).isEqualTo("AdminHei");
        assertThat(user.getName()).isEqualTo("Admin");
    }



    @Test

    public void shouldAddUser() throws SQLException {
        //WHEN
        User newUser = new User("Odell", "Beckham Jr", "Obj", "Goat@hei.yncrea.fr", "1234", LocalDate.of(1993, 12, 25));
        userDaoImpl.addUser(newUser);

        //THEN
        try (Connection connection = DataSourceProvider.getDataSource().getConnection(); //Connexion a la base de données
             Statement stmt = connection.createStatement()) {
            try (ResultSet rs = stmt.executeQuery("SELECT * FROM users WHERE name = 'Odell'")) {
                assertThat(rs.next()).isTrue();
                assertThat(rs.getString("name")).isEqualTo("Odell");
                assertThat(rs.next()).isFalse();
            }
        }
    }

    @Test
    public void shouldDeleteUser() throws SQLException {
        //WHEN
        userDaoImpl.delUser(1); // ( Probleme de correspondance entre le new User et son id généré automatiquement )
        List<User> users = userDaoImpl.listUser();

        //THEN
        try (Connection connection = DataSourceProvider.getDataSource().getConnection(); //Connexion a la base de données
             Statement stmt = connection.createStatement()) {
            try (ResultSet rs = stmt.executeQuery("SELECT * FROM users")) {
                assertThat(users).hasSize(4);
            }
        }
    }


     @Test
    public void shouldUpdateUser() throws SQLException { // Test Fonctionnel
         //WHEN
         User newUser = new User("Amaury", "Balamba", "Claudeblmb", "claude.balamba@hei.yncrea.fr", "$argon2i$v=19$m=65536,t=5,p=1$1pExvKiY/4q9J1Z/DhUrPcDspbsBWhTiyopvgTaF1bovBYuiqcOUPbrk0g3G/TumpMg/Aff6KduTbP4BhREaXUEq5iU/ZQO3F+WTM4KIP1WxcUzGkQiv/qG0IJQoACtzfwATCkHhoNkflX+r7FqFhye5unFJlbyvFgG3EubEjMI$NOLfzfUeeRtC+LaGC/LvZN4U+n4xXzsdiSO8olXkk6VQzgkJ4HbaTrdCNwbZLSIjkrbBFL4HL3HnzmSqZ0X7/DXJP1XL3aehNQ34X0/UVG0HG8sOmrjGOaCqx+pkIodryWO05qPCMDQIvGATvmyCuEJHU5iPA3JOfGZssoqQKD4", LocalDate.of(1996, 03, 25));
         userDaoImpl.updateUser("Claudeblmb", newUser);

         //THEN
         try (Connection connection = DataSourceProvider.getDataSource().getConnection(); //Connexion a la base de données
              Statement stmt = connection.createStatement()) {
             try (ResultSet rs = stmt.executeQuery("SELECT * FROM users WHERE mail = 'claude.balamba@hei.yncrea.fr'")) {
                 assertThat(rs.next()).isTrue();
                 assertThat(rs.getString("name")).isEqualTo("Amaury");
                 assertThat(rs.getString("name")).isNotEqualTo("Claude");
                 assertThat(rs.getString("lastname")).isEqualTo("Balamba");
                 assertThat(rs.next()).isFalse();
            }
         }
    }


   /* @Test
        public void shouldGetStalksOfUser() {

         //WHEN
        ArrayList<User> stalks = userDaoImpl.ArrayList();



         //THEN
        asser



    }
*/




}



