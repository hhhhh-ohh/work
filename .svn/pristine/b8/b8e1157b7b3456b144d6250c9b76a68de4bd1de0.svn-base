package com.wanmi.sbc.marketing.electroniccoupon.repository;

import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.marketing.electroniccoupon.model.root.ElectronicCoupon;
import java.util.List;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * <p>电子卡券表DAO</p>
 * @author 许云鹏
 * @date 2022-01-26 17:18:05
 */
@Repository
public interface ElectronicCouponRepository extends JpaRepository<ElectronicCoupon, Long>,
        JpaSpecificationExecutor<ElectronicCoupon> {

    /**
     * 单个删除电子卡券表
     * @author 许云鹏
     */
    @Override
    @Modifying
    @Query("update ElectronicCoupon set delFlag = com.wanmi.sbc.common.enums.DeleteFlag.YES where id = ?1")
    void deleteById(Long id);

    /**
     * 查询单个电子卡券表
     * @author 许云鹏
     */
    ElectronicCoupon findByIdAndDelFlag(Long id, DeleteFlag delFlag);

    /**
     * 根据卡券名称统计数量
     * @param name
     * @return
     */
    int countByCouponNameAndDelFlagAndStoreId(String name, DeleteFlag deleteFlag, Long storeId);

    /***
     * 根据卡券名称统计数量
     * @param nameList
     * @param deleteFlag
     * @return
     */
    @Query("select count(id) from ElectronicCoupon where delFlag = ?2 and couponName in(?1)")
    int countByCouponNameListAndDelFlag(List<String> nameList, DeleteFlag deleteFlag);

    /**
     * 根据卡券名称统计数量（排除自己）
     * @param id
     * @param name
     * @return
     */
    @Query("select count(id) from ElectronicCoupon where delFlag = com.wanmi.sbc.common.enums.DeleteFlag.NO and id <> ?1 and couponName = ?2 and storeId = ?3")
    int countByCouponNameNotSelf(Long id, String name, Long storeId);


    @Modifying
    @Query("update ElectronicCoupon set freezeStock = freezeStock + ?1 where id = ?2")
    void updateFreezeStock(Long freezeStock,Long id);

    @Modifying
    @Query("update ElectronicCoupon set bindingFlag = ?2 where id in ?1")
    void updateBindingFlag(List<Long> ids, Boolean flag);

}
