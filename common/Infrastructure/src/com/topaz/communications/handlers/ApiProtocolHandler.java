package com.topaz.communications.handlers;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.rabbitmq.client.QueueingConsumer;
import com.topaz.communications.apis.Api;
import com.topaz.communications.messages.ApiMessage;
import com.topaz.communications.protocols.MessageProtocol;
import com.topaz.communications.servers.MessageService;
import com.topaz.nodes.Node;

import java.io.IOException;
import java.util.HashMap;

/**
 * Created by rbgodwin on 4/24/14.
 */
public class ApiProtocolHandler extends MessageProtocolHandler{

    // GSON to work with JSON
    Gson gson = new Gson();

    private final String THREAD_NAME = "API_PROTOCOL_HANDLER";

    public ApiProtocolHandler(MessageService ms, Node node, MessageProtocol mp) {

        super(ms,node,mp);

    }

    private class MessageClass {
        ApiMessage message;
    }

    @Override
    public void run() {
        //Set the name of this thread
        Thread.currentThread().setName(THREAD_NAME);

        QueueingConsumer.Delivery delivery;
        String apiMessageJson;

        // Setup the exchange and subscribe to the route we need to service
        while (!Thread.interrupted()) {
            try {
                delivery = queueConsumer.nextDelivery();
                apiMessageJson = new String(delivery.getBody());
                System.out.println("ApiProtocolHandler thread received:\n" +
                        apiMessageJson);
            } catch (InterruptedException e) {
                break;
            }

            MessageClass apiMessageWrap = gson.fromJson(apiMessageJson,MessageClass.class);

            ApiMessage apiMessage = apiMessageWrap.message;

            this.nodes.get(apiMessage.getNodeId()).acceptApi(apiMessage.getApi());

            // Send back the response if return for the API is not void
            if(!apiMessage.getApi().getReturns().equals(new String("void")))
            {
                try {
                    // Use the sequence number from the request message
                    apiMessage.setGenSeqNumber(false);

                    // We don't want a response to our response
                    apiMessage.getApi().setReturns("void");

                    messageService.sendMessage(this.messageProtocol.getExchange(),
                            (String)apiMessage.getApi().getParameter(ApiMessage.REPLY_ROUTE),apiMessage);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }
}
