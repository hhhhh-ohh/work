package com.wanmi.sbc.marketing.api.response.bookingsale;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.goods.bean.vo.BookingSaleVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>预售信息修改结果</p>
 * @author dany
 * @date 2020-06-05 10:47:21
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookingSaleModifyResponse extends BasicResponse {
    private static final long serialVersionUID = 1L;

    /**
     * 已修改的预售信息信息
     */
    @Schema(description = "已修改的预售信息信息")
    private BookingSaleVO bookingSaleVO;
}
