package com.wanmi.sbc.goods.api.response.thirdgoodscate;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.goods.bean.vo.ThirdGoodsCateVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>第三方平台类目分页结果</p>
 * @author 
 * @date 2020-08-29 13:35:42
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ThirdGoodsCatePageResponse extends BasicResponse {
    private static final long serialVersionUID = 1L;

    /**
     * 第三方平台类目分页结果
     */
    @Schema(description = "第三方平台类目分页结果")
    private MicroServicePage<ThirdGoodsCateVO> thirdGoodsCateVOPage;
}
