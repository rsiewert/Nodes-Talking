package com.topaz.communications.handlers;

import com.topaz.communications.protocols.MessageProtocol;
import com.topaz.communications.servers.MessageService;
import com.topaz.nodes.Node;

/**
 * Created by rbgodwin on 4/24/14.
 */
public class ControlProtocolHandler extends MessageProtocolHandler{


    public ControlProtocolHandler(MessageService ms, Node node, MessageProtocol mp) {

        super(ms,node,mp);

    }

    @Override
    public void run() {
        super.run();

    }
}
