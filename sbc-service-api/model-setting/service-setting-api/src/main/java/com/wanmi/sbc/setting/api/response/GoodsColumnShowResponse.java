package com.wanmi.sbc.setting.api.response;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.setting.bean.vo.GoodsColumnShowVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @className GoodsColumnShowResponse
 * @description TODO
 * @author 黄昭
 * @date 2021/9/16 10:24
 **/
@Data
@Schema
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GoodsColumnShowResponse extends BasicResponse {
    private static final long serialVersionUID = -8951484535517952741L;

    @Schema(description = "商品展示字段状态")
    private List<GoodsColumnShowVO> goodsColumnShowVOList;
}
