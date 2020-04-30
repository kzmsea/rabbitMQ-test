package com.kzm.work;

import com.kzm.util.ConnectionUtil;
import com.rabbitmq.client.*;

import java.io.IOException;

public class Recv {

    private final static String QUEUE_NAME="test_queue_work";

    public static void main(String[] args) throws Exception {
        //获取连接
        Connection connection = ConnectionUtil.getConnection();
        //从连接获取通道
        Channel channel = connection.createChannel();

        //声明队列
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);

        //同一时刻服务器只会发一条消息给消费者
        channel.basicQos(1);

        //定义队列的消费者
        DefaultConsumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body, "utf-8");
                System.out.println("message:" + message);
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //返回确认状态，注释掉表示使用自动确认模式
                channel.basicAck(envelope.getDeliveryTag(),false);
            }
        };

        //监听队列，false表示手动返回完成状态，true表示自动
        channel.basicConsume(QUEUE_NAME,false,consumer);
    }

}
