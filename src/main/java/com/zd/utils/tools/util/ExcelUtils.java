package com.zd.utils.tools.util;

import com.sf.pg.exception.BusinessException;
import com.zd.utils.constant.FileType;
import com.zd.utils.tools.util.io.IOUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

import static com.zd.utils.tools.util.io.FileUtils.getType;

/**
 * @author Zidong
 * @Date 2018/7/10 9:57
 */
public class ExcelUtils {

    public static File fileFormatCheck(MultipartFile file) throws IOException {
        File tempFile = null;
        if (file == null || file.getSize() <= 0) {
            throw new BusinessException("空文件");
        } else {
            InputStream ins = file.getInputStream();
            tempFile = new File(Objects.requireNonNull(file.getOriginalFilename()));
            IOUtil.inputStreamToFile(ins, tempFile);
        }
        FileInputStream fileInputStream = new FileInputStream(Objects.requireNonNull(tempFile));
        FileType type = getType(fileInputStream);
        if (type == null) {
            throw new BusinessException("错误的文件类型");
        }
        String name = type.name();
        if (StringUtils.isBlank(name) || (!name.equals(FileType.DOCX_XLSX.name()) && !name.equals(FileType.PPT_DOC_XLS.name()) && !name.equals(FileType.EXCEL_XLS.name()))) {
            throw new BusinessException("错误的文件类型");
        }
        return tempFile;
    }
}
