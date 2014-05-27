package com.topaz.communications.servers;

import com.google.gson.Gson;
import com.topaz.communications.apis.Api;
import com.topaz.communications.messages.Message;
import com.topaz.communications.protocols.MessageProtocol;
import com.topaz.nodes.Node;
import com.topaz.communications.messages.ApiMessage;

import java.io.IOException;
import java.util.UUID;

/**
 * Created by rbgodwin on 5/6/14.
 */
public class NodeApiServer {

    // We use both a REST server and a Message Server.  Can be used for redundancy
    protected static MessageService ms;
    protected static RestServer rs;
    protected static Gson gson = new Gson();

    public static void setServers(MessageService ms, RestServer rs) {
        NodeApiServer.ms = ms;
        NodeApiServer.rs = rs;

    }

    public static Api handleApi(Node node, Api api)
    {
        MessageProtocol mProtocol = node.getProtocolSpec().getMessageProtocol("command_server");


        ApiMessage msg = new ApiMessage(api);
        msg.setNodeId(node.getNodeId());


        // For messages with replies we will wait till notified
        synchronized (msg) {
            // Create a message and send it via the Message service
            try {
                ms.sendMessage(mProtocol.getExchange(), mProtocol.getRoutingKey(), msg);
            } catch (IOException e) {
                e.printStackTrace();
            }
            // Return the returned api for the sent message
            return msg.getApi();
        }
    }
}
