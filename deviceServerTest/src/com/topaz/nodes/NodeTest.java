package com.topaz.nodes;

import org.junit.*;
import static org.junit.Assert.*;

import com.topaz.communications.protocols.MessageProtocol;
import com.topaz.communications.protocols.ProtocolSpec;
import com.topaz.communications.protocols.RestProtocol;
import com.topaz.nodes.Node.builder;
import com.topaz.utils.Location;

/**
 * The class <code>NodeTest</code> contains tests for the class <code>{@link Node}</code>.
 *
 * @generatedBy CodePro at 3/12/14 9:52 PM
 * @author rbgodwin
 * @version $Revision: 1.0 $
 */
public class NodeTest {
	/**
	 * Run the Node() constructor test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 3/12/14 9:52 PM
	 */
	@Test
	public void testNode_1()
		throws Exception {

		Node result = new Node();

		// add additional test code here
		assertNotNull(result);
		assertEquals(null, result.getLocation());
		assertEquals(null, result.getStatus());
		assertEquals(null, result.getDescription());
		assertEquals(null, result.getNodeId());
		assertEquals(null, result.getActsAs());
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
		Node fixture = new Node();
		fixture.setLocation(new Location(1.0, 1.0, 1.0));
		fixture.setProtocolSpec(new ProtocolSpec());
		fixture.setStatus(Node.STATUS.BLACK);
		fixture.setNodeId("");
		fixture.setActsAs(Node.ActsAs.DEVICE);
		fixture.setDescription("");
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
		Node fixture = new Node();
		fixture.setLocation(new Location(1.0, 1.0, 1.0));
		fixture.setProtocolSpec(new ProtocolSpec());
		fixture.setStatus(Node.STATUS.BLACK);
		fixture.setNodeId("");
		fixture.setActsAs(Node.ActsAs.DEVICE);
		fixture.setDescription("");
		String name = "";
		RestProtocol rest = new RestProtocol();

		fixture.addRestProtocol(name, rest);

		// add additional test code here
	}

	/**
	 * Run the Node.builder<Node.builder> builder() method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 3/12/14 9:52 PM
	 */
	@Test
	public void testBuilder_1()
		throws Exception {

		builder<?> result = Node.builder();

		// add additional test code here
		assertNotNull(result);
	}

	/**
	 * Run the Node.ActsAs getActsAs() method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 3/12/14 9:52 PM
	 */
	@Test
	public void testGetActsAs_1()
		throws Exception {
		Node fixture = new Node();
		fixture.setLocation(new Location(1.0, 1.0, 1.0));
		fixture.setProtocolSpec(new ProtocolSpec());
		fixture.setStatus(Node.STATUS.BLACK);
		fixture.setNodeId("");
		fixture.setActsAs(Node.ActsAs.DEVICE);
		fixture.setDescription("");

		Node.ActsAs result = fixture.getActsAs();

		// add additional test code here
		assertNotNull(result);
		assertEquals("DEVICE", result.name());
		assertEquals("DEVICE", result.toString());
		assertEquals(0, result.ordinal());
	}

	/**
	 * Run the String getDescription() method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 3/12/14 9:52 PM
	 */
	@Test
	public void testGetDescription_1()
		throws Exception {
		Node fixture = new Node();
		fixture.setLocation(new Location(1.0, 1.0, 1.0));
		fixture.setProtocolSpec(new ProtocolSpec());
		fixture.setStatus(Node.STATUS.BLACK);
		fixture.setNodeId("");
		fixture.setActsAs(Node.ActsAs.DEVICE);
		fixture.setDescription("");

		String result = fixture.getDescription();

		// add additional test code here
		assertEquals("", result);
	}

	/**
	 * Run the Location getLocation() method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 3/12/14 9:52 PM
	 */
	@Test
	public void testGetLocation_1()
		throws Exception {
		Node fixture = new Node();
		fixture.setLocation(new Location(1.0, 1.0, 1.0));
		fixture.setProtocolSpec(new ProtocolSpec());
		fixture.setStatus(Node.STATUS.BLACK);
		fixture.setNodeId("");
		fixture.setActsAs(Node.ActsAs.DEVICE);
		fixture.setDescription("");

		Location result = fixture.getLocation();

		// add additional test code here
		assertNotNull(result);
		assertEquals(1.0, result.getLatitude(), 1.0);
		assertEquals(1.0, result.getLongitude(), 1.0);
		assertEquals(1.0, result.getAltitude(), 1.0);
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
		Node fixture = new Node();
		fixture.setLocation(new Location(1.0, 1.0, 1.0));
		fixture.setProtocolSpec(new ProtocolSpec());
		fixture.setStatus(Node.STATUS.BLACK);
		fixture.setNodeId("");
		fixture.setActsAs(Node.ActsAs.DEVICE);
		fixture.setDescription("");

		String result = fixture.getNodeId();

		// add additional test code here
		assertEquals("", result);
	}

