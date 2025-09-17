package com.wanmi.ares.report.customer.dao;

import com.wanmi.ares.source.model.root.Store;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface StoreMapper {

    int insert(@Param("store") Store store);

    Store queryById(@Param("storeId") String storeId);

    List<Store> queryByCompanyIds(@Param("companyIds") List<String> companyIds);

    List<Store> queryByCondition(Map<String, Object> params);

    int updateById(@Param("store") Store store);


}

