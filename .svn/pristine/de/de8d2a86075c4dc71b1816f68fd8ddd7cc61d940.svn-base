package com.wanmi.sbc.store;

import com.google.common.collect.Lists;
import com.wanmi.sbc.common.annotation.MultiSubmit;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.enums.EnableStatus;
import com.wanmi.sbc.common.enums.StoreType;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.sensitiveword.annotation.ReturnSensitiveWords;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.customer.api.provider.employee.EmployeeQueryProvider;
import com.wanmi.sbc.customer.api.provider.store.StoreCustomerQueryProvider;
import com.wanmi.sbc.customer.api.provider.store.StoreProvider;
import com.wanmi.sbc.customer.api.provider.store.StoreQueryProvider;
import com.wanmi.sbc.customer.api.provider.storelevel.StoreLevelQueryProvider;
import com.wanmi.sbc.customer.api.request.employee.EmployeeByCompanyIdRequest;
import com.wanmi.sbc.customer.api.request.store.AccountDateModifyRequest;
import com.wanmi.sbc.customer.api.request.store.InitStoreByCompanyRequest;
import com.wanmi.sbc.customer.api.request.store.ListStoreByNameForAutoCompleteRequest;
import com.wanmi.sbc.customer.api.request.store.ListStoreByNameRequest;
import com.wanmi.sbc.customer.api.request.store.ListStoreRequest;
import com.wanmi.sbc.customer.api.request.store.NoDeleteStoreByIdRequest;
import com.wanmi.sbc.customer.api.request.store.StoreAuditRequest;
import com.wanmi.sbc.customer.api.request.store.StoreContractModifyRequest;
import com.wanmi.sbc.customer.api.request.store.StoreCustomerQueryRequest;
import com.wanmi.sbc.customer.api.request.store.StoreInfoByIdRequest;
import com.wanmi.sbc.customer.api.request.store.StorePageRequest;
import com.wanmi.sbc.customer.api.request.store.StoreSaveRequest;
import com.wanmi.sbc.customer.api.request.store.StoreSwitchRequest;
import com.wanmi.sbc.customer.api.request.storelevel.StoreLevelByStoreIdRequest;
import com.wanmi.sbc.customer.api.request.storelevel.StoreLevelListRequest;
import com.wanmi.sbc.customer.api.response.employee.EmployeeByCompanyIdResponse;
import com.wanmi.sbc.customer.api.response.store.StoreBaseInfoResponse;
import com.wanmi.sbc.customer.api.response.store.StoreInfoResponse;
import com.wanmi.sbc.customer.api.response.storelevel.StoreLevelListResponse;
import com.wanmi.sbc.customer.api.response.storelevel.StroeLevelInfoResponse;
import com.wanmi.sbc.customer.bean.enums.CheckState;
import com.wanmi.sbc.customer.bean.enums.StoreState;
import com.wanmi.sbc.customer.bean.vo.CustomerLevelVO;
import com.wanmi.sbc.customer.bean.vo.StoreCustomerVO;
import com.wanmi.sbc.customer.bean.vo.StoreSimpleVO;
import com.wanmi.sbc.customer.bean.vo.StoreVO;
import com.wanmi.sbc.elastic.api.provider.employee.EsEmployeeProvider;
import com.wanmi.sbc.elastic.api.provider.goods.EsGoodsInfoElasticProvider;
import com.wanmi.sbc.elastic.api.provider.storeInformation.EsStoreInformationProvider;
import com.wanmi.sbc.elastic.api.provider.storeInformation.EsStoreInformationQueryProvider;
import com.wanmi.sbc.elastic.api.request.employee.EsEmployeeSaveRequest;
import com.wanmi.sbc.elastic.api.request.goods.EsGoodsStoreInfoModifyRequest;
import com.wanmi.sbc.elastic.api.request.storeInformation.StoreInfoContractRequest;
import com.wanmi.sbc.elastic.api.request.storeInformation.StoreInfoModifyRequest;
import com.wanmi.sbc.elastic.api.request.storeInformation.StoreInfoQueryPageRequest;
import com.wanmi.sbc.elastic.api.request.storeInformation.StoreInfoRejectModifyRequest;
import com.wanmi.sbc.elastic.api.request.storeInformation.StoreInfoStateModifyRequest;
import com.wanmi.sbc.goods.api.request.goods.GoodsModifyStoreNameByStoreIdRequest;
import com.wanmi.sbc.marketing.api.provider.bargaingoods.BargainGoodsSaveProvider;
import com.wanmi.sbc.goods.api.provider.distributionmatter.DistributionGoodsMatterProvider;
import com.wanmi.sbc.goods.api.provider.distributor.goods.DistributorGoodsInfoProvider;
import com.wanmi.sbc.goods.api.provider.pointsgoods.PointsGoodsSaveProvider;
import com.wanmi.sbc.marketing.api.request.bargaingoods.StoreTerminalActivityRequest;
import com.wanmi.sbc.goods.api.request.distributionmatter.DistributionGoodsMatterModifyByStoreIdRequest;
import com.wanmi.sbc.goods.api.request.distributor.goods.DistributorGoodsInfoDeleteByStoreIdRequest;
import com.wanmi.sbc.goods.api.request.pointsgoods.PointsGoodsModifyStatusByStoreIdRequest;
import com.wanmi.sbc.mq.GoodsProducer;
import com.wanmi.sbc.store.request.StoreListByTypeRequest;
import com.wanmi.sbc.util.OperateLogMQUtil;
import io.seata.spring.annotation.GlobalTransactional;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.ListUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Author: songhanlin
 * @Date: Created In 下午2:20 2017/11/2
 * @Description: 店铺信息Controller
 */
