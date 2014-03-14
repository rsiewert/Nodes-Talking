package com.topaz.communications.handlers;

import org.junit.*;
import static org.junit.Assert.*;
import com.rabbitmq.client.Channel;
import com.topaz.communications.handlers.MessageProtocolHandler;
import com.topaz.communications.protocols.MessageProtocol;
import com.topaz.communications.protocols.Protocol;

/**
 * The class <code>MessageProtocolHandlerTest</code> contains tests for the class <code>{@link MessageProtocolHandler}</code>.
 *
 * @generatedBy CodePro at 3/12/14 9:52 PM
 * @author rbgodwin
 * @version $Revision: 1.0 $
 */
public class MessageProtocolHandlerTest {
	/**
	 * Run the Channel getChannel() method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 3/12/14 9:52 PM
	 */
	@Test
	public void testGetChannel_1()
		throws Exception {
		MessageProtocolHandler fixture = new MessageProtocolHandler();
		fixture.setMessageProtocol(new MessageProtocol());
		fixture.setChannel((Channel) null);
		fixture.protocol = new Protocol();

		Channel result = fixture.getChannel();

		// add additional test code here
		assertEquals(null, result);
	}

	/**
	 * Run the MessageProtocol getMessageProtocol() method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 3/12/14 9:52 PM
	 */
	@Test
	public void testGetMessageProtocol_1()
		throws Exception {
		MessageProtocolHandler fixture = new MessageProtocolHandler();
		fixture.setMessageProtocol(new MessageProtocol());
		fixture.setChannel((Channel) null);
		fixture.protocol = new Protocol();

		MessageProtocol result = fixture.getMessageProtocol();

		// add additional test code here
		assertNotNull(result);
		assertEquals(false, result.getManageExchange());
		assertEquals(null, result.getExchange());
		assertEquals(null, result.getQueue());
		assertEquals(null, result.getRoutingKey());
		assertEquals(null, result.getProtocolHandler());
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
		MessageProtocolHandler fixture = new MessageProtocolHandler();
		fixture.setMessageProtocol(new MessageProtocol());
		fixture.setChannel((Channel) null);
		fixture.protocol = new Protocol();

		fixture.run();

		// add additional test code here
	}

	/**
	 * Run the void setChannel(Channel) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 3/12/14 9:52 PM
	 */
	@Test
	public void testSetChannel_1()
		throws Exception {
		MessageProtocolHandler fixture = new MessageProtocolHandler();
		fixture.setMessageProtocol(new MessageProtocol());
		fixture.setChannel((Channel) null);
		fixture.protocol = new Protocol();
		Channel channel = null;

		fixture.setChannel(channel);

		// add additional test code here
	}

	/**
	 * Run the void setMessageProtocol(MessageProtocol) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 3/12/14 9:52 PM
	 */
	@Test
	public void testSetMessageProtocol_1()
		throws Exception {
		MessageProtocolHandler fixture = new MessageProtocolHandler();
		fixture.setMessageProtocol(new MessageProtocol());
		fixture.setChannel((Channel) null);
		fixture.protocol = new Protocol();
		MessageProtocol messageProtocol = new MessageProtocol();

		fixture.setMessageProtocol(messageProtocol);

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
		new org.junit.runner.JUnitCore().run(MessageProtocolHandlerTest.class);
	}
}