package com.wanmi.sbc.customer.api.response.payingmemberrightsrel;

import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.customer.bean.vo.PayingMemberRightsRelVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>权益与付费会员等级关联表分页结果</p>
 * @author zhanghao
 * @date 2022-05-13 13:41:21
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PayingMemberRightsRelPageResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 权益与付费会员等级关联表分页结果
     */
    @Schema(description = "权益与付费会员等级关联表分页结果")
    private MicroServicePage<PayingMemberRightsRelVO> payingMemberRightsRelVOPage;
}
