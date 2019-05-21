package com.zd.utils.enumpack;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Zidong
 * @date 2019/5/21 3:59 PM
 */
class PackageUtil {

    /**
     * 返回包下所有的类
     *
     * @param packagePathList 包全限定名集合
     * @return List<String> 包下所有的类
     */
    static List<String> getPackageClasses(List<String> packagePathList) {
        List<String> result = new ArrayList<>();
        for (String packagePath : packagePathList) {
            // { com.zd.utils.enumpack.EnumUtil }
            List<String> classNames = getClassName(packagePath);
            // com.zd.utils.enumpack
            String path = packagePath + ".";
            for (String className : classNames) {
                // com.zd.utils.enumpack.EnumUtil
                // result.add(path + className.substring(className.lastIndexOf(".") + 1));
                result.add(className);
            }
        }
        return result;
    }

    /**
     * 获取该包名全路径下的所有 class的全限定名集合 {com.zd.utils.enumpack.SexEnum}
     *
     * @param packageName 包名全路径
     * @return
     */
    private static List<String> getClassName(String packageName) {
        // 获取包名的系统路径 /Users/michen/myCode/utils/target/classes/com/zd/utils/enumpack
        String filePath = ClassLoader.getSystemResource("").getPath() + packageName.replace(".", "/");
        // { "com.zd.utils.enumpack.SexEnum" }
        return getClassName(filePath, null);
    }

    /**
     * 获取filePath文件夹下的所有 class的全限定名
     *
     * @param filePath
     * @param className
     * @return
     */
    private static List<String> getClassName(String filePath, List<String> className) {
        List<String> myClassName = new ArrayList<>();
        File file = new File(filePath);
        File[] childFiles = file.listFiles();
        if (childFiles != null) {
            for (File childFile : childFiles) {
                if (childFile.isDirectory()) {
                    // 递归获取该文件夹下的子文件夹里的所有文件
                    myClassName.addAll(getClassName(childFile.getPath(), myClassName));
                } else {
                    // /Users/michen/myCode/utils/target/classes/com/zd/utils/enumpack/EnumUtil.class
                    String childFilePath = childFile.getPath();
                    // com/zd/utils/enumpack/EnumUtil
                    childFilePath = childFilePath.substring(childFilePath.indexOf("/classes") + 9, childFilePath.lastIndexOf("."));
                    // com.zd.utils.enumpack.EnumUtil
                    childFilePath = childFilePath.replace("/", ".");
                    myClassName.add(childFilePath);
                }
            }
        }
        return myClassName;
    }
}
