package com.wanmi.sbc.goods.api.response.thirdgoodscate;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.goods.bean.vo.ThirdGoodsCateVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>根据id查询任意（包含已删除）第三方平台类目信息response</p>
 * @author 
 * @date 2020-08-29 13:35:42
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ThirdGoodsCateByIdResponse extends BasicResponse {
    private static final long serialVersionUID = 1L;

    /**
     * 第三方平台类目信息
     */
    @Schema(description = "第三方平台类目信息")
    private ThirdGoodsCateVO thirdGoodsCateVO;
}
