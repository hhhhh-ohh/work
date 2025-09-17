package com.wanmi.sbc.job.jdvop.service;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.wanmi.sbc.common.enums.CompanySourceType;
import com.wanmi.sbc.common.enums.ThirdPlatformType;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.customer.api.provider.store.StoreQueryProvider;
import com.wanmi.sbc.customer.api.request.store.StoreBycompanySourceType;
import com.wanmi.sbc.customer.bean.vo.StoreVO;
import com.wanmi.sbc.elastic.api.provider.goods.EsGoodsInfoElasticProvider;
import com.wanmi.sbc.elastic.api.provider.standard.EsStandardProvider;
import com.wanmi.sbc.elastic.api.request.goods.EsGoodsDeleteByIdsRequest;
import com.wanmi.sbc.elastic.api.request.goods.EsGoodsInfoRequest;
import com.wanmi.sbc.elastic.api.request.standard.EsStandardDeleteByIdsRequest;
import com.wanmi.sbc.elastic.api.request.standard.EsStandardInitRequest;
import com.wanmi.sbc.empower.api.response.channel.vop.message.VopMessageResponse;
import com.wanmi.sbc.goods.api.provider.goods.GoodsProvider;
import com.wanmi.sbc.goods.api.provider.info.GoodsInfoQueryProvider;
import com.wanmi.sbc.goods.api.request.goods.ThirdPlatformGoodsDelRequest;
import com.wanmi.sbc.goods.api.request.info.GoodsInfoListByConditionRequest;
import com.wanmi.sbc.goods.api.response.linkedmall.ThirdPlatformGoodsDelResponse;
import com.wanmi.sbc.goods.bean.dto.ThirdPlatformGoodsDelDTO;
import com.wanmi.sbc.goods.bean.enums.GoodsSource;
import com.wanmi.sbc.goods.bean.vo.GoodsInfoVO;
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
import java.util.stream.Collectors;

/**
 * @author hanwei
 * @className JdVopGoodsAddOrDeleteMessageHandler
 * @description 处理商品新增或删除消息
 * @date 2021/6/2 10:25
 **/
@Slf4j
@Component
public class JdVopGoodsAddOrDeleteMessageHandler implements JdVopMessageHandler{

    @Autowired
    private GoodsInfoQueryProvider goodsInfoQueryProvider;

    @Autowired
    private GoodsProvider goodsProvider;

    @Autowired
    private EsGoodsInfoElasticProvider esGoodsInfoElasticProvider;

    @Autowired
    private EsStandardProvider esStandardProvider;

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
     * @return
     */
    @Override
    public Integer getVopMessageType() {
        //6:商品池内商品添加/删除
        return 6;
    }

