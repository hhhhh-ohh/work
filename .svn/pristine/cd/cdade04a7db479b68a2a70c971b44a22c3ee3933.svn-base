package com.wanmi.sbc.elastic.api.request.goods;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.goods.bean.vo.GoodsBrandVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @author houshuai
 * 新增商品品牌入参
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EsGoodsBrandSaveRequest extends BaseRequest {

    private static final long serialVersionUID = 1L;

    /**
     * 商品品牌信息
     */
    @Schema(description = "商品品牌信息")
    List<GoodsBrandVO> goodsBrandVOList;
}