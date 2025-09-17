package com.wanmi.sbc.goods;

import com.google.common.collect.Lists;
import com.wanmi.sbc.common.annotation.MultiSubmit;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.enums.SortType;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.customer.api.provider.store.StoreQueryProvider;
import com.wanmi.sbc.customer.api.request.store.ListNoDeleteStoreByIdsRequest;
import com.wanmi.sbc.customer.api.response.store.ListNoDeleteStoreByIdsResponse;
import com.wanmi.sbc.customer.bean.enums.CheckState;
import com.wanmi.sbc.customer.bean.vo.StoreVO;
import com.wanmi.sbc.elastic.api.provider.goods.EsCateBrandProvider;
import com.wanmi.sbc.elastic.api.provider.goods.EsGoodsBrandProvider;
import com.wanmi.sbc.elastic.api.provider.goods.EsGoodsBrandQueryProvider;
import com.wanmi.sbc.elastic.api.provider.goods.EsGoodsInfoElasticProvider;
import com.wanmi.sbc.elastic.api.request.goods.EsBrandDeleteByIdsRequest;
import com.wanmi.sbc.elastic.api.request.goods.EsBrandUpdateRequest;
import com.wanmi.sbc.elastic.api.request.goods.EsGoodsBrandPageRequest;
import com.wanmi.sbc.elastic.api.request.goods.EsGoodsBrandSaveRequest;
import com.wanmi.sbc.goods.api.provider.brand.ContractBrandAuditQueryProvider;
import com.wanmi.sbc.goods.api.provider.brand.ContractBrandQueryProvider;
import com.wanmi.sbc.goods.api.provider.brand.GoodsBrandProvider;
import com.wanmi.sbc.goods.api.provider.brand.GoodsBrandQueryProvider;
import com.wanmi.sbc.goods.api.request.brand.*;
import com.wanmi.sbc.goods.api.response.brand.ContractBrandAuditListResponse;
import com.wanmi.sbc.goods.api.response.brand.GoodsBrandByIdResponse;
import com.wanmi.sbc.goods.api.response.brand.GoodsBrandResponse;
import com.wanmi.sbc.goods.bean.enums.GoodsErrorCodeEnum;
import com.wanmi.sbc.goods.bean.vo.ContractBrandVO;
import com.wanmi.sbc.goods.bean.vo.GoodsBrandSimpleVO;
import com.wanmi.sbc.goods.bean.vo.GoodsBrandVO;
import com.wanmi.sbc.goods.service.GoodsBrandExcelService;
import com.wanmi.sbc.util.CommonUtil;
import com.wanmi.sbc.util.OperateLogMQUtil;
import io.seata.spring.annotation.GlobalTransactional;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.ListUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Author: songhanlin
 * @Date: Created In 上午10:05 2017/11/1
 * @Description: 商品品牌Controller
 */
@Slf4j
@Tag(name =  "商品品牌API", description =  "BossGoodsBrandController")
@RestController("bossGoodsBrandController")
@RequestMapping("/goods")
@Validated
public class BossGoodsBrandController {

    @Autowired
    private GoodsBrandQueryProvider goodsBrandQueryProvider;

    @Autowired
    private GoodsBrandProvider goodsBrandProvider;

    @Autowired
    private ContractBrandQueryProvider contractBrandQueryProvider;

    @Autowired
    private StoreQueryProvider storeQueryProvider;

    @Autowired
    private EsGoodsInfoElasticProvider esGoodsInfoElasticProvider;

    @Autowired
    private OperateLogMQUtil operateLogMQUtil;

    @Autowired
    private CommonUtil commonUtil;

    @Autowired
    private EsGoodsBrandQueryProvider esGoodsBrandQueryProvider;

    @Autowired
    private EsGoodsBrandProvider esGoodsBrandProvider;

    @Autowired
    private EsCateBrandProvider esCateBrandProvider;

    @Autowired
    private ContractBrandAuditQueryProvider contractBrandAuditQueryProvider;

    @Autowired
    private GoodsBrandExcelService goodsBrandExcelService;

