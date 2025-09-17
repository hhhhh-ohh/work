package com.wanmi.sbc.account.api.request.finance.record;

import com.wanmi.sbc.account.bean.enums.AccountRecordType;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * <p>对账列表汇总请求参数</p>
 * Created by of628-wenzhi on 2018-10-13-上午11:48.
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountGatherListRequest extends AccountBaseRequest {

    private static final long serialVersionUID = -940234983511513229L;
    /**
     * 商家id
     */
    @Schema(description = "商家id")
    private Long supplierId;

    /**
     * 检索关键字
     */
    @Schema(description = "检索关键字")
    private String keywords;

    /**
     * 开始时间,非空，格式：yyyy-MM-dd HH:mm:ss
     */
    @Schema(description = "开始时间")
    @NotNull
    private String beginTime;

    /**
     * 结束时间，非空，格式：yyyy-MM-dd HH:mm:ss
     */
    @Schema(description = "结束时间")
    @NotNull
    private String endTime;

    /**
     * 需要查询的对账记录类型 {@link AccountRecordType}
     */
    @Schema(description = "需要查询的对账记录类型")
    private AccountRecordType accountRecordType;
}
