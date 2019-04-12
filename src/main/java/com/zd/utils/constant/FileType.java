package com.zd.utils.constant;

/**
 * @author Zidong
 * @date 2019/3/6 下午5:01
 */
public enum FileType {
    /**
     * 各种文件格式
     */
    JPG("ffd8ff"),

    PNG("89504e47"),

    BMP("424d"),

    /**
     * excel 2007
     */
    DOCX_XLSX("504b0304140006"),

    /**
     * excel 2003
     */
    EXCEL_XLS("2ad5cbbac5092a"),

    PPT_DOC_XLS("d0cf11e0a1b11ae1");

    private String value = "";

    FileType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
