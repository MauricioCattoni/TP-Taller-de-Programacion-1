package testCajaBlanca;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import personas.Fisica;
import personas.Persona;

public class SistemaVacioTest {

	EscenarioVacioSistema Escenario = new EscenarioVacioSistema();
	
	@Before
	public void setUp() throws Exception {
		Escenario.setUp();
	}

	@After
	public void tearDown() throws Exception {
		Escenario.tearDown();
	}

	
	// Agregar Facturas
	@Test
	public void testAgregarFacturasCorrecto() {
		
		Persona f = new Fisica("Juan",12456789);
		Escenario.sistema.agregarFacturas(f);
		
		assertTrue(Escenario.sistema.getListaFacturas().containsKey("Juan"));
	}
	
	
}
