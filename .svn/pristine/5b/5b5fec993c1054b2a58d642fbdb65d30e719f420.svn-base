package com.wanmi.sbc.elastic.distributionrecord.repository;


import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.elastic.distributionrecord.model.root.EsDistributionRecord;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

/**
 * @author houshuai
 * @date 2021/1/4 16:21
 * @description <p> 分销记录DO </p>
 */
@Repository
public interface EsDistributionRecordRepository extends ElasticsearchRepository<EsDistributionRecord, Long> {

    /**
     * 根据TradeId 查询数据
     * @param tradeId
     * @param deleteFlag
     * @return
     */
    EsDistributionRecord findByTradeIdAndDeleteFlag(String tradeId, DeleteFlag deleteFlag);

}
