package com.work.entity;

import lombok.Data;

@Data
public class TableField {
    private Long id;

    private String fieldName;

    private Boolean autoIncrement;

    private String dataType;


}
