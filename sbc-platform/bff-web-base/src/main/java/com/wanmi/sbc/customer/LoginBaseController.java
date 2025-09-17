package com.wanmi.sbc.customer;

import com.alibaba.fastjson2.JSONObject;
import com.google.code.kaptcha.Producer;
import com.wanmi.sbc.common.annotation.MultiSubmit;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.cache.CacheConstants;
import com.wanmi.sbc.common.enums.*;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.redis.CacheKeyConstant;
import com.wanmi.sbc.common.redis.util.RedisUtil;
import com.wanmi.sbc.common.util.*;
import com.wanmi.sbc.configuration.jwt.JwtProperties;
import com.wanmi.sbc.customer.api.provider.agent.AgentQueryProvider;
import com.wanmi.sbc.customer.api.provider.customer.CustomerQueryProvider;
import com.wanmi.sbc.customer.api.provider.detail.CustomerDetailQueryProvider;
import com.wanmi.sbc.customer.api.provider.employee.EmployeeQueryProvider;
import com.wanmi.sbc.customer.api.provider.enterpriseinfo.EnterpriseInfoQueryProvider;
import com.wanmi.sbc.customer.api.provider.growthvalue.CustomerGrowthValueProvider;
import com.wanmi.sbc.customer.api.provider.loginregister.CustomerSiteProvider;
import com.wanmi.sbc.customer.api.provider.loginregister.CustomerSiteQueryProvider;
import com.wanmi.sbc.customer.api.provider.points.CustomerPointsDetailSaveProvider;
import com.wanmi.sbc.customer.api.request.agent.AgentGetByUniqueCodeRequest;
import com.wanmi.sbc.customer.api.request.agent.GetAgentRequest;
import com.wanmi.sbc.customer.api.request.customer.CustomerGetByIdRequest;
import com.wanmi.sbc.customer.api.request.detail.CustomerDetailByCustomerIdRequest;
import com.wanmi.sbc.customer.api.request.employee.EmployeeByIdRequest;
import com.wanmi.sbc.customer.api.request.enterpriseinfo.EnterpriseInfoByCustomerIdRequest;
import com.wanmi.sbc.customer.api.request.growthvalue.CustomerGrowthValueAddRequest;
import com.wanmi.sbc.customer.api.request.loginregister.CustomerByAccountRequest;
import com.wanmi.sbc.customer.api.request.loginregister.CustomerCheckByAccountRequest;
import com.wanmi.sbc.customer.api.request.loginregister.CustomerConsummateRegisterRequest;
import com.wanmi.sbc.customer.api.request.loginregister.CustomerLoginRequest;
import com.wanmi.sbc.customer.api.request.loginregister.CustomerModifyRequest;
import com.wanmi.sbc.customer.api.request.loginregister.CustomerRegisterRequest;
import com.wanmi.sbc.customer.api.request.loginregister.CustomerUpdateLoginTimeRequest;
import com.wanmi.sbc.customer.api.request.points.CustomerPointsDetailAddRequest;
import com.wanmi.sbc.customer.api.response.agent.AgentGetByUniqueCodeResponse;
import com.wanmi.sbc.customer.api.response.agent.GetAgentResponse;
import com.wanmi.sbc.customer.api.response.customer.CustomerGetByIdResponse;
import com.wanmi.sbc.customer.api.response.employee.EmployeeByIdResponse;
import com.wanmi.sbc.customer.api.response.enterpriseinfo.EnterpriseInfoByCustomerIdResponse;
import com.wanmi.sbc.customer.api.response.loginregister.CustomerByAccountResponse;
import com.wanmi.sbc.customer.api.response.loginregister.CustomerConsummateRegisterResponse;
import com.wanmi.sbc.customer.api.response.loginregister.CustomerLoginResponse;
import com.wanmi.sbc.customer.api.response.loginregister.CustomerRegisterResponse;
import com.wanmi.sbc.customer.bean.dto.CustomerDTO;
import com.wanmi.sbc.customer.bean.enums.*;
import com.wanmi.sbc.customer.bean.vo.CustomerDetailVO;
import com.wanmi.sbc.customer.bean.vo.CustomerVO;
import com.wanmi.sbc.customer.bean.vo.DistributionCustomerVO;
import com.wanmi.sbc.customer.bean.vo.EnterpriseInfoVO;
import com.wanmi.sbc.customer.request.LoginByTemporaryCodeRequest;
import com.wanmi.sbc.customer.request.LoginRequest;
import com.wanmi.sbc.customer.request.LoginVerificationCodeRequest;
import com.wanmi.sbc.customer.request.RegisterRequest;
import com.wanmi.sbc.customer.response.LoginResponse;
import com.wanmi.sbc.customer.response.RegisterResponse;
import com.wanmi.sbc.customer.service.CustomerCacheService;
import com.wanmi.sbc.customer.service.DistributionInviteNewService;
import com.wanmi.sbc.customer.service.LoginBaseService;
import com.wanmi.sbc.distribute.DistributionCacheService;
import com.wanmi.sbc.elastic.api.provider.customer.EsCustomerDetailProvider;
import com.wanmi.sbc.elastic.api.request.customer.EsCustomerDetailInitRequest;
import com.wanmi.sbc.marketing.api.provider.communitystatistics.CommunityStatisticsCustomerProvider;
import com.wanmi.sbc.marketing.api.provider.coupon.CouponActivityProvider;
import com.wanmi.sbc.marketing.api.request.communitystatistics.CommunityStatisticsCustomerAddRequest;
import com.wanmi.sbc.marketing.api.request.coupon.GetCouponGroupRequest;
import com.wanmi.sbc.marketing.api.request.coupon.GetRegisterCouponRequest;
import com.wanmi.sbc.marketing.api.response.coupon.GetRegisterOrStoreCouponResponse;
import com.wanmi.sbc.marketing.bean.constant.Constant;
import com.wanmi.sbc.marketing.bean.enums.CouponActivityType;
import com.wanmi.sbc.marketing.bean.enums.RegisterLimitType;
import com.wanmi.sbc.message.StoreMessageBizService;
import com.wanmi.sbc.mq.producer.WebBaseProducerService;
import com.wanmi.sbc.setting.api.provider.AuditQueryProvider;
import com.wanmi.sbc.setting.api.response.UserAuditResponse;
import com.wanmi.sbc.setting.bean.enums.ConfigKey;
import com.wanmi.sbc.util.CommonUtil;
import com.wanmi.sbc.util.WeakPasswordsCheckUtil;
import com.wanmi.sbc.vas.bean.vo.IepSettingVO;

import io.jsonwebtoken.*;
import io.seata.spring.annotation.GlobalTransactional;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.*;

import javax.imageio.ImageIO;

/**
 * 会员
 * Created by Daiyitian on 2017/4/19.
 */
@RestController
@Validated
@Slf4j
@Tag(name = "LoginBaseController", description = "S2B web公用-会员登录信息API")
public class LoginBaseController {

    @Autowired
    private CustomerSiteQueryProvider customerSiteQueryProvider;

    @Autowired
    private CustomerSiteProvider customerSiteProvider;

    @Autowired
    private CustomerQueryProvider customerQueryProvider;

    @Autowired
    private CustomerDetailQueryProvider customerDetailQueryProvider;

    @Autowired
    private EmployeeQueryProvider employeeQueryProvider;

    @Autowired
    private RedisUtil redisService;

    @Value("${jwt.secret-key}")
    private String jwtSecretKey;

    @Autowired
    private CommonUtil commonUtil;

    @Autowired
    private JwtProperties jwtProperties;

    @Autowired
    private Producer captchaProducer;

    @Autowired
    private AuditQueryProvider auditQueryProvider;

    @Autowired
    private CouponActivityProvider couponActivityProvider;

    @Autowired
    private CustomerGrowthValueProvider customerGrowthValueProvider;

    @Autowired
    private AgentQueryProvider agentQueryProvider;

    /**
     * 注入分销邀新service
     */
    @Autowired
    private DistributionInviteNewService distributionInviteNewService;

    @Autowired
    private CustomerPointsDetailSaveProvider customerPointsDetailSaveProvider;

    @Autowired
    private LoginBaseService loginBaseService;

    @Autowired
    private DistributionCacheService distributionCacheService;

    @Autowired
    private EnterpriseInfoQueryProvider enterpriseInfoQueryProvider;

    @Autowired
    private WebBaseProducerService webBaseProducerService;

    @Autowired
    private CustomerCacheService customerCacheService;

    @Autowired private RedissonClient redissonClient;

    @Autowired private StoreMessageBizService storeMessageBizService;

    @Autowired private WeakPasswordsCheckUtil weakPasswordsCheckUtil;

    @Autowired
    private CommunityStatisticsCustomerProvider communityStatisticsCustomerProvider;



