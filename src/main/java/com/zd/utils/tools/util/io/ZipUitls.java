package com.zd.utils.tools.util.io;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * 文件压缩、解压工具类。文件压缩格式为zip
 */
public class ZipUitls {
    /**
     * 文件后缀名
     */
    private static final String ZIP_FILE_SUFFIX = ".zip";

    /**
     * 压缩文件
     *
     * @param resourcePath 源文件
     * @param targetPath   目的文件,保存文件路径
     * @date : 2016年5月24日 下午9:56:36
     */
    public static void zipFile(String resourcePath, String targetPath) {
        File resourcesFile = new File(resourcePath);
        File targetFile = new File(targetPath);

        //目的文件不存在，则新建
        if (!targetFile.exists()) {
            targetFile.mkdirs();
        }
        //文件名
        String targetName = resourcesFile.getName() + ZIP_FILE_SUFFIX;

        ZipOutputStream out = null;
        try {
            FileOutputStream outputStream = new FileOutputStream(targetPath + "//" + targetName);
            out = new ZipOutputStream(new BufferedOutputStream(outputStream));

            compressedFile(out, resourcesFile, "");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * @param out
     * @param dir
     */
    private static void compressedFile(ZipOutputStream out, File file, String dir) {
        FileInputStream fis = null;
        try {
            //文件夹
            if (file.isDirectory()) {
                // 得到文件列表信息
                File[] files = file.listFiles();
                // 将文件夹添加到下一级打包目录
                out.putNextEntry(new ZipEntry(dir + "/"));
                dir = dir.length() == 0 ? "" : dir + "/";
                // 循环将文件夹中的文件打包
                if (files != null) {
                    for (File file1 : files) {
                        // 递归处理
                        compressedFile(out, file1, dir + file1.getName());
                    }
                }
            } else {    //如果是文件则打包处理
                fis = new FileInputStream(file);
                out.putNextEntry(new ZipEntry(dir));
                // 进行写操作
                int j = 0;
                byte[] buffer = new byte[1024];
                while ((j = fis.read(buffer)) > 0) {
                    out.write(buffer, 0, j);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 关闭输入流
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
