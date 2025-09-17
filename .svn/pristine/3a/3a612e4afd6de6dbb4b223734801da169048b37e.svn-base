package com.wanmi.sbc.job;

import com.wanmi.sbc.common.enums.SellPlatformType;
import com.wanmi.sbc.setting.api.provider.thirdexpresscompany.ThirdExpressCompanyProvider;
import com.wanmi.sbc.setting.api.request.thirdexpresscompany.ThirdExpressCompanyAddRequest;
import com.wanmi.sbc.setting.api.request.thirdexpresscompany.ThirdExpressCompanyBatchAddRequest;
import com.wanmi.sbc.vas.api.provider.sellplatform.SellPlatformOrderProvider;
import com.wanmi.sbc.vas.api.request.sellplatform.SellPlatformBaseRequest;
import com.wanmi.sbc.vas.api.response.sellplatform.order.SellPlatformExpressListResponse;
import com.wanmi.sbc.vas.bean.vo.sellplatform.SellPlatformExpressVO;
import com.xxl.job.core.handler.annotation.XxlJob;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 全量初始化微信视频号支持的快递公司
 */
@Component
public class WechatVideoExpressCompanyInitHandler {
    
    @Autowired private SellPlatformOrderProvider sellPlatformOrderProvider;

    @Autowired private ThirdExpressCompanyProvider thirdExpressCompanyProvider;

    @XxlJob(value = "WechatVideoExpressCompanyInitHandler")
    public void execute() throws Exception {
        SellPlatformExpressListResponse expressListResponse =
                sellPlatformOrderProvider.getExpressList(new SellPlatformBaseRequest()).getContext();
        if (Objects.nonNull(expressListResponse) && CollectionUtils.isNotEmpty(expressListResponse.getExpressList())) {
            List<ThirdExpressCompanyAddRequest> addRequestList = new ArrayList<>();
            List<SellPlatformExpressVO> expressList = expressListResponse.getExpressList();
            expressList.forEach(item -> {
                ThirdExpressCompanyAddRequest addRequest = new ThirdExpressCompanyAddRequest();
                addRequest.setExpressCode(item.getExpressCode());
                addRequest.setExpressName(item.getExpressName());
                addRequest.setSellPlatformType(SellPlatformType.WECHAT_VIDEO);
                addRequestList.add(addRequest);
            });
            // 执行批量保存
            thirdExpressCompanyProvider.batchAdd(ThirdExpressCompanyBatchAddRequest.builder()
                    .thirdExpressCompanyList(addRequestList).build());
        }
    }
}
