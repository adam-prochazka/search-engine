package books;

import jakarta.jms.*;
import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

public class MessageSender {

    private static final String url = ActiveMQConnection.DEFAULT_BROKER_URL;
    private static final String queueName = "MESSAGE_QUEUE";
    public static void sendMessage(String fileName) {
        try {
            ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);
            Connection connection = connectionFactory.createConnection();
            connection.start();

            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

            Destination destino = session.createQueue(queueName);

            MessageProducer producer = session.createProducer(destino);

            TextMessage textMessage = session.createTextMessage(fileName);
            producer.send(textMessage);

            producer.close();
            session.close();
            connection.close();

        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

}
