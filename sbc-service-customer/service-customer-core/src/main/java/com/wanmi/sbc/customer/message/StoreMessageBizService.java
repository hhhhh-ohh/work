package com.wanmi.sbc.customer.message;

import com.google.common.collect.Lists;
import com.wanmi.sbc.common.base.StoreMessageMQRequest;
import com.wanmi.sbc.common.enums.BoolFlag;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.enums.storemessage.BossMessageNode;
import com.wanmi.sbc.common.enums.storemessage.SupplierMessageNode;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.common.util.MutableMap;
import com.wanmi.sbc.customer.api.provider.store.StoreCustomerQueryProvider;
import com.wanmi.sbc.customer.api.provider.store.StoreQueryProvider;
import com.wanmi.sbc.customer.api.request.store.StoreCustomerRelaListByConditionRequest;
import com.wanmi.sbc.customer.api.request.store.StorePageRequest;
import com.wanmi.sbc.customer.bean.enums.CheckState;
import com.wanmi.sbc.customer.bean.enums.CustomerType;
import com.wanmi.sbc.customer.bean.enums.StoreState;
import com.wanmi.sbc.customer.bean.vo.StoreCustomerRelaVO;
import com.wanmi.sbc.customer.bean.vo.StoreVO;
import com.wanmi.sbc.customer.level.model.root.CustomerLevel;
import com.wanmi.sbc.customer.model.root.Customer;
import com.wanmi.sbc.customer.mq.ProducerService;
import io.seata.common.util.CollectionUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @description customer，商家消息具体发送业务服务，统一管理
 * @author malianfeng
 * @date 2022/7/12 14:59
 */
@Slf4j
@Service
public class StoreMessageBizService {

    @Autowired private ProducerService producerService;

    @Autowired private StoreQueryProvider storeQueryProvider;

    @Autowired private StoreCustomerQueryProvider storeCustomerQueryProvider;

    /**
     * 客户升级提醒
     * @param originalRealLevel 原始等级
     * @param willReachLevel 即将达到的等级
     * @param customer 客户信息
     */
    @Async
    public void handleForCustomerUpdateLevel(CustomerLevel originalRealLevel, CustomerLevel willReachLevel, Customer customer) {
        try {
            // 原始等级 != 即将达到的等级，即为升级
            if (ObjectUtils.notEqual(originalRealLevel.getCustomerLevelId(), willReachLevel.getCustomerLevelId())) {

                // 消息接收店铺id列表
                List<Long> targetStoreIds = new ArrayList<>();
                CustomerType customerType = customer.getCustomerType();
                String customerId = customer.getCustomerId();

                // 1. 平台客户/店铺关联的客户
                if (CustomerType.PLATFORM == customerType) {
                    // 1.1 查询所有自营店铺id
                    targetStoreIds.add(Constants.BOSS_DEFAULT_STORE_ID);
                    int maxPageSize = 1000;
                    StorePageRequest storePageRequest = new StorePageRequest();
                    storePageRequest.setPageSize(maxPageSize);
                    storePageRequest.setStoreState(StoreState.OPENING);
                    storePageRequest.setAuditState(CheckState.CHECKED);
                    storePageRequest.setDelFlag(DeleteFlag.NO);
                    storePageRequest.setCompanyType(BoolFlag.NO);
                    List<StoreVO> storeInfos;
                    do {
                        storeInfos = storeQueryProvider.page(storePageRequest).getContext().getStoreVOPage().getContent();
                        targetStoreIds.addAll(storeInfos.stream().map(StoreVO::getStoreId).collect(Collectors.toList()));
                        storePageRequest.setPageNum(storePageRequest.getPageNum() + 1);
                    } while (storeInfos.size() == maxPageSize);

                    // 1.2 查询关联此客户的第三方店铺id
                    StoreCustomerRelaListByConditionRequest conditionRequest = new StoreCustomerRelaListByConditionRequest();
                    conditionRequest.setCustomerType(CustomerType.PLATFORM);
                    conditionRequest.setCustomerId(customerId);
                    List<StoreCustomerRelaVO> relaVOList = storeCustomerQueryProvider.listByCondition(conditionRequest).getContext().getRelaVOList();
                    if (CollectionUtils.isNotEmpty(relaVOList)) {
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
                            ? BossMessageNode.CUSTOMER_UPGRADE.getCode() : SupplierMessageNode.CUSTOMER_UPGRADE.getCode();
                    // 封装发送请求
                    StoreMessageMQRequest mqRequest = new StoreMessageMQRequest();
                    mqRequest.setStoreId(targetStoreId);
                    mqRequest.setNodeCode(nodeCode);
                    mqRequest.setProduceTime(LocalDateTime.now());
                    mqRequest.setContentParams(Lists.newArrayList(customer.getCustomerAccount(), willReachLevel.getCustomerLevelName()));
                    mqRequest.setRouteParams(MutableMap.of("account", customer.getCustomerAccount()));
                    producerService.sendStoreMessage(mqRequest);
                }
            }
        } catch (Exception e) {
            log.error("客户升级提醒，消息处理失败，{}", customer, e);
        }
    }
}

