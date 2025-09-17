package com.wanmi.sbc.goods.api.request.goodsaudit;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.goods.bean.vo.PriceAdjustmentRecordDetailVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

/**
 * @author 黄昭
 * @className EditLevelPriceRequest
 * @description TODO
 * @date 2022/2/17 18:06
 **/
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EditLevelPriceRequest extends BaseRequest {

    @Schema(description = "老商品Id")
    private String oldGoodsId;

    @Schema(description = "批量调价详情")
    private List<PriceAdjustmentRecordDetailVO> detailVOList;
}