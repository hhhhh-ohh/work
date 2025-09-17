package com.wanmi.sbc.mq.report.service.base;

import com.wanmi.sbc.common.enums.LogOutStatus;
import com.wanmi.sbc.common.redis.CacheKeyConstant;
import com.wanmi.sbc.common.redis.util.RedisUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * @author 黄昭
 * @className ExportUtilService
 * @description 导出工具
 * @date 2022/3/30 14:35
 **/
@Service
public class ExportUtilService {

    @Autowired
    private RedisUtil redisUtil;

    /**
     * 获取客户注销状态
     * @param customerId
     * @return
     */
    public Map<String,LogOutStatus> getLogOutStatus(List<String> customerId){
        Map<String,LogOutStatus> map = new HashMap<>();
        if (CollectionUtils.isEmpty(customerId)){
            return map;
        }
        List<String> keys = customerId.stream().map(id -> CacheKeyConstant.LOG_OUT_STATUS + id).collect(Collectors.toList());
        List<String> results = redisUtil.getMString(keys).stream().filter(StringUtils::isNotBlank).collect(Collectors.toList());

        if (CollectionUtils.isNotEmpty(results)){
            results.forEach(result -> {
                String[] keyText = result.split(":");
                if (keyText.length < 1) {
                    return;
                }
                String key = keyText[0];
                LogOutStatus value = LogOutStatus.fromValue(Integer.parseInt(keyText[1]));
                map.put(key, value);
            });
        }
        return map;
    }

    /***
     * 随机对象
     */
    private final Random rand = new Random();

    /**
     * 获取4位随机数
     * @return
     */
    public String getRandomNum(){
        StringBuffer bff = new StringBuffer();
        for (int i = 0; i < 4; i++) {
            bff.append(rand.nextInt(10));
        }
        return bff.toString();
    }
}
