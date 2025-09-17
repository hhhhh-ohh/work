package com.wanmi.sbc.setting.payadvertisement.repository;

import com.wanmi.sbc.setting.payadvertisement.model.root.PayAdvertisement;
import com.wanmi.sbc.setting.payadvertisement.model.root.PayAdvertisementStore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author 黄昭
 * @className PayAdvertisementStoreRepository
 * @description 广告页配置门店关联
 * @date 2022/4/6 10:50
 **/
@Repository
public interface PayAdvertisementStoreRepository extends JpaRepository<PayAdvertisementStore, Long>,
        JpaSpecificationExecutor<PayAdvertisementStore> {

    @Query("FROM PayAdvertisementStore WHERE payAdvertisementId = :id")
    List<PayAdvertisementStore> findByPayAdvertisementId(@Param("id") Long id);
}