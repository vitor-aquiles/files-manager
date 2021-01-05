package com.aquiles.filesmanager.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;

@Service
public class CSVService {

    public ObjectNode csvToJson(MultipartFile file, Boolean hasHeader){
        //String filePath = "/Users/Aquiles/Documents/test-file.csv";

        BufferedReader br;
        String line;
        String[] lines;
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode root = mapper.createObjectNode();
        ArrayNode data = mapper.createArrayNode();
        try (InputStream is = file.getInputStream()) {
            br = new BufferedReader(new InputStreamReader(is));
            String[] header = new String[0];
            int headerCount = 0;

            while((line = br.readLine()) != null){
                ObjectNode values = mapper.createObjectNode();
                lines = line.split(";");
                System.out.println("Line: " + Arrays.toString(lines));
                if(hasHeader && (header.length == 0)){
                    header = lines;
                }

                for(int i = 0; i < lines.length ; i++){
                    if(hasHeader){
                        values.put(header[i], lines[i]);
                    }else{
                        values.put(String.valueOf(i), lines[i]);
                    }
                }
                if(headerCount > 0) {
                    data.add(values);
                }
                headerCount++;
            }
            root.set("data", data);
            System.out.println(root);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return root;
    }
}
