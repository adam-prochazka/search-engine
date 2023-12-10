import datalake.DataLake;
import jakarta.jms.*;
import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

public class Receiver implements MessageListener {
    private static final String url = ActiveMQConnection.DEFAULT_BROKER_URL;
    private static final String queueName = "MESSAGE_QUEUE";

    public Receiver() {
        try {
            ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);
            Connection connection = connectionFactory.createConnection();
            connection.start();

            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

            Destination destino = session.createQueue(queueName);

            MessageConsumer consumer = session.createConsumer(destino);

            consumer.setMessageListener(this);

        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onMessage(Message message) {
        try {
            if (message instanceof TextMessage) {

                TextMessage textMessage = (TextMessage) message;
                String bookFilename = textMessage.getText();

                DataLake dataLake = new DataLake();
                new FileSystemIndexer(dataLake).indexOne(dataLake.getDataLakePath()+bookFilename);

            }

        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
