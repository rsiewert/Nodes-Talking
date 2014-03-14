package com.topaz.communications.messages;

import java.util.Date;
import org.junit.*;
import static org.junit.Assert.*;

import com.topaz.communications.messages.Registration;
import com.topaz.nodes.Node;

/**
 * The class <code>RegistrationTest</code> contains tests for the class <code>{@link Registration}</code>.
 *
 * @generatedBy CodePro at 3/12/14 9:52 PM
 * @author rbgodwin
 * @version $Revision: 1.0 $
 */
public class RegistrationTest {
	/**
	 * Run the Registration() constructor test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 3/12/14 9:52 PM
	 */
	@Test
	public void testRegistration_1()
		throws Exception {

		Registration result = new Registration();

		assertNotNull(result);
		assertEquals(null, result.getRegisteringNode());
		assertEquals(null, result.getNodeId());
		assertEquals(0L, result.getSeqId());
		assertEquals(Boolean.FALSE, result.getRequestAck());
	}

	/**
	 * Run the Registration(String) constructor test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 3/12/14 9:52 PM
	 */
	@Test
	public void testRegistration_2()
		throws Exception {
		String nodeId = "";

		Registration result = new Registration(nodeId);

		// add additional test code here
		assertNotNull(result);
		assertEquals(null, result.getRegisteringNode());
		assertEquals("", result.getNodeId());
		assertEquals(0L, result.getSeqId());
		assertEquals(Boolean.FALSE, result.getRequestAck());
	}

	/**
	 * Run the Node getRegisteringNode() method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 3/12/14 9:52 PM
	 */
	@Test
	public void testGetRegisteringNode_1()
		throws Exception {
		Registration fixture = new Registration("");
		fixture.setRegisteringNode((Node) null);
		fixture.originTimestamp = new Date();
		fixture.requestAck = new Boolean(true);
		fixture.seqId = 1L;

		Node result = fixture.getRegisteringNode();

		// add additional test code here
		assertEquals(null, result);
	}

	/**
	 * Run the void setRegisteringNode(Node) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 3/12/14 9:52 PM
	 */
	@Test
	public void testSetRegisteringNode_1()
		throws Exception {
		Registration fixture = new Registration("");
		fixture.setRegisteringNode((Node) null);
		fixture.originTimestamp = new Date();
		fixture.requestAck = new Boolean(true);
		fixture.seqId = 1L;
		Node registeringNode = null;

		fixture.setRegisteringNode(registeringNode);

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
		new org.junit.runner.JUnitCore().run(RegistrationTest.class);
	}
}