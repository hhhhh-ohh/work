package com.wanmi.sbc.iep;

import com.wanmi.sbc.common.annotation.MultiSubmit;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.enums.Platform;
import com.wanmi.sbc.common.enums.StoreType;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.*;
import com.wanmi.sbc.customer.api.provider.company.CompanyInfoQueryProvider;
import com.wanmi.sbc.customer.api.request.company.CompanyInfoByIdRequest;
import com.wanmi.sbc.customer.bean.vo.CompanyInfoVO;
import com.wanmi.sbc.elastic.api.provider.goods.EsGoodsInfoElasticProvider;
import com.wanmi.sbc.elastic.api.provider.standard.EsStandardProvider;
import com.wanmi.sbc.elastic.api.request.goods.EsGoodsInfoModifyAddedStatusRequest;
import com.wanmi.sbc.elastic.api.request.goods.EsGoodsInfoRequest;
import com.wanmi.sbc.elastic.api.request.goods.EsGoodsInitProviderGoodsInfoRequest;
import com.wanmi.sbc.elastic.api.request.standard.EsStandardInitRequest;
import com.wanmi.sbc.goods.api.provider.excel.GoodsSupplierExcelProvider;
import com.wanmi.sbc.goods.api.provider.goods.GoodsProvider;
import com.wanmi.sbc.goods.api.provider.goods.GoodsQueryProvider;
import com.wanmi.sbc.goods.api.provider.info.GoodsInfoProvider;
import com.wanmi.sbc.goods.api.provider.info.GoodsInfoQueryProvider;
import com.wanmi.sbc.goods.api.provider.standard.StandardGoodsQueryProvider;
import com.wanmi.sbc.goods.api.provider.storecate.StoreCateQueryProvider;
import com.wanmi.sbc.goods.api.request.excel.GoodsSupplierExcelExportTemplateIEPByStoreIdRequest;
import com.wanmi.sbc.goods.api.request.goods.*;
import com.wanmi.sbc.goods.api.request.info.GoodsInfoListByIdRequest;
import com.wanmi.sbc.goods.api.request.info.GoodsInfoModifyRequest;
import com.wanmi.sbc.goods.api.response.goods.GoodsAddResponse;
import com.wanmi.sbc.goods.api.response.goods.GoodsByIdResponse;
import com.wanmi.sbc.goods.api.response.goods.GoodsViewByIdResponse;
import com.wanmi.sbc.goods.bean.dto.GoodsInfoDTO;
import com.wanmi.sbc.goods.bean.enums.AddedFlag;
import com.wanmi.sbc.goods.bean.enums.GoodsErrorCodeEnum;
import com.wanmi.sbc.goods.bean.vo.GoodsInfoVO;
import com.wanmi.sbc.goods.bean.vo.GoodsVO;
import com.wanmi.sbc.goods.request.GoodsSupplierExcelImportRequest;
import com.wanmi.sbc.goods.service.GoodsBaseService;
import com.wanmi.sbc.goods.service.SupplierGoodsExcelService;
import com.wanmi.sbc.message.service.StoreMessageBizService;
import com.wanmi.sbc.setting.api.provider.AuditQueryProvider;
import com.wanmi.sbc.setting.api.request.GoodsSecondaryAuditRequest;
import com.wanmi.sbc.setting.bean.enums.ConfigType;
import com.wanmi.sbc.util.CommonUtil;
import com.wanmi.sbc.util.OperateLogMQUtil;
import io.seata.spring.annotation.GlobalTransactional;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 商品服务
 * Created by daiyitian on 17/4/12.
 */
@Slf4j
@Tag(name = "StoreGoodsForIEPController", description = "商品服务 API")
@RestController
@Validated
@RequestMapping("/iep/goods")
public class StoreGoodsForIEPController {

    @Autowired
    private GoodsProvider goodsProvider;

    @Autowired
    private GoodsSupplierExcelProvider goodsSupplierExcelProvider;

    @Autowired
    private CompanyInfoQueryProvider companyInfoQueryProvider;

    @Autowired
    private CommonUtil commonUtil;

    @Autowired
    private OperateLogMQUtil operateLogMQUtil;

    @Autowired
    private SupplierGoodsExcelService supplierGoodsExcelService;

    @Autowired
    private GoodsInfoProvider goodsInfoProvider;

    @Autowired
    private GoodsQueryProvider goodsQueryProvider;

    @Autowired
    private StoreCateQueryProvider storeCateQueryProvider;

    @Autowired
    private OsUtil osUtil;

    @Autowired
    private EsGoodsInfoElasticProvider esGoodsInfoElasticProvider;

    @Autowired
    private StandardGoodsQueryProvider standardGoodsQueryProvider;

    @Autowired
    private EsStandardProvider esStandardProvider;

    @Autowired
    private AuditQueryProvider auditQueryProvider;

    @Autowired
    private GoodsBaseService goodsBaseService;

    @Autowired
    private GoodsInfoQueryProvider goodsInfoQueryProvider;

    @Autowired
    private StoreMessageBizService storeMessageBizService;

    /**
     * 编辑商品SKU
     */
    @MultiSubmit
    @GlobalTransactional
    @Operation(summary = "编辑商品SKU")
    @RequestMapping(value = "/sku", method = RequestMethod.PUT)
    public ResponseEntity<BaseResponse> edit(@RequestBody GoodsInfoModifyRequest saveRequest) {
        if (saveRequest.getGoodsInfo() == null || saveRequest.getGoodsInfo().getGoodsInfoId() == null) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        boolean providerAudit = Objects.equals(commonUtil.getOperator().getPlatform(), Platform.PROVIDER) &&
                auditQueryProvider.isBossGoodsSecondaryAudit(GoodsSecondaryAuditRequest
                        .builder().configType(ConfigType.PROVIDER_GOODS_SECONDARY_AUDIT).build()).getContext().isAudit();
        if (providerAudit) {
            //获取商品详情
            GoodsViewByIdResponse response = goodsBaseService.info(saveRequest.getGoodsInfo().getGoodsId()).getContext();
            GoodsModifyRequest goodsModifyRequest = goodsBaseService.viewToGoodsModify(response, saveRequest);
            goodsBaseService.edit(goodsModifyRequest);
            GoodsInfoVO goodsInfoVO = goodsInfoQueryProvider
                    .getGoodsInfoById(GoodsInfoListByIdRequest.builder().goodsInfoId(saveRequest.getGoodsInfo().getGoodsInfoId()).build())
                    .getContext()
                    .getGoodsInfoVO();
            goodsInfoVO.setGoodsInfoNo(saveRequest.getGoodsInfo().getGoodsInfoNo());
            goodsInfoVO.setAddedTime(saveRequest.getGoodsInfo().getAddedTime());
            goodsInfoVO.setAddedFlag(saveRequest.getGoodsInfo().getAddedFlag());
            goodsInfoVO.setAddedTimingFlag(saveRequest.getGoodsInfo().getAddedTimingFlag());
            goodsInfoVO.setAddedTimingTime(saveRequest.getGoodsInfo().getAddedTimingTime());
            goodsInfoVO.setTakedownTime(saveRequest.getGoodsInfo().getTakedownTime());
            goodsInfoVO.setTakedownTimeFlag(saveRequest.getGoodsInfo().getTakedownTimeFlag());
            goodsInfoVO.setUpdateTime(LocalDateTime.now());
            saveRequest.setGoodsInfo(KsBeanUtil.convert(goodsInfoVO, GoodsInfoDTO.class));
        }
        //校验定时上下架
        goodsBaseService.checkAddAndTakedownTime(saveRequest.getGoodsInfo().getTakedownTime(), saveRequest.getGoodsInfo().getTakedownTimeFlag(),
                saveRequest.getGoodsInfo().getAddedTimingTime(), saveRequest.getGoodsInfo().getAddedTimingFlag());

        goodsInfoProvider.modify(saveRequest);
        GoodsByIdRequest goodsByIdRequest = new GoodsByIdRequest();
        goodsByIdRequest.setGoodsId(saveRequest.getGoodsInfo().getGoodsId());
        GoodsByIdResponse goodsByIdResponse = goodsQueryProvider.getById(goodsByIdRequest).getContext();
        if (goodsByIdResponse != null) {
            //刷新商品库es
            esStandardProvider.init(EsStandardInitRequest.builder()
                    .relGoodsIds(Collections.singletonList(saveRequest.getGoodsInfo().getGoodsId())).build());

            esGoodsInfoElasticProvider.initProviderEsGoodsInfo(EsGoodsInitProviderGoodsInfoRequest.builder().
                    storeId(null).providerGoodsIds(Collections.singletonList(goodsByIdRequest.getGoodsId())).build());
        }
        operateLogMQUtil.convertAndSend("商品", "编辑商品", "编辑商品：SKU编码" + saveRequest.getGoodsInfo().getGoodsInfoNo());

        // ============= 处理平台的消息发送：供应商编辑商品触发待审核 START =============
        storeMessageBizService.handleForProviderGoodsAudit(providerAudit, goodsByIdResponse);
        // ============= 处理平台的消息发送：供应商编辑商品触发待审核 END =============

        return ResponseEntity.ok(BaseResponse.SUCCESSFUL());
    }

    /**
     * 新增商品
     */
    @MultiSubmit
    @GlobalTransactional
    @Operation(summary = "新增商品")
    @RequestMapping(value = "/spu", method = RequestMethod.POST)
    public BaseResponse<String> add(@RequestBody @Valid GoodsAddRequest request) {
        log.info("商品新增开始provider");
        long startTime = System.currentTimeMillis();
        Platform platform = commonUtil.getOperator().getPlatform();
        if (!Platform.PROVIDER.equals(platform)) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K999998);
        }
        CompanyInfoVO companyInfo = companyInfoQueryProvider.getCompanyInfoById(
                CompanyInfoByIdRequest.builder().companyInfoId(commonUtil.getCompanyInfoId()).build()
        ).getContext();
        if (companyInfo == null) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        //如果是供应商商品，商品来源为0
        if(!NumberUtils.INTEGER_ZERO.equals(request.getGoods().getGoodsSource())) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        //商品名称不能为空
        if(StringUtils.isEmpty(request.getGoods().getGoodsName())) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        //运费模版不能为空
        if(Objects.isNull(request.getGoods().getFreightTempId())) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        request.getGoods().setCompanyInfoId(commonUtil.getCompanyInfoId());
        request.getGoods().setCompanyType(companyInfo.getCompanyType());
        request.getGoods().setStoreId(commonUtil.getStoreId());
        request.getGoods().setSupplierName(companyInfo.getSupplierName());
        BaseResponse<GoodsAddResponse> baseResponse = goodsProvider.add(request);
        GoodsAddResponse response = baseResponse.getContext();
        String goodsId = Optional.ofNullable(response)
                .map(GoodsAddResponse::getResult)
                .orElse(null);

        operateLogMQUtil.convertAndSend("商品", "直接发布",
                "直接发布：SPU编码" + request.getGoods().getGoodsNo());

        //供应商商品审核成功直接加入到商品库
        //store_cate_goods_rela为联合主键，seata不支持联合主键，放弃使用seata,,加入商品库操作移至goodsService.add()
        if (StringUtils.isNotBlank(goodsId)) {
            //初始化ES
            esGoodsInfoElasticProvider.initEsGoodsInfo(EsGoodsInfoRequest.builder().goodsId(goodsId).build());
        }
        log.info("商品新增结束provider->花费{}毫秒", (System.currentTimeMillis() - startTime));

        // ============= 处理平台的消息发送：供应商新增商品待审核 START =============
        storeMessageBizService.handleForProviderGoodsAudit(request, response);
        // ============= 处理平台的消息发送：供应商新增商品待审核 END =============
        return BaseResponse.success(goodsId);
    }

    /**
     * 编辑商品
     */
    @MultiSubmit
    @GlobalTransactional
    @Operation(summary = "编辑商品")
    @RequestMapping(value = "/spu", method = RequestMethod.PUT)
    public BaseResponse edit(@RequestBody @Valid GoodsModifyRequest request) {
        return goodsBaseService.providerEdit(request);
    }

    /**
     * 下载模板
     */
    @Operation(summary = "下载模板")
    @Parameter(name = "encrypted", description = "加密", required = true)
    @RequestMapping(value = "/excel/template/{encrypted}", method = RequestMethod.GET)
    public void template(@PathVariable String encrypted) {
        GoodsSupplierExcelExportTemplateIEPByStoreIdRequest request =
                new GoodsSupplierExcelExportTemplateIEPByStoreIdRequest();
        request.setStoreId(commonUtil.getStoreId());
        String file = goodsSupplierExcelProvider.supplierExportTemplateIEP(request).getContext().getFile();
        if (StringUtils.isNotBlank(file)) {
            try {
                String fileName = URLEncoder.encode("商品导入模板.xls", StandardCharsets.UTF_8.name());
                HttpUtil.getResponse().setHeader("Content-Disposition", String.format("attachment;filename=\"%s\";" +
                        "filename*=\"utf-8''%s\"", fileName, fileName));
                HttpUtil.getResponse()
                        .getOutputStream()
                        .write(Base64.getDecoder().decode(file));
            } catch (Exception e) {
                throw new SbcRuntimeException(CommonErrorCodeEnum.K000001);
            }
        }
    }

    /**
     * 确认导入商品
     */
    @MultiSubmit
    @Operation(summary = "确认导入商品")
    @Parameter(name = "ext", description = "后缀", required = true)
    @RequestMapping(value = "/import/{ext}", method = RequestMethod.GET)
    public BaseResponse<Boolean> implGoods(@PathVariable String ext) {
        if (!(Constants.XLS.equalsIgnoreCase(ext) || Constants.XLSX.equalsIgnoreCase(ext))) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }

        CompanyInfoVO companyInfo = companyInfoQueryProvider.getCompanyInfoById(
                CompanyInfoByIdRequest.builder().companyInfoId(commonUtil.getCompanyInfoId()).build()
        ).getContext();
        if (companyInfo == null) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }

        GoodsSupplierExcelImportRequest importRequest = new GoodsSupplierExcelImportRequest();
        importRequest.setExt(ext);
        importRequest.setUserId(commonUtil.getOperatorId());
        importRequest.setCompanyInfoId(commonUtil.getCompanyInfoId());
        importRequest.setStoreId(commonUtil.getStoreId());
        importRequest.setCompanyType(companyInfo.getCompanyType());
        importRequest.setSupplierName(companyInfo.getSupplierName());
        importRequest.setType(StoreType.PROVIDER);
        importRequest.setGoodsType(NumberUtils.INTEGER_ZERO);
        supplierGoodsExcelService.implGoods(importRequest);

        return BaseResponse.success(Boolean.TRUE);
    }

    /**
     * 批量上架商品
     */
    @MultiSubmit
    @GlobalTransactional
    @Operation(summary = "批量上架商品")
    @RequestMapping(value = "/spu/sale", method = RequestMethod.PUT)
    public BaseResponse onSale(@RequestBody GoodsModifyAddedStatusRequest request) {
        if (CollectionUtils.isEmpty(request.getGoodsIds())) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        //如果勾选了定时上架时间
        if (Boolean.TRUE.equals(request.getAddedTimingFlag())
                && request.getAddedTimingTime() != null) {
            //定时上架可选时间不早于现在，不能晚于1年后
            if (request.getAddedTimingTime().compareTo(LocalDateTime.now()) > 0 &&
                    request.getAddedTimingTime().compareTo(DateUtil.parseDayCanEmpty(DateUtil.afterOneYear())) < 0) {
                request.setAddedFlag(AddedFlag.NO.toValue());
            } else {
                throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
            }
        } else {
            request.setAddedFlag(AddedFlag.YES.toValue());
            request.setAddedTimingFlag(Boolean.FALSE);
        }

        //供应商商品上架
        goodsProvider.providerModifyAddedStatus(request);

        esStandardProvider.init(EsStandardInitRequest.builder().relGoodsIds(request.getGoodsIds()).build());
        //更新ES
        esGoodsInfoElasticProvider.updateAddedStatus(EsGoodsInfoModifyAddedStatusRequest.builder().
                addedFlag(request.getAddedFlag()).goodsIds(request.getGoodsIds()).goodsInfoIds(null).build());
        //更新关联的商家商品es
        esGoodsInfoElasticProvider.initProviderEsGoodsInfo(EsGoodsInitProviderGoodsInfoRequest.builder().
                storeId(null).providerGoodsIds(request.getGoodsIds()).build());

        if (Boolean.TRUE.equals(request.getAddedTimingFlag())){
            operateLogMQUtil.convertAndSend("商品", "批量定时上架", "批量定时上架");
        } else {
            if (1 == request.getGoodsIds().size()) {
                GoodsByIdRequest goodsByIdRequest = new GoodsByIdRequest();
                goodsByIdRequest.setGoodsId(request.getGoodsIds().get(0));
                GoodsByIdResponse response = goodsQueryProvider.getById(goodsByIdRequest).getContext();
                operateLogMQUtil.convertAndSend("供应商商品", "上架",
                        "上架：SPU编码" + response.getGoodsNo());
            } else {
                operateLogMQUtil.convertAndSend("供应商商品", "批量上架", "批量上架");
            }
        }
        return BaseResponse.SUCCESSFUL();
    }

    /**
     * 批量上下架商品
     */
    @MultiSubmit
    @GlobalTransactional
    @Operation(summary = "批量上下架商品")
    @RequestMapping(value = "/spu/sale", method = RequestMethod.POST)
    public BaseResponse onOrOffSale(@RequestBody GoodsModifyAddedStatusRequest request) {
        if (CollectionUtils.isEmpty(request.getGoodsIds())) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }

        List<GoodsVO> goodsVos = goodsQueryProvider
                .listByIds(GoodsListByIdsRequest.builder().goodsIds(request.getGoodsIds()).build())
                .getContext()
                .getGoodsVOList();

        if (CollectionUtils.isEmpty(goodsVos)){
            throw new SbcRuntimeException(GoodsErrorCodeEnum.K030035);
        }

        List<Long> storeIds = goodsVos
                .stream()
                .map(GoodsVO::getStoreId)
                .distinct()
                .collect(Collectors.toList());
        if (storeIds.size() != 1 || !Objects.equals(commonUtil.getStoreId(),storeIds.get(0))){
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000014);
        }

        //定时上架和定时下架
        if (Boolean.TRUE.equals(request.getAddedTimingFlag()) || Boolean.TRUE.equals(request.getTakedownTimeFlag())){
            //上下架时间校验
            goodsBaseService.checkAddAndTakedownTime(request.getTakedownTime(),request.getTakedownTimeFlag(),
                    request.getAddedTimingTime(),request.getAddedTimingFlag());

            String opContext = "";
            Integer addedFlag = AddedFlag.NO.toValue();
            //如果只选择了定时上架，商品先下架-再定时上架
            if (!Boolean.TRUE.equals(request.getTakedownTimeFlag())){
                addedFlag = AddedFlag.NO.toValue();
                opContext = "上架";
            }
            //如果只选择了定时下架，商品先上架-再定时下架
            if (!Boolean.TRUE.equals(request.getAddedTimingFlag())){
                addedFlag = AddedFlag.YES.toValue();
                opContext = "下架";
            }
            //即选择了定时上架也选择了定时下架
            if (Boolean.TRUE.equals(request.getTakedownTimeFlag()) && Boolean.TRUE.equals(request.getAddedTimingFlag())){
                //商品此时上下架状态取决于谁的时间靠前
                if (request.getAddedTimingTime().isBefore(request.getTakedownTime())){
                    addedFlag = AddedFlag.NO.toValue();
                }else {
                    addedFlag = AddedFlag.YES.toValue();
                }
                opContext = "上/下架";
            }

            request.setAddedFlag(addedFlag);
            goodsProvider.providerModifyAddedStatus(request);

            esStandardProvider.init(EsStandardInitRequest.builder().relGoodsIds(request.getGoodsIds()).build());

            //更新ES
            esGoodsInfoElasticProvider.updateAddedStatus(EsGoodsInfoModifyAddedStatusRequest.builder().
                    addedFlag(addedFlag).goodsIds(request.getGoodsIds()).goodsInfoIds(null).build());

            //更新关联的商家商品es
            esGoodsInfoElasticProvider.initProviderEsGoodsInfo(EsGoodsInitProviderGoodsInfoRequest.builder().
                    storeId(null).providerGoodsIds(request.getGoodsIds()).build());

            //更新ES
            esGoodsInfoElasticProvider.initEsGoodsInfo(EsGoodsInfoRequest.builder().goodsIds(request.getGoodsIds()).build());

            operateLogMQUtil.convertAndSend("商品", "批量定时上/下架", opContext);

            return BaseResponse.SUCCESSFUL();
        }else {
            //上架
            if ((request.getAddedFlag() != null && request.getAddedFlag() == AddedFlag.YES.toValue())){
                return this.onSale(request);
            }

            //下架
            if (request.getAddedFlag() != null && request.getAddedFlag() == AddedFlag.NO.toValue()){
                return this.offSale(request);
            }
        }

        return BaseResponse.SUCCESSFUL();
    }




    /**
     * 批量下架商品
     */
    @MultiSubmit
    @GlobalTransactional
    @Operation(summary = "批量下架商品")
    @RequestMapping(value = "/spu/sale", method = RequestMethod.DELETE)
    public BaseResponse offSale(@RequestBody GoodsModifyAddedStatusRequest request) {
        if (CollectionUtils.isEmpty(request.getGoodsIds())) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        request.setAddedFlag(AddedFlag.NO.toValue());
        goodsProvider.providerModifyAddedStatus(request);

        List<GoodsVO> goodsVos = goodsQueryProvider
                .listByIds(GoodsListByIdsRequest.builder().goodsIds(request.getGoodsIds()).build())
                .getContext()
                .getGoodsVOList();

        if (CollectionUtils.isEmpty(goodsVos)){
            throw new SbcRuntimeException(GoodsErrorCodeEnum.K030035);
        }

        List<Long> storeIds = goodsVos
                .stream()
                .map(GoodsVO::getStoreId)
                .distinct()
                .collect(Collectors.toList());
        if (storeIds.size() != 1 || !Objects.equals(commonUtil.getStoreId(),storeIds.get(0))){
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000014);
        }

        if (1 == request.getGoodsIds().size()) {
            GoodsByIdRequest goodsByIdRequest = new GoodsByIdRequest();
            goodsByIdRequest.setGoodsId(request.getGoodsIds().get(0));
            GoodsByIdResponse response = goodsQueryProvider.getById(goodsByIdRequest).getContext();
            operateLogMQUtil.convertAndSend("商品", "下架",
                    "下架：SPU编码" + response.getGoodsNo());
        } else {
            operateLogMQUtil.convertAndSend("商品", "批量下架", "批量下架");
        }
        esStandardProvider.init(EsStandardInitRequest.builder().relGoodsIds(request.getGoodsIds()).build());
        //更新ES
        esGoodsInfoElasticProvider.updateAddedStatus(EsGoodsInfoModifyAddedStatusRequest.builder().
                addedFlag(AddedFlag.NO.toValue()).goodsIds(request.getGoodsIds()).goodsInfoIds(null).build());
        //更新关联的商家商品es
        esGoodsInfoElasticProvider.initProviderEsGoodsInfo(EsGoodsInitProviderGoodsInfoRequest.builder().
                storeId(null).providerGoodsIds(request.getGoodsIds()).build());
        return BaseResponse.SUCCESSFUL();
    }
}
