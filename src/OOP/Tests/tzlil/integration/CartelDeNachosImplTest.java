package OOP.Tests.tzlil.integration;

import org.junit.Assert;
import org.junit.Test;

import java.util.Collections;
import java.util.Set;

import OOP.Provided.CartelDeNachos;
import OOP.Provided.CasaDeBurrito;
import OOP.Provided.Profesor;
import OOP.Tests.tzlil.utils.CartelDeNachosImplBaseTest;

public class CartelDeNachosImplTest
        extends CartelDeNachosImplBaseTest {

    @Test
    public void joinCartelGetProfesor_success()
            throws Profesor.ProfesorNotInSystemException {
        addProfesorWithId(1);
        Assert.assertEquals(1, cartel.getProfesor(1).getId());
    }

    @Test
    public void addCasaDeBurritoGetCasaDeBurrito_success()
            throws CasaDeBurrito.CasaDeBurritoNotInSystemException {
        addCasaWithId(1);
        Assert.assertEquals(1, cartel.getCasaDeBurrito(1).getId());
    }

    @Test
    public void favoritesByRatingAndDist()
            throws CasaDeBurrito.RateRangeException,
                   Profesor.UnratedFavoriteCasaDeBurritoException,
                   Profesor.ProfesorAlreadyInSystemException,
                   CasaDeBurrito.CasaDeBurritoAlreadyInSystemException,
                   Profesor.ProfesorNotInSystemException, Profesor.ConnectionAlreadyExistsException,
                   Profesor.SameProfesorException {

        final Set<String> defaultMenu = Collections.emptySet();

        Profesor profesor1 = cartel.joinCartel(1, "1"); // p2, p4, c1, c4.
        Profesor profesor2 = cartel.joinCartel(2, "2"); // p1, p3, c2, c3, c6, c7, c8.
        Profesor profesor3 = cartel.joinCartel(3, "3"); // p2, c5.
        Profesor profesor4 = cartel.joinCartel(4, "4"); // p1, c2, c4, c6.

        CasaDeBurrito casa1 = cartel.addCasaDeBurrito(1, "1", 2, defaultMenu); // Rate: 3.5.
        CasaDeBurrito casa2 = cartel.addCasaDeBurrito(2, "2", 3, defaultMenu); // Rate: 3.5.
        CasaDeBurrito casa3 = cartel.addCasaDeBurrito(3, "3", 2, defaultMenu); // Rate: 3.5.
        CasaDeBurrito casa4 = cartel.addCasaDeBurrito(4, "4", 1, defaultMenu); // Rate: 5.
        CasaDeBurrito casa5 = cartel.addCasaDeBurrito(5, "5", 3, defaultMenu); // Rate: 2.5.
        CasaDeBurrito casa6 = cartel.addCasaDeBurrito(6, "6", 3, defaultMenu); // Rate: 3.5.
        CasaDeBurrito casa7 = cartel.addCasaDeBurrito(7, "7", 2, defaultMenu); // Rate: 4.5.
        CasaDeBurrito casa8 = cartel.addCasaDeBurrito(8, "8", 3, defaultMenu); // Rate: 4.5.

        cartel.addConnection(profesor1, profesor2);
        cartel.addConnection(profesor2, profesor3);
        cartel.addConnection(profesor1, profesor4);

        casa1.rate(profesor1, 3);
        casa1.rate(profesor2, 3);
        casa1.rate(profesor3, 4);
        casa1.rate(profesor4, 4);

        casa2.rate(profesor1, 3);
        casa2.rate(profesor2, 3);
        casa2.rate(profesor3, 4);
        casa2.rate(profesor4, 4);

        casa3.rate(profesor1, 3);
        casa3.rate(profesor2, 3);
        casa3.rate(profesor3, 4);
        casa3.rate(profesor4, 4);

        casa4.rate(profesor1, 5);
        casa4.rate(profesor2, 5);
        casa4.rate(profesor3, 5);
        casa4.rate(profesor4, 5);

        casa5.rate(profesor1, 2);
        casa5.rate(profesor2, 2);
        casa5.rate(profesor3, 3);
        casa5.rate(profesor4, 3);

        casa6.rate(profesor1, 3);
        casa6.rate(profesor2, 3);
        casa6.rate(profesor3, 4);
        casa6.rate(profesor4, 4);

        casa7.rate(profesor1, 4);
        casa7.rate(profesor2, 4);
        casa7.rate(profesor3, 5);
        casa7.rate(profesor4, 5);

        casa8.rate(profesor1, 4);
        casa8.rate(profesor2, 4);
        casa8.rate(profesor3, 5);
        casa8.rate(profesor4, 5);

        profesor1.favorite(casa1);
        profesor1.favorite(casa4);

        profesor2.favorite(casa2);
        profesor2.favorite(casa3);
        profesor2.favorite(casa6);
        profesor2.favorite(casa7);
        profesor2.favorite(casa8);

        profesor3.favorite(casa5);

        profesor4.favorite(casa2);
        profesor4.favorite(casa4);
        profesor4.favorite(casa6);

        assertCasasCollectionByOrder(cartel.favoritesByRating(profesor1), 7, 8, 3, 2, 6, 4);
        assertCasasCollectionByOrder(cartel.favoritesByDist(profesor1), 7, 3, 8, 2, 6, 4);
        assertFavoritesNotAliased(this::favoritesByRatingWrapper, profesor1);
        assertFavoritesNotAliased(this::favoritesByDistWrapper, profesor1);

        assertCasasCollectionByOrder(cartel.favoritesByRating(profesor2), 4, 1, 5);
        assertCasasCollectionByOrder(cartel.favoritesByDist(profesor2), 4, 1, 5);
        assertFavoritesNotAliased(this::favoritesByRatingWrapper, profesor2);
        assertFavoritesNotAliased(this::favoritesByDistWrapper, profesor2);

        assertCasasCollectionByOrder(cartel.favoritesByRating(profesor3), 7, 8, 3, 2, 6);
        assertCasasCollectionByOrder(cartel.favoritesByDist(profesor3), 7, 3, 8, 2, 6);
        assertFavoritesNotAliased(this::favoritesByRatingWrapper, profesor3);
        assertFavoritesNotAliased(this::favoritesByDistWrapper, profesor3);

        assertCasasCollectionByOrder(cartel.favoritesByRating(profesor4), 4, 1);
        assertCasasCollectionByOrder(cartel.favoritesByDist(profesor4), 4, 1);
        assertFavoritesNotAliased(this::favoritesByRatingWrapper, profesor4);
        assertFavoritesNotAliased(this::favoritesByDistWrapper, profesor4);
    }

    @Test
    public void getRecommendation()
            throws Profesor.ProfesorAlreadyInSystemException,
                   CasaDeBurrito.CasaDeBurritoAlreadyInSystemException,
                   Profesor.ProfesorNotInSystemException, Profesor.SameProfesorException,
                   Profesor.ConnectionAlreadyExistsException, CasaDeBurrito.RateRangeException,
                   Profesor.UnratedFavoriteCasaDeBurritoException,
                   CartelDeNachos.ImpossibleConnectionException,
                   CasaDeBurrito.CasaDeBurritoNotInSystemException {

        final Set<String> defaultMenu = Collections.emptySet();

        Profesor profesor1 = cartel.joinCartel(1, "1");
        Profesor profesor2 = cartel.joinCartel(2, "2");
        Profesor profesor3 = cartel.joinCartel(3, "3");
        Profesor profesor4 = cartel.joinCartel(4, "4");
        Profesor profesor5 = cartel.joinCartel(5, "5");
        Profesor profesor6 = cartel.joinCartel(6, "6");
        Profesor profesor7 = cartel.joinCartel(7, "7");

        CasaDeBurrito casa1 = cartel.addCasaDeBurrito(1, "1", 1, defaultMenu);
        CasaDeBurrito casa2 = cartel.addCasaDeBurrito(2, "2", 2, defaultMenu);
        CasaDeBurrito casa3 = cartel.addCasaDeBurrito(3, "3", 3, defaultMenu);

        cartel.addConnection(profesor1, profesor2);
        cartel.addConnection(profesor1, profesor3);
        cartel.addConnection(profesor2, profesor3);

        cartel.addConnection(profesor5, profesor6);
        cartel.addConnection(profesor5, profesor7);
        cartel.addConnection(profesor6, profesor7);

        cartel.addConnection(profesor4, profesor3);
        cartel.addConnection(profesor4, profesor5);

        casa1.rate(profesor1, 5);
        profesor1.favorite(casa1);

        casa2.rate(profesor5, 5);
        profesor5.favorite(casa2);

        casa3.rate(profesor6, 5);
        profesor6.favorite(casa3);

        Assert.assertFalse(cartel.getRecommendation(profesor1, casa2, 2));
        Assert.assertTrue(cartel.getRecommendation(profesor1, casa2, 3));
        Assert.assertTrue(cartel.getRecommendation(profesor1, casa2, 30));

        Assert.assertFalse(cartel.getRecommendation(profesor1, casa3, 3));
        Assert.assertTrue(cartel.getRecommendation(profesor1, casa3, 4));
        Assert.assertTrue(cartel.getRecommendation(profesor1, casa3, 20));

        Assert.assertFalse(cartel.getRecommendation(profesor4, casa1, 1));
        Assert.assertTrue(cartel.getRecommendation(profesor4, casa1, 2));
        Assert.assertTrue(cartel.getRecommendation(profesor4, casa1, 20));

        Assert.assertFalse(cartel.getRecommendation(profesor4, casa3, 1));
        Assert.assertTrue(cartel.getRecommendation(profesor4, casa3, 2));
        Assert.assertTrue(cartel.getRecommendation(profesor4, casa3, 20));
    }

    @Test
    public void getMostPopularRestaurantsIds()
            throws Profesor.ProfesorAlreadyInSystemException,
                   CasaDeBurrito.CasaDeBurritoAlreadyInSystemException,
                   Profesor.ProfesorNotInSystemException, Profesor.SameProfesorException,
                   Profesor.ConnectionAlreadyExistsException, CasaDeBurrito.RateRangeException,
                   Profesor.UnratedFavoriteCasaDeBurritoException {

        final Set<String> defaultMenu = Collections.emptySet();

        Profesor profesor1 = cartel.joinCartel(1, "1");
        Profesor profesor2 = cartel.joinCartel(2, "2");
        Profesor profesor3 = cartel.joinCartel(3, "3");
        Profesor profesor4 = cartel.joinCartel(4, "4");
        Profesor profesor5 = cartel.joinCartel(5, "5");

        CasaDeBurrito casa1 = cartel.addCasaDeBurrito(1, "1", 1, defaultMenu);
        CasaDeBurrito casa2 = cartel.addCasaDeBurrito(2, "2", 2, defaultMenu);
        CasaDeBurrito casa3 = cartel.addCasaDeBurrito(3, "3", 3, defaultMenu);
        CasaDeBurrito casa4 = cartel.addCasaDeBurrito(4, "4", 4, defaultMenu);
        CasaDeBurrito casa5 = cartel.addCasaDeBurrito(5, "5", 5, defaultMenu);

        cartel.addConnection(profesor1, profesor2);
        cartel.addConnection(profesor1, profesor4);
        cartel.addConnection(profesor3, profesor2);
        cartel.addConnection(profesor3, profesor4);
        cartel.addConnection(profesor2, profesor5);

        casa1.rate(profesor1, 5);
        profesor1.favorite(casa1);

        casa2.rate(profesor3, 5);
        profesor3.favorite(casa2);

        casa3.rate(profesor3, 5);
        profesor3.favorite(casa3);

        casa4.rate(profesor4, 5);
        profesor4.favorite(casa4);

        casa5.rate(profesor4, 5);
        profesor4.favorite(casa5);

        casa2.rate(profesor5, 5);
        profesor5.favorite(casa2);

        casa4.rate(profesor5, 5);
        profesor5.favorite(casa4);

        assertCollectionByOrder(cartel.getMostPopularRestaurantsIds(), 2, 4);
        assertCollectionSupplierNotAliased(cartel::getMostPopularRestaurantsIds);
    }
}
