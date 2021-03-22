package test;

import static org.junit.Assert.*;

import java.util.HashMap;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import interfaces.I_Contratable;
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
			
		
		
		Persona p = new Fisica("Juan",40323213);		
		Factura f = new Factura(p);
		
		I_Contratable i1 = new Internet100(new DomicilioCasa("Moreno",2254));
		I_Contratable i2 = new Internet500(new DomicilioCasa("Luro",5554));
		I_Contratable i3 = new Internet500(new DomicilioCasa("Constitucion",5000));
		
		f.nuevaContratacion(i1);
		f.nuevaContratacion(i2);
		f.nuevaContratacion(i3);
		
		sistema.getListaFacturas().put(p.getNombre(), f);
		
		
		p = new Fisica("Fernando",38684917);
		sistema.getListaFacturas().put(p.getNombre(), new Factura(p));
		
		p = new Juridica("Pedro",32681977);
		sistema.getListaFacturas().put(p.getNombre(), new Factura(p));
		
	}

	@After
	public void tearDown() throws Exception {
		sistema.getListaFacturas().clear();
	}


}
