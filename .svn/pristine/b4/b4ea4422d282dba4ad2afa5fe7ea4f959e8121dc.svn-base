package com.wanmi.sbc.vas.recommend.recommendsystemconfig.repository;

import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.vas.recommend.recommendsystemconfig.model.root.RecommendSystemConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * <p>智能推荐配置DAO</p>
 * @author lvzhenwei
 * @date 2020-11-27 16:28:20
 */
@Repository
public interface RecommendSystemConfigRepository extends JpaRepository<RecommendSystemConfig, Long>,
        JpaSpecificationExecutor<RecommendSystemConfig> {

    /**
     * 单个删除智能推荐配置
     * @author lvzhenwei
     */
    @Modifying
    @Query("update RecommendSystemConfig set delFlag = com.wanmi.sbc.common.enums.DeleteFlag.YES where id = ?1")
    void deleteById(Long id);

    /**
     * 批量删除智能推荐配置
     * @author lvzhenwei
     */
    @Modifying
    @Query("update RecommendSystemConfig set delFlag = com.wanmi.sbc.common.enums.DeleteFlag.YES where id in ?1")
    void deleteByIdList(List<Long> idList);

    Optional<RecommendSystemConfig> findByIdAndDelFlag(Long id, DeleteFlag delFlag);

}
