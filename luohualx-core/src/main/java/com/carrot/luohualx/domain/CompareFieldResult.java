package com.carrot.luohualx.domain;

import lombok.Data;

import java.util.List;

/**
 * @author carrot
 * @date 2022/6/21
 */
@Data
public class CompareFieldResult{

    /**
     * 英文字段
     */
    private String field;
    /**
     * 字段旧值
     */
    private Object oldValue;
    /**
     * 字段新值
     */
    private Object newValue;
    /**
     * 字段中文解析
     */
    private String desc;
    /**
     * 针对对象嵌套对象的情况
     */
    private List<CompareResult> compareResults;

    /**
     * 字段旧值 针对某些数据库存储的是id之类的唯一标志的数据
     */
    private Object oldIdValue;
    /**
     * 字段新值 字段旧值 针对某些数据库存储的是id之类的唯一标志的数据
     */
    private Object newIdValue;

    /**
     * 英文字段
     */
    private String mappingField;

}
