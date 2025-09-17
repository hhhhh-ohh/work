package com.wanmi.sbc.goods.api.response.price.adjustment;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.goods.bean.vo.PriceAdjustmentRecordVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * <p>调价记录表列表结果</p>
 * @author chenli
 * @date 2020-12-09 19:57:21
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PriceAdjustmentRecordListResponse extends BasicResponse {
    private static final long serialVersionUID = 1L;

    /**
     * 调价记录表列表结果
     */
    @Schema(description = "调价记录表列表结果")
    private List<PriceAdjustmentRecordVO> priceAdjustmentRecordVOList;
}