@Tag(name = "StoreController", description = "店铺信息相关API")
@RestController("bossStoreController")
@Validated
@RequestMapping("/store")
public class StoreController {

    @Autowired
    private StoreQueryProvider storeQueryProvider;

    @Autowired
    private StoreProvider storeProvider;

    @Autowired
    private StoreBaseService baseService;

    @Autowired
    private StoreSelfService selfService;

    @Autowired
    private StoreCustomerQueryProvider storeCustomerQueryProvider;

    @Autowired
    private StoreLevelQueryProvider storeLevelQueryProvider;

    @Autowired
    private EsGoodsInfoElasticProvider esGoodsInfoElasticProvider;

    @Autowired
    private OperateLogMQUtil operateLogMQUtil;

    @Autowired
    private DistributorGoodsInfoProvider distributorGoodsInfoProvider;

    @Autowired
    private PointsGoodsSaveProvider pointsGoodsSaveProvider;

    @Autowired
    private GoodsProducer goodsProducer;

    @Autowired
    private EsStoreInformationProvider esStoreInformationProvider;

    @Autowired
    private EsStoreInformationQueryProvider esStoreInformationQueryProvider;

    @Autowired
    private EsEmployeeProvider esEmployeeProvider;

    @Autowired
    private EmployeeQueryProvider employeeQueryProvider;

    @Autowired
    private DistributionGoodsMatterProvider distributionGoodsMatterProvider;

    @Autowired
    private BargainGoodsSaveProvider bargainGoodsSaveProvider;

    /**
     * 编辑店铺结算日期
     */
    @Operation(summary = "编辑店铺结算日期")
    @RequestMapping(value = "/days", method = RequestMethod.PUT)
    public BaseResponse<StoreVO> edit(@Valid @RequestBody AccountDateModifyRequest request) {
        StoreVO store = storeProvider.accountDateModify(request).getContext().getStoreVO();
        return BaseResponse.success(store);
    }


