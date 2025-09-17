package com.wanmi.sbc.util;

import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.TypeReference;
import com.wanmi.sbc.common.base.DistributeChannel;
import com.wanmi.sbc.common.base.Operator;
import com.wanmi.sbc.common.base.VASEntity;
import com.wanmi.sbc.common.constant.RedisKeyConstant;
import com.wanmi.sbc.common.constant.VASStatus;
import com.wanmi.sbc.common.enums.*;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.redis.CacheKeyConstant;
import com.wanmi.sbc.common.redis.util.RedisUtil;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.common.util.HttpUtil;
import com.wanmi.sbc.common.util.MD5Util;
import com.wanmi.sbc.configuration.jwt.JwtProperties;
import com.wanmi.sbc.customer.api.provider.communityleader.CommunityLeaderQueryProvider;
import com.wanmi.sbc.customer.api.provider.communitypickup.CommunityLeaderPickupPointQueryProvider;
import com.wanmi.sbc.customer.api.provider.customer.CustomerProvider;
import com.wanmi.sbc.customer.api.provider.customer.CustomerQueryProvider;
import com.wanmi.sbc.customer.api.provider.detail.CustomerDetailQueryProvider;
import com.wanmi.sbc.customer.api.provider.distribution.DistributionCustomerQueryProvider;
import com.wanmi.sbc.customer.api.provider.enterpriseinfo.EnterpriseInfoQueryProvider;
import com.wanmi.sbc.customer.api.request.communityleader.CommunityLeaderByIdRequest;
import com.wanmi.sbc.customer.api.request.communitypickup.CommunityLeaderPickupPointListRequest;
import com.wanmi.sbc.customer.api.request.customer.CustomerAccountModifyStateRequest;
import com.wanmi.sbc.customer.api.request.customer.CustomerGetByIdRequest;
import com.wanmi.sbc.customer.api.request.detail.CustomerDetailByCustomerIdRequest;
import com.wanmi.sbc.customer.api.request.distribution.DistributionCustomerByCustomerIdRequest;
import com.wanmi.sbc.customer.api.request.enterpriseinfo.EnterpriseInfoByCustomerIdRequest;
import com.wanmi.sbc.customer.api.response.communityleader.CommunityLeaderByIdResponse;
import com.wanmi.sbc.customer.api.response.customer.CustomerGetByIdResponse;
import com.wanmi.sbc.customer.bean.enums.CustomerErrorCodeEnum;
import com.wanmi.sbc.customer.bean.enums.CustomerStatus;
import com.wanmi.sbc.customer.bean.vo.*;
import com.wanmi.sbc.customer.response.LoginResponse;
import com.wanmi.sbc.customer.service.CustomerCacheService;
import com.wanmi.sbc.empower.bean.enums.IsOpen;
import com.wanmi.sbc.empower.bean.vo.PayGatewayVO;
import com.wanmi.sbc.order.api.provider.trade.TradeQueryProvider;
import com.wanmi.sbc.order.api.request.trade.TradeQueryFirstCompleteRequest;
import com.wanmi.sbc.setting.bean.enums.ConfigKey;
import com.wanmi.sbc.vas.api.provider.iep.iepsetting.IepSettingQueryProvider;
import com.wanmi.sbc.vas.bean.enums.VasErrorCodeEnum;
import com.wanmi.sbc.vas.bean.vo.IepSettingVO;

import io.jsonwebtoken.*;

import jakarta.servlet.http.HttpServletRequest;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.*;
import java.util.stream.Collectors;

/**
 * BFF公共工具类
 * Created by daiyitian on 15/12/29.
 */
@Component
@Slf4j
public final class CommonUtil {

    @Autowired
    private CustomerQueryProvider customerQueryProvider;

    @Autowired
    private CustomerDetailQueryProvider customerDetailQueryProvider;

    @Autowired
    private RedisUtil redisService;

    @Autowired
    private IepSettingQueryProvider iepSettingQueryProvider;

