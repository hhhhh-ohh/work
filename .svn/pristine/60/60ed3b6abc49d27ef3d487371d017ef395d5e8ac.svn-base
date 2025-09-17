package com.wanmi.sbc.customer.api.response.livecompany;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.customer.bean.vo.LiveCompanyVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>根据id查询任意（包含已删除）直播商家信息response</p>
 * @author zwb
 * @date 2020-06-06 18:06:59
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LiveCompanyByIdResponse extends BasicResponse {
    private static final long serialVersionUID = 1L;

    /**
     * 直播商家信息
     */
    @Schema(description = "直播商家信息")
    private LiveCompanyVO liveCompanyVO;
}
