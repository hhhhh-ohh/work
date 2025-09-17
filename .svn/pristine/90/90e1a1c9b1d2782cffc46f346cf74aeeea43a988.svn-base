package com.wanmi.ares.scheduled.community;

import com.alibaba.fastjson2.JSONObject;
import com.wanmi.ares.community.service.CommunityService;
import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * @author edz
 * @className CommunityJobHandler
 * @description 社区团购数据统计
 * @date 2023/8/11 16:43
 **/
@Slf4j
@Component
public class CommunityJobHandler {
    @Autowired
    CommunityService communityService;

    @XxlJob(value = "communityJobHandler")
    public void execute(){
        String s = XxlJobHelper.getJobParam();
        String startDate = null;
        String tabName = null;
        if (StringUtils.isNotEmpty(s)){
            JSONObject jsonObject = JSONObject.parseObject(s);
            if (Objects.nonNull(jsonObject.get("startDate"))){
                startDate = jsonObject.get("startDate").toString();
            }
            if (Objects.nonNull(jsonObject.get("tabName"))){
                tabName = jsonObject.get("tabName").toString();
            }
        }
        log.info("CommunityJobHandler社区团购统计任务开始执行,参数：{}", s);
        if (StringUtils.isNotEmpty(tabName)){
            switch (tabName){
                case "leader":
                    communityService.insertLeader(startDate);
                    break;
                case "goods":
                    communityService.insertGoods(startDate);
                    break;
                case "customer":
                    communityService.insertCustomer(startDate);
                    break;
                default:
                    log.info("CommunityJobHandler.switch:{}", tabName);
            }
        } else {
            communityService.insertLeader(startDate);
            communityService.insertGoods(startDate);
            communityService.insertCustomer(startDate);
        }
        log.info("CommunityJobHandler社区团购统计任务执行结束");
        XxlJobHelper.log("执行参数:{}", s);
    }

}
