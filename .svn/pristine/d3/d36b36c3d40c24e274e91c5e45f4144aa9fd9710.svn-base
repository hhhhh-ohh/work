package com.wanmi.sbc.mq;

import com.alibaba.fastjson2.JSONObject;
import com.wanmi.sbc.customer.api.request.customer.LedgerBindInfoToEsRequest;
import com.wanmi.sbc.customer.bean.vo.LedgerReceiverRelVO;
import com.wanmi.sbc.mq.api.provider.MqSendProvider;
import com.wanmi.sbc.mq.bean.constants.ProducerTopic;
import com.wanmi.sbc.mq.bean.dto.MqSendDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author xuyunpeng
 * @className ProducerService
 * @description
 * @date 2022/7/14 4:05 PM
 **/

@Slf4j
@Service
public class ProducerService {

    @Autowired
    private MqSendProvider mqSendProvider;

    /**
     * 批量新增分账绑定关系
     * @param accountId
     */
    public void batchAddLedgerReceiverRel(String accountId) {
        MqSendDTO mqSendDTO = new MqSendDTO();
        mqSendDTO.setTopic(ProducerTopic.LEDGER_RECEIVER_REL_BATCH_ADD);
        mqSendDTO.setData(accountId);
        mqSendProvider.send(mqSendDTO);
    }

    /**
     * es保存分账绑定数据
     * @param rels
     */
    public void esAddLedgerBindInfo(List<LedgerReceiverRelVO> rels) {
        LedgerBindInfoToEsRequest request = LedgerBindInfoToEsRequest.builder().relVOList(rels).build();
        MqSendDTO mqSendDTO = new MqSendDTO();
        mqSendDTO.setTopic(ProducerTopic.ES_LEDGER_BIND_INFO_ADD);
        mqSendDTO.setData(JSONObject.toJSONString(request));
        mqSendProvider.send(mqSendDTO);
    }
}
