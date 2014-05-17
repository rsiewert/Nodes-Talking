package com.topaz.communications.servers;

import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.AMQP.*;
import com.topaz.communications.handlers.*;
import com.topaz.communications.messages.*;
import com.topaz.communications.protocols.MessageProtocol;

import java.io.IOException;
import java.util.*;
public class MessageService {

	// Information about the connection to the message server 
	private Channel channel;
	private Connection connection;
	private BasicProperties bp;

    protected static final UUID myResponseRoute = UUID.randomUUID();
    protected static final UUID myResponseQueue = UUID.randomUUID();

    protected static final int TIMEOUT = 50000;

    // Api Response Handlers for each exchange messages sent out on
    protected HashMap<String,ApiResponseProtocolHandler> myResponseHandlers =
            new HashMap<String, ApiResponseProtocolHandler>();

	// Sequence id used to stamp a message
	private static long seqId = 0;
	
	private static MessageService singleMS = null;
	
	private ArrayList<Thread> serviceThreads = new ArrayList<Thread>();
	
	//  Protocols managed by the MessageService
	private ArrayList<MessageProtocol> messageProtocols = 
			new ArrayList<MessageProtocol>();

	private ArrayList<String> exchangeNames = new ArrayList<String>();


	// Some of our messages will want to check if there is an ack in response
	private HashMap<Long, Message>  ackChecks = new HashMap<Long, Message>();


    // Messages with a return value
    private HashMap<Long, Message>  resultMgs = new HashMap<Long, Message>();


    public Message getResultMsg(Long seqNumber) {
        Message resultWaiter =  this.resultMgs.get(seqNumber);
        this.resultMgs.remove(seqNumber);
        return resultWaiter;
    }


    public static MessageService getMessageService() throws IOException {

		// Check if we have an instance of the message service
		if (MessageService.singleMS != null)
			return MessageService.singleMS;
	
		// If not then log an error and throw and io exception
		System.out.println("getMessageService: Error Null Message Service\n");
		IOException e = new IOException("Must insitialize with a port and IP");
		throw e;
	}
	
	public static MessageService getMessageService(String host) throws IOException {

		// Check if we have an instance of the message service
		if (MessageService.singleMS != null)
			return MessageService.singleMS;
	
		// If not then create one and return it
		MessageService.singleMS = new MessageService(host);
		return MessageService.singleMS;
		}
	
	
	public static MessageService getMessageService(String host, int port) {

		// Check if we have an instance of the message service
		if (MessageService.singleMS != null)
			return MessageService.singleMS;
	
		// If not then create one and return it
		MessageService.singleMS = new MessageService(host,port);
		return MessageService.singleMS;
		
	}
	
	// Constructor that connects to the message server 
	public MessageService(String host, int port)  {

		ConnectionFactory factory = new ConnectionFactory();	
		factory.setHost(host);
		if(port != 0)
			factory.setPort(port);
		try {
			connection = factory.newConnection();
			channel = connection.createChannel();
			bp =  new BasicProperties().builder()
					.contentType("application/json")
					.build();
		} catch (IOException e) {
			e.printStackTrace();
		}
		singleMS = this;
	}
	
	// Telescoping constructor that uses the default port for the message server
	public MessageService(String host)  throws java.io.IOException {
		
		this(host, 0);

	}

