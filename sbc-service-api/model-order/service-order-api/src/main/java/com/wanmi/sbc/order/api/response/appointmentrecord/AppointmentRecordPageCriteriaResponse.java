package com.wanmi.sbc.order.api.response.appointmentrecord;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.marketing.bean.vo.AppointmentRecordVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema
public class AppointmentRecordPageCriteriaResponse extends BasicResponse {

    private static final long serialVersionUID = 1L;
    /**
     * 分页数据
     */
    @Schema(description = "分页数据")
    private MicroServicePage<AppointmentRecordVO> appointmentRecordPage;

}
