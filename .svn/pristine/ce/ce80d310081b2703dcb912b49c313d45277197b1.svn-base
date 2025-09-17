package com.wanmi.sbc.system;

import com.alibaba.fastjson2.JSONObject;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.empower.api.provider.miniprogramset.MiniProgramSetProvider;
import com.wanmi.sbc.empower.api.provider.miniprogramset.MiniProgramSetQueryProvider;
import com.wanmi.sbc.empower.api.provider.wechatauth.WechatAuthProvider;
import com.wanmi.sbc.empower.api.request.miniprogramset.MiniProgramSetByTypeRequest;
import com.wanmi.sbc.empower.api.request.miniprogramset.MiniProgramSetModifyRequest;
import com.wanmi.sbc.empower.api.request.wechatauth.MiniProgramQrCodeRequest;
import com.wanmi.sbc.empower.api.response.miniprogramset.MiniProgramSetByTypeResponse;
import com.wanmi.sbc.empower.bean.vo.MiniProgramSetVO;
import com.wanmi.sbc.setting.api.provider.AuditQueryProvider;
import com.wanmi.sbc.setting.api.provider.baseconfig.BaseConfigQueryProvider;
import com.wanmi.sbc.setting.api.provider.baseconfig.BaseConfigSaveProvider;
import com.wanmi.sbc.setting.api.request.MiniProgramSetRequest;
import com.wanmi.sbc.setting.api.request.baseconfig.BaseConfigAddRequest;
import com.wanmi.sbc.setting.api.request.baseconfig.BaseConfigModifyRequest;
import com.wanmi.sbc.setting.api.request.baseconfig.BaseConfigSaveRopRequest;
import com.wanmi.sbc.setting.api.request.baseconfig.BaseConfigUpdateRequest;
import com.wanmi.sbc.setting.api.response.MiniProgramSetGetResponse;
import com.wanmi.sbc.setting.api.response.SupplierOrderAuditResponse;
import com.wanmi.sbc.setting.api.response.baseconfig.BaseConfigModifyResponse;
import com.wanmi.sbc.setting.api.response.baseconfig.BaseConfigRopResponse;
import com.wanmi.sbc.setting.api.response.baseconfig.BossLogoResponse;
import com.wanmi.sbc.setting.bean.vo.BaseConfigVO;
import com.wanmi.sbc.util.CommonUtil;
import com.wanmi.sbc.util.OperateLogMQUtil;

import io.jsonwebtoken.Claims;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

/**
 * 基本设置服务
 * Created by CHENLI on 2017/5/12.
 */
@Tag(name = "BaseConfigController", description = "基本设置服务 Api")
@RestController
@Validated
public class BaseConfigController {
    @Autowired
    private OperateLogMQUtil operateLogMQUtil;

    @Autowired
    private WechatAuthProvider wechatAuthProvider;

    @Autowired
    private AuditQueryProvider auditQueryProvider;

    @Autowired
    private BaseConfigQueryProvider baseConfigQueryProvider;

    @Autowired
    private BaseConfigSaveProvider baseConfigSaveProvider;

    @Autowired
    private MiniProgramSetQueryProvider miniProgramSetQueryProvider;

    @Autowired
    private MiniProgramSetProvider miniProgramSetProvider;

    @Value("${jwt.secret-key}")
    private String jwtSecretKey;

    @Value("${cookie.secure}")
    private boolean secure;

    @Value("${cookie.maxAge}")
    private Integer maxAge;

    @Value("${cookie.path}")
    private String path;

    @Value("${cookie.domain}")
    private String domain;

    @Autowired
    private CommonUtil commonUtil;

    /**
     * 查询基本设置
     *
     * @return
     */
    @Operation(summary = "查询基本设置")
    @RequestMapping(value = "/baseConfig", method = RequestMethod.GET)
    public BaseResponse<BaseConfigRopResponse> findBaseConfig() {
        return baseConfigQueryProvider.getBaseConfig();
    }

