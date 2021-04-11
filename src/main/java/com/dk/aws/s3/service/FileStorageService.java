package com.dk.aws.s3.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.util.IOUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;

@Service
@Slf4j
public class FileStorageService {

    @Value("${application.bucket.name}")
    private String bucketName;

    @Autowired
    private AmazonS3Client s3Client;

    public String uploadFile(MultipartFile file) {
        File outputFile = convertMultipartFileToFile(file);
        String fileName = file.getOriginalFilename() + "_" + System.currentTimeMillis();
        s3Client.putObject(new PutObjectRequest(bucketName, fileName, outputFile));
        outputFile.delete();
        return "File uploaded : " + fileName;
    }

    public byte[] downloadFile(String fileName) {
        S3Object s3Object = s3Client.getObject(bucketName, fileName);
        S3ObjectInputStream inputStream = s3Object.getObjectContent();
        try {
            byte[] content = IOUtils.toByteArray(inputStream);
            return content;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String deleteFile(String fileName){
        s3Client.deleteObject(bucketName,fileName);
        return fileName+" removed.";
    }

    private File convertMultipartFileToFile(MultipartFile file) {
        File convertedFile = new File(file.getOriginalFilename());
        try {
            FileOutputStream stream = new FileOutputStream(convertedFile);
            stream.write(file.getBytes());
        } catch (Exception e) {
            log.error("Error at converting MultipartFile to File", e);
        }
        return convertedFile;
    }
}
