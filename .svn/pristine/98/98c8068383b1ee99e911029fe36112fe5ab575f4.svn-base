package com.wanmi.sbc.account.api.request.offline;

import com.wanmi.sbc.account.api.request.AccountBaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.*;

import java.util.List;

/**
 * 线下账户获取请求
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OfflineAccountGetByIdsRequest extends AccountBaseRequest {

    private static final long serialVersionUID = -8436020970346987289L;
    /**
     * 线下账户Id
     */
    @Schema(description = "线下账户Id")
    @NotNull
    private List<Long> offlineAccountIds;
}
