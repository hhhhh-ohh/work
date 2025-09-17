package com.wanmi.sbc.dbreplay.utils;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.JarURLConnection;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * \* Author: zgl
 * \* Date: 2020-2-18
 * \* Time: 14:43
 * \* Description:
 * \
 */
@Slf4j
public class FileUtils {
    private final static String START_STR = "file:";
    //读取文件内容
    public static String readFile(String fileName){
        StringBuilder stringBuilder=new StringBuilder();
        try {
            Resource resource = null;
            if(fileName.contains(START_STR)){
                resource = new FileSystemResource(fileName.replaceAll(START_STR,""));
            }else {
                resource = new ClassPathResource(fileName);
            }
            if(resource == null || !resource.exists()){
                log.error("{} 文件不存在！",fileName);
                return null;
            }
            BufferedReader br = new BufferedReader(new InputStreamReader(resource.getInputStream()));
            String temp = null;
            while ((temp = br.readLine()) != null) {
                stringBuilder.append(temp);
            }
            br.close();
        }catch (Exception e){
            log.error("FileUtils.readFile",e);
            return null;
        }
        return stringBuilder.toString();
    }

    public static String[] getPathFileNames(String path) {
        List<String> list = new ArrayList<>();
        try {
            Resource resource = null;
            if(path.contains(START_STR)){
                resource = new FileSystemResource(path.replaceAll(START_STR,""));
                if (resource == null || !resource.exists()) {
                    log.error("{} 文件不存在！", path);
                    return null;
                }
                return resource.getFile().list();
            }else {
                resource = new ClassPathResource(path);

                if (resource == null || !resource.exists()) {
                    log.error("{} 文件不存在！", path);
                    return null;
                }
               /* BufferedReader br = new BufferedReader(new InputStreamReader(resource.getInputStream()));
                String temp = null;
                while ((temp = br.readLine()) != null) {
                    list.add(temp);
                }
                br.close();*/
                URLConnection connection = resource.getURL().openConnection();
                if(connection instanceof JarURLConnection) {
                    JarFile jarFile = ((JarURLConnection) connection).getJarFile();

                    Enumeration<JarEntry> jarEntrys = jarFile.entries();
                    while (jarEntrys.hasMoreElements()) {
                        JarEntry entry = jarEntrys.nextElement();
                        String name = entry.getName();
                        if(name.contains(path) && !entry.isDirectory()) {
                            list.add(name.replace(path+"/","").replace(path,""));
                        }
                    }
                }else{
                    return resource.getFile().list();
                }
                return list.toArray(new String[list.size()]);
            }
        }catch (Exception e){
            log.error("FileUtils.readFile",e);
            return null;
        }

    }

    public static <T> T getBean(String fileName, Class<T> clazz){
        String str = readFile(fileName);
        if(StringUtils.isNotBlank(str)){
            return  JSON.parseObject(str,clazz);
        }else {
            return null;
        }
    }

    public static void main(String[] args) throws IOException {
        String[] str = getPathFileNames("mongo/data/enum");
        for(String s :str) {
            System.out.println(s);
        }
      /*  String str = "{ \"$set\" : { \"returnOrderNum\" : 0 , \"tradeState.finalTime\" : { \"$date\" : \"2020-02-19T08:04:06.143Z\"}}}";
        str = JsonUtil.dealUpdate$(str);*/
    }
}
