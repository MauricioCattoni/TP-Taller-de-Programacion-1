package test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ ContratableFactoryTestCase.class, FacturaConDatosTest.class,
		SistemaVacioTest.class, SistemaConDatosTest.class })

public class AllTests {

}
