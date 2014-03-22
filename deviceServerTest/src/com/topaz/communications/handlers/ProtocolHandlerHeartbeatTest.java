package com.topaz.communications.handlers;

import org.junit.*;
import static org.junit.Assert.*;
import com.topaz.communications.servers.MessageService;
import com.topaz.communications.handlers.ProtocolHandlerHeartbeat;
import com.topaz.communications.messages.HeartbeatMessage;
import com.topaz.communications.protocols.MessageProtocol;
import com.topaz.communications.protocols.Protocol;

/**
 * The class <code>ProtocolHandlerHeartbeatTest</code> contains tests for the class <code>{@link ProtocolHandlerHeartbeat}</code>.
 *
 * @generatedBy CodePro at 3/12/14 9:52 PM
 * @author rbgodwin
 * @version $Revision: 1.0 $
 */
public class ProtocolHandlerHeartbeatTest {
		/**
	 * Run the int getIntervalSec() method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 3/12/14 9:52 PM
	 */
	@Test
	public void testGetIntervalSec_1()
		throws Exception {

		int result = ProtocolHandlerHeartbeat.getIntervalSec();

		// add additional test code here
		assertEquals(1, result);
	}

	/**
	 * Run the void run() method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 3/12/14 9:52 PM
	 */
	@Test
	public void testRun_1()
		throws Exception {
		MessageProtocol messageProtocol = new MessageProtocol();
		ProtocolHandlerHeartbeat fixture = new ProtocolHandlerHeartbeat(MessageService.getMessageService(), 
				new HeartbeatMessage(""),messageProtocol);

		fixture.protocol = new Protocol();
		fixture.heartbeatInterval = new Integer(1);
		fixture.channel = null;
		messageProtocol.setRoutingKey("");
		messageProtocol.setExchange("");

		fixture.messageProtocol = messageProtocol;

		// Create the thread for the handler function
		Thread handlerThread = new Thread(fixture);

		handlerThread.start();
		
		Thread.sleep(10000);
		
		handlerThread.interrupt();
		handlerThread.join();
		
		}
	/**
	 * Run the void setHeartbeatStatus(STATUS) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 3/12/14 9:52 PM
	 */
	@Test
	public void testSetHeartbeatStatus_1()
		throws Exception {
		ProtocolHandlerHeartbeat fixture = new ProtocolHandlerHeartbeat(MessageService.getMessageService(), 
				new HeartbeatMessage(""),new MessageProtocol());
		fixture.protocol = new Protocol();
		fixture.heartbeatInterval = new Integer(1);
		fixture.channel = null;
		com.topaz.nodes.Node.STATUS status = com.topaz.nodes.Node.STATUS.BLACK;

		fixture.setHeartbeatStatus(status);

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
		MessageService ms = new MessageService("localhost");
		assertNotNull(ms);
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
		new org.junit.runner.JUnitCore().run(ProtocolHandlerHeartbeatTest.class);
	}
}