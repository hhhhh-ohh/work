package com.wanmi.sbc.util;

import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.TypeReference;
import com.wanmi.sbc.common.base.Operator;
import com.wanmi.sbc.common.base.VASEntity;
import com.wanmi.sbc.common.constant.VASStatus;
import com.wanmi.sbc.common.enums.*;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.redis.CacheKeyConstant;
import com.wanmi.sbc.common.redis.util.RedisUtil;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.common.util.DateUtil;
import com.wanmi.sbc.common.util.HttpUtil;
import com.wanmi.sbc.common.util.MD5Util;
import com.wanmi.sbc.common.util.Nutils;
import com.wanmi.sbc.configuration.jwt.JwtProperties;
import com.wanmi.sbc.setting.bean.enums.ConfigKey;
import com.wanmi.sbc.vas.api.provider.iep.iepsetting.IepSettingQueryProvider;
import com.wanmi.sbc.vas.bean.enums.VasErrorCodeEnum;
import com.wanmi.sbc.vas.bean.vo.IepSettingVO;

import io.jsonwebtoken.Claims;

import jakarta.servlet.http.HttpServletRequest;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * BFF公共工具类
 * Created by daiyitian on 15/12/29.
 */
@Slf4j
@Component
public final class CommonUtil {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private RedisUtil redisService;

    @Autowired
    private IepSettingQueryProvider iepSettingQueryProvider;


    @Autowired
    private JwtProperties jwtProperties;
    /**
     * 获取当前登录编号
     *
     * @return
     */
    public String getOperatorId() {
        return getOperator().getUserId();
    }

    /**
     * 获取当前登录的公司信息ID
     *
     * @return
     */
    public Long getCompanyInfoId() {
        String companyInfoId = this.getOperator().getAdminId();
        if (StringUtils.isNotBlank(companyInfoId)) {
            return Long.valueOf(companyInfoId);
        }
        return null;
    }

    /**
     * 获取当前登录的店铺信息ID
     *
     * @return
     */
    public Long getStoreId() {
        String storeId = this.getOperator().getStoreId();
        if (StringUtils.isNotBlank(storeId)) {
            return Long.valueOf(storeId);
        }
        return null;
    }

    /**
     * 获取当前登录的店铺信息ID
     * 若没有设置默认值为boss对应的店铺ID
     *
     * @return
     */
    public Long getStoreIdWithDefault() {
        String storeId = this.getOperator().getStoreId();
        if (StringUtils.isNotBlank(storeId)) {
            return Long.valueOf(storeId);
        }
        return Constants.BOSS_DEFAULT_STORE_ID;
    }


    /**
     * 获取当前登录的商家类型
     *
     * @return
     */
    public BoolFlag getCompanyType() {
        return this.getOperator().getCompanyType();
    }


    /**
     * 获取当前登录的商家客户等级类别
     * 自营 平台等级
     * 店铺 店铺等级
     *
     * @return
     */
    public DefaultFlag getCustomerLevelType() {
        return this.getOperator().getCompanyType().equals(BoolFlag.YES) ? DefaultFlag.NO : DefaultFlag.YES;
    }

    /**
     * 获取当前登录人昵称
     *
     * @return
     */
    public String getAccountName() {
        return this.getOperator().getName();
    }

    /**
     * 获取当前登录账号
     *
     * @return
     */
    public String getAccount() {
        return this.getOperator().getAccount();
    }

