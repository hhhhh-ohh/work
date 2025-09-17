package com.wanmi.sbc.vas.recommend.recommendpositionconfiguration.repository;

import com.wanmi.sbc.vas.bean.enums.recommen.PositionOpenFlag;
import com.wanmi.sbc.vas.recommend.recommendpositionconfiguration.model.root.RecommendPositionConfiguration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * <p>推荐坑位设置DAO</p>
 * @author lvzhenwei
 * @date 2020-11-17 14:14:13
 */
@Repository
public interface RecommendPositionConfigurationRepository extends JpaRepository<RecommendPositionConfiguration, Long>,
        JpaSpecificationExecutor<RecommendPositionConfiguration> {

    @Modifying
    @Query("update RecommendPositionConfiguration set isOpen = ?1,updateTime = now() where id=?2")
    int updateIsOpen(PositionOpenFlag isOpen, Long Id);

}
