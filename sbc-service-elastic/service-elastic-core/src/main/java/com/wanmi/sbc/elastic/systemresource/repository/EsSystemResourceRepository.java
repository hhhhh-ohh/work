package com.wanmi.sbc.elastic.systemresource.repository;

import com.wanmi.sbc.elastic.systemresource.model.root.EsSystemResource;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

/**
 * @author houshuai
 * @date 2020/12/14 11:01
 * @description <p> 资源素材 </p>
 */
@Repository
public interface EsSystemResourceRepository extends ElasticsearchRepository<EsSystemResource,Long> {
}
