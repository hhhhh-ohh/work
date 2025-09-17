package com.wanmi.sbc.elastic.api.request.storeInformation;

import com.wanmi.sbc.common.base.EsInitRequest;

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
public class ESStoreInfoInitRequest extends EsInitRequest {

    /**
     * 批量店铺ID
     */
    @Schema(description = "批量店铺ID")
    private List<Long> idList;

}