    /**
     * 注册用户
     * 审核开关关闭并且信息完善开关关闭，则直接登录
     * 其他情况则跳转到对应页面
     *
     * @param registerRequest
     * @return LoginResponse
     */
    @Operation(summary = "注册用户")
    @RequestMapping(value = "/register", method = RequestMethod.POST)
   // @Transactional
    @GlobalTransactional
    @MultiSubmit
    public BaseResponse<LoginResponse> register(@RequestBody RegisterRequest registerRequest) {
        log.info("register start registerRequest = {}",registerRequest);

        // 参数校验
        if (StringUtils.isAnyBlank(registerRequest.getCustomerAccount(), registerRequest.getCustomerPassword(), registerRequest.getVerifyCode())) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        //弱密码校验处理
        weakPasswordsCheckUtil.weakPasswordsCheck(registerRequest.getCustomerPassword());

        //累计验证错误次数
        String errKey = CacheKeyConstant.REGISTER_ERR.concat(registerRequest.getCustomerAccount());
        // 校验及封装校验后请求参数
        String smsKey = CacheKeyConstant.VERIFY_CODE_KEY.concat(registerRequest.getCustomerAccount());
        // 校验短信验证码
        commonUtil.verifyCodeMsg(errKey, smsKey, registerRequest.getVerifyCode());
        DistributionCustomerVO distributionCustomerVO = new DistributionCustomerVO();
        // 校验邀请码,若为pc端普通会员注册，无分销则不进行校验
        if (!TerminalType.PC.equals(registerRequest.getTerminalType())){
             distributionCustomerVO =
                    loginBaseService.checkInviteIdAndInviteCode(registerRequest.getInviteeId(),
                            registerRequest.getInviteCode());
        }

        CustomerDTO customer = new CustomerDTO();
        customer.setCustomerAccount(registerRequest.getCustomerAccount());
        customer.setCustomerPassword(registerRequest.getCustomerPassword());
        if (StringUtils.isNotBlank(registerRequest.getEmployeeId())) {
            EmployeeByIdRequest idRequest = new EmployeeByIdRequest();
            idRequest.setEmployeeId(registerRequest.getEmployeeId());
            if (Objects.isNull(employeeQueryProvider.getById(idRequest).getContext())) {
                registerRequest.setEmployeeId(null);
            }
        }
        CustomerRegisterRequest customerRegisterRequest = new CustomerRegisterRequest();
        customerRegisterRequest.setEmployeeId(registerRequest.getEmployeeId());
        customerRegisterRequest.setCustomerDTO(customer);
        BaseResponse<CustomerRegisterResponse> customerRegisterResponseBaseResponse = customerSiteProvider.register(customerRegisterRequest);
        CustomerVO customerVO = customerRegisterResponseBaseResponse.getContext();
        if (customerVO != null) {
            log.info("register customer success ......注册信息详情：{}",customerVO);
            //删除验证码缓存
            redisService.delete(smsKey);
            //redisService.hdelete(CacheKeyConstant.KAPTCHA_KEY, registerRequest.getUuid());
            LoginResponse loginResponse = commonUtil.getLoginResponse(customerVO, jwtSecretKey);
            //审核开关关闭并且信息完善开关关闭，则直接登录
            boolean isPerfectCustomer = auditQueryProvider.isPerfectCustomerInfo().getContext().isPerfect();
            boolean isCustomerAudit = auditQueryProvider.isCustomerAudit().getContext().isAudit();
            if (!isPerfectCustomer && !isCustomerAudit) {
                log.info("to home page ......");
                loginResponse.setIsLoginFlag(Boolean.TRUE);
                CustomerUpdateLoginTimeRequest updateLoginTimeRequest = new CustomerUpdateLoginTimeRequest();
                updateLoginTimeRequest.setCustomerId(customerVO.getCustomerId());
                customerSiteQueryProvider.updateLoginTime(updateLoginTimeRequest);
                // 领取注册赠券
                GetRegisterOrStoreCouponResponse couponResponse = this.getCouponGroup(customerVO.getCustomerId(), CouponActivityType.REGISTERED_COUPON, Constant.BOSS_DEFAULT_STORE_ID);
//                loginResponse.setCouponResponse(couponResponse);
                if (Objects.nonNull(couponResponse)) {
                    redisService.setString(CacheKeyConstant.REGISTER_SEND_COUPON.concat(customerVO.getCustomerId()), customerVO.getCustomerId());
                    redisService.expireByMinutes(CacheKeyConstant.REGISTER_SEND_COUPON.concat(customerVO.getCustomerId()), 180L);
                }
            } else {

                // 领取注册赠券 ,如果完善信息开关打开则不能参加注册赠券活动
                if (!isPerfectCustomer) {
                    GetRegisterOrStoreCouponResponse couponResponse = this.getCouponGroup(customerVO.getCustomerId(), CouponActivityType.REGISTERED_COUPON, Constant.BOSS_DEFAULT_STORE_ID);
                }
                log.info("to information improve page ......");
                loginResponse.setCustomerId(customerVO.getCustomerId());
                loginResponse.setIsLoginFlag(Boolean.FALSE);

            }
            webBaseProducerService.sendMQForCustomerRegister(customerVO);

            // 新增邀新记录/成长值/积分等
            this.addOtherInfos(customerVO, distributionCustomerVO, registerRequest);

            // ============= 处理平台的消息发送：新增客户注册待审核 START =============
            storeMessageBizService.handleForCustomerAudit(customerVO);
            // ============= 处理平台的消息发送：新增客户注册待审核 END =============

            return BaseResponse.success(loginResponse);
        }
        log.info("register customer failed ......");
        return BaseResponse.FAILED();
    }

    @Operation(summary = "注册企业用户")
    @RequestMapping(value = "/registerEnterprise", method = RequestMethod.POST)
    @Transactional
    @GlobalTransactional
    public BaseResponse registerEnterprise(@RequestBody RegisterRequest registerRequest) {
        log.info("enter registerEnterprise method ......");

        // 首先判断是否购买企业购增值服务,未购买或未设置自动抛出异常
        IepSettingVO iepSettingVO = commonUtil.getIepSettingInfo();
        if(Objects.isNull(iepSettingVO.getEnterpriseCustomerAuditFlag())){
            throw new SbcRuntimeException(CustomerErrorCodeEnum.K010086);
        }

        if (StringUtils.isBlank(registerRequest.getCustomerId())) {

            // 首次注册校验及封装校验后请求参数
            if (StringUtils.isBlank(registerRequest.getCustomerAccount()) || StringUtils.isBlank(registerRequest.getCustomerPassword())
                    || StringUtils.isBlank(registerRequest.getVerifyCode())) {
                throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
            }
            //校验弱密码处理
            weakPasswordsCheckUtil.weakPasswordsCheck(registerRequest.getCustomerPassword());
            // 校验邀请码
            DistributionCustomerVO distributionCustomerVO =
                    loginBaseService.checkInviteIdAndInviteCode(registerRequest.getInviteeId(),
                            registerRequest.getInviteCode());

            // 如果是第一次请求，返回成功，校验通过进入公司信息页面
            if(Objects.nonNull(registerRequest.getFirstRegisterFlag()) && registerRequest.getFirstRegisterFlag()){
                //累计验证错误次数
                String errKey = CacheKeyConstant.REGISTER_ERR.concat(registerRequest.getCustomerAccount());
                String smsKey = CacheKeyConstant.VERIFY_CODE_KEY.concat(registerRequest.getCustomerAccount());
                // 校验短信验证码
                commonUtil.verifyCodeMsg(errKey, smsKey, registerRequest.getVerifyCode());
                //删除验证码缓存
                redisService.delete(smsKey);
                //redisService.hdelete(CacheKeyConstant.KAPTCHA_KEY, registerRequest.getUuid());
                LoginResponse loginResponse = LoginResponse.builder().token(commonUtil.wrapperToken(jwtSecretKey,registerRequest.getCustomerAccount())).enterpriseRegisterState(Boolean.TRUE).build();
                return BaseResponse.success(loginResponse);
            }
        }

        log.info("register enterpriseCustomer failed ......");
        return BaseResponse.FAILED();
    }