    /**
     * 查询店铺信息
     */
    @Operation(summary = "查询店铺信息")
    @Parameter(name = "storeId",
            description = "店铺Id", required = true)
    @RequestMapping(value = "/{storeId}", method = RequestMethod.GET)
    public BaseResponse<StoreVO> info(@PathVariable Long storeId) {
        StoreVO store = storeQueryProvider.getNoDeleteStoreById(new NoDeleteStoreByIdRequest(storeId))
                .getContext().getStoreVO();
        return BaseResponse.success(store);
    }

    /**
     * 根据商家id获取店铺信息
     *
     * @param companyInfoId
     * @return
     */
    @Operation(summary = "根据商家id获取店铺信息")
    @Parameter(name = "companyInfoId",
            description = "商家Id", required = true)
    @RequestMapping(value = "/from/company/{companyInfoId}", method = RequestMethod.GET)
    public BaseResponse<StoreVO> fromCompanyInfoId(@PathVariable Long companyInfoId) {
        StoreVO store = storeProvider.initStoreByCompany(new InitStoreByCompanyRequest(companyInfoId))
                .getContext().getStoreVO();
        return BaseResponse.success(store);
    }

    /**
     * 开/关 店铺
     *
     * @param request
     * @return
     */
    @MultiSubmit
    @GlobalTransactional
    @Operation(summary = "开/关 店铺")
    @RequestMapping(value = "/close", method = RequestMethod.PUT)
    public BaseResponse<StoreVO> closeStore(@Valid @RequestBody StoreSwitchRequest request) {
        StoreVO store = storeProvider.switchStore(request).getContext().getStoreVO();
        Integer providerStatus = Constants.yes;
        if (StoreState.CLOSED.equals(request.getStoreState())) {
            // 店铺关店。同时删除分销员关联的分销商品
            DistributorGoodsInfoDeleteByStoreIdRequest distributorGoodsInfoDeleteByStoreIdRequest =
                    new DistributorGoodsInfoDeleteByStoreIdRequest();
            distributorGoodsInfoDeleteByStoreIdRequest.setStoreId(request.getStoreId());
            distributorGoodsInfoProvider.deleteByStoreId(distributorGoodsInfoDeleteByStoreIdRequest);
            // 停用该店铺关联的积分商品
            pointsGoodsSaveProvider.modifyStatusByStoreId(PointsGoodsModifyStatusByStoreIdRequest.builder()
                    .storeId(store.getStoreId()).status(EnableStatus.DISABLE).build());
            providerStatus = Constants.no;
        }
        //商品分销素材-更新店铺开关
        DistributionGoodsMatterModifyByStoreIdRequest distributionGoodsMatterModifyRequest =
                new DistributionGoodsMatterModifyByStoreIdRequest();
        distributionGoodsMatterModifyRequest.setStoreId(request.getStoreId());
        distributionGoodsMatterModifyRequest.setStoreState(request.getStoreState());
        distributionGoodsMatterProvider.modifyByStoreId(distributionGoodsMatterModifyRequest);

        if (StoreType.PROVIDER.equals(store.getStoreType())) {
            //更新代销商品的店铺状态，刷新es
            goodsProducer.updateProviderStatus(providerStatus, Lists.newArrayList(request.getStoreId()));
        } else {
            goodsProducer.updateStoreStateByStoreId(EsGoodsStoreInfoModifyRequest.builder()
                    .storeId(request.getStoreId()).storeState(request.getStoreState()).build());
        }

        // 记录操作日志
        if (request.getStoreState() == StoreState.OPENING) {
            operateLogMQUtil.convertAndSend("商家", "开店",
                    "开店：商家编号" + store.getCompanyCode());
        } else {
            operateLogMQUtil.convertAndSend("商家", "关店",
                    "关店：商家编号" + store.getCompanyCode());
        }
        if (StoreType.PROVIDER.equals(store.getStoreType())) {
            //更新代销商品的店铺状态，刷新es
            goodsProducer.updateProviderStatus(providerStatus, Lists.newArrayList(request.getStoreId()));
        } else {
            esGoodsInfoElasticProvider.modifyStore(EsGoodsStoreInfoModifyRequest.builder()
                    .storeId(request.getStoreId()).storeState(request.getStoreState()).build());
        }
        //更新es店铺状态
        esStoreInformationProvider.modifyStoreState(StoreInfoStateModifyRequest
                .builder()
                .storeId(store.getStoreId())
                .storeState(request.getStoreState().toValue())
                .storeClosedReason(request.getStoreClosedReason())
                .build());
        // 商家关店，处理砍价商品活动状态为终止
        if (StoreState.CLOSED.equals(request.getStoreState())) {
            bargainGoodsSaveProvider.storeTerminalActivity(StoreTerminalActivityRequest.builder().storeId(store.getStoreId()).build());
        }
        return BaseResponse.success(store);
    }

