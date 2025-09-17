package com.wanmi.sbc.goods.api.response.goodsaudit;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.goods.bean.vo.GoodsAuditVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;
import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>商品审核列表结果</p>
 * @author 黄昭
 * @date 2021-12-16 18:10:20
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GoodsAuditListResponse extends BasicResponse {
    private static final long serialVersionUID = 1L;

    /**
     * 商品审核列表结果
     */
    @Schema(description = "商品审核列表结果")
    private List<GoodsAuditVO> goodsAuditVOList;
}