	// Clean up the connection to the message server
	public void close()  throws java.io.IOException {
		
		// Stop all our protocol handling threads
		for(Thread protocolHandlerThread: this.serviceThreads)
		{
			protocolHandlerThread.interrupt();
			try {
				protocolHandlerThread.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
		}
		// Delete all temporary exchanges
		for(MessageProtocol protocol: this.messageProtocols)
		{
			if(protocol.getManageExchange() == true) {
				if(this.exchangeNames.contains(protocol.getExchange())) {
					System.out.println("Deleted Exchange" + protocol.getExchange());
					this.channel.exchangeDelete(protocol.getExchange());
					this.exchangeNames.remove(protocol.getExchange());
				}
			}
			
		}
		// Close the channel and connection
		if(channel != null)
		{
			channel.close();
			channel = null;
		}
		if(connection != null) {
			connection.close();
			connection = null;
		}
		
		// Set the singleton to NULL to make next use of the Service create a new MS
		MessageService.singleMS = null;
		
		System.out.println("Closed Channel and Connection\n");
	
	}
		
	// Send a message out to a particular exchange with a given route
	public void sendMessage(String exchange, String route, Message msg)
            throws java.io.IOException {

        boolean waitForReply = false;

        synchronized (msg) {

            // Set the Sequence ID of the message
            if(msg.getGenSeqNumber() == true)
                msg.setSeqId(seqId++);

            // Check for API messages that have return values
           if(msg instanceof ApiMessage)
            {
                String returns = ((ApiMessage) msg).getApi().getReturns();
                if(returns != null && !returns.equals("void"))
                {
                    // Add a reply route
                    ((ApiMessage) msg).getApi().addParameter(ApiMessage.REPLY_ROUTE,
                            this.myResponseRoute.toString());

                    // Put the message on our list
                    this.resultMgs.put(msg.getSeqId(),msg);
                    System.out.println("Putting message on Results");

                    // Check if we have a reply protocol handler for this exchange
                    if(myResponseHandlers.get(exchange) == null)
                    {
                        // If not then create one
                        ApiResponseProtocolHandler respProtHandler =
                            new ApiResponseProtocolHandler(this, null,
                                        new MessageProtocol(exchange,
                                        this.myResponseQueue.toString(),
                                        this.myResponseRoute.toString()));


                        // Put it on our saved response handlers
                        myResponseHandlers.put(exchange,respProtHandler);

                        // Register it
                        this.addProtocolHandler(respProtHandler);
                    }
                    waitForReply = true;
                }
            }

            // Check if we are requesting and ACK. If so put the message on the ACK List
            if (msg.getRequestAck())
                this.addAckMessage(msg);

            channel.basicPublish(exchange, route, bp, msg.toJsonString().getBytes());
            System.out.println("Sent:exchange-" + exchange + " route:" + route + msg.toJsonString() );

            // Notify all waiters that all clear if no need to wait for reply
            if(waitForReply == true) {

                try {
                    msg.wait(TIMEOUT);
                    System.out.println("Msg Wait Ended");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    throw (new IOException("Response Timeout"));
                }

            }

        }

	}
	
	// Add a message to the group of messages waiting for an ACK
	private void addAckMessage(Message msg) {
		
		// Put the message on the ackable list with it's sequence number as the key
		this.ackChecks.put(msg.getSeqId(), msg);

	}

	// Create all the message exchanges and register
	public void addProtocolHandler(MessageProtocolHandler protocolHandler){
	
		
		// Set the protocol handler's channel
		protocolHandler.setChannel(this.getChannel());
		
		// Set up the exchange, queue etc for the protocol handler
		String exchange = protocolHandler.getMessageProtocol().getExchange();
		String routingKey = protocolHandler.getMessageProtocol().getRoutingKey();
		String queueName = protocolHandler.getMessageProtocol().getQueue();

		try {
			// Create our exchange
			this.getChannel().exchangeDeclare(exchange, "topic");
			if(!this.exchangeNames.contains(exchange))
				this.exchangeNames.add(exchange);
			// Add our queue 
			if(queueName != null) {
				this.getChannel().queueDeclare(queueName, true, false, false, null);
				this.getChannel().queueBind(queueName, exchange, routingKey);
			}
			
			if(protocolHandler.getQueueConsumer() != null && queueName != null) {
				// Bind our consumer to the queue
				this.getChannel().basicConsume(queueName, true, protocolHandler.getQueueConsumer());
			}
		}
		catch  (IOException e1) {
			e1.printStackTrace();
		}
		
		// Create the thread for the handler function
		Thread handlerThread = new Thread(protocolHandler);

		// Add the thread to our list of threads 
		this.serviceThreads.add(handlerThread);
	
		// Add the protocol the handler uses to our list
		this.messageProtocols.add(protocolHandler.getMessageProtocol());
		// Crank it up
		handlerThread.start();

	}

	/**
	 * @return the channel
	 */
	public Channel getChannel() {
		return channel;
	}

	/**
	 * @param channel the channel to set
	 */
	public void setChannel(Channel channel) {
		this.channel = channel;
	}

	/**
	 * @return the connection
	 */
	public Connection getConnection() {
		return connection;
	}

	/**
	 * @param connection the connection to set
	 */
	public void setConnection(Connection connection) {
		this.connection = connection;
	}

	
}

