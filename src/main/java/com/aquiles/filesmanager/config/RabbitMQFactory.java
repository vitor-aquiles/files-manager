package com.aquiles.filesmanager.config;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class RabbitMQFactory {

    private ConnectionFactory factory;

    public Channel getConnection() throws Exception{
        factory.setHost("localhost");
        try (Connection connection = factory.newConnection(); Channel channel = connection.createChannel()){
            return channel;
        }
    }

}
