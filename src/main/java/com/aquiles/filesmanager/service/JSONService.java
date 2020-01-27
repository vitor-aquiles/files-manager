package com.aquiles.filesmanager.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.opencsv.CSVWriter;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class JSONService {
    public void jsonToCsv() {
        String json = "{\"data\":[{\"Nome\":\"Aquiles\",\"Idade\":\"26\",\"CPF\":\"41885604874\"},{\"Nome\":\"Vitor\",\"Idade\":\"35\",\"CPF\":\"12345678910\"}]}";

        ObjectMapper mapper = new ObjectMapper();
        ArrayNode arrayData;
        try {
            JsonNode root = mapper.readTree(json);
            Iterator<Map.Entry<String, JsonNode>> fields = root.fields();
            List<String[]> lines = new ArrayList<>();
            while (fields.hasNext()){
                Map.Entry<String, JsonNode> jsonField = fields.next();
                arrayData = (ArrayNode) jsonField.getValue();
                for(JsonNode node: arrayData){
                    Iterator<Map.Entry<String, JsonNode>> nodeFields = node.fields();
                    List<String> data = new ArrayList<>();
                    while(nodeFields.hasNext()){
                        data.add(nodeFields.next().getValue().asText());
                        System.out.println(data);
                    }

                    String[] line = data.toArray(new String[0]);
                    System.out.println(Arrays.toString(line));
                    lines.add(line);
                }
            }
            CSVWriter writer = new CSVWriter(new FileWriter("/Users/Aquiles/Documents/csv-from-json.csv"));
            writer.writeAll(lines);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
