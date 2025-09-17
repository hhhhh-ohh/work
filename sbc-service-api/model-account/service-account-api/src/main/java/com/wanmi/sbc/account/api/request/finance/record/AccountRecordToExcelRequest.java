package com.wanmi.sbc.account.api.request.finance.record;

import com.wanmi.sbc.account.bean.enums.AccountRecordType;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>对账数据导出Excel 参数结构</p>
 * Created by chenli on 2018-12-21-下午14:39.
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
public class AccountRecordToExcelRequest extends BasePageRequest {

    private static final long serialVersionUID = 3819688659850453545L;
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
     * token
     */
    @Schema(description = "token")
    private String token;

    /**
     * 需要查询的对账记录类型 {@link AccountRecordType}
     */
    @Schema(description = "需要查询的对账记录类型")
    private AccountRecordType accountRecordType;

}
