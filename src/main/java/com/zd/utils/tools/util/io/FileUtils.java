package com.zd.utils.tools.util.io;

import com.zd.utils.constant.FileType;
import com.zd.utils.tools.util.date.DateUtil;
import com.zd.utils.tools.util.random.RandomUtils;

import java.io.*;
import java.math.BigInteger;
import java.security.MessageDigest;

import static com.zd.utils.tools.util.io.IOUtil.inputStream;

/**
 * 文件工具类
 */
public class FileUtils {

    private static final String FOLDER_SEPARATOR = "/";

    private static final char EXTENSION_SEPARATOR = '.';

    /**
     * @param filePath 指定的文件路径
     * @param isNew    true：新建、false：不新建
     * @return 存在返回TRUE，不存在返回FALSE
     * @desc: 判断指定路径是否存在，如果不存在，根据参数决定是否新建
     */
    public static boolean isExist(String filePath, boolean isNew) {
        File file = new File(filePath);
        if (!file.exists() && isNew) {
            //新建文件路径
            return file.mkdirs();
        }
        return false;
    }

    /**
     * 生成文件名 构建结构为 prefix + yyyyMMddHH24mmss + 10位随机数 + suffix + .type
     * 获取文件名，
     *
     * @param type   文件类型
     * @param prefix 前缀
     * @param suffix 后缀
     */
    public static String getFileName(String type, String prefix, String suffix) {
        //当前时间 yyyy-MM-dd HH:mm:ss
        String date = DateUtil.getCurrentDateTime();
        //10位随机数
        String random = RandomUtils.generateNumberString(10);
        //返回文件名
        return prefix + date + random + suffix + "." + type;
    }

    /**
     * 获取文件名，文件名构成:当前时间 + 10位随机数 + .type
     *
     * @param type 文件类型
     */
    public static String getFileName(String type) {
        return getFileName(type, "", "");
    }

    /**
     * 获取指定文件的大小
     */
    @SuppressWarnings("resource")
    public static long getFileSize(File file) throws Exception {
        long size = 0;
        if (file.exists()) {
            FileInputStream fis;
            fis = new FileInputStream(file);
            size = fis.available();
        } else {
            file.createNewFile();
        }
        return size;
    }

