package com.wanmi.sbc.goods.api.response.goodssharerecord;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.goods.bean.vo.GoodsShareRecordVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>商品分享分页结果</p>
 * @author zhangwenchang
 * @date 2020-03-06 13:46:24
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GoodsShareRecordPageResponse extends BasicResponse {
    private static final long serialVersionUID = 1L;

    /**
     * 商品分享分页结果
     */
    @Schema(description = "商品分享分页结果")
    private MicroServicePage<GoodsShareRecordVO> goodsShareRecordVOPage;
}
