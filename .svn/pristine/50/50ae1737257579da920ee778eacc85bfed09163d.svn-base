package com.wanmi.sbc.goods.api.response.freight;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.goods.bean.vo.FreightCartVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Schema
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CartListResponse extends BasicResponse {

    /**
     * 运费模板信息
     */
    @Schema(description = "运费模板信息")
    private List<FreightCartVO> freightCateVOList;
}
