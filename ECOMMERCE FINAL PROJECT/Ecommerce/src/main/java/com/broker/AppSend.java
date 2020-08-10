package com.broker;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class AppSend {

    private static final String EXCHANGE_NAME = "logs";

    public void sendToDB(String a) throws Exception {
        System.out.println("JSONObject diterima RESTAPI dan akan dikirim ke RabbitMQ");
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            //durable = true;
            channel.exchangeDeclare(EXCHANGE_NAME, "fanout",true);

            String message = a;
            //persistent
            channel.basicPublish(EXCHANGE_NAME, "", MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes("UTF-8"));
            System.out.println(" [x] Sent '" + message + "'");
        }
    }
}
