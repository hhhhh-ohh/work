package com.wanmi.sbc.empower.api.response.deliveryrecord;

import com.wanmi.sbc.empower.bean.vo.DeliveryRecordDadaVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * <p>根据id查询任意（包含已删除）达达配送记录信息response</p>
 * @author dyt
 * @date 2019-07-30 14:08:26
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryRecordDadaByIdResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 达达配送记录信息
     */
    @Schema(description = "达达配送记录信息")
    private DeliveryRecordDadaVO deliveryRecordDadaVO;
}
