package com.wanmi.sbc.system;

import com.alibaba.fastjson2.JSONObject;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.setting.api.provider.systemconfig.SystemConfigSaveProvider;
import com.wanmi.sbc.setting.api.request.*;
import com.wanmi.sbc.setting.api.provider.AuditProvider;
import com.wanmi.sbc.setting.api.provider.AuditQueryProvider;
import com.wanmi.sbc.setting.api.request.ConfigContextModifyByTypeAndKeyRequest;
import com.wanmi.sbc.setting.api.request.ConfigQueryRequest;
import com.wanmi.sbc.setting.api.request.ConfigRequest;
import com.wanmi.sbc.setting.api.request.ConfigStatusModifyByTypeAndKeyRequest;
import com.wanmi.sbc.setting.api.provider.systemconfig.SystemConfigQueryProvider;
import com.wanmi.sbc.setting.api.request.systemconfig.SystemConfigQueryRequest;
import com.wanmi.sbc.setting.api.response.AuditConfigListResponse;
import com.wanmi.sbc.setting.api.response.GoodsColumnShowResponse;
import com.wanmi.sbc.setting.api.response.systemconfig.SystemConfigTypeResponse;
import com.wanmi.sbc.setting.bean.enums.ConfigKey;
import com.wanmi.sbc.setting.bean.enums.ConfigType;
import com.wanmi.sbc.setting.bean.vo.ConfigVO;
import com.wanmi.sbc.setting.bean.vo.EsGoodsBoostSettingVO;
import com.wanmi.sbc.setting.bean.vo.SeoSettingVO;
import com.wanmi.sbc.setting.bean.vo.SystemConfigVO;
import com.wanmi.sbc.util.OperateLogMQUtil;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * S2B 平台端-审核开关配置
 * Created by wj on 2017/12/06.
 */
@Tag(name = "BossConfigController", description = "S2B 平台端-审核开关配置API")
@RestController
@Validated
@RequestMapping("/boss/config")
public class BossConfigController {

    @Resource
    private AuditQueryProvider auditQueryProvider;

    @Autowired
    private AuditProvider auditProvider;

    @Autowired
    private OperateLogMQUtil operateLogMQUtil;

    @Autowired
    private SystemConfigQueryProvider systemConfigQueryProvider;

    @Autowired
    private SystemConfigSaveProvider systemConfigSaveProvider;

    @Operation(summary = "查询审核开关状态")
    @RequestMapping(value = "/audit/list", method = RequestMethod.GET)
    public BaseResponse<List<ConfigVO>> listConfigs() {
        return BaseResponse.success(auditQueryProvider.listAuditConfig().getContext().getConfigVOList());
    }

    @Operation(summary = "查询商品配置")
    @RequestMapping(value = "/audit/list-goods-configs",method = RequestMethod.GET)
    public BaseResponse<List<ConfigVO>> queryGoodsSettingConfigs() {
        ConfigQueryRequest request = new ConfigQueryRequest();
        request.setConfigKey(ConfigKey.GOODS_SETTING.toString());
        AuditConfigListResponse listResponse = auditQueryProvider.getByConfigKey(request).getContext();
        List<ConfigVO> configVOList = listResponse.getConfigVOList();
        List<ConfigVO> newResult = configVOList.stream().peek(conf -> {
            if (StringUtils.equals(conf.getConfigType(), ConfigType.GOODS_EVALUATE_SETTING.toValue())) {
                String context = conf.getContext();
                if(StringUtils.isNotBlank(context)){
                    JSONObject jsonObject = JSONObject.parseObject(context);
                    Integer isShow = jsonObject.getInteger("isShow");
                    conf.setIsShow(isShow);
                    return;
                }
                conf.setIsShow(NumberUtils.INTEGER_ZERO);
            }
        }).collect(Collectors.toList());
        return BaseResponse.success(newResult);
    }

    /**
     * 开启商品审核开关
     *
     * @return BaseResponse
     */
    @Operation(summary = "开启商品审核开关")
    @RequestMapping(value = "/audit/goods/open", method = RequestMethod.POST)
    public BaseResponse openAuditGoods() {
        ConfigStatusModifyByTypeAndKeyRequest request = new ConfigStatusModifyByTypeAndKeyRequest();
        request.setConfigKey(ConfigKey.S2BAUDIT);
        request.setConfigType(ConfigType.SUPPLIERGOODSAUDIT);
        request.setStatus(1);

        auditProvider.modifyStatusByTypeAndKey(request);

        operateLogMQUtil.convertAndSend("设置", "修改审核开关", "修改审核开关：商品审核设为开");
        return BaseResponse.SUCCESSFUL();
    }

