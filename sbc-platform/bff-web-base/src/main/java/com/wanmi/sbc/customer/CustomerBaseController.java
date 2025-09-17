package com.wanmi.sbc.customer;

import com.alibaba.fastjson2.JSONObject;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.enums.OperateType;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.redis.CacheKeyConstant;
import com.wanmi.sbc.common.redis.util.RedisUtil;
import com.wanmi.sbc.common.util.*;
import com.wanmi.sbc.customer.api.constant.CustomerLabel;
import com.wanmi.sbc.customer.api.provider.customer.CustomerProvider;
import com.wanmi.sbc.customer.api.provider.customer.CustomerQueryProvider;
import com.wanmi.sbc.customer.api.provider.detail.CustomerDetailProvider;
import com.wanmi.sbc.customer.api.provider.detail.CustomerDetailQueryProvider;
import com.wanmi.sbc.customer.api.provider.employee.EmployeeQueryProvider;
import com.wanmi.sbc.customer.api.provider.enterpriseinfo.EnterpriseInfoQueryProvider;
import com.wanmi.sbc.customer.api.provider.growthvalue.CustomerGrowthValueProvider;
import com.wanmi.sbc.customer.api.provider.growthvalue.CustomerGrowthValueQueryProvider;
import com.wanmi.sbc.customer.api.provider.level.CustomerLevelQueryProvider;
import com.wanmi.sbc.customer.api.provider.points.CustomerPointsDetailQueryProvider;
import com.wanmi.sbc.customer.api.provider.points.CustomerPointsDetailSaveProvider;
import com.wanmi.sbc.customer.api.request.customer.CustomerAccountModifyRequest;
import com.wanmi.sbc.customer.api.request.customer.CustomerGetByIdRequest;
import com.wanmi.sbc.customer.api.request.customer.CustomerPointsAvailableByIdRequest;
import com.wanmi.sbc.customer.api.request.customer.NoDeleteCustomerGetByAccountRequest;
import com.wanmi.sbc.customer.api.request.detail.CustomerDetailByCustomerIdRequest;
import com.wanmi.sbc.customer.api.request.detail.CustomerDetailModifyRequest;
import com.wanmi.sbc.customer.api.request.employee.EmployeeOptionalByIdRequest;
import com.wanmi.sbc.customer.api.request.enterpriseinfo.EnterpriseInfoByCustomerIdRequest;
import com.wanmi.sbc.customer.api.request.growthvalue.CustomerGrowthValueAddRequest;
import com.wanmi.sbc.customer.api.request.growthvalue.CustomerGrowthValuePageRequest;
import com.wanmi.sbc.customer.api.request.level.CustomerLevelWithDefaultByIdRequest;
import com.wanmi.sbc.customer.api.request.loginregister.CustomerSendMobileCodeRequest;
import com.wanmi.sbc.customer.api.request.points.CustomerPointsDetailAddRequest;
import com.wanmi.sbc.customer.api.request.points.CustomerPointsDetailQueryRequest;
import com.wanmi.sbc.customer.api.response.customer.CustomerGetByIdResponse;
import com.wanmi.sbc.customer.api.response.customer.CustomerPointsAvailableByCustomerIdResponse;
import com.wanmi.sbc.customer.api.response.customer.GrowthValueAndPointResponse;
import com.wanmi.sbc.customer.api.response.customer.NoDeleteCustomerGetByAccountResponse;
import com.wanmi.sbc.customer.api.response.detail.CustomerDetailGetCustomerIdResponse;
import com.wanmi.sbc.customer.api.response.employee.EmployeeOptionalByIdResponse;
import com.wanmi.sbc.customer.api.response.enterpriseinfo.EnterpriseInfoByCustomerIdResponse;
import com.wanmi.sbc.customer.bean.enums.*;
import com.wanmi.sbc.customer.bean.vo.CustomerDetailVO;
import com.wanmi.sbc.customer.bean.vo.CustomerLevelVO;
import com.wanmi.sbc.customer.bean.vo.CustomerVO;
import com.wanmi.sbc.customer.bean.vo.EnterpriseInfoVO;
import com.wanmi.sbc.customer.request.CustomerBaseInfoRequest;
import com.wanmi.sbc.customer.request.CustomerMobileRequest;
import com.wanmi.sbc.customer.api.request.customer.EditHeadImgRequest;
import com.wanmi.sbc.customer.response.CustomerBaseInfoResponse;
import com.wanmi.sbc.customer.response.CustomerCenterResponse;
import com.wanmi.sbc.customer.response.CustomerSafeResponse;
import com.wanmi.sbc.customer.service.CustomerCacheService;
import com.wanmi.sbc.elastic.api.provider.customer.EsDistributionCustomerProvider;
import com.wanmi.sbc.elastic.api.request.customer.EsDistributionCustomerModifyRequest;
import com.wanmi.sbc.mq.producer.WebBaseProducerService;
import com.wanmi.sbc.setting.api.provider.systemconfig.SystemConfigQueryProvider;
import com.wanmi.sbc.setting.api.request.ConfigQueryRequest;
import com.wanmi.sbc.setting.bean.enums.ConfigType;
import com.wanmi.sbc.setting.bean.vo.ConfigVO;
import com.wanmi.sbc.util.CommonUtil;
import com.wanmi.sbc.vas.bean.vo.IepSettingVO;
import io.seata.spring.annotation.GlobalTransactional;


