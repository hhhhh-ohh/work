package com.wanmi.sbc.goods.api.response.cate;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.goods.bean.vo.GoodsCateVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * com.wanmi.sbc.goods.api.response.goodscate.GoodsCateAddResponse
 * 新增商品分类信息响应对象
 * @author lipeng
 * @dateTime 2018/11/1 下午4:54
 */
@Schema
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GoodsCateAddResponse extends BasicResponse {

    private static final long serialVersionUID = 397728909323582544L;

    @Schema(description = "签约分类")
    private GoodsCateVO goodsCate;
}
