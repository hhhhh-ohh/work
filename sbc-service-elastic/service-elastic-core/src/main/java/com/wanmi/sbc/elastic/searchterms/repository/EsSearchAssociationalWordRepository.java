package com.wanmi.sbc.elastic.searchterms.repository;

import com.wanmi.sbc.elastic.searchterms.model.root.EsSearchAssociationalWord;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

/**
 * @author houshuai
 * @date 2020/12/17 14:38
 * @description <p> 搜索词DO </p>
 */
@Repository
public interface EsSearchAssociationalWordRepository extends ElasticsearchRepository<EsSearchAssociationalWord, Long> {

}
