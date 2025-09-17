package com.wanmi.sbc.customer.api.request.store;

import com.wanmi.sbc.customer.api.request.CustomerBaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.*;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: songhanlin
 * @Date: Created In 下午1:56 2017/11/2
 * @Description: 设置结算日期Request
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
public class AccountDateModifyRequest extends CustomerBaseRequest {

    private static final long serialVersionUID = 1L;

    /**
     * 店铺Id
     */
    @Schema(description = "店铺Id")
    @NotNull
    private Long storeId;

    /**
     * 结算日期
     */
    @Schema(description = "结算日期")
    @Size(min = 1, max = 5)
    @NotEmpty
    private List<@NotNull @Min(1) @Max(31) Long> days = new ArrayList<>();

    /**
     * 结算日期字符串
     */
    @Schema(description = "结算日期字符串")
    private String accountDay;

}
