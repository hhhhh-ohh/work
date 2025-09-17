package com.wanmi.ares.request.paymember;

import com.wanmi.ares.base.BaseRequest;
import com.wanmi.ares.enums.QueryDateCycle;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author xuyunpeng
 * @className PayMemberQueryRequest
 * @description
 * @date 2022/5/25 2:14 PM
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema
public class PayMemberQueryRequest extends BaseRequest {

    private static final long serialVersionUID = -5554343433221034161L;

    /**
     * 按日期周期查询，如果按自然月查询，此项可为空
     * @see com.wanmi.ares.enums.QueryDateCycle
     */
    @Schema(description = "按日期周期查询 0、今天 1、昨天 2、近7天 3、近30天")
    private QueryDateCycle dateCycle;

    /**
     * 按自然月查询时，传入年和月，格式："yyyyMM",若按日期周期统计，此项可为空
     */
    private String month;

}
