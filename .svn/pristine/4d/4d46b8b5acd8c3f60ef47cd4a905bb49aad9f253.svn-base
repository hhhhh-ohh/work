package com.wanmi.sbc.marketing.api.response.communitydeliveryorder;

import com.wanmi.sbc.marketing.bean.vo.CommunityDeliveryOrderVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>根据id查询任意（包含已删除）社区团购发货单信息response</p>
 * @author dyt
 * @date 2023-08-03 16:23:20
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommunityDeliveryOrderByIdResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 社区团购发货单信息
     */
    @Schema(description = "社区团购发货单信息")
    private CommunityDeliveryOrderVO communityDeliveryOrderVO;
}
