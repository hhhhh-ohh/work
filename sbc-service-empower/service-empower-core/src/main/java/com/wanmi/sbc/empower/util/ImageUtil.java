package com.wanmi.sbc.empower.util;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.http.entity.ContentType;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Objects;

/**
 * @Author: xufan
 * @Date: 2020/3/5 9:34
 * @Description: 图片url转MultipartFile工具
 *
 */
public class ImageUtil {
    /**
     * url转变为 MultipartFile对象
     * @param url 图片url
     * @param fileName 文件名称
     * @return
     */
    public static MultipartFile createFileItem(String url, String fileName) {
        FileItem item = null;
        try {
            HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
            conn.setReadTimeout(30000);
            conn.setConnectTimeout(30000);
            //设置应用程序要从网络连接读取数据
            conn.setDoInput(true);
            conn.setRequestMethod("GET");
            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStream inputStream = conn.getInputStream();
                InputStream is = inputStream;

                FileItemFactory factory = new DiskFileItemFactory(16, null);
                String textFieldName = "uploadFile";
                item = factory.createItem(textFieldName, ContentType.APPLICATION_OCTET_STREAM.toString(), false, fileName);
                OutputStream os = item.getOutputStream();

                int bytesRead = 0;
                byte[] buffer = new byte[8192];
                while ((bytesRead = is.read(buffer, 0, 8192)) != -1) {
                    os.write(buffer, 0, bytesRead);
                }
                os.close();
                is.close();
                inputStream.close();
            }
        } catch (IOException e) {
            throw new RuntimeException("文件下载失败", e);
        }

        if(Objects.nonNull(item)) {
            return new CustomMultipartFile(item);
        } else {
            return null;
        }
    }
}
