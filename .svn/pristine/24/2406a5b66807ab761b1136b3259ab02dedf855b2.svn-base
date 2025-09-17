package com.wanmi.sbc.message.api.request.storemessagedetail;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.*;

import java.io.Serializable;

/**
 * <p>单个删除商家消息/公告请求参数</p>
 * @author 马连峰
 * @date 2022-07-05 10:52:24
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StoreMessageDetailDelByIdRequest implements Serializable {
private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @Schema(description = "主键id")
    @NotNull
    private String id;

    /**
     * 商家id
     */
    @Schema(description = "商家id", hidden = true)
    @NotNull
    private Long storeId;
}
