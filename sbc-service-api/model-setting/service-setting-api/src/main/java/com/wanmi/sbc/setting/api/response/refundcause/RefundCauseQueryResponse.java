package com.wanmi.sbc.setting.api.response.refundcause;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.setting.bean.vo.RefundCauseVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @author houshuai
 * @date 2021/11/16 11:12
 * @description <p> 退款原因查询 </p>
 */
@Schema
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RefundCauseQueryResponse extends BasicResponse {

    private static final long serialVersionUID = 611640901281404974L;

    @Schema(description = "退款原因信息")
    List<RefundCauseVO> refundCauseVOList;

}