    /**
     * 分页商品品牌
     *
     * @param queryRequest 商品品牌参数
     * @return 商品详情
     */
    @Operation(summary = "分页商品品牌")
    @RequestMapping(value = "/goodsBrands", method = RequestMethod.POST)
    public BaseResponse<Page<GoodsBrandResponse>> page(@RequestBody GoodsBrandPageRequest queryRequest) {
        queryRequest.setDelFlag(DeleteFlag.NO.toValue());

        //如果前端有设置按照排序号排序
        if(null != queryRequest.getSortRole()){
            queryRequest.putSort("brandSort", queryRequest.getSortRole());
            queryRequest.putSort("createTime", SortType.DESC.toValue());
        }else{
            queryRequest.putSort("createTime", SortType.DESC.toValue());
            queryRequest.putSort("brandId", SortType.ASC.toValue());
        }


        //分页查询es
        EsGoodsBrandPageRequest pageRequest = EsGoodsBrandPageRequest.builder().build();
        BeanUtils.copyProperties(queryRequest, pageRequest);
     //   pageRequest.setPageSize(CommonConstant.ES_MAX_SIZE);
        MicroServicePage<GoodsBrandVO> page = esGoodsBrandQueryProvider.page(pageRequest).getContext().getGoodsBrandPage();

        List<ContractBrandVO> contractBrands;
        if (page.getTotalElements() > 0L) {
            List<Long> brandIds = page.getContent().stream().map(GoodsBrandVO::getBrandId).collect(Collectors.toList());
            ContractBrandListRequest contractBrandQueryRequest = new ContractBrandListRequest();
            contractBrandQueryRequest.setGoodsBrandIds(brandIds);
            contractBrands =
                    contractBrandQueryProvider.list(contractBrandQueryRequest).getContext().getContractBrandVOList();
        } else {
            contractBrands = new ArrayList<>();
        }

        List<GoodsBrandResponse> goodsBrandResponses = new ArrayList<>();

        Map<Long, String> storeNameMap = new HashMap<>();
        List<Long> storeIds =
                contractBrands.stream()
                        .map(ContractBrandVO::getStoreId)
                        .collect(Collectors.toList());
        if (!storeIds.isEmpty()) {
            ListNoDeleteStoreByIdsResponse storeByIdsResponse = storeQueryProvider
                    .listNoDeleteStoreByIds(
                            ListNoDeleteStoreByIdsRequest.builder()
                                    .storeIds(storeIds)
                                    .build())
                    .getContext();
            List<StoreVO> stores = storeByIdsResponse.getStoreVOList();
            if (CollectionUtils.isNotEmpty(stores)) {

                        stores.stream()
                                .filter(store -> CheckState.CHECKED.equals(store.getAuditState()))
                                .forEach(storeVO -> storeNameMap.put(storeVO.getStoreId(),storeVO.getSupplierName()));
            }
        }
        Map<Long, String> finalStoreNameMap = storeNameMap;
        page.getContent()
                .forEach(
                        info -> {
                            GoodsBrandResponse goodsBrandResponse = new GoodsBrandResponse();
                            BeanUtils.copyProperties(info, goodsBrandResponse);
                            List<Long> ids =
                                    contractBrands.stream()
                                            .filter(
                                                    contractBrand ->
                                                            info.getBrandId()
                                                                    .equals(
                                                                            contractBrand
                                                                                    .getGoodsBrand()
                                                                                    .getBrandId()))
                                            .map(ContractBrandVO::getStoreId)
                                            .collect(Collectors.toList());
                            if (!ids.isEmpty()) {
                                List<String> names = new ArrayList<>();
                                for (Long id : ids) {
                                    String name = finalStoreNameMap.get(id);
                                    if (StringUtils.isNotEmpty(name)) {
                                        names.add(name);
                                    }
                                }
                                goodsBrandResponse.setSupplierNames(
                                        StringUtils.join(names.toArray(), ","));
                            }
                            goodsBrandResponses.add(goodsBrandResponse);
                        });
        return BaseResponse.success(new PageImpl<>(goodsBrandResponses, queryRequest.getPageable(),
                page.getTotalElements()));
    }


    /**
     * 获取商品品牌详情信息
     *
     * @param brandId 商品品牌编号
     * @return 商品详情
     */
    @Operation(summary = "获取商品品牌详情信息")
    @Parameter(name = "brandId", description = "商品品牌编号", required = true)
    @RequestMapping(value = "/goodsBrand/{brandId}", method = RequestMethod.GET)
    public BaseResponse<GoodsBrandVO> list(@PathVariable Long brandId) {
        if (brandId == null) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        return BaseResponse.success(goodsBrandQueryProvider.getById(GoodsBrandByIdRequest.builder().brandId(brandId)
                .build()).getContext());
    }

