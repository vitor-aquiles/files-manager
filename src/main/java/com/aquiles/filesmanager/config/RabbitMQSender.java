package com.aquiles.filesmanager.config;

import com.rabbitmq.client.Channel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.concurrent.TimeoutException;
@Service
public class RabbitMQSender {
    private final static String QUEUE_NAME = "hello";

    @Autowired
    private RabbitMQFactory factory;

    public void send(String json){
        try(Channel channel = factory.getConnection()){
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            channel.basicPublish("", QUEUE_NAME, null, json.getBytes());
            System.out.println(" [x] Sent '" + json + "'");
        } catch (TimeoutException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
