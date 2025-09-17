package com.wanmi.sbc.logistics;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.empower.api.provider.channel.linkedmall.order.LinkedMallOrderProvider;
import com.wanmi.sbc.empower.api.provider.channel.logistics.ChannelLogisticsQueryProvider;
import com.wanmi.sbc.empower.api.provider.logisticslog.LogisticsLogQueryProvider;
import com.wanmi.sbc.empower.api.request.channel.linkedmall.LinkedMallLogisticsQueryRequest;
import com.wanmi.sbc.empower.api.request.channel.logistics.ChannelLogisticsQueryRequest;
import com.wanmi.sbc.empower.api.request.logisticslog.LogisticsLogSimpleListByCustomerIdRequest;
import com.wanmi.sbc.empower.api.response.channel.linkedmall.LinkedMallLogisticsQueryResponse;
import com.wanmi.sbc.empower.api.response.channel.logistics.ChannelLogisticsQueryResponse;
import com.wanmi.sbc.empower.api.response.logisticslog.LogisticsLogSimpleListByCustomerIdResponse;
import com.wanmi.sbc.empower.bean.vo.channel.logistics.DataItemVO;
import com.wanmi.sbc.empower.bean.vo.channel.logistics.LogisticsDetailListItemVO;
import com.wanmi.sbc.logistics.response.LogisticsLinkedMallResponse;
import com.wanmi.sbc.order.api.provider.thirdplatformtrade.ThirdPlatformTradeQueryProvider;
import com.wanmi.sbc.util.CommonUtil;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.*;

@Tag(name = "LogisticsLogBaseController", description = "物流信息 API")
@RestController
@Validated
@RequestMapping("/logisticsLog")
public class LogisticsLogBaseController {

    @Autowired
    private LogisticsLogQueryProvider logisticsLogQueryProvider;

    @Autowired
    private CommonUtil commonUtil;

    @Autowired
    private LinkedMallOrderProvider linkedMallOrderProvider;

    @Autowired
    private ChannelLogisticsQueryProvider channelLogisticsQueryProvider;

    @Autowired
    private ThirdPlatformTradeQueryProvider thirdPlatformTradeQueryProvider;
    /**
     * 获取物流信息
     * @return
     */
    @Operation(summary = "获取物流信息")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public BaseResponse<LogisticsLogSimpleListByCustomerIdResponse> list(){
        String customerId = commonUtil.getOperatorId();
        return logisticsLogQueryProvider.listByCustomerId(LogisticsLogSimpleListByCustomerIdRequest.builder()
                .customerId(customerId).build());
    }

    /**
     * 查询linkedmall订单的物流详情
     */
    @Operation(summary = "查询linkedmall订单的物流详情，返回: 物流详情")
    @RequestMapping(value = "/linkedMall/deliveryInfos", method = RequestMethod.POST)
    public BaseResponse<LogisticsLinkedMallResponse> logistics4LinkedMall(@RequestBody LinkedMallLogisticsQueryRequest request) {
        LinkedMallLogisticsQueryResponse.DataItem dataItem = linkedMallOrderProvider.getOrderLogistics(request).getContext().getDataItems().get(0);
        List<LinkedMallLogisticsQueryResponse.LogisticsDetailListItem> logisticsDetail = dataItem.getLogisticsDetailList();
        LogisticsLinkedMallResponse logisticsLinkedMallResponse = new LogisticsLinkedMallResponse();
        logisticsLinkedMallResponse.setLogisticCompanyName(dataItem.getLogisticsCompanyName());
        logisticsLinkedMallResponse.setLogisticNo(dataItem.getMailNo());
        logisticsLinkedMallResponse.setLogisticStandardCode(dataItem.getLogisticsCompanyCode());
        if (logisticsDetail.size()>1) {
            logisticsLinkedMallResponse.setDeliveryTime(logisticsDetail.get(logisticsDetail.size()-2).getOcurrTimeStr());
        }else {
            logisticsLinkedMallResponse.setDeliveryTime("");
        }
        ArrayList<Map<String, String>> logisticsDetailList = new ArrayList<>();
        for (LinkedMallLogisticsQueryResponse.LogisticsDetailListItem logisticsDetailListItem : logisticsDetail) {
            HashMap<String, String> map = new HashMap<>();
            map.put("time",logisticsDetailListItem.getOcurrTimeStr());
            map.put("context",logisticsDetailListItem.getStanderdDesc());
            logisticsDetailList.add(map);
        }
        logisticsLinkedMallResponse.setLogisticsDetailList(logisticsDetailList);
        return BaseResponse.success(logisticsLinkedMallResponse);
    }

    /**
     * 查询linkedmall订单的物流详情
     */
    @Operation(summary = "查询linkedmall订单的物流详情，返回: 物流详情")
    @RequestMapping(value = "/channel/deliveryInfos", method =
            RequestMethod.POST)
    public BaseResponse<LogisticsLinkedMallResponse> logistics4Channel(@RequestBody ChannelLogisticsQueryRequest request) {

        if(Objects.isNull(request.getOrderId())){
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }

        //查询渠道订单   ---   VOP 可以根据VOP的子单号直接查询物流信息
//        ThirdPlatformTradeByThirdPlatformOrderIdResponse trade = thirdPlatformTradeQueryProvider.queryByThirdPlatformOrderId(
//                ThirdPlatformTradeByThirdPlatformOrderIdRequest.builder()
//                        .thirdPlatformType(request.getThirdPlatformType())
//                        .thirdPlatformOrderId(request.getOrderId().toString()).build()
//        ).getContext();
//
//        if(Objects.isNull(trade)){
//            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
//        }
//
//        if(!trade.getThirdPlatformTradeVO().getBuyer().getId().equals(customerId)){
//            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
//        }


        ChannelLogisticsQueryResponse channelLogisticsQueryResponse =
                channelLogisticsQueryProvider.getLogisticsList(request).getContext();
        DataItemVO dataItem = channelLogisticsQueryResponse.getDataItemVO();
        LogisticsLinkedMallResponse logisticsLinkedMallResponse = new LogisticsLinkedMallResponse();
        logisticsLinkedMallResponse.setLogisticCompanyName(dataItem.getLogisticsCompanyName());
        logisticsLinkedMallResponse.setLogisticNo(dataItem.getMailNo());
        logisticsLinkedMallResponse.setLogisticStandardCode(dataItem.getLogisticsCompanyCode());
        List<LogisticsDetailListItemVO> logisticsDetail = dataItem.getLogisticsDetailList();
        if (logisticsDetail.size()>1) {
            //按照时间排序
            logisticsDetail.sort(Comparator.comparing(LogisticsDetailListItemVO::getOcurrTimeStr).reversed());
            //按照时间倒序后  最后条是订单确认日志 倒数第二条是发货日志
            logisticsLinkedMallResponse.setDeliveryTime(logisticsDetail.get(logisticsDetail.size()-2).getOcurrTimeStr());
        }else {
            logisticsLinkedMallResponse.setDeliveryTime("");
        }
        ArrayList<Map<String, String>> logisticsDetailList = new ArrayList<>();
        for (LogisticsDetailListItemVO logisticsDetailListItem : logisticsDetail) {
            HashMap<String, String> map = new HashMap<>();
            map.put("time",logisticsDetailListItem.getOcurrTimeStr());
            map.put("context",logisticsDetailListItem.getStanderdDesc());
            logisticsDetailList.add(map);
        }
        logisticsLinkedMallResponse.setLogisticsDetailList(logisticsDetailList);
        return BaseResponse.success(logisticsLinkedMallResponse);
    }
}
