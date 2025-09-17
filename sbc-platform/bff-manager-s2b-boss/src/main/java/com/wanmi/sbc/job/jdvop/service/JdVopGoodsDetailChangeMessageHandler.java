package com.wanmi.sbc.job.jdvop.service;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.wanmi.sbc.common.enums.CompanySourceType;
import com.wanmi.sbc.common.enums.ThirdPlatformType;
import com.wanmi.sbc.customer.api.provider.store.StoreQueryProvider;
import com.wanmi.sbc.customer.api.request.store.StoreBycompanySourceType;
import com.wanmi.sbc.customer.bean.vo.StoreVO;
import com.wanmi.sbc.empower.api.response.channel.vop.message.VopMessageResponse;
import com.wanmi.sbc.message.api.provider.vopmessage.VopLogProvider;
import com.wanmi.sbc.message.api.request.vopmessage.VopLogAddRequest;
import com.wanmi.sbc.message.bean.enums.VopLogType;
import com.wanmi.sbc.vas.api.provider.channel.goods.ChannelSyncGoodsProvider;
import com.wanmi.sbc.vas.api.request.channel.goods.ChannelGoodsSyncBySkuVasRequest;
import com.xxl.job.core.context.XxlJobHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author hanwei
 * @className JdVopGoodsDetailChangeMessageHandler
 * @description 处理京东VOP更新商品消息
 * @date 2021/6/2 10:47
 **/
@Slf4j
@Component
public class JdVopGoodsDetailChangeMessageHandler implements JdVopMessageHandler {

    @Autowired
    private ChannelSyncGoodsProvider channelSyncGoodsProvider;

    @Autowired
    private StoreQueryProvider storeQueryProvider;

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
        return 16;
    }

    /**
     * @param messageList
     * @return java.util.List<java.lang.String>
     * @description 消息处理
     * @author hanwei
     * @date 2021/6/2 10:26
     **/
    @Override
    public List<String> handleMessage(List<VopMessageResponse> messageList) {
        List<String> successList = new ArrayList<>();
        for (VopMessageResponse message : messageList) {
            try {
                if (VOP_LOG_FLAG){
                    vopLogProvider.add(VopLogAddRequest.builder().vopLogType(VopLogType.Sixteen).content(("商品信息变更-VOP" +
                            "响应信息-").concat(JSON.toJSONString(message))).build());
                }
                JSONObject jsonObject = JSON.parseObject(message.getResult());
                String jdSkuId = jsonObject.getString("skuId");
                //同步商品
                StoreVO storeVO = storeQueryProvider.getBycompanySourceType(new StoreBycompanySourceType(CompanySourceType.JD_VOP)).getContext();
                List<Long> skuList = Collections.singletonList(Long.valueOf(jdSkuId));
                channelSyncGoodsProvider.syncSkuList(ChannelGoodsSyncBySkuVasRequest.builder()
                        .skuIds(skuList)
                        .thirdPlatformType(ThirdPlatformType.VOP)
                        .storeId(storeVO.getStoreId())
                        .companyInfoId(storeVO.getCompanyInfoId())
                        .companyName(storeVO.getCompanyInfo() == null ? null : storeVO.getCompanyInfo().getCompanyName())
                        .build());
                log.info("同步商品信息成功，第三方sku商品id：{}", jdSkuId);
                if (VOP_LOG_FLAG){
                    vopLogProvider.add(VopLogAddRequest.builder().vopLogType(VopLogType.Sixteen).majorId(jdSkuId).content(
                            "商品信息变更-同步商品信息成功").build());
                }
                XxlJobHelper.log("同步商品信息成功，第三方sku商品id：{}", jdSkuId);
                // 测试时暂不删除消息
                successList.add(message.getId());
            } catch (Exception e) {
                log.error("处理京东推送信息[16:更新商品信息]出现异常，消息内容为：{}", JSON.toJSONString(message), e);
                if (VOP_LOG_FLAG){
                    vopLogProvider.add(VopLogAddRequest.builder().vopLogType(VopLogType.Sixteen).content(
                            "商品信息变更-异常".concat(JSON.toJSONString(message))).build());
                }
                XxlJobHelper.log("处理京东推送信息[16:更新商品信息]出现异常，消息内容为：{}", JSON.toJSONString(message));
                XxlJobHelper.log(e);
            }
        }
        return successList;
    }
}