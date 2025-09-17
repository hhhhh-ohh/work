package com.wanmi.sbc.goods;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.common.enums.*;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.common.util.HttpUtil;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.customer.api.provider.company.CompanyInfoQueryProvider;
import com.wanmi.sbc.customer.api.provider.ledgerreceiverrel.LedgerReceiverRelQueryProvider;
import com.wanmi.sbc.customer.api.request.company.CompanyInfoByIdRequest;
import com.wanmi.sbc.customer.api.request.ledgerreceiverrel.LedgerReceiverRelBindStateRequest;
import com.wanmi.sbc.customer.bean.vo.CompanyInfoVO;
import com.wanmi.sbc.elastic.api.provider.spu.EsSpuQueryProvider;
import com.wanmi.sbc.elastic.api.request.spu.EsSpuPageRequest;
import com.wanmi.sbc.elastic.api.response.spu.EsSpuPageResponse;
import com.wanmi.sbc.goods.api.constant.GoodsImportReason;
import com.wanmi.sbc.goods.api.provider.excel.GoodsExcelProvider;
import com.wanmi.sbc.goods.api.provider.excel.GoodsSupplierExcelProvider;
import com.wanmi.sbc.goods.api.provider.goods.GoodsProvider;
import com.wanmi.sbc.goods.api.provider.goods.GoodsQueryProvider;
import com.wanmi.sbc.goods.api.provider.goodsaudit.GoodsAuditQueryProvider;
import com.wanmi.sbc.goods.api.provider.info.GoodsInfoQueryProvider;
import com.wanmi.sbc.goods.api.provider.storecate.StoreCateQueryProvider;
import com.wanmi.sbc.goods.api.request.excel.GoodsSupplierExcelExportTemplateByStoreIdRequest;
import com.wanmi.sbc.goods.api.request.excel.MarketingTemplateRequest;
import com.wanmi.sbc.goods.api.request.goods.GoodsModifyCateRequest;
import com.wanmi.sbc.goods.api.request.goods.GoodsPageForGrouponRequest;
import com.wanmi.sbc.goods.api.request.goods.GoodsQueryNeedSynRequest;
import com.wanmi.sbc.goods.api.request.goodsaudit.GoodsAuditPageRequest;
import com.wanmi.sbc.goods.api.request.goodsaudit.GoodsAuditQueryRequest;
import com.wanmi.sbc.goods.api.request.info.GoodsIdByStockRequest;
import com.wanmi.sbc.goods.api.request.storecate.StoreCateListByGoodsRequest;
import com.wanmi.sbc.goods.api.request.storecate.StoreCateListGoodsRelByStoreCateIdAndIsHaveSelfRequest;
import com.wanmi.sbc.goods.api.response.goods.GoodsListNeedSynCountResponse;
import com.wanmi.sbc.goods.api.response.goods.GoodsListNeedSynResponse;
import com.wanmi.sbc.goods.api.response.goodsaudit.GoodsAuditListResponse;
import com.wanmi.sbc.goods.api.response.goodsaudit.GoodsAuditPageResponse;
import com.wanmi.sbc.goods.api.response.storecate.StoreCateListByGoodsResponse;
import com.wanmi.sbc.goods.api.response.storecate.StoreCateListGoodsRelByStoreCateIdAndIsHaveSelfResponse;
import com.wanmi.sbc.goods.bean.enums.AddedFlag;
import com.wanmi.sbc.goods.bean.enums.CheckStatus;
import com.wanmi.sbc.goods.bean.vo.GoodsAuditVO;
import com.wanmi.sbc.goods.bean.vo.GoodsPageSimpleVO;
import com.wanmi.sbc.goods.bean.vo.StoreCateGoodsRelaVO;
import com.wanmi.sbc.goods.request.GoodsSupplierExcelImportRequest;
import com.wanmi.sbc.goods.service.GoodsBaseService;
import com.wanmi.sbc.goods.service.SupplierGoodsExcelService;
import com.wanmi.sbc.marketing.api.provider.grouponactivity.GrouponActivityQueryProvider;
import com.wanmi.sbc.marketing.api.request.grouponactivity.GrouponActivityListSpuIdRequest;
import com.wanmi.sbc.setting.api.provider.storemessagenodesetting.StoreMessageNodeSettingQueryProvider;
import com.wanmi.sbc.setting.api.request.storemessagenodesetting.StoreMessageNodeSettingByStoreIdRequest;
import com.wanmi.sbc.setting.bean.vo.StoreMessageNodeSettingVO;
import com.wanmi.sbc.system.service.SystemPointsConfigService;
import com.wanmi.sbc.util.CommonUtil;
import com.wanmi.sbc.util.OperateLogMQUtil;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Base64;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 商品服务
 * Created by daiyitian on 17/4/12.
 */
