package com.wanmi.sbc.marketing.api.response.bookingsalegoods;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.marketing.bean.vo.BookingVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * <p>魔方预售</p>
 *
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookingResponse extends BasicResponse {
    private static final long serialVersionUID = 1L;

    /**
     * 预售商品信息
     */
    @Schema(description = "预售商品信息")
    private MicroServicePage<BookingVO> bookingVOMicroServicePage;
}
