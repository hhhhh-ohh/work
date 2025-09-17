package com.wanmi.sbc.goods.api.response.spec;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.goods.bean.vo.GoodsInfoSpecExportVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Schema
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GoodsInfoSpecForExportResponse extends BasicResponse {

    private static final long serialVersionUID = 3124298968836608291L;

    @Schema(description = "规格列表")
    private Map<String, List<GoodsInfoSpecExportVO>> goodsInfoSpecMap;

}
