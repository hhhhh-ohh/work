package com.wanmi.sbc.setting.api.request.refundcause;

import com.wanmi.sbc.common.base.BaseRequest;
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
 * @description <p> 退款原因编辑 </p>
 */
@Schema
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RefundCauseModifyRequest extends BaseRequest {

    private static final long serialVersionUID = -9216460799089747965L;

    @Schema(description = "退款原因信息")
    private List<RefundCauseVO> refundCauseVOList;

}