    @Operation(summary = "完善企业用户信息")
    @RequestMapping(value = "/prefectEnterpriseInfo", method = RequestMethod.POST)
    @Transactional
    @GlobalTransactional
    public BaseResponse prefectEnterpriseInfo(@RequestBody RegisterRequest registerRequest) {
        log.info("enter registerEnterprise method ......");

        // 首先判断是否购买企业购增值服务,未购买或未设置自动抛出异常
        IepSettingVO iepSettingVO = commonUtil.getIepSettingInfo();
        if(Objects.isNull(iepSettingVO.getEnterpriseCustomerAuditFlag())){
            throw new SbcRuntimeException(CustomerErrorCodeEnum.K010086);
        }

        if (StringUtils.isNotEmpty(registerRequest.getCustomerId())) {
            if (!registerRequest.getCustomerId().equals(commonUtil.getOperatorId())){
                return BaseResponse.error("非法越权操作");
            }
            // 被驳回后再次注册
            LoginResponse loginResponse = this.registerAgain(registerRequest, iepSettingVO);
            return BaseResponse.success(loginResponse);

        } else {

            // 首次注册校验及封装校验后请求参数
            if (StringUtils.isBlank(registerRequest.getCustomerAccount()) || StringUtils.isBlank(registerRequest.getCustomerPassword())
                    || StringUtils.isBlank(registerRequest.getVerifyCode())) {
                throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
            }
            // 校验邀请码
            DistributionCustomerVO distributionCustomerVO =
                    loginBaseService.checkInviteIdAndInviteCode(registerRequest.getInviteeId(),
                            registerRequest.getInviteCode());

            // 如果是第一次请求，返回成功，校验通过进入公司信息页面
            if(Objects.nonNull(registerRequest.getFirstRegisterFlag()) && !registerRequest.getFirstRegisterFlag()) {

                String account = commonUtil.getOperator().getAccount();
                if (!account.equals(registerRequest.getCustomerAccount())) {
                    throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
                }

                // 校验公司信息参数
                if (StringUtils.isBlank(registerRequest.getEnterpriseName()) || Objects.isNull(registerRequest.getBusinessNatureType())
                        || StringUtils.isBlank(registerRequest.getSocialCreditCode()) || StringUtils.isBlank(registerRequest.getBusinessLicenseUrl())) {
                    throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
                }

                CustomerDTO customer = new CustomerDTO();
                customer.setCustomerAccount(registerRequest.getCustomerAccount());
                customer.setCustomerPassword(registerRequest.getCustomerPassword());
                if (StringUtils.isNotBlank(registerRequest.getEmployeeId())) {
                    EmployeeByIdRequest idRequest = new EmployeeByIdRequest();
                    idRequest.setEmployeeId(registerRequest.getEmployeeId());
                    if (Objects.isNull(employeeQueryProvider.getById(idRequest).getContext())) {
                        registerRequest.setEmployeeId(null);
                    }
                }

                CustomerRegisterRequest customerRegisterRequest =
                        CustomerRegisterRequest.builder().employeeId(registerRequest.getEmployeeId()).customerDTO(customer)
                                .enterpriseName(registerRequest.getEnterpriseName()).businessNatureType(registerRequest.getBusinessNatureType())
                                .socialCreditCode(registerRequest.getSocialCreditCode()).businessLicenseUrl(registerRequest.getBusinessLicenseUrl())
                                .enterpriseCustomerAuditFlag(iepSettingVO.getEnterpriseCustomerAuditFlag()).build();

                BaseResponse<CustomerRegisterResponse> customerRegisterResponseBaseResponse = customerSiteProvider.registerEnterprise(customerRegisterRequest);
                CustomerVO customerVO = customerRegisterResponseBaseResponse.getContext();

                if (Objects.nonNull(customerVO)) {
                    log.info("register enterpriseCustomer success ......企业用户注册信息详情：{}", customerVO);

                    LoginResponse loginResponse = commonUtil.getLoginResponse(customerVO, jwtSecretKey);
                    // 根据审核开关状态判断
                    if (DefaultFlag.NO.equals(iepSettingVO.getEnterpriseCustomerAuditFlag())) {
                        // 无需审核
                        log.info("to home page ......");
                        loginResponse.setIsLoginFlag(Boolean.TRUE);
                        CustomerUpdateLoginTimeRequest updateLoginTimeRequest = new CustomerUpdateLoginTimeRequest();
                        updateLoginTimeRequest.setCustomerId(customerVO.getCustomerId());
                        customerSiteQueryProvider.updateLoginTime(updateLoginTimeRequest);

                        // 领取企业会员注册赠券
                        GetRegisterOrStoreCouponResponse couponResponse = this.getCouponGroup(customerVO.getCustomerId(), CouponActivityType.ENTERPRISE_REGISTERED_COUPON, Constant.BOSS_DEFAULT_STORE_ID);
                        //有注册赠券，则设置参数
                        if (couponResponse != null) {
//                            loginResponse.setCouponResponse(couponResponse);
                            redisService.setString(CacheKeyConstant.REGISTER_SEND_COUPON.concat(customerVO.getCustomerId()), customerVO.getCustomerId());
                            redisService.expireByMinutes(CacheKeyConstant.REGISTER_SEND_COUPON.concat(customerVO.getCustomerId()), 180L);
                        }
                    } else {
                        // 领取企业会员注册赠券 ,参加注册赠券活动
                        GetRegisterOrStoreCouponResponse couponResponse = this.getCouponGroup(customerVO.getCustomerId(), CouponActivityType.ENTERPRISE_REGISTERED_COUPON, Constant.BOSS_DEFAULT_STORE_ID);
//                        loginResponse.setCouponResponse(couponResponse);
                        log.info("information to be confirmed  ......");
                        loginResponse.setCustomerId(customerVO.getCustomerId());
                        loginResponse.setIsLoginFlag(Boolean.FALSE);
                    }

                    webBaseProducerService.sendMQForCustomerRegister(customerVO);

                    // 新增邀新记录/成长值/积分等
                    this.addOtherInfos(customerVO, distributionCustomerVO, registerRequest);

                    // ============= 处理平台的消息发送：新增企业客户注册待审核 START =============
                    storeMessageBizService.handleForEnterpriseAudit(customerVO);
                    // ============= 处理平台的消息发送：新增企业客户注册待审核 END =============

                    return BaseResponse.success(loginResponse);
                }
            }
        }

        log.info("register enterpriseCustomer failed ......");
        return BaseResponse.FAILED();
    }

    /**
     * 企业会员审核被驳回后再次注册
     * @param registerRequest
     * @param iepSettingVO
     * @return
     */
    public LoginResponse registerAgain(RegisterRequest registerRequest, IepSettingVO iepSettingVO) {

        // 校验公司信息参数
        if (StringUtils.isBlank(registerRequest.getEnterpriseName()) || Objects.isNull(registerRequest.getBusinessNatureType())
                || StringUtils.isBlank(registerRequest.getSocialCreditCode()) || StringUtils.isBlank(registerRequest.getBusinessLicenseUrl())) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }

        CustomerRegisterRequest customerRegisterRequest =
                CustomerRegisterRequest.builder().employeeId(registerRequest.getEmployeeId()).customerDTO(new CustomerDTO()).customerId(registerRequest.getCustomerId())
                        .enterpriseName(registerRequest.getEnterpriseName()).businessNatureType(registerRequest.getBusinessNatureType())
                        .socialCreditCode(registerRequest.getSocialCreditCode()).businessLicenseUrl(registerRequest.getBusinessLicenseUrl())
                        .enterpriseCustomerAuditFlag(iepSettingVO.getEnterpriseCustomerAuditFlag()).build();
        // 再次注册
        CustomerVO customerVO =
                customerSiteProvider.registerEnterpriseAgain(customerRegisterRequest).getContext();

