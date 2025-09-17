package com.wanmi.sbc.setting.api.response.platformaddress;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.setting.bean.vo.PlatformAddressVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * <p>平台地址信息分页结果</p>
 * @author dyt
 * @date 2020-03-30 14:39:57
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PlatformAddressPageResponse extends BasicResponse {
    private static final long serialVersionUID = 1L;

    /**
     * 平台地址信息分页结果
     */
    @Schema(description = "平台地址信息分页结果")
    private MicroServicePage<PlatformAddressVO> platformAddressVOPage;
}
