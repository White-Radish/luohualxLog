package com.carrot.luohualx.demo;


import com.carrot.luohualx.annotation.ComPareFieldDesc;
import com.carrot.luohualx.service.LuohualxService;
import lombok.Data;

/**
 * @author carrot
 * @date 2022/6/17
 */
@Data
public class LogDemo2 {
    /**
     * 姓名
     */
    @ComPareFieldDesc(desc = "姓名")
    private String name;
    /**
     * 主键id
     */
    @ComPareFieldDesc(desc = "主键")
    private Long id;
    /**
     * 年龄
     */
    @ComPareFieldDesc(desc = "年龄")
    private Integer age;
    /**
     * 地址
     */
    @ComPareFieldDesc(desc = "地址",mappingField = "id",transferBean= LuohualxService.class,methodName = "test2",paramName = "id", returnValueColumn = "user")
    private String address;


    public LogDemo2(String name, Long id, Integer age, String address) {
        this.name = name;
        this.id = id;
        this.age = age;
        this.address = address;
    }

    public LogDemo2() {

    }
}
