package com.wanmi.sbc.marketing.api.response.bookingsalegoods;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.goods.bean.vo.BookingSaleGoodsVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * <p>根据id查询任意（包含已删除）预售商品信息信息response</p>
 * @author dany
 * @date 2020-06-05 10:51:35
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookingSaleGoodsByIdResponse extends BasicResponse {
    private static final long serialVersionUID = 1L;

    /**
     * 预售商品信息信息
     */
    @Schema(description = "预售商品信息信息")
    private BookingSaleGoodsVO bookingSaleGoodsVO;
}
