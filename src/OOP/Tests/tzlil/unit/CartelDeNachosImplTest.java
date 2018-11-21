package OOP.Tests.tzlil.unit;

import org.junit.Assert;
import org.junit.Test;

import java.util.Collections;

import OOP.Provided.CartelDeNachos;
import OOP.Provided.CasaDeBurrito;
import OOP.Provided.Profesor;
import OOP.Tests.tzlil.utils.CartelDeNachosImplBaseTest;

public class CartelDeNachosImplTest
        extends CartelDeNachosImplBaseTest {

    @Test
    public void joinCartel_fail_alreadyInSystem()
            throws Profesor.ProfesorAlreadyInSystemException {
        cartel.joinCartel(1, "1");
        expectException(Profesor.ProfesorAlreadyInSystemException.class);
        cartel.joinCartel(1, "1");
    }

    @Test
    public void joinCartel_returnNonNull() {
        Assert.assertNotNull(addRandomProfesor());
    }

    @Test
    public void addCasaDeBurrito_fail_alreadyInSystem()
            throws CasaDeBurrito.CasaDeBurritoAlreadyInSystemException {
        cartel.addCasaDeBurrito(1, "1", 1, Collections.emptySet());
        expectException(CasaDeBurrito.CasaDeBurritoAlreadyInSystemException.class);
        cartel.addCasaDeBurrito(1, "1", 1, Collections.emptySet());
    }

    @Test
    public void addCasaDeBurrito_returnNonNull() {
        Assert.assertNotNull(addRandomCasa());
    }

    @Test
    public void registeredProfesores_empty() {
        Assert.assertTrue(cartel.registeredProfesores().isEmpty());
    }

    @Test
    public void registeredProfesores_success() {
        addProfesorWithId(1);
        addProfesorWithId(2);
        addProfesorWithId(3);
        assertProfesorCollectionContainsIds(cartel.registeredProfesores(), 1, 2, 3);
    }

    @Test
    public void registeredProfesores_notAliased() {
        addProfesorWithId(1);
        addProfesorWithId(2);
        assertCollectionSupplierNotAliased(cartel::registeredProfesores);
    }

    @Test
    public void registeredCasasDeBurrito_empty() {
        Assert.assertTrue(cartel.registeredCasasDeBurrito().isEmpty());
    }

    @Test
    public void registeredCasasDeBurrito_success() {
        addCasaWithId(1);
        addCasaWithId(2);
        addCasaWithId(3);
        assertCasasCollectionContainsIds(cartel.registeredCasasDeBurrito(), 1, 2, 3);
    }

    @Test
    public void registeredCasasDeBurrito_notAliased() {
        addCasaWithId(1);
        addCasaWithId(2);
        assertCollectionSupplierNotAliased(cartel::registeredCasasDeBurrito);
    }

    @Test
    public void getProfesor_fail_notInTheSystem()
            throws Profesor.ProfesorNotInSystemException {
        expectException(Profesor.ProfesorNotInSystemException.class);
        cartel.getProfesor(1);
    }

    @Test
    public void getCasaDeBurrito_fail_notInTheSystem()
            throws CasaDeBurrito.CasaDeBurritoNotInSystemException {
        expectException(CasaDeBurrito.CasaDeBurritoNotInSystemException.class);
        cartel.getCasaDeBurrito(1);
    }

    @Test
    public void addConnection_returnNonNull() {
        Assert.assertNotNull(connectRandomProfesors());
    }

    @Test
    public void addConnection_success()
            throws Profesor.ProfesorNotInSystemException, Profesor.SameProfesorException,
                   Profesor.ConnectionAlreadyExistsException {
        cartel.addConnection(addProfesorWithId(1), addProfesorWithId(2));
        assertProfesorHasFriend(1, 2);
        assertProfesorHasFriend(2, 1);
    }

    @Test
    public void addConnection_fail_firstNotInTheSystem()
            throws Profesor.ProfesorNotInSystemException, Profesor.SameProfesorException,
                   Profesor.ConnectionAlreadyExistsException {
        expectException(Profesor.ProfesorNotInSystemException.class);
        cartel.addConnection(getNewProfesorWithId(1), addProfesorWithId(2));
    }

    @Test
    public void addConnection_fail_secondNotInTheSystem()
            throws Profesor.ProfesorNotInSystemException, Profesor.SameProfesorException,
                   Profesor.ConnectionAlreadyExistsException {
        expectException(Profesor.ProfesorNotInSystemException.class);
        cartel.addConnection(addProfesorWithId(1), getNewProfesorWithId(2));
    }

    @Test
    public void addConnection_fail_bothNotInTheSystem()
            throws Profesor.ProfesorNotInSystemException, Profesor.SameProfesorException,
                   Profesor.ConnectionAlreadyExistsException {
        expectException(Profesor.ProfesorNotInSystemException.class);
        cartel.addConnection(getNewProfesorWithId(1), getNewProfesorWithId(2));
    }

    @Test
    public void addConnection_fail_sameProfesor()
            throws Profesor.ProfesorNotInSystemException, Profesor.SameProfesorException,
                   Profesor.ConnectionAlreadyExistsException {
        expectException(Profesor.SameProfesorException.class);
        cartel.addConnection(addProfesorWithId(1), getNewProfesorWithId(1));
    }

    @Test
    public void addConnection_fail_connectionAlreadyExists()
            throws Profesor.ProfesorNotInSystemException, Profesor.SameProfesorException,
                   Profesor.ConnectionAlreadyExistsException {
        cartel.addConnection(addProfesorWithId(1), addProfesorWithId(2));
        expectException(Profesor.ConnectionAlreadyExistsException.class);
        cartel.addConnection(getNewProfesorWithId(1), getNewProfesorWithId(2));
    }

    @Test
    public void favoritesByRating_fail_notInTheSystem()
            throws Profesor.ProfesorNotInSystemException {
        expectException(Profesor.ProfesorNotInSystemException.class);
        cartel.favoritesByRating(getNewProfesorWithId(1));
    }

    @Test
    public void favoritesByDist_fail_notInTheSystem()
            throws Profesor.ProfesorNotInSystemException {
        expectException(Profesor.ProfesorNotInSystemException.class);
        cartel.favoritesByDist(getNewProfesorWithId(1));
    }

    @Test
    public void getRecommendation_fail_profesorNotInTheSystem()
            throws Profesor.ProfesorNotInSystemException,
                   CasaDeBurrito.CasaDeBurritoNotInSystemException,
                   CartelDeNachos.ImpossibleConnectionException {
        CasaDeBurrito casa = addCasaWithId(1);
        expectException(Profesor.ProfesorNotInSystemException.class);
        Assert.assertFalse(cartel.getRecommendation(getNewProfesorWithId(1), casa, 0));
    }

    @Test
    public void getRecommendation_fail_casaNotInTheSystem()
            throws Profesor.ProfesorNotInSystemException,
                   CasaDeBurrito.CasaDeBurritoNotInSystemException,
                   CartelDeNachos.ImpossibleConnectionException {
        Profesor profesor = addProfesorWithId(1);
        expectException(CasaDeBurrito.CasaDeBurritoNotInSystemException.class);
        Assert.assertFalse(cartel.getRecommendation(profesor, getNewCasaWithId(1), 0));
    }

    @Test
    public void getRecommendation_fail_negativeT()
            throws Profesor.ProfesorNotInSystemException,
                   CasaDeBurrito.CasaDeBurritoNotInSystemException,
                   CartelDeNachos.ImpossibleConnectionException {
        Profesor profesor = addProfesorWithId(1);
        CasaDeBurrito casa = addCasaWithId(1);
        expectException(CartelDeNachos.ImpossibleConnectionException.class);
        Assert.assertFalse(cartel.getRecommendation(profesor, casa, -1));
    }

    @Test
    public void getRecommendation_false_t0HasNoFavorites()
            throws Profesor.ProfesorNotInSystemException,
                   CasaDeBurrito.CasaDeBurritoNotInSystemException,
                   CartelDeNachos.ImpossibleConnectionException {
        Profesor profesor = addProfesorWithId(1);
        CasaDeBurrito casa = addCasaWithId(1);
        Assert.assertFalse(cartel.getRecommendation(profesor, casa, 0));
    }

    @Test
    public void getRecommendation_true_t0InFavorites()
            throws Profesor.UnratedFavoriteCasaDeBurritoException,
                   Profesor.ProfesorNotInSystemException,
                   CasaDeBurrito.CasaDeBurritoNotInSystemException,
                   CartelDeNachos.ImpossibleConnectionException, CasaDeBurrito.RateRangeException {
        Profesor profesor = addProfesorWithId(1);
        CasaDeBurrito casa = addCasaWithId(1);
        casa.rate(profesor, 5);
        profesor.favorite(casa);
        Assert.assertTrue(cartel.getRecommendation(profesor, casa, 0));
    }

    @Test
    public void getMostPopularRestaurantsIds_emptySystem() {
        Assert.assertTrue(cartel.getMostPopularRestaurantsIds().isEmpty());
    }

    @Test
    public void getMostPopularRestaurantsIds_noRatings() {
        addCasaWithId(2);
        addCasaWithId(1);
        addCasaWithId(3);
        assertCollectionByOrder(cartel.getMostPopularRestaurantsIds(), 1, 2, 3);
    }

    @Test
    public void toString_empty() {
        String expectedString = "Registered profesores: .\n" +
                "Registered casas de burrito: .\n" +
                "Profesores:\n" +
                "End profesores.";
        Assert.assertEquals(expectedString, cartel.toString());
    }

    @Test
    public void toString_noConnections() {
        addProfesorWithId(1);
        String expectedString = "Registered profesores: 1.\n" +
                "Registered casas de burrito: .\n" +
                "Profesores:\n" +
                "1 -> [].\n" +
                "End profesores.";
        Assert.assertEquals(expectedString, cartel.toString());
    }

    @Test
    public void toString_success()
            throws Profesor.ProfesorNotInSystemException, Profesor.SameProfesorException,
                   Profesor.ConnectionAlreadyExistsException {
        Profesor profesor2 = addProfesorWithId(2);
        Profesor profesor1 = addProfesorWithId(1);
        Profesor profesor4 = addProfesorWithId(4);
        addProfesorWithId(3);

        addCasaWithId(6);
        addCasaWithId(5);

        cartel.addConnection(profesor1, profesor2);
        cartel.addConnection(profesor1, profesor4);

        String expectedString = "Registered profesores: 1, 2, 3, 4.\n" +
                "Registered casas de burrito: 5, 6.\n" +
                "Profesores:\n" +
                "1 -> [2, 4].\n" +
                "2 -> [1].\n" +
                "3 -> [].\n" +
                "4 -> [1].\n" +
                "End profesores.";

        Assert.assertEquals(expectedString, cartel.toString());
    }
}
