package OOP.Tests.tzlil.utils;

import org.junit.Before;

import java.util.Collections;
import java.util.Random;
import java.util.Set;

import OOP.Provided.CasaDeBurrito;
import OOP.Provided.Profesor;
import OOP.Solution.CasaDeBurritoImpl;

public class CasaDeBurritoImplBaseTest
        extends BaseTest {

    protected CasaDeBurrito casa;

    @Before
    public void initDefaultCasa() {
        setRandomCasa();
    }

    protected CasaDeBurrito getDuplicateCasa() {
        int id = 1;
        String name = "1";
        int distance = 1;
        Set<String> menu = Collections.emptySet();
        setCasa(id, name, distance, menu);
        return getNewCasa(id, name, distance, menu);
    }

    protected CasaDeBurrito getDuplicateDerivedCasa() {
        int id = 1;
        String name = "1";
        int distance = 1;
        Set<String> menu = Collections.emptySet();
        setCasa(id, name, distance, menu);
        return new CasaDeBurritoImpl(id, name, distance, menu) {
        };
    }

    protected CasaDeBurrito getDifferentCasa() {
        setCasaWithId(1);
        return getNewCasaWithId(2);
    }

    protected void setCasa(int id, String name, int dist, Set<String> menu) {
        casa = getNewCasa(id, name, dist, menu);
    }

    private void setRandomCasa() {
        setCasa(counter, String.valueOf(counter), counter, Collections.emptySet());
        counter++;
    }

    protected void setCasaWithId(int id) {
        setCasa(id, String.valueOf(counter), counter, Collections.emptySet());
        counter++;
    }

    protected void setCasaWithName(String name) {
        setCasa(counter, name, counter, Collections.emptySet());
        counter++;
    }

    protected void setCasaWithDistance(int distance) {
        setCasa(counter, String.valueOf(counter), distance, Collections.emptySet());
        counter++;
    }

    protected CasaDeBurrito rateCasaWrapper(Profesor profesor, int rate) {
        return super.rateCasaWrapper(casa, profesor, rate);
    }

    protected void testRate_failWithValue(int value)
            throws CasaDeBurrito.RateRangeException {
        expectException(CasaDeBurrito.RateRangeException.class);
        rateWithRandomProfesor(value);
    }

    protected void testRate_successWithValue(int value)
            throws CasaDeBurrito.RateRangeException {
        rateWithRandomProfesor(value);
    }

    protected CasaDeBurrito rateWithRandomProfesor(int rate)
            throws CasaDeBurrito.RateRangeException {
        return casa.rate(getNewRandomProfesor(), rate);
    }

    protected CasaDeBurrito randomRateWithRandomProfesor() {
        return randomRateWithProfesor(getNewRandomProfesor());
    }

    protected CasaDeBurrito randomRateWithProfesor(Profesor profesor) {
        return rateCasaWrapper(casa, profesor, new Random().nextInt(6));
    }
}