    /**
     * 查询cookie
     *
     * @return
     */
    @Operation(summary = "查询基本设置")
    @RequestMapping(value = "/setCookie", method = RequestMethod.GET)
    public BaseResponse setCookie(HttpServletRequest request, HttpServletResponse response) {
        Claims claims = (Claims) request.getAttribute("claims");
        String token = commonUtil.getToken(request);

        Object storeId = claims.get("storeId");
        if(Objects.isNull(storeId)){
            storeId = -1;
        }

        System.out.println("storeId:"+storeId);
        System.out.println("token:"+token);

        Cookie cookie = new Cookie(storeId.toString(), token);
        cookie.setSecure(secure);
        cookie.setMaxAge(maxAge);
        cookie.setPath(path);
        if(org.apache.commons.lang.StringUtils.isNotEmpty(domain)){
            cookie.setDomain(domain);
        }
        response.addCookie(cookie);

        return BaseResponse.SUCCESSFUL();
    }

    @Operation(summary = "查询平台Logo")
    @RequestMapping(value = "/bosslogo", method = RequestMethod.GET)
    public BaseResponse<String> queryBossLogo() {
        BossLogoResponse response = baseConfigQueryProvider.queryBossLogo().getContext();
        return BaseResponse.success(response.getPcLogo());
    }

    /**
     * 保存基本设置
     *
     * @param saveRopRequest
     * @return
     */
    @Operation(summary = "保存基本设置")
    @RequestMapping(value = "/baseConfig", method = RequestMethod.POST)
    public BaseResponse<BaseConfigRopResponse> saveBaseConfig(@Valid @RequestBody BaseConfigSaveRopRequest saveRopRequest) {
        BaseConfigAddRequest addRequest = new BaseConfigAddRequest();
        KsBeanUtil.copyPropertiesThird(saveRopRequest, addRequest);
        BaseConfigVO baseConfigVO = baseConfigSaveProvider.add(addRequest).getContext().getBaseConfigVO();
        operateLogMQUtil.convertAndSend("设置", "新增基本设置", "新增基本设置");
        return BaseResponse.success(KsBeanUtil.convert(baseConfigVO, BaseConfigRopResponse.class));
    }

