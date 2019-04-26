package com.zd.utils.tools.util.io;

import org.junit.Test;

import java.io.*;

/**
 * @author Zidong
 * @date 2019/4/26 9:53 AM
 */
public class ReadFile {
    public ReadFile() throws IOException {
    }

    @Test
    public void xx() throws IOException {
        testLoopOutAllFileName("/Users/michen/myCode/mgmt-itsc-hd-hz-dp/src/main");
    }

    /**
     * 读取到的每个文件的内容
     */
    private StringBuffer buffer = new StringBuffer();

    /**
     * 将读取到的内容追加到这里
     */
    private File newFile = new File("/Users/michen/Desktop/xx.txt");

    /**
     * 写入对象
     */
    private FileWriter fw = new FileWriter(newFile.getAbsoluteFile());

    /**
     * @param testFileDir 文件名或目录名
     */
    private void testLoopOutAllFileName(String testFileDir) throws IOException {
        // 将 buffer清了
        buffer = new StringBuffer();
        if (testFileDir == null) {
            return;
        }
        // 获取当前文件夹下所有的文件
        File[] testFile = new File(testFileDir).listFiles();
        if (testFile == null) {
            return;
        }
        for (File file : testFile) {
            if (file.getName().endsWith("model") || file.getName().endsWith("dao")) {
                continue;
            }
            if (file.isFile()) {
                if (file.getName().endsWith(".java") && !file.getName().contains("Response") && !file.getName().contains("Request") && !file.getName().contains("Vo") && !file.getName().contains("Do") && !file.getName().contains("Pojo") && !file.getName().contains("Criteria")) {
                    InputStream is = new FileInputStream(file.getAbsolutePath());
                    // 每行读取的内容
                    String line;
                    BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                    // 读取第一行
                    line = reader.readLine();
                    while (line != null) {
                        // 将读到的内容添加到 buffer 中
                        buffer.append(line);
                        // 添加换行符
                        buffer.append("\n");
                        // 读取下一行
                        line = reader.readLine();
                    }
                    reader.close();
                    is.close();
                    BufferedWriter bw = new BufferedWriter(fw);
                    bw.write(buffer.toString());
                    // bw.close();
                    // 将文件追加到文件中
                }
            } else if (file.isDirectory()) {
                // 说明是文件夹，再来一遍
                testLoopOutAllFileName(file.getPath());
            } else {
                System.out.println("文件读入有误！");
            }
        }
    }
}
