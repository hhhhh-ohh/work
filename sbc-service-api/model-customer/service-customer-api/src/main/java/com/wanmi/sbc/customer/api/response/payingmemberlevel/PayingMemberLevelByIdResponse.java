package com.wanmi.sbc.customer.api.response.payingmemberlevel;

import com.wanmi.sbc.customer.bean.vo.PayingMemberLevelVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>根据id查询任意（包含已删除）付费会员等级表信息response</p>
 * @author zhanghao
 * @date 2022-05-13 11:42:42
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PayingMemberLevelByIdResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 付费会员等级表信息
     */
    @Schema(description = "付费会员等级表信息")
    private PayingMemberLevelVO payingMemberLevelVO;
}
