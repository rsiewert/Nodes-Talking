package com.topaz.apps;

import org.junit.*;
import static org.junit.Assert.*;

/**
 * The class <code>DeviceServerTest</code> contains tests for the class <code>{@link DeviceServer}</code>.
 *
 * @generatedBy CodePro at 3/12/14 9:52 PM
 * @author rbgodwin
 * @version $Revision: 1.0 $
 */
public class DeviceServerTest {
	/**
	 * Run the DeviceServer() constructor test.
	 *
	 * @generatedBy CodePro at 3/12/14 9:52 PM
	 */
	@Test
	public void testDeviceServer_1()
		throws Exception {
		DeviceServer result = new DeviceServer();
		assertNotNull(result);
		// add additional test code here
	}

	/**
	 * Run the void main(String[]) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 3/12/14 9:52 PM
	 */
	@Test
	public void testMain_1()
		throws Exception {
		String[] args = new String[] {};

		DeviceServer.main(args);

		// add additional test code here
		// An unexpected exception was thrown in user code while executing this test:
		//    java.lang.SecurityException: Cannot write to files while generating test cases
		//       at com.instantiations.assist.eclipse.junit.CodeProJUnitSecurityManager.checkWrite(CodeProJUnitSecurityManager.java:70)
		//       at java.io.FileOutputStream.<init>(FileOutputStream.java:237)
		//       at java.net.SocketOutputStream.<init>(SocketOutputStream.java:58)
		//       at java.net.AbstractPlainSocketImpl.getOutputStream(AbstractPlainSocketImpl.java:411)
		//       at java.net.Socket$3.run(Socket.java:865)
		//       at java.security.AccessController.doPrivileged(Native Method)
		//       at java.net.Socket.getOutputStream(Socket.java:862)
		//       at com.rabbitmq.client.impl.SocketFrameHandler.<init>(SocketFrameHandler.java:54)
		//       at com.rabbitmq.client.ConnectionFactory.createFrameHandler(ConnectionFactory.java:462)
		//       at com.rabbitmq.client.ConnectionFactory.createFrameHandler(ConnectionFactory.java:447)
		//       at com.rabbitmq.client.ConnectionFactory.newConnection(ConnectionFactory.java:504)
		//       at com.rabbitmq.client.ConnectionFactory.newConnection(ConnectionFactory.java:533)
		//       at com.topaz.communications.MessageService.<init>(MessageService.java:66)
		//       at com.topaz.communications.MessageService.<init>(MessageService.java:80)
		//       at com.topaz.apps.DeviceServer.main(DeviceServer.java:20)
	}

	/**
	 * Run the void main(String[]) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 3/12/14 9:52 PM
	 */
	@Test
	public void testMain_2()
		throws Exception {
		String[] args = new String[] {};

		DeviceServer.main(args);

		// add additional test code here
		// An unexpected exception was thrown in user code while executing this test:
		//    java.lang.SecurityException: Cannot write to files while generating test cases
		//       at com.instantiations.assist.eclipse.junit.CodeProJUnitSecurityManager.checkWrite(CodeProJUnitSecurityManager.java:70)
		//       at java.io.FileOutputStream.<init>(FileOutputStream.java:237)
		//       at java.net.SocketOutputStream.<init>(SocketOutputStream.java:58)
		//       at java.net.AbstractPlainSocketImpl.getOutputStream(AbstractPlainSocketImpl.java:411)
		//       at java.net.Socket$3.run(Socket.java:865)
		//       at java.security.AccessController.doPrivileged(Native Method)
		//       at java.net.Socket.getOutputStream(Socket.java:862)
		//       at com.rabbitmq.client.impl.SocketFrameHandler.<init>(SocketFrameHandler.java:54)
		//       at com.rabbitmq.client.ConnectionFactory.createFrameHandler(ConnectionFactory.java:462)
		//       at com.rabbitmq.client.ConnectionFactory.createFrameHandler(ConnectionFactory.java:447)
		//       at com.rabbitmq.client.ConnectionFactory.newConnection(ConnectionFactory.java:504)
		//       at com.rabbitmq.client.ConnectionFactory.newConnection(ConnectionFactory.java:533)
		//       at com.topaz.communications.MessageService.<init>(MessageService.java:66)
		//       at com.topaz.communications.MessageService.<init>(MessageService.java:80)
		//       at com.topaz.apps.DeviceServer.main(DeviceServer.java:20)
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
		new org.junit.runner.JUnitCore().run(DeviceServerTest.class);
	}
}