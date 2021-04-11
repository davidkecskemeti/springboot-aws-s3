package com.dk.aws.s3.model;

import lombok.Data;

import java.util.Date;

@Data
public class LogObject {

    private Date created;
    private String createdBy;
    private String objectId;
    private Object values;

}
