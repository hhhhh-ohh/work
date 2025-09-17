package com.wanmi.sbc.goods.api.response.flashsalegoods;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.common.util.StringUtil;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author yang
 * @since 2019-07-23
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IsFlashSaleResponse extends BasicResponse {

    private static final long serialVersionUID = 5646212467171654802L;

    /**
     * 活动时间：13:00
     */
    @Schema(description = "活动时间：13:00")
    private String activityTime;

    /**
     * 商品编号
     */
    @Schema(description = "商品编号")
    private String goodsId;

    /**
     * 是否有未结束活动关联商品
     */
    @Schema(description = "是否有未结束活动关联商品")
    private Boolean isFlashSale;

    /**
     * 从数据库实体转换为返回前端的用户信息
     * (字段顺序不可变)
     */
    public IsFlashSaleResponse convertFromNativeSQLResult(Object result) {
        Object[] results = StringUtil.cast(result, Object[].class);
        if (results == null || results.length < 1) {
            return this;
        }

        this.setActivityTime(StringUtil.cast(results, 0, String.class));
        this.setGoodsId(StringUtil.cast(results, 1, String.class));
        return this;
    }
}
