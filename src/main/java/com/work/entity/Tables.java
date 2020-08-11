package com.work.entity;

import lombok.Data;

import java.util.List;

@Data
public class Tables {
    private Long tableId;
    private String tableName;

    private List<TableField> list;

    private Integer isSyn;

    private Integer type;
}