    /**
     * 通过/驳回 审核
     *
     * @param request
     * @return
     */
    @Operation(summary = "通过/驳回 审核")
    @RequestMapping(value = "/reject", method = RequestMethod.PUT)
    @GlobalTransactional
    public BaseResponse<StoreVO> rejectStore(@Valid @RequestBody StoreAuditRequest request) {
        StoreVO store = selfService.rejectOrPass(request);
        //记录操作日志
        if (request.getAuditState() == CheckState.CHECKED) {
            //同步es店铺信息
            StoreInfoRejectModifyRequest storeInfoRequest = new StoreInfoRejectModifyRequest();
            KsBeanUtil.copyPropertiesThird(store, storeInfoRequest);
            operateLogMQUtil.convertAndSend("商家", "审核商家",
                    "审核商家：商家编号" + store.getCompanyInfo().getCompanyCode());
            esStoreInformationProvider.modifyStoreReject(storeInfoRequest);
            //将店铺主账号同步至es员工信息
            EmployeeByCompanyIdResponse mainEmployeeResponse = employeeQueryProvider.getByCompanyId(
                    EmployeeByCompanyIdRequest.builder().companyInfoId(store.getCompanyInfoId()).build()).getContext();
            if (Objects.nonNull(mainEmployeeResponse)) {
                EsEmployeeSaveRequest esEmployeeSaveRequest = KsBeanUtil.convert(mainEmployeeResponse, EsEmployeeSaveRequest.class);
                //主账号默认管理根部门
                esEmployeeSaveRequest.setManageDepartmentIds(Collections.singletonList("0"));
                esEmployeeProvider.save(esEmployeeSaveRequest);
            }
        } else if (request.getAuditState() == CheckState.NOT_PASS) {
            //同步es店铺信息
            StoreInfoRejectModifyRequest storeInfoRequest = new StoreInfoRejectModifyRequest();
            storeInfoRequest.setStoreId(request.getStoreId());
            storeInfoRequest.setAuditState(request.getAuditState());
            storeInfoRequest.setAuditReason(request.getAuditReason());
            operateLogMQUtil.convertAndSend("商家", "驳回商家",
                    "驳回商家：商家编号" + store.getCompanyInfo().getCompanyCode());
            esStoreInformationProvider.modifyStoreReject(storeInfoRequest);
        }
        return BaseResponse.success(store);
    }

    /**
     * 查询店铺基本信息
     *
     * @return
     */
    @Operation(summary = "查询店铺基本信息")
    @Parameter(name = "storeId",
            description = "店铺Id", required = true)
    @RequestMapping(value = "/store-info/{storeId}", method = RequestMethod.GET)
    @ReturnSensitiveWords(functionName = "f_boss_query_store_sign_word")
    public BaseResponse<StoreInfoResponse> queryStore(@PathVariable Long storeId) {
        return storeQueryProvider.getStoreInfoById(new StoreInfoByIdRequest(storeId));
    }

