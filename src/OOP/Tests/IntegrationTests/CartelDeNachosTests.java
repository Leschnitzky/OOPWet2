package OOP.Tests.IntegrationTests;

import OOP.Provided.CartelDeNachos;
import OOP.Provided.CasaDeBurrito;
import OOP.Provided.Profesor;
import OOP.Solution.CartelDeNachosImpl;
import OOP.Solution.CasaDeBurritoImpl;
import OOP.Solution.ProfesorImpl;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.fail;

public class CartelDeNachosTests {

    private CartelDeNachos cdn1;
    private CartelDeNachos cdn2;
    private CartelDeNachos cdn3;



    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void setUp(){
        cdn1 = new CartelDeNachosImpl();
        cdn2 = new CartelDeNachosImpl();
        cdn3 = new CartelDeNachosImpl();
    }

    @Test
    public void testJoinCartel() throws Profesor.ProfesorAlreadyInSystemException, Profesor.ProfesorNotInSystemException {
        cdn1.joinCartel(1,"Cartel 1");
        assertNotNull(cdn1.getProfesor(1));
    }

    @Test
    public void testReturnAProfesorInstanceJoinCartel() throws Profesor.ProfesorAlreadyInSystemException{
        Profesor pf = cdn1.joinCartel(1,"Prof 1");
        assertNotNull(pf);
        cdn1.registeredProfesores().contains(pf);
    }

    @Test
    public void testProfesorAlreadyInSystemJoinCartel() throws Profesor.ProfesorAlreadyInSystemException{
        thrown.expect(Profesor.ProfesorAlreadyInSystemException.class);
        cdn1.joinCartel(1,"Chuki");
        cdn1.joinCartel(1,"Another");
    }

    @Test
    public void testAddCasaDeBurrito() throws CasaDeBurrito.CasaDeBurritoAlreadyInSystemException, CasaDeBurrito.CasaDeBurritoNotInSystemException {
        cdn1.addCasaDeBurrito(1,"Casa 1",3, Stream.of("A","B","C").collect(Collectors.toSet()));
        assertNotNull(cdn1.getCasaDeBurrito(1));
    }

    @Test
    public void testReturnACasaInstanceAddCasa() throws CasaDeBurrito.CasaDeBurritoAlreadyInSystemException{
        CasaDeBurrito cdb = cdn1.addCasaDeBurrito(1,"Casa 1",3, Stream.of("A","B","C").
                collect(Collectors.toSet()));
        assertNotNull(cdb);
        cdn1.registeredCasasDeBurrito().contains(cdb);
    }

    @Test
    public void testCasaAlreadyInSystemAddCasa() throws CasaDeBurrito.CasaDeBurritoAlreadyInSystemException{
        thrown.expect(CasaDeBurrito.CasaDeBurritoAlreadyInSystemException.class);
        cdn1.addCasaDeBurrito(1,"Casa 1",3, Stream.of("A","B","C").collect(Collectors.toSet()));
        cdn1.addCasaDeBurrito(1,"Casa 2",5, Stream.of("C","B","A").collect(Collectors.toSet()));
    }

    @Test
    public void testRegisteredProfesorsNotEmpty() throws Profesor.ProfesorAlreadyInSystemException {
        cdn1.joinCartel(1,"prof 1");
        assertEquals(cdn1.registeredProfesores().size(),1);
    }

    @Test
    public void testRegisteredCasasNotEmpty() throws CasaDeBurrito.CasaDeBurritoAlreadyInSystemException {
        cdn1.addCasaDeBurrito(1,"Casa 1",3, Stream.of("A","B","C").collect(Collectors.toSet()));
        assertEquals(cdn1.registeredCasasDeBurrito().size(),1);
    }

    @Test
    public void testRegisteredProfessorChanges() throws Profesor.ProfesorAlreadyInSystemException{
        cdn1.joinCartel(1,"prof 1");
        Collection<Profesor> profs = cdn1.registeredProfesores();
        cdn1.joinCartel(2,"prof 2");
        assertEquals(profs.size(),1);
    }

    @Test
    public void testRegisteredCasaChanges() throws CasaDeBurrito.CasaDeBurritoAlreadyInSystemException{
        cdn1.addCasaDeBurrito(1,"Casa 1",3, Stream.of("A","B","C").collect(Collectors.toSet()));
        Collection<CasaDeBurrito> casas = cdn1.registeredCasasDeBurrito();
        cdn1.addCasaDeBurrito(2,"Casa 2",3, Stream.of("A","B","C").collect(Collectors.toSet()));
        assertEquals(casas.size(),1);
    }

