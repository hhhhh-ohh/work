package com.wanmi.sbc.customer.api.response.payingmembercustomerrel;

import com.wanmi.sbc.customer.bean.vo.PayingMemberCustomerRelVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>根据id查询任意（包含已删除）客户与付费会员等级关联表信息response</p>
 * @author zhanghao
 * @date 2022-05-13 13:40:48
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PayingMemberCustomerRelByIdResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 客户与付费会员等级关联表信息
     */
    @Schema(description = "客户与付费会员等级关联表信息")
    private PayingMemberCustomerRelVO payingMemberCustomerRelVO;
}
