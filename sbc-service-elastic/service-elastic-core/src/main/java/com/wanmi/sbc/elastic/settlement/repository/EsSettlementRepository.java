package com.wanmi.sbc.elastic.settlement.repository;

import com.wanmi.sbc.elastic.settlement.model.root.EsSettlement;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

/**
 * 结算单ES持久层
 */
@Repository
public interface EsSettlementRepository extends ElasticsearchRepository<EsSettlement, String> {
}
