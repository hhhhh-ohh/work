package com.wanmi.sbc.customer.api.response.ledgeraccount;

import com.wanmi.sbc.customer.bean.vo.LedgerAccountVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @author edz
 * @className QueryByBusinessIdsResponse
 * @description TODO
 * @date 2022/8/3 10:44
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema
public class QueryByBusinessIdsResponse implements Serializable {

    @Schema(description = "<BusinessId,LedgerAccountVO>")
    private Map<String, LedgerAccountVO> businessIdToLedgerAccountVOMap;

}
