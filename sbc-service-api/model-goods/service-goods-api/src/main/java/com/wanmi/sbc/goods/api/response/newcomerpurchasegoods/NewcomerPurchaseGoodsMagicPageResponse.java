package com.wanmi.sbc.goods.api.response.newcomerpurchasegoods;

import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.goods.bean.vo.NewcomerPurchaseGoodsMagicVO;
import com.wanmi.sbc.goods.bean.vo.NewcomerPurchaseGoodsVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author xuyunpeng
 * @className NewcomerPurchaseGoodsMagicPageResponse
 * @description
 * @date 2022/8/24 10:18 AM
 **/
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NewcomerPurchaseGoodsMagicPageResponse implements Serializable {
    private static final long serialVersionUID = 6796910388316456972L;

    /**
     * 分页商品数据
     */
    @Schema(description = "分页商品数据")
    private MicroServicePage<NewcomerPurchaseGoodsMagicVO> newcomerPurchaseGoodsVOS;
}
