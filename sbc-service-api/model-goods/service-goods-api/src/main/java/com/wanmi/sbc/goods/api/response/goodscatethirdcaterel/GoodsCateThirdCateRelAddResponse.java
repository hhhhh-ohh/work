package com.wanmi.sbc.goods.api.response.goodscatethirdcaterel;

import com.wanmi.sbc.common.base.BasicResponse;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * <p>平台类目和第三方平台类目映射新增结果</p>
 * @author 
 * @date 2020-08-18 19:51:55
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GoodsCateThirdCateRelAddResponse extends BasicResponse {

    private List<String> updateEsGoodsIds;
    private List<String> delEsGoodsIds;
    private List<String> updateEsStandardGoodsIds;
}