    /**
     * 修改店铺基本信息
     *
     * @param saveRequest
     * @return
     */
    @Operation(summary = "修改店铺基本信息")
    @RequestMapping(value = "/store-info", method = RequestMethod.PUT)
    @GlobalTransactional
    public BaseResponse<StoreVO> updateStore(@RequestBody StoreSaveRequest saveRequest) {
        if (Objects.isNull(saveRequest.getStoreId()) || Objects.isNull(saveRequest.getCompanyInfoId())) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        StoreVO store = baseService.updateStore(saveRequest);
        //更新数据到es
        StoreInfoModifyRequest storeInfoModifyRequest = new StoreInfoModifyRequest();
        KsBeanUtil.copyPropertiesThird(saveRequest, storeInfoModifyRequest);
        //记录操作日志
        operateLogMQUtil.convertAndSend("商家", "编辑商家信息",
                "编辑商家信息：商家编号" + store.getCompanyInfo().getCompanyCode());
        esStoreInformationProvider.modifyStoreBasicInfo(storeInfoModifyRequest);

        //更新商品的对应店铺名称，刷新商品es
        goodsProducer.updateGoodsStoreNameByStoreId(GoodsModifyStoreNameByStoreIdRequest.builder()
                .storeId(saveRequest.getStoreId()).storeName(saveRequest.getStoreName()).supplierName(saveRequest.getSupplierName()).build());

        return BaseResponse.success(store);
    }

    /**
     * 修改签约日期和商家类型
     *
     * @param saveRequest
     * @return
     */
    @MultiSubmit
    @GlobalTransactional
    @Operation(summary = "修改签约日期和商家类型")
    @RequestMapping(value = "/contract/date", method = RequestMethod.PUT)
    public BaseResponse<StoreVO> updateStoreContract(@Valid @RequestBody StoreContractModifyRequest saveRequest) {
        StoreVO store = storeProvider.modifyStoreContract(saveRequest).getContext().getStoreVO();
        //商品分销素材-更新签约日期
        DistributionGoodsMatterModifyByStoreIdRequest distributionGoodsMatterModifyRequest =
                new DistributionGoodsMatterModifyByStoreIdRequest();
        distributionGoodsMatterModifyRequest.setStoreId(saveRequest.getStoreId());
        distributionGoodsMatterModifyRequest.setContractEndDate(saveRequest.getContractEndDate());
        distributionGoodsMatterProvider.modifyByStoreId(distributionGoodsMatterModifyRequest);
        //如果店铺审核通过
        if (Objects.equals(CheckState.CHECKED.toValue(), store.getAuditState().toValue())) {
            //更新代销商品的店铺状态，刷新es
            goodsProducer.updateProviderStatus(Constants.yes, Lists.newArrayList(saveRequest.getStoreId()));
            //更新Es商品的时间
            esGoodsInfoElasticProvider.modifyStore(EsGoodsStoreInfoModifyRequest.builder()
                    .storeId(saveRequest.getStoreId()).contractStartDate(saveRequest.getContractStartDate())
                    .contractEndDate(saveRequest.getContractEndDate()).build());
        }
        //签约信息重新刷入es
        esStoreInformationProvider.modifyStoreContractInfo(KsBeanUtil.copyPropertiesThird(saveRequest, StoreInfoContractRequest.class));
        return BaseResponse.success(store);
    }

    /**
     * 查询店铺的会员信息，不区分会员的禁用状态
     * bail 2017-11-16
     *
     * @return 会员信息
     */
    @Operation(summary = "查询店铺的会员信息，不区分会员的禁用状态")
    @Parameter(name = "storeId",
            description = "店铺Id", required = true)
    @RequestMapping(value = "/allCustomers/{storeId}", method = RequestMethod.POST)
    public BaseResponse<List<StoreCustomerVO>> customers(@PathVariable Long storeId) {
        StoreCustomerQueryRequest request = new StoreCustomerQueryRequest();
        request.setStoreId(storeId);

        return BaseResponse.success(storeCustomerQueryProvider.listAllCustomer(request).getContext().getStoreCustomerVOList());
    }

