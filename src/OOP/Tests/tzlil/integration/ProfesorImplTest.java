package OOP.Tests.tzlil.integration;

import org.junit.Test;

import OOP.Provided.CasaDeBurrito;
import OOP.Provided.Profesor;
import OOP.Tests.tzlil.utils.ProfesorImplBaseTest;

public class ProfesorImplTest
        extends ProfesorImplBaseTest {

    @Test
    public void favoritesByRatingAndDist() {
        Profesor profesor1 = profesor;
        Profesor profesor2 = getNewRandomProfesor();
        Profesor profesor3 = getNewRandomProfesor();

        CasaDeBurrito casa1 = getNewCasaWithDefaultMenu(1, "1", 2); // Rate: 3.
        CasaDeBurrito casa2 = getNewCasaWithDefaultMenu(2, "2", 1); // Rate: 3.
        CasaDeBurrito casa3 = getNewCasaWithDefaultMenu(3, "3", 2); // Rate: 3.
        CasaDeBurrito casa4 = getNewCasaWithDefaultMenu(4, "4", 1); // Rate: 4.
        CasaDeBurrito casa5 = getNewCasaWithDefaultMenu(5, "5", 3); // Rate: 2.
        CasaDeBurrito casa6 = getNewCasaWithDefaultMenu(6, "6", 2); // Rate: 4.

        rateCasaWrapper(casa1, profesor1, 3);
        rateCasaWrapper(casa1, profesor2, 3);
        rateCasaWrapper(casa1, profesor3, 3);

        rateCasaWrapper(casa2, profesor1, 3);
        rateCasaWrapper(casa2, profesor2, 3);
        rateCasaWrapper(casa2, profesor3, 3);

        rateCasaWrapper(casa3, profesor1, 3);
        rateCasaWrapper(casa3, profesor2, 3);
        rateCasaWrapper(casa3, profesor3, 3);

        rateCasaWrapper(casa4, profesor1, 4);
        rateCasaWrapper(casa4, profesor2, 4);
        rateCasaWrapper(casa4, profesor3, 4);

        rateCasaWrapper(casa5, profesor1, 2);
        rateCasaWrapper(casa5, profesor2, 2);
        rateCasaWrapper(casa5, profesor3, 2);

        rateCasaWrapper(casa6, profesor1, 4);
        rateCasaWrapper(casa6, profesor2, 4);
        rateCasaWrapper(casa6, profesor3, 4);

        favoriteCasas(casa1, casa2, casa3, casa4, casa5, casa6);

        assertFavoritesContainsIdsByOrder(profesor1.favoritesByRating(3), 4, 6, 2, 1, 3);
        assertFavoritesContainsIdsByOrder(profesor1.favoritesByDist(2), 4, 2, 6, 1, 3);
    }
}
