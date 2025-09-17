package com.wanmi.sbc.order.paytimeseries.service;

import com.wanmi.sbc.empower.bean.enums.TradeStatus;
import com.wanmi.sbc.order.api.request.paytimeseries.PayTimeSeriesQueryRequest;
import com.wanmi.sbc.order.bean.vo.PayTimeSeriesVO;
import com.wanmi.sbc.order.paytimeseries.model.root.PayTimeSeries;
import com.wanmi.sbc.order.paytimeseries.repository.PayTimeSeriesRepository;
import com.wanmi.sbc.order.paytraderecord.model.root.PayTradeRecord;
import com.wanmi.sbc.order.paytraderecord.repository.TradeRecordRepository;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.wanmi.sbc.common.util.KsBeanUtil;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 支付流水记录业务逻辑
 *
 * @author zhanggaolei
 * @date 2022-12-08 15:30:16
 */
@Service("PayTimeSeriesService")
public class PayTimeSeriesService {
    @Autowired private PayTimeSeriesRepository payTimeSeriesRepository;

    @Autowired private TradeRecordRepository tradeRecordRepository;

    /**
     * 新增支付流水记录
     *
     * @author zhanggaolei
     */
    @Transactional
    public PayTimeSeries add(PayTimeSeries entity) {
        entity.setCreateTime(LocalDateTime.now());
        payTimeSeriesRepository.save(entity);
        return entity;
    }

    /**
     * 修改支付流水记录
     *
     * @author zhanggaolei
     */
    @Transactional
    public PayTimeSeries modify(PayTimeSeries entity) {
        payTimeSeriesRepository.save(entity);
        return entity;
    }

    /**
     * 单个查询支付流水记录
     *
     * @author zhanggaolei
     */
    public PayTimeSeries getOne(String id) {
        return payTimeSeriesRepository
                .findByPayNo(id)
                .orElse(null);
    }

    /**
     * 列表查询支付流水记录
     *
     * @author zhanggaolei
     */
    public List<PayTimeSeries> list(PayTimeSeriesQueryRequest queryReq) {
        return payTimeSeriesRepository.findAll(PayTimeSeriesWhereCriteriaBuilder.build(queryReq));
    }

    /**
     * 获取重复支付的流水记录
     * @param businessId
     * @return
     */
    public List<PayTimeSeries> getDuplicatePay(String businessId){
        PayTradeRecord payTradeRecord = tradeRecordRepository.findByBusinessId(businessId);
        if(payTradeRecord!=null && payTradeRecord.getStatus().equals(TradeStatus.SUCCEED)){
            return payTimeSeriesRepository.findDuplicatePay(businessId,payTradeRecord.getPayNo());
        }
        return null;
    }

    /**
     * 将实体包装成VO
     *
     * @author zhanggaolei
     */
    public PayTimeSeriesVO wrapperVo(PayTimeSeries payTimeSeries) {
        if (payTimeSeries != null) {
            PayTimeSeriesVO payTimeSeriesVO =
                    KsBeanUtil.convert(payTimeSeries, PayTimeSeriesVO.class);
            return payTimeSeriesVO;
        }
        return null;
    }

    /**
     * @description 查询总数量
     * @author zhanggaolei
     */
    public Long count(PayTimeSeriesQueryRequest queryReq) {
        return payTimeSeriesRepository.count(PayTimeSeriesWhereCriteriaBuilder.build(queryReq));
    }
}
