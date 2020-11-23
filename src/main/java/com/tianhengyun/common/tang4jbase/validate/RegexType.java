package com.tianhengyun.common.tang4jbase.validate;

import com.tianhengyun.common.tang4jbase.exception.ValidateException;

/**
 * 正则校验
 */
public enum RegexType {


    REQUIRED("required", "", "非空", "必填"),
    //数字相关的正则表达式
    NUMBER("number", "^[0-9]*$", "数字", "不合符数字规范"),
    NUMBER_LENGTH("numberLength", "：^\\\\d{startNumber}$", "定长的数字", "数字长度必须为startNumber"),
    NUMBER_SIZE("numberSize", "^\\\\d{startNumber,endNumber}$", "区间数字", "数字必须在startNumber-endNumber之间规范"),
    DECIMAL("decimal", "^(\\\\-)?\\\\d+(\\\\.\\\\d{startNumber,endNumber})?$", "保留小数位", "带startNumber-endNumber位小数的正数或负数"),
    //字符相关的正则表达式
    CHINESE("chinese", "^[\\\\u4e00-\\\\u9fa5]{0,}$", "汉字", "不符合汉字规范"),
    STR_SIZE("strSize", "^.{startNumber,endNumber}$", "字符长度", "长度为startNumber-endNumber的所有字符"),
    CODE("code", "^\\\\w{startNumber,endNumber}$", "编码", "由数字、26个英文字母或者下划线组成的长度为startNumber-endNumber的字符串"),
    ACCOUNT("account", "^[a-zA-Z][a-zA-Z0-9_]{4,15}$", "帐号", "字母开头，允许5-16字节，允许字母数字下划线"),
    PASSWORD("password", "^[a-zA-Z]\\\\w{5,17}$", "密码", "以字母开头，长度在6~18之间，只能包含字母、数字和下划线"),
    STRONG_PASSWORD("strongPassword", "^(?=.*\\\\d)(?=.*[a-z])(?=.*[A-Z]).{8,10}$", "强密码", "必须包含大小写字母和数字的组合，不能使用特殊字符，长度在8-10之间"),
    //固定格式约定俗成的正则表达式
    EMAIL("email", "^\\\\w+([-+.]\\\\w+)*@\\\\w+([-.]\\\\w+)*\\\\.\\\\w+([-.]\\\\w+)*$", "邮箱地址", "不符合邮箱规范"),
    WEB("web", "[a-zA-Z0-9][-a-zA-Z0-9]{0,62}(/.[a-zA-Z0-9][-a-zA-Z0-9]{0,62})+/.?", "网站域名", "不符合网站域名规范"),
    URL("url", "^http://([\\\\w-]+\\\\.)+[\\\\w-]+(/[\\\\w-./?%&=]*)?$", "网址", "不符合网址规范"),
    MOBILE("mobile", "^(13[0-9]|17[0-9]|14[5|7]|15[0|1|2|3|5|6|7|8|9]|18[0|1|2|3|5|6|7|8|9])\\\\d{8}$", "手机号码", "不符合手机号码规范"),
    TELEPHONE("telephone", "^(\\\\(\\\\d{3,4}-)|\\\\d{3.4}-)?\\\\d{7,8}$", "固定电话号码", "不符合固定电话号码规范"),
    IN_PHONE("phone", "\\\\d{3}-\\\\d{8}|\\\\d{4}-\\\\d{7}", "固定国内电话号码", "不符合固定电话号码规范"),
    ID_CARD("idCard", "^\\\\d{15}|\\\\d{18}$", "身份证号", "不符合身份证号规范"),
    DATE("date", "^\\\\d{4}-\\\\d{1,2}-\\\\d{1,2}", "日期格式", "不符合身份证号规范"),
    QQ("date", "[1-9][0-9]{4,8}", "腾讯QQ号", "不符合腾讯QQ号规范"),
    POST("post", "[1-9]\\\\d{5}(?!\\\\d)", "中国邮政编码", "不符合中国邮政编码规范"),
    IP("post", "((?:(?:25[0-5]|2[0-4]\\\\d|[01]?\\\\d?\\\\d)\\\\.){3}(?:25[0-5]|2[0-4]\\\\d|[01]?\\\\d?\\\\d))", "IP地址", "不符合IP地址规范"),
    CUSTOM("custom", "", "", "");

    /**
     * 类型唯一
     */
    public String type;
    /**
     * 正则表达式
     */
    public String regex;
    /**
     * 简称
     */
    public String abbreviation;
    /**
     * 提示信息
     */
    public String message;


    public static RegexType getRegexType(String type) {
        RegexType[] var1 = values();
        int var2 = var1.length;

        for (int var3 = 0; var3 < var2; ++var3) {
            RegexType regexType = var1[var3];
            if (regexType.type.equalsIgnoreCase(type)) {
                return regexType;
            }
        }
        throw new ValidateException(RegexType.class.getName() + ":未找到符合规则的校验格式" + type);
    }

    /**
     * 用来修正长度限制的规则
     *
     * @param type    规则类型
     * @param numbers 要修正的数字
     * @return 修正完毕之后的规则
     */
    public static RegexType getRegexType(String type, Integer... numbers) {

        for (int i = 0; i < numbers.length; i++) {
            if ((i + 1) < numbers.length) {
                if (numbers[i + 1] <= numbers[i]) {
                    throw new ValidateException("第" + (i + 1) + "个数字必须小于第" + (i + 2) + "个数字，并且同为数字不得小于1");
                }
            }
        }
        RegexType[] var1 = values();
        int var2 = var1.length;

        for (int var3 = 0; var3 < var2; ++var3) {
            RegexType regexType = var1[var3];
            if (regexType.type.equalsIgnoreCase(type)) {
                regexType.regex = regexType.regex.replace("startNumber", String.valueOf(numbers[0]));
                regexType.message = regexType.message.replace("startNumber", String.valueOf(numbers[0]));
                if (numbers.length > 1) {
                    regexType.regex = regexType.regex.replace("endNumber", String.valueOf(numbers[1]));
                    regexType.message = regexType.message.replace("endNumber", String.valueOf(numbers[1]));
                }
                return regexType;
            }
        }
        throw new ValidateException(RegexType.class.getName() + ":未找到符合规则的校验格式" + type);
    }

    RegexType(String type, String regex, String abbreviation, String message) {
        this.type = type;
        this.regex = regex;
        this.abbreviation = abbreviation;
        this.message = message;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRegex() {
        return regex;
    }

    public void setRegex(String regex) {
        this.regex = regex;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
