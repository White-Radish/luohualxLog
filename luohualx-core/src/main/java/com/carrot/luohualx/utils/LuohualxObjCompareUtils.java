package com.carrot.luohualx.utils;

import com.carrot.luohualx.annotation.ComPareFieldDesc;
import com.carrot.luohualx.domain.CompareFieldResult;
import com.carrot.luohualx.domain.CompareResult;
import com.carrot.luohualx.enums.ErrorCode;
import com.carrot.luohualx.enums.OperateObjEnum;
import com.carrot.luohualx.enums.OperateTypeEnum;
import com.carrot.luohualx.exception.LuohualxException;
import com.carrot.luohualx.service.ReflectionService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author carrot
 * @date 2022/7/1
 */
@Slf4j
public class LuohualxObjCompareUtils {
    public static CompareResult compareObj(Object oldObj, Object newObj, OperateTypeEnum operateTypeEnum, OperateObjEnum operateObj) throws Exception {
        return compareObj(oldObj, newObj, null, operateTypeEnum, operateObj);
    }

    public static CompareResult compareObj(Object oldObj, Object newObj) throws Exception {
        return compareObj(oldObj, newObj, null, OperateTypeEnum.UPDATE, null);
    }

    private static Object checkData(Object oldObj, Object newObj) {
        try {

            if (oldObj == null) {
                throw new LuohualxException(ErrorCode.FAILED.getCode(), "原对象不能为空");
            }
            if (newObj == null) {
                throw new LuohualxException(ErrorCode.FAILED.getCode(), "新对象不能为空");
            }
            if (!oldObj.getClass().isAssignableFrom(newObj.getClass())) {
                throw new LuohualxException(ErrorCode.FAILED.getCode(), "两个对象不相同，无法比较");
            }

            Field oldObjIdField = oldObj.getClass().getDeclaredField("id");
            oldObjIdField.setAccessible(true);
            Object oldId = oldObjIdField.get(oldObj);
            Field newObjIdField = newObj.getClass().getDeclaredField("id");
            newObjIdField.setAccessible(true);
            Object newId = oldObjIdField.get(newObj);

            if (oldId == null || newId == null) {
                throw new LuohualxException(ErrorCode.FAILED.getCode(), "主键为空无法保存操作日志");
            }
            if (!oldId.equals(newId)) {
                throw new LuohualxException(ErrorCode.FAILED.getCode(), "更新的数据主键不相同，不是同一条数据");
            }
            return oldId;
        } catch (Exception e) {
            log.error("参数检查报错", e);
        }
        return 0L;
    }

    private static CompareResult compareObj(Object oldObj, Object newObj, Long parentId, OperateTypeEnum operateTypeEnum, OperateObjEnum operateObj) throws Exception {
        //检查参数
        try {
            Object id = checkData(oldObj, newObj);
            if (id.equals(0L)) {
                return null;
            }
            if (parentId == null) {
                parentId = 0L;
            }
            List<CompareFieldResult> diffs = new ArrayList<>();
            //取出属性
            Field[] oldObjFields = oldObj.getClass().getDeclaredFields();
            Field[] newOldFields = newObj.getClass().getDeclaredFields();
            Field.setAccessible(oldObjFields, true);
            Field.setAccessible(newOldFields, true);
            //遍历取出差异值
            CompareResult compareResult = new CompareResult();
            if (oldObjFields.length > 0) {
                compareResult.setParentId(parentId);
                compareResult.setId(Long.parseLong(id.toString()));
                if (operateTypeEnum == null) {
                    operateTypeEnum = OperateTypeEnum.UPDATE;
                }
                if (operateObj != null) {
                    compareResult.setOperateObj(operateObj.getCode());
                }
                compareResult.setOperateDesc(operateTypeEnum.getMessage() + (operateObj != null ? operateObj.getMessage() : StringUtils.EMPTY));
                compareResult.setOperateTypeEnum(operateTypeEnum);
                for (int i = 0; i < oldObjFields.length; i++) {
                    CompareResult childrenCompareResult = null;
                    //判断某些不需要解析的字段直接跳过
                    if (notNeedResolve(oldObjFields, newOldFields, i)) {
                        continue;
                    }
                    if (!LuohualxReflectUtils.isPrimitive(oldObjFields[i])) {
                        boolean nullValue = isNullValue(oldObj, oldObjFields[i], newOldFields[i]);
                        if (!nullValue) {
                            childrenCompareResult = compareObj(oldObjFields[i].get(oldObj), newOldFields[i].get(newObj), null, null, null);
                        }
                    }
                    resolveObject(oldObj, newObj, diffs, oldObjFields[i], newOldFields[i], childrenCompareResult, oldObjFields, newOldFields);
                }
            }
            compareResult.setFieldResultList(diffs);
            return compareResult;
        } catch (IllegalAccessException e) {
            log.error("比较对象差异报错", e);
        }
        return null;
    }

    private static boolean isNullValue(Object oldObj, Field oldObjField, Field newOldField) throws IllegalAccessException {
        Object oldChildrenObj = oldObjField.get(oldObj);
        Object newChildrenObj = newOldField.get(oldObj);
        boolean nullValue = (oldChildrenObj == null && newChildrenObj == null);
        try {
            if (oldChildrenObj == null) {
                oldChildrenObj = oldObjField.getType().newInstance();
                setVal(oldChildrenObj);
            }
            if (newChildrenObj == null) {
                newChildrenObj = newOldField.getType().newInstance();
                setVal(newChildrenObj);
            }
        } catch (InstantiationException e) {
            throw new LuohualxException(ErrorCode.FAILED.getCode(), "缺少无参的构造函数");
        }
        return nullValue;
    }


