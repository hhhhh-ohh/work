package com.wanmi.sbc.customer.company.repository;

import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.customer.company.model.root.CompanyInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * 公司信息数据源
 * Created by CHENLI on 2017/5/12.
 */
@Repository
public interface CompanyInfoRepository extends JpaRepository<CompanyInfo, Long>, JpaSpecificationExecutor<CompanyInfo> {

    /**
     * 根据id查询店铺
     *
     * @param companyInfoId
     * @param deleteFlag
     * @return
     */
    CompanyInfo findByCompanyInfoIdAndDelFlag(Long companyInfoId, DeleteFlag deleteFlag);

    /**
     * 根据店铺名称查询店铺
     *
     * @param supplierName
     * @param deleteFlag
     * @return
     */
    Optional<CompanyInfo> findBySupplierNameAndDelFlag(String supplierName, DeleteFlag deleteFlag);

    /**
     * 查询统一社会信用代码是否存在
     *
     * @param code
     * @param id
     * @return
     */
    List<CompanyInfo> findBySocialCreditCodeAndCompanyInfoIdNotAndDelFlag(String code, Long id, DeleteFlag
            deleteFlag);

    /**
     * 根据商家id列表查询
     *
     * @param companyInfoIds
     * @param deleteFlag
     * @return
     */
    @Query("select c from CompanyInfo c where c.companyInfoId in (:companyInfoIds) and c.delFlag = :deleteFlag")
    List<CompanyInfo> queryByCompanyinfoIds(@Param("companyInfoIds") List<Long> companyInfoIds, @Param("deleteFlag")
            DeleteFlag deleteFlag);

    /***
     * 根据精确的CompanyCode查询（区分大小写）
     * @param companyCode   公司编码
     * @param deleteFlag    删除标记
     * @return              公司对象
     */
    @Query(value = "select * from company_info c where binary c.company_code = ?1 and c.del_flag = ?2", nativeQuery = true)
    CompanyInfo queryByPreciseCompanyCode(String companyCode, Integer deleteFlag);

    /**
     * 公司营业执照
     * @author zqk
     */
    @Modifying
    @Query("update CompanyInfo c set c.businessLicence = ?2 where c.companyInfoId = ?1")
    int modifyBusinessLicence(Long companyInfoId, String businessLicence);

}
