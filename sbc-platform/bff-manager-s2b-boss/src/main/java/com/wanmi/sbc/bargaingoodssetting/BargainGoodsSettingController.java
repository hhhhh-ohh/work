package com.wanmi.sbc.bargaingoodssetting;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.BoolFlag;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.setting.api.provider.AuditProvider;
import com.wanmi.sbc.setting.api.provider.systemconfig.SystemConfigQueryProvider;
import com.wanmi.sbc.setting.api.request.ConfigListModifyRequest;
import com.wanmi.sbc.setting.api.request.ConfigQueryRequest;
import com.wanmi.sbc.setting.api.response.systemconfig.SystemConfigResponse;
import com.wanmi.sbc.setting.bean.enums.ConfigKey;
import com.wanmi.sbc.setting.bean.enums.ConfigType;
import com.wanmi.sbc.setting.bean.vo.ConfigVO;
import com.wanmi.sbc.system.request.BargainGoodsSettingModifyRequest;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Tag(name =  "砍价商品设置API", description =  "BargainGoodsSettingController")
@RestController
@Validated
@RequestMapping(value = "/bargaingoods/setting")
public class BargainGoodsSettingController {

    @Autowired
    private SystemConfigQueryProvider systemConfigQueryProvider;

    @Autowired
    private AuditProvider auditProvider;

    @Operation(summary = "获取砍价设置")
    @GetMapping("/query")
    public BaseResponse<SystemConfigResponse> getSetting() {
        ConfigQueryRequest configQueryRequest = new ConfigQueryRequest();
        configQueryRequest.setDelFlag(DeleteFlag.NO.toValue());
        configQueryRequest.setConfigKey(ConfigKey.BARGIN_GOODS_SETTING.toValue());
        return systemConfigQueryProvider.findByConfigKeyAndDelFlagNew(configQueryRequest);
    }

    @Operation(summary = "编辑砍价设置")
    @PutMapping("/save")
    public BaseResponse save(@RequestBody @Valid BargainGoodsSettingModifyRequest request) {
        // 1. 数据校验
        try {
            // 1.1 随机语
            JSONArray randomWordsArray = JSONArray.parseArray(request.getBargainGoodsRandomWords());
            // 1.1.1 最少8条，最多15条
            if (randomWordsArray.size() < 8 || randomWordsArray.size() > 15) {
                throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
            }
            // 1.1.2  每条最多15个字符
            for (int i = 0; i < randomWordsArray.size(); i++) {
                JSONObject randomWord = randomWordsArray.getJSONObject(i);
                String word = randomWord.getString("word").trim();
                if (StringUtils.isBlank(word) || word.length() > 15) {
                    throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
                }
            }
            // 1.2 砍价频道海报
            JSONArray goodsSalePosterArray = JSONArray.parseArray(request.getBargainGoodsSalePoster());
            // 1.2.1 最少1张，最多8张
            if (goodsSalePosterArray.size() < 1 || goodsSalePosterArray.size() > 8) {
                throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
            }
            // 1.2.2 校验是否存在图片地址
            for (int i = 0; i < goodsSalePosterArray.size(); i++) {
                JSONObject goodsSalePoster = goodsSalePosterArray.getJSONObject(i);
                if (!goodsSalePoster.containsKey("artworkUrl")) {
                    throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
                }
            }
        } catch (Exception e) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }

        // 2. 组装各配置
        List<ConfigVO> configRequestList = new ArrayList<>();

        // 2.1 砍价审核开关
        ConfigVO auditSetting = new ConfigVO();
        auditSetting.setConfigKey(ConfigKey.BARGIN_GOODS_SETTING.toValue());
        auditSetting.setConfigType(ConfigType.BARGIN_GOODS_AUDIT.toValue());
        auditSetting.setStatus(request.getBargainGoodsAuditFlag().toValue());

        // 2.2 砍价叠加优惠券使用开关
        ConfigVO useCouponSetting = new ConfigVO();
        useCouponSetting.setConfigKey(ConfigKey.BARGIN_GOODS_SETTING.toValue());
        useCouponSetting.setConfigType(ConfigType.BARGIN_USE_COUPON.toValue());
        useCouponSetting.setStatus(request.getBargainUseCouponFlag().toValue());

        // 2.3 帮砍次数限制
        ConfigVO maxNumEveryDay = new ConfigVO();
        maxNumEveryDay.setConfigKey(ConfigKey.BARGIN_GOODS_SETTING.toValue());
        maxNumEveryDay.setConfigType(ConfigType.BARGIN_MAX_NUM_EVERY_DAY.toValue());
        maxNumEveryDay.setContext(String.valueOf(request.getBargainMaxNumEveryDay()));
        maxNumEveryDay.setStatus(BoolFlag.YES.toValue());

        // 2.4 砍价活动时长
        ConfigVO activityTimeSetting = new ConfigVO();
        activityTimeSetting.setConfigKey(ConfigKey.BARGIN_GOODS_SETTING.toValue());
        activityTimeSetting.setConfigType(ConfigType.BARGIN_ACTIVITY_TIME.toValue());
        activityTimeSetting.setContext(String.valueOf(request.getBargainActivityTime()));
        activityTimeSetting.setStatus(BoolFlag.YES.toValue());

        // 2.5 砍价频道海报
        ConfigVO salePosterSetting = new ConfigVO();
        salePosterSetting.setConfigKey(ConfigKey.BARGIN_GOODS_SETTING.toValue());
        salePosterSetting.setConfigType(ConfigType.BARGIN_GOODS_SALE_POSTER.toValue());
        salePosterSetting.setContext(request.getBargainGoodsSalePoster());
        salePosterSetting.setStatus(BoolFlag.YES.toValue());

        // 2.6 砍价随机语
        ConfigVO randomWordsSetting = new ConfigVO();
        randomWordsSetting.setConfigKey(ConfigKey.BARGIN_GOODS_SETTING.toValue());
        randomWordsSetting.setConfigType(ConfigType.BARGIN_GOODS_RANDOM_WORDS.toValue());
        randomWordsSetting.setContext(request.getBargainGoodsRandomWords());
        randomWordsSetting.setStatus(BoolFlag.YES.toValue());

        // 2.7 砍价规则
        ConfigVO goodsRuleSetting = new ConfigVO();
        goodsRuleSetting.setConfigKey(ConfigKey.BARGIN_GOODS_SETTING.toValue());
        goodsRuleSetting.setConfigType(ConfigType.BARGIN_GOODS_RULE.toValue());
        goodsRuleSetting.setContext(request.getBargainGoodsRule());
        goodsRuleSetting.setStatus(BoolFlag.YES.toValue());

        // 3. 组装成配置列表
        configRequestList.add(auditSetting);
        configRequestList.add(useCouponSetting);
        configRequestList.add(maxNumEveryDay);
        configRequestList.add(activityTimeSetting);
        configRequestList.add(salePosterSetting);
        configRequestList.add(randomWordsSetting);
        configRequestList.add(goodsRuleSetting);
        auditProvider.modifyConfigList(new ConfigListModifyRequest(configRequestList));
        return BaseResponse.SUCCESSFUL();
    }

}
