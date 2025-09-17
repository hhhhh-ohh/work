package com.wanmi.sbc.order.wxpayuploadshippinginfo.repository;

import com.wanmi.sbc.order.wxpayuploadshippinginfo.model.root.WxPayUploadShippingInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * <p>微信小程序支付发货信息DAO</p>
 * @author 吕振伟
 * @date 2023-07-24 14:13:21
 */
@Repository
public interface WxPayUploadShippingInfoRepository extends JpaRepository<WxPayUploadShippingInfo, Long>,
        JpaSpecificationExecutor<WxPayUploadShippingInfo> {

    /**
     * 单个删除微信小程序支付发货信息
     * @author 吕振伟
     */
    @Modifying
    @Query("delete from WxPayUploadShippingInfo where id = ?1")
    void deleteById(Long id);

    /**
     * 批量删除微信小程序支付发货信息
     * @author 吕振伟
     */
    @Modifying
    @Query("delete from WxPayUploadShippingInfo where id in ?1")
    void deleteByIdList(List<Long> idList);

    /**
     * 查询单个微信小程序支付发货信息
     * @author 吕振伟
     */
    Optional<WxPayUploadShippingInfo> findById(Long id);

    /**
     * 根据BusinessId查询微信小程序支付发货信息
     * @author 吕振伟
     */
    Optional<WxPayUploadShippingInfo> findByTransactionId(String transactionId);

}
