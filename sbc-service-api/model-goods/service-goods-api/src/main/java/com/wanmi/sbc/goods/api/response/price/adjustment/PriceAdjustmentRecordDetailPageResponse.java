package com.wanmi.sbc.goods.api.response.price.adjustment;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.goods.bean.vo.PriceAdjustmentRecordDetailVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * <p>调价单详情表分页结果</p>
 * @author chenli
 * @date 2020-12-09 19:55:41
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PriceAdjustmentRecordDetailPageResponse extends BasicResponse {
    private static final long serialVersionUID = 1L;

    /**
     * 调价单详情表分页结果
     */
    @Schema(description = "调价单详情表分页结果")
    private MicroServicePage<PriceAdjustmentRecordDetailVO> recordDetailPage;
}
