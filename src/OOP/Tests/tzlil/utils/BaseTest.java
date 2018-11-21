package OOP.Tests.tzlil.utils;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.rules.ExpectedException;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;
import java.util.function.Predicate;
import java.util.function.Supplier;

import OOP.Provided.CasaDeBurrito;
import OOP.Provided.Profesor;
import OOP.Solution.CasaDeBurritoImpl;
import OOP.Solution.ProfesorImpl;

public class BaseTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    protected int counter;

    protected void expectException(Class<? extends Throwable> exception) {
        expectedException.expect(exception);
    }

    protected void assertEqualsDouble(double expected, double actual) {
        final double precision = 0.01;
        Assert.assertEquals(expected, actual, precision);
    }

    protected <T> void assertCollectionSupplierNotAliased(Supplier<Collection<T>> supplier) {
        Collection<T> collection = supplier.get();
        Assert.assertFalse(collection.isEmpty());
        Assert.assertFalse(supplier.get().isEmpty());
        collection.clear();
        Assert.assertTrue(collection.isEmpty());
        Assert.assertFalse(supplier.get().isEmpty());
    }

    protected void assertProfesorCollectionContainsIds(Collection<Profesor> collection, int... ids) {
        Profesor[] profesors = new Profesor[ids.length];
        for (int i = 0; i < ids.length; i++) {
            profesors[i] = getNewProfesorWithId(ids[i]);
        }
        assertCollectionContains(collection, profesors);
    }

    protected void assertCasasCollectionContainsIds(Collection<CasaDeBurrito> collection, int... ids) {
        CasaDeBurrito[] casas = new CasaDeBurrito[ids.length];
        for (int i = 0; i < ids.length; i++) {
            casas[i] = getNewCasaWithId(ids[i]);
        }
        assertCollectionContains(collection, casas);
    }

    protected void assertCasasCollectionByOrder(Collection<CasaDeBurrito> collection, int... ids) {
        CasaDeBurrito[] casas = new CasaDeBurrito[ids.length];
        for (int i = 0; i < ids.length; i++) {
            casas[i] = getNewCasaWithId(ids[i]);
        }
        assertCollectionByOrder(collection, casas);
    }

    @SafeVarargs
    protected final <T> void assertCollectionContains(Collection<T> collection, T... items) {
        Assert.assertEquals(items.length, collection.size());
        for (T item : items) {
            Assert.assertTrue(collection.contains(item));
        }
    }

    @SafeVarargs
    protected final <T> void assertCollectionByOrder(Collection<T> collection, T... items) {
        Assert.assertEquals(items.length, collection.size());
        int index = 0;
        for (T item : collection) {
            Assert.assertEquals(item, items[index]);
            index++;
        }
    }

    protected <T> Predicate<T> getTruePredicate() {
        return item -> true;
    }

    protected <T> Predicate<T> getFalsePredicate() {
        return item -> false;
    }

    protected CasaDeBurrito getNewCasa(int id, String name, int dist, Set<String> menu) {
        return new CasaDeBurritoImpl(id, name, dist, menu);
    }

    protected CasaDeBurrito getNewCasaWithDefaultMenu(int id, String name, int dist) {
        return getNewCasa(id, name, dist, Collections.emptySet());
    }

    protected CasaDeBurrito getNewCasaWithId(int id) {
        int currentCounter = counter;
        counter++;
        return getNewCasa(id, String.valueOf(currentCounter), currentCounter, Collections.emptySet());
    }

    protected CasaDeBurrito getNewCasaWithName(String name) {
        int currentCounter = counter;
        counter++;
        return getNewCasa(currentCounter, name, currentCounter, Collections.emptySet());
    }

    protected CasaDeBurrito getNewRandomCasa() {
        int currentCounter = counter;
        counter++;
        return getNewCasa(currentCounter, String.valueOf(currentCounter), currentCounter, Collections.emptySet());
    }

    protected Profesor getNewProfessor(int id, String name) {
        return new ProfesorImpl(id, name);
    }

    protected Profesor getNewProfesorWithId(int id) {
        int currentCounter = counter;
        counter++;
        return getNewProfessor(id, String.valueOf(currentCounter));
    }

    protected Profesor getNewRandomProfesor() {
        int currentCounter = counter;
        counter++;
        return getNewProfessor(currentCounter, String.valueOf(currentCounter));
    }

    protected CasaDeBurrito rateCasaWrapper(CasaDeBurrito casa, Profesor profesor, int rate) {
        try {
            return casa.rate(profesor, rate);
        } catch (CasaDeBurrito.RateRangeException ignored) {
            throw new IllegalArgumentException(
                    "Rate must be in range [0,5]. Rate: " + rate + ".");
        }
    }

    protected Profesor favoriteCasaWrapper(Profesor profesor, CasaDeBurrito casa) {
        try {
            return profesor.favorite(casa);
        } catch (Profesor.UnratedFavoriteCasaDeBurritoException ignored) {
            throw new IllegalArgumentException("Profesor must rate the casa first.");
        }
    }

    protected Profesor addFriendWrapper(Profesor profesor, Profesor friend) {
        try {
            return profesor.addFriend(friend);
        } catch (Profesor.SameProfesorException ignored) {
            throw new IllegalArgumentException("Cannot add itself");
        } catch (Profesor.ConnectionAlreadyExistsException ignored) {
            throw new IllegalArgumentException("Cannot add friend twice.");
        }
    }
}
