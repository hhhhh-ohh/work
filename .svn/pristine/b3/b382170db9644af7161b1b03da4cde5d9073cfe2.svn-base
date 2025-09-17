package com.wanmi.sbc.empower.customerservice.repository;

import com.wanmi.sbc.empower.bean.enums.CustomerServicePlatformType;
import com.wanmi.sbc.empower.customerservice.model.root.CustomerServiceSetting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.wanmi.sbc.common.enums.DeleteFlag;
import java.util.Optional;
import java.util.List;

/**
 * <p>在线客服配置DAO</p>
 * @author 韩伟
 * @date 2021-04-08 15:35:16
 */
@Repository
public interface CustomerServiceSettingRepository extends JpaRepository<CustomerServiceSetting, Long>,
        JpaSpecificationExecutor<CustomerServiceSetting> {

    /**
     * 单个删除在线客服配置
     * @author 韩伟
     */
    @Modifying
    @Query("update CustomerServiceSetting set delFlag = com.wanmi.sbc.common.enums.DeleteFlag.YES where id = ?1")
    void deleteById(Long id);

    Optional<CustomerServiceSetting> findByStoreIdAndPlatformTypeAndDelFlag(Long storeId,
                                                                           CustomerServicePlatformType platformType,
                                                                          DeleteFlag delFlag);

    @Modifying
    @Query("update CustomerServiceSetting set status = com.wanmi.sbc.common.enums.DefaultFlag.NO where platformType = 3")
    void closeQiYuForStore();

    @Modifying
    @Query("update CustomerServiceSetting set status = com.wanmi.sbc.common.enums.DefaultFlag.NO where platformType = 2")
    void closeWXForStore();

}