    /**
     * 关闭商品审核开关
     *
     * @return BaseResponse
     */
    @Operation(summary = "关闭商品审核开关")
    @RequestMapping(value = "/audit/goods/close", method = RequestMethod.POST)
    public BaseResponse closeAuditGoods() {
        ConfigStatusModifyByTypeAndKeyRequest request = new ConfigStatusModifyByTypeAndKeyRequest();
        request.setConfigKey(ConfigKey.S2BAUDIT);
        request.setConfigType(ConfigType.SUPPLIERGOODSAUDIT);
        request.setStatus(0);

        auditProvider.modifyStatusByTypeAndKey(request);

        operateLogMQUtil.convertAndSend("设置", "修改审核开关", "修改审核开关：商品审核设为关");
        operateLogMQUtil.convertAndSend("设置", "修改审核开关", "修改审核开关：自营商品审核设为关");
        return BaseResponse.SUCCESSFUL();
    }

    /**
     * 开启自营商品审核开关
     *
     * @return BaseResponse
     */
    @Operation(summary = "开启自营商品审核开关")
    @RequestMapping(value = "/audit/goods/self/open", method = RequestMethod.POST)
    public BaseResponse openAuditSelfGoods() {
        ConfigStatusModifyByTypeAndKeyRequest request = new ConfigStatusModifyByTypeAndKeyRequest();
        request.setConfigKey(ConfigKey.S2BAUDIT);
        request.setConfigType(ConfigType.BOSSGOODSAUDIT);
        request.setStatus(1);

        auditProvider.modifyStatusByTypeAndKey(request);
        operateLogMQUtil.convertAndSend("设置", "修改审核开关", "修改审核开关：自营商品审核设为开");
        return BaseResponse.SUCCESSFUL();
    }

    /**
     * 关闭自营商品审核开关
     *
     * @return BaseResponse
     */
    @Operation(summary = "关闭自营商品审核开关")
    @RequestMapping(value = "/audit/goods/self/close", method = RequestMethod.POST)
    public BaseResponse closeAuditSelfGoods() {
        ConfigStatusModifyByTypeAndKeyRequest request = new ConfigStatusModifyByTypeAndKeyRequest();
        request.setConfigKey(ConfigKey.S2BAUDIT);
        request.setConfigType(ConfigType.BOSSGOODSAUDIT);
        request.setStatus(0);

        auditProvider.modifyStatusByTypeAndKey(request);
        operateLogMQUtil.convertAndSend("设置", "修改审核开关", "修改审核开关：自营商品审核设为关");
        return BaseResponse.SUCCESSFUL();
    }

    /**
     * 开启订单审核开关
     *
     * @return BaseResponse
     */
    @Operation(summary = "开启订单审核开关")
    @RequestMapping(value = "/audit/order/open", method = RequestMethod.POST)
    public BaseResponse openAuditOrder() {
        ConfigStatusModifyByTypeAndKeyRequest request = new ConfigStatusModifyByTypeAndKeyRequest();
        request.setConfigKey(ConfigKey.S2BAUDIT);
        request.setConfigType(ConfigType.ORDERAUDIT);
        request.setStatus(1);

        auditProvider.modifyStatusByTypeAndKey(request);
        operateLogMQUtil.convertAndSend("设置", "修改审核开关", "修改审核开关：订单审核设为开");
        return BaseResponse.SUCCESSFUL();
    }

    /**
     * 关闭订单审核开关
     *
     * @return BaseResponse
     */
    @Operation(summary = "关闭订单审核开关")
    @RequestMapping(value = "/audit/order/close", method = RequestMethod.POST)
    public BaseResponse closeAuditOrder() {
        ConfigStatusModifyByTypeAndKeyRequest request = new ConfigStatusModifyByTypeAndKeyRequest();
        request.setConfigKey(ConfigKey.S2BAUDIT);
        request.setConfigType(ConfigType.ORDERAUDIT);
        request.setStatus(0);

        auditProvider.modifyStatusByTypeAndKey(request);
        operateLogMQUtil.convertAndSend("设置", "修改审核开关", "修改审核开关：订单审核设为关");
        return BaseResponse.SUCCESSFUL();
    }

    /**
     * 开启客户审核开关
     *
     * @return BaseResponse
     */
    @Operation(summary = "开启客户审核开关")
    @RequestMapping(value = "/audit/customer/open", method = RequestMethod.POST)
    public BaseResponse openAuditCustomer() {
        ConfigStatusModifyByTypeAndKeyRequest request = new ConfigStatusModifyByTypeAndKeyRequest();
        request.setConfigKey(ConfigKey.S2BAUDIT);
        request.setConfigType(ConfigType.CUSTOMERAUDIT);
        request.setStatus(1);

        auditProvider.modifyStatusByTypeAndKey(request);
        operateLogMQUtil.convertAndSend("设置", "修改审核开关", "修改审核开关：客户审核设为开");
        return BaseResponse.SUCCESSFUL();
    }

