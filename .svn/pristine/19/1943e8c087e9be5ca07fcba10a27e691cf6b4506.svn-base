package com.wanmi.sbc.marketing.api.response.bookingsale;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.goods.bean.vo.BookingSaleVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * <p>预售信息列表结果</p>
 * @author dany
 * @date 2020-06-05 10:47:21
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookingSaleListResponse extends BasicResponse {
    private static final long serialVersionUID = 1L;

    /**
     * 预售信息列表结果
     */
    @Schema(description = "预售信息列表结果")
    private List<BookingSaleVO> bookingSaleVOList;
}
