package com.wanmi.sbc.goods.api.request.goods;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.goods.bean.enums.CheckStatus;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * com.wanmi.sbc.goods.api.request.goods.GoodsCheckRequest
 * 商品审核请求对象
 * @author lipeng
 * @dateTime 2018/11/5 上午11:18
 */
@Schema
@Data
public class GoodsCheckRequest extends BaseRequest {

    private static final long serialVersionUID = -7826095117130452824L;

    /**
     * SpuId
     */
    @Schema(description = "SpuId")
    private List<String> goodsIds;

    /**
     * 审核状态
     */
    @Schema(description = "审核状态，0：待审核 1：已审核 2：审核失败 3：禁售中")
    private CheckStatus auditStatus;

    /**
     * 审核人名称
     */
    @Schema(description = "审核人名称")
    private String checker;

    /**
     * 审核驳回原因
     */
    @Schema(description = "审核驳回原因")
    private String auditReason;

    /**
     * 是否处理商品库商品
     */
    @Schema(description = "是否处理商品库商品")
    private Boolean dealStandardGoodsFlag = Boolean.FALSE;
}
