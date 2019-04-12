package com.zd.utils.tools.util;

import java.util.Random;
import java.util.UUID;

/**
 * @author Zidong
 */
public class UUIDUtils {
    private static String[] chars = new String[] { "a", "b", "c", "d", "e", "f",
            "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s",
            "t", "u", "v", "w", "x", "y", "z", "0", "1", "2", "3", "4", "5",
            "6", "7", "8", "9", "A", "B", "C", "D", "E", "F", "G", "H", "I",
            "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
            "W", "X", "Y", "Z" };

    public static String getShortUUID() {
        StringBuilder uid = new StringBuilder();
        String uuid = UUID.randomUUID().toString().replace("-", "");
        for (int i = 0; i < 8; i++) {
            String str = uuid.substring(i * 4, i * 4 + 4);
            int x = Integer.parseInt(str, 16);
            uid.append(chars[x % 0x3E]);
        }
        return uid.toString();
    }

    /**
     * 生成随机数六位码（数字）
     * @return 六位码
     */
    public static String getSixCode() {
        // 生成随机数
        Random rand = new Random();
        StringBuilder cardNnumer = new StringBuilder();
        for (int a = 0; a < 11; a++) {
            // 生成6位数字
            cardNnumer.append(rand.nextInt(10));
        }
        return cardNnumer.toString();
    }

    /**
     * 生成全大写，不包含 "-"的 32位UUID
     * @return
     */
    public static String getId(){
        return UUID.randomUUID().toString().replace("-", "").toUpperCase();
    }


    /**
     * 生成全大写，不包含 "-"的 64位UUID
     * @return
     */
    public static String getUUID64(){
        return getId()+getId();
    }

    /**
     * 生成包含 "-"的UUID
     * @return
     */
    public static String getCode(){
        return getId();
    }

    /**
     * 图片名生成
     */
    public static String genImageName() {
        // 取当前时间的长整形值包含毫秒
        long millis = System.currentTimeMillis();
        // long millis = System.nanoTime();
        // 加上三位随机数
        Random random = new Random();
        int end3 = random.nextInt(999);
        //如果不足三位前面补0
        return millis + String.format("%03d", end3);
    }

    /**
     * 商品id生成
     */
    public static long genItemId() {
        // 取当前时间的长整形值包含毫秒
        long millis = System.currentTimeMillis();
        // long millis = System.nanoTime();
        // 加上两位随机数
        Random random = new Random();
        int end2 = random.nextInt(99);
        // 如果不足两位前面补0
        String str = millis + String.format("%02d", end2);
        return new Long(str);
    }
}
