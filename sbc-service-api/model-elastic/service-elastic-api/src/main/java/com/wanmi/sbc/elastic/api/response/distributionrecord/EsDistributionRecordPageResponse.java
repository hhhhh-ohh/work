package com.wanmi.sbc.elastic.api.response.distributionrecord;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.marketing.bean.vo.DistributionRecordVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;



/**
 * @author houshuai
 * 分页分销记录结果
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema
public class EsDistributionRecordPageResponse extends BasicResponse {

    /**
     * DistributionRecord分页结果
     */
    @Schema(description = "分页分销记录结果")
    private MicroServicePage<DistributionRecordVO> distributionRecordVOPage;
}