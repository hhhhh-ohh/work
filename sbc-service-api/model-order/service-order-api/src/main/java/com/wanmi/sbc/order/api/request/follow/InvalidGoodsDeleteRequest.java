package com.wanmi.sbc.order.api.request.follow;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.order.api.request.follow.validGroups.FollowAdd;
import com.wanmi.sbc.order.api.request.follow.validGroups.FollowDelete;
import com.wanmi.sbc.order.api.request.follow.validGroups.FollowFilter;
import com.wanmi.sbc.order.bean.enums.FollowFlag;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.hibernate.validator.constraints.NotEmpty;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema
public class InvalidGoodsDeleteRequest extends BaseRequest {

    /**
     * 编号
     */
    @Schema(description = "编号")
    private List<Long> followIds;

    /**
     * SKU编号
     */
    @Schema(description = "SKU编号")
    @NotBlank(groups = { FollowAdd.class})
    private String goodsInfoId;

    /**
     * SKU编号
     */
    @Schema(description = "SKU编号")
    @NotEmpty(groups = {FollowDelete.class, FollowFilter.class})
    private List<String> goodsInfoIds;

    /**
     * 会员编号
     */
    @Schema(description = "会员id")
    private String customerId;

    /**
     * 购买数量
     */
    @Schema(description = "购买数量")
    private Long goodsNum;

    /**
     * 收藏标识
     */
    @Schema(description = "收藏标识")
    private FollowFlag followFlag;
}