    /**
     * 查询平台会员信息
     *
     * @return 会员信息
     */
    @Operation(summary = "查询平台会员信息")
    @RequestMapping(value = "/allBossCustomers", method = RequestMethod.GET)
    public BaseResponse<List<StoreCustomerVO>> allBossCustomers() {
        return BaseResponse.success(
                storeCustomerQueryProvider.listBossAllCustomer().getContext().getStoreCustomerVOList());
    }

    /**
     * 查询所有会员等级
     *
     * @return
     */
    @Operation(summary = "查询所有会员等级")
    @Parameter(name = "storeId",
            description = "店铺Id", required = true)
    @RequestMapping(value = "/levels/{storeId}", method = RequestMethod.GET)
    public BaseResponse<List<CustomerLevelVO>> levels(@PathVariable Long storeId) {
        BaseResponse<StroeLevelInfoResponse> stroeLevelInfoResponseBaseResponse
                = storeLevelQueryProvider.queryStoreLevelInfo(StoreLevelByStoreIdRequest.builder().storeId(storeId).build());
        StroeLevelInfoResponse context = stroeLevelInfoResponseBaseResponse.getContext();
        if (Objects.nonNull(context)) {
            return BaseResponse.success(context.getCustomerLevelVOList());
        }
        return BaseResponse.success(Collections.emptyList());
    }

    @Operation(summary = "根据店铺名称模糊匹配店铺列表，自动关联5条信息，返回Map, 以店铺Id为key, 店铺名称为value")
    @Parameter(name = "storeName", description = "店铺名称", required = true)
    @RequestMapping(value = "/name", method = RequestMethod.GET)
    public BaseResponse<Map<Long, String>> queryStoreByNameForAutoComplete(@RequestParam("storeName") String storeName, @RequestParam(value = "storeType", required = false) Integer storeType) {
        ListStoreByNameForAutoCompleteRequest listStoreByNameForAutoCompleteRequest = ListStoreByNameForAutoCompleteRequest.builder().storeName(storeName)
                .build();
        if (Objects.nonNull(storeType)) {
            listStoreByNameForAutoCompleteRequest.setStoreType(StoreType.fromValue(storeType));
        }
        List<StoreVO> storeList = storeQueryProvider.listByNameForAutoComplete(listStoreByNameForAutoCompleteRequest).getContext().getStoreVOList();
        Map<Long, String> storeMap = new HashMap<>();
        storeList.stream().forEach(store -> storeMap.put(store.getStoreId(), store.getStoreName()));
        return BaseResponse.success(storeMap);
    }

    @Operation(summary = "根据店铺名称模糊匹配店铺列表，自动关联5条信息，返回Map, 以店铺Id为key, 店铺名称为value")
    @Parameter(name = "storeName", description = "店铺名称", required = true)
    @RequestMapping(value = "/old/provider/name", method = RequestMethod.GET)
    public BaseResponse<Map<Long, String>> queryProviderStoreByNameForAutoComplete(@RequestParam("storeName") String storeName) {
        List<StoreVO> storeList = storeQueryProvider.listByNameForAutoComplete(ListStoreByNameForAutoCompleteRequest.builder().storeName(storeName).storeType(StoreType.PROVIDER).build()).getContext().getStoreVOList();
        Map<Long, String> storeMap = new HashMap<>();
        storeList.stream().forEach(store -> storeMap.put(store.getStoreId(), store.getStoreName()));
        return BaseResponse.success(storeMap);
    }

    @Operation(summary = "根据店铺名称模糊匹配店铺列表，自动关联5条信息，返回Map, 以店铺Id为key, 店铺名称为value")
    @Parameter(name = "storeName", description = "店铺名称", required = true)
    @RequestMapping(value = "/provider/name", method = RequestMethod.GET)
    public BaseResponse<Map<Long, String>> queryProviderStoreByNameForAutoCompleteForEs(@RequestParam("storeName") String storeName) {
        List<StoreVO> storeList = esStoreInformationQueryProvider.queryStoreByNameAndStoreTypeForAutoComplete(
                StoreInfoQueryPageRequest
                        .builder()
                        .storeName(storeName)
                        .storeType(StoreType.PROVIDER)
                        .build()).getContext().getStoreVOList();
        Map<Long, String> storeMap = new HashMap<>();
        if (CollectionUtils.isNotEmpty(storeList)){
            for (StoreVO v : storeList) {
                if (Objects.nonNull(v.getStoreId())) {
                    storeMap.put(v.getStoreId(), v.getStoreName());
                }
            }
        }
        return BaseResponse.success(storeMap);
    }

