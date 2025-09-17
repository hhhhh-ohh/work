package com.wanmi.sbc.empower.api.response.deliveryrecord;

import com.wanmi.sbc.empower.bean.vo.DadaCityVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * <p>达达配送城市列表结果</p>
 * @author dyt
 * @date 2019-07-30 14:08:26
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryRecordDadaCityListResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 达达配送城市列表结果
     */
    @Schema(description = "城市列表")
    private List<DadaCityVO> cityList;
}
