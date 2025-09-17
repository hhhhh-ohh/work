package com.wanmi.sbc.goods.api.request.goods;

import com.wanmi.sbc.common.enums.OperateType;import com.wanmi.sbc.goods.api.request.common.OperationLogAddRequest;import com.wanmi.sbc.goods.bean.dto.BatchGoodsStockDTO;
import io.swagger.v3.oas.annotations.media.Schema;import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;import java.util.List;
/**
 * @type BatchGoodsStockRequest.java
 * @desc
 * @author zhanggaolei
 * @date 2023/3/15 16:24
 * @version
 */
@Schema
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BatchGoodsStockRequest {
    private List<BatchGoodsStockDTO> batchGoodsStockDTOS;

    /**
     * 登录信息
     */
    @Schema(description = "登录信息")
    private OperationLogAddRequest operationLogAddRequest;

}
