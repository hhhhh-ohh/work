package com.wanmi.sbc.vas.commodityscoringalgorithm.repository;

import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.vas.commodityscoringalgorithm.model.root.CommodityScoringAlgorithm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * <p>商品评分算法DAO</p>
 * @author Bob
 * @date 2021-03-03 14:27:46
 */
@Repository
public interface CommodityScoringAlgorithmRepository extends JpaRepository<CommodityScoringAlgorithm, Long>,
        JpaSpecificationExecutor<CommodityScoringAlgorithm> {

    /**
     * 单个删除商品评分算法
     * @author Bob
     */
    @Modifying
    @Query("update CommodityScoringAlgorithm set delFlag = com.wanmi.sbc.common.enums.DeleteFlag.YES where id = ?1")
    void deleteById(Long id);

    /**
     * 批量删除商品评分算法
     * @author Bob
     */
    @Modifying
    @Query("update CommodityScoringAlgorithm set delFlag = com.wanmi.sbc.common.enums.DeleteFlag.YES where id in ?1")
    void deleteByIdList(List<Long> idList);

    Optional<CommodityScoringAlgorithm> findByIdAndDelFlag(Long id, DeleteFlag delFlag);

}
