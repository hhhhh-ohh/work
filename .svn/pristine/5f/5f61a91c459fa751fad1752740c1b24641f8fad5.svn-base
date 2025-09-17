package com.wanmi.sbc.marketing.api.response.grouponactivity;

import com.wanmi.sbc.common.base.BasicResponse;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * <p>根据id查询任意（包含已删除）拼团活动信息表信息response</p>
 *
 * @author groupon
 * @date 2019-05-15 14:02:38
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GrouponActivityFreeDeliveryByIdResponse extends BasicResponse {

    private static final long serialVersionUID = -4564503849568007761L;

    /**
     * 是否包邮
     */
    @Schema(description = "是否包邮")
    private boolean freeDelivery;
}
