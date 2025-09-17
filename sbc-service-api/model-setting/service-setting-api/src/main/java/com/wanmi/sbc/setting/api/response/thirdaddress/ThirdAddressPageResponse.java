package com.wanmi.sbc.setting.api.response.thirdaddress;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.setting.bean.vo.ThirdAddressPageVO;
import com.wanmi.sbc.setting.bean.vo.ThirdAddressVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>第三方地址映射表分页结果</p>
 * @author dyt
 * @date 2020-08-14 13:41:44
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ThirdAddressPageResponse extends BasicResponse {
    private static final long serialVersionUID = 1L;

    /**
     * 第三方地址映射表分页结果
     */
    @Schema(description = "第三方地址映射表分页结果")
    private MicroServicePage<ThirdAddressPageVO> thirdAddressVOPage;
}
