package OOP.Tests.tzlil.utils;

import org.junit.Before;

import java.util.Collection;

import OOP.Provided.CasaDeBurrito;
import OOP.Provided.Profesor;
import OOP.Solution.ProfesorImpl;

public class ProfesorImplBaseTest
        extends BaseTest {

    protected Profesor profesor;

    @Before
    public void initDefaultProfessor() {
        setDefaultProfessor();
    }

    protected void assertCollectionContainsIds(int... ids) {
        assertProfesorCollectionContainsIds(profesor.getFriends(), ids);
    }

    protected void assertFavoritesContainsIds(int... ids) {
        assertFavoritesContainsIds(profesor.favorites(), ids);
    }

    protected void assertFavoritesContainsIds(Collection<CasaDeBurrito> collection, int... ids) {
        assertCollectionContains(collection, fromIdsToCasas(ids));
    }

    protected void assertFavoritesContainsIdsByOrder(Collection<CasaDeBurrito> collection, int... ids) {
        assertCollectionByOrder(collection, fromIdsToCasas(ids));
    }

    private CasaDeBurrito[] fromIdsToCasas(int... ids) {
        CasaDeBurrito[] casas = new CasaDeBurrito[ids.length];
        for (int i = 0; i < ids.length; i++) {
            casas[i] = getNewCasaWithId(ids[i]);
        }
        return casas;
    }

    protected Profesor getDuplicateProfesor() {
        setProfesorWithId(1);
        return getNewProfesorWithId(1);
    }

    protected Profesor getDuplicateDerivedProfesor() {
        setProfesorWithId(1);
        return new ProfesorImpl(1, "1") {
        };
    }

    protected Profesor getDifferentProfesor() {
        setProfesorWithId(1);
        return getNewProfesorWithId(2);
    }

    private void setDefaultProfessor() {
        profesor = getNewRandomProfesor();
    }

    protected void setProfesorWithId(int id) {
        profesor = getNewProfesorWithId(id);
    }

    protected void setProfesor(int id, String name) {
        profesor = getNewProfessor(id, name);
    }

    protected CasaDeBurrito rateCasaWrapper(CasaDeBurrito casa, int rate) {
        return rateCasaWrapper(casa, profesor, rate);
    }

    protected CasaDeBurrito getRatedRandomCasa() {
        CasaDeBurrito casa = getNewRandomCasa();
        return rateCasaWrapper(casa, profesor, 5);
    }

    protected CasaDeBurrito getRatedCasaWithId(int id) {
        CasaDeBurrito casa = getNewCasaWithId(id);
        return rateCasaWrapper(casa, profesor, 5);
    }

    protected CasaDeBurrito getRatedCasaWithName(String name) {
        CasaDeBurrito casa = getNewCasaWithName(name);
        return rateCasaWrapper(casa, profesor, 5);
    }

    protected Profesor favoriteRandomCasa() {
        return favoriteCasaWrapper(profesor, getRatedRandomCasa());
    }

    protected void favoriteCasas(CasaDeBurrito... casas) {
        for (CasaDeBurrito casa : casas) {
            favoriteCasaWrapper(casa);
        }
    }

    protected void favoriteCasasWithIds(int... ids) {
        for (int id : ids) {
            favoriteCasaWrapper(getRatedCasaWithId(id));
        }
    }

    protected void favoriteCasasWithNames(String... names) {
        for (String name : names) {
            favoriteCasaWrapper(getRatedCasaWithName(name));
        }
    }

    protected Profesor favoriteCasaWrapper(CasaDeBurrito casa) {
        return favoriteCasaWrapper(profesor, casa);
    }

    protected Profesor addRandomFriend() {
        return addFriendWrapper(getDifferentProfesor());
    }

    protected Profesor addFriendWrapper(Profesor friend) {
        return addFriendWrapper(profesor, friend);
    }

    protected void addFriendsWithIds(int... ids) {
        for (int id : ids) {
            addFriendWrapper(getNewProfesorWithId(id));
        }
    }
}
