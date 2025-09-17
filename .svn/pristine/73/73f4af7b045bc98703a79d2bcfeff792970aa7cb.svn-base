package com.wanmi.sbc.setting.api.response.platformaddress;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.setting.bean.vo.PlatformAddressVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * <p>根据id查询任意（包含已删除）平台地址信息信息response</p>
 * @author dyt
 * @date 2020-03-30 14:39:57
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PlatformAddressByIdResponse extends BasicResponse {
    private static final long serialVersionUID = 1L;

    /**
     * 平台地址信息信息
     */
    @Schema(description = "平台地址信息信息")
    private PlatformAddressVO platformAddressVO;
}
