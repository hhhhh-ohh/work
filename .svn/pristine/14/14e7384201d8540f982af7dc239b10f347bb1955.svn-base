package com.wanmi.sbc.customer.api.response.payingmemberstorerel;

import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.customer.bean.vo.PayingMemberStoreRelVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>商家与付费会员等级关联表分页结果</p>
 * @author zhanghao
 * @date 2022-05-13 13:41:04
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PayingMemberStoreRelPageResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 商家与付费会员等级关联表分页结果
     */
    @Schema(description = "商家与付费会员等级关联表分页结果")
    private MicroServicePage<PayingMemberStoreRelVO> payingMemberStoreRelVOPage;
}