    /**
     * @description 消息处理
     * @author  hanwei
     * @date 2021/6/2 10:26
     * @param messageList
     * @return java.util.List<java.lang.String>
     **/
    @Override
    public List<String> handleMessage(List<VopMessageResponse> messageList) {
        List<String> successList = new ArrayList<>();
        Map<String, List<String>> deleteThirdSkuIdListMap = new HashMap<>();
        StoreVO storeVO = storeQueryProvider.getBycompanySourceType(new StoreBycompanySourceType(CompanySourceType.JD_VOP)).getContext();

        messageList.forEach(message -> {
            try {
                if (VOP_LOG_FLAG){
                    vopLogProvider.add(VopLogAddRequest.builder().vopLogType(VopLogType.Six).content(("商品池内商品添加/删除-VOP" +
                            "响应信息-").concat(JSON.toJSONString(message))).build());
                }
                JSONObject jsonObject = JSON.parseObject(message.getResult());
                String jdSkuId = jsonObject.getString("skuId");
                Integer state = jsonObject.getInteger("state");
                List<GoodsInfoVO> goodsInfos = goodsInfoQueryProvider.listByCondition(GoodsInfoListByConditionRequest.builder()
                                .thirdPlatformSkuId(Collections.singletonList(jdSkuId))
                                .thirdPlatformType(ThirdPlatformType.VOP.toValue())
                                .goodsSource(GoodsSource.VOP.toValue())
                                .build())
                        .getContext().getGoodsInfos();
                // 添加
                if (state == 1 && CollectionUtils.isEmpty(goodsInfos)) {
                    //添加商品
                    channelSyncGoodsProvider.syncSkuList(ChannelGoodsSyncBySkuVasRequest.builder()
                            .skuIds(Collections.singletonList(Long.valueOf(jdSkuId)))
                            .thirdPlatformType(ThirdPlatformType.VOP)
                            .storeId(storeVO.getStoreId())
                            .companyInfoId(storeVO.getCompanyInfoId())
                            .companyName(storeVO.getCompanyInfo() == null ? null : storeVO.getCompanyInfo().getCompanyName())
                            .build());
                    log.info("添加商品成功，第三方sku商品id：{}", jdSkuId);
                    if (VOP_LOG_FLAG){
                        vopLogProvider.add(VopLogAddRequest.builder().vopLogType(VopLogType.Six).majorId(jdSkuId).content(
                                "商品池内商品添加/删除-添加商品成功").build());
                    }
                    XxlJobHelper.log("添加商品成功，第三方sku商品id：{}", jdSkuId);
                }
                //删除
                else if (state == Constants.TWO && CollectionUtils.isNotEmpty(goodsInfos)) {
                    GoodsInfoVO goodsInfo = goodsInfos.get(0);
                    List<String> thirdSkuIdList = deleteThirdSkuIdListMap.get(goodsInfo.getThirdPlatformSpuId());
                    if(CollectionUtils.isEmpty(thirdSkuIdList)){
                        thirdSkuIdList = goodsInfos.stream().map(GoodsInfoVO::getThirdPlatformSkuId).collect(Collectors.toList());
                    }else{
                        thirdSkuIdList.addAll(goodsInfos.stream().map(GoodsInfoVO::getThirdPlatformSkuId).collect(Collectors.toList()));
                    }
                    deleteThirdSkuIdListMap.put(goodsInfo.getThirdPlatformSpuId(), thirdSkuIdList);
                }
                // 测试时暂不删除消息
                successList.add(message.getId());
            } catch (Exception e) {
                log.error("处理京东推送信息[6:商品池商品增加或减少]出现异常，消息内容为：{}", JSON.toJSONString(message), e);
                if (VOP_LOG_FLAG){
                    vopLogProvider.add(VopLogAddRequest.builder().vopLogType(VopLogType.Six).content("商品池内商品添加/删除-异常".concat(JSON.toJSONString(message))).build());
                }
                XxlJobHelper.log("处理京东推送信息[6:商品池商品增加或减少]出现异常，消息内容为：{}", JSON.toJSONString(message));
                XxlJobHelper.log(e);
            }
        });

        //删除商品
        if (CollectionUtils.isNotEmpty(deleteThirdSkuIdListMap.entrySet())) {
            List<ThirdPlatformGoodsDelDTO> thirdPlatformGoodsDelDTOList = deleteThirdSkuIdListMap.entrySet().stream()
                    .map(entry -> ThirdPlatformGoodsDelDTO.builder()
                            .thirdPlatformType(ThirdPlatformType.VOP)
                            .goodsSource(GoodsSource.VOP)
                            .itemId(Long.valueOf(entry.getKey()))
                            .skuIdList(entry.getValue().stream().map(Long::parseLong).collect(Collectors.toList()))
                            .build())
                    .collect(Collectors.toList());
            ThirdPlatformGoodsDelResponse response = goodsProvider.deleteThirdPlatformGoods(
                    ThirdPlatformGoodsDelRequest.builder().thirdPlatformGoodsDelDTOS(thirdPlatformGoodsDelDTOList).deleteAllSku(true).build()).getContext();
            List<String> esGoodsInfoIds = response.getGoodsInfoIds();
            if (esGoodsInfoIds.size() > 0) {
                esGoodsInfoElasticProvider.deleteByGoods(EsGoodsDeleteByIdsRequest.builder().deleteIds(esGoodsInfoIds).build());
                esGoodsInfoElasticProvider.initEsGoodsInfo(EsGoodsInfoRequest.builder().skuIds(esGoodsInfoIds).build());
            }
            //初始化商品库ES
            if (CollectionUtils.isNotEmpty(response.getStandardIds())) {
                esStandardProvider.init(EsStandardInitRequest.builder().goodsIds(response.getStandardIds()).build());
            }
            //删除商品库ES
            if (CollectionUtils.isNotEmpty(response.getDelStandardIds())) {
                esStandardProvider.deleteByIds(EsStandardDeleteByIdsRequest.builder().goodsIds(response.getDelStandardIds()).build());
            }
            log.info("删除商品成功，第三方商品id：{}", JSON.toJSONString(deleteThirdSkuIdListMap));
            if (VOP_LOG_FLAG){
                vopLogProvider.add(VopLogAddRequest.builder().vopLogType(VopLogType.Six).content("商品池内商品添加/删除-删除商品成功".concat(JSON.toJSONString(deleteThirdSkuIdListMap))).build());
            }
            XxlJobHelper.log("删除商品成功，第三方商品id：{}", JSON.toJSONString(deleteThirdSkuIdListMap));
        }
        return successList;
    }
}