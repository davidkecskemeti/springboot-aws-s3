package com.dk.aws.s3.controller;

import com.dk.aws.s3.model.LogObject;
import com.dk.aws.s3.service.LogStorageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/log")
@Slf4j
public class LogStorageController {

    @Autowired
    private LogStorageService logStorageService;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestBody Object obj) {

        LogObject logObject =new LogObject();
        logObject.setCreated(new Date());
        logObject.setCreatedBy("system");
        logObject.setObjectId("log_"+System.currentTimeMillis());
        logObject.setValues(obj);

        log.info("Log object created:"+ logObject);

        return new ResponseEntity<>(logStorageService.uploadObject(logObject), HttpStatus.OK);
    }

}