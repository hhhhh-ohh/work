package com.wanmi.sbc.job;

import com.alibaba.fastjson2.JSON;
import com.google.common.collect.Lists;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.VASConstants;
import com.wanmi.sbc.empower.api.provider.channel.vop.message.VopMessageProvider;
import com.wanmi.sbc.empower.api.request.vop.message.VopDeleteMessageRequest;
import com.wanmi.sbc.empower.api.request.vop.message.VopGetMessageRequest;
import com.wanmi.sbc.empower.api.response.channel.vop.message.VopMessageResponse;
import com.wanmi.sbc.job.jdvop.service.JdVopMessageHandler;
import com.wanmi.sbc.util.CommonUtil;
import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.concurrent.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.wanmi.sbc.vop.VopConstant.DEFAULT_HANDLE_MSG;

/**
 * @description 获取京东VOP的推送消息并处理任务
 * @author hanwei
 * @date 2021/5/14
 */
@Component
@Slf4j
public class JdVopMessageJobHandler {

    @Autowired
    private CommonUtil commonUtil;
    @Autowired
    private VopMessageProvider vopMessageProvider;
    @Autowired
    private List<JdVopMessageHandler> messageHandlerList;

    private static final ThreadFactory THREAD_FACTORY_NAME = new ThreadFactoryBuilder()
            .setNameFormat("delete-vop-push-message-%d").build();

    private final ThreadPoolExecutor executor = new ThreadPoolExecutor(8, 100,
            NumberUtils.LONG_ZERO, TimeUnit.MILLISECONDS,
            new LinkedBlockingQueue<>(1000), THREAD_FACTORY_NAME,
            new ThreadPoolExecutor.AbortPolicy());

    @XxlJob(value = "jdVopMessageJobHandler")
    public void execute() throws Exception {
        String param = XxlJobHelper.getJobParam();
        boolean flag = commonUtil.findVASBuyOrNot(VASConstants.THIRD_PLATFORM_VOP);
        if (!flag) {
            XxlJobHelper.log("未开通京东vop增值服务");
            return;
        }
        XxlJobHelper.log("已开通京东vop增值服务");

        Map<Integer, JdVopMessageHandler> jdVopMessageHandlerMap = messageHandlerList.stream()
                .collect(Collectors.toMap(JdVopMessageHandler::getVopMessageType, Function.identity()));

        if (StringUtils.isBlank(param)) {
            param = DEFAULT_HANDLE_MSG;
        }
        for (String type : StringUtils.split(param, ',')) {
            try {
                List<VopMessageResponse> vopMessageList = getVopMessageList(type);
                if (CollectionUtils.isEmpty(vopMessageList)) {
                    XxlJobHelper.log("未查询到京东vop的{}消息: {}", type, JSON.toJSONString(vopMessageList));
                    log.info("未查询到京东vop的{}消息: {}", type, JSON.toJSONString(vopMessageList));
                } else {
                    XxlJobHelper.log("查询到京东vop的{}消息: {}", type, JSON.toJSONString(vopMessageList));
                    log.info("查询到京东vop的{}消息: {}", type, JSON.toJSONString(vopMessageList));
                    JdVopMessageHandler jdVopMessageHandler = jdVopMessageHandlerMap.get(Integer.parseInt(type));
                    if (jdVopMessageHandler != null) {
                        XxlJobHelper.log("开始处理京东vop的{}消息，消息处理器: {}", type, jdVopMessageHandler.getClass().getName());
                        log.info("开始处理京东vop的{}消息，消息处理器: {}", type, jdVopMessageHandler.getClass().getName());
                        List<String> deleteList = jdVopMessageHandler.handleMessage(vopMessageList);
                        XxlJobHelper.log("完成处理京东vop的{}消息，删除处理成功的消息: {}", type, JSON.toJSONString(deleteList));
                        log.info("完成处理京东vop的{}消息，删除处理成功的消息: {}", type, JSON.toJSONString(deleteList));
                        deleteVopMessage(deleteList, Integer.parseInt(type));
                    } else {
                        log.error("京东vop的{}消息无处理服务: {}", type, JSON.toJSONString(vopMessageList));
                    }
                }
            } catch (Exception e) {
                log.error("处理京东vop的{}消息异常", type, e);
                XxlJobHelper.log("处理京东vop的{}消息异常", type);
                XxlJobHelper.log(e);
            }
        }
    }

    private List<VopMessageResponse> getVopMessageList(String type) {
        List<VopMessageResponse> vopMessageResponseList = null;
        try {
            BaseResponse<List<VopMessageResponse>> response =
                    vopMessageProvider.getMessage(VopGetMessageRequest.builder().type(type).build());
            vopMessageResponseList = response.getContext();
        } catch (Exception e) {
            log.error("查询京东vop的{}消息异常", type, e);
            XxlJobHelper.log(e);
        }

        return vopMessageResponseList;
    }

    public void deleteVopMessage(List<String> deleteList, Integer type) {
        if (CollectionUtils.isEmpty(deleteList)) {
            return;
        }
        // 对成功的推送信息调用接口进行删除
        // 项目上超时问题，因为是盲改，所以不确定是不是feign调用超时问题，如果是的话，线程需要加到empower服务中调用vop接口处 处理
        List<List<String>> partition = Lists.partition(deleteList, 100);
        List<CompletableFuture<Void>> futureList = partition.stream().map(ids ->
                CompletableFuture.runAsync(() -> {
                    BaseResponse response =
                            vopMessageProvider.deleteMessage(
                                    VopDeleteMessageRequest.builder().id(String.join(",", ids)).build());
                    if (Boolean.TRUE.equals(response.getContext())) {
                        XxlJobHelper.log("已删除京东vop[{}]消息ID: {}", type, JSON.toJSONString(deleteList));
                        log.info("已删除京东vop[{}]消息ID: {}", type, JSON.toJSONString(deleteList));
                    } else {
                        log.error("删除京东推送消息失败：{}", String.join(",", deleteList));
                        XxlJobHelper.log("删除京东推送消息失败：{}", String.join(",", deleteList));
                    }
                }, executor)).collect(Collectors.toList());
        // 定时任务处理，最后一步主进程可等可不等 线程
        futureList.forEach(CompletableFuture::join);
    }
}
