package com.wanmi.sbc.empower.miniprogramset.repository;

import com.wanmi.sbc.empower.miniprogramset.model.root.MiniProgramSet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.wanmi.sbc.common.enums.DeleteFlag;
import java.util.Optional;
import java.util.List;

/**
 * <p>小程序配置DAO</p>
 * @author zhanghao
 * @date 2021-04-22 17:20:23
 */
@Repository
public interface MiniProgramSetRepository extends JpaRepository<MiniProgramSet, Integer>,
        JpaSpecificationExecutor<MiniProgramSet> {


    Optional<MiniProgramSet> findByIdAndDelFlag(Integer id, DeleteFlag delFlag);


    Optional<MiniProgramSet> findByTypeAndDelFlag(Integer type, DeleteFlag delFlag);

}
