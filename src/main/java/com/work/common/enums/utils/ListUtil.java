package com.work.common.enums.utils;

import com.work.entity.TableField;

import java.util.List;
import java.util.stream.Collectors;

public class ListUtil {

    public static boolean isRepeat(List list){
        return list.stream().distinct().count()==list.size()?true:false;
    }

    public static boolean listFieldRepeat(List<TableField> list){
        List<String> strList=list.stream().map(TableField::getFieldName).collect(Collectors.toList());
        return strList.stream().distinct().count()==strList.size()?true:false;
    }
}
