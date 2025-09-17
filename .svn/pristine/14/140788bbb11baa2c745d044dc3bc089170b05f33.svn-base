package com.wanmi.sbc.openapisetting;

import com.wanmi.sbc.common.annotation.MultiSubmit;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.*;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.DESUtil;
import com.wanmi.sbc.common.util.MD5Util;
import com.wanmi.sbc.common.util.auth.NetworkUtil;
import com.wanmi.sbc.customer.api.provider.store.StoreQueryProvider;
import com.wanmi.sbc.customer.api.request.store.ListStoreByIdsRequest;
import com.wanmi.sbc.customer.api.request.store.StoreByIdRequest;
import com.wanmi.sbc.customer.api.response.store.ListStoreByIdsResponse;
import com.wanmi.sbc.customer.api.response.store.StoreByIdResponse;
import com.wanmi.sbc.customer.bean.vo.StoreVO;
import com.wanmi.sbc.setting.api.provider.openapisetting.OpenApiSettingProvider;
import com.wanmi.sbc.setting.api.provider.openapisetting.OpenApiSettingQueryProvider;
import com.wanmi.sbc.setting.api.request.openapisetting.*;
import com.wanmi.sbc.setting.api.response.openapisetting.OpenApiSettingByIdResponse;
import com.wanmi.sbc.setting.api.response.openapisetting.OpenApiSettingGetSecretResponse;
import com.wanmi.sbc.setting.api.response.openapisetting.OpenApiSettingListResponse;
import com.wanmi.sbc.setting.api.response.openapisetting.OpenApiSettingPageResponse;
import com.wanmi.sbc.setting.bean.vo.OpenApiSettingSecretVO;
import com.wanmi.sbc.setting.bean.vo.OpenApiSettingVO;
import com.wanmi.sbc.util.CommonUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.net.InetAddress;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @description 开放平台api设置管理API
 * @author lvzhenwei
 * @date 2021/4/13 3:15 下午
 */
@Tag(name =  "开放平台api设置管理API", description =  "OpenApiSettingController")
@RestController
@Validated
@RequestMapping(value = "/open-api-setting")
public class OpenApiSettingController {

    @Autowired private OpenApiSettingQueryProvider openApiSettingQueryProvider;

    @Autowired private OpenApiSettingProvider openApiSettingProvider;

    @Autowired private CommonUtil commonUtil;

    @Autowired private StoreQueryProvider storeQueryProvider;

    @Value("${openApi.secret.key}")
    private String openApiSecretKey;

    /**
     * @description 分页查询开放平台api设置
     * @author lvzhenwei
     * @date 2021/4/14 3:14 下午
     * @param pageReq
     * @return
     *     com.wanmi.sbc.common.base.BaseResponse<com.wanmi.sbc.setting.api.response.openapisetting.OpenApiSettingPageResponse>
     */
    @Operation(summary = "分页查询开放平台api设置")
    @PostMapping("/page")
    public BaseResponse<OpenApiSettingPageResponse> getPage(
            @RequestBody @Valid OpenApiSettingPageRequest pageReq) {
        BaseResponse<OpenApiSettingPageResponse> baseResponse =
                openApiSettingQueryProvider.page(pageReq);
        if (Objects.isNull(baseResponse) || Objects.isNull(baseResponse.getContext())) {
            return baseResponse;
        }
        List<Long> storeIdList = new ArrayList<>();
        baseResponse
                .getContext()
                .getOpenApiSettingVOPage()
                .getContent()
                .forEach(
                        openApiSettingVO -> {
                            storeIdList.add(openApiSettingVO.getStoreId());
                        });
        // 批量查询 门店信息
        BaseResponse<ListStoreByIdsResponse> storeListBaseResponse =
                storeQueryProvider.listByIds(
                        ListStoreByIdsRequest.builder().storeIds(storeIdList).build());
        if (Objects.isNull(storeListBaseResponse)
                || Objects.isNull(storeListBaseResponse.getContext())) {
            return baseResponse;
        }
        Map<Long, StoreVO> payChannelItemVoMap =
                storeListBaseResponse.getContext().getStoreVOList().stream()
                        .collect(Collectors.toMap(StoreVO::getStoreId, StoreVO -> StoreVO));
        baseResponse
                .getContext()
                .getOpenApiSettingVOPage()
                .getContent()
                .forEach(
                        openApiSettingVO -> {
                            if (payChannelItemVoMap.containsKey(openApiSettingVO.getStoreId())) {
                                openApiSettingVO.setCompanyType(
                                        payChannelItemVoMap
                                                .get(openApiSettingVO.getStoreId())
                                                .getCompanyType());
                                openApiSettingVO.setStoreName(payChannelItemVoMap
                                        .get(openApiSettingVO.getStoreId()).getStoreName());
                                openApiSettingVO.setSupplierName(payChannelItemVoMap
                                        .get(openApiSettingVO.getStoreId()).getSupplierName());
                            }
                        });
        return baseResponse;
    }

