package OOP.Tests.UnitTests;

import OOP.Provided.CartelDeNachos;
import OOP.Provided.CasaDeBurrito;
import OOP.Provided.Profesor;
import OOP.Solution.CartelDeNachosImpl;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static junit.framework.TestCase.assertEquals;

public class CartelDeNachosTests {
    private CartelDeNachos cdn1;

    @Rule
    public ExpectedException thrown = ExpectedException.none();


    @Before
    public void setUp(){
        cdn1 = new CartelDeNachosImpl();
    }

    @Test
    public void testEmptySetRegisteredProfesors(){
        assert(cdn1.registeredProfesores().isEmpty());
    }

    @Test
    public void testEmptySetRegisteredCasas(){
        assert(cdn1.registeredCasasDeBurrito().isEmpty());
    }

    @Test
    public void testProfesorNotInCollection() throws Profesor.ProfesorNotInSystemException{
        thrown.expect(Profesor.ProfesorNotInSystemException.class);
        cdn1.getProfesor(1);
    }

    @Test
    public void testCasaNotInCollection() throws CasaDeBurrito.CasaDeBurritoNotInSystemException{
        thrown.expect(CasaDeBurrito.CasaDeBurritoNotInSystemException.class);
        cdn1.getCasaDeBurrito(1);
    }




    @Test
    public void testEmptyToString(){
        assertEquals(cdn1.toString(),
                    "Registered profesores: .\n" +
                            "Registered casas de burrito: .\n" +
                            "Profesores:\n" +
                            "End profesores."
                );
    }
}
