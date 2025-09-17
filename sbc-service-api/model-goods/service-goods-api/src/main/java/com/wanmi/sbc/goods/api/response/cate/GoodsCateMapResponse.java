package com.wanmi.sbc.goods.api.response.cate;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.goods.bean.vo.GoodsCateVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Map;

/***
 * 商品分类Map返回
 * @className GoodsCateMapResponse
 * @author zhengyang
 * @date 2021/8/4 19:39
 **/
@Schema
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GoodsCateMapResponse extends BasicResponse {

    @Schema(description = "商品类目")
    private Map<Long,GoodsCateVO> goodsCateMap;
}
