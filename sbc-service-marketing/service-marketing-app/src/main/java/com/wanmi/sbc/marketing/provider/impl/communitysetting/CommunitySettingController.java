package com.wanmi.sbc.marketing.provider.impl.communitysetting;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.marketing.api.provider.communitysetting.CommunitySettingProvider;
import com.wanmi.sbc.marketing.api.request.communitysetting.CommunitySettingModifyRequest;
import com.wanmi.sbc.marketing.bean.enums.DeliveryOrderAreaSummaryType;
import com.wanmi.sbc.marketing.bean.enums.DeliveryOrderSummaryType;
import com.wanmi.sbc.marketing.communitysetting.model.root.CommunitySetting;
import com.wanmi.sbc.marketing.communitysetting.service.CommunitySettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

/**
 * <p>社区拼团商家设置表保存服务接口实现</p>
 *
 * @author dyt
 * @date 2023-07-20 11:30:25
 */
@RestController
@Validated
public class CommunitySettingController implements CommunitySettingProvider {
    @Autowired
    private CommunitySettingService communitySettingService;

    @Override
    public BaseResponse modify(@RequestBody @Valid CommunitySettingModifyRequest communitySettingModifyRequest) {
        CommunitySetting communitySetting = KsBeanUtil.convert(communitySettingModifyRequest, CommunitySetting.class);
        if (communitySetting != null) {
            communitySetting.setDeliveryOrderType(DeliveryOrderSummaryType.toValue(communitySettingModifyRequest.getDeliveryOrderTypes()));
            if(communitySettingModifyRequest.getDeliveryOrderTypes() != null
                    && communitySettingModifyRequest.getDeliveryOrderTypes().contains(DeliveryOrderSummaryType.AREA)
                    && communitySetting.getDeliveryAreaType() == null){
                communitySetting.setDeliveryAreaType(DeliveryOrderAreaSummaryType.PROVINCE);
            }
        }
        communitySettingService.save(communitySetting);
        return BaseResponse.SUCCESSFUL();
    }
}

