package OOP.Tests.tzlil.unit;

import org.junit.Assert;
import org.junit.Test;

import java.util.Collection;
import java.util.Comparator;
import java.util.function.Supplier;

import OOP.Provided.CasaDeBurrito;
import OOP.Provided.Profesor;
import OOP.Tests.tzlil.utils.ProfesorImplBaseTest;

public class ProfesorImplTest
        extends ProfesorImplBaseTest {

    @Test
    public void getId_success() {
        final int expectedId = 2;
        setProfesorWithId(expectedId);
        Assert.assertEquals(expectedId, profesor.getId());
    }

    @Test
    public void favorite_success_invokeTwice() {
        CasaDeBurrito casa = getRatedRandomCasa();
        favoriteCasaWrapper(casa);
        favoriteCasaWrapper(casa);
    }

    @Test
    public void favorite_fail_casaNotRated()
            throws Profesor.UnratedFavoriteCasaDeBurritoException {
        expectException(Profesor.UnratedFavoriteCasaDeBurritoException.class);
        profesor.favorite(getNewRandomCasa());
    }

    @Test
    public void favorite_returnNonNull() {
        Assert.assertNotNull(favoriteRandomCasa());
    }

    @Test
    public void favorites_empty() {
        Assert.assertTrue(profesor.favorites().isEmpty());
    }

    @Test
    public void favorites_success() {
        favoriteCasasWithIds(1, 2);
        assertFavoritesContainsIds(1, 2);
    }

    @Test
    public void favorites_notAliased() {
        favoriteCasasWithIds(1, 2);
        assertCollectionSupplierNotAliased(profesor::favorites);
    }

    @Test
    public void addFriend_cannotAddItself()
            throws Profesor.ConnectionAlreadyExistsException, Profesor.SameProfesorException {
        expectException(Profesor.SameProfesorException.class);
        profesor.addFriend(profesor);
    }

    @Test
    public void addFriend_cannotAddEqual()
            throws Profesor.ConnectionAlreadyExistsException, Profesor.SameProfesorException {
        Profesor duplicateProfesor = getDuplicateProfesor();
        expectException(Profesor.SameProfesorException.class);
        profesor.addFriend(duplicateProfesor);
    }

    @Test
    public void addFriend_cannotAddTwice()
            throws Profesor.ConnectionAlreadyExistsException, Profesor.SameProfesorException {
        Profesor anotherProfesor = getDifferentProfesor();
        profesor.addFriend(anotherProfesor);
        expectException(Profesor.ConnectionAlreadyExistsException.class);
        profesor.addFriend(anotherProfesor);
    }

    @Test
    public void addFriend_returnNonNull() {
        Assert.assertNotNull(addRandomFriend());
    }

    @Test
    public void getFriends_empty() {
        Assert.assertTrue(profesor.getFriends().isEmpty());
    }

    @Test
    public void getFriends_success() {
        setProfesorWithId(1);
        addFriendsWithIds(2, 3);
        assertCollectionContainsIds(2, 3);
    }

    @Test
    public void getFriends_notAliased() {
        setProfesorWithId(1);
        addFriendsWithIds(2, 3);
        assertCollectionSupplierNotAliased(profesor::getFriends);
    }

    @Test
    public void filteredFriends_success() {
        setProfesorWithId(1);
        addFriendsWithIds(2, 3);
        Collection<Profesor> friends = profesor.filteredFriends(
                profesor -> profesor.getId() % 2 == 1);
        assertCollectionContains(friends, getNewProfesorWithId(3));
    }

    @Test
    public void filteredFriends_success_becomesEmpty() {
        setProfesorWithId(1);
        addFriendsWithIds(2, 3);
        Collection<Profesor> friends = profesor.filteredFriends(
                profesor -> profesor.getId() > 3);
        Assert.assertTrue(friends.isEmpty());
    }

    @Test
    public void filteredFriends_notAliased() {
        setProfesorWithId(1);
        addFriendsWithIds(2, 3);
        Supplier<Collection<Profesor>> supplier = () -> profesor.filteredFriends(getTruePredicate());
        assertCollectionSupplierNotAliased(supplier);
    }

    @Test
    public void filterAndSortFavorites_becomesEmpty() {
        favoriteCasasWithIds(3, 2, 4);
        Collection<CasaDeBurrito> favorites = profesor.filterAndSortFavorites(
                Comparator.naturalOrder(), getFalsePredicate());
        Assert.assertTrue(favorites.isEmpty());
    }

    @Test
    public void filterAndSortFavorites_success() {
        favoriteCasasWithIds(3, 2, 4);
        Collection<CasaDeBurrito> favorites = profesor.filterAndSortFavorites(
                Comparator.reverseOrder(), profesor -> profesor.getId() != 2);
        assertFavoritesContainsIdsByOrder(favorites, 4, 3);
    }

    @Test
    public void filterAndSortFavorites_notAliased() {
        favoriteCasasWithIds(2, 3, 4);
        Supplier<Collection<CasaDeBurrito>> supplier = () -> profesor.filterAndSortFavorites(
                Comparator.naturalOrder(), getTruePredicate());
        assertCollectionSupplierNotAliased(supplier);
    }

    @Test
    public void favoritesByRating_notAliased() {
        favoriteCasasWithIds(2, 3, 4);
        Supplier<Collection<CasaDeBurrito>> supplier = () -> profesor.favoritesByRating(0);
        assertCollectionSupplierNotAliased(supplier);
    }

    @Test
    public void favoritesByDist_notAliased() {
        favoriteCasasWithIds(2, 3, 4);
        Supplier<Collection<CasaDeBurrito>> supplier = () -> profesor.favoritesByDist(Integer.MAX_VALUE);
        assertCollectionSupplierNotAliased(supplier);
    }

    @Test
    public void equals_reflexive() {
        //noinspection EqualsWithItself,SimplifiableJUnitAssertion
        Assert.assertTrue(profesor.equals(profesor));
    }

    @Test
    public void equals_symmetric() {
        Profesor duplicateProfesor = getDuplicateProfesor();
        //noinspection SimplifiableJUnitAssertion
        Assert.assertTrue(profesor.equals(duplicateProfesor));
        //noinspection SimplifiableJUnitAssertion
        Assert.assertTrue(duplicateProfesor.equals(profesor));
    }

    @Test
    public void equals_fail_null() {
        //noinspection ConstantConditions,SimplifiableJUnitAssertion
        Assert.assertFalse(profesor.equals(null));
    }

    @Test
    public void equals_fail_notEqual() {
        Profesor differentProfesor = getDifferentProfesor();
        //noinspection SimplifiableJUnitAssertion
        Assert.assertFalse(profesor.equals(differentProfesor));
        //noinspection SimplifiableJUnitAssertion
        Assert.assertFalse(differentProfesor.equals(profesor));
    }

    @Test
    public void equals_fail_derivedClass() {
        Profesor duplicateDerivedProfesor = getDuplicateDerivedProfesor();
        //noinspection SimplifiableJUnitAssertion
        Assert.assertFalse(profesor.equals(duplicateDerivedProfesor));
        //noinspection SimplifiableJUnitAssertion
        Assert.assertFalse(duplicateDerivedProfesor.equals(profesor));
    }

    @Test
    public void equals_fail_notCasaDeBurrito() {
        //noinspection EqualsBetweenInconvertibleTypes,SimplifiableJUnitAssertion
        Assert.assertFalse(profesor.equals("string"));
    }

    @Test
    public void hashCode_consistent() {
        final int numberOfTries = 1000;
        int hash = profesor.hashCode();
        for (int i = 0; i < numberOfTries; i++) {
            Assert.assertEquals(hash, profesor.hashCode());
        }
    }

    @Test
    public void toString_success() {
        final int id = 1;
        final String name = "name";
        final String casaName1 = "casa1";
        final String casaName2 = "casa2";
        final String casaName3 = "casa3";
        setProfesor(id, name);
        favoriteCasasWithNames(casaName2, casaName3, casaName1);

        String expectedString = "Profesor: " + name + ".\n" +
                "Id: " + id + ".\n" +
                "Favorites: " +
                casaName1 + ", " +
                casaName2 + ", " +
                casaName3 + ".";

        Assert.assertEquals(expectedString, profesor.toString());
    }

    @Test
    public void toString_noFavorites() {
        final int id = 1;
        final String name = "name";
        setProfesor(id, name);

        String expectedString = "Profesor: " + name + ".\n" +
                "Id: " + id + ".\n" +
                "Favorites: .";

        Assert.assertEquals(expectedString, profesor.toString());
    }

    @Test
    public void compareTo_thisBefore() {
        setProfesorWithId(1);
        Profesor greaterProfesor = getNewProfesorWithId(2);
        Assert.assertTrue(profesor.compareTo(greaterProfesor) < 0);
        Assert.assertTrue(greaterProfesor.compareTo(profesor) > 0);
    }

    @Test
    public void compareTo_thisAfter() {
        setProfesorWithId(2);
        Profesor lowerProfessor = getNewProfesorWithId(1);
        Assert.assertTrue(profesor.compareTo(lowerProfessor) > 0);
        Assert.assertTrue(lowerProfessor.compareTo(profesor) < 0);
    }

    @Test
    public void compareTo_equals() {
        setProfesorWithId(1);
        Profesor sameProfesor = getNewProfesorWithId(1);
        Assert.assertEquals(0, profesor.compareTo(sameProfesor));
        Assert.assertEquals(0, sameProfesor.compareTo(profesor));
    }

    @Test
    public void compareTo_equalsToItself() {
        //noinspection EqualsWithItself
        Assert.assertEquals(0, profesor.compareTo(profesor));
    }
}
