package com.zd.utils.tools.util.io;

import com.sf.pg.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Zidong on 2018/8/8 上午9:47
 */
@Slf4j
public class ImgUtil {

    /**
     * 添加水印的图片地址, 水印图片地址, 输出地址, 水印旋转度数
     * @param iconPath 添加水印的图片地址
     * @param srcImagePath 水印图片地址
     * @param targerPath 输出地址
     * @param degree 水印旋转度数
     * @return
     */
    public static OutputStream markImageByIcon(String iconPath, String srcImagePath, String targerPath, Integer degree) {
        OutputStream os = null;
        try {
            Image srcImage = ImageIO.read(new File(srcImagePath));
            BufferedImage bufferImg = new BufferedImage(
                    srcImage.getWidth(null),
                    srcImage.getHeight(null),
                    BufferedImage.TYPE_INT_RGB);
            Graphics2D g = bufferImg.createGraphics();
            g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                    RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g.drawImage(srcImage.getScaledInstance(srcImage.getWidth(null),
                    srcImage.getHeight(null),
                    BufferedImage.TYPE_INT_RGB),
                    0,
                    0,
                    null);
            if (null != degree) {
                g.rotate(Math.toDegrees(degree),
                        (double) bufferImg.getWidth() / 2,
                        (double) bufferImg.getHeight() / 2);
            }
            ImageIcon imageIcon = new ImageIcon(iconPath);
            Image img = imageIcon.getImage();
            float alpha = 0.5f;
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, alpha));
            g.drawImage(img, 150, 300, null);
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER));
            g.dispose();
            os = new FileOutputStream(targerPath);
            boolean jpg = ImageIO.write(bufferImg, "jpg", os);
            if (!jpg) {
                throw new BusinessException("图像转换异常");
            }
            return os;
        } catch (IOException e) {
            throw new BusinessException("添加水印时出现异常");
        } finally{
            // 关闭资源
            if(null != os){
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 打印文字水印图片
     *
     * @param date
     *            --文字
     * @param targetFile --
     *            目标图片
     * @param fontStyle --
     *            字体样式
     * @param fontSize --
     *            字体大小
     */
    public static void pressText(String date, File targetFile, int fontStyle, int fontSize) throws IOException {
        FileOutputStream out = null;
        try {
            Image src = ImageIO.read(targetFile);
            int wideth = src.getWidth(null);
            int height = src.getHeight(null);
            BufferedImage image = new BufferedImage(wideth, height,
                    BufferedImage.TYPE_INT_RGB);
            Graphics g = image.createGraphics();
            g.drawImage(src, 0, 0, wideth, height, null);
            g.setColor(Color.RED);
            // 字体名、字体样式、字体大小
            g.setFont(new Font("SimHei", fontStyle, wideth/30));
            g.drawString(date, wideth/20, height-height/20);
            g.dispose();
            out = new FileOutputStream(targetFile);
            ImageIO.write(image, "jpg", out);
        } catch (Exception e) {
            log.error("文件加水印失败！"+ e.getMessage());
            throw new BusinessException("图片添加水印失败：" + e.getMessage());
        }finally {
            // 关闭资源
            if (out != null) {
                out.close();
            }
        }
    }

    /**
     * 打水印测试
     */
    @Test
    public  void xx(){
        SimpleDateFormat form = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String format = form.format(new Date());
        System.out.println(format);
        File file = new File("/Users/michen/Documents/demo.png");
        try {
            pressText("2018-08-11 1267387910234123", file,12,80);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

