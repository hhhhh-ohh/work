package com.wanmi.sbc.customer.mqconsumer;

import com.alibaba.fastjson2.JSONObject;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.enums.LogOutStatus;
import com.wanmi.sbc.common.enums.OperateType;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.customer.api.request.customer.CustomerLogoutStatusModifyRequest;
import com.wanmi.sbc.customer.api.request.growthvalue.CustomerGrowthValueAddRequest;
import com.wanmi.sbc.customer.api.request.points.CustomerPointsBatchAdjustRequest;
import com.wanmi.sbc.customer.api.request.points.CustomerPointsDetailAddRequest;
import com.wanmi.sbc.customer.bean.dto.CustomerPointsAdjustDTO;
import com.wanmi.sbc.customer.bean.enums.PointsServiceType;
import com.wanmi.sbc.customer.bean.enums.ThirdLoginType;
import com.wanmi.sbc.customer.distribution.service.DistributionCustomerService;
import com.wanmi.sbc.customer.growthvalue.service.GrowthValueIncreaseService;
import com.wanmi.sbc.customer.invoice.service.CustomerInvoiceService;
import com.wanmi.sbc.customer.model.root.Customer;
import com.wanmi.sbc.customer.points.service.CustomerPointsDetailService;
import com.wanmi.sbc.customer.quicklogin.model.root.ThirdLoginRelation;
import com.wanmi.sbc.customer.quicklogin.model.root.WeChatQuickLogin;
import com.wanmi.sbc.customer.quicklogin.service.ThirdLoginRelationService;
import com.wanmi.sbc.customer.quicklogin.service.WeChatQuickLoginService;
import com.wanmi.sbc.customer.service.CustomerService;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author lvzhenwei
 * @className CustomerMqConsumerService
 * @description mq方法实现
 * @date 2021/8/16 10:58 上午
 **/
@Slf4j
@Service
public class CustomerMqConsumerService {

    @Autowired
    private GrowthValueIncreaseService growthValueIncreaseService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private DistributionCustomerService distributionCustomerService;

    @Autowired
    private CustomerPointsDetailService customerPointsDetailService;

    @Autowired
    private CustomerInvoiceService customerInvoiceService;

    @Autowired
    WeChatQuickLoginService weChatQuickLoginService;

    @Autowired
    ThirdLoginRelationService thirdLoginRelationService;


    /**
     * @description 增加成长值
     * @author  lvzhenwei
     * @date 2021/8/16 11:01 上午
     * @param msg
     * @return void
     **/
    public void increaseCustomerGrowthValue(String msg) {
        CustomerGrowthValueAddRequest addRequest = JSONObject.parseObject(msg, CustomerGrowthValueAddRequest.class);
        growthValueIncreaseService.increaseGrowthValue(addRequest);

    }

    /**
     * @description 注销用户
     * @author  xufeng
     * @date 2022/3/31 11:01 上午
     * @param msg
     * @return void
     **/
    public boolean customerLogout(String msg) {
        Customer customer = customerService.findById(msg);
        //不是待注销状态
        if (customer.getLogOutStatus()!=LogOutStatus.LOGGING_OFF){
            return false;
        }
        Long pointsAvailable = customer.getPointsAvailable();
        CustomerLogoutStatusModifyRequest customerLogoutStatusModifyRequest = new CustomerLogoutStatusModifyRequest();
        customerLogoutStatusModifyRequest.setCustomerId(msg);
        customerLogoutStatusModifyRequest.setLogOutStatus(LogOutStatus.LOGGED_OUT);
        customerService.modifyLogOutStatus(customerLogoutStatusModifyRequest);
        // 扣除积分
        if (pointsAvailable > 0){
            customerPointsDetailService.deductPoints(CustomerPointsDetailAddRequest.builder()
                    .customerId(msg)
                    .type(OperateType.DEDUCT)
                    .serviceType(PointsServiceType.LOG_OUT)
                    .points(pointsAvailable)
                    .build());
        }
        //注销后分销脱钩
        distributionCustomerService.updateForbiddenFlagByCustomerId(msg);
        //作废 增专票信息
        customerInvoiceService.invalidCustomerInvoiceByCustomerId(msg);

        try{
            ThirdLoginRelation thirdLoginRelation =
                    thirdLoginRelationService.deleteByCustomerIdAndStoreId(customer.getCustomerId(),
                            ThirdLoginType.WECHAT,
                            Constants.BOSS_DEFAULT_STORE_ID);
            if (ThirdLoginType.WECHAT.equals(thirdLoginRelation.getThirdLoginType())){
                WeChatQuickLogin weChatQuickLogin = new WeChatQuickLogin();
                weChatQuickLogin.setOpenId(thirdLoginRelation.getThirdLoginOpenId());
                weChatQuickLogin.setDelFlag(DeleteFlag.YES);
                weChatQuickLoginService.save(weChatQuickLogin);
            } else {
                log.error("类名：CustomerMqConsumerService, 方法名: customerLogout, 快捷登录类型：{}", thirdLoginRelation.getThirdLoginType());
            }
        } catch (SbcRuntimeException e){
            log.info("类名：CustomerMqConsumerService, 方法名: customerLogout, 用户注销:{}", e.getMessage());
        }
        return true;
    }

    /**
     * 批量修改积分
     */
    @Async
    public void batchCustomerPointsUpdate (String mqContentJson ) {
        try {
            log.info("批量客户积分修改");

            CustomerPointsBatchAdjustRequest request = JSONObject.parseObject(mqContentJson,
                    CustomerPointsBatchAdjustRequest.class);
                //如果是批量增加积分
                List<CustomerPointsAdjustDTO> addAdjustList = request.getPointsAdjustDTOList().stream()
                        .filter(adjustDTO -> OperateType.GROWTH.equals(adjustDTO.getOperateType()))
                        .collect(Collectors.toList());
            customerPointsDetailService.batchIncreasePoints(CustomerPointsBatchAdjustRequest.builder().pointsAdjustDTOList(addAdjustList).build());

                //如果是批量减少会员积分
                List<CustomerPointsAdjustDTO> deductAdjustList = request.getPointsAdjustDTOList().stream()
                        .filter(adjustDTO -> OperateType.DEDUCT.equals(adjustDTO.getOperateType()))
                        .collect(Collectors.toList());
            customerPointsDetailService.batchReducePoints(CustomerPointsBatchAdjustRequest.builder().pointsAdjustDTOList(deductAdjustList).build());

                //如果是批量覆盖积分
                List<CustomerPointsAdjustDTO> replaceAdjustList = request.getPointsAdjustDTOList().stream()
                        .filter(adjustDTO -> OperateType.REPLACE.equals(adjustDTO.getOperateType()))
                        .collect(Collectors.toList());
            customerPointsDetailService.batchCoverPoints(CustomerPointsBatchAdjustRequest.builder().pointsAdjustDTOList(replaceAdjustList).build());


            log.info("批量客户积分修改");
        } catch (Exception e) {
            log.error("批量客户积分修改, param={},e={}", mqContentJson, e);
        }
    }
}
