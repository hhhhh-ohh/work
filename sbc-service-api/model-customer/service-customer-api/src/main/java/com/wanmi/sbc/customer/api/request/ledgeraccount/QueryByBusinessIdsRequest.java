package com.wanmi.sbc.customer.api.request.ledgeraccount;

import com.wanmi.sbc.customer.bean.vo.LedgerAccountVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @author edz
 * @className QueryByBusinessIdsRequest
 * @description TODO
 * @date 2022/8/3 10:44
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema
@Builder
public class QueryByBusinessIdsRequest implements Serializable {

    @Schema(description = "业务ID")
    private List<String> businessIds;
}
