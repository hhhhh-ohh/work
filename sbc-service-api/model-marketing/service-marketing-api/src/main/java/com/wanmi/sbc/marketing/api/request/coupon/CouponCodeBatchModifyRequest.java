package com.wanmi.sbc.marketing.api.request.coupon;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.marketing.bean.dto.CouponCodeBatchModifyDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotEmpty;

import java.io.Serializable;
import java.util.List;

/**
 * 批量更新券码使用状态请求结构
 * @Author: gaomuwei
 * @Date: Created In 下午7:47 2018/10/9
 * @Description:
 */
@Schema
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CouponCodeBatchModifyRequest extends BaseRequest {

    private static final long serialVersionUID = -8319082085466549881L;

    /**
     * 优惠券码批量修改 {@link CouponCodeBatchModifyDTO}
     */
    @Schema(description = "券码使用状态信息列表")
    @NotEmpty
    private List<CouponCodeBatchModifyDTO> modifyDTOList;

}
