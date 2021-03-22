package testCajaBlanca;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
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
import mediospagos.PagoCheque;
import mediospagos.PagoEfectivo;
import mediospagos.PagoTarjeta;
import modelo.Sistema;
import personas.Fisica;
import personas.Persona;
import servicios.DomicilioCasa;

public class SistemaConDatosTest {

	EscenarioConDatosSistema Escenario = new EscenarioConDatosSistema();
	private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
	private final PrintStream originalOut = System.out;
	private final PrintStream originalErr = System.err;
	
	@Before
	public void setUp() throws Exception {
		Escenario.setUp();
		System.setOut(new PrintStream(outContent));
	    System.setErr(new PrintStream(errContent));
	}

	@After
	public void tearDown() throws Exception {
		Escenario.tearDown();
		System.setOut(originalOut);
	    System.setErr(originalErr);
	}

	
	// Agregar Facturas
	@Test
	public void testAgregarFacturasCorrecto() {
		
		Persona f = new Fisica("Carlos",12456789);
		Escenario.sistema.agregarFacturas(f);
		
		assertTrue(Escenario.sistema.getListaFacturas().containsKey("Carlos"));
	}
	
	@Test
	public void testAgregarFacturasDuplicado() {
		
		int t = Escenario.sistema.getListaFacturas().size();
		
		Persona f = new Fisica("Juan",12456789);
		Escenario.sistema.agregarFacturas(f);
		
		assertEquals("Persona ya existente",t,Escenario.sistema.getListaFacturas().size());
	}
	
	
	
	// AGREGAR SERVICIO
	@Test
	public void testAgregarServicioCorrecto() {		
		int cantServicios = Escenario.sistema.getListaFacturas().get("Juan").getListaContrataciones().size();
		Escenario.sistema.agregarServicio("Juan", "internet100", 1, 2, 1, new DomicilioCasa("Strobel",5057));
		assertEquals(Escenario.sistema.getListaFacturas().get("Juan").getListaContrataciones().size(),cantServicios+1);
	}
	
	
	// Domicilio null
	@Test
	public void testAgregarServicioIncorrecto1() {		
		int cantServicios = Escenario.sistema.getListaFacturas().get("Juan").getListaContrataciones().size();
		Escenario.sistema.agregarServicio("Juan", "internet100", 1, 2, 1, null);
		Assert.assertEquals(cantServicios,Escenario.sistema.getListaFacturas().get("Juan").getListaContrataciones().size());
		Assert.assertEquals("No se ingreso un domicilio" + System.getProperty("line.separator"), outContent.toString());
	}
	
	// Internet no existe
	@Test
	public void testAgregarServicioIncorrecto2() {		
		int cantServicios = Escenario.sistema.getListaFacturas().get("Juan").getListaContrataciones().size();
		Escenario.sistema.agregarServicio("Juan", "internet800", 1, 2, 1, new DomicilioCasa("Strobel",5057));
		Assert.assertEquals(cantServicios, Escenario.sistema.getListaFacturas().get("Juan").getListaContrataciones().size());
		Assert.assertEquals("El servicio de internet ingresado no existe" + System.getProperty("line.separator"), outContent.toString());
	}
	
	
	@Test
	public void testgetInstanciaConSistemaExistente() {
		Sistema unaInstancia = Sistema.getInstancia();
		Sistema otraInstancia = Sistema.getInstancia();
		assertSame(unaInstancia,otraInstancia);
	}
	
	@Test
	public void testgetInstanciaConSistemaInexistente() {
		Sistema.getInstancia().setInstancia();
		Sistema unaInstancia = Sistema.getInstancia();
		assertNotSame(unaInstancia,null);
	}
	
	
	// DUPLICAR FACTURA
	@Test
	public void testDuplicarFacturaConPersonaFisica() {
		Escenario.sistema.duplicarFactura("Juan");
		String out = outContent.toString();
		Assert.assertTrue(out.contains("FACTURA DUPLICADA:"));
	}

	@Test
	public void testDuplicarFacturaConPersonaJuridica() {
		Escenario.sistema.duplicarFactura("Pedro");
		String out = outContent.toString();
		Assert.assertTrue(out.contains("Error al clonar, la persona es juridica"));
	}
	
	
	
	// ELIMINAR CONTRATACION. Persona esta en la lista, Domicilio esta en la factura, la persona tiene una contratacion.
	@Test
	public void testEliminarContratacion1() {
		Escenario.sistema.eliminarContratacion("Fernando", "Constitucion 5000");
		assertFalse(Escenario.sistema.getListaFacturas().containsKey("Fernando"));
		
	}	
	
	// ELIMINAR CONTRATACION. Persona esta en la lista, Domicilio esta en la factura, la persona tiene mas de una contratacion.
	@Test
	public void testEliminarContratacion2() {
		Escenario.sistema.eliminarContratacion("Juan", "Moreno 2254");
		ArrayList <I_Contratable> aux = Escenario.sistema.getListaFacturas().get("Juan").getListaContrataciones();
		Iterator it = aux.iterator();
		while(it.hasNext())
		{
			I_Contratable contratacion = (I_Contratable)it.next();
			if(	(contratacion.getDomicilio().getAltura() == 2254) && contratacion.getDomicilio().getCalle().equals("Moreno"))
				fail();
		}
	}

	// ELIMINAR CONTRATACION. Persona esta en la lista, Domicilio no existe en la factura.
	@Test
	public void testEliminarContratacion3() {
		int cantContrataciones = Escenario.sistema.getListaFacturas().get("Juan").getListaContrataciones().size();
		Escenario.sistema.eliminarContratacion("Juan", "Moreno 3000");
		assertTrue(cantContrataciones == Escenario.sistema.getListaFacturas().get("Juan").getListaContrataciones().size());
	}

	// ELIMINAR CONTRATACION. Persona no esta en la lista.
	@Test
	public void testEliminarContratacion4() {
		Escenario.sistema.eliminarContratacion("Jose", "Moreno 2254");
		assertFalse(Escenario.sistema.getListaFacturas().containsKey("Jose"));
	}
	
	//Precio factura: total con porcentaje >= total sin porcentaje
	@Test
	public void testlistarFactura1() {
		Escenario.sistema.abonar("Juan", new PagoCheque());
		Escenario.sistema.listarFactura("Juan");
		String out = Escenario.sistema.listarFactura("Juan");
		assertTrue(out.contains("PRECIO TOTAL:"));
	}
	
	//Precio factura: total con porcentaje < total sin porcentaje
	@Test
	public void testlistarFactura2() {
		Escenario.sistema.abonar("Juan", new PagoEfectivo());
		Escenario.sistema.listarFactura("Juan");
		String out = Escenario.sistema.listarFactura("Juan");
		assertTrue(out.contains("PRECIO TOTAL SIN DESCUENTO:") && out.contains("PRECIO TOTAL CON DESCUENTO:"));
	}
	
	@Test
	public void testlistarFacturas() {
		Escenario.sistema.abonar("Juan", new PagoCheque());
		Escenario.sistema.abonar("Fernando", new PagoEfectivo());
		Escenario.sistema.abonar("Pedro", new PagoTarjeta());
		Escenario.sistema.listarFacturas();
		String out = Escenario.sistema.listarFacturas();
		assertTrue(out.contains("PRECIO TOTAL:") || (out.contains("PRECIO TOTAL SIN DESCUENTO:") && out.contains("PRECIO TOTAL CON DESCUENTO:")));
	}

}
