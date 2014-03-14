package com.topaz.utils;

import org.junit.*;
import static org.junit.Assert.*;

/**
 * The class <code>LocationTest</code> contains tests for the class <code>{@link Location}</code>.
 *
 * @generatedBy CodePro at 3/12/14 9:52 PM
 * @author rbgodwin
 * @version $Revision: 1.0 $
 */
public class LocationTest {
	/**
	 * Run the Location(double,double,double) constructor test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 3/12/14 9:52 PM
	 */
	@Test
	public void testLocation_1()
		throws Exception {
		double latitude = 1.0;
		double longitude = 1.0;
		double altitude = 1.0;

		Location result = new Location(latitude, longitude, altitude);

		// add additional test code here
		assertNotNull(result);
		assertEquals(1.0, result.getLatitude(), 1.0);
		assertEquals(1.0, result.getLongitude(), 1.0);
		assertEquals(1.0, result.getAltitude(), 1.0);
	}

	/**
	 * Run the double getAltitude() method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 3/12/14 9:52 PM
	 */
	@Test
	public void testGetAltitude_1()
		throws Exception {
		Location fixture = new Location(1.0, 1.0, 1.0);

		double result = fixture.getAltitude();

		// add additional test code here
		assertEquals(1.0, result, 0.1);
	}

	/**
	 * Run the double getLatitude() method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 3/12/14 9:52 PM
	 */
	@Test
	public void testGetLatitude_1()
		throws Exception {
		Location fixture = new Location(1.0, 1.0, 1.0);

		double result = fixture.getLatitude();

		// add additional test code here
		assertEquals(1.0, result, 0.1);
	}

	/**
	 * Run the double getLongitude() method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 3/12/14 9:52 PM
	 */
	@Test
	public void testGetLongitude_1()
		throws Exception {
		Location fixture = new Location(1.0, 1.0, 1.0);

		double result = fixture.getLongitude();

		// add additional test code here
		assertEquals(1.0, result, 0.1);
	}

	/**
	 * Run the void setAltitude(double) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 3/12/14 9:52 PM
	 */
	@Test
	public void testSetAltitude_1()
		throws Exception {
		Location fixture = new Location(1.0, 1.0, 1.0);
		double altitude = 1.0;

		fixture.setAltitude(altitude);

		// add additional test code here
	}

	/**
	 * Run the void setLatitude(double) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 3/12/14 9:52 PM
	 */
	@Test
	public void testSetLatitude_1()
		throws Exception {
		Location fixture = new Location(1.0, 1.0, 1.0);
		double latitude = 1.0;

		fixture.setLatitude(latitude);

		// add additional test code here
	}

	/**
	 * Run the void setLongiture(double) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 3/12/14 9:52 PM
	 */
	@Test
	public void testSetLongiture_1()
		throws Exception {
		Location fixture = new Location(1.0, 1.0, 1.0);
		double longiture = 1.0;

		fixture.setLongiture(longiture);

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
		new org.junit.runner.JUnitCore().run(LocationTest.class);
	}
}