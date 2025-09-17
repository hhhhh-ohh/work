package com.wanmi.sbc.goods.api.response.goodsaudit;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.goods.bean.vo.GoodsAuditVO;
import com.wanmi.sbc.goods.bean.vo.GoodsBrandVO;
import com.wanmi.sbc.goods.bean.vo.GoodsCateVO;
import com.wanmi.sbc.goods.bean.vo.GoodsInfoVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>商品审核分页结果</p>
 * @author 黄昭
 * @date 2021-12-16 18:10:20
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GoodsAuditPageResponse extends BasicResponse {
    private static final long serialVersionUID = 1L;

    /**
     * 商品审核分页结果
     */
    @Schema(description = "商品审核分页结果")
    private MicroServicePage<GoodsAuditVO> goodsAuditVOPage;
}
