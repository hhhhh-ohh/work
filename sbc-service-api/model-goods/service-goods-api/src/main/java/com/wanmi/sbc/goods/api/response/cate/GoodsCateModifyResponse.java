package com.wanmi.sbc.goods.api.response.cate;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.goods.bean.vo.GoodsCateVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * com.wanmi.sbc.goods.api.response.goodscate.GoodsCateModifyResponse
 * 修改商品分类信息响应对象
 *
 * @author lipeng
 * @dateTime 2018/11/1 下午4:54
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GoodsCateModifyResponse extends BasicResponse {

    private static final long serialVersionUID = -8211967929852699130L;

    @Schema(description = "商品类目")
    private List<GoodsCateVO> goodsCateListVOList;
}
