package com.topaz.communications.protocols;

import org.junit.*;
import static org.junit.Assert.*;

import com.topaz.communications.handlers.MessageProtocolHandler;
import com.topaz.communications.protocols.MessageProtocol;

/**
 * The class <code>MessageProtocolTest</code> contains tests for the class <code>{@link MessageProtocol}</code>.
 *
 * @generatedBy CodePro at 3/12/14 9:52 PM
 * @author rbgodwin
 * @version $Revision: 1.0 $
 */
public class MessageProtocolTest {
	/**
	 * Run the String getExchange() method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 3/12/14 9:52 PM
	 */
	@Test
	public void testGetExchange_1()
		throws Exception {
		MessageProtocol fixture = new MessageProtocol();
		fixture.setManageExchange(true);
		fixture.setRoutingKey("");
		fixture.setQueue("");
		fixture.setProtocolHandler(new MessageProtocolHandler());
		fixture.setExchange("");
		fixture.protocolHandler = new MessageProtocolHandler();

		String result = fixture.getExchange();

		// add additional test code here
		assertEquals("", result);
	}

	/**
	 * Run the boolean getManageExchange() method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 3/12/14 9:52 PM
	 */
	@Test
	public void testGetManageExchange_1()
		throws Exception {
		MessageProtocol fixture = new MessageProtocol();
		fixture.setManageExchange(true);
		fixture.setRoutingKey("");
		fixture.setQueue("");
		fixture.setProtocolHandler(new MessageProtocolHandler());
		fixture.setExchange("");
		fixture.protocolHandler = new MessageProtocolHandler();

		boolean result = fixture.getManageExchange();

		// add additional test code here
		assertEquals(true, result);
	}

	/**
	 * Run the boolean getManageExchange() method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 3/12/14 9:52 PM
	 */
	@Test
	public void testGetManageExchange_2()
		throws Exception {
		MessageProtocol fixture = new MessageProtocol();
		fixture.setManageExchange(false);
		fixture.setRoutingKey("");
		fixture.setQueue("");
		fixture.setProtocolHandler(new MessageProtocolHandler());
		fixture.setExchange("");
		fixture.protocolHandler = new MessageProtocolHandler();

		boolean result = fixture.getManageExchange();

		// add additional test code here
		assertEquals(false, result);
	}

	/**
	 * Run the MessageProtocolHandler getProtocolHandler() method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 3/12/14 9:52 PM
	 */
	@Test
	public void testGetProtocolHandler_1()
		throws Exception {
		MessageProtocol fixture = new MessageProtocol();
		fixture.setManageExchange(true);
		fixture.setRoutingKey("");
		fixture.setQueue("");
		fixture.setProtocolHandler(new MessageProtocolHandler());
		fixture.setExchange("");
		fixture.protocolHandler = new MessageProtocolHandler();

		MessageProtocolHandler result = fixture.getProtocolHandler();

		// add additional test code here
		assertNotNull(result);
		assertEquals(null, result.getChannel());
		assertEquals(null, result.getMessageProtocol());
		assertEquals(null, result.getProtocol());
	}

	/**
	 * Run the String getQueue() method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 3/12/14 9:52 PM
	 */
	@Test
	public void testGetQueue_1()
		throws Exception {
		MessageProtocol fixture = new MessageProtocol();
		fixture.setManageExchange(true);
		fixture.setRoutingKey("");
		fixture.setQueue("");
		fixture.setProtocolHandler(new MessageProtocolHandler());
		fixture.setExchange("");
		fixture.protocolHandler = new MessageProtocolHandler();

		String result = fixture.getQueue();

		// add additional test code here
		assertEquals("", result);
	}

	/**
	 * Run the String getRoutingKey() method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 3/12/14 9:52 PM
	 */
	@Test
	public void testGetRoutingKey_1()
		throws Exception {
		MessageProtocol fixture = new MessageProtocol();
		fixture.setManageExchange(true);
		fixture.setRoutingKey("");
		fixture.setQueue("");
		fixture.setProtocolHandler(new MessageProtocolHandler());
		fixture.setExchange("");
		fixture.protocolHandler = new MessageProtocolHandler();

		String result = fixture.getRoutingKey();

		// add additional test code here
		assertEquals("", result);
	}

	/**
	 * Run the void setExchange(String) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 3/12/14 9:52 PM
	 */
	@Test
	public void testSetExchange_1()
		throws Exception {
		MessageProtocol fixture = new MessageProtocol();
		fixture.setManageExchange(true);
		fixture.setRoutingKey("");
		fixture.setQueue("");
		fixture.setProtocolHandler(new MessageProtocolHandler());
		fixture.setExchange("");
		fixture.protocolHandler = new MessageProtocolHandler();
		String exchange = "";

		fixture.setExchange(exchange);

		// add additional test code here
	}

	/**
	 * Run the void setManageExchange(boolean) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 3/12/14 9:52 PM
	 */
	@Test
	public void testSetManageExchange_1()
		throws Exception {
		MessageProtocol fixture = new MessageProtocol();
		fixture.setManageExchange(true);
		fixture.setRoutingKey("");
		fixture.setQueue("");
		fixture.setProtocolHandler(new MessageProtocolHandler());
		fixture.setExchange("");
		fixture.protocolHandler = new MessageProtocolHandler();
		boolean manageExchange = true;

		fixture.setManageExchange(manageExchange);

		// add additional test code here
	}

	/**
	 * Run the void setProtocolHandler(MessageProtocolHandler) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 3/12/14 9:52 PM
	 */
	@Test
	public void testSetProtocolHandler_1()
		throws Exception {
		MessageProtocol fixture = new MessageProtocol();
		fixture.setManageExchange(true);
		fixture.setRoutingKey("");
		fixture.setQueue("");
		fixture.setProtocolHandler(new MessageProtocolHandler());
		fixture.setExchange("");
		fixture.protocolHandler = new MessageProtocolHandler();
		MessageProtocolHandler protocolHandler = new MessageProtocolHandler();

		fixture.setProtocolHandler(protocolHandler);

		// add additional test code here
	}

	/**
	 * Run the void setQueue(String) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 3/12/14 9:52 PM
	 */
	@Test
	public void testSetQueue_1()
		throws Exception {
		MessageProtocol fixture = new MessageProtocol();
		fixture.setManageExchange(true);
		fixture.setRoutingKey("");
		fixture.setQueue("");
		fixture.setProtocolHandler(new MessageProtocolHandler());
		fixture.setExchange("");
		fixture.protocolHandler = new MessageProtocolHandler();
		String queue = "";

		fixture.setQueue(queue);

		// add additional test code here
	}

	/**
	 * Run the void setRoutingKey(String) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 3/12/14 9:52 PM
	 */
	@Test
	public void testSetRoutingKey_1()
		throws Exception {
		MessageProtocol fixture = new MessageProtocol();
		fixture.setManageExchange(true);
		fixture.setRoutingKey("");
		fixture.setQueue("");
		fixture.setProtocolHandler(new MessageProtocolHandler());
		fixture.setExchange("");
		fixture.protocolHandler = new MessageProtocolHandler();
		String routingKey = "";

		fixture.setRoutingKey(routingKey);

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
		new org.junit.runner.JUnitCore().run(MessageProtocolTest.class);
	}
}