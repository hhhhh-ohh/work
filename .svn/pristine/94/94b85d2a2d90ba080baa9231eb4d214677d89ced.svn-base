package com.wanmi.sbc.marketing.api.response.communitystockorder;

import com.wanmi.sbc.marketing.bean.vo.CommunityStockOrderVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>根据id查询任意（包含已删除）社区团购备货单信息response</p>
 * @author dyt
 * @date 2023-08-03 14:05:20
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommunityStockOrderByIdResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 社区团购备货单信息
     */
    @Schema(description = "社区团购备货单信息")
    private CommunityStockOrderVO communityStockOrderVO;
}
