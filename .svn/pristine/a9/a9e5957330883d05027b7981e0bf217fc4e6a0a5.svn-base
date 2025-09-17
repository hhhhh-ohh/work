package com.wanmi.sbc.vas.recommend.recommendcatemanage.repository;

import com.wanmi.sbc.vas.bean.enums.recommen.NoPushType;
import com.wanmi.sbc.vas.recommend.recommendcatemanage.model.root.RecommendCateManage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>分类推荐管理DAO</p>
 * @author lvzhenwei
 * @date 2020-11-19 14:05:07
 */
@Repository
public interface RecommendCateManageRepository extends JpaRepository<RecommendCateManage, Long>,
        JpaSpecificationExecutor<RecommendCateManage> {

    @Modifying
    @Query("update RecommendCateManage set weight = ?1 ,updateTime = now() where id = ?2")
    int updateCateWeight(Integer weight, Long id);

    @Modifying
    @Query("update RecommendCateManage set weight = null ,updateTime = now() where id = ?1")
    int updateCateWeightNull(Long id);

    @Modifying
    @Query("update RecommendCateManage set noPushType = ?1 ,updateTime = now() where id = ?2")
    int updateCateNoPushType(NoPushType noPushType, Long id);

    @Modifying
    @Query("update RecommendCateManage set noPushType = ?1 ,updateTime = now() where id in ?2")
    int updateCateNoPushTypeForIdList(NoPushType noPushType, List<Long> ids);

}
