package com.wanmi.sbc.customer.api.response.payingmembercustomerrel;

import com.wanmi.sbc.customer.bean.vo.PayingMemberCustomerRelPageVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;
import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>客户与付费会员等级关联表列表结果</p>
 * @author zhanghao
 * @date 2022-05-13 13:40:48
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PayingMemberCustomerRelExportResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 客户与付费会员等级关联表列表结果
     */
    @Schema(description = "客户与付费会员等级关联表列表结果")
    private List<PayingMemberCustomerRelPageVO> payingMemberCustomerRelList;
}
