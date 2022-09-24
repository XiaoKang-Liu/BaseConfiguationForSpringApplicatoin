package com.base.enums;

/**
 * @author lxk
 * @date 2022/9/22 16:48
 */
public enum Type implements BaseEnum<String> {

    /**
     * 成功
     */
    SUCCESS("success", "成功"),

    /**
     * 失败
     */
    FAIL("fail", "失败")
    ;

    private final String value;

    private final String description;

    Type(String value, String description) {
        this.value = value;
        this.description = description;
    }

    @Override
    public String getValue() {
        return this.value;
    }

    @Override
    public String getDescription() {
        return this.description;
    }

    /**
     * 通过 value 值获取枚举
     * @see com.base.handler.BaseEnumTypeHandler#get(Class, int)
     * @see com.base.handler.BaseEnumTypeHandler#get(Class, String)
     */
    public static Type get(String value) {
        for (Type type : Type.values()) {
            if (value.equals(type.value)) {
                return type;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return this.value;
    }
}
