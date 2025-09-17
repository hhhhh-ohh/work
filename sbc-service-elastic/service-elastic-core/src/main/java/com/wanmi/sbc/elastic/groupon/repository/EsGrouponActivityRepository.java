package com.wanmi.sbc.elastic.groupon.repository;

import com.wanmi.sbc.elastic.groupon.model.root.EsGrouponActivity;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author: HouShuai
 * @date: 2020/12/8 11:16
 * @description: 拼团活动
 */
@Repository
public interface EsGrouponActivityRepository extends ElasticsearchRepository<EsGrouponActivity, String> {

}