@Tag(name = "StoreGoodsController", description = "商品服务 API")
@RestController
@Validated
@RequestMapping("/goods")
@Slf4j
@RefreshScope
public class StoreGoodsController {

    @Autowired
    private GoodsQueryProvider goodsQueryProvider;

    @Autowired
    private GoodsProvider goodsProvider;

    @Autowired
    private GoodsSupplierExcelProvider goodsSupplierExcelProvider;

    @Autowired
    private CompanyInfoQueryProvider companyInfoQueryProvider;

    @Autowired
    private StoreCateQueryProvider storeCateQueryProvider;

    @Autowired
    private CommonUtil commonUtil;

    @Autowired
    private OperateLogMQUtil operateLogMQUtil;

    @Autowired
    private SupplierGoodsExcelService supplierGoodsExcelService;

    @Autowired
    private GrouponActivityQueryProvider grouponActivityQueryProvider;

    @Autowired
    private EsSpuQueryProvider esSpuQueryProvider;

    @Autowired
    private SystemPointsConfigService systemPointsConfigService;

    @Autowired
    private GoodsAuditQueryProvider goodsAuditQueryProvider;

    @Autowired
    private GoodsBaseService goodsBaseService;

    @Autowired
    private LedgerReceiverRelQueryProvider ledgerReceiverRelQueryProvider;

    @Autowired
    private GoodsExcelProvider goodsExcelProvider;

    @Autowired
    private GoodsStockExcelService goodsStockExcelService;

    @Autowired
    private StoreMessageNodeSettingQueryProvider storeMessageNodeSettingQueryProvider;

    @Autowired
    private GoodsInfoQueryProvider goodsInfoQueryProvider;



