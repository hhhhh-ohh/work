package com.wanmi.sbc.job.jdvop.service;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.ThirdPlatformType;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.empower.api.provider.channel.vop.order.VopOrderProvider;
import com.wanmi.sbc.empower.api.request.channel.vop.order.VopQueryOrderDetailRequest;
import com.wanmi.sbc.empower.api.response.channel.vop.message.VopMessageResponse;
import com.wanmi.sbc.empower.api.response.channel.vop.order.VopQueryOrderDetailResponse;
import com.wanmi.sbc.empower.bean.vo.channel.order.VopSuborderVO;
import com.wanmi.sbc.message.api.provider.vopmessage.VopLogProvider;
import com.wanmi.sbc.message.api.request.vopmessage.VopLogAddRequest;
import com.wanmi.sbc.message.bean.enums.VopLogType;
import com.wanmi.sbc.order.api.provider.thirdplatformtrade.ThirdPlatformTradeProvider;
import com.wanmi.sbc.order.api.provider.thirdplatformtrade.ThirdPlatformTradeQueryProvider;
import com.wanmi.sbc.order.api.request.trade.ThirdPlatformTradeByThirdPlatformOrderIdRequest;
import com.wanmi.sbc.order.api.request.trade.ThirdPlatformTradeUpdateRequest;
import com.wanmi.sbc.order.api.response.trade.ThirdPlatformTradeByThirdPlatformOrderIdResponse;
import com.wanmi.sbc.order.bean.dto.ThirdPlatformTradeDTO;
import com.wanmi.sbc.order.bean.vo.TradePlatformSuborderItemVO;
import com.wanmi.sbc.order.bean.vo.TradePlatformSuborderVO;
import com.xxl.job.core.context.XxlJobHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author hanwei
 * @className JdVopSplitOrderMessageHandler
 * @description 京东VOP拆单消息处理
 * @date 2021/6/2 10:49
 **/
@Slf4j
@Component
public class JdVopSplitOrderMessageHandler implements JdVopMessageHandler {

    @Autowired
    private ThirdPlatformTradeQueryProvider thirdPlatformTradeQueryProvider;

    @Autowired
    private ThirdPlatformTradeProvider thirdPlatformTradeProvider;

    @Autowired
    private VopOrderProvider vopOrderProvider;

    @Autowired
    private VopLogProvider vopLogProvider;

    /**
     * 是否记录vop日志，true是记录, false是关闭
     * @return
     */
    @Value("${vopLogFlag}")
    private boolean VOP_LOG_FLAG = true;

    /**
     * 要处理的京东VOP消息类型
     * 1:拆单 4:商品上下架变更 6:商品池内商品添加/删除 10:订单取消 12:配送单生成成功 14:支付失败消息 16:商品信息变更
     *
     * @return
     */
    @Override
    public Integer getVopMessageType() {
        return 1;
    }

    /**
     * 京东VOP拆单消息处理
     * 根据主单查询订单详情获取所有子单信息，并将子单信息全量跟新到数据库中
     * @author  wur
     * @date: 2021/5/18 19:18
     * @param messageList
     * @return
     **/
    @Override
    public List<String> handleMessage(List<VopMessageResponse> messageList) {
        List<String> successList = new ArrayList<>();
        messageList.forEach(message -> {
            try {
                if (VOP_LOG_FLAG){
                    vopLogProvider.add(VopLogAddRequest.builder().vopLogType(VopLogType.One).content(("拆单-VOP" +
                            "响应信息-").concat(JSON.toJSONString(message))).build());
                }
                JSONObject jsonObject = JSON.parseObject(message.getResult());
                String pOrder = jsonObject.getString("pOrder");
                // 查询第三方平台订单，如果不存在则不消费此消息
                BaseResponse<ThirdPlatformTradeByThirdPlatformOrderIdResponse> baseResponse = thirdPlatformTradeQueryProvider.queryByThirdPlatformOrderId(ThirdPlatformTradeByThirdPlatformOrderIdRequest.builder().thirdPlatformOrderId(pOrder).thirdPlatformType(ThirdPlatformType.VOP).build());
                if (Objects.nonNull(baseResponse.getContext().getThirdPlatformTradeVO())) {
                    BaseResponse<VopQueryOrderDetailResponse> orderDetailResponse = vopOrderProvider.queryOrderDetail(VopQueryOrderDetailRequest.builder().jdOrderId(Long.valueOf(pOrder)).build());
                    if (Objects.nonNull(orderDetailResponse.getContext())) {
                        List<VopSuborderVO> suborderS = orderDetailResponse.getContext().toSubOrder();
                        List<TradePlatformSuborderVO> suborderList = new ArrayList<>();
                        suborderS.forEach(suborder -> {
                            TradePlatformSuborderVO suborderVO = new TradePlatformSuborderVO();
                            suborderVO.setSuborderId(suborder.getJdOrderId());
                            List<TradePlatformSuborderItemVO> suborderItemList = new ArrayList<>();
                            suborder.toSkuList().forEach(vopSuborderSkuVO -> {
                                suborderItemList.add(TradePlatformSuborderItemVO.builder().name(vopSuborderSkuVO.getName()).num(vopSuborderSkuVO.getNum()).skuId(vopSuborderSkuVO.getSkuId().toString()).build());
                            });
                            suborderVO.setItemList(suborderItemList);
                            suborderList.add(suborderVO);
                        });
                        ThirdPlatformTradeDTO thirdPlatformTradeDTO = KsBeanUtil.convert(baseResponse.getContext().getThirdPlatformTradeVO(), ThirdPlatformTradeDTO.class);
                        thirdPlatformTradeDTO.setSuborderList(suborderList);
                        thirdPlatformTradeProvider.update(ThirdPlatformTradeUpdateRequest.builder().trade(thirdPlatformTradeDTO).build());
                    }
                    successList.add(message.getId());
                }else{
                    log.error("订单不存在，第三方订单id：{}", pOrder);
                    if (VOP_LOG_FLAG){
                        vopLogProvider.add(VopLogAddRequest.builder().vopLogType(VopLogType.One).majorId(pOrder).content(
                                "拆单-订单不存在").build());
                    }
                    XxlJobHelper.log("订单不存在，第三方订单id：{}", pOrder);
                }
            } catch (Exception e) {
                log.error("处理订单拆单信息出现异常，消息内容为:{}", JSON.toJSONString(message), e);
                if (VOP_LOG_FLAG){
                    vopLogProvider.add(VopLogAddRequest.builder().vopLogType(VopLogType.One).content("拆单-订单拆单异常".concat(JSON.toJSONString(message))).build());
                }
                XxlJobHelper.log("处理订单拆单信息出现异常，消息内容为:{}", JSON.toJSONString(message));
                XxlJobHelper.log(e);
            }
        });
        return successList;
    }
}