    /**
     * 判断当前字段是否需要解析
     *
     * @param oldObjFields
     * @param newOldFields
     * @param i
     * @return
     */
    private static boolean notNeedResolve(Field[] oldObjFields, Field[] newOldFields, int i) {
        ComPareFieldDesc olDesc = oldObjFields[i].getAnnotation(ComPareFieldDesc.class);
        if (olDesc == null) {
            log.info("当前字段没有描述信息不需要进行比较,旧数据{}，新数据{}", oldObjFields[i].getName(), newOldFields[i].getName());
            return true;
        }
        return false;
    }


    private static void resolveObject(Object oldObj, Object newObj, List<CompareFieldResult> diffs
            , Field oldObjField, Field newOldField, CompareResult childrenCompareResult, Field[] oldObjFields, Field[] newObjFields) throws Exception {
        ComPareFieldDesc olDesc = oldObjField.getAnnotation(ComPareFieldDesc.class);
        Object beforeValue = oldObjField.get(oldObj);
        Object afterValue = newOldField.get(newObj);
        boolean oldValueIsNotNullCondition = (beforeValue != null && !"".equals(beforeValue) && !beforeValue.equals(afterValue));
        boolean oldValueIsNullCondition = ((beforeValue == null || "".equals(beforeValue)) && afterValue != null);
        if (beforeValue == null && afterValue == null) {
            return;
        }
        if (oldValueIsNotNullCondition || oldValueIsNullCondition) {
            CompareFieldResult fieldResult = new CompareFieldResult();
            fieldResult.setField(oldObjField.getName());
            if (childrenCompareResult == null) {
                formatMappingFieldValue(oldObj, newObj, oldObjFields, newObjFields, olDesc, beforeValue, afterValue, fieldResult);
            } else {
                fieldResult.setCompareResults(Collections.singletonList(childrenCompareResult));
            }
            fieldResult.setDesc(olDesc.desc());
            diffs.add(fieldResult);
        }
    }

    private static void formatMappingFieldValue(Object oldObj, Object newObj, Field[] oldObjFields, Field[] newObjFields, ComPareFieldDesc olDesc, Object beforeValue, Object afterValue, CompareFieldResult fieldResult) throws Exception {
        fieldResult.setOldValue(beforeValue);
        fieldResult.setNewValue(afterValue);
        if (StringUtils.isNotBlank(olDesc.mappingField()) && ReflectionService.applicationContext != null) {
            Object oldId = getFieldValue(oldObjFields, olDesc.mappingField(), oldObj);
            Object newId = getFieldValue(newObjFields, olDesc.mappingField(), newObj);
            fieldResult.setOldIdValue(oldId);
            fieldResult.setNewIdValue(newId);
            fieldResult.setOldValue(getTransferValue(olDesc, oldId));
            fieldResult.setNewValue(getTransferValue(olDesc, newId));
            fieldResult.setMappingField(LuohualxStrUtils.underscoreName(olDesc.mappingField()));
        }
    }

    /**
     * 获取转换后的数据
     *
     * @param olDesc
     * @param oldId
     * @return
     * @throws Exception
     */
    private static Object getTransferValue(ComPareFieldDesc olDesc, Object oldId) throws Exception {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put(olDesc.paramName(), oldId);
        Object transObj = LuohualxReflectUtils.invokeService(olDesc.transferBean(), olDesc.methodName(), paramMap);
        if (transObj == null) {
            return null;
        }
        if (LuohualxReflectUtils.isPrimitive(transObj.getClass())) {
            return transObj;
        }
        JSONObject jsonObject = new JSONObject(transObj);
        if (jsonObject.keySet().size() == 0) {
            return null;
        }
        return jsonObject.get(LuohualxStrUtils.underscoreName(olDesc.returnValueColumn()));
    }

    /**
     * 根据字段获取字段名
     *
     * @param fields       所有字段
     * @param mappingField 映射的字段名
     * @param currentObj   当前对象
     * @return
     * @throws IllegalAccessException
     */
    public static Object getFieldValue(Field[] fields, String mappingField, Object currentObj) throws IllegalAccessException {
        for (Field field : fields) {
            if (field.getName().equals(LuohualxStrUtils.underscoreName(mappingField))) {
                return field.get(currentObj);
            }
        }
        return null;
    }

    private static void setVal(Object obj) throws IllegalAccessException {
        Field[] fields = obj.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            Object object = 0;
            if ("id".equals(field.getName())) {
                if (LuohualxReflectUtils.isPrimitive(field)) {
                    object = ConvertUtils.convert(object, field.getType());
                } else {
                    try {
                        object = LuohualxReflectUtils.getInstance(field.getType());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    // 赋值
                    try {
                        Map<String, Object> map = new HashMap<>();
                        map.put("id", 0);
                        BeanUtils.populate(object, map);
                    } catch (Exception e) {
                        log.error(e.getMessage());
                        throw new LuohualxException(e.getMessage());
                    }
                }
                field.set(obj, object);
                break;
            }
        }

    }
}
