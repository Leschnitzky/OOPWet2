package OOP.Tests.tzlil.unit;

import org.junit.Assert;
import org.junit.Test;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import OOP.Provided.CasaDeBurrito;
import OOP.Provided.Profesor;
import OOP.Tests.tzlil.utils.CasaDeBurritoImplBaseTest;

public class CasaDeBurritoImplTest
        extends CasaDeBurritoImplBaseTest {

    @Test
    public void getId_success() {
        int expectedId = 3;
        setCasaWithId(expectedId);
        Assert.assertEquals(expectedId, casa.getId());
    }

    @Test
    public void getName_success() {
        String expectedName = "name";
        setCasaWithName(expectedName);
        Assert.assertEquals(expectedName, casa.getName());
    }

    @Test
    public void distance_success() {
        int expectedDistance = 5;
        setCasaWithDistance(expectedDistance);
        Assert.assertEquals(expectedDistance, casa.distance());
    }

    @Test
    public void isRatedBy_false_null() {
        Assert.assertFalse(casa.isRatedBy(null));
    }

    @Test
    public void isRatedBy_false_didNotRate() {
        Profesor profesor = getNewRandomProfesor();
        Assert.assertFalse(casa.isRatedBy(profesor));
    }

    @Test
    public void isRatedBy_true() {
        Profesor profesor = getNewRandomProfesor();
        randomRateWithProfesor(profesor);
        Assert.assertTrue(casa.isRatedBy(profesor));
    }

    @Test
    public void isRatedBy_true_ratedTwice() {
        Profesor profesor = getNewRandomProfesor();
        randomRateWithProfesor(profesor);
        randomRateWithProfesor(profesor);
        Assert.assertTrue(casa.isRatedBy(profesor));
    }

    @Test
    public void rate_fail_rateBelowMinimum()
            throws CasaDeBurrito.RateRangeException {
        testRate_failWithValue(-1);
    }

    @Test
    public void rate_fail_rateAboveMinimum()
            throws CasaDeBurrito.RateRangeException {
        testRate_failWithValue(6);
    }

    @Test
    public void rate_success_0()
            throws CasaDeBurrito.RateRangeException {
        testRate_successWithValue(0);
    }

    @Test
    public void rate_success_1()
            throws CasaDeBurrito.RateRangeException {
        testRate_successWithValue(1);
    }

    @Test
    public void rate_success_5()
            throws CasaDeBurrito.RateRangeException {
        testRate_successWithValue(5);
    }

    @Test
    public void rate_returnNonNull() {
        Assert.assertNotNull(randomRateWithRandomProfesor());
    }

    @Test
    public void numberOfRates_noRates() {
        Assert.assertEquals(0, casa.numberOfRates());
    }

    @Test
    public void averageRating_noRates() {
        assertEqualsDouble(0, casa.averageRating());
    }

    @Test
    public void equals_reflexive() {
        //noinspection EqualsWithItself,SimplifiableJUnitAssertion
        Assert.assertTrue(casa.equals(casa));
    }

    @Test
    public void equals_symmetric() {
        CasaDeBurrito casaDuplicate = getDuplicateCasa();
        //noinspection SimplifiableJUnitAssertion
        Assert.assertTrue(casa.equals(casaDuplicate));
        //noinspection SimplifiableJUnitAssertion
        Assert.assertTrue(casaDuplicate.equals(casa));
    }

    @Test
    public void equals_fail_null() {
        //noinspection ConstantConditions,SimplifiableJUnitAssertion
        Assert.assertFalse(casa.equals(null));
    }

    @Test
    public void equals_fail_notEqual() {
        CasaDeBurrito differentCasa = getDifferentCasa();
        //noinspection SimplifiableJUnitAssertion
        Assert.assertFalse(casa.equals(differentCasa));
        //noinspection SimplifiableJUnitAssertion
        Assert.assertFalse(differentCasa.equals(casa));
    }

    @Test
    public void equals_fail_derivedClass() {
        CasaDeBurrito casaDuplicateDerived = getDuplicateDerivedCasa();
        //noinspection SimplifiableJUnitAssertion
        Assert.assertFalse(casa.equals(casaDuplicateDerived));
        //noinspection SimplifiableJUnitAssertion
        Assert.assertFalse(casaDuplicateDerived.equals(casa));
    }

    @Test
    public void equals_fail_notCasaDeBurrito() {
        //noinspection EqualsBetweenInconvertibleTypes,SimplifiableJUnitAssertion
        Assert.assertFalse(casa.equals("string"));
    }

    @Test
    public void hashCode_consistent() {
        final int numberOfTries = 1000;
        int hash = casa.hashCode();
        for (int i = 0; i < numberOfTries; i++) {
            Assert.assertEquals(hash, casa.hashCode());
        }
    }

    @Test
    public void toString_success() {
        final int id = 2;
        final String name = "name";
        final int distance = 3;
        final String item1 = "item1";
        final String item2 = "item2";
        final String item3 = "item3";
        final Set<String> menu = Stream.of(item3, item1, item2).collect(Collectors.toSet());
        setCasa(id, name, distance, menu);

        String expectedString = "CasaDeBurrito: " + name + ".\n" +
                "Id: " + id + ".\n" +
                "Distance: " + distance + ".\n" +
                "Menu: " +
                item1 + ", " +
                item2 + ", " +
                item3 + ".";

        Assert.assertEquals(expectedString, casa.toString());
    }

    @Test
    public void compareTo_thisBefore() {
        setCasaWithId(1);
        CasaDeBurrito greaterCasa = getNewCasaWithId(2);
        Assert.assertTrue(casa.compareTo(greaterCasa) < 0);
        Assert.assertTrue(greaterCasa.compareTo(casa) > 0);
    }

    @Test
    public void compareTo_thisAfter() {
        setCasaWithId(2);
        CasaDeBurrito lowerCase = getNewCasaWithId(1);
        Assert.assertTrue(casa.compareTo(lowerCase) > 0);
        Assert.assertTrue(lowerCase.compareTo(casa) < 0);
    }

    @Test
    public void compareTo_equals() {
        setCasaWithId(1);
        CasaDeBurrito sameCasa = getNewCasaWithId(1);
        Assert.assertEquals(0, casa.compareTo(sameCasa));
        Assert.assertEquals(0, sameCasa.compareTo(casa));
    }

    @Test
    public void compareTo_equalsToItself() {
        //noinspection EqualsWithItself
        Assert.assertEquals(0, casa.compareTo(casa));
    }
}
