package com.wanmi.sbc.account.api.request.finance.record;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @author edz
 * @className getBySettlementUuidRequest
 * @description TODO
 * @date 2022/8/15 14:34
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetBySettlementUuidRequest implements Serializable {

    private List<String> settlementUuids;
}
