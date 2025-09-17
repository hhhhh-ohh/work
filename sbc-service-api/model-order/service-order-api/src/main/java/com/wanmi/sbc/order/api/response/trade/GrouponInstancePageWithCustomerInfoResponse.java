package com.wanmi.sbc.order.api.response.trade;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.order.bean.vo.GrouponInstanceWithCustomerInfoVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Description:
 * @Date: 2018-12-04 11:02
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema
public class GrouponInstancePageWithCustomerInfoResponse extends BasicResponse {


    private static final long serialVersionUID = 1L;
    /**
     * 分页数据
     */
    @Schema(description = "分页数据")
    private MicroServicePage<GrouponInstanceWithCustomerInfoVO> grouponInstanceVOS;

}
