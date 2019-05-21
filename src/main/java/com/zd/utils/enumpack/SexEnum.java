package com.zd.utils.enumpack;

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
}