  /**
   * 设置品牌是否推荐
   *
   * @param saveRequest
   * @return
   */
  @Operation(summary = "设置品牌是否推荐")
  @RequestMapping(value = "/brand-recommend-edit", method = RequestMethod.POST)
  @GlobalTransactional
  public BaseResponse setRecommend(@Valid @RequestBody GoodsBrandSaveRequest saveRequest) {

    if (saveRequest.getGoodsBrand() == null
        || saveRequest.getGoodsBrand().getBrandId() == null
        || saveRequest.getGoodsBrand().getRecommendFlag() == null) {
      throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
    }

    GoodsBrandByIdResponse goodsBrandByIdResponse =
        goodsBrandQueryProvider
            .getById(
                GoodsBrandByIdRequest.builder()
                    .brandId(saveRequest.getGoodsBrand().getBrandId())
                    .build())
            .getContext();

    if (Objects.isNull(goodsBrandByIdResponse)) {
      throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
    }

    GoodsBrandModifyRequest request = new GoodsBrandModifyRequest();
    KsBeanUtil.copyPropertiesThird(goodsBrandByIdResponse, request);

    // 设置品牌推荐
    request.setRecommendFlag(saveRequest.getGoodsBrand().getRecommendFlag());

    GoodsBrandVO oldGoodsBrand = goodsBrandProvider.modify(request).getContext();
    esCateBrandProvider.updateBrandFromEs(
        EsBrandUpdateRequest.builder().isDelete(false).goodsBrand(oldGoodsBrand).build());

    // 同步es
    EsGoodsBrandSaveRequest editRequest =
        EsGoodsBrandSaveRequest.builder()
            .goodsBrandVOList(Lists.newArrayList(oldGoodsBrand))
            .build();
    esGoodsBrandProvider.addGoodsBrandList(editRequest);

    operateLogMQUtil.convertAndSend("商品", request.getRecommendFlag() == DefaultFlag.YES
            ? "设置推荐" : "取消推荐", "推荐品牌：" + oldGoodsBrand.getBrandName());
    return BaseResponse.SUCCESSFUL();
  }

  /**
   * 设置品牌排序
   *
   * @param saveRequest
   * @return
   */
  @Operation(summary = "设置品牌排序")
  @RequestMapping(value = "/brand-sort-edit", method = RequestMethod.POST)
  @GlobalTransactional
  public BaseResponse setBrandSort(@Valid @RequestBody GoodsBrandSaveRequest saveRequest) {

    Long sort = saveRequest.getGoodsBrand().getBrandSort();

    if (saveRequest.getGoodsBrand() == null ||
            saveRequest.getGoodsBrand().getBrandId() == null ||
            (!Objects.isNull(sort) && (sort < 0 || sort > 99999999))
    ) {
      throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
    }

    GoodsBrandByIdResponse goodsBrandByIdResponse =
        goodsBrandQueryProvider
            .getById(
                GoodsBrandByIdRequest.builder()
                    .brandId(saveRequest.getGoodsBrand().getBrandId())
                    .build())
            .getContext();

    if (Objects.isNull(goodsBrandByIdResponse)) {
      throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
    }

    GoodsBrandModifyRequest request = new GoodsBrandModifyRequest();
    KsBeanUtil.copyPropertiesThird(goodsBrandByIdResponse, request);

    // 设置品牌排序
    request.setBrandSort(sort == null ? 0L : sort);

    GoodsBrandVO oldGoodsBrand = goodsBrandProvider.modify(request).getContext();
    esCateBrandProvider.updateBrandFromEs(
        EsBrandUpdateRequest.builder().isDelete(false).goodsBrand(oldGoodsBrand).build());

    // 同步es
    EsGoodsBrandSaveRequest editRequest =
        EsGoodsBrandSaveRequest.builder()
            .goodsBrandVOList(Lists.newArrayList(oldGoodsBrand))
            .build();
    esGoodsBrandProvider.addGoodsBrandList(editRequest);

    operateLogMQUtil.convertAndSend("商品", "设置排序", "排序品牌：" + oldGoodsBrand.getBrandName());
    return BaseResponse.SUCCESSFUL();
  }

    /**
     * 删除商品品牌
     */
    @MultiSubmit
    @GlobalTransactional
    @Operation(summary = "删除商品品牌")
    @Parameter(name = "brandId", description = "商品品牌编号", required = true)
    @RequestMapping(value = "/goodsBrand/{brandId}", method = RequestMethod.DELETE)
    public BaseResponse delete(@PathVariable Long brandId) {
        long startTime = System.currentTimeMillis();
        log.info("开始时间：{}",startTime);
        if (Objects.isNull(brandId)) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }

        //校验是否有二次签约的品牌信息
        ContractBrandAuditListResponse contractBrandAuditListResponse = contractBrandAuditQueryProvider.list(
                ContractBrandAuditListRequest.builder().goodsBrandIds(Arrays.asList(brandId)).build()).getContext();

