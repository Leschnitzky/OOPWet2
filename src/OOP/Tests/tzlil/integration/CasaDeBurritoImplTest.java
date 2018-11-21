package OOP.Tests.tzlil.integration;

import org.junit.Assert;
import org.junit.Test;

import OOP.Provided.CasaDeBurrito;
import OOP.Provided.Profesor;
import OOP.Tests.tzlil.utils.CasaDeBurritoImplBaseTest;

public class CasaDeBurritoImplTest
        extends CasaDeBurritoImplBaseTest {

    @Test
    public void rate_ChangeRateCount() {
        Assert.assertEquals(0, casa.numberOfRates());
        randomRateWithRandomProfesor();
        Assert.assertEquals(1, casa.numberOfRates());
        randomRateWithRandomProfesor();
        Assert.assertEquals(2, casa.numberOfRates());
    }

    @Test
    public void rate_TwiceBySameProfesorDoNotChangeRateCount() {
        Assert.assertEquals(0, casa.numberOfRates());
        Profesor profesor = getNewRandomProfesor();
        rateCasaWrapper(profesor, 1);
        Assert.assertEquals(1, casa.numberOfRates());
        rateCasaWrapper(profesor, 2);
        Assert.assertEquals(1, casa.numberOfRates());
    }

    @Test
    public void rate_ChangeAverageRating()
            throws CasaDeBurrito.RateRangeException {
        assertEqualsDouble(0, casa.averageRating());
        rateWithRandomProfesor(2);
        assertEqualsDouble(2, casa.averageRating());
        rateWithRandomProfesor(3);
        assertEqualsDouble(2.5, casa.averageRating());
    }

    @Test
    public void rate_TwiceChangeAverageRating() {
        assertEqualsDouble(0, casa.averageRating());
        Profesor profesor = getNewRandomProfesor();
        rateCasaWrapper(profesor, 3);
        assertEqualsDouble(3, casa.averageRating());
        rateCasaWrapper(profesor, 2);
        assertEqualsDouble(2, casa.averageRating());
    }

    @Test
    public void ifEqualsThenSameHash() {
        CasaDeBurrito duplicateCasa = getDuplicateCasa();
        //noinspection SimplifiableJUnitAssertion
        Assert.assertTrue(casa.equals(duplicateCasa));
        Assert.assertEquals(casa.hashCode(), duplicateCasa.hashCode());
    }

    @Test
    public void ifDifferentHashThenNotEquals() {
        CasaDeBurrito differentCasa = getDifferentCasa();
        Assert.assertNotEquals(casa.hashCode(), differentCasa.hashCode());
        //noinspection SimplifiableJUnitAssertion
        Assert.assertFalse(casa.equals(differentCasa));
    }
}
