package com.wanmi.sbc.customer.api.response.payingmemberprice;

import com.wanmi.sbc.customer.bean.vo.PayingMemberPriceVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>根据id查询任意（包含已删除）付费设置表信息response</p>
 * @author zhanghao
 * @date 2022-05-13 13:40:30
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PayingMemberPriceByIdResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 付费设置表信息
     */
    @Schema(description = "付费设置表信息")
    private PayingMemberPriceVO payingMemberPriceVO;
}
