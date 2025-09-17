package com.wanmi.sbc.marketing.giftcard.repository;

import com.wanmi.sbc.marketing.bean.enums.GiftCardStatus;
import com.wanmi.sbc.marketing.bean.enums.GiftCardType;
import com.wanmi.sbc.marketing.giftcard.model.root.UserGiftCard;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * @author wur
 * @className UserGiftCardRepository
 * @description 礼品卡信息
 * @date 2022/12/8 16:16
 **/
@Repository
public interface UserGiftCardRepository extends JpaRepository<UserGiftCard, Long>, JpaSpecificationExecutor<UserGiftCard> {

    /**
     * @description 根据礼品卡id获取礼品卡详情
     * @author  lvzhenwei
     * @date 2022/12/10 4:54 下午
     * @param userGiftCardId
     * @return com.wanmi.sbc.marketing.giftcard.model.root.UserGiftCard
     **/
    Optional<UserGiftCard> getByUserGiftCardId(Long userGiftCardId);

    /**
     * @description 根据礼品卡编号获取礼品卡详情
     * @author  lvzhenwei
     * @date 2022/12/10 4:54 下午
     * @param giftCardNo
     * @return com.wanmi.sbc.marketing.giftcard.model.root.UserGiftCard
     **/
    Optional<UserGiftCard>  getByGiftCardNoAndCustomerId(String giftCardNo,String customerId);

    /**
     * @description 更新礼品卡详情--礼品卡状态、以及余额
     * @author  lvzhenwei
     * @date 2022/12/10 1:57 下午
     **/
    @Modifying
    @Query("update UserGiftCard set cardStatus = ?1,balance = ?2 where giftCardNo in ?3 and customerId = ?4 and cardStatus != 2")
    int updateGiftCardDetailCancelStatus(GiftCardStatus giftCardStatus, BigDecimal balance, String giftCardNo, String customerId);

    /**
     * @description 查询会员可用礼品卡金额
     * @author  lvzhenwei
     * @date 2022/12/10 1:57 下午
     **/
    @Query("select sum(balance) from UserGiftCard where customerId = ?1 and cardStatus = ?2 and (now() < expirationTime or expirationTime is null)")
    BigDecimal getGiftCardBalanceByCustomerId(String customerId, GiftCardStatus giftCardStatus);


    /**
     * @description  查询面值
     * @author  wur
     * @date: 2022/12/23 16:47
     * @param customerId
     * @param giftCardStatus
     * @return
     **/
    @Query("select sum(parValue) from UserGiftCard where customerId = ?1 and cardStatus = ?2 and (now() < expirationTime or expirationTime is null)")
    Long getGiftCardParValueByCustomerId(String customerId, GiftCardStatus giftCardStatus);