        LoginResponse loginResponse = new LoginResponse();
        if(Objects.nonNull(customerVO)){
            loginResponse = commonUtil.getLoginResponse(customerVO, jwtSecretKey);
            if(DefaultFlag.NO.equals(customerRegisterRequest.getEnterpriseCustomerAuditFlag())){
                // 无需审核
                log.info("to home page ......");
                loginResponse.setIsLoginFlag(Boolean.TRUE);

            } else {
                log.info("information to be confirmed  ......");
                loginResponse.setCustomerId(customerVO.getCustomerId());
                loginResponse.setIsLoginFlag(Boolean.FALSE);
            }

            webBaseProducerService.sendMQForCustomerRegister(customerVO);

            // ============= 处理平台的消息发送：企业客户驳回后重新提交待审核 START =============
            storeMessageBizService.handleForEnterpriseAudit(customerVO);
            // ============= 处理平台的消息发送：企业客户驳回后重新提交待审核 END =============

        }
        return loginResponse;
    }

    /**
     * 新增邀新记录/成长值/积分等
     */
    public void addOtherInfos(CustomerVO customerVO, DistributionCustomerVO distributionCustomerVO,
                              RegisterRequest registerRequest){

        //新增分销员信息
        loginBaseService.addDistributionCustomer(customerVO.getCustomerId(),customerVO.getCustomerAccount(),
                customerVO.getCustomerDetail().getCustomerName(),StringUtils.isNotBlank(distributionCustomerVO.getInviteCustomerIds()) ?
                        StringUtils.join(distributionCustomerVO.getCustomerId(),",",StringUtils.split(distributionCustomerVO.getInviteCustomerIds(),",")[0]) :
                        distributionCustomerVO.getCustomerId()
        );

        // 新增邀新记录
        distributionInviteNewService.addRegisterInviteNewRecord(customerVO.getCustomerId(),
                distributionCustomerVO.getCustomerId(),commonUtil.getTerminal());

        // 增加成长值
        customerGrowthValueProvider.increaseGrowthValue(CustomerGrowthValueAddRequest.builder()
                .customerId(customerVO.getCustomerId())
                .type(OperateType.GROWTH)
                .serviceType(GrowthValueServiceType.REGISTER)
                .build());
        // 增加积分
        customerPointsDetailSaveProvider.add(CustomerPointsDetailAddRequest.builder()
                .customerId(customerVO.getCustomerId())
                .type(OperateType.GROWTH)
                .serviceType(PointsServiceType.REGISTER)
                .build());

        String shareUserId =  StringUtils.isNotBlank(registerRequest.getShareUserId()) ? registerRequest.getShareUserId() : distributionCustomerVO.getCustomerId();
        // 分享注册增加成长值积分
        if(StringUtils.isNotBlank(shareUserId)){
            customerGrowthValueProvider.increaseGrowthValue(
                    CustomerGrowthValueAddRequest.builder()
                            .customerId(shareUserId)
                            .type(OperateType.GROWTH)
                            .serviceType(GrowthValueServiceType.SHAREREGISTER)
                            .build());
            customerPointsDetailSaveProvider.add(CustomerPointsDetailAddRequest.builder()
                    .customerId(shareUserId)
                    .type(OperateType.GROWTH)
                    .serviceType(PointsServiceType.SHAREREGISTER)
                    .build());
        }

        //团长邀请注册逻辑处理
        if(StringUtils.isNotBlank(registerRequest.getLeaderId())
                && StringUtils.isNotBlank(registerRequest.getActivityId())){
            log.info("register community customer start");
            communityStatisticsCustomerProvider.add(CommunityStatisticsCustomerAddRequest.builder()
                    .leaderId(registerRequest.getLeaderId())
                    .activityId(registerRequest.getActivityId())
                    .customerId(customerVO.getCustomerId())
                    .build());
        }
    }

    /**
     * 会员登录
     *
     * @param loginRequest
     * @return LoginResponse
     */
    @Operation(summary = "会员登录")
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public BaseResponse<LoginResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        log.info("enter account&password login method ......");
        CustomerLoginRequest request = new CustomerLoginRequest();
        request.setCustomerAccount(new String(Base64.getUrlDecoder().decode(loginRequest.getCustomerAccount()
                .getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8));

        // 前端传参为base64编码后的password字符数组，要转为passwordChars字符数组处理
        char[] passwordChars = SecurityUtil.decodeAndWipeBase64Chars(loginRequest.getCustomerPassword());
        // 填充请求参数，调用微服务登录方法
        request.setPassword(passwordChars);
        BaseResponse<CustomerLoginResponse> customerLoginResponseBaseResponse = customerSiteQueryProvider.login(request);
        // 擦除passwordChars字符数组，避免敏感数据泄漏
        CharArrayUtils.wipe(passwordChars);

        CustomerLoginResponse customerLoginResponse = customerLoginResponseBaseResponse.getContext();
        LoginResponse loginResponse = LoginResponse.builder().build();
        if (Objects.nonNull(customerLoginResponse)) {
            CustomerVO customerVO = new CustomerVO();
            KsBeanUtil.copyPropertiesThird(customerLoginResponse, customerVO);
            //返回值
            loginResponse = commonUtil.getLoginResponse(customerVO, jwtSecretKey);
            loginResponse.setNewFlag(Boolean.FALSE);
            if(Objects.isNull(customerLoginResponse.getLoginTime())){
                loginResponse.setNewFlag(Boolean.TRUE);
            }
            //首次登录则封装用户注册券信息  注册券不在登录接口返回  所有端统一通过新增接口 来获取
            if(Boolean.TRUE.equals(customerLoginResponse.getFirstLoginFlag())) {

                redisService.setString(CacheKeyConstant.REGISTER_SEND_COUPON.concat(customerLoginResponse.getCustomerId()), customerLoginResponse.getCustomerId());
                redisService.expireByMinutes(CacheKeyConstant.REGISTER_SEND_COUPON.concat(customerLoginResponse.getCustomerId()), 180L);
//            loginResponse.setCouponResponse(couponResponse);
            }
        }
        // 处理数据
//        loginResponse.getCustomerDetail().setCustomerAddress(null);
//        loginResponse.getCustomerDetail().setContactPhone(null);
        log.info("login success ......");
        return BaseResponse.success(loginResponse);
    }


    /**
     * 获取用户注册优惠券信息
     *
     * @return GetRegisterOrStoreCouponResponse
     */
    @Operation(summary = "获取用户注册优惠券信息")
    @RequestMapping(value = "/registerCoupon", method = RequestMethod.POST)
    public BaseResponse<GetRegisterOrStoreCouponResponse> queryRegisterCoupon () {
        //获取用户ID
        String customerId = commonUtil.getOperator().getUserId();
        if (StringUtils.isEmpty(customerId)) {
            return BaseResponse.SUCCESSFUL();
        }
        String lockKey = CacheKeyConstant.REGISTER_SEND_COUPON_QUERY_LOCK.concat(customerId);
        GetRegisterOrStoreCouponResponse couponResponse = new GetRegisterOrStoreCouponResponse();

        RLock rLock = redissonClient.getFairLock(lockKey);
        rLock.lock();
        try {
            //缓存中是否有用户注册赠券信息
            String couponKey = CacheKeyConstant.REGISTER_SEND_COUPON.concat(customerId);
            if (StringUtils.isBlank(redisService.getString(couponKey))){
                return BaseResponse.SUCCESSFUL();
            }
            //获取用户信息
            CustomerGetByIdResponse response = customerQueryProvider.getCustomerById(CustomerGetByIdRequest.builder().customerId(customerId).build()).getContext();
            if (Objects.isNull(response)) {
                return BaseResponse.SUCCESSFUL();
            }

            //查询用户注册赠送的优惠券
            CouponActivityType couponActivityType = CouponActivityType.REGISTERED_COUPON;
            if (response.getEnterpriseCheckState().toValue() != EnterpriseCheckState.INIT.toValue()) {
                couponActivityType = CouponActivityType.ENTERPRISE_REGISTERED_COUPON;
            }
            couponResponse = couponActivityProvider.queryRegisterCoupon(
                    GetRegisterCouponRequest.builder().customerId(customerId).couponActivityType(couponActivityType).build())
                    .getContext();
            ///goodsDetailEvaluate/evaluatePageLogin
            redisService.delete(couponKey);
        } catch (Exception e) {
            log.info("registerCoupon e");
        } finally {
            rLock.unlock();
        }
        return BaseResponse.success(couponResponse);
    }


    /**
     * 查询单条会员信息
     *
     * @param customerId
     * @return
     */
    @Operation(summary = "查询单条会员信息")
    @RequestMapping(value = "/enterprise/customer/{customerId}", method = RequestMethod.GET)
    public BaseResponse<CustomerGetByIdResponse> findById(@PathVariable String customerId) {
        String loginCustomer = commonUtil.getOperatorId();
        if (Objects.nonNull(loginCustomer) && !StringUtils.equals(customerId,loginCustomer)){
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        CustomerGetByIdResponse customer =
                customerQueryProvider.getCustomerById(new CustomerGetByIdRequest(customerId)).getContext();
        //查询企业信息
        if(commonUtil.findVASBuyOrNot(VASConstants.VAS_IEP_SETTING)){
            BaseResponse<EnterpriseInfoByCustomerIdResponse> enterpriseInfo = enterpriseInfoQueryProvider.getByCustomerId(EnterpriseInfoByCustomerIdRequest.builder()
                    .customerId(customerId)
                    .build());
            if(Objects.nonNull(enterpriseInfo.getContext())){
                customer.setEnterpriseInfoVO(enterpriseInfo.getContext().getEnterpriseInfoVO());
            }
        }

        if (Objects.nonNull(customer)) {
            CustomerVO customerVO = new CustomerVO();
            KsBeanUtil.copyPropertiesThird(customer, customerVO);
            customer.setInviteCode(commonUtil.getLoginResponse(customerVO, jwtSecretKey).getInviteCode());
        }


        return BaseResponse.success(customer);
    }

    /**
     * 验证图片验证码
     *
     * @param registerRequest
     * @return
     */
    @Operation(summary = "验证图片验证码")
    @RequestMapping(value = "/patchca", method = RequestMethod.POST)
    public BaseResponse patchca(@RequestBody RegisterRequest registerRequest) {

        // 参数校验
        if (StringUtils.isAnyBlank(registerRequest.getPatchca(), registerRequest.getUuid())) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }

        if (registerRequest.getPatchca().equalsIgnoreCase(redisService.hget(CacheKeyConstant.KAPTCHA_KEY,
                registerRequest.getUuid()))) {
            return BaseResponse.SUCCESSFUL();
        }
        return BaseResponse.FAILED();
    }

    /**
     * 获取验证
     *
     * @param uuid
     * @return
     */
    @Operation(summary = "获取验证码")
    @Parameter(name = "uuid", description = "图片验证码key", required = true)
    @RequestMapping(value = "/patchca/{uuid}", method = RequestMethod.GET)
    public BaseResponse<String> patchca(@PathVariable("uuid") String uuid) {
        String code = captchaProducer.createText().toUpperCase();
        redisService.hset(CacheKeyConstant.KAPTCHA_KEY, uuid, code);
        ByteArrayOutputStream os = null;
        try {
            os = new ByteArrayOutputStream();
            ImageIO.write(captchaProducer.createImage(code), "png", os);
            return BaseResponse.success("data:image/png;base64,".concat(Base64.getEncoder().encodeToString(os
                    .toByteArray())));
        } catch (Exception ex) {
        } finally {
            if (os != null) {
                try {
                    os.close();
                } catch (Exception e) {
                }
            }
        }
        return BaseResponse.FAILED();
    }

    @Autowired
    private EsCustomerDetailProvider esCustomerDetailProvider;

    /**
     * 完善用户信息
     *
     * @param registerRequest
     * @return LoginResponse
     */
    @Operation(summary = "完善用户信息")
    @RequestMapping(value = "/perfect", method = RequestMethod.POST)
    public BaseResponse<LoginResponse> perfect(@RequestBody RegisterRequest registerRequest) {
        // 参数校验
        if (StringUtils.isAnyBlank(registerRequest.getCustomerId())) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }

        log.info("enter information improve perfect method ......");
        String customerId = commonUtil.getOperator().getUserId();
        if (!customerId.equals(registerRequest.getCustomerId())){
            return BaseResponse.error("非法越权操作");
        }
        //需要完善信息
        CustomerConsummateRegisterRequest customerConsummateRegisterRequest = new CustomerConsummateRegisterRequest();
        KsBeanUtil.copyProperties(registerRequest, customerConsummateRegisterRequest);
        BaseResponse<CustomerConsummateRegisterResponse> customerConsummateRegisterResponseBaseResponse = customerSiteProvider.registerConsummate(customerConsummateRegisterRequest);
        CustomerConsummateRegisterResponse customerConsummateRegisterResponse = customerConsummateRegisterResponseBaseResponse.getContext();
        if (Objects.nonNull(customerConsummateRegisterResponse)) {
            LoginResponse loginResponse = new LoginResponse();
            CustomerVO customerVO = new CustomerVO();
            KsBeanUtil.copyPropertiesThird(customerConsummateRegisterResponse, customerVO);

            //审核开关关闭并且信息完善开关关闭，则直接登录
            if (!auditQueryProvider.isCustomerAudit().getContext().isAudit()) {
                log.info("to home page ......");

                loginResponse = commonUtil.getLoginResponse(customerVO, jwtSecretKey);
                loginResponse.setIsLoginFlag(Boolean.TRUE);
                CustomerUpdateLoginTimeRequest updateLoginTimeRequest = new CustomerUpdateLoginTimeRequest();
                updateLoginTimeRequest.setCustomerId(customerVO.getCustomerId());
                customerSiteQueryProvider.updateLoginTime(updateLoginTimeRequest);


                // 领取注册赠券
                GetRegisterOrStoreCouponResponse couponResponse = this.getCouponGroup(customerVO.getCustomerId(), CouponActivityType.REGISTERED_COUPON, Constant.BOSS_DEFAULT_STORE_ID);
//                loginResponse.setCouponResponse(couponResponse);
                if(Objects.nonNull(couponResponse)) {
                    redisService.setString(CacheKeyConstant.REGISTER_SEND_COUPON.concat(customerVO.getCustomerId()), customerVO.getCustomerId());
                    redisService.expireByMinutes(CacheKeyConstant.REGISTER_SEND_COUPON.concat(customerVO.getCustomerId()), 180L);
                }
            } else {
                log.info("to information improve page ......");
                loginResponse.setCustomerId(customerConsummateRegisterResponse.getCustomerId());
                loginResponse.setIsLoginFlag(Boolean.FALSE);


                // 完善信息之后，尝试领取注册赠券
                if (customerConsummateRegisterResponse.getCheckState() == CheckState.WAIT_CHECK) {
                    // 领取注册赠券
                    GetRegisterOrStoreCouponResponse couponResponse = this.getCouponGroup(customerConsummateRegisterResponse.getCustomerId(), CouponActivityType.REGISTERED_COUPON, Constant.BOSS_DEFAULT_STORE_ID);
//                    loginResponse.setCouponResponse(couponResponse);
                }
            }

            //同步ES
            esCustomerDetailProvider.init(EsCustomerDetailInitRequest.builder().idList(Arrays.asList(customerVO.getCustomerId())).build());

            webBaseProducerService.sendMQForCustomerRegister(customerVO);

            // ============= 处理平台的消息发送：客户注册驳回后重新提交待审核 START =============
            storeMessageBizService.handleForCustomerAudit(customerVO);
            // ============= 处理平台的消息发送：客户注册驳回后重新提交待审核 END =============

            return BaseResponse.success(loginResponse);
        }
        return BaseResponse.FAILED();
    }

    /**
     * 检测用户名是否存在
     *
     * @return LoginResponse
     */
    @Operation(summary = "检测用户名是否存在")
    @RequestMapping(value = "/checkAccount", method = RequestMethod.POST)
    public BaseResponse<Integer> checkAccount(@RequestBody RegisterRequest registerRequest) {
        // 参数校验
        if (StringUtils.isAnyBlank(registerRequest.getCustomerAccount())) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }

        CustomerByAccountRequest request = new CustomerByAccountRequest();
        request.setCustomerAccount(registerRequest.getCustomerAccount());
        BaseResponse<CustomerByAccountResponse> responseBaseResponse = customerSiteQueryProvider.getCustomerByCustomerAccount(request);
        CustomerByAccountResponse response = responseBaseResponse.getContext();
        if (Objects.nonNull(response)) {
            return BaseResponse.success(Constants.yes);
        }
        return BaseResponse.success(Constants.no);
    }

    /**
     * 发送验证码(一户一码申请时)
     *
     * @param registerRequest
     * @return LoginResponse
     */
    @Operation(summary = "一户一码申请时发送验证码")
    @RequestMapping(value = "/checkSmsByAgentRegister", method = RequestMethod.POST)
    public BaseResponse checkSmsByAgentRegister(@RequestBody RegisterRequest registerRequest) {
        // 参数校验
        if (StringUtils.isAnyBlank(registerRequest.getCustomerAccount())) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }

        //验证输入的手机号码格式
        if (!ValidateUtil.isPhone(registerRequest.getCustomerAccount())) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        //同一个手机是否操作频繁
        if (!customerCacheService.validateSendMobileCode(registerRequest.getCustomerAccount())) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000016);
        }
        // 校验手机号是否注册一户一码
        GetAgentRequest getAgentRequest = GetAgentRequest.builder().contactPhone(registerRequest.getCustomerAccount()).build();
        BaseResponse<GetAgentResponse> getAgentResponse = agentQueryProvider.getAgentByContactPhone(getAgentRequest);

        //审核状态 0已创建 1待审核 2通过 3驳回
        if (getAgentResponse.getContext()!= null) {
            Integer auditStatus = getAgentResponse.getContext().getAuditStatus();
            if (auditStatus != null) {
                // 已通过（2），拦截
                if (auditStatus.equals(2)) {
                    throw new SbcRuntimeException(CommonErrorCodeEnum.K000027);
                }
            }
        }

        //删除验证错误次数
        redisService.delete(CacheKeyConstant.YZM_CUSTOMER_LOGIN_NUM.concat(registerRequest.getCustomerAccount()));
        //发送验证码
        if (Constants.yes.equals(customerCacheService.sendMobileCode(CacheKeyConstant.YZM_CUSTOMER_LOGIN,
                registerRequest.getCustomerAccount(), SmsTemplate.VERIFICATION_CODE))) {
            return BaseResponse.SUCCESSFUL();
        }
        return BaseResponse.FAILED();
    }

    /**
     * 发送验证码(注册时)
     *
     * @param registerRequest
     * @return LoginResponse
     */
    @Operation(summary = "注册时发送验证码")
    @RequestMapping(value = "/checkSmsByRegister", method = RequestMethod.POST)
    public BaseResponse checkSmsByRegister(@RequestBody RegisterRequest registerRequest) {
        // 参数校验
        if (StringUtils.isAnyBlank(registerRequest.getCustomerAccount())) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }

        //验证输入的手机号码格式
        if (!ValidateUtil.isPhone(registerRequest.getCustomerAccount())) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        //同一个手机是否操作频繁
        if (!customerCacheService.validateSendMobileCode(registerRequest.getCustomerAccount())) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000016);
        }
        CustomerByAccountRequest customerByAccountRequest = new CustomerByAccountRequest();
        customerByAccountRequest.setCustomerAccount(registerRequest.getCustomerAccount());
        BaseResponse<CustomerByAccountResponse> responseBaseResponse = customerSiteQueryProvider.getCustomerByCustomerAccount(customerByAccountRequest);
        CustomerByAccountResponse customerByAccountResponse = responseBaseResponse.getContext();
        if (customerByAccountResponse != null) {
            // 已注销的不校验
            if (LogOutStatus.LOGGED_OUT!=customerByAccountResponse.getLogOutStatus()){
                CustomerDetailVO vo = customerByAccountResponse.getCustomerDetail();
                if (Objects.nonNull(vo)) {
                    if (CustomerStatus.DISABLE.equals(vo.getCustomerStatus())) {
                        throw new SbcRuntimeException(CustomerErrorCodeEnum.K010004, new Object[]{"，原因为：" + Objects.toString(vo
                                .getForbidReason(),"")});
                    }
                }
                throw new SbcRuntimeException(CustomerErrorCodeEnum.K010021);
            }
        }
        //删除验证错误次数
        redisService.delete(CacheKeyConstant.REGISTER_ERR.concat(registerRequest.getCustomerAccount()));
        //发送验证码
        if (Constants.yes.equals(customerCacheService.sendMobileCode(CacheKeyConstant.VERIFY_CODE_KEY,
                registerRequest.getCustomerAccount(), SmsTemplate.VERIFICATION_CODE))) {
            //59秒
            //redisService.expireByMinutes(CacheKeyConstant.KAPTCHA_KEY.concat(registerRequest.getUuid()), 1L);
            return BaseResponse.SUCCESSFUL();
        }
        return BaseResponse.FAILED();
    }

    /**
     * 发送验证码（忘记密码时）
     *
     * @param registerRequest
     * @return LoginResponse
     */
    @Operation(summary = "忘记密码时发送验证码")
    @RequestMapping(value = "/checkSmsByForgot", method = RequestMethod.POST)
    public BaseResponse checkSmsByForgot(@RequestBody RegisterRequest registerRequest) {
        // 参数校验
        if (StringUtils.isAnyBlank(registerRequest.getCustomerAccount())) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }

        //是否可以发送
        if (!customerCacheService.validateSendMobileCode(registerRequest.getCustomerAccount())) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000016);
        }

        CustomerByAccountRequest request = new CustomerByAccountRequest();
        request.setCustomerAccount(registerRequest.getCustomerAccount());
        BaseResponse<CustomerByAccountResponse> responseBaseResponse = customerSiteQueryProvider.getCustomerByCustomerAccount(request);
        CustomerByAccountResponse response = responseBaseResponse.getContext();
        if (response == null) {
            throw new SbcRuntimeException(CustomerErrorCodeEnum.K010008);
        }

        CustomerDetailVO customerDetail = loginBaseService.findCustomerDetailByCustomerId(response.getCustomerId());
        if (customerDetail == null) {
            throw new SbcRuntimeException(CustomerErrorCodeEnum.K010008);
        }

        //是否禁用
        if (CustomerStatus.DISABLE.toValue() == customerDetail.getCustomerStatus().toValue()) {
            throw new SbcRuntimeException(CustomerErrorCodeEnum.K010004, new Object[]{"，原因为：" + customerDetail
                    .getForbidReason()});
        }

        //删除验证错误次数
        redisService.delete((registerRequest.getIsForgetPassword() != null && registerRequest.getIsForgetPassword() ?
                CacheKeyConstant.YZM_FORGET_PWD_KEY_NUM : CacheKeyConstant.YZM_UPDATE_PWD_KEY_NUM).concat(registerRequest.getCustomerAccount()));
        //发送验证码
        String redisKey = registerRequest.getIsForgetPassword() != null && registerRequest.getIsForgetPassword() ?
                CacheKeyConstant.YZM_FORGET_PWD_KEY : CacheKeyConstant.YZM_UPDATE_PWD_KEY;
        if (Constants.yes.equals(customerCacheService.sendMobileCode(redisKey, registerRequest.getCustomerAccount(),
                SmsTemplate.VERIFICATION_CODE))) {
            return BaseResponse.SUCCESSFUL();
        }
        return BaseResponse.FAILED();
    }

    /**
     * 验证验证码（忘记密码时）
     *
     * @param registerRequest
     * @return LoginResponse<客户编号>
     */
    @Operation(summary = "忘记密码时验证验证码")
    @RequestMapping(value = "/validateSmsByForgot", method = RequestMethod.POST)
    public BaseResponse<String> validateSmsByForgot(@RequestBody RegisterRequest registerRequest) {
        // 参数校验
        if (StringUtils.isAnyBlank(registerRequest.getCustomerAccount(), registerRequest.getVerifyCode())) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }

        //累计验证错误次数
        String errKey = registerRequest.getIsForgetPassword() != null && registerRequest
                .getIsForgetPassword() ? CacheKeyConstant.YZM_FORGET_PWD_KEY_NUM.concat(registerRequest
                .getCustomerAccount()) : CacheKeyConstant.YZM_UPDATE_PWD_KEY_NUM.concat(registerRequest
                .getCustomerAccount());
        String smsKey = registerRequest.getIsForgetPassword() != null && registerRequest
                .getIsForgetPassword() ? CacheKeyConstant.YZM_FORGET_PWD_KEY.concat(registerRequest
                .getCustomerAccount()) : CacheKeyConstant.YZM_UPDATE_PWD_KEY.concat(registerRequest
                .getCustomerAccount());
        // 校验短信验证码
        commonUtil.verifyCodeMsg(errKey, smsKey, registerRequest.getVerifyCode());
        CustomerByAccountRequest request = new CustomerByAccountRequest();
        request.setCustomerAccount(registerRequest.getCustomerAccount());
        BaseResponse<CustomerByAccountResponse> responseBaseResponse = customerSiteQueryProvider.getCustomerByCustomerAccount(request);
        CustomerByAccountResponse response = responseBaseResponse.getContext();
        if (Objects.isNull(response)) {
            throw new SbcRuntimeException(CustomerErrorCodeEnum.K010008);
        }
        CustomerDetailVO customerDetail = loginBaseService.findCustomerDetailByCustomerId(response.getCustomerId());
        if (customerDetail == null) {
            throw new SbcRuntimeException(CustomerErrorCodeEnum.K010008);
        }

        //是否禁用
        if (CustomerStatus.DISABLE.toValue() == customerDetail.getCustomerStatus().toValue()) {
            throw new SbcRuntimeException(CustomerErrorCodeEnum.K010004, new Object[]{"，原因为：" + customerDetail
                    .getForbidReason()});
        }

        return BaseResponse.success(response.getCustomerId());
    }

    /**
     * 修改验证码（忘记密码时）
     *
     * @param registerRequest
     * @return LoginResponse<客户编号>
     */
    @Operation(summary = "忘记密码时修改验证码")
    @RequestMapping(value = "/passwordByForgot", method = RequestMethod.POST)
    public BaseResponse passwordByForgot(@RequestBody RegisterRequest registerRequest) {
        // 参数校验
        if (StringUtils.isAnyBlank(registerRequest.getCustomerId(), registerRequest.getCustomerPassword(), registerRequest.getVerifyCode())) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        //校验弱密码处理
        weakPasswordsCheckUtil.weakPasswordsCheck(registerRequest.getCustomerPassword());

        CustomerGetByIdResponse customer = customerQueryProvider.getCustomerById(new CustomerGetByIdRequest(registerRequest
                .getCustomerId())).getContext();
        if (customer == null) {
            throw new SbcRuntimeException(CustomerErrorCodeEnum.K010008);
        }
        CustomerDetailVO customerDetail = loginBaseService.findCustomerDetailByCustomerId(registerRequest.getCustomerId());
        if (customerDetail == null) {
            throw new SbcRuntimeException(CustomerErrorCodeEnum.K010008);
        }

        //累计验证错误次数
        String errKey = registerRequest.getIsForgetPassword() != null && registerRequest
                .getIsForgetPassword() ? CacheKeyConstant.YZM_FORGET_PWD_KEY_NUM.concat(customer
                .getCustomerAccount()) : CacheKeyConstant.YZM_UPDATE_PWD_KEY_NUM.concat(customer
                .getCustomerAccount());
        String smsKey = registerRequest.getIsForgetPassword() != null && registerRequest
                .getIsForgetPassword() ? CacheKeyConstant.YZM_FORGET_PWD_KEY.concat(customer
                .getCustomerAccount()) : CacheKeyConstant.YZM_UPDATE_PWD_KEY.concat(customer
                .getCustomerAccount());
        // 校验短信验证码
        commonUtil.verifyCodeMsg(errKey, smsKey, registerRequest.getVerifyCode());

        //是否禁用
        if (CustomerStatus.DISABLE.toValue() == customerDetail.getCustomerStatus().toValue()) {
            throw new SbcRuntimeException(CustomerErrorCodeEnum.K010004, new Object[]{"，原因为：" + customerDetail
                    .getForbidReason()});
        }

        //
        customer.setCustomerPassword(registerRequest.getCustomerPassword());
        CustomerModifyRequest customerModifyRequest = new CustomerModifyRequest();
        KsBeanUtil.copyPropertiesThird(customer, customerModifyRequest);
        customerSiteProvider.modifyCustomerPwd(customerModifyRequest);
        //删除验证码缓存
        redisService.delete(registerRequest.getIsForgetPassword() != null && registerRequest.getIsForgetPassword() ?
                CacheKeyConstant.YZM_FORGET_PWD_KEY.concat(customer.getCustomerAccount()) : CacheKeyConstant
                .YZM_UPDATE_PWD_KEY.concat(customer.getCustomerAccount()));
        return BaseResponse.SUCCESSFUL();
    }

    /**
     * 验证TOKEN，实现自动登录
     *
     * @return LoginResponse
     */
    @Operation(summary = "验证TOKEN，实现自动登录")
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public BaseResponse<String> login() {
        String jwtHeaderKey = StringUtils.isNotBlank(jwtProperties.getJwtHeaderKey()) ? jwtProperties.getJwtHeaderKey
                () : "Authorization";
        String jwtHeaderPrefix = StringUtils.isNotBlank(jwtProperties.getJwtHeaderPrefix()) ? jwtProperties
                .getJwtHeaderPrefix() : "Bearer ";

        String authHeader = HttpUtil.getRequest().getHeader(jwtHeaderKey);
        if (StringUtils.isBlank(authHeader) || !authHeader.startsWith(jwtHeaderPrefix)) {
            return BaseResponse.FAILED();
        }

        //当token失效,直接返回失败
        String token = authHeader.substring(jwtHeaderPrefix.length());
        Claims claims = Jwts.parser().setSigningKey(this.jwtSecretKey).build().parseClaimsJws(token).getBody();
        if (claims == null) {
            return BaseResponse.FAILED();
        }
        log.info("验证TOKEN，实现自动登录,会员ID:{}",claims.get("customerId"));

        CustomerGetByIdResponse customer = customerQueryProvider.getCustomerById(new CustomerGetByIdRequest(String.valueOf(claims
                .get("customerId")))).getContext();
        //当用户不存在,直接返回失败
        if (customer == null) {
            return BaseResponse.FAILED();
        }

        log.info("验证TOKEN，实现自动登录,获取会员详情信息如下：{}",customer);

        //当用户被逻辑删除,直接返回失效token
        if (DeleteFlag.YES.toValue() == customer.getDelFlag().toValue()
                || LogOutStatus.LOGGING_OFF==customer.getLogOutStatus()
                || LogOutStatus.LOGGED_OUT==customer.getLogOutStatus()) {
            throw new SbcRuntimeException(CustomerErrorCodeEnum.K010017);
        }

        //当用户被禁用,直接返回失效token
        CustomerDetailVO detail = this.findAnyCustomerDetailByCustomerId(customer.getCustomerId());
        if (detail == null) {
            throw new SbcRuntimeException(CustomerErrorCodeEnum.K010004);
        }

        if (CustomerStatus.DISABLE.toValue() == detail.getCustomerStatus().toValue()) {
            throw new SbcRuntimeException(CustomerErrorCodeEnum.K010004, new Object[]{"，原因为：" + detail
                    .getForbidReason()});
        }

        Date date = new Date();
        Date expiration = claims.getExpiration();
        Map<String, String> vasList = redisService.hgetall(ConfigKey.VALUE_ADDED_SERVICES.toString());
        JwtBuilder builder = Jwts.builder().setSubject(customer.getCustomerAccount())
                .compressWith(CompressionCodecs.DEFLATE)
                .signWith(SignatureAlgorithm.HS256, jwtSecretKey)
                .setIssuedAt(date)
                .claim("customerId", customer.getCustomerId())
                .claim("customerAccount", customer.getCustomerAccount())
                .claim("customerName", customer.getCustomerDetail().getCustomerName())
                .claim("ip", customer.getLoginIp())
                .claim("terminalToken",String.valueOf(claims.get("terminalToken")))
                .claim("firstLogin", claims.get("firstLogin"))//是否首次登陆
                .claim(ConfigKey.VALUE_ADDED_SERVICES.toString(), JSONObject.toJSONString(vasList));

        //当超时时间与当前时间不足1个小时，自动加30分钟
        if (DateUtils.addHours(expiration, 1).after(date)) {
            expiration = DateUtils.addMinutes(expiration, 30);
        }
        token = builder.setExpiration(expiration).compact();
        // 缓存Token
        commonUtil.putLoginRedis(token);
        return BaseResponse.success(token);
    }

    /**
     * 发送验证码（使用验证码登录）
     *
     * @param customerAccount
     * @return LoginResponse
     */
    @Operation(summary = "使用验证码登录发送验证码")
    @Parameter(name = "customerAccount", description = "会员账号", required = true)
    @RequestMapping(value = "/login/verification/{customerAccount}", method = RequestMethod.POST)
    public BaseResponse sendLoginCode(@PathVariable("customerAccount") String customerAccount) {
        //验证输入的手机号码格式
        if (!ValidateUtil.isPhone(customerAccount)) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        //是否可以发送
        if (!customerCacheService.validateSendMobileCode(customerAccount)) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000016);
        }

        //账号是否注册
        CustomerByAccountRequest request = new CustomerByAccountRequest();
        request.setCustomerAccount(customerAccount);
        BaseResponse<CustomerByAccountResponse> responseBaseResponse = customerSiteQueryProvider.getCustomerByCustomerAccount(request);
        CustomerByAccountResponse response = responseBaseResponse.getContext();
        if (Objects.isNull(response)) {
            throw new SbcRuntimeException(CustomerErrorCodeEnum.K010008);
        }
        loginBaseService.validateAccountStatus(response);
        CustomerDetailVO customerDetail = loginBaseService.findCustomerDetailByCustomerId(response.getCustomerId());
        if (customerDetail == null) {
            throw new SbcRuntimeException(CustomerErrorCodeEnum.K010008);
        }

        //是否禁用
        if (CustomerStatus.DISABLE.toValue() == customerDetail.getCustomerStatus().toValue()) {
            throw new SbcRuntimeException(CustomerErrorCodeEnum.K010004, new Object[]{"，原因为：" + customerDetail
                    .getForbidReason()});
        }

        //删除验证错误次数
        redisService.delete(CacheKeyConstant.YZM_CUSTOMER_LOGIN_NUM.concat(customerAccount));
        //发送验证码
        if (Constants.yes.equals(customerCacheService.sendMobileCode(CacheKeyConstant.YZM_CUSTOMER_LOGIN,
                customerAccount, SmsTemplate.VERIFICATION_CODE))) {
            return BaseResponse.SUCCESSFUL();
        }
        return BaseResponse.FAILED();
    }

    /**
     * 验证发送的验证码
     * <p>
     * 使用验证码登录
     *
     * @param loginRequest
     * @return
     */
    @Operation(summary = "使用验证码登录验证发送的验证码并登录")
    @RequestMapping(value = "/login/verification", method = RequestMethod.POST)
    @MultiSubmit
    public BaseResponse<LoginResponse> loginWithVerificationCode(@Valid @RequestBody LoginVerificationCodeRequest
                                                                         loginRequest) {
        //验证手机号格式
        if (!ValidateUtil.isPhone(loginRequest.getCustomerAccount())) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        //验证验证码格式
        if (!ValidateUtil.isVerificationCode(loginRequest.getVerificationCode())) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }

        //账号是否注册
        CustomerByAccountRequest request = new CustomerByAccountRequest();
        request.setCustomerAccount(loginRequest.getCustomerAccount());
        BaseResponse<CustomerByAccountResponse> responseBaseResponse = customerSiteQueryProvider.getCustomerByCustomerAccount(request);
        CustomerByAccountResponse response = responseBaseResponse.getContext();
        if (Objects.isNull(response)) {
            throw new SbcRuntimeException(CustomerErrorCodeEnum.K010008);
        }

        CustomerDetailVO customerDetail = loginBaseService.findCustomerDetailByCustomerId(response.getCustomerId());
        if (customerDetail == null) {
            throw new SbcRuntimeException(CustomerErrorCodeEnum.K010008);
        }

        //是否禁用
        if (CustomerStatus.DISABLE.toValue() == customerDetail.getCustomerStatus().toValue()) {
            throw new SbcRuntimeException(CustomerErrorCodeEnum.K010004, new Object[]{"，原因为：" + customerDetail
                    .getForbidReason()});
        }

        //累计验证错误次数
        String errKey = CacheKeyConstant.YZM_CUSTOMER_LOGIN_NUM.concat(loginRequest.getCustomerAccount());
        // 校验及封装校验后请求参数
        String smsKey = CacheKeyConstant.YZM_CUSTOMER_LOGIN.concat(loginRequest.getCustomerAccount());
        // 校验短信验证码
        commonUtil.verifyCodeMsg(errKey, smsKey, loginRequest.getVerificationCode());

        //删除验证码缓存
        redisService.delete(CacheKeyConstant.YZM_CUSTOMER_LOGIN.concat(loginRequest.getCustomerAccount()));

        //返回值
        CustomerVO customerVO = new CustomerVO();
        KsBeanUtil.copyPropertiesThird(response, customerVO);
        LoginResponse loginResponse = commonUtil.getLoginResponse(customerVO, jwtSecretKey);
        CustomerVO customerDetailCustomerVO = customerDetail.getCustomerVO();
        if (Objects.nonNull(customerDetailCustomerVO)){
            customerDetailCustomerVO.setCustomerId(null);
            customerDetailCustomerVO.setCustomerPassword(null);
            customerDetailCustomerVO.setCustomerPayPassword(null);
            customerDetailCustomerVO.setCustomerSaltVal(null);
        }
        EnterpriseInfoVO enterpriseInfoVO = loginResponse.getEnterpriseInfoVO();
        if (Objects.nonNull(enterpriseInfoVO)){
            enterpriseInfoVO.setCustomerId(null);
        }
        //前端获取不到customerId，分享店铺功能不可用，暂时放开
