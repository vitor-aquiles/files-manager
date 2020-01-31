package com.aquiles.filesmanager.controller;

import com.aquiles.filesmanager.config.RabbitMQFactory;
import com.aquiles.filesmanager.config.RabbitMQSender;
import com.aquiles.filesmanager.service.CSVService;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(value = "/csv")
public class CSVController {

    @Autowired
    private CSVService csvService;

    @Autowired
    private RabbitMQSender rabbitMQSender;

    @PostMapping(value = "/convert")
    public ObjectNode csvFile(@RequestParam("file") MultipartFile file, @RequestParam("header") boolean hasHeader){
        ObjectNode obj = csvService.csvToJson(file, hasHeader);
        rabbitMQSender.send(obj.toString());
        return obj;
    }
}
