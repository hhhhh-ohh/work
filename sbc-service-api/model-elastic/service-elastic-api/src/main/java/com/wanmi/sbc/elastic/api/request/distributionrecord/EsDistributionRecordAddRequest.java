package com.wanmi.sbc.elastic.api.request.distributionrecord;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.marketing.bean.vo.DistributionRecordVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author houshuai
 *
 * 分销记录信息
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema
public class EsDistributionRecordAddRequest extends BaseRequest {


    /**
     * 已新增的DistributionRecord信息
     */
    @Schema(description = "分销记录信息")
    private List<DistributionRecordVO> distributionRecordVOs;
}