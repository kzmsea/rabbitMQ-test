package com.kzm.subscribe;

import com.kzm.util.ConnectionUtil;
import com.rabbitmq.client.*;

import java.io.IOException;

public class Recv2 {

    private final static String QUEUE_NAME = "test_queue_work2";

    private final static String EXCHANGE_NAME = "test_exchange_fanout";

    public static void main(String[] args) throws Exception {
        Connection connection = ConnectionUtil.getConnection();
        Channel channel = connection.createChannel();

        //声明队列
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);

        //绑定队列到交换机
        channel.queueBind(QUEUE_NAME,EXCHANGE_NAME,"");

        //同一时刻服务器只会发送一条消息给消费者
        channel.basicQos(1);

        //定义消费者
        DefaultConsumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body, "utf-8");
                System.out.println("message:"+message);
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //返回确认状态
                channel.basicAck(envelope.getDeliveryTag(),false);
            }
        };

        //监听队列
        channel.basicConsume(QUEUE_NAME,false,consumer);
    }

}
