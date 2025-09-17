package com.wanmi.sbc.marketing.api.response.appointmentsale;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.goods.bean.vo.AppointmentSaleVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * <p>预约抢购列表结果</p>
 * @author zxd
 * @date 2020-05-21 10:32:23
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentSaleListResponse extends BasicResponse {
    private static final long serialVersionUID = 1L;

    /**
     * 预约抢购列表结果
     */
    @Schema(description = "预约抢购列表结果")
    private List<AppointmentSaleVO> appointmentSaleVOList;
}
