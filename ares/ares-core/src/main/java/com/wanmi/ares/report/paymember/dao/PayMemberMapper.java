package com.wanmi.ares.report.paymember.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

/**
 * @author xuyunpeng
 * @className PayMemberMapper
 * @description 付费会员Mapper
 * @date 2022/5/25 3:36 PM
 **/
@Repository
public interface PayMemberMapper {

    /**
     * 查询总数
     * @param date
     * @return
     */
    long queryTotal(@Param("date") LocalDate date);

    /**
     * 查询新增会员数
     * @param date
     * @return
     */
    long queryGrowthCount(@Param("date") LocalDate date);

    /**
     * 查询过期未续费会员数
     * @param date
     * @return
     */
    long queryOverTimeCount(@Param("date") LocalDate date);
}
