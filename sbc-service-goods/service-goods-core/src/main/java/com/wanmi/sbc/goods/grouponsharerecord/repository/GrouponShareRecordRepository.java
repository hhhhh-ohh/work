package com.wanmi.sbc.goods.grouponsharerecord.repository;

import com.wanmi.sbc.goods.grouponsharerecord.model.root.GrouponShareRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import java.util.Optional;

/**
 * <p>拼团分享访问记录DAO</p>
 * @author zhangwenchang
 * @date 2021-01-07 15:02:41
 */
@Repository
public interface GrouponShareRecordRepository extends JpaRepository<GrouponShareRecord, Long>,
        JpaSpecificationExecutor<GrouponShareRecord> {

}
