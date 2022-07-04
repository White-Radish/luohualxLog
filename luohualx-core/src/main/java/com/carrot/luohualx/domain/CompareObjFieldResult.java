package com.carrot.luohualx.domain;

import lombok.Data;

/**
 * @author carrot
 * @date 2022/6/28
 */
@Data
public class CompareObjFieldResult{
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
}
