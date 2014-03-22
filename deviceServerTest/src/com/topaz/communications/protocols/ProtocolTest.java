package com.topaz.communications.protocols;

import org.junit.*;
import static org.junit.Assert.*;

import com.topaz.communications.handlers.MessageProtocolHandler;
import com.topaz.communications.handlers.ProtocolHandler;
import com.topaz.communications.protocols.Protocol;

/**
 * The class <code>ProtocolTest</code> contains tests for the class <code>{@link Protocol}</code>.
 *
 * @generatedBy CodePro at 3/12/14 9:52 PM
 * @author rbgodwin
 * @version $Revision: 1.0 $
 */
public class ProtocolTest {
	/**
	 * Run the ProtocolHandler getProtocolHandler() method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 3/12/14 9:52 PM
	 */
	@Test
	public void testGetProtocolHandler_1()
		throws Exception {
		Protocol fixture = new Protocol();
		fixture.setProtocolHandler(new MessageProtocolHandler());

		ProtocolHandler result = fixture.getProtocolHandler();

		// add additional test code here
		assertNotNull(result);
		assertEquals(null, result.getProtocol());
	}

	/**
	 * Run the void setProtocolHandler(ProtocolHandler) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 3/12/14 9:52 PM
	 */
	@Test
	public void testSetProtocolHandler_1()
		throws Exception {
		Protocol fixture = new Protocol();
		fixture.setProtocolHandler(new MessageProtocolHandler());
		ProtocolHandler protocolHandler = new MessageProtocolHandler();

		fixture.setProtocolHandler(protocolHandler);

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
		new org.junit.runner.JUnitCore().run(ProtocolTest.class);
	}
}