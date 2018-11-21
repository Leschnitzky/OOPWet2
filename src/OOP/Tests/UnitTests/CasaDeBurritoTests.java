package OOP.Tests.UnitTests;

import OOP.Provided.CasaDeBurrito;
import OOP.Solution.CasaDeBurritoImpl;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static junit.framework.TestCase.*;

public class CasaDeBurritoTests {
    private CasaDeBurritoImpl cdb1;
    private CasaDeBurritoImpl cdb2;
    private CasaDeBurritoImpl cdb3;
    @Before
    public void setUP(){
//      Add a creation
        int id = 1,dist = 1;
        String name = "Rancho Del Carno";
        Set<String> menu = Stream.of("A","B","C").collect(Collectors.toSet());
        cdb1 = new CasaDeBurritoImpl(id,name,dist,menu);
        int id2 = 2,dist2 = 2;
        String name2 = "Rancho Del Carno 2";
        cdb2 = new CasaDeBurritoImpl(id2,name2,dist2,menu);
        int dist3 = 3;
        String name3 = "Rancho Del Carno 3";
        cdb3 = new CasaDeBurritoImpl(id,name3,dist3,menu);

    }
    @Test
    public void testCasaDeBurritoCreation(){
        assertNotNull(cdb1);
    }

    @Test
    public void testCasaDeBurritoGetId(){
        int testId = cdb1.getId();
        assertNotNull(testId);
        assert(testId == 1);
    }

    @Test
    public void testGetName(){
        String name =  cdb1.getName();
        assertNotNull(name);
        assert(name.equals("Rancho Del Carno"));
    }

    @Test
    public void testDistance(){
        int distance = cdb1.distance();
        assertNotNull(distance);
        assert(distance == 1);
    }

    @Test
    public void testEqualsToNotNull(){
        assertFalse(cdb1.equals(null));
    }

    @Test
    public void testEqualsToAnObjectThatIsNotEqual(){
        assertFalse(cdb1.equals(cdb2));
    }

    @Test
    public void testEqualsToAnEqualObject(){
        assert(cdb1.equals(cdb3));
    }

    @Test
    public void testGetNumberOfRates(){
        int rates = cdb1.numberOfRates();
        assert (rates == 0);
    }

    @Test
    public void testGetAverage(){
        double avg = cdb1.averageRating();
        assert (avg == 0);
    }

    @Test
    public void testEqualsOnADifferentObject(){
        Integer m = 4;
        assertFalse(cdb1.equals(m));
    }

    @Test
    public void testToStringFormat(){
        assertEquals(cdb1.toString(),
                "CasaDeBurrito: Rancho Del Carno.\n" +
                        "Id: 1.\n" +
                        "Distance: 1.\n" +
                        "Menu: A, B, C."
        );
    }

    @Test
    public void testCompareTo(){
        assert (cdb1.compareTo(cdb2) < 0);
        assert (cdb2.compareTo(cdb1) > 0);
    }

    @Test
    public void testHashDifference(){
        assertEquals(new CasaDeBurritoImpl(1,"Taco Place",2, Stream.of("A","B","C").collect(Collectors.toSet())).hashCode(),
                new CasaDeBurritoImpl(1,"Taco Place",2, Stream.of("A","B","C").collect(Collectors.toSet())).hashCode()
                );
        assertNotSame(new CasaDeBurritoImpl(1,"Taco Place",2, Stream.of("A","B","C").collect(Collectors.toSet())).hashCode(),
                new CasaDeBurritoImpl(2,"Taco Place",2, Stream.of("A","B","C").collect(Collectors.toSet())).hashCode());
        assertEquals(new CasaDeBurritoImpl(1,"Taco Place2",5, Stream.of("A1","B","C").collect(Collectors.toSet())).hashCode(),
                new CasaDeBurritoImpl(1,"Taco Place",2, Stream.of("A","B","C").collect(Collectors.toSet())).hashCode());
    }


}
