package url.genchi.messagequeue.rabbitmq.rpcserver;

import com.rabbitmq.client.*;

import java.util.concurrent.TimeoutException;

/**
 * Created by mac on 2016/6/23.
 */
public class RpcServer {
    private final static String QUEUE_NAME = "rpc";
    public static void main(String[] args)  throws java.io.IOException, TimeoutException, InterruptedException{
        Connection connection = null;
        Channel channel = null;
        try {
            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost("192.168.99.102");
            factory.setUsername("g7");
            factory.setPassword("g7");
            connection = factory.newConnection();
            channel = connection.createChannel();
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            channel.basicQos(1);
            QueueingConsumer consumer = new QueueingConsumer(channel);
            channel.basicConsume(QUEUE_NAME, false, consumer);
            System.out.println(" [x] Awaiting RPC requests");
            while (true) {
                String response = null;
                QueueingConsumer.Delivery delivery = consumer.nextDelivery();
                AMQP.BasicProperties props = delivery.getProperties();
                AMQP.BasicProperties replyProps = new AMQP.BasicProperties().builder()
                        .correlationId(props.getCorrelationId())
                        .build();
                try {
                    String message = new String(delivery.getBody(),"UTF-8");
                    Thread.sleep(10000);
                    response = "return resoult of " + message;
                }
                catch (Exception e){
                    System.out.println(" [.] " + e.toString());
                    response = "";
                }
                finally {
                    channel.basicPublish( "", props.getReplyTo(), replyProps, response.getBytes("UTF-8"));
                    channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
                }
            }
        }
        catch  (Exception e) {
            e.printStackTrace();
        }
        finally {
            if (connection != null) {
                try {
                    connection.close();
                }
                catch (Exception ignore) {}
            }
        }
    }
}
