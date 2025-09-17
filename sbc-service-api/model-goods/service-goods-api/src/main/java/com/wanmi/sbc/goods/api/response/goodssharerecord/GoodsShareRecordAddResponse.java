package com.wanmi.sbc.goods.api.response.goodssharerecord;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.goods.bean.vo.GoodsShareRecordVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>商品分享新增结果</p>
 * @author zhangwenchang
 * @date 2020-03-06 13:46:24
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GoodsShareRecordAddResponse extends BasicResponse {
    private static final long serialVersionUID = 1L;

    /**
     * 已新增的商品分享信息
     */
    @Schema(description = "已新增的商品分享信息")
    private GoodsShareRecordVO goodsShareRecordVO;
}
