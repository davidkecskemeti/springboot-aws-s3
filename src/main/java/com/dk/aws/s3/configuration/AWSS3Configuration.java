package com.dk.aws.s3.configuration;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AWSS3Configuration {

    @Value("${cloud.aws.region.static}")
    private String region;

    @Autowired(required = false)
    private AWSCredentialsProvider awsCredentialsProvider;

    @Bean
    public AmazonS3 s3Client() {
        return AmazonS3ClientBuilder
                .standard()
                .withRegion(region)
                .build();
    }
}
