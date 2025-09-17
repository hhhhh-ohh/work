package com.wanmi.sbc.store;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.constant.RedisKeyConstant;
import com.wanmi.sbc.common.enums.AccountType;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.redis.util.RedisUtil;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.customer.api.provider.store.StoreCustomerQueryProvider;
import com.wanmi.sbc.customer.api.provider.store.StoreProvider;
import com.wanmi.sbc.customer.api.provider.store.StoreQueryProvider;
import com.wanmi.sbc.customer.api.request.store.BossStoreBaseInfoByIdRequest;
import com.wanmi.sbc.customer.api.request.store.NoDeleteStoreByIdRequest;
import com.wanmi.sbc.customer.api.request.store.StoreAddRequest;
import com.wanmi.sbc.customer.api.request.store.StoreCustomerQueryByCustomerNameRequest;
import com.wanmi.sbc.customer.api.request.store.StoreCustomerQueryRequest;
import com.wanmi.sbc.customer.api.request.store.StoreInfoByIdRequest;
import com.wanmi.sbc.customer.api.request.store.StoreInfoSmallProgramRequest;
import com.wanmi.sbc.customer.api.request.store.StoreModifyLogoRequest;
import com.wanmi.sbc.customer.api.request.store.StorePickupStateModifyRequest;
import com.wanmi.sbc.customer.api.request.store.StoreSaveRequest;
import com.wanmi.sbc.customer.api.response.store.BossStoreBaseInfoResponse;
import com.wanmi.sbc.customer.api.response.store.StoreCustomerResponse;
import com.wanmi.sbc.customer.api.response.store.StoreInfoExtendResponse;
import com.wanmi.sbc.customer.api.response.store.StoreInfoResponse;
import com.wanmi.sbc.customer.bean.vo.StoreCustomerVO;
import com.wanmi.sbc.customer.bean.vo.StoreVO;
import com.wanmi.sbc.elastic.api.provider.storeInformation.EsStoreInformationProvider;
import com.wanmi.sbc.elastic.api.request.storeInformation.StoreInfoModifyRequest;
import com.wanmi.sbc.empower.api.provider.wechatauth.WechatAuthProvider;
import com.wanmi.sbc.empower.api.request.wechatauth.MiniProgramQrCodeRequest;
import com.wanmi.sbc.goods.api.request.goods.GoodsModifyStoreNameByStoreIdRequest;
import com.wanmi.sbc.mq.GoodsProducer;
import com.wanmi.sbc.setting.api.provider.platformaddress.PlatformAddressQueryProvider;
import com.wanmi.sbc.setting.api.request.platformaddress.PlatformAddressListRequest;
import com.wanmi.sbc.setting.bean.vo.PlatformAddressVO;
import com.wanmi.sbc.util.CommonUtil;
import com.wanmi.sbc.util.OperateLogMQUtil;
import io.seata.spring.annotation.GlobalTransactional;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 店铺信息bff
 * @author chenli
 * Created by CHENLI on 2017/11/2.
 */