    /**
     * 关闭客户审核开关
     *
     * @return BaseResponse
     */
    @Operation(summary = "关闭客户审核开关")
    @RequestMapping(value = "/audit/customer/close", method = RequestMethod.POST)
    public BaseResponse closeAuditCustomer() {
        ConfigStatusModifyByTypeAndKeyRequest request = new ConfigStatusModifyByTypeAndKeyRequest();
        request.setConfigKey(ConfigKey.S2BAUDIT);
        request.setConfigType(ConfigType.CUSTOMERAUDIT);
        request.setStatus(0);

        auditProvider.modifyStatusByTypeAndKey(request);
        operateLogMQUtil.convertAndSend("设置", "修改审核开关", "修改审核开关：客户审核设为关");
        return BaseResponse.SUCCESSFUL();
    }

    /**
     * 开启客户信息完善开关
     *
     * @return BaseResponse
     */
    @Operation(summary = "开启客户信息完善开关")
    @RequestMapping(value = "/audit/customer-info/open", method = RequestMethod.POST)
    public BaseResponse openAuditCustomerInfo() {
        ConfigStatusModifyByTypeAndKeyRequest request = new ConfigStatusModifyByTypeAndKeyRequest();
        request.setConfigKey(ConfigKey.S2BAUDIT);
        request.setConfigType(ConfigType.CUSTOMERINFOAUDIT);
        request.setStatus(1);
        auditProvider.modifyStatusByTypeAndKey(request);

        operateLogMQUtil.convertAndSend("设置", "修改审核开关", "修改审核开关：客户信息完善设为开");
        return BaseResponse.SUCCESSFUL();
    }

    /**
     * 关闭客户信息完善开关
     * 关闭客户信息完善开关时，客户审核开关一起关闭
     *
     * @return BaseResponse
     */
    @Operation(summary = "关闭客户信息完善开关-------关闭客户信息完善开关时，客户审核开关一起关闭")
    @RequestMapping(value = "/audit/customer-info/close", method = RequestMethod.POST)
    public BaseResponse closeAuditCustomerInfo() {
        ConfigStatusModifyByTypeAndKeyRequest request = new ConfigStatusModifyByTypeAndKeyRequest();
        request.setConfigKey(ConfigKey.S2BAUDIT);
        request.setConfigType(ConfigType.CUSTOMERINFOAUDIT);
        request.setStatus(0);
        auditProvider.modifyStatusByTypeAndKey(request);

        operateLogMQUtil.convertAndSend("设置", "修改审核开关", "修改审核开关：客户信息完善设为关");
        operateLogMQUtil.convertAndSend("设置", "修改审核开关", "修改审核开关：客户审核设为关");
        return BaseResponse.SUCCESSFUL();
    }


    /**
     * 开启用户审核（即访问需登录）
     *
     * @return
     */
    @Operation(summary = "开启用户审核（即访问需登录）")
    @RequestMapping(value = "/audit/usersetting/open", method = RequestMethod.POST)
    public BaseResponse openUserSetting() {
        ConfigStatusModifyByTypeAndKeyRequest request = new ConfigStatusModifyByTypeAndKeyRequest();
        request.setConfigKey(ConfigKey.S2BAUDIT);
        request.setConfigType(ConfigType.USERAUDIT);
        request.setStatus(1);
        auditProvider.modifyStatusByTypeAndKey(request);
        operateLogMQUtil.convertAndSend("设置", "修改审核开关", "修改审核开关：用户设置设为开");
        return BaseResponse.SUCCESSFUL();
    }

    /**
     * 关闭用户审核（即访问商城无需登录）
     *
     * @return
     */
    @Operation(summary = "关闭用户审核（即访问商城无需登录）")
    @RequestMapping(value = "/audit/usersetting/close", method = RequestMethod.POST)
    public BaseResponse closeUserSetting() {
        ConfigStatusModifyByTypeAndKeyRequest request = new ConfigStatusModifyByTypeAndKeyRequest();
        request.setConfigKey(ConfigKey.S2BAUDIT);
        request.setConfigType(ConfigType.USERAUDIT);
        request.setStatus(0);
        auditProvider.modifyStatusByTypeAndKey(request);

        operateLogMQUtil.convertAndSend("设置", "修改审核开关", "修改审核开关：用户设置设为关");
        return BaseResponse.SUCCESSFUL();
    }

