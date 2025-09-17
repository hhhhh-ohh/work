package com.wanmi.sbc.mq.distribution;

import com.wanmi.sbc.order.api.provider.distribution.DistributionTaskTempProvider;
import com.wanmi.sbc.order.api.request.distribution.DistributionTaskTempRequest;
import com.wanmi.sbc.order.api.response.distribution.DistributionTaskTempPageResponse;
import com.wanmi.sbc.order.bean.vo.DistributionTaskTempVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DistributionTaskTempService {

    @Autowired
    private DistributionTaskTempProvider distributionTaskTempProvider;

    /**
     * 增加退单数量
     *
     * @return
     */
    @Transactional
    public void addReturnOrderNum(String orderId) {
        DistributionTaskTempRequest distributionTaskTempRequest = new DistributionTaskTempRequest();
        distributionTaskTempRequest.setOrderId(orderId);
        distributionTaskTempProvider.addReturnOrderNum(distributionTaskTempRequest);
    }

    /**
     * 减少退单数量
     *
     * @return
     */
    @Transactional
    public void minusReturnOrderNum(String orderId) {
        DistributionTaskTempRequest distributionTaskTempRequest = new DistributionTaskTempRequest();
        distributionTaskTempRequest.setOrderId(orderId);
        distributionTaskTempProvider.minusReturnOrderNum(distributionTaskTempRequest);
    }

}
