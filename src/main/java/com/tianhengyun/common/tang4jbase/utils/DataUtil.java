package com.tianhengyun.common.tang4jbase.utils;

import com.tianhengyun.common.tang4jbase.support.ValidSymbol;
import com.tianhengyun.common.tang4jbase.validate.RegexType;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.CodeSource;
import java.security.ProtectionDomain;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DataUtil {

    static final Pattern NUMBER_PATTERN = Pattern.compile("\\d+(.\\d+)?$" );

    private DataUtil() {
    }

    public static final String byte2hex(byte[] b) {
        StringBuilder hs = new StringBuilder(b.length * 2);
        String stmp = "";
        byte[] var3 = b;
        int var4 = b.length;

        for (int var5 = 0; var5 < var4; ++var5) {
            byte element = var3[var5];
            stmp = Integer.toHexString(element & 255);
            if (stmp.length() == 1) {
                hs.append("0" ).append(stmp);
            } else {
                hs.append(stmp);
            }
        }

        return hs.toString();
    }

    public static final byte[] hex2byte(String hs) {
        byte[] b = hs.getBytes();
        if (b.length % 2 != 0) {
            throw new IllegalArgumentException("长度不是偶数" );
        } else {
            byte[] b2 = new byte[b.length / 2];

            for (int n = 0; n < b.length; n += 2) {
                String item = new String(b, n, 2);
                b2[n / 2] = (byte) Integer.parseInt(item, 16);
            }

            return b2;
        }
    }

    public static final String getFullPathRelateClass(String relatedPath, Class<?> cls) {
        String path = null;
        if (relatedPath == null) {
            throw new NullPointerException();
        } else {
            String clsPath = getPathFromClass(cls);
            File clsFile = new File(clsPath);
            String tempPath = clsFile.getParent() + File.separator + relatedPath;
            File file = new File(tempPath);

            try {
                path = file.getCanonicalPath();
            } catch (IOException var8) {
                var8.printStackTrace();
            }

            return path;
        }
    }

    public static final String getPathFromClass(Class<?> cls) {
        String path = null;
        if (cls == null) {
            throw new NullPointerException();
        } else {
            URL url = getClassLocationURL(cls);
            if (url != null) {
                path = url.getPath();
                if ("jar".equalsIgnoreCase(url.getProtocol())) {
                    try {
                        path = (new URL(path)).getPath();
                    } catch (MalformedURLException var6) {
                        ;
                    }

                    int location = path.indexOf("!/" );
                    if (location != -1) {
                        path = path.substring(0, location);
                    }
                }

                File file = new File(path);

                try {
                    path = file.getCanonicalPath();
                } catch (IOException var5) {
                    var5.printStackTrace();
                }
            }

            return path;
        }
    }

    public static final boolean isEmpty(Object pObj) {
        if (pObj != null && !"".equals(pObj)) {
            if (pObj instanceof String) {
                if (((String) pObj).trim().length() == 0) {
                    return true;
                }
            } else if (pObj instanceof Collection) {
                if (((Collection) pObj).size() == 0) {
                    return true;
                }
            } else if (pObj instanceof Map && ((Map) pObj).size() == 0) {
                return true;
            }

            return false;
        } else {
            return true;
        }
    }

    public static final boolean isNotEmpty(Object pObj) {
        if (pObj != null && !"".equals(pObj)) {
            if (pObj instanceof String) {
                if (((String) pObj).trim().length() == 0) {
                    return false;
                }
            } else if (pObj instanceof Collection) {
                if (((Collection) pObj).size() == 0) {
                    return false;
                }
            } else if (pObj instanceof Map && ((Map) pObj).size() == 0) {
                return false;
            }

            return true;
        } else {
            return false;
        }
    }

    public static final String[] trim(String[] paramArray) {
        if (ArrayUtils.isEmpty(paramArray)) {
            return paramArray;
        } else {
            String[] resultArray = new String[paramArray.length];

            for (int i = 0; i < paramArray.length; ++i) {
                String param = paramArray[i];
                resultArray[i] = StringUtils.trim(param);
            }

            return resultArray;
        }
    }

    private static URL getClassLocationURL(Class<?> cls) {
        if (cls == null) {
            throw new IllegalArgumentException("null input: cls" );
        } else {
            URL result = null;
            String clsAsResource = cls.getName().replace('.', '/').concat(".class" );
            ProtectionDomain pd = cls.getProtectionDomain();
            if (pd != null) {
                CodeSource cs = pd.getCodeSource();
                if (cs != null) {
                    result = cs.getLocation();
                }

                if (result != null && "file".equals(result.getProtocol())) {
                    try {
                        if (!result.toExternalForm().endsWith(".jar" ) && !result.toExternalForm().endsWith(".zip" )) {
                            if ((new File(result.getFile())).isDirectory()) {
                                result = new URL(result, clsAsResource);
                            }
                        } else {
                            result = new URL("jar:".concat(result.toExternalForm()).concat("!/" ).concat(clsAsResource));
                        }
                    } catch (MalformedURLException var6) {
                        ;
                    }
                }
            }

            if (result == null) {
                ClassLoader clsLoader = cls.getClassLoader();
                result = clsLoader != null ? clsLoader.getResource(clsAsResource) : ClassLoader.getSystemResource(clsAsResource);
            }

            return result;
        }
    }

    public static final <K> K ifNull(K k, K defaultValue) {
        return k == null ? defaultValue : k;
    }

    public static boolean isIp(String ip) {
        return !isEmpty(ip) && ip.matches(RegexType.IP.getRegex());
    }

    public static boolean isEmail(String email) {
        return !isEmpty(email) && email.matches(RegexType.EMAIL.getRegex());
    }

    public static boolean isNumber(String number) {
        return !isEmpty(number) && number.matches(RegexType.NUMBER.getRegex());
    }

    public static boolean isDecimal(String decimal, int count) {
        if (isEmpty(decimal)) {
            return false;
        } else {
            String regex = "^(-)?(([1-9]{1}\\d*)|([0]{1}))(\\.(\\d){" + count + "})?$";
            return decimal.matches(regex);
        }
    }

    public static boolean isMobile(String phoneNumber) {
        return !isEmpty(phoneNumber) && phoneNumber.matches(RegexType.MOBILE.getRegex());
    }

    public static boolean isTelephone(String telephone) {
        return !isEmpty(telephone) && telephone.matches(RegexType.TELEPHONE.getRegex());
    }

    public static boolean hasSpecialChar(String text) {
        if (isEmpty(text)) {
            return false;
        } else {
            return text.replaceAll("[a-z]*[A-Z]*\\d*-*_*\\s*", "" ).length() == 0;
        }
    }

    public static boolean isPassword(String value) {
        return !isEmpty(value) && value.matches(RegexType.PASSWORD.getRegex());
    }

    public static boolean isChinese(String text) {
        if (isEmpty(text)) {
            return false;
        } else {
            Pattern p = Pattern.compile(RegexType.CHINESE.getRegex());
            Matcher m = p.matcher(text);
            return m.find();
        }
    }

    public static boolean isChinese2(String strName) {
        char[] ch = strName.toCharArray();
        char[] var2 = ch;
        int var3 = ch.length;

        for (int var4 = 0; var4 < var3; ++var4) {
            char c = var2[var4];
            if (isChinese(c)) {
                return true;
            }
        }

        return false;
    }

    private static boolean isChinese(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        return ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_B || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION;
    }

    public static final boolean isNumber(Object object) {
        return NUMBER_PATTERN.matcher(object.toString()).matches();
    }

    public static final String format(Number obj, String pattern) {
        if (obj == null) {
            return null;
        } else {
            if (pattern == null || "".equals(pattern)) {
                pattern = "#";
            }

            DecimalFormat format = new DecimalFormat(pattern);
            return format.format(obj);
        }
    }


    /**
     * 字符串首字母大写
     *
     * @param str 字符串
     * @return 首字母大写的字符串
     */
    public static String upperCase(String str) {
        char[] ch = str.toCharArray();
        if (ch[0] >= 'a' && ch[0] <= 'z') {
            ch[0] = (char) (ch[0] - 32);
        }
        return new String(ch);
    }

    /**
     * 数据库字符类型转校验字符类型枚举
     *
     * @param symbol 数据库字符类型
     * @return 校验字符类型枚举
     */
    public static ValidSymbol dbFiledTypeConvertToValidSymbol(String symbol) {

        return null;
    }


    /**
     * 获取所有数字类型的数据库类型
     *
     * @return 数字类型集合
     */
    public static List<String> getNumberTypeList() {
        List<String> numberTypeList = new ArrayList<>();
        ValidSymbol[] values = ValidSymbol.values();
        for (ValidSymbol validSymbol : values) {
            if (validSymbol.getNature().equalsIgnoreCase("number" )) {
                numberTypeList.add(validSymbol.getType());
            }
        }

        return numberTypeList;
    }


    /**
     * 合并字符串 将相同部分合并
     *
     * @param var1 第一个字符串
     * @param var2 第二个字符串
     * @return 合并后的字符串
     */
    public static String merge(String var1, String var2) {
        // 将接收的字符串转换为字符数组
        char[] var1Char = var1.toCharArray();
        char[] var2Char = var2.toCharArray();

        //定义一个长度
        int length = var1Char.length * 2;
        //定义一个字符数组并给一个长度
        char[] mixChar = new char[length];
        for (int i = 0; i < var1Char.length; i++) {
            //将字符数组aChar中的元素赋予给新的字符数组
            mixChar[i * 2] = var1Char[i];
            //将字符数组bChar中的元素赋予给新的字符数组中的第二个位置
            mixChar[i * 2 + 1] = var2Char[i];
        }
        return String.valueOf(mixChar);
    }


}
