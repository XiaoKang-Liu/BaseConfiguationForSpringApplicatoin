package com.base.enums;

/**
 * @author lxk
 * @date 2022/9/21 16:37
 */
public enum Status implements BaseEnum<Integer> {

    /**
     * 正常
     */
    NORMAL(0, "正常"),

    /**
     * 删除
     */
    DELETED(1, "删除");

    private final Integer value;

    private final String description;

    Status(Integer value, String description) {
        this.value = value;
        this.description = description;
    }

    @Override
    public Integer getValue() {
        return value;
    }

    @Override
    public String getDescription() {
        return description;
    }

    public static Status get(Integer value) {
        for (Status status : Status.values()) {
            if (value.equals(status.value)) {
                return status;
            }
        }
        return null;
    }


    @Override
    public String toString() {
        return String.valueOf(this.value);
    }
}
