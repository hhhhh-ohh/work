package com.wanmi.sbc.setting.bean.vo;

import com.wanmi.sbc.common.base.BasicResponse;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>自提员工关系VO</p>
 * @author xufeng
 * @date 2021-09-06 14:23:11
 */
@Schema
@Data
public class PickupEmployeeRelaVO extends BasicResponse {
    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @Schema(description = "主键id")
    private Long id;

    /**
     * 员工id
     */
    @Schema(description = "员工id")
    private String employeeId;

    /**
     * 员工名称
     */
    @Schema(description = "员工名称")
    private String employeeName;

    /**
     * 自提点id
     */
    @Schema(description = "自提点id")
    private Long pickupId;

}