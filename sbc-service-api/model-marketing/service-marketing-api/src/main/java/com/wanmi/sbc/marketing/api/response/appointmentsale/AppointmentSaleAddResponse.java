package com.wanmi.sbc.marketing.api.response.appointmentsale;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.goods.bean.vo.AppointmentSaleVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * <p>预约抢购新增结果</p>
 * @author zxd
 * @date 2020-05-21 10:32:23
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentSaleAddResponse extends BasicResponse {
    private static final long serialVersionUID = 1L;

    /**
     * 已新增的预约抢购信息
     */
    @Schema(description = "已新增的预约抢购信息")
    private AppointmentSaleVO appointmentSaleVO;
}
