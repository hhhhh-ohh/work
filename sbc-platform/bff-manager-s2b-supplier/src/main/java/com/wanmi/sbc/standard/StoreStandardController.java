package com.wanmi.sbc.standard;

import com.wanmi.sbc.common.annotation.MultiSubmit;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.enums.SortType;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.redis.util.RedisUtil;
import com.wanmi.sbc.common.util.BaseResUtils;
import com.wanmi.sbc.common.util.OsUtil;
import com.wanmi.sbc.common.util.WmCollectionUtils;
import com.wanmi.sbc.common.validation.Assert;
import com.wanmi.sbc.customer.api.provider.company.CompanyInfoQueryProvider;
import com.wanmi.sbc.customer.api.provider.ledgerreceiverrel.LedgerReceiverRelQueryProvider;
import com.wanmi.sbc.customer.api.request.company.CompanyInfoByIdRequest;
import com.wanmi.sbc.customer.api.request.ledgerreceiverrel.LedgerReceiverRelBindStateRequest;
import com.wanmi.sbc.customer.bean.vo.CompanyInfoVO;
import com.wanmi.sbc.elastic.api.provider.goods.EsGoodsInfoElasticProvider;
import com.wanmi.sbc.elastic.api.provider.standard.EsStandardProvider;
import com.wanmi.sbc.elastic.api.provider.standard.EsStandardQueryProvider;
import com.wanmi.sbc.elastic.api.request.goods.EsGoodsInfoModifyAddedStatusRequest;
import com.wanmi.sbc.elastic.api.request.goods.EsGoodsInfoRequest;
import com.wanmi.sbc.elastic.api.request.standard.EsStandardInitRequest;
import com.wanmi.sbc.elastic.api.request.standard.EsStandardPageRequest;
import com.wanmi.sbc.elastic.api.response.standard.EsStandardPageResponse;
import com.wanmi.sbc.elastic.bean.vo.goods.EsStandardGoodsPageVO;
import com.wanmi.sbc.goods.api.constant.GoodsImportReason;
import com.wanmi.sbc.goods.api.provider.brand.ContractBrandQueryProvider;
import com.wanmi.sbc.goods.api.provider.cate.ContractCateQueryProvider;
import com.wanmi.sbc.goods.api.provider.cate.GoodsCateQueryProvider;
import com.wanmi.sbc.goods.api.provider.goods.GoodsProvider;
import com.wanmi.sbc.goods.api.provider.goods.GoodsQueryProvider;
import com.wanmi.sbc.goods.api.provider.standard.StandardGoodsQueryProvider;
import com.wanmi.sbc.goods.api.provider.standard.StandardImportProvider;
import com.wanmi.sbc.goods.api.provider.standard.StandardSkuQueryProvider;
import com.wanmi.sbc.goods.api.provider.storecate.StoreCateQueryProvider;
import com.wanmi.sbc.goods.api.request.goods.GoodsByIdRequest;
import com.wanmi.sbc.goods.api.request.goods.GoodsListByIdsRequest;
import com.wanmi.sbc.goods.api.request.goods.GoodsSynRequest;
import com.wanmi.sbc.goods.api.request.goods.GoodsViewByIdRequest;
import com.wanmi.sbc.goods.api.request.standard.StandardGoodsByIdRequest;
import com.wanmi.sbc.goods.api.request.standard.StandardGoodsGetUsedStandardRequest;
import com.wanmi.sbc.goods.api.request.standard.StandardImportGoodsRequest;
import com.wanmi.sbc.goods.api.request.standard.StandardSkuByIdRequest;
import com.wanmi.sbc.goods.api.request.storecate.StoreCateListByGoodsRequest;
import com.wanmi.sbc.goods.api.response.goods.GoodsByIdResponse;
import com.wanmi.sbc.goods.api.response.goods.GoodsViewByIdResponse;
import com.wanmi.sbc.goods.api.response.standard.StandardGoodsByIdResponse;
import com.wanmi.sbc.goods.api.response.standard.StandardImportGoodsResponse;
import com.wanmi.sbc.goods.api.response.standard.StandardSkuByIdResponse;
import com.wanmi.sbc.goods.api.response.storecate.StoreCateListByGoodsResponse;
import com.wanmi.sbc.goods.bean.enums.AddedFlag;
import com.wanmi.sbc.goods.bean.enums.GoodsImportState;
import com.wanmi.sbc.goods.bean.enums.GoodsSource;
import com.wanmi.sbc.goods.bean.vo.StoreCateGoodsRelaVO;
import com.wanmi.sbc.ledgeraccount.LedgerAccountBaseService;
import com.wanmi.sbc.setting.api.provider.operatedatalog.OperateDataLogSaveProvider;
import com.wanmi.sbc.setting.api.request.operatedatalog.OperateDataLogSynRequest;
import com.wanmi.sbc.util.CommonUtil;
import com.wanmi.sbc.util.OperateLogMQUtil;
import io.seata.spring.annotation.GlobalTransactional;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 商品库服务
 * Created by daiyitian on 17/4/12.
 */
