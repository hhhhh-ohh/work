package com.wanmi.sbc.customer.api.response.payingmemberrecommendrel;

import com.wanmi.sbc.customer.bean.vo.PayingMemberRecommendRelVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>推荐商品与付费会员等级关联表新增结果</p>
 * @author zhanghao
 * @date 2022-05-13 13:41:51
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PayingMemberRecommendRelAddResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 已新增的推荐商品与付费会员等级关联表信息
     */
    @Schema(description = "已新增的推荐商品与付费会员等级关联表信息")
    private PayingMemberRecommendRelVO payingMemberRecommendRelVO;
}