    /**
     * @description   根据指定多少月来修改过期时间
     * @author  wu
     * @date: 2022/12/12 10:33
     * @param giftCardId   礼品卡Id
     * @param rangeMonth   根据指定多少月来修改过期时间
     * @return
     **/
    @Modifying
    @Query(value = "update user_gift_card set  expiration_time = DATE_ADD(activation_time, INTERVAL :rangeMonth month) " +
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
    @Query("update UserGiftCard SET expirationTime = ?2 where giftCardId = ?1")
    void updateExpirationTime(Long giftCardId, LocalDateTime expirationTime);

    /**
     * @description  查询用户可用礼品卡数量
     * @author  wur
     * @date: 2022/12/12 15:56
     * @param customerId
     * @return
     **/
    @Query("select count(1) from UserGiftCard where customerId = ?1 and giftCardType = ?2 and cardStatus = 1 and (now" +
            "() < " +
            "expirationTime or expirationTime is null) and (balance > 0 or balance is null)")
    Long getUseNumCustomerId(String customerId, GiftCardType giftCardType);

    //    @Query("select count(1) from UserGiftCard where customerId = ?1 and cardStatus = 1 and (now" +
    //            "() < " +
    //            "expirationTime or expirationTime is null) and (balance > 0 or balance is null)")
    //查询 cardStatus 所有状态
    @Query("select count(1) from UserGiftCard where customerId = ?1 and (now" +
            "() < " +
            "expirationTime or expirationTime is null) and (balance > 0 or balance is null)")
    Long getUseNumCustomerId(String customerId);

    /**
     * @description  查询用户不可用礼品卡数量
     * @author  wur
     * @date: 2022/12/12 15:56
     * @param customerId
     * @return
     **/
    @Query("select count(1) from UserGiftCard where customerId = ?1 and giftCardType = ?2 and (cardStatus = 2 or " +
            "(expirationTime is not null and now() > expirationTime) or (cardStatus = 1 and balance <= 0))")
    Long getInvalidCustomerId(String customerId, GiftCardType giftCardType);

    @Query("select count(1) from UserGiftCard where customerId = ?1 and (cardStatus = 2 or " +
            "(expirationTime is not null and now() > expirationTime) or (cardStatus = 1 and balance <= 0))")
    Long getInvalidCustomerId(String customerId);

    /**
     * @description  查询待激活礼品卡数量
     * @author  wur
     * @date: 2022/12/12 15:56
     * @param customerId
     * @return
     **/
    @Query("select count(1) from UserGiftCard where customerId = ?1 and giftCardType = ?2 and cardStatus = 0 and " +
            "(now() < expirationTime or expirationTime is null)")
    Long getNotActiveNumCustomerId(String customerId, GiftCardType giftCardType);

    @Query("select count(1) from UserGiftCard where customerId = ?1 and cardStatus = 0 and " +
            "(now() < expirationTime or expirationTime is null)")
    Long getNotActiveNumCustomerId(String customerId);

    @Override
    List<UserGiftCard> findAll(Specification<UserGiftCard> spec);

    /**
     * 根据用户ID和卡号查询
     * @param customerId   用户Id
     * @param giftCardNo   卡号
     * @return
     */
    Optional<UserGiftCard> getByCustomerIdAndGiftCardNo(String customerId, String giftCardNo);

    /**
     * @description     根据礼品卡ID编辑用户礼品卡名称
     * @author  wur
     * @date: 2022/12/15 10:47
     * @param giftCardId
     * @param name
     * @return
     **/
    @Modifying
    @Query("update UserGiftCard SET giftCardName = ?2 where giftCardId = ?1")
    void updateGiftCardName(Long giftCardId, String name);

    /**
     * @description 查询指定礼品卡卡号列表余额之和
     * @author malianfeng
     * @date 2022/12/15 20:09
     * @param giftCardNoList 礼品卡卡号列表
     * @return java.math.BigDecimal
     */
    @Query(value =
            "SELECT SUM(u1.balance) FROM user_gift_card u1 " +
            "INNER JOIN (" +
                    "SELECT MAX(user_gift_card_id) AS user_gift_card_id FROM user_gift_card " +
                    "WHERE gift_card_no IN (?1) " +
                    "GROUP BY gift_card_no) u2 " +
            "ON u1.user_gift_card_id = u2.user_gift_card_id", nativeQuery = true)
    BigDecimal queryBalance(List<String> giftCardNoList);

    /**
     * @description 查询指定礼品卡卡号的记录
     * @author malianfeng
     * @date 2022/12/16 12:30
     * @param giftCardNoList 礼品卡卡号列表
     * @return java.util.List<com.wanmi.sbc.marketing.giftcard.model.root.UserGiftCard>
     */
    List<UserGiftCard> findByGiftCardNoIn(List<String> giftCardNoList);

    /**
     * @description 查询指定用户未销卡的卡号列表
     * @author malianfeng
     * @date 2022/12/26 14:20
     * @param customerId 用户ID
     * @return java.util.List<java.lang.String>
     */
    @Query("select giftCardNo from UserGiftCard where customerId = ?1 and cardStatus <> 2")
    List<String> findNotCancelGiftCardNoByCustomerId(String customerId);

    @Query(value = """
    SELECT gift_card_no FROM user_gift_card
    WHERE card_status = 0 AND (:orderSn IS NULL OR order_sn = :orderSn)
    AND (:orderDetailRetailId IS NULL OR order_detail_retail_id = :orderDetailRetailId)
    LIMIT :number
    """, nativeQuery = true)
    List<String> selectGiftCardNoList(
            @Param("orderSn") String orderSn,
            @Param("orderDetailRetailId") Integer orderDetailRetailId,
            @Param("number") Integer number
    );

    @Modifying
    @Query("update UserGiftCard SET balance = null where userGiftCardId = ?1")
    int updateBalanceNull(Long userGiftCardId);

    List<UserGiftCard> findByOrderSnInAndCardStatus(List<String> orderSns, GiftCardStatus cardStatus);
}