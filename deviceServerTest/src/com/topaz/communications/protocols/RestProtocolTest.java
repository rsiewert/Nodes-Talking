package com.topaz.communications.protocols;

import org.junit.*;
import static org.junit.Assert.*;

import com.topaz.communications.handlers.MessageProtocolHandler;
import com.topaz.communications.protocols.RestProtocol;

/**
 * The class <code>RestProtocolTest</code> contains tests for the class <code>{@link RestProtocol}</code>.
 *
 * @generatedBy CodePro at 3/12/14 9:52 PM
 * @author rbgodwin
 * @version $Revision: 1.0 $
 */
public class RestProtocolTest {
	/**
	 * Run the RestProtocol() constructor test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 3/12/14 9:52 PM
	 */
	@Test
	public void testRestProtocol_1()
		throws Exception {

		RestProtocol result = new RestProtocol();

		// add additional test code here
		assertNotNull(result);
		assertEquals(0, result.getPort());
		assertEquals(null, result.getDomain());
		assertEquals(null, result.getEndpoint());
		assertEquals(null, result.getProtocolHandler());
	}

	/**
	 * Run the String getDomain() method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 3/12/14 9:52 PM
	 */
	@Test
	public void testGetDomain_1()
		throws Exception {
		RestProtocol fixture = new RestProtocol();
		fixture.setPort(1);
		fixture.setEndpoint("");
		fixture.setDomain("");
		fixture.protocolHandler = new MessageProtocolHandler();

		String result = fixture.getDomain();

		// add additional test code here
		assertEquals("", result);
	}

	/**
	 * Run the String getEndpoint() method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 3/12/14 9:52 PM
	 */
	@Test
	public void testGetEndpoint_1()
		throws Exception {
		RestProtocol fixture = new RestProtocol();
		fixture.setPort(1);
		fixture.setEndpoint("");
		fixture.setDomain("");
		fixture.protocolHandler = new MessageProtocolHandler();

		String result = fixture.getEndpoint();

		// add additional test code here
		assertEquals("", result);
	}

	/**
	 * Run the int getPort() method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 3/12/14 9:52 PM
	 */
	@Test
	public void testGetPort_1()
		throws Exception {
		RestProtocol fixture = new RestProtocol();
		fixture.setPort(1);
		fixture.setEndpoint("");
		fixture.setDomain("");
		fixture.protocolHandler = new MessageProtocolHandler();

		int result = fixture.getPort();

		// add additional test code here
		assertEquals(1, result);
	}

	/**
	 * Run the void setDomain(String) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 3/12/14 9:52 PM
	 */
	@Test
	public void testSetDomain_1()
		throws Exception {
		RestProtocol fixture = new RestProtocol();
		fixture.setPort(1);
		fixture.setEndpoint("");
		fixture.setDomain("");
		fixture.protocolHandler = new MessageProtocolHandler();
		String domain = "";

		fixture.setDomain(domain);

		// add additional test code here
	}

	/**
	 * Run the void setEndpoint(String) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 3/12/14 9:52 PM
	 */
	@Test
	public void testSetEndpoint_1()
		throws Exception {
		RestProtocol fixture = new RestProtocol();
		fixture.setPort(1);
		fixture.setEndpoint("");
		fixture.setDomain("");
		fixture.protocolHandler = new MessageProtocolHandler();
		String endpoint = "";

		fixture.setEndpoint(endpoint);

		// add additional test code here
	}

	/**
	 * Run the void setPort(int) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 3/12/14 9:52 PM
	 */
	@Test
	public void testSetPort_1()
		throws Exception {
		RestProtocol fixture = new RestProtocol();
		fixture.setPort(1);
		fixture.setEndpoint("");
		fixture.setDomain("");
		fixture.protocolHandler = new MessageProtocolHandler();
		int port = 1;

		fixture.setPort(port);

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
		new org.junit.runner.JUnitCore().run(RestProtocolTest.class);
	}
}