package com.carrot.luohualx.domain;

import lombok.Data;

import java.io.Serializable;

/**
 * @author carrot
 * @date 2022/6/17
 */
@Data
public class CompareObject implements Serializable {
    /**
     * 旧值
     */
    private Object oldObj;
    /**
     * 新值
     */
    private Object newObj;
}