    /**
     * 修改基本设置
     *
     * @param
     * @return
     */
    @Operation(summary = "修改基本设置")
    @RequestMapping(value = "/baseConfig", method = RequestMethod.PUT)
    public BaseResponse<BaseConfigRopResponse> updateBaseConfig(@RequestBody @Valid BaseConfigSaveRopRequest updateRopRequest) {
        if (
                StringUtils.isEmpty(updateRopRequest.getBaseConfigId()) ||
                StringUtils.isEmpty(updateRopRequest.getPcWebsite()) ||
                StringUtils.isEmpty(updateRopRequest.getMobileWebsite()))
        {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        BaseConfigModifyRequest modifyRequest = new BaseConfigModifyRequest();
        KsBeanUtil.copyPropertiesThird(updateRopRequest, modifyRequest);
        BaseConfigModifyResponse response = baseConfigSaveProvider.modify(modifyRequest).getContext();
        operateLogMQUtil.convertAndSend("设置", "编辑基本设置", "编辑基本设置");
        return BaseResponse.success(KsBeanUtil.convert(response.getBaseConfigVO(), BaseConfigRopResponse.class));
    }

    /**
     * 获取平台boss的小程序码（与PC商城展示的一样）
     *
     * @return
     */
    @Operation(summary = "获取平台boss的小程序码（与PC商城展示的一样）")
    @RequestMapping(value = "/getS2bBossQrcode", method = RequestMethod.POST)
    public BaseResponse<String> getS2bBossQrcode() {
        MiniProgramQrCodeRequest request = new MiniProgramQrCodeRequest();
        //参数先这样，待发布后再修改，否则传入未经发布的页面，会报错。
        request.setPage("pages/index/index");
        request.setScene("123");
        return wechatAuthProvider.getWxaCodeUnlimit(request);
    }

    /**
     * 获取商家boss的二维码（扫码进入以后显示的是店铺首页）
     *
     * @return
     */
    @Operation(summary = "获取商家boss的二维码（扫码进入以后显示的是店铺首页）")
    @RequestMapping(value = "/getS2bSupplierQrcode/{storeId}", method = RequestMethod.POST)
    public BaseResponse<String> getS2bSupplierQrcode(@PathVariable String storeId) {
        MiniProgramQrCodeRequest request = new MiniProgramQrCodeRequest();
        request.setPage("pages/sharepage/sharepage");
        request.setScene("/store-main/" + storeId);
        return wechatAuthProvider.getWxaCodeUnlimit(request);
    }

    /**
     * 获取小程序配置
     *
     * @return
     */
    @Operation(summary = "获取小程序配置")
    @RequestMapping(value = "/getMiniProgramSet", method = RequestMethod.GET)
    public BaseResponse<MiniProgramSetGetResponse> getMiniProgramSet() {
        MiniProgramSetGetResponse miniProgramSetGetResponse = new MiniProgramSetGetResponse();
        // 小程序
        BaseResponse<MiniProgramSetByTypeResponse> miniProgramSetByTypeResponseBaseResponse = miniProgramSetQueryProvider.getByType(MiniProgramSetByTypeRequest.builder()
                .type(Constants.ZERO)
                .build());
        if (CommonErrorCodeEnum.K000000.getCode().equals(miniProgramSetByTypeResponseBaseResponse.getCode())) {
            MiniProgramSetVO miniProgramSetVO = miniProgramSetByTypeResponseBaseResponse.getContext().getMiniProgramSetVO();
            miniProgramSetGetResponse.setStatus(miniProgramSetVO.getStatus());
            miniProgramSetGetResponse.setRemark(miniProgramSetVO.getRemark());
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("appId",miniProgramSetVO.getAppId());
            jsonObject.put("appSecret",miniProgramSetVO.getAppSecret());
            miniProgramSetGetResponse.setContext(jsonObject.toJSONString());
        }
        return BaseResponse.success(miniProgramSetGetResponse);
    }

    /**
     * 修改
     *
     * @param request
     * @return
     */
    @Operation(summary = "修改小程序配置")
    @RequestMapping(value = "/updateMiniProgramSet", method = RequestMethod.PUT)
    public BaseResponse updateMiniProgramSet(@RequestBody MiniProgramSetRequest request) {
        return miniProgramSetProvider.modify(MiniProgramSetModifyRequest.builder()
                .appId(request.getAppId())
                .appSecret(request.getAppSecret())
                .status(request.getStatus())
                .type(0)//小程序类型 0 微信小程序
                .build());
    }

    /**
     * 获取订单是否需要审核
     *
     * @return
     */
    @Operation(summary = "获取订单是否需要审核")
    @RequestMapping(value = "/getSupplierOrderAudit", method = RequestMethod.GET)
    public BaseResponse<SupplierOrderAuditResponse> getSupplierOrderAudit() {
        return auditQueryProvider.isSupplierOrderAudit();
    }

    /**
     * 修改基本设置
     *
     * @param
     * @return
     */
    @Operation(summary = "修改注册注销协议设置")
    @RequestMapping(value = "/baseConfig/modify", method = RequestMethod.POST)
    public BaseResponse modifyBaseConfig(@RequestBody @Valid BaseConfigUpdateRequest request) {
        BaseResponse baseResponse = baseConfigSaveProvider.modifyAgreement(request);
        operateLogMQUtil.convertAndSend("设置", "编辑注册注销协议设置", "编辑注册注销协议设置");
        return baseResponse;
    }
}
