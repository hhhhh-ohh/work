package com.wanmi.sbc.marketing.giftcard.repository;

import com.wanmi.sbc.marketing.giftcard.model.root.UniformsGiftCardBinding;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface UniformsGiftCardBindingRepository extends JpaRepository<UniformsGiftCardBinding, Long>, JpaSpecificationExecutor<UniformsGiftCardBinding> {

    /**
     * 根据地市和季节查询单个校服提货卡绑定记录
     * @param city 地市
     * @param season 季节
     * @return 匹配的校服提货卡绑定记录
     */
    UniformsGiftCardBinding findByCityAndSeason( String city, String season);
}
