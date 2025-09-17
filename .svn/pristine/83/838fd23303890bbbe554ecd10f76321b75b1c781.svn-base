package com.wanmi.sbc.empower.pay.repository;

import com.wanmi.sbc.empower.pay.model.root.WechatConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import jakarta.transaction.Transactional;
import java.util.List;

/**
 * @description
 * @author  wur
 * @date: 2022/12/2 16:21
 **/
@Repository
public interface WechatConfigRepository extends JpaRepository<WechatConfig, Long>, JpaSpecificationExecutor<WechatConfig> {

    /**
     * @description   更新商家指定场景的微信配置
     * @author wur
     * @date: 2022/12/2 16:21
     * @param sceneType
     * @param storeId
     * @param appId
     * @param secret
     * @return
     */
    @Modifying(clearAutomatically = true)
    @Transactional
    @Query(
            "update WechatConfig set appId=:appId,secret=:secret where sceneType=:sceneType and storeId=:storeId")
    int update(
            @Param("sceneType") Integer sceneType,
            @Param("storeId") Long storeId,
            @Param("appId") String appId,
            @Param("secret") String secret);

    /**
     * @description   查询商家指定场景的微信配置
     * @author wur
     * @date: 2022/12/2 16:21
     * @param sceneType
     * @param storeId
     * @return
     */
    WechatConfig findBySceneTypeAndStoreId(Integer sceneType, Long storeId);

    /**
     * @description   查询商家所有场景的微信配置
     * @author wur
     * @date: 2022/12/2 16:21
     * @param storeId
     * @return
     */
    List<WechatConfig> findBySceneTypeInAndStoreId(List<Integer> sceneTypList, Long storeId);

    /**
     * @description   查询商家所有场景的微信配置
     * @author wur
     * @date: 2022/12/2 16:21
     * @param storeId
     * @return
     */
    List<WechatConfig> findByStoreId(Long storeId);
}
