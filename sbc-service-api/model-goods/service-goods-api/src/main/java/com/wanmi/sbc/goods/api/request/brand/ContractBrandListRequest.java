package com.wanmi.sbc.goods.api.request.brand;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 签约品牌查询请求结构
 * Created by daiyitian on 2018/11/02.
 */
@Schema
@Data
public class ContractBrandListRequest extends BaseRequest {

    private static final long serialVersionUID = 9078779328338871539L;

    /**
     * 签约品牌分类
     */
    @Schema(description = "签约品牌分类")
    private Long contractBrandId;

    /**
     * 店铺主键
     */
    @Schema(description = "店铺主键")
    private Long storeId;

    /**
     * 平台品牌id
     */
    @Schema(description = "平台品牌id")
    private List<Long> goodsBrandIds;

    /**
     * 自定义品牌名称
     */
    @Schema(description = "自定义品牌名称")
    private String checkBrandName;

}