    /**
     * 查询商品
     *
     * @param queryRequest 商品
     * @return 商品详情
     */
    @Operation(summary = "查询商品")
    @RequestMapping(value = "/spus", method = RequestMethod.POST)
    public BaseResponse<EsSpuPageResponse> list(@RequestBody EsSpuPageRequest queryRequest) {
        queryRequest.setCompanyInfoId(commonUtil.getCompanyInfoId());
        queryRequest.setStoreId(commonUtil.getStoreId());
        queryRequest.setDelFlag(DeleteFlag.NO.toValue());
        //补充店铺分类
        if(queryRequest.getStoreCateId() != null) {
            BaseResponse<StoreCateListGoodsRelByStoreCateIdAndIsHaveSelfResponse>  baseResponse = storeCateQueryProvider.listGoodsRelByStoreCateIdAndIsHaveSelf(new StoreCateListGoodsRelByStoreCateIdAndIsHaveSelfRequest(queryRequest.getStoreCateId(), true));
            StoreCateListGoodsRelByStoreCateIdAndIsHaveSelfResponse cateIdAndIsHaveSelfResponse = baseResponse.getContext();
            if (Objects.nonNull(cateIdAndIsHaveSelfResponse)) {
                List<StoreCateGoodsRelaVO> relas = cateIdAndIsHaveSelfResponse.getStoreCateGoodsRelaVOList();
                if (CollectionUtils.isEmpty(relas)) {
                    EsSpuPageResponse response = new EsSpuPageResponse();
                    response.setGoodsPage(new MicroServicePage<>(Collections.emptyList(), queryRequest.getPageable(), 0));
                    return BaseResponse.success(response);
                }
                queryRequest.setStoreCateGoodsIds(relas.stream().map(StoreCateGoodsRelaVO::getGoodsId).collect(Collectors.toList()));
            }else{
                EsSpuPageResponse response = new EsSpuPageResponse();
                response.setGoodsPage(new MicroServicePage<>(Collections.emptyList(), queryRequest.getPageable(), 0));
                return BaseResponse.success(response);
            }
        }
        if(CheckStatus.WAIT_CHECK.equals(queryRequest.getAuditStatus())){
            BaseResponse<GoodsAuditListResponse> baseResponse =
                    goodsAuditQueryProvider.listByCondition(GoodsAuditQueryRequest.builder()
                    .companyInfoId(commonUtil.getCompanyInfoId())
                    .storeId(commonUtil.getStoreId())
                    .delFlag(DeleteFlag.NO.toValue())
                    .build());
            if(Objects.nonNull(baseResponse.getContext())
                    && CollectionUtils.isNotEmpty(baseResponse.getContext().getGoodsAuditVOList())){
                queryRequest.setGoodsIds(baseResponse.getContext().getGoodsAuditVOList().stream().map(GoodsAuditVO::getGoodsId).collect(Collectors.toList()));
            }
            queryRequest.setAuditStatus(null);
        }
        queryRequest.setShowVendibilityFlag(Boolean.TRUE);//显示可售性
        BaseResponse<EsSpuPageResponse> pageResponse = esSpuQueryProvider.page(queryRequest);
        EsSpuPageResponse response = pageResponse.getContext();
        List<GoodsPageSimpleVO> goodses = response.getGoodsPage().getContent();
        if(CollectionUtils.isNotEmpty(goodses)) {
            List<String> goodsIds = goodses.stream().map(GoodsPageSimpleVO::getGoodsId).collect(Collectors.toList());
            BaseResponse<StoreCateListByGoodsResponse> baseResponse = storeCateQueryProvider.listByGoods(new StoreCateListByGoodsRequest(goodsIds));
            StoreCateListByGoodsResponse cateListByGoodsResponse = baseResponse.getContext();
            if (Objects.isNull(cateListByGoodsResponse)){
                return BaseResponse.success(response);
            }
            Map<String, List<StoreCateGoodsRelaVO>> storeCateMap = cateListByGoodsResponse.getStoreCateGoodsRelaVOList()
                    .stream().collect(Collectors.groupingBy(StoreCateGoodsRelaVO::getGoodsId));
            //为每个spu填充店铺分类编号
            if(MapUtils.isNotEmpty(storeCateMap)){
                goodses.stream()
                        .filter(goods -> storeCateMap.containsKey(goods.getGoodsId()))
                        .forEach(goods -> {
                            goods.setStoreCateIds(storeCateMap.get(goods.getGoodsId()).stream().map(StoreCateGoodsRelaVO::getStoreCateId).filter(Objects::nonNull).distinct().collect(Collectors.toList()));
                        });
            }
            systemPointsConfigService.clearBuyPointsForEsSpuNew(goodses);
            // 商品列表展示供应商库存
            goodsBaseService.updateGoodsStockForPrivate(goodses);

            //判断商品是否有待审核商品
            GoodsAuditListResponse auditListResponse = goodsAuditQueryProvider.getWaitCheckGoodsAudit(GoodsAuditQueryRequest.builder().goodsIdList(goodsIds).build()).getContext();
            if (CollectionUtils.isNotEmpty(auditListResponse.getGoodsAuditVOList())) {
                List<String> auditOldGoodsIds = auditListResponse.getGoodsAuditVOList().stream().map(GoodsAuditVO::getOldGoodsId).collect(Collectors.toList());
                goodses.forEach(goods -> {
                    if (auditOldGoodsIds.contains(goods.getGoodsId())) {
                        goods.setEditFlag(BoolFlag.NO);
                    }
                });
            }
            //供应商商品填充供货价
            goodsBaseService.populateSupplyPrice(goodses);

            //设置下架原因
            if (Platform.SUPPLIER.equals(commonUtil.getOperator().getPlatform())) {
                List<Long> providerIds = goodses.stream()
                        .filter(g -> Objects.nonNull(g.getProviderId()))
                        .map(GoodsPageSimpleVO::getProviderId).collect(Collectors.toList());
                if (CollectionUtils.isNotEmpty(providerIds)) {
                    LedgerReceiverRelBindStateRequest stateRequest = LedgerReceiverRelBindStateRequest.builder()
                            .supplierId(commonUtil.getCompanyInfoId())
                            .receiverStoreIds(providerIds)
                            .build();
                    List<Long> unBindStores = ledgerReceiverRelQueryProvider.findUnBindStores(stateRequest).getContext().getUnBindStores();
                    if (CollectionUtils.isNotEmpty(unBindStores)) {
                        goodses.stream().filter(g -> AddedFlag.NO.toValue() == g.getAddedFlag() && unBindStores.contains(g.getProviderId()))
                                .forEach(g -> {
                                    g.setAddFalseReason(GoodsImportReason.LEDGER_NOT_BIND);
                                });
                    }
                }

            }

            //填充是否预警标识
            //1、查询当前商家的库存预警值
            Long storeId = commonUtil.getStoreId();
            if(Objects.nonNull(storeId)){
                StoreMessageNodeSettingVO storeMessageNodeSettingVO = storeMessageNodeSettingQueryProvider.getWarningStock(StoreMessageNodeSettingByStoreIdRequest.builder().storeId(storeId).build()).getContext().getStoreMessageNodeSettingVO();
                if (Objects.nonNull(storeMessageNodeSettingVO) && storeMessageNodeSettingVO.getStatus().equals(BoolFlag.YES)) {
                    Long warningStock = storeMessageNodeSettingVO.getWarningStock();
                    //查goodsInfo表中库存小于预警库存的goodsIds
                    List<String> stockOutSpuIds = goodsInfoQueryProvider.getGoodsIdByStock(GoodsIdByStockRequest.builder().warningStock(warningStock).storeId(storeId).build()).getContext().getGoodsIds();
                    if (Objects.nonNull(warningStock) && CollectionUtils.isNotEmpty(stockOutSpuIds)) {
                        goodses.stream()
                                .filter(goods -> Objects.equals(goods.getAuditStatus(), CheckStatus.CHECKED))
                                .filter(goods -> Objects.equals(goods.getAddedFlag(), NumberUtils.INTEGER_ONE))
                                .forEach(goods -> goods.setShowStockOutFlag(goods.getStock() != null && stockOutSpuIds.contains(goods.getGoodsId())));
                    }
                }
            }
        }
        return BaseResponse.success(response);
    }

