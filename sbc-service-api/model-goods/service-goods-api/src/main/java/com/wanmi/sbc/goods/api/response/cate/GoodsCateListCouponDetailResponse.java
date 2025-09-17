package com.wanmi.sbc.goods.api.response.cate;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.goods.bean.vo.CouponInfoForScopeNamesVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * com.wanmi.sbc.goods.api.response.goodscate.GoodsCateListResponse
 * 根据条件查询商品分类列表信息响应对象
 *
 * @author lipeng
 * @dateTime 2018/11/1 下午3:26
 */
@Schema
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GoodsCateListCouponDetailResponse extends BasicResponse {

    private static final long serialVersionUID = 3114126860750743622L;

    @Schema(description = "商品类目")
    private List<CouponInfoForScopeNamesVO> voList;
}
