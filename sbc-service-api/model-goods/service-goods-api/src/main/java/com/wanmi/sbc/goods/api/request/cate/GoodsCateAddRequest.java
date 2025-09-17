package com.wanmi.sbc.goods.api.request.cate;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.goods.bean.vo.GoodsCateVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * com.wanmi.sbc.goods.api.request.goodscate.GoodsCateAddRequest
 * 添加商品分类请求对象
 * @author lipeng
 * @dateTime 2018/11/1 下午4:50
 */
@Schema
@Data
public class GoodsCateAddRequest extends BaseRequest {

    private static final long serialVersionUID = -540115820977825902L;

    @Schema(description = "商品分类")
    private GoodsCateVO goodsCate;
}
