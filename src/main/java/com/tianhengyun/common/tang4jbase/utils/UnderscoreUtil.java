package com.tianhengyun.common.tang4jbase.utils;

/**
 * 驼峰与下划线的属性名互相转换
 */
public class UnderscoreUtil {


    /**
     * 转换为下划线
     *
     * @param camelCaseName 驼峰字段
     * @return 下划线字段
     */
    public static String underscoreName(String camelCaseName) {
        StringBuilder result = new StringBuilder();
        if (camelCaseName != null && camelCaseName.length() > 0) {
            result.append(camelCaseName.substring(0, 1).toLowerCase());
            for (int i = 1; i < camelCaseName.length(); i++) {
                char ch = camelCaseName.charAt(i);
                if (Character.isUpperCase(ch)) {
                    result.append("_");
                    result.append(Character.toLowerCase(ch));
                } else {
                    result.append(ch);
                }
            }
//            if (!result.toString().contains("_")) {
//                result.append("_");
//            }
        }else {
            throw new RuntimeException("空字符异常");
        }
        return result.toString();
    }

    /**
     * 转换为驼峰
     *
     * @param underscoreName 下划线字段
     * @return 驼峰字段
     */
    public static String camelCaseName(String underscoreName) {
        StringBuilder result = new StringBuilder();
        if (underscoreName != null && underscoreName.length() > 0) {
            boolean flag = false;
            for (int i = 0; i < underscoreName.length(); i++) {
                char ch = underscoreName.charAt(i);
                if ("_".charAt(0) == ch) {
                    flag = true;
                } else {
                    if (flag) {
                        result.append(Character.toUpperCase(ch));
                        flag = false;
                    } else {
                        result.append(ch);
                    }
                }
            }
        }
        return result.toString();
    }

}
