package com.wanmi.sbc.elastic.customer.repository;

import com.wanmi.sbc.elastic.customer.model.root.EsDistributionCustomer;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

/**
 * @author: HouShuai
 * @date: 2020/12/7 10:20
 * @description: 分销员do
 */
@Repository
public interface EsDistributionCustomerRepository extends ElasticsearchRepository<EsDistributionCustomer, String> {


    /**
     * 根据分销员id查询分销员信息
     *
     * @param ids
     * @return
     */
    List<EsDistributionCustomer> findByDistributionIdIn(Collection<String> ids);

    /**
     * 根据会员id查询分销员
     * @param customerId
     * @return
     */
    EsDistributionCustomer findByCustomerId(String customerId);

}