import io.swagger.v3.oas.annotations.Parameter;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;
import java.util.ArrayList;
import java.util.Objects;
import java.util.regex.Pattern;

/**
 * 客户信息
 * Created by CHENLI on 2017/7/11.
 */
@RestController
@Validated
@RequestMapping("/customer")
@Tag(name = "CustomerBaseController", description = "S2B web公用-客户信息API")
public class CustomerBaseController {
    @Autowired
    private CustomerQueryProvider customerQueryProvider;

    @Autowired
    private CustomerProvider customerProvider;

    @Autowired
    private CustomerDetailProvider customerDetailProvider;

    @Autowired
    private CustomerDetailQueryProvider customerDetailQueryProvider;

    @Autowired
    private CustomerLevelQueryProvider customerLevelQueryProvider;

    @Autowired
    private EmployeeQueryProvider employeeQueryProvider;

    @Autowired
    private CustomerPointsDetailSaveProvider customerPointsDetailSaveProvider;

    @Autowired
    private CustomerGrowthValueProvider customerGrowthValueProvider;

    @Autowired
    private RedisUtil redisService;

    @Autowired
    private CommonUtil commonUtil;

    @Autowired
    private SystemConfigQueryProvider systemConfigQueryProvider;

    @Autowired
    private EnterpriseInfoQueryProvider enterpriseInfoQueryProvider;
    @Autowired
    private CustomerGrowthValueQueryProvider customerGrowthValueQueryProvider;
    @Autowired
    private CustomerPointsDetailQueryProvider customerPointsDetailQueryProvider;

    @Autowired
    private WebBaseProducerService webBaseProducerService;

    @Autowired
    private CustomerCacheService customerCacheService;

    @Autowired
    private EsDistributionCustomerProvider esDistributionCustomerProvider;

