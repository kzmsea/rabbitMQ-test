package com.kzm.simple;

import com.kzm.util.ConnectionUtil;
import com.rabbitmq.client.*;

import java.io.IOException;

public class Recv {

    private final static String QUEUE_NAME="q_test_01";

    public static void main(String[] args) throws Exception {
        //获取连接
        Connection connection = ConnectionUtil.getConnection();
        //从连接获取通道
        Channel channel = connection.createChannel();
        //声明队列
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);


        //定义队列的消费者
        DefaultConsumer consumer = new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println(body);
                String message = new String(body, "utf-8");
                System.out.println("message:"+message);
            }
        };

        //监听队列
        channel.basicConsume(QUEUE_NAME,true,consumer);

    }

}
