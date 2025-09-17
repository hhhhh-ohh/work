package com.wanmi.sbc.common.util;

import org.apache.commons.lang3.StringUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.*;
import java.nio.file.Files;

/**
 * ImageUtil
 *
 * @author yx
 * @date 2019/12/10 9:22
 */

public class ImageUtils {

    /**
     * @param srcImg     原图片
     * @param destImg    目标位置
     * @param width      期望宽
     * @param height     期望高
     * @param equalScale 是否等比例缩放
     */
    public static void reSize(File srcImg, File destImg, int width,
                              int height, boolean equalScale) {
        String type = getImageType(srcImg);
        if (type == null) {
            return;
        }
        if (width < 0 || height < 0) {
            return;
        }

        BufferedImage srcImage = null;
        try {
            srcImage = ImageIO.read(srcImg);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        if (srcImage != null) {
            System.out.println("srcImg size=" + srcImage.getWidth() + "X" + srcImage.getHeight());
            // targetW，targetH分别表示目标长和宽
            BufferedImage target = null;
            double sx = (double) width / srcImage.getWidth();
            double sy = (double) height / srcImage.getHeight();
            // 等比缩放
            if (equalScale) {
                if (sx > sy) {
                    sx = sy;
                    width = (int) (sx * srcImage.getWidth());
                } else {
                    sy = sx;
                    height = (int) (sy * srcImage.getHeight());
                }
            }
            System.out.println("destImg size=" + width + "X" + height);
            ColorModel cm = srcImage.getColorModel();
            WritableRaster raster = cm.createCompatibleWritableRaster(width, height);
            boolean alphaPremultiplied = cm.isAlphaPremultiplied();

            target = new BufferedImage(cm, raster, alphaPremultiplied, null);
            Graphics2D g = target.createGraphics();
            // smoother than exlax:
            g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
            g.drawRenderedImage(srcImage, AffineTransform.getScaleInstance(sx, sy));
            g.dispose();
            // 将转换后的图片保存
            OutputStream fos = null;
            try {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ImageIO.write(target, type, baos);
                fos = Files.newOutputStream(destImg.toPath());
                fos.write(baos.toByteArray());
                fos.flush();
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                if(fos != null){
                    try {
                        fos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    /**
     * 获取文件后缀不带.
     *
     * @param file 文件后缀名
     * @return
     */
    public static String getImageType(File file) {
        if (file != null && file.exists() && file.isFile()) {
            String fileName = file.getName();
            int index = fileName.lastIndexOf('.');
            if (index != -1 && index < fileName.length() - 1) {
                return fileName.substring(index + 1);
            }
        }
        return null;
    }

    /**
     * 获取文件后缀不带.
     *
     * @param imgPath
     * @return
     */
    public static String getImageType(String imgPath) {
        int index = imgPath.lastIndexOf('.');
        if (index != -1 && index < imgPath.length() - 1) {
            return imgPath.substring(index + 1);
        }
        return null;
    }

    //图片格式
    private static final String[] imageTypes = new String[]{
            ".jpg", ".bmp", ".jpeg", ".png", ".gif", ".ico",
            ".JPG", ".BMP", ".JPEG", ".PNG", ".GIF", ".ICO"
    };

    /**
     * 校验图片后缀
     * @param imgPath
     * @return
     */
    public static boolean checkImageSuffix(String imgPath) {
        boolean flag =false;
        if(!StringUtils.isBlank(imgPath)){
            for (int i = 0; i < imageTypes.length; i++) {
                String fileType = imageTypes[i];
                if (imgPath.endsWith(fileType)) {
                    flag = true;
                    break;
                }
            }
        }
        return flag;
    }

    //图片格式
    private static final String[] imageVideoTypes = new String[]{
            ".jpg", ".bmp", ".jpeg", ".png", ".gif",".ico", ".mp4",
            ".JPG", ".BMP", ".JPEG", ".PNG", ".GIF", ".ICO", ".MP4"
    };

    //视频格式
    private static final String[] videoTypes = new String[]{
            ".wmv",".asf",".asx",".rm",".rmvb",".mpg",".mpeg",".mpe",
            ".3gp",".mov",".mp4",".m4v",".avi",".dat",".mkv",".flv",".vob",
            ".WMV",".ASF",".ASX",".RM",".RMVB",".MPG",".MPEG",".MPE",
            ".3GP",".MOV",".MP4",".M4V",".AVI",".DAT",".MKV",".FLV",".VOB"
    };

    /**
     * 校验图片后缀
     * @param imgPath
     * @return
     */
    public static boolean checkImageAndVideoSuffix(String imgPath) {
        boolean flag =false;
        if(!StringUtils.isBlank(imgPath)){
            for (int i = 0; i < imageVideoTypes.length; i++) {
                String fileType = imageVideoTypes[i];
                if (imgPath.endsWith(fileType)) {
                    flag = true;
                    break;
                }
            }
        }
        return flag;
    }

    /**
     * 校验视频后缀
     * @param imgPath
     * @return
     */
    public static boolean checkVideoSuffix(String imgPath) {
        boolean flag =false;
        if(!StringUtils.isBlank(imgPath)){
            for (int i = 0; i < videoTypes.length; i++) {
                String fileType = videoTypes[i];
                if (imgPath.endsWith(fileType)) {
                    flag = true;
                    break;
                }
            }
        }
        return flag;
    }
}