    @Test
    public void testGetProfessor() throws Profesor.ProfesorNotInSystemException, Profesor.ProfesorAlreadyInSystemException {
        cdn1.joinCartel(1,"Prof 1");
        Profesor pf = cdn1.getProfesor(1);
        assertNotNull(pf);
    }

    @Test
    public void testGetCasa() throws CasaDeBurrito.CasaDeBurritoAlreadyInSystemException,CasaDeBurrito.CasaDeBurritoNotInSystemException{
        cdn1.addCasaDeBurrito(1,"Casa 1",3, Stream.of("A","B","C").collect(Collectors.toSet()));
        CasaDeBurrito cdb = cdn1.getCasaDeBurrito(1);
        assertNotNull(cdb);
    }

    @Test
    public void testBothConnectionProfessorsNotInCollection() throws Profesor.ProfesorNotInSystemException, Profesor.ConnectionAlreadyExistsException, Profesor.SameProfesorException {
        thrown.expect(Profesor.ProfesorNotInSystemException.class);
        Profesor pf = new ProfesorImpl(1,"Omri");
        Profesor pf2 = new ProfesorImpl(2,"Imri");
        cdn1.addConnection(pf,pf2);
    }

    @Test
    public void testOneConnectionProfessorNotInCollection() throws Profesor.ProfesorAlreadyInSystemException, Profesor.ProfesorNotInSystemException, Profesor.SameProfesorException, Profesor.ConnectionAlreadyExistsException {
        thrown.expect(Profesor.ProfesorNotInSystemException.class);
        Profesor pf = new ProfesorImpl(1,"Omri");
        Profesor pf2 = new ProfesorImpl(2,"Imri");
        cdn1.joinCartel(1,"Omri");
        cdn2.joinCartel(2,"Imri");

        cdn1.addConnection(pf,pf2);
        cdn2.addConnection(pf,pf2);

    }

    @Test
    public void testSameProfesorConnection() throws Profesor.ProfesorAlreadyInSystemException, Profesor.ProfesorNotInSystemException, Profesor.SameProfesorException, Profesor.ConnectionAlreadyExistsException {
        thrown.expect(Profesor.SameProfesorException.class);
        Profesor pf = new ProfesorImpl(1,"Omri");
        cdn1.joinCartel(1,"Omri");

        cdn1.addConnection(pf,pf);
    }