        if(CollectionUtils.isNotEmpty(contractBrandAuditListResponse.getContractBrandVOList())){
            throw new SbcRuntimeException(GoodsErrorCodeEnum.K030014);
        }

        GoodsBrandVO goodsBrand = goodsBrandProvider.delete(
                GoodsBrandDeleteByIdRequest.builder().brandId(brandId).build()
        ).getContext();
        esGoodsInfoElasticProvider.delBrandIds(EsBrandDeleteByIdsRequest.builder().deleteIds(
                Collections.singletonList(goodsBrand.getBrandId())).storeId(null).build());

        log.info("结束时间：{}",System.currentTimeMillis()-startTime);
        /*
         *  查询条件
         */
        EsGoodsBrandPageRequest pageRequest = EsGoodsBrandPageRequest.builder()
                .brandIds(Collections.singletonList(brandId))
                .delFlag(NumberUtils.INTEGER_ZERO)
                .build();

        MicroServicePage<GoodsBrandVO> page = esGoodsBrandQueryProvider.page(pageRequest).getContext().getGoodsBrandPage();
        List<GoodsBrandVO> newGoodsBrandVOList = page.getContent();
        if (CollectionUtils.isNotEmpty(newGoodsBrandVOList)) {
            List<GoodsBrandVO> delGoodsBrandVOList = newGoodsBrandVOList.stream().peek(entity -> entity.setDelFlag(DeleteFlag.YES)).collect(Collectors.toList());
            EsGoodsBrandSaveRequest request = EsGoodsBrandSaveRequest.builder().goodsBrandVOList(delGoodsBrandVOList).build();
            esGoodsBrandProvider.addGoodsBrandList(request);
        }

        //操作日志记录
        operateLogMQUtil.convertAndSend("商品", "删除品牌", "删除品牌：" + goodsBrand.getBrandName());
        return BaseResponse.SUCCESSFUL();
    }


    @Operation(summary = "分页商品品牌")
    @RequestMapping(value = "/goodsBrands/page", method = RequestMethod.POST)
    public BaseResponse<List<GoodsBrandSimpleVO>> brandPageList(@RequestBody GoodsBrandPageRequest queryRequest) {
        queryRequest.setDelFlag(DeleteFlag.NO.toValue());
        MicroServicePage<GoodsBrandVO> page =
                goodsBrandQueryProvider.page(queryRequest).getContext().getGoodsBrandPage();
        List<GoodsBrandSimpleVO> brandList =null;
        if (page.getTotalElements() > 0L) {
            brandList = KsBeanUtil.convert(page.getContent(), GoodsBrandSimpleVO.class);
        }
        return BaseResponse.success(ListUtils.emptyIfNull(brandList));

    }

    /**
     * 下载模板
     */
    @Operation(summary = "下载模板")
    @RequestMapping(value = "/goodsBrand/excel/template/{encrypted}", method = RequestMethod.GET)
    public void template(@PathVariable String encrypted) {
        goodsBrandExcelService.exportTemplate();
    }


    /**
     * 下载错误文档
     */
    @Operation(summary = "下载错误文档")
    @RequestMapping(value = "/goodsBrand/excel/err/{ext}/{decrypted}", method = RequestMethod.GET)
    public void downErrExcel(@PathVariable String ext, @PathVariable String decrypted) {
        if (!(Constants.XLS.equalsIgnoreCase(ext) || Constants.XLSX.equalsIgnoreCase(ext))) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        goodsBrandExcelService.downErrExcel(commonUtil.getOperatorId(), ext);
    }

    /**
     * 上传模板
     *
     * @param uploadFile
     * @return
     */
    @Operation(summary = "上传模板")
    @RequestMapping(value = "/goodsBrand/excel/upload", method = RequestMethod.POST)
    public BaseResponse<String> upload(@RequestParam("uploadFile") MultipartFile uploadFile) {
        return BaseResponse.success(goodsBrandExcelService.upload(uploadFile, commonUtil.getOperatorId()));
    }

    /**
     * 品牌导入
     *
     * @param ext
     * @return
     */
    @Operation(summary = "品牌导入")
    @RequestMapping(value = "/goodsBrand/import/{ext}", method = RequestMethod.GET)
    public BaseResponse<Boolean> importGoodsCate(@PathVariable String ext) {
        if (!(Constants.XLS.equalsIgnoreCase(ext) || Constants.XLSX.equalsIgnoreCase(ext))) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        goodsBrandExcelService.importGoodsBrand(commonUtil.getOperatorId());
        return BaseResponse.success(Boolean.TRUE);
    }

}