    /**
     * 查询商品列表(供添加拼团活动中选择商品用)
     *
     * @param request
     * @return
     */
    @Operation(summary = "查询商品列表(供添加拼团活动中选择商品用)")
    @RequestMapping(value = "/groupon-spus", method = RequestMethod.POST)
    public BaseResponse<EsSpuPageResponse> listForGroupon(@RequestBody GoodsPageForGrouponRequest request) {
        // 1.查询商品列表接口
        EsSpuPageRequest pageRequest = KsBeanUtil.convert(request, EsSpuPageRequest.class);
        pageRequest.setAddedFlags(Arrays.asList(1, 2));
        pageRequest.setAuditStatus(CheckStatus.CHECKED);
        BaseResponse<EsSpuPageResponse> response = this.list(pageRequest);
        List<GoodsPageSimpleVO> goodses = response.getContext().getGoodsPage().getContent();

        // 2.标记不可选择的商品
        if (goodses.size() != 0
                && request.getActivityStartTime() != null
                && request.getActivityEndTime() != null) {
            List<String> goodsIds = grouponActivityQueryProvider.listActivityingSpuIds(
                    new GrouponActivityListSpuIdRequest(
                            goodses.stream().map(GoodsPageSimpleVO::getGoodsId).collect(Collectors.toList()),
                            request.getActivityStartTime(),
                            request.getActivityEndTime())
            ).getContext().getGoodsIds();

            if (CollectionUtils.isNotEmpty(goodsIds)) {
                goodses.forEach(goods -> {
                    if (goodsIds.contains(goods.getGoodsId())) {
                        goods.setGrouponForbiddenFlag(true);
                    }
                });
            }
        }
        return response;
    }

