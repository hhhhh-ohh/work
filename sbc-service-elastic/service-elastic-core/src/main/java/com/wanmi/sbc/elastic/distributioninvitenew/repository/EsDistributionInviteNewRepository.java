package com.wanmi.sbc.elastic.distributioninvitenew.repository;

import com.wanmi.sbc.elastic.distributioninvitenew.model.root.EsInviteNewRecord;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

/**
 * @author houshuai
 * @date 2021/1/6 14:44
 * @description <p> 邀新记录DO </p>
 */
@Repository
public interface EsDistributionInviteNewRepository extends ElasticsearchRepository<EsInviteNewRecord,String> {
}
