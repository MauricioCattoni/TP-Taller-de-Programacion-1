package test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import agregado.ContratableFactory;
import excepciones.DomicilioInvalidoException;
import excepciones.ServicioInternetInvalidoException;
import interfaces.I_Contratable;
import servicios.DomicilioCasa;

public class ContratableFactoryTestCase {

	@Before
	public void setUp() throws Exception {
		
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testNuevoServicioCorrecto() {
		
		try {
			I_Contratable servicio = ContratableFactory.nuevoServicio("INTERNET100",1, 1, 1,new DomicilioCasa("Rejon",6161) );
			assertNotNull("Servicio nulo y debia ser creado", servicio);
           
		} catch (ServicioInternetInvalidoException e) {
			fail("No debia salir por Servicio Internet invalido");
		} catch (DomicilioInvalidoException e) {
			fail("No debia salir por Domicilio invalido");
		}
	}
	
	
	@Test
	public void testNuevoServicioInternetNull() {
		try {
			I_Contratable servicio = ContratableFactory.nuevoServicio(null,1, 1, 1,new DomicilioCasa("Rejon",6161) );
			fail("Debia salir por ServicioInternetInvalidoException ");
		} catch (ServicioInternetInvalidoException e) {
			//SALIDA CORRECTA
		} catch (DomicilioInvalidoException e) {
			fail("No debia salir por Domicilio invalido");
		}
	}
	
	@Test
	public void testNuevoServicioInternetVacio() {
		
		try {
			I_Contratable servicio = ContratableFactory.nuevoServicio("",1, 1, 1,new DomicilioCasa("Rejon",6161) );
			fail("Debia salir por ServicioInternetInvalidoException ");
           
		} catch (ServicioInternetInvalidoException e) {
			//salida correcta
		} catch (DomicilioInvalidoException e) {
			// TODO Auto-generated catch block
			fail("No debia salir por Domicilio invalido");
		}

	}
	
	@Test
	public void testNuevoServicioDomicilioNull() {
		try {
			I_Contratable servicio = ContratableFactory.nuevoServicio("INTERNET100",1, 1, 1,null);
			fail("Debia salir por DomicilioInvalidoException ");
		} catch (ServicioInternetInvalidoException e) {
			fail("No debia salir por Internet invalido");
		} catch (DomicilioInvalidoException e) {
			//SALIDA CORRECTA
		}
	}

}