@Slf4j
@Tag(name = "StoreStandardController", description = "商品库服务 API")
@RestController
@Validated
@RequestMapping("/standard")
public class StoreStandardController {

    @Autowired
    private CompanyInfoQueryProvider companyInfoQueryProvider;

    @Autowired
    private StandardGoodsQueryProvider standardGoodsQueryProvider;

    @Autowired
    private ContractCateQueryProvider contractCateQueryProvider;

    @Autowired
    private ContractBrandQueryProvider contractBrandQueryProvider;

    @Autowired
    private EsGoodsInfoElasticProvider esGoodsInfoElasticProvider;

    @Autowired
    private StandardImportProvider standardImportProvider;

    @Autowired
    private CommonUtil commonUtil;

    @Autowired
    private GoodsCateQueryProvider goodsCateQueryProvider;

    @Autowired
    private OperateLogMQUtil operateLogMQUtil;

    @Autowired
    private GoodsQueryProvider goodsQueryProvider;

    @Autowired
    private GoodsProvider goodsProvider;

    @Autowired
    private OperateDataLogSaveProvider operateDataLogSaveProvider;

    @Autowired
    private StandardSkuQueryProvider standardSkuQueryProvider;

    @Autowired
    private OsUtil osUtil;

    @Autowired
    private StoreCateQueryProvider storeCateQueryProvider;

    @Autowired
    private EsStandardQueryProvider esStandardQueryProvider;

    @Autowired
    private EsStandardProvider esStandardProvider;

    @Autowired
    private LedgerReceiverRelQueryProvider ledgerReceiverRelQueryProvider;

    @Autowired
    private LedgerAccountBaseService ledgerAccountBaseService;

    @Autowired private RedisUtil redisUtil;

    /**
     * 查询商品
     *
     * @param pageRequest 商品
     * @return 商品详情
     */
    @Operation(summary = "查询商品")
    @RequestMapping(value = "/spus", method = RequestMethod.POST)
    public BaseResponse<EsStandardPageResponse> list(@RequestBody EsStandardPageRequest pageRequest) {

        Long storeId = commonUtil.getStoreId();

        pageRequest.setDelFlag(DeleteFlag.NO.toValue());
        //按创建时间倒序、ID升序
        pageRequest.putSort("createTime", SortType.DESC.toValue());
        pageRequest.setStoreId(storeId);
        BaseResponse<EsStandardPageResponse> response = esStandardQueryProvider.page(pageRequest);
        //列出已被导入的商品库ID
        EsStandardPageResponse context = response.getContext();
        if (CollectionUtils.isNotEmpty(context.getStandardGoodsPage().getContent())) {
            StandardGoodsGetUsedStandardRequest standardRequest = new StandardGoodsGetUsedStandardRequest();
            standardRequest.setStandardIds(context.getStandardGoodsPage().getContent().stream().map(EsStandardGoodsPageVO::getGoodsId).collect(Collectors.toList()));
            standardRequest.setStoreIds(Collections.singletonList(storeId));
            context.setUsedStandard(standardGoodsQueryProvider.getUsedStandard(standardRequest).getContext().getStandardIds());

            //查询商户与接收方绑定状态
            Boolean gatewayOpen = ledgerAccountBaseService.getGatewayOpen();
            List<Long> unBindStores = new ArrayList<>();
            if (Objects.nonNull(pageRequest.getGoodsSource()) && GoodsSource.PROVIDER.toValue() == pageRequest.getGoodsSource() && gatewayOpen) {
                List<Long> storeIds = context.getStandardGoodsPage().getContent().stream()
                        .map(EsStandardGoodsPageVO::getStoreId).filter(Objects::nonNull)
                        .distinct().collect(Collectors.toList());
                if (CollectionUtils.isNotEmpty(storeIds)) {
                    LedgerReceiverRelBindStateRequest stateRequest = LedgerReceiverRelBindStateRequest.builder()
                            .supplierId(commonUtil.getCompanyInfoId())
                            .receiverStoreIds(storeIds)
                            .build();
                    unBindStores = ledgerReceiverRelQueryProvider.findUnBindStores(stateRequest)
                            .getContext().getUnBindStores();
                }
            }

            //设置导入状态和原因
            for (EsStandardGoodsPageVO goods : context.getStandardGoodsPage()) {
                if (context.getUsedStandard().contains(goods.getGoodsId())) {
                    goods.setImportState(GoodsImportState.IMPORT.toValue());
                } else if (unBindStores.contains(goods.getStoreId())) {
                    goods.setImportState(GoodsImportState.CAN_NOT_IMPORT.toValue());
                    goods.setReason(GoodsImportReason.LEDGER_NOT_BIND);
                } else {
                    goods.setImportState(GoodsImportState.NOT_IMPORT.toValue());
                }
            }
        }
        return response;
    }

