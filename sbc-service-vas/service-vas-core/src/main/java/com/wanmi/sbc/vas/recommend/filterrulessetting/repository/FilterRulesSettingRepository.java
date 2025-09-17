package com.wanmi.sbc.vas.recommend.filterrulessetting.repository;

import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.vas.recommend.filterrulessetting.model.root.FilterRulesSetting;
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
 * @date 2020-11-26 16:32:49
 */
@Repository
public interface FilterRulesSettingRepository extends JpaRepository<FilterRulesSetting, Integer>,
        JpaSpecificationExecutor<FilterRulesSetting> {

    /**
     * 单个删除
     * @author zhongjichuan
     */
    @Modifying
    @Query("update FilterRulesSetting set delFlag = com.wanmi.sbc.common.enums.DeleteFlag.YES where id = ?1")
    void deleteById(Integer id);

    /**
     * 批量删除
     * @author zhongjichuan
     */
    @Modifying
    @Query("update FilterRulesSetting set delFlag = com.wanmi.sbc.common.enums.DeleteFlag.YES where id in ?1")
    void deleteByIdList(List<Integer> idList);

    Optional<FilterRulesSetting> findByIdAndDelFlag(Integer id, DeleteFlag delFlag);

}
