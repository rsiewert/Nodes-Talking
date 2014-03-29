package com.topaz.communications.messages;

import java.util.Date;
import org.junit.*;

import com.topaz.communications.messages.HeartbeatMessage;

import static org.junit.Assert.*;

/**
 * The class <code>HeartbeatMessageTest</code> contains tests for the class <code>{@link HeartbeatMessage}</code>.
 *
 * @generatedBy CodePro at 3/12/14 9:52 PM
 * @author rbgodwin
 * @version $Revision: 1.0 $
 */
public class HeartbeatMessageTest {
	/**
	 * Run the HeartbeatMessage(String) constructor test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 3/12/14 9:52 PM
	 */
	@Test
	public void testHeartbeatMessage_1()
		throws Exception {
		String nodeId = "";

		HeartbeatMessage result = new HeartbeatMessage(nodeId);

		// add additional test code here
		assertNotNull(result);
		assertEquals("", result.getNodeId());
		assertEquals(0L, result.getSeqId());
		assertEquals(Boolean.FALSE, result.getRequestAck());
	}

	/**
	 * Run the com.topaz.nodes.Node.STATUS getStatus() method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 3/12/14 9:52 PM
	 */
	@Test
	public void testGetStatus_1()
		throws Exception {
		HeartbeatMessage fixture = new HeartbeatMessage("");
		HeartbeatMessage.Heartbeat heartbeatMessage$Heartbeat = new HeartbeatMessage("").new Heartbeat();
		heartbeatMessage$Heartbeat.status = com.topaz.nodes.Node.STATUS.BLACK;
		fixture.heartbeat = heartbeatMessage$Heartbeat;
		fixture.requestAck = new Boolean(true);
		fixture.nodeId = "";
		fixture.seqId = 1L;
		fixture.originTimestamp = new Date();

		com.topaz.nodes.Node.STATUS result = fixture.getStatus();

		// add additional test code here
		assertNotNull(result);
		assertEquals("BLACK", result.name());
		assertEquals("BLACK", result.toString());
		assertEquals(3, result.ordinal());
	}

	/**
	 * Run the void setStatus(STATUS) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 3/12/14 9:52 PM
	 */
	@Test
	public void testSetStatus_1()
		throws Exception {
		HeartbeatMessage fixture = new HeartbeatMessage("");
		HeartbeatMessage.Heartbeat heartbeatMessage$Heartbeat = new HeartbeatMessage("").new Heartbeat();
		heartbeatMessage$Heartbeat.status = com.topaz.nodes.Node.STATUS.BLACK;
		fixture.heartbeat = heartbeatMessage$Heartbeat;
		fixture.requestAck = new Boolean(true);
		fixture.nodeId = "";
		fixture.seqId = 1L;
		fixture.originTimestamp = new Date();
		com.topaz.nodes.Node.STATUS status = com.topaz.nodes.Node.STATUS.BLACK;

		fixture.setStatus(status);

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
		new org.junit.runner.JUnitCore().run(HeartbeatMessageTest.class);
	}
}