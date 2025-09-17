package com.wanmi.sbc.goods.api.response.storetobeevaluate;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.goods.bean.vo.StoreTobeEvaluateVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>店铺服务待评价修改结果</p>
 * @author lzw
 * @date 2019-03-20 17:01:46
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StoreTobeEvaluateModifyResponse extends BasicResponse {
    private static final long serialVersionUID = 1L;

    /**
     * 已修改的店铺服务待评价信息
     */
    @Schema(description = "已修改的店铺服务待评价信息")
    private StoreTobeEvaluateVO storeTobeEvaluateVO;
}
