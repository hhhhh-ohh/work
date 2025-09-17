package com.wanmi.sbc.marketing.electroniccoupon.repository;


import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.marketing.electroniccoupon.model.root.ElectronicCard;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * <p>电子卡密表DAO</p>
 * @author 许云鹏
 * @date 2022-01-26 17:24:59
 */
@Repository
public interface ElectronicCardRepository extends JpaRepository<ElectronicCard, String>,
        JpaSpecificationExecutor<ElectronicCard> {

    /**
     * 批量删除电子卡密表
     * @author 许云鹏
     * @
     */
    @Modifying
    @Query("update ElectronicCard set delFlag = com.wanmi.sbc.common.enums.DeleteFlag.YES where id in ?1")
    void deleteByIdList(List<String> idList);

    /**
     * 根据卡券id批量删除电子卡密表
     * @author 许云鹏
     * @
     */
    @Modifying
    @Query("update ElectronicCard set delFlag = com.wanmi.sbc.common.enums.DeleteFlag.YES where couponId = ?1 and cardState <> 1")
    void deleteByCouponId(Long couponId);

    /**
     * 根据批次id批量删除电子卡密表
     * @author 许云鹏
     * @
     */
    @Modifying
    @Query("update ElectronicCard set delFlag = com.wanmi.sbc.common.enums.DeleteFlag.YES where recordId = ?1 and cardState <> 1")
    void deleteByRecordId(String recordId);

    /**
     * 查询单个电子卡密表
     * @author 许云鹏
     */
    ElectronicCard findByIdAndDelFlag(String id, DeleteFlag delFlag);

    /**
     * 查询不是未发放的卡密的卡号
     * @param ids
     * @return
     */
    @Query("select id from ElectronicCard where id in ?1 and cardState = 1 and delFlag = com.wanmi.sbc.common.enums.DeleteFlag.NO")
    List<String> findByIdListNotSend(List<String> ids);

    /**
     * 根据数据已存在的卡号
     * @param cardNumbers
     * @return
     */
    @Query("select cardNumber from ElectronicCard where  delFlag = com.wanmi.sbc.common.enums.DeleteFlag.NO and couponId = ?2 and cardNumber in ?1")
    List<String> findExistsCardNumber(List<String> cardNumbers, Long couponId);

    /**
     * 根据数据已存在的卡密
     * @param cardPasswords
     * @return
     */
    @Query("select cardPassword from ElectronicCard where delFlag = com.wanmi.sbc.common.enums.DeleteFlag.NO and couponId = ?2 and cardPassword in ?1")
    List<String> findExistsCardPassword(List<String> cardPasswords, Long couponId);

    /**
     * 根据数据已存在的优惠码
     * @param cardPromoCodes
     * @return
     */
    @Query("select cardPromoCode from ElectronicCard where delFlag = com.wanmi.sbc.common.enums.DeleteFlag.NO and couponId = ?2 and cardPromoCode in ?1")
    List<String> findExistsCardPromoCode(List<String> cardPromoCodes, Long couponId);

    /**
     * 统计同一卡券下除自己以外相同卡号的数量
     * @param cardNumber
     * @param deleteFlag
     * @param id
     * @return
     */
    int countByCouponIdAndCardNumberAndDelFlagAndIdNot(Long couponId, String cardNumber, DeleteFlag deleteFlag, String id);

    /**
     * 统计同一卡券下除自己以外相同卡密的数量
     * @param cardPassword
     * @param deleteFlag
     * @param id
     * @return
     */
    int countByCouponIdAndCardPasswordAndDelFlagAndIdNot(Long couponId, String cardPassword, DeleteFlag deleteFlag, String id);

    /**
     * 统计同一卡券下除自己以外相同优惠码的数量
     * @param cardPromoCode
     * @param deleteFlag
     * @param id
     * @return
     */
    int countByCouponIdAndCardPromoCodeAndDelFlagAndIdNot(Long couponId, String cardPromoCode, DeleteFlag deleteFlag, String id);

    /**
     * 批量修改状态未已失效
     * @param time
     */
    @Modifying
    @Query("update ElectronicCard set cardState = 2 where delFlag = com.wanmi.sbc.common.enums.DeleteFlag.NO and cardState = 0 and saleEndTime <= ?1 ")
    void updateCardInvalid(LocalDateTime time);

    /**
     * 批量修改状态未已失效
     * @param time
     */
    @Query("select count(id) from ElectronicCard  where delFlag = com.wanmi.sbc.common.enums.DeleteFlag.NO and cardState = 0 and couponId = ?1 and saleEndTime <= ?2 ")
    Long countCardInvalidByCouponId(Long couponId,LocalDateTime time);

    /**
     * 统计卡券某个状态的数量
     * @param couponId
     * @param deleteFlag
     * @param cardState
     * @return
     */
    long countByCouponIdAndDelFlagAndCardState(Long couponId, DeleteFlag deleteFlag, Integer cardState);

    /**
     * 查询未过销售期的卡券id
     * @param couponIds
     * @param time
     * @return
     */
    @Query("select distinct couponId from ElectronicCard where delFlag = com.wanmi.sbc.common.enums.DeleteFlag.NO and couponId in ?1 and saleEndTime > ?2")
    List<Long> findSaleCoupon(List<Long> couponIds, LocalDateTime time);

    /**
     * 查询过销售期的卡券id
     * @param couponIds
     * @param time
     * @return
     */
    @Query(value = "select b.coupon_id from (" +
            "select coupon_id, max(sale_end_time) time from (" +
            "select coupon_id, sale_end_time from electronic_card where del_flag = 0 and coupon_id in (?1) order by sale_end_time desc" +
            ") a " +
            "GROUP BY coupon_id) b " +
            "where time <= ?2", nativeQuery = true)
    List<Long> findOverdueCoupon(List<Long> couponIds, LocalDateTime time);

    /**
     * 统计未过销售期的卡密数量
     * @param couponId
     * @param time
     * @return
     */
    @Query("select count(id) from ElectronicCard where delFlag = com.wanmi.sbc.common.enums.DeleteFlag.NO and couponId in ?1 and saleEndTime > ?2")
    Long countSaleCouponById(Long couponId, LocalDateTime time);

    /**
     * 根据卡密id批量修改卡密状态
     * @param ids
     * @param cardState
     */
    @Modifying
    @Query("update ElectronicCard set cardState = ?2 where id in ?1")
    void updateCardState(List<String> ids, Integer cardState);


    /**
     * 统计有效的卡密数量
     * @param couponId
     * @param time
     * @return
     */
    @Query("select count(id) from ElectronicCard where delFlag = com.wanmi.sbc.common.enums.DeleteFlag.NO and couponId = ?1 and saleEndTime > ?2 and cardState = 0")
    Long countEffectiveCoupon(Long couponId, LocalDateTime time);



    /**
     * 查询有效的卡券，取最早过期的几个
     * @param couponId
     * @param time
     * @return
     */
    @Query(value = "select * from electronic_card where del_flag = 0 and coupon_id = ?1 and sale_end_time > ?2 and card_state = 0 " +
            "order by sale_end_time asc limit ?3", nativeQuery = true)
    List<ElectronicCard> findSomeEffectiveCoupon(Long couponId, LocalDateTime time, Long num);

    /**
     * 根据订单号查询未发放的卡密
     * @param orderNo
     * @return
     */
    @Query("from ElectronicCard where delFlag = com.wanmi.sbc.common.enums.DeleteFlag.NO and cardState = 0 and orderNo = ?1")
    List<ElectronicCard> findNotSendByOrderNo(String orderNo);

    /**
     * 根据订单号和卡券id查询数量
     * @param orderNo
     * @param couponId
     * @return
     */
    @Query("from ElectronicCard where delFlag = com.wanmi.sbc.common.enums.DeleteFlag.NO and cardState = 0 and orderNo = ?1 and couponId = ?2 and saleEndTime > now()")
    List<ElectronicCard> findByOrderNoAndCouponId(String orderNo, Long couponId);
}
