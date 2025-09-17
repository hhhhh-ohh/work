package com.wanmi.sbc.goods.api.response.goods;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.goods.bean.enums.CheckStatus;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * com.wanmi.sbc.goods.api.response.goods.GoodsAddAllResponse
 * 新增商品基本信息、基价响应对象
 *
 * @author lipeng
 * @dateTime 2018/11/5 上午10:39
 */
@Schema
@Data
public class GoodsAddAllResponse extends BasicResponse {

    private static final long serialVersionUID = -7640414060932951335L;

    @Schema(description = "新增商品响应对象")
    private String result;

    @Schema(description = "商品审核状态")
    private CheckStatus auditStatus;
}
