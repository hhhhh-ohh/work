package com.wanmi.sbc.marketing.newcomerpurchasecoupon.repository;

import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.marketing.newcomerpurchasecoupon.model.root.NewcomerPurchaseCoupon;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * <p>新人购优惠券DAO</p>
 * @author zhanghao
 * @date 2022-08-19 14:27:36
 */
@Repository
public interface NewcomerPurchaseCouponRepository extends JpaRepository<NewcomerPurchaseCoupon, Integer>,
        JpaSpecificationExecutor<NewcomerPurchaseCoupon> {


    /**
     * 批量删除新人购优惠券
     * @author zhanghao
     */
    @Modifying
    @Query("update NewcomerPurchaseCoupon set delFlag = com.wanmi.sbc.common.enums.DeleteFlag.YES where id in ?1")
    void deleteByIdList(List<Integer> idList);

    /**
     * 查询单个新人购优惠券
     * @author zhanghao
     */
    Optional<NewcomerPurchaseCoupon> findByIdAndDelFlag(Integer id, DeleteFlag delFlag);


    /**
     * 编辑新人购优惠券
     * @author zhanghao
     */
    @Modifying
    @Query("update NewcomerPurchaseCoupon set couponStock = couponStock + ?1, activityStock = activityStock + ?1, groupOfNum = ?2, updateTime = now()  where id = ?3")
    void modifyStockAndGroupNum(Long couponStock, Integer groupOfNum, Integer id);

    /**
     * 根据优惠卷id查询新人购优惠券
     * @param couponId
     * @param delFlag
     * @return
     */
    NewcomerPurchaseCoupon findByCouponIdAndDelFlag(String couponId,DeleteFlag delFlag);

    /**
     * 编辑新人购优惠券的优惠券名称
     * @author zhanghao
     */
    @Modifying
    @Query("update NewcomerPurchaseCoupon set couponName = ?1 where id = ?2")
    void modifyCouponName(String couponName, Integer id);

    /**
     * 查询可领取的优惠券
     * @return
     */
    @Query("from NewcomerPurchaseCoupon where delFlag = com.wanmi.sbc.common.enums.DeleteFlag.NO and couponStock > 0")
    List<NewcomerPurchaseCoupon> listFetchCoupons();

    /**
     * 查询可领取的优惠券
     * @return
     */
    @Query("from NewcomerPurchaseCoupon where delFlag = com.wanmi.sbc.common.enums.DeleteFlag.NO and couponStock > 0 and couponId in (?1)")
    List<NewcomerPurchaseCoupon> listFetchCoupons(List<String> couponIds);

    @Modifying
    @Query(value = "update NewcomerPurchaseCoupon set couponStock = couponStock - ?2 where couponId = ?1 and delFlag = com.wanmi.sbc.common.enums.DeleteFlag.NO")
    void subCouponStock(String couponId, Long stock);

    @Modifying
    @Query(value = "update NewcomerPurchaseCoupon set couponStock = couponStock + ?2 where couponId = ?1 and delFlag = com.wanmi.sbc.common.enums.DeleteFlag.NO")
    void addCouponStock(String couponId, Long stock);

    /**
     * 查询未删除的优惠券
     * @return
     */
    @Query("from NewcomerPurchaseCoupon where delFlag = com.wanmi.sbc.common.enums.DeleteFlag.NO")
    List<NewcomerPurchaseCoupon> getCoupons();
}
