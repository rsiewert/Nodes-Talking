package com.topaz.nodes;

import org.junit.*;
import static org.junit.Assert.*;

/**
 * The class <code>DeviceTest</code> contains tests for the class <code>{@link Device}</code>.
 *
 * @generatedBy CodePro at 3/12/14 9:52 PM
 * @author rbgodwin
 * @version $Revision: 1.0 $
 */
public class DeviceTest {
	/**
	 * Run the Device() constructor test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 3/12/14 9:52 PM
	 */
	@Test
	public void testDevice_1()
		throws Exception {

		Device result = new Device();

		// add additional test code here
		assertNotNull(result);
		assertEquals(null, result.getLocation());
		assertEquals(null, result.getStatus());
		assertEquals(null, result.getDescription());
		assertEquals(null, result.getNodeId());
		assertEquals(null, result.getActsAs());
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
		new org.junit.runner.JUnitCore().run(DeviceTest.class);
	}
}