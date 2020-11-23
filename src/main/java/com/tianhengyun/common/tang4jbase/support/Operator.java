package com.tianhengyun.common.tang4jbase.support;

import com.tianhengyun.common.tang4jbase.exception.ValidateException;

/**
 * 绑定{@link com.baomidou.mybatisplus.core.conditions.query.QueryWrapper}的sql操作符
 */
public enum Operator {

    eq("eq", "等于=", 2),
    ne("ne", "不等于<>", 2),
    gt("gt", "大于>", 2),
    ge("ge", "大于等于>=", 2),
    lt("lt", "小于<", 2),
    le("le", "小于等于<=", 2),
    between("between", "between 值1 and 值2", 3),
    notBetween("notBetween", "not between 值1 and 值2", 3),
    like("like", "like '%值%'", 2),
    notLike("notLike", "not like '%值%'", 2),
    likeLeft("likeLeft", "like '%值'", 2),
    likeRight("likeRight", "like '值%'", 2),
    isNull("isNull", "字段 is null", 0),
    isNotNull("isNotNull", "字段 is not null", 1),
    in("in", "字段 in (v0,v1,v2...)", 2),
    notIn("notIn", "字段 not in (v0,v1,v2...)", 2),
    inSql("inSql", "字段 in (sql语句)", 2),
    notInSql("notInSql", "字段 not in (sql语句)", 2),
    groupBy("groupBy", "分组", 1),
    orderByDesc("orderByDesc", "倒序", 1),
    orderByAsc("orderByAsc", "正序", 1),
    orderBy("orderBy", "按字段排序", 99),
    having("having", "having语句", 99),
    or("or", "拼接", 0),
    and("and", "嵌套", 0),
    apply("apply", "拼接sql", 99),
    last("last", "无视优化规则直接拼接到sql最后", 1),
    exists("exists", "拼接exists语句", 1),
    notExists("notExists", "拼接notExists语句", 1),
    nested("nested", "正常嵌套不带and 和 or", 1);

    private final String name;
    private final String desc;
    /**
     * 反射时该操作符对应的参数数量 (比实际多1)
     */
    private final Integer parameter;


    public static Operator getOperator(String name) {
        Operator[] var1 = values();
        int var2 = var1.length;

        for (int var3 = 0; var3 < var2; ++var3) {
            Operator operator = var1[var3];
            if (operator.name.equalsIgnoreCase(name)) {
                return operator;
            }
        }

        throw new ValidateException(ValidSymbol.class.getName() + ":No matching character types found > " + name);
    }

    public String getName() {
        return name;
    }

    public String getDesc() {
        return desc;
    }

    public Integer getParameter() {
        return parameter;
    }

    Operator(String name, String desc, Integer parameter) {

        this.name = name;
        this.desc = desc;
        this.parameter = parameter;
    }
}
