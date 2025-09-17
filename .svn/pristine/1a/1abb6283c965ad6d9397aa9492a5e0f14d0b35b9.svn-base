package com.wanmi.sbc.goods.api.response.cate;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.goods.bean.vo.GoodsCateVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * com.wanmi.sbc.goods.api.response.goodscate.GoodsCateByIdsResponse
 * 根据分类编号批量查询商品分类信息响应对象
 *
 * @author lipeng
 * @dateTime 2018/11/1 下午4:44
 */
@Schema
@Data
public class GoodsCateByIdsResponse extends BasicResponse {

    private static final long serialVersionUID = 4081510740754399207L;

    @Schema(description = "商品分类")
    private List<GoodsCateVO> goodsCateVOList;
}
