package com.wanmi.sbc.customer.invoice.repository;

import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.customer.bean.enums.CheckState;
import com.wanmi.sbc.customer.bean.enums.InvalidFlag;
import com.wanmi.sbc.customer.bean.enums.InvoiceStyle;
import com.wanmi.sbc.customer.invoice.model.root.CustomerInvoice;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * 客户增专信息数据层
 * Created by CHENLI on 2017/4/21.
 */
@Repository
public interface CustomerInvoiceRepository extends JpaRepository<CustomerInvoice, Long>,
        JpaSpecificationExecutor<CustomerInvoice> {

    /**
     * 根据会员ID查询客户的增专票信息
     *
     * @param customerId
     * @return
     */
    @Query(value = "from CustomerInvoice e where e.delFlag = com.wanmi.sbc.common.enums.DeleteFlag.NO and e.customerId = :customerId and e.checkState = 1 order by e.updateTime desc ")
    List<CustomerInvoice> findByCustomerId(@Param("customerId") String customerId);

    /**
     * 根据会员ID查询客户的增专票信息
     *
     * @param customerId
     * @return
     */
    @Query(value = "from CustomerInvoice e where e.delFlag = com.wanmi.sbc.common.enums.DeleteFlag.NO and e.customerId = :customerId and e.checkState = 1 and e.invoiceStyle=:invoiceStyle order by e.updateTime desc")
    Optional<CustomerInvoice> findByCustomerIdAnAndInvoiceStyle(@Param("customerId") String customerId,@Param("invoiceStyle") InvoiceStyle invoiceStyle);

    /**
     * 根据会员ID查询客户的增专票信息
     *
     * @param customerId
     * @return
     */
    List<CustomerInvoice> findByCustomerIdAndDelFlagOrderByUpdateTimeDesc(String customerId, DeleteFlag delFlag);

    /**
     * 根据会员ID查询客户的增专票信息
     *
     * @param customerId
     * @return
     */
    List<CustomerInvoice> findByCustomerIdAndDelFlagAndInvoiceStyleOrderByUpdateTimeDesc(String customerId, DeleteFlag delFlag, InvoiceStyle invoiceStyle);

    /**
     * 根据会员ID查询客户的增专票信息
     *
     * @param customerId
     * @return
     */
    List<CustomerInvoice> findByCustomerIdAndCheckState(String customerId, CheckState checkState);

    /**
     * 根据增专票ID查询客户的增专票信息
     *
     * @param customerInvoiceId
     * @param deleteFlag
     * @return
     */
    Optional<CustomerInvoice> findByCustomerInvoiceIdAndDelFlag(Long customerInvoiceId, DeleteFlag deleteFlag);

    /**
     * 根据纳税人识别号查询客户的增专票信息
     *
     * @param taxpayerNumber
     * @param deleteFlag
     * @return
     */
    Optional<CustomerInvoice> findByTaxpayerNumberAndDelFlagAndCustomerId(String taxpayerNumber, DeleteFlag deleteFlag,String customerId);

    /**
     * 批量删除 增专票信息
     *
     * @param ids
     */
    @Query("update CustomerInvoice e set e.delFlag = com.wanmi.sbc.common.enums.DeleteFlag.YES where e.delFlag = com.wanmi.sbc.common.enums.DeleteFlag.NO and e.customerInvoiceId in :ids")
    @Modifying
    void deleteCustomerInvoicesByIds(@Param("ids") List<Long> ids);

    /**
     * 批量删除 根据会员id
     *
     * @param customerIds customerIds
     * @return rows
     */
    @Query("update CustomerInvoice c set c.delFlag = com.wanmi.sbc.common.enums.DeleteFlag.YES where c.delFlag = com.wanmi.sbc.common.enums.DeleteFlag.NO and c.customerId in :customerIds")
    @Modifying
    int deleteCustomerInvoiceByCustomerIds(@Param("customerIds") List<String> customerIds);

    /**
     * 单条 / 批量审核 增专票信息
     *
     * @param ids
     */
    @Query("update CustomerInvoice e set e.checkState = :checkState where e.customerInvoiceId in :ids")
    @Modifying
    void checkCustomerInvoice(@Param("checkState") CheckState checkState, @Param("ids") List<Long> ids);

    /**
     * 批量作废 增专票信息
     *
     * @param ids
     */
    @Query("update CustomerInvoice e set e.invalidFlag = :invalidFlag , e.checkState=com.wanmi.sbc.customer.bean" +
            ".enums.CheckState.NOT_PASS where e.customerInvoiceId in " +
            ":ids")
    @Modifying
    void invalidCustomerInvoice(@Param("invalidFlag") InvalidFlag invalidFlag, @Param("ids") List<Long> ids);

    /**
     * 驳回增专票信息
     *
     * @param rejectReason      rejectReason
     * @param customerInvoiceId customerInvoiceId
     * @return rows
     */
    @Modifying
    @Query("update CustomerInvoice e set e.rejectReason = :rejectReason" +
            ", e.checkState = :checkState where e.customerInvoiceId = :customerInvoiceId")
    int rejectInvoice(@Param("rejectReason") String rejectReason,
                      @Param("customerInvoiceId") Long customerInvoiceId,
                      @Param("checkState") CheckState checkState
    );

    /**
     * 作废 增专票信息
     *
     * @param customerId
     */
    @Query("update CustomerInvoice e set e.invalidFlag = :invalidFlag , e.checkState=com.wanmi.sbc.customer.bean" +
            ".enums.CheckState.NOT_PASS where e.customerId = " +
            ":customerId")
    @Modifying
    void invalidCustomerInvoiceByCustomerId(@Param("invalidFlag") InvalidFlag invalidFlag,
                                            @Param("customerId") String customerId);

    /**
     * 批量查询增专票信息
     * @param customerInvoiceIds
     * @param delFlag
     * @return
     */
    List<CustomerInvoice> findAllByCustomerInvoiceIdInAndDelFlag(List<Long> customerInvoiceIds, DeleteFlag delFlag);

    /**
     * 批量查询增专票信息
     * @param customerInvoiceId
     * @param delFlag
     * @return
     */
    Optional<CustomerInvoice> findAllByCustomerInvoiceIdAndCustomerIdAndDelFlag(Long customerInvoiceId,String customerId, DeleteFlag delFlag);
}
