package com.wanmi.sbc.setting.appexternallink.repository;

import com.wanmi.sbc.setting.appexternallink.model.root.AppExternalLink;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.wanmi.sbc.common.enums.DeleteFlag;
import java.util.Optional;
import java.util.List;

/**
 * <p>AppExternalLinkDAO</p>
 * @author 黄昭
 * @date 2022-09-28 14:16:09
 */
@Repository
public interface AppExternalLinkRepository extends JpaRepository<AppExternalLink, Long>,
        JpaSpecificationExecutor<AppExternalLink> {

    /**
     * 单个删除AppExternalLink
     * @author 黄昭
     */
    @Modifying
    @Query("update AppExternalLink set delFlag = com.wanmi.sbc.common.enums.DeleteFlag.YES where id = ?1")
    void deleteById(Long id);

    /**
     * 批量删除AppExternalLink
     * @author 黄昭
     */
    @Modifying
    @Query("update AppExternalLink set delFlag = com.wanmi.sbc.common.enums.DeleteFlag.YES where id in ?1")
    void deleteByIdList(List<Long> idList);

    /**
     * 查询单个AppExternalLink
     * @author 黄昭
     */
    Optional<AppExternalLink> findByIdAndDelFlag(Long id, DeleteFlag delFlag);

    /**
     * 根据配置Id删除页面链接
     * @author 黄昭
     */
    @Modifying
    @Query("update AppExternalLink set delFlag = com.wanmi.sbc.common.enums.DeleteFlag.YES where configId in ?1")
    void deleteByConfigId(Long id);
}
