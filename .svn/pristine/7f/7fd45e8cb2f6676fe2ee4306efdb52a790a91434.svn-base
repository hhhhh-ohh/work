package com.wanmi.sbc.common.util;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import lombok.extern.slf4j.Slf4j;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @description 二维码工具类
 * @author  daiyitian
 * @date 2022/1/11 15:58
 **/
@Slf4j
public class QrCodeUtils {

    private static BitMatrix deleteMargin(BitMatrix matrix) {
        int[] rec = matrix.getEnclosingRectangle();
        int resWidth = rec[2] + 1;
        int resHeight = rec[3] + 1;

        BitMatrix resMatrix = new BitMatrix(resWidth, resHeight);
        resMatrix.clear();
        for (int i = 0; i < resWidth; i++) {
            for (int j = 0; j < resHeight; j++) {
                if (matrix.get(i + rec[0], j + rec[1])) {
                    resMatrix.set(i, j);
                }
            }
        }
        return resMatrix;
    }

    public static BufferedImage createQrCodeImage(String url, int width, int height) {
        return createQrCodeImage(url, width, height, 1);
    }

    public static BufferedImage createQrCodeImage(String url, int width, int height, int margin) {
        if (url == null) {
            return null;
        }
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        int white = 255 << 16 | 255 << 8 | 255;
        int black = 0;
        try {
            Map<EncodeHintType, Object> hints = new HashMap<>(3);
            hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
            hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
            hints.put(EncodeHintType.MARGIN, margin);

            BitMatrix bitMatrix =
                    qrCodeWriter.encode(url, BarcodeFormat.QR_CODE, width, height, hints);
            if (margin == 0) {
                bitMatrix = deleteMargin(bitMatrix);
                width = bitMatrix.getWidth();
                height = bitMatrix.getHeight();
            }

            // create an empty image
            BufferedImage image =
                    new BufferedImage(
                            width, height, BufferedImage.TYPE_INT_RGB);

            // set pixel one by one
            for (int i = 0; i < width; i++) {
                for (int j = 0; j < height; j++) {
                    image.setRGB(i, j, bitMatrix.get(i, j) ? black : white);
                }
            }
            return image;
        } catch (WriterException e) {
            log.error("生成二维码图片失败", url, e);
            return null;
        }
    }

    /**
     * BufferedImage转byte[]
     *
     * @param bImage BufferedImage对象
     * @return byte[]
     * @auth zhy
     */
    public static byte[] imageToBytes(BufferedImage bImage) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            ImageIO.write(bImage, "png", out);
        } catch (IOException e) {
            log.error(e.getMessage());
        }
        return out.toByteArray();
    }
}
