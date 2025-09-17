package com.wanmi.sbc.goods.service;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.constant.RedisKeyConstant;
import com.wanmi.sbc.common.enums.*;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.redis.util.RedisUtil;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.common.util.OsUtil;
import com.wanmi.sbc.elastic.api.provider.goods.EsGoodsInfoElasticProvider;
import com.wanmi.sbc.elastic.api.provider.spu.EsSpuQueryProvider;
import com.wanmi.sbc.elastic.api.provider.standard.EsStandardProvider;
import com.wanmi.sbc.elastic.api.request.goods.EsGoodsDeleteByIdsRequest;
import com.wanmi.sbc.elastic.api.request.goods.EsGoodsInfoRequest;
import com.wanmi.sbc.elastic.api.request.goods.EsGoodsInitProviderGoodsInfoRequest;
import com.wanmi.sbc.elastic.api.request.spu.EsSpuPageRequest;
import com.wanmi.sbc.elastic.api.request.standard.EsStandardDeleteByIdsRequest;
import com.wanmi.sbc.elastic.api.request.standard.EsStandardInitRequest;
import com.wanmi.sbc.elastic.api.response.spu.EsSpuPageResponse;
import com.wanmi.sbc.goods.api.provider.buycyclegoods.BuyCycleGoodsProvider;
import com.wanmi.sbc.goods.api.provider.buycyclegoods.BuyCycleGoodsQueryProvider;
import com.wanmi.sbc.goods.api.provider.freight.FreightTemplateGoodsQueryProvider;
import com.wanmi.sbc.goods.api.provider.goods.GoodsProvider;
import com.wanmi.sbc.goods.api.provider.goods.GoodsQueryProvider;
import com.wanmi.sbc.goods.api.provider.goodsaudit.GoodsAuditQueryProvider;
import com.wanmi.sbc.goods.api.provider.goodscommission.GoodsCommissionPriceConfigProvider;
import com.wanmi.sbc.goods.api.provider.goodsproperty.GoodsPropertyQueryProvider;
import com.wanmi.sbc.goods.api.provider.info.GoodsInfoQueryProvider;
import com.wanmi.sbc.goods.api.provider.storecate.StoreCateQueryProvider;
import com.wanmi.sbc.goods.api.request.buycyclegoods.BuyCycleGoodsListRequest;
import com.wanmi.sbc.goods.api.request.buycyclegoods.BuyCycleGoodsModifyStateRequest;
import com.wanmi.sbc.goods.api.request.freight.FreightTemplateGoodsExistsByIdRequest;
import com.wanmi.sbc.goods.api.request.goods.*;
import com.wanmi.sbc.goods.api.request.goodsaudit.GoodsAuditByIdRequest;
import com.wanmi.sbc.goods.api.request.goodsaudit.GoodsAuditQueryRequest;
import com.wanmi.sbc.goods.api.request.goodscommission.GoodsCommissionPriceConfigQueryRequest;
import com.wanmi.sbc.goods.api.request.goodsproperty.GoodsPropertyByGoodsIdRequest;
import com.wanmi.sbc.goods.api.request.info.GoodsInfoListByIdsRequest;
import com.wanmi.sbc.goods.api.request.info.GoodsInfoModifyRequest;
import com.wanmi.sbc.goods.api.request.info.GoodsInfoPartColsByIdsRequest;
import com.wanmi.sbc.goods.api.request.info.GoodsInfoPopulateStatusRequest;
import com.wanmi.sbc.goods.api.request.storecate.StoreCateListByGoodsRequest;
import com.wanmi.sbc.goods.api.request.storecate.StoreCateListByIdsRequest;
import com.wanmi.sbc.goods.api.response.goods.*;
import com.wanmi.sbc.goods.api.response.goodscommission.GoodsCommissionPriceConfigQueryResponse;
import com.wanmi.sbc.goods.api.response.goodsproperty.GoodsPropertyListForGoodsResponse;
import com.wanmi.sbc.goods.api.response.storecate.StoreCateListByGoodsResponse;
import com.wanmi.sbc.goods.bean.dto.GoodsDTO;
import com.wanmi.sbc.goods.bean.dto.GoodsInfoDTO;
import com.wanmi.sbc.goods.bean.dto.GoodsPropertyDetailRelDTO;
import com.wanmi.sbc.goods.bean.enums.*;
import com.wanmi.sbc.goods.bean.vo.*;
import com.wanmi.sbc.marketing.api.provider.appointmentsale.AppointmentSaleQueryProvider;
import com.wanmi.sbc.marketing.api.provider.bookingsale.BookingSaleQueryProvider;
import com.wanmi.sbc.marketing.api.provider.electroniccoupon.ElectronicCardQueryProvider;
import com.wanmi.sbc.marketing.api.provider.electroniccoupon.ElectronicCouponQueryProvider;
import com.wanmi.sbc.marketing.api.request.appointmentsale.AppointmentSaleByGoodsIdRequest;
import com.wanmi.sbc.marketing.api.request.bookingsale.BookingSaleByGoodsIdRequest;
import com.wanmi.sbc.marketing.api.request.electroniccoupon.ElectronicCardInvalidRequest;
import com.wanmi.sbc.marketing.api.request.electroniccoupon.ElectronicCouponByIdRequest;
import com.wanmi.sbc.marketing.api.request.electroniccoupon.ElectronicCouponListRequest;
import com.wanmi.sbc.marketing.api.response.appointmentsale.AppointmentSaleNotEndResponse;
import com.wanmi.sbc.marketing.api.response.bookingsale.BookingSaleNotEndResponse;
import com.wanmi.sbc.marketing.bean.vo.ElectronicCouponVO;
import com.wanmi.sbc.message.service.StoreMessageBizService;
import com.wanmi.sbc.order.bean.enums.OrderErrorCodeEnum;
import com.wanmi.sbc.setting.api.provider.operatedatalog.OperateDataLogQueryProvider;
import com.wanmi.sbc.setting.api.request.operatedatalog.OperateDataLogListRequest;
import com.wanmi.sbc.setting.api.response.operatedatalog.OperateDataLogListResponse;
import com.wanmi.sbc.setting.bean.vo.OperateDataLogVO;
import com.wanmi.sbc.util.CommonUtil;
import com.wanmi.sbc.util.OperateLogMQUtil;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author 黄昭
 * @className GoodsBaseService
 * @description TODO
 * @date 2021/12/31 10:36
 **/
@Slf4j
@Service
public class GoodsBaseService {

    @Autowired
    private GoodsProvider goodsProvider;

    @Autowired
    private GoodsQueryProvider goodsQueryProvider;

    @Autowired
    private StoreCateQueryProvider storeCateQueryProvider;

    @Autowired
    private EsGoodsInfoElasticProvider esGoodsInfoElasticProvider;

    @Autowired
    private CommonUtil commonUtil;

    @Autowired
    private OsUtil osUtil;

    @Autowired
    private FreightTemplateGoodsQueryProvider freightTemplateGoodsQueryProvider;

    @Autowired
    private OperateLogMQUtil operateLogMQUtil;

    @Autowired
    private OperateDataLogQueryProvider operateDataLogQueryProvider;

    @Autowired
    private AppointmentSaleQueryProvider appointmentSaleQueryProvider;

    @Autowired
    private BookingSaleQueryProvider bookingSaleQueryProvider;

