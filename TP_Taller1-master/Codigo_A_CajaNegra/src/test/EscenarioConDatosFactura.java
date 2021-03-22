package test;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import interfaces.I_Contratable;
import personas.Fisica;
import servicios.DomicilioCasa;
import servicios.Factura;
import servicios.Internet100;
import servicios.Internet500;
import servicios.Domicilio;
import servicios.Servicio;

public class EscenarioConDatosFactura {

    Factura facturaPrueba = new Factura(new Fisica("Juan",38551263));

	
	public EscenarioConDatosFactura() {
		
	 }
	
	
	@Before
	public void setUp()  {
		Internet100 servicio1 = new Internet100(new DomicilioCasa("Rejon", 6161),1);
		Internet500 servicio2 = new Internet500(new DomicilioCasa("Gascon", 1740),2);
		this.facturaPrueba.getListaContrataciones().add(servicio1);
		this.facturaPrueba.getListaContrataciones().add(servicio2);
		
	}

	@After
	public void tearDown() {
		this.facturaPrueba.getListaContrataciones().clear();
		
	}


}
