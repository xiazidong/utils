package com.zd.utils.tools.util.format;

import org.junit.Test;

public class NumberFormatUtil {

    public static String format(int length, int value){
        String format1 = String.format("%0" + length + "d", value);
        if (format1.length() > length){
            format1 = format1.substring(format1.length() - length);
        }
        return format1;
    }


    @Test
    public void xx() {
        String format = String.format("%04d", 12323123);
        System.out.println(format);
        String format1 = format(4, 12323123);
        System.out.println(format1);
    }


}
