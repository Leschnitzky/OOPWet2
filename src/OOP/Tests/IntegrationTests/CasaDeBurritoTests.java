package OOP.Tests.IntegrationTests;

import OOP.Provided.CasaDeBurrito;
import OOP.Provided.Profesor;
import OOP.Solution.CasaDeBurritoImpl;
import OOP.Solution.ProfesorImpl;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import static junit.framework.TestCase.*;

public class CasaDeBurritoTests{
    private CasaDeBurritoImpl cdb1;
    private CasaDeBurritoImpl cdb2;
    private CasaDeBurritoImpl cdb3;
    private Profesor pf1;
    private Profesor pf2;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void setUp() {
        cdb1 = new CasaDeBurritoImpl(1,"CDB1",4, Stream.of("A","B","C").collect(Collectors.toSet()));
        cdb2 = new CasaDeBurritoImpl(2,"CDB2",4, Stream.of("A","B","C").collect(Collectors.toSet()));
        cdb3 = new CasaDeBurritoImpl(3,"CDB3",4, Stream.of("A","B","C").collect(Collectors.toSet()));

        pf1 = new ProfesorImpl(1,"Yoni shvartz");
        pf2 = new ProfesorImpl(2,"Yoni shvartz 2");

    }

    @Test
    public void testRatingExceedsRange() throws CasaDeBurrito.RateRangeException {
        thrown.expect(CasaDeBurrito.RateRangeException.class);
        cdb1.rate(pf1,7);
        cdb1.rate(pf1, -3);
    }

    @Test
    public void testRatingReturnsSelf(){
        try {
            cdb1.rate(pf1,5).rate(pf1,2);
        } catch (CasaDeBurrito.RateRangeException e) {
            fail();
        }
    }

    @Test
    public void testNormalRating(){
        try {
            cdb1.rate(pf1,3);
        } catch (CasaDeBurrito.RateRangeException e) {
            fail();
        }
    }

    @Test
    public void testIsRatedByProfesor(){
        try {
            cdb1.rate(pf1,4);
        } catch (CasaDeBurrito.RateRangeException e) {
            fail();
        }
        assert(cdb1.isRatedBy(pf1));
    }

    @Test
    public void testMultipleRatingsByProfesors() throws CasaDeBurrito.RateRangeException {
        cdb1.rate(pf1,3);
        assertFalse(cdb1.isRatedBy(pf2));
        cdb1.rate(pf2,2);
        assert(cdb1.isRatedBy(pf2));
    }

    @Test
    public void testRatingMultipleCasaOneProf() throws CasaDeBurrito.RateRangeException {
        cdb1.rate(pf1,3);
        cdb2.rate(pf1,5);

        assert(cdb1.isRatedBy(pf1));
        assert(cdb2.isRatedBy(pf1));
    }

    @Test
    public void testNumberOfRates() throws CasaDeBurrito.RateRangeException {
        assertEquals(cdb1.numberOfRates(),0);
        cdb1.rate(pf1,3);
        cdb1.rate(pf2,5);
        assertEquals(cdb1.numberOfRates(),2);
    }

    @Test
    public void testNumberOfRatesDoesntTransfer() throws CasaDeBurrito.RateRangeException {
        cdb1.rate(pf1,3);
        cdb2.rate(pf2,5);

        assertEquals(cdb1.numberOfRates(),1);
        assertEquals(cdb2.numberOfRates(),1);
    }

    @Test
    public void testNumberOfRatesInUpdate() throws CasaDeBurrito.RateRangeException {
        cdb1.rate(pf1,3).rate(pf1,2);
        assertEquals(cdb1.numberOfRates(),1);
    }

    @Test
    public void testAverageRatingOfEmpty(){
        assertEquals(cdb1.averageRating(),0.0);
    }

    @Test
    public void testAverageRating() throws CasaDeBurrito.RateRangeException {
        cdb1.rate(pf1,5).rate(pf2,3);
        assertEquals(cdb1.averageRating(),4.0);
    }

    @Test
    public void testAveratgeRatingInUpdate() throws CasaDeBurrito.RateRangeException {
        cdb1.rate(pf1,5).rate(pf1,2);
        assertEquals(cdb1.averageRating(),2.0);
    }
}
