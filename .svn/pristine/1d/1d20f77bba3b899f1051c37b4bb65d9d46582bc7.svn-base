package com.wanmi.sbc.elastic.api.request.storeInformation;

import com.wanmi.sbc.common.base.BaseQueryRequest;
import com.wanmi.sbc.common.enums.StoreType;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author yangzhen
 * @Description // es店铺信息
 * @Date 18:30 2020/12/17
 * @Param
 * @return
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema
public class StoreInfoQueryPageRequest extends BaseQueryRequest {

    /**
     * 店铺ID
     */
    @Schema(description = "店铺ID")
    private Long storeId;

    /**
     * 店铺名称关键字
     */
    @Schema(description = "店铺名称")
    private String storeName;

    /**
     * 店铺类型
     */
    @Schema(description = "店铺类型")
    private StoreType storeType;

    /**
     * 批量店铺ID
     */
    @Schema(description = "批量店铺ID")
    private List<Long> idList;
}