//        loginResponse.setCustomerId(null);
        loginResponse.setCustomerDetail(customerDetail);
        return BaseResponse.success(loginResponse);
    }

    @PostMapping(value = "/get/temporaryCode")
    public BaseResponse<String> temporaryCode(){
        return BaseResponse.success(loginBaseService.getTemporaryCode());
    }

    @PostMapping(value = "/with/temporaryCode")
    public BaseResponse<LoginResponse> loginWithTemporaryCode(@Valid @RequestBody LoginByTemporaryCodeRequest
                                                                         loginByTemporaryCodeRequest) {
        Map<String, String> map = redisService.hgetall(loginByTemporaryCodeRequest.getTemporaryCode());
        if (map.isEmpty()) throw new SbcRuntimeException(CommonErrorCodeEnum.K000001);

        String customerAccount = map.get(LoginBaseService.CUSTOMER_ACCOUNT);
        String verifyCode = map.get(LoginBaseService.VERIFY_CODE);
        //验证手机号格式
        if (!ValidateUtil.isPhone(customerAccount)) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        //验证验证码格式
        if (!ValidateUtil.isVerificationCode(verifyCode)) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }

        //账号是否注册
        CustomerByAccountRequest request = new CustomerByAccountRequest();
        request.setCustomerAccount(customerAccount);
        BaseResponse<CustomerByAccountResponse> responseBaseResponse = customerSiteQueryProvider.getCustomerByCustomerAccount(request);
        CustomerByAccountResponse response = responseBaseResponse.getContext();
        if (Objects.isNull(response)) {
            throw new SbcRuntimeException(CustomerErrorCodeEnum.K010008);
        }

        CustomerDetailVO customerDetail = loginBaseService.findCustomerDetailByCustomerId(response.getCustomerId());
        if (customerDetail == null) {
            throw new SbcRuntimeException(CustomerErrorCodeEnum.K010008);
        }

        //是否禁用
        if (CustomerStatus.DISABLE.toValue() == customerDetail.getCustomerStatus().toValue()) {
            throw new SbcRuntimeException(CustomerErrorCodeEnum.K010004, new Object[]{"，原因为：" + customerDetail
                    .getForbidReason()});
        }

        //累计验证错误次数
        String errKey = CacheKeyConstant.YZM_CUSTOMER_LOGIN_NUM.concat(customerAccount);
        // 校验及封装校验后请求参数
        String smsKey = CacheKeyConstant.YZM_CUSTOMER_LOGIN.concat(customerAccount);
        // 校验短信验证码
        commonUtil.verifyCodeMsgLakala(errKey, smsKey, verifyCode);

        //删除验证码缓存
        redisService.delete(CacheKeyConstant.YZM_CUSTOMER_LOGIN.concat(customerAccount));

        //返回值
        CustomerVO customerVO = new CustomerVO();
        KsBeanUtil.copyPropertiesThird(response, customerVO);
        LoginResponse loginResponse = commonUtil.getLoginResponse(customerVO, jwtSecretKey);
        CustomerVO customerDetailCustomerVO = customerDetail.getCustomerVO();
        if (Objects.nonNull(customerDetailCustomerVO)){
            customerDetailCustomerVO.setCustomerId(null);
            customerDetailCustomerVO.setCustomerPassword(null);
            customerDetailCustomerVO.setCustomerPayPassword(null);
            customerDetailCustomerVO.setCustomerSaltVal(null);
        }
        EnterpriseInfoVO enterpriseInfoVO = loginResponse.getEnterpriseInfoVO();
        if (Objects.nonNull(enterpriseInfoVO)){
            enterpriseInfoVO.setCustomerId(null);
        }
        //前端获取不到customerId，分享店铺功能不可用，暂时放开
