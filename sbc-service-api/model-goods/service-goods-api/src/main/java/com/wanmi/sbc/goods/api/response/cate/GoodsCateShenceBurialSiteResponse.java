package com.wanmi.sbc.goods.api.response.cate;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.goods.bean.vo.GoodsCateShenceBurialSiteVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * <p>获取一二三级分类列表</p>
 * author: weiwenhao
 * Date: 2020-06-02
 */
@Schema
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GoodsCateShenceBurialSiteResponse extends BasicResponse {

    private static final long serialVersionUID = -6783901036827949522L;

    /**
     * 商品类目
     */
    @Schema(description = "商品类目")
    private List<GoodsCateShenceBurialSiteVO> goodsCateList;
}