    @Operation(summary = "查询商品列表(供添加拼团活动中选择商品用)")
    @RequestMapping(value = "/list/goods/template", method = RequestMethod.POST)
    public BaseResponse<EsSpuPageResponse> listForGoodsTemplate(@RequestBody GoodsPageForGrouponRequest request) {
        // 1.查询商品列表接口
        EsSpuPageRequest pageRequest = KsBeanUtil.convert(request, EsSpuPageRequest.class);
        pageRequest.setAuditStatus(CheckStatus.CHECKED);
        return this.list(pageRequest);
    }


    /**
     * 批量设置分类
     */
    @Operation(summary = "批量设置分类")
    @RequestMapping(value = "/spu/cate", method = RequestMethod.PUT)
    public BaseResponse updateCate(@RequestBody GoodsModifyCateRequest request) {

        if (CollectionUtils.isEmpty(request.getGoodsIds())) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        if (CollectionUtils.isEmpty(request.getStoreCateIds())) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }

        goodsProvider.modifyCate(request);
        operateLogMQUtil.convertAndSend("商品","批量设置店铺分类","批量设置店铺分类");
        return BaseResponse.SUCCESSFUL();
    }

    /**
     * 下载模板(原实体商品)
     */
    @Operation(summary = "下载模板")
    @Parameter(name = "encrypted", description = "加密", required = true)
    @RequestMapping(value = "/excel/template/{encrypted}", method = RequestMethod.GET)
    public void template(@PathVariable String encrypted) {
        GoodsSupplierExcelExportTemplateByStoreIdRequest request =
                new GoodsSupplierExcelExportTemplateByStoreIdRequest();
        request.setStoreId(commonUtil.getStoreId());
        request.setGoodsType(NumberUtils.INTEGER_ZERO);
        String file = goodsSupplierExcelProvider.supplierExportTemplate(request).getContext().getFile();
        if(StringUtils.isNotBlank(file)){
            try {
                String fileName = URLEncoder.encode("商品导入模板.xls", StandardCharsets.UTF_8.name());
                HttpUtil.getResponse().setHeader("Content-Disposition", String.format("attachment;filename=\"%s\";filename*=\"utf-8''%s\"", fileName, fileName));
                HttpUtil.getResponse().getOutputStream().write(Base64.getDecoder().decode(file));
            }catch (Exception e){
                throw new SbcRuntimeException(CommonErrorCodeEnum.K000001);
            }
        }
    }

    /**
     * 下载模板（包含虚拟商品）
     */
    @Operation(summary = "下载模板")
    @Parameter(name = "encrypted", description = "加密", required = true)
    @RequestMapping(value = "/excel/template/{goodsType}/{encrypted}", method = RequestMethod.GET)
    public void templateForGoodsType(@PathVariable Integer goodsType,@PathVariable String encrypted) {
        GoodsSupplierExcelExportTemplateByStoreIdRequest request =
                new GoodsSupplierExcelExportTemplateByStoreIdRequest();
        request.setStoreId(commonUtil.getStoreId());
        request.setGoodsType(goodsType);
        String file = goodsSupplierExcelProvider.supplierExportTemplate(request).getContext().getFile();
        if(StringUtils.isNotBlank(file)){
            try {
                String fileName = URLEncoder.encode("商品导入模板.xls", StandardCharsets.UTF_8.name());
                HttpUtil.getResponse().setHeader("Content-Disposition", String.format("attachment;filename=\"%s\";filename*=\"utf-8''%s\"", fileName, fileName));
                HttpUtil.getResponse().getOutputStream().write(Base64.getDecoder().decode(file));
            }catch (Exception e){
                throw new SbcRuntimeException(CommonErrorCodeEnum.K000001);
            }
        }
    }

    /**
     * 下载模板(原实体商品)
     */
    @Operation(summary = "下载批量修改库存模板")
    @Parameter(name = "encrypted", description = "加密", required = true)
    @RequestMapping(value = "/excel/template/stock/{encrypted}", method = RequestMethod.GET)
    public void stockTemplate(@PathVariable String encrypted) {

        String file = goodsExcelProvider.getTemplate(MarketingTemplateRequest.builder().activityType(Constants.NUM_20).build()).getContext().getFile();
        if(StringUtils.isNotBlank(file)){
            try {
                String fileName = URLEncoder.encode("商品库存导入模板.xls", StandardCharsets.UTF_8.name());
                HttpUtil.getResponse().setHeader("Content-Disposition", String.format("attachment;filename=\"%s\";filename*=\"utf-8''%s\"", fileName, fileName));
                HttpUtil.getResponse().getOutputStream().write(Base64.getDecoder().decode(file));
            }catch (Exception e){
                throw new SbcRuntimeException(CommonErrorCodeEnum.K000001);
            }
        }
    }

    /**
     * 确认导入商品
     */
    @Operation(summary = "确认导入商品")
    @Parameter(name = "ext", description = "后缀", required = true)
    @RequestMapping(value = "/import/{ext}", method = RequestMethod.GET)
    public BaseResponse<Boolean> implGoods(@PathVariable String ext) {
        if(!(Constants.XLS.equalsIgnoreCase(ext) || Constants.XLSX.equalsIgnoreCase(ext))){
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }

        CompanyInfoVO companyInfo = companyInfoQueryProvider.getCompanyInfoById(
                CompanyInfoByIdRequest.builder().companyInfoId(commonUtil.getCompanyInfoId()).build()
        ).getContext();
        if(companyInfo == null){
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }

        GoodsSupplierExcelImportRequest importRequest = new GoodsSupplierExcelImportRequest();
        importRequest.setExt(ext);
        importRequest.setUserId(commonUtil.getOperatorId());
        importRequest.setCompanyInfoId(commonUtil.getCompanyInfoId());
        importRequest.setStoreId(commonUtil.getStoreId());
        importRequest.setCompanyType(companyInfo.getCompanyType());
        importRequest.setSupplierName(companyInfo.getSupplierName());
        importRequest.setType(StoreType.SUPPLIER);
        importRequest.setStoreType(companyInfo.getStoreType());
        importRequest.setGoodsType(NumberUtils.INTEGER_ZERO);
        supplierGoodsExcelService.implGoods(importRequest);

        return BaseResponse.success(Boolean.TRUE);
    }


    /**
     * 确认导入商品
     */
    @Operation(summary = "确认导入商品")
    @Parameter(name = "ext", description = "后缀", required = true)
    @RequestMapping(value = "/import/{ext}/{goodsType}", method = RequestMethod.GET)
    public BaseResponse<Boolean> implGoods(@PathVariable String ext, @PathVariable Integer goodsType) {
        if(!(Constants.XLS.equalsIgnoreCase(ext) || Constants.XLSX.equalsIgnoreCase(ext))){
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }

        CompanyInfoVO companyInfo = companyInfoQueryProvider.getCompanyInfoById(
                CompanyInfoByIdRequest.builder().companyInfoId(commonUtil.getCompanyInfoId()).build()
        ).getContext();
        if(companyInfo == null){
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }

        GoodsSupplierExcelImportRequest importRequest = new GoodsSupplierExcelImportRequest();
        importRequest.setExt(ext);
        importRequest.setUserId(commonUtil.getOperatorId());
        importRequest.setCompanyInfoId(commonUtil.getCompanyInfoId());
        importRequest.setStoreId(commonUtil.getStoreId());
        importRequest.setCompanyType(companyInfo.getCompanyType());
        importRequest.setSupplierName(companyInfo.getSupplierName());
        importRequest.setType(StoreType.SUPPLIER);
        importRequest.setStoreType(companyInfo.getStoreType());
        importRequest.setGoodsType(goodsType);
        supplierGoodsExcelService.implGoods(importRequest);

        return BaseResponse.success(Boolean.TRUE);
    }

    /**
     * 查询需同步商品列表
     *
     * @param queryRequest 商品
     * @return 商品详情
     */
    @Operation(summary = "查询需同步商品列表")
    @RequestMapping(value = "/spus/need/syn", method = RequestMethod.POST)
    public BaseResponse<GoodsListNeedSynResponse> listNeedSyn(@RequestBody GoodsQueryNeedSynRequest queryRequest) {
        queryRequest.setCompanyInfoId(commonUtil.getCompanyInfoId());
        queryRequest.setStoreId(commonUtil.getStoreId());

        BaseResponse<GoodsListNeedSynResponse> goodsListNeedSynResponse = goodsQueryProvider.listNeedSyn(queryRequest);
        GoodsListNeedSynResponse response = goodsListNeedSynResponse.getContext();

        return BaseResponse.success(response);
    }

    /**
     * 查询需同步商品列表
     *
     * @param queryRequest 商品
     * @return 商品详情
     */
    @Operation(summary = "查询需同步商品列表")
    @RequestMapping(value = "/spus/need/syn/count", method = RequestMethod.POST)
    public BaseResponse<GoodsListNeedSynCountResponse> countNeedSyn(@RequestBody GoodsQueryNeedSynRequest queryRequest) {
        queryRequest.setCompanyInfoId(commonUtil.getCompanyInfoId());
        queryRequest.setStoreId(commonUtil.getStoreId());
        return goodsQueryProvider.countNeedSyn(queryRequest);
    }

    /**
     * 商品审核列表
     *
     * @param request 商品
     * @return 商品详情
     */
    @Operation(summary = "商品审核列表")
    @RequestMapping(value = "/audit/page", method = RequestMethod.POST)
    public BaseResponse<GoodsAuditPageResponse> list(@RequestBody GoodsAuditPageRequest request) {
        request.setCompanyInfoId(commonUtil.getCompanyInfoId());
        request.setStoreId(commonUtil.getStoreId());
        request.setDelFlag(DeleteFlag.NO.toValue());
        //按创建时间倒序、ID升序
        request.putSort("updateTime", SortType.DESC.toValue());
        return goodsAuditQueryProvider.page(request);
    }


    /**
     * 上传库存修改文件
     */
    @Operation(summary = "上传库存修改文件")
    @RequestMapping(value = "/excel/upload/stock", method = RequestMethod.POST)
    public BaseResponse<String> uploadForGoodsStock(@RequestParam("uploadFile") MultipartFile uploadFile) {
        Long companyInfoId = commonUtil.getCompanyInfoId();
        CompanyInfoVO companyInfo = companyInfoQueryProvider.getCompanyInfoById(
                CompanyInfoByIdRequest.builder().companyInfoId(companyInfoId).build()
        ).getContext();
        if (companyInfo == null) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }

        return BaseResponse.success(goodsStockExcelService.upload(uploadFile, commonUtil.getOperatorId()));
    }

    /**
     * 确认导入商品库存修改
     */
    @Operation(summary = "确认导入商品库存修改")
    @Parameter(name = "ext", description = "后缀", required = true)
    @RequestMapping(value = "/import/stock/{ext}", method = RequestMethod.GET)
    public BaseResponse<Boolean> implGoodsStock(@PathVariable String ext) {
        if(!(Constants.XLS.equalsIgnoreCase(ext) || Constants.XLSX.equalsIgnoreCase(ext))){
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        CompanyInfoVO companyInfo = companyInfoQueryProvider.getCompanyInfoById(
                CompanyInfoByIdRequest.builder().companyInfoId(commonUtil.getCompanyInfoId()).build()
        ).getContext();
        if(companyInfo == null){
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        goodsStockExcelService.implGoods(commonUtil.getOperatorId());

        return BaseResponse.success(Boolean.TRUE);
    }
}
