package com.wanmi.sbc.goods.api.response.customergoodsevaluatepraise;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.goods.bean.vo.CustomerGoodsEvaluatePraiseVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * <p>会员商品评价点赞关联表分页结果</p>
 *
 * @author lvzhenwei
 * @date 2019-05-07 14:25:25
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerGoodsEvaluatePraisePageResponse extends BasicResponse {
    private static final long serialVersionUID = 1L;

    /**
     * 会员商品评价点赞关联表分页结果
     */
    @Schema(description = "会员商品评价点赞关联表分页结果")
    private MicroServicePage<CustomerGoodsEvaluatePraiseVO> customerGoodsEvaluatePraiseVOPage;
}
