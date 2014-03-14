package com.topaz.sensors;

import org.junit.*;
import static org.junit.Assert.*;

/**
 * The class <code>SensorTest</code> contains tests for the class <code>{@link Sensor}</code>.
 *
 * @generatedBy CodePro at 3/12/14 9:52 PM
 * @author rbgodwin
 * @version $Revision: 1.0 $
 */
public class SensorTest {
	/**
	 * Run the Sensor() constructor test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 3/12/14 9:52 PM
	 */
	@Test
	public void testSensor_1()
		throws Exception {

		Sensor result = new Sensor();

		// add additional test code here
		assertNotNull(result);
		assertEquals(null, result.getType());
		assertEquals(null, result.getDescription());
	}

	/**
	 * Run the Sensor(String,TYPE) constructor test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 3/12/14 9:52 PM
	 */
	@Test
	public void testSensor_2()
		throws Exception {
		String description = "";
		Sensor.TYPE type = Sensor.TYPE.AIRPRESSURE;

		Sensor result = new Sensor(description, type);

		// add additional test code here
		assertNotNull(result);
		assertEquals("", result.getDescription());
	}

	/**
	 * Run the String getDescription() method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 3/12/14 9:52 PM
	 */
	@Test
	public void testGetDescription_1()
		throws Exception {
		Sensor fixture = new Sensor("", Sensor.TYPE.AIRPRESSURE);

		String result = fixture.getDescription();

		// add additional test code here
		assertEquals("", result);
	}

	/**
	 * Run the Sensor.TYPE getType() method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 3/12/14 9:52 PM
	 */
	@Test
	public void testGetType_1()
		throws Exception {
		Sensor fixture = new Sensor("", Sensor.TYPE.AIRPRESSURE);

		Sensor.TYPE result = fixture.getType();

		// add additional test code here
		assertNotNull(result);
		assertEquals("AIRPRESSURE", result.name());
		assertEquals("AIRPRESSURE", result.toString());
		assertEquals(1, result.ordinal());
	}

	/**
	 * Run the void setDescription(String) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 3/12/14 9:52 PM
	 */
	@Test
	public void testSetDescription_1()
		throws Exception {
		Sensor fixture = new Sensor("", Sensor.TYPE.AIRPRESSURE);
		String description = "";

		fixture.setDescription(description);

		// add additional test code here
	}

	/**
	 * Run the void setType(TYPE) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 3/12/14 9:52 PM
	 */
	@Test
	public void testSetType_1()
		throws Exception {
		Sensor fixture = new Sensor("", Sensor.TYPE.AIRPRESSURE);
		Sensor.TYPE type = Sensor.TYPE.AIRPRESSURE;

		fixture.setType(type);

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
		new org.junit.runner.JUnitCore().run(SensorTest.class);
	}
}