/*package services.impl;


import Exceptions.IdentifiantAlreadyExistsException;
import Exceptions.IdentifiantOrPassIncorrectException;
import Exceptions.InputsEmptyException;
import Exceptions.UserDeletesHimselfException;
import dao.UserDAO;
import dao.impl.UserDaoimpl;
import entites.User;
import javafx.beans.binding.When;
import manager.UserLibrary;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.cglib.core.Local;
import org.mockito.runners.MockitoJUnitRunner;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.fail;


@RunWith(MockitoJUnitRunner.class)

public class UserServicesTestCase {

    @Mock
    private UserDAO userDao;

    @InjectMocks
    private UserLibrary userLibraryMock;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);

    }

    @Test
    public void shouldAddUser() throws SQLException, IdentifiantAlreadyExistsException {

        //GIVEN


        assertThatExceptionOfType(InputsEmptyException.class).isThrownBy(() -> {
            userLibraryMock.addUser("", "", "", "", "", "");

        })
                .withMessage("Les champs sont vides");
    }



    @Test
    public void shouldAddUserAgain() throws SQLException, IdentifiantAlreadyExistsException, InputsEmptyException {

        userLibraryMock.addUser("Neymar", "Jr", "2018-11-12", "neymar@hei.fr", "1", "0000");

        //THEN
        //Mockito.verify(userLibraryMock.addUser(Mockito.any()));


        assertThatExceptionOfType(IdentifiantAlreadyExistsException.class).isThrownBy(() -> {
            userLibraryMock.addUser("Odell", "Beckham", "2013-11-12", "odell@hei.fr", "1", "1234");

        })
                .withMessage("L'identifiant existe deja");
    }




@Test(expected = InputsEmptyException.class)

public void shouldAddUserAgain() throws SQLException, InputsEmptyException, IdentifiantAlreadyExistsException {


    //WHEN
    userLibraryMock.addUser("","","","","","");
   //THEN

    fail("Erreur");
}


    @Test

    public void shouldDelUser() throws SQLException, IdentifiantAlreadyExistsException, UserDeletesHimselfException {

        //GIVEN
        int CurrentId = 2;
        int TargetId = 1;

        //WHEN

        userLibraryMock.delUser(2, 1);

        //THEN

        Mockito.verify(userDao).delUser(Mockito.eq(TargetId));


    }

    @Test(expected = UserDeletesHimselfException.class)

    public void shouldDelUserAgain() throws SQLException, IdentifiantAlreadyExistsException, UserDeletesHimselfException {

        //GIVEN
        int CurrentId = 1;
        int TargetId = 1;

        //WHEN

        userLibraryMock.delUser(1, 1);

        //THEN

        fail("Vous vous supprimez vous-mêmes");


    }


    @Test
    public void shouldGetUser() throws SQLException {

        //GIVEN

        int usrId = 1;

        //WHEN

        userLibraryMock.getUser(usrId);

        //THEN

        Mockito.verify(userDao).getUser(usrId);


    }



@Test
public void shouldGetUSerAgain() throws SQLException {
    //GIVEN

    String identifiant = "1";

    //WHEN

    userLibraryMock.getUser(identifiant);

    //THEN

    Mockito.verify(userDao).getUser(identifiant);

}

@Test
public void shouldListUser(){

    //WHEN
    userLibraryMock.listUser();

    //THEN
    Mockito.verify(userDao).listUser();

}

@Test
public void  shouldUpdateUser(){
    //GIVEN
    String identifiant="1";
    User newUser = new User("Cristiano", "Ronaldo", "1", "cr7@hei.fr", "cr7",LocalDate.of(1986,12,11));

    //WHEN
    userLibraryMock.updateUser(identifiant,newUser);

    //THEN
    Mockito.verify(userDao).updateUser(identifiant,newUser);
}

@Test
public void shouldUpdateUserStatus(){
    //GIVEN
    int id=1;
    String status="Salut";

    //WHEN
    userLibraryMock.updateUserStatus(id,status);

    //THEN
    Mockito.verify(userDao).updateUserStatus(id,status);
}

@Test
public void shouldSearchUser(){
    //GIVEN
    String searchChaine = "search";

    //WHEN
    userLibraryMock.searchUser(searchChaine);

    //THEN
    Mockito.verify(userDao).searchUser(searchChaine);
}

@Test
public void shouldGetStalksOfUser(){
    //GIVEN
    User newUser = new User("Kylian", "Mbappe", "1", "KM7@hei.fr", "km7",LocalDate.of(1998,12,11));

    //WHEN
    userLibraryMock.getStalksOfUser(newUser);

    //THEN
    Mockito.verify(userDao).getStalksOfUser(newUser);
}


@Test
public void shouldAddStalksToUser(){
    //GIVEN
    int currentId=1;
    int targetId=2;

    //WHEN
    userLibraryMock.addStalkToUser(currentId,targetId);

    //THEN
    Mockito.verify(userDao).addStalkToUser(currentId,targetId);
}


    @Test
    public void shouldDelStalksOfUser(){
        //GIVEN
        int currentId=1;
        int targetId=2;

        //WHEN
        userLibraryMock.delStalkOfUser(currentId,targetId);

        //THEN
        Mockito.verify(userDao).delStalkOfUser(currentId,targetId);
    }


    @Test
    public void shouldPropositionOfUser(){
        //GIVEN
        User newUser = new User("Karim", "Benzema", "1", "KB9@hei.fr", "kb9",LocalDate.of(1986,10,11));
        int nbElements = 2;

        //WHEN
        userLibraryMock.propositionOfUser(newUser,nbElements);

        //THEN
        Mockito.verify(userDao).propositionOfUser(newUser,nbElements);
    }


    @Test
    public void shouldVerifyUserInfos() throws SQLException, IdentifiantOrPassIncorrectException {
        //GIVEN
        User newUser = null;

        Assertions.assertThatExceptionOfType(IdentifiantOrPassIncorrectException.class)
                .isThrownBy(() -> {
            userLibraryMock.verifyUserInfos(null,null);
        })
        .withMessage("Utilisateur introuvable");
    }

}
*/