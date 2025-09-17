package com.wanmi.sbc.goods.api.request.cate;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * com.wanmi.sbc.goods.api.request.goodscate.GoodsCateByIdsRequest
 * 根据分类编号批量查询商品分类信息请求对象
 * @author lipeng
 * @dateTime 2018/11/1 下午4:46
 */
@Schema
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GoodsCateByIdsRequest extends BaseRequest {

    @Schema(description = "分类Id集合")
    private List<Long> cateIds;
}
