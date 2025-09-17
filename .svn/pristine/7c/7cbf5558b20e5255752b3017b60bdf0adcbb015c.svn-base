package com.wanmi.sbc.job.jdvop.service;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.wanmi.sbc.common.enums.CompanySourceType;
import com.wanmi.sbc.common.enums.ThirdPlatformType;
import com.wanmi.sbc.customer.api.provider.store.StoreQueryProvider;
import com.wanmi.sbc.customer.api.request.store.StoreBycompanySourceType;
import com.wanmi.sbc.customer.bean.vo.StoreVO;
import com.wanmi.sbc.empower.api.response.channel.vop.message.VopMessageResponse;
import com.wanmi.sbc.goods.api.provider.info.GoodsInfoQueryProvider;
import com.wanmi.sbc.message.api.provider.vopmessage.VopLogProvider;
import com.wanmi.sbc.message.api.request.vopmessage.VopLogAddRequest;
import com.wanmi.sbc.message.bean.enums.VopLogType;
import com.wanmi.sbc.vas.api.provider.channel.goods.ChannelSyncGoodsProvider;
import com.wanmi.sbc.vas.api.request.channel.goods.ChannelGoodsSyncBySkuVasRequest;
import com.xxl.job.core.context.XxlJobHelper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.*;

import static com.wanmi.sbc.vop.VopConstant.MSG_PRICE_CHANGE;

/**
 * @description 修改价格
 * @date 2022/1/29 15:27
 **/
@Slf4j
@Component
public class JdVopPriceMessageHandler implements JdVopMessageHandler {

    @Autowired
    private VopLogProvider vopLogProvider;

    @Autowired
    private GoodsInfoQueryProvider goodsInfoQueryProvider;

    @Autowired
    private ChannelSyncGoodsProvider channelSyncGoodsProvider;

    @Autowired
    private StoreQueryProvider storeQueryProvider;


    /**
     * 是否记录vop日志，true是记录, false是关闭
     * @return
     */
    @Value("${vopLogFlag}")
    private boolean VOP_LOG_FLAG = true;

    @Override
    public Integer getVopMessageType() {
        return MSG_PRICE_CHANGE;
    }

    @Override
    public List<String> handleMessage(List<VopMessageResponse> messageList) {
        List<String> successList = new ArrayList<>();
        if (CollectionUtils.isEmpty(messageList)){
            return successList;
        }

        Map<Long,VopMessageResponse> messageMap = new HashMap<>();

        messageList.forEach(message -> {
            recordVopLog("商品改价-VOP响应消息-".concat(JSON.toJSONString(message)));
            JSONObject jsonObject = JSON.parseObject(message.getResult());
            Long jdSkuId = jsonObject.getLongValue("skuId");
            Integer state = jsonObject.getInteger("state");
            XxlJobHelper.log("同步更新价格信息成功，第三方sku商品id：{}，状态：{}", jdSkuId, state);
            // 批次消息内相同sku，取最新的价格变动消息，旧消息默认消费成功
            if (Objects.nonNull(messageMap.put(jdSkuId, message))){
                successList.add(message.getId());
            }
        });

        StoreVO storeVO = storeQueryProvider.getBycompanySourceType(new StoreBycompanySourceType(CompanySourceType.JD_VOP)).getContext();

        messageMap.keySet().forEach(jdSkuId->{
            VopMessageResponse message = messageMap.get(jdSkuId);
            try {
                channelSyncGoodsProvider.syncSkuPriceList(ChannelGoodsSyncBySkuVasRequest.builder()
                        .skuIds(Collections.singletonList(jdSkuId))
                        .thirdPlatformType(ThirdPlatformType.VOP)
                        .storeId(storeVO.getStoreId())
                        .companyInfoId(storeVO.getCompanyInfoId())
                        .companyName(storeVO.getCompanyInfo() == null ? null : storeVO.getCompanyInfo().getCompanyName())
                        .build());
                successList.add(message.getId());
            }catch (Exception e){
                recordVopLog("更新价格信息-异常".concat(JSON.toJSONString(message)));
                log.error("处理京东推送信息[2:更新价格信息]出现异常，消息内容为：{}", JSON.toJSONString(message), e);
                XxlJobHelper.log("处理京东推送信息[2:更新价格信息]出现异常，消息内容为：{}", JSON.toJSONString(message));
                XxlJobHelper.log(e);
            }
        });

        return successList;
    }

    public void recordVopLog(String message){
        if (VOP_LOG_FLAG){
            vopLogProvider.add(VopLogAddRequest.builder().vopLogType(VopLogType.Two).content(message).build());
        }
    }
}