//        loginResponse.setCustomerId(null);
        loginResponse.setCustomerDetail(customerDetail);
        return BaseResponse.success(loginResponse);
    }

    /**
     * 根据会员获取
     *
     * @param customerId
     * @return
     */

    /**
     * 根据会员获取
     *
     * @param customerId
     * @return
     */
    private CustomerDetailVO findAnyCustomerDetailByCustomerId(String customerId) {
        return customerDetailQueryProvider.getCustomerDetailByCustomerId(
                CustomerDetailByCustomerIdRequest.builder().customerId(customerId).build()).getContext();
    }


    /**
     * 访问是否需要登录
     *
     * @return
     */
    @Operation(summary = "查询访问是否需要登录")
    @Cacheable(value = CacheConstants.GLOBAL_CACHE_NAME, key = "'isVisitWithLogin'")
    @RequestMapping(value = "/userSetting/isVisitWithLogin", method = RequestMethod.POST)
    public BaseResponse<UserAuditResponse> isVisitWithLogin() {
        UserAuditResponse userAuditResponse = new UserAuditResponse();
        userAuditResponse.setAudit(auditQueryProvider.getIsVisitWithLogin().getContext().isAudit());
        return BaseResponse.success(userAuditResponse);
    }


    /**
     * 弹框里的注册发送验证码
     *
     * @param customerAccount
     * @return
     */
    @Operation(summary = "开放访问注册时发送验证码")
    @Parameter(name = "customerAccount", description = "会员账号", required = true)
    @RequestMapping(value = "/checkSmsByRegister/web/modal/{customerAccount}", method = RequestMethod.POST)
    public BaseResponse checkSmsByWebModalRegister(@PathVariable("customerAccount") String customerAccount) {
        //验证输入的手机号码格式
        if (!ValidateUtil.isPhone(customerAccount)) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        //同一个手机是否操作频繁
        if (!customerCacheService.validateSendMobileCode(customerAccount)) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000016);
        }
        //账号是否注册
        CustomerByAccountRequest request = new CustomerByAccountRequest();
        request.setCustomerAccount(customerAccount);
        BaseResponse<CustomerByAccountResponse> responseBaseResponse = customerSiteQueryProvider.getCustomerByCustomerAccount(request);
        CustomerByAccountResponse customer = responseBaseResponse.getContext();
        if (customer != null) {
            // 已注销的不校验
            if (LogOutStatus.LOGGED_OUT!=customer.getLogOutStatus()){
                CustomerDetailVO customerDetail = customer.getCustomerDetail();
                if (customerDetail != null) {
                    if (CustomerStatus.DISABLE.equals(customerDetail.getCustomerStatus())) {
                        throw new SbcRuntimeException(CustomerErrorCodeEnum.K010004, new Object[]{"，原因为：" + Objects.toString(customerDetail
                                .getForbidReason(), "")});
                    }
                }
                throw new SbcRuntimeException(CustomerErrorCodeEnum.K010021);
            }
        }
        //删除验证错误次数
        redisService.delete(CacheKeyConstant.REGISTER_ERR.concat(customerAccount));
        //发送验证码
        if (Constants.yes.equals(customerCacheService.sendMobileCode(CacheKeyConstant.REGISTER_MODAL_CODE, customerAccount,
                SmsTemplate.VERIFICATION_CODE))) {
            return BaseResponse.SUCCESSFUL();
        }
        return BaseResponse.FAILED();
    }


    /**
     * 弹框里的注册
     *
     * @param registerRequest
     * @return
     */
    @Operation(summary = "开放访问注册")
    @RequestMapping(value = "/register/modal", method = RequestMethod.POST)
    @GlobalTransactional
    public BaseResponse<LoginResponse> registerModal(@RequestBody RegisterRequest registerRequest) {
        log.info("enter register method ......");

        // 参数校验
        if (StringUtils.isAnyBlank(registerRequest.getCustomerAccount(), registerRequest.getCustomerPassword(), registerRequest.getVerifyCode())) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }

        //累计验证错误次数
        String errKey = CacheKeyConstant.REGISTER_ERR.concat(registerRequest.getCustomerAccount());
        String smsKey = CacheKeyConstant.REGISTER_MODAL_CODE.concat(registerRequest.getCustomerAccount());
        commonUtil.verifyCodeMsg(errKey, smsKey, registerRequest.getVerifyCode());

        //验证邀请ID、邀请码是否正确
        DistributionCustomerVO distributionCustomerVO = loginBaseService.checkInviteIdAndInviteCode(registerRequest.getInviteeId(),registerRequest.getInviteCode());

        CustomerDTO customer = new CustomerDTO();
        customer.setCustomerAccount(registerRequest.getCustomerAccount());
        customer.setCustomerPassword(registerRequest.getCustomerPassword());
        if (StringUtils.isNotBlank(registerRequest.getEmployeeId())) {
            EmployeeByIdResponse employee = employeeQueryProvider.getById(
                    EmployeeByIdRequest.builder().employeeId(registerRequest.getEmployeeId()).build()
            ).getContext();
            if (Objects.isNull(employee)) {
                registerRequest.setEmployeeId(null);
            }
        }

        CustomerRegisterRequest customerRegisterRequest = new CustomerRegisterRequest();
        customerRegisterRequest.setEmployeeId(registerRequest.getEmployeeId());
        customerRegisterRequest.setCustomerDTO(customer);
        //弱密码校验
        weakPasswordsCheckUtil.weakPasswordsCheck(customer.getCustomerPassword());
        BaseResponse<CustomerRegisterResponse> customerRegisterResponseBaseResponse = customerSiteProvider.register(customerRegisterRequest);
        CustomerVO customerVO = customerRegisterResponseBaseResponse.getContext();

        if (customerVO != null) {
            log.info("register customer success ......注册信息详情：{}",customerVO);
            //删除验证码缓存
            redisService.delete(smsKey);
            LoginResponse loginResponse = commonUtil.getLoginResponse(customerVO, jwtSecretKey);
            //审核开关关闭并且信息完善开关关闭，则直接登录
            boolean isPerfectCustomer = auditQueryProvider.isPerfectCustomerInfo().getContext().isPerfect();
            boolean isCustomerAudit = auditQueryProvider.isCustomerAudit().getContext().isAudit();
            if (!isPerfectCustomer && !isCustomerAudit) {
                log.info("to home page ......");
                loginResponse.setIsLoginFlag(Boolean.TRUE);
                CustomerUpdateLoginTimeRequest updateLoginTimeRequest = new CustomerUpdateLoginTimeRequest();
                updateLoginTimeRequest.setCustomerId(customerVO.getCustomerId());
                customerSiteQueryProvider.updateLoginTime(updateLoginTimeRequest);
                // 领取注册赠券
                GetRegisterOrStoreCouponResponse couponResponse = this.getCouponGroup(customerVO.getCustomerId(), CouponActivityType.REGISTERED_COUPON, Constant.BOSS_DEFAULT_STORE_ID);
                //有注册赠券，则设置参数
                if (Objects.nonNull(couponResponse)) {
//                    loginResponse.setCouponResponse(couponResponse);
                    redisService.setString(CacheKeyConstant.REGISTER_SEND_COUPON.concat(customerVO.getCustomerId()), customerVO.getCustomerId());
                    redisService.expireByMinutes(CacheKeyConstant.REGISTER_SEND_COUPON.concat(customerVO.getCustomerId()), 180L);
                }
            } else {
                // 领取注册赠券 ,如果完善信息开关打开则不能参加注册赠券活动
                if (!isPerfectCustomer) {
                    GetRegisterOrStoreCouponResponse couponResponse = this.getCouponGroup(customerVO.getCustomerId(), CouponActivityType.REGISTERED_COUPON, Constant.BOSS_DEFAULT_STORE_ID);
                    loginResponse.setCouponResponse(couponResponse);
                }
                log.info("to information improve page ......");
                loginResponse.setCustomerId(customerVO.getCustomerId());
                loginResponse.setIsLoginFlag(Boolean.FALSE);
            }

            webBaseProducerService.sendMQForCustomerRegister(customerVO);


            // 新增邀新记录/成长值/积分等
            this.addOtherInfos(customerVO, distributionCustomerVO, registerRequest);

            return BaseResponse.success(loginResponse);
        }
        log.info("register customer failed ......");
        return BaseResponse.FAILED();
    }

    /**
     * 查询注册赠券
     *
     * @param customerId
     * @param type
     * @param storeId
     * @return
     */
    GetRegisterOrStoreCouponResponse getCouponGroup(String customerId, CouponActivityType type, Long storeId) {
        GetCouponGroupRequest getCouponGroupRequest = new GetCouponGroupRequest();
        getCouponGroupRequest.setCustomerId(customerId);
        getCouponGroupRequest.setType(type);
        getCouponGroupRequest.setStoreId(storeId);
        return couponActivityProvider.getCouponGroup(getCouponGroupRequest).getContext();
    }

    /**
     * 注册验证
     * @param registerRequest
     * @return
     */
    @Operation(summary = "注册验证")
    @RequestMapping(value = "/register/check", method = RequestMethod.POST)
    public BaseResponse checkRegister(@RequestBody RegisterCheckRequest registerRequest){
        //累计验证错误次数
        String errKey = CacheKeyConstant.REGISTER_ERR.concat(registerRequest.getCustomerAccount());
        String smsKey = CacheKeyConstant.REGISTER_MODAL_CODE.concat(registerRequest.getCustomerAccount());
        if (registerRequest.getFromPage().equals(NumberUtils.INTEGER_ZERO)){
            smsKey = CacheKeyConstant.VERIFY_CODE_KEY.concat(registerRequest.getCustomerAccount());
        }
        commonUtil.verifyCodeMsg(errKey, smsKey, registerRequest.getVerifyCode());

        customerSiteQueryProvider.checkByAccount(new CustomerCheckByAccountRequest(registerRequest.getCustomerAccount()));
        return BaseResponse.SUCCESSFUL();
    }

    /**
     * 获取注册限制、是否开启分销
     * @return
     */
    @Operation(summary = "获取注册限制、是否开启分销")
    @RequestMapping(value = "/getRegisterLimitType", method = RequestMethod.POST)
    public BaseResponse<RegisterResponse> getRegisterLimitType() {
        RegisterLimitType registerLimitType = distributionCacheService.getRegisterLimitType();
        DefaultFlag openFlag = distributionCacheService.queryOpenFlag();
        DefaultFlag inviteOpenFlag = distributionCacheService.queryInviteOpenFlag();
        return BaseResponse.success(new RegisterResponse(registerLimitType,openFlag,inviteOpenFlag));
    }


    /**
     * 登出
     *
     * @param request
     */
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    @Operation(summary = "登出接口，会员登出")
    public BaseResponse logout(HttpServletRequest request) {
        String token = commonUtil.getToken(request);
        commonUtil.deleteLoginRedis(token);
        return BaseResponse.SUCCESSFUL();
    }


    /**
     * 验证唯一码是否有效
     *
     * @param agentUniqueCode
     * @return
     */
    @Operation(summary = "验证唯一码是否有效")
    @Parameter(name = "agentUniqueCode", description = "唯一码", required = true)
    @RequestMapping(value = "/queryVaildAgentByUniqueCode/{agentUniqueCode}", method = RequestMethod.POST)
    public BaseResponse<AgentGetByUniqueCodeResponse> queryVaildAgentByUniqueCode(@PathVariable("agentUniqueCode") String agentUniqueCode) {

        // 参数校验
        if (StringUtils.isBlank(agentUniqueCode)) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        AgentGetByUniqueCodeRequest agentGetByUniqueCodeRequest = new AgentGetByUniqueCodeRequest();
        agentGetByUniqueCodeRequest.setAgentUniqueCode(agentUniqueCode);
        BaseResponse<AgentGetByUniqueCodeResponse> responseBaseResponse = agentQueryProvider.queryVaildAgentByUniqueCode(agentGetByUniqueCodeRequest);
        if (!responseBaseResponse.isSuccess()) {
            return BaseResponse.error(responseBaseResponse.getMessage());
        }

        return BaseResponse.success(responseBaseResponse.getContext());
    }
}