    /**
     * @description 列表查询开放平台api设置
     * @author lvzhenwei
     * @date 2021/4/14 3:15 下午
     * @param listReq
     * @return
     *     com.wanmi.sbc.common.base.BaseResponse<com.wanmi.sbc.setting.api.response.openapisetting.OpenApiSettingListResponse>
     */
    @Operation(summary = "列表查询开放平台api设置")
    @PostMapping("/list")
    public BaseResponse<OpenApiSettingListResponse> getList(
            @RequestBody @Valid OpenApiSettingListRequest listReq) {
        listReq.setDelFlag(DeleteFlag.NO);
        listReq.putSort("id", "desc");
        return openApiSettingQueryProvider.list(listReq);
    }

    /**
     * @description 根据id查询开放平台api设置
     * @author lvzhenwei
     * @date 2021/4/14 3:15 下午
     * @param id
     * @return
     *     com.wanmi.sbc.common.base.BaseResponse<com.wanmi.sbc.setting.api.response.openapisetting.OpenApiSettingByIdResponse>
     */
    @Operation(summary = "根据id查询开放平台api设置")
    @GetMapping("/{id}")
    public BaseResponse<OpenApiSettingByIdResponse> getById(@PathVariable Long id) {
        if (id == null) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        OpenApiSettingByIdRequest idReq = new OpenApiSettingByIdRequest();
        idReq.setId(id);
        return openApiSettingQueryProvider.getById(idReq);
    }

    /**
     * 根据Store查询开放平台api设置
     *
     * @author wur
     * @date: 2021/4/25 11:55
     */
    @Operation(summary = "根据Store查询开放平台api设置")
    @GetMapping("/by-store-id")
    public BaseResponse<OpenApiSettingByIdResponse> byStoreId() {
        return openApiSettingQueryProvider.getByStoreId(
                OpenApiSettingByStoreIdRequest.builder().storeId(commonUtil.getStoreId()).build());
    }

    /**
     * @description 新增开放平台api设置
     * @author lvzhenwei
     * @date 2021/4/14 3:16 下午
     * @param addReq
     * @return
     *     com.wanmi.sbc.common.base.BaseResponse<com.wanmi.sbc.setting.api.response.openapisetting.OpenApiSettingAddResponse>
     */
    @Operation(summary = "新增开放平台api设置")
    @PostMapping("/add")
    public BaseResponse<OpenApiSettingGetSecretResponse> add(
            @RequestBody @Valid OpenApiSettingAddRequest addReq) {
        if(PlatformType.STORE.equals(addReq.getPlatformType())){
            StoreByIdResponse storeByIdResponse =
                    storeQueryProvider
                            .getById(StoreByIdRequest.builder().storeId(addReq.getStoreId()).build())
                            .getContext();
            if (Objects.nonNull(storeByIdResponse)) {
                StoreVO storeVO = storeByIdResponse.getStoreVO();
                if (Objects.nonNull(storeVO)) {
                    addReq.setStoreName(storeVO.getStoreName());
                    addReq.setSupplierName(storeVO.getSupplierName());
                }
            }
        }
        addReq.setContractStartDate(LocalDateTime.now());
        addReq.setAppKey(generatorAppKey(addReq.getStoreId().toString()));
        addReq.setDelFlag(DeleteFlag.NO);
        addReq.setCreatePerson(commonUtil.getOperatorId());
        addReq.setDisableState(EnableStatus.ENABLE);
        addReq.setAppSecret(generatorAppSecret(addReq.getAppKey()));
        addReq.setAuditState(AuditStatus.CHECKED);
        openApiSettingProvider.add(addReq);
        OpenApiSettingSecretVO openApiSettingSecretVO = new OpenApiSettingSecretVO();
        openApiSettingSecretVO.setAppSecret(addReq.getAppSecret());
        return BaseResponse.success(
                OpenApiSettingGetSecretResponse.builder()
                        .openApiSettingSecretVO(openApiSettingSecretVO)
                        .build());
    }

