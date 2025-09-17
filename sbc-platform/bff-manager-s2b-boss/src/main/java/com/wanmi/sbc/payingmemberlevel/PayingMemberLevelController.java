package com.wanmi.sbc.payingmemberlevel;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.google.common.collect.ImmutableMap;
import com.wanmi.ares.request.export.ExportDataRequest;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.BoolFlag;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.enums.MarketingAllType;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.customer.api.provider.levelrights.CustomerLevelRightsQueryProvider;
import com.wanmi.sbc.customer.api.provider.payingmemberlevel.PayingMemberLevelProvider;
import com.wanmi.sbc.customer.api.provider.payingmemberlevel.PayingMemberLevelQueryProvider;
import com.wanmi.sbc.customer.api.request.levelrights.CustomerLevelRightsQueryRequest;
import com.wanmi.sbc.customer.api.request.payingmemberdiscountrel.PayingMemberDiscountRelAddRequest;
import com.wanmi.sbc.customer.api.request.payingmemberdiscountrel.PayingMemberDiscountRelModifyRequest;
import com.wanmi.sbc.customer.api.request.payingmemberlevel.*;
import com.wanmi.sbc.customer.api.request.payingmemberrecommendrel.PayingMemberRecommendRelAddRequest;
import com.wanmi.sbc.customer.api.request.payingmemberrecommendrel.PayingMemberRecommendRelModifyRequest;
import com.wanmi.sbc.customer.api.request.payingmemberrightsrel.PayingMemberRightsRelAddRequest;
import com.wanmi.sbc.customer.api.request.payingmemberrightsrel.PayingMemberRightsRelModifyRequest;
import com.wanmi.sbc.customer.api.request.payingmemberstorerel.PayingMemberStoreRelAddRequest;
import com.wanmi.sbc.customer.api.request.payingmemberstorerel.PayingMemberStoreRelModifyRequest;
import com.wanmi.sbc.customer.api.response.payingmemberlevel.*;
import com.wanmi.sbc.customer.bean.enums.LevelRightsType;
import com.wanmi.sbc.customer.bean.vo.CustomerLevelRightsVO;
import com.wanmi.sbc.customer.bean.vo.PayingMemberDiscountRelVO;
import com.wanmi.sbc.customer.bean.vo.PayingMemberLevelVO;
import com.wanmi.sbc.customer.bean.vo.PayingMemberRecommendRelVO;
import com.wanmi.sbc.elastic.api.provider.sku.EsSkuQueryProvider;
import com.wanmi.sbc.elastic.api.request.sku.EsSkuPageRequest;
import com.wanmi.sbc.elastic.api.response.sku.EsSkuPageResponse;
import com.wanmi.sbc.goods.api.provider.info.GoodsInfoQueryProvider;
import com.wanmi.sbc.goods.api.request.info.GoodsInfoListByIdsRequest;
import com.wanmi.sbc.goods.bean.vo.GoodsInfoVO;
import com.wanmi.sbc.goods.service.GoodsBaseService;
import com.wanmi.sbc.payingmemberlevel.excel.PayingMemberExcelService;
import com.wanmi.sbc.payingmemberlevel.excel.PayingMemberTemplateRequest;
import com.wanmi.sbc.payingmemberlevel.excel.impl.DiscountExcelServiceImpl;
import com.wanmi.sbc.payingmemberlevel.excel.impl.RecommendExcelServiceImpl;
import com.wanmi.sbc.util.CommonUtil;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

@Tag(name =  "付费会员等级表管理API", description =  "PayingMemberLevelController")
@RestController
@Validated
@RequestMapping(value = "/payingmemberlevel")
public class PayingMemberLevelController {

    @Autowired
    private Map<String, PayingMemberExcelService> payingMemberExcelServiceMap;

    private static final Map<MarketingAllType, Class<?>> CLASS = ImmutableMap.<MarketingAllType, Class<?>> builder()
            .put(MarketingAllType.PAYING_MEMBER_DISCOUNT, DiscountExcelServiceImpl.class)
            .put(MarketingAllType.PAYING_MEMBER_RECOMMEND, RecommendExcelServiceImpl.class)
            .build();

    @Autowired
    private PayingMemberLevelQueryProvider payingMemberLevelQueryProvider;

