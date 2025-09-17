package com.wanmi.sbc.goods.api.request.storegoodstab;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.enums.DeleteFlag;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * Author: xiemengnan
 * Time: 2018/10/13.10:22
 */
@Schema
@Data
public class StoreGoodsTabModifyRequest extends BaseRequest {

    private static final long serialVersionUID = 6678308253559963050L;

    /**
     * 模板标识
     */
    @Schema(description = "模板标识")
    private Long tabId;

    /**
     * 批量模板标识
     */
    @Schema(description = "批量模板标识")
    private List<Long> tabIds;

    /**
     * 店铺标识
     */
    @Schema(description = "店铺标识")
    private Long storeId;

    /**
     * 模板名称
     */
    @Schema(description = "模板名称")
    private String tabName;

    /**
     * 删除标记
     */
    @Schema(description = "删除标记 0: 否, 1: 是")
    private DeleteFlag delFlag;

    /**
     * 创建人
     */
    @Schema(description = "创建人")
    private String createPerson;

    /**
     * 修改人
     */
    @Schema(description = "修改人")
    private String updatePerson;

}