    /**
     * 商家申请开放平台api设置
     *
     * @author lvzhenwei
     * @date 2021/4/14 3:16 下午
     * @return
     *     com.wanmi.sbc.common.base.BaseResponse<com.wanmi.sbc.setting.api.response.openapisetting.OpenApiSettingAddResponse>
     */
    @MultiSubmit
    @Operation(summary = "商家申请开放平台api设置")
    @GetMapping("/apply")
    public BaseResponse apply() {
        StoreByIdResponse storeByIdResponse =
                storeQueryProvider
                        .getById(
                                StoreByIdRequest.builder().storeId(commonUtil.getStoreId()).build())
                        .getContext();
        OpenApiSettingAddRequest addReq = new OpenApiSettingAddRequest();
        if (Objects.nonNull(storeByIdResponse)) {
            StoreVO storeVO = storeByIdResponse.getStoreVO();
            if (Objects.nonNull(storeVO)) {
                addReq.setStoreName(storeVO.getStoreName());
                addReq.setSupplierName(storeVO.getSupplierName());
                addReq.setStoreType(storeVO.getStoreType());
                addReq.setStoreId(storeVO.getStoreId());
            }
        }
        addReq.setContractStartDate(LocalDateTime.now());
        addReq.setDelFlag(DeleteFlag.NO);
        addReq.setCreatePerson(commonUtil.getOperatorId());
        addReq.setDisableState(EnableStatus.ENABLE);
        addReq.setAuditState(AuditStatus.WAIT_CHECK);
        addReq.setPlatformType(PlatformType.STORE);
        openApiSettingProvider.add(addReq);
        return BaseResponse.SUCCESSFUL();
    }

    /**
     * @description 修改开放平台api设置
     * @author lvzhenwei
     * @date 2021/4/14 3:16 下午
     * @param modifyReq
     * @return
     *     com.wanmi.sbc.common.base.BaseResponse<com.wanmi.sbc.setting.api.response.openapisetting.OpenApiSettingModifyResponse>
     */
    @Operation(summary = "修改开放平台api设置")
    @PutMapping("/modify")
    public BaseResponse modify(@RequestBody @Valid OpenApiSettingModifyRequest modifyReq) {
        modifyReq.setUpdatePerson(commonUtil.getOperatorId());
        modifyReq.setPlatformType(PlatformType.BOSS);
        return openApiSettingProvider.modify(modifyReq);
    }

    /**
     * @description 根据id删除开放平台api设置
     * @author lvzhenwei
     * @date 2021/4/14 3:16 下午
     * @param id
     * @return com.wanmi.sbc.common.base.BaseResponse
     */
    @Operation(summary = "根据id删除开放平台api设置")
    @DeleteMapping("/{id}")
    public BaseResponse deleteById(@PathVariable Long id) {
        if (id == null) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        OpenApiSettingDelByIdRequest delByIdReq = new OpenApiSettingDelByIdRequest();
        delByIdReq.setId(id);
        return openApiSettingProvider.deleteById(delByIdReq);
    }

