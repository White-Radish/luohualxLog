package com.carrot.luohualx.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author carrot
 */
@AllArgsConstructor
@Getter
public enum OperateTypeEnum {
    /**
     * 默认值
     */
    OTHERS("default","默认值"),
    /**
     * 新增操作
     */
    SAVE("save","新增"),
    /**
     * 更新操作
     */
    UPDATE("update","更新"),
    /**
     * 查询操作
     */
    FIND("find","查询数据"),
    /**
     * 删除操作
     */
    DELETE("delete","删除"),
    /**
     * 生效操作
     */
    EFFECT("effect","生效"),
    /**
     * 失效操作
     */
    INVALID("invalid","失效");

    private final String code;

    private final String message;
}
