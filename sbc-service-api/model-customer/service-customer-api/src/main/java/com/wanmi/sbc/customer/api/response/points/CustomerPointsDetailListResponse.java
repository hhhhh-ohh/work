package com.wanmi.sbc.customer.api.response.points;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.customer.bean.vo.CustomerPointsDetailVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * <p>会员积分明细列表结果</p>
 * @author minchen
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerPointsDetailListResponse extends BasicResponse {
    private static final long serialVersionUID = 1L;

    /**
     * 会员积分明细列表结果
     */
    @Schema(description = "会员积分明细列表结果")
    private List<CustomerPointsDetailVO> customerPointsDetailVOList;
}
