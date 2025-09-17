package com.wanmi.sbc.message;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson2.JSONObject;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.TerminalSource;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.message.api.provider.appmessage.AppMessageProvider;
import com.wanmi.sbc.message.api.provider.appmessage.AppMessageQueryProvider;
import com.wanmi.sbc.message.api.request.appmessage.AppMessageDelByIdRequest;
import com.wanmi.sbc.message.api.request.appmessage.AppMessagePageRequest;
import com.wanmi.sbc.message.api.request.appmessage.AppMessageSetReadRequest;
import com.wanmi.sbc.message.api.response.appmessage.AppMessagePageResponse;
import com.wanmi.sbc.order.api.provider.trade.TradeQueryProvider;
import com.wanmi.sbc.order.api.request.trade.TradeGetByIdsRequest;
import com.wanmi.sbc.order.bean.vo.TradeVO;
import com.wanmi.sbc.util.CommonUtil;


import io.swagger.v3.oas.annotations.Parameter;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Tag(name = "MessageController", description = "站内信 API")
@RestController
@Validated
@RequestMapping("/appMessage")
public class MessageController {

    @Autowired
    private AppMessageQueryProvider appMessageQueryProvider;

    @Autowired
    private AppMessageProvider appMessageProvider;

    @Autowired
    private CommonUtil commonUtil;

    @Autowired
    private TradeQueryProvider tradeQueryProvider;

    /**
     * 分页查询app消息
     * @param request
     * @return
     */
    @Operation(summary = "分页查询app消息")
    @RequestMapping(value = "/page", method = RequestMethod.POST)
    public BaseResponse<AppMessagePageResponse> page(@RequestBody AppMessagePageRequest request){
        request.setCustomerId(commonUtil.getOperatorId());
        AppMessagePageResponse response = appMessageQueryProvider.page(request).getContext();
        if (TerminalSource.PC.equals(commonUtil.getTerminal())) {
            List<String> orderIds = new ArrayList<>();
            Map<String, String> map = new HashMap<>();
            response.getAppMessageVOPage().forEach(appMessageVO -> {
                try {
                    JSONObject jsonObject = StringUtils.isNotBlank(appMessageVO.getPcRouteParam())
                            ? JSONObject.parseObject(appMessageVO.getPcRouteParam())
                            : JSONObject.parseObject(appMessageVO.getRouteParam());
                    if (jsonObject != null) {
                        Integer type = jsonObject.getInteger("type");
                        String id = jsonObject.getString("id");
                        if (type != null && type == Constants.NINE && StringUtils.isNotBlank(id)) {
                            orderIds.add(id);
                            map.put(appMessageVO.getAppMessageId(), id);
                        }
                    }
                } catch (Exception e){
                    if(e instanceof JSONException && "OrderDetail".equals(appMessageVO.getRouteName())){
                        orderIds.add(appMessageVO.getRouteParam());
                        map.put(appMessageVO.getAppMessageId(), appMessageVO.getRouteParam());
                    }
                }
            });

            if (CollectionUtils.isNotEmpty(orderIds)) {
                List<TradeVO> tradeVOS = tradeQueryProvider.getByIds(TradeGetByIdsRequest.builder().tid(orderIds).build()).getContext().getTradeVO();
                List<String> ids = tradeVOS.stream()
                        .filter(trade -> trade.getOrderTag() != null && Boolean.TRUE.equals(trade.getOrderTag().getBuyCycleFlag()))
                        .map(TradeVO::getId).collect(Collectors.toList());
                response.getAppMessageVOPage().forEach(appMessageVO -> {
                    String id = map.get(appMessageVO.getAppMessageId());
                    if (StringUtils.isNotBlank(id) && ids.contains(id)) {
                        appMessageVO.setBuyCycleOrderFlag(Boolean.TRUE);
                    }
                });
            }
        }
        return BaseResponse.success(response);
    }

    /**
     * 将某条未读消息置为已读
     * @param id
     * @return
     */
    @Operation(summary = "将某条未读消息置为已读")
    @Parameter(name = "id", description = "APP消息id", required = true)
    @RequestMapping(value = "/setMessageRead/{id}", method = RequestMethod.PUT)
    public BaseResponse setMessageRead(@PathVariable("id")String id){
        AppMessageSetReadRequest request = AppMessageSetReadRequest.builder()
                .messageId(id)
                .customerId(commonUtil.getOperatorId())
                .build();
        appMessageProvider.setMessageRead(request);
        return BaseResponse.SUCCESSFUL();
    }

    /**
     * 将所有未读消息置为已读
     * @param
     * @return
     */
    @Operation(summary = "将所有未读消息置为已读")
    @RequestMapping(value = "/setMessageAllRead", method = RequestMethod.PUT)
    public BaseResponse setMessageAllRead(){
        AppMessageSetReadRequest request = AppMessageSetReadRequest.builder()
                .customerId(commonUtil.getOperatorId()).build();
        appMessageProvider.setMessageAllRead(request);
        return BaseResponse.SUCCESSFUL();
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    @Parameter(name = "id", description = "APP消息id", required = true)
    @Operation(summary = "删除消息")
    public BaseResponse deleteById(@PathVariable("id") String id){
        AppMessageDelByIdRequest request = new AppMessageDelByIdRequest(id, commonUtil.getOperatorId());
        appMessageProvider.deleteById(request);
        return BaseResponse.SUCCESSFUL();
    }


}
