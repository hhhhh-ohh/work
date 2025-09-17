package com.wanmi.sbc.order.bean.vo;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.setting.bean.enums.WriteOffStatus;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Date;

/**
 * @className WriteOffInfoVO
 * @description TODO
 * @author 黄昭
 * @date 2021/9/10 10:54
 **/
@Data
@Schema
public class WriteOffInfoVO extends BasicResponse {

    @Schema(description = "核销码")
    private String writeOffCode;

    @Schema(description = "核销人")
    private String writeOffPerson;

    @Schema(description = "核销时间")
    private Date writeOffTime;

    @Schema(description = "核销状态")
    private WriteOffStatus writeOffStatus;
}
