package OOP.Tests.IntegrationTests;

import OOP.Provided.CasaDeBurrito;
import OOP.Provided.Profesor;
import OOP.Solution.CasaDeBurritoImpl;
import OOP.Solution.ProfesorImpl;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.Collection;
import java.util.Comparator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static junit.framework.TestCase.*;
import static org.junit.Assert.assertFalse;

public class ProfesorTests {
    private CasaDeBurrito cdb1;
    private CasaDeBurrito cdb2;
    private CasaDeBurrito cdb3;
    private CasaDeBurrito cdb4;
    private Profesor pf1;
    private Profesor pf2;
    private Profesor pf3;

    @Rule
    public ExpectedException thrown = ExpectedException.none();


    @Before
    public void setUp(){
        cdb1 = new CasaDeBurritoImpl(1,"CDB1",6, Stream.of("A","B","C").collect(Collectors.toSet()));
        cdb2 = new CasaDeBurritoImpl(2,"CDB2",5, Stream.of("A","B","C").collect(Collectors.toSet()));
        cdb3 = new CasaDeBurritoImpl(3,"CDB3",4, Stream.of("A","B","C").collect(Collectors.toSet()));
        cdb4 = new CasaDeBurritoImpl(4,"CDB4", 4,Stream.of("A","B","C").collect(Collectors.toSet()));

        pf1 = new ProfesorImpl(1,"Prof 1");
        pf2 = new ProfesorImpl(2,"Prof 2");
        pf3 = new ProfesorImpl(3,"Prof 3");
    }

    @Test
    public void testFavorite() throws CasaDeBurrito.RateRangeException, Profesor.UnratedFavoriteCasaDeBurritoException {
        cdb1.rate(pf1,3);
        pf1.favorite(cdb1);
        assert(pf1.favorites().contains(cdb1));
    }

    @Test
    public void testFavoriteUnrated() throws Profesor.UnratedFavoriteCasaDeBurritoException {
        thrown.expect(Profesor.UnratedFavoriteCasaDeBurritoException.class);
        pf1.favorite(cdb1);
    }

    @Test
    public void testFavoriteReturnSelf() throws CasaDeBurrito.RateRangeException, Profesor.UnratedFavoriteCasaDeBurritoException {
        cdb1.rate(pf1,3);
        cdb2.rate(pf1,4);
        pf1.favorite(cdb1).favorite(cdb2);
    }

    @Test
    public void testChangeOfDBOnFavorites() throws CasaDeBurrito.RateRangeException, Profesor.UnratedFavoriteCasaDeBurritoException {
        cdb1.rate(pf1,4);
        pf1.favorite(cdb1);
        Collection<CasaDeBurrito> favorites =  pf1.favorites();

        cdb2.rate(pf1,3);
        pf1.favorite(cdb2);
        assertFalse(favorites.contains(cdb2));
    }

    @Test
    public void testFilterAndSortFavorites() throws CasaDeBurrito.RateRangeException, Profesor.UnratedFavoriteCasaDeBurritoException {
        cdb1.rate(pf1,3);
        cdb2.rate(pf1,5);
        cdb3.rate(pf1,2);
        pf1.favorite(cdb1);
        pf1.favorite(cdb2);
        pf1.favorite(cdb3);

        Collection<CasaDeBurrito> col = pf1.filterAndSortFavorites(Comparator.comparingInt(CasaDeBurrito::getId)
                                        ,(x) -> x.distance() > 4);
        assertEquals(col.size(), 2);
        assert(col.contains(cdb1));
        assert(col.contains(cdb2));
        assert (col.toArray()[0].equals(cdb1));
    }

    @Test
    public void testNotOriginalOrderFilterAndSort() throws CasaDeBurrito.RateRangeException, Profesor.UnratedFavoriteCasaDeBurritoException {
        cdb1.rate(pf1,3);
        cdb2.rate(pf1,5);
        cdb3.rate(pf1,2);

        pf1.favorite(cdb1);
        pf1.favorite(cdb2);
        pf1.favorite(cdb3);

        Collection<CasaDeBurrito> col = pf1.filterAndSortFavorites(Comparator.comparingInt(CasaDeBurrito::distance)
                ,(x) -> true);
        assert (col.toArray()[0].equals(cdb3));
    }

