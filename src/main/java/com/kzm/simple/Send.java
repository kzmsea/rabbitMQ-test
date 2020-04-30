package com.kzm.simple;

import com.kzm.util.ConnectionUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Send {

    private final static String QUEUE_NAME="q_test_01";

    public static void main(String[] args) throws Exception {
        //获取连接
        Connection connection = ConnectionUtil.getConnection();
        //从连接中获取通道
        Channel channel = connection.createChannel();

        //声明队列
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);

        //消息内容
        String message="Hello World";
        channel.basicPublish("",QUEUE_NAME,null,message.getBytes());
        System.out.println("send:"+message);
        //关闭连接和通道
        channel.close();
        connection.close();
    }

}