    @Operation(summary = "根据店铺名称模糊匹配店铺列表，自动关联5条信息，返回Map, 以店铺Id为key, 店铺名称为value")
    @Parameter(name = "storeName", description = "店铺名称", required = true)
    @RequestMapping(value = "/old/supplier/name", method = RequestMethod.GET)
    public BaseResponse<Map<Long, String>> querySupplierStoreByNameForAutoComplete(@RequestParam("storeName") String storeName) {
        List<StoreVO> storeList = storeQueryProvider.listByNameForAutoComplete(ListStoreByNameForAutoCompleteRequest.builder().storeName(storeName).storeType(StoreType.SUPPLIER).build()).getContext().getStoreVOList();
        Map<Long, String> storeMap = new HashMap<>();
        storeList.stream().forEach(store -> storeMap.put(store.getStoreId(), store.getStoreName()));
        return BaseResponse.success(storeMap);
    }

    @Operation(summary = "根据店铺名称模糊匹配店铺列表，自动关联5条信息，返回Map, 以店铺Id为key, 店铺名称为value")
    @Parameter(name = "storeName", description = "店铺名称", required = true)
    @RequestMapping(value = "/supplier/name", method = RequestMethod.GET)
    public BaseResponse<Map<Long, String>> querySupplierStoreByNameForAutoCompleteForEs(@RequestParam("storeName") String storeName, @RequestParam(value = "storeType", required = false) Integer storeType) {
        List<StoreVO> storeList = esStoreInformationQueryProvider.queryStoreByNameAndStoreTypeForAutoComplete(
                StoreInfoQueryPageRequest
                        .builder()
                        .storeName(storeName)
                        .storeType(Objects.isNull(storeType) ? StoreType.SUPPLIER : StoreType.fromValue(storeType))
                        .build()).getContext().getStoreVOList();
        Map<Long, String> storeMap = new HashMap<>();

        if (CollectionUtils.isNotEmpty(storeList)){
            for (StoreVO v : storeList) {
                if (Objects.nonNull(v.getStoreId())) {
                    storeMap.put(v.getStoreId(), v.getStoreName());
                }
            }
        }
        return BaseResponse.success(storeMap);
    }

    /**
     * 店铺列表
     */
    @Operation(summary = "店铺列表")
    @RequestMapping(method = RequestMethod.GET)
    public BaseResponse<List<StoreBaseInfoResponse>> list() {
        ListStoreRequest queryRequest = new ListStoreRequest();
        queryRequest.setAuditState(CheckState.CHECKED);
//        queryRequest.setStoreState(StoreState.OPENING);
//        queryRequest.setGteContractStartDate(LocalDateTime.now());
//        queryRequest.setLteContractEndDate(LocalDateTime.now());
        queryRequest.setDelFlag(DeleteFlag.NO);
        queryRequest.setStoreType(StoreType.SUPPLIER);
        List<StoreBaseInfoResponse> list =
                storeQueryProvider.listStore(queryRequest).getContext().getStoreVOList().stream().map(s -> {
                    StoreBaseInfoResponse response = new StoreBaseInfoResponse().convertFromEntity(s);
                    return response;
                }).collect(Collectors.toList());
        return BaseResponse.success(list);
    }