    /**
     * @description 开放平台重置secret
     * @author lvzhenwei
     * @date 2021/4/14 4:56 下午
     * @param id
     * @return com.wanmi.sbc.common.base.BaseResponse
     */
    @Operation(summary = "开放平台重置secret")
    @GetMapping("/reset-secret/{id}")
    public BaseResponse<OpenApiSettingGetSecretResponse> resetSecret(@PathVariable Long id) {
        if (id == null) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        OpenApiSettingByIdRequest idReq = new OpenApiSettingByIdRequest();
        idReq.setId(id);
        OpenApiSettingVO openApiSettingVO =
                openApiSettingQueryProvider.getById(idReq).getContext().getOpenApiSettingVO();
        String secret = generatorAppSecret(openApiSettingVO.getAppKey());
        openApiSettingProvider.resetAppSecret(
                OpenApiSettingResetSecretRequest.builder().appSecret(secret).id(id).build());
        OpenApiSettingSecretVO openApiSettingSecretVO = new OpenApiSettingSecretVO();
        openApiSettingSecretVO.setAppSecret(secret);
        return BaseResponse.success(
                OpenApiSettingGetSecretResponse.builder()
                        .openApiSettingSecretVO(openApiSettingSecretVO)
                        .build());
    }

    /**
     * @description 开放平台开放权限审核
     * @author lvzhenwei
     * @date 2021/4/14 4:06 下午
     * @param request
     * @return com.wanmi.sbc.common.base.BaseResponse
     */
    @Operation(summary = "开放平台开放权限审核")
    @PostMapping(value = "/check-audit-state")
    public BaseResponse<OpenApiSettingGetSecretResponse> checkAuditState(
            @RequestBody @Valid OpenApiSettingCheckAuditStateRequest request) {
        OpenApiSettingSecretVO openApiSettingSecretVO = new OpenApiSettingSecretVO();
        if (request.getAuditState() == AuditStatus.CHECKED) {
            BaseResponse<OpenApiSettingByIdResponse> baseResponse =
                    openApiSettingQueryProvider.getById(
                            OpenApiSettingByIdRequest.builder().id(request.getId()).build());
            if (Objects.isNull(baseResponse.getContext().getOpenApiSettingVO())) {
                throw new SbcRuntimeException(CommonErrorCodeEnum.K000003);
            }
            String appKey = generatorAppKey((baseResponse.getContext().getOpenApiSettingVO().getStoreId().toString()));
            String appSecret =generatorAppSecret(appKey);
            openApiSettingSecretVO.setAppSecret(appSecret);
            request.setAppKey(appKey);
            request.setAppSecret(appSecret);
        }
        openApiSettingProvider.checkAuditState(request);
        return BaseResponse.success(
                OpenApiSettingGetSecretResponse.builder()
                        .openApiSettingSecretVO(openApiSettingSecretVO)
                        .build());
    }

    /**
     * @description 开放平台启用/禁用
     * @author lvzhenwei
     * @date 2021/4/14 4:06 下午
     * @param request
     * @return com.wanmi.sbc.common.base.BaseResponse
     */
    @Operation(summary = "开放平台启用/禁用")
    @PostMapping(value = "/change-disable-state")
    public BaseResponse changeDisableState(
            @RequestBody @Valid OpenApiSettingChangeDisableStateRequest request) {
        openApiSettingProvider.changeDisableState(request);
        return BaseResponse.SUCCESSFUL();
    }

    /**
     * @description app key、secret生成规则 先进行md5加密生成16位，然后在对md5加密结果进行base64加密 storeId+时间戳+mac地址生成key
     * @author lvzhenwei
     * @date 2021/4/12 7:34 下午
     * @param str   商户ID
     * @return java.lang.String
     */
    private String generatorAppKey(String str) {
        try {
            InetAddress inetAddress = NetworkUtil.getLocalHostLANAddress();
            String macAddress = NetworkUtil.getMacByInetAddress(inetAddress);
            str = str + LocalDateTime.now().toString() + macAddress;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Base64.getEncoder().encodeToString(MD5Util.md5Hex(str).substring(8, 24).getBytes());
    }

    /**
     * 根据AppKey获取AppSecret，将AppKey拼接时间戳后DES加密
     *
     * @author wur
     * @date: 2021/4/30 14:00
     * @param appKey appKey
     * @return AppSecret
     */
    private String generatorAppSecret(String appKey) {
        try {
            appKey = appKey + LocalDateTime.now().toString();
            return DESUtil.encryptAppointKey(appKey, openApiSecretKey);
        } catch (Exception e) {
            e.printStackTrace();
        }
        throw new SbcRuntimeException(CommonErrorCodeEnum.K000001);
    }
}
