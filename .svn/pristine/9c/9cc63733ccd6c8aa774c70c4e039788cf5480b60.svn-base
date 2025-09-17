package com.wanmi.sbc.vas.iep.iepsetting.repository;

import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.vas.iep.iepsetting.model.root.IepSetting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>企业购设置DAO</p>
 *
 * @author 宋汉林
 * @date 2020-03-02 20:15:04
 */
@Repository
public interface IepSettingRepository extends JpaRepository<IepSetting, String>,
        JpaSpecificationExecutor<IepSetting> {

    /**
     * 单个删除企业购设置
     *
     * @author 宋汉林
     */
    @Modifying
    @Query("update IepSetting set delFlag = com.wanmi.sbc.common.enums.DeleteFlag.YES where id = ?1")
    void deleteById(String id);

    /**
     * 批量删除企业购设置
     *
     * @author 宋汉林
     */
    @Modifying
    @Query("update IepSetting set delFlag = com.wanmi.sbc.common.enums.DeleteFlag.YES where id in ?1")
    void deleteByIdList(List<String> idList);

    @Query("from IepSetting where id = ?1 and delFlag = ?2")
    IepSetting findByIdAndDelFlag(String id, DeleteFlag delFlag);

    /**
     * 查询企业购信息第一条, 有且只有一条
     *
     * @param delFlag
     * @return
     */
    @Query("from IepSetting where delFlag = ?1")
    List<IepSetting> findDelFlag(DeleteFlag delFlag);

}
