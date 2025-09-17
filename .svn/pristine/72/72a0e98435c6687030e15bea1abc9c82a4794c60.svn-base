package com.wanmi.sbc.job;

import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.order.api.provider.wxpayuploadshippinginfo.WxPayUploadShippingInfoQueryProvider;
import com.wanmi.sbc.order.api.request.wxpayuploadshippinginfo.WxPayUploadShippingInfoListRequest;
import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @ClassName PayCallBackJobHandle
 * @Description 微信小程序支付后台没有自动发货出错补偿定时任务
 * @Author lvzhenwei
 * @Date 2020/7/6 11:21
 **/
@Component
@Slf4j
public class WxPayUploadShippingInfoJobHandle {

    private static final String FAILED = "2";

    private static final String ORDER_CODE = "4";

    @Autowired
    WxPayUploadShippingInfoQueryProvider wxPayUploadShippingInfoQueryProvider;

    @XxlJob(value = "wxPayUploadShippingInfoJobHandle")
    public void execute() throws Exception {
        String paramStr = XxlJobHelper.getJobParam();
        if (StringUtils.isNotBlank(paramStr)) {
            String[] paramStrArr = paramStr.split("&");
            String type = paramStrArr[0];
            WxPayUploadShippingInfoListRequest wxPayUploadShippingInfoListRequest = new WxPayUploadShippingInfoListRequest();
            wxPayUploadShippingInfoListRequest.setResultStatus(Constants.TWO);
            //指定订单号补偿
            if (type.equals(FAILED)) {
                //查询处理失败的微信支付发货接口
                wxPayUploadShippingInfoQueryProvider.handleWxPayUploadShippingInfo(wxPayUploadShippingInfoListRequest);
            } else if (type.equals(ORDER_CODE)) {
                if (paramStrArr.length > 1 && StringUtils.isNotBlank(paramStrArr[1])) {
                    String orderCodeStr = paramStrArr[1];
                    List<String> ids = new ArrayList<>();
                    Collections.addAll(ids, orderCodeStr.split(","));
                    List<String> businessIdList = new ArrayList<>();
                    ids.stream().forEach(t -> businessIdList.add(t));
                    if(CollectionUtils.isNotEmpty(businessIdList)){
                        wxPayUploadShippingInfoListRequest.setBusinessIdList(businessIdList);
                        wxPayUploadShippingInfoQueryProvider.handleWxPayUploadShippingInfo(wxPayUploadShippingInfoListRequest);
                    }
                }
            }
        }
    }

}
