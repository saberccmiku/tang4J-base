package com.tianhengyun.common.tang4jbase.multiple;

import com.baomidou.mybatisplus.annotation.TableField;
import com.tianhengyun.common.tang4jbase.utils.ReflectUtil;
import com.tianhengyun.common.tang4jbase.utils.UnderscoreUtil;
import org.apache.commons.lang3.StringUtils;

import javax.validation.ValidationException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * 联表类
 * <M, T>  主表 从表
 */
public class Multiple {

    /**
     * 主表表名
     */
    private String masterTableName;
    /**
     * 主表别名
     */
    private String masterNickName;
    /**
     * 主表映射的实体类字段
     */
    private List<String> masterEntityFields = new ArrayList<>();
    /**
     * 主表字段
     */
    private List<String> masterTableFields = new ArrayList<>();
    /**
     * 从表表名
     */
    private String servantTableName;
    /**
     * 从表别名
     */
    private String servantNickName;
    /**
     * 从表映射的实体类字段
     */
    private List<String> servantEntityFields = new ArrayList<>();
    ;
    /**
     * 从表字段
     */
    private List<String> servantTableFields = new ArrayList<>();
    ;
    /**
     * 主表联表字段
     */
    private String masterColumn;
    /**
     * 从表联表字段
     */
    private String servantColumn;
    /**
     * 自定义sql
     */
    private String customSqlSegment;

    /**
     * Pattern静态申明
     */
    private final Pattern inPattern = Pattern.compile(":");

    private List<Multiple> multiples = new ArrayList<>();

    /**
     * @param masterTableName  主表名称
     * @param servantTableName 从表名称
     * @param masterColumn     主表关联字段
     * @param servantColumn    从表关联字段
     */
    public Multiple(String masterTableName, String servantTableName, String masterColumn, String servantColumn) {
        this.masterTableName = masterTableName;
        this.servantTableName = servantTableName;
        this.masterNickName = masterTableName.toLowerCase().replaceAll("_", "");
        this.servantNickName = servantTableName.toLowerCase().replaceAll("_", "");
        this.masterColumn = masterColumn;
        this.servantColumn = servantColumn;
        multiples.add(this);
    }

    public Multiple() {

    }


    public String getMasterTableName() {
        return masterTableName;
    }

    public void setMasterTableName(String masterTableName) {
        this.masterTableName = masterTableName;
    }

    public String getServantTableName() {
        return servantTableName;
    }

    public void setServantTableName(String servantTableName) {
        this.servantTableName = servantTableName;
    }

    public String getMasterColumn() {
        return masterColumn;
    }

    public void setMasterColumn(String masterColumn) {
        this.masterColumn = masterColumn;
    }

    public String getServantColumn() {
        return servantColumn;
    }

    public void setServantColumn(String servantColumn) {
        this.servantColumn = servantColumn;
    }

    public String getMasterNickName() {
        return masterNickName;
    }

    public void setMasterNickName(String masterNickName) {
        this.masterNickName = masterNickName;
    }

    public String getServantNickName() {
        return servantNickName;
    }

    public void setServantNickName(String servantNickName) {
        this.servantNickName = servantNickName;
    }

    public List<String> getMasterEntityFields() {
        return masterEntityFields;
    }

    public void setMasterEntityFields(List<String> masterEntityFields) {
        this.masterEntityFields = masterEntityFields;
    }

    public List<String> getMasterTableFields() {
        return masterTableFields;
    }

    public void setMasterTableFields(List<String> masterTableFields) {
        this.masterTableFields = masterTableFields;
    }

    public List<String> getServantEntityFields() {
        return servantEntityFields;
    }

    public void setServantEntityFields(List<String> servantEntityFields) {
        this.servantEntityFields = servantEntityFields;
    }

    public List<String> getServantTableFields() {
        return servantTableFields;
    }

    public void setServantTableFields(List<String> servantTableFields) {
        this.servantTableFields = servantTableFields;
    }

    public Multiple addMasterTableField(String... columns) {
        checkColumnPattern(this.masterTableFields, this.masterEntityFields, columns);
        return this;
    }

    public Multiple addMasterTableField(Class<?> clazz) {
        List<String> tempColumn = convertColumn(clazz);
        checkColumnPattern(this.masterTableFields, this.masterEntityFields, tempColumn.toArray(new String[tempColumn.size()]));
        return this;
    }

    public Multiple addServantTableField(String... columns) {
        checkColumnPattern(this.servantEntityFields, this.servantTableFields, columns);
        return this;
    }


    public Multiple addServantTableField(Class<?> clazz) {

        List<String> tempColumn = convertColumn(clazz);
        checkColumnPattern(this.servantEntityFields, this.servantTableFields, tempColumn.toArray(new String[tempColumn.size()]));
        return this;
    }

    private List<String> convertColumn(Class<?> clazz) {
        Field[] declaredFields = ReflectUtil.getAllDeclaredFields(clazz);
        List<String> tempColumn = new ArrayList<>(declaredFields.length);
        for (Field declaredField : declaredFields) {
            if (declaredField.isAnnotationPresent(TableField.class)) {
                if (declaredField.getAnnotation(TableField.class).exist()) {
                    String value = declaredField.getAnnotation(TableField.class).value();
                    if (!StringUtils.isEmpty(value)) {
                        tempColumn.add(value + ":" + declaredField.getName());
                    }
                }
            } else {
                tempColumn.add(UnderscoreUtil.underscoreName(declaredField.getName()) + ":" + declaredField.getName());
            }
        }
        return tempColumn;
    }

    private void checkColumnPattern(List<String> entityFields, List<String> tableFields, String... columns) {
        for (String column : columns) {
            String[] split = inPattern.split(column);
            if (split.length != 2) {
                throw new ValidationException(String.format("the param of [%s] must match the pattern of [tableName:nickName]", column));
            }
            tableFields.add(split[0]);
            entityFields.add(split[1]);
        }
    }

    public Multiple joinMultiple(String masterTableName, String servantTableName, String masterColumn, String servantColumn) {
        Multiple multiple = new Multiple(masterTableName, servantTableName, masterColumn, servantColumn);
        multiple.getMultiples().addAll(this.multiples);
        return multiple;
    }

    public Multiple[] toArray() {
        return this.multiples.toArray(new Multiple[this.multiples.size()]);
    }

    public List<Multiple> getMultiples() {
        return multiples;
    }

    public void setMultiples(List<Multiple> multiples) {
        this.multiples = multiples;
    }

    public String getCustomSqlSegment() {
        return customSqlSegment;
    }

    public void setCustomSqlSegment(String customSqlSegment) {
        this.customSqlSegment = customSqlSegment;
    }
}