    /**
     * 删除所有文件，包括文件夹
     */
    public void deleteAll(String dirpath) {
        File path = new File(dirpath);
        try {
            // 目录不存在退出
            if (!path.exists()) {
                return;
            }
            // 如果是文件删除
            if (path.isFile()) {
                path.delete();
                return;
            }
            // 如果目录中有文件递归删除文件
            File[] files = path.listFiles();
            if (files != null) {
                for (File file : files) {
                    deleteAll(file.getAbsolutePath());
                }
            }
            path.delete();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 复制文件或者文件夹
     *
     * @param inputFile   源文件
     * @param outputFile  目的文件
     * @param isOverWrite 是否覆盖文件
     * @throws IOException
     */
    public static void copy(File inputFile, File outputFile, boolean isOverWrite)
            throws IOException {
        if (!inputFile.exists()) {
            throw new RuntimeException(inputFile.getPath() + "源目录不存在!");
        }
        copyPri(inputFile, outputFile, isOverWrite);
    }

    /**
     * 复制文件或者文件夹
     *
     * @param inputFile   源文件
     * @param outputFile  目的文件
     * @param isOverWrite 是否覆盖文件
     * @throws IOException
     */
    private static void copyPri(File inputFile, File outputFile, boolean isOverWrite) throws IOException {
        //文件
        if (inputFile.isFile()) {
            copySimpleFile(inputFile, outputFile, isOverWrite);
        } else {
            //文件夹
            if (!outputFile.exists()) {
                outputFile.mkdirs();
            }
            // 循环子文件夹
            for (File child : inputFile.listFiles()) {
                if (child != null) {
                    copy(child, new File(outputFile.getPath() + "/" + child.getName()), isOverWrite);
                }
            }
        }
    }

    /**
     * 复制单个文件
     *
     * @param inputFile   源文件
     * @param outputFile  目的文件
     * @param isOverWrite 是否覆盖
     * @throws IOException
     */
    private static void copySimpleFile(File inputFile, File outputFile, boolean isOverWrite) throws IOException {
        if (outputFile.exists()) {
            //可以覆盖
            if (isOverWrite) {
                if (!outputFile.delete()) {
                    throw new RuntimeException(outputFile.getPath() + "无法覆盖！");
                }
            } else {
                // 不允许覆盖
                return;
            }
        }
        InputStream in = new FileInputStream(inputFile);
        OutputStream out = new FileOutputStream(outputFile);
        byte[] buffer = new byte[1024];
        int read = 0;
        while ((read = in.read(buffer)) != -1) {
            out.write(buffer, 0, read);
        }
        in.close();
        out.close();
    }

    /**
     * 获取文件的MD5
     *
     * @param file 文件
     * @return
     */
    public static String getFileMD5(File file) {
        if (!file.exists() || !file.isFile()) {
            return null;
        }
        MessageDigest digest = null;
        FileInputStream in = null;
        byte buffer[] = new byte[1024];
        int len;
        try {
            digest = MessageDigest.getInstance("MD5");
            in = new FileInputStream(file);
            while ((len = in.read(buffer, 0, 1024)) != -1) {
                digest.update(buffer, 0, len);
            }
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        BigInteger bigInt = new BigInteger(1, digest.digest());
        return bigInt.toString(16);
    }

    /**
     * 获取文件的后缀
     *
     * @param file 文件
     * @return
     */
    public static String getFileSuffix(String file) {
        if (file == null) {
            return null;
        }
        int extIndex = file.lastIndexOf(EXTENSION_SEPARATOR);
        if (extIndex == -1) {
            return null;
        }
        int folderIndex = file.lastIndexOf(FOLDER_SEPARATOR);
        if (folderIndex > extIndex) {
            return null;
        }
        return file.substring(extIndex + 1);
    }

    /**
     * 文件重命名
     *
     * @param oldPath 老文件
     * @param newPath 新文件
     */
    public boolean renameDir(String oldPath, String newPath) {
        // 文件或目录
        File oldFile = new File(oldPath);
        // 文件或目录
        File newFile = new File(newPath);
        // 重命名
        return oldFile.renameTo(newFile);
    }

    /**
     * 判断文件类型
     * 504B03041400060008000000210062EE9D685E010000900400001300
     *
     * @return 文件类型
     */
    public static FileType getType(FileInputStream inputStream) throws IOException {
        String fileHead = getFileContent(inputStream);
        if (fileHead == null || fileHead.length() == 0) {
            return null;
        }
        fileHead = fileHead.toLowerCase();
        FileType[] fileTypes = FileType.values();
        for (FileType type : fileTypes) {
            if (fileHead.startsWith(type.getValue())) {
                return type;
            }
        }
        return null;
    }

    /**
     * 读取文件内容
     *
     * @param filePath
     * @return
     * @throws IOException
     */
    private static String getFileContent(String filePath) throws IOException {

        byte[] b = new byte[28];

        InputStream inputStream = null;

        try {
            inputStream = new FileInputStream(filePath);
            inputStream.read(b, 0, 28);
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        } finally {
            inputStream(inputStream);
        }
        return bytesToHexString(b);
    }

    /**
     * 读取文件内容
     *
     * @param inputStream
     * @return
     * @throws IOException
     */
    private static String getFileContent(FileInputStream inputStream) throws IOException {
        byte[] b = new byte[28];
        try {
            inputStream.read(b, 0, 28);
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        } finally {
            inputStream(inputStream);
        }
        return bytesToHexString(b);
    }

    /**
     * 将文件头转换成16进制字符串
     *
     * @param
     * @return 16进制字符串
     */
    private static String bytesToHexString(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder();
        if (src == null || src.length <= 0) {
            return null;
        }
        for (byte aSrc : src) {
            int v = aSrc & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }
}
