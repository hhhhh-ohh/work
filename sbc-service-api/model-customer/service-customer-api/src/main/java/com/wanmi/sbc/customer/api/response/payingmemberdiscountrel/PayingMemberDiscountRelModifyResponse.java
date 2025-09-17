package com.wanmi.sbc.customer.api.response.payingmemberdiscountrel;

import com.wanmi.sbc.customer.bean.vo.PayingMemberDiscountRelVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>折扣商品与付费会员等级关联表修改结果</p>
 * @author zhanghao
 * @date 2022-05-13 13:41:36
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PayingMemberDiscountRelModifyResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 已修改的折扣商品与付费会员等级关联表信息
     */
    @Schema(description = "已修改的折扣商品与付费会员等级关联表信息")
    private PayingMemberDiscountRelVO payingMemberDiscountRelVO;
}
