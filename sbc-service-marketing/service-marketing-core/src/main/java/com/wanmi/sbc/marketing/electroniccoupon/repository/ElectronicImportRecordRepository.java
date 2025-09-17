package com.wanmi.sbc.marketing.electroniccoupon.repository;

import com.wanmi.sbc.marketing.electroniccoupon.model.root.ElectronicImportRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>卡密导入记录表DAO</p>
 * @author 许云鹏
 * @date 2022-01-26 17:36:55
 */
@Repository
public interface ElectronicImportRecordRepository extends JpaRepository<ElectronicImportRecord, String>,
        JpaSpecificationExecutor<ElectronicImportRecord> {

}
