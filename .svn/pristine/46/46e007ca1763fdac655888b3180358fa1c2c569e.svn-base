package com.wanmi.sbc.customer.api.response.livecompany;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.customer.api.response.company.CompanyReponse;
import com.wanmi.sbc.customer.bean.vo.LiveCompanyVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * <p>直播商家分页结果</p>
 * @author zwb
 * @date 2020-06-06 18:06:59
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LiveCompanyPagePackResponse extends BasicResponse {
    private static final long serialVersionUID = 1L;

    /**
     * 直播商家分页结果
     */
    @Schema(description = "直播商家分页结果")
    private MicroServicePage<CompanyReponse> liveCompanyVOPage;

}
