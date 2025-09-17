package com.wanmi.sbc.crm.api.request.manualrecommendgoods;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.io.Serializable;
import java.util.List;

/**
 * @ClassName ManualRecommendGoodsAddListRequest
 * @Description TODO
 * @Author lvzhenwei
 * @Date 2020/11/23 16:40
 **/
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ManualRecommendGoodsAddListRequest extends BaseRequest {
    private static final long serialVersionUID = 1L;

    /**
     * 手动推荐商品List
     */
    @Schema(description = "手动推荐商品List")
    private List<ManualRecommendGoodsAddRequest> manualRecommendGoodsList;
}
