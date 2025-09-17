package com.wanmi.sbc.message.api.request.storemessagedetail;


import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * <p>商家消息/公告分页结果</p>
 * @author 马连峰
 * @date 2022-07-05 10:52:24
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StoreMessageDetailTopListRequest implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotNull
    @Schema(description = "关联的消息节点id或公告id列表")
    private List<Long> joinIdList;

    /**
     * 商家ID
     */
    @NotNull
    @Schema(description = "商家ID")
    private Long storeId;
}
