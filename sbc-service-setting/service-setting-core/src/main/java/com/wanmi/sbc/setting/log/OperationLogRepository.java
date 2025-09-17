package com.wanmi.sbc.setting.log;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * 操作日志仓库
 * Created by dyt on 2017/5/15.
 */
public interface OperationLogRepository extends JpaRepository<OperationLog, Long>, JpaSpecificationExecutor<OperationLog> {


    List<OperationLog> findTop10ByEmployeeIdAndOpModuleAndCompanyInfoIdAndStoreIdOrderByOpTimeDesc(String employeeId,
                                                                                                   String opModule,
                                                                                                   Long companyId,
                                                                                                   Long storeId);
}
