package com.wanmi.sbc.goods.api.request.cate;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * com.wanmi.sbc.goods.api.request.goodscate.GoodsCateByIdRequest
 * 根据分类编号查询商品信息请求对象
 * @author lipeng
 * @dateTime 2018/11/1 下午4:41
 */
@Schema
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GoodsCateByIdRequest extends BaseRequest {

    private static final long serialVersionUID = 7044296733822460649L;

    @Schema(description = "分类Id")
    private Long cateId;
}
