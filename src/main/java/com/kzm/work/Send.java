package com.kzm.work;

import com.kzm.util.ConnectionUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

public class Send {

    private final static String QUEUE_NAME="test_queue_work";

    public static void main(String[] args) throws Exception {
        //获取连接
        Connection connection = ConnectionUtil.getConnection();
        //从连接获取通道
        Channel channel = connection.createChannel();
        //定义队列
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);

        for(int i=0;i<100;i++){
            //消息内容
            String message=""+i;
            channel.basicPublish("",QUEUE_NAME,null,message.getBytes());
            System.out.println("message:"+message);
            Thread.sleep(i*10);
        }
        //关闭资源
        channel.close();
        connection.close();
    }

}
