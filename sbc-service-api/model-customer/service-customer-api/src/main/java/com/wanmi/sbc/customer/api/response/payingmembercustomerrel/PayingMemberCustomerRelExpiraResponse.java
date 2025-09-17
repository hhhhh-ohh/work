package com.wanmi.sbc.customer.api.response.payingmembercustomerrel;

import com.wanmi.sbc.customer.bean.vo.PayingMemberCustomerRelVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * <p>客户与付费会员等级关联表新增结果</p>
 * @author zhanghao
 * @date 2022-05-13 13:40:48
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PayingMemberCustomerRelExpiraResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 已新增的客户与付费会员等级关联表信息
     */
    @Schema(description = "过期会员")
    private List<PayingMemberCustomerRelVO> payingMemberCustomerRelVOList;
}
