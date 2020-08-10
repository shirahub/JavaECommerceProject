package com.broker;

import com.rabbitmq.client.*;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

@Service
public class AppRecv {

    private final static String QUEUE_NAME = "DBKeRest";

    public String receiveFromDB() throws IOException, TimeoutException {
        String message = "";
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        GetResponse response;
        do{
            response = channel.basicGet(QUEUE_NAME, true);
        }while (response == null);
        message = new String(response.getBody(), "UTF-8");
        channel.close();
        connection.close();

        System.out.println("AppRecv : " + message);
        return message;
    }

}
