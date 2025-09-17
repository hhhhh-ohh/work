package com.wanmi.sbc.vas.api.response.recommend.recommendgoodsmanage;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.vas.bean.vo.recommend.RecommendGoodsManageInfoVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @ClassName RecommendGoodsManageInfoListResponse
 * @Description TODO
 * @Author lvzhenwei
 * @Date 2020/11/18 17:58
 **/
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecommendGoodsManageInfoListResponse extends BasicResponse {

    private static final long serialVersionUID = 1L;

    /**
     * 商品推荐管理列表结果
     */
    @Schema(description = "商品推荐管理列表结果")
    private MicroServicePage<RecommendGoodsManageInfoVO> recommendGoodsManageInfoVOPage;
}
