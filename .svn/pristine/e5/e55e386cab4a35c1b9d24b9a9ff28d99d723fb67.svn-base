package com.wanmi.sbc.job.jdvop.service;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.constant.RedisKeyConstant;
import com.wanmi.sbc.common.enums.ThirdPlatformType;
import com.wanmi.sbc.common.redis.util.RedisUtil;
import com.wanmi.sbc.elastic.api.provider.goods.EsGoodsInfoElasticProvider;
import com.wanmi.sbc.elastic.api.request.goods.EsGoodsDeleteByIdsRequest;
import com.wanmi.sbc.elastic.api.request.goods.EsGoodsInitProviderGoodsInfoRequest;
import com.wanmi.sbc.empower.api.provider.channel.vop.goods.VopGoodsProvider;
import com.wanmi.sbc.empower.api.request.channel.vop.goods.CheckSkuRequest;
import com.wanmi.sbc.empower.api.request.channel.vop.goods.SkuSellingPriceRequest;
import com.wanmi.sbc.empower.api.response.channel.vop.goods.CheckSkuResponse;
import com.wanmi.sbc.empower.api.response.channel.vop.goods.SkuSellingPriceResponse;
import com.wanmi.sbc.empower.api.response.channel.vop.message.VopMessageResponse;
import com.wanmi.sbc.goods.api.provider.info.GoodsInfoProvider;
import com.wanmi.sbc.goods.api.provider.info.GoodsInfoQueryProvider;
import com.wanmi.sbc.goods.api.request.info.GoodsInfoListByConditionRequest;
import com.wanmi.sbc.goods.api.request.info.GoodsInfoModifyAddedStatusByProviderRequest;
import com.wanmi.sbc.goods.api.request.info.GoodsInfoUpdateSupplyPriceRequest;
import com.wanmi.sbc.goods.bean.enums.AddedFlag;
import com.wanmi.sbc.goods.bean.enums.GoodsSource;
import com.wanmi.sbc.goods.bean.vo.GoodsInfoVO;
import com.wanmi.sbc.message.api.provider.vopmessage.VopLogProvider;
import com.wanmi.sbc.message.api.request.vopmessage.VopLogAddRequest;
import com.wanmi.sbc.message.bean.enums.VopLogType;
import com.xxl.job.core.context.XxlJobHelper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 京东VOP 处理商品上下架消息
 * @author hanwei
 * @className JdVopGoodsAddedFlagMessageHandler
 * @description TODO
 * @date 2021/6/1 18:48
 **/
@Slf4j
@Component
public class JdVopGoodsAddedFlagMessageHandler implements JdVopMessageHandler{

    private static final int GOODS_CAN_SALE = 1;//是否可售（1是0否）

    @Autowired
    private GoodsInfoQueryProvider goodsInfoQueryProvider;

    @Autowired
    private GoodsInfoProvider goodsInfoProvider;

    @Autowired
    private VopGoodsProvider vopGoodsProvider;

    @Autowired
    private EsGoodsInfoElasticProvider esGoodsInfoElasticProvider;

    @Autowired
    private RedisUtil redisService;

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
        //4:商品上下架变更
        return 4;
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
        List<String> deleteList = new ArrayList<>();
        List<String> takeDownList = new ArrayList<>();
        List<String> addedList = new ArrayList<>();
        Set<String> goodsIds = new HashSet<>();
        List<GoodsInfoVO> updateGoodsInfoList = new ArrayList<>();
        messageList.forEach(message -> {
            try {
                if (VOP_LOG_FLAG){
                    vopLogProvider.add(VopLogAddRequest.builder().vopLogType(VopLogType.Four).content("商品上下架变更-VOP响应信息-".concat(JSON.toJSONString(message))).build());
                }
                JSONObject jsonObject = JSON.parseObject(message.getResult());
                String jdSkuId = jsonObject.getString("skuId");
                Integer state = jsonObject.getInteger("state");
                List<GoodsInfoVO> goodsInfos = goodsInfoQueryProvider.listByCondition(GoodsInfoListByConditionRequest.builder()
                        .thirdPlatformSkuId(Collections.singletonList(jdSkuId))
                        .thirdPlatformType(ThirdPlatformType.VOP.toValue())
                        .build())
                        .getContext().getGoodsInfos();
                // 获取当前商品的上下架状态
                if (CollectionUtils.isNotEmpty(goodsInfos)) {
                    List<String> goodsInfoIdList = goodsInfos.stream().map(GoodsInfoVO::getGoodsInfoId).collect(Collectors.toList());
                    List<String> goodsIdList = goodsInfos.stream().map(GoodsInfoVO::getGoodsId).collect(Collectors.toList());
                    //京东供应商端
                    GoodsInfoVO goodsInfoJd = goodsInfos.stream().filter(goodsInfoVO -> goodsInfoVO.getGoodsSource().equals(GoodsSource.VOP.toValue())).findFirst().orElse(null);
                    if (Objects.nonNull(goodsInfoJd)) {
                        if (state == 1) {
                            // 上架
                            // 上架前要1获取商品可售性 -> 2获取最新的价格
                            BaseResponse<List<CheckSkuResponse>> checkSku = vopGoodsProvider.checkSku(CheckSkuRequest.builder().skuIds(jdSkuId).build());
                            if (CollectionUtils.isNotEmpty(checkSku.getContext())) {
                                if (GOODS_CAN_SALE == checkSku.getContext().get(0).getSaleState()) {
                                    // 可售
                                    BaseResponse<List<SkuSellingPriceResponse>> sellingPrice = vopGoodsProvider.getSellingPrice(SkuSellingPriceRequest.builder().sku(jdSkuId).build());
                                    if (CollectionUtils.isNotEmpty(sellingPrice.getContext())) {
                                        // 更新最新的价格
                                        goodsInfoJd.setSupplyPrice(sellingPrice.getContext().get(0).getPrice());
                                        goodsInfoJd.setUpdateTime(LocalDateTime.now());
                                        updateGoodsInfoList.add(goodsInfoJd);
                                        addedList.addAll(goodsInfoIdList);
                                        goodsIds.addAll(goodsIdList);
                                    }
                                } else {
                                    // 不可售 -> 下架
                                    takeDownList.addAll(goodsInfoIdList);
                                    goodsIds.addAll(goodsIdList);
                                }
                            }
                        } else if (state == 0) {
                            // 下架
                            takeDownList.addAll(goodsInfoIdList);
                            goodsIds.addAll(goodsIdList);
                        }
                        // 测试时暂不删除消息
                        deleteList.add(message.getId());
                    }
                } else {
                    log.error("商品信息不存在，推送Id:{},京东商品Id：{}", message.getId(), jdSkuId);
                    if (VOP_LOG_FLAG){
                        vopLogProvider.add(VopLogAddRequest.builder().vopLogType(VopLogType.Four).majorId(jdSkuId).content(
                                "商品上下架变更-商品信息不存在").build());
                    }
                    XxlJobHelper.log("商品信息不存在，推送Id:{},京东商品Id：{}", message.getId(), jdSkuId);
                    // 不存在的也进行删除
                    // 测试时暂不删除消息
                    deleteList.add(message.getId());
                }
            } catch (Exception e) {
                log.error("处理京东推送信息[4:商品上下架]出现异常，消息内容为：{}", JSON.toJSONString(message), e);
                if (VOP_LOG_FLAG){
                    vopLogProvider.add(VopLogAddRequest.builder().vopLogType(VopLogType.Four).content("商品上下架变更-异常".concat(JSON.toJSONString(message))).build());
                }
                XxlJobHelper.log("处理京东推送信息[4:商品上下架]出现异常，消息内容为：{}", JSON.toJSONString(message));
                XxlJobHelper.log(e);
            }
        });
        // 统一下架
        if (CollectionUtils.isNotEmpty(takeDownList)) {
            goodsInfoProvider.modifyAddedStatusByProvider(GoodsInfoModifyAddedStatusByProviderRequest.builder()
                    .providerGoodsInfoIds(takeDownList)
                    .addedFlag(AddedFlag.NO)
                    .build());
            log.info("商品下架成功，商品id：{}", JSON.toJSONString(takeDownList));
            if (VOP_LOG_FLAG){
                vopLogProvider.add(VopLogAddRequest.builder().vopLogType(VopLogType.Four).content("商品上下架变更-商品下架成功".concat(JSON.toJSONString(takeDownList))).build());
            }
            XxlJobHelper.log("商品下架成功，商品id：{}", JSON.toJSONString(takeDownList));
        }
        // 统一上架
        if (CollectionUtils.isNotEmpty(addedList)) {
            goodsInfoProvider.modifyAddedStatusByProvider(GoodsInfoModifyAddedStatusByProviderRequest.builder()
                    .providerGoodsInfoIds(addedList)
                    .addedFlag(AddedFlag.YES)
                    .build());
            log.info("商品上架成功，商品id：{}", JSON.toJSONString(addedList));
            if (VOP_LOG_FLAG){
                vopLogProvider.add(VopLogAddRequest.builder().vopLogType(VopLogType.Four).content("商品上下架变更-商品上架成功".concat(JSON.toJSONString(addedList))).build());
            }
            XxlJobHelper.log("商品上架成功，商品id：{}", JSON.toJSONString(addedList));
        }

        //统一修改供货价
        if (CollectionUtils.isNotEmpty(updateGoodsInfoList)) {
            goodsInfoProvider.updateSupplyPrice(GoodsInfoUpdateSupplyPriceRequest.builder().goodsInfos(updateGoodsInfoList).build());
        }

        //刷新ES
        List<String> changeList = new ArrayList<>(goodsIds);
        if (CollectionUtils.isNotEmpty(changeList)) {
            //更新ES
            esGoodsInfoElasticProvider.deleteByGoods(EsGoodsDeleteByIdsRequest.builder().deleteIds(changeList).build());
            esGoodsInfoElasticProvider.initProviderEsGoodsInfo(EsGoodsInitProviderGoodsInfoRequest.builder().
                    storeId(null).providerGoodsIds(changeList).build());
            goodsIds.forEach(goodsId -> {
                //更新redis商品基本数据
                String goodsDetailInfo = redisService.getString(RedisKeyConstant.GOODS_DETAIL_CACHE + goodsId);
                if (StringUtils.isNotBlank(goodsDetailInfo)) {
                    redisService.delete(RedisKeyConstant.GOODS_DETAIL_CACHE + goodsId);
                }
            });
        }

        return deleteList;
    }
}