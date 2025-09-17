package com.wanmi.sbc.vas.recommend.goodscorrelationmodelsetting.repository;

import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.vas.recommend.goodscorrelationmodelsetting.model.root.GoodsCorrelationModelSetting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * <p>DAO</p>
 * @author zhongjichuan
 * @date 2020-11-27 11:27:06
 */
@Repository
public interface GoodsCorrelationModelSettingRepository extends JpaRepository<GoodsCorrelationModelSetting, Integer>,
        JpaSpecificationExecutor<GoodsCorrelationModelSetting> {

    /**
     * 单个删除
     * @author zhongjichuan
     */
    @Modifying
    @Query("update GoodsCorrelationModelSetting set delFlag = com.wanmi.sbc.common.enums.DeleteFlag.YES where id = ?1")
    void deleteById(Integer id);

    /**
     * 批量删除
     * @author zhongjichuan
     */
    @Modifying
    @Query("update GoodsCorrelationModelSetting set delFlag = com.wanmi.sbc.common.enums.DeleteFlag.YES where id in ?1")
    void deleteByIdList(List<Integer> idList);

    Optional<GoodsCorrelationModelSetting> findByIdAndDelFlag(Integer id, DeleteFlag delFlag);

}