    @Autowired
    private EsStandardProvider esStandardProvider;

    @Autowired
    private RedisUtil redisService;

    @Autowired
    private GoodsCommissionPriceConfigProvider goodsCommissionPriceConfigProvider;

    @Autowired
    private GoodsAuditQueryProvider goodsAuditQueryProvider;

    @Autowired
    private ElectronicCouponQueryProvider electronicCouponQueryProvider;

    @Autowired
    private EsSpuQueryProvider esSpuQueryProvider;

    @Autowired
    private StoreMessageBizService storeMessageBizService;

    /**
     * 默认全部支持的购买方式
     */
    private static final String GOODS_BUY_TYPES = "0,1";

    @Autowired
    private GoodsPropertyQueryProvider goodsPropertyQueryProvider;

    @Autowired
    private ElectronicCardQueryProvider electronicCardQueryProvider;

    @Autowired
    private GoodsInfoQueryProvider goodsInfoQueryProvider;

    @Autowired
    private BuyCycleGoodsQueryProvider buyCycleGoodsQueryProvider;

    @Autowired
    private BuyCycleGoodsProvider buyCycleGoodsProvider;

    /**
     * 获取商品详情信息
     *
     * @param goodsId 商品编号
     * @return 商品详情
     */
    public BaseResponse<GoodsViewByIdResponse> info(@PathVariable String goodsId) {
        Boolean isEditProviderGoods = false; //控制显示是否是编辑供应商商品
        GoodsByIdResponse goodsByIdResponse = goodsQueryProvider.getById(GoodsByIdRequest.builder().goodsId(goodsId).build()).getContext();
        if (commonUtil.getOperator().getPlatform().equals(Platform.SUPPLIER)
                && StringUtils.isNotBlank(goodsByIdResponse.getProviderGoodsId())) {
            isEditProviderGoods = true;
        }
        GoodsViewByIdRequest request = new GoodsViewByIdRequest();
        request.setGoodsId(goodsId);
        request.setShowLabelFlag(Boolean.TRUE);
        request.setIsEditProviderGoods(isEditProviderGoods);
        GoodsViewByIdResponse response = goodsQueryProvider.getViewById(request).getContext();

        //商家端代销功能展示
        if (commonUtil.getOperator().getPlatform().equals(Platform.SUPPLIER)
                && StringUtils.isNotBlank(response.getGoods().getProviderGoodsId())) {
            //商家当前的list
            List<GoodsInfoVO> goodsInfos = response.getGoodsInfos();
            GoodsViewByIdRequest providertId = new GoodsViewByIdRequest();
            //根据当前商品的provideId获取供应商goodsIndo
            providertId.setGoodsId(response.getGoods().getProviderGoodsId());
            providertId.setIsEditProviderGoods(isEditProviderGoods);
            GoodsViewByIdResponse providerResponse = goodsQueryProvider.getViewById(providertId).getContext();
            List<GoodsInfoVO> goodsInfosProvider = providerResponse.getGoodsInfos();

            //1.需要删除的list 这条信息在两边都是delflag=1
            List<GoodsInfoVO> collectDel = goodsInfos.stream().filter(goodsInfoVO -> goodsInfosProvider.stream()
                    .filter(goodsInfosProviderVO -> goodsInfosProviderVO.getDelFlag().toValue() == 1)
                    .map(GoodsInfoVO::getGoodsInfoId)
                    .collect(Collectors.toList()).contains(goodsInfoVO.getProviderGoodsInfoId()))
                    .filter(goodsInfoVO -> goodsInfoVO.getDelFlag().toValue() == 1)
                    .collect(Collectors.toList());

            //2. 商家删除供应商已经删除且商家不代销的
            goodsInfos.removeAll(collectDel);

            //2.去掉剩余的仅供应商有且删除的
            List<GoodsInfoVO> goodInfoNoDel = goodsInfosProvider.stream().filter(goodsInfoVO -> goodsInfoVO.getDelFlag().toValue() == 0)
                    .collect(Collectors.toList());

            //商家goodsInfos 和供应商 goodInfoNoDel数据差集  即展示0 0， 0，1，0 null的数据
            List<GoodsInfoVO> newAddSkus = goodInfoNoDel.stream()
                    .filter(provider -> !goodsInfos.stream().map(GoodsInfoVO::getProviderGoodsInfoId)
                            .collect(Collectors.toList()).contains(provider.getGoodsInfoId()))
                    .collect(Collectors.toList());

            if (CollectionUtils.isNotEmpty(newAddSkus)) {
                newAddSkus.forEach(goodsInfoVO -> {
                    goodsInfoVO.setIsNewAdd(NewAdd.YES);
                    //将供应商的新sku状态改为不代销，可在页面显示
                    goodsInfoVO.setDelFlag(DeleteFlag.YES);
                    goodsInfoVO.setProviderQuickOrderNo(goodsInfoVO.getQuickOrderNo());
                    goodsInfoVO.setQuickOrderNo(null);
                });
            }
            //商家和供应商商品list合并
            goodsInfos.addAll(newAddSkus);

            //获取sku的加价比例
            GoodsCommissionPriceConfigQueryRequest goodsCommissionPriceConfigQueryRequest = new GoodsCommissionPriceConfigQueryRequest();
            goodsCommissionPriceConfigQueryRequest.setTargetIdList(goodsInfos.stream().map(GoodsInfoVO::getGoodsInfoId).collect(Collectors.toList()));
            goodsCommissionPriceConfigQueryRequest.setTargetType(CommissionPriceTargetType.SKU);
            goodsCommissionPriceConfigQueryRequest.setUserId(commonUtil.getOperatorId());
            goodsCommissionPriceConfigQueryRequest.setBaseStoreId(commonUtil.getStoreId());
            GoodsCommissionPriceConfigQueryResponse context = goodsCommissionPriceConfigProvider.query(goodsCommissionPriceConfigQueryRequest).getContext();
            if (CollectionUtils.isNotEmpty(context.getCommissionPriceConfigVOList())) {
                Map<String, GoodsCommissionPriceConfigVO> priceConfigMap = context.getCommissionPriceConfigVOList().stream().collect(Collectors.toMap(GoodsCommissionPriceConfigVO::getTargetId, g -> g));
                goodsInfos.forEach(goodsInfoVO -> {
                    if (priceConfigMap.containsKey(goodsInfoVO.getGoodsInfoId())) {
                        goodsInfoVO.setAddRate(priceConfigMap.get(goodsInfoVO.getGoodsInfoId()).getAddRate());
                    }
                });
            }
            response.setGoodsInfos(goodsInfos);
        } else {

        }

        //获取商品店铺分类
        if (osUtil.isS2b()) {
            StoreCateListByGoodsRequest storeCateListByGoodsRequest =
                    new StoreCateListByGoodsRequest(Collections.singletonList(goodsId));
            BaseResponse<StoreCateListByGoodsResponse> baseResponse =
                    storeCateQueryProvider.listByGoods(storeCateListByGoodsRequest);
            StoreCateListByGoodsResponse storeCateListByGoodsResponse = baseResponse.getContext();
            if (Objects.nonNull(storeCateListByGoodsResponse)) {
                List<StoreCateGoodsRelaVO> storeCateGoodsRelaVOList =
                        storeCateListByGoodsResponse.getStoreCateGoodsRelaVOList();
                response.getGoods().setStoreCateIds(storeCateGoodsRelaVOList.stream()
                        .filter(rela -> rela.getStoreCateId() != null)
                        .map(StoreCateGoodsRelaVO::getStoreCateId)
                        .collect(Collectors.toList()));
                if (CollectionUtils.isNotEmpty(response.getGoods().getStoreCateIds())){
                    StoreCateListByIdsRequest storeCateListByIdsRequest = new StoreCateListByIdsRequest();
                    List<Long> storeCateIds = response.getGoods().getStoreCateIds();
                    storeCateListByIdsRequest.setCateIds(storeCateIds);
                    List<Long> parentIds = storeCateQueryProvider
                            .listByIds(storeCateListByIdsRequest)
                            .getContext()
                            .getStoreCateVOList()
                            .stream()
                            .map(StoreCateVO::getCateParentId)
                            .collect(Collectors.toList());
                    response.getGoods().setStoreCateParentIds(parentIds);
                }
            }

        }
        OperateDataLogListResponse operateDataLogListResponse =
                operateDataLogQueryProvider.list(OperateDataLogListRequest.builder().operateId(goodsId).delFlag(DeleteFlag.NO).build()).getContext();
        List<OperateDataLogVO> operateDataLogList = new ArrayList<OperateDataLogVO>();
        if (operateDataLogListResponse != null && CollectionUtils.isNotEmpty(operateDataLogListResponse.getOperateDataLogVOList())) {
            operateDataLogList = operateDataLogListResponse.getOperateDataLogVOList();
        }
        response.setOperateDataLogVOList(operateDataLogList);
        // 解析电子卡券商品关联的卡券名称
        List<Long> electronicCouponsIds = response.getGoodsInfos().stream().map(GoodsInfoVO::getElectronicCouponsId).distinct().filter(Objects::nonNull).collect(Collectors.toList());
        if (CollectionUtils.isNotEmpty(electronicCouponsIds)){
            // 根据卡券ID集合查询卡券列表
            List<ElectronicCouponVO> electronicCouponVOList = electronicCouponQueryProvider.list(ElectronicCouponListRequest.builder().idList(electronicCouponsIds).build()).getContext().getElectronicCouponVOList();
            if (CollectionUtils.isNotEmpty(electronicCouponVOList)){
                Map<Long, String> electricCouponMap = electronicCouponVOList.stream()
                        .collect(Collectors.toMap(ElectronicCouponVO::getId, ElectronicCouponVO::getCouponName));
                response.getGoodsInfos().forEach(v -> v.setElectronicCouponsName(electricCouponMap.get(v.getElectronicCouponsId())));
            }
        }
        return BaseResponse.success(response);
    }

