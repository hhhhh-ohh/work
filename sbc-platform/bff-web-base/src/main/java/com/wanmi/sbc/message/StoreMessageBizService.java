package com.wanmi.sbc.message;

import com.google.common.collect.Lists;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.enums.storemessage.BossMessageNode;
import com.wanmi.sbc.common.enums.storemessage.ProviderMessageNode;
import com.wanmi.sbc.common.enums.storemessage.SupplierMessageNode;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.common.util.MutableMap;
import com.wanmi.sbc.customer.api.provider.customer.CustomerQueryProvider;
import com.wanmi.sbc.customer.api.provider.store.StoreCustomerQueryProvider;
import com.wanmi.sbc.customer.api.request.customer.CustomerGetByIdRequest;
import com.wanmi.sbc.customer.api.request.store.StoreCustomerRelaListByConditionRequest;
import com.wanmi.sbc.customer.api.response.customer.CustomerGetByIdResponse;
import com.wanmi.sbc.customer.bean.enums.*;
import com.wanmi.sbc.customer.bean.vo.CustomerVO;
import com.wanmi.sbc.customer.bean.vo.StoreCustomerRelaVO;
import com.wanmi.sbc.elastic.api.provider.storeInformation.EsStoreInformationQueryProvider;
import com.wanmi.sbc.elastic.api.request.storeInformation.EsCompanyPageRequest;
import com.wanmi.sbc.elastic.bean.vo.storeInformation.EsCompanyInfoVO;
import com.wanmi.sbc.order.bean.vo.ReturnOrderVO;
import com.wanmi.sbc.order.bean.vo.SupplierVO;
import com.wanmi.sbc.setting.api.provider.AuditQueryProvider;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @description 用户端，商家消息具体发送业务服务，统一管理
 * @author malianfeng
 * @date 2022/7/12 14:59
 */
@Slf4j
@Service
public class StoreMessageBizService {

    @Autowired private StoreMessageService storeMessageService;

    @Autowired private EsStoreInformationQueryProvider esStoreInformationQueryProvider;

    @Autowired private CustomerQueryProvider customerQueryProvider;

    @Autowired private StoreCustomerQueryProvider storeCustomerQueryProvider;

    @Autowired private AuditQueryProvider auditQueryProvider;

    // ==================================== 处理平台的消息发送 START ====================================

    /**
     * 会员提现申请待审核
     */
    public void handleForCustomerWithdrawAudit(CustomerVO customerVO) {
        try {
            storeMessageService.convertAndSend(
                    Constants.BOSS_DEFAULT_STORE_ID,
                    BossMessageNode.CUSTOMER_WITHDRAW_WAIT_AUDIT.getCode(),
                    Lists.newArrayList(customerVO.getCustomerAccount()),
                    MutableMap.of("account", customerVO.getCustomerAccount()));
        } catch (Exception e) {
            log.error("会员提现申请待审核, 消息处理失败，{}", customerVO, e);
        }
    }

    /**
     * 客户注销提醒
     */
    @Async
    public void handleForCustomerLogout(String customerId) {
        try {
            // 消息接收店铺id列表
            List<Long> targetStoreIds = new ArrayList<>();
            CustomerGetByIdResponse customerVO = customerQueryProvider.getCustomerById(CustomerGetByIdRequest.builder()
                    .customerId(customerId).build()).getContext();
            CustomerType customerType = customerVO.getCustomerType();

            // 1. 平台客户/店铺关联的客户
            if (CustomerType.PLATFORM == customerType) {
                // 1.1 查询所有自营店铺id
                targetStoreIds.add(Constants.BOSS_DEFAULT_STORE_ID);
                int maxPageSize = 1000;
                EsCompanyPageRequest companyPageRequest = new EsCompanyPageRequest();
                companyPageRequest.setPageSize(maxPageSize);
                companyPageRequest.setAccountState(AccountState.ENABLE.toValue());
                companyPageRequest.setStoreState(StoreState.OPENING.toValue());
                companyPageRequest.setAuditState(CheckState.CHECKED.toValue());
                companyPageRequest.setDeleteFlag(DeleteFlag.NO);
                companyPageRequest.setCompanyType(CompanyType.PLATFORM.toValue());
                List<EsCompanyInfoVO> companyInfos;
                do {
                    companyInfos = esStoreInformationQueryProvider.companyInfoPage(companyPageRequest).getContext().getEsCompanyAccountPage().getContent();
                    targetStoreIds.addAll(companyInfos.stream().map(EsCompanyInfoVO::getStoreId).collect(Collectors.toList()));
                    companyPageRequest.setPageNum(companyPageRequest.getPageNum() + 1);
                } while (companyInfos.size() == maxPageSize);

                // 1.2 查询关联此客户的第三方店铺id
                StoreCustomerRelaListByConditionRequest conditionRequest = new StoreCustomerRelaListByConditionRequest();
                conditionRequest.setCustomerType(CustomerType.PLATFORM);
                conditionRequest.setCustomerId(customerId);
                List<StoreCustomerRelaVO> relaVOList = storeCustomerQueryProvider.listByCondition(conditionRequest).getContext().getRelaVOList();
                if (org.apache.commons.collections4.CollectionUtils.isNotEmpty(relaVOList)) {
                    targetStoreIds.addAll(relaVOList.stream().map(StoreCustomerRelaVO::getStoreId).collect(Collectors.toList()));
                }
            } else if (CustomerType.SUPPLIER == customerType) {
                // 2. 第三方店铺发展而来的客户
                StoreCustomerRelaListByConditionRequest conditionRequest = new StoreCustomerRelaListByConditionRequest();
                conditionRequest.setCustomerType(CustomerType.SUPPLIER);
                conditionRequest.setCustomerId(customerId);
                List<StoreCustomerRelaVO> relaVOList = storeCustomerQueryProvider.listByCondition(conditionRequest).getContext().getRelaVOList();
                if (CollectionUtils.isNotEmpty(relaVOList)) {
                    targetStoreIds.addAll(relaVOList.stream().map(StoreCustomerRelaVO::getStoreId).collect(Collectors.toList()));
                }
            }

            // 3. 循环发送消息
            for (Long targetStoreId : targetStoreIds) {
                // 区分平台/商家节点
                String nodeCode = Constants.BOSS_DEFAULT_STORE_ID.equals(targetStoreId)
                        ? BossMessageNode.CUSTOMER_LOGOUT.getCode() : SupplierMessageNode.CUSTOMER_LOGOUT.getCode();
                // 发送
                storeMessageService.convertAndSend(
                        targetStoreId,
                        nodeCode,
                        Lists.newArrayList(customerVO.getCustomerAccount()),
                        MutableMap.of("account", customerVO.getCustomerAccount()));
            }
        } catch (Exception e) {
            log.error("客户注销提醒，消息处理失败，{}", customerId, e);
        }
    }

    /**
     * 新增客户注册待审核
     */
    public void handleForCustomerAudit(CustomerVO customerVO) {
        try {
            // 客户审核是否需审核
            boolean audit = auditQueryProvider.isCustomerAudit().getContext().isAudit();
            if (audit) {
                // 仅客户审核开启时，发送消息
                storeMessageService.convertAndSend(
                        Constants.BOSS_DEFAULT_STORE_ID,
                        BossMessageNode.CUSTOMER_WAIT_AUDIT.getCode(),
                        Lists.newArrayList(customerVO.getCustomerAccount()),
                        MutableMap.of("account", customerVO.getCustomerAccount()));
            }
        } catch (Exception e) {
            log.error("新增客户注册待审核, 消息处理失败，{}", customerVO, e);
        }
    }

    /**
     * 新增企业客户注册待审核
     */
    public void handleForEnterpriseAudit(CustomerVO customerVO) {
        try {
            if (EnterpriseCheckState.WAIT_CHECK == customerVO.getEnterpriseCheckState()) {
                storeMessageService.convertAndSend(
                        Constants.BOSS_DEFAULT_STORE_ID,
                        BossMessageNode.ENTERPRISE_CUSTOMER_WAIT_AUDIT.getCode(),
                        Lists.newArrayList(customerVO.getCustomerAccount()),
                        MutableMap.of("account", customerVO.getCustomerAccount()));
            }
        } catch (Exception e) {
            log.error("新增企业客户注册待审核, 消息处理失败，{} {}", customerVO, e);
        }
    }

    /**
     * 客户提交授信申请
     */
    public void handleForCreditAccountAudit(CustomerVO customerVO) {
        try {
            storeMessageService.convertAndSend(
                    Constants.BOSS_DEFAULT_STORE_ID,
                    BossMessageNode.CREDIT_ACCOUNT_WAIT_AUDIT.getCode(),
                    Lists.newArrayList(customerVO.getCustomerAccount()),
                    MutableMap.of("account", customerVO.getCustomerAccount()));
        } catch (Exception e) {
            log.error("客户提交授信申请, 消息处理失败，{}", customerVO, e);
        }
    }

    // ==================================== 处理商家/供应商的消息发送 START ====================================

    /**
     * 待商家/供应商收货提醒
     */
    public void handleForReturnOrderWaitReceive(ReturnOrderVO returnOrder) {
        try {
            String nodeCode;
            Long storeId;
            if (Objects.nonNull(returnOrder.getProviderId())) {
                // 处理供应商
                nodeCode = ProviderMessageNode.PROVIDER_WAIT_RECEIVE.getCode();
                storeId = Long.valueOf(returnOrder.getProviderId());
            } else {
                // 处理商家
                nodeCode = SupplierMessageNode.SUPPLIER_WAIT_RECEIVE.getCode();
                storeId = returnOrder.getCompany().getStoreId();
            }
            storeMessageService.convertAndSend(
                    storeId,
                    nodeCode,
                    Lists.newArrayList(returnOrder.getId()),
                    MutableMap.of("rid", returnOrder.getId()));
        } catch (Exception e) {
            log.error("待商家/供应商收货提醒, 消息处理失败，{}", returnOrder, e);
        }
    }
}