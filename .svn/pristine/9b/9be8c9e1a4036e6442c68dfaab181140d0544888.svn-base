package com.wanmi.sbc.customer;


import com.wanmi.sbc.account.api.provider.customerdrawcash.CustomerDrawCashQueryProvider;
import com.wanmi.sbc.account.api.provider.customerdrawcash.CustomerDrawCashSaveProvider;
import com.wanmi.sbc.account.api.request.customerdrawcash.*;
import com.wanmi.sbc.account.api.response.customerdrawcash.CustomerDrawCashAddResponse;
import com.wanmi.sbc.account.api.response.customerdrawcash.CustomerDrawCashByIdResponse;
import com.wanmi.sbc.account.api.response.customerdrawcash.CustomerDrawCashModifyResponse;
import com.wanmi.sbc.account.bean.enums.AuditStatus;
import com.wanmi.sbc.account.bean.enums.CustomerOperateStatus;
import com.wanmi.sbc.account.bean.vo.CustomerDrawCashVO;
import com.wanmi.sbc.common.annotation.MultiSubmitWithToken;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.enums.TerminalType;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.customer.api.provider.loginregister.CustomerSiteProvider;
import com.wanmi.sbc.customer.api.provider.quicklogin.ThirdLoginRelationQueryProvider;
import com.wanmi.sbc.customer.api.request.loginregister.CustomerCheckPayPasswordRequest;
import com.wanmi.sbc.customer.api.request.quicklogin.ThirdLoginRelationByCustomerRequest;
import com.wanmi.sbc.customer.api.response.quicklogin.ThirdLoginRelationResponse;
import com.wanmi.sbc.customer.bean.enums.CustomerErrorCodeEnum;
import com.wanmi.sbc.customer.bean.enums.ThirdLoginType;
import com.wanmi.sbc.customer.bean.vo.CustomerVO;
import com.wanmi.sbc.customer.bean.vo.ThirdLoginRelationVO;
import com.wanmi.sbc.empower.api.provider.pay.PaySettingQueryProvider;
import com.wanmi.sbc.marketing.bean.enums.MarketingErrorCodeEnum;
import com.wanmi.sbc.message.StoreMessageBizService;
import com.wanmi.sbc.third.login.api.WechatApi;
import com.wanmi.sbc.third.wechat.WechatSetService;
import com.wanmi.sbc.util.CommonUtil;


import io.swagger.v3.oas.annotations.Parameter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * 会员提现管理
 */
@Slf4j
@RestController(value = "CustomerDrawCashController")
@Validated
@RequestMapping("/draw/cash")
@Tag(name = "CustomerDrawCashController",description = "S2B 会员提现管理API")
public class CustomerDrawCashController {

    @Autowired
    private CustomerDrawCashQueryProvider customerDrawCashQueryProvider;

    @Autowired
    private CustomerDrawCashSaveProvider customerDrawCashSaveProvider;

    @Autowired
    private CommonUtil commonUtil;

    @Autowired
    private RedissonClient redissonClient;

    @Autowired
    private WechatApi wechatApi;

    @Autowired
    private PaySettingQueryProvider paySettingQueryProvider;

    @Autowired
    private CustomerSiteProvider customerSiteProvider;

    @Autowired
    private ThirdLoginRelationQueryProvider thirdLoginRelationQueryProvider;

    @Autowired
    private WechatSetService wechatSetService;

    @Autowired
    private StoreMessageBizService storeMessageBizService;

    /**
     * 会员提现管理列表
     * @param request
     * @return
     */
    @Operation(summary = "S2B 会员提现管理列表")
    @RequestMapping(value = "/page",method = RequestMethod.POST)
    public BaseResponse<MicroServicePage<CustomerDrawCashVO> > page(@RequestBody CustomerDrawCashQueryRequest request){

        CustomerDrawCashPageRequest cashPageRequest = CustomerDrawCashPageRequest.builder()
                .customerId(commonUtil.getOperatorId())
                .auditStatus(request.getAuditStatus())
                .finishStatus(request.getFinishStatus())
                .customerOperateStatus(request.getCustomerOperateStatus())
                .drawCashStatus(request.getDrawCashStatus())
                .sourceFromPlatForm(Boolean.FALSE)
                .build();
        cashPageRequest.setPageNum(request.getPageNum());
        cashPageRequest.setPageSize(request.getPageSize());

        MicroServicePage<CustomerDrawCashVO> page = customerDrawCashQueryProvider.page(cashPageRequest).getContext().getCustomerDrawCashVOPage();

        return BaseResponse.success(page);
    }



    /**
     * 查询提现单详情
     */
    @Operation(summary = "查询提现单详情")
    @Parameter(name = "dcId", description = "提现单ID", required = true)
    @RequestMapping(value = "/detail/{dcId}", method = RequestMethod.GET)
    public BaseResponse<CustomerDrawCashByIdResponse> detail(@PathVariable String dcId) {
        CustomerDrawCashByIdResponse response =
                customerDrawCashQueryProvider.getById(CustomerDrawCashByIdRequest.builder().drawCashId(dcId).build()).getContext();
        if (Objects.isNull(response) || Objects.isNull(response.getCustomerDrawCashVO())) {
            return BaseResponse.error("暂无数据");
        }
        if(!Objects.equals(response.getCustomerDrawCashVO().getCustomerId(), commonUtil.getOperatorId())){
            return BaseResponse.error("非法越权操作");
        }
        ThirdLoginRelationResponse thirdLoginRelationResponse =
                thirdLoginRelationQueryProvider.thirdLoginRelationByCustomerAndDelFlag(
                        ThirdLoginRelationByCustomerRequest.builder().customerId(response.getCustomerDrawCashVO()
                                .getCustomerId()).thirdLoginType(ThirdLoginType.WECHAT).delFlag(DeleteFlag.NO).build()).getContext();
        if (Objects.nonNull(thirdLoginRelationResponse.getThirdLoginRelation())){
            response.getCustomerDrawCashVO().setHeadimgurl(thirdLoginRelationResponse.getThirdLoginRelation().getHeadimgurl());
        }

        return BaseResponse.success(response);
    }


    /**
     * 查询提现单详情
     */
    @Operation(summary = "查询提现单详情")
    @Parameter(name = "dcId", description = "提现单ID", required = true)
    @RequestMapping(value = "/cancel/{dcId}", method = RequestMethod.GET)
    @MultiSubmitWithToken
    public BaseResponse<CustomerDrawCashModifyResponse> cancel(@PathVariable String dcId) {
        CustomerDrawCashVO cash = customerDrawCashQueryProvider.getById(CustomerDrawCashByIdRequest.builder()
                .drawCashId(dcId)
                .build())
                .getContext().getCustomerDrawCashVO();

        if(!cash.getCustomerId().equals(commonUtil.getOperatorId())){
            return BaseResponse.error("非法越权操作");
        }

        if(cash.getAuditStatus() != AuditStatus.WAIT){
            throw new SbcRuntimeException(MarketingErrorCodeEnum.K080196);
        }

        // 已取消不可重复点击
        if(cash.getCustomerOperateStatus() == CustomerOperateStatus.CANCEL){
            throw new SbcRuntimeException(MarketingErrorCodeEnum.K080200);
        }

        return customerDrawCashSaveProvider.modify(
                CustomerDrawCashModifyRequest.builder()
                        .drawCashId(dcId)
                        .drawCashNo(cash.getDrawCashNo())
                        .applyTime(cash.getApplyTime())
                        .customerId(cash.getCustomerId())
                        .customerName(cash.getCustomerName())
                        .customerAccount(cash.getCustomerAccount())
                        .drawCashChannel(cash.getDrawCashChannel())
                        .drawCashAccountName(cash.getDrawCashAccountName())
                        .drawCashAccount(cash.getDrawCashAccount())
                        .drawCashSum(cash.getDrawCashSum())
                        .drawCashRemark(cash.getDrawCashRemark())
                        .auditStatus(cash.getAuditStatus())
                        .rejectReason(cash.getRejectReason())
                        .drawCashStatus(cash.getDrawCashStatus())
                        .drawCashFailedReason(cash.getDrawCashFailedReason())
                        .customerOperateStatus(CustomerOperateStatus.CANCEL)
                        .finishStatus(cash.getFinishStatus())
                        .finishTime(cash.getFinishTime())
                        .supplierOperateId(cash.getSupplierOperateId())
                        .updateTime(cash.getUpdateTime())
                        .delFlag(cash.getDelFlag())
                        .accountBalance(cash.getAccountBalance())
                        .build()
        );
    }

    /**
     * 新增提现记录
     * @param customerDrawCashAddRequest
     * @return
     */
    @Operation(summary = "新增提现记录")
    @RequestMapping(value = "/addCustomerDrawCash",method = RequestMethod.POST)
    public BaseResponse<CustomerDrawCashAddResponse> addCustomerDrawCash(@RequestBody CustomerDrawCashAddRequest customerDrawCashAddRequest){
        CustomerVO customerVO = commonUtil.getCustomer();
        RLock rLock = redissonClient.getFairLock(customerVO.getCustomerId());
        rLock.lock();
        try {
            //验证微信设置状态
            TerminalType terminalType = TerminalType.H5;
            if (Objects.nonNull(customerDrawCashAddRequest.getDrawCashSource())) {
                switch (customerDrawCashAddRequest.getDrawCashSource()) {
                  case APP:
                    terminalType = TerminalType.APP;
                    break;
                  case PC:
                    terminalType = TerminalType.PC;
                    break;
                  case MINI:
                    terminalType = TerminalType.MINI;
                    break;
                  default:
                    break;
                }
            }
            DefaultFlag defaultFlag = wechatSetService.getStatus(terminalType);
            if (DefaultFlag.NO.equals(defaultFlag)) {
                throw new SbcRuntimeException(CommonErrorCodeEnum.K000019);
            }

            //验证绑定微信号
            ThirdLoginRelationByCustomerRequest relRequest = ThirdLoginRelationByCustomerRequest.builder()
                    .customerId(customerVO.getCustomerId())
                            .delFlag(DeleteFlag.NO)
                    .thirdLoginType(ThirdLoginType.WECHAT)
                            .storeId(Constants.BOSS_DEFAULT_STORE_ID).build();
            ThirdLoginRelationVO relation = thirdLoginRelationQueryProvider.thirdLoginRelationByCustomerAndDelFlag(relRequest)
                    .getContext()
                    .getThirdLoginRelation();
            if(Objects.isNull(relation) || StringUtils.isBlank(relation.getThirdLoginOpenId())){
                throw new SbcRuntimeException(CustomerErrorCodeEnum.K010051);
            }
            customerDrawCashAddRequest.setOpenId(relation.getThirdLoginOpenId());

            CustomerCheckPayPasswordRequest customerCheckPayPasswordRequest = new CustomerCheckPayPasswordRequest();
            customerCheckPayPasswordRequest.setPayPassword(customerDrawCashAddRequest.getPayPassword());
            customerCheckPayPasswordRequest.setCustomerId(customerVO.getCustomerId());
            //校验密码
            customerSiteProvider.checkCustomerPayPwd(customerCheckPayPasswordRequest);
            // 应用唯一标识，在微信开放平台提交应用审核通过后获得
            /*String appId;
            // 应用密钥AppSecret，在微信开放平台提交应用审核通过后获得
            String secret;
            // APP提现
            if (DrawCashSource.APP == customerDrawCashAddRequest.getDrawCashSource()) {
                PayGatewayConfigResponse payGatewayConfig = paySettingQueryProvider.getGatewayConfigByGateway(new
                        GatewayConfigByGatewayRequest(PayGatewayEnum.WECHAT, Constants.BOSS_DEFAULT_STORE_ID)).getContext();
                appId = payGatewayConfig.getOpenPlatformAppId();
                secret = payGatewayConfig.getOpenPlatformSecret();
                // 使用code换取access_token和openId
                GetAccessTokeResponse resp = wechatApi.getWeChatAccessToken(appId, secret, customerDrawCashAddRequest.getCode());
                // 保存提现终端的openId
                customerDrawCashAddRequest.setOpenId(resp.getOpenid());
            }*/
            customerDrawCashAddRequest.setCustomerId(customerVO.getCustomerId());
            customerDrawCashAddRequest.setCustomerName(customerVO.getCustomerDetail().getCustomerName());
            customerDrawCashAddRequest.setCustomerAccount(customerVO.getCustomerAccount());
            /*if(StringUtils.isBlank(customerDrawCashAddRequest.getOpenId())){
                log.error("新增提现记录报错，openId不能为空！");
                return BaseResponse.FAILED();
            }*/

            BaseResponse<CustomerDrawCashAddResponse> response = customerDrawCashSaveProvider.add(customerDrawCashAddRequest);

            // ============= 处理平台的消息发送：会员提现申请待审核 START =============
            storeMessageBizService.handleForCustomerWithdrawAudit(customerVO);
            // ============= 处理平台的消息发送：会员提现申请待审核 END =============

            return response;
        } catch(SbcRuntimeException e){
            throw e;
        } catch(Exception e){
            log.error("新增提现记录报错", e);
        } finally {
            rLock.unlock();
        }
        return BaseResponse.FAILED();
    }

    /**
     * 获取当天提现金额
     * @return
     */
    @RequestMapping(value = "/countDrawCashSumByCustId", method = RequestMethod.GET)
    public BaseResponse<BigDecimal> countDrawCashSumByCustId(){
        CustomerDrawCashQueryRequest request = new CustomerDrawCashQueryRequest();
        request.setCustomerId(commonUtil.getOperatorId());
        return customerDrawCashQueryProvider.countDrawCashSum(request);
    }


}
