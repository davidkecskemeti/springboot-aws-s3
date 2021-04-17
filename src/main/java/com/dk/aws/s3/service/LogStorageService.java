package com.dk.aws.s3.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.time.Instant;

@Service
@Slf4j
public class LogStorageService {

    @Value("${application.bucket.name}")
    private String bucketName;

    @Autowired
    private AmazonS3Client s3Client;

    public String uploadObject(Object object) {
        String json = objectToJsonString(object);
        String fileName = String.valueOf(Instant.now().toEpochMilli()).concat(".json");
        PutObjectResult response = s3Client.putObject(bucketName, "logs/"+fileName, json);
        return "Object uploaded : " + fileName;
    }

    private String objectToJsonString(Object object) {
        try {
            ObjectMapper objectMapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            return objectMapper.writeValueAsString(object);
        } catch (Exception e) {
            log.warn("Error converting Map to String: {}", e.getMessage());
            return object.toString();
        }
    }

}