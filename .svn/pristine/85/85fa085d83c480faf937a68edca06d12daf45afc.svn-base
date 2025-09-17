package com.wanmi.sbc.setting.provider.impl.flashsalesetting;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.setting.api.provider.flashsalesetting.FlashSaleSettingQueryProvider;
import com.wanmi.sbc.setting.api.request.flashsalesetting.FlashSaleSettingListRequest;
import com.wanmi.sbc.setting.api.request.flashsalesetting.FlashSaleSettingQueryRequest;
import com.wanmi.sbc.setting.api.response.flashsalesetting.FlashSaleSettingListResponse;
import com.wanmi.sbc.setting.api.response.flashsalesetting.FlashSaleSettingResponse;
import com.wanmi.sbc.setting.audit.AuditService;
import com.wanmi.sbc.setting.bean.enums.ConfigKey;
import com.wanmi.sbc.setting.bean.enums.ConfigType;
import com.wanmi.sbc.setting.bean.vo.FlashSaleSettingVO;
import com.wanmi.sbc.setting.config.Config;
import com.wanmi.sbc.setting.flashsalesetting.model.root.FlashSaleSetting;
import com.wanmi.sbc.setting.flashsalesetting.service.FlashSaleSettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>秒杀设置查询服务接口实现</p>
 *
 * @author yxz
 * @date 2019-06-11 13:48:53
 */
@RestController
@Validated
public class FlashSaleSettingQueryController implements FlashSaleSettingQueryProvider {

    @Autowired
    private FlashSaleSettingService flashSaleSettingService;

    @Autowired
    private AuditService auditService;

    @Override
    public BaseResponse<FlashSaleSettingListResponse> list(@RequestBody @Valid FlashSaleSettingListRequest flashSaleSettingListReq) {
        FlashSaleSettingQueryRequest queryReq = new FlashSaleSettingQueryRequest();
        KsBeanUtil.copyPropertiesThird(flashSaleSettingListReq, queryReq);
        List<FlashSaleSetting> flashSaleSettingList = flashSaleSettingService.list(queryReq);
        List<FlashSaleSettingVO> newList = flashSaleSettingList.stream().map(entity -> flashSaleSettingService.wrapperVo(entity)).collect(Collectors.toList());
        return BaseResponse.success(FlashSaleSettingListResponse.builder()
                .flashSaleSettingVOList(newList)
                .build());
    }

    @Override
    public BaseResponse<FlashSaleSettingResponse> get() {
        String imgJSON  = flashSaleSettingService.getImgJSON();

        return BaseResponse.success(FlashSaleSettingResponse.builder().imgJSON(imgJSON).build());
    }

    @Override
    public BaseResponse<FlashSaleSettingResponse> getPromotion() {
        Config config = auditService
                .findByConfigTypeAndConfigKeyAndDelFlag(ConfigType.FLASH_GOODS_SALE_POSTER.toValue()
                        , ConfigKey.FLASH_GOODS_SALE.toString()
                        , DeleteFlag.NO);
        String imgJSON  = config.getContext();
        return BaseResponse.success(FlashSaleSettingResponse.builder().imgJSON(imgJSON).build());
    }

}

