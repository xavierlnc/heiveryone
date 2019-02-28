package dao.impl;

import entites.Post;
import entites.User;
import org.junit.Before;
import org.junit.Test;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

public class PostDaoTestCase {

    private PostDaoimpl postDaoimpl = new PostDaoimpl();

    @Before
    public void initDb() throws Exception {
        try (Connection connection = DataSourceProvider.getDataSource().getConnection();
             Statement stmt = connection.createStatement()) {
            stmt.executeUpdate("DELETE FROM users;");
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
            stmt.executeUpdate("DELETE FROM posts;");
            stmt.executeUpdate("INSERT INTO `posts`(`id`,`user_id`,`date_of_post`,`time_of_post`,`texte` ) " +
                    "VALUES ('1','1','2018-11-19','09:14:41','Coucou tout le monde !');");
            stmt.executeUpdate("INSERT INTO `posts`(`id`,`user_id`,`date_of_post`,`time_of_post`,`texte` ) " +
                    "VALUES ('2','4','2018-12-19','09:16:41','Hello heiveryone');");
            stmt.executeUpdate("INSERT INTO `posts`(`id`,`user_id`,`date_of_post`,`time_of_post`,`texte` ) " +
                    "VALUES ('3','2','2018-08-19','09:17:41','Hola todos');");
            stmt.executeUpdate("INSERT INTO `posts`(`id`,`user_id`,`date_of_post`,`time_of_post`,`texte` ) " +
                    "VALUES ('4','3','2018-11-21','09:18:40','Bon appétit');");
            stmt.executeUpdate("DELETE FROM stalks;");
            stmt.executeUpdate("INSERT INTO `stalks`(`user_id`,`user_stalked`) " +
                    "VALUES ('1','1');");
            stmt.executeUpdate("INSERT INTO `stalks`(`user_id`,`user_stalked`) " +
                    "VALUES ('1','2');");
            stmt.executeUpdate("INSERT INTO `stalks`(`user_id`,`user_stalked`) " +
                    "VALUES ('1','3');");
        }
    }



    @Test
    public void shouldGetListPostOfUser() {
        //WHEN
        User user = new User(
                1,
                "Admin",
                "Hei",
                "AdminHei",
                "admin.hei@hei.yncrea.fr",
                "$argon2i$v=19$m=65536,t=5,p=1$1pExvKiY/4q9J1Z/DhUrPcDspbsBWhTiyopvgTaF1bovBYuiqcOUPbrk0g3G/TumpMg/Aff6KduTbP4BhREaXUEq5iU/ZQO3F+WTM4KIP1WxcUzGkQiv/qG0IJQoACtzfwATCkHhoNkflX+r7FqFhye5unFJlbyvFgG3EubEjMI$NOLfzfUeeRtC+LaGC/LvZN4U+n4xXzsdiSO8olXkk6VQzgkJ4HbaTrdCNwbZLSIjkrbBFL4HL3HnzmSqZ0X7/DXJP1XL3aehNQ34X0/UVG0HG8sOmrjGOaCqx+pkIodryWO05qPCMDQIvGATvmyCuEJHU5iPA3JOfGZssoqQKD4",
                LocalDate.of(2018,11,05),
                "Fan de HEI",
                "favicon.ico",
                false,
                "clair");
        List<Post> posts = postDaoimpl.getListPostOfUser(user);

        //THEN
        assertThat(posts).hasSize(1);
        assertThat(posts).extracting("id","author","dateOfPublication","timeOfPublication","texte").containsOnly(
                tuple(1,user,LocalDate.of(2018,11,19), Time.valueOf("09:14:41"),"Coucou tout le monde !"));

    }


    @Test
    public void shouldGetListPostOfStalks(){
        //WHEN
        User user = new User(
                1,
                "Admin",
                "Hei",
                "AdminHei",
                "admin.hei@hei.yncrea.fr",
                "$argon2i$v=19$m=65536,t=5,p=1$1pExvKiY/4q9J1Z/DhUrPcDspbsBWhTiyopvgTaF1bovBYuiqcOUPbrk0g3G/TumpMg/Aff6KduTbP4BhREaXUEq5iU/ZQO3F+WTM4KIP1WxcUzGkQiv/qG0IJQoACtzfwATCkHhoNkflX+r7FqFhye5unFJlbyvFgG3EubEjMI$NOLfzfUeeRtC+LaGC/LvZN4U+n4xXzsdiSO8olXkk6VQzgkJ4HbaTrdCNwbZLSIjkrbBFL4HL3HnzmSqZ0X7/DXJP1XL3aehNQ34X0/UVG0HG8sOmrjGOaCqx+pkIodryWO05qPCMDQIvGATvmyCuEJHU5iPA3JOfGZssoqQKD4",
                LocalDate.of(2018,11,05),
                "Fan de HEI",
                "favicon.ico",
                false,
                "clair");
        List<Post> posts = postDaoimpl.getListPostOfStalks(user);

        //THEN
        assertThat(posts).hasSize(3);
        assertThat(posts).extracting("id","dateOfPublication","timeOfPublication","texte").containsOnly(
                tuple(4,LocalDate.of(2018,11,21),Time.valueOf("09:18:40"),"Bon appétit"),
                tuple(1,LocalDate.of(2018,11,19),Time.valueOf("09:14:41"),"Coucou tout le monde !"),
                tuple(3,LocalDate.of(2018,8,19),Time.valueOf("09:17:41"),"Hola todos")

        );


    }


    @Test
    public void shouldAddPostOfUser() throws SQLException {
        //WHEN
        User user = new User(
                5,
                "Admini",
                "Hei",
                "AdminiHei",
                "admini.hei@hei.yncrea.fr",
                "$argon2i$v=19$m=65536,t=5,p=1$1pExvKiY/4q9J1Z/DhUrPcDspbsBWhTiyopvgTaF1bovBYuiqcOUPbrk0g3G/TumpMg/Aff6KduTbP4BhREaXUEq5iU/ZQO3F+WTM4KIP1WxcUzGkQiv/qG0IJQoACtzfwATCkHhoNkflX+r7FqFhye5unFJlbyvFgG3EubEjMI$NOLfzfUeeRtC+LaGC/LvZN4U+n4xXzsdiSO8olXkk6VQzgkJ4HbaTrdCNwbZLSIjkrbBFL4HL3HnzmSqZ0X7/DXJP1XL3aehNQ34X0/UVG0HG8sOmrjGOaCqx+pkIodryWO05qPCMDQIvGATvmyCuEJHU5iPA3JOfGZssoqQKD4",
                LocalDate.of(2018,11,05),
                "Fan de HEI",
                "favicon.ico",
                false,
                "clair");
        Post post = new Post(
                user,
                LocalDate.of(2018,11,19),
                Time.valueOf("09:14:41"),
                "Salut tout le monde !");
        postDaoimpl.addPostOfUser(post,user);

        //THEN
        try (Connection connection = DataSourceProvider.getDataSource().getConnection();
             Statement stmt = connection.createStatement()) {
            try (ResultSet rs = stmt.executeQuery("SELECT * FROM posts WHERE user_id = 5")) {
                assertThat(rs.next()).isTrue();
                assertThat(rs.getString("texte")).isEqualTo("Salut tout le monde !");
                assertThat(rs.next()).isFalse();
            }
        }
    }


    @Test
    public void shouldGetPost(){
        //WHEN
        Post post = postDaoimpl.getPost(1);

        //THEN
        assertThat(post).isNotNull();
        assertThat(post.getDateOfPublication()).isEqualTo(LocalDate.of(2018,11,19));
        assertThat(post.getTimeOfPublication()).isEqualTo(Time.valueOf("09:14:41"));
        assertThat(post.getTexte()).isEqualTo("Coucou tout le monde !");
        assertThat(post.getId()).isEqualTo(1);
    }


    @Test
    public void shouldDelPost() throws SQLException {
        //WHEN
        postDaoimpl.delPost(1);

        //THEN
        try (Connection connection = DataSourceProvider.getDataSource().getConnection();
             Statement stmt = connection.createStatement()) {
            try (ResultSet rs = stmt.executeQuery("SELECT * FROM posts WHERE user_id = 1")) {
                assertThat(rs.next()).isFalse();
            }
        }
    }

}
