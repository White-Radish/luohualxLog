package com.carrot.luohualx.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

/**
 * @author carrot
 */
@AllArgsConstructor
@Getter
public enum OperateObjEnum {
    /**
     * 默认值
     */
    OTHERS("default","默认值"),
    /**
     * 新增操作
     */
    PROJECT("project","项目信息管理"),
    /**
     * 更新操作
     */
    CONTRACT("contract","合同管理");


    private final String code;

    private final String message;


    private static final Map<String,OperateObjEnum> OPERATE_OBJ_MAP = new HashMap<>();;

    static {
        for (OperateObjEnum e : OperateObjEnum.values()) {
            OPERATE_OBJ_MAP.put(e.code,e);
        }
    }

    public static OperateObjEnum getOperateObj(String code) {
        return  OPERATE_OBJ_MAP.getOrDefault(code,OTHERS);
    }

}
