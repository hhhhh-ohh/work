package com.wanmi.sbc.customer.ledgerfile.repository;

import com.wanmi.sbc.customer.ledgerfile.model.root.LedgerFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * <p>分账文件DAO</p>
 * @author 许云鹏
 * @date 2022-07-01 16:43:39
 */
@Repository
public interface LedgerFileRepository extends JpaRepository<LedgerFile, String>,
        JpaSpecificationExecutor<LedgerFile> {

    /**
     * 单个删除分账文件
     * @author 许云鹏
     */
    @Override
    @Modifying
    @Query("delete LedgerFile where id = ?1")
    void deleteById(String id);

    /**
     * 批量删除分账文件
     * @author 许云鹏
     */
    @Modifying
    @Query("delete LedgerFile where id in ?1")
    void deleteByIdList(List<String> idList);

    /**
     * 根据ids查询
     * @param ids
     * @return
     */
    List<LedgerFile> findByIdIn(List<String> ids);

}