    @Autowired
    private EnterpriseInfoQueryProvider enterpriseInfoQueryProvider;

    @Autowired
    private DistributionCustomerQueryProvider distributionCustomerQueryProvider;

    @Value("${jwt.secret-key}")
    private String key;

    @Autowired
    private JwtProperties jwtProperties;

    @Autowired
    private TradeQueryProvider tradeQueryProvider;

    @Autowired
    private CustomerProvider customerProvider;

    @Autowired
    private CommunityLeaderPickupPointQueryProvider communityLeaderPickupPointQueryProvider;

    @Autowired
    private CommunityLeaderQueryProvider communityLeaderQueryProvider;

    @Autowired
    private CustomerCacheService customerCacheService;


    /**
     * 获取当前登录编号
     *
     * @return 当前登录编号
     */
    public String getOperatorId() {
        Operator operator = getOperator();
        if (operator != null) {
            return getOperator().getUserId();
        }
        return null;
    }


    /**
     * 正则表达式：验证手机号 匹配最新的正则表达式
     */
    public static final String REGEX_MOBILE = "^1\\d{10}$";

    /**
     * 正则表达式：验证邮箱
     */
    public static final String REGEX_EMAIL = "^([a-z0-9A-Z_]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";

    /** 正则表达式：银行卡 */
    public static final String REGEX_BANK_CARD =
            "^([1-9]{1})(\\d{14}|\\d{15}|\\d{16}|\\d{17}|\\d{18}|\\d{19,30})$";

    /**
     * 图片格式后缀大全
     */
    protected static final String[] IMAGE_SUFFIX = new String[]{"bmp", "jpg", "jpeg", "heif", "png", "tif", "gif", "pcx",
            "tga",
            "exif", "fpx", "svg", "psd", "cdr", "pcd", "dxf", "ufo", "eps", "ai", "raw", "WMF", "webp"};


    /**
     * 常见视频格式后缀大全
     */
    protected static final String[] VIDEO_SUFFIX = new String[]{"avi", "wmv", "rm", "rmvb", "mpeg1", "mpeg2",
            "mpeg4(mp4)",
            "3gp", "asf", "swf"
            , "vob", "dat", "mov", "m4v", "flv", "f4v", "mkv", "mts", "ts", "imv", "amv", "xv", "qsv"};


    /**
     * 获取登录客户信息
     *
     * @return
     */
    public CustomerVO getCustomer() {

        if (StringUtils.isNotEmpty(getOperatorId())) {
            // 获取会员和等级
            CustomerGetByIdResponse customer =
                    customerQueryProvider
                            .getCustomerById(new CustomerGetByIdRequest(getOperatorId()))
                            .getContext();
            if (customer == null) {
                throw new SbcRuntimeException(CommonErrorCodeEnum.K000014);
            }
            return customer;
        }else{
            return null;
        }

    }

