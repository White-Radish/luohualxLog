package com.carrot.luohualx.service;

import com.carrot.luohualx.domain.CompareResult;
import com.carrot.luohualx.utils.LuohualxObjCompareUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author carrot
 * @date 2022/7/1
 */
@Service
@Slf4j
public class LuohualxService {

    public String test(){
        return "测试成功";
    }

    public String test2(String id){
        return id;
    }

    public String test3(String name,Long id){
        return id+"-"+name;
    }

    public CompareResult compareObj(Object oldObj, Object newObj) {
        try {
            return LuohualxObjCompareUtils.compareObj(oldObj,newObj);
        } catch (Exception e) {
            log.error("对象比较出错");
        }
        return null;
    }

}