    /**
     * 查询会员基本信息数据
     *
     * @return
     */
    @Operation(summary = "查询会员基本信息数据")
    @RequestMapping(value = "/customerBase", method = RequestMethod.GET)
    public BaseResponse<CustomerBaseInfoResponse> findCustomerBaseInfo() {
        String customerId = commonUtil.getOperatorId();
        CustomerVO customer =
                customerQueryProvider.getCustomerById(new CustomerGetByIdRequest(customerId)).getContext();

        CustomerLevelVO customerLevel = customerLevelQueryProvider.getCustomerLevelWithDefaultById(
                CustomerLevelWithDefaultByIdRequest.builder().customerLevelId(customer.getCustomerLevelId()).build())
                .getContext();

        EmployeeOptionalByIdRequest idRequest = new EmployeeOptionalByIdRequest();
        idRequest.setEmployeeId(customer.getCustomerDetail().getEmployeeId());

        EmployeeOptionalByIdResponse employee = new EmployeeOptionalByIdResponse();

        if (StringUtils.isNotBlank(customer.getCustomerDetail().getEmployeeId())) {
            employee = employeeQueryProvider.getOptionalById(idRequest).getContext();
        }

        //企业信息
        Boolean isEnterpriseCustomer = !EnterpriseCheckState.INIT.equals(customer.getEnterpriseCheckState());
        EnterpriseInfoVO enterpriseInfo=null;
        if(isEnterpriseCustomer){
            BaseResponse<EnterpriseInfoByCustomerIdResponse> enterpriseInfoResponse = enterpriseInfoQueryProvider.getByCustomerId(EnterpriseInfoByCustomerIdRequest.builder()
                    .customerId(customerId)
                    .build());
            if(Objects.nonNull(enterpriseInfoResponse.getContext())){
                enterpriseInfo=enterpriseInfoResponse.getContext().getEnterpriseInfoVO();
            }
        }

        return BaseResponse.success(CustomerBaseInfoResponse.builder()
                .customerDetailId(customer.getCustomerDetail().getCustomerDetailId())
                .customerId(customerId)
                .customerAccount(customer.getCustomerAccount())
                .customerName(customer.getCustomerDetail().getCustomerName())
                .customerLevelName(customerLevel.getCustomerLevelName())
                .provinceId(customer.getCustomerDetail().getProvinceId())
                .cityId(customer.getCustomerDetail().getCityId())
                .areaId(customer.getCustomerDetail().getAreaId())
                .streetId(customer.getCustomerDetail().getStreetId())
                .customerAddress(customer.getCustomerDetail().getCustomerAddress())
                .contactName(customer.getCustomerDetail().getContactName())
                .contactPhone(customer.getCustomerDetail().getContactPhone())
                .employeeName(Objects.isNull(employee) ? null : employee.getEmployeeName())
                .birthDay(customer.getCustomerDetail().getBirthDay())
                .gender(customer.getCustomerDetail().getGender())
                .headImg(customer.getHeadImg())
                .isEnterpriseCustomer(isEnterpriseCustomer)
                .enterpriseInfo(enterpriseInfo)
                .build()
        );
    }

    /**
     * 修改会员基本信息
     *
     * @param customerEditRequest
     * @return
     */
    @Operation(summary = "修改会员基本信息")
    @RequestMapping(value = "/customerBase", method = RequestMethod.PUT)
    @GlobalTransactional
    public BaseResponse updateCustomerBaseInfo(@Valid @RequestBody CustomerBaseInfoRequest customerEditRequest) {
        if (StringUtils.isEmpty(customerEditRequest.getCustomerId())) {
            return BaseResponse.error("参数不正确");
        }
        CustomerDetailByCustomerIdRequest idRequest = new CustomerDetailByCustomerIdRequest(commonUtil.getOperatorId());
        BaseResponse<CustomerDetailGetCustomerIdResponse> detailByCustomerId = customerDetailQueryProvider.getCustomerDetailByCustomerId(idRequest);
        CustomerDetailGetCustomerIdResponse detail = detailByCustomerId.getContext();
        if(Objects.isNull(detail) || StringUtils.isBlank(detail.getCustomerDetailId())){
            return BaseResponse.error("非法越权操作");
        }

        //防止越权
        if (!commonUtil.getOperatorId().equals(customerEditRequest.getCustomerId()) || !detail.getCustomerDetailId().equals(customerEditRequest.getCustomerDetailId())) {
            return BaseResponse.error("非法越权操作");
        }



        //”基本信息->联系方式”中的联系方式，在前端进行了格式校验，后端没有校验
        if (StringUtils.isNotEmpty(customerEditRequest.getContactPhone()) &&
                !Pattern.matches(CommonUtil.REGEX_MOBILE, customerEditRequest.getContactPhone())) {
            return BaseResponse.error("手机号格式不正确");
        }

        CustomerDetailModifyRequest modifyRequest = new CustomerDetailModifyRequest();
        KsBeanUtil.copyProperties(customerEditRequest, modifyRequest);
        customerDetailProvider.modifyCustomerDetail(modifyRequest);

        webBaseProducerService.sendMQForModifyBaseInfo(modifyRequest);

        esDistributionCustomerProvider.modify(EsDistributionCustomerModifyRequest.builder()
                .customerId(modifyRequest.getCustomerId())
                .customerName(modifyRequest.getCustomerName())
                .build());
        if (customerEditRequest.getAreaId() != null && customerEditRequest.getCustomerAddress() != null
                && customerEditRequest.getBirthDay() != null && customerEditRequest.getGender() != null) {
            // 完善信息积分和成长值都没有获取过才考虑新增
            if (CollectionUtils.isEmpty(
                            customerGrowthValueQueryProvider
                                    .page(
                                            CustomerGrowthValuePageRequest.builder()
                                                    .growthValueServiceType(
                                                            GrowthValueServiceType.PERFECTINFO)
                                                    .customerId(customerEditRequest.getCustomerId())
                                                    .type(OperateType.GROWTH)
                                                    .build())
                                    .getContext()
                                    .getCustomerGrowthValueVOPage()
                                    .getContent())
                    && CollectionUtils.isEmpty(
                            customerPointsDetailQueryProvider
                                    .list(
                                            CustomerPointsDetailQueryRequest.builder()
                                                    .serviceType(PointsServiceType.PERFECTINFO)
                                                    .type(OperateType.GROWTH)
                                                    .customerId(customerEditRequest.getCustomerId())
                                                    .build())
                                    .getContext()
                                    .getCustomerPointsDetailVOList())) {
                // 增加成长值
                customerGrowthValueProvider.increaseGrowthValue(CustomerGrowthValueAddRequest.builder()
                        .customerId(commonUtil.getOperatorId())
                        .type(OperateType.GROWTH)
                        .serviceType(GrowthValueServiceType.PERFECTINFO)
                        .build());
                // 增加积分
                customerPointsDetailSaveProvider.add(CustomerPointsDetailAddRequest.builder()
                        .customerId(commonUtil.getOperatorId())
                        .type(OperateType.GROWTH)
                        .serviceType(PointsServiceType.PERFECTINFO)
                        .build());
            }

        }

        return BaseResponse.SUCCESSFUL();
    }