    /**
     * pc端商品列表大小图默认展示设置
     *
     * @return
     */
    @Operation(summary = "pc端商品列表大小图默认展示设置")
    @Parameter(name = "status", description = "状态", required = true)
    @RequestMapping(value = "/audit/imgdisplayforpc/{status}", method = RequestMethod.POST)
    public BaseResponse setDisplayImgForPc(@PathVariable Integer status) {
        if (status != DefaultFlag.NO.toValue() && status != DefaultFlag.YES.toValue()){
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        ConfigStatusModifyByTypeAndKeyRequest request = new ConfigStatusModifyByTypeAndKeyRequest();
        request.setConfigKey(ConfigKey.S2BAUDIT);
        request.setConfigType(ConfigType.PC_GOODS_IMAGE_SWITCH);
        request.setStatus(status);
        auditProvider.modifyStatusByTypeAndKey(request);
        operateLogMQUtil.convertAndSend("设置", "修改商品设置", "修改商品设置：PC商城商品列表默认展示设为" + (status == 1 ? "大图列表" : "小图列表"));
        return BaseResponse.SUCCESSFUL();
    }

    /**
     * PC商城商品列表展示维度SKU或者SPU设置
     *
     * @return
     */
    @Operation(summary = "PC商城商品列表展示维度SKU或者SPU设置")
    @Parameter(name = "status", description = "状态", required = true)
    @RequestMapping(value = "/audit/specdisplayforpc/{status}", method = RequestMethod.POST)
    public BaseResponse setDisplaySpecForPc(@PathVariable Integer status) {
        if (status != DefaultFlag.NO.toValue() && status != DefaultFlag.YES.toValue()){
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        ConfigStatusModifyByTypeAndKeyRequest request = new ConfigStatusModifyByTypeAndKeyRequest();
        request.setConfigKey(ConfigKey.S2BAUDIT);
        request.setConfigType(ConfigType.PC_GOODS_SPEC_SWITCH);
        request.setStatus(status);
        auditProvider.modifyStatusByTypeAndKey(request);
        operateLogMQUtil.convertAndSend("设置", "修改商品设置", "修改商品设置：PC商城商品列表展示维度设为" + (status == 1 ? "SPU维度" : "SKU维度"));
        return BaseResponse.SUCCESSFUL();
    }

    /**
     * 移动端商品列表大小图默认展示设置
     *
     * @return
     */
    @Operation(summary = "移动端商品列表大小图默认展示设置")
    @Parameter(name = "status", description = "状态", required = true)
    @RequestMapping(value = "/audit/imgdisplayformobile/{status}", method = RequestMethod.POST)
    public BaseResponse setDisplayImgForMobile(@PathVariable Integer status) {
        if (status != DefaultFlag.NO.toValue() && status != DefaultFlag.YES.toValue()){
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        ConfigStatusModifyByTypeAndKeyRequest request = new ConfigStatusModifyByTypeAndKeyRequest();
        request.setConfigKey(ConfigKey.S2BAUDIT);
        request.setConfigType(ConfigType.MOBILE_GOODS_IMAGE_SWITCH);
        request.setStatus(status);
        auditProvider.modifyStatusByTypeAndKey(request);
        operateLogMQUtil.convertAndSend("设置", "修改商品设置", "修改商品设置：移动商城商品列表默认展示设为" + (status == 1 ? "大图列表" : "小图列表"));
        return BaseResponse.SUCCESSFUL();
    }

    /**
     * 移动端商城商品列表展示维度SKU或者SPU设置
     *
     * @return
     */
    @Operation(summary = "移动端商城商品列表展示维度SKU或者SPU设置")
    @Parameter(name = "status", description = "状态", required = true)
    @RequestMapping(value = "/audit/specdisplayformobile/{status}", method = RequestMethod.POST)
    public BaseResponse setDisplaySpecForMobile(@PathVariable Integer status) {
        if (status != DefaultFlag.NO.toValue() && status != DefaultFlag.YES.toValue()){
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        ConfigStatusModifyByTypeAndKeyRequest request = new ConfigStatusModifyByTypeAndKeyRequest();
        request.setConfigKey(ConfigKey.S2BAUDIT);
        request.setConfigType(ConfigType.MOBILE_GOODS_SPEC_SWITCH);
        request.setStatus(status);
        auditProvider.modifyStatusByTypeAndKey(request);
        operateLogMQUtil.convertAndSend("设置", "修改商品设置", "修改商品设置：移动商城商品列表展示维度设为" + (status == 1 ? "SPU维度" : "SKU维度"));
        return BaseResponse.SUCCESSFUL();
    }

    /** 商品评价开关设置 */
    @Operation(summary = "商品评价开关设置")
    @Parameter(
            name = "status",
            description = "状态",
            required = true)
    @RequestMapping(value = "/audit/goods-evaluate/{status}/{isShow}", method = RequestMethod.POST)
    public BaseResponse setGoodsEvaluateSwitch(
            @PathVariable Integer status, @PathVariable Integer isShow) {
        ConfigStatusModifyByTypeAndKeyRequest request = new ConfigStatusModifyByTypeAndKeyRequest();
        request.setConfigKey(ConfigKey.GOODS_SETTING);
        request.setConfigType(ConfigType.GOODS_EVALUATE_SETTING);
        request.setStatus(status);
        request.setIsShow(isShow);
        auditProvider.modifyStatusByTypeAndKey(request);
        operateLogMQUtil.convertAndSend(
                "设置", "修改商品设置", "修改商品设置：商品评价" + (status == 1 ? "开放" : "关闭") + "展示");
        return BaseResponse.SUCCESSFUL();
    }

    @Operation(summary = "仅展示有货商品开关设置")
    @Parameter(name = "status", description = "状态", required = true)
    @RequestMapping(value = "/audit/goods-out-of-stock-show/{status}", method = RequestMethod.POST)
    public BaseResponse setGoodsOutOfStockShowSwitch(@PathVariable Integer status) {
        if (status != Constants.ZERO && status != Constants.ONE){
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000001);
        }
        ConfigStatusModifyByTypeAndKeyRequest request = new ConfigStatusModifyByTypeAndKeyRequest();
        request.setConfigKey(ConfigKey.GOODS_SETTING);
        request.setConfigType(ConfigType.GOODS_OUT_OF_STOCK_SHOW_SETTING);
        request.setStatus(status);
        auditProvider.modifyStatusByTypeAndKey(request);
        operateLogMQUtil.convertAndSend("设置", "修改商品设置", "修改商品设置：仅展示有货商品开关设置" + (status == 1 ? "开放" : "关闭") + "展示");
        return BaseResponse.SUCCESSFUL();
    }

    /**
     * 小程序分享设置
     *
     * @param request
     * @return
     */
    @Operation(summary = "小程序分享设置")
    @RequestMapping(value = "/audit/modify-share-little-program", method = RequestMethod.POST)
    public BaseResponse modifyShareLittleProgram(@RequestBody ConfigContextModifyByTypeAndKeyRequest request) {
        request.setConfigKey(ConfigKey.S2BAUDIT);
        request.setConfigType(ConfigType.APPLET_SHARE_SETTING);
        auditProvider.modifyShareLittleProgram(request);
        return BaseResponse.SUCCESSFUL();
    }

    /**
     * 修改用户设置（访问商城是否需要登录）
     *
     * @param request
     * @return
     */
    @Operation(summary = "修改访问商城是否需要登录")
    @RequestMapping(value = "/audit/usersetting/modify", method = RequestMethod.POST)
    public BaseResponse modifyUserSetting(@RequestBody @Valid UserSettingModifyRequest request) {

        // 1. 组装登录访问商城开关设置
        ConfigVO userSetting = new ConfigVO();
        userSetting.setConfigKey(ConfigKey.S2BAUDIT.toValue());
        userSetting.setConfigType(ConfigType.USERAUDIT.toValue());
        userSetting.setStatus(request.getVisitWithLoginStatus().toValue());

        // 3. 组装配置列表
        List<ConfigVO> configRequestList = new ArrayList<>();
        configRequestList.add(userSetting);
        auditProvider.modifyConfigList(new ConfigListModifyRequest(configRequestList));
        return BaseResponse.SUCCESSFUL();
    }

    /**
     * 修改小程序分享设置
     *
     * @param request
     * @return
     */
    @Operation(summary = "修改小程序分享设置")
    @RequestMapping(value = "/audit/minisetting/modify", method = RequestMethod.POST)
    public BaseResponse modifyUserSetting(@RequestBody @Valid MiniSettingModifyRequest request) {
        // 0. 数据校验
        try {
            JSONObject parseObject = JSONObject.parseObject(request.getAppletShareSettingContext());
            if (StringUtils.isBlank(parseObject.getString("title"))) {
                // 标题不能为空
                throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
            }
            if (StringUtils.isBlank(parseObject.getJSONArray("imgUrl").getJSONObject(0).getString("url"))) {
                // 图片地址不能为空
                throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
            }
        } catch (Exception e) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }

        // 1. 组装小程序分享设置
        ConfigVO appletShareSetting = new ConfigVO();
        appletShareSetting.setConfigKey(ConfigKey.S2BAUDIT.toValue());
        appletShareSetting.setConfigType(ConfigType.APPLET_SHARE_SETTING.toValue());
        appletShareSetting.setContext(request.getAppletShareSettingContext());

        // 3. 组装配置列表
        List<ConfigVO> configRequestList = new ArrayList<>();
        configRequestList.add(appletShareSetting);
        auditProvider.modifyConfigList(new ConfigListModifyRequest(configRequestList));
        return BaseResponse.SUCCESSFUL();
    }

    @Operation(summary = "修改移动商城商品列表展示字段")
    @PostMapping(value = "/audit/goods-column-show-update")
    public BaseResponse goodsColumnShowForMobileUpdate(@RequestBody @Valid ConfigRequest request){
        Integer status = request.getStatus();
        if (status != Constants.ONE && status != Constants.ZERO){
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }

        request.setConfigKey(ConfigKey.GOODS_COLUMN_SHOW);
        auditProvider.goodsColumnShowForMobileUpdate(request);
        operateLogMQUtil.convertAndSend("设置", "修改移动商城商品列表展示字段", "修改移动商城商品列表展示字段");
        return BaseResponse.SUCCESSFUL();
    }

    @Operation(summary = "移动商城商品列表展示字段")
    @GetMapping(value = "/audit/goods-column-show")
    public BaseResponse<GoodsColumnShowResponse> goodsColumnShowForMobile(){
        ConfigContextModifyByTypeAndKeyRequest request = new ConfigContextModifyByTypeAndKeyRequest();
        request.setConfigKey(ConfigKey.GOODS_COLUMN_SHOW);

        return auditQueryProvider.goodsColumnShowForMobile(request);
    }

    @Operation(summary = "查询es权重配置")
    @RequestMapping(value = "/esGoodsQuerySetting", method = RequestMethod.GET)
    public BaseResponse<EsGoodsBoostSettingVO> esGoodsQuerySetting() {
        List<SystemConfigVO> systemConfigVOList = systemConfigQueryProvider.list(SystemConfigQueryRequest.builder()
                .configType(ConfigType.ES_QUERY_BOOST.toValue()).delFlag(DeleteFlag.NO).build()).getContext().getSystemConfigVOList();
        if (!CollectionUtils.isEmpty(systemConfigVOList)) {
            String context = systemConfigVOList.get(0).getContext();
            if (StringUtils.isNotBlank(context)) {
                return BaseResponse.success(JSONObject.parseObject(context, EsGoodsBoostSettingVO.class));
            }
        }
        return BaseResponse.success(new EsGoodsBoostSettingVO());
    }

    @Operation(summary = "修改es权重配置")
    @RequestMapping(value = "/updateEsGoodsQuerySetting", method = RequestMethod.PUT)
    public BaseResponse esGoodsQuerySettingModify(@Valid @RequestBody EsGoodsBoostModifyRequest request) {
        String context = JSONObject.toJSONString(request);
        ModifyEsGoodsBoostRequest modifyEsGoodsBoostRequest = new ModifyEsGoodsBoostRequest();
        modifyEsGoodsBoostRequest.setContext(context);
        systemConfigSaveProvider.modifyEsGoodsBoost(modifyEsGoodsBoostRequest);
        return BaseResponse.SUCCESSFUL();
    }

    /**
     * 关闭商家商品二次审核开关
     *
     * @return BaseResponse
     */
    @Operation(summary = "关闭商家商品二次审核开关")
    @RequestMapping(value = "/supplier/goods/secondary/audit/close", method = RequestMethod.POST)
    public BaseResponse closeSupplierGoodsSecondaryAudit() {
        ConfigStatusModifyByTypeAndKeyRequest request = new ConfigStatusModifyByTypeAndKeyRequest();
        request.setConfigKey(ConfigKey.S2BAUDIT);
        request.setConfigType(ConfigType.SUPPLIER_GOODS_SECONDARY_AUDIT);
        request.setStatus(0);

        auditProvider.modifyStatusByTypeAndKey(request);

        operateLogMQUtil.convertAndSend("设置", "修改审核开关", "修改审核开关：商家商品二次审核设为关");
        return BaseResponse.SUCCESSFUL();
    }

    /**
     * 开启商家商品二次审核开关
     *
     * @return BaseResponse
     */
    @Operation(summary = "开启商家商品二次审核开关")
    @RequestMapping(value = "/supplier/goods/secondary/audit/open", method = RequestMethod.POST)
    public BaseResponse openSupplierGoodsSecondaryAudit() {
        ConfigStatusModifyByTypeAndKeyRequest request = new ConfigStatusModifyByTypeAndKeyRequest();
        request.setConfigKey(ConfigKey.S2BAUDIT);
        request.setConfigType(ConfigType.SUPPLIER_GOODS_SECONDARY_AUDIT);
        request.setStatus(1);

        auditProvider.modifyStatusByTypeAndKey(request);

        operateLogMQUtil.convertAndSend("设置", "修改审核开关", "修改审核开关：商家商品二次审核设为开");
        return BaseResponse.SUCCESSFUL();
    }

    /**
     * 关闭供应商商品二次审核开关
     *
     * @return BaseResponse
     */
    @Operation(summary = "关闭供应商商品二次审核开关")
    @RequestMapping(value = "/provider/goods/secondary/audit/close", method = RequestMethod.POST)
    public BaseResponse closeProviderGoodsSecondaryAudit() {
        ConfigStatusModifyByTypeAndKeyRequest request = new ConfigStatusModifyByTypeAndKeyRequest();
        request.setConfigKey(ConfigKey.S2BAUDIT);
        request.setConfigType(ConfigType.PROVIDER_GOODS_SECONDARY_AUDIT);
        request.setStatus(0);

        auditProvider.modifyStatusByTypeAndKey(request);

        operateLogMQUtil.convertAndSend("设置", "修改审核开关", "修改审核开关：供应商商品二次审核设为关");
        return BaseResponse.SUCCESSFUL();
    }

    /**
     * 开启供应商商品二次审核开关
     *
     * @return BaseResponse
     */
    @Operation(summary = "开启供应商商品二次审核开关")
    @RequestMapping(value = "/provider/goods/secondary/audit/open", method = RequestMethod.POST)
    public BaseResponse openProviderGoodsSecondaryAudit() {
        ConfigStatusModifyByTypeAndKeyRequest request = new ConfigStatusModifyByTypeAndKeyRequest();
        request.setConfigKey(ConfigKey.S2BAUDIT);
        request.setConfigType(ConfigType.PROVIDER_GOODS_SECONDARY_AUDIT);
        request.setStatus(1);

        auditProvider.modifyStatusByTypeAndKey(request);

        operateLogMQUtil.convertAndSend("设置", "修改审核开关", "修改审核开关：供应商商品二次审核设为开");
        return BaseResponse.SUCCESSFUL();
    }

    /**
     * 关闭商家签约信息二次审核开关
     *
     * @return BaseResponse
     */
    @Operation(summary = "关闭商家签约信息二次审核开关")
    @RequestMapping(value = "/supplier/sign/secondary/audit/close", method = RequestMethod.POST)
    public BaseResponse closeSupplierSignSecondaryAudit() {
        ConfigStatusModifyByTypeAndKeyRequest request = new ConfigStatusModifyByTypeAndKeyRequest();
        request.setConfigKey(ConfigKey.S2BAUDIT);
        request.setConfigType(ConfigType.SUPPLIER_SIGN_SECONDARY_AUDIT);
        request.setStatus(0);

        auditProvider.modifyStatusByTypeAndKey(request);

        operateLogMQUtil.convertAndSend("设置", "修改审核开关", "修改审核开关：商家签约信息二次审核设为关");
        return BaseResponse.SUCCESSFUL();
    }

    /**
     * 开启商家签约信息二次审核开关
     *
     * @return BaseResponse
     */
    @Operation(summary = "开启商家签约信息二次审核开关")
    @RequestMapping(value = "/supplier/sign/secondary/audit/open", method = RequestMethod.POST)
    public BaseResponse openSupplierSignSecondaryAudit() {
        ConfigStatusModifyByTypeAndKeyRequest request = new ConfigStatusModifyByTypeAndKeyRequest();
        request.setConfigKey(ConfigKey.S2BAUDIT);
        request.setConfigType(ConfigType.SUPPLIER_SIGN_SECONDARY_AUDIT);
        request.setStatus(1);

        auditProvider.modifyStatusByTypeAndKey(request);

        operateLogMQUtil.convertAndSend("设置", "修改审核开关", "修改审核开关：商家签约信息二次审核设为开");
        return BaseResponse.SUCCESSFUL();
    }

    /**
     * 关闭供应商签约信息二次审核开关
     *
     * @return BaseResponse
     */
    @Operation(summary = "关闭供应商签约信息二次审核开关")
    @RequestMapping(value = "/provider/sign/secondary/audit/close", method = RequestMethod.POST)
    public BaseResponse closeProviderSignSecondaryAudit() {
        ConfigStatusModifyByTypeAndKeyRequest request = new ConfigStatusModifyByTypeAndKeyRequest();
        request.setConfigKey(ConfigKey.S2BAUDIT);
        request.setConfigType(ConfigType.PROVIDER_SIGN_SECONDARY_AUDIT);
        request.setStatus(0);

        auditProvider.modifyStatusByTypeAndKey(request);

        operateLogMQUtil.convertAndSend("设置", "修改审核开关", "修改审核开关：供应商签约信息二次审核设为关");
        return BaseResponse.SUCCESSFUL();
    }

    /**
     * 开启供应商签约信息二次审核开关
     *
     * @return BaseResponse
     */
    @Operation(summary = "开启供应商签约信息二次审核开关")
    @RequestMapping(value = "/provider/sign/secondary/audit/open", method = RequestMethod.POST)
    public BaseResponse openProviderSignSecondaryAudit() {
        ConfigStatusModifyByTypeAndKeyRequest request = new ConfigStatusModifyByTypeAndKeyRequest();
        request.setConfigKey(ConfigKey.S2BAUDIT);
        request.setConfigType(ConfigType.PROVIDER_SIGN_SECONDARY_AUDIT);
        request.setStatus(1);

        auditProvider.modifyStatusByTypeAndKey(request);

        operateLogMQUtil.convertAndSend("设置", "修改审核开关", "修改审核开关：供应商签约信息二次审核设为开");
        return BaseResponse.SUCCESSFUL();
    }

    @Operation(summary = "活动互斥编辑")
    @Parameter(name = "status", description = "状态", required = true)
    @PutMapping("/marketing/mutex/{status}")
    public BaseResponse setMarketingMutex(@PathVariable Integer status) {
        if (status != Constants.ZERO && status != Constants.ONE){
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        ConfigStatusModifyByTypeAndKeyRequest request = new ConfigStatusModifyByTypeAndKeyRequest();
        request.setConfigKey(ConfigKey.MARKETING_MUTEX);
        request.setConfigType(ConfigType.MARKETING_MUTEX);
        request.setStatus(status);
        auditProvider.modifyStatusByTypeAndKey(request);
        operateLogMQUtil.convertAndSend("设置", "修改活动互斥设置", "修改活动互斥设置：状态设为" + (status == 1 ? "开启" : "关闭"));
        return BaseResponse.SUCCESSFUL();
    }

    @Operation(summary = "查询SEO设置")
    @RequestMapping(value = "/seoSetting", method = RequestMethod.GET)
    public BaseResponse<SeoSettingVO> getSeoSetting() {
        ConfigQueryRequest request = new ConfigQueryRequest();
        request.setConfigType(ConfigType.SEO_SETTING.toValue());
        SystemConfigTypeResponse response = systemConfigQueryProvider.findByConfigTypeAndDelFlag(request).getContext();
        if (Objects.nonNull(response.getConfig())) {
            return BaseResponse.success(JSONObject.parseObject(response.getConfig().getContext(), SeoSettingVO.class));
        }
        return BaseResponse.success(new SeoSettingVO());
    }

    @Operation(summary = "修改SEO设置")
    @RequestMapping(value = "/modifySeoSetting", method = RequestMethod.PUT)
    public BaseResponse modifySeoSetting(@Valid @RequestBody SeoSettingModifyRequest request) {
        return systemConfigSaveProvider.modifySeoSetting(request);
    }

    /**
     * 开启礼品卡制卡审核开关
     *
     * @return BaseResponse
     */
    @Operation(summary = "开启礼品卡制卡审核开关")
    @RequestMapping(value = "/giftCard/makeCard/audit/open", method = RequestMethod.POST)
    public BaseResponse openGiftCardMakeCardAudit() {
        ConfigStatusModifyByTypeAndKeyRequest request = new ConfigStatusModifyByTypeAndKeyRequest();
        request.setConfigKey(ConfigKey.S2BAUDIT);
        request.setConfigType(ConfigType.GIFT_CARD_MAKE_CARD_AUDIT);
        request.setStatus(1);
        auditProvider.modifyStatusByTypeAndKey(request);
        operateLogMQUtil.convertAndSend("设置", "修改审核开关", "修改审核开关：开启礼品卡制卡审核开关");
        return BaseResponse.SUCCESSFUL();
    }

    /**
     * 关闭礼品卡制卡审核开关
     *
     * @return BaseResponse
     */
    @Operation(summary = "关闭礼品卡制卡审核开关")
    @RequestMapping(value = "/giftCard/makeCard/audit/close", method = RequestMethod.POST)
    public BaseResponse closeGiftCardMakeCardAudit() {
        ConfigStatusModifyByTypeAndKeyRequest request = new ConfigStatusModifyByTypeAndKeyRequest();
        request.setConfigKey(ConfigKey.S2BAUDIT);
        request.setConfigType(ConfigType.GIFT_CARD_MAKE_CARD_AUDIT);
        request.setStatus(0);

        auditProvider.modifyStatusByTypeAndKey(request);

        operateLogMQUtil.convertAndSend("设置", "修改审核开关", "修改审核开关：关闭礼品卡制卡审核开关");
        return BaseResponse.SUCCESSFUL();
    }

    /**
     * 开启礼品卡发卡审核开关
     *
     * @return BaseResponse
     */
    @Operation(summary = "开启礼品卡发卡审核开关")
    @RequestMapping(value = "/giftCard/sendCard/audit/open", method = RequestMethod.POST)
    public BaseResponse openGiftCardSendCardAudit() {
        ConfigStatusModifyByTypeAndKeyRequest request = new ConfigStatusModifyByTypeAndKeyRequest();
        request.setConfigKey(ConfigKey.S2BAUDIT);
        request.setConfigType(ConfigType.GIFT_CARD_SEND_CARD_AUDIT);
        request.setStatus(1);

        auditProvider.modifyStatusByTypeAndKey(request);

        operateLogMQUtil.convertAndSend("设置", "修改审核开关", "修改审核开关：开启礼品卡发卡审核开关");
        return BaseResponse.SUCCESSFUL();
    }

    /**
     * 关闭礼品卡发卡审核开关
     *
     * @return BaseResponse
     */
    @Operation(summary = "关闭礼品卡发卡审核开关")
    @RequestMapping(value = "/giftCard/sendCard/audit/close", method = RequestMethod.POST)
    public BaseResponse closeGiftCardSendCardAudit() {
        ConfigStatusModifyByTypeAndKeyRequest request = new ConfigStatusModifyByTypeAndKeyRequest();
        request.setConfigKey(ConfigKey.S2BAUDIT);
        request.setConfigType(ConfigType.GIFT_CARD_SEND_CARD_AUDIT);
        request.setStatus(0);
        auditProvider.modifyStatusByTypeAndKey(request);
        operateLogMQUtil.convertAndSend("设置", "修改审核开关", "修改审核开关：关闭礼品卡发卡审核开关");
        return BaseResponse.SUCCESSFUL();
    }
}
