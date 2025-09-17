package com.wanmi.sbc.message.mqconsumer;

import com.alibaba.fastjson2.JSON;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.wanmi.sbc.common.base.MessageMQRequest;
import com.wanmi.sbc.common.base.StoreMessageMQRequest;
import com.wanmi.sbc.common.enums.BoolFlag;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.enums.TaskBizType;
import com.wanmi.sbc.common.enums.TaskResult;
import com.wanmi.sbc.common.enums.TriggerNodeType;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.empower.api.provider.apppush.AppPushProvider;
import com.wanmi.sbc.empower.api.provider.minimsgcustomerrecord.MiniMsgCustomerRecordQueryProvider;
import com.wanmi.sbc.empower.api.provider.sellplatform.miniprogramsubscribe.PlatformMiniMsgProvider;
import com.wanmi.sbc.empower.api.request.apppush.AppPushQueryRequest;
import com.wanmi.sbc.empower.api.request.minimsgcustomerrecord.MiniMsgCusRecordByActivityIdRequest;
import com.wanmi.sbc.empower.api.request.minimsgcustomerrecord.MiniMsgCustomerRecordListRequest;
import com.wanmi.sbc.empower.api.request.sellplatform.goods.PlatformSendMiniMsgRequest;
import com.wanmi.sbc.empower.api.response.apppush.AppPushQueryResponse;
import com.wanmi.sbc.empower.api.response.minimsgcustomerrecord.MiniMsgCustomerRecordByActivityIdResponse;
import com.wanmi.sbc.empower.api.response.minimsgcustomerrecord.MiniMsgCustomerRecordListResponse;
import com.wanmi.sbc.empower.bean.enums.AppPushAppType;
import com.wanmi.sbc.empower.bean.enums.EmpowerErrorCodeEnum;
import com.wanmi.sbc.empower.bean.vo.MiniMsgCustomerRecordVO;
import com.wanmi.sbc.message.SmsProxy;
import com.wanmi.sbc.message.api.request.smsbase.SmsSendRequest;
import com.wanmi.sbc.message.bean.enums.ProgramSendStatus;
import com.wanmi.sbc.message.bean.enums.PushPlatform;
import com.wanmi.sbc.message.bean.enums.PushStatus;
import com.wanmi.sbc.message.bean.enums.ReadFlag;
import com.wanmi.sbc.message.bean.enums.StoreMessageType;
import com.wanmi.sbc.message.bean.enums.StoreNoticeSendStatus;
import com.wanmi.sbc.message.bean.vo.PushSendVO;
import com.wanmi.sbc.message.bean.vo.StoreNoticeSendVO;
import com.wanmi.sbc.message.handle.MessageDelivery;
import com.wanmi.sbc.message.minimsgactivitysetting.model.root.MiniMsgActivitySetting;
import com.wanmi.sbc.message.minimsgactivitysetting.service.MiniMsgActivitySettingService;
import com.wanmi.sbc.message.minimsgtempsetting.model.root.MiniMsgTempSetting;
import com.wanmi.sbc.message.minimsgtempsetting.service.MiniMsgTempSettingService;
import com.wanmi.sbc.message.pushdetail.model.root.PushDetail;
import com.wanmi.sbc.message.pushdetail.service.PushDetailService;
import com.wanmi.sbc.message.smssenddetail.model.root.SmsSendDetail;
import com.wanmi.sbc.message.storemessagedetail.model.root.StoreMessageDetail;
import com.wanmi.sbc.message.storemessagedetail.service.StoreMessageDetailService;
import com.wanmi.sbc.message.storenoticesend.service.StoreNoticeSendService;
import com.wanmi.sbc.setting.api.provider.TaskLogProvider;
import com.wanmi.sbc.setting.api.provider.storemessagenode.StoreMessageNodeQueryProvider;
import com.wanmi.sbc.setting.api.provider.storemessagenodesetting.StoreMessageNodeSettingQueryProvider;
import com.wanmi.sbc.setting.api.request.TaskLogAddRequest;
import com.wanmi.sbc.setting.api.request.storemessagenode.StoreMessageNodeByIdRequest;
import com.wanmi.sbc.setting.api.request.storemessagenodesetting.StoreMessageNodeSettingListRequest;
import com.wanmi.sbc.setting.bean.vo.StoreMessageNodeSettingVO;
import com.wanmi.sbc.setting.bean.vo.StoreMessageNodeVO;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

/**
 * @author lvzhenwei
 * @className MessageMqConsumerService
 * @description mq消费者方法处理
 * @date 2021/8/11 3:24 下午
 **/
@Slf4j
@Service
public class MessageMqConsumerService {

    @Resource
    private Set<MessageDelivery> messageHandlerList;

    @Autowired
    private AppPushProvider appPushProvider;

    @Autowired
    private PushDetailService pushDetailService;

    @Autowired
    private StoreMessageDetailService storeMessageDetailService;

    @Autowired
    private StoreNoticeSendService storeNoticeSendService;

    @Autowired
    private TaskLogProvider taskLogProvider;

    @Autowired
    private StoreMessageNodeSettingQueryProvider storeMessageNodeSettingQueryProvider;

    @Autowired
    private StoreMessageNodeQueryProvider storeMessageNodeQueryProvider;

    @Autowired
    private MiniMsgCustomerRecordQueryProvider miniMsgCustomerRecordQueryProvider;

    @Autowired
    private MiniMsgTempSettingService miniMsgTempSettingService;

    @Autowired
    private MiniMsgActivitySettingService miniMsgActivitySettingService;

    @Autowired
    private PlatformMiniMsgProvider platformMiniMsgProvider;

    @Autowired
    private SmsProxy smsProxy;

    public void smsMessageSend(String json) {
        MessageMQRequest request = null;
        try {
            request = JSON.parseObject(json, MessageMQRequest.class);
        } catch (Exception e) {
            log.error("消息消费错误，消息内容无法转换! 消息内容:{}", json, e);
        }
        String currentNode = "";
        if (!CollectionUtils.isEmpty(messageHandlerList)) {
            for (MessageDelivery messageDelivery : messageHandlerList) {
                if (Objects.isNull(messageDelivery.messageHandlerName())) {
                    continue;
                }
                currentNode = messageDelivery.messageHandlerName();
                try {
                    messageDelivery.handle(request);
                } catch (Exception e) {
                    log.error("消息发送错误！{}通知消费异常", currentNode, e);
                }
            }
        }
    }

    /**
     * @description 运营计划push查询推送详情
     * @author  lvzhenwei
     * @date 2021/8/11 6:14 下午
     * @param json
     * @return void
     **/
    public void smsPushQuery(String json){
        log.info("=============== PushQueryConsumer处理start ===============");
        PushSendVO pushSendVO = JSON.parseObject(json, PushSendVO.class);
        if (StringUtils.isNotBlank(pushSendVO.getIosTaskId())){
            AppPushQueryRequest appPushQueryRequest =
                    AppPushQueryRequest.builder().taskId(pushSendVO.getIosTaskId()).type(AppPushAppType.IOS).build();
            AppPushQueryResponse resultEntry = appPushProvider.query(appPushQueryRequest).getContext();
            if (Boolean.TRUE.equals(resultEntry.getSuccess())){
                PushDetail detail = new PushDetail();
                detail.setTaskId(resultEntry.getTaskId());
                detail.setPlatform(PushPlatform.IOS);
                detail.setOpenSum(resultEntry.getOpenCount());
                detail.setSendStatus(PushStatus.fromValue(resultEntry.getStatus()));
                detail.setSendSum(resultEntry.getSentCount());
                detail.setCreateTime(LocalDateTime.now());
                detail.setPlanId(pushSendVO.getPlanId());
                pushDetailService.add(detail);
            } else {
                log.error("PushSendQueryController.detail::友盟iOS查询接口失败");
                throw new SbcRuntimeException(EmpowerErrorCodeEnum.K060020, new Object[]{"iOS查询"});
            }
        } else if (StringUtils.isNotBlank(pushSendVO.getAndroidTaskId())){
            AppPushQueryRequest appPushQueryRequest =
                    AppPushQueryRequest.builder().taskId(pushSendVO.getAndroidTaskId()).type(AppPushAppType.ANDROID).build();
            AppPushQueryResponse resultEntry = appPushProvider.query(appPushQueryRequest).getContext();
            if (Boolean.TRUE.equals(resultEntry.getSuccess())){
                PushDetail detail = new PushDetail();
                detail.setTaskId(resultEntry.getTaskId());
                detail.setPlatform(PushPlatform.ANDROID);
                detail.setOpenSum(resultEntry.getOpenCount());
                detail.setSendStatus(PushStatus.fromValue(resultEntry.getStatus()));
                detail.setSendSum(resultEntry.getSentCount());
                detail.setCreateTime(LocalDateTime.now());
                detail.setPlanId(pushSendVO.getPlanId());
                pushDetailService.add(detail);
            } else {
                log.error("PushSendQueryController.detail::友盟android查询接口失败");
                throw new SbcRuntimeException(EmpowerErrorCodeEnum.K060020, new Object[]{"android查询"});
            }
        }
        log.info("=============== PushQueryConsumer处理end ===============");
    }

    /**
     * @description 验证码短信发送
     * @author  lvzhenwei
     * @date 2021/8/16 11:42 上午
     * @param json
     * @return void
     **/
    public void smsSendMessageCode(String json){
        log.info("接收到发验证码短信的MQ消息：{}", json);
        SmsSendRequest request = JSON.parseObject(json, SmsSendRequest.class);
        SmsSendDetail smsSend = new SmsSendDetail();
        KsBeanUtil.copyPropertiesThird(request, smsSend);
        if(Objects.nonNull(request.getTemplateParamDTO())) {
            smsSend.setTemplateParam(JSON.toJSONString(request.getTemplateParamDTO()));
        }
        smsProxy.sendSms(smsSend);
    }

    /**
     * @description 短信发送
     * @author  lvzhenwei
     * @date 2021/8/17 3:58 下午
     * @param json
     * @return void
     **/
    public void smsSendMessage(String json){
        log.info("接收到短信发送的MQ消息：{}", json);
        SmsSendDetail smsSendDetail = JSON.parseObject(json, SmsSendDetail.class);
        smsProxy.sendSms(smsSendDetail);
    }

    /**
     * @description 商家公告发送
     * @author malianfeng
     * @date 2022/7/13 11:34
     * @param json
     * @return void
     */
    public void storeNoticeSend(String json) {
        log.info("=============== storeNoticeSendConsumer 处理start ===============");
        Long noticeId = JSON.parseObject(json, Long.class);
        try {
            // 0. 再查一遍最新的详情
            StoreNoticeSendVO noticeSendVO = storeNoticeSendService.getOneWithScope(noticeId);
            if (Objects.isNull(noticeSendVO)) {
                // 公告不存在，直接返回
                return;
            }
            if (StoreNoticeSendStatus.NOT_SENT != noticeSendVO.getSendStatus()) {
                // 状态非未发送，直接返回
                return;
            }
            // 1. 公告状态置为发送中
            storeNoticeSendService.modifySendStatusById(noticeSendVO.getId(), StoreNoticeSendStatus.SENDING);
            // 2. 处理消息发送
            storeMessageDetailService.handleSendNotice(noticeSendVO);
        } catch (Exception e) {
            log.error("商家公告任务发送失败，data: {}", json, e);
            // 将任务状态改为失败
            storeNoticeSendService.modifySendStatusById(noticeId, StoreNoticeSendStatus.SEND_FAIL);
            // 新增定时任务异常日志
            taskLogProvider.add(TaskLogAddRequest.builder()
                    .bizId(String.valueOf(noticeId))
                    .storeId(Constants.BOSS_DEFAULT_STORE_ID)
                    .remarks("商家公告发送任务，发生异常! data: " + json)
                    .taskResult(TaskResult.EXECUTE_FAIL)
                    .taskBizType(TaskBizType.STORE_NOTICE_SEND)
                    .stackMessage(ExceptionUtils.getStackTrace(e))
                    .createTime(LocalDateTime.now()).build());
            throw e;
        }
        log.info("=============== storeNoticeSendConsumer 处理end ===============");
    }

    /**
     * @description 商家消息发送
     * @author malianfeng
     * @date 2022/7/13 11:34
     * @param json
     * @return void
     */
    public void storeMessageSend(String json) {
        log.info("=============== StoreMessageSendConsumer处理start ===============");
        StoreMessageMQRequest request = null;
        try {
            request = JSON.parseObject(json, StoreMessageMQRequest.class);
            // 1. 查询目标商家的节点开关是否开启
            List<StoreMessageNodeSettingVO> nodeSettingList = storeMessageNodeSettingQueryProvider.list(StoreMessageNodeSettingListRequest.builder()
                    .storeId(request.getStoreId()).nodeCode(request.getNodeCode()).delFlag(DeleteFlag.NO).build()).getContext().getStoreMessageNodeSettingVOList();
            StoreMessageNodeSettingVO nodeSetting = nodeSettingList.stream().findFirst().orElse(null);
            // 2. 存在开关，且处于开启状态
            if (Objects.nonNull(nodeSetting) && BoolFlag.YES == nodeSetting.getStatus()) {
                StoreMessageNodeVO node = storeMessageNodeQueryProvider.getById(StoreMessageNodeByIdRequest.builder()
                        .id(nodeSetting.getNodeId()).build()).getContext().getStoreMessageNodeVO();
                if (Objects.nonNull(node)) {
                    // 标题前缀，【消息类型去掉"通知/提醒"】
                    String prefix = String.format("【%s】", node.getTypeName().replaceAll("通知|提醒", ""));
                    // 根据模板填充消息内容构造标题
                    String title = prefix + fillContextByParams(node.getNodeContext(), request.getContentParams());
                    // 路由参数填充节点标识
                    request.getRouteParams().put("nodeCode",  node.getNodeCode());
                    // 构造实体对象
                    StoreMessageDetail detail = new StoreMessageDetail();
                    detail.setStoreId(request.getStoreId());
                    detail.setJoinId(node.getId());
                    detail.setTitle(title);
                    detail.setRouteParam(JSON.toJSONString(request.getRouteParams()));
                    detail.setMessageType(StoreMessageType.MESSAGE);
                    detail.setIsRead(ReadFlag.NO);
                    detail.setSendTime(request.getProduceTime());
                    detail.setDelFlag(DeleteFlag.NO);
                    detail.setCreateTime(LocalDateTime.now());
                    storeMessageDetailService.add(detail);
                }
            } else {
                log.info("商家:{}未开启节点:{}的通知，不处理该消息", request.getStoreId(), request.getNodeCode());
            }
        } catch (Exception e) {
            log.error("商家消息消费失败，消息内容:{}", json, e);
        }
        log.info("=============== StoreMessageSendConsumer处理end ===============");
    }

    public void dealMiniProgramSubscribeMsg(String json){
        PlatformSendMiniMsgRequest request = JSON.parseObject(json, PlatformSendMiniMsgRequest.class);
        // 处理新消息通知
        Long activityId = request.getActivityId();
        MiniMsgActivitySetting response = null;
        // 查询活动是否已删除
        if (Objects.nonNull(activityId)) {
            response =
                    miniMsgActivitySettingService.getOne(activityId);
            if (response.getDelFlag() == DeleteFlag.YES
                    || ProgramSendStatus.NOT_SEND != response.getSendStatus()){
                return;
            }
            // 更新活动状态为推送中
            miniMsgActivitySettingService.modifyById(ProgramSendStatus.SENDING, Constants.ZERO, Constants.ZERO, activityId);
        }
        switch (request.getTriggerNodeId()){
            case GROUPON_SUCCESS:
                // 处理拼团成功
                request = dealGroupSuccessMiniProgramSubscribeMsg(request);
                if (Objects.isNull(request)) {
                    return;
                }
                break;
            case GROUPON_FAIL:
                // 处理拼团失败
                request = dealGroupFailMiniProgramSubscribeMsg(request);
                if (Objects.isNull(request)) {
                    return;
                }
                break;
            case COUPON_ISSUANCE:
                // 处理优惠券发放
                request = dealCouponIsSuanceMiniProgramSubscribeMsg(request);
                if (Objects.isNull(request)) {
                    return;
                }
                break;
            case AUTO_CONFIRM_RECEIPT:
                // 处理自动确认收货前24小时
                request = dealAutoConfirmReceiptMiniProgramSubscribeMsg(request);
                if (Objects.isNull(request)) {
                    return;
                }
                break;
            case COUPON_USAGE:
                // 处理优惠券过期前24小时
                request = dealCouponUsageMiniProgramSubscribeMsg(request);
                if (Objects.isNull(request)) {
                    return;
                }
                break;
            case BEFORE_GROUPON_END:
                // 距离拼团结束还剩3小时，且未成团
                request = dealBeforeGrouponEndMiniProgramSubscribeMsg(request);
                if (Objects.isNull(request)) {
                    return;
                }
                break;
            case BALANCE_PAYMENT:
                // 尾款开始支付
                request = dealBalancePaymentMiniProgramSubscribeMsg(request);
                if (Objects.isNull(request)) {
                    return;
                }
                break;
            case BALANCE_PAYMENT_OVERTIME:
                // 尾款支付超时提醒
                request = dealBalancePaymentOverTimeMiniProgramSubscribeMsg(request);
                if (Objects.isNull(request)) {
                    return;
                }
                break;
            case MEMBER_EXPIRATION:
                // 会员到期提醒
                request = dealMemberExpirationMiniProgramSubscribeMsg(request);
                if (Objects.isNull(request)) {
                    return;
                }
                break;
            case REFUND_SUCCESS:
                // 退款回调通知成功
                request = dealRefundSuccessMiniProgramSubscribeMsg(request);
                if (Objects.isNull(request)) {
                    return;
                }
                break;
            case ORDER_DELIVERY:
                // 发货
                request = dealOrderDeliveryMiniProgramSubscribeMsg(request);
                if (Objects.isNull(request)) {
                    return;
                }
                break;
            default:
                break;
        }

        platformMiniMsgProvider.sendMiniProgramSubscribeMessage(request);
        if (Objects.nonNull(activityId)) {
            // 查询推送数量
            MiniMsgCustomerRecordByActivityIdResponse messageCustomerRecordByActivityIdResponse =
                    miniMsgCustomerRecordQueryProvider.countRecordsByActivityId(
                    MiniMsgCusRecordByActivityIdRequest.builder()
                            .activityId(activityId).build()).getContext();
            // 预计推送人数
            int preCount = messageCustomerRecordByActivityIdResponse.getReturnNum().intValue();
            // 推送成功数量
            int realCountInt = messageCustomerRecordByActivityIdResponse.getReturnSuccessNum().intValue();
            if (realCountInt == 0){
                // 更新活动状态为推送失败
                miniMsgActivitySettingService.modifyById(ProgramSendStatus.SEND_FAIL, preCount, Constants.ZERO, activityId);
            }else if (realCountInt<preCount){
                // 更新活动状态为部分失败
                miniMsgActivitySettingService.modifyById(ProgramSendStatus.SEND_PART, preCount, realCountInt, activityId);
            }else {
                miniMsgActivitySettingService.modifyById(ProgramSendStatus.SEND_DOWN, preCount, realCountInt, activityId);
            }
        }
    }

    /**
     * @description 发货通知成功
     * @author xufeng
     * @date 2022/9/2 17:34
     * @param messageRequest
     * @return void
     */
    public PlatformSendMiniMsgRequest dealOrderDeliveryMiniProgramSubscribeMsg(PlatformSendMiniMsgRequest messageRequest) {
        String customerId = messageRequest.getCustomerId();
        TriggerNodeType triggerNodeId = messageRequest.getTriggerNodeId();
        // 异步处理小程序订阅消息发送
        FindMiniProgramTemplateAndCustomerRecord findMiniProgramTemplateAndCustomerRecord =
                new FindMiniProgramTemplateAndCustomerRecord(customerId, triggerNodeId).invoke();
        // 获取模板配置
        MiniMsgTempSetting miniMsgTempSetting =
                findMiniProgramTemplateAndCustomerRecord.getMiniProgramSubscribeTemplateSetting();
        // 获取用户授权信息
        List<MiniMsgCustomerRecordVO> miniMsgCustomerRecordVOList =
                findMiniProgramTemplateAndCustomerRecord.getMiniProgramSubscribeMessageCustomerRecordVOList();
        if (org.apache.commons.collections.CollectionUtils.isNotEmpty(miniMsgCustomerRecordVOList)){
            // 取一条发送消息
            MiniMsgCustomerRecordVO messageCustomerRecordVO =
                    miniMsgCustomerRecordVOList.get(0);
            List<String> toUsers = Lists.newArrayList();
            toUsers.add(messageCustomerRecordVO.getOpenId());
            Map<String, Object> map1 = Maps.newHashMapWithExpectedSize(Constants.ONE);
            map1.put("value", messageRequest.getTradeId());
            Map<String, Object> map2 = Maps.newHashMapWithExpectedSize(Constants.ONE);
            map2.put("value", messageRequest.getDeliverTime());
            Map<String, Object> map3 = Maps.newHashMapWithExpectedSize(Constants.ONE);
            map3.put("value", messageRequest.getDeliverNo());
            Map<String, Object> map4 = Maps.newHashMapWithExpectedSize(Constants.ONE);
            map4.put("value", messageRequest.getExpressName());
            Map<String, Object> map = Maps.newHashMapWithExpectedSize(Constants.ONE);
            map.put("number2", map1);
            map.put("date4", map2);
            map.put("character_string13", map3);
            map.put("thing14", map4);
            // 组装入参
            return PlatformSendMiniMsgRequest.builder()
                    .customerRecordId(messageCustomerRecordVO.getId())
                    .toUsers(toUsers)
                    .templateId(miniMsgTempSetting.getTemplateId())
                    .page(miniMsgTempSetting.getToPage().concat(messageRequest.getTradeId()))
                    .data(map)
                    .build();
        }
        return null;
    }

    /**
     * @description 退款回调通知成功
     * @author xufeng
     * @date 2022/8/29 17:34
     * @param messageRequest
     * @return void
     */
    public PlatformSendMiniMsgRequest dealRefundSuccessMiniProgramSubscribeMsg(PlatformSendMiniMsgRequest messageRequest) {
        String customerId = messageRequest.getCustomerId();
        TriggerNodeType triggerNodeId = messageRequest.getTriggerNodeId();
        // 异步处理小程序订阅消息发送
        FindMiniProgramTemplateAndCustomerRecord findMiniProgramTemplateAndCustomerRecord =
                new FindMiniProgramTemplateAndCustomerRecord(customerId, triggerNodeId).invoke();
        // 获取模板配置
        MiniMsgTempSetting miniMsgTempSetting =
                findMiniProgramTemplateAndCustomerRecord.getMiniProgramSubscribeTemplateSetting();
        // 获取用户授权信息
        List<MiniMsgCustomerRecordVO> miniMsgCustomerRecordVOList =
                findMiniProgramTemplateAndCustomerRecord.getMiniProgramSubscribeMessageCustomerRecordVOList();
        if (org.apache.commons.collections.CollectionUtils.isNotEmpty(miniMsgCustomerRecordVOList)){
            // 取一条发送消息
            MiniMsgCustomerRecordVO messageCustomerRecordVO =
                    miniMsgCustomerRecordVOList.get(0);
            List<String> toUsers = Lists.newArrayList();
            toUsers.add(messageCustomerRecordVO.getOpenId());
            Map<String, Object> map1 = Maps.newHashMapWithExpectedSize(Constants.ONE);
            map1.put("value", messageRequest.getRid());
            Map<String, Object> map2 = Maps.newHashMapWithExpectedSize(Constants.ONE);
            map2.put("value", messageRequest.getFinishTime());
            Map<String, Object> map3 = Maps.newHashMapWithExpectedSize(Constants.ONE);
            map3.put("value", messageRequest.getActualReturnPrice());
            Map<String, Object> map4 = Maps.newHashMapWithExpectedSize(Constants.ONE);
            map4.put("value", messageRequest.getActualPoints());
            Map<String, Object> map5 = Maps.newHashMapWithExpectedSize(Constants.ONE);
            map5.put("value", "原路退款");
            Map<String, Object> map = Maps.newHashMapWithExpectedSize(Constants.ONE);
            map.put("character_string8", map1);
            map.put("time2", map2);
            map.put("amount4", map3);
            map.put("number9", map4);
            map.put("phrase6", map5);
            // 组装入参
            return PlatformSendMiniMsgRequest.builder()
                    .customerRecordId(messageCustomerRecordVO.getId())
                    .toUsers(toUsers)
                    .templateId(miniMsgTempSetting.getTemplateId())
                    .page(miniMsgTempSetting.getToPage().concat(messageRequest.getRid()))
                    .data(map)
                    .build();
        }
        return null;
    }

    /**
     * @description 会员到期提醒
     * @author xufeng
     * @date 2022/8/23 17:34
     * @param messageRequest
     * @return void
     */
    public PlatformSendMiniMsgRequest dealMemberExpirationMiniProgramSubscribeMsg(PlatformSendMiniMsgRequest messageRequest) {
        String customerId = messageRequest.getCustomerId();
        TriggerNodeType triggerNodeId = messageRequest.getTriggerNodeId();
        // 异步处理小程序订阅消息发送
        FindMiniProgramTemplateAndCustomerRecord findMiniProgramTemplateAndCustomerRecord =
                new FindMiniProgramTemplateAndCustomerRecord(customerId, triggerNodeId).invoke();
        // 获取模板配置
        MiniMsgTempSetting miniMsgTempSetting =
                findMiniProgramTemplateAndCustomerRecord.getMiniProgramSubscribeTemplateSetting();
        // 获取用户授权信息
        List<MiniMsgCustomerRecordVO> miniMsgCustomerRecordVOList =
                findMiniProgramTemplateAndCustomerRecord.getMiniProgramSubscribeMessageCustomerRecordVOList();
        if (org.apache.commons.collections.CollectionUtils.isNotEmpty(miniMsgCustomerRecordVOList)){
            // 取一条发送消息
            MiniMsgCustomerRecordVO messageCustomerRecordVO =
                    miniMsgCustomerRecordVOList.get(0);
            String tips = miniMsgTempSetting.getNewTips();
            if (StringUtils.isBlank(tips)) {
                tips = miniMsgTempSetting.getTips();
            }
            List<String> toUsers = Lists.newArrayList();
            toUsers.add(messageCustomerRecordVO.getOpenId());
            Map<String, Object> map1 = Maps.newHashMapWithExpectedSize(Constants.ONE);
            map1.put("value", messageRequest.getPayingMemberName());
            Map<String, Object> map2 = Maps.newHashMapWithExpectedSize(Constants.ONE);
            map2.put("value", messageRequest.getExpirationDate());
            Map<String, Object> map3 = Maps.newHashMapWithExpectedSize(Constants.ONE);
            map3.put("value", tips);
            Map<String, Object> map = Maps.newHashMapWithExpectedSize(Constants.ONE);
            map.put("thing1", map1);
            map.put("date2", map2);
            map.put("thing3", map3);
            // 组装入参
            return PlatformSendMiniMsgRequest.builder()
                    .customerRecordId(messageCustomerRecordVO.getId())
                    .toUsers(toUsers)
                    .templateId(miniMsgTempSetting.getTemplateId())
                    .page(miniMsgTempSetting.getToPage())
                    .data(map)
                    .build();
        }
        return null;
    }

    /**
     * @description 尾款支付超时提醒
     * @author xufeng
     * @date 2022/8/23 17:34
     * @param messageRequest
     * @return void
     */
    public PlatformSendMiniMsgRequest dealBalancePaymentOverTimeMiniProgramSubscribeMsg(PlatformSendMiniMsgRequest messageRequest) {
        String customerId = messageRequest.getCustomerId();
        TriggerNodeType triggerNodeId = messageRequest.getTriggerNodeId();
        // 异步处理小程序订阅消息发送
        FindMiniProgramTemplateAndCustomerRecord findMiniProgramTemplateAndCustomerRecord =
                new FindMiniProgramTemplateAndCustomerRecord(customerId, triggerNodeId).invoke();
        // 获取模板配置
        MiniMsgTempSetting miniMsgTempSetting =
                findMiniProgramTemplateAndCustomerRecord.getMiniProgramSubscribeTemplateSetting();
        // 获取用户授权信息
        List<MiniMsgCustomerRecordVO> miniMsgCustomerRecordVOList =
                findMiniProgramTemplateAndCustomerRecord.getMiniProgramSubscribeMessageCustomerRecordVOList();
        if (org.apache.commons.collections.CollectionUtils.isNotEmpty(miniMsgCustomerRecordVOList)){
            // 取一条发送消息
            MiniMsgCustomerRecordVO messageCustomerRecordVO =
                    miniMsgCustomerRecordVOList.get(0);
            String tips = miniMsgTempSetting.getNewTips();
            if (StringUtils.isBlank(tips)) {
                tips = miniMsgTempSetting.getTips();
            }
            List<String> toUsers = Lists.newArrayList();
            toUsers.add(messageCustomerRecordVO.getOpenId());
            Map<String, Object> map1 = Maps.newHashMapWithExpectedSize(Constants.ONE);
            map1.put("value", messageRequest.getSkuName());
            Map<String, Object> map2 = Maps.newHashMapWithExpectedSize(Constants.ONE);
            map2.put("value", messageRequest.getTailPrice());
            Map<String, Object> map3 = Maps.newHashMapWithExpectedSize(Constants.ONE);
            map3.put("value", messageRequest.getTailEndTime());
            Map<String, Object> map4 = Maps.newHashMapWithExpectedSize(Constants.ONE);
            map4.put("value", tips);
            Map<String, Object> map = Maps.newHashMapWithExpectedSize(Constants.ONE);
            map.put("thing5", map1);
            map.put("amount6", map2);
            map.put("time7", map3);
            map.put("thing4", map4);
            // 组装入参
            return PlatformSendMiniMsgRequest.builder()
                    .customerRecordId(messageCustomerRecordVO.getId())
                    .toUsers(toUsers)
                    .templateId(miniMsgTempSetting.getTemplateId())
                    .page(miniMsgTempSetting.getToPage().concat(messageRequest.getTradeId()))
                    .data(map)
                    .build();
        }
        return null;
    }

    /**
     * @description 尾款开始支付
     * @author xufeng
     * @date 2022/8/23 17:34
     * @param messageRequest
     * @return void
     */
    public PlatformSendMiniMsgRequest dealBalancePaymentMiniProgramSubscribeMsg(PlatformSendMiniMsgRequest messageRequest) {
        String customerId = messageRequest.getCustomerId();
        TriggerNodeType triggerNodeId = messageRequest.getTriggerNodeId();
        // 异步处理小程序订阅消息发送
        FindMiniProgramTemplateAndCustomerRecord findMiniProgramTemplateAndCustomerRecord =
                new FindMiniProgramTemplateAndCustomerRecord(customerId, triggerNodeId).invoke();
        // 获取模板配置
        MiniMsgTempSetting miniMsgTempSetting =
                findMiniProgramTemplateAndCustomerRecord.getMiniProgramSubscribeTemplateSetting();
        // 获取用户授权信息
        List<MiniMsgCustomerRecordVO> miniMsgCustomerRecordVOList =
                findMiniProgramTemplateAndCustomerRecord.getMiniProgramSubscribeMessageCustomerRecordVOList();
        if (org.apache.commons.collections.CollectionUtils.isNotEmpty(miniMsgCustomerRecordVOList)){
            // 取一条发送消息
            MiniMsgCustomerRecordVO messageCustomerRecordVO =
                    miniMsgCustomerRecordVOList.get(0);
            String tips = miniMsgTempSetting.getNewTips();
            if (StringUtils.isBlank(tips)) {
                tips = miniMsgTempSetting.getTips();
            }
            List<String> toUsers = Lists.newArrayList();
            toUsers.add(messageCustomerRecordVO.getOpenId());
            Map<String, Object> map1 = Maps.newHashMapWithExpectedSize(Constants.ONE);
            map1.put("value", messageRequest.getSkuName());
            Map<String, Object> map2 = Maps.newHashMapWithExpectedSize(Constants.ONE);
            map2.put("value", messageRequest.getTailPrice());
            Map<String, Object> map3 = Maps.newHashMapWithExpectedSize(Constants.ONE);
            map3.put("value", messageRequest.getTailEndTime());
            Map<String, Object> map4 = Maps.newHashMapWithExpectedSize(Constants.ONE);
            map4.put("value", tips);
            Map<String, Object> map = Maps.newHashMapWithExpectedSize(Constants.ONE);
            map.put("thing1", map1);
            map.put("amount2", map2);
            map.put("date7", map3);
            map.put("thing4", map4);
            // 组装入参
            return PlatformSendMiniMsgRequest.builder()
                    .customerRecordId(messageCustomerRecordVO.getId())
                    .toUsers(toUsers)
                    .templateId(miniMsgTempSetting.getTemplateId())
                    .page(miniMsgTempSetting.getToPage().concat(messageRequest.getTradeId()))
                    .data(map)
                    .build();
        }
        return null;
    }

    /**
     * @description 距离拼团结束还剩3小时，且未成团
     * @author xufeng
     * @date 2022/8/23 17:34
     * @param messageRequest
     * @return void
     */
    public PlatformSendMiniMsgRequest dealBeforeGrouponEndMiniProgramSubscribeMsg(PlatformSendMiniMsgRequest messageRequest) {
        String customerId = messageRequest.getCustomerId();
        TriggerNodeType triggerNodeId = messageRequest.getTriggerNodeId();
        // 异步处理小程序订阅消息发送
        FindMiniProgramTemplateAndCustomerRecord findMiniProgramTemplateAndCustomerRecord =
                new FindMiniProgramTemplateAndCustomerRecord(customerId, triggerNodeId).invoke();
        // 获取模板配置
        MiniMsgTempSetting miniMsgTempSetting =
                findMiniProgramTemplateAndCustomerRecord.getMiniProgramSubscribeTemplateSetting();
        // 获取用户授权信息
        List<MiniMsgCustomerRecordVO> miniMsgCustomerRecordVOList =
                findMiniProgramTemplateAndCustomerRecord.getMiniProgramSubscribeMessageCustomerRecordVOList();
        if (org.apache.commons.collections.CollectionUtils.isNotEmpty(miniMsgCustomerRecordVOList)){
            // 取一条发送消息
            MiniMsgCustomerRecordVO messageCustomerRecordVO =
                    miniMsgCustomerRecordVOList.get(0);
            String tips = miniMsgTempSetting.getNewTips();
            if (StringUtils.isBlank(tips)) {
                tips = miniMsgTempSetting.getTips();
            }
            List<String> toUsers = Lists.newArrayList();
            toUsers.add(messageCustomerRecordVO.getOpenId());
            Map<String, Object> map1 = Maps.newHashMapWithExpectedSize(Constants.ONE);
            map1.put("value", messageRequest.getSkuName());
            Map<String, Object> map2 = Maps.newHashMapWithExpectedSize(Constants.ONE);
            map2.put("value", messageRequest.getPrice());
            Map<String, Object> map3 = Maps.newHashMapWithExpectedSize(Constants.ONE);
            map3.put("value", messageRequest.getRemainNum());
            Map<String, Object> map4 = Maps.newHashMapWithExpectedSize(Constants.ONE);
            map4.put("value", messageRequest.getRemainDate());
            Map<String, Object> map5 = Maps.newHashMapWithExpectedSize(Constants.ONE);
            map5.put("value", tips);
            Map<String, Object> map = Maps.newHashMapWithExpectedSize(Constants.ONE);
            map.put("thing1", map1);
            map.put("amount4", map2);
            map.put("number2", map3);
            map.put("thing8", map4);
            map.put("thing7", map5);
            // 组装入参
            return PlatformSendMiniMsgRequest.builder()
                    .customerRecordId(messageCustomerRecordVO.getId())
                    .toUsers(toUsers)
                    .templateId(miniMsgTempSetting.getTemplateId())
                    .page(miniMsgTempSetting.getToPage().concat(messageRequest.getSkuId()))
                    .data(map)
                    .build();
        }
        return null;
    }

    /**
     * @description 发送小程序订阅消息-拼团成功
     * @author xufeng
     * @date 2022/8/22 17:34
     * @param messageRequest
     * @return void
     */
    public PlatformSendMiniMsgRequest dealGroupSuccessMiniProgramSubscribeMsg(PlatformSendMiniMsgRequest messageRequest) {
        String customerId = messageRequest.getCustomerId();
        TriggerNodeType triggerNodeId = messageRequest.getTriggerNodeId();
        // 异步处理小程序订阅消息发送
        FindMiniProgramTemplateAndCustomerRecord findMiniProgramTemplateAndCustomerRecord =
                new FindMiniProgramTemplateAndCustomerRecord(customerId, triggerNodeId).invoke();
        // 获取模板配置
        MiniMsgTempSetting miniMsgTempSetting =
                findMiniProgramTemplateAndCustomerRecord.getMiniProgramSubscribeTemplateSetting();
        // 获取用户授权信息
        List<MiniMsgCustomerRecordVO> miniMsgCustomerRecordVOList =
                findMiniProgramTemplateAndCustomerRecord.getMiniProgramSubscribeMessageCustomerRecordVOList();
        if (org.apache.commons.collections.CollectionUtils.isNotEmpty(miniMsgCustomerRecordVOList)){
            // 取一条发送消息
            MiniMsgCustomerRecordVO messageCustomerRecordVO =
                    miniMsgCustomerRecordVOList.get(0);
            String tips = miniMsgTempSetting.getNewTips();
            if (StringUtils.isBlank(tips)) {
                tips = miniMsgTempSetting.getTips();
            }
            List<String> toUsers = Lists.newArrayList();
            toUsers.add(messageCustomerRecordVO.getOpenId());
            Map<String, Object> map1 = Maps.newHashMapWithExpectedSize(Constants.ONE);
            map1.put("value", messageRequest.getSkuName());
            Map<String, Object> map2 = Maps.newHashMapWithExpectedSize(Constants.ONE);
            map2.put("value", messageRequest.getPrice());
            Map<String, Object> map3 = Maps.newHashMapWithExpectedSize(Constants.ONE);
            map3.put("value", messageRequest.getGrouponSuccessTime());
            Map<String, Object> map4 = Maps.newHashMapWithExpectedSize(Constants.ONE);
            map4.put("value", tips);
            Map<String, Object> map = Maps.newHashMapWithExpectedSize(Constants.ONE);
            map.put("thing1", map1);
            map.put("amount2", map2);
            map.put("date7", map3);
            map.put("thing5", map4);
            // 组装入参
            return PlatformSendMiniMsgRequest.builder()
                    .customerRecordId(messageCustomerRecordVO.getId())
                    .toUsers(toUsers)
                    .templateId(miniMsgTempSetting.getTemplateId())
                    .page(miniMsgTempSetting.getToPage().concat(messageRequest.getTradeId()))
                    .data(map)
                    .build();
        }
        return null;
    }

    /**
     * @description 发送小程序订阅消息-拼团失败
     * @author xufeng
     * @date 2022/8/22 17:34
     * @param messageRequest
     * @return void
     */
    public PlatformSendMiniMsgRequest dealGroupFailMiniProgramSubscribeMsg(PlatformSendMiniMsgRequest messageRequest) {
        String customerId = messageRequest.getCustomerId();
        TriggerNodeType triggerNodeId = messageRequest.getTriggerNodeId();
        // 异步处理小程序订阅消息发送
        FindMiniProgramTemplateAndCustomerRecord findMiniProgramTemplateAndCustomerRecord =
                new FindMiniProgramTemplateAndCustomerRecord(customerId, triggerNodeId).invoke();
        // 获取模板配置
        MiniMsgTempSetting miniMsgTempSetting =
                findMiniProgramTemplateAndCustomerRecord.getMiniProgramSubscribeTemplateSetting();
        // 获取用户授权信息
        List<MiniMsgCustomerRecordVO> miniMsgCustomerRecordVOList =
                findMiniProgramTemplateAndCustomerRecord.getMiniProgramSubscribeMessageCustomerRecordVOList();
        if (org.apache.commons.collections.CollectionUtils.isNotEmpty(miniMsgCustomerRecordVOList)){
            // 取一条发送消息
            MiniMsgCustomerRecordVO messageCustomerRecordVO =
                    miniMsgCustomerRecordVOList.get(0);
            List<String> toUsers = Lists.newArrayList();
            toUsers.add(messageCustomerRecordVO.getOpenId());
            Map<String, Object> map1 = Maps.newHashMapWithExpectedSize(Constants.ONE);
            map1.put("value", messageRequest.getSkuName());
            Map<String, Object> map2 = Maps.newHashMapWithExpectedSize(Constants.ONE);
            map2.put("value", messageRequest.getTradeStateDes());
            Map<String, Object> map3 = Maps.newHashMapWithExpectedSize(Constants.ONE);
            map3.put("value", messageRequest.getPrice());
            Map<String, Object> map4 = Maps.newHashMapWithExpectedSize(Constants.ONE);
            map4.put("value", messageRequest.getActualReturnPrice());
            Map<String, Object> map = Maps.newHashMapWithExpectedSize(Constants.ONE);
            map.put("thing1", map1);
            map.put("phrase15", map2);
            map.put("amount6", map3);
            map.put("amount2", map4);
            // 组装入参
            return PlatformSendMiniMsgRequest.builder()
                    .customerRecordId(messageCustomerRecordVO.getId())
                    .toUsers(toUsers)
                    .templateId(miniMsgTempSetting.getTemplateId())
                    .page(miniMsgTempSetting.getToPage().concat(messageRequest.getTradeId()))
                    .data(map)
                    .build();
        }
        return null;
    }

    /**
     * @description 发送小程序订阅消息-处理发送优惠券
     * @author xufeng
     * @date 2022/8/22 17:34
     * @param messageRequest
     * @return void
     */
    public PlatformSendMiniMsgRequest dealCouponIsSuanceMiniProgramSubscribeMsg(PlatformSendMiniMsgRequest messageRequest) {
        String customerId = messageRequest.getCustomerId();
        TriggerNodeType triggerNodeId = messageRequest.getTriggerNodeId();
        // 异步处理小程序订阅消息发送
        FindMiniProgramTemplateAndCustomerRecord findMiniProgramTemplateAndCustomerRecord =
                new FindMiniProgramTemplateAndCustomerRecord(customerId, triggerNodeId).invoke();
        // 获取模板配置
        MiniMsgTempSetting miniMsgTempSetting =
                findMiniProgramTemplateAndCustomerRecord.getMiniProgramSubscribeTemplateSetting();
        // 获取用户授权信息
        List<MiniMsgCustomerRecordVO> miniMsgCustomerRecordVOList =
                findMiniProgramTemplateAndCustomerRecord.getMiniProgramSubscribeMessageCustomerRecordVOList();
        if (org.apache.commons.collections.CollectionUtils.isNotEmpty(miniMsgCustomerRecordVOList)){
            // 取一条发送消息
            MiniMsgCustomerRecordVO messageCustomerRecordVO =
                    miniMsgCustomerRecordVOList.get(0);
            List<String> toUsers = Lists.newArrayList();
            toUsers.add(messageCustomerRecordVO.getOpenId());
            Map<String, Object> map1 = Maps.newHashMapWithExpectedSize(Constants.ONE);
            map1.put("value", messageRequest.getCouponName());
            Map<String, Object> map2 = Maps.newHashMapWithExpectedSize(Constants.ONE);
            map2.put("value", messageRequest.getDenomination());
            Map<String, Object> map3 = Maps.newHashMapWithExpectedSize(Constants.ONE);
            map3.put("value", messageRequest.getScopeTypeDec());
            Map<String, Object> map4 = Maps.newHashMapWithExpectedSize(Constants.ONE);
            map4.put("value", messageRequest.getEffectiveDate());
            Map<String, Object> map5 = Maps.newHashMapWithExpectedSize(Constants.ONE);
            map5.put("value", messageRequest.getCouponNum());
            Map<String, Object> map = Maps.newHashMapWithExpectedSize(Constants.ONE);
            map.put("thing1", map1);
            map.put("amount2", map2);
            map.put("thing4", map3);
            map.put("time5", map4);
            map.put("number7", map5);
            // 组装入参
            return PlatformSendMiniMsgRequest.builder()
                    .customerRecordId(messageCustomerRecordVO.getId())
                    .toUsers(toUsers)
                    .templateId(miniMsgTempSetting.getTemplateId())
                    .page(miniMsgTempSetting.getToPage())
                    .data(map)
                    .build();
        }
        return null;
    }

    /**
     * @description 发送小程序订阅消息-处理自动确认收货前24小时
     * @author xufeng
     * @date 2022/8/22 17:34
     * @param messageRequest
     * @return void
     */
    public PlatformSendMiniMsgRequest dealAutoConfirmReceiptMiniProgramSubscribeMsg(PlatformSendMiniMsgRequest messageRequest) {
        String customerId = messageRequest.getCustomerId();
        TriggerNodeType triggerNodeId = messageRequest.getTriggerNodeId();
        // 异步处理小程序订阅消息发送
        FindMiniProgramTemplateAndCustomerRecord findMiniProgramTemplateAndCustomerRecord =
                new FindMiniProgramTemplateAndCustomerRecord(customerId, triggerNodeId).invoke();
        // 获取模板配置
        MiniMsgTempSetting miniMsgTempSetting =
                findMiniProgramTemplateAndCustomerRecord.getMiniProgramSubscribeTemplateSetting();
        // 获取用户授权信息
        List<MiniMsgCustomerRecordVO> miniMsgCustomerRecordVOList =
                findMiniProgramTemplateAndCustomerRecord.getMiniProgramSubscribeMessageCustomerRecordVOList();
        if (org.apache.commons.collections.CollectionUtils.isNotEmpty(miniMsgCustomerRecordVOList)){
            // 取一条发送消息
            MiniMsgCustomerRecordVO messageCustomerRecordVO =
                    miniMsgCustomerRecordVOList.get(0);
            String tips = miniMsgTempSetting.getNewTips();
            if (StringUtils.isBlank(tips)) {
                tips = miniMsgTempSetting.getTips();
            }
            List<String> toUsers = Lists.newArrayList();
            toUsers.add(messageCustomerRecordVO.getOpenId());
            Map<String, Object> map1 = Maps.newHashMapWithExpectedSize(Constants.ONE);
            map1.put("value", messageRequest.getTradeId());
            Map<String, Object> map2 = Maps.newHashMapWithExpectedSize(Constants.ONE);
            map2.put("value", messageRequest.getDeliverTime());
            Map<String, Object> map3 = Maps.newHashMapWithExpectedSize(Constants.ONE);
            map3.put("value", messageRequest.getAutoConfirmTime());
            Map<String, Object> map4 = Maps.newHashMapWithExpectedSize(Constants.ONE);
            map4.put("value", tips);
            Map<String, Object> map = Maps.newHashMapWithExpectedSize(Constants.ONE);
            map.put("character_string2", map1);
            map.put("date4", map2);
            map.put("date5", map3);
            map.put("thing1", map4);
            // 组装入参
            return PlatformSendMiniMsgRequest.builder()
                    .customerRecordId(messageCustomerRecordVO.getId())
                    .toUsers(toUsers)
                    .templateId(miniMsgTempSetting.getTemplateId())
                    .page(miniMsgTempSetting.getToPage().concat(messageRequest.getTradeId()))
                    .data(map)
                    .build();
        }
        return null;
    }

    /**
     * @description 发送小程序订阅消息-处理优惠券过期前24小时
     * @author xufeng
     * @date 2022/8/22 17:34
     * @param messageRequest
     * @return void
     */
    public PlatformSendMiniMsgRequest dealCouponUsageMiniProgramSubscribeMsg(PlatformSendMiniMsgRequest messageRequest) {
        String customerId = messageRequest.getCustomerId();
        TriggerNodeType triggerNodeId = messageRequest.getTriggerNodeId();
        // 异步处理小程序订阅消息发送
        FindMiniProgramTemplateAndCustomerRecord findMiniProgramTemplateAndCustomerRecord =
                new FindMiniProgramTemplateAndCustomerRecord(customerId, triggerNodeId).invoke();
        // 获取模板配置
        MiniMsgTempSetting miniMsgTempSetting =
                findMiniProgramTemplateAndCustomerRecord.getMiniProgramSubscribeTemplateSetting();
        // 获取用户授权信息
        List<MiniMsgCustomerRecordVO> miniMsgCustomerRecordVOList =
                findMiniProgramTemplateAndCustomerRecord.getMiniProgramSubscribeMessageCustomerRecordVOList();
        if (org.apache.commons.collections.CollectionUtils.isNotEmpty(miniMsgCustomerRecordVOList)){
            // 取一条发送消息
            MiniMsgCustomerRecordVO messageCustomerRecordVO =
                    miniMsgCustomerRecordVOList.get(0);
            String tips = miniMsgTempSetting.getNewTips();
            if (StringUtils.isBlank(tips)) {
                tips = miniMsgTempSetting.getTips();
            }
            List<String> toUsers = Lists.newArrayList();
            toUsers.add(messageCustomerRecordVO.getOpenId());
            Map<String, Object> map1 = Maps.newHashMapWithExpectedSize(Constants.ONE);
            map1.put("value", messageRequest.getCouponName());
            Map<String, Object> map2 = Maps.newHashMapWithExpectedSize(Constants.ONE);
            map2.put("value", messageRequest.getEffectiveDate());
            Map<String, Object> map3 = Maps.newHashMapWithExpectedSize(Constants.ONE);
            map3.put("value", messageRequest.getDenomination());
            Map<String, Object> map4 = Maps.newHashMapWithExpectedSize(Constants.ONE);
            map4.put("value", tips);
            Map<String, Object> map = Maps.newHashMapWithExpectedSize(Constants.ONE);
            map.put("thing1", map1);
            map.put("time8", map2);
            map.put("thing9", map3);
            map.put("thing3", map4);
            // 组装入参
            return PlatformSendMiniMsgRequest.builder()
                    .customerRecordId(messageCustomerRecordVO.getId())
                    .toUsers(toUsers)
                    .templateId(miniMsgTempSetting.getTemplateId())
                    .page(miniMsgTempSetting.getToPage())
                    .data(map)
                    .build();
        }
        return null;
    }

    private class FindMiniProgramTemplateAndCustomerRecord {
        private final String customerId;
        private final TriggerNodeType triggerNodeId;
        private MiniMsgTempSetting miniMsgTempSetting;
        private List<MiniMsgCustomerRecordVO> miniMsgCustomerRecordVOList;

        public FindMiniProgramTemplateAndCustomerRecord(String customerId, TriggerNodeType triggerNodeId) {
            this.customerId = customerId;
            this.triggerNodeId = triggerNodeId;
        }

        public MiniMsgTempSetting getMiniProgramSubscribeTemplateSetting() {
            return miniMsgTempSetting;
        }

        public List<MiniMsgCustomerRecordVO> getMiniProgramSubscribeMessageCustomerRecordVOList() {
            return miniMsgCustomerRecordVOList;
        }

        public FindMiniProgramTemplateAndCustomerRecord invoke() {
            // 更新完发mq处理数据
            miniMsgTempSetting =
                    miniMsgTempSettingService.findByTriggerNodeId(triggerNodeId);
            MiniMsgCustomerRecordListResponse response =
                    miniMsgCustomerRecordQueryProvider.list(MiniMsgCustomerRecordListRequest.builder()
                            .customerId(customerId).triggerNodeId(triggerNodeId).sendFlag(Constants.ZERO).build()).getContext();
            miniMsgCustomerRecordVOList = response.getMiniMsgCustomerRecordVOList();
            return this;
        }
    }

    /**
     * 根据模板和参数列表填充内容
     * @param template 内容模板，例："任务{}开始执行，时间{}"
     * @param params 内容参数，例：Arrays.asList(1001L, LocalDateTime.now())
     * @return
     */
    private static String fillContextByParams(String template, List<Object> params) {
        if (StringUtils.isBlank(template) || CollectionUtils.isEmpty(params)) {
            return template;
        }
        StringBuilder context = new StringBuilder();
        String[] nodeContents = template.split("\\{(.*?)\\}");
        for (int i = 0; i < nodeContents.length; i++){
            context.append(nodeContents[i]);
            if (!CollectionUtils.isEmpty(params) && i < params.size()){
                context.append(params.get(i));
            }
        }
        return context.toString();
    }

    public static void main(String[] args) {
        String content = fillContextByParams("任务{}开始执行，时间{}", Arrays.asList(1001L, LocalDateTime.now()));
        System.out.println("content = " + content);
    }
}
