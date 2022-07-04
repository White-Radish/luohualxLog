# luohualxLog(皮皮虾日志)
用于更新数据时记录具体每个字段的操作日志
用法：
1.针对springboot 项目 启动类实现LuohualxInitContainer接口
2.
```
LogDemoTest logDemo1 = new LogDemoTest("小明", 5L, 23, "台州");
LogDemoTest logDemo2 = new LogDemoTest("小胡", 5L, 24, "杭州");
LogDemoTest logDemo3 = new LogDemoTest("小强", 4L, 25, "义乌", logDemo1);
LogDemoTest logDemo4 = new LogDemoTest("小红", 4L, 28, "上海", logDemo2);
LuohualxObjCompareUtils.compareObj(logDemo3, logDemo4);
```
支持对象的嵌套比对
```json
{
    "operateTypeEnum": "UPDATE",
    "operateDesc": "更新",
    "operateObj": null,
    "parentId": 0,
    "id": 4,
    "fieldResultList": [
        {
            "field": "name",
            "oldValue": "小强",
            "newValue": "小红",
            "desc": "姓名",
            "compareResults": null,
            "oldIdValue": null,
            "newIdValue": null,
            "mappingField": null
        },
        {
            "field": "age",
            "oldValue": 25,
            "newValue": 28,
            "desc": "年龄",
            "compareResults": null,
            "oldIdValue": null,
            "newIdValue": null,
            "mappingField": null
        },
        {
            "field": "address",
            "oldValue": "4",
            "newValue": "4",
            "desc": "地址",
            "compareResults": null,
            "oldIdValue": 4,
            "newIdValue": 4,
            "mappingField": "id"
        },
        {
            "field": "logDemo",
            "oldValue": null,
            "newValue": null,
            "desc": "子节点日志",
            "compareResults": [
                {
                    "operateTypeEnum": "UPDATE",
                    "operateDesc": "更新",
                    "operateObj": null,
                    "parentId": 0,
                    "id": 5,
                    "fieldResultList": [
                        {
                            "field": "name",
                            "oldValue": "小明",
                            "newValue": "小胡",
                            "desc": "姓名",
                            "compareResults": null,
                            "oldIdValue": null,
                            "newIdValue": null,
                            "mappingField": null
                        },
                        {
                            "field": "age",
                            "oldValue": 23,
                            "newValue": 24,
                            "desc": "年龄",
                            "compareResults": null,
                            "oldIdValue": null,
                            "newIdValue": null,
                            "mappingField": null
                        },
                        {
                            "field": "address",
                            "oldValue": "5",
                            "newValue": "5",
                            "desc": "地址",
                            "compareResults": null,
                            "oldIdValue": 5,
                            "newIdValue": 5,
                            "mappingField": "id"
                        }
                    ]
                }
            ],
            "oldIdValue": null,
            "newIdValue": null,
            "mappingField": null
        }
    ]
}
```
