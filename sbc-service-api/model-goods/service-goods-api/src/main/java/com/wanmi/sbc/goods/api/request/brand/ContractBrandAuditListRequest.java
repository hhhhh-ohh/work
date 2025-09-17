package com.wanmi.sbc.goods.api.request.brand;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 二次签约品牌查询请求结构
 * @author wangchao
 */
@Schema
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ContractBrandAuditListRequest extends BaseRequest {

    private static final long serialVersionUID = -868165319205322699L;

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
