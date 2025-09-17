package com.wanmi.sbc.empower.api.response.deliveryrecord;

import com.wanmi.sbc.empower.bean.vo.DadaReasonVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * <p>达达配送记录取消理由列表结果</p>
 * @author dyt
 * @date 2019-07-30 14:08:26
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryRecordDadaReasonListResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 达达配送记录列表结果
     */
    @Schema(description = "取消理由列表")
    private List<DadaReasonVO> reasonList;
}
