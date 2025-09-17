package com.wanmi.sbc.third.login;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.enums.SpecialSymbols;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.redis.CacheKeyConstant;
import com.wanmi.sbc.common.redis.util.RedisUtil;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.common.util.ValidateUtil;
import com.wanmi.sbc.customer.api.provider.loginregister.CustomerSiteQueryProvider;
import com.wanmi.sbc.customer.api.provider.quicklogin.ThirdLoginRelationProvider;
import com.wanmi.sbc.customer.api.provider.quicklogin.ThirdLoginRelationQueryProvider;
import com.wanmi.sbc.customer.api.request.loginregister.CustomerByAccountRequest;
import com.wanmi.sbc.customer.api.request.quicklogin.ThirdLoginRelationByCustomerRequest;
import com.wanmi.sbc.customer.api.request.quicklogin.ThirdLoginRelationDeleteByCustomerRequest;
import com.wanmi.sbc.customer.api.response.loginregister.CustomerByAccountResponse;
import com.wanmi.sbc.customer.bean.enums.CustomerErrorCodeEnum;
import com.wanmi.sbc.customer.bean.enums.SmsTemplate;
import com.wanmi.sbc.customer.bean.enums.ThirdLoginType;
import com.wanmi.sbc.customer.bean.vo.ThirdLoginRelationVO;
import com.wanmi.sbc.customer.service.CustomerCacheService;
import com.wanmi.sbc.third.login.response.LinkedAccountFlagsQueryResponse;
import com.wanmi.sbc.third.login.response.ThirdLoginSendCodeResponse;
import com.wanmi.sbc.util.CommonUtil;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import jodd.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;

import java.util.Objects;

@Tag(name = "ThirdLoginController", description = "第三方登录 API")
@RestController
@Validated
@RequestMapping("/third/login")
public class ThirdLoginController {

    private static final String USER_INFO_KEY = CacheKeyConstant.WE_CHAT + SpecialSymbols.COLON.toValue() + "USER_INFO" +
            SpecialSymbols.COLON.toValue();

    @Autowired
    private RedisUtil redisService;

    @Autowired
    private ThirdLoginRelationProvider thirdLoginRelationProvider;

    @Autowired
    private ThirdLoginRelationQueryProvider thirdLoginRelationQueryProvider;

    @Autowired
    private CommonUtil commonUtil;

    @Autowired
    private CustomerSiteQueryProvider customerSiteQueryProvider;

    @Value("${jwt.secret-key}")
    private String jwtSecretKey;

    @Autowired
    private CustomerCacheService customerCacheService;

    /**
     * 绑定第三方账号 发送验证码
     *
     * @return
     */
    @Operation(summary = "绑定第三方账号 发送验证码")
    @Parameters({
            @Parameter(name = "phone", description = "手机号", required = true),
            @Parameter(name = "id", description = "id", required = true)
    })
    @RequestMapping(value = "/bind/sendCode/{phone}/{id}", method = RequestMethod.GET)
    public BaseResponse<ThirdLoginSendCodeResponse> sendCode(@PathVariable String phone, @PathVariable String id) {
        // 校验输入信息是否合法
        if (StringUtil.isBlank(phone) || StringUtil.isBlank(id) || !ValidateUtil.isPhone(phone)) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        // 判断是否在10分钟之内验证 为了确保在验证验证码绑定商城账户时，能获取到在/wetchLogin接口中存入redis的微信userInfo
        boolean wxFlag = redisService.hasKey(USER_INFO_KEY + id);
        if (!wxFlag) {
            throw new SbcRuntimeException(CustomerErrorCodeEnum.K010047);
        }
        //是否可以发送
        if (!customerCacheService.validateSendMobileCode(phone)) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000016);
        }

        //账号是否注册
        CustomerByAccountRequest request = new CustomerByAccountRequest();
        request.setCustomerAccount(phone);
        BaseResponse<CustomerByAccountResponse> responseBaseResponse =
                customerSiteQueryProvider.getCustomerByCustomerAccount(request);
        CustomerByAccountResponse response = responseBaseResponse.getContext();
        // 已注册的情况下, 需要走的流程
        Boolean isRegister = Boolean.FALSE;
        if (Objects.nonNull(response)) {
            ThirdLoginRelationVO phoneRelation = thirdLoginRelationQueryProvider
                    .thirdLoginRelationByCustomerAndDelFlag(
                            ThirdLoginRelationByCustomerRequest.builder()
                                    .customerId(response.getCustomerId())
                                    .thirdLoginType(ThirdLoginType.WECHAT)
                                    .delFlag(DeleteFlag.NO)
                                    .storeId(Constants.BOSS_DEFAULT_STORE_ID)
                                    .build()
                    ).getContext().getThirdLoginRelation();
            // 手机号是否已经被绑定
            if (Objects.nonNull(phoneRelation)) {
                throw new SbcRuntimeException(CustomerErrorCodeEnum.K010048);
            }
        } else {
            isRegister = Boolean.TRUE;
        }
        //删除验证错误次数
        redisService.delete(CacheKeyConstant.WX_BINDING_LOGIN_NUM.concat(phone));
        //发送验证码
        if (Constants.yes.equals(customerCacheService.sendMobileCode(CacheKeyConstant.WX_BINDING_LOGIN, phone,
                SmsTemplate.VERIFICATION_CODE))) {
            return BaseResponse.success(ThirdLoginSendCodeResponse.builder()
                    .isRegister(isRegister)
                    .build());
        }
        return BaseResponse.FAILED();
    }

    /**
     * 解绑
     *
     * @param thirdLoginType 第三方登录方式
     * @return
     */
    @Operation(summary = "解绑，thirdLoginType: 0微信")
    @Parameter(name = "thirdLoginType", description = "登录方式", required = true)
    @RequestMapping(value = "/remove/bind/{thirdLoginType}", method = RequestMethod.DELETE)
    public BaseResponse removeBind(@PathVariable ThirdLoginType thirdLoginType) {
        return thirdLoginRelationProvider.deleteThirdLoginRelationByCustomerIdAndStoreId(
                ThirdLoginRelationDeleteByCustomerRequest.builder()
                        .customerId(commonUtil.getOperatorId())
                        .thirdLoginType(thirdLoginType)
                        .storeId(Constants.BOSS_DEFAULT_STORE_ID)
                        .build()
        );
    }

    /**
     * 查询登录用户关联账号的状态
     */
    @Operation(summary = "查询登录用户关联账号的状态")
    @RequestMapping(value = "/linked-account-flags", method = RequestMethod.GET)
    public BaseResponse<LinkedAccountFlagsQueryResponse> queryLinkedAccountFlags() {
        ThirdLoginRelationVO phoneRelation = thirdLoginRelationQueryProvider
                .thirdLoginRelationByCustomerAndDelFlag(
                        ThirdLoginRelationByCustomerRequest.builder()
                                .customerId(commonUtil.getOperatorId())
                                .thirdLoginType(ThirdLoginType.WECHAT)
                                .delFlag(DeleteFlag.NO)
                                .storeId(Constants.BOSS_DEFAULT_STORE_ID)
                                .build()
                ).getContext().getThirdLoginRelation();
        String nickName = "";
        String headImgUrl = "";
        if (Objects.nonNull(phoneRelation)) {
            headImgUrl = phoneRelation.getHeadimgurl();
            nickName = phoneRelation.getNickname();
        }
        return BaseResponse.success(LinkedAccountFlagsQueryResponse.builder()
                .wxFlag(phoneRelation != null)
                .nickname(nickName)
                .headimgurl(headImgUrl)
                .build());
    }
}
