package OOP.Tests.tzlil.utils;

import org.junit.Assert;
import org.junit.Before;

import java.util.Collection;
import java.util.Collections;
import java.util.function.Function;

import OOP.Provided.CartelDeNachos;
import OOP.Provided.CasaDeBurrito;
import OOP.Provided.Profesor;
import OOP.Solution.CartelDeNachosImpl;

public class CartelDeNachosImplBaseTest
        extends BaseTest {

    protected CartelDeNachos cartel;

    @Before
    public void initMembers() {
        cartel = new CartelDeNachosImpl();
    }

    protected Profesor addProfesorWithId(int id) {
        try {
            return cartel.joinCartel(id, String.valueOf(counter++));
        } catch (Profesor.ProfesorAlreadyInSystemException e) {
            throw new UnsupportedOperationException(
                    "Cannot add profesor with id \"" + id + "\" twice.");
        }
    }

    protected Profesor addRandomProfesor() {
        return addProfesorWithId(counter);
    }

    protected CasaDeBurrito addCasaWithId(int id) {
        try {
            int currentCounter = counter;
            counter++;
            return cartel.addCasaDeBurrito(id, String.valueOf(currentCounter), counter, Collections.emptySet());
        } catch (CasaDeBurrito.CasaDeBurritoAlreadyInSystemException e) {
            throw new UnsupportedOperationException(
                    "Cannot add casa with id \"" + id + "\" twice.");
        }
    }

    protected CasaDeBurrito addRandomCasa() {
        return addCasaWithId(counter);
    }

    protected Profesor getProfesorWrapper(int id) {
        try {
            return cartel.getProfesor(id);
        } catch (Profesor.ProfesorNotInSystemException e) {
            throw new IllegalArgumentException("Profesor with id: \"" + id + "\" does not exist.");
        }
    }

    protected CasaDeBurrito getCasaWrapper(int id) {
        try {
            return cartel.getCasaDeBurrito(id);
        } catch (CasaDeBurrito.CasaDeBurritoNotInSystemException e) {
            throw new IllegalArgumentException("Casa with id: \"" + id + "\" does not exist.");
        }
    }

    protected CartelDeNachos connectRandomProfesors() {
        Profesor profesor1 = addProfesorWithId(1);
        Profesor profesor2 = addProfesorWithId(2);
        try {
            return cartel.addConnection(profesor1, profesor2);
        } catch (Profesor.ProfesorNotInSystemException | Profesor.ConnectionAlreadyExistsException | Profesor.SameProfesorException e) {
            throw new UnsupportedOperationException("Must connect two in-the-system distinct profesors.");
        }
    }

    protected void assertProfesorHasFriend(int profesor, int friend)
            throws Profesor.ProfesorNotInSystemException {
        Assert.assertTrue(cartel.getProfesor(profesor)
                .getFriends().contains(getNewProfesorWithId(friend)));
    }

    protected Collection<CasaDeBurrito> favoritesByRatingWrapper(Profesor profesor) {
        try {
            return cartel.favoritesByRating(profesor);
        } catch (Profesor.ProfesorNotInSystemException e) {
            throw new IllegalArgumentException("Profesor not in the system");
        }
    }

    protected Collection<CasaDeBurrito> favoritesByDistWrapper(Profesor profesor) {
        try {
            return cartel.favoritesByDist(profesor);
        } catch (Profesor.ProfesorNotInSystemException e) {
            throw new IllegalArgumentException("Profesor not in the system");
        }
    }

    protected void assertFavoritesNotAliased(Function<Profesor, Collection<CasaDeBurrito>> function, Profesor profesor) {
        assertCollectionSupplierNotAliased(() -> function.apply(profesor));
    }
}