    @Operation(summary = "获取商品库详情信息")
    @Parameter(name = "goodsId", description = "供应商商品ID", required = true)
    @RequestMapping(value = "/spuByProvider/{providerGoodsId}", method = RequestMethod.GET)
    public BaseResponse<StandardGoodsByIdResponse> infoByProvider(@PathVariable String providerGoodsId) {
        StandardGoodsByIdRequest standardGoodsByIdRequest = new StandardGoodsByIdRequest();
        standardGoodsByIdRequest.setGoodsId(providerGoodsId);
        BaseResponse<StandardGoodsByIdResponse> response =  standardGoodsQueryProvider.getByProviderId(standardGoodsByIdRequest);
        //获取商品店铺分类
        if (osUtil.isS2b()) {
            StoreCateListByGoodsRequest storeCateListByGoodsRequest = new StoreCateListByGoodsRequest(Collections.singletonList(providerGoodsId));
            BaseResponse<StoreCateListByGoodsResponse> baseResponse = storeCateQueryProvider.listByGoods(storeCateListByGoodsRequest);
            StoreCateListByGoodsResponse storeCateListByGoodsResponse = baseResponse.getContext();
            if (Objects.nonNull(storeCateListByGoodsResponse)) {
                List<StoreCateGoodsRelaVO> storeCateGoodsRelaVOList = storeCateListByGoodsResponse.getStoreCateGoodsRelaVOList();
                response.getContext().getGoods().setStoreCateIds(storeCateGoodsRelaVOList.stream()
                        .filter(rela -> rela.getStoreCateId() != null)
                        .map(StoreCateGoodsRelaVO::getStoreCateId)
                        .collect(Collectors.toList()));
            }
        }
        return response;
    }

