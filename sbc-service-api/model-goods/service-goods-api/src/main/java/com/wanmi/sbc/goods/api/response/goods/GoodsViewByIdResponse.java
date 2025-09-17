package com.wanmi.sbc.goods.api.response.goods;

import com.wanmi.sbc.goods.bean.vo.*;
import com.wanmi.sbc.setting.bean.vo.OperateDataLogVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * com.wanmi.sbc.goods.api.response.goods.GoodsByIdResponse
 * 根据编号查询商品视图信息响应对象
 *
 * @author lipeng
 * @dateTime 2018/11/5 上午9:39
 */
@Schema
@Data
public class GoodsViewByIdResponse extends GoodsViewByIdBaseResponse implements Serializable {

    private static final long serialVersionUID = -3888285875556463006L;

    /**
     * 商品SKU列表
     */
    @Schema(description = "商品SKU列表")
    private List<GoodsInfoVO> goodsInfos;
}
