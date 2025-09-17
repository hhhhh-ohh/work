package com.wanmi.sbc.marketing.api.request.grouponcenter;

import com.wanmi.sbc.common.base.BaseQueryRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>H5-拼团活动首页列表查询请求参数</p>
 *
 * @author chenli
 * @date 2019-05-21 14:02:38
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GrouponCenterListRequest extends BaseQueryRequest {
    private static final long serialVersionUID = 7705983488453217344L;
    /**
     * 拼团分类ID
     */
    @Schema(description = "拼团分类ID")
    private String grouponCateId;

    /**
     * spu商品名称
     */
    @Schema(description = "spu商品名称")
    private String goodsName;

    /**
     * 是否精选
     */
    @Schema(description = "是否精选")
    private Boolean sticky = Boolean.FALSE;

    @Schema(description = "店铺Id")
    private Long storeId;

}