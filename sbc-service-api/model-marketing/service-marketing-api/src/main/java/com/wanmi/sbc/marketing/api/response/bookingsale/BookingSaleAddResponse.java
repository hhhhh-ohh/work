package com.wanmi.sbc.marketing.api.response.bookingsale;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.goods.bean.vo.BookingSaleVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * <p>预售信息新增结果</p>
 * @author dany
 * @date 2020-06-05 10:47:21
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookingSaleAddResponse extends BasicResponse {
    private static final long serialVersionUID = 1L;

    /**
     * 已新增的预售信息信息
     */
    @Schema(description = "已新增的预售信息信息")
    private BookingSaleVO bookingSaleVO;
}
