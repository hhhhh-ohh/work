package com.wanmi.sbc.vas.recommend.recommendgoodsmanage.repository;

import com.wanmi.sbc.vas.bean.enums.recommen.NoPushType;
import com.wanmi.sbc.vas.recommend.recommendgoodsmanage.model.root.RecommendGoodsManage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

/**
 * <p>商品推荐管理DAO</p>
 * @author lvzhenwei
 * @date 2020-11-18 14:07:44
 */
@Repository
public interface RecommendGoodsManageRepository extends JpaRepository<RecommendGoodsManage, Long>,
        JpaSpecificationExecutor<RecommendGoodsManage> {

    @Modifying
    @Query("update RecommendGoodsManage set weight = ?1 ,updateTime = now() where id=?2")
    int updateWeight(BigDecimal weight, Long id);

    @Modifying
    @Query("update RecommendGoodsManage set weight = null ,updateTime = now() where id=?1")
    int updateWeightNull(Long id);

    @Modifying
    @Query("update RecommendGoodsManage set noPushType = ?1 ,updateTime = now() where id=?2")
    int updateNoPush(NoPushType noPushType, Long id);

    @Modifying
    @Query("update RecommendGoodsManage set noPushType = ?1 ,updateTime = now() where id in ?2")
    int updateNoPushForIds(NoPushType noPushType, List<Long> ids);

}
