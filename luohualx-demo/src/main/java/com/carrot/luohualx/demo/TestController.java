package com.carrot.luohualx.demo;

import com.alibaba.fastjson.JSON;
import com.carrot.luohualx.utils.LuohualxObjCompareUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author carrot
 * @date 2022/7/1
 */
@RestController
@RequestMapping("/luohualx")
public class TestController {
    @Resource
    private ApplicationContext applicationContext;
    @GetMapping("/test")
    public Object doCompare() throws Exception {
        LogDemo2 logDemo1 = new LogDemo2("小明",5L,23,"台州");
        LogDemo2 logDemo2 = new LogDemo2("小胡",5L,24,"杭州");
        LogDemo logDemo3 = new LogDemo("小强",4L,25,"义乌",logDemo1);
        LogDemo logDemo4 = new LogDemo("小红",4L,28,"上海",logDemo2);
       return  LuohualxObjCompareUtils.compareObj(logDemo3,logDemo4);
    }

    public static void main(String[] args) throws Exception {
        LogDemo2 logDemo1 = new LogDemo2("小明",5L,23,"台州");
        LogDemo2 logDemo2 = new LogDemo2("小胡",5L,24,"杭州");
        LogDemo logDemo3 = new LogDemo("小强",4L,25,"义乌",logDemo1);
        LogDemo logDemo4 = new LogDemo("小红",4L,28,"上海",logDemo2);

        System.out.println(JSON.toJSONString(LuohualxObjCompareUtils.compareObj(logDemo1, logDemo2)));
    }
}
