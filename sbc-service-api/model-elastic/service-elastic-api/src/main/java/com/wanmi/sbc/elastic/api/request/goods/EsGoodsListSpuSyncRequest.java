package com.wanmi.sbc.elastic.api.request.goods;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.elastic.bean.dto.goods.EsGoodsSkuSyncDTO;
import com.wanmi.sbc.elastic.bean.dto.goods.EsGoodsSpuSyncDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

/**
 * @author lvzhenwei
 * @className EsGoodsListSpuStockSubRequest
 * @description TODO
 * @date 2021/11/4 10:42 上午
 **/
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema
public class EsGoodsListSpuSyncRequest extends BaseRequest {

    @Schema(description = "同步spu信息")
    List<EsGoodsSpuSyncDTO> syncSpuList;

    @Schema(description = "同步sku信息")
    List<EsGoodsSkuSyncDTO> syncSkuList;
}