    /**
     * 查询会员中心主页面数据
     *
     * @return
     */
    @Operation(summary = "查询会员中心主页面数据")
    @RequestMapping(value = "/customerCenter", method = RequestMethod.GET)
    public BaseResponse<CustomerCenterResponse> findCustomerCenterInfo() {
        String customerId = commonUtil.getOperatorId();
        CustomerGetByIdResponse customer = customerQueryProvider.getCustomerById(new CustomerGetByIdRequest
                (customerId)).getContext();
        CustomerLevelVO customerLevel = new CustomerLevelVO();
        if (Objects.nonNull(customer.getCustomerLevelId())) {
            customerLevel = customerLevelQueryProvider.getCustomerLevelWithDefaultById(
                    CustomerLevelWithDefaultByIdRequest.builder().customerLevelId(customer.getCustomerLevelId()).build())
                    .getContext();
        }

        CustomerCenterResponse customerCenterResponse = CustomerCenterResponse.builder()
                .customerId(customerId)
                .customerAccount(StringUtils.substring(customer.getCustomerAccount(), 0, 3).concat("****").concat(StringUtils.substring(customer.getCustomerAccount(), 7)))
                .customerName(customer.getCustomerDetail().getCustomerName())
                .customerLevelName(customerLevel.getCustomerLevelName())
                .growthValue(customer.getGrowthValue())
                .rankBadgeImg(customerLevel.getRankBadgeImg())
                .headImg(customer.getHeadImg())
                .pointsAvailable(customer.getPointsAvailable())
                .customerLabelList(new ArrayList<>())
                .build();
        if (EnterpriseCheckState.CHECKED.equals(customer.getEnterpriseCheckState())){
            customerCenterResponse.getCustomerLabelList().add(CustomerLabel.EnterpriseCustomer);
            IepSettingVO iepSettingInfo = commonUtil.getIepSettingInfo();
            customerCenterResponse.setEnterpriseCustomerName(iepSettingInfo.getEnterpriseCustomerName());
            customerCenterResponse.setEnterpriseCustomerLogo(iepSettingInfo.getEnterpriseCustomerLogo());
        }
        return BaseResponse.success(customerCenterResponse);
    }

