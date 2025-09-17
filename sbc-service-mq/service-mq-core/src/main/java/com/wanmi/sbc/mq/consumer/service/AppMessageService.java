package com.wanmi.sbc.mq.consumer.service;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.crm.api.provider.crmgroup.CrmGroupProvider;
import com.wanmi.sbc.crm.api.request.crmgroup.CrmGroupRequest;
import com.wanmi.sbc.customer.api.provider.customer.CustomerQueryProvider;
import com.wanmi.sbc.customer.api.request.customer.CustomerDetailPageRequest;
import com.wanmi.sbc.customer.api.request.customer.CustomerListCustomerIdByPageableRequest;
import com.wanmi.sbc.customer.bean.vo.CustomerDetailForPageVO;
import com.wanmi.sbc.message.api.provider.appmessage.AppMessageProvider;
import com.wanmi.sbc.message.api.provider.messagesend.MessageSendProvider;
import com.wanmi.sbc.message.api.provider.messagesend.MessageSendQueryProvider;
import com.wanmi.sbc.message.api.request.appmessage.AppMessageAddRequest;
import com.wanmi.sbc.message.api.request.appmessage.ModifyAppMessageStatusRequest;
import com.wanmi.sbc.message.api.request.messagesend.MessageSendByIdRequest;
import com.wanmi.sbc.message.api.response.appmessage.AppMessageAddResponse;
import com.wanmi.sbc.message.api.response.messagesend.MessageSendByIdResponse;
import com.wanmi.sbc.message.bean.constant.ReceiveGroupType;
import com.wanmi.sbc.message.bean.enums.MessageSendType;
import com.wanmi.sbc.message.bean.enums.MessageType;
import com.wanmi.sbc.message.bean.enums.SendStatus;
import com.wanmi.sbc.message.bean.vo.AppMessageVO;
import com.wanmi.sbc.message.bean.vo.MessageSendCustomerScopeVO;
import com.wanmi.sbc.message.bean.vo.MessageSendVO;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * @author lvzhenwei
 * @className AppMessageService
 * @description app-message站内信发送处理
 * @date 2021/8/19 1:40 下午
 */
@Slf4j
@Service
public class AppMessageService {

    @Autowired private MessageSendQueryProvider messageSendQueryProvider;

    @Autowired private CustomerQueryProvider customerQueryProvider;

    @Autowired private AppMessageProvider appMessageProvider;

    @Autowired private CrmGroupProvider crmGroupProvider;

    @Autowired private RedissonClient redissonClient;

    @Autowired private MessageSendProvider messageSendProvider;

    private static final String SEND_MESSAGE_LOCK_KEY = "send_message:";

    @Bean
    public Consumer<Message<String>> mqAppMessageService() {
        return this::extracted;
    }

    private void extracted(Message<String> message) {
        String json = message.getPayload();
        MessageSendByIdRequest messageSendByIdRequest =
                new MessageSendByIdRequest(Long.valueOf(json));
        // 分布式锁，防止同一个任务同时执行
        RLock rLock = redissonClient.getFairLock(SEND_MESSAGE_LOCK_KEY + json);
        rLock.lock();
        try {
            // 将发送任务状态改为发送中
            changeMessageSendStatus(SendStatus.BEGIN, messageSendByIdRequest.getMessageId());
            // 查询发送任务详情
            BaseResponse<MessageSendByIdResponse> baseResponse =
                    messageSendQueryProvider.getById(messageSendByIdRequest);
            MessageSendByIdResponse response = baseResponse.getContext();
            MessageSendVO messageSendVO = response.getMessageSendVO();
            List<MessageSendCustomerScopeVO> scopeVOList = messageSendVO.getScopeVOList();
            // 封装app消息
            AppMessageVO appMessageVO = new AppMessageVO();
            appMessageVO.setMessageType(MessageType.Preferential);
            appMessageVO.setImgUrl(messageSendVO.getImgUrl());
            appMessageVO.setTitle(messageSendVO.getTitle());
            appMessageVO.setContent(messageSendVO.getContent());
            appMessageVO.setSendTime(messageSendVO.getSendTime());
            appMessageVO.setJoinId(messageSendVO.getMessageId());
            appMessageVO.setRouteParam(
                    this.replaceRoute(messageSendVO.getRouteParams()).toJSONString());
            appMessageVO.setPcRouteParam(
                    this.replaceRoute(messageSendVO.getPcRouteParams()).toJSONString());
            // 处理消息发送
            handleMessageSend(messageSendVO, scopeVOList, appMessageVO);
            // 将发送任务状态改为发送成功
            changeMessageSendStatus(SendStatus.END, messageSendByIdRequest.getMessageId());
        } catch (Exception e) {
            e.printStackTrace();
            // 将任务状态改为失败
            log.error("app push任务发送失败，任务id{}",messageSendByIdRequest.getMessageId());
            changeMessageSendStatus(SendStatus.FAILED, messageSendByIdRequest.getMessageId());
        } finally {
            rLock.unlock();
        }
    }

    /**
     * @description 修改发送状态
     * @author lvzhenwei
     * @date 2021/8/19 3:36 下午
     * @param sendStatus
     * @param messageId
     * @return void
     */
    private void changeMessageSendStatus(SendStatus sendStatus, Long messageId) {
        ModifyAppMessageStatusRequest request = new ModifyAppMessageStatusRequest();
        request.setMessageId(messageId);
        request.setStatus(sendStatus);
        messageSendProvider.modifySendStatus(request);
    }

    /**
     * @description 处理消息发送
     * @author lvzhenwei
     * @date 2021/8/19 1:46 下午
     * @param messageSendVO
     * @param scopeVOList
     * @param appMessageVO
     * @return void
     */
    @GlobalTransactional
    public void handleMessageSend(
            MessageSendVO messageSendVO,
            List<MessageSendCustomerScopeVO> scopeVOList,
            AppMessageVO appMessageVO) {
        Long messageId = messageSendVO.getMessageId();
        log.info(
                "根据消息任务ID：{},关联的接收人：{}，APP消息详情信息：{},消息发送开始！",
                messageId,
                messageSendVO.getMessageType(),
                appMessageVO);
        // 接收人列表
        List<String> joinIds =
                scopeVOList.stream()
                        .map(MessageSendCustomerScopeVO::getJoinId)
                        .collect(Collectors.toList());
        Integer pageNum = NumberUtils.INTEGER_ZERO;
        Integer pageSize = 1000;
        while (true) {
            List<String> customerIds;
            // 指定用户
            if (MessageSendType.CUSTOMER.equals(messageSendVO.getSendType())) {
                customerIds = joinIds;
                BaseResponse<AppMessageAddResponse> baseResponse =
                        appMessageProvider.addBatch(
                                new AppMessageAddRequest(appMessageVO, customerIds));
                if (CommonErrorCodeEnum.K000000.getCode().equals(baseResponse.getCode())) {
                    log.info("根据消息任务ID：{},消息发送，指定用户，发送成功========>>更新任务状态&&移除此任务", messageId);
                    // TODO
                }
                break;
            }
            // 全部用户
            if (MessageSendType.ALL.equals(messageSendVO.getSendType())) {
                CustomerDetailPageRequest request = new CustomerDetailPageRequest();
                request.setPageSize(pageSize);
                request.setPageNum(pageNum);
                List<CustomerDetailForPageVO> detailResponseList =
                        customerQueryProvider.page(request).getContext().getDetailResponseList();
                if (CollectionUtils.isNotEmpty(detailResponseList)) {
                    customerIds =
                            detailResponseList.stream()
                                    .map(CustomerDetailForPageVO::getCustomerId)
                                    .collect(Collectors.toList());
                } else {
                    customerIds = null;
                }
            } else if (MessageSendType.LEVEL.equals(messageSendVO.getSendType())) {
                // 指定等级
                List<Long> ids =
                        joinIds.stream().map(id -> Long.valueOf(id)).collect(Collectors.toList());
                CustomerListCustomerIdByPageableRequest levelRequest =
                        new CustomerListCustomerIdByPageableRequest();
                levelRequest.setPageSize(pageSize);
                levelRequest.setPageNum(pageNum);
                levelRequest.setCustomerLevelIds(ids);
                customerIds =
                        customerQueryProvider
                                .listCustomerIdByPageable(levelRequest)
                                .getContext()
                                .getCustomerIds();
            } else if (MessageSendType.GROUP.equals(messageSendVO.getSendType())) {
                // 指定人群
                List<Long> sysGroupList = new ArrayList<>();
                List<Long> customGroupList = new ArrayList<>();
                for (String str : joinIds) {
                    String[] arr = str.split("_");
                    if (arr[0].equals(ReceiveGroupType.CUSTOM)) {
                        customGroupList.add(Long.valueOf(arr[1]));
                    }
                    if (arr[0].equals(ReceiveGroupType.SYS)) {
                        sysGroupList.add(Long.valueOf(arr[1]));
                    }
                }
                // 查询系统人群和自定义人群的会员（去重）
                CrmGroupRequest crmGroupRequest =
                        CrmGroupRequest.builder()
                                .sysGroupList(sysGroupList)
                                .customGroupList(customGroupList)
                                .build();
                crmGroupRequest.setPageNum(pageNum);
                crmGroupRequest.setPageSize(pageSize);
                customerIds = crmGroupProvider.queryListByGroupId(crmGroupRequest).getContext();
            } else {
                log.info(
                        "根据消息任务ID：{},消息接收类型：{}，消息类型不符合要求，请检查相关信息",
                        messageId,
                        messageSendVO.getSendType());
                break;
            }
            log.info(
                    "根据消息任务ID：{},消息接收类型：{}，消息发送指定ID集合详情信息：{}，开始发送消息！",
                    messageId,
                    messageSendVO.getSendType(),
                    customerIds);
            if(CollectionUtils.isEmpty(customerIds)){
                break;
            }
            appMessageProvider.addBatch(new AppMessageAddRequest(appMessageVO, customerIds));
            pageNum++;
        }
    }

    private JSONObject replaceRoute(String route) {
        JSONObject params = new JSONObject();
        if (StringUtils.isNotBlank(route)) {
            String router = route.replace("'", "\"");
            JSONObject jsonObject = JSONObject.parseObject(router);
            String link = jsonObject.getString("linkKey");
            JSONObject info = jsonObject.getJSONObject("info");
            switch (link) {
                case "goodsList":
                    String skuId = info.getString("skuId");
                    params.put("type", 0);
                    params.put("skuId", skuId);
                    break;
                case "categoryList":
                    JSONArray selectedKeys = info.getJSONArray("selectedKeys");
                    String pathNames = info.getString("pathName");
                    String[] names = pathNames.split(",");
                    String cateId = selectedKeys.getString(selectedKeys.size() - 1);
                    String cateName = names[names.length - 1];
                    params.put("type", 3);
                    params.put("cateId", cateId);
                    params.put("cateName", cateName);
                    break;
                case "storeList":
                    String storeId = info.getString("storeId");
                    params.put("type", 4);
                    params.put("storeId", storeId);
                    break;
                case "promotionList":
                    params.put("type", 5);
                    String cateKey = info.getString("cateKey");
                    if ("groupon".equals(cateKey)) {
                        String goodsInfoId = info.getString("goodsInfoId");
                        params.put("node", 0);
                        params.put("skuId", goodsInfoId);
                    } else if ("full".equals(cateKey)) {
                        String marketingId = info.getString("marketingId");
                        params.put("node", 2);
                        params.put("mid", marketingId);
                    } else if ("flash".equals(cateKey)) {
                        params.put("node", 1);
                        String goodsInfoId = info.getString("goodsInfoId");
                        params.put("skuId", goodsInfoId);
                    } else if ("onePrice".equals(cateKey)) {
                        String marketingId = info.getString("marketingId");
                        params.put("node", 2);
                        params.put("mid", marketingId);
                    } else if ("halfPrice".equals(cateKey)) {
                        String marketingId = info.getString("marketingId");
                        params.put("node", 2);
                        params.put("mid", marketingId);
                    } else if ("comBuy".equals(cateKey)) {
                        String goodsInfoId = info.getString("goodsInfoId");
                        params.put("node", 3);
                        params.put("skuId", goodsInfoId);
                    } else if ("preOrder".equals(cateKey)) {
                        String goodsInfoId = info.getString("goodsInfoId");
                        params.put("node", 1);
                        params.put("skuId", goodsInfoId);
                    } else if ("preSell".equals(cateKey)) {
                        String goodsInfoId = info.getString("goodsInfoId");
                        params.put("node", 1);
                        params.put("skuId", goodsInfoId);
                    }
                    break;
                case "userpageList":
                    params.put("type", 12);
                    String appPath = info.getString("appPath");
                    String wechatPath = info.getString("wechatPath");
                    String path = info.getString("path");
                    String key = info.getString("key");
                    String linkUrl = info.getString("link");
                    params.put("router", appPath);
                    params.put("wechatPath", wechatPath);
                    params.put("path", path);
                    params.put("key", key);
                    params.put("linkUrl", linkUrl);
                    break;
                case "pageList":
                    params.put("type", 6);
                    String pageType = info.getString("pageType");
                    String pageCode = info.getString("pageCode");
                    params.put("pageType", pageType);
                    params.put("pageCode", pageCode);
                    break;
                case "miniLink":
                    params.put("type", 13);
                    params.put("pageId", info.getString("pageId"));
                    break;
                default:
                    break;
            }
        }
        return params;
    }
}
