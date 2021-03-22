package test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Iterator;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import agregado.Celular;
import agregado.TV_Cable;
import agregado.Telefono;
import interfaces.I_Contratable;
import mediospagos.PagoEfectivo;
import personas.Fisica;
import personas.Persona;
import servicios.DomicilioCasa;

public class SistemaConDatosTest {

	EscenarioConDatosSistema Escenario = new EscenarioConDatosSistema();
	
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
		
		Persona f = new Fisica("Carlos",12456789);
		Escenario.sistema.agregarFacturas(f);
		
		assertTrue("No se agregó correctamente",Escenario.sistema.getListaFacturas().containsKey("Carlos"));
	}
	
	@Test
	public void testAgregarFacturasDuplicado() {
		
		int t = Escenario.sistema.getListaFacturas().size();
		
		Persona f = new Fisica("Juan",12456789);
		Escenario.sistema.agregarFacturas(f);
		
		assertEquals("Se agregó un duplicado",t,Escenario.sistema.getListaFacturas().size());
	}
	
	
	
	// AGREGAR SERVICIO
	@Test
	public void testAgregarServicioCorrecto() {		
		Escenario.sistema.agregarServicio("Juan", "internet100", 1, 2, 1, new DomicilioCasa("Strobel",5057));
		
		ArrayList<I_Contratable> listaContrataciones = Escenario.sistema.getListaFacturas().get("Juan").getListaContrataciones();
		I_Contratable aux = listaContrataciones.get(listaContrataciones.size()-1);

		assertTrue("No es TV_Cable",aux.isTV_Cable());			
		TV_Cable aux1 = (TV_Cable)aux;
		assertEquals("Cantidad lineas TV_Cable incorrecto",aux1.getCantLineas(),1);
			
		assertTrue("No es Telefono",aux1.getContratable().isTelefono());
		Telefono aux2 = (Telefono)aux1.getContratable();
		assertEquals("Cantidad lineas Telefono incorrecto",aux2.getCantLineas(),2);
			
		assertTrue("No es Celular",aux2.getContratable().isCelular());
		Celular aux3 = (Celular)aux2.getContratable();
		assertEquals("Cantidad lineas Celular incorrecto",aux3.getCantLineas(),1);
			
		I_Contratable aux4 = aux3.getContratable();
		assertTrue("Internet no es el correcto", aux4.isInternet100());
		
		assertEquals("Calle incorrecta", aux.getDomicilio().getCalle(),"Strobel");
		assertEquals("Altura incorrecta", aux.getDomicilio().getAltura(),5057);	
	}
	
	
	// Domicilio null
	@Test
	public void testAgregarServicioIncorrecto1() {		
		int t = Escenario.sistema.getListaFacturas().get("Juan").getListaContrataciones().size();
		Escenario.sistema.agregarServicio("Juan", "internet100", 1, 2, 1, null);
		assertTrue("No debió modificar la lista", t == Escenario.sistema.getListaFacturas().get("Juan").getListaContrataciones().size());	
	}
	
	// Internet no existe
	@Test
	public void testAgregarServicioIncorrecto2() {		
		int t = Escenario.sistema.getListaFacturas().get("Juan").getListaContrataciones().size();
		Escenario.sistema.agregarServicio("Juan", "internet800", 1, 2, 1, new DomicilioCasa("Strobel",5057));
		assertTrue("No debió modificar la lista", t == Escenario.sistema.getListaFacturas().get("Juan").getListaContrataciones().size());	
	}
	
	// Internet null
	@Test
	public void testAgregarServicioIncorrecto3() {		
		int t = Escenario.sistema.getListaFacturas().get("Juan").getListaContrataciones().size();
		Escenario.sistema.agregarServicio("Juan", null, 1, 2, 1, new DomicilioCasa("Strobel",5057));
		assertTrue("No debió modificar la lista", t == Escenario.sistema.getListaFacturas().get("Juan").getListaContrataciones().size());	
	}
	
		
	
	
	// MODIFICAR AGREGADO
	@Test
	public void testModificarAgregado() {
		// Tenía Internet100
		Escenario.sistema.modificarAgregado("Juan", "Moreno 2254", "CAMBIAR", "INTERNET500");
		assertTrue("No se modificó",Escenario.sistema.getListaFacturas().get("Juan").getListaContrataciones().get(0).isInternet500());
	}

	
	
	
	
	// ABONAR
	@Test
	public void testAbonar() {
		double ant = Escenario.sistema.getListaFacturas().get("Juan").getTotalSinP();
		Escenario.sistema.abonar("Juan",new PagoEfectivo());
		double act = Escenario.sistema.getListaFacturas().get("Juan").getTotalSinP();
		assertTrue("No hizo los cambios",ant != act);
	}
	
	@Test
	public void testAbonarIncorrecto()
	{
		Escenario.sistema.abonar("Jose",new PagoEfectivo());
	}
	

	
	// DUPLICAR FACTURA
	@Test
	public void testDuplicarFactura() {
		// no hubo manera de verificar que se haya duplicado con caja negra
		Escenario.sistema.duplicarFactura("Juan");
	}

	@Test
	public void testDuplicarFacturaIncorrecto() {
		Escenario.sistema.duplicarFactura("Jose");
	}
	
	
	
	// ELIMINAR CONTRATACION
	@Test
	public void testEliminarContratacion() {
		Escenario.sistema.eliminarContratacion("Juan", "Moreno 2254");
		ArrayList <I_Contratable> aux = Escenario.sistema.getListaFacturas().get("Juan").getListaContrataciones();
		Iterator it = aux.iterator();
		while(it.hasNext())
		{
			I_Contratable contratacion = (I_Contratable)it.next();
			if(	(contratacion.getDomicilio().getAltura() == 5000) && contratacion.getDomicilio().getCalle().equals("Constitucion"))
				fail("No eliminó la contratacion");
		}
	}	
	
	@Test
	public void testEliminarContratacionIncorrecto() {
		int t = Escenario.sistema.getListaFacturas().get("Jose").getListaContrataciones().size();
		Escenario.sistema.eliminarContratacion("Jose", "Moreno 2254");
		assertTrue("No debió modificar la lista", t == Escenario.sistema.getListaFacturas().get("Jose").getListaContrataciones().size());
	}

	

}
