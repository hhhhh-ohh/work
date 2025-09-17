package com.wanmi.ares.report.paymember.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @author xuyunpeng
 * @className PayMemberRecordMapper
 * @description 付费记录持久化Mapper
 * @date 2022/5/26 11:13 AM
 **/
@Repository
public interface PayMemberRecordMapper {

    /**
     * 查询续费会员数
     * @param beginTime
     * @param endTime
     * @return
     */
    long queryRenewalCount(@Param("beginTime") String beginTime, @Param("endTime") String endTime);
}
