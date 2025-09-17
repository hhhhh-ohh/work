package com.wanmi.sbc.marketing.pluginconfig.service;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.wanmi.sbc.common.cache.CacheConstants;
import com.wanmi.sbc.common.constant.RedisKeyConstant;
import com.wanmi.sbc.common.redis.util.RedisUtil;
import com.wanmi.sbc.marketing.bean.dto.MarketingPluginConfigDTO;
import com.wanmi.sbc.marketing.pluginconfig.model.entry.MarketingPluginConfigConvert;
import com.wanmi.sbc.marketing.pluginconfig.model.root.MarketingPluginConfig;
import com.wanmi.sbc.marketing.pluginconfig.repository.MarketingPluginConfigRepository;

import io.seata.common.util.StringUtils;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author zhanggaolei
 * @className MarketingPluginConfigService
 * @description TODO
 * @date 2021/6/9 17:28
 */
@Service
public class MarketingPluginConfigService {

    @Autowired MarketingPluginConfigRepository repository;

    @Autowired RedisUtil redisUtil;

    public List<MarketingPluginConfigDTO> getList() {
        List<MarketingPluginConfig> list = this.repository.findAllByOrderBySortAsc();
        if (CollectionUtils.isNotEmpty(list)) {
            return MarketingPluginConfigConvert.toDTOList(list);
        }
        return null;
    }

    /**
     * 通过缓存返回
     *
     * @return
     */
    @Cacheable(value = CacheConstants.GLOBAL_CACHE_NAME, key = "'MARKETING_PLUG_CONFIG'")
    public List<MarketingPluginConfigDTO> getListByCache() {
        String json = redisUtil.getString(RedisKeyConstant.MARKETING_PLUGIN_KEY);
        if (StringUtils.isNotEmpty(json)) {
            return JSONArray.parseArray(json, MarketingPluginConfigDTO.class);
        }

        List<MarketingPluginConfigDTO> list = getList();
        if (CollectionUtils.isNotEmpty(list)) {
            redisUtil.setString(
                    RedisKeyConstant.MARKETING_PLUGIN_KEY, JSONObject.toJSONString(list));
        }
        return list;
    }
}
