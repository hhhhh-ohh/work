package com.wanmi.sbc.marketing.newcomerpurchaseconfig.repository;

import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.marketing.newcomerpurchaseconfig.model.root.NewcomerPurchaseConfig;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * <p>新人专享设置DAO</p>
 * @author zhanghao
 * @date 2022-08-19 14:28:12
 */
@Repository
public interface NewcomerPurchaseConfigRepository extends JpaRepository<NewcomerPurchaseConfig, Integer>,
        JpaSpecificationExecutor<NewcomerPurchaseConfig> {

    /**
     * 单个删除新人专享设置
     * @author zhanghao
     */
    @Modifying
    @Query("update NewcomerPurchaseConfig set delFlag = com.wanmi.sbc.common.enums.DeleteFlag.YES where id = ?1")
    void deleteById(Integer id);

    /**
     * 批量删除新人专享设置
     * @author zhanghao
     */
    @Modifying
    @Query("update NewcomerPurchaseConfig set delFlag = com.wanmi.sbc.common.enums.DeleteFlag.YES where id in ?1")
    void deleteByIdList(List<Integer> idList);

    /**
     * 查询单个新人专享设置
     * @author zhanghao
     */
    Optional<NewcomerPurchaseConfig> findByIdAndDelFlag(Integer id, DeleteFlag delFlag);

    /**
     * 查询设置信息
     * @param deleteFlag
     * @return
     */
    NewcomerPurchaseConfig findFirstByDelFlag(DeleteFlag deleteFlag);

}
