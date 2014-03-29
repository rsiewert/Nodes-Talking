package com.topaz.communications.protocols;

import java.util.HashMap;
import org.junit.*;

import com.topaz.communications.protocols.MessageProtocol;
import com.topaz.communications.protocols.ProtocolSpec;
import com.topaz.communications.protocols.RestProtocol;

import static org.junit.Assert.*;

/**
 * The class <code>ProtocolSpecTest</code> contains tests for the class <code>{@link ProtocolSpec}</code>.
 *
 * @generatedBy CodePro at 3/12/14 9:52 PM
 * @author rbgodwin
 * @version $Revision: 1.0 $
 */
public class ProtocolSpecTest {
	/**
	 * Run the ProtocolSpec() constructor test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 3/12/14 9:52 PM
	 */
	@Test
	public void testProtocolSpec_1()
		throws Exception {

		ProtocolSpec result = new ProtocolSpec();

		// add additional test code here
		assertNotNull(result);
	}

	/**
	 * Run the void addMessageProtocol(String,MessageProtocol) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 3/12/14 9:52 PM
	 */
	@Test
	public void testAddMessageProtocol_1()
		throws Exception {
		ProtocolSpec fixture = new ProtocolSpec();
		fixture.messenger = null;
		fixture.rest = new HashMap<String, RestProtocol>();
		String name = "";
		MessageProtocol messP = new MessageProtocol();

		fixture.addMessageProtocol(name, messP);

		// add additional test code here
	}

	/**
	 * Run the void addMessageProtocol(String,MessageProtocol) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 3/12/14 9:52 PM
	 */
	@Test
	public void testAddMessageProtocol_2()
		throws Exception {
		ProtocolSpec fixture = new ProtocolSpec();
		fixture.messenger = new HashMap<String, MessageProtocol>();
		fixture.rest = new HashMap<String, RestProtocol>();
		String name = "";
		MessageProtocol messP = new MessageProtocol();

		fixture.addMessageProtocol(name, messP);

		// add additional test code here
	}

	/**
	 * Run the void addRestProtocol(String,RestProtocol) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 3/12/14 9:52 PM
	 */
	@Test
	public void testAddRestProtocol_1()
		throws Exception {
		ProtocolSpec fixture = new ProtocolSpec();
		fixture.messenger = new HashMap<String,MessageProtocol>();
		fixture.rest = null;
		String name = "";
		RestProtocol restP = new RestProtocol();

		fixture.addRestProtocol(name, restP);

		// add additional test code here
	}

	/**
	 * Run the void addRestProtocol(String,RestProtocol) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 3/12/14 9:52 PM
	 */
	@Test
	public void testAddRestProtocol_2()
		throws Exception {
		ProtocolSpec fixture = new ProtocolSpec();
		fixture.messenger = new HashMap<String,MessageProtocol>();
		fixture.rest = new HashMap<String, RestProtocol>();
		String name = "";
		RestProtocol restP = new RestProtocol();

		fixture.addRestProtocol(name, restP);

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
		new org.junit.runner.JUnitCore().run(ProtocolSpecTest.class);
	}
}