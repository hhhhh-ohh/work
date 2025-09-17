package com.wanmi.ares.report.customer.dao;

import com.wanmi.ares.source.model.root.CompanyInfo;
import com.wanmi.ares.source.model.root.Store;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface CompanyInfoMapper {

    CompanyInfo queryByCompanyCode(@Param("companyCode") String companyCode);

    CompanyInfo queryByCompanyId(@Param("companyId") int companyId);

}

