package com.topaz.communications.handlers;

import com.google.gson.Gson;
import com.rabbitmq.client.QueueingConsumer;
import com.topaz.communications.messages.ApiMessage;
import com.topaz.communications.protocols.MessageProtocol;
import com.topaz.communications.servers.MessageService;
import com.topaz.nodes.Node;

import java.io.IOException;

/**
 * Created by rbgodwin on 5/8/14.
 */
public class ApiResponseProtocolHandler extends MessageProtocolHandler {

    // GSON to work with JSON
    Gson gson = new Gson();

    private final String THREAD_NAME = "API_RESP_PROTOCOL_HANDLER";

    public ApiResponseProtocolHandler(MessageService ms, Node node, MessageProtocol mp) {

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
                System.out.println("ApiResponseProtocolHandler thread received:\n" +
                        apiMessageJson);
            } catch (InterruptedException e) {
                break;
            }

            MessageClass apiMessageWrap = gson.fromJson(apiMessageJson,MessageClass.class);

            ApiMessage apiMessage = apiMessageWrap.message;

            // Look up the waiting original message api and load up the response
            ApiMessage original = (ApiMessage)this.messageService.getResultMsg(apiMessage.getSeqId());

            synchronized (original) {
                // Pass up the received data to the original caller
                original.setApi(apiMessage.getApi());

                // Notify the caller that the message response is ready
                System.out.println("Notifying waiter");
                original.notify();
            }
        }
    }


}