    /**
     * 店铺列表
     */
    @Operation(summary = "店铺列表")
    @RequestMapping(value = "/listPlus", method = RequestMethod.POST)
    public BaseResponse<List<StoreBaseInfoResponse>> listPlus(@RequestBody StoreListByTypeRequest request) {
        ListStoreRequest queryRequest = new ListStoreRequest();
        queryRequest.setAuditState(CheckState.CHECKED);
        queryRequest.setDelFlag(DeleteFlag.NO);
        queryRequest.setStoreType(request.getStoreType());
        queryRequest.setStoreTypeList(request.getStoreTypeList());
        if (Objects.isNull(request.getStoreType())) {
            queryRequest.setNotShowStoreType(StoreType.PROVIDER);
        }
        List<StoreBaseInfoResponse> list =
                storeQueryProvider.listStore(queryRequest).getContext().getStoreVOList().stream().map(s -> {
                    StoreBaseInfoResponse response = new StoreBaseInfoResponse().convertFromEntity(s);
                    return response;
                }).collect(Collectors.toList());
        return BaseResponse.success(list);
    }

    /**
     * 店铺列表
     */
    @Operation(summary = "店铺列表")
    @RequestMapping(value = "/storeList", method = RequestMethod.POST)
    public BaseResponse<List<StoreBaseInfoResponse>> storeList(@RequestBody ListStoreRequest queryRequest) {
        queryRequest.setAuditState(CheckState.CHECKED);
        queryRequest.setDelFlag(DeleteFlag.NO);
        List<StoreBaseInfoResponse> list =
                storeQueryProvider.listStore(queryRequest).getContext().getStoreVOList().stream().map(s -> {
                    StoreBaseInfoResponse response = new StoreBaseInfoResponse().convertFromEntity(s);
                    return response;
                }).collect(Collectors.toList());
        return BaseResponse.success(list);
    }


    /**
     * 店铺列表
     */
    @Operation(summary = "店铺列表")
    @RequestMapping(value = "/listByName", method = RequestMethod.POST)
    public BaseResponse<List<StoreSimpleVO>> listByName(@RequestBody StorePageRequest pageRequest) {
        pageRequest.setAuditState(CheckState.CHECKED);
        pageRequest.setStoreState(StoreState.OPENING);
        MicroServicePage<StoreVO> page = storeQueryProvider.page(pageRequest).getContext().getStoreVOPage();
        List<StoreSimpleVO> list = null;
        if (page.getTotalElements() > 0) {
            list = KsBeanUtil.convert(page.getContent(), StoreSimpleVO.class);
        }
        return BaseResponse.success(ListUtils.emptyIfNull(list));
    }

    /**
     * 查询店铺等级信息
     *
     * @param storeId
     * @return
     */
    @Operation(summary = "查询店铺等级信息")
    @Parameter(name = "storeId", description = "店铺Id", required = true)
    @RequestMapping(value = "/level/list/{storeId}", method = RequestMethod.GET)
    public BaseResponse<StoreLevelListResponse> queryStoreLevelList(@PathVariable Long storeId) {
        StoreLevelListRequest request = StoreLevelListRequest.builder().storeId(storeId).build();
        return BaseResponse.success(storeLevelQueryProvider.listAllStoreLevelByStoreId(request).getContext());
    }

    @Operation(summary = "店铺名称下拉框,根据店铺名称模糊匹配,只包含店铺名称与Id(全部)，返回Map, 以店铺Id为key, 店铺名称为value")
    @Parameter(name = "storeName", description = "店铺名称", required = true)
    @RequestMapping(value = "/name/list", method = RequestMethod.GET)
    public BaseResponse<Map<Long, String>> queryStoreByNameForAutoCompleteForEs(@RequestParam(value = "storeName", required = false) String storeName, @RequestParam(value = "storeType", required = false) Integer storeType) {

        return BaseResponse.success(storeQueryProvider.storeNameDropdownBox(
                ListStoreByNameRequest
                        .builder()
                        .storeName(storeName)
                        .storeType(Objects.isNull(storeType) ? StoreType.SUPPLIER : StoreType.fromValue(storeType))
                        .build()).getContext());
    }
}