    @Test
    public void testFavoritesByRating() throws CasaDeBurrito.RateRangeException, Profesor.UnratedFavoriteCasaDeBurritoException {
        cdb1.rate(pf1,3);
        cdb2.rate(pf1,5);
        cdb3.rate(pf1,2);

        pf1.favorite(cdb1);
        pf1.favorite(cdb2);
        pf1.favorite(cdb3);

        Collection<CasaDeBurrito> col = pf1.favoritesByRating(4);
        assertEquals(col.size(),1);
        assert(col.contains(cdb2));
    }

    @Test
    public void testFavoriteBySecondOrder() throws CasaDeBurrito.RateRangeException, Profesor.UnratedFavoriteCasaDeBurritoException {
        cdb1.rate(pf1,5);
        cdb2.rate(pf1,5);
        cdb3.rate(pf1,5);

        pf1.favorite(cdb1);
        pf1.favorite(cdb2);
        pf1.favorite(cdb3);

        Collection<CasaDeBurrito> col = pf1.favoritesByRating(4);
        assertEquals(col.size(),3);
        assert (col.toArray()[0].equals(cdb3));
    }

    @Test
    public void testFavoriteByThirdOrder() throws CasaDeBurrito.RateRangeException, Profesor.UnratedFavoriteCasaDeBurritoException {
        cdb1.rate(pf1,5);
        cdb2.rate(pf1,5);
        cdb3.rate(pf1,5);
        cdb4.rate(pf1,5);

        pf1.favorite(cdb1);
        pf1.favorite(cdb2);
        pf1.favorite(cdb3);
        pf1.favorite(cdb4);

        Collection<CasaDeBurrito> col = pf1.favoritesByRating(4);
        assertEquals(col.size(),4);
        assert (col.toArray()[0].equals(cdb3));
    }

    @Test
    public void testFavoritesByRatingZeroOrNegative() throws CasaDeBurrito.RateRangeException, Profesor.UnratedFavoriteCasaDeBurritoException {
        cdb1.rate(pf1,1);
        cdb2.rate(pf1,2);
        cdb3.rate(pf1,3);
        cdb4.rate(pf1,4);

        pf1.favorite(cdb1);
        pf1.favorite(cdb2);
        pf1.favorite(cdb3);
        pf1.favorite(cdb4);

        Collection<CasaDeBurrito> col = pf1.favoritesByRating(0);
        Collection<CasaDeBurrito> col2 = pf1.favoritesByRating(-8);
        assertEquals(col.size(),4);
        assertEquals(col2.size(),4);
    }

    @Test
    public void testFavoritesByDistZeroOrNegative() throws CasaDeBurrito.RateRangeException, Profesor.UnratedFavoriteCasaDeBurritoException {
        cdb1.rate(pf1,1);
        cdb2.rate(pf1,2);


        pf1.favorite(cdb1);
        pf1.favorite(cdb2);

        Collection<CasaDeBurrito> col = pf1.favoritesByDist(0);
        Collection<CasaDeBurrito> col2 = pf1.favoritesByDist(-8);
        assertEquals(col.size(),0);
        assertEquals(col2.size(),0);
    }

    @Test
    public void testFavoritesByDist() throws CasaDeBurrito.RateRangeException, Profesor.UnratedFavoriteCasaDeBurritoException {
        cdb1.rate(pf1,1);
        cdb2.rate(pf1,2);
        cdb3.rate(pf1,3);

        pf1.favorite(cdb1);
        pf1.favorite(cdb2);
        pf1.favorite(cdb3);

        Collection<CasaDeBurrito> col = pf1.favoritesByDist(5);
        assertEquals(col.size(),2);
        assert(col.toArray()[0].equals(cdb3));
    }

    @Test
    public void testFavoritesByDistSecondOrder() throws CasaDeBurrito.RateRangeException, Profesor.UnratedFavoriteCasaDeBurritoException {
        cdb3.rate(pf1,2);
        cdb4.rate(pf1,3);

        pf1.favorite(cdb3);
        pf1.favorite(cdb4);

        Collection<CasaDeBurrito> col = pf1.favoritesByDist(5);
        assert(col.toArray()[0].equals(cdb4));
    }

    @Test
    public void testFavoritesByDistThirdOrder() throws CasaDeBurrito.RateRangeException, Profesor.UnratedFavoriteCasaDeBurritoException {
        cdb3.rate(pf1,3);
        cdb4.rate(pf1,3);

        pf1.favorite(cdb3);
        pf1.favorite(cdb4);

        Collection<CasaDeBurrito> col = pf1.favoritesByDist(5);
        assert(col.toArray()[0].equals(cdb3));
    }

}
