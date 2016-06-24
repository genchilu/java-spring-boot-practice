package url.genchi.messagequeue.rabbitmq.rpcclient;

import com.rabbitmq.client.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Created by mac on 2016/6/23.
 */
@Service
public class RpcClientService {

    private Connection connection;
    private Channel channel;
    private final static String QUEUE_NAME = "rpc";
    private String replyQueueName;
    private QueueingConsumer consumer;

    @Autowired
    public RpcClientService(@Value("${rabbitmq.ip}") String rabbitmqIP,
                            @Value("${rabbitmq.username}") String rabbitmqUsername,
                            @Value("${rabbitmq.password}") String rabbitmqPassword) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(rabbitmqIP);
        factory.setUsername(rabbitmqUsername);
        factory.setPassword(rabbitmqPassword);
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
//        RpcClientService caller = new RpcClientService();
//        System.out.println(" [x] Requesting 3");
//        String response = caller.call("3");
//        System.out.println(" [.] Got '" + response + "'");
//        caller.close();
    }
}