    @Test
    public void testAddAConnection() throws Profesor.ProfesorAlreadyInSystemException {
        Profesor pf = new ProfesorImpl(1,"Omri");
        Profesor pf2 = new ProfesorImpl(2,"Imri");
        cdn1.joinCartel(1,"Omri");
        cdn1.joinCartel(2,"Imri");

        try {
            cdn1.addConnection(pf,pf2);
        } catch (Exception e){
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void testAddSameConnection() throws Profesor.ProfesorAlreadyInSystemException, Profesor.ProfesorNotInSystemException, Profesor.SameProfesorException, Profesor.ConnectionAlreadyExistsException {
        thrown.expect(Profesor.ConnectionAlreadyExistsException.class);
        Profesor pf = new ProfesorImpl(1,"Omri");
        Profesor pf2 = new ProfesorImpl(2,"Imri");
        cdn1.joinCartel(1,"Omri");
        cdn1.joinCartel(2,"Imri");
        cdn1.addConnection(pf,pf2);
        cdn1.addConnection(pf,pf2);
    }

    @Test
    public void testSymetricConnection() throws Profesor.ProfesorAlreadyInSystemException, Profesor.ProfesorNotInSystemException, Profesor.SameProfesorException, Profesor.ConnectionAlreadyExistsException {
        thrown.expect(Profesor.ConnectionAlreadyExistsException.class);
        Profesor pf1 = new ProfesorImpl(3,"Omri");
        Profesor pf3 = new ProfesorImpl(5,"Imri");
        cdn1.joinCartel(3,"Omri");
        cdn1.joinCartel(5,"Imri");
        cdn1.addConnection(pf1,pf3);
        cdn1.addConnection(pf3,pf1);
    }

    @Test
    public void testInstanceReturnConnection() throws Profesor.ProfesorAlreadyInSystemException, Profesor.ProfesorNotInSystemException, Profesor.SameProfesorException, Profesor.ConnectionAlreadyExistsException {
        Profesor pf1 = new ProfesorImpl(3,"Omri");
        Profesor pf3 = new ProfesorImpl(5,"Imri");
        Profesor pf5 = new ProfesorImpl(8,"Tomri");
        cdn1.joinCartel(3,"Omri");
        cdn1.joinCartel(5,"Imri");
        cdn1.joinCartel(8,"Tomri");
        CartelDeNachos cdn = cdn1.addConnection(pf1,pf3).addConnection(pf5,pf3);
        assertNotNull(cdn);
    }

    @Test
    public void testProfesorNotInSystemFavoriteByRating() throws Profesor.ProfesorNotInSystemException{
        thrown.expect(Profesor.ProfesorNotInSystemException.class);
        Profesor pf1 = new ProfesorImpl(3,"Omri");
        cdn1.favoritesByRating(pf1);
    }

    @Test
    public void testProfesorNotInSystemFavoriteByDist() throws Profesor.ProfesorNotInSystemException{
        thrown.expect(Profesor.ProfesorNotInSystemException.class);
        Profesor pf1 = new ProfesorImpl(3,"Omri");
        cdn1.favoritesByDist(pf1);
    }

    @Test
    public void testFriendlessProfessorInFavoriteByDist() throws Profesor.ProfesorAlreadyInSystemException, Profesor.ProfesorNotInSystemException {
        Profesor pf1 = new ProfesorImpl(1,"Udu");
        cdn1.joinCartel(1,"Udu");
        assertNotNull(cdn1.favoritesByDist(pf1));
        assert(cdn1.favoritesByDist(pf1).isEmpty());
    }

    @Test
    public void testFriendlessProfessorInFavoriteByRating() throws Profesor.ProfesorAlreadyInSystemException, Profesor.ProfesorNotInSystemException {
        Profesor pf1 = new ProfesorImpl(1,"Udu");
        cdn1.joinCartel(1,"Udu");
        assertNotNull(cdn1.favoritesByRating(pf1));
        assert(cdn1.favoritesByRating(pf1).isEmpty());
    }

    @Test
    public void testFriendsWithNoRecommendationsFavoriteByDist() throws Profesor.ProfesorAlreadyInSystemException, Profesor.ProfesorNotInSystemException, Profesor.SameProfesorException, Profesor.ConnectionAlreadyExistsException {
        Profesor pf1 = new ProfesorImpl(1,"Udu");
        Profesor pf2 = new ProfesorImpl(2,"Yoni");
        cdn1.joinCartel(1,"Udu");
        cdn1.joinCartel(2,"Yoni");

        cdn1.addConnection(pf1,pf2);

        assertNotNull(cdn1.favoritesByDist(pf1));
        assert(cdn1.favoritesByDist(pf1).isEmpty());
    }

    @Test
    public void testFriendsWithNoRecommendationsFavoriteByRating() throws Profesor.ProfesorAlreadyInSystemException, Profesor.ProfesorNotInSystemException, Profesor.SameProfesorException, Profesor.ConnectionAlreadyExistsException {
        Profesor pf1 = new ProfesorImpl(2,"Udu");
        Profesor pf2 = new ProfesorImpl(4,"Yoni");
        cdn1.joinCartel(2,"Udu");
        cdn1.joinCartel(4,"Yoni");

        cdn1.addConnection(pf1,pf2);

        assertNotNull(cdn1.favoritesByRating(pf1));
        assert(cdn1.favoritesByRating(pf1).isEmpty());
    }

    @Test
    public void testFavoritesByRatingAndDistFirstOrder() throws Profesor.ProfesorAlreadyInSystemException, Profesor.ProfesorNotInSystemException, CasaDeBurrito.CasaDeBurritoAlreadyInSystemException, CasaDeBurrito.CasaDeBurritoNotInSystemException, Profesor.UnratedFavoriteCasaDeBurritoException, CasaDeBurrito.RateRangeException, Profesor.ConnectionAlreadyExistsException, Profesor.SameProfesorException {

        cdn1.joinCartel(2,"Udu");
        cdn1.joinCartel(4,"Yoni");
        cdn1.joinCartel(6,"Uri");

        Profesor yoni = cdn1.getProfesor(4);
        Profesor test_prof = cdn1.getProfesor(6);
        cdn1.addCasaDeBurrito(1,"Casa 1",3, Stream.of("A","B","C").collect(Collectors.toSet()));
        cdn1.addCasaDeBurrito(2,"Casa 2",3, Stream.of("A","B","C").collect(Collectors.toSet()));
        CasaDeBurrito test_cdb = cdn1.getCasaDeBurrito(1);
        test_cdb.rate(test_prof,4);
        test_prof.favorite(test_cdb);
        Profesor test_prof2 = cdn1.getProfesor(2);
        CasaDeBurrito test_cdb2 = cdn1.getCasaDeBurrito(2);
        test_cdb2.rate(test_prof2,5);
        test_prof2.favorite(test_cdb2);

        cdn1.addConnection(yoni,test_prof);
        cdn1.addConnection(yoni,test_prof2);

        Collection<CasaDeBurrito> rate_col = cdn1.favoritesByRating(yoni);
        Collection<CasaDeBurrito> dist_col = cdn1.favoritesByDist(yoni);

        assert(rate_col.toArray()[0].equals(test_cdb2));
        assert(dist_col.toArray()[0].equals(test_cdb2));
    }

    @Test
    public void testFavoriteByRatingSecondOrder() throws Profesor.ProfesorAlreadyInSystemException, Profesor.ProfesorNotInSystemException, CasaDeBurrito.CasaDeBurritoAlreadyInSystemException, CasaDeBurrito.CasaDeBurritoNotInSystemException, CasaDeBurrito.RateRangeException, Profesor.UnratedFavoriteCasaDeBurritoException, Profesor.ConnectionAlreadyExistsException, Profesor.SameProfesorException {
        cdn1.joinCartel(4,"Yoni");
        cdn1.joinCartel(6,"Uri");

        Profesor yoni = cdn1.getProfesor(4);
        Profesor test_prof = cdn1.getProfesor(6);
        cdn1.addCasaDeBurrito(1,"Casa 1",3, Stream.of("A","B","C").collect(Collectors.toSet()));
        cdn1.addCasaDeBurrito(2,"Casa 2",3, Stream.of("A","B","C").collect(Collectors.toSet()));
        CasaDeBurrito test_cdb = cdn1.getCasaDeBurrito(1);
        test_cdb.rate(test_prof,4);
        test_prof.favorite(test_cdb);
        CasaDeBurrito test_cdb2 = cdn1.getCasaDeBurrito(2);
        test_cdb2.rate(test_prof,5);
        test_prof.favorite(test_cdb2);

        cdn1.addConnection(yoni,test_prof);

        Collection<CasaDeBurrito> rate_col = cdn1.favoritesByRating(yoni);

        assert(rate_col.toArray()[0].equals(test_cdb2));
    }

    @Test
    public void testFavoriteByDistanceSecondOrder() throws Profesor.ProfesorAlreadyInSystemException, Profesor.ProfesorNotInSystemException, CasaDeBurrito.CasaDeBurritoAlreadyInSystemException, CasaDeBurrito.CasaDeBurritoNotInSystemException, CasaDeBurrito.RateRangeException, Profesor.UnratedFavoriteCasaDeBurritoException, Profesor.ConnectionAlreadyExistsException, Profesor.SameProfesorException {

        cdn1.joinCartel(4,"Yoni");
        cdn1.joinCartel(6,"Uri");

        Profesor yoni = cdn1.getProfesor(4);
        Profesor test_prof = cdn1.getProfesor(6);
        cdn1.addCasaDeBurrito(1,"Casa 1",4, Stream.of("A","B","C").collect(Collectors.toSet()));
        cdn1.addCasaDeBurrito(2,"Casa 2",5, Stream.of("A","B","C").collect(Collectors.toSet()));
        CasaDeBurrito test_cdb = cdn1.getCasaDeBurrito(1);
        test_cdb.rate(test_prof,4);
        test_prof.favorite(test_cdb);
        CasaDeBurrito test_cdb2 = cdn1.getCasaDeBurrito(2);
        test_cdb2.rate(test_prof,5);
        test_prof.favorite(test_cdb2);

        cdn1.addConnection(yoni,test_prof);

        Collection<CasaDeBurrito> rate_col = cdn1.favoritesByDist(yoni);

        assert(rate_col.toArray()[0].equals(test_cdb));
    }

    @Test
    public void testFavoriteByDistByThirdBullet() throws Profesor.ProfesorAlreadyInSystemException, Profesor.ProfesorNotInSystemException, CasaDeBurrito.CasaDeBurritoAlreadyInSystemException, CasaDeBurrito.CasaDeBurritoNotInSystemException, CasaDeBurrito.RateRangeException, Profesor.ConnectionAlreadyExistsException, Profesor.SameProfesorException, Profesor.UnratedFavoriteCasaDeBurritoException {
        cdn1.joinCartel(2,"Test Prof");
        cdn1.joinCartel(4,"Yoni");
        cdn1.joinCartel(6,"Uri");

        Profesor test_prof = cdn1.getProfesor(2);
        Profesor yoni = cdn1.getProfesor(4);
        Profesor uri = cdn1.getProfesor(6);

        cdn1.addCasaDeBurrito(1,"Casa 1",4, Stream.of("A","B","C").collect(Collectors.toSet()));
        cdn1.addCasaDeBurrito(2,"Casa 2",5, Stream.of("A","B","C").collect(Collectors.toSet()));
        cdn1.addCasaDeBurrito(3,"Casa 3",2, Stream.of("A","B","C").collect(Collectors.toSet()));

        CasaDeBurrito cdb1 = cdn1.getCasaDeBurrito(1);
        CasaDeBurrito cdb2 = cdn1.getCasaDeBurrito(2);
        CasaDeBurrito cdb3 = cdn1.getCasaDeBurrito(3);

        cdb1.rate(test_prof,5);
        cdb2.rate(test_prof,5);
        test_prof.favorite(cdb1);
        test_prof.favorite(cdb2);
        cdb3.rate(uri,5);
        uri.favorite(cdb3);

        cdn1.addConnection(yoni,test_prof);
        cdn1.addConnection(yoni,uri);

        Collection<CasaDeBurrito> dist_col = cdn1.favoritesByDist(yoni);
        assert(dist_col.toArray()[0].equals(cdb1));
    }

    @Test
    public void testFavoriteByRatingByThirdBullet() throws Profesor.ProfesorAlreadyInSystemException, Profesor.ProfesorNotInSystemException, CasaDeBurrito.CasaDeBurritoAlreadyInSystemException, CasaDeBurrito.CasaDeBurritoNotInSystemException, CasaDeBurrito.RateRangeException, Profesor.ConnectionAlreadyExistsException, Profesor.SameProfesorException, Profesor.UnratedFavoriteCasaDeBurritoException {
        cdn1.joinCartel(3,"Test Prof");
        cdn1.joinCartel(4,"Yoni");
        cdn1.joinCartel(6,"Uri");

        Profesor test_prof = cdn1.getProfesor(3);
        Profesor yoni = cdn1.getProfesor(4);
        Profesor uri = cdn1.getProfesor(6);

        cdn1.addCasaDeBurrito(1,"Casa 1",5, Stream.of("A","B","C").collect(Collectors.toSet()));
        cdn1.addCasaDeBurrito(2,"Casa 2",5, Stream.of("A","B","C").collect(Collectors.toSet()));
        cdn1.addCasaDeBurrito(3,"Casa 3",5, Stream.of("A","B","C").collect(Collectors.toSet()));

        CasaDeBurrito cdb1 = cdn1.getCasaDeBurrito(1);
        CasaDeBurrito cdb2 = cdn1.getCasaDeBurrito(2);
        CasaDeBurrito cdb3 = cdn1.getCasaDeBurrito(3);

        cdb1.rate(test_prof,3);
        cdb2.rate(test_prof,4);
        test_prof.favorite(cdb1);
        test_prof.favorite(cdb2);
        cdb3.rate(uri,5);
        uri.favorite(cdb3);

        cdn1.addConnection(yoni,test_prof);
        cdn1.addConnection(yoni,uri);

        Collection<CasaDeBurrito> dist_col = cdn1.favoritesByRating(yoni);
        assert(dist_col.toArray()[0].equals(cdb2));
    }

    @Test
    public void testMultiplicationOfFieldsForFavoritesDistAndRating() throws Profesor.ProfesorAlreadyInSystemException, Profesor.ProfesorNotInSystemException, CasaDeBurrito.CasaDeBurritoAlreadyInSystemException, CasaDeBurrito.CasaDeBurritoNotInSystemException, CasaDeBurrito.RateRangeException, Profesor.UnratedFavoriteCasaDeBurritoException, Profesor.ConnectionAlreadyExistsException, Profesor.SameProfesorException {
        cdn1.joinCartel(3,"Test Prof");
        cdn1.joinCartel(4,"Yoni");
        cdn1.joinCartel(6,"Uri");

        Profesor test_prof = cdn1.getProfesor(3);
        Profesor yoni = cdn1.getProfesor(4);
        Profesor uri = cdn1.getProfesor(6);

        cdn1.addCasaDeBurrito(1,"Casa 1",5, Stream.of("A","B","C").collect(Collectors.toSet()));
        cdn1.addCasaDeBurrito(2,"Casa 2",5, Stream.of("A","B","C").collect(Collectors.toSet()));

        CasaDeBurrito cdb1 = cdn1.getCasaDeBurrito(1);
        CasaDeBurrito cdb2 = cdn1.getCasaDeBurrito(2);

        cdb1.rate(test_prof,5);
        cdb1.rate(uri,5);
        cdb2.rate(test_prof,5);

        test_prof.favorite(cdb1);
        test_prof.favorite(cdb2);
        uri.favorite(cdb1);

        cdn1.addConnection(yoni,test_prof);
        cdn1.addConnection(yoni,uri);

        Collection<CasaDeBurrito> dist_col = cdn1.favoritesByDist(yoni);
        Collection<CasaDeBurrito> rate_col = cdn1.favoritesByRating(yoni);


        assertEquals(2,dist_col.size());
        assertEquals(2,rate_col.size());
    }

    @Test
    public void testCasasFavoritesDistAndRateOnlyByProfessor() throws Profesor.ProfesorAlreadyInSystemException, Profesor.ProfesorNotInSystemException, CasaDeBurrito.CasaDeBurritoAlreadyInSystemException, CasaDeBurrito.CasaDeBurritoNotInSystemException, CasaDeBurrito.RateRangeException, Profesor.UnratedFavoriteCasaDeBurritoException, Profesor.ConnectionAlreadyExistsException, Profesor.SameProfesorException {
        cdn1.joinCartel(3,"Test Prof");
        cdn1.joinCartel(4,"Yoni");

        Profesor test_prof = cdn1.getProfesor(3);
        Profesor yoni = cdn1.getProfesor(4);

        cdn1.addCasaDeBurrito(1,"Casa 1",5, Stream.of("A","B","C").collect(Collectors.toSet()));
        cdn1.addCasaDeBurrito(2,"Casa 1",5, Stream.of("A","B","C").collect(Collectors.toSet()));

        CasaDeBurrito cdb1 = cdn1.getCasaDeBurrito(1);
        CasaDeBurrito cdb2 = cdn1.getCasaDeBurrito(1);

        cdn1.addConnection(test_prof,yoni);

        cdb1.rate(test_prof,5);
        test_prof.favorite(cdb1);
        cdb2.rate(yoni,5);
        yoni.favorite(cdb2);

        Collection<CasaDeBurrito> rate_col_yoni = cdn1.favoritesByRating(yoni);
        Collection<CasaDeBurrito> dist_col_yoni = cdn1.favoritesByDist(yoni);
        Collection<CasaDeBurrito> rate_col_tp = cdn1.favoritesByRating(test_prof);
        Collection<CasaDeBurrito> dist_col_tp = cdn1.favoritesByDist(test_prof);

        assertEquals(1,rate_col_tp.size());
        assertEquals(rate_col_tp.size(),rate_col_yoni.size());
        assertEquals(rate_col_yoni.size(),dist_col_yoni.size());
        assertEquals(dist_col_yoni.size(),dist_col_tp.size());
    }

    @Test
    public void testNegativeConnectionRecommendation() throws Profesor.ProfesorAlreadyInSystemException, CasaDeBurrito.CasaDeBurritoAlreadyInSystemException, CasaDeBurrito.CasaDeBurritoNotInSystemException, Profesor.ProfesorNotInSystemException, CartelDeNachos.ImpossibleConnectionException {
        thrown.expect(CartelDeNachos.ImpossibleConnectionException.class);

        cdn1.joinCartel(2,"test");
        cdn1.addCasaDeBurrito(1,"Casa 1",5, Stream.of("A","B","C").collect(Collectors.toSet()));

        cdn1.getRecommendation(cdn1.getProfesor(2),cdn1.getCasaDeBurrito(1),-3);
    }

    @Test
    public void testNoProfesorInRecommendation() throws CasaDeBurrito.CasaDeBurritoAlreadyInSystemException, Profesor.ProfesorNotInSystemException, CasaDeBurrito.CasaDeBurritoNotInSystemException, CartelDeNachos.ImpossibleConnectionException {
        thrown.expect(Profesor.ProfesorNotInSystemException.class);
        cdn1.addCasaDeBurrito(1,"Casa 1",5, Stream.of("A","B","C").collect(Collectors.toSet()));
        cdn1.getRecommendation(cdn1.getProfesor(2),cdn1.getCasaDeBurrito(1),2);
    }

    @Test
    public void testNoCasaInRecommendation() throws Profesor.ProfesorAlreadyInSystemException, Profesor.ProfesorNotInSystemException, CasaDeBurrito.CasaDeBurritoNotInSystemException, CartelDeNachos.ImpossibleConnectionException {
        thrown.expect(CasaDeBurrito.CasaDeBurritoNotInSystemException.class);

        cdn1.joinCartel(2,"test");
        CasaDeBurrito cdb = new CasaDeBurritoImpl(1,"Casa 1",5, Stream.of("A","B","C").collect(Collectors.toSet()));
        cdn1.getRecommendation(cdn1.getProfesor(2),cdb,2);
    }

    @Test
    public void testRecommendationToSelf() throws Profesor.ProfesorAlreadyInSystemException, CasaDeBurrito.CasaDeBurritoAlreadyInSystemException, CasaDeBurrito.CasaDeBurritoNotInSystemException, Profesor.ProfesorNotInSystemException, CasaDeBurrito.RateRangeException, Profesor.UnratedFavoriteCasaDeBurritoException, CartelDeNachos.ImpossibleConnectionException {
        cdn1.joinCartel(2,"test");
        cdn1.addCasaDeBurrito(1,"Casa 1",5, Stream.of("A","B","C").collect(Collectors.toSet()));

        CasaDeBurrito cdb = cdn1.getCasaDeBurrito(1);
        Profesor prof = cdn1.getProfesor(2);
        cdb.rate(prof,5);
        prof.favorite(cdb);
        assert(cdn1.getRecommendation(prof,cdb,0));
    }

    @Test
    public void testGetRecommendation() throws Profesor.ProfesorAlreadyInSystemException, CasaDeBurrito.CasaDeBurritoAlreadyInSystemException, Profesor.ProfesorNotInSystemException, CasaDeBurrito.CasaDeBurritoNotInSystemException, CasaDeBurrito.RateRangeException, Profesor.UnratedFavoriteCasaDeBurritoException, Profesor.ConnectionAlreadyExistsException, Profesor.SameProfesorException, CartelDeNachos.ImpossibleConnectionException {
        cdn1.joinCartel(2,"test");
        cdn1.joinCartel(4,"test");
        cdn1.joinCartel(6,"test");

        cdn1.addCasaDeBurrito(1,"Casa 1",5, Stream.of("A","B","C").collect(Collectors.toSet()));

        Profesor first_prof = cdn1.getProfesor(2);
        Profesor second_prof = cdn1.getProfesor(4);
        Profesor third_prof = cdn1.getProfesor(6);

        CasaDeBurrito cdb = cdn1.getCasaDeBurrito(1);

        cdb.rate(third_prof,5);
        third_prof.favorite(cdb);
        cdn1.addConnection(first_prof,second_prof).addConnection(second_prof,third_prof);

        assert(cdn1.getRecommendation(first_prof,cdb,2));
    }

    @Test
    public void testEmptyGraphMostRecommendation(){
        assert(cdn1.getMostPopularRestaurantsIds().isEmpty());
    }

    @Test
    public void testSingleVertexInGraph() throws Profesor.ProfesorAlreadyInSystemException, Profesor.ProfesorNotInSystemException, CasaDeBurrito.CasaDeBurritoAlreadyInSystemException, CasaDeBurrito.CasaDeBurritoNotInSystemException, CasaDeBurrito.RateRangeException, Profesor.UnratedFavoriteCasaDeBurritoException {
        cdn1.joinCartel(2,"test");
        Profesor first_prof = cdn1.getProfesor(2);

        cdn1.addCasaDeBurrito(1,"Casa 1",5, Stream.of("A","B","C").collect(Collectors.toSet()));
        CasaDeBurrito cdb = cdn1.getCasaDeBurrito(1);
        cdb.rate(first_prof,5);
        first_prof.favorite(cdb);

        assertEquals(1,cdn1.getMostPopularRestaurantsIds().size());
        assert(cdn1.getMostPopularRestaurantsIds().toArray()[0].equals(1));
    }

    @Test
    public void testGetMostPopularRestaurantsIds() throws Profesor.ProfesorAlreadyInSystemException, CasaDeBurrito.CasaDeBurritoAlreadyInSystemException, Profesor.ProfesorNotInSystemException, CasaDeBurrito.CasaDeBurritoNotInSystemException, CasaDeBurrito.RateRangeException, Profesor.UnratedFavoriteCasaDeBurritoException, Profesor.ConnectionAlreadyExistsException, Profesor.SameProfesorException {
        cdn1.joinCartel(1,"p1");
        cdn1.joinCartel(2,"p2");
        cdn1.joinCartel(3,"p3");
        cdn1.joinCartel(4,"p4");

        cdn1.addCasaDeBurrito(1,"r1",5, Stream.of("A","B","C").collect(Collectors.toSet()));
        cdn1.addCasaDeBurrito(2,"r2",5, Stream.of("A","B","C").collect(Collectors.toSet()));
        cdn1.addCasaDeBurrito(3,"r3",5, Stream.of("A","B","C").collect(Collectors.toSet()));

        Profesor p1 = cdn1.getProfesor(1);
        Profesor p2 = cdn1.getProfesor(2);
        Profesor p3 = cdn1.getProfesor(3);
        Profesor p4 = cdn1.getProfesor(4);

        CasaDeBurrito r1 = cdn1.getCasaDeBurrito(1);
        CasaDeBurrito r2 = cdn1.getCasaDeBurrito(2);
        CasaDeBurrito r3 = cdn1.getCasaDeBurrito(3);

        r1.rate(p2,5).rate(p4,5).rate(p3,5);
        r2.rate(p1,5).rate(p4,5);
        r3.rate(p2,5).rate(p1,5).rate(p4,5);

        p1.favorite(r2).favorite(r3);
        p2.favorite(r1).favorite(r3);
        p3.favorite(r1);
        p4.favorite(r1).favorite(r2).favorite(r3);

        cdn1.addConnection(p1,p2).addConnection(p1,p3).addConnection(p1,p4).addConnection(p2,p3).addConnection(p3,p4);

        List<Integer> list = cdn1.getMostPopularRestaurantsIds();
        assertEquals(list.size(),2);
        assert(list.get(0) == 1);
        assert(list.get(1) == 3);
    }

    @Test
    public void testAllRestArePopular() throws Profesor.ProfesorAlreadyInSystemException, CasaDeBurrito.CasaDeBurritoAlreadyInSystemException, Profesor.ProfesorNotInSystemException, CasaDeBurrito.CasaDeBurritoNotInSystemException, CasaDeBurrito.RateRangeException, Profesor.UnratedFavoriteCasaDeBurritoException, Profesor.ConnectionAlreadyExistsException, Profesor.SameProfesorException {
        cdn1.joinCartel(1,"p1");
        cdn1.joinCartel(2,"p2");
        cdn1.joinCartel(3,"p3");
        cdn1.joinCartel(4,"p4");

        cdn1.addCasaDeBurrito(1,"r1",5, Stream.of("A","B","C").collect(Collectors.toSet()));
        cdn1.addCasaDeBurrito(2,"r2",5, Stream.of("A","B","C").collect(Collectors.toSet()));
        cdn1.addCasaDeBurrito(3,"r3",5, Stream.of("A","B","C").collect(Collectors.toSet()));
        cdn1.addCasaDeBurrito(4,"r3",5, Stream.of("A","B","C").collect(Collectors.toSet()));

        Profesor p1 = cdn1.getProfesor(1);
        Profesor p2 = cdn1.getProfesor(2);
        Profesor p3 = cdn1.getProfesor(3);
        Profesor p4 = cdn1.getProfesor(4);

        CasaDeBurrito r1 = cdn1.getCasaDeBurrito(1);
        CasaDeBurrito r2 = cdn1.getCasaDeBurrito(2);
        CasaDeBurrito r3 = cdn1.getCasaDeBurrito(3);
        CasaDeBurrito r4 = cdn1.getCasaDeBurrito(4);

        r1.rate(p1,5);
        r2.rate(p2,5);
        r3.rate(p3,5);
        r4.rate(p4,5);

        p1.favorite(r1);
        p2.favorite(r2);
        p3.favorite(r3);
        p4.favorite(r4);

        cdn1.addConnection(p1,p2).addConnection(p3,p4);

        assertEquals(4,cdn1.getMostPopularRestaurantsIds().size());
    }

    @Test
    public void testToStringFull() throws CasaDeBurrito.CasaDeBurritoAlreadyInSystemException, Profesor.ProfesorAlreadyInSystemException, Profesor.ProfesorNotInSystemException, Profesor.ConnectionAlreadyExistsException, Profesor.SameProfesorException {
        cdn1.joinCartel(1,"p1");
        cdn1.joinCartel(2,"p2");
        cdn1.joinCartel(3,"p3");
        cdn1.joinCartel(4,"p4");

        cdn1.addCasaDeBurrito(1,"r1",5, Stream.of("A").collect(Collectors.toSet()));
        cdn1.addCasaDeBurrito(2,"r2",5, Stream.of("B").collect(Collectors.toSet()));
        cdn1.addCasaDeBurrito(3,"r3",5, Stream.of("C").collect(Collectors.toSet()));
        cdn1.addCasaDeBurrito(4,"r3",5, Stream.of("D").collect(Collectors.toSet()));

        Profesor p1 = cdn1.getProfesor(1);
        Profesor p2 = cdn1.getProfesor(2);
        Profesor p3 = cdn1.getProfesor(3);
        Profesor p4 = cdn1.getProfesor(4);

        cdn1.addConnection(p1,p3).addConnection(p1,p2).addConnection(p1,p4).addConnection(p2,p3).addConnection(p3,p4);
        assertEquals(cdn1.toString(),
                "Registered profesores: 1, 2, 3, 4.\n" +
                        "Registered casas de burrito: 1, 2, 3, 4.\n" +
                        "Profesores:\n" +
                        "1 -> [2, 3, 4].\n" +
                        "2 -> [1, 3].\n" +
                        "3 -> [1, 2, 4].\n" +
                        "4 -> [1, 3].\n" +
                        "End profesores."
                );
    }


}
