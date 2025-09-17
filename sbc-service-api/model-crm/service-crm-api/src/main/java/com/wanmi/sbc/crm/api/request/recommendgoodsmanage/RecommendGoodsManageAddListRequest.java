package com.wanmi.sbc.crm.api.request.recommendgoodsmanage;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.io.Serializable;
import java.util.List;

/**
 * @ClassName RecommendGoodsManageAddListRequest
 * @description
 * @Author lvzhenwei
 * @Date 2020/11/23 15:32
 **/
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecommendGoodsManageAddListRequest extends BaseRequest {
    private static final long serialVersionUID = 1L;

    /**
     * 新增推荐商品列表
     */
    @Schema(description = "新增推荐商品列表")
    private List<RecommendGoodsManageAddRequest> recommendGoodsManageList;
}
