package OOP.Tests.UnitTests;

import OOP.Provided.CasaDeBurrito;
import OOP.Provided.Profesor;
import OOP.Provided.Profesor.*;
import OOP.Solution.ProfesorImpl;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.Collection;
import java.util.Set;

import static junit.framework.TestCase.*;

public class ProfesorTests {
    private ProfesorImpl pf1;
    private ProfesorImpl pf2;
    private ProfesorImpl pf3;
    private ProfesorImpl pf4;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void setUp(){
        int id1 = 1,id2 = 2,id3 = 3;
        String name1 = "Chan chen chaniv", name2 = "Tzenzor", name3 = "Leonid Phishkin",name4 = "Gil Bareket";
        pf1 = new ProfesorImpl(id1,name1);
        pf2 = new ProfesorImpl(id2,name2);
        pf3 = new ProfesorImpl(id1,name3);
        pf4 = new ProfesorImpl(id3,name4);
    }

    @Test
    public void testProfesorCreation(){
        assertNotNull(pf1);
    }

    @Test
    public void testGetID(){
        int id = pf1.getId();
        assertEquals(id,1);
    }

    @Test
    public void testGetEmptyRestaurants(){
        Collection<CasaDeBurrito> restaurants = pf1.favorites();
        assertNotNull(restaurants);
        assert (restaurants.isEmpty());
    }

    @Test
    public void testAddingAFriend(){
        try{
            pf1.addFriend(pf2);
        } catch(Exception e) {
            fail();
        }
    }

    @Test
    public void testAddingSelf() throws SameProfesorException {
        thrown.expect(SameProfesorException.class);
        try {
            pf1.addFriend(pf1);
        }  catch (ConnectionAlreadyExistsException e) {
            fail();
        }
    }

    @Test
    public void testAddingSomeoneInside() throws ConnectionAlreadyExistsException {
        thrown.expect(ConnectionAlreadyExistsException.class);
        try {
            pf1.addFriend(pf2).addFriend(pf2);
        } catch (SameProfesorException e) {
            fail();
        }
    }

    @Test
    public void testAddingEqualsProfesor() throws SameProfesorException {
        thrown.expect(SameProfesorException.class);
        try {
            pf1.addFriend(pf3);
        } catch (ConnectionAlreadyExistsException e) {
            fail();
        }
    }

    @Test
    public void testEmptyNumberOfFriends(){
        assert(pf1.getFriends().isEmpty());
    }

    @Test
    public void testNotEmptyCollectionOfFriends(){
        try {
            pf1.addFriend(pf2);
        } catch (Exception e){
            fail();
        }
        assertEquals(pf1.getFriends().size(), 1);
        assert(pf1.getFriends().contains(pf2));

        try {
            pf1.addFriend(pf4);
        } catch(Exception e){
            fail();
        }

        assertEquals(pf1.getFriends().size(),2);
        assert(pf1.getFriends().contains(pf4));
    }

    @Test
    public void testFriendCollectionDoesntChange(){
        try {
            pf1.addFriend(pf2);
        } catch (Exception e){
            fail();
        }

        Set<Profesor> friends = pf1.getFriends();
        friends.add(pf4);
        assertFalse(pf1.getFriends().contains(pf4));
    }

    @Test
    public void testBasicFilteringByID(){
        try {
            pf1.addFriend(pf2).addFriend(pf4);
        } catch (Exception e){
            fail();
        }
        assertEquals(pf1.filteredFriends(
                (x) -> (x.getId() == 3)
                ).size(),1
                );
    }

    @Test
    public void testFilterAll(){
        try {
            pf1.addFriend(pf2).addFriend(pf4);
        } catch (Exception e){
            fail();
        }
        assertEquals(pf1.filteredFriends(
                (x) -> false
        ).size(),0);
    }

    @Test
    public void testProfesorEquality(){
        assert(pf1.equals(pf3));
    }

    @Test
    public void testSelfEquality(){
        assert(pf1.equals(pf1));
    }

    @Test
    public void testProfesorUnequality(){
        assertFalse(pf1.equals(pf2));
    }

    @Test
    public void testNullUnequality(){
        assertFalse(pf1.equals(null));
    }

    @Test
    public void testDifferentObjectUnequality(){
        Integer m = 3;
        assertFalse(pf1.equals(m));
    }

    @Test
    public void testCompareProfessors(){
        assert(pf1.compareTo(pf2) < 0);
        assert(pf2.compareTo(pf1) > 0);
    }

    @Test
    public void testToStringFormat(){
        assertEquals(pf1.toString(),
                "Profesor: Chan chen chaniv.\n" +
                        "Id: 1.\n" +
                        "Favorites: ."
                );
    }
}
