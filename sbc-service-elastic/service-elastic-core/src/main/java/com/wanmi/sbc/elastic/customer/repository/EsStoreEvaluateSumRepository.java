package com.wanmi.sbc.elastic.customer.repository;


import com.wanmi.sbc.elastic.customer.model.root.EsStoreEvaluateSum;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

/**
 * @author houshai
 * 店铺评价
 */
@Repository
public interface EsStoreEvaluateSumRepository extends ElasticsearchRepository<EsStoreEvaluateSum,Long> {

}