package com.wanmi.sbc.customer.api.response.payingmemberrecommendrel;

import com.wanmi.sbc.customer.bean.vo.PayingMemberRecommendRelVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>根据id查询任意（包含已删除）推荐商品与付费会员等级关联表信息response</p>
 * @author zhanghao
 * @date 2022-05-13 13:41:51
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PayingMemberRecommendRelByIdResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 推荐商品与付费会员等级关联表信息
     */
    @Schema(description = "推荐商品与付费会员等级关联表信息")
    private PayingMemberRecommendRelVO payingMemberRecommendRelVO;
}