    /**
     * 编辑商品
     */
    public BaseResponse edit(@RequestBody @Valid GoodsModifyRequest request) {
        log.info("商品编辑开始supplier");
        long startTime = System.currentTimeMillis();
        Long fId = request.getGoods().getFreightTempId();
        request.setBaseStoreId(commonUtil.getStoreId());
        if (request.getGoods() == null || CollectionUtils.isEmpty(request.getGoodsInfos()) || request
                .getGoods().getGoodsId() == null || Objects.isNull(fId)) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }

        // 添加默认值, 适应云掌柜编辑商品没有设置购买方式, 导致前台不展示购买方式问题
        if (StringUtils.isBlank(request.getGoods().getGoodsBuyTypes())) {
            request.getGoods().setGoodsBuyTypes(GOODS_BUY_TYPES);
        }

        //校验商品是否正在参加预约预售活动
        GoodsViewByIdResponse oldData = checkSale(request.getGoods().getGoodsId(), request.getGoods().getSaleType());

        //校验商品是否有待审核记录
        List<GoodsAuditVO> goodsAuditVOList = goodsAuditQueryProvider
                .getWaitCheckGoodsAudit(GoodsAuditQueryRequest.builder()
                        .goodsIdList(Collections.singletonList(request.getGoods().getGoodsId()))
                        .build())
                .getContext()
                .getGoodsAuditVOList();
        if (CollectionUtils.isNotEmpty(goodsAuditVOList)){
            throw new SbcRuntimeException(GoodsErrorCodeEnum.K030148);
        }

        //判断运费模板是否存在
        freightTemplateGoodsQueryProvider.existsById(
                FreightTemplateGoodsExistsByIdRequest.builder().freightTempId(fId).build());

        //获取商品店铺分类
        if (osUtil.isS2b()) {
            StoreCateListByGoodsRequest storeCateListByGoodsRequest =
                    new StoreCateListByGoodsRequest(Collections.singletonList(request.getGoods().getGoodsId()));
            BaseResponse<StoreCateListByGoodsResponse> baseResponse =
                    storeCateQueryProvider.listByGoods(storeCateListByGoodsRequest);
            StoreCateListByGoodsResponse storeCateListByGoodsResponse = baseResponse.getContext();
            if (Objects.nonNull(storeCateListByGoodsResponse)) {
                List<StoreCateGoodsRelaVO> storeCateGoodsRelaVOList =
                        storeCateListByGoodsResponse.getStoreCateGoodsRelaVOList();
                oldData.getGoods().setStoreCateIds(storeCateGoodsRelaVOList.stream()
                        .filter(rela -> rela.getStoreCateId() != null)
                        .map(StoreCateGoodsRelaVO::getStoreCateId)
                        .collect(Collectors.toList()));
            }
        }

        GoodsModifyResponse response = goodsProvider.modify(request).getContext();

        if(CollectionUtils.isNotEmpty(response.getStandardIds()) || Objects.nonNull(response.getReturnMap())) {
            //更新redis商品基本数据
            String goodsDetailInfo =
                    redisService.getString(RedisKeyConstant.GOODS_DETAIL_CACHE + request.getGoods().getGoodsId());
            if (StringUtils.isNotBlank(goodsDetailInfo)) {
                redisService.delete(RedisKeyConstant.GOODS_DETAIL_CACHE + request.getGoods().getGoodsId());
            }

            //刷新商品库
            if (CollectionUtils.isNotEmpty(response.getStandardIds())) {
                esStandardProvider.init(EsStandardInitRequest.builder().goodsIds(response.getStandardIds()).build());
            }
            GoodsModifyInfoResponse returnMap = response.getReturnMap();
            if (Objects.nonNull(returnMap) && CollectionUtils.isNotEmpty(returnMap.getDelInfoIds())) {
                esGoodsInfoElasticProvider.delete(EsGoodsDeleteByIdsRequest.builder()
                        .deleteIds(returnMap.getDelInfoIds()).build());
            }
            //刷新es商品
            esGoodsInfoElasticProvider.deleteByGoods(EsGoodsDeleteByIdsRequest.builder().
                    deleteIds(Collections.singletonList(request.getGoods().getGoodsId())).build());
            esGoodsInfoElasticProvider.initEsGoodsInfo(EsGoodsInfoRequest.builder().goodsId(request.getGoods().getGoodsId()).build());
        }

