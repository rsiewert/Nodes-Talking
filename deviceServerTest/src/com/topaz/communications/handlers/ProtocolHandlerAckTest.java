package com.topaz.communications.handlers;

import org.junit.*;
import static org.junit.Assert.*;

import com.topaz.communications.handlers.ProtocolHandlerAck;
import com.topaz.communications.protocols.MessageProtocol;
import com.topaz.communications.protocols.Protocol;
import com.topaz.communications.servers.*;
/**
 * The class <code>ProtocolHandlerAckTest</code> contains tests for the class <code>{@link ProtocolHandlerAck}</code>.
 *
 * @generatedBy CodePro at 3/12/14 9:52 PM
 * @author rbgodwin
 * @version $Revision: 1.0 $
 */
public class ProtocolHandlerAckTest {
	
	String msHost = "local";
	
	/**
	 * Run the ProtocolHandlerAck() constructor test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 3/12/14 9:52 PM
	 */
	@Test
	public void testProtocolHandlerAck_1()
		throws Exception {

		ProtocolHandlerAck result = new ProtocolHandlerAck();

		assertNotNull(result);
		assertEquals(null, result.getChannel());
		assertEquals(null, result.getMessageProtocol());
		assertEquals(null, result.getProtocol());
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
		ProtocolHandlerAck fixture = new ProtocolHandlerAck();
		MessageProtocol messageProtocol = new MessageProtocol();
		messageProtocol.setExchange("com.topaz.unitTest");
		messageProtocol.setQueue("com.topaz.unitTestQ");
		messageProtocol.setRoutingKey("com.topaz.routingkey");
		fixture.messageProtocol = messageProtocol;
		fixture.protocol = new Protocol();
		MessageService ms = MessageService.getMessageService(this.msHost);
		fixture.setChannel(ms.getChannel());
		
		// Create the thread for the handler function
		Thread handlerThread = new Thread(fixture);

		handlerThread.start();
				
		Thread.sleep(10000);
				
		handlerThread.interrupt();
		handlerThread.join();
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
	}

	/**
	 * Launch the test.
	 *
	 * @param args the command line arguments
	 *
	 * @generatedBy CodePro at 3/12/14 9:52 PM
	 */
	public static void main(String[] args) {
		new org.junit.runner.JUnitCore().run(ProtocolHandlerAckTest.class);
	}
}