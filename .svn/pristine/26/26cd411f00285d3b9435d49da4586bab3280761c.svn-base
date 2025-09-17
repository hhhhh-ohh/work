package com.wanmi.sbc.elastic.communityleader.repository;

import com.wanmi.sbc.elastic.communityleader.root.EsCommunityLeader;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

/**
 * @author: wc
 * @date: 2020/12/7 10:20
 * @description: 社区团长do
 */
@Repository
public interface EsCommunityLeaderRepository extends ElasticsearchRepository<EsCommunityLeader, String> {


    /**
     * 根据社区团长id查询社区团长信息
     *
     * @param ids
     * @return
     */
    List<EsCommunityLeader> findByLeaderIdIn(Collection<String> ids);

    /**
     * 根据会员id查询社区团长
     * @param customerId
     * @return
     */
    EsCommunityLeader findByCustomerId(String customerId);

}
