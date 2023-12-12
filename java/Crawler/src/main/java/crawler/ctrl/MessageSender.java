package crawler.ctrl;

import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;
import javax.jms.Queue;
import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MessageSender {
    private static final String url = "tcp://artemis:61616";
    public static final String username = "artemis";
    public static final String password = "artemis";
    private static final String queueName = "MESSAGE_QUEUE";

    private static final Logger logger = LoggerFactory.getLogger(MessageSender.class);

    public static void sendMessage(String fileName) {
        try {
            ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url, username, password);
            Connection connection = connectionFactory.createConnection();
            connection.start();

            logger.info("Connection to ActiveMQ established.");

            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Queue queue = session.createQueue(queueName);

            MessageProducer producer = session.createProducer(queue);
            TextMessage textMessage = session.createTextMessage(fileName);
            producer.send(textMessage);

            logger.info("Sent: " + fileName);

            producer.close();
            session.close();
            connection.close();

        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

}