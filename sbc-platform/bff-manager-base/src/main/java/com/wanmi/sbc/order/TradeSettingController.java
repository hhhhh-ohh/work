package com.wanmi.sbc.order;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.VASConstants;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.order.api.provider.trade.TradeSettingProvider;
import com.wanmi.sbc.order.api.request.trade.TradeSettingModifyRequest;
import com.wanmi.sbc.order.bean.dto.TradeSettingDTO;
import com.wanmi.sbc.order.bean.enums.OrderErrorCodeEnum;
import com.wanmi.sbc.setting.api.provider.AuditQueryProvider;
import com.wanmi.sbc.setting.api.request.TradeConfigGetByTypeRequest;
import com.wanmi.sbc.setting.api.response.TradeConfigGetByTypeResponse;
import com.wanmi.sbc.setting.bean.enums.ConfigKey;
import com.wanmi.sbc.setting.bean.enums.ConfigType;
import com.wanmi.sbc.setting.bean.vo.ConfigVO;
import com.wanmi.sbc.util.CommonUtil;
import com.wanmi.sbc.util.OperateLogMQUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Tag(name = "TradeSettingController", description = "订单设置 Api")
@RestController
@Validated
@RequestMapping("/tradeSetting")
@Slf4j
public class TradeSettingController {

    @Autowired
    private TradeSettingProvider tradeSettingProvider;

    @Autowired
    private AuditQueryProvider auditQueryProvider;

    @Autowired
    private OperateLogMQUtil operateLogMQUtil;

    @Autowired private CommonUtil commonUtil;

    /**
     * 查询订单配置
     *
     * @return BaseResponse<List>
     */
    @Operation(summary = "查询订单配置")
    @GetMapping("/order_configs")
    public BaseResponse<List<ConfigVO>> queryOrderSettingConfigs() {
        return BaseResponse.success(auditQueryProvider.listTradeConfig().getContext().getConfigVOList());
    }


    /**
     * 修改订单设置
     *
     * @param tradeSettingHttpRequest tradeSettingHttpRequest
     * @return BaseResponse
     */
    @Operation(summary = "修改订单设置")
    @RequestMapping(value = "/order_configs", method = RequestMethod.PUT)
    public BaseResponse updateOrderSettingConfigs(@RequestBody TradeSettingModifyRequest tradeSettingHttpRequest) {
        if (Objects.isNull(tradeSettingHttpRequest) || CollectionUtils.isEmpty(tradeSettingHttpRequest.getTradeSettingRequests())) {
            throw new SbcRuntimeException(OrderErrorCodeEnum.K050040);
        } else {
            boolean o2oBuyOrNot = commonUtil.findVASBuyOrNot(VASConstants.VAS_O2O_SETTING);
            tradeSettingHttpRequest.getTradeSettingRequests().forEach(tradeSettingRequest -> {
                if (Objects.isNull(tradeSettingRequest.getStatus()) || Objects.isNull(tradeSettingRequest.getContext())) {
                    throw new SbcRuntimeException(OrderErrorCodeEnum.K050040);
                }
                if (tradeSettingRequest.getConfigKey() == ConfigKey.ORDERSETTING
                        && tradeSettingRequest.getConfigType() == ConfigType.ORDER_SETTING_PAYMENT_ORDER
                        && o2oBuyOrNot
                        && tradeSettingRequest.getStatus() == 0) {
                    throw new SbcRuntimeException(OrderErrorCodeEnum.K050040);
                }
                if(tradeSettingRequest.getConfigType() == ConfigType.ORDER_SETTING_TIMEOUT_CANCEL){
                    if(StringUtils.isNotBlank(tradeSettingRequest.getContext())){
                        if(Objects.nonNull(JSONObject.parseObject(tradeSettingRequest.getContext()).get("minute"))) {
                            String minuteStr = JSONObject.parseObject(tradeSettingRequest.getContext()).get("minute").toString();
                            if(StringUtils.isNotBlank(minuteStr)){
                                if(Integer.valueOf(minuteStr)>9999){
                                    throw new SbcRuntimeException(OrderErrorCodeEnum.K050040);
                                }
                            }
                        }
                    }
                }
            });
            //支付顺序为不限时，订单失效时间设置需关闭
            Optional<Integer> payMentOrder = tradeSettingHttpRequest.getTradeSettingRequests().stream()
                    .filter(dto -> dto.getConfigType() == ConfigType.ORDER_SETTING_PAYMENT_ORDER)
                    .map(TradeSettingDTO::getStatus).findFirst();
            Optional<Integer> timeoutCancel = tradeSettingHttpRequest.getTradeSettingRequests().stream()
                    .filter(dto -> dto.getConfigType() == ConfigType.ORDER_SETTING_TIMEOUT_CANCEL)
                    .map(TradeSettingDTO::getStatus).findFirst();
            if (payMentOrder.isPresent() && payMentOrder.get() == 0 && timeoutCancel.isPresent() && timeoutCancel.get() == 1) {
                throw new SbcRuntimeException(OrderErrorCodeEnum.K050040);
            }
        }
        tradeSettingProvider.modifyTradeConfigs(tradeSettingHttpRequest);
        List<TradeSettingDTO> tradeSettingRequestList = tradeSettingHttpRequest.getTradeSettingRequests();

        //操作日志记录
        this.saveOperateLog(tradeSettingRequestList);
        return BaseResponse.SUCCESSFUL();
    }

    /**
     * 记录操作日志
     *
     * @param tradeSettingRequestList
     */
    private void saveOperateLog(List<TradeSettingDTO> tradeSettingRequestList) {

        Map<ConfigType, TradeSettingDTO> collect = tradeSettingRequestList.stream()
                .collect(Collectors.toMap(TradeSettingDTO::getConfigType, Function.identity()));

        for (Map.Entry<ConfigType, TradeSettingDTO> entry : collect.entrySet()) {

            if (ConfigType.ORDER_SETTING_TIMEOUT_CANCEL == entry.getKey()) {
                continue;//不做处理 跟ConfigType.ORDER_SETTING_PAYMENT_ORDER 一起处理
            }

            if (ConfigType.ORDER_SETTING_PAYMENT_ORDER == entry.getKey()) {
                if (entry.getValue().getStatus() == 0) {//订单支付顺序不限
                    operateLogMQUtil.convertAndSend("订单", "修改订单设置", "修改订单设置：订单支付顺序设为不限");
                    operateLogMQUtil.convertAndSend("订单", "修改订单设置", "修改订单设置：订单失效时间设为关");
                } else {
                    operateLogMQUtil.convertAndSend("订单", "修改订单设置", "修改订单设置：订单支付顺序设为先款后货");
                    TradeSettingDTO trade = collect.get(ConfigType.ORDER_SETTING_TIMEOUT_CANCEL);
                    if (trade.getStatus() == 0) {
                        operateLogMQUtil.convertAndSend("订单", "修改订单设置", "修改订单设置：订单失效时间设为关");
                    } else {
                        operateLogMQUtil.convertAndSend("订单", "修改订单设置",
                                "修改订单设置：订单失效时间设为开,时间为" + trade.getContext().substring(8,
                                        trade.getContext().length() - 1) + "小时");
                    }
                }
                continue;
            }

            if(ConfigType.ORDER_SETTING_ALONG_REFUND == entry.getKey()){
                if(entry.getValue().getStatus() == 0){
                    operateLogMQUtil.convertAndSend("订单", "修改订单设置", "修改订单设置：允许在途退货设为关");
                    continue;
                }
                operateLogMQUtil.convertAndSend("订单", "修改订单设置", "修改订单设置：允许在途退货设为开");
                continue;
            }

            if (ConfigType.ORDER_SETTING_COUNTDOWN == entry.getKey()){
                if(entry.getValue().getStatus() == 0){
                    operateLogMQUtil.convertAndSend("订单", "订单倒计时设置", "订单倒计时设置：订单倒计时设为关");
                    continue;
                }
                operateLogMQUtil.convertAndSend("订单", "订单倒计时设置", "订单倒计时设置：订单倒计时设为开");
                continue;
            }

            if (ConfigType.ORDER_SETTING_BUYER_MODIFY_CONSIGNEE == entry.getKey()){
                if(entry.getValue().getStatus() == 0){
                    operateLogMQUtil.convertAndSend("订单", "修改订单设置", "修改订单设置：买家自助修改收货地址设为关");
                    continue;
                }
                operateLogMQUtil.convertAndSend("订单", "修改订单设置", "修改订单设置：买家自助修改收货地址设为开");
                continue;
            }

            if (ConfigType.ORDER_SETTING_QUICK_ORDER == entry.getKey()){
                if(entry.getValue().getStatus() == 0){
                    operateLogMQUtil.convertAndSend("订单", "修改订单设置", "修改订单设置：PC商城快速下单设为关");
                    continue;
                }
                operateLogMQUtil.convertAndSend("订单", "修改订单设置", "修改订单设置：PC商城快速下单设为开");
                continue;
            }

            StringBuilder opContext = new StringBuilder("修改订单设置：");
            if (ConfigType.ORDER_SETTING_AUTO_RECEIVE == entry.getKey()) {
                opContext.append("订单自动确认收货设为");
            } else if (ConfigType.ORDER_SETTING_APPLY_REFUND == entry.getKey()) {
                opContext.append("已完成订单允许申请退单设为");
            } else if (ConfigType.ORDER_SETTING_REFUND_AUTO_AUDIT == entry.getKey()) {
                opContext.append("待审核退单自动审核设为");
            } else if (ConfigType.ORDER_SETTING_REFUND_AUTO_RECEIVE == entry.getKey()) {
                opContext.append("退单自动确认收货设为");
            } else if (ConfigType.ORDER_SETTING_TIMEOUT_EVALUATE == entry.getKey()) {
                opContext.append("订单超时自动评价设为");
            }else if (ConfigType.ORDER_SETTING_VIRTUAL_APPLY_REFUND == entry.getKey()) {
                opContext.append("卡券订单允许代客退单设为");
            }
            if (entry.getValue().getStatus() == 1) {
                String context = entry.getValue().getContext();
                opContext.append("开,时间为").append(context, 7, context.length() - 1).append('天');
                if(ConfigType.ORDER_SETTING_TIMEOUT_EVALUATE== entry.getKey()){
                    String comment = JSON.parseObject(context).getString("comment");
                    if(StringUtils.isNotBlank(comment)){
                        opContext.append("，好评文案为").append(comment);
                    }
                }
            } else {
                opContext.append('关');
            }
            operateLogMQUtil.convertAndSend("订单", "修改订单设置", opContext.toString());
        }
    }

