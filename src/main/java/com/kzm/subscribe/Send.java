package com.kzm.subscribe;

import com.kzm.util.ConnectionUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

public class Send {

    private final static String EXCHANGE_NAME="test_exchange_fanout";

    public static void main(String[] args) throws Exception {
        Connection connection = ConnectionUtil.getConnection();
        Channel channel = connection.createChannel();

        //声明exchange
        channel.exchangeDeclare(EXCHANGE_NAME,"fanout");

        String message="Hello World";
        //给交换机发消息
        channel.basicPublish(EXCHANGE_NAME,"",null,message.getBytes());
        System.out.println("message:"+message);

        channel.close();
        connection.close();
    }

}
