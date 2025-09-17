package com.wanmi.sbc.websocket;


import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.mq.api.provider.MqSendProvider;
import com.wanmi.sbc.order.api.provider.trade.GrouponInstanceQueryProvider;
import com.wanmi.sbc.order.api.request.trade.GrouponInstanceByGrouponNoRequest;
import com.wanmi.sbc.order.bean.vo.GrouponInstanceVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.validation.annotation.Validated;


/**
 * lq
 */
@RestController
@Validated
@Slf4j
public class WebSocketController {

    @Autowired
    private GrouponInstanceQueryProvider grouponInstanceQueryProvider;

//    @Autowired
//    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    private MqSendProvider mqSendProvider;


    /**
     * 测试接口
     * 根据团实例编号查询团信息
     */
    @RequestMapping(value = "/testmq/grouponInstanceInfo/{grouponNo}", method = RequestMethod.GET)
    public BaseResponse<GrouponInstanceVO> getGrouponInstanceInfoByMQ(@PathVariable String grouponNo) {
        log.info("getGrouponInstanceInfo {}", grouponNo);
        GrouponInstanceByGrouponNoRequest request = GrouponInstanceByGrouponNoRequest.builder().grouponNo(grouponNo)
                .build();
        grouponInstanceQueryProvider.detailByGrouponNo(request).getContext
                ().getGrouponInstance();
        //发送消息
//        messagingTemplate.convertAndSend("/topic/getGrouponInstance", grouponInstanceVO);
        return BaseResponse.SUCCESSFUL();
    }

    /**
     * 测试接口
     * 根据团实例编号查询团信息
     */
    @RequestMapping(value = "/test/grouponInstanceInfo/{grouponNo}", method = RequestMethod.GET)
    public BaseResponse<GrouponInstanceVO> getGrouponInstanceInfo(@PathVariable String grouponNo) {
        log.info("getGrouponInstanceInfo {}", grouponNo);
        GrouponInstanceByGrouponNoRequest request = GrouponInstanceByGrouponNoRequest.builder().grouponNo(grouponNo)
                .build();
        grouponInstanceQueryProvider.detailByGrouponNo(request).getContext
                ().getGrouponInstance();
        //发送消息
//        messagingTemplate.convertAndSend("/topic/getGrouponInstance", grouponInstanceVO);
        return BaseResponse.SUCCESSFUL();
    }
}
