package com.carrot.luohualx.domain;


import com.carrot.luohualx.enums.OperateTypeEnum;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author carrot
 */
@Data
public class CompareResult implements Serializable {

    /**
     * 操作 新增、删除、修改等
     */
    private OperateTypeEnum operateTypeEnum;
    /**
     * 操作描述
     */
    private String operateDesc;
    /**
     * 操作对象
     */
    private String operateObj;
    /**
     * 父类对象
     */
    private Long parentId;
    /**
     * 主键id
     */
    private Long id;

    /**
     * 字段比对结果
     */
    private List<CompareFieldResult> fieldResultList;
}
