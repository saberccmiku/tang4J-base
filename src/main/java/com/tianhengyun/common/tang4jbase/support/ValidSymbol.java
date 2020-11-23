package com.tianhengyun.common.tang4jbase.support;

import com.tianhengyun.common.tang4jbase.exception.ValidateException;

/**
 * java类型对应的非空注解
 */
public enum ValidSymbol {

    BASE_BYTE("byte", "@NotNull", "str"),
    BASE_SHORT("short", "@NotEmpty", "str"),
    BASE_CHAR("char", "@NotEmpty", "str"),
    BASE_INT("int", "@NotNull", "number"),
    BASE_LONG("long", "@NotNull", "number"),
    BASE_FLOAT("float", "@NotNull", "number"),
    BASE_DOUBLE("double", "@NotNull", "number"),
    BASE_BOOLEAN("boolean", "@NotNull", "str"),
    BYTE("Byte", "@NotNull", "str"),
    SHORT("Short", "@NotEmpty", "str"),
    CHARACTER("Character", "@NotEmpty", "str"),
    INTEGER("Integer", "@NotNull", "number"),
    LONG("Long", "@NotNull", "number"),
    FLOAT("Float", "@NotNull", "number"),
    DOUBLE("Double", "@NotNull", "number"),
    BOOLEAN("Boolean", "@NotEmpty", "str"),
    STRING("String", "@NotEmpty", "str"),
    DATE_SQL("Date", "@NotNull", "date"),
    TIME("Time", "@NotNull", "date"),
    TIMESTAMP("Timestamp", "@NotNull", "date"),
    BLOB("Blob", "@NotNull", "str"),
    CLOB("Clob", "@NotNull", "str"),
    LOCAL_DATE("LocalDate", "@NotNull", "date"),
    LOCAL_TIME("LocalTime", "@NotNull", "date"),
    YEAR("Year", "@NotNull", "date"),
    YEAR_MONTH("YearMonth", "@NotNull", "date"),
    LOCAL_DATE_TIME("LocalDateTime", "@NotNull", "date"),
    INSTANT("Instant", "@NotNull", "date"),
    BYTE_ARRAY("byte[]", "@NotNull", "str"),
    OBJECT("Object", "@NotNull", "str"),
    DATE("Date", "@NotNull", "date"),
    BIG_INTEGER("BigInteger", "@NotNull", "number"),
    BIG_DECIMAL("BigDecimal", "@NotNull", "number");

    private final String type;
    private final String symbol;
    /**
     * 标注它是数字还是字符串还是二进制
     */
    private final String nature;

    public static ValidSymbol getValidSymbol(String type) {
        ValidSymbol[] var1 = values();
        int var2 = var1.length;

        for (int var3 = 0; var3 < var2; ++var3) {
            ValidSymbol validSymbol = var1[var3];
            if (validSymbol.type.equalsIgnoreCase(type)) {
                return validSymbol;
            }
        }

        throw new ValidateException(ValidSymbol.class.getName() + ":No matching character types found > " + type);
    }

    private ValidSymbol(String type, String symbol, String nature) {
        this.type = type;
        this.symbol = symbol;
        this.nature = nature;
    }

    public String getType() {
        return this.type;
    }

    public String getSymbol() {
        return this.symbol;
    }

    public String getNature() {
        return this.nature;
    }
}