    /**
     * 获取过期时间
     */
    public LocalDateTime getExpireTime() {
        Claims claims = (Claims) HttpUtil.getRequest().getAttribute("claims");
        if (claims == null) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000014);
        }
        Date expiration = claims.getExpiration();
        return DateUtil.dateToLocalDateTime(expiration);
    }

    /**
     * 获取当前登录对象
     *
     * @return
     */
    public Operator getOperator() {
        Claims claims = (Claims) HttpUtil.getRequest().getAttribute("claims");
        if (claims == null) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000014);
        }

        Object vasObject = claims.get(ConfigKey.VALUE_ADDED_SERVICES.toString());
        List<VASEntity> services = new ArrayList<>();
        if (Objects.nonNull(vasObject)) {
            String vasJson = vasObject.toString();
            Map<String, String> map = JSONObject.parseObject(vasJson, new TypeReference<Map<String, String>>(){});
            services = map.entrySet().stream().map(m -> {
                VASEntity vasEntity = new VASEntity();
                vasEntity.setServiceName(VASConstants.fromValue(m.getKey()));
                vasEntity.setServiceStatus(StringUtils.equals(VASStatus.ENABLE.toValue(), m.getValue()));
                return vasEntity;
            }).collect(Collectors.toList());
        }
        String storeId = Nutils.nonNullActionRt(claims.get("storeId"), Object::toString, StringUtils.EMPTY);
        storeId = StringUtils.isBlank(storeId)?null:storeId;
        StoreType storeType = Objects.nonNull(claims.get("storeType")) ?
                StoreType.fromValue(Integer.parseInt(Nutils.nonNullActionRt(claims.get("storeType"), Object::toString, StringUtils.EMPTY))) : null;
        Long versionNo = Objects.nonNull(claims.get("versionNo")) ?
                NumberUtils.toLong(claims.get("versionNo").toString()) : null;
        return Operator.builder()
                .platform(Platform.forValue(Nutils.nonNullActionRt(claims.get("platform"), Object::toString, StringUtils.EMPTY)))
                .adminId(Nutils.nonNullActionRt(claims.get("adminId"), Object::toString, StringUtils.EMPTY))
                .ip(Nutils.nonNullActionRt(claims.get("ip"), Object::toString, StringUtils.EMPTY))
                .account(Nutils.nonNullActionRt(claims.get("EmployeeName"), Object::toString, StringUtils.EMPTY))
                .name(Nutils.nonNullActionRt(claims.get("realEmployeeName"), Object::toString, StringUtils.EMPTY))
                .userId(Nutils.nonNullActionRt(claims.get("employeeId"), Object::toString, StringUtils.EMPTY))
                .versionNo(versionNo)
                .storeId(storeId)
                .companyType(BoolFlag.fromValue(Nutils.nonNullActionRt(claims.get("companyType"), Object::toString, StringUtils.EMPTY)))
                .companyInfoId(Long.valueOf(Objects.toString(claims.get("companyInfoId"), "0")))
                .services(services)
                .storeType(storeType)
                .build();
    }

    /**
     * 查询指定增值服务是否购买
     *
     * @param constants
     * @return
     */
    public boolean findVASBuyOrNot(VASConstants constants) {
        boolean flag = false;
        VASEntity vasEntity = this.getVasService(constants);
        if (Objects.nonNull(vasEntity)) {
            flag = vasEntity.isServiceStatus();
        }
        return flag;
    }

    /**
     * 获取所有增值服务
     *
     * @return
     */
    public List<VASEntity> getAllServices() {
        if (RequestContextHolder.getRequestAttributes() == null || HttpUtil.getRequest() == null) {
            Map<String, String> vasList = redisService.hgetall(ConfigKey.VALUE_ADDED_SERVICES.toString());
            return vasList.entrySet().stream().map(m -> {
                VASEntity vasEntity = new VASEntity();
                vasEntity.setServiceName(VASConstants.fromValue(m.getKey()));
                vasEntity.setServiceStatus(StringUtils.equals(VASStatus.ENABLE.toValue(), m.getValue()));
                return vasEntity;
            }).collect(Collectors.toList());
        } else {
            return this.getOperator().getServices();
        }
    }

    /**
     * 获取指定增值服务
     *
     * @return
     */
    public VASEntity getVasService(VASConstants vasConstants) {
        if (RequestContextHolder.getRequestAttributes() == null || HttpUtil.getRequest() == null) {
            String vas = redisService.hget(ConfigKey.VALUE_ADDED_SERVICES.toString(),vasConstants.toValue());
            VASEntity vasEntity = new VASEntity();
            vasEntity.setServiceName(VASConstants.fromValue(vasConstants.toValue()));
            vasEntity.setServiceStatus(StringUtils.equals(VASStatus.ENABLE.toValue(), vas));
            return vasEntity;
        } else {
            return this.getOperator().getServices().stream().
                    filter(f -> StringUtils.equals(f.getServiceName().toValue(), vasConstants.toValue())).findFirst().orElse(null);
        }
    }

    /**
     * @description 是否购买任意第三方渠道
     * @author  songhanlin
     * @date: 2021/5/21 上午11:17
     * @return
     **/
    public boolean buyAnyThirdChannelOrNot() {
        return this.findVASBuyOrNot(VASConstants.THIRD_PLATFORM_LINKED_MALL) ||
                this.findVASBuyOrNot(VASConstants.THIRD_PLATFORM_VOP);
    }

    /**
     * 获取当前登录对象,可为空
     *
     * @return
     */
    public Operator getOperatorWithNull() {
        Claims claims = (Claims) HttpUtil.getRequest().getAttribute("claims");
        if (claims == null) {
            return null;
        }

        return Operator.builder()
                .platform(Platform.forValue(Nutils.nonNullActionRt(claims.get("platform"), Object::toString, StringUtils.EMPTY)))
                .adminId(Nutils.nonNullActionRt(claims.get("adminId"), Object::toString, StringUtils.EMPTY))
                .ip(Nutils.nonNullActionRt(claims.get("ip"), Object::toString, StringUtils.EMPTY))
                .account(Nutils.nonNullActionRt(claims.get("EmployeeName"), Object::toString, StringUtils.EMPTY))
                .name(Nutils.nonNullActionRt(claims.get("EmployeeName"), Object::toString, StringUtils.EMPTY))
                .userId(Nutils.nonNullActionRt(claims.get("employeeId"), Object::toString, StringUtils.EMPTY))
                .storeId(Nutils.nonNullActionRt(claims.get("storeId"), Object::toString, StringUtils.EMPTY))
                .build();
    }

    /**
     * cookie中获取token，以获得用户信息
     *
     * @return
     */
    public Operator getOperatorFromCookie() {
        String token = "";
        if (StringUtils.isEmpty(token)) {
            return null;
        }
        Claims claims = jwtUtil.validate(token);
        if (claims == null) {
            return null;
        }
        return Operator.builder()
                .platform(Platform.forValue(Nutils.nonNullActionRt(claims.get("platform"), Object::toString, StringUtils.EMPTY)))
                .adminId(Nutils.nonNullActionRt(claims.get("adminId"), Object::toString, StringUtils.EMPTY))
                .ip(Nutils.nonNullActionRt(claims.get("ip"), Object::toString, StringUtils.EMPTY))
                .account(Nutils.nonNullActionRt(claims.get("EmployeeName"), Object::toString, StringUtils.EMPTY))
                .name(Nutils.nonNullActionRt(claims.get("EmployeeName"), Object::toString, StringUtils.EMPTY))
                .userId(Nutils.nonNullActionRt(claims.get("employeeId"), Object::toString, StringUtils.EMPTY))
                .storeId(Nutils.nonNullActionRt(claims.get("storeId"), Object::toString, StringUtils.EMPTY))
                .build();
    }

    /**
     * 获取企业购配置信息
     *
     * @return
     */
    public IepSettingVO getIepSettingInfo() {
        if (!this.findVASBuyOrNot(VASConstants.VAS_IEP_SETTING)) {
            throw new SbcRuntimeException(VasErrorCodeEnum.K120001);
        }
        String val = redisService.getString(CacheKeyConstant.IEP_SETTING);
        if (StringUtils.isNotBlank(val)) {
            IepSettingVO iepSettingVO = JSONObject.parseObject(val,
                    IepSettingVO.class);
            return iepSettingVO;
        } else {
            return iepSettingQueryProvider.cacheIepSetting().getContext().getIepSettingVO();
        }
    }

    /**
     * 从cookie中获取店铺Id
     *
     * @return
     */
    public Long getStoreIdFromCookieWithNotLoginDefault() {
        //先从cookie取
        Operator operator = this.getOperator();
        if (Objects.nonNull(operator)) {
            String storeId = operator.getStoreId();
            if (StringUtils.isNotBlank(storeId)) {
                return Long.valueOf(storeId);
            }
        }
        return Constants.BOSS_DEFAULT_STORE_ID;
    }

    /**
     * 获取jwtToken
     *
     * @return
     */
    public String getToken(HttpServletRequest request) {
        String jwtHeaderKey = org.apache.commons.lang3.StringUtils.isNotBlank(jwtProperties.getJwtHeaderKey()) ?
                jwtProperties.getJwtHeaderKey() : "Authorization";
        String jwtHeaderPrefix = org.apache.commons.lang3.StringUtils.isNotBlank(jwtProperties.getJwtHeaderPrefix()) ?
                jwtProperties.getJwtHeaderPrefix() : "Bearer ";
        String authHeader = request.getHeader(jwtHeaderKey);
        //当token失效,直接返回失败
        if (authHeader != null && authHeader.length() > Constants.NUM_16) {
            return authHeader.substring(jwtHeaderPrefix.length());
        }
        return null;
    }

    /**
     * 获取当前登录的店铺类型
     *
     * @return
     */
    public StoreType getStoreType() {
        return this.getOperator().getStoreType();
    }

    /**
     * 获取TokenLoginKey
     * @param token 登陆凭证
     */
    private String getLoginTokenKey(String token) {
        String tmpToken = MD5Util.md5Hex(token, StandardCharsets.UTF_8.name()).substring(8, 24);
        return CacheKeyConstant.JSON_WEB_TOKEN.concat(tmpToken);
    }

    /**
     * 设定登录凭证
     * @param token 登陆凭证
     */
    public void putLoginRedis(String token){
        //7天后过期
//        redisService.setString(getLoginTokenKey(token),token,60L*60*24*7);
        //12小时后过期
        redisService.setString(getLoginTokenKey(token),token,60L*60*12);
    }

    /**
     * 设定登录凭证
     * @param token 登陆凭证
     */
    public void deleteLoginRedis(String token){
        redisService.delete(getLoginTokenKey(token));
    }

    /**
     * 注册验证
     * @param errKey
     * @param smsKey
     * @param verifyCode
     */
    public void verifyCodeMsg(String errKey, String smsKey, String verifyCode) {
        //验证码错误5次即失效
        if (NumberUtils.toInt(redisService.getString(errKey)) >= Constants.FIVE) {
            redisService.delete(smsKey);
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000016);
        }
        //验证验证码
        String t_verifyCode = redisService.getString(smsKey);
        if (StringUtils.isBlank(t_verifyCode) || (!t_verifyCode.equalsIgnoreCase(verifyCode))) {
            redisService.incrKey(errKey);
            redisService.expireBySeconds(errKey, 60L);
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000010);
        }
    }

    /**
     * 越权校验
     * @param companyInfoId
     */
    public void checkCompanyInfoId(Long companyInfoId) {
        if (!Objects.equals(Platform.PLATFORM,getOperator().getPlatform())){
            if (!Objects.equals(getCompanyInfoId(), companyInfoId)){
                throw new SbcRuntimeException(CommonErrorCodeEnum.K000014);
            }
        }
    }

    /**
     * 越权校验
     * @param storeId
     */
    public void checkStoreId(Long storeId) {
        if (!Objects.equals(Platform.PLATFORM,getOperator().getPlatform())){
            if (!Objects.equals(getStoreId(), storeId)){
                throw new SbcRuntimeException(CommonErrorCodeEnum.K000014);
            }
        }
    }
}
