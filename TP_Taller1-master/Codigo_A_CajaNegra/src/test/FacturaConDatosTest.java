package test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import mediospagos.PagoEfectivo;
import personas.Fisica;

public class FacturaConDatosTest {

	EscenarioConDatosFactura Escenario = new EscenarioConDatosFactura();
	@Before
	public void setUp() throws Exception {
		Escenario.setUp();
	}

	@After
	public void tearDown() throws Exception {
		Escenario.tearDown();
	}

	// BUSCA CONTRATACION
	@Test
	public void testBuscaContratacion() {
		assertEquals("Posicion incorrecta",0,Escenario.facturaPrueba.buscaContratacion("Rejon 6161"));
	}
		
	
	// MODIFICA CONTRATACION
	@Test
	public void testModificaContratacionCorrecto() {
		Escenario.facturaPrueba.modificaContratacion(1, "CAMBIAR", "INTERNET100");
		assertTrue("El cambio no se realizo correctamente",Escenario.facturaPrueba.getListaContrataciones().get(1).isInternet100());
	}
	
	
	@Test
	public void testModificaContratacionIncorrecto() {
		Escenario.facturaPrueba.modificaContratacion(1, "QUITAR", "TVCABLE");
		fail("Se quiere quitar un servicio que esa persona no tiene");
	}
	
	@Test
	public void testModificaContratacionServicioInexistente() {
		Escenario.facturaPrueba.modificaContratacion(1, "QUITAR", "CUALQUIERA");
		fail("Se quiere quitar un servicio que no existe");
		
	}
	
	
	@Test
	public void testModificaContratacionAccionVacia() {
		Escenario.facturaPrueba.modificaContratacion(1, "", "INTERNET100");
		fail("Deberia estar considerado el caso de que la accion sea un String vacio");
		
	}
	
	@Test
	public void testModificaContratacionAccionNula() {
		Escenario.facturaPrueba.modificaContratacion(1, null, "INTERNET100");
		fail("Deberia estar considerado el caso de que la accion sea un String nulo");
	}
	
	
	@Test
	public void testModificaContratacionAccionServicioIncorrecto() {
		Escenario.facturaPrueba.modificaContratacion(1, "CAMBIAR","TVCABLE");
		fail("Esa accion no corresponde a ese servicio");
	}
	
	@Test
	public void testModificaContratacionAccionServicioVacio() {
		Escenario.facturaPrueba.modificaContratacion(1, "CAMBIAR","");
		fail("Deberia estar considerado el caso de que el servicio sea un String vacio");
	}
	
	@Test
	public void testPrecioFinalCorrecto() {
		Escenario.facturaPrueba.precioFinal(new Fisica("Juan",38551263), new PagoEfectivo());
	}
	
	@Test
	public void testPrecioFinalPersonaInexistente() {
		Escenario.facturaPrueba.precioFinal(new Fisica("Carlos",38551264), new PagoEfectivo());
		fail("Deberia estar contemplado el caso de que la persona no exista");
	}
	

	
	

}
