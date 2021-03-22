package testCajaBlanca;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import modelo.Sistema;

public class EscenarioVacioSistema {

	Sistema sistema = Sistema.getInstancia();
	
	public EscenarioVacioSistema() {
		
	}
	
	@Before
	public void setUp() throws Exception {
		sistema.getListaFacturas().clear();
	}

	@After
	public void tearDown() throws Exception {
		sistema.getListaFacturas().clear();
	}

}
