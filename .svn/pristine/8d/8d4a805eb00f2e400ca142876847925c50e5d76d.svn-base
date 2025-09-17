package com.wanmi.sbc.dbreplay.config.capture.mapping;

import com.alibaba.fastjson2.JSONObject;
import com.wanmi.sbc.dbreplay.bean.capture.mapping.MappingBean;
import com.wanmi.sbc.dbreplay.utils.FileUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.*;

import static com.wanmi.sbc.dbreplay.common.MappingCache.*;

/**
 * \* Author: zgl
 * \* Date: 2020-2-17
 * \* Time: 16:07
 * \* Description:
 * \
 */
@Slf4j
@Component
public class LoadDataMapping implements InitializingBean {
    @Value("${mongo.capture.mapping.enable:false}")
    private Boolean mappingFlag;
    @Value("${mongo.capture.mapping.path:mongo/data/mapping}")
    private  String mappingPath;
    @Value("${mongo.capture.enum.path:mongo/data/enum}")
    private  String enumPath ;

    private final String initFileName = "init.json";

    private void init(){
       String dataFileName = getFullFileName(mappingPath,initFileName);
       String json = FileUtils.readFile(dataFileName);
       if(StringUtils.isNotBlank(json)){
           Map<String,List<String>> map = JSONObject.parseObject(json,Map.class);
           DATA.putAll(map);
           for(Map.Entry<String,List<String>> entry : DATA.entrySet()){
               List<String> beanList = entry.getValue();
               for(String beanName : beanList){
                   BEAN.put(beanName,FileUtils.getBean(getFullFileName(mappingPath ,beanName), MappingBean.class));
               }
           }
       }
       initEnum();
    }

    private void initEnum(){
        String[] fileNames = FileUtils.getPathFileNames(enumPath);
        log.info("enum 文件个数：{}",fileNames.length);
        if(fileNames.length>0){
            for(String fileName : fileNames) {
                Map enumMap = FileUtils.getBean(getFullFileName(enumPath ,fileName),Map.class);
                ENUM.put(fileName,enumMap);
                log.info("enum 文件：{}",fileName);
            }
        }
    }

    private String getFullFileName(String patch,String fileName){
        if(patch.lastIndexOf("/") == patch.length()-1){
            return patch+fileName;
        }else{
            return patch+"/"+fileName;
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if (mappingFlag){
            init();
        }
    }
}