    @Autowired
    private PayingMemberLevelProvider payingMemberLevelProvider;

    @Autowired
    private CommonUtil commonUtil;

    @Autowired
    private GoodsInfoQueryProvider goodsInfoQueryProvider;

    @Autowired
    private CustomerLevelRightsQueryProvider customerLevelRightsQueryProvider;

    @Autowired
    private EsSkuQueryProvider esSkuQueryProvider;

    @Autowired
    private GoodsBaseService goodsBaseService;


    @Operation(summary = "分页查询付费会员等级表")
    @PostMapping("/page")
    public BaseResponse<PayingMemberLevelPageResponse> getPage(@RequestBody @Valid PayingMemberLevelPageRequest pageReq) {
        pageReq.setDelFlag(DeleteFlag.NO);
        pageReq.putSort("levelId", "desc");
        return payingMemberLevelQueryProvider.page(pageReq);
    }

    @Operation(summary = "列表查询付费会员等级表")
    @PostMapping("/list")
    public BaseResponse<PayingMemberLevelListResponse> getList(@RequestBody @Valid PayingMemberLevelListRequest listReq) {
        listReq.setDelFlag(DeleteFlag.NO);
        return payingMemberLevelQueryProvider.list(listReq);
    }

    @Operation(summary = "根据id查询付费会员等级表")
    @GetMapping("/{levelId}")
    public BaseResponse<PayingMemberLevelByIdResponse> getById(@PathVariable Integer levelId) {
        if (levelId == null) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        PayingMemberLevelByIdRequest idReq = new PayingMemberLevelByIdRequest();
        idReq.setLevelId(levelId);
        idReq.setCustomer(false);
        PayingMemberLevelVO payingMemberLevelVO = payingMemberLevelQueryProvider.getById(idReq).getContext().getPayingMemberLevelVO();
        // 如果存在自定义折扣商品，则实时查询商品数据
        if (CollectionUtils.isNotEmpty(payingMemberLevelVO.getPayingMemberDiscountRelVOS())) {
            List<PayingMemberDiscountRelVO> payingMemberDiscountRelVOS = payingMemberLevelVO.getPayingMemberDiscountRelVOS();
            List<String> discountSkuIds = payingMemberDiscountRelVOS.parallelStream().map(PayingMemberDiscountRelVO::getGoodsInfoId)
                    .collect(Collectors.toList());
            List<GoodsInfoVO> goodsInfos = getGoodsInfoList(discountSkuIds);
            payingMemberDiscountRelVOS = goodsInfos.parallelStream().map(goodsInfoVO -> {
                Optional<PayingMemberDiscountRelVO> optional = payingMemberLevelVO.getPayingMemberDiscountRelVOS().parallelStream()
                        .filter(payingMemberDiscountRelVO -> payingMemberDiscountRelVO.getGoodsInfoId().equals(goodsInfoVO.getGoodsInfoId()))
                        .findFirst();
                PayingMemberDiscountRelVO payingMemberDiscountRelVO = null;
                if (optional.isPresent()) {
                    payingMemberDiscountRelVO = optional.get();
                    payingMemberDiscountRelVO.setGoodsInfoVO(goodsInfoVO);
                }
                return payingMemberDiscountRelVO;
            }).filter(Objects::nonNull).collect(Collectors.toList());
            payingMemberLevelVO.setPayingMemberDiscountRelVOS(payingMemberDiscountRelVOS);
        }
        //实时查询推荐商品信息
        List<PayingMemberRecommendRelVO> payingMemberRecommendRelVOS = payingMemberLevelVO.getPayingMemberRecommendRelVOS();
        List<String> recommendSkuIds = payingMemberRecommendRelVOS.parallelStream().map(PayingMemberRecommendRelVO::getGoodsInfoId).collect(Collectors.toList());
        List<GoodsInfoVO> recommendSkuList = getGoodsInfoList(recommendSkuIds);
        payingMemberRecommendRelVOS = recommendSkuList.parallelStream().map(goodsInfoVO -> {
            Optional<PayingMemberRecommendRelVO> optional = payingMemberLevelVO.getPayingMemberRecommendRelVOS().parallelStream()
                    .filter(payingMemberRecommendRelVO -> payingMemberRecommendRelVO.getGoodsInfoId().equals(goodsInfoVO.getGoodsInfoId()))
                    .findFirst();
            PayingMemberRecommendRelVO payingMemberRecommendRelVO = null;
            if (optional.isPresent()) {
                payingMemberRecommendRelVO = optional.get();
                payingMemberRecommendRelVO.setGoodsInfoVO(goodsInfoVO);
            }
            return payingMemberRecommendRelVO;
        }).filter(Objects::nonNull).collect(Collectors.toList());
        payingMemberLevelVO.setPayingMemberRecommendRelVOS(payingMemberRecommendRelVOS);
        return BaseResponse.success(PayingMemberLevelByIdResponse.builder()
                .payingMemberLevelVO(payingMemberLevelVO)
                .build());
    }

    @Operation(summary = "新增付费会员等级表")
    @PostMapping("/add")
    public BaseResponse add(@RequestBody @Valid PayingMemberLevelAddRequest addReq) {
        checkLevel(addReq);
        addReq.setDelFlag(DeleteFlag.NO);
        addReq.setCreatePerson(commonUtil.getOperatorId());
        return payingMemberLevelProvider.add(addReq);
    }

    @Operation(summary = "修改付费会员等级表")
    @PutMapping("/modify")
    public BaseResponse<PayingMemberLevelModifyResponse> modify(@RequestBody @Valid PayingMemberLevelModifyRequest modifyReq) {
        checkLevel(modifyReq);
        modifyReq.setUpdatePerson(commonUtil.getOperatorId());
        return payingMemberLevelProvider.modify(modifyReq);
    }

    @Operation(summary = "根据id删除付费会员等级表")
    @DeleteMapping("/{levelId}")
    public BaseResponse deleteById(@PathVariable Integer levelId) {
        if (levelId == null) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        PayingMemberLevelDelByIdRequest delByIdReq = new PayingMemberLevelDelByIdRequest();
        delByIdReq.setLevelId(levelId);
        return payingMemberLevelProvider.deleteById(delByIdReq);
    }

    @Operation(summary = "根据idList批量删除付费会员等级表")
    @DeleteMapping("/delete-by-id-list")
    public BaseResponse deleteByIdList(@RequestBody @Valid PayingMemberLevelDelByIdListRequest delByIdListReq) {
        return payingMemberLevelProvider.deleteByIdList(delByIdListReq);
    }

    @Operation(summary = "导出付费会员等级表列表")
//    @GetMapping("/export/{encrypted}")
    public BaseResponse exportData(@PathVariable String encrypted) {
        String decrypted = new String(Base64.getUrlDecoder().decode(encrypted.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
        PayingMemberLevelExportRequest listReq = JSON.parseObject(decrypted, PayingMemberLevelExportRequest.class);

        ExportDataRequest exportDataRequest = new ExportDataRequest();
        exportDataRequest.setParam(JSONObject.toJSONString(listReq));
//FIXME: 需要手动添加ReportType里面的枚举值, 替换掉下面的ReportType.XXXXXXX, 并放开注释, 实现方法的调用
//        exportDataRequest.setTypeCd(ReportType.XXXXXXX);
//        exportCenter.sendExport(exportDataRequest);
        return BaseResponse.SUCCESSFUL();
    }

    /**
     * 检查校验等级参数
     * @param object
     */
    private void checkLevel(Object object) {
        if (object instanceof PayingMemberLevelAddRequest) {
            PayingMemberLevelAddRequest request = ((PayingMemberLevelAddRequest) object);
            request.getPayingMemberPriceAddRequests().forEach(payingMemberPriceAddRequest -> {
                List<Integer> rightsIds = payingMemberPriceAddRequest.getPayingMemberRightsRelAddRequests()
                        .parallelStream().map(PayingMemberRightsRelAddRequest::getRightsId).collect(Collectors.toList());
                //查询付费设置下的权益
                List<CustomerLevelRightsVO> customerLevelRightsVOList = customerLevelRightsQueryProvider.list(CustomerLevelRightsQueryRequest.builder()
                        .rightsIdList(rightsIds)
                        .delFlag(DeleteFlag.NO)
                        .build()).getContext().getCustomerLevelRightsVOList();
                //根据权益类型去重
                Set<LevelRightsType> rightsTypes = customerLevelRightsVOList.parallelStream()
                        .map(CustomerLevelRightsVO::getRightsType).collect(Collectors.toSet());
                //不相等，则权益类型重复，参数错误
                if (customerLevelRightsVOList.size() != rightsTypes.size()) {
                    throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
                }
            });
            if (request.getLevelStoreRange() == Constants.ZERO) { // 自营商家
                    if (request.getLevelDiscountType() == Constants.ZERO) { //付费会员等级折扣类型：0.所有商品统一设置
                        //获取推荐商品列表
                        List<PayingMemberRecommendRelAddRequest> payingMemberRecommendRelAddRequests = request.getPayingMemberRecommendRelAddRequests();
                        checkSku(payingMemberRecommendRelAddRequests.stream()
                                .map(PayingMemberRecommendRelAddRequest::getGoodsInfoId).collect(Collectors.toList()));
                    } else if (request.getLevelDiscountType() == Constants.ONE) {  //付费会员等级折扣类型：1.自定义商品设置
                        //获取折扣商品列表
                        List<PayingMemberDiscountRelAddRequest> payingMemberDiscountRelAddRequests = request.getPayingMemberDiscountRelAddRequests();
                        List<String> discountSkuIds = payingMemberDiscountRelAddRequests.stream()
                                .map(PayingMemberDiscountRelAddRequest::getGoodsInfoId).collect(Collectors.toList());
                        checkSku(discountSkuIds);
                        //获取推荐商品列表
                        List<PayingMemberRecommendRelAddRequest> payingMemberRecommendRelAddRequests = request.getPayingMemberRecommendRelAddRequests();
                        List<String> recommendSkuIds = payingMemberRecommendRelAddRequests.stream()
                                .map(PayingMemberRecommendRelAddRequest::getGoodsInfoId).collect(Collectors.toList());
                        //如果推荐商品列表不在折扣商品列表内，则参数错误
                        if (!discountSkuIds.containsAll(recommendSkuIds)) {
                            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
                        }
                    }

            } else if (request.getLevelStoreRange() == Constants.ONE) {  // 自定义商家
                List<PayingMemberStoreRelAddRequest> payingMemberStoreRelAddRequests = request.getPayingMemberStoreRelAddRequests();
                List<Long> storeIds = payingMemberStoreRelAddRequests.parallelStream().map(PayingMemberStoreRelAddRequest::getStoreId).collect(Collectors.toList());
                if (request.getLevelDiscountType() == Constants.ZERO) { //付费会员等级折扣类型：0.所有商品统一设置
                    //获取推荐商品列表
                    List<PayingMemberRecommendRelAddRequest> payingMemberRecommendRelAddRequests = request.getPayingMemberRecommendRelAddRequests();
                    checkSku(payingMemberRecommendRelAddRequests.stream()
                            .map(PayingMemberRecommendRelAddRequest::getGoodsInfoId).collect(Collectors.toList()),storeIds);
                }else if (request.getLevelDiscountType() == Constants.ONE) {  //付费会员等级折扣类型：1.自定义商品设置
                    //获取折扣商品列表
                    List<PayingMemberDiscountRelAddRequest> payingMemberDiscountRelAddRequests = request.getPayingMemberDiscountRelAddRequests();
                    List<String> discountSkuIds = payingMemberDiscountRelAddRequests.stream()
                            .map(PayingMemberDiscountRelAddRequest::getGoodsInfoId).collect(Collectors.toList());
                    checkSku(discountSkuIds,storeIds);
                    //获取推荐商品列表
                    List<PayingMemberRecommendRelAddRequest> payingMemberRecommendRelAddRequests = request.getPayingMemberRecommendRelAddRequests();
                    List<String> recommendSkuIds = payingMemberRecommendRelAddRequests.stream()
                            .map(PayingMemberRecommendRelAddRequest::getGoodsInfoId).collect(Collectors.toList());
                    //如果推荐商品列表不在折扣商品列表内，则参数错误
                    if (!discountSkuIds.containsAll(recommendSkuIds)) {
                        throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
                    }
                }
            }
        } else if (object instanceof PayingMemberLevelModifyRequest){
            PayingMemberLevelModifyRequest request = ((PayingMemberLevelModifyRequest) object);
            request.getPayingMemberPriceModifyRequests().forEach(payingMemberPriceAddRequest -> {
                List<Integer> rightsIds = payingMemberPriceAddRequest.getPayingMemberRightsRelModifyRequests()
                        .parallelStream().map(PayingMemberRightsRelModifyRequest::getRightsId).collect(Collectors.toList());
                //查询付费设置下的权益
                List<CustomerLevelRightsVO> customerLevelRightsVOList = customerLevelRightsQueryProvider.list(CustomerLevelRightsQueryRequest.builder()
                        .rightsIdList(rightsIds)
                        .delFlag(DeleteFlag.NO)
                        .build()).getContext().getCustomerLevelRightsVOList();
                //根据权益类型去重
                Set<LevelRightsType> rightsTypes = customerLevelRightsVOList.parallelStream()
                        .map(CustomerLevelRightsVO::getRightsType).collect(Collectors.toSet());
                //不相等，则权益类型重复，参数错误
                if (customerLevelRightsVOList.size() != rightsTypes.size()) {
                    throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
                }
            });
            if (request.getLevelStoreRange() == Constants.ZERO) { // 自营商家
                if (request.getLevelDiscountType() == Constants.ZERO) { //付费会员等级折扣类型：0.所有商品统一设置
                    //获取推荐商品列表
                    List<PayingMemberRecommendRelModifyRequest> payingMemberRecommendRelModifyRequests = request.getPayingMemberRecommendRelModifyRequests();
                    checkSku(payingMemberRecommendRelModifyRequests.parallelStream()
                            .map(PayingMemberRecommendRelModifyRequest::getGoodsInfoId).collect(Collectors.toList()));
                } else if (request.getLevelDiscountType() == Constants.ONE) {  //付费会员等级折扣类型：1.自定义商品设置
                    //获取折扣商品列表
                    List<PayingMemberDiscountRelModifyRequest> payingMemberDiscountRelModifyRequests = request.getPayingMemberDiscountRelModifyRequests();
                    List<String> discountSkuIds = payingMemberDiscountRelModifyRequests.parallelStream()
                            .map(PayingMemberDiscountRelModifyRequest::getGoodsInfoId).collect(Collectors.toList());
                    checkSku(discountSkuIds);
                    //获取推荐商品列表
                    List<PayingMemberRecommendRelModifyRequest> payingMemberRecommendRelModifyRequests = request.getPayingMemberRecommendRelModifyRequests();
                    List<String> recommendSkuIds = payingMemberRecommendRelModifyRequests.parallelStream()
                            .map(PayingMemberRecommendRelModifyRequest::getGoodsInfoId).collect(Collectors.toList());
                    //如果推荐商品列表不在折扣商品列表内，则参数错误
                    if (!discountSkuIds.containsAll(recommendSkuIds)) {
                        throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
                    }
                }

            } else if (request.getLevelStoreRange() == Constants.ONE) {  // 自定义商家
                List<PayingMemberStoreRelModifyRequest> payingMemberStoreRelModifyRequests = request.getPayingMemberStoreRelModifyRequests();
                List<Long> storeIds = payingMemberStoreRelModifyRequests.parallelStream().map(PayingMemberStoreRelModifyRequest::getStoreId).collect(Collectors.toList());
                if (request.getLevelDiscountType() == Constants.ZERO) { //付费会员等级折扣类型：0.所有商品统一设置
                    //获取推荐商品列表
                    List<PayingMemberRecommendRelModifyRequest> payingMemberRecommendRelModifyRequests = request.getPayingMemberRecommendRelModifyRequests();
                    checkSku(payingMemberRecommendRelModifyRequests.parallelStream()
                            .map(PayingMemberRecommendRelModifyRequest::getGoodsInfoId).collect(Collectors.toList()),storeIds);
                }else if (request.getLevelDiscountType() == Constants.ONE) {  //付费会员等级折扣类型：1.自定义商品设置
                    //获取折扣商品列表
                    List<PayingMemberDiscountRelModifyRequest> payingMemberDiscountRelModifyRequests = request.getPayingMemberDiscountRelModifyRequests();
                    List<String> discountSkuIds = payingMemberDiscountRelModifyRequests.parallelStream()
                            .map(PayingMemberDiscountRelModifyRequest::getGoodsInfoId).collect(Collectors.toList());
                    checkSku(discountSkuIds,storeIds);
                    //获取推荐商品列表
                    List<PayingMemberRecommendRelModifyRequest> payingMemberRecommendRelModifyRequests = request.getPayingMemberRecommendRelModifyRequests();
                    List<String> recommendSkuIds = payingMemberRecommendRelModifyRequests.parallelStream()
                            .map(PayingMemberRecommendRelModifyRequest::getGoodsInfoId).collect(Collectors.toList());
                    //如果推荐商品列表不在折扣商品列表内，则参数错误
                    if (!discountSkuIds.containsAll(recommendSkuIds)) {
                        throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
                    }
                }
            }
        }
    }

    /**
     * 检查商品
     * @param goodsInfoIds
     */
    private void checkSku( List<String> goodsInfoIds) {
        List<GoodsInfoVO> goodsInfos = goodsInfoQueryProvider.listByIds(GoodsInfoListByIdsRequest.builder()
                .goodsInfoIds(goodsInfoIds)
                .build()).getContext().getGoodsInfos();
        // 找出第三方商家商品
        Optional<GoodsInfoVO> optional = goodsInfos.parallelStream().filter(goodsInfoVO -> BoolFlag.YES.equals(goodsInfoVO.getCompanyType())).findFirst();
        // 存在则参数错误
        if (optional.isPresent()) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
    }

    /**
     * 检查商品
     * @param goodsInfoIds
     */
    private void checkSku( List<String> goodsInfoIds,List<Long> storeIds) {
        List<GoodsInfoVO> goodsInfos = goodsInfoQueryProvider.listByIds(GoodsInfoListByIdsRequest.builder()
                .goodsInfoIds(goodsInfoIds)
                .build()).getContext().getGoodsInfos();
        //商品的店铺id集合 去重
        Set<Long> skuStoreIds = goodsInfos.parallelStream().map(GoodsInfoVO::getStoreId).collect(Collectors.toSet());
        //如果不在自定义商家范围内，则参数错误
        if (!storeIds.containsAll(skuStoreIds)) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
    }

    /**
     * 下载模版
     *
     */
    @Operation(summary = "下载导入模版")
    @Parameter(name = "encrypted",
            description = "加密", required = true)
    @GetMapping("/excel/template/{encrypted}")
    public void downloadTemplate(@PathVariable String encrypted) {
        //获取入参
        Integer activityType = this.getActivityType(encrypted);
        //获取beanName
        String beanName = this.getBeanName(activityType);
        PayingMemberExcelService payingMemberExcelService = payingMemberExcelServiceMap.get(beanName);
        payingMemberExcelService.downloadTemplate(activityType);
    }

    /**
     * 上传文件
     */
    @Operation(summary = "上传文件")
    @PostMapping("/excel/upload")
    public BaseResponse<String> upload(@RequestParam("uploadFile") MultipartFile uploadFile,
                                       @RequestParam Map<String,String> paramMap) {
        String activityType = paramMap.get("activityType");
        String levelStoreRange = paramMap.get("levelStoreRange");
        String levelDiscountType = paramMap.get("levelDiscountType");
        String storeIdList = paramMap.get("storeIdList");
        String skuIdList = paramMap.get("skuIdList");
        PayingMemberTemplateRequest payingMemberTemplateRequest = PayingMemberTemplateRequest.builder()
                .activityType(Integer.valueOf(activityType))
                .levelDiscountType(Integer.valueOf(levelDiscountType))
                .levelStoreRange(Integer.valueOf(levelStoreRange))
                .storeIdList(JSONArray.parseArray(storeIdList).toJavaList(Long.class))
                .skuIdList(JSONArray.parseArray(skuIdList).toJavaList(String.class))
                .build();
        //获取beanName
        String beanName = this.getBeanName(payingMemberTemplateRequest.getActivityType());
        PayingMemberExcelService payingMemberExcelService = payingMemberExcelServiceMap.get(beanName);
        String fileExt = payingMemberExcelService.uploadFile(uploadFile, payingMemberTemplateRequest);
        return BaseResponse.success(fileExt);
    }

    /**
     * 下载错误文件
     * @param ext
     * @param decrypted
     */
    @Operation(summary = "下载错误文件")
    @GetMapping("/excel/err/{ext}/{decrypted}")
    public void downErrExcel(@PathVariable String ext, @PathVariable String decrypted) {
        if (!StringUtils.equalsAnyIgnoreCase(ext, Constants.XLS,Constants.XLSX)) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        //获取入参
        Integer activityType = this.getActivityType(decrypted);
        //获取beanName
        String beanName = this.getBeanName(activityType);
        PayingMemberExcelService payingMemberExcelService = payingMemberExcelServiceMap.get(beanName);
        payingMemberExcelService.downloadErrorFile(ext);
    }

    /**
     * 下载错误文件
     */
    @Operation(summary = "查询导入数据")
    @GetMapping("/excel/list/{activityType}")
    public BaseResponse<List> getGoodsInfoList(@PathVariable Integer activityType) {
        //获取beanName
        String beanName = this.getBeanName(activityType);
        PayingMemberExcelService payingMemberExcelService = payingMemberExcelServiceMap.get(beanName);
        List goodsInfoList = payingMemberExcelService.getGoodsInfoList();
        return BaseResponse.success(goodsInfoList);
    }

    /**
     * 获取spring的beanName
     * @param activityType
     * @return
     */
    private String getBeanName(Integer activityType){
        Class<?> clazz = CLASS.get(MarketingAllType.values()[activityType]);
        if(Objects.isNull(clazz)){
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        //获取类名
        String className = ClassUtils.getShortClassName(clazz);
        //首字母小写
        String defaultBeanName = StringUtils.uncapitalize(className);
        //获取当前类上的service注解
        Service service = AnnotationUtils.getAnnotation(clazz, Service.class);
        if(Objects.isNull(service)){
            Component component = AnnotationUtils.getAnnotation(clazz, Component.class);
            Assert.notNull(component,"指定@Component或@Service注解中的一种");
            String beanName = component.value();
            return StringUtils.defaultIfBlank(beanName,defaultBeanName);
        }
        //获取注解beanName
        String beanName = service.value();
        return StringUtils.defaultIfBlank(beanName,defaultBeanName);
    };

    /**
     * 获取入参
     * @param encrypted
     * @return
     */
    private Integer getActivityType(String encrypted){
        byte[] decode = Base64.getUrlDecoder().decode(encrypted);
        String decrypted = new String(decode, StandardCharsets.UTF_8);
        PayingMemberTemplateRequest request = JSON.parseObject(decrypted, PayingMemberTemplateRequest.class);
        Integer activityType = request.getActivityType();
        if(Objects.isNull(activityType)){
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        return activityType;
    }

    /**
     * 查询商品信息
     * @param goodsInfoIds
     * @return
     */
    private  List<GoodsInfoVO> getGoodsInfoList(List<String> goodsInfoIds) {
        EsSkuPageRequest esSkuPageRequest = new EsSkuPageRequest();
        esSkuPageRequest.setPageSize(Constants.NUM_1000);
        esSkuPageRequest.setGoodsInfoIds(goodsInfoIds);
        esSkuPageRequest.setStoreId(commonUtil.getStoreId());
        esSkuPageRequest.setDelFlag(DeleteFlag.NO.toValue());
        EsSkuPageResponse response = esSkuQueryProvider.page(esSkuPageRequest).getContext();
        List<GoodsInfoVO> goodsInfoVOS = response.getGoodsInfoPage().getContent();
        //填充marketingGoodsStatus属性
        goodsBaseService.populateMarketingGoodsStatus(goodsInfoVOS);
        return goodsInfoVOS;
    }
    /**
     * 查询boos所有的付费会员等级
     */
    @Operation(summary = "查询boos所有的付费会员等级")
    @PostMapping("/base-list")
    public BaseResponse<PayingMemberLevelListNewResponse> findAllPayingMemberLevelNew(){
        return payingMemberLevelQueryProvider.listAllPayingMemberLevelNew();
    }
}
