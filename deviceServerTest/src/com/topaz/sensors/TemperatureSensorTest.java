package com.topaz.sensors;

import org.junit.*;
import static org.junit.Assert.*;

/**
 * The class <code>TemperatureSensorTest</code> contains tests for the class <code>{@link TemperatureSensor}</code>.
 *
 * @generatedBy CodePro at 3/12/14 9:52 PM
 * @author rbgodwin
 * @version $Revision: 1.0 $
 */
public class TemperatureSensorTest {
	/**
	 * Run the TemperatureSensor(String,TYPE) constructor test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 3/12/14 9:52 PM
	 */
	@Test
	public void testTemperatureSensor_1()
		throws Exception {
		String description = "";
		Sensor.TYPE type = Sensor.TYPE.AIRPRESSURE;

		TemperatureSensor result = new TemperatureSensor(description, type);

		// add additional test code here
		assertNotNull(result);
		assertEquals(0.0, result.getTemperature(), 1.0);
		assertEquals("", result.getDescription());
	}

	/**
	 * Run the double getTemperature() method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 3/12/14 9:52 PM
	 */
	@Test
	public void testGetTemperature_1()
		throws Exception {
		TemperatureSensor fixture = new TemperatureSensor("", Sensor.TYPE.AIRPRESSURE);
		fixture.setTemperature(1.0);
		fixture.description = "";
		fixture.type = Sensor.TYPE.AIRPRESSURE;

		double result = fixture.getTemperature();

		// add additional test code here
		assertEquals(1.0, result, 0.1);
	}

	/**
	 * Run the void setTemperature(double) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 3/12/14 9:52 PM
	 */
	@Test
	public void testSetTemperature_1()
		throws Exception {
		TemperatureSensor fixture = new TemperatureSensor("", Sensor.TYPE.AIRPRESSURE);
		fixture.setTemperature(1.0);
		fixture.description = "";
		fixture.type = Sensor.TYPE.AIRPRESSURE;
		double temperature = 1.0;

		fixture.setTemperature(temperature);

		// add additional test code here
	}

	/**
	 * Perform pre-test initialization.
	 *
	 * @throws Exception
	 *         if the initialization fails for some reason
	 *
	 * @generatedBy CodePro at 3/12/14 9:52 PM
	 */
	@Before
	public void setUp()
		throws Exception {
		// add additional set up code here
	}

	/**
	 * Perform post-test clean-up.
	 *
	 * @throws Exception
	 *         if the clean-up fails for some reason
	 *
	 * @generatedBy CodePro at 3/12/14 9:52 PM
	 */
	@After
	public void tearDown()
		throws Exception {
		// Add additional tear down code here
	}

	/**
	 * Launch the test.
	 *
	 * @param args the command line arguments
	 *
	 * @generatedBy CodePro at 3/12/14 9:52 PM
	 */
	public static void main(String[] args) {
		new org.junit.runner.JUnitCore().run(TemperatureSensorTest.class);
	}
}