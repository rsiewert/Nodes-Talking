import com.topaz.message.*;
import com.topaz.communication.*;
import com.topaz.handlers.*;

public class DeviceServer {

	private final static String EXCHANGE_NAME = "test-exchange";
	private final static String ROUTE = "the.routing.register";

	/**
	 * @param args
	 */
	public static void main(String[] args) throws java.io.IOException {

	//	MessageService ms = new MessageService("localhost");
		 MessageService ms = new MessageService("thespacetimecontinuum.com",
		 20005);

		Registration regMessage = new Registration(new String(
				"0xfa32da59.gateway"), ProtocolSpec.ActsAs.DEVICE);
		RestProtocol restP = new RestProtocol();
		restP.setDomain("192.168.1.44");
		restP.setEndpoint("/json-mirror");
		restP.setPort(3000);
		regMessage.addRestProtocol("Mirror", restP);

		/* Create a Protocol for MessageQ commands */
		MessageProtocol messP = new MessageProtocol();
		messP.setExchange("deviceExchange.0xfa32da59");
		messP.setQueue("serviceQueue");
		messP.setRoutingKey("0xfa32da59.routing.commands");

		// Send the protocol over to the message service to handle
		regMessage.addMessageProtocol("command_server", messP);

		/* Create a protocol for ACKS from MQ Servers */
		MessageProtocol messA = new MessageProtocol();
		messA.setExchange("deviceExchange.0xfa32da59");
		messA.setQueue("serviceQueue");
		messA.setRoutingKey("0xfa32da59.routing.acks");

		// The application will create and destroy the exchange used to
		// communicate
		messA.setManageExchange(true);
		
		// Notify the MessageService of the new protocol and a protocol server
		ms.addProtocol(messA, new ProtocolHandlerAck());

		// Add the protocol to our registration message
		regMessage.addMessageProtocol("on_ack", messA);

		/* Create a protocol for ON Publish */
		MessageProtocol messOP = new MessageProtocol();
		messOP.setExchange("deviceExchange.0xfa32da59");
		messOP.setQueue("serviceQueue");
		messOP.setRoutingKey("0xfa32da59.routing.on_publish");
		regMessage.addMessageProtocol("on_publish", messOP);
		regMessage.setRequestAck(true);

		for (int i = 0; i < 5; i++) {
			ms.sendMessage(EXCHANGE_NAME, ROUTE, regMessage);
			// For test bump up the sequence number
			regMessage.setSeqId(i);
			System.out.println("\n [x] Sent '" + regMessage.toJsonString()
					+ "'");
			try {
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		ms.close();
	}
}
