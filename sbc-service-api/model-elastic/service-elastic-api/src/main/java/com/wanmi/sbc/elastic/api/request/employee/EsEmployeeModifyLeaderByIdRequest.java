package com.wanmi.sbc.elastic.api.request.employee;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author yangzhen
 * @Description 是否为主管
 * @Date 15:05 2021/3/26
 **/
@Schema
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EsEmployeeModifyLeaderByIdRequest extends BaseRequest {

    /**
     * 是否主管，0：否，1：是
     */
    @Schema(description = "是否主管，0：否，1：是")
    private Integer isLeader;


    /**
     * 主管ID
     */
    @Schema(description = "主管ID")
    private String oldEmployeeId;

    /**
     * 新主管ID
     */
    @Schema(description = "新主管ID")
    @NotBlank
    private String newEmployeeId;

    /**
     * 部门ID
     */
    @Schema(description = "部门ID")
    @NotBlank
    private String departmentId;


}
