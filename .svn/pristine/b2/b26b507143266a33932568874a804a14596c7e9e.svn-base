package com.wanmi.sbc.elastic.api.request.operationlog;

import com.wanmi.sbc.common.base.BaseQueryRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @author houshuai
 *
 * 操作日志查询参数
 */
@Slf4j
@EqualsAndHashCode(callSuper = true)
@Schema
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EsOperationLogListRequest extends BaseQueryRequest {

    private static final long serialVersionUID = 2584160228306434248L;

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