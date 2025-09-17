package com.wanmi.sbc.setting.authority.repository;

import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.enums.Platform;
import com.wanmi.sbc.setting.authority.model.root.FunctionInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * 系统功能数据源
 * Created by bail on 2017/01/04
 */
@Repository
public interface FunctionRepository extends JpaRepository<FunctionInfo, String>, JpaSpecificationExecutor<FunctionInfo> {

    /**
     * 根据系统类别查询菜单信息
     *
     * @param systemTypeCd 系统类别
     * @param deleteFlag   删除状态
     * @return
     */
    List<FunctionInfo> findBySystemTypeCdAndDelFlagOrderBySort(Platform systemTypeCd, DeleteFlag deleteFlag);

    /**
     * 根据id逻辑删除信息
     *
     * @param functionId 主键
     * @param deleteFlag 删除标识
     * @return 条数
     */
    @Modifying
    @Query("update FunctionInfo set delFlag = ?2 where functionId = ?1")
    int deleteFunctionInfo(String functionId, DeleteFlag deleteFlag);

    int countByFunctionNameAndDelFlagAndSystemTypeCd(String functionName, DeleteFlag deleteFlag, Platform systemTypeCd);

    /**
     * 查询功能名称是否重复
     * @param functionName
     * @param delFlag
     * @param systemTypeCd
     * @param functionId
     * @return
     */
    @Query(nativeQuery = true, value = "select count(function_id) from function_info where function_name = :functionName and del_flag = :delFlag and system_type_cd = :systemTypeCd and function_id != :functionId")
    int checkFunctionName(String functionName, Integer delFlag, String systemTypeCd, String functionId);

    /**
     * 根据menuIdList逻辑删除信息
     *
     * @param menuIdList
     * @param deleteFlag 删除标识
     * @return 条数
     */
    @Modifying
    @Query("update FunctionInfo set delFlag = ?2 where menuId in ?1")
    int deleteFunctionInfoByMenuIdList(List<String> menuIdList, DeleteFlag deleteFlag);
}
