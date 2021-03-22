package testCajaBlanca;

import static org.junit.Assert.*;

import java.util.HashMap;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import interfaces.I_Contratable;
import mediospagos.PagoCheque;
import modelo.Sistema;
import personas.Fisica;
import personas.Juridica;
import personas.Persona;
import servicios.Domicilio;
import servicios.DomicilioCasa;
import servicios.Factura;
import servicios.Internet100;
import servicios.Internet500;

public class EscenarioConDatosSistema {

	Sistema sistema = Sistema.getInstancia();
	
	@Before
	public void setUp() throws Exception {
		
		//JUAN
		
		Persona p = new Fisica("Juan",40323213);		
		
		Factura f = new Factura(p);
		
		I_Contratable i1 = new Internet100(new DomicilioCasa("Moreno",2254));

		I_Contratable i2 = new Internet500(new DomicilioCasa("Luro",5554));
		
		f.nuevaContratacion(i1);
		f.nuevaContratacion(i2);
		
		sistema.getListaFacturas().put(p.getNombre(), f);
		
		//FERNANDO
		
		p = new Fisica("Fernando",38684917);
	
		Factura f2 = new Factura(p);
		
		I_Contratable i3 = new Internet500(new DomicilioCasa("Constitucion",5000));
		
		f2.nuevaContratacion(i3);
		
		//sistema.abonar("Fernando", new PagoCheque());
		sistema.getListaFacturas().put(p.getNombre(), f2);
		
		//PEDRO
		
		p = new Juridica("Pedro",32681977);
		
		Factura f3 = new Factura(p);
		
		I_Contratable i4 = new Internet100(new DomicilioCasa("Dorrego",3120));
		
		f3.nuevaContratacion(i4);
		
		sistema.getListaFacturas().put(p.getNombre(), new Factura(p));
		
	}

	@After
	public void tearDown() throws Exception {
		sistema.getListaFacturas().clear();
	}


}