    /**
     * 获取登录客户信息
     *
     * @return
     */
    public CommunityLeaderPickupPointVO getLeader() {
        String customerId = getOperatorId();
        if (StringUtils.isNotEmpty(customerId)) {
            CommunityLeaderPickupPointListRequest listRequest = CommunityLeaderPickupPointListRequest.builder()
                    .customerId(customerId)
                    .delFlag(DeleteFlag.NO)
                    .assistRelFlag(Boolean.TRUE)
                    .build();
            List<CommunityLeaderPickupPointVO> voList = communityLeaderPickupPointQueryProvider.list(listRequest).getContext()
                    .getCommunityLeaderPickupPointList();
            if (CollectionUtils.isEmpty(voList)) {
                throw new SbcRuntimeException(CommonErrorCodeEnum.K999999, "您还不是团长，可联系平台申请团长资格");
            }
            return voList.get(0);
        }else{
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000014);
        }
    }

    /**
     * 获取登录客户信息
     *
     * @return
     */
    public CommunityLeaderVO getCommunityLeader() {
        String customerId = getOperatorId();
        if (StringUtils.isNotEmpty(customerId)) {
            //查询团长信息
            CommunityLeaderByIdResponse communityLeaderByIdResponse = communityLeaderQueryProvider.getByCustomerId(
                    CommunityLeaderByIdRequest.builder()
                            .customerId(customerId).build()).getContext();
            Map<String, LogOutStatus> map = customerCacheService.getLogOutStatus(Collections.singletonList(customerId));
            communityLeaderByIdResponse.getCommunityLeaderVO().setLogOutStatus(map.getOrDefault(customerId, LogOutStatus.NORMAL));
            return communityLeaderByIdResponse.getCommunityLeaderVO();
        }else{
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000014);
        }
    }

    /**
     * 获取customer对象, 不校验customer信息
     * 默认获取的jwt是真实数据, 无须校验
     * @return
     */
    public CustomerVO getCustomerNotValidCustomer() {
        //获取会员和等级
        CustomerGetByIdResponse customer = new CustomerGetByIdResponse();
        customer.setCustomerId(getOperatorId());
        return customer;
    }


    /**
     * 是否是分销员
     *
     * @return
     */
    public boolean isDistributor() {
        String operatorId = getOperatorId();
        if (StringUtils.isNotEmpty(operatorId)) {
            Boolean isDistributor =
                    customerQueryProvider
                            .isDistributor(
                                    CustomerGetByIdRequest.builder().customerId(operatorId).build())
                            .getContext();
            return isDistributor;
        }
        return false;
    }

    /**
     * 是否是企业会员
     *
     * @return
     */
    public boolean isIepCustomer() {
        String operatorId = getOperatorId();
        if (StringUtils.isNotEmpty(operatorId)) {
            Boolean isIepCustomer =
                    customerQueryProvider
                            .isIepCustomer(
                                    CustomerGetByIdRequest.builder().customerId(operatorId).build())
                            .getContext();
            return isIepCustomer;
        }
        return false;
    }


    /**
     * 是否是新人
     *
     * @return
     */
    public Boolean isNewCustomer() {
        Operator operator = getOperator();
        Boolean isNew = operator.getIsNew();
        if (Boolean.FALSE.equals(isNew)) {
            return Boolean.FALSE;
        }
        String operatorId = operator.getUserId();
        if (StringUtils.isNotEmpty(operatorId)) {
            Integer isNewCustomer =
                    customerQueryProvider
                            .isNewCustomer(
                                    CustomerGetByIdRequest.builder().customerId(operatorId).build())
                            .getContext();
            if (Objects.isNull(isNewCustomer)) {
                String tradeId = tradeQueryProvider.queryFirstPayTrade(new TradeQueryFirstCompleteRequest(operatorId))
                        .getContext().getTradeId();
                //不存在已经支付订单
                if (StringUtils.isEmpty(tradeId)) {
                    //更新新人状态
                    customerProvider.modifyNewCustomerState(CustomerAccountModifyStateRequest.builder()
                            .customerId(operatorId)
                            .isNew(Constants.ZERO)
                            .build());
                    return Boolean.TRUE;
                } else {
                    //更新新人状态
                    customerProvider.modifyNewCustomerState(CustomerAccountModifyStateRequest.builder()
                            .customerId(operatorId)
                            .isNew(Constants.ONE)
                            .build());
                    return Boolean.FALSE;
                }
            }
            return isNewCustomer == Constants.ZERO;
        }
        return Boolean.FALSE;
    }

    /**
     * 获取分销渠道对象
     *
     * @return
     */
    public DistributeChannel getDistributeChannel() {
        DistributeChannel distributeChannel = JSONObject.parseObject(
                HttpUtil.getRequest().getHeader("distribute-channel"), DistributeChannel.class);
        if (Objects.isNull(distributeChannel)) {
            distributeChannel = new DistributeChannel();
        }
        return distributeChannel;
    }


    /**
     * 获取终端
     *
     * @return
     */
    public TerminalSource getTerminal() {
        String terminal = HttpUtil.getRequest().getHeader("terminal");
        return TerminalSource.getTerminalSource(terminal);
    }

    /**
     * 获取购物车归属
     * 当且仅当为店铺精选时，需要根据InviteeId区分购物车
     */
    public String getPurchaseInviteeId() {

        if (null != this.getDistributeChannel() && Objects.equals(this.getDistributeChannel().getChannelType(),
                ChannelType.SHOP)) {
            return this.getDistributeChannel().getInviteeId();
        }
        return Constants.PURCHASE_DEFAULT;
    }


    /**
     * 获取当前登录对象
     *
     * @return
     */
    public Operator getOperator() {
        this.getToken(HttpUtil.getRequest());
        Claims claims = (Claims) (HttpUtil.getRequest().getAttribute("claims"));
        if (claims == null) {
            return new Operator();
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
        return Operator.builder()
                .account(ObjectUtils.toString(claims.get("customerAccount")))
                .platform(Platform.CUSTOMER)
                .adminId(ObjectUtils.toString(claims.get("adminId")))
                .ip(String.valueOf(claims.get("ip")))
                .name(String.valueOf(claims.get("customerName")))
                .userId(String.valueOf(claims.get("customerId")))
                .customerDetailId(String.valueOf(claims.get("customerDetailId")))
                .firstLogin(claims.get("firstLogin") == null ? Boolean.FALSE : Boolean.valueOf(Objects.toString(claims.get("firstLogin"))))
                .services(services)
                //生成用户token，用于同一用户不同终端登陆区别
                //.terminalToken(String.valueOf(claims.get("terminalToken")))
                .terminalToken(claims.get("terminalToken") == null ? String.valueOf(claims.get("customerId")) : String.valueOf(claims.get("terminalToken")))
                .isNew(claims.get("isNew") == null ? null : Boolean.FALSE)
                .build();
    }

    /**
     * 获取用户的登陆的终端token
     *
     * @return
     */
    public String getTerminalToken() {
        String terminalToken = getOperator().getTerminalToken();
        if (StringUtils.isEmpty(terminalToken) || "null".equals(terminalToken)) {
            terminalToken = getOperator().getUserId();
        }
        return terminalToken;

    }

    /**
     * 获取当前登录对象(JWT忽略的时候使用)
     *
     * @return
     */
    public Operator getUserInfo() {
        Claims claims = (Claims) (HttpUtil.getRequest().getAttribute("claims"));
        if (claims == null) {
            //从header中直接获取token解析 —— 解决需要在被过滤的请求中获取当前登录人信息
            return getUserInfo(this.getToken(HttpUtil.getRequest()));
        }
        return getUserInfo(claims);
    }


    /**
     * @description 根据token获取会员信息
     * @author  daiyitian
     * @date 2021/6/16 18:41
     * @param token token
     * @return com.wanmi.sbc.common.base.Operator 会员信息
     **/
    public Operator getUserInfo(String token){
        if (StringUtils.isNotBlank(token)) {
            return this.getUserInfo(this.validate(token));
        }
        return new Operator();
    }

    /**
     * @description 根据jwt对象获取会员信息
     * @author daiyitian
     * @date 2021/6/16 18:41
     * @param claims jwt对象
     * @return com.wanmi.sbc.common.base.Operator 会员信息
     */
    public Operator getUserInfo(Claims claims) {
        // 修复sonar检测出的bug：A "NullPointerException" could be thrown; "claims" is nullable here.
        if (claims == null) {
            return new Operator();
        }
        // 已登录会员，需要再次比对storeid 加强校验，防止携带其他店铺登录的token，越权查询操作数据
        //        checkIfStore(Long.valueOf(claims.get("storeId").toString()));
        return Operator.builder()
                .account(ObjectUtils.toString(claims.get("customerAccount")))
                .platform(Platform.CUSTOMER)
                .adminId(ObjectUtils.toString(claims.get("adminId")))
                .ip(String.valueOf(claims.get("ip")))
                .name(String.valueOf(claims.get("customerName")))
                .userId(String.valueOf(claims.get("customerId")))
                .storeId(String.valueOf(claims.get("storeId")))
                .customerDetailId(String.valueOf(claims.get("customerDetailId")))
                .firstLogin(
                        claims.get("firstLogin") == null
                                ? Boolean.FALSE
                                : Boolean.valueOf(Objects.toString(claims.get("firstLogin"))))
                // 生成用户token，用于同一用户不同终端登陆区别
                .terminalToken(String.valueOf(claims.get("terminalToken")))
                .terminalToken(
                        claims.get("terminalToken") == null
                                ? String.valueOf(claims.get("customerId"))
                                : String.valueOf(claims.get("terminalToken")))
                .build();
    }

    /**
     * 获取jwtToken
     *
     * @return
     */
    public String getToken(HttpServletRequest request) {

        String jwtHeaderKey = org.apache.commons.lang3.StringUtils.isNotBlank(jwtProperties.getJwtHeaderKey()) ? jwtProperties.getJwtHeaderKey
                () : "Authorization";
        String jwtHeaderPrefix = org.apache.commons.lang3.StringUtils.isNotBlank(jwtProperties.getJwtHeaderPrefix()) ? jwtProperties
                .getJwtHeaderPrefix() : "Bearer ";

        String authHeader = request.getHeader(jwtHeaderKey);

        //当token失效,直接返回失败
        if (authHeader != null && authHeader.length() > Constants.NUM_16) {
            this.validateToken(authHeader.substring(jwtHeaderPrefix.length()));
            return authHeader.substring(jwtHeaderPrefix.length());
        }
        return null;
    }

    /**
     * 验证转换为Claims
     *
     * @param token
     * @return
     */
    private Claims validate(String token) {
        try {
            final Claims claims = Jwts.parser().setSigningKey(key).build().parseClaimsJws(token).getBody();
            log.debug("JwtFilter out ['Authorization success']");
            return claims;
        } catch (final SignatureException | MalformedJwtException | ExpiredJwtException e) {
            log.info("JwtFilter exception, exMsg:{}", e.getMessage());
            return null;
        }
    }

    public LoginResponse loginByCustomerId(String customerId, String jwtSecretKey) {
//        Customer customer = customerService.findInfoById(customerId);
        CustomerGetByIdResponse customer = customerQueryProvider.getCustomerById(new CustomerGetByIdRequest
                (customerId)).getContext();
        if (customer == null) {
            throw new SbcRuntimeException(CustomerErrorCodeEnum.K010008);
        }

        CustomerDetailVO customerDetail = customerDetailQueryProvider.getCustomerDetailByCustomerId(
                CustomerDetailByCustomerIdRequest.builder().customerId(customer.getCustomerId()).build()).getContext();

        if (customerDetail == null) {
            throw new SbcRuntimeException(CustomerErrorCodeEnum.K010008);
        }
        //是否禁用
        if (CustomerStatus.DISABLE.toValue() == customerDetail.getCustomerStatus().toValue()) {
            throw new SbcRuntimeException(CustomerErrorCodeEnum.K010004, new Object[]{"，原因为：" + customerDetail
                    .getForbidReason()});
        }
        //组装登录信息
        return this.getLoginResponse(customer, jwtSecretKey);
    }

    /**
     * 拼接登录后返回值
     *
     * @param customer
     * @return
     */
    public LoginResponse getLoginResponse(CustomerVO customer, String jwtSecretKey) {
        Date date = new Date();
        Map<String, String> vasList = redisService.hgetall(ConfigKey.VALUE_ADDED_SERVICES.toString());
        Integer isNew = customer.getIsNew();
        Boolean isNewCustomer =  (Objects.nonNull(isNew) && isNew == Constants.ONE) ? Boolean.FALSE : null;
        String token = Jwts.builder().setSubject(customer.getCustomerAccount())
                .compressWith(CompressionCodecs.DEFLATE)
                .signWith(SignatureAlgorithm.HS256, jwtSecretKey)
                .setIssuedAt(date)
                .claim("customerId", customer.getCustomerId())
                .claim("customerAccount", customer.getCustomerAccount())
                .claim("customerName", customer.getCustomerDetail().getCustomerName())
                .claim("customerDetailId", customer.getCustomerDetail().getCustomerDetailId())
                .claim("ip", customer.getLoginIp())
                .claim("terminalToken", MD5Util.md5Hex(customer.getCustomerId() + date.getTime() + RandomStringUtils.randomNumeric(4)))
                .claim("firstLogin", Objects.isNull(customer.getLoginTime()))//是否首次登陆
                .claim("isNew", isNewCustomer)
                .claim(ConfigKey.VALUE_ADDED_SERVICES.toString(), JSONObject.toJSONString(vasList))
//                .setExpiration(DateUtils.addMonths(date, 1))
                .setExpiration(DateUtils.addDays(date, 360))
                .compact();
        // 如果是企业会员，则增加公司信息与审核信息/不通过原因
        EnterpriseInfoVO enterpriseInfoVO =
                enterpriseInfoQueryProvider.getByCustomerId(EnterpriseInfoByCustomerIdRequest.builder()
                        .customerId(customer.getCustomerId()).build()).getContext().getEnterpriseInfoVO();

        // 登陆时查询是否是分销员邀请注册，若是则增加邀请码返回前台展示使用
        DistributionCustomerVO distributionCustomerVO =
                distributionCustomerQueryProvider.getByCustomerId(DistributionCustomerByCustomerIdRequest.builder()
                        .customerId(customer.getCustomerId()).build()).getContext().getDistributionCustomerVO();
        String inviteCode = StringUtils.EMPTY;
        if (Objects.nonNull(distributionCustomerVO)) {
            String inviteId = StringUtils.isEmpty(distributionCustomerVO.getInviteCustomerIds()) ? StringUtils.EMPTY :
                    distributionCustomerVO.getInviteCustomerIds().split(",")[0];
            DistributionCustomerVO inviteCustomer =
                    distributionCustomerQueryProvider.getByCustomerId(DistributionCustomerByCustomerIdRequest.builder()
                            .customerId(inviteId).build()).getContext().getDistributionCustomerVO();
            if (Objects.nonNull(inviteCustomer)) {
                inviteCode = inviteCustomer.getInviteCode();
            }
        }

        // Token放入缓存
        this.putLoginRedis(token);

        return LoginResponse.builder()
                .accountName(customer.getCustomerAccount())
                .customerId(customer.getCustomerId())
                .token(token)
                .checkState(customer.getCheckState().toValue())
                .enterpriseCheckState(customer.getEnterpriseCheckState())
                .enterpriseCheckReason(customer.getEnterpriseCheckReason())
                .customerDetail(customer.getCustomerDetail())
                .enterpriseInfoVO(enterpriseInfoVO)
                .inviteCode(inviteCode)
                .logOutStatus(customer.getLogOutStatus())
                .build();
    }

    public void validateToken(String token) {
        String tokenKey = getLoginTokenKey(token);
        if(!redisService.hasKey(tokenKey)) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000015);
        }
        return;
    }

    /**
     * 设定登录凭证
     * @param token 登陆凭证
     */
    public void putLoginRedis(String token){
        //7天后过期
//        redisService.setString(getLoginTokenKey(token),token,60L*60*24*7);
        redisService.setString(getLoginTokenKey(token),token,60L*60*24*360);
    }

    /**
     * 删除登录的Token
     * @param token 登陆凭证
     */
    public void deleteLoginRedis(String token){
        redisService.delete(getLoginTokenKey(token));
    }

    /**
     * 获取TokenLoginKey
     * @param token 登陆凭证
     */
    private String getLoginTokenKey(String token) {
        String tmpToken = MD5Util.md5Hex(token, StandardCharsets.UTF_8.name()).substring(8, 24);
        return CacheKeyConstant.JSON_WEB_TOKEN_C.concat(tmpToken);
    }

    public String wrapperToken(String jwtSecretKey,String customerAccount) {
        Date date = new Date();
        Map<String, String> vasList = redisService.hgetall(ConfigKey.VALUE_ADDED_SERVICES.toString());
        String token = Jwts.builder().setSubject(customerAccount)
                .compressWith(CompressionCodecs.DEFLATE)
                .signWith(SignatureAlgorithm.HS256, jwtSecretKey)
                .setIssuedAt(date)
                .claim("customerAccount", customerAccount)
                .claim(ConfigKey.VALUE_ADDED_SERVICES.toString(), JSONObject.toJSONString(vasList))
                .setExpiration(DateUtils.addDays(date, 360))
                //.setExpiration(DateUtils.addHours(date, 12))
                .compact();
        // Token放入缓存
        this.putLoginRedis(token);
        return token;
    }



    /**
     * 查询指定增值服务是否购买
     *
     * @param constants
     * @return
     */
    public boolean findVASBuyOrNot(VASConstants constants) {
        boolean flag = false;
        List<VASEntity> list = this.getAllServices();
        VASEntity vasEntity =
                list.stream().filter(f -> constants.equals(f.getServiceName()) && f.isServiceStatus()).findFirst().orElse(null);
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
        Claims claims = (Claims) HttpUtil.getRequest().getAttribute("claims");
        if (claims == null) {
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
     * 获取分享id
     * @return
     */
    public String getShareId(String customerId){
        //防止刷分行为，每次分享生成一个id，存在redis中
        long milli = LocalDateTime.now().toInstant(ZoneOffset.of("+8")).toEpochMilli();
        String shareKey;
        if(StringUtils.isNotBlank(customerId)) {
            shareKey = RedisKeyConstant.WX_SHARE_FLAG.concat(customerId.concat(String.valueOf(milli)));
        } else {
            shareKey = RedisKeyConstant.WX_SHARE_FLAG.concat(Objects.requireNonNull(this.getOperatorId()).concat(String.valueOf(milli)));
        }

        //存放一周
        redisService.setString(shareKey, "0", 604800);
        return shareKey;
    }

    /**
     * 校验图片
     * @param imageName
     * @return
     */
    public static boolean checkImageByName(String imageName) {
        String[] imageSuffixArr = {"jpg", "JPG", "jpeg", "png", "PNG", "gif", "GIF", "heic"};
        for (String imageSuffix : Arrays.asList(imageSuffixArr)) {
            if (StringUtils.isNotBlank(imageName) && imageName.endsWith(imageSuffix)) {
                return true;
            }
        }
        return false;
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
     * 注册验证
     * @param errKey
     * @param smsKey
     * @param verifyCode
     */
    public void verifyCodeMsgLakala(String errKey, String smsKey, String verifyCode) {
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
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000026);
        }
    }

    public boolean lakalaPayIsOpen() {
        boolean lakalaOpenFlag = false;
        PayGatewayVO payGateway =
                JSONObject.parseObject(
                        RedisUtil.getInstance().getString(RedisKeyConstant.LAKALA_PAY_SETTING),
                        PayGatewayVO.class);
        if (Objects.nonNull(payGateway) && IsOpen.YES.equals(payGateway.getIsOpen())) {
            lakalaOpenFlag = true;
        }
        PayGatewayVO payGatewayVO2 = JSONObject.parseObject(
                RedisUtil.getInstance().getString(RedisKeyConstant.LAKALA_CASHER_PAY_SETTING),
                PayGatewayVO.class);
        if (Objects.nonNull(payGatewayVO2) && IsOpen.YES.equals(payGatewayVO2.getIsOpen())) {
            lakalaOpenFlag = true;
        }
        return lakalaOpenFlag;
    }
}