@Tag(name =  "StoreController", description =  "店铺信息服务API")
@RestController("supplierStoreController")
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
    private StoreCustomerQueryProvider storeCustomerQueryProvider;

    @Autowired
    private CommonUtil commonUtil;

    @Autowired
    private OperateLogMQUtil logMqUtil;

    @Autowired
    private WechatAuthProvider wechatAuthProvider;

    @Autowired
    private EsStoreInformationProvider esStoreInformationProvider;

    @Autowired
    private PlatformAddressQueryProvider platformAddressQueryProvider;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private GoodsProducer goodsProducer;

    /**
     * 查询店铺基本信息
     * @param storeId   店铺ID
     * @return          店铺基本信息
     */
    @Operation(summary = "查询店铺基本信息")
    @Parameter(name = "storeId", description = "店铺Id", required = true)
    @GetMapping(value = "/store-info/{storeId}")
    public BaseResponse<StoreInfoResponse> queryStore(@PathVariable Long storeId) {
        return BaseResponse.success(storeQueryProvider.getStoreInfoById(new StoreInfoByIdRequest(storeId))
                .getContext());
    }

    /**
     * 查询店铺信息
     * @return      查询店铺信息
     */
    @Operation(summary = "查询店铺信息")
    @GetMapping(value = "/info")
    public BaseResponse<StoreVO> info() {
        return BaseResponse.success(storeQueryProvider.getNoDeleteStoreById(new NoDeleteStoreByIdRequest(commonUtil.getStoreId()))
                .getContext().getStoreVO());
    }

    /**
     * 查询店铺基本信息(名称,logo,店招等)
     * @return      店铺基本信息
     */
    @Operation(summary = "查询店铺基本信息(名称,logo,店招等)")
    @GetMapping(value = "/storeBaseInfo")
    public BaseResponse<BossStoreBaseInfoResponse> queryStoreBaseInfo() {
        return storeQueryProvider.getBossStoreBaseInfoById(new BossStoreBaseInfoByIdRequest
                (commonUtil.getStoreId()));
    }

    /**
     * 查询店铺信息
     * @return      店铺信息
     */
    @Operation(summary = "查询店铺信息")
    @GetMapping(value = "/storeInfo")
    public BaseResponse<StoreInfoResponse> queryStore() {
        BaseResponse<StoreInfoResponse> response = storeQueryProvider.getStoreInfoById(new StoreInfoByIdRequest(commonUtil
                .getStoreId()));
        Long storeId = commonUtil.getStoreId();
        StoreInfoResponse context = response.getContext();
        String isKnow = redisUtil.getString(RedisKeyConstant.IS_KNOW_SHOW_KEY.concat(String.valueOf(storeId)));
        if(StringUtils.isNotBlank(isKnow)){
            context.setIsKnow(Boolean.TRUE);
        }
        return response;
    }

    /**
     * 查询店铺信息
     * @return      店铺信息
     */
    @Operation(summary = "查询店铺信息")
    @GetMapping(value = "/storeInfoExtend")
    public BaseResponse<StoreInfoExtendResponse> queryStoreExtend() {
        BaseResponse<StoreInfoResponse> storeInfo =
                storeQueryProvider.getStoreInfoById(new StoreInfoByIdRequest(commonUtil
                        .getStoreId()));
        StoreInfoExtendResponse storeInfoExtendResponse = new StoreInfoExtendResponse();
        if (Objects.nonNull(storeInfo.getContext())){
            KsBeanUtil.copyProperties(storeInfo.getContext(), storeInfoExtendResponse);
            List<String> addrIds = new ArrayList<>();
            addrIds.add(Objects.toString(storeInfoExtendResponse.getProvinceId()));
            addrIds.add(Objects.toString(storeInfoExtendResponse.getCityId()));
            addrIds.add(Objects.toString(storeInfoExtendResponse.getAreaId()));
            addrIds.add(Objects.toString(storeInfoExtendResponse.getStreetId()));

            //根据Id获取地址信息
            List<PlatformAddressVO> voList = platformAddressQueryProvider.list(PlatformAddressListRequest.builder()
                    .addrIdList(addrIds).build()).getContext().getPlatformAddressVOList();

            Map<String, String> addrMap = new HashMap<>();
            if (CollectionUtils.isNotEmpty(voList)) {
                addrMap = voList.stream().collect(Collectors.toMap(PlatformAddressVO::getAddrId, PlatformAddressVO::getAddrName));
            }

            String provinceName = addrMap.get(Objects.toString(storeInfoExtendResponse.getProvinceId()));
            String cityName = addrMap.get(Objects.toString(storeInfoExtendResponse.getCityId()));
            String areaName = addrMap.get(Objects.toString(storeInfoExtendResponse.getAreaId()));
            String streetName = addrMap.get(Objects.toString(storeInfoExtendResponse.getStreetId()));

            storeInfoExtendResponse.setProvinceName(provinceName);
            storeInfoExtendResponse.setCityName(cityName);
            storeInfoExtendResponse.setAreaName(areaName);
            storeInfoExtendResponse.setStreetName(streetName);
        }
        return BaseResponse.success(storeInfoExtendResponse);
    }

    /**
     * 新增店铺基本信息
     * @param request   店铺信息保存参数
     * @return          店铺信息
     */
    @Operation(summary = "新增店铺基本信息")
    @PostMapping(value = "/storeInfo")
    @GlobalTransactional
    public BaseResponse<StoreVO> saveStore(@Valid @RequestBody StoreAddRequest request) {
        if (!Objects.equals(commonUtil.getCompanyInfoId(), request.getCompanyInfoId())) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000014);
        }
        // TODO 是O2O的时候，请求CompanyService保存公司信息
        StoreVO store = storeProvider.add(request).getContext().getStoreVO();

        // 更新数据到es
        esStoreInformationProvider.modifyStoreBasicInfo(
                KsBeanUtil.copyPropertiesThird(request, StoreInfoModifyRequest.class));
        return BaseResponse.success(store);
    }

    /**
     * 修改店铺logo与店招
     * @param request   修改参数
     * @return          是否成功
     */
    @Operation(summary = "修改店铺logo与店招")
    @PutMapping(value = "/storeBaseInfo")
    public BaseResponse updateStoreBaseInfo(@Valid @RequestBody StoreModifyLogoRequest request) {
        // 越权校验
        commonUtil.checkStoreId(request.getStoreId());
        request.setStoreId(commonUtil.getStoreId());
        storeProvider.modifyStoreBaseInfo(request);

        logMqUtil.convertAndSend("设置","基本设置","编辑基本设置");
        return BaseResponse.SUCCESSFUL();
    }

    /**
     * 修改店铺自提开关
     * @param request   修改参数
     * @return          是否成功
     */
    @Operation(summary = "修改店铺自提开关")
    @PutMapping(value = "/modifyPickupState")
    public BaseResponse modifyPickupState(@Valid @RequestBody StorePickupStateModifyRequest request) {
        request.setStoreId(commonUtil.getStoreId());
        storeProvider.modifyPickupState(request);
        logMqUtil.convertAndSend("设置","设置自提开关","设置自提开关");
        return BaseResponse.SUCCESSFUL();
    }

    /**
     * 修改店铺基本信息
     * @param saveRequest      店铺基本信息参数
     * @return                 保存的店铺信息
     */
    @Operation(summary = "修改店铺基本信息")
    @PutMapping(value = "/storeInfo")
    @GlobalTransactional
    public BaseResponse<StoreVO> updateStore(@RequestBody StoreSaveRequest saveRequest) {

        if (Objects.isNull(saveRequest.getStoreId()) || Objects.isNull(saveRequest.getCompanyInfoId())) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }

        if (!Objects.equals(commonUtil.getCompanyInfoId(), saveRequest.getCompanyInfoId())) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000014);
        }
        saveRequest.setAccountType(AccountType.s2bSupplier);
        StoreVO store = baseService.updateStoreForSupplier(saveRequest);
        // 更新数据到es
        StoreInfoModifyRequest storeInfoModifyRequest = KsBeanUtil.copyPropertiesThird(store,StoreInfoModifyRequest.class);
        KsBeanUtil.copyPropertiesThird(store.getCompanyInfo(),storeInfoModifyRequest);

        logMqUtil.convertAndSend("设置","店铺信息","编辑店铺信息");
        esStoreInformationProvider.modifyStoreBasicInfo(storeInfoModifyRequest);

        //更新商品的对应店铺名称，刷新商品es
        goodsProducer.updateGoodsStoreNameByStoreId(GoodsModifyStoreNameByStoreIdRequest.builder()
                .storeId(saveRequest.getStoreId())
                .supplierName(saveRequest.getSupplierName())
                .storeName(saveRequest.getStoreName()).build());

        return BaseResponse.success(store);
    }

    /**
     * 修改店铺运费计算方式
     * @param request   修改请求参数
     * @return          是否修改成功
     */
    @Operation(summary = "修改店铺运费计算方式")
    @PutMapping(value = "/storeInfo/freightType")
    public BaseResponse updateStoreFreightType(@RequestBody StoreSaveRequest request) {
        if (commonUtil.getStoreId() == null) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        baseService.updateStoreFreightType(commonUtil.getStoreId(),request.getFreightTemplateType());
        logMqUtil.convertAndSend("设置","设置运费计算模式",
                "设置运费计算模式："+ (request.getFreightTemplateType().equals(DefaultFlag.YES) ? "单品运费" : "店铺运费"));
        return BaseResponse.SUCCESSFUL();
    }

    /**
     * 查询店铺的会员信息
     * bail 2017-11-16
     *
     * @return 会员信息
     */
    @Operation(summary = "查询店铺的会员信息")
    @PostMapping(value = "/store-customers")
    public BaseResponse<BaseResponse<StoreCustomerResponse>> customers() {
        return BaseResponse.success(storeCustomerQueryProvider.listCustomerByStoreId(new StoreCustomerQueryRequest(commonUtil.getStoreId())));
    }

    /**
     * 查询店铺关联的所有会员信息
     * 未删除且通过审核的
     * wj 2017-12-22
     *
     * @return 会员信息
     */
    @Operation(summary = "查询店铺的会员信息，不区分会员的禁用状态")
    @PostMapping(value = "/allCustomers")
    public BaseResponse<List<StoreCustomerVO>> allCustomers() {
        return BaseResponse.success(
                storeCustomerQueryProvider.listAllCustomer(
                        new StoreCustomerQueryRequest(commonUtil.getStoreId())).getContext().getStoreCustomerVOList());
    }

    /**
     * 查询平台前50条未删除并且通过审核的会员信息
     * wj 2017-12-22
     * @return 会员信息
     */
    @Operation(summary = "查询平台会员信息")
    @PostMapping(value = "/allBossCustomers")
    public BaseResponse<List<StoreCustomerVO>> allBossCustomers() {
        return BaseResponse.success(storeCustomerQueryProvider
                .listBossAllCustomer()
                .getContext()
                .getStoreCustomerVOList()
                .stream()
                .peek(v->v.setCustomerAccount(null))
                .collect(Collectors.toList()));
    }

    /**
     * 根据会员名模糊匹配
     * 前50条未删除并且通过审核的平台会员信息
     * wj 2017-12-22
     *
     * @return 会员信息
     */
    @Operation(summary = "查询平台会员信息")
    @Parameter(name = "customerName", description = "会员名称", required = true)
    @GetMapping(value = "/bossCustomersByName/{customerName}")
    public BaseResponse<List<StoreCustomerVO>> bossCustomersByName(@PathVariable String customerName) {
        return BaseResponse.success(
                storeCustomerQueryProvider.listBossCustomerByName(new StoreCustomerQueryByCustomerNameRequest(customerName)).getContext()
                        .getStoreCustomerVOList());
    }

    /**
     * 获取商家boss的二维码（扫码进入以后显示的是店铺首页）
     * @return  商家boss的二维码
     */
    @Operation(summary = "获取商家boss的二维码（扫码进入以后显示的是店铺首页）")
    @Parameter(name = "storeId", description = "店铺Id", required = true)
    @PostMapping(value = "/getS2bSupplierQrcode/{storeId}")
    public BaseResponse<String> getS2bSupplierQrcode(@PathVariable Long storeId){
        StoreVO store = storeQueryProvider.getNoDeleteStoreById(new NoDeleteStoreByIdRequest(storeId))
                .getContext().getStoreVO();
        // 店铺码不为空，直接返回
        if(StringUtils.isNotBlank(store.getSmallProgramCode())){
            return BaseResponse.success(store.getSmallProgramCode());
        }
        // 没有重新生成
        MiniProgramQrCodeRequest request = new MiniProgramQrCodeRequest();
        request.setPage("pages/sharepage/sharepage");
        request.setScene("/store-main/"+storeId);

        String codeUrl = wechatAuthProvider.getWxaCodeUnlimit(request).getContext();
        // 更新字段
        if(StringUtils.isNotBlank(codeUrl)){
            storeProvider.updateStoreSmallProgram(new StoreInfoSmallProgramRequest(storeId,codeUrl));
        }
        return BaseResponse.success(codeUrl);
    }
}
