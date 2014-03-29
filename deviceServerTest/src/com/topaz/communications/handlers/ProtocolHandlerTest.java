package com.topaz.communications.handlers;

import org.junit.*;
import static org.junit.Assert.*;

import com.topaz.communications.handlers.MessageProtocolHandler;
import com.topaz.communications.handlers.ProtocolHandler;
import com.topaz.communications.protocols.Protocol;

/**
 * The class <code>ProtocolHandlerTest</code> contains tests for the class <code>{@link ProtocolHandler}</code>.
 *
 * @generatedBy CodePro at 3/12/14 9:52 PM
 * @author rbgodwin
 * @version $Revision: 1.0 $
 */
public class ProtocolHandlerTest {
	/**
	 * Run the Connection getConnection() method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 3/12/14 9:52 PM
	 */
	/**
	 * Run the Protocol getProtocol() method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 3/12/14 9:52 PM
	 */
	@Test
	public void testGetProtocol_1()
		throws Exception {
		MessageProtocolHandler fixture = new MessageProtocolHandler();
		fixture.protocol = new Protocol();

		Protocol result = fixture.getProtocol();

		// add additional test code here
		assertNotNull(result);
		assertEquals(null, result.getProtocolHandler());
	}
	/**
	 * Run the void setProtocol(Protocol) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 3/12/14 9:52 PM
	 */
	@Test
	public void testSetProtocol_1()
		throws Exception {
		MessageProtocolHandler fixture = new MessageProtocolHandler();
		fixture.protocol = new Protocol();
		Protocol protocol = new Protocol();

		fixture.setProtocol(protocol);

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
		new org.junit.runner.JUnitCore().run(ProtocolHandlerTest.class);
	}
}