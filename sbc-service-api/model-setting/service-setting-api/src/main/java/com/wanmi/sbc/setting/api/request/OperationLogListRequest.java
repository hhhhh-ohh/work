package com.wanmi.sbc.setting.api.request;

import com.wanmi.sbc.common.base.BaseQueryRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 操作日志查询请求
 * Created by daiyitian on 2017/3/24.
 */
@Schema
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OperationLogListRequest extends BaseQueryRequest {

    private static final long serialVersionUID = 1L;

    @Schema(description = "token")
    private String token;

    /**
     * 员工编号
     */
    @Schema(description = "员工编号")
    private String employeeId;

    /**
     * 店铺Id
     */
    @Schema(description = "店铺Id")
    private Long storeId;

    /**
     * 公司Id
     */
    @Schema(description = "公司Id")
    private Long companyInfoId;

    /**
     * 商家编号
     */
    @Schema(description = "商家编号")
    private String thirdId;

    /**
     * 操作人账号
     */
    @Schema(description = "操作人账号")
    private String opAccount;

    /**
     * 操作人名称
     */
    @Schema(description = "操作人名称")
    private String opName;

    /**
     * 操作模块
     */
    @Schema(description = "操作模块")
    private String opModule;

    /**
     * 操作类型
     */
    @Schema(description = "操作类型")
    private String opCode;

    /**
     * 操作内容
     */
    @Schema(description = "操作内容")
    private String opContext;

    /**
     * 开始时间
     */
    @Schema(description = "开始时间")
    private String beginTime;

    /**
     * 结束时间
     */
    @Schema(description = "结束时间")
    private String endTime;

    @Schema(description = "批量操作日志id")
    private List<Long> idList;
}
