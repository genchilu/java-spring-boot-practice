package url.genchi.messagequeue.rabbitmq;

import com.rabbitmq.client.*;

/**
 * Created by mac on 2016/6/23.
 */
public class Caller {

    private Connection connection;
    private Channel channel;
    private final static String QUEUE_NAME = "hello";
    private String replyQueueName;
    private QueueingConsumer consumer;

    public Caller() throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("192.168.99.102");
        factory.setUsername("g7");
        factory.setPassword("g7");
        connection = factory.newConnection();
        channel = connection.createChannel();

        replyQueueName = channel.queueDeclare().getQueue();
        consumer = new QueueingConsumer(channel);
        channel.basicConsume(replyQueueName, true, consumer);
    }

    public String call(String message) throws Exception {
        String response = null;
        String corrId = java.util.UUID.randomUUID().toString();

        AMQP.BasicProperties props = new AMQP.BasicProperties
                .Builder()
                .correlationId(corrId)
                .replyTo(replyQueueName)
                .build();

        channel.basicPublish("", QUEUE_NAME, props, message.getBytes());

        while (true) {
            QueueingConsumer.Delivery delivery = consumer.nextDelivery();
            if (delivery.getProperties().getCorrelationId().equals(corrId)) {
                response = new String(delivery.getBody());
                break;
            }
        }

        return response;
    }

    public void close() throws Exception {
        connection.close();
    }
    public static void main(String[] args) throws java.lang.Exception {
        Caller caller = new Caller();
        System.out.println(" [x] Requesting 3");
        String response = caller.call("3");
        System.out.println(" [.] Got '" + response + "'");
        caller.close();
    }
}