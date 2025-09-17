package com.wanmi.sbc.goods.bean.vo;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.common.enums.BoolFlag;
import com.wanmi.sbc.common.enums.DeleteFlag;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 商品库关联VO
 *
 * @auther dyt
 * @create 2018/03/20 10:04
 */
@Schema
@Data
public class StandardGoodsRelVO extends BasicResponse {

    /**
     * 编号
     */
    @Schema(description = "编号")
    private Long relId;

    /**
     * SPU标识
     */
    @Schema(description = "SPU标识")
    private String goodsId;

    /**
     *商品库SPU编号
     */
    @Schema(description = "商品库SPU编号")
    private String standardId;

    /**
     *店铺id
     */
    @Schema(description = "店铺id")
    private Long storeId;

    /**
     * 删除标记  二次导入可能用到
     */
    @Schema(description = "删除标记")
    private DeleteFlag delFlag;

    /**
     * 是否需要同步 0：不需要同步 1：需要同步
     */
    @Schema(description = "是否需要同步 0：不需要同步 1：需要同步")
    private BoolFlag needSynchronize;

}
