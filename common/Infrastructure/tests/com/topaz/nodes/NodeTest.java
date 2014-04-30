package com.topaz.nodes;

import com.topaz.communications.protocols.RestProtocol;
import com.topaz.utils.Location;
import  org.junit.Assert;

/**
 * Created by rbgodwin on 4/26/14.
 */

public class NodeTest {

    final static String NODE_ID = "testNodeId";
    final static Node.ActsAs ACTS_AS = Node.ActsAs.DEVICE;
    final static String DESCRIPTION = "test node description";
    final static Location LOCATION = new Location(100.31,200.21,300.12);
    final static Node.STATUS STATUS = Node.STATUS.GREEN;
    final static String[] NODE_IDS = {"TestId 0", "TestId 1", "Testid 2"};

    final static Location[] LOCATIONS = {new Location(100.00, 200.00,300.00),
                                         new Location(83.12,  91.12, 121.12),
                                         new Location(66.21,  129.12, 30000) };

    @org.junit.Test
    public void testBuilder() throws Exception {

    Node node = Node.builder()
            .nodeId(this.NODE_ID)
            .actsAs(this.ACTS_AS)
            .description(this.DESCRIPTION)
            .location(this.LOCATION)
            .status(this.STATUS).build();

        // test that all values are set correctly
        Assert.assertEquals(node.getActsAs(),this.ACTS_AS);
        Assert.assertEquals(node.getNodeId(),this.NODE_ID);
        Assert.assertEquals(node.getDescription(),this.DESCRIPTION);
        Assert.assertEquals(node.getLocation(),this.LOCATION);
        Assert.assertEquals(node.getStatus(),this.STATUS);


    }

    @org.junit.Test
    public void testSetActsAs() throws Exception {

    Node testNode = new Node();

        // Iterate over all the possibilities
        for(Node.ActsAs actsAs : Node.ActsAs.values())
        {
           testNode.setActsAs(actsAs);
           Assert.assertEquals(testNode.getActsAs(),actsAs);
        }
    }

    @org.junit.Test
    public void testSetNodeId() throws Exception {

        Node testNode = new Node();

        // Iterate over all the possibilities
        for(String  nodeId: this.NODE_IDS)
        {
            testNode.setNodeId(nodeId);
            Assert.assertEquals(testNode.getNodeId(), nodeId);
        }
    }


    @org.junit.Test
    public void testSetLocation() throws Exception {

        Node testNode = new Node();

        // Iterate over all the possibilities
        for(Location location : this.LOCATIONS)
        {
            testNode.setLocation(location);
            Assert.assertEquals(testNode.getLocation(),location);
        }


    }

    @org.junit.Test
    public void testSetStatus() throws Exception {

        Node testNode = new Node();

        // Iterate over all the possibilities
        for(Node.STATUS status : Node.STATUS.values())
        {
            testNode.setStatus(status);
            Assert.assertEquals(testNode.getStatus(),status);
        }
    }


    @org.junit.Test
    public void testAddRestProtocol() throws Exception {

        //TODO : add test that sets and checks all parameters
    }
}