    /**
     * 查询可用积分
     *
     * @return
     */
    @Operation(summary = "查询可用积分")
    @RequestMapping(value = "/getPointsAvailable", method = RequestMethod.GET)
    public BaseResponse<CustomerPointsAvailableByCustomerIdResponse> getPointsAvailable() {
        String customerId = commonUtil.getOperatorId();
        return customerQueryProvider.getPointsAvailable(new CustomerPointsAvailableByIdRequest
                (customerId));
    }

    /**
     * 会员中心查询会员绑定的手机号
     *
     * @return
     */
    @Operation(summary = "会员中心查询会员绑定的手机号")
    @RequestMapping(value = "/customerMobile", method = RequestMethod.GET)
    public BaseResponse<CustomerSafeResponse> findCustomerMobile() {
        String customerId = commonUtil.getOperatorId();
        CustomerGetByIdResponse customer = customerQueryProvider.getCustomerById(new CustomerGetByIdRequest
                (customerId)).getContext();
        String customerAccount = customer.getCustomerAccount();
        if (Objects.nonNull(customerAccount)) {
            return BaseResponse.success(CustomerSafeResponse.builder().customerAccount(customerAccount).build());
        }
        return BaseResponse.FAILED();
    }

    /**
     * 会员中心 修改绑定手机号 给原号码发送验证码
     *
     * @param customerAccount
     * @return
     */
    @Operation(summary = "会员中心 修改绑定手机号 给原号码发送验证码")
    @Parameter( name = "customerAccount", description = "会员账户", required =
            true)
    @RequestMapping(value = "/customerVerified/{customerAccount}", method = RequestMethod.POST)
    public BaseResponse sendVerifiedCode(@PathVariable String customerAccount) {
        if (!customerAccount.equals(commonUtil.getOperator().getAccount())) {
            throw new SbcRuntimeException(CustomerErrorCodeEnum.K010039);
        }
        //是否可以发送
        if (!customerCacheService.validateSendMobileCode(customerAccount)) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000016);
        }
        //验证原手机号状态
        String result = this.checkOldCustomerAccount(customerAccount);
        if (StringUtils.isNotEmpty(result)) {
            return BaseResponse.error(result);
        }
        //删除验证错误次数
        redisService.delete(CacheKeyConstant.YZM_MOBILE_OLD_KEY_NUM.concat(customerAccount));
        //发送验证码
        if (Constants.yes.equals(customerCacheService.sendMobileCode(CacheKeyConstant.YZM_MOBILE_OLD_KEY, customerAccount,
                SmsTemplate.VERIFICATION_CODE))) {
            return BaseResponse.SUCCESSFUL();
        }
        return BaseResponse.FAILED();
    }

    /**
     * 验证原手机号状态
     *
     * @param customerAccount
     * @return
     */
    private String checkOldCustomerAccount(String customerAccount) {
        String result = "";
        NoDeleteCustomerGetByAccountResponse customer =
                customerQueryProvider.getNoDeleteCustomerByAccount(new NoDeleteCustomerGetByAccountRequest
                (customerAccount)).getContext();
        if (Objects.isNull(customer)) {
            result = "该账号不存在！";
        } else {
            //如果该手机号已存在
            CustomerDetailVO customerDetail = customer.getCustomerDetail();
            //是否禁用
            if (CustomerStatus.DISABLE.toValue() == customerDetail.getCustomerStatus().toValue()) {
                result = "该手机号已被禁用！";
            }
        }

        return result;
    }

    /**
     * 会员中心 修改绑定手机号 验证原号码发送的验证码
     *
     * @param mobileRequest
     * @return
     */
    @Operation(summary = "会员中心 修改绑定手机号 验证原号码发送的验证码")
    @RequestMapping(value = "/oldMobileCode", method = RequestMethod.POST)
    public BaseResponse<String> validateVerifiedCode(@RequestBody CustomerMobileRequest mobileRequest) {
        String customerAccount = commonUtil.getOperator().getAccount();
        //验证原手机号状态
        String result = this.checkOldCustomerAccount(customerAccount);
        if (StringUtils.isNotEmpty(result)) {
            return BaseResponse.error(result);
        }
        //累计验证错误次数
        String errKey = CacheKeyConstant.YZM_MOBILE_OLD_KEY_NUM.concat(customerAccount);
        // 校验及封装校验后请求参数
        String smsKey = CacheKeyConstant.YZM_MOBILE_OLD_KEY.concat(customerAccount);
        // 校验短信验证码
        commonUtil.verifyCodeMsg(errKey, smsKey, mobileRequest.getVerifyCode());

        //为了最后修改新手机号码用
        redisService.setString(CacheKeyConstant.YZM_MOBILE_OLD_KEY_AGAIN.concat(mobileRequest.getVerifyCode()),
                mobileRequest.getVerifyCode());
        //删除验证码缓存
        redisService.delete(CacheKeyConstant.YZM_MOBILE_OLD_KEY.concat(customerAccount));
        return BaseResponse.success(mobileRequest.getVerifyCode());
    }

    /**
     * 会员中心 修改绑定手机号
     * 1）验证新输入的手机号
     * 2）发送验证码给新手机号
     *
     * @param customerAccount
     * @return
     */
    @Operation(summary = "会员中心 修改绑定手机号 发送验证码给新手机号")
    @Parameter( name = "customerAccount", description = "会员账户", required =
            true)
    @RequestMapping(value = "/newCustomerVerified/{customerAccount}", method = RequestMethod.POST)
    public BaseResponse sendVerifiedCodeNew(@PathVariable String customerAccount) {
        //验证手机号格式
        if(!ValidateUtil.isPhone(customerAccount)){
            return BaseResponse.error("手机号格式不正确");
        }
        //是否可以发送
        if (!customerCacheService.validateSendMobileCode(customerAccount)) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000016);
        }
        //验证新输入的手机号
        String result = this.checkCustomerAccount(customerAccount);
        if (StringUtils.isNotEmpty(result)) {
            return BaseResponse.error(result);
        }

        CustomerSendMobileCodeRequest customerSendMobileCodeRequest = new CustomerSendMobileCodeRequest();
        customerSendMobileCodeRequest.setMobile(customerAccount);
        customerSendMobileCodeRequest.setRedisKey(CacheKeyConstant.YZM_MOBILE_NEW_KEY);
        customerSendMobileCodeRequest.setSmsTemplate(SmsTemplate.VERIFICATION_CODE);
        //删除验证错误次数
        redisService.delete(CacheKeyConstant.YZM_MOBILE_NEW_KEY_NUM.concat(customerAccount));
        //发送验证码
        if (Constants.yes.equals(customerCacheService.sendMobileCode(CacheKeyConstant.YZM_MOBILE_NEW_KEY, customerAccount,
                SmsTemplate.VERIFICATION_CODE))) {
            return BaseResponse.SUCCESSFUL();
        }

        return BaseResponse.FAILED();
    }

    /**
     * 会员中心 修改绑定手机号
     * 1）验证新手机号码的验证码是否正确
     * 2）更换绑定手机号码
     *
     * @param mobileRequest
     * @return
     */
    @Operation(summary = "会员中心 修改绑定手机号")
    @RequestMapping(value = "/newMobileCode", method = RequestMethod.POST)
    public BaseResponse changeNewMobile(@RequestBody CustomerMobileRequest mobileRequest) {
        //发送验证码，验证手机号
        if(!ValidateUtil.isPhone(mobileRequest.getCustomerAccount())){
            return BaseResponse.error("手机号格式不正确");
        }

        if (StringUtils.isEmpty(mobileRequest.getOldVerifyCode())) {
            return BaseResponse.error("操作失败");
        }

        //查询原验证码
        String oldVerifyCode =
                redisService.getString(CacheKeyConstant.YZM_MOBILE_OLD_KEY_AGAIN.concat(mobileRequest.getOldVerifyCode()));
        if (Objects.isNull(oldVerifyCode)) {
            return BaseResponse.error("操作失败");
        }

        String result = this.checkCustomerAccount(mobileRequest.getCustomerAccount());
        if (StringUtils.isNotEmpty(result)) {
            return BaseResponse.error(result);
        }

        //累计验证错误次数
        String errKey = CacheKeyConstant.YZM_MOBILE_NEW_KEY_NUM.concat(mobileRequest.getCustomerAccount());
        // 校验及封装校验后请求参数
        String smsKey = CacheKeyConstant.YZM_MOBILE_NEW_KEY.concat(mobileRequest.getCustomerAccount());
        // 校验短信验证码
        commonUtil.verifyCodeMsg(errKey, smsKey, mobileRequest.getVerifyCode());

        //校验手机号是不是已使用，如果已使用则不能进行绑定
        NoDeleteCustomerGetByAccountRequest request =
                new NoDeleteCustomerGetByAccountRequest();
        request.setCustomerAccount(mobileRequest.getCustomerAccount());
        NoDeleteCustomerGetByAccountResponse response =
                customerQueryProvider.getNoDeleteCustomerByAccount(request).getContext();
        if(Objects.nonNull(response) && Objects.nonNull(response.getCustomerId())){
            throw new SbcRuntimeException(CustomerErrorCodeEnum.K010038);
        }
        //更换绑定手机号码
//        if (Constants.yes == customerService.updateCustomerAccount(commonUtil.getOperatorId(), mobileRequest
//        .getCustomerAccount())) {
        if (Constants.yes == customerProvider.modifyCustomerAccount(new CustomerAccountModifyRequest(commonUtil
                .getOperatorId(), mobileRequest
                .getCustomerAccount())
        ).getContext().getCount()) {
            webBaseProducerService.sendMQForModifyCustomerAccount(commonUtil.getOperatorId(),mobileRequest.getCustomerAccount());
            //删除验证码缓存
            redisService.delete(CacheKeyConstant.YZM_MOBILE_NEW_KEY.concat(mobileRequest.getCustomerAccount()));
            redisService.delete(CacheKeyConstant.YZM_MOBILE_OLD_KEY_AGAIN.concat(mobileRequest.getOldVerifyCode()));

            return BaseResponse.SUCCESSFUL();
        }

        return BaseResponse.FAILED();
    }

    /**
     * 验证手机号码是否存在或禁用
     *
     * @param customerAccount
     * @return
     */
    private String checkCustomerAccount(String customerAccount) {
        //原手机号
//        String customerAccountOld = commonUtil.getOperator().getAccount();

        String result = "";
//        Customer customer = customerService.findByCustomerAccountAndDelFlag(customerAccount);
        NoDeleteCustomerGetByAccountResponse customer =
                customerQueryProvider.getNoDeleteCustomerByAccount(new NoDeleteCustomerGetByAccountRequest
                (customerAccount)).getContext();
        if (Objects.nonNull(customer)) {
            //如果该手机号已存在
            CustomerDetailVO customerDetail = customer.getCustomerDetail();
            //是否禁用
            if (CustomerStatus.DISABLE.toValue() == customerDetail.getCustomerStatus().toValue()) {
                result = "该手机号已被禁用！";
            } else {
                //如果新手机号不是原手机号，则新手机号已被绑定，原手机号也不需要再绑定了
                result = "该手机号已被绑定！";
            }
        }
        return result;
    }

    /**
     * 根据用户ID查询用户详情
     *
     * @param
     * @return
     */
    @Operation(summary = "根据用户ID查询用户详情")
    @Parameter(name = "customerId", description = "会员id", required = true)
    @RequestMapping(value = "/customerInfoById/{customerId}", method = RequestMethod.GET)
    public BaseResponse<CustomerDetailVO> getCustomerBaseInfo(@PathVariable String customerId) {
        if (StringUtils.isEmpty(customerId)) {
            return BaseResponse.error("参数不正确");
        }
        CustomerGetByIdResponse customer =
                customerQueryProvider.getCustomerById(new CustomerGetByIdRequest(customerId)).getContext();
        return BaseResponse.success(customer.getCustomerDetail());
    }

    /**
     * 验证token
     *
     * @return
     */
    @Operation(summary = "验证token")
    @RequestMapping(value = "/check-token", method = RequestMethod.GET)
    public BaseResponse checkToken() {
        return BaseResponse.SUCCESSFUL();
    }

    /**
     * 获取当前登录人信息
     *
     * @return
     */
    @Operation(summary = "获取当前登录人信息")
    @RequestMapping(value = "/getLoginCustomerInfo", method = RequestMethod.GET)
    public BaseResponse<CustomerGetByIdResponse> getLoginCustomerInfo() {
        String customerId = commonUtil.getOperatorId();
        CustomerGetByIdResponse customer =  customerQueryProvider.getCustomerById(new CustomerGetByIdRequest(customerId)).getContext();

        //屏蔽用户敏感信息
        if(Objects.nonNull(customer)) {
            customer.setCustomerPassword(null);
            customer.setCustomerPayPassword(null);
            customer.setCustomerSaltVal(null);
            customer.setLoginIp(null);
        }
        return BaseResponse.success(customer);
    }

    /**
     * 获取完善信息可得成长值和积分
     *
     * @return
     */
    @Operation(summary = "获取当前登录人信息")
    @RequestMapping(value = "/getGrowthValueAndPoint", method = RequestMethod.GET)
    public BaseResponse<GrowthValueAndPointResponse> getGrowthValueAndPoint() {
        String customerId = commonUtil.getOperatorId();
        BaseResponse<CustomerGetByIdResponse> customerById = customerQueryProvider.getCustomerById(
                new CustomerGetByIdRequest(customerId));
        GrowthValueAndPointResponse response = new GrowthValueAndPointResponse();
        //判断完善信息获取积分设置是否开启
        ConfigQueryRequest pointsRequest = new ConfigQueryRequest();
        pointsRequest.setConfigType(ConfigType.POINTS_BASIC_RULE_COMPLETE_INFORMATION.toValue());
        pointsRequest.setDelFlag(DeleteFlag.NO.toValue());
        ConfigVO pointsConfig =
                systemConfigQueryProvider.findByConfigTypeAndDelFlag(pointsRequest).getContext().getConfig();
        if (pointsConfig != null && pointsConfig.getStatus() == 1) {
            response.setPointFlag(Boolean.TRUE);
            response.setPoint(this.getValue(pointsConfig.getContext()));
        } else {
            response.setPointFlag(Boolean.FALSE);
            response.setPoint(0L);
        }
        //判断完善信息获取成长值设置是否开启
        ConfigQueryRequest request = new ConfigQueryRequest();
        request.setConfigType(ConfigType.GROWTH_VALUE_BASIC_RULE_COMPLETE_INFORMATION.toValue());
        request.setDelFlag(DeleteFlag.NO.toValue());
        ConfigVO growthConfig = systemConfigQueryProvider.findByConfigTypeAndDelFlag(request).getContext().getConfig();
        if (growthConfig != null && growthConfig.getStatus() == 1) {
            response.setGrowthFlag(Boolean.TRUE);
            response.setGrowthValue(this.getValue(growthConfig.getContext()));
        } else {
            response.setGrowthFlag(Boolean.FALSE);
            response.setGrowthValue(0L);
        }
        //只要获取过完善有礼的任意成长值或积分奖励，将不再获得这些完善奖励
        if (CollectionUtils.isNotEmpty(customerGrowthValueQueryProvider.page(CustomerGrowthValuePageRequest.builder()
                .growthValueServiceType(GrowthValueServiceType.PERFECTINFO)
                .customerId(customerId).type(OperateType.GROWTH).build()).getContext()
                .getCustomerGrowthValueVOPage().getContent())
        ||CollectionUtils.isNotEmpty(customerPointsDetailQueryProvider.list(CustomerPointsDetailQueryRequest.builder()
                .serviceType(PointsServiceType.PERFECTINFO).type(OperateType.GROWTH)
                .customerId(customerId).build()).getContext().getCustomerPointsDetailVOList())) {
            response.setGrowthValue(0L);
            response.setPoint(0L);
        }
        return BaseResponse.success(response);
    }

    /**
     * 积分/成长值转换
     *
     * @param value
     * @return
     */
    private Long getValue(String value) {
        if (StringUtils.isNotBlank(value)) {
            return JSONObject.parseObject(value).getLong("value");
        } else {
            return null;
        }

    }


    /**
     * 用户更换头像
     *
     * @param request
     * @return
     */
    @Operation(summary = "用户更换头像")
    @RequestMapping(value = "/editHeadImg", method = RequestMethod.POST)
    public BaseResponse editHeadImg(@Valid @RequestBody EditHeadImgRequest request) {
        String customerId = commonUtil.getOperatorId();
        request.setCustomerId(customerId);
        if (StringUtils.isBlank(request.getCustomerId())){
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        customerProvider.editHeadImg(request);
        return BaseResponse.SUCCESSFUL();
    }

}