    /**
     * 是否允许申请退单
     *
     * @return BaseResponse
     */
    @Operation(summary = "记录操作日志")
    @RequestMapping(value = "/order_configs/return_order_apply")
    public BaseResponse<Boolean> queryOrderSettingApplyRefund(){
        TradeConfigGetByTypeRequest request = new TradeConfigGetByTypeRequest();
        request.setConfigType(ConfigType.ORDER_SETTING_APPLY_REFUND);
        TradeConfigGetByTypeResponse tradeConfigGetByTypeResponse = auditQueryProvider.getTradeConfigByType(request).getContext();
        return BaseResponse.success(Integer.valueOf(1).equals(tradeConfigGetByTypeResponse.getStatus()));
    }

    /**
     * 是否允许申请退单config
     *
     * @return BaseResponse
     */
    @Operation(summary = "是否允许申请退单config")
    @RequestMapping(value = "/order_configs/return_order_apply/config")
    public BaseResponse<TradeConfigGetByTypeResponse> queryOrderSettingApplyRefundConfig(){
        TradeConfigGetByTypeRequest request = new TradeConfigGetByTypeRequest();
        request.setConfigType(ConfigType.ORDER_SETTING_APPLY_REFUND);
        TradeConfigGetByTypeResponse tradeConfigGetByTypeResponse = auditQueryProvider.getTradeConfigByType(request).getContext();
        return BaseResponse.success(tradeConfigGetByTypeResponse);
    }
}