        //ares埋点-商品-后台修改商品sku,迁移至goods微服务下
        operateLogMQUtil.convertAndSend("商品", "编辑商品",
                "编辑商品：SPU编码" + request.getGoods().getGoodsNo());
        log.info("商品编辑结束supplier->花费{}毫秒", (System.currentTimeMillis() - startTime));
        return BaseResponse.SUCCESSFUL();
    }

    /**
     * 审核商品
     * @param checkRequest
     */
    public void check(@Valid @RequestBody GoodsCheckRequest checkRequest) {

        List<GoodsAuditVO> goodsAuditVOList = goodsAuditQueryProvider
                .getByIds(GoodsAuditQueryRequest.builder().goodsIdList(checkRequest.getGoodsIds()).build())
                .getContext()
                .getGoodsAuditVOList();

        if(CheckStatus.CHECKED.equals(checkRequest.getAuditStatus())) {
            List<String> oldGoodsIds = goodsAuditVOList.stream()
                    .map(GoodsAuditVO::getOldGoodsId)
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
            if (CollectionUtils.isNotEmpty(oldGoodsIds)) {
                List<GoodsVO> goodsVOList = goodsQueryProvider
                        .listByIds(GoodsListByIdsRequest.builder().goodsIds(oldGoodsIds).build())
                        .getContext()
                        .getGoodsVOList();

                for (GoodsVO goodsVO : goodsVOList) {
                    GoodsAuditVO goodsAuditVO = goodsAuditVOList
                            .stream()
                            .filter(v -> Objects.equals(goodsVO.getGoodsId(), v.getOldGoodsId()))
                            .findFirst().orElse(null);
                    if (Objects.isNull(goodsAuditVO)) {
                        throw new SbcRuntimeException(GoodsErrorCodeEnum.K030035);
                    }
                    //校验审核商品是否正在参加预约预售活动
                    checkSale(goodsVO.getGoodsId(), goodsAuditVO.getSaleType());
                }
            }
        }

        checkRequest.setChecker(commonUtil.getAccountName());
        GoodsCheckResponse response = goodsProvider.checkGoods(checkRequest).getContext();
        checkRequest = response.getGoodsCheckRequest();

        //操作日志记录
        GoodsByIdRequest goodsByIdRequest = new GoodsByIdRequest();
        goodsByIdRequest.setGoodsId(checkRequest.getGoodsIds().get(0));
        GoodsByIdResponse goods = goodsQueryProvider.getById(goodsByIdRequest).getContext();

        //初始化标品库ES
        if(CollectionUtils.isNotEmpty(response.getStandardIds())){
            esStandardProvider.init(EsStandardInitRequest.builder().goodsIds(response.getStandardIds()).build());
        }

        //删除标品库ES
        if(CollectionUtils.isNotEmpty(response.getDeleteStandardIds())){
            esStandardProvider.deleteByIds(EsStandardDeleteByIdsRequest.builder().goodsIds(response.getDeleteStandardIds()).build());
        }

        //初始化商家商品ES
        if (Objects.nonNull(goods)) {
            if (Integer.valueOf(GoodsSource.SELLER.toValue()).equals(goods.getGoodsSource())) {
                //更新ES
                esGoodsInfoElasticProvider.deleteByGoods(EsGoodsDeleteByIdsRequest.builder().deleteIds(checkRequest.getGoodsIds()).build());
                esGoodsInfoElasticProvider.initEsGoodsInfo(EsGoodsInfoRequest.builder().
                        goodsIds(checkRequest.getGoodsIds()).build());
            } else { //初始化供应商品ES
                esGoodsInfoElasticProvider.deleteByGoods(EsGoodsDeleteByIdsRequest.builder().deleteIds(checkRequest.getGoodsIds()).build());
                esGoodsInfoElasticProvider.initProviderEsGoodsInfo(EsGoodsInitProviderGoodsInfoRequest.builder().
                        providerGoodsIds(checkRequest.getGoodsIds()).build());
            }
            if (checkRequest.getAuditStatus() == CheckStatus.CHECKED) {
                operateLogMQUtil.convertAndSend("商品", "审核商品",
                        "审核商品：SPU编码" + goods.getGoodsNo());
                // ============= 处理商家/供应商的消息发送：商品审核结果提醒 START =============
                storeMessageBizService.handleForGoodsAuditCheckedResult(goods);
                // ============= 处理商家/供应商的消息发送：商品审核结果提醒 START =============
            }
        }

        if(checkRequest.getAuditStatus() == CheckStatus.CHECKED) {
            List<GoodsAuditVO> buyCycle = goodsAuditVOList
                    .stream()
                    .filter(s -> Objects.equals(SaleType.WHOLESALE, SaleType.fromValue(s.getSaleType()))
                            && StringUtils.isNotBlank(s.getOldGoodsId()))
                    .toList();
            if (CollectionUtils.isNotEmpty(buyCycle)) {
                BuyCycleGoodsListRequest listRequest = new BuyCycleGoodsListRequest();
                listRequest.setDelFlag(DeleteFlag.NO);
                listRequest.setGoodsIdList(buyCycle.stream().map(GoodsAuditVO::getOldGoodsId).toList());
                List<BuyCycleGoodsVO> buyCycleGoodsVO = buyCycleGoodsQueryProvider.list(listRequest).getContext().getBuyCycleGoodsVOList();
                if (CollectionUtils.isNotEmpty(buyCycleGoodsVO)) {
                    buyCycleGoodsVO.forEach(b -> {
                        buyCycleGoodsProvider.modifyState(BuyCycleGoodsModifyStateRequest.builder()
                                .id(b.getId())
                                .cycleState(Constants.yes)
                                .build());
                    });
                }
            }
        }

        if (checkRequest.getAuditStatus() == CheckStatus.NOT_PASS) {
            GoodsAuditVO goodsAuditVO = goodsAuditQueryProvider.getById(GoodsAuditByIdRequest.builder().goodsId(checkRequest.getGoodsIds().get(0)).build()).getContext().getGoodsAuditVO();
            if (Objects.nonNull(goodsAuditVO)){
                operateLogMQUtil.convertAndSend("商品", "驳回商品",
                        "驳回商品：SPU编码" + goodsAuditVO.getGoodsNo());
                // ============= 处理商家/供应商的消息发送：商品审核结果提醒 START =============
                storeMessageBizService.handleForGoodsAuditNotPassResult(goodsAuditVO);
                // ============= 处理商家/供应商的消息发送：商品审核结果提醒 START =============
            }
        }
    }

    private GoodsViewByIdResponse checkSale(String goodsId, Integer saleType) {

        GoodsViewByIdRequest goodsViewByIdRequest = new GoodsViewByIdRequest();
        goodsViewByIdRequest.setGoodsId(goodsId);
        GoodsViewByIdResponse oldData = goodsQueryProvider.getViewById(goodsViewByIdRequest).getContext();

        Integer oldSaleType = oldData.getGoods().getSaleType();

        // 参与预约活动的商品不可修改
        AppointmentSaleNotEndResponse appointmentSaleNotEndResponse =
                appointmentSaleQueryProvider.getNotEndActivity(AppointmentSaleByGoodsIdRequest.builder().goodsId(goodsId).build()).getContext();
        if (Objects.nonNull(appointmentSaleNotEndResponse) && !CollectionUtils.isEmpty(appointmentSaleNotEndResponse.getAppointmentSaleVOList()) && !saleType.equals(oldSaleType)) {
            throw new SbcRuntimeException(OrderErrorCodeEnum.K050185);
        }
        // 参与预售活动的商品不可修改
        BookingSaleNotEndResponse bookingSaleNotEndResponse =
                bookingSaleQueryProvider.getNotEndActivity(BookingSaleByGoodsIdRequest.builder().goodsId(goodsId).build()).getContext();
        if (Objects.nonNull(bookingSaleNotEndResponse) && !CollectionUtils.isEmpty(bookingSaleNotEndResponse.getBookingSaleVOList()) && !saleType.equals(oldSaleType)) {
            throw new SbcRuntimeException(OrderErrorCodeEnum.K050185);
        }
        return oldData;
    }

    /**
     * 将商品视图信息转给商品修改对象
     * @param response
     * @param saveRequest
     * @return
     */
    public GoodsModifyRequest viewToGoodsModify(GoodsViewByIdResponse response, GoodsInfoModifyRequest saveRequest) {
        GoodsModifyRequest request = new GoodsModifyRequest();

        if(Objects.nonNull(response) && Objects.nonNull(response.getGoods())){
            request.setGoods(response.getGoods());
            request.setIsIndependent(request.getGoods().getIsIndependent());
        }else{
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }


        if(CollectionUtils.isNotEmpty(response.getImages())){
            request.setImages(response.getImages().stream().map(image -> {
                GoodsImageVO vo = new GoodsImageVO();
                vo.setArtworkUrl(image.getArtworkUrl());
                vo.setSort(image.getSort());
                if (Objects.nonNull(image.getSort())){
                    vo.setImageId(image.getSort() == 0 ? null : Long.valueOf(image.getSort()));
                }
                return vo;
            }).collect(Collectors.toList()));
        }

        String goodsId = response.getGoods().getGoodsId();
        if(Objects.nonNull(saveRequest) && Objects.nonNull(saveRequest.getGoodsInfo())){
            goodsId = saveRequest.getGoodsInfo().getGoodsId();
        }

        GoodsPropertyListForGoodsResponse propertyResponse = goodsPropertyQueryProvider
                .getGoodsPropertyListForGoods(GoodsPropertyByGoodsIdRequest.builder()
                        .goodsId(goodsId).build())
                .getContext();

        if(CollectionUtils.isNotEmpty(propertyResponse.getGoodsPropertyDetailRelVOList())){
            List<GoodsPropertyDetailRelDTO> relDTOList = propertyResponse.getGoodsPropertyDetailRelVOList().stream().map(rel -> {
                GoodsPropertyDetailRelDTO dto = KsBeanUtil.convert(rel, GoodsPropertyDetailRelDTO.class);

                if (StringUtils.isNotBlank(rel.getPropValueCountry())) {
                    dto.setPropValueCountry(Arrays.stream(rel.getPropValueCountry().split(",")).map(Long::valueOf).collect(Collectors.toList()));
                }
                if (StringUtils.isNotBlank(rel.getDetailId())){
                    dto.setDetailIdList(Arrays.stream(rel.getDetailId().split(",")).map(Long::valueOf).collect(Collectors.toList()));
                }
                if (StringUtils.isNotBlank(rel.getPropValueProvince())) {
                    dto.setPropValueProvince(Arrays.stream(rel.getPropValueProvince().split(",")).collect(Collectors.toList()));
                }
                return dto;
            }).collect(Collectors.toList());

            if(CollectionUtils.isNotEmpty(relDTOList)){
                List<GoodsPropDetailRelVO> goodsPropDetailRelVOS = KsBeanUtil.convert(relDTOList, GoodsPropDetailRelVO.class);
                request.setGoodsPropDetailRels(goodsPropDetailRelVOS);
                request.setGoodsDetailRel(relDTOList);
            }

        }

        if(CollectionUtils.isNotEmpty(response.getGoodsSpecs())){
            request.setGoodsSpecs(response.getGoodsSpecs().stream().map(spec -> {
                GoodsSpecVO vo = new GoodsSpecVO();
                vo.setSpecId(spec.getSpecId());
                vo.setMockSpecId(spec.getSpecId());
                vo.setSpecName(spec.getSpecName());
                return vo;
            }).collect(Collectors.toList()));
        }

        if(CollectionUtils.isNotEmpty(response.getGoodsSpecDetails())){
            request.setGoodsSpecDetails(response.getGoodsSpecDetails().stream().map(specDetails -> {
                GoodsSpecDetailVO vo = new GoodsSpecDetailVO();
                vo.setSpecId(specDetails.getSpecId());
                vo.setMockSpecId(specDetails.getSpecId());
                GoodsSpecVO goodsSpecVO = response.getGoodsSpecs().stream().filter(v -> v.getSpecId().equals(specDetails.getSpecId())).findFirst().orElse(null);
                vo.setSpecName(Objects.isNull(goodsSpecVO) ? null : goodsSpecVO.getSpecName());
                vo.setSpecDetailId(specDetails.getSpecDetailId());
                vo.setMockSpecDetailId(specDetails.getSpecDetailId());
                vo.setDetailName(specDetails.getDetailName());
                return vo;
            }).collect(Collectors.toList()));
        }

        if(CollectionUtils.isNotEmpty(response.getGoodsInfos())){
            request.setGoodsInfos(response.getGoodsInfos().stream().map(info -> {
                GoodsInfoVO vo = new GoodsInfoVO();

                if (Objects.nonNull(saveRequest) && Objects.nonNull(saveRequest.getGoodsInfo())) {
                    String saveGoodsInfoId = saveRequest.getGoodsInfo().getGoodsInfoId();
                    String goodsInfoId = info.getGoodsInfoId();
                    if(saveGoodsInfoId.equals(goodsInfoId)){
                        GoodsInfoVO goodsInfoVo = KsBeanUtil.convert(saveRequest.getGoodsInfo(), GoodsInfoVO.class);
                        getGoodsInfo(vo, goodsInfoVo, saveRequest.getGoodsInfo());
                        return vo;
                    }
                }
                GoodsInfoDTO goodsInfoDTO = Objects.nonNull(saveRequest) ? saveRequest.getGoodsInfo() : null;
                getGoodsInfo(vo, info,  goodsInfoDTO);
                return vo;
            }).collect(Collectors.toList()));
        }

        request.setGoodsLevelPrices(response.getGoodsLevelPrices());
        request.setGoodsCustomerPrices(response.getGoodsCustomerPrices());
        request.setGoodsIntervalPrices(response.getGoodsIntervalPrices());
        request.setGoodsTabRelas(response.getGoodsTabRelas());

        return request;
    }

    private void getGoodsInfo(GoodsInfoVO vo, GoodsInfoVO goodsInfo, GoodsInfoDTO goodsInfoDTO) {
        vo.setMockSpecDetailIds(goodsInfo.getMockSpecDetailIds());
        vo.setGoodsInfoNo(goodsInfo.getGoodsInfoNo());
        vo.setGoodsCubage(goodsInfo.getGoodsCubage());
        vo.setRetailPrice(goodsInfo.getRetailPrice());
        vo.setSupplyPrice(goodsInfo.getSupplyPrice());
        vo.setBuyPoint(goodsInfo.getBuyPoint());
        vo.setMarketPrice(goodsInfo.getMarketPrice());
        vo.setGoodsWeight(goodsInfo.getGoodsWeight());
        vo.setMockSpecIds(goodsInfo.getMockSpecIds());
        vo.setStock(goodsInfo.getStock());
        vo.setDelFlag(goodsInfo.getDelFlag());
        vo.setGoodsInfoBarcode(goodsInfo.getGoodsInfoBarcode());
        vo.setGoodsInfoId(goodsInfo.getGoodsInfoId());
        vo.setGoodsInfoImg(goodsInfo.getGoodsInfoImg());
        vo.setAloneFlag(goodsInfo.getAloneFlag());
        vo.setMarketPrice(goodsInfo.getMarketPrice());
        vo.setCustomFlag(goodsInfo.getCustomFlag());
        vo.setLevelDiscountFlag(goodsInfo.getLevelDiscountFlag());
        vo.setQuickOrderNo(goodsInfo.getQuickOrderNo());
        vo.setProviderQuickOrderNo(goodsInfo.getProviderQuickOrderNo());
        if (Objects.nonNull(goodsInfo.getElectronicCouponsId())){
            vo.setElectronicCouponsId(goodsInfo.getElectronicCouponsId());
        }
        if (Objects.nonNull(goodsInfoDTO)
                && Objects.nonNull(goodsInfoDTO.getGoodsInfoId())
                && goodsInfo.getGoodsInfoId().equals(goodsInfoDTO.getGoodsInfoId())){
            vo.setAloneFlag(goodsInfoDTO.getAloneFlag());
            vo.setMarketPrice(goodsInfoDTO.getMarketPrice());
            vo.setCustomFlag(goodsInfoDTO.getCustomFlag());
            vo.setLevelDiscountFlag(goodsInfoDTO.getLevelDiscountFlag());
            vo.setAddedTime(goodsInfoDTO.getAddedTime());
            vo.setAddedFlag(goodsInfoDTO.getAddedFlag());
            vo.setAddedTimingFlag(goodsInfoDTO.getAddedTimingFlag());
            vo.setAddedTimingTime(goodsInfoDTO.getAddedTimingTime());
        }else {
            vo.setAddedTime(goodsInfo.getAddedTime());
            vo.setAddedFlag(goodsInfo.getAddedFlag());
            vo.setAddedTimingFlag(goodsInfo.getAddedTimingFlag());
            vo.setAddedTimingTime(goodsInfo.getAddedTimingTime());
        }
    }

    /**
     * 处理商品价格
     */
    public GoodsModifyInfoResponse dealPrice(@RequestBody @Valid GoodsModifyAllRequest request) {
        Long fId = request.getGoods().getFreightTempId();
        if (request.getGoods() == null || request.getGoods().getGoodsId() == null) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        // 非 linkedMall和京东VOP 才需要校验运费模板id
        ThirdPlatformType thirdPlatformType = request.getGoods().getThirdPlatformType();
        if (Objects.isNull(fId)
                && ThirdPlatformType.LINKED_MALL != thirdPlatformType
                && ThirdPlatformType.VOP != thirdPlatformType) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }

        //订货价和订货区间不能为空且大于零
        if (request.getGoods().getSaleType() == SaleType.WHOLESALE.toValue() &&
            request.getGoods().getPriceType() == PriceType.SKU.toValue()){
            boolean flag = request.getGoodsIntervalPrices().stream().anyMatch(goodsIntervalPriceVO ->
                    (goodsIntervalPriceVO.getPrice() == null || goodsIntervalPriceVO.getPrice().compareTo(BigDecimal.ZERO) < Constants.ZERO) ||
                            (goodsIntervalPriceVO.getCount() == null || goodsIntervalPriceVO.getCount() < Constants.ZERO));

            if (flag){
                throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
            }
        }

        // 添加默认值, 适应云掌柜新增商品没有设置购买方式, 导致前台不展示购买方式问题
        if (StringUtils.isBlank(request.getGoods().getGoodsBuyTypes())) {
            request.getGoods().setGoodsBuyTypes(GOODS_BUY_TYPES);
        }
        //虚拟商品校验
        checkVirtualGoods(Objects.requireNonNull(KsBeanUtil.convert(request.getGoods(), GoodsDTO.class)),KsBeanUtil.convert(request.getGoodsInfos(),GoodsInfoDTO.class));
        //判断运费模板是否存在
        if (Objects.nonNull(fId)) {
            freightTemplateGoodsQueryProvider.existsById(
                    FreightTemplateGoodsExistsByIdRequest.builder().freightTempId(fId).build());
        }

        //校验商品是否正在预约预售
        try {
            checkSale(request.getGoods().getGoodsId(),request.getGoods().getSaleType());
        } catch (SbcRuntimeException e) {
            //如果是代销商品需要特殊返回
            if (StringUtils.isNotBlank(request.getGoods().getProviderGoodsId()) && GoodsErrorCodeEnum.K030035.getCode().equals(e.getErrorCode())) {
                throw new SbcRuntimeException(e.getData(), GoodsErrorCodeEnum.K030046);
            }
        } catch (Exception e) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000001, e);
        }

        request.setBaseStoreId(commonUtil.getStoreId());
        Integer goodsType = request.getGoods().getGoodsType();
        request.setGoodsInfos(request.getGoodsInfos().parallelStream()
                .peek(goodsInfoDTO -> {
                    goodsInfoDTO.setGoodsType(goodsType);
                    // 如果不是实体商品，设置重量体积为0
                    if (!goodsType.equals(NumberUtils.INTEGER_ZERO)) {
                        goodsInfoDTO.setGoodsCubage(BigDecimal.ZERO);
                        goodsInfoDTO.setGoodsWeight(BigDecimal.ZERO);
                    }
                    //如果是卡券商品，库存取卡密实际库存
                    if (goodsType.equals(GoodsType.ELECTRONIC_COUPON_GOODS.toValue())) {
                        LocalDateTime time = LocalDate.now().atTime(LocalDateTime.now().getHour(), 0, 0);
                        Long count = electronicCardQueryProvider.countEffectiveCoupon(ElectronicCardInvalidRequest.builder()
                                .couponId(goodsInfoDTO.getElectronicCouponsId())
                                .time(time)
                                .build()).getContext().getCount();
                        goodsInfoDTO.setStock(count);
                    }
                })
                .collect(Collectors.toList()));
        GoodsModifyInfoResponse goodsModifyInfoResponse = goodsProvider.modifyAll(request).getContext();

        //更新redis商品基本数据
        String goodsDetailInfo =
                redisService.getString(RedisKeyConstant.GOODS_DETAIL_CACHE + request.getGoods().getGoodsId());
        if (StringUtils.isNotBlank(goodsDetailInfo)) {
            redisService.delete(RedisKeyConstant.GOODS_DETAIL_CACHE + request.getGoods().getGoodsId());
        }
        //刷新es
        esGoodsInfoElasticProvider.deleteByGoods(EsGoodsDeleteByIdsRequest.builder().
                deleteIds(Collections.singletonList(request.getGoods().getGoodsId())).build());
        esGoodsInfoElasticProvider.initEsGoodsInfo(EsGoodsInfoRequest.builder().goodsId(request.getGoods().getGoodsId()).build());
        //ares埋点-商品-后台修改商品sku,迁移至goods微服务下
        operateLogMQUtil.convertAndSend("商品", "设价",
                "设价：SPU编码" + request.getGoods().getGoodsNo());

        // ============= 处理平台的消息发送：商家编辑商品触发待审核 START =============
        storeMessageBizService.handleForGoodsAudit(request, goodsModifyInfoResponse);
        // ============= 处理平台的消息发送：商家编辑商品触发待审核 END =============

        return goodsModifyInfoResponse;
    }


    /**
     * @description 商家商品列表展示供应商库存查询供应商库存
     * @author  lvzhenwei
     * @date 2021/11/3 10:57 上午
     * @param goodses
     * @return void
     **/
    public void updateGoodsStockForPrivate(List<GoodsPageSimpleVO> goodses){
        List<String> privateGoodsIdList = new ArrayList<>();
        goodses.forEach(goods->{
            if(StringUtils.isNotBlank(goods.getProviderGoodsId())){
                privateGoodsIdList.add(goods.getProviderGoodsId());
            }
        });
        if(CollectionUtils.isEmpty(privateGoodsIdList)){
            return;
        }
        EsSpuPageRequest esSpuPageRequest = new EsSpuPageRequest();
        esSpuPageRequest.setGoodsIds(privateGoodsIdList);
        esSpuPageRequest.setPageSize(privateGoodsIdList.size());
        BaseResponse<EsSpuPageResponse> esPageResponse = esSpuQueryProvider.page(esSpuPageRequest);
        List<GoodsPageSimpleVO> esGoodsList = esPageResponse.getContext().getGoodsPage().getContent();
        Map<String,Long> goodsStockMap = new HashMap<>();
        esGoodsList.forEach(esGoods->{
            String goodsId = esGoods.getGoodsId();
            if(StringUtils.isNotBlank(esGoods.getProviderGoodsId())){
                goodsId = esGoods.getProviderGoodsId();
            }
            Long stock = esGoods.getStock();
            goodsStockMap.put(goodsId,stock);
        });
        goodses.forEach(goods->{
            if(StringUtils.isNotBlank(goods.getProviderGoodsId())){
                goods.setStock(goodsStockMap.get(goods.getProviderGoodsId()));
            }
        });
    }

    /**
     * 供应商编辑商品
     * @param request
     * @return
     */
    public BaseResponse providerEdit(@RequestBody @Valid GoodsModifyRequest request) {
        log.info("商品编辑开始provider");
        long startTime = System.currentTimeMillis();

        //校验商品是否有待审核记录
        List<GoodsAuditVO> goodsAuditVOList = goodsAuditQueryProvider
                .getWaitCheckGoodsAudit(GoodsAuditQueryRequest.builder()
                        .goodsIdList(Collections.singletonList(request.getGoods().getGoodsId()))
                        .build())
                .getContext()
                .getGoodsAuditVOList();
        if (CollectionUtils.isNotEmpty(goodsAuditVOList)){
            throw new SbcRuntimeException(GoodsErrorCodeEnum.K030148);
        }

        GoodsViewByIdRequest goodsViewByIdRequest = new GoodsViewByIdRequest();
        goodsViewByIdRequest.setGoodsId(request.getGoods().getGoodsId());
        GoodsViewByIdResponse oldData = goodsQueryProvider.getViewById(goodsViewByIdRequest).getContext();
        //获取商品店铺分类
        if (osUtil.isS2b()) {
            StoreCateListByGoodsRequest storeCateListByGoodsRequest =
                    new StoreCateListByGoodsRequest(Collections.singletonList(request.getGoods().getGoodsId()));
            BaseResponse<StoreCateListByGoodsResponse> baseResponse =
                    storeCateQueryProvider.listByGoods(storeCateListByGoodsRequest);
            StoreCateListByGoodsResponse storeCateListByGoodsResponse = baseResponse.getContext();
            if (Objects.nonNull(storeCateListByGoodsResponse)) {
                List<StoreCateGoodsRelaVO> storeCateGoodsRelaVOList =
                        storeCateListByGoodsResponse.getStoreCateGoodsRelaVOList();
                oldData.getGoods().setStoreCateIds(storeCateGoodsRelaVOList.stream()
                        .filter(rela -> rela.getStoreCateId() != null)
                        .map(StoreCateGoodsRelaVO::getStoreCateId)
                        .collect(Collectors.toList()));
            }
        }

        GoodsModifyResponse response = goodsProvider.modify(request).getContext();

        //同步商品库信息
        if(CollectionUtils.isNotEmpty(response.getStandardIds())) {
            esStandardProvider.init(EsStandardInitRequest.builder().goodsIds(response.getStandardIds()).build());
        }

        //更新关联的商家商品es
        esGoodsInfoElasticProvider.initProviderEsGoodsInfo(EsGoodsInitProviderGoodsInfoRequest.builder().storeId(null)
                .providerGoodsIds(Collections.singletonList(request.getGoods().getGoodsId())).build());
        //ares埋点-商品-后台修改商品sku,迁移至goods微服务下
        operateLogMQUtil.convertAndSend("商品", "编辑商品",
                "编辑商品：SPU编码" + request.getGoods().getGoodsNo());
        log.info("商品编辑结束provider->花费{}毫秒", (System.currentTimeMillis() - startTime));

        // ============= 处理平台的消息发送：供应商编辑商品触发待审核 START =============
        storeMessageBizService.handleForProviderGoodsAudit(request);
        // ============= 处理平台的消息发送：供应商编辑商品触发待审核 END =============

        return BaseResponse.SUCCESSFUL();
    }

    public void checkVirtualGoods(GoodsDTO goods,List<GoodsInfoDTO> goodsInfos) {
        Long fId = goods.getFreightTempId();
        Integer goodsType = goods.getGoodsType();
        //商品类型是必选的
        if (Objects.isNull(goodsType)) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }

        if (!goodsType.equals(NumberUtils.INTEGER_ZERO)) {
            // 实物商品用原来的运费模版，虚拟商品用-1的虚拟商品默认模版
            if (fId != -1) {
                throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
            }
            //购买方式 虚拟商品及电子卡券 只能立即购买，不能加入购物车
            String goodsBuyTypes = goods.getGoodsBuyTypes();
            if(!StringUtils.equals(String.valueOf(GoodsBuyType.IMMEDIATE.toValue()),goodsBuyTypes)) {
                throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
            }
            //销售类别 虚拟商品及电子卡券只能零售，不能批发
            Integer saleType = goods.getSaleType();
            if (!Objects.equals(SaleType.RETAIL.toValue(),saleType)){
                throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
            }
        }
        // 如果是电子卡券，则校验电子卡券是否存在
        if (goodsType.equals(GoodsType.ELECTRONIC_COUPON_GOODS.toValue())) {
            goodsInfos.forEach(goodsInfoDTO -> {
                //如果没传电子卡券id，则参数错误
                if (Objects.isNull(goodsInfoDTO.getElectronicCouponsId())) {
                    throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
                }
                //如果没传电子卡券id查不到改电子卡券，则参数错误
                ElectronicCouponVO electronicCouponVO = electronicCouponQueryProvider.getById(ElectronicCouponByIdRequest.builder()
                        .id(goodsInfoDTO.getElectronicCouponsId())
                        .build()).getContext().getElectronicCouponVO();
                if (Objects.isNull(electronicCouponVO)) {
                    throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
                }

                //检查电子卡券是否被商品绑定
                goodsQueryProvider.checkBindElectronicCoupons(GoodsCheckBindRequest.builder()
                        .goodsInfoId(goodsInfoDTO.getGoodsInfoId())
                        .electronicCouponsId(goodsInfoDTO.getElectronicCouponsId())
                        .build());


            });
        }
    }

    /**
     * @description 上下架时间校验
     * @Author qiyuanzhao
     * @Date 2022/6/13 14:40
     * @param
     * @return
     **/
    public void checkAddAndTakedownTime(LocalDateTime takedownTime, Boolean takedownTimeFlag, LocalDateTime addedTimingTime, Boolean addedTimingFlag) {
        LocalDateTime now = LocalDateTime.now();
        //定时上架时间检验
        if (Boolean.TRUE.equals(addedTimingFlag)){
            //定时上架可选时间不早于现在，不能晚于1年后
            if (addedTimingTime.isBefore(now) ||
                    now.plusYears(Constants.ONE).isBefore(addedTimingTime)) {
                throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
            }
        }

        if (Boolean.TRUE.equals(takedownTimeFlag)){

            if (addedTimingTime == null){
                addedTimingTime = now;
            }
            //定时下架和定时上架时间不能相同，不能晚于当前时间或者定时上架时间1年后，不能早于现在
            if (takedownTime.compareTo(addedTimingTime) == Constants.ZERO ||
                    addedTimingTime.plusYears(Constants.ONE).isBefore(takedownTime) ||
                    takedownTime.isBefore(now)) {
                throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
            }

        }
    }

    public void populateSupplyPrice(List<GoodsPageSimpleVO> goodses) {
        if (CollectionUtils.isEmpty(goodses)){
            return;
        }
        Map<String, BigDecimal> supplyPriceMap = new HashMap<>();
        List<String> providerGoodsIds = goodses.stream().filter(goodsPageSimpleVO -> goodsPageSimpleVO.getProviderGoodsId() != null).map(GoodsPageSimpleVO::getProviderGoodsId).collect(Collectors.toList());
        if (CollectionUtils.isNotEmpty(providerGoodsIds)){
            GoodsListByIdsRequest goodsListByIdsRequest = GoodsListByIdsRequest.builder().goodsIds(providerGoodsIds).build();
            List<GoodsVO> goodsVOList = goodsQueryProvider.listByIds(goodsListByIdsRequest).getContext().getGoodsVOList();
            supplyPriceMap = goodsVOList.stream()
                    .filter(item -> Objects.nonNull(item.getSupplyPrice()))
                    .collect(Collectors.toMap(GoodsVO::getGoodsId, GoodsVO::getSupplyPrice));
        }

        for (GoodsPageSimpleVO goodsPageSimpleVO : goodses){
            if (goodsPageSimpleVO.getProviderGoodsId() != null){
                String providerGoodsId = goodsPageSimpleVO.getProviderGoodsId();
                BigDecimal supplyPrice = supplyPriceMap.getOrDefault(providerGoodsId, BigDecimal.ZERO);
                goodsPageSimpleVO.setSupplyPrice(supplyPrice);
            }
        }


    }

    //同步供应商供货价
    public void populateSkuSupplyPrice(List<GoodsInfoVO> goodses) {
        if (CollectionUtils.isEmpty(goodses)){
            return;
        }
        Map<String, BigDecimal> supplyPriceMap = new HashMap<>();
        List<String> providerGoodsIds = goodses.stream().map(GoodsInfoVO::getProviderGoodsInfoId).filter(Objects::nonNull).collect(Collectors.toList());
        if (CollectionUtils.isNotEmpty(providerGoodsIds)){
            GoodsInfoPartColsByIdsRequest goodsListByIdsRequest = GoodsInfoPartColsByIdsRequest.builder().goodsInfoIds(providerGoodsIds)
                    .build();
            Map<String, BigDecimal> goodsVOList = goodsInfoQueryProvider.listPartColsByIds(goodsListByIdsRequest).getContext().getGoodsInfos()
                    .stream()
                    .filter(i -> Objects.nonNull(i.getSupplyPrice()))
                    .collect(Collectors.toMap(GoodsInfoVO::getGoodsInfoId, GoodsInfoVO::getSupplyPrice, (a,b)-> a));
            supplyPriceMap.putAll(goodsVOList);
        }
        for (GoodsInfoVO goodsInfoVO : goodses){
            if (goodsInfoVO.getProviderGoodsInfoId() != null){
                BigDecimal supplyPrice = supplyPriceMap.getOrDefault(goodsInfoVO.getProviderGoodsInfoId(), BigDecimal.ZERO);
                goodsInfoVO.setSupplyPrice(supplyPrice);
            }
        }
    }

    /**
     * @description 填充营销商品状态
     * @Author qiyuanzhao
     * @Date 2022/10/10 13:35
     * @param
     * @return
     **/
    public void populateMarketingGoodsStatus(List<GoodsInfoVO> goodsInfoVOS) {
        if (CollectionUtils.isEmpty(goodsInfoVOS)){
            return;
        }

        for (GoodsInfoVO goodsInfoVO : goodsInfoVOS){
            GoodsInfoPopulateStatusRequest request =
                    GoodsInfoPopulateStatusRequest.builder().goodsInfoVO(goodsInfoVO).build();
            MarketingGoodsStatus status = goodsInfoQueryProvider.populateMarketingGoodsStatus(request).getContext().getMarketingGoodsStatus();
            goodsInfoVO.setMarketingGoodsStatus(status);
        }
    }


    /**
     * @description 根据goodsInfoIds获取marketingGoodsStatus属性
     * @Author qiyuanzhao
     * @Date 2022/10/10 14:15
     * @param
     * @return
     **/
    public Map<String, MarketingGoodsStatus> getMarketingGoodsStatusListByGoodsInfoIds(List<String> goodsInfoIds) {
        if (CollectionUtils.isEmpty(goodsInfoIds)){
            return MapUtils.EMPTY_MAP;
        }
        //获取goodsInfoVO的集合
        List<GoodsInfoVO> goodsInfoVOS = goodsInfoQueryProvider.getGoodsInfoByIds(
                        GoodsInfoListByIdsRequest.builder().goodsInfoIds(goodsInfoIds).build())
                .getContext().getGoodsInfos();
        //填充marketingGoodsStatus属性
        this.populateMarketingGoodsStatus(goodsInfoVOS);
        if (CollectionUtils.isEmpty(goodsInfoVOS)){
            return MapUtils.EMPTY_MAP;
        }
        //获取goodsInfoId->marketingGoodsStatus 的集合
        return goodsInfoVOS.stream().collect(Collectors.toMap(GoodsInfoVO::getGoodsInfoId, GoodsInfoVO::getMarketingGoodsStatus));

    }

    public void fillShowOutStockFlag(List<GoodsInfoVO> goodsInfoVOList, Long warningStock) {
        if (CollectionUtils.isNotEmpty(goodsInfoVOList)) {
            goodsInfoVOList.forEach(item -> this.fillShowOutStockFlag(item, warningStock));
        }
    }

    public void fillShowOutStockFlag(GoodsInfoVO goodsInfoVO, Long warningStock) {
        if (Objects.isNull(goodsInfoVO) || Objects.isNull(warningStock)) {
            return;
        }
        if (Objects.equals(goodsInfoVO.getAuditStatus(), CheckStatus.CHECKED)
                && Objects.equals(goodsInfoVO.getAddedFlag(), NumberUtils.INTEGER_ONE)) {
            goodsInfoVO.setWarningStock(warningStock);
            if (goodsInfoVO.getStock() < warningStock) {
                goodsInfoVO.setShowStockOutFlag(true);
            }
        }
    }
}