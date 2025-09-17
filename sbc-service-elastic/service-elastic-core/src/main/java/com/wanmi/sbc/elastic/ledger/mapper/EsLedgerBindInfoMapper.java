package com.wanmi.sbc.elastic.ledger.mapper;

import com.wanmi.sbc.customer.bean.vo.LedgerReceiverRelVO;
import com.wanmi.sbc.elastic.ledger.model.EsLedgerBindInfo;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;

import java.util.List;

/**
 * @author xuyunpeng
 * @className EsLedgerBindInfoMapper
 * @description
 * @date 2022/7/13 3:24 PM
 **/
@Mapper(componentModel = "spring",builder = @Builder(disableBuilder = true))
public interface EsLedgerBindInfoMapper {

    @Mappings({})
    List<EsLedgerBindInfo> ledgerBindInfoToEsLedgerBindInfo(List<LedgerReceiverRelVO> rels);

    @Mappings({})
    EsLedgerBindInfo ledgerBindInfoToEsLedgerBindInfo(LedgerReceiverRelVO rel);

}
