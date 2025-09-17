package com.wanmi.sbc.crm.api.response.manualrecommendgoods;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.crm.bean.vo.ManualRecommendGoodsInfoVO;
import com.wanmi.sbc.crm.bean.vo.ManualRecommendGoodsVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @ClassName ManualRecommendGoodsInfoListResponse
 * @Description TODO
 * @Author lvzhenwei
 * @Date 2020/11/23 11:38
 **/
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ManualRecommendGoodsInfoListResponse extends BasicResponse {

    /**
     * 手动推荐商品管理分页结果
     */
    @Schema(description = "手动推荐商品管理分页结果")
    private MicroServicePage<ManualRecommendGoodsInfoVO> manualRecommendGoodsInfoVOPage;
}
