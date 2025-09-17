package com.wanmi.sbc.empower.pay.repository;


import com.wanmi.sbc.empower.bean.enums.PayGatewayEnum;
import com.wanmi.sbc.empower.bean.enums.TerminalType;
import com.wanmi.sbc.empower.pay.model.root.PayChannelItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by sunkun on 2017/8/3.
 */
@Repository
public interface ChannelItemRepository extends JpaRepository<PayChannelItem, Long>, JpaSpecificationExecutor<PayChannelItem> {
    // todo  SaaS独立收款 依据gatewayName查找
    List<PayChannelItem> findByGatewayName(PayGatewayEnum gatewayName);

    @Query("select p from PayChannelItem p where p.gatewayName = ?1 and p.isOpen = com.wanmi.sbc.empower.bean.enums.IsOpen.YES and p.terminal = ?2 and p.id > 0")
    List<PayChannelItem> findOpenItemByGatewayName(PayGatewayEnum gatewayName, TerminalType terminalType);

    List<PayChannelItem> findByIdIn(List<Long> ids);
}
