package com.zd.utils.enumpack;

import java.util.Arrays;

/**
 * @author Zidong
 * @date 2019/5/21 3:58 PM
 */
public enum SexEnum implements EnumMessage {
    /**
     * 男性
     */
    MAN("M", "男"),

    /**
     * 女性
     */
    WOMAN("W", "女");

    private String code;

    private String desc;

    SexEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @Override
    public Object getValue() {
        return code;
    }

    public static SexEnum findByType(final String type) {
        return Arrays.stream(values()).filter(value -> value.getCode().equals(type)).findFirst().orElse(null);
    }
    public static SexEnum findByTypeInfo(final String typeInfo) {
        return Arrays.stream(values()).filter(value -> value.getDesc().equals(typeInfo)).findFirst().orElse(null);
    }
}
