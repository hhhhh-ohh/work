package com.wanmi.sbc.customer.util.qrcode;


import com.wanmi.sbc.common.enums.ResourceType;
import com.wanmi.sbc.common.util.QrCodeUtils;
import com.wanmi.sbc.setting.api.provider.yunservice.YunServiceProvider;
import com.wanmi.sbc.setting.api.request.yunservice.YunUploadResourceRequest;
import com.wanmi.sbc.setting.api.response.yunservice.YunUploadResourceResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.awt.image.BufferedImage;


/**
 * @program: sbc-service-api
 * @description: 二维码生成工具类
 * @create: 2019-03-05 15:05
 **/
@Component
@Slf4j
public class GenerateQrCodeUtil {

    @Autowired
    private YunServiceProvider yunServiceProvider;

    /**
     * 根据URL生成二维码并上传到阿里云OSS
     * @param url 用于生成二维码的URL
     * @param qrCodeName 二维码图片名称
     * @return 上传到OSS后的图片URL
     */
    public String generateAndUploadQrCode(String url, String qrCodeName) {
        try {
            // 1. 生成二维码图片
            BufferedImage qrCodeImage = QrCodeUtils.createQrCodeImage(url, 300, 300);
            if (qrCodeImage == null) {
                log.error("生成二维码图片失败，url: {}", url);
                return null;
            }

            // 2. 将图片转换为字节数组
            byte[] qrCodeBytes = QrCodeUtils.imageToBytes(qrCodeImage);
            if (qrCodeBytes == null || qrCodeBytes.length == 0) {
                log.error("二维码图片转换为字节数组失败，url: {}", url);
                return null;
            }

            // 3. 上传到阿里云OSS
            YunUploadResourceResponse response = yunServiceProvider.uploadFile(YunUploadResourceRequest.builder()
                    .resourceType(ResourceType.IMAGE)
                    .resourceName(qrCodeName)
                    .content(qrCodeBytes)
                    .build()).getContext();

            if (response != null) {
                return response.getResourceUrl();
            } else {
                log.error("上传二维码图片到OSS失败，url: {}", url);
                return null;
            }
        } catch (Exception e) {
            log.error("生成并上传二维码图片异常，url: {}", url, e);
            return null;
        }
    }

}
