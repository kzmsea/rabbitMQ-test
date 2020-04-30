package com.kzm.route;

import com.kzm.util.ConnectionUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

public class Send {

    private final static String EXCHANGE_NAME="test_exchange_direct";

    public static void main(String[] args) throws Exception {
        Connection connection = ConnectionUtil.getConnection();
        Channel channel = connection.createChannel();

        //声明exchange
        //direct:交换机类型
        channel.exchangeDeclare(EXCHANGE_NAME,"direct");

        //消息内容
        String message="删除商品";
        //delete:消息key
        channel.basicPublish(EXCHANGE_NAME,"delete",null,message.getBytes());
        System.out.println("message:"+message);
        channel.basicPublish(EXCHANGE_NAME,"info",null,"this is info".getBytes());

        channel.close();
        connection.close();
    }

}
