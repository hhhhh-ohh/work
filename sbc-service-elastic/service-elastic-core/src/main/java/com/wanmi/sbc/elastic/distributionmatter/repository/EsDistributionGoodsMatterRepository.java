package com.wanmi.sbc.elastic.distributionmatter.repository;

import com.wanmi.sbc.elastic.distributionmatter.model.root.EsDistributionGoodsMatter;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

/**
 * @author houshuai
 * @date 2021/1/8 15:05
 * @description <p> 分销素材DO </p>
 */
@Repository
public interface EsDistributionGoodsMatterRepository extends ElasticsearchRepository<EsDistributionGoodsMatter,String> {
}
