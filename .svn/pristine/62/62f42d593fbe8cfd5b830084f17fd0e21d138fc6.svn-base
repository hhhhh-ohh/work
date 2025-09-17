package com.wanmi.sbc.job.service;

import com.wanmi.sbc.order.api.provider.distribution.DistributionTaskTempProvider;
import com.wanmi.sbc.order.api.request.distribution.DistributionTaskTempRequest;
import com.wanmi.sbc.order.api.response.distribution.DistributionTaskTempPageResponse;
import com.wanmi.sbc.order.bean.vo.DistributionTaskTempVO;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DistributionTaskTempService {

    @Autowired
    private DistributionTaskTempProvider distributionTaskTempProvider;

    /**
     * 定时任务启动，搜索数据
     *
     * @return
     */
    public List<DistributionTaskTempVO> queryData(int size) {
        DistributionTaskTempRequest distributionTaskTempRequest = new DistributionTaskTempRequest();
        distributionTaskTempRequest.setPageNum(0);
        distributionTaskTempRequest.setPageSize(size);
        DistributionTaskTempPageResponse distributionTaskTempPageResponse = distributionTaskTempProvider.pageByParam(distributionTaskTempRequest).getContext();
        List<DistributionTaskTempVO> list = distributionTaskTempPageResponse.getDistributionTaskTempVOPage().getContent();
        return list;
    }

    @Transactional
    public void deleteById(String id) {
        DistributionTaskTempRequest request = new DistributionTaskTempRequest();
        request.setId(id);
        distributionTaskTempProvider.deleteById(request);
    }

    /**
     * 根据订单查询记录
     * @param orderId
     * @return
     */
    public DistributionTaskTempVO findByOrderId(String orderId) {
        DistributionTaskTempRequest request = new DistributionTaskTempRequest();
        request.setOrderId(orderId);
        List<DistributionTaskTempVO> distributionTaskTempVOList = distributionTaskTempProvider.findByOrderId(request).getContext().getDistributionTaskTempVOList();
        if (CollectionUtils.isNotEmpty(distributionTaskTempVOList)) {
            // 根据交易号默认只能查询到一条记录
            return distributionTaskTempVOList.get(NumberUtils.INTEGER_ZERO);
        }
        return null;
    }

    /**
     * 定时任务启动，搜索数据
     *
     * @return
     */
    public List<DistributionTaskTempVO> pageByLedgerTask(int size) {
        DistributionTaskTempRequest distributionTaskTempRequest = new DistributionTaskTempRequest();
        distributionTaskTempRequest.setPageNum(0);
        distributionTaskTempRequest.setPageSize(size);
        DistributionTaskTempPageResponse distributionTaskTempPageResponse = distributionTaskTempProvider.pageByLedgerTask(distributionTaskTempRequest).getContext();
        List<DistributionTaskTempVO> list = distributionTaskTempPageResponse.getDistributionTaskTempVOPage().getContent();
        return list;
    }

}
