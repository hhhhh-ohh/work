package com.wanmi.sbc.marketing.giftcard.repository;

import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.marketing.bean.enums.GiftCardDetailStatus;
import com.wanmi.sbc.marketing.giftcard.model.root.GiftCardDetail;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * <p>礼品卡详情DAO</p>
 * @author 马连峰
 * @date 2022-12-09 14:08:26
 */
@Repository
public interface GiftCardDetailRepository extends JpaRepository<GiftCardDetail, String>,
        JpaSpecificationExecutor<GiftCardDetail> {

    /**
     * 单个删除礼品卡详情
     * @author 马连峰
     */
    @Modifying
    @Query("update GiftCardDetail set delFlag = com.wanmi.sbc.common.enums.DeleteFlag.YES where giftCardNo = ?1")
    void deleteById(String giftCardNo);

    /**
     * 批量删除礼品卡详情
     * @author 马连峰
     */
    @Modifying
    @Query("update GiftCardDetail set delFlag = com.wanmi.sbc.common.enums.DeleteFlag.YES where batchNo = ?1")
    void deleteByBatchNo(String batchNo);

    /**
     * 批量删除礼品卡详情
     * @author 马连峰
     */
    @Modifying
    @Query("update GiftCardDetail set delFlag = com.wanmi.sbc.common.enums.DeleteFlag.YES where giftCardNo in ?1")
    void deleteByIdList(List<String> giftCardNoList);

    /**
     * 根据批次号查询礼品卡详情列表
     * @author 马连峰
     */
    List<GiftCardDetail> findByBatchNoAndDelFlag(String batchNo, DeleteFlag delFlag);

    /**
     * 查询单个礼品卡详情
     * @author 马连峰
     */
    Optional<GiftCardDetail> findByGiftCardNoAndDelFlag(String id, DeleteFlag delFlag);

    /**
     * @description 更新礼品卡详情--礼品卡状态
     * @author  lvzhenwei
     * @date 2022/12/10 1:57 下午
     **/
    @Modifying
    @Query("update GiftCardDetail set cardDetailStatus = ?1, activationPerson = ?2, expirationTime = ?3,activationTime = now()  where giftCardNo in ?4")
    void updateGiftCardDetailActivated(GiftCardDetailStatus cardDetailStatus, String customerId, LocalDateTime expirationTime,String giftCardNo);

    /**
     * @description   根据指定多少月来修改过期时间
     * @author  wu
     * @date: 2022/12/12 10:33
     * @param giftCardId   礼品卡Id
     * @param rangeMonth   根据指定多少月来修改过期时间
     * @return
     **/
    @Modifying
    @Query(value = "update gift_card_detail set  expiration_time = DATE_ADD(activation_time, INTERVAL :rangeMonth month) " +
            "where gift_card_id = :giftCardId and activation_time is not null ", nativeQuery = true)
    void updateExpirationTimeByRangeMonth(@Param("giftCardId")Long giftCardId,@Param("rangeMonth") int rangeMonth);

    /**
     * @description   根据具体时间来修改过期时间
     * @author  wu
     * @date: 2022/12/12 10:33
     * @param giftCardId   礼品卡Id
     * @param expirationTime   根据指定多少月来修改过期时间
     * @return
     **/
    @Modifying
    @Query("update GiftCardDetail SET expirationTime = ?2 where giftCardId = ?1")
    void updateExpirationTime(Long giftCardId, LocalDateTime expirationTime);


}
