package com.wanmi.sbc.elastic.storeInformation.repository;

import com.wanmi.sbc.elastic.storeInformation.model.root.StoreInformation;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

/**
 * 商家店铺信息ES持久层
 */
@Repository
public interface EsStoreInformationRepository extends ElasticsearchRepository<StoreInformation, String> {
}