	/**
	 * Run the ProtocolSpec getProtocolSpec() method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 3/12/14 9:52 PM
	 */
	@Test
	public void testGetProtocolSpec_1()
		throws Exception {
		Node fixture = new Node();
		fixture.setLocation(new Location(1.0, 1.0, 1.0));
		fixture.setProtocolSpec(new ProtocolSpec());
		fixture.setStatus(Node.STATUS.BLACK);
		fixture.setNodeId("");
		fixture.setActsAs(Node.ActsAs.DEVICE);
		fixture.setDescription("");

		ProtocolSpec result = fixture.getProtocolSpec();

		// add additional test code here
		assertNotNull(result);
	}

	/**
	 * Run the Node.STATUS getStatus() method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 3/12/14 9:52 PM
	 */
	@Test
	public void testGetStatus_1()
		throws Exception {
		Node fixture = new Node();
		fixture.setLocation(new Location(1.0, 1.0, 1.0));
		fixture.setProtocolSpec(new ProtocolSpec());
		fixture.setStatus(Node.STATUS.BLACK);
		fixture.setNodeId("");
		fixture.setActsAs(Node.ActsAs.DEVICE);
		fixture.setDescription("");

		Node.STATUS result = fixture.getStatus();

		// add additional test code here
		assertNotNull(result);
		assertEquals("BLACK", result.name());
		assertEquals("BLACK", result.toString());
		assertEquals(3, result.ordinal());
	}

	/**
	 * Run the void setActsAs(ActsAs) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 3/12/14 9:52 PM
	 */
	@Test
	public void testSetActsAs_1()
		throws Exception {
		Node fixture = new Node();
		fixture.setLocation(new Location(1.0, 1.0, 1.0));
		fixture.setProtocolSpec(new ProtocolSpec());
		fixture.setStatus(Node.STATUS.BLACK);
		fixture.setNodeId("");
		fixture.setActsAs(Node.ActsAs.DEVICE);
		fixture.setDescription("");
		Node.ActsAs actsAs = Node.ActsAs.DEVICE;

		fixture.setActsAs(actsAs);

		// add additional test code here
	}

	/**
	 * Run the void setDescription(String) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 3/12/14 9:52 PM
	 */
	@Test
	public void testSetDescription_1()
		throws Exception {
		Node fixture = new Node();
		fixture.setLocation(new Location(1.0, 1.0, 1.0));
		fixture.setProtocolSpec(new ProtocolSpec());
		fixture.setStatus(Node.STATUS.BLACK);
		fixture.setNodeId("");
		fixture.setActsAs(Node.ActsAs.DEVICE);
		fixture.setDescription("");
		String description = "";

		fixture.setDescription(description);

		// add additional test code here
	}

	/**
	 * Run the void setLocation(Location) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 3/12/14 9:52 PM
	 */
	@Test
	public void testSetLocation_1()
		throws Exception {
		Node fixture = new Node();
		fixture.setLocation(new Location(1.0, 1.0, 1.0));
		fixture.setProtocolSpec(new ProtocolSpec());
		fixture.setStatus(Node.STATUS.BLACK);
		fixture.setNodeId("");
		fixture.setActsAs(Node.ActsAs.DEVICE);
		fixture.setDescription("");
		Location location = new Location(1.0, 1.0, 1.0);

		fixture.setLocation(location);

		// add additional test code here
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
		Node fixture = new Node();
		fixture.setLocation(new Location(1.0, 1.0, 1.0));
		fixture.setProtocolSpec(new ProtocolSpec());
		fixture.setStatus(Node.STATUS.BLACK);
		fixture.setNodeId("");
		fixture.setActsAs(Node.ActsAs.DEVICE);
		fixture.setDescription("");
		String nodeId = "";

		fixture.setNodeId(nodeId);

		// add additional test code here
	}

	/**
	 * Run the void setProtocolSpec(ProtocolSpec) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 3/12/14 9:52 PM
	 */
	@Test
	public void testSetProtocolSpec_1()
		throws Exception {
		Node fixture = new Node();
		fixture.setLocation(new Location(1.0, 1.0, 1.0));
		fixture.setProtocolSpec(new ProtocolSpec());
		fixture.setStatus(Node.STATUS.BLACK);
		fixture.setNodeId("");
		fixture.setActsAs(Node.ActsAs.DEVICE);
		fixture.setDescription("");
		ProtocolSpec protocolSpec = new ProtocolSpec();

		fixture.setProtocolSpec(protocolSpec);

		// add additional test code here
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
		Node fixture = new Node();
		fixture.setLocation(new Location(1.0, 1.0, 1.0));
		fixture.setProtocolSpec(new ProtocolSpec());
		fixture.setStatus(Node.STATUS.BLACK);
		fixture.setNodeId("");
		fixture.setActsAs(Node.ActsAs.DEVICE);
		fixture.setDescription("");
		Node.STATUS status = Node.STATUS.BLACK;

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
		new org.junit.runner.JUnitCore().run(NodeTest.class);
	}
}