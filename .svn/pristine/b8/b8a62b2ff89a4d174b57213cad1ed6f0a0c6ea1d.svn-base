package com.wanmi.sbc.job;

import com.wanmi.sbc.common.enums.SellPlatformType;
import com.wanmi.sbc.empower.api.provider.pay.weixin.WxPayProvider;
import com.wanmi.sbc.empower.api.response.sellplatform.order.PlatformCompanyResponse;
import com.wanmi.sbc.empower.bean.vo.sellplatform.order.PlatformCompanyVO;
import com.wanmi.sbc.setting.api.provider.expresscompanythirdrel.ExpressCompanyThirdRelProvider;
import com.wanmi.sbc.setting.api.provider.thirdexpresscompany.ThirdExpressCompanyProvider;
import com.wanmi.sbc.setting.api.request.expresscompanythirdrel.ExpressCompanyThirdRelMappingRequest;
import com.wanmi.sbc.setting.api.request.thirdexpresscompany.ThirdExpressCompanyAddRequest;
import com.wanmi.sbc.setting.api.request.thirdexpresscompany.ThirdExpressCompanyBatchAddRequest;
import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 全量初始化微信视频号支持的快递公司
 */
@Component
public class WechatPayExpressCompanyHandler {
    
    @Autowired private WxPayProvider wxPayProvider;
    @Autowired private ThirdExpressCompanyProvider thirdExpressCompanyProvider;
    @Autowired private ExpressCompanyThirdRelProvider expressCompanyThirdRelProvider;

    @XxlJob(value = "wechatPayExpressCompanyHandler")
    public void execute() throws Exception {
        String paramStr = XxlJobHelper.getJobParam();
        if(StringUtils.isBlank(paramStr)) {
            PlatformCompanyResponse response =
                    wxPayProvider.wxPayGetDeliveryList().getContext();
            if (Objects.nonNull(response) && CollectionUtils.isNotEmpty(response.getCompany_list())) {
                List<ThirdExpressCompanyAddRequest> addRequestList = new ArrayList<>();
                List<PlatformCompanyVO> expressList = response.getCompany_list();
                expressList.forEach(item -> {
                    ThirdExpressCompanyAddRequest addRequest = new ThirdExpressCompanyAddRequest();
                    addRequest.setExpressCode(item.getDelivery_id());
                    addRequest.setExpressName(item.getDelivery_name());
                    addRequest.setSellPlatformType(SellPlatformType.MINI_PROGRAM_PAY);
                    addRequestList.add(addRequest);
                });
                // 执行批量保存
                thirdExpressCompanyProvider.batchAdd(ThirdExpressCompanyBatchAddRequest.builder()
                        .thirdExpressCompanyList(addRequestList).build());
            }
        }
        ExpressCompanyThirdRelMappingRequest mappingRequest = new ExpressCompanyThirdRelMappingRequest();
        mappingRequest.setSellPlatformType(SellPlatformType.MINI_PROGRAM_PAY);
        expressCompanyThirdRelProvider.mapping(mappingRequest);
    }
}
