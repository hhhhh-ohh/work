package com.wanmi.sbc.goods.api.request.storegoodstab;

import com.wanmi.sbc.common.base.BaseRequest;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author: gaomuwei
 * @Date: Created In 下午2:01 2018/12/13
 * @Description:
 */
@Schema
@Data
public class StoreGoodsTabDeleteRequest extends BaseRequest {

    private static final long serialVersionUID = -3983814990059217629L;

    /**
     * 模板标识
     */
    @Schema(description = "模板标识")
    private Long tabId;

    /**
     * 店铺标识
     */
    @Schema(description = "店铺标识")
    private Long storeId;

}
