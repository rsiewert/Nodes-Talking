package com.topaz.communications.messages;

import java.util.Date;
import org.junit.*;

import com.topaz.communications.messages.Message;

import static org.junit.Assert.*;

/**
 * The class <code>MessageTest</code> contains tests for the class <code>{@link Message}</code>.
 *
 * @generatedBy CodePro at 3/12/14 9:52 PM
 * @author rbgodwin
 * @version $Revision: 1.0 $
 */
public class MessageTest {
	/**
	 * Run the Message() constructor test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 3/12/14 9:52 PM
	 */
	@Test
	public void testMessage_1()
		throws Exception {

		Message result = new Message();
		assertNotNull(result);
		assertEquals(null, result.getNodeId());
		assertEquals(0L, result.getSeqId());
		assertEquals(Boolean.FALSE, result.getRequestAck());
	}


	/**
	 * Run the String getNodeId() method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 3/12/14 9:52 PM
	 */
	@Test
	public void testGetNodeId_1()
		throws Exception {
		Message fixture = new Message();
		fixture.setNodeId("");
		fixture.setOriginTimestamp(new Date());
		fixture.setSeqId(1L);
		fixture.setRequestAck(new Boolean(true));

		String result = fixture.getNodeId();

		// add additional test code here
		assertEquals("", result);
	}

	/**
	 * Run the Date getOriginTimestamp() method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 3/12/14 9:52 PM
	 */
	@Test
	public void testGetOriginTimestamp_1()
		throws Exception {
		Message fixture = new Message();
		fixture.setNodeId("");
		Date testDate = new Date();
		fixture.setOriginTimestamp(testDate);
		fixture.setSeqId(1L);
		fixture.setRequestAck(new Boolean(true));

		Date result = fixture.getOriginTimestamp();

		// add additional test code here
		assertNotNull(result);
		assertEquals(testDate.getTime(), result.getTime());
	}

	/**
	 * Run the Boolean getRequestAck() method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 3/12/14 9:52 PM
	 */
	@Test
	public void testGetRequestAck_1()
		throws Exception {
		Message fixture = new Message();
		fixture.setNodeId("");
		fixture.setOriginTimestamp(new Date());
		fixture.setSeqId(1L);
		fixture.setRequestAck(new Boolean(true));

		Boolean result = fixture.getRequestAck();

		// add additional test code here
		assertNotNull(result);
		assertEquals("true", result.toString());
		assertEquals(true, result.booleanValue());
	}

	/**
	 * Run the long getSeqId() method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 3/12/14 9:52 PM
	 */
	@Test
	public void testGetSeqId_1()
		throws Exception {
		Message fixture = new Message();
		fixture.setNodeId("");
		fixture.setOriginTimestamp(new Date());
		fixture.setSeqId(1L);
		fixture.setRequestAck(new Boolean(true));

		long result = fixture.getSeqId();

		// add additional test code here
		assertEquals(1L, result);
	}
	/**
	 * Run the void setNodeId(String) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 3/12/14 9:52 PM
	 */
	@Test
	public void testSetNodeId_1()
		throws Exception {
		Message fixture = new Message();
		fixture.setNodeId("");
		fixture.setOriginTimestamp(new Date());
		fixture.setSeqId(1L);
		fixture.setRequestAck(new Boolean(true));
		String nodeId = "";

		fixture.setNodeId(nodeId);

		// add additional test code here
	}

	/**
	 * Run the void setOrigTimestamp(Date) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 3/12/14 9:52 PM
	 */
	@Test
	public void testSetOrigTimestamp_1()
		throws Exception {
		Message fixture = new Message();
		fixture.setNodeId("");
		fixture.setOriginTimestamp(new Date());
		fixture.setSeqId(1L);
		fixture.setRequestAck(new Boolean(true));
		Date timestamp = new Date();

		fixture.setOrigTimestamp(timestamp);

		// add additional test code here
	}

	/**
	 * Run the void setOriginTimestamp(Date) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 3/12/14 9:52 PM
	 */
	@Test
	public void testSetOriginTimestamp_1()
		throws Exception {
		Message fixture = new Message();
		fixture.setNodeId("");
		fixture.setOriginTimestamp(new Date());
		fixture.setSeqId(1L);
		fixture.setRequestAck(new Boolean(true));
		Date originTimestamp = new Date();

		fixture.setOriginTimestamp(originTimestamp);

		// add additional test code here
	}

	/**
	 * Run the void setRequestAck(Boolean) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 3/12/14 9:52 PM
	 */
	@Test
	public void testSetRequestAck_1()
		throws Exception {
		Message fixture = new Message();
		fixture.setNodeId("");
		fixture.setOriginTimestamp(new Date());
		fixture.setSeqId(1L);
		fixture.setRequestAck(new Boolean(true));
		Boolean requestAck = new Boolean(true);

		fixture.setRequestAck(requestAck);

		// add additional test code here
	}

	/**
	 * Run the void setSeqId(long) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 3/12/14 9:52 PM
	 */
	@Test
	public void testSetSeqId_1()
		throws Exception {
		Message fixture = new Message();
		fixture.setNodeId("");
		fixture.setOriginTimestamp(new Date());
		fixture.setSeqId(1L);
		fixture.setRequestAck(new Boolean(true));
		long seqId = 1L;

		fixture.setSeqId(seqId);

		// add additional test code here
	}

	/**
	 * Run the String toJsonString() method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 3/12/14 9:52 PM
	 */
	@Test
	public void testToJsonString_1()
		throws Exception {
		Message fixture = new Message();
		fixture.setNodeId("");
		fixture.setOriginTimestamp(new Date());
		fixture.setSeqId(1L);
		fixture.setRequestAck(new Boolean(true));

		String result = fixture.toJsonString();

		assertNotNull(result);
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
		new org.junit.runner.JUnitCore().run(MessageTest.class);
	}
}