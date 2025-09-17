package com.wanmi.sbc.message.service;

import com.google.common.collect.Lists;
import com.wanmi.sbc.account.api.provider.finance.record.SettlementQueryProvider;
import com.wanmi.sbc.account.api.request.finance.record.SettlementBatchModifyStatusRequest;
import com.wanmi.sbc.account.api.request.finance.record.SettlementPageRequest;
import com.wanmi.sbc.account.api.response.finance.record.LakalaSettlementAddResponse;import com.wanmi.sbc.account.api.response.storeInformation.StoreAuditStateResponse;
import com.wanmi.sbc.account.bean.enums.CheckState;
import com.wanmi.sbc.account.bean.enums.SettleStatus;
import com.wanmi.sbc.account.bean.vo.SettlementViewVO;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.enums.StoreType;import com.wanmi.sbc.common.enums.storemessage.BossMessageNode;
import com.wanmi.sbc.common.enums.storemessage.ProviderMessageNode;
import com.wanmi.sbc.common.enums.storemessage.StoreMessagePlatform;
import com.wanmi.sbc.common.enums.storemessage.SupplierMessageNode;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.common.util.MutableMap;
import com.wanmi.sbc.customer.api.provider.store.StoreQueryProvider;
import com.wanmi.sbc.customer.api.request.store.ListStoreByIdsRequest;
import com.wanmi.sbc.customer.api.response.store.StoreInfoResponse;
import com.wanmi.sbc.customer.bean.vo.StoreVO;
import com.wanmi.sbc.elastic.api.provider.storeInformation.EsStoreInformationQueryProvider;
import com.wanmi.sbc.elastic.api.request.storeInformation.StoreInfoQueryPageRequest;
import com.wanmi.sbc.goods.api.provider.goodsaudit.GoodsAuditQueryProvider;
import com.wanmi.sbc.goods.api.request.goods.GoodsAddRequest;
import com.wanmi.sbc.goods.api.request.goods.GoodsModifyAllRequest;
import com.wanmi.sbc.goods.api.request.goods.GoodsModifyRequest;
import com.wanmi.sbc.goods.api.request.goodsaudit.GoodsAuditByIdRequest;
import com.wanmi.sbc.goods.api.request.goodsaudit.GoodsAuditModifyRequest;
import com.wanmi.sbc.goods.api.request.goodsaudit.GoodsAuditQueryRequest;
import com.wanmi.sbc.goods.api.request.info.GoodsInfoModifyRequest;
import com.wanmi.sbc.goods.api.response.goods.GoodsAddResponse;
import com.wanmi.sbc.goods.api.response.goods.GoodsByIdResponse;
import com.wanmi.sbc.goods.api.response.goods.GoodsModifyInfoResponse;
import com.wanmi.sbc.goods.api.response.goodsaudit.GoodsAuditModifyResponse;
import com.wanmi.sbc.goods.bean.enums.CheckStatus;
import com.wanmi.sbc.goods.bean.enums.GoodsSource;
import com.wanmi.sbc.goods.bean.vo.GoodsAuditVO;
import com.wanmi.sbc.goods.bean.vo.GoodsInfoVO;
import com.wanmi.sbc.setting.api.provider.AuditQueryProvider;
import com.wanmi.sbc.setting.api.request.GoodsSecondaryAuditRequest;
import com.wanmi.sbc.setting.bean.enums.ConfigType;
import com.wanmi.sbc.util.CommonUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;import java.util.Collections;
import java.util.List;
import java.util.Objects;import java.util.stream.Collectors;

/**
 * @description 管理端，商家消息具体发送业务服务，统一管理
 * @author malianfeng
 * @date 2022/7/12 14:59
 */
@Slf4j
@Service
public class StoreMessageBizService {

    @Autowired private CommonUtil commonUtil;

    @Autowired private StoreMessageService storeMessageService;

    @Autowired private EsStoreInformationQueryProvider esStoreInformationQueryProvider;

    @Autowired private AuditQueryProvider auditQueryProvider;

    @Autowired private SettlementQueryProvider settlementQueryProvider;

    @Autowired private GoodsAuditQueryProvider goodsAuditQueryProvider;

    @Autowired private StoreQueryProvider storeQueryProvider;

    /**
     * 根据商品来源，获取审核结果消息节点名称
     * @param goodsSource 商品来源
     * @return
     */
    private String getAuditResultNodeByGoodsSource(Integer goodsSource) {
        if (Objects.isNull(goodsSource)) {
            return null;
        }
        // 区分商家/供应商
        if (goodsSource.equals(GoodsSource.SELLER.toValue())) {
            return SupplierMessageNode.GOODS_AUDIT_RESULT.getCode();
        } else if (goodsSource.equals(GoodsSource.PROVIDER.toValue())) {
            return ProviderMessageNode.GOODS_AUDIT_RESULT.getCode();
        } else {
            return null;
        }
    }

    /**
     * 根据id查询二审记录
     * @param auditGoodsId 商品id
     * @return
     */
    private GoodsAuditVO queryAuditRecord(String auditGoodsId) {
        return goodsAuditQueryProvider.getById(
                GoodsAuditByIdRequest.builder().goodsId(auditGoodsId).build())
                .getContext().getGoodsAuditVO();
    }

    /**
     * 根据商品id查询当前生效的二审记录
     * @param oldGoodsId 商品id
     * @return
     */
    private List<GoodsAuditVO> queryAuditRecord(String oldGoodsId, ConfigType configType) {
        // 判断是否开启了二次商品审核开关
        boolean isAudit = auditQueryProvider.isBossGoodsSecondaryAudit(
                GoodsSecondaryAuditRequest.builder().configType(configType).build()).getContext().isAudit();
        if (isAudit && Objects.nonNull(oldGoodsId)) {
            return goodsAuditQueryProvider.listByCondition(GoodsAuditQueryRequest.builder()
                    .oldGoodsId(oldGoodsId)
                    .delFlag(DeleteFlag.NO.toValue())
                    .auditStatus(CheckStatus.WAIT_CHECK.toValue()).build()).getContext().getGoodsAuditVOList();
        }
        return Collections.emptyList();
    }

    /**
     * 根据店铺id查询es详情
     * @param storeId 店铺id
     * @return
     */
    private StoreVO queryEsStoreInfo(Long storeId) {
        List<StoreVO> storeVOList = esStoreInformationQueryProvider.queryStoreByStoreIds(StoreInfoQueryPageRequest.builder()
                .idList(Collections.singletonList(storeId)).build()).getContext().getStoreVOList();
        return storeVOList.stream().findFirst().orElse(null);
    }

    // ==================================== 处理平台的消息发送 ====================================

    /**
     * 新商家/供应商入驻待审核
     */
    public void handleForStoreAudit(BaseResponse<StoreAuditStateResponse> response, Long storeId) {
        try {
            if (CheckState.CHECK == response.getContext().getAuditState() && Objects.nonNull(storeId)) {
                StoreMessagePlatform messagePlatform = StoreMessagePlatform.fromValue(commonUtil.getOperator().getPlatform());
                // 区分商家/供应商
                BossMessageNode messageNode = messagePlatform == StoreMessagePlatform.SUPPLIER
                        ? BossMessageNode.SUPPLIER_WAIT_AUDIT
                        : BossMessageNode.PROVIDER_WAIT_AUDIT;
                StoreVO storeVO = this.queryEsStoreInfo(storeId);
                if (Objects.nonNull(storeVO)) {
                    storeMessageService.convertAndSend(
                            Constants.BOSS_DEFAULT_STORE_ID,
                            messageNode.getCode(),
                            Lists.newArrayList(storeVO.getCompanyCode()),
                            MutableMap.of("storeId", storeId));
                }
            }
        } catch (Exception e) {
            log.error("新商家/供应商入驻待审核，消息处理失败，{} {}", response, storeId, e);
        }
    }

    /**
     * 商家/供应商修改签约信息待审核
     */
    public void handleForStoreAudit(StoreInfoResponse storeInfoResponse) {
        try {
            StoreMessagePlatform messagePlatform = StoreMessagePlatform.fromValue(commonUtil.getOperator().getPlatform());
            // 区分商家/供应商
            BossMessageNode messageNode = messagePlatform == StoreMessagePlatform.SUPPLIER
                    ? BossMessageNode.SUPPLIER_WAIT_AUDIT
                    : BossMessageNode.PROVIDER_WAIT_AUDIT;
            storeMessageService.convertAndSend(
                    Constants.BOSS_DEFAULT_STORE_ID,
                    messageNode.getCode(),
                    Lists.newArrayList(storeInfoResponse.getSupplierCode()),
                    MutableMap.of("storeId", storeInfoResponse.getStoreId()));
        } catch (Exception e) {
            log.error("商家/供应商修改签约信息待审核，消息处理失败，{}", storeInfoResponse, e);
        }
    }

    /**
     * 商家新增商品
     */
    public void handleForGoodsAudit(CheckStatus checkStatus, GoodsAddRequest request, GoodsAddResponse response) {
        try {
            if (Objects.equals(CheckStatus.WAIT_CHECK, checkStatus)) {
                storeMessageService.convertAndSend(
                        Constants.BOSS_DEFAULT_STORE_ID,
                        BossMessageNode.SUPPLIER_GOODS_WAIT_AUDIT.getCode(),
                        Lists.newArrayList(request.getGoods().getGoodsNo()),
                        MutableMap.of("newGoodsId", response.getResult()));
            }
        } catch (Exception e) {
            log.error("商家新增商品，平台待审核，消息处理失败，{} {} {}", checkStatus, request, response, e);
        }
    }

    /**
     * 商家编辑商品触发待审核
     */
    public void handleForGoodsAudit(GoodsModifyAllRequest request, GoodsModifyInfoResponse response) {
        try {
            if (Objects.nonNull(response) && CollectionUtils.isNotEmpty(response.getNewGoodsInfo())) {
                GoodsInfoVO goodsInfoVO = response.getNewGoodsInfo().get(0);
                if (CheckStatus.WAIT_CHECK == goodsInfoVO.getAuditStatus() && Objects.nonNull(request.getGoods())) {
                    storeMessageService.convertAndSend(
                            Constants.BOSS_DEFAULT_STORE_ID,
                            BossMessageNode.SUPPLIER_GOODS_WAIT_AUDIT.getCode(),
                            Lists.newArrayList(request.getGoods().getGoodsNo()),
                            MutableMap.of(
                                    "secondaryFlag", true,
                                    "oldGoodsId", request.getGoods().getGoodsId(),
                                    "newGoodsId", goodsInfoVO.getGoodsId()
                            )
                    );
                }
            }
        } catch (Exception e) {
            log.error("商家编辑商品触发待审核，消息处理失败，{} {}", request, response, e);
        }
    }

    /**
     * 待审核列表，商家/供应商编辑商品触发待审核
     */
    public void handleForEditAuditGoods(GoodsInfoModifyRequest request, boolean isAudit) {
        try {
            if (isAudit) {
                // 二审开关已开启
                String auditGoodsId = request.getGoodsInfo().getGoodsId();
                GoodsAuditVO goodsAuditVO = this.queryAuditRecord(auditGoodsId);
                if (Objects.nonNull(goodsAuditVO)) {
                    // 未删除且处于待审核
                    if (DeleteFlag.NO == goodsAuditVO.getDelFlag() && CheckStatus.WAIT_CHECK == goodsAuditVO.getAuditStatus()) {
                        // 区分商家/供应商
                        StoreMessagePlatform messagePlatform = StoreMessagePlatform.fromValue(commonUtil.getOperator().getPlatform());
                        BossMessageNode messageNode = messagePlatform == StoreMessagePlatform.SUPPLIER
                                ? BossMessageNode.SUPPLIER_GOODS_WAIT_AUDIT
                                : BossMessageNode.PROVIDER_GOODS_WAIT_AUDIT;
                        storeMessageService.convertAndSend(
                                Constants.BOSS_DEFAULT_STORE_ID,
                                messageNode.getCode(),
                                Lists.newArrayList(goodsAuditVO.getGoodsNo()),
                                MutableMap.of(
                                        "secondaryFlag", true,
                                        "oldGoodsId", goodsAuditVO.getOldGoodsId(),
                                        "newGoodsId", auditGoodsId
                                )
                        );
                    }
                }
            }
        } catch (Exception e) {
            log.error("待审核列表，商家/供应商编辑商品触发待审核，消息处理失败，{} {}", request, isAudit, e);
        }
    }

    /**
     * 待审核列表，商家/供应商编辑商品触发待审核
     */
    public void handleForEditAuditGoods(GoodsAuditModifyRequest request, GoodsAuditModifyResponse response) {
        try {
            if (Objects.isNull(response) || CheckStatus.WAIT_CHECK == response.getCheckRequest().getAuditStatus()) {
                // 区分商家/供应商
                StoreMessagePlatform messagePlatform = StoreMessagePlatform.fromValue(commonUtil.getOperator().getPlatform());
                BossMessageNode messageNode = messagePlatform == StoreMessagePlatform.SUPPLIER
                        ? BossMessageNode.SUPPLIER_GOODS_WAIT_AUDIT
                        : BossMessageNode.PROVIDER_GOODS_WAIT_AUDIT;
                storeMessageService.convertAndSend(
                        Constants.BOSS_DEFAULT_STORE_ID,
                        messageNode.getCode(),
                        Lists.newArrayList(request.getGoodsAudit().getGoodsNo()),
                        MutableMap.of(
                                "secondaryFlag", true,
                                "oldGoodsId", request.getGoodsAudit().getOldGoodsId(),
                                "newGoodsId", request.getGoodsAudit().getGoodsId()
                        )
                );
            }
        } catch (Exception e) {
            log.error("待审核列表，商家/供应商编辑商品触发待审核，消息处理失败，{} {}", request, response, e);
        }
    }

    /**
     * 供应商编辑商品触发待审核
     */
    public void handleForProviderGoodsAudit(GoodsModifyRequest request) {
        try {
            String oldGoodsId = request.getGoods().getGoodsId();
            List<GoodsAuditVO> goodsAuditVOList = this.queryAuditRecord(oldGoodsId, ConfigType.PROVIDER_GOODS_SECONDARY_AUDIT);
            if (CollectionUtils.isNotEmpty(goodsAuditVOList)) {
                // 存在待审核记录，即触发了二审
                storeMessageService.convertAndSend(
                        Constants.BOSS_DEFAULT_STORE_ID,
                        BossMessageNode.PROVIDER_GOODS_WAIT_AUDIT.getCode(),
                        Lists.newArrayList(request.getGoods().getGoodsNo()),
                        MutableMap.of("secondaryFlag", true,
                                "oldGoodsId", oldGoodsId,
                                "newGoodsId", goodsAuditVOList.get(0).getGoodsId())
                );
            }
        } catch (Exception e) {
            log.error("供应商编辑商品触发待审核，消息处理失败，{}", request, e);
        }
    }

    /**
     * 供应商编辑商品触发待审核
     */
    public void handleForProviderGoodsAudit(boolean providerAudit, GoodsByIdResponse response) {
        try {
            if (providerAudit && Objects.nonNull(response)) {
                String oldGoodsId = response.getGoodsId();
                List<GoodsAuditVO> goodsAuditVOList = this.queryAuditRecord(oldGoodsId, ConfigType.PROVIDER_GOODS_SECONDARY_AUDIT);
                if (CollectionUtils.isNotEmpty(goodsAuditVOList)) {
                    // 存在待审核记录，即触发了二审
                    storeMessageService.convertAndSend(
                            Constants.BOSS_DEFAULT_STORE_ID,
                            BossMessageNode.PROVIDER_GOODS_WAIT_AUDIT.getCode(),
                            Lists.newArrayList(response.getGoodsNo()),
                            MutableMap.of("secondaryFlag", true,
                                    "oldGoodsId", oldGoodsId,
                                    "newGoodsId", goodsAuditVOList.get(0).getGoodsId())
                    );
                }
            }
        } catch (Exception e) {
            log.error("供应商编辑商品触发待审核，消息处理失败，{}", response, e);
        }
    }

    /**
     * 供应商新增商品待审核
     */
    public void handleForProviderGoodsAudit(GoodsAddRequest request, GoodsAddResponse response) {
        try {
            if (Objects.nonNull(response) && Objects.equals(CheckStatus.WAIT_CHECK, response.getAuditStatus())) {
                storeMessageService.convertAndSend(
                        Constants.BOSS_DEFAULT_STORE_ID,
                        BossMessageNode.PROVIDER_GOODS_WAIT_AUDIT.getCode(),
                        Lists.newArrayList(request.getGoods().getGoodsNo()),
                        MutableMap.of("newGoodsId", response.getResult()));
            }
        } catch (Exception e) {
            log.error("供应商新增商品待审核，消息处理失败，{}", response, e);
        }
    }

    /**
     * 商家的待退款订单提醒
     */
    public void handleForTradeWaitRefund(BaseResponse response, String rid) {
        try {
            if (Objects.nonNull(response) && CommonErrorCodeEnum.K000000.getCode().equals(response.getCode())) {
                storeMessageService.convertAndSend(
                        Constants.BOSS_DEFAULT_STORE_ID,
                        BossMessageNode.TRADE_WAIT_REFUND.getCode(),
                        Lists.newArrayList(rid),
                        MutableMap.of("rid", rid));
            }
        } catch (Exception e) {
            log.error("商家的待退款订单提醒, 消息处理失败，{} {}", response, rid, e);
        }
    }

    /**
     * 商家/供应商待结算单生成提醒
     */
    public void handleForSettlementProduce(String nodeCode, List<SettlementViewVO> settlementViewList) {
        try {
            for (SettlementViewVO settlementViewVO : settlementViewList) {
                storeMessageService.convertAndSend(
                        Constants.BOSS_DEFAULT_STORE_ID,
                        nodeCode,
                        Lists.newArrayList(settlementViewVO.getSettleCode()),
                        MutableMap.of("settlementCode", settlementViewVO.getSettleCode()));
            }
        } catch (Exception e) {
            log.error("商家/供应商待结算单生成提醒, 消息处理失败，{} {}", nodeCode, settlementViewList, e);
        }
    }



    // ==================================== 处理商家/供应商的消息发送 ====================================

    /**
     * 商品审核通过提醒
     */
    public void handleForGoodsAuditCheckedResult(GoodsByIdResponse goods) {
        try {
            String nodeCode = this.getAuditResultNodeByGoodsSource(goods.getGoodsSource());
            if (Objects.nonNull(nodeCode)) {
                storeMessageService.convertAndSend(
                        goods.getStoreId(),
                        nodeCode,
                        Lists.newArrayList(goods.getGoodsName(), goods.getGoodsNo(), "成功"),
                        MutableMap.of("oldGoodsId", goods.getGoodsId())
                );
            }
        } catch (Exception e) {
            log.error("商品审核通过提醒，消息处理失败，{}", goods, e);
        }
    }

    /**
     * 商品审核驳回提醒
     */
    public void handleForGoodsAuditNotPassResult(GoodsAuditVO goodsAuditVO) {
        try {
            String nodeCode = this.getAuditResultNodeByGoodsSource(goodsAuditVO.getGoodsSource());
            if (Objects.nonNull(nodeCode)) {
                storeMessageService.convertAndSend(
                        goodsAuditVO.getStoreId(),
                        nodeCode,
                        Lists.newArrayList(goodsAuditVO.getGoodsName(), goodsAuditVO.getGoodsNo(), "失败"),
                        MutableMap.of(
                                "oldGoodsId", goodsAuditVO.getOldGoodsId(),
                                "newGoodsId", goodsAuditVO.getGoodsId()));
            }
        } catch (Exception e) {
            log.error("商品审核驳回提醒，消息处理失败，{}", goodsAuditVO, e);
        }
    }

    /**
     * 商家/供应商待结算单结算提醒
     */
    public void handleForSettlementSettled(String nodeCode, SettlementBatchModifyStatusRequest request) {
        try {
            if (CollectionUtils.isNotEmpty(request.getSettleIdList()) && SettleStatus.SETTLED.equals(request.getStatus())) {
                SettlementPageRequest settlementPageRequest = new SettlementPageRequest();
                settlementPageRequest.setIdList(request.getSettleIdList());
                settlementPageRequest.setPageSize(request.getSettleIdList().size());
                List<SettlementViewVO> settlementViews = settlementQueryProvider.page(settlementPageRequest).getContext().getSettlementViewVOPage().getContent();
                for (SettlementViewVO settlementView : settlementViews) {
                    storeMessageService.convertAndSend(
                            settlementView.getStoreId(),
                            nodeCode,
                            Lists.newArrayList(settlementView.getSettlementCode()),
                            MutableMap.of("settlementCode", settlementView.getSettlementCode()));
                }
            }
        } catch (Exception e) {
            log.error("商家/供应商待结算单结算提醒，消息处理失败，{} {}", nodeCode, request, e);
        }
    }

    /**
     * 平台/商家/供应商拉卡拉待结算单生成提醒
     */
    public void handleForLakalaBossSettlementProduce(String nodeCode, List<LakalaSettlementAddResponse> settlementViewList) {
        try {
            for (LakalaSettlementAddResponse settlementViewVO : settlementViewList) {
                if (StoreType.SUPPLIER.equals(settlementViewVO.getStoreType())) {
                    String settlementCode = String.format("JS%07d", settlementViewVO.getSettleId());
                    storeMessageService.convertAndSend(
                            Constants.BOSS_DEFAULT_STORE_ID,
                            nodeCode,
                            Lists.newArrayList(settlementCode),
                            MutableMap.of(
                                    "settleId", settlementViewVO.getSettleId(),
                                    "settleUuid", settlementViewVO.getSettleUuid()
                            )
                    );
                }
            }
        } catch (Exception e) {
            log.error("商家/供应商拉卡拉待结算单生成提醒, 消息处理失败，{} {}", nodeCode, settlementViewList, e);
        }
    }

    /**
     * 商家/供应商拉卡拉待结算单生成提醒
     */
    public void handleForLakalaSupplierSettlementProduce(List<LakalaSettlementAddResponse> settlementViewList) {
        try {
            // 结算单中的storeId集合
            List<Long> storeIds = settlementViewList.stream().map(LakalaSettlementAddResponse::getStoreId).distinct().collect(Collectors.toList());
            // 根据storeIds查询storeList
            List<StoreVO> storeVOList = storeQueryProvider.listByIds(ListStoreByIdsRequest.builder().storeIds(storeIds).build()).getContext().getStoreVOList();
            // 判断出店铺是商家店铺还是供应商店铺
            List<Long> supplierStoreIds = new ArrayList<>();
            List<Long> providerStoreIds = new ArrayList<>();
            if (org.apache.commons.collections4.CollectionUtils.isNotEmpty(storeVOList)){
                // 拉卡拉商家结算店铺ID
                supplierStoreIds = storeVOList.stream().filter(storeVO -> storeVO.getStoreType().equals(StoreType.SUPPLIER)).map(StoreVO::getStoreId).collect(Collectors.toList());
                // 拉卡拉供应商结算店铺ID
                providerStoreIds = storeVOList.stream().filter(storeVO -> storeVO.getStoreType().equals(StoreType.PROVIDER)).map(StoreVO::getStoreId).collect(Collectors.toList());
            }
            for (LakalaSettlementAddResponse settlementViewVO : settlementViewList) {
                String settlementCode = String.format("JS%07d", settlementViewVO.getSettleId());
                if (org.apache.commons.collections4.CollectionUtils.isNotEmpty(supplierStoreIds) && supplierStoreIds.contains(settlementViewVO.getStoreId())) {
                    storeMessageService.convertAndSend(
                            settlementViewVO.getStoreId(),
                            SupplierMessageNode.LAKALA_SETTLEMENT_SETTLE.getCode(),
                            Lists.newArrayList(settlementCode),
                            MutableMap.of(
                                    "settleId", settlementViewVO.getSettleId(),
                                    "settleUuid", settlementViewVO.getSettleUuid()
                            )
                    );
                } else if (org.apache.commons.collections4.CollectionUtils.isNotEmpty(providerStoreIds) && providerStoreIds.contains(settlementViewVO.getStoreId())) {
                    storeMessageService.convertAndSend(
                            settlementViewVO.getStoreId(),
                            ProviderMessageNode.LAKALA_SETTLEMENT_SETTLE.getCode(),
                            Lists.newArrayList(settlementCode),
                            MutableMap.of(
                                    "settleId", settlementViewVO.getSettleId(),
                                    "settleUuid", settlementViewVO.getSettleUuid()
                            )
                    );
                }
            }
        } catch (Exception e) {
            log.error("商家/供应商拉卡拉待结算单生成提醒, 消息处理失败，{}", settlementViewList, e);
        }
    }

}