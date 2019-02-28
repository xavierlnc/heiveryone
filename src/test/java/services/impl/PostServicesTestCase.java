package services.impl;

import Exceptions.InputsEmptyException;
import dao.PostDAO;
import dao.UserDAO;
import entites.User;
import manager.PostLibrary;
import manager.UserLibrary;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

@RunWith(MockitoJUnitRunner.class)

public class PostServicesTestCase {

    @Mock
    private PostDAO postDao;

    @InjectMocks
    private PostLibrary postLibraryMock;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);

    }

    @Test
    public void shouldGetListPostOfUser(){

        //GIVEN
        User newUser = new User("Kylian", "Mbappe", "1", "KM7@hei.fr", "km7", LocalDate.of(1998,12,11));

        //WHEN
        postLibraryMock.getListPostOfUser(newUser);

        //THEN
        Mockito.verify(postDao).getListPostOfUser(newUser);

    }


    @Test
    public void shouldGetListPostOfStalks(){

        //GIVEN
        User newUser = new User("Kylian", "Mbappe", "1", "KM7@hei.fr", "km7", LocalDate.of(1998,12,11));

        //WHEN
        postLibraryMock.getListPostOfStalks(newUser);

        //THEN
        Mockito.verify(postDao).getListPostOfStalks(newUser);

    }

    @Test
    public void shouldGetPost(){
        //GIVEN
        int postId = 1;

        //WHEN
        postLibraryMock.getPost(postId);

        //THEN
        Mockito.verify(postDao).getPost(postId);
    }


    @Test
    public void shouldDelPost(){
        //GIVEN
        int postId = 1;

        //WHEN
        postLibraryMock.delPost(postId);

        //THEN
        Mockito.verify(postDao).delPost(postId);
    }


    @Test
    public void shouldAddPostOfUser(){

        User newUser = new User("Kylian", "Mbappe", "1", "KM7@hei.fr", "km7",LocalDate.of(1998,12,11));

        assertThatExceptionOfType(InputsEmptyException.class).isThrownBy(() -> {
            postLibraryMock.addPostOfUser(newUser, LocalDate.of(2018,11,12), Time.valueOf("09:12:31"), "");

        })
                .withMessage("Le message du post est vide");
    }

}
