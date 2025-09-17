package com.wanmi.sbc.order.api.response.groupon;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.order.bean.vo.GrouponDetailVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * <p>团明细</p>
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GrouponDetailQueryResponse extends BasicResponse {
    private static final long serialVersionUID = 1L;

    /***
     * 团明细
     */
    private GrouponDetailVO grouponDetail;



}
