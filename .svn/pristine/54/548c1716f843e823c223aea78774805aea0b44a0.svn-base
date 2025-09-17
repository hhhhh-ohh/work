package com.wanmi.sbc.crm.api.response.caterelatedrecommend;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.crm.bean.vo.CateRelatedRecommendDetailVO;
import com.wanmi.sbc.crm.bean.vo.CateRelatedRecommendInfoVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @ClassName CateRelatedRecommendDetailListResponse
 * @Description 商品相关性推荐类目查看--逐条列表结果VO
 * @Author lvzhenwei
 * @Date 2020/11/26 16:05
 **/
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CateRelatedRecommendDetailListResponse extends BasicResponse {
    private static final long serialVersionUID = 1L;

    /**
     * 商品相关性推荐类目查看--逐条
     */
    @Schema(description = "商品相关性推荐类目查看--逐条列表结果")
    private MicroServicePage<CateRelatedRecommendDetailVO> cateRelatedRecommendDetailVOPage;
}