    /**
     * 导入商品
     *
     * @param request 导入参数
     * @return 成功结果
     */
    @MultiSubmit
    @GlobalTransactional
    @Operation(summary = "导入商品")
    @RequestMapping(value = "/goods", method = RequestMethod.POST)
    public BaseResponse<StandardImportGoodsResponse> importGoods(@RequestBody StandardImportGoodsRequest request) {
        String key = "IMPORT_GOODS_KEY:" + commonUtil.getStoreId().toString();
        if (!redisUtil.setNx(key)) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K999997);
        }
        try {
            if (CollectionUtils.isEmpty(request.getGoodsIds())) {
                throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
            }
            CompanyInfoVO companyInfo = companyInfoQueryProvider.getCompanyInfoById(
                    CompanyInfoByIdRequest.builder().companyInfoId(commonUtil.getCompanyInfoId()).build()
            ).getContext();
            if (companyInfo == null) {
                throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
            }
            request.setCompanyInfoId(commonUtil.getCompanyInfoId());
            request.setCompanyType(companyInfo.getCompanyType());
            request.setStoreId(commonUtil.getStoreId());
            request.setSupplierName(companyInfo.getSupplierName());
            request.setStoreType(companyInfo.getStoreType());

            StandardImportGoodsResponse response = BaseResUtils.getContextFromRes(standardImportProvider.importGoods(request));
            // 刷新标品库ES
            esStandardProvider.init(EsStandardInitRequest.builder().goodsIds(request.getGoodsIds()).build());

            if(Objects.nonNull(response)){
                List<String> skuIds = response.getSkuIdList();
                List<String> goodsNameList = response.getGoodsNameList();
                // 加入ES
                if (WmCollectionUtils.isNotEmpty(skuIds)) {
                    esGoodsInfoElasticProvider.initEsGoodsInfo(EsGoodsInfoRequest.builder().skuIds(skuIds).build());
                }

                if (WmCollectionUtils.onlyOne(request.getGoodsIds()) && WmCollectionUtils.isNotEmpty(goodsNameList)) {
                    operateLogMQUtil.convertAndSend("商品", "商品库导入",
                            "商品库导入:" + WmCollectionUtils.findFirst(goodsNameList));
                } else {
                    operateLogMQUtil.convertAndSend("商品", "商品库批量导入", "商品库批量导入");
                }
            }
            return BaseResponse.success(response);
        } catch (Exception e) {
            log.error("商品导入 异常：", e);
        } finally {
            redisUtil.delete(key);
        }
        return BaseResponse.FAILED();
    }

    /**
     * 批量同步商品
     */
    @MultiSubmit
    @GlobalTransactional
    @Operation(summary = "批量同步商品")
    @RequestMapping(value = "/spu/syn", method = RequestMethod.POST)
    public BaseResponse onSale(@RequestBody GoodsSynRequest request) {
        // 断言参数不为空
        Assert.assertNotEmpty(request.getGoodsIds(), CommonErrorCodeEnum.K000009);
        request.setAddedFlag(AddedFlag.NO.toValue());
        //查询上架商品中是否包含供应商商品(下架状态的)，包含则返回
        GoodsListByIdsRequest goodsListByIdsRequest = new GoodsListByIdsRequest();
        goodsListByIdsRequest.setGoodsIds(request.getGoodsIds());
        request.setStoreId(commonUtil.getStoreId());
        List<String> goodsIds = goodsProvider.synGoods(request).getContext().getGoodsIds();
        if (CollectionUtils.isNotEmpty(goodsIds)) {
            if (1 == goodsIds.size()) {
                GoodsByIdRequest goodsByIdRequest = new GoodsByIdRequest();
                goodsByIdRequest.setGoodsId(goodsIds.get(0));
                GoodsByIdResponse response = goodsQueryProvider.getById(goodsByIdRequest).getContext();
                operateLogMQUtil.convertAndSend("商品", "同步",
                        "同步：SPU编码" + response.getGoodsNo());
            } else {
                operateLogMQUtil.convertAndSend("商品", "批量同步", "批量同步");
            }
            for (String goodsId : goodsIds) {
                GoodsViewByIdRequest goodsViewByIdRequest = new GoodsViewByIdRequest();
                goodsViewByIdRequest.setGoodsId(goodsId);
                GoodsViewByIdResponse newData = goodsQueryProvider.getViewById(goodsViewByIdRequest).getContext();
                //同步日志
                operateDataLogSaveProvider.synDataLog(OperateDataLogSynRequest.builder().supplierGoodsId(goodsId).providerGoodsId(newData.getGoods().getProviderGoodsId()).build());
            }
        }
        // 更新ES
        esGoodsInfoElasticProvider.updateAddedStatus(EsGoodsInfoModifyAddedStatusRequest.builder().
                addedFlag(AddedFlag.NO.toValue()).goodsIds(goodsIds).goodsInfoIds(null).build());
        return BaseResponse.SUCCESSFUL();
    }

    /**
     * 获取商品库SKU详情信息
     *
     * @param standardInfoId 商品库SKU编号
     * @return 商品详情
     */
    @Operation(summary = "获取商品库SKU详情信息")
    @RequestMapping(value = "/sku/{standardInfoId}", method = RequestMethod.GET)
    @Parameter(name = "standardInfoId", description = "商品库ID", required = true)
    public BaseResponse<StandardSkuByIdResponse> skuInfo(@PathVariable String standardInfoId) {
        return standardSkuQueryProvider.getById(
                StandardSkuByIdRequest.builder().standardInfoId(standardInfoId).build()
        );
    }


    /**
     * 获取商品库详情信息
     *
     * @param goodsId 商品库编号 {@link String}
     * @return 商品库详情
     */
    @Operation(summary = "获取商品库详情信息")
    @Parameter(name = "goodsId", description = "商品库商品Id", required = true)
    @RequestMapping(value = "/spu/{goodsId}", method = RequestMethod.GET)
    public BaseResponse<StandardGoodsByIdResponse> spuInfo(@PathVariable String goodsId) {
        StandardGoodsByIdRequest standardGoodsByIdRequest = new StandardGoodsByIdRequest();
        standardGoodsByIdRequest.setGoodsId(goodsId);
        BaseResponse<StandardGoodsByIdResponse> response = standardGoodsQueryProvider.getById(standardGoodsByIdRequest);
        //获取商品店铺分类
        if (osUtil.isS2b()) {
            goodsId = response.getContext().getGoods().getProviderGoodsId();
            if (goodsId != null) {
                StoreCateListByGoodsRequest storeCateListByGoodsRequest =
                        new StoreCateListByGoodsRequest(Collections.singletonList(goodsId));
                BaseResponse<StoreCateListByGoodsResponse> baseResponse =
                        storeCateQueryProvider.listByGoods(storeCateListByGoodsRequest);
                StoreCateListByGoodsResponse storeCateListByGoodsResponse = baseResponse.getContext();
                if (Objects.nonNull(storeCateListByGoodsResponse)) {
                    List<StoreCateGoodsRelaVO> storeCateGoodsRelaVOList =
                            storeCateListByGoodsResponse.getStoreCateGoodsRelaVOList();
                    response.getContext().getGoods().setStoreCateIds(storeCateGoodsRelaVOList.stream()
                            .filter(rela -> rela.getStoreCateId() != null)
                            .map(StoreCateGoodsRelaVO::getStoreCateId)
                            .collect(Collectors.toList()));
                }
            }
        }
        return response;
    }

}
