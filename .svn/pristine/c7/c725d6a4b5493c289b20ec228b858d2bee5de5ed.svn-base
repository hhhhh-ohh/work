package com.wanmi.sbc.order.distribution.service;

import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.order.api.request.distribution.DistributionTaskTempAddRequest;
import com.wanmi.sbc.order.api.request.distribution.DistributionTaskTempRequest;
import com.wanmi.sbc.order.bean.vo.DistributionTaskTempVO;
import com.wanmi.sbc.order.distribution.model.root.DistributionTaskTemp;
import com.wanmi.sbc.order.distribution.repository.DistributionTaskTempRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class DistributionTaskTempService {

    @Autowired
    DistributionTaskTempRepository distributionTaskTempRepository;

    /**
     * 定时任务启动，搜索数据
     *
     * @return
     */
    public Page<DistributionTaskTemp> pageByParam(DistributionTaskTempRequest reques) {
        Page<DistributionTaskTemp> dataPage = distributionTaskTempRepository.queryForTask(reques.getPageable());
        return dataPage;
    }

    public List<DistributionTaskTemp> findByOrderId(String orderId) {
        List<DistributionTaskTemp> distributionTaskTempList = distributionTaskTempRepository.findByOrderId(orderId);
        return distributionTaskTempList;
    }

    /**
     * 增加退单数量
     *
     * @return
     */
    @Transactional
    public void addReturnOrderNum(String orderId) {
        distributionTaskTempRepository.addReturnOrderNum(orderId);
    }

    /**
     * 减少退单数量
     *
     * @return
     */
    @Transactional
    public void minusReturnOrderNum(String orderId) {
        distributionTaskTempRepository.minusReturnOrderNum(orderId);
    }

    /**
     * 删除数据
     *
     * @returnaddReturnOrderNum
     */
    @Transactional
    public int deleteByIds(List<String> ids) {
        int num = distributionTaskTempRepository.deleteByIdIn(ids);
        return num;
    }

    @Transactional
    public void deleteById(String id) {
        distributionTaskTempRepository.deleteById(id);
    }

    /**
     * @description 新增分销任务临时表数据
     * @author  lvzhenwei
     * @date 2021/8/16 7:07 下午
     * @param request
     * @return void
     **/
    public void save(DistributionTaskTempAddRequest request){
        DistributionTaskTemp distributionTaskTemp = KsBeanUtil.copyPropertiesThird(request, DistributionTaskTemp.class);
        distributionTaskTempRepository.save(distributionTaskTemp);
    }

    /**
     * @description 实体转VO
     * @author  lvzhenwei
     * @date 2021/8/16 4:39 下午
     * @param distributionTaskTemp
     * @return com.wanmi.sbc.order.bean.vo.DistributionTaskTempVO
     **/
    public DistributionTaskTempVO wrapperVo(DistributionTaskTemp distributionTaskTemp) {
        if (distributionTaskTemp != null) {
            DistributionTaskTempVO distributionTaskTempVO = new DistributionTaskTempVO();
            KsBeanUtil.copyPropertiesThird(distributionTaskTemp, distributionTaskTempVO);
            return distributionTaskTempVO;
        }
        return null;
    }

    /**
     * 定时任务启动，搜索数据
     *
     * @return
     */
    public Page<DistributionTaskTemp> pageByLedgerTask(DistributionTaskTempRequest reques) {
        Page<DistributionTaskTemp> dataPage = distributionTaskTempRepository.queryForLedgerTask(reques.getPageable());
        return dataPage;
    }

    /**
     * 分账更新数据
     * @param orderId
     * @param params
     * @param ledgerTime
     */
    @Transactional
    public void updateForLedger(String orderId, String params, LocalDateTime ledgerTime){
        distributionTaskTempRepository.updateForLedger(orderId, params, ledgerTime);
    }

}
