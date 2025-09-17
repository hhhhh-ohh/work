package com.wanmi.sbc.marketing.api.response.bookingsalegoods;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.goods.bean.vo.BookingSaleGoodsVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * <p>魔方H5预售商品response</p>
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookingGoodsResponse extends BasicResponse {
    private static final long serialVersionUID = 1L;

    /**
     * 预售商品信息
     */
    @Schema(description = "预售商品信息")
    private MicroServicePage<BookingSaleGoodsVO> page;
}
