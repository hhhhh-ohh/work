package com.wanmi.sbc.system;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.cache.CacheConstants;
import com.wanmi.sbc.common.redis.util.RedisUtil;
import com.wanmi.sbc.setting.api.provider.AuditQueryProvider;
import com.wanmi.sbc.setting.api.request.ConfigContextModifyByTypeAndKeyRequest;
import com.wanmi.sbc.setting.api.request.ConfigQueryRequest;
import com.wanmi.sbc.setting.api.response.GoodsColumnShowResponse;
import com.wanmi.sbc.setting.bean.enums.ConfigKey;
import com.wanmi.sbc.setting.bean.vo.ConfigVO;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;

import jakarta.annotation.Resource;
import java.util.List;

@Tag(name = "MobileConfigController", description = "移动端配置 API")
@RestController
@Validated
@RequestMapping("/mobile/config")
public class MobileConfigController {

    @Resource
    private AuditQueryProvider auditQueryProvider;

    @Autowired
    private RedisUtil redisUtil;

    private static final String BUSY_IMG_URL = "BUSY_IMG_URL";

    @Operation(summary = "获取配置列表")
    @Cacheable(value = CacheConstants.GLOBAL_CACHE_NAME,key = "'auditList'")
    @RequestMapping(value = "/audit/list", method = RequestMethod.GET)
    public BaseResponse<List<ConfigVO>> listConfigs() {
        return BaseResponse.success(auditQueryProvider.listAuditConfig().getContext().getConfigVOList());
    }

    @Operation(summary = "查询商品配置")
    @Cacheable(value = CacheConstants.GLOBAL_CACHE_NAME,key = "'auditListGoodsConfigs'")
    @RequestMapping(value = "/audit/list-goods-configs",method = RequestMethod.GET)
    public BaseResponse<List<ConfigVO>> queryGoodsSettingConfigs() {
        ConfigQueryRequest request = new ConfigQueryRequest();
        request.setConfigKey(ConfigKey.GOODS_SETTING.toString());
        return BaseResponse.success(auditQueryProvider.getByConfigKey(request).getContext().getConfigVOList());
    }

    @Operation(summary = "移动商城商品列表展示字段")
    @Cacheable(value = CacheConstants.GLOBAL_CACHE_NAME,key = "'auditGoodsColumnShow'")
    @GetMapping(value = "/audit/goods-column-show")
    public BaseResponse<GoodsColumnShowResponse> goodsColumnShowForMobile(){
        ConfigContextModifyByTypeAndKeyRequest request = new ConfigContextModifyByTypeAndKeyRequest();
        request.setConfigKey(ConfigKey.GOODS_COLUMN_SHOW);

        return auditQueryProvider.goodsColumnShowForMobile(request);
    }

    @Operation(summary = "查询系统繁忙图片")
    @Cacheable(value = CacheConstants.GLOBAL_CACHE_NAME,key = "'busyImage'")
    @RequestMapping(value = "/busyImage",method = RequestMethod.GET)
    public BaseResponse getBusyImage() {
        String imageUrl = redisUtil.getString(BUSY_IMG_URL);
        return BaseResponse.success(imageUrl);
    }
}
