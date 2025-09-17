package com.wanmi.sbc.account.api.request.finance.record;

import com.wanmi.sbc.account.bean.enums.AccountRecordType;
import com.wanmi.sbc.common.enums.StoreType;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>对账查询参数结构</p>
 * Created by of628-wenzhi on 2017-12-07-下午7:39.
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
public class AccountRecordPageRequest extends BasePageRequest {

    private static final long serialVersionUID = 8920985231995473483L;
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

    @Schema(description = "店铺类型")
    private StoreType storeType;

}
