package com.wanmi.sbc.goods.api.request.cate;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * com.wanmi.sbc.goods.api.request.goodscate.GoodsCateExistsChildByIdRequest
 * 根据编号查询当前分类下面是否存在子分类请求对象
 * @author lipeng
 * @dateTime 2018/11/1 下午5:16
 */
@Schema
@Data
public class GoodsCateExistsChildByIdRequest extends BaseRequest {

    private static final long serialVersionUID = 5909637880644302492L;

    @Schema(description = "分类Id")
    private Long cateId;